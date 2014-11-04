/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.smf.platform.security;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问决策器， 决定某个用户具体的角色，是否有足够的权限访问某个资源
 * @author decide 方法接受三个参数，
 *         第一个使用户拥有的角色，
 *         第二个参数就是在 过滤器中新建的 FilterInvocation 对象，
 *         第三个参数就是该访问路径对应的角色集合。
 *         然后可以根据 用户角色和菜单角色对比，判断该用户是否具有该路径的访问资格。如果没有，抛出异常。
 * @version 1.0
 * @ClassName MyAccessDecisionManager
 * @Description TODO
 * @date 2013-9-5 上午11:46:35
 */
public class SmfAccessDecisionManager implements AccessDecisionManager {

    /**
     * LOGGER 日志对象
     */
    private final static Logger LOGGER = Logger.getLogger(SmfAccessDecisionManager.class);

    /**
     * decide 方法接受三个参数，
     * 第一个使用户拥有的角色，
     * 第二个参数就是在 过滤器中新建的 FilterInvocation 对象，
     * 第三个参数就是该访问路径对应的角色集合。
     * 然后可以根据 用户角色和菜单角色对比，判断该用户是否具有该路径的访问资格。
     * 如果没有，抛出异常。
     * @param authentication  //用户的角色信息
     * @param object  //当前被访问的菜单
     * @param configAttributes  //菜单对应的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        LOGGER.info("MyAccessDecisionManager.decide");
        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        
        for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            ConfigAttribute c = iter.next();
            String needRole = c.getAttribute();

            LOGGER.info("菜单["+object.toString()+"]访问权限：" + needRole);
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("没有权限访问这个菜单, 结束。");
    }

    /**
     * @param attribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}