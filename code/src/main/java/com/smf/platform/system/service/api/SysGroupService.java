package com.smf.platform.system.service.api;

import java.io.Serializable;
import java.util.List;

//import com.smf.platform.component.jsf.tree.PFTreeNode;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysUser;

public interface SysGroupService extends SmfBaseService<SysGroup>  {
	
	/**
	 * 返回所有父节点为null的组织机构关系
	 * @return
	 */
	public List <SysGroup> listSysGroupRelationOfRoot();

	/**
	 * 根据组织机构编码查询数据
	 * @param sysGroupCode
	 * @return
	 */
	public SysGroup getSysGroupByCode(String sysGroupCode);
	
	/**
	 * 根据组织机构名称查询数据
	 * @param sysGroupName
	 * @return
	 */
	public SysGroup getSysGroupByName(String sysGroupName);
	
	/**
	 * 根据SQL查询语句获取用户组树的根节点
	 * @param sql 查询语句
	 * @param isUser 是否显示用户节点
	 */
//	public PFTreeNode getSysGroupTree(String sql,boolean isUser);
	
	public void removeSysGroupFromParent(Serializable parentId, SysGroup child);
	public void removeSysUser(SysGroup parent, SysUser child);
	
	/**
	 * 根据组织机构编码查询组织机构
	 * @param groupCode
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse <SysGroup> listBy (String groupCode, int start, int limit);
	
	public void save(SysGroup sysGroup);
	
	/**
	 * 删除一条或多条记录
	 * @param id
	 */
	public void removeList(List<Long> id);
	
	/**
	 * 查询组织机构
	 * @return
	 */
	public List<SysGroup> list();

	/**
	 * 
	 * @return
	 */
	public List<SysGroup> listSysUserItemList();

}
