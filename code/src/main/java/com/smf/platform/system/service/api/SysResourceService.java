package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysResource;

public interface SysResourceService extends SmfBaseService<SysResource> {
	
	/**
	 * 根据资源名称查询数据
	 * @param resourceName
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysResource> listBy(String resourceName, String resourceType, int start, int limit); 
	
	/**
	 * 根据资源名称查询
	 * @param resourceName
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysResource> list(SysResource resource,String resourceType, int start, int limit); 
	
	/**
	 * 保存资源
	 * @param limit
	 */
	public void save(SysResource sysResource);
	
	/**
	 * 删除资源
	 * @param limit
	 */
	public void remove(List<Long> idlist);
	
	/**
	 * 根据资源名称查询资源
	 * @param resourceName
	 * @return
	 */
	public List<SysResource> list(String resourceName);
	
	/**
	 * 查询资源
	 * @param limit
	 */
	public List<SysResource> listSysresource(String resourceType);
	
	public List<SysResource> listAllSysResource(String resourceType);
	
	/**根据资源名称,资源类型 查询资源内容
	 * @param resourceName
	 * @param resourceType
	 * @return
	 */
	public String getSysresourceUrl(String resourceName,String resourceType);
	
}
