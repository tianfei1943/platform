package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.service.api.SmfCommonService;
import com.smf.platform.system.domain.SysParameterValue;

@Namespace("/platform/commonaction")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SmfDataDictionaryAction extends ActionSupport {

	@Autowired
	@Qualifier("smfCommonService")
	private SmfCommonService smfCommonService = null;

	// listParameterValues，选择的参数类型
	private String parameterType;
	
	private List<DataDirectory> values;

	/**
	 * 取所有参数类型,不包括所有Action
	 * 
	 * @return
	 */
	@Action(value = "listDataDirectorys", results = { @Result(name = SUCCESS, type = "json") })
	@SkipValidation
	public String listDataDirectorys() throws Exception {
		List<SysParameterValue> paraValues = smfCommonService.getSysParameters(this.getParameterType());
		values = new ArrayList<DataDirectory>();
		for (SysParameterValue value : paraValues) {
			DataDirectory dd = new DataDirectory(value.getName(), value.getValue());
			values.add(dd);
		}
		return SUCCESS;
	}
	
	@Action(value = "listDataDirectorysByAjax")
	@SkipValidation
	public void listDataDirectorysByAjax() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		List<SysParameterValue> paraValues = smfCommonService.getSysParameters(this.getParameterType());
		values = new ArrayList<DataDirectory>();
		for (SysParameterValue value : paraValues) {
			DataDirectory dd = new DataDirectory(value.getName(), value.getValue());
			values.add(dd);
		}
		JSONArray jsObj = JSONArray.fromObject(values);
		response.getWriter().write(jsObj.toString());
	}
	
	@JSON(serialize=false) 
	public String getParameterType() {
		return parameterType;
	}


	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	
	public List<DataDirectory> getValues() {
		return values;
	}

	public void setValues(List<DataDirectory> values) {
		this.values = values;
	}

	public class DataDirectory {
	
		private String name;
		private String value;
		
		public DataDirectory(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}		
		
	}
}
