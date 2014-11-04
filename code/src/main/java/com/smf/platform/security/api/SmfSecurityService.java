package com.smf.platform.security.api;

import java.util.List;
import java.util.Set;

import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.SysResource;

/**
 *
 *	
 * @author robbie
 * @since 2011-08-10
 */
public interface SmfSecurityService {
	
	/**
	 * 获取所有的菜单和URL资源
	 * @return
	 */
	public List<SysResource> getAllUrlResources();
	
	/**
	 * 获取当前用户的菜单资源
	 * @return
	 */
	public List<SysResource> getMenusOfCurrentUser(SmfUserDetails smfUserDetails);
	
	/**
	 * 根据业务对象名称获取当前用户的可以访问的业务对象资源
	 * @return
	 */
	public Set<SysResource> getBosOfCurrentUser(String boName);
	
	
	/**
	 * 根据业务对象属性名称获取当前用户的可以访问的业务对象资源
	 * @return
	 */
	public Set<SysResource> getOsPropSysResourceOfCurrentUser(SmfUserDetails smfUserDetails);
	
}
