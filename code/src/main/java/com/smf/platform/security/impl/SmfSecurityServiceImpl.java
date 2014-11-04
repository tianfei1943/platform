package com.smf.platform.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.common.SmfConst;
import com.smf.platform.security.api.SmfSecurityService;
import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.service.api.SysPrivilegeService;
import com.smf.platform.system.service.api.SysResourceService;

/**
 *
 *	
 * @author robbie
 * @since 2011-08-10
 */
@Component("smfSecurityService")
public class SmfSecurityServiceImpl implements SmfSecurityService {

	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService;
	
	@Autowired
	@Qualifier("sysPrivilegeService")
	private SysPrivilegeService sysPrivilegeService;
	
	@Transactional(readOnly = true)
	public List<SysResource> getAllUrlResources() {
		List<SysResource> menuResources = sysResourceService.listAllSysResource(SmfConst.PRIV_RESOURCE_TYPE_MENU);
		List<SysResource> urlResources = sysResourceService.listAllSysResource(SmfConst.PRIV_RESOURCE_TYPE_URL);
		menuResources.addAll(urlResources);
		return menuResources;
	}
	
	public List<SysResource> getMenusOfCurrentUser(SmfUserDetails smfUserDetails) {
		List<SysResource> menus = new ArrayList<SysResource>();
		Set<SysPrivilege> privs = getPrivilegesOfCurrentUser(smfUserDetails);
		for(SysPrivilege pri : privs) {
			if(pri.getPrivilegeType() != null && pri.getPrivilegeType().equals(SmfConst.PRIV_RESOURCE_TYPE_MENU)) {
				List<SysResource> resources = pri.getResources();
				if(resources != null)
					for (SysResource resource : resources) {
						if (resource.getStatus() == SmfConst.STATUS_SHOW) {
							menus.add(resource);
						}
					}
			}
		}
		return menus;
	}
	
	private Set<SysPrivilege> getPrivilegesOfCurrentUser() {
		Set<SysPrivilege> sysPrivs = new HashSet<SysPrivilege>();
		UserDetails currentUserDetails = getCurrentUserDetails();
		Collection<GrantedAuthority> privStrSet = null;
		if(currentUserDetails != null)
			privStrSet = (Collection<GrantedAuthority>) currentUserDetails.getAuthorities();
		if(privStrSet != null)
			for(GrantedAuthority ga : privStrSet) {
				SysPrivilege priv = sysPrivilegeService.queryByPrivilegeCode(ga.getAuthority());
				sysPrivs.add(priv);
			}
		return sysPrivs;
	}
	
	private Set<SysPrivilege> getPrivilegesOfCurrentUser(SmfUserDetails smfUserDetails) {
		Set<SysPrivilege> sysPrivs = new HashSet<SysPrivilege>();
		Collection<GrantedAuthority> privStrSet = null;
		if(smfUserDetails != null)
			privStrSet = smfUserDetails.getAuthorities();
		if(privStrSet != null)
			for(GrantedAuthority ga : privStrSet) {
				SysPrivilege priv = sysPrivilegeService.queryByPrivilegeCode(ga.getAuthority());
				sysPrivs.add(priv);
			}
		return sysPrivs;
	}
	
	private UserDetails getCurrentUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof SmfUserDetails)
			return (UserDetails)principal;
		return null;
	}

	public Set<SysResource> getBosOfCurrentUser(String boName) {
		Set<SysResource> bos = new HashSet<SysResource>();
		Set<SysPrivilege> privs = getPrivilegesOfCurrentUser();
		for(SysPrivilege pri : privs) {
			if(pri.getPrivilegeType() != null && pri.getPrivilegeType().equals(SmfConst.PRIV_RESOURCE_TYPE_CLASS)) {
				List<SysResource> resources = pri.getResources();
				if(resources != null)
					for (SysResource temp : resources) {
						if (temp.getResourceName().equals(boName) && temp.getStatus() == SmfConst.STATUS_SHOW) {
							bos.add(temp);
						}
					}
			}
		}
		return bos;
	}
	
	
	public Set<SysResource> getOsPropSysResourceOfCurrentUser(SmfUserDetails smfUserDetails) {
		Set<SysResource> bos = new HashSet<SysResource>();
		Set<SysPrivilege> privs = getPrivilegesOfCurrentUser(smfUserDetails);
		for(SysPrivilege pri : privs) {
			if(pri.getPrivilegeType() != null && pri.getPrivilegeType().equals(SmfConst.PRIV_RESOURCE_TYPE_PROP)) {
				List<SysResource> resources = pri.getResources();
				if(resources != null)
					for (SysResource temp : resources) {
						if (temp.getResourceType().equals(SmfConst.PRIV_RESOURCE_TYPE_PROP) && temp.getStatus() == SmfConst.STATUS_SHOW) {
							bos.add(temp);
						}
					}
			}
		}
		return bos;
	}
	
	
}
