package com.smf.platform.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.common.SmfConst;
import com.smf.platform.security.api.SmfAuthenticationSuccessService;
import com.smf.platform.security.api.SmfSecurityService;
import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.SysResource;

@Transactional
public class MyAuthenticationSuccessService implements SmfAuthenticationSuccessService {

	// 获取菜单接口
	@Autowired
	@Qualifier("smfSecurityService")
	private SmfSecurityService smfSecurityService;
	
	private String target_url;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest reqest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.println("用户登录成功，数据初始化");
		this.loadUserDetail();
		//reqest.get
		response.sendRedirect(this.target_url);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void removeDuplicate(List<SysResource> sysResources)  { 
		HashSet h = new HashSet(sysResources);  
		sysResources.clear();  
		sysResources.addAll(h);  
	}  
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadUserDetail(){
		List<SysResource> rootMenus = new ArrayList<SysResource>();

		List<SysResource> temp = new ArrayList<SysResource>();
		// 获取当前用户可以访问的菜单
		SmfUserDetails smfUserDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		List<SysResource> menuSet = smfSecurityService.getMenusOfCurrentUser(smfUserDetails);
		System.out.println("menuSet size: " + menuSet.size());
		Iterator<SysResource> iter = menuSet.iterator();
		while (iter.hasNext()) {
			SysResource menu = iter.next();
			this.findTitle(temp, menu);
		}

		List<SysResource> sysResources = new ArrayList<SysResource>();
		// 类型转化
		this.loadResourceSetToList(sysResources, menuSet);

		// 获取有权限的父节点
		List<SysResource> tempall = new ArrayList<SysResource>();
		Iterator<SysResource> iterall = menuSet.iterator();
		while (iterall.hasNext()) {
			SysResource tempmenu = iterall.next();
			this.findTitleOfAll(tempall, tempmenu);
		}
		// 合并所有有权限的菜单
		for (SysResource sysResourcetemp : tempall) {
			if (!sysResources.contains(sysResourcetemp)) {
				sysResources.add(sysResourcetemp);
			}
		}

		// 根据sortOrder对rootMenus进行排序
		Object[] temps = temp.toArray();
		Arrays.sort(temps, new Comparator() {
			public int compare(Object t1, Object t2) {
				SysResource obj1 = (SysResource) t1;
				SysResource obj2 = (SysResource) t2;
				return obj1.getSortOrder().intValue() - obj2.getSortOrder().intValue();
			}
		});

		for (int i = 0; i < temps.length; i++) {
			SysResource sr = (SysResource) temps[i];
			rootMenus.add(sr);
		}
		
		// 从sysResources中去除重复的菜单
		this.removeDuplicate(sysResources);
		
		smfUserDetails.setCurrentSysResources(sysResources);
		smfUserDetails.setRootMenus(rootMenus);
		
		Set<SysResource> osPropSysResources = smfSecurityService.getOsPropSysResourceOfCurrentUser(smfUserDetails);
		smfUserDetails.setBoPropSysResources(osPropSysResources);
	}
	
	/**
	 * 查找标题
	 * 
	 * @param temp
	 * @param sysResource
	 */
	private void findTitle(List<SysResource> temp, SysResource sysResource) {
		if (sysResource.getParent() == null) {
			if (!temp.contains(sysResource)) {
				temp.add(sysResource);
			}
		} else {
			findTitle(temp, sysResource.getParent());
		}

	}

	/**
	 * 查找所有相关的父节点
	 * 
	 * @param temp
	 * @param sysResource
	 */
	private void findTitleOfAll(List<SysResource> temp, SysResource sysResource) {
		if (sysResource.getParent() == null) {
			if (!temp.contains(sysResource)) {
				temp.add(sysResource);
			}
		} else {
			if (!temp.contains(sysResource.getParent())) {
				temp.add(sysResource.getParent());
			}
			findTitle(temp, sysResource.getParent());
		}

	}

	/**
	 * 存储类型转换
	 * 
	 * @param sysResources
	 * @param sysResourceSet
	 */
	private void loadResourceSetToList(List<SysResource> sysResources, List<SysResource> sysResourceSet) {
		for (SysResource sysResource : sysResourceSet) {
			if (sysResource.getStatus() == SmfConst.STATUS_SHOW) {
				sysResources.add(sysResource);
				if (sysResource.getChildren() != null) {
					loadResourceSetToList(sysResources, sysResource.getChildren());
				}
			}
		}
	};

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	
	

}
