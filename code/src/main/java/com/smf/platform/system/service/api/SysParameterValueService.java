package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.SysParameterValue;

public interface SysParameterValueService extends SmfBaseService<SysParameterValue> {

	/**
	 * 分页查询系统参数数据
	 * 
	 * @return
	 */
	public PageResponse<SysParameterValue> list(SysParameterValue value, int start, int limit);

	public void save(SysParameterValue value);
	
	/**
	 * 修改更新系统参数数据
	 * 
	 * @return
	 */
	public void saveOrUpdate(SysParameterValue value);

	/**
	 * 删除系统参数数据
	 * 
	 * @return
	 */
	public void removeByIDList(List<Long> idlist);
	
	/**
	 * 根据参数名称获取参数
	 * 
	 * @param parameterName
	 */
	public SysParameterValue getByName(String parameterName);
	
	/**
	 * 获取某参数类型下所有父节点为空的参数值
	 * 
	 * @param sysParameterTypeCode
	 * @return
	 */
	public List<SysParameterValue> list(String sysParameterTypeCode);
	
	/**
	 * 
	 * @param sysParameterTypeCode
	 * @return
	 */
	public List<SysParameterValue> listin(String sysParameterTypeCode);
	
/**
 * 
 * @param sysParameterTypeCode
 * @param startValue
 * @param endValue
 * @return
 */
	public List<SysParameterValue> listBy(String sysParameterTypeCode, String startValue, String endValue);
	
	/**
	 * 获取某参数类型Id下所有父节点为空的参数值
	 * 
	 * @param sysParameterTypeId
	 * @return
	 */
	public List<SysParameterValue> listByTypeId(String sysParameterTypeId);
	
	public List<SysParameterValue> childList(String sysParameterTypeCode);
	
	public List<SysParameterValue> getSysParametersByParameterId(String parameterId);
	
	public List<SysParameterValue> getSysParametersByParentName(
			String parameterName);
	
	public List<SysParameterValue> getSysParametersByParameterName(String parameterName);
	
	public SysParameterValue getSysParameterByValue(String value);
	
}
