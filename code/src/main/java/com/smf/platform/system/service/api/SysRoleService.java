package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysRole;

public interface SysRoleService extends SmfBaseService<SysRole>{
	
	/**
	 * 初期显示查询
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysRole> list(SysRole role, int start, int limit);
	/**
	 * 根据角色编码查询角色 用于验证编码的唯一性
	 * @param roleCode
	 * @return
	 */
	public List<SysRole> listByCode(String roleCode);
	
	/**
	 * 根据角色名称查询角色 用于验证名称的唯一性
	 * @param roleName
	 * @return
	 */
	public List<SysRole> listByName(String roleName);
	
	/**
	 * 保存角色
	 * @param role
	 */
	public void save(SysRole role);
	
	/**
	 * 删除角色
	 * @param idList
	 */
	public void remove(List<Long> idList);
	
	/**
	 * 保存或更新角色
	 * @param role
	 */
	public void saveOrUpdate(SysRole role);
	
	/** 获取所有角色
	 * @return
	 */
	public List<SysRole> listSysRole();
}
