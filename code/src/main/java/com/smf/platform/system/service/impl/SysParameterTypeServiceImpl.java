package com.smf.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.SysParameterType;
import com.smf.platform.system.service.api.SysParameterTypeService;

@SuppressWarnings("unchecked")
@Component("sysParameterTypeService")
public class SysParameterTypeServiceImpl extends SmfBaseServiceImpl<SysParameterType> implements SysParameterTypeService {

	/**
	 * 查询系统参数类型数据
	 * 
	 * @return
	 */
	public List<SysParameterType> listSysBaseParameterType() {
		StringBuffer sql = new StringBuffer();
		sql.append(" from SysParameterType s where 1=1 and s.systemId='" + SystemConfig.getSystemId() + "'");
		return this.query(sql.toString());
	}

	/**
	 * 分页查询系统参数类型数据
	 * 
	 * @return
	 */
	public PageResponse<SysParameterType> listSysBaseParameterType(SysParameterType type, int start, int limit) {

		StringBuffer sql = new StringBuffer();
		sql.append(" from SysParameterType t where 1=1");
		if (type != null) {
			if (type.getParameterType() != null && !"".equals(type.getParameterType())
					&& !"-1".equals(type.getParameterType())) {
				sql.append(" and t.parameterType like '%" + type.getParameterType() + "%'");
			}
		}
		sql.append(" and t.systemId='" + SystemConfig.getSystemId() + "'");
		// 查询数据
		List<SysParameterType> result = this.queryByPage(sql.toString(), null, start, limit);
		StringBuffer sqlTotal = new StringBuffer();
		sqlTotal.append("select count(*) " + sql.toString());
		// 总条数
		int total = Integer.parseInt(this.query(sqlTotal.toString()).get(0).toString());
		PageResponse<SysParameterType> response = new PageResponse<SysParameterType>();
		response.setTotal(total);
		response.setResult(result);
		return response;
	}

	/**
	 * 保存系统参数类型数据
	 * 
	 * @return
	 */
	public void save(SysParameterType type) {
		type.setSystemId(SystemConfig.getSystemId());
		this.dao.save(type);
	}
	
	/**
	 * 修改更新系统参数类型数据
	 * 
	 * @return
	 */
	public void saveOrUpdate(SysParameterType type) {

		if (dao.get(SysParameterType.class, type.getId()) == null) {
			type.setSystemId(SystemConfig.getSystemId());
			this.dao.save(type);
		} else {
			this.dao.update(type);
		}
	}

	/**
	 * 删除系统参数类型数据
	 * 
	 * @return
	 */
	public void removeByIDList(List<Long> idlist) {
		for (Long id : idlist) {
			this.removeById(id);
		}
	}

	public SysParameterType getSysParameterType(String parameterCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SysParameterType t where t.parameterCode = '");
		sql.append(parameterCode);
		sql.append("' and t.systemId='" + SystemConfig.getSystemId() + "'");
		SysParameterType parmetertype = new SysParameterType();
		List<SysParameterType> parmetertypes = this.query(sql.toString());
		if (null != parmetertypes && parmetertypes.size() != 0) {
			parmetertype = parmetertypes.get(0);
		}
		return parmetertype;
	}
}
