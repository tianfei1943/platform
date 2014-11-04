package com.smf.platform.system.web.action;

import java.text.ParseException;
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

import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.service.api.SysResourceService;

/**
 * 
 * 资源管理
 * @author robbie
 * 
 */
@Namespace("/platform/sysresource/otherresource")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysResourceOfOthersAction extends CrudActionSupport{
	
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService = null;

	
	// ------ 页面属性 Begin ------//
	private SysResource sysResource;
	// 资源名称
	private String resourceName;
	// 资源类型
	private String resourceType;
	
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	
	private String resourceJson;
	
	// SysResourceId
	private String id;
	
	// ------ 页面属性 end ------//
	
	/**
	 *  获取资源记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysresource-others-json.jsp") })
	@SkipValidation
	public String list() throws Exception{
		SysResource sr = new SysResource();
		sr.setResourceName(this.getResourceName());
		PageResponse<SysResource> pageresponse = sysResourceService.list(sr,this.getResourceType(), page-1, rows);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[7];
		excludes[0] = "sortOrder";
		excludes[1] = "parent";
		excludes[2] = "errorMessage";
		excludes[3] = "openType";
		excludes[4] = "privileges";
		excludes[5] = "children";
		excludes[6] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsObj = JSONArray.fromObject(pageresponse.getResult(), jsn);			
		resourceJson = "{\"total\":" + pageresponse.getTotal() + ",\"rows\":"
				+ jsObj.toString() + "}";
		return SUCCESS;
	}
	
	/**
	 *  跳转到添加修改页面
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "input", results = { @Result(name = INPUT, location = "sysresource-others-cu.jsp") })
	@SkipValidation
	public String input() throws Exception{
		return INPUT;
	}
	
	/**
	 * 保存action
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "save", results = {
			@Result(name = RELOAD, location = "sysresource-others.jsp", type = "redirect"),
			@Result(name = INPUT, location = "sysresource-others-cu.jsp") })			
	public String save() throws Exception {
		if (sysResource.getId() != null) {
			//设置资源权限
			SysResource temp = sysResourceService.get(sysResource.getId());
			sysResource.setPrivileges(temp.getPrivileges());
			sysResourceService.update(sysResource);
		} else {
			sysResourceService.save(sysResource);
		}
		return RELOAD;
	}

	/**
	 *  删除资源，删除操作 可以删除多条记录，多个ID之间使用逗号分隔符
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(name = CrudActionSupport.JSON, type = "json") })
	@SkipValidation
	public String delete() throws Exception{
		if(null != id){
			String strId = this.getId().trim();
			String[] resourceIds = strId.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String id : resourceIds) {
				idList.add(Long.parseLong(id));
			}
			sysResourceService.remove(idList);
		}
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
			sysResource = sysResourceService.get(Long.parseLong(this.getId()));
		} else {
			sysResource = new SysResource();
		}
	}	
	
	/**
	 * 在保存前进行验证，在save方法调用前被调用，由DefaultWorkflowInterceptor拦截器提供该功能
	 * @throws Exception
	 */	
	public void validateSave() throws Exception{
		if(sysResource!=null){
			if(sysResource.getResourceType()==null || "".equals(sysResource.getResourceType())){
				this.addFieldError("sysResource.resourceType", "资源类型不能为空");
			}
			if(sysResource.getResourceName()==null||"".equals(sysResource.getResourceName())){
				this.addFieldError("sysResource.resourceName", "资源名称不能为空");
			}
			if(!"".equals(sysResource.getResourceName())){
				if(sysResource.getResourceName().length()>100){
					this.addFieldError("sysResource.resourceName", "资源名称长度太长");
				}
//				List<SysResource> list = sysResourceService.list(sysResource.getResourceName());
//				//判断是添加还是修改
//				if(sysResource.getId()!=null&&!"".equals(sysResource.getId())){
//					SysResource resource = sysResourceService.get(sysResource.getId());
//					if(resource!=null&&!"".equals(resource)){
//						//判断用户名是否改变
//						if(!resource.getResourceName().equals(sysResource.getResourceName())){
//							if(list.size()>0){
//								this.addFieldError("sysResource.resourceName", "资源名称已经存在,请从新输入");
//							}
//						}
//					}
//				}else{
//					if(list.size()>0){
//						this.addFieldError("sysResource.resourceName", "资源名称已经存在,请从新输入");
//					}
//				}
			}
			if(!"".equals(sysResource.getDesciption())){
				if(sysResource.getDesciption().length()>100){
					this.addFieldError("sysResource.desciption", "资源描述长度太长");
				}
			}
		}
	}
	
	public SysResource getSysResource() {
		if(sysResource==null){
			sysResource= new SysResource();
		}
		return sysResource;
	}
	
	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

	public String getResourceJson() {
		return resourceJson;
	}
	
	public void setResourceJson(String resourceJson) {
		this.resourceJson = resourceJson;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	
	
	
	
	
	
	
}
