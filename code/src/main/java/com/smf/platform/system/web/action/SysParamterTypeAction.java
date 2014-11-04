package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

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
import com.smf.platform.system.service.api.SysParameterTypeService;

/**
 * @author
 * @since
 * 
 */
//定义URL映射对应/platform/sysparatype.action
@Namespace("/platform/sysparatype")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysParamterTypeAction extends CrudActionSupport {

	@Autowired
	@Qualifier("sysParameterTypeService")
	private SysParameterTypeService sysParameterTypeService = null;

	// Hidden传值变量
	private String id;
	// 数据类型名称
	private String parameterType;

	// 参数类型
	private SysParameterType type;
	// 所有参数类型
	private List<SysParameterType> allSysParameterTypes;
	// 查询结果
	private JSONObject jsono = new JSONObject(); 
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;

	/**
	 * 获取参数类型Action，以JSON的格式返回用户记录，分页
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, type = "json",params={"root","jsono"} ) })
	@SkipValidation
	@Override	
	public String list() throws Exception {
		SysParameterType tempType = new SysParameterType();
		tempType.setParameterType(this.getParameterType());
		PageResponse<SysParameterType> pageResponse = sysParameterTypeService.listSysBaseParameterType(tempType, page-1, rows);
		jsono.put("total", pageResponse.getTotal());
		jsono.put("rows", pageResponse.getResult());
		return "success";
	}
	
	/**
	 * 取所有参数类型,包括所有Action
	 * 
	 * @return
	 */
	@Action(value = "listAll", results = { @Result(name = "success", type = "json",params={"root","allSysParameterTypes"}) })
	@SkipValidation
	public String listAll() throws Exception {
		allSysParameterTypes = sysParameterTypeService.listSysBaseParameterType();
		SysParameterType temp = new SysParameterType();
		temp.setParameterCode("-1");
		temp.setParameterType("所有");
		allSysParameterTypes.add(0, temp);
		return "success";
	}
	
	/**
	 * 跳转到添加/修改页面
	 * 
	 **/
	@Action(value = "input", results = { @Result(name = ActionSupport.INPUT, location = "sysparatype-cu.jsp")})
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
			@Result(name = CrudActionSupport.RELOAD, location = "sysparatype.jsp", type = "redirect"), 
			@Result(name = ActionSupport.INPUT, location = "sysparatype-cu.jsp")})					
	public String save() throws Exception {
		sysParameterTypeService.saveOrUpdate(type);
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
		String[] strIds = strId.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for (String ParameterTypeIds : strIds) {
			idlist.add(Long.parseLong(ParameterTypeIds));
		}
		sysParameterTypeService.removeByIDList(idlist);
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
			type = sysParameterTypeService.get(Long.parseLong(this.getId().trim()));
		} else {
			type = new SysParameterType();
		}
	}
	
	public void validateSave() {
		if (type.getParameterCode() == null || "".equals(type.getParameterCode())) {
			this.addFieldError("type.parameterCode", "请输入类型编号");
		}
		if (type.getId() == null) {
			if (type.getParameterCode() != null && !"".equals(type.getParameterCode())) {
				if (sysParameterTypeService.getSysParameterType(type.getParameterCode()).getParameterCode() != null) {
					this.addFieldError("type.parameterCode", "类型编号已存在");
				}
			}
		}
		if (type.getParameterType() == null || "".equals(type.getParameterType())) {
			this.addFieldError("type.parameterType", "请选择参数类型");
		}
	}

	public List<SysParameterType> getAllSysParameterTypes() {
		return allSysParameterTypes;
	}

	public void setAllSysParameterTypes(List<SysParameterType> allSysParameterTypes) {
		this.allSysParameterTypes = allSysParameterTypes;
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

	public SysParameterType getType() {
		if (null == this.type) {
			this.type = new SysParameterType();
		}
		return type;
	}

	public void setType(SysParameterType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public JSONObject getJsono() {
		return jsono;
	}

	public void setJsono(JSONObject jsono) {
		this.jsono = jsono;
	}

}
