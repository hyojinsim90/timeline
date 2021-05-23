package com.timeline.jwt;

import com.timeline.controller.dto.auth.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-08 오후 4:07
 * @brief : 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴. JWT 토큰에 관련된 암호화, 복호화, 검증 로직은 다 이곳에서 이루어짐
**/
@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;

    /*
    * 생성자
    * application.properties 에 정의해놓은 jwt.secret 값을 가져와서
    * JWT 를 만들 때 사용하는 암호화 키값을 생성합니다.
     * */
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
    * generateTokenDto
    * 유저 정보를 넘겨받아서 Access Token 과 Refresh Token 을 생성합니다.
    * 넘겨받은 유저 정보의 authentication.getName() 메소드가 username 을 가져옵니다.
    * 저는 username 으로 Member ID 를 저장했기 때문에 해당 값이 설정될 겁니다.
    * Access Token 에는 유저와 권한 정보를 담고 Refresh Token 에는 아무 정보도 담지 않습니다.
    * */
    public TokenDto generateTokenDto(HttpServletResponse response,Authentication authentication) {
        log.info("[토큰 생성]");

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.info(" - 권한 : " + authorities);

        // 토큰 만료시간 설정
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // 유저 // payload "sub": "name" // sub: 토큰 제목
                .claim(AUTHORITIES_KEY, authorities)        // 권한 정보 // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시) // exp: 토큰의 만료시간
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512" // alg: Signature 를 해싱하기 위한 알고리즘 정보를 갖고 있음
                .compact();

        log.info("- access token : " + accessToken );

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        log.info("- refresh token : " + refreshToken );

        response.addHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + " " + accessToken);
        // response header에 Authorization: Bearer + access token 추가

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }


    /*
    * getAuthentication
    * JWT 토큰을 복호화하여 토큰에 들어 있는 정보를 꺼냅니다.
    * Access Token 에만 유저 정보를 담기 때문에 명시적으로 accessToken 을 파라미터로 받게 했습니다.
    * Refresh Token 에는 아무런 정보 없이 만료일자만 담았습니다.
    * UserDetails 객체를 생생성해서 UsernamePasswordAuthenticationToken 형태로 리턴하는데 SecurityContext 를 사용하기 위한 절차라고 생각하면 됩니다..
    * 사실 좀 불필요한 절차라고 생각되지만 SecurityContext 가 Authentication 객체를 저장하기 때문에 어쩔수 없습니다.
    * */
    public Authentication getAuthentication(String accessToken) {
        log.info("[토큰 복호화]");

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        log.info("- UserDetails - 권한 : " + authorities); // [ROLE_USER]
        log.info("- UserDetails- 아이디 : " + claims.getSubject()); // 1
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /*
    * 토큰 정보를 검증합니다.
    * request로 넘어온 access_token을 받아 Jwts를 통해 검증합니다.
    * Jwts 모듈이 알아서 Exception 을 던져줍니다.
    * */
    public boolean validateToken(String token) {
        log.info("[토큰 정보 검증]");

        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        log.info(" - jwt 정보 " + claims.getBody());

        boolean isNotExpire = claims.getBody().getExpiration().after(new Date());

        log.info(" - jwt isNotExpire " + isNotExpire);

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /*
    * 만료된 토큰이어도 정보를 꺼내기 위해
    * */
    public Claims parseClaims(String accessToken) {
        log.info("[ parseClaims ]");
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
