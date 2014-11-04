package com.smf.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.smf.platform.dao.impl.GenericDaoImpl;
import com.smf.platform.service.api.SmfCommonService;
import com.smf.platform.system.domain.SysParameterValue;
import com.smf.platform.system.service.api.SysParameterValueService;

@Component("smfCommonService")
public class SmfCommonServiceImpl extends GenericDaoImpl implements SmfCommonService {
	
	@Autowired
	@Qualifier("sysParameterValueService")
	private SysParameterValueService sysParameterValueService;

	/**
	 * 返回系统参数名称对应的参数值
	 * TODO need to complete
	 * @param parameterName
	 *            系统参数名称
	 */
	public String getSysParameterValue(String parameterName) {
//		// 根据系统参数名称取出所有相关的参数值对象
//		List<SysBaseParameterValue> list = this.query("from SysBaseParameterValue s where s.baseParameter.name='"
//				+ parameterName + "'");
//		if (list != null && list.size() > 0) {
//			// spv1是这个参数名称对应的系统默认值
//			SysBaseParameterValue spv = null;
//
//			for (int i = 0; i < list.size(); i++) {
//				SysBaseParameterValue value = (SysBaseParameterValue) list.get(i);
//				if (value.getIsDefaultValue().equals("1")) {
//					spv = value;
//					break;
//				}
//			}
//			// 存在系统默认值的话返回系统默认值
//			if (spv != null) {
//				return spv.getValue();
//			} else {
//				return list.get(0).getValue();
//			}
//		}
		return null;
	}
	
	public List<SysParameterValue> getSysParameters(String parameterType) {
		return sysParameterValueService.list(parameterType);
	}
	
	public List<SysParameterValue> getChildSysParameters(String parameterType) {
		return sysParameterValueService.childList(parameterType);
	}

	@Override
	public List<SysParameterValue> getSysParametersByParameterId(
			String parameterId) {
		return sysParameterValueService.getSysParametersByParameterId(parameterId);
	}
	@Override
	public List<SysParameterValue> getSysParametersByParentName(
			String parameterName) {
		return sysParameterValueService.getSysParametersByParentName(parameterName);
	}
	@Override
	public List<SysParameterValue> getSysParametersByParameterName(
			String parameterName) {
		return sysParameterValueService.getSysParametersByParameterName(parameterName);
	}

	@Override
	public SysParameterValue getSysParameterByValue(String value) {
		return sysParameterValueService.getSysParameterByValue(value);
	}

	
}
