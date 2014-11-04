package com.smf.platform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.common.SmfConst;
import com.smf.platform.security.api.SmfSecurityService;
import com.smf.platform.security.domain.SmfUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysUserService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户详细信息获取接口
 * @author 实现类中，我们可以通过 loadUserByUsername 方法，
 *         根据用户名找到该用户的基本信息和角色信息。
 *         并创建 UserDetails 实现类对象返回。
 *         我们在这里设置了角色集合对象 array 并将其赋值给了User 对象。
 */
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
	
//	// 获取菜单接口
//	@Autowired
//	@Qualifier("smfSecurityService")
//	private SmfSecurityService smfSecurityService;
    
    /**
	 * 获取用户Details信息的回调函数.
	 */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
   // @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		
		System.out.println("SmfSecurityUserServiceImpl loadUserByUsername() invok begin");
		
		List<SysUser> users = sysUserService.queryByUserName(username);
		if(users.isEmpty()) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
        }
		SysUser tempUser = users.get(0);
		if(tempUser.getStatus().equals(SmfConst.STATUS_INACTIVE)){
			throw new UsernameNotFoundException("用户" + username + " 已停用");
        }
		SysUser sysUser = users.get(0); 

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(sysUser);

		//-- mini-web示例中无以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		SmfUserDetails smfUserDetails = new SmfUserDetails(sysUser.getUserCode(), sysUser.getPassword(), 
				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths, sysUser);

//		List<SysResource> rootMenus = new ArrayList<SysResource>();
//
//		List<SysResource> temp = new ArrayList<SysResource>();
//		// 获取当前用户可以访问的菜单
//		List<SysResource> menuSet = smfSecurityService.getMenusOfCurrentUser(smfUserDetails);
////		System.out.println("menuSet size: " + menuSet.size());
//		Iterator<SysResource> iter = menuSet.iterator();
//		while (iter.hasNext()) {
//			SysResource menu = iter.next();
//			this.findTitle(temp, menu);
//		}
//
//		List<SysResource> sysResources = new ArrayList<SysResource>();
//		// 类型转化
//		this.sysResourceSetToList(sysResources, menuSet);
//
//		// 获取有权限的父节点
//		List<SysResource> tempall = new ArrayList<SysResource>();
//		Iterator<SysResource> iterall = menuSet.iterator();
//		while (iterall.hasNext()) {
//			SysResource tempmenu = iterall.next();
//			this.findTitleOfAll(tempall, tempmenu);
//		}
//		// 合并所有有权限的菜单
//		for (SysResource sysResourcetemp : tempall) {
//			if (!sysResources.contains(sysResourcetemp)) {
//				sysResources.add(sysResourcetemp);
//			}
//		}
//
//		// 根据sortOrder对rootMenus进行排序
//		Object[] temps = temp.toArray();
//		Arrays.sort(temps, new Comparator() {
//			public int compare(Object t1, Object t2) {
//				SysResource obj1 = (SysResource) t1;
//				SysResource obj2 = (SysResource) t2;
//				return obj1.getSortOrder().intValue() - obj2.getSortOrder().intValue();
//			}
//		});
//
//		for (int i = 0; i < temps.length; i++) {
//			SysResource sr = (SysResource) temps[i];
//			rootMenus.add(sr);
//		}
//		
//		// 从sysResources中去除重复的菜单
//		this.removeDuplicate(sysResources);
//		
//		smfUserDetails.setCurrentSysResources(sysResources);
//		smfUserDetails.setRootMenus(rootMenus);
//		
//		Set<SysResource> osPropSysResources = smfSecurityService.getOsPropSysResourceOfCurrentUser(smfUserDetails);
//		smfUserDetails.setBoPropSysResources(osPropSysResources);
//				
		System.out.println("SmfSecurityUserServiceImpl loadUserByUsername() invoke end");
		
		return smfUserDetails;
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void removeDuplicate(List<SysResource> sysResources)  { 
//		HashSet h = new HashSet(sysResources);  
//		sysResources.clear();  
//		sysResources.addAll(h);  
//	}  
	
	/**
	 * 获得用户所有角色的权限集合.  存放的是权限Code
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(SysUser user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		// 获取用户对应的角色
		Set<SysRole> roleSet = user.getRoles();
		Iterator<SysRole> roleIter = roleSet.iterator();
		while (roleIter.hasNext()) {
			SysRole role = roleIter.next();
			Set<SysPrivilege> privilegeSet = role.getPrivileges();
			Iterator<SysPrivilege> privilegeIter = privilegeSet.iterator();
			while (privilegeIter.hasNext()) {
				SysPrivilege privilege = privilegeIter.next();
				authSet.add(new SimpleGrantedAuthority(privilege.getPrivilegeCode()));
			}
		}
		// 还需获取用户所属组织对应的角色
		Set<SysGroup> groupSet = user.getGroups();
		Iterator<SysGroup> groupIter = groupSet.iterator();
		while (groupIter.hasNext()) {
			SysGroup sysGroup = groupIter.next();
			Set<SysRole> groupRoleSet = sysGroup.getRoles();
			Iterator<SysRole> groupRoleIter = groupRoleSet.iterator();
			while (groupRoleIter.hasNext()) {
				SysRole role = groupRoleIter.next();
				Set<SysPrivilege> privilegeSet = role.getPrivileges();
				Iterator<SysPrivilege> privilegeIter = privilegeSet.iterator();
				while (privilegeIter.hasNext()) {
					SysPrivilege privilege = privilegeIter.next();
					authSet.add(new SimpleGrantedAuthority(privilege.getPrivilegeCode()));
				}
			}	
		}
		
		return authSet;
	}
	
//	/**
//	 * 查找标题
//	 * 
//	 * @param temp
//	 * @param sysResource
//	 */
//	private void findTitle(List<SysResource> temp, SysResource sysResource) {
//		if (sysResource.getParent() == null) {
//			if (!temp.contains(sysResource)) {
//				temp.add(sysResource);
//			}
//		} else {
//			findTitle(temp, sysResource.getParent());
//		}
//
//	}

//	/**
//	 * 查找所有相关的父节点
//	 * 
//	 * @param temp
//	 * @param sysResource
//	 */
//	private void findTitleOfAll(List<SysResource> temp, SysResource sysResource) {
//		if (sysResource.getParent() == null) {
//			if (!temp.contains(sysResource)) {
//				temp.add(sysResource);
//			}
//		} else {
//			if (!temp.contains(sysResource.getParent())) {
//				temp.add(sysResource.getParent());
//			}
//			findTitle(temp, sysResource.getParent());
//		}
//
//	}
//
//	/**
//	 * 存储类型转换
//	 * 
//	 * @param sysResources
//	 * @param sysResourceSet
//	 */
//	private void sysResourceSetToList(List<SysResource> sysResources, List<SysResource> sysResourceSet) {
//		for (SysResource sysResource : sysResourceSet) {
//			if (sysResource.getStatus() == SmfConst.STATUS_SHOW) {
//				sysResources.add(sysResource);
//				if (sysResource.getChildren() != null) {
//					sysResourceSetToList(sysResources, sysResource.getChildren());
//				}
//			}
//		}
//	};

}
