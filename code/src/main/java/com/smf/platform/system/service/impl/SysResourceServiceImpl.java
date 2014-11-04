package com.smf.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.SmfConst;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.service.api.SysResourceService;

@SuppressWarnings("unchecked")
@Component("sysResourceService")
public class SysResourceServiceImpl extends SmfBaseServiceImpl<SysResource> implements SysResourceService {

	public PageResponse<SysResource> listBy(String resourceName, String resourceType, int start, int limit) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from SysResource s  where  1=1");
		if(resourceName!=null && !"".equals(resourceName)){
			sbf.append(" and s.resourceName like '%" + resourceName + "%'");
		}
		if(resourceType!=null && !"".equals(resourceType)){
			sbf.append(" and s.resourceType = '" + resourceType + "'");
		}
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		sbf.append(" and s.status=1");
		sbf1.append(sbf.toString()).append(" order by s.resourceName asc");
		countSql.append(sbf.toString());
		
		int total = Integer.parseInt(this.query(countSql.toString()).get(0).toString());
		List<SysResource> result = this.queryByPage(sbf1.toString(), null, start, limit);
		PageResponse<SysResource> pageresponse = new PageResponse<SysResource>();
		pageresponse.setTotal(total);
		pageresponse.setResult(result);
		return pageresponse;
	}
	
	public void save(SysResource resource) {
		resource.setSystemId(SystemConfig.getSystemId());
		this.dao.save(resource);
	}
	
	public void remove(List<Long> idlist) {
		for(Long id : idlist){
			SysResource resource = (SysResource)this.get(id);
			if (resource != null) {
				resource.setStatus(SmfConst.STATUS_HIDDEN);
				this.update(resource);
			}
//			this.removeById(id);
		}
	}

	public List<SysResource> list(String resourceName) {
		String sql = "from SysResource s where s.resourceName = '"+resourceName+"' and s.systemId='" + SystemConfig.getSystemId() + "' and s.status=1";
		return this.dao.query(sql);
	}

	public List<SysResource> listSysresource(String resourceType) {
		String sql = "from SysResource s where s.parent is null and s.resourceType = '"+resourceType+"' and s.systemId='" + SystemConfig.getSystemId() + "' and s.status=1 order by s.sortOrder";
		List<SysResource> list = this.dao.query(sql, true);
		return list;
	}
	
	public List<SysResource> listAllSysResource(String resourceType) {
		String sql = "from SysResource s left join fetch s.privileges where s.resourceType = '"+resourceType+ "' and s.systemId='" + SystemConfig.getSystemId() + "' and s.status=1 order by s.sortOrder";
		List<SysResource> list = this.dao.query(sql, true);
		return list;
	}

	public PageResponse<SysResource> list(SysResource resource,String resourceType, int start, int limit) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from SysResource s  where  1=1");
		if(resource!=null){
			if(resource.getResourceName()!=null && !"".equals(resource.getResourceName())){
				sbf.append(" and s.resourceName like '%" + resource.getResourceName() + "%'");
			}
			if(resourceType!=null && !"".equals(resourceType)){
				if(resourceType!="-1"&&!"-1".equals(resourceType)){
					sbf.append(" and s.resourceType = '" + resourceType + "'");
				}
			}
		}
		sbf.append(" and s.resourceType <> '" + SmfConst.PRIV_RESOURCE_TYPE_MENU + "'");
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		sbf.append(" and s.status=1");
		sbf1.append(sbf.toString()).append(" order by s.resourceName asc");
		countSql.append(sbf.toString());
		int total = Integer.parseInt(this.query(countSql.toString()).get(0).toString());
		List<SysResource> result = this.queryByPage(sbf1.toString(), null, start, limit);
		PageResponse<SysResource> pageresponse = new PageResponse<SysResource>();
		pageresponse.setTotal(total);
		pageresponse.setResult(result);
		return pageresponse;
	}

	@Override
	public  String getSysresourceUrl(String resourceName, String resourceType) {
		String sql = "from  SysResource s where s.resourceName = '"+resourceName+"' and s.resourceType = '"+resourceType+"' and s.systemId='" + SystemConfig.getSystemId() + "' and s.status=1";
		List<SysResource>  listResources=  this.dao.query(sql);
		if(listResources.size()>0){
			return listResources.get(0).getResourceUrl();
		}
		return null;
	}
	
}
