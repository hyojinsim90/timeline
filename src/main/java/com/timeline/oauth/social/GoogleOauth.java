package com.timeline.oauth.social;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.timeline.controller.dto.member.MemberSaveRequestDto;
import com.timeline.entity.member.Member;
import com.timeline.entity.member.RefreshToken;
import com.timeline.repository.MemberRepository;
import com.timeline.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:19
 * @brief : 공통 interface(SocialOauth)를 구현할 소셜 로그인 구글 타입 Class 생성
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager am;

    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.client.scope}")
    private String GOOGLE_SNS_CLIENT_SCOPE;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;
    @Value("${sns.google.userinfo.url}")
    private String GOOGLE_SNS_USERINFO_URL;

    /**
    *  redirect 처리를 할 URL을 생성하는 메소드
    * 리턴된 URL로 sendRedirect 하게됨
    * */
    @Override
    public String getOauthRedirectURL() {
        log.info("[ getOauthRedirectURL ]");

        Map<String, Object> params = new HashMap<>();
        params.put("scope", GOOGLE_SNS_CLIENT_SCOPE);
        params.put("response_type", "code");
        params.put("access_type", "offline");
        params.put("include_granted_scopes", "true");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        //  refresh_token 받을려면
        // params.put("access_type", "offline");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        log.info("--- parameterString ---> " + parameterString);

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    /**
    * API로부터 얻어온 code를 가지고 토큰 생성
    * */
    @Override
    public String requestAccessToken(String code) {
        log.info("[ requestAccessToken ]");
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        log.info("--- params --->" + params);

        // 토큰 생성해주는 url로 param정보를 보냄
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            // 생성된 토큰 및 정보 확인
            log.info("--- responseEntity.getBody() --->" + responseEntity.getBody());

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonobeject = (JsonObject) jsonParser.parse(responseEntity.getBody().toString());

            log.info("--- jsonobeject --->" + jsonobeject);

            log.info("--- access_token --->" + jsonobeject.get("access_token").toString());

            // access token으로 얻어낸 유저 정보 저장
            getGoogleUserInfo(jsonobeject);

            return responseEntity.getBody();
        }
        return "구글 로그인 요청 처리 실패";
    }

    public String requestAccessTokenUsingURL(String code) {
        try {
            URL url = new URL(GOOGLE_SNS_TOKEN_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", GOOGLE_SNS_CLIENT_ID);
            params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
            params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
            params.put("grant_type", "authorization_code");

            String parameterString = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));

            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
            bous.write(parameterString.getBytes());
            bous.flush();
            bous.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            if (conn.getResponseCode() == 200) {
                return sb.toString();
            }
            return "구글 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 구글 로그인 Access Token 요청 URL 입니다 :: " + GOOGLE_SNS_TOKEN_BASE_URL);
        }
    }

    /** * GOOGLE USER INFO * @param access_Token
     * @param jsonobeject
     * */
    public void getGoogleUserInfo (JsonObject jsonobeject) {

        String access_token = jsonobeject.get("access_token").toString();

        //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        // String reqURL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+access_Token;
        // String reqURL = "https://www.googleapis.com/userinfo/v2/me?access_token="+access_Token;
        String reqURL = GOOGLE_SNS_USERINFO_URL+access_token;

        try {
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


                // 회원가입
                if (!memberRepository.existsByEmail(email)){

                    // 회원가입
                    MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto();
                    memberSaveRequestDto.setNickname(name);
                    memberSaveRequestDto.setEmail(email);
                    memberSaveRequestDto.setPassword("google");

                    Member member = memberSaveRequestDto.toGoogleMember(passwordEncoder);
                    memberRepository.save(member);

                    // securityContextHolder저장
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "google");
                    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    log.info("SecurityContextHolder.getContext() : " +  SecurityContextHolder.getContext().getAuthentication());

                    // refresh_token 저장
                    if (jsonobeject.get("refresh_token") != null){

                        String refresh_token = jsonobeject.get("refresh_token").toString();
                        log.info("refresh token : " + refresh_token);

                        RefreshToken refreshToken = RefreshToken.builder()
                                .key(authentication.getName())
                                .value(refresh_token)
                                .build();

                    refreshTokenRepository.save(refreshToken);

                    }

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}