/**
 * 
 */
package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.system.domain.SysParameterType;
import com.smf.platform.system.domain.SysParameterValue;
import com.smf.platform.system.service.api.SysParameterTypeService;
import com.smf.platform.system.service.api.SysParameterValueService;

/**
 * @author
 * @since
 * 
 */
//定义URL映射对应/platform/sysparavalue.action
@Namespace("/platform/sysparavalue")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysParameterValueAction extends CrudActionSupport {

	@Autowired
	@Qualifier("sysParameterValueService")
	private SysParameterValueService sysParameterValueService = null;
	@Autowired
	@Qualifier("sysParameterTypeService")
	private SysParameterTypeService sysParameterTypeService = null;

	// JSON值
	private String sysParameterValuesJson = null;
	// 查询条件、添加、修改所用参数
	private SysParameterValue value;
	// Hidden变量
	private String id;
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	// 查询页面，选择的数据值类型
	private String parameterCode;
	// 参数类型Id
	private String parameterTypeId;
	private int parentValueId = -1;

	// 所有参数类型
	private List<SysParameterType> allSysParameterTypes;
	
	// 所有参数类型
	private List<SysParameterValue> allSysParameterValues;	
	private String parameterType = null;
	
	/**
	 * 取所有参数类型,不包括所有Action
	 * 
	 * @return
	 */
	@Action(value = "listParaType", results = { @Result(name = SUCCESS, type = "json",params={"root","allSysParameterTypes"}) })
	@SkipValidation
	public String listSysParameterTypes() throws Exception {
		allSysParameterTypes = sysParameterTypeService.listSysBaseParameterType();
		return SUCCESS;
	}

	@Action(value = "listParentPara", results = { @Result(name = SUCCESS, type = "json",params={"root","allSysParameterValues"}) })
	@SkipValidation
	public String listParentPara() throws Exception {		
		allSysParameterValues = sysParameterValueService.listByTypeId(this.getParameterTypeId());
		SysParameterValue parameterValue = new SysParameterValue();
		parameterValue.setId(new Long(-1));
		parameterValue.setName("不选择");
		allSysParameterValues.add(0, parameterValue);
		return SUCCESS;
	}
	
	/**
	 * 初期查询数据Action
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysparavalue-json.jsp") })
	@SkipValidation
	public String list() throws Exception {
		SysParameterValue tempValue = new SysParameterValue();
		tempValue.getType().setParameterCode(this.getParameterCode());
		
		PageResponse<SysParameterValue> pageResponse = sysParameterValueService.list(tempValue, page-1, rows);

		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[1];
		excludes[0] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(pageResponse.getResult(), jsn);
		sysParameterValuesJson = "{\"total\":" + pageResponse.getTotal() + ",\"rows\":" + jsonObject.toString() + "}";

		return SUCCESS;
	}

	/**
	 * 跳转到添加/修改页面
	 * 
	 **/
	@Action(value = "input", results = { @Result(name = ActionSupport.INPUT, location = "sysparavalue-cu.jsp")})
	@SkipValidation
	@Override
	public String input() throws Exception {
		return INPUT;
	}	
	
	/**
	 * 保存（新建或更新）
	 * 
	 * @return
	 */
	@Action(value = "save", results = { 
			@Result(name = CrudActionSupport.RELOAD, location = "sysparavalue.jsp", type = "redirect"), 
			@Result(name = ActionSupport.INPUT, location = "sysparavalue-cu.jsp")})					
	public String save() throws Exception {
		if (this.getId() != null && !this.getId().trim().equals("")) {
			SysParameterType sysParameterType = sysParameterTypeService.get(value.getType().getId());
			try {
				// 如果界面选择的父节点参数值得Id为-1，则表示不设置父节点，所以设为空				
				if (parentValueId == -1) {
					value.setParentSysParameterValue(null);
				} else {
					SysParameterValue parentValue = new SysParameterValue();
					parentValue.setId(new Long(parentValueId));
					value.setParentSysParameterValue(parentValue);
				}
				value.setType(sysParameterType);
				sysParameterValueService.update(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 如果界面选择的父节点参数值得Id为-1，则表示不设置父节点，所以设为空
			if (parentValueId == -1) {
				value.setParentSysParameterValue(null);
			} else {
				SysParameterValue parentValue = new SysParameterValue();
				parentValue.setId(new Long(parentValueId));
				value.setParentSysParameterValue(parentValue);
			}
			sysParameterValueService.save(value);
		}
		return RELOAD;
	}	

	/**
	 * 删除操作 可以删除多条记录，多个ID之间使用逗号分隔符
	 * 
	 * @return
	 */
	@Action(value = "delete", results = { @Result(name = CrudActionSupport.JSON, type = "json") })
	@SkipValidation
	@Override	
	public String delete() throws Exception {
		String strId = this.getId();
		String[] strIdValue = strId.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for (String ParameterIds : strIdValue) {
			idlist.add(Long.parseLong(ParameterIds));
		}
		sysParameterValueService.removeByIDList(idlist);
		return CrudActionSupport.JSON;
	}
	
	/**
	 * 根据页面传递的id参数加载相应的对象
	 * 在CrudActionSupport的prepareList、prepareInput、prepareSave中调用
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (null != this.getId() && !this.getId().equals("")) {
			value = sysParameterValueService.get(Long.parseLong(this.getId().trim()));
		} else {
			value = new SysParameterValue();
		}
	}

	public void validateSave() {
		if (value.getName() == null || "".equals(value.getName())) {
			this.addFieldError("value.name", "请输入数据值名称");
		}
		if (value.getId() == null) {
			if (value.getName() != null && !"".equals(value.getName())) {
				if (sysParameterValueService.getByName(value.getName()) != null) {
					this.addFieldError("value.name", "数据值名称已存在");
				}
			}
		}
		if (value.getType().getId() == null) {
			this.addFieldError("value.type.id", "请选择数据类型");
		}
		if (value.getValue() == null || "".equals(value.getValue())) {
			this.addFieldError("value.value", "请输入数据值");
		}

		if (value.getDescription() != null) {
			if (value.getDescription().length() > 100) {
				this.addFieldError("value.description", "备注的长度不能超过100");
			}
		}
	}

	public String getSysParameterValuesJson() {
		return sysParameterValuesJson;
	}

	public void setSysParameterValuesJson(String sysParameterValuesJson) {
		this.sysParameterValuesJson = sysParameterValuesJson;
	}

	public SysParameterValue getValue() {
		if (null == this.value) {
			this.value = new SysParameterValue();
		}
		return value;
	}

	public void setValue(SysParameterValue value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<SysParameterType> getAllSysParameterTypes() {
		return allSysParameterTypes;
	}

	public void setAllSysParameterTypes(List<SysParameterType> allSysParameterTypes) {
		this.allSysParameterTypes = allSysParameterTypes;
	}


	public String getParameterType() {
		return parameterType;
	}


	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	
	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public List<SysParameterValue> getAllSysParameterValues() {
		return allSysParameterValues;
	}

	public void setAllSysParameterValues(
			List<SysParameterValue> allSysParameterValues) {
		this.allSysParameterValues = allSysParameterValues;
	}

	public String getParameterTypeId() {
		return parameterTypeId;
	}

	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}

	public int getParentValueId() {
		return parentValueId;
	}

	public void setParentValueId(int parentValueId) {
		this.parentValueId = parentValueId;
	}
	
}