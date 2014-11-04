package com.smf.platform.system.service.impl;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.smf.platform.log.LogOwnerProvider;

/**
 *
 *	
 * @author robbie
 * @since 2011-08-11
 */
public class LogOwnerProviderImpl implements LogOwnerProvider {

	/* (non-Javadoc)
	 * @see com.smf.platform.log.LogOwnerProvider#getRemoteAddress()
	 */
	public String getRemoteAddress() {
		WebAuthenticationDetails detail = (WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (detail != null) {
			return detail.getRemoteAddress();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.smf.platform.log.LogOwnerProvider#getUsername()
	 */
	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getName();
//			if (auth.getPrincipal() != null) {
//				return (auth.getPrincipal()).getUsername();
//			} 
//			return null;
		}
		return null;
	}
}
