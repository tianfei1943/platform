package com.smf.platform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.smf.platform.common.SmfConst;
import com.smf.platform.security.domain.SmfUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysUserService;
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
public class SmfUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
    
    /**
	 * 获取用户Details信息的回调函数.
	 */
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
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

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		SmfUserDetails smfUserDetails = new SmfUserDetails(sysUser.getUserCode(), sysUser.getPassword(), 
				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths, sysUser);
		
		return smfUserDetails;
	}

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
	
}
