package com.smf.platform.service.api;

import java.util.List;

import com.smf.platform.dao.api.GenericDao;
import com.smf.platform.system.domain.SysParameterValue;

/**
 * 该接口提供开发平台对外提供的数据查询接口
 * @author wubin
 *
 */
public interface SmfCommonService extends GenericDao{

	/**
	 * 根据系统参数名称获取当前设置的参数值
	 * @param parameterName
	 * @return
	 */
	public String getSysParameterValue(String parameterName);
	
	/**
	 * 根据参数类型获取类型下的所有参数值
	 * @param parameterType
	 * @return
	 */
	public List<SysParameterValue> getSysParameters(String parameterType);
	
	public List<SysParameterValue> getChildSysParameters(String parameterType);
	
	public List<SysParameterValue> getSysParametersByParameterId(String parameterId);
	public List<SysParameterValue> getSysParametersByParameterName(String parameterName);
	public List<SysParameterValue> getSysParametersByParentName(
			String parameterName);
	public SysParameterValue getSysParameterByValue(String value);
	
}
