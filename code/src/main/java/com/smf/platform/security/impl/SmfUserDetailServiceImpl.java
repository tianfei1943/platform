package com.smf.platform.security.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.smf.platform.common.SmfConst;
import com.smf.platform.security.SmfSecurityContextHolder;
import com.smf.platform.security.api.SmfSecurityService;
import com.smf.platform.security.api.SmfUserDetailService;
import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.SysResource;

@Component("smfUserDetailService")
public class SmfUserDetailServiceImpl implements SmfUserDetailService {

	@Autowired
	@Qualifier("smfSecurityService")
	private SmfSecurityService smfSecurityService;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateUserDetail(){
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void removeDuplicate(List<SysResource> sysResources)  { 
		HashSet h = new HashSet(sysResources);  
		sysResources.clear();  
		sysResources.addAll(h);  
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
	}

}
