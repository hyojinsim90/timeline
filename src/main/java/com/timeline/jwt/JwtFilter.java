package com.timeline.jwt;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-08 오후 4:07
 * @brief :  Spring Request 앞단에 붙일 Custom Filter
 **/
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // OncePerRequestFilter : 요청 받을 떄 한번 실행

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;


    /*
     * 실제 필터링 로직 수행 - 토큰을 꺼내서 검사하는 필터링
     * Request Header 에서 Access Token 을 꺼내고 여러가지 검사 후 유저 정보를 꺼내서 SecurityContext 의 UserDetail에 저장
     *
     * 가입/로그인/재발급을 제외한 모든 Request 요청은 이 필터를 거치기 때문에 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않습니다.
     * 그리고 요청이 정상적으로 Controller 까지 도착했다면 SecurityContext 에 Member ID 가 존재한다는 것이 보장됩니다.
     * 대신 직접 DB 를 조회한 것이 아니라 Access Token 에 있는 Member ID 를 꺼낸 거라서, 탈퇴로 인해 Member ID 가 DB 에 없는 경우 등 예외 상황은 Service 단에서 고려해야 합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("[ 토큰 검사 ]");

        // 1. Request Header 에서 access 토큰을 꺼냄
        String jwt = resolveToken(request);

        if(jwt != null ){
            if(jwt.startsWith("ya29")){

                log.info("- 토큰 유형 : GOOGLE -" );
                String reqURL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token="+jwt;

                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 요청에 필요한 Header에 포함될 내용 conn.setRequestProperty("Authorization", "Bearer " + access_Token);
                int responseCode = conn.getResponseCode();

                if(responseCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = "";
                    String result = "";
                    while ((line = br.readLine()) != null) {
                        result += line;
                    }
                    JsonParser parser = new JsonParser();
                    System.out.println("result : " + result);

                    JsonElement element = parser.parse(result);
                    String name = element.getAsJsonObject().get("name").getAsString();
                    String email = element.getAsJsonObject().get("email").getAsString();

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "google");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } else {
                log.info("- 토큰 유형 : 일반사용자 -" );
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // validateToken : 토큰 상태가 어떤지 알려줌
                    Authentication authentication = tokenProvider.getAuthentication(jwt); // getAuthentication : 토큰 정보를 꺼냄
                    SecurityContextHolder.getContext().setAuthentication(authentication); // 토큰 정보를 SecurityContext에 저장
                }
            }

        }

//        // 2. validateToken 으로 토큰 유효성 검사
//        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // validateToken : 토큰 상태가 어떤지 알려줌
//            Authentication authentication = tokenProvider.getAuthentication(jwt); // getAuthentication : 토큰 정보를 꺼냄
//            SecurityContextHolder.getContext().setAuthentication(authentication); // 토큰 정보를 SecurityContext에 저장
//        }

        log.info("getRequestURI : "+ request.getRequestURI());
        log.info("getParameterMap : "+ request.getParameterMap().entrySet());
        log.info("getParameterMap : "+ request.getParameterMap().keySet());
        log.info("getPathInfo : "+ request.getPathInfo());
        log.info("getMethod : "+ request.getMethod());
        log.info("getContextPath : "+ request.getContextPath());
        log.info("getHeaderNames : "+ request.getHeaderNames());
        log.info("getAuthType : "+ request.getAuthType());
        log.info("getPathTranslated : "+ request.getPathTranslated());
        log.info("getRemoteUser : "+ request.getRemoteUser());
        log.info("getQueryString : "+ request.getQueryString());
        log.info("getRequestedSessionId : "+ request.getRequestedSessionId());
        log.info("getServletPath : "+ request.getServletPath());
        log.info("getContentType : "+ request.getContentType());
        log.info("getLocalName : "+ request.getLocalName());
        log.info("getServerName : "+ request.getServerName());
        log.info("getParameterNames : "+ request.getParameterNames());

        filterChain.doFilter(request, response);
        // 실제 필터링 로직은 doFilterInternal 에 들어감
        // filterChain.doFilter : JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        log.info("[ 토큰 정보 꺼내오기 ]");

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        log.info("- Authorization 헤더 확인 : " + bearerToken); // 'Bearer ' + access_token

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            log.info("- access_token : " + bearerToken.substring(7)); // Bearer 을 제거하고 access_token 만 얻음
            return bearerToken.substring(7); // access_token을 반환
        }
        return null;
    }
}
