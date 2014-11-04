package com.smf.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.SmfConst;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysUserService;

@SuppressWarnings("unchecked")
@Component("sysUserService")
public class SysUserServiceImpl extends SmfBaseServiceImpl<SysUser> implements SysUserService{
	
	public List<SysUser> queryByUserCode(String userCode) {
		String sql = "from SysUser t where t.userCode = '" + userCode + "' and t.systemId='" + SystemConfig.getSystemId() + "'";
		return this.query(sql);
	}
	
	public List<SysUser> queryByUserName(String userName) {
		String sql = "from SysUser t where t.userName = '" + userName + "' and t.systemId='" + SystemConfig.getSystemId() + "'";
		return this.query(sql);
	}
	
	public PageResponse<SysUser> listUser(String userName, int start, int limit) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from SysUser s where  1=1");
//		sbf.append("from  SysUser s  where s.status='" + SmfConst.STATUS_ACTIVE + "' ");
		if(userName.trim()!=null && !"".equals(userName.trim())){
			sbf.append(" and s.userName like '%" + userName + "%'");
		}
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		sbf1.append(sbf.toString()).append(" order by s.userName asc");
		countSql.append(sbf.toString());
		
		int total =  Integer.parseInt(this.query(countSql.toString()).get(0).toString());
		List<SysUser> result = this.queryByPage(sbf1.toString(), null, start, limit);
		PageResponse<SysUser> pageresponse = new PageResponse<SysUser>();
		pageresponse.setTotal(total);
		pageresponse.setResult(result);
		return pageresponse;
	}
	
	public void remove(List<Long> idlist) {
		for(Long id : idlist){
			SysUser user = (SysUser)this.get(id);
			if (user != null) {
				user.setStatus(SmfConst.STATUS_INACTIVE);
				this.update(user);
			}
//			this.removeById(id);
		}
	}
	
	public void restore(List<Long> idlist) {
		for(Long id : idlist){
			SysUser user = (SysUser)this.get(id);
			if (user != null) {
				user.setStatus(SmfConst.STATUS_ACTIVE);
				this.dao.update(user);
			}
//			this.removeById(id);
		}
	}
	
	public void save(SysUser user) {
		user.setSystemId(SystemConfig.getSystemId());
		this.dao.save(user);
	}
	
	public void saveOrUpdate(SysUser user) {
		if(user.getId()!=null){
			user.setSystemId(SystemConfig.getSystemId());
			this.dao.update(user);
		}else{
			user.setSystemId(SystemConfig.getSystemId());
			this.dao.save(user);
		}
	}
	
	public void updateUser(SysUser user) {
		user.setSystemId(SystemConfig.getSystemId());
		this.dao.update(user);
	}
	
	public List<SysUser> list() {
		String sql = "from SysUser s where s.systemId='" + SystemConfig.getSystemId() + "'";
//		String sql = "from  SysUser s where s.status = '" + SmfConst.STATUS_ACTIVE + "'";
		return this.dao.query(sql);
	}
}
