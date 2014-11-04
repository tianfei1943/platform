package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysParameterType;

public interface SysParameterTypeService extends SmfBaseService<SysParameterType> {
	
	/**
	 * 查询系统参数类型数据
	 * 
	 * @return
	 */
	public List<SysParameterType> listSysBaseParameterType();

	/**
	 * 分页查询系统参数类型数据
	 * 
	 * @return
	 */
	public PageResponse<SysParameterType> listSysBaseParameterType(SysParameterType type, int start, int limit);

	public void save(SysParameterType sysParameterType);
	
	/**
	 * 修改更新系统参数类型数据
	 * 
	 * @return
	 */
	public void saveOrUpdate(SysParameterType type);

	/**
	 * 删除系统参数类型数据
	 * 
	 * @return
	 */
	public void removeByIDList(List<Long> idlist);

	/**
	 * 
	 * @param parameterCode
	 * @return
	 */
	public SysParameterType getSysParameterType(String parameterCode);
	
}
