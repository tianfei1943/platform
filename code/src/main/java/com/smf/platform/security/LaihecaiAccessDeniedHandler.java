/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.smf.platform.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 14-6-17.
 */
public class LaihecaiAccessDeniedHandler implements AccessDeniedHandler {
    private static String ACCESS_DENIED_MSG = "message";
    private String accessDeniedUrl;

    public LaihecaiAccessDeniedHandler() {
    }
    public LaihecaiAccessDeniedHandler(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;

    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect(accessDeniedUrl);
        String deniedMessage = accessDeniedException.getMessage();
        String rp = request.getRequestURI();
        System.out.println(":::::"+deniedMessage);
        request.getSession().setAttribute(ACCESS_DENIED_MSG, deniedMessage);
    }


    public String getAccessDeniedUrl() {

        return accessDeniedUrl;

    }


    public void setAccessDeniedUrl(String accessDeniedUrl) {

        this.accessDeniedUrl = accessDeniedUrl;

    }

}