package com.timeline.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    * Request Header 에서 Access Token 을 꺼내고 여러가지 검사 후 유저 정보를 꺼내서 SecurityContext 에 저장
    *
    * 가입/로그인/재발급을 제외한 모든 Request 요청은 이 필터를 거치기 때문에 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않습니다.
    * 그리고 요청이 정상적으로 Controller 까지 도착했다면 SecurityContext 에 Member ID 가 존재한다는 것이 보장됩니다.
    * 대신 직접 DB 를 조회한 것이 아니라 Access Token 에 있는 Member ID 를 꺼낸 거라서, 탈퇴로 인해 Member ID 가 DB 에 없는 경우 등 예외 상황은 Service 단에서 고려해야 합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("[ doFilterInternal ]");

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);
        log.info("---jwt--->" + jwt);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // validateToken : 토큰 상태가 어떤지 알려줌
            Authentication authentication = tokenProvider.getAuthentication(jwt); // getAuthentication : 토큰 정보를 꺼냄
            SecurityContextHolder.getContext().setAuthentication(authentication); // 토큰 정보를 SecurityContext에 저장
        }
        filterChain.doFilter(request, response);
        // 실제 필터링 로직은 doFilterInternal 에 들어감
        // filterChain.doFilter : JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        log.info("[ resolveToken ]");

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        log.info("---AUTHORIZATION_HEADER--->" + AUTHORIZATION_HEADER);
        log.info("---bearerToken--->" + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            log.info("---StringUtils.hasText(bearerToken)--->" + StringUtils.hasText(bearerToken));
            log.info("---bearerToken.substring(7)--->" + bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}