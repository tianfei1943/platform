package com.smf.platform.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.framework.SystemConfig;
import com.smf.platform.log.domain.SysLog;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.service.api.SysLogService;
import com.smf.platform.util.PlatFormDateUtil;


@SuppressWarnings("unchecked")
@Component("sysLogService")
public class SysLogServiceImpl extends SmfBaseServiceImpl<SysLog> implements SysLogService{

	public PageResponse<SysLog> list(String remoteAddress, Date startTime, Date endTime , int start, int limit) {
		StringBuffer sqlTotal = new StringBuffer();
		sqlTotal.append("select count(*) ");
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbf1 = new StringBuffer();
		sbf.append("from  SysLog s where  1=1");
		if (!"".equals(remoteAddress)) {
			sbf.append(" and s.remoteAddress='" + remoteAddress + "'");
		}
		if (startTime != null) {
			sbf.append(" and convert(varchar(12),s.logTime, 112) >='");
			sbf.append(PlatFormDateUtil.formatDate(startTime, "yyyyMMdd"));
			sbf.append("'");
		}
		if (endTime != null) {
			sbf.append(" and convert(varchar(12),s.logTime, 112) <='");
			sbf.append(PlatFormDateUtil.formatDate(endTime, "yyyyMMdd"));
			sbf.append("'");
		}
		sbf.append(" and s.systemId='" + SystemConfig.getSystemId() + "'");
		sbf1.append(sbf.toString()).append(" order by s.logTime desc");
		sqlTotal.append(sbf.toString());
		
		int total = Integer.parseInt(this.query(sqlTotal.toString()).get(0).toString());
		List<SysLog> result = this.queryByPage(sbf1.toString(), null, start, limit);

		PageResponse<SysLog> response = new PageResponse<SysLog>();
		response.setTotal(total);
		response.setResult(result);
		return response;
	}
	
	
}
