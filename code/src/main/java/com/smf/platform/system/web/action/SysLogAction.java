package com.smf.platform.system.web.action;

import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.log.domain.SysLog;
import com.smf.platform.system.service.api.SysLogService;

@ParentPackage("smfjson-default")
public class SysLogAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("sysLogService")
	private SysLogService sysLogService = null;
	//Json数据
	private String logJson;
	//查询条件
	private Date startTime;
	private Date endTime;
	private String remoteAddress;
	
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;

	/**
	 *  获取日志记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "/platform/syslog/listSysLog", results = { @Result(name = "success", location = "json_sysLog.jsp") })
	@SkipValidation
	public String listSysLog() throws ParseException{
		PageResponse<SysLog> responce = sysLogService.list(this.getRemoteAddress(), this.getStartTime(), this.getEndTime(), page-1, rows);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[1];
		excludes[0] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsObj = JSONArray.fromObject(responce.getResult(), jsn);			
		logJson = "{\"total\":" + responce.getTotal() + ",\"rows\":"+ jsObj.toString() + "}";
		return "success";
	}
	
	public String getLogJson() {
		return logJson;
	}
	public void setLogJson(String logJson) {
		this.logJson = logJson;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
