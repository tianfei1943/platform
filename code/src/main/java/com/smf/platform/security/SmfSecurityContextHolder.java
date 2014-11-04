package com.smf.platform.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.SysUser;

public class SmfSecurityContextHolder {
	
	public static SysUser getCurrentUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if(principal instanceof SmfUserDetails)
				return ((SmfUserDetails)principal).getUser();
			return null;
		}
		return null;
	}
	
	public static SmfUserDetails getCurrentUserDetails(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if(principal instanceof SmfUserDetails)
				return (SmfUserDetails)principal;
			return null;
		}
		return null;
	}

}
