package com.timeline.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-08 오후 4:09
 * @brief : 인증 정보 없을 때 401 에러
 * @detail : 유저 정보 없이 접근하면 SC_UNAUTHORIZED (401) 응답을 내려줍니다.
**/
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
/*
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
*/

        log.info("-------------401에러------유저정보 없음------");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
