package com.smf.platform.security.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.security.api.SmfSecurityResourceService;
import com.smf.platform.security.api.SmfSecurityService;
import com.smf.platform.system.domain.SysResource;

/**
 * 从数据库查询URL--授权定义Map的实现类.
 * 项目实现的URL-授权查询服务
 * 
 * @author calvin
 */
@Component("smfSecurityResourceService")
@Transactional(readOnly = true)
public class SmfSecurityResourceServiceImpl implements SmfSecurityResourceService {
	
	@Autowired
	@Qualifier("smfSecurityService")
	private SmfSecurityService smfSecurityService;

	/**
	 * @see SmfSecurityResourceService#getRequestMap()
	 */
	public LinkedHashMap<String, String> getRequestMap() {//NOSONAR
		List<SysResource> resourceList = smfSecurityService.getAllUrlResources();

		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
		for (SysResource resource : resourceList) {
			if (resource.getResourceUrl() != null) {
				requestMap.put("/" + resource.getResourceUrl(), resource.getAuthNames());
			}
		}
		return requestMap;
	}
}
