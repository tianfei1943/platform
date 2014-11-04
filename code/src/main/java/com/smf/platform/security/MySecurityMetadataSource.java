/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.smf.platform.security;

import com.smf.platform.security.api.SmfSecurityResourceService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;

/**
 * 资源源数据定义， 将所有的资源和权限的对应关系建立起来，即定义某一资源可以被哪些角色去访问。
 * securityMetadataSource 这个类型的接口，
 * 提供了根据访问资源获取角色集合的接口，
 * 也就是说此类维护着，
 * 资源和角色的关系并提供外界使用。
 * securityMetadataSource 必须是 FilterInvocationSecurityMetadataSource 接口实现类。
 * <p/>
 * 我在 loadResourceDefine 方法中，初始化了资源。将资源和权限以 Map 的形式做了映射。
 * 一个地址会对应一组权限。然后实现了接口方法 public Collection<ConfigAttribute> getAttributes(Object object) ，
 * 可以通过此方法获取权限集合。
 * 这个接口的调用在 AbstractSecurityInterceptor 的 beforeInvocation 方法中。
 *
 * @version 1.0
 * @ClassName MySecurityMetadataSource
 * @Description TODO
 * @date 2013-9-5 上午10:50:30
 */
public class MySecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

	@Autowired
	@Qualifier("smfSecurityResourceService")
	private SmfSecurityResourceService smfSecurityResourceService;

    /**
     * LOGGER 日志对象
     */
    private final static Logger LOGGER = Logger.getLogger(MySecurityMetadataSource.class);

    //资源和权限以 Map 的形式做了映射
    private LinkedHashMap<String, Collection<ConfigAttribute>> map = new LinkedHashMap<String, Collection<ConfigAttribute>>();

    /**
     * 加载资源，初始化资源变量
     */
    private void loadResourceDefine() {
    	LinkedHashMap<String, String> srcMap = smfSecurityResourceService.getRequestMap();
    	for (Map.Entry<String, String> entry : srcMap.entrySet()) {
    		String key = entry.getKey();
    		String values = entry.getValue();
    		if(StringUtils.isNotBlank(values)){
    			Collection<ConfigAttribute> array = new ArrayList<ConfigAttribute>(values.length());
    			String[] rolesArr = values.split(",");
    			for(int i=0;i<rolesArr.length;i++){
    				String roleName = rolesArr[i];
    				ConfigAttribute cfg = new SecurityConfig(roleName);
    				array.add(cfg);
    			}
    			map.put(key, array);
    		}
    	}
    }


    public MySecurityMetadataSource() {
       // loadResourceDefine();
    }

    /**
     * 根据路径获取访问权限的集合接口
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if (map.size() < 1) {
            loadResourceDefine();
        }
        LOGGER.info(object);

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        RequestMatcher matcher = null;
        String resUrl = null;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (null != resUrl && matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    
    public void refreshDefinitionSource() throws Exception {
    	LOGGER.info("刷新资源权限对应数据");
    	loadResourceDefine();
	}

}

