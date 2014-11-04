/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.smf.platform.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author
 * @version 1.0
 * @ClassName MySecurityInterceptor
 * @Description TODO
 * @date 2013-9-5 上午10:20:11
 */
public class SmfSecurityInterceptor extends AbstractSecurityInterceptor
        implements Filter {

    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    public void invoke(FilterInvocation fi) {
        InterceptorStatusToken token = super.beforeInvocation(fi);

        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (Exception e) {
            super.afterInvocation(token, null);
        }

    }


    /**
     *
     */
    @Override
    public void destroy() {
    }

    /**
     * @return
     */
    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * @return
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

}
