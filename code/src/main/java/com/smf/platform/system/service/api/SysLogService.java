package com.smf.platform.system.service.api;

import java.util.Date;

import com.smf.platform.common.page.PageResponse;
import com.smf.platform.log.domain.SysLog;
import com.smf.platform.service.api.SmfBaseService;

public interface SysLogService extends SmfBaseService<SysLog>{
	
	/**
	 * 查询日志
	 * @param log
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<SysLog> list(String remoteAddress, Date startTime, Date endTime, int start, int limit);
	
}
