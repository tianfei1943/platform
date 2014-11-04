package com.smf.platform.system.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysGroupService;


@SuppressWarnings("unchecked")
@Component("sysGroupService")
public class SysGroupServiceImpl extends SmfBaseServiceImpl<SysGroup> implements SysGroupService {

	
	public SysGroupServiceImpl(){	
	}
	
	public SysGroup getSysGroupByCode(String sysGroupCode) {
		String sql = "from SysGroup t where t.groupCode = '" + sysGroupCode + "' and t.systemId='" + SystemConfig.getSystemId() + "'";
		List<SysGroup> groups = this.query(sql);
		if  (groups != null && groups.size() > 0) {
			return groups.get(0);
		}
		return null;
	}
	
	public List <SysGroup> listSysGroupRelationOfRoot(){
		String s = "from SysGroup s where s.parents is empty and s.systemId='" + SystemConfig.getSystemId() + "' order by s.id ";
		List <SysGroup> datas = this.dao.query(s, true);
		return datas;
	}

	public SysGroup getSysGroupByName(String sysGroupName) {
		String sql = "from SysGroup t where t.groupName = '" + sysGroupName + "' and t.systemId='" + SystemConfig.getSystemId() + "'";
		List<SysGroup> groups = this.query(sql);
		if  (groups != null && groups.size() > 0) {
			return groups.get(0);
		}
		return null;
	}
	
	public void save(SysGroup sysGroup) {
		sysGroup.setSystemId(SystemConfig.getSystemId());
		this.dao.save(sysGroup);
	}
	
	public void removeSysGroupFromParent(Serializable parentId, SysGroup child) {
		SysGroup parent = (SysGroup)this.get(parentId);		
		if (parent != null) {
			parent.getChildren().remove(child);
		}
		this.update(parent);
	}

	public void removeSysUser(SysGroup parent, SysUser child) {
		parent.getUsers().remove(child);
		this.update(parent);
	}
	
	public PageResponse<SysGroup> listBy(String groupCode, int start, int limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) ");
		StringBuffer sb1 = new StringBuffer();
		sb1.append("from SysGroup s where 1=1");
		if(groupCode != null && !"".equals(groupCode.trim())){
			sb1.append(" and s.groupCode like'%" + groupCode+ "%'");
		}
		sb1.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		//sb1.append(" order by s.groupCode asc");
		sb.append(sb1.toString());
		int total = Integer.parseInt(this.query(sb.toString()).get(0).toString());
		List <SysGroup> result = this.queryByPage(sb1.toString(), null, start, limit);
		PageResponse <SysGroup> pageResponse = new PageResponse<SysGroup>();
		pageResponse.setTotal(total);
		pageResponse.setResult(result);
		return pageResponse;
	}

	public void removeList(List<Long> id) {
		for(Long id1:id){
			this.dao.remove(SysGroup.class, id1);
		}
		
	}

	public List<SysGroup> list() {
		String sql = "from  SysGroup s where s.systemId='" + SystemConfig.getSystemId() + "'";
		return this.dao.query(sql);
	}
	
	public List<SysGroup> listSysUserItemList() {
		StringBuffer sb = new StringBuffer();
		sb.append("from SysGroup t where t.systemId='" + SystemConfig.getSystemId() + "' order by t.groupCode");
		return this.query(sb.toString());
	}
	
}