package com.smf.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysParameterValue;
import com.smf.platform.system.service.api.SysParameterValueService;

@SuppressWarnings("unchecked")
@Component("sysParameterValueService")
public class SysParameterValueServiceImpl extends SmfBaseServiceImpl<SysParameterValue> implements
		SysParameterValueService {

	/**
	 * 保存系统参数数据
	 * 
	 * @return
	 */
	public void save(SysParameterValue value) {
		value.setSystemId(SystemConfig.getSystemId());
		this.dao.save(value);
	}
	
	/**
	 * 修改更新系统参数数据
	 * 
	 * @return
	 */
	public void saveOrUpdate(SysParameterValue value) {
		if (dao.get(SysParameterValue.class, value.getId()) == null) {
			value.setSystemId(SystemConfig.getSystemId());
			this.dao.save(value);
		} else {
			this.dao.update(value);
		}
	}

	/**
	 * 删除系统参数数据
	 * 
	 * @return
	 */
	public void removeByIDList(List<Long> idlist) {
		for (Long id : idlist) {
			this.removeById(id);
		}
	}
	
	/**
	 * Create By Jlist_wx
	 * 
	 */
	public SysParameterValue getByName(String parameterName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from SysParameterValue s where s.name = '" + parameterName + "' and s.systemId='" + SystemConfig.getSystemId() + "'");
		List<SysParameterValue> values = this.dao.query(sql.toString());
		if (values != null && values.size() > 0) {
			return values.get(0);
		}
		return null;
	}

	/**
	 * 分页查询数据
	 * 
	 * @return
	 */
	public PageResponse<SysParameterValue> list(SysParameterValue value, int start, int limit) {

		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		sql.append(" from SysParameterValue t where 1=1 ");
		if (value != null) {
			if (value.getType() != null && value.getType().getParameterCode() != null && !"".equals(value.getType().getParameterCode()) && !"-1".equals(value.getType().getParameterCode())) {
				sql.append(" and t.type.parameterCode = '" + value.getType().getParameterCode()+"'");
			}
			if (value.getName() != null && !value.getName().trim().equals("")) {
				sql.append(" and t.name like '%" + value.getName() + "%'");
			}
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql1.append(sql.toString());
		sql.append(" order by t.type.parameterCode");

		List<SysParameterValue> result = this.queryByPage(sql.toString(), null, start, limit);

		StringBuffer sqlTotal = new StringBuffer();
		sqlTotal.append("select count(*) " + sql1.toString());
		int total = Integer.parseInt(this.query(sqlTotal.toString()).get(0).toString());

		PageResponse<SysParameterValue> response = new PageResponse<SysParameterValue>();
		response.setResult(result);
		response.setTotal(total);

		return response;
	}
	
	/**
	 * 查询指定系统类型数据(COMBOX使用)
	 * 
	 * @return
	 */
	public List<SysParameterValue> list(String sysParameterTypeCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue is null");
		if (null != sysParameterTypeCode) {
			sql.append(" and t.type.parameterCode = '" + sysParameterTypeCode + "'");
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	
	
	/**
	 * 查询指定系统类型数据(COMBOX使用)
	 * 
	 * @return
	 */
	public List<SysParameterValue> listBy(String sysParameterTypeCode, String startValue, String endValue) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue is null");
		if (null != sysParameterTypeCode) {
			sql.append(" and t.type.parameterCode = '" + sysParameterTypeCode + "'");
		}
		sql.append(" and t.value >= '");
		sql.append(startValue);
		sql.append("' and t.value <= '");
		sql.append(endValue);
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	
	/**
	 * 查询指定系统类型数据(COMBOX使用)
	 * 
	 * @return
	 */
	public List<SysParameterValue> listByTypeId(String sysParameterTypeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue is null");
		if (null != sysParameterTypeId) {
			sql.append(" and t.type.id = '" + sysParameterTypeId + "'");
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	
	public List<SysParameterValue> childList(String sysParameterTypeCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue is not null");
		if (null != sysParameterTypeCode) {
			sql.append(" and t.type.parameterCode = '" + sysParameterTypeCode + "'");
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}

	@Override
	public List<SysParameterValue> getSysParametersByParameterId(
			String parameterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue.id = "+parameterId + " and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	
	
	@Override
	public List<SysParameterValue> getSysParametersByParentName(
			String parameterName) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue.name = '"+parameterName+"' and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	@Override
	public List<SysParameterValue> getSysParametersByParameterName(
			String parameterName) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue.value = '"+parameterName+"' and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}

	@Override
	public SysParameterValue getSysParameterByValue(String value) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.value = '"+value+"' and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return (SysParameterValue)this.query(sql.toString()).get(0);
	}

	@Override
	public List<SysParameterValue> listin(String sysParameterTypeCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterValue t where t.parentSysParameterValue is null");
		if (null != sysParameterTypeCode) {
			sql.append(" and t.type.parameterCode in(" + sysParameterTypeCode + ")");
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		sql.append(" order by t.sortNum, t.id");
		return this.query(sql.toString());
	}
	
	
}
