package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysUser;

public interface SysUserService extends SmfBaseService<SysUser>{
	
	/**
	 * 根据用户姓名查询用户的数据
	 * @param userCode
	 * @return
	 */
	public List<SysUser> queryByUserName(String userName);
	
	/**
	 * 根据用户编码查询用户的数据
	 * @param userCode
	 * @return
	 */
	public List<SysUser> queryByUserCode(String userCode);
	/**
	 * 根据用户编码查询用户的数据
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysUser> listUser(String userCode, int start, int limit); 
	
	/**
	 * 保存用户
	 * @param user
	 */
	public void save(SysUser user);
	
	/**
	 * 保存或更新用户
	 * @param user
	 */
	public void saveOrUpdate(SysUser user);
	
	/**
	 * 删除用户
	 * @param idlist
	 */
	public void remove(List<Long> idlist);
	
	/**
	 * 恢复用户
	 * @param idlist
	 */
	public void restore(List<Long> idlist);
	
	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(SysUser user);
	
	public List<SysUser> list();
}
