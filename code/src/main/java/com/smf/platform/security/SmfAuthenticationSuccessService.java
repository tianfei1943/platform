package com.smf.platform.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.security.api.SmfSecurityResourceService;
import com.smf.platform.security.api.SmfUserDetailService;

@Transactional
public class SmfAuthenticationSuccessService implements AuthenticationSuccessHandler {

	@Autowired
	@Qualifier("smfUserDetailService")
	private SmfUserDetailService smfUserDetailService;
	
	private static Logger logger = LoggerFactory.getLogger(SmfAuthenticationSuccessService.class); 
	
	private String target_url;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest reqest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		logger.info("用户登录成功，数据初始化");
		smfUserDetailService.updateUserDetail();
		//reqest.get
		response.sendRedirect(this.target_url);
	}
	
	

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	
	

}
