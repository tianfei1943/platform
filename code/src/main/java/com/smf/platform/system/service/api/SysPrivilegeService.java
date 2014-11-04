package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysPrivilege;

public interface SysPrivilegeService extends SmfBaseService<SysPrivilege> {
	
	/**
	 * 查询菜单权限
	 * @param privilege
	 * @param privilegeType
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysPrivilege> list(SysPrivilege privilege,String privilegeType,int start, int limit);
	
	/**
	 * 根据权限名称查询权限 用于验证权限名的唯一性
	 * @param privilegeName
	 * @return
	 */
	public List<SysPrivilege> listByName(String privilegeName);
	
	/**
	 * 根据权限编码查询权限 用于验证权限编码的唯一性
	 * @param privilegeCode
	 * @return
	 */
	public List<SysPrivilege> listByCode(String privilegeCode);
	
	/**
	 * 根据权限编码查询权限数据
	 * @param privilegeCode
	 * @return
	 */
	public SysPrivilege queryByPrivilegeCode(String privilegeCode);
	
	/**
	 * 删除权限
	 * @param idList
	 */
	public void remove(List<Long> idList);
	
	/**
	 * 查询全部权限
	 * @return
	 */
	public List<SysPrivilege> listAllPrivilege();
	
	public void save(SysPrivilege sysPrivilege);
	
	public void update(SysPrivilege sysPrivilege);

}
