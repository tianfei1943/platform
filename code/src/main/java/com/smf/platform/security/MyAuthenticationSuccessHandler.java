package com.smf.platform.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private String target_url;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest reqest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.println("=====3333=====");
		
		response.sendRedirect("http://www.baidu.com");
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	
	

}
