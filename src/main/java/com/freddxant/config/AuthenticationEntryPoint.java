/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freddxant.config;

import com.freddxant.model.response.ResponseModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddxant
 */
@Slf4j
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        log.error("ERROR Unauthorized - 401");
        Gson gson = new Gson();
        ResponseModel res = new ResponseModel(401, "UNAUTHORIZED", authEx.getMessage(), null);
        String jsonS = gson.toJson(res);
        PrintWriter writer = response.getWriter();
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.println(jsonS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("freddxant");
        super.afterPropertiesSet();
    }
    
}
