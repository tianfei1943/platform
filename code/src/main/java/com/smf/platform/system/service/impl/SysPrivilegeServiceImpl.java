package com.smf.platform.system.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.security.SmfSecurityMetadataSource;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.service.api.SysPrivilegeService;

@SuppressWarnings("unchecked")
@Component("sysPrivilegeService")
public class SysPrivilegeServiceImpl extends SmfBaseServiceImpl<SysPrivilege> implements SysPrivilegeService{

	@Autowired
	@Qualifier("smfSecurityMetadataSource")
	private SmfSecurityMetadataSource databaseDefinitionSource = null; 
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public PageResponse<SysPrivilege> list(SysPrivilege privilege, String privilegeType, int start, int limit) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from  SysPrivilege s  where  1=1");
		if(privilege!=null){
			if(privilege.getPrivilegeName()!=null&&!"".equals(privilege.getPrivilegeName())){
				sbf.append(" and s.privilegeName like '%" + privilege.getPrivilegeName() + "%'");
			}
			if(privilege.getOpClass()!=null&&!"".equals(privilege.getOpClass())){
				sbf.append(" and s.opClass = '" + privilege.getOpClass() + "'");
			}
			if(privilege.getOpMethod()!=null&&!"".equals(privilege.getOpMethod())){
				sbf.append(" and s.opMethod = '" + privilege.getOpMethod() + "'");
			}
		}
		sbf.append(" and s.privilegeType = '" + privilegeType + "'");
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		
		sbf1.append(sbf.toString()).append(" order by s.privilegeName asc");
		countSql.append(sbf.toString());
		
		int total = Integer.parseInt(this.query(countSql.toString()).get(0).toString());
		List<SysPrivilege> result = this.queryByPage(sbf1.toString(), null, start, limit);
		PageResponse<SysPrivilege> pageresponse = new PageResponse<SysPrivilege>();
		pageresponse.setTotal(total);
		pageresponse.setResult(result);
		return pageresponse;
	}

	public List<SysPrivilege> listByName(String privilegeName) {
		String sql = "from SysPrivilege s where s.privilegeName = '"+privilegeName+"' and s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}

	public List<SysPrivilege> listByCode(String privilegeCode) {
		String sql = "from SysPrivilege s where s.privilegeCode = '"+privilegeCode+"' and s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}

	public List<SysPrivilege> listAllPrivilege() {
		String sql = "from SysPrivilege s where s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}
	
	public SysPrivilege queryByPrivilegeCode(String privilegeCode) {
		String sql = "from SysPrivilege s where s.privilegeCode = '"+privilegeCode+"' and s.systemId='" + SystemConfig.getSystemId() + "'";
		List<SysPrivilege> privileges = this.dao.query(sql);
		if (privileges != null && privileges.size() > 0) {
			return privileges.get(0);
		}
		return null;
	}
	
	// 添加、修改、删除权限后，需更新SpringSecurity中数据
	public void save(SysPrivilege sysPrivilege) {
		sysPrivilege.setSystemId(SystemConfig.getSystemId());
		this.dao.save(sysPrivilege);
		try {
			databaseDefinitionSource.refreshDefinitionSource();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void update(SysPrivilege sysPrivilege) {
		this.dao.update(sysPrivilege);
		try {
			databaseDefinitionSource.refreshDefinitionSource();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void remove(List<Long> idList) {
		for(Long id : idList){
			this.removeById(id);
		}
		try {
			databaseDefinitionSource.refreshDefinitionSource();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
