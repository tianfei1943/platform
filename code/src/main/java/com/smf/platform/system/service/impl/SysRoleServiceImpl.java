package com.smf.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.service.api.SysRoleService;

@SuppressWarnings("unchecked")
@Component("sysRoleService")
public class SysRoleServiceImpl extends SmfBaseServiceImpl<SysRole> implements SysRoleService {
	/**
	 * 初期显示查询
	 */
	public PageResponse<SysRole> list(SysRole role, int start, int limit) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from SysRole s where  1=1");
		if(role!=null){
			if(role.getRoleName()!=null && !"".equals(role.getRoleName())){
				sbf.append(" and s.roleName like '%" + role.getRoleName() + "%'");
			}
		}
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		sbf1.append(sbf.toString()).append(" order by s.roleName asc");
		countSql.append(sbf.toString());

		int total = Integer.parseInt(this.query(countSql.toString()).get(0).toString());
		List<SysRole> result = this.queryByPage(sbf1.toString(), null, start, limit);
		PageResponse<SysRole> pageresponse = new PageResponse<SysRole>();
		pageresponse.setTotal(total);
		pageresponse.setResult(result);
		return pageresponse;
	}

	public List<SysRole> listByCode(String roleCode) {
		String sql="from SysRole s where s.roleCode = '"+roleCode+"' and s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}

	public void remove(List<Long> idList) {
		for(Long id : idList){
			this.dao.remove(SysRole.class, id);
		}
	}

	public void save(SysRole role) {
		role.setSystemId(SystemConfig.getSystemId());
		this.dao.save(role);
	}
	
	public void saveOrUpdate(SysRole role) {
		if(role.getId() == null){
			role.setSystemId(SystemConfig.getSystemId());
			this.dao.save(role);
		}else{
			role.setSystemId(SystemConfig.getSystemId());
			this.dao.update(role);
		}
	}

	public List<SysRole> listByName(String roleName) {
		String sql="from SysRole s where s.roleName = '"+roleName+"' and s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}

	@Override
	public List<SysRole> listSysRole() {
		String sql="from SysRole s where s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}
	
}



