package com.smf.platform.security.domain;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.domain.SysUser;

public class SmfUserDetails extends User {
	
	private static final long serialVersionUID = -3873397099397399262L;
	
	// 当前用户
	private SysUser user;
	
	// 当前用户的根菜单集合
	List<SysResource> rootMenus;
	
	// 当前用户的可以访问的菜单集合
	List<SysResource> currentSysResources;
	
	//当前用户可以访问的业务对象属性集合
	Set<SysResource> boPropSysResources;
	
	public SmfUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
	        boolean credentialsNonExpired, boolean accountNonLocked, Set<GrantedAuthority> authorities, SysUser sysUser) {
		super(username, password, enabled, accountNonExpired,
		        credentialsNonExpired, accountNonLocked, authorities);
		this.user = sysUser;
	}

	public SysUser getUser() {
		return user;
	}
	
	public void setRootMenus(List<SysResource> rootMenus){
		this.rootMenus = rootMenus;
	}

	public void setCurrentSysResources(List<SysResource> currentSysResources){
		this.currentSysResources = currentSysResources;
	}

	public List<SysResource> getRootMenus() {
		return rootMenus;
	}

	public List<SysResource> getCurrentSysResources() {
		return currentSysResources;
	}

	public Set<SysResource> getBoPropSysResources() {
		return boPropSysResources;
	}

	public void setBoPropSysResources(Set<SysResource> boPropSysResources) {
		this.boPropSysResources = boPropSysResources;
	}
	
}
