package com.smf.platform.system.web.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.smf.platform.common.SmfConst;
import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.system.domain.Node;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.service.api.SysPrivilegeService;
import com.smf.platform.system.service.api.SysResourceService;

/**
 * 权限管理
 * 
 * @author robbie
 * 
 */
@Namespace("/platform/sysprivilege/menuprivilege")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysMenuPrivilegeAction extends CrudActionSupport{
	
	@Autowired
	@Qualifier("sysPrivilegeService")
	private SysPrivilegeService sysPrivilegeService = null;
	
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService = null;
	
	// ------ 页面属性 Begin ------//
	//json数据
	private String privilegesJson;
	private String resourcesJson;
	
	//权限对象
	private SysPrivilege privilege;
	
	// 查询条件中的权限名称
	private String privilegeName;
	
	//选中权限Id
	private String id;
	
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	
	//资源对象
	private SysResource sysResource;
	
	//选中资源Id
	private String sysResourceId;
	// ------ 页面属性 End ------//
	
	/**
	 *  获取权限记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "menuprivilege-json.jsp") })
	@SkipValidation
	public String list() throws Exception{
		SysPrivilege tempPrivilege = new SysPrivilege();
		tempPrivilege.setPrivilegeName(this.getPrivilegeName());
		PageResponse<SysPrivilege> pageresponse = sysPrivilegeService.list(tempPrivilege, SmfConst.PRIV_RESOURCE_TYPE_MENU, page-1, rows);
		JsonConfig jsn = new JsonConfig();
		
		String[] excludes = new String[5];
		excludes[0] = "privilegeCode";
		excludes[1] = "valueConstraint";
		excludes[2] = "roles";
		excludes[3] = "resources";
		excludes[4] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		
		JSONArray jsObj = JSONArray.fromObject(pageresponse.getResult(), jsn);			
		privilegesJson = "{\"total\":" + pageresponse.getTotal() + ",\"rows\":" + jsObj.toString() + "}";
		return SUCCESS;
	}
	
	/**
	 *  获取资源记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listmenuresource", results = { @Result(name = SUCCESS, location = "menuprivilege-resource-json.jsp") })
	@SkipValidation
	public String listBoPropSysResource() throws Exception{
		List<SysResource> resources = sysResourceService.listSysresource(SmfConst.PRIV_RESOURCE_TYPE_MENU);
		List<Node> nodes = new ArrayList<Node>();
		converToNode(nodes,resources);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[6];
		excludes[0] = "sortOrder";
		excludes[1] = "parent";
		excludes[2] = "errorMessage";
		excludes[3] = "openType";
		excludes[4] = "privileges";
		excludes[5] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(nodes, jsn);
		resourcesJson = jsonObject.toString();
		return SUCCESS;
	}
	
	private void converToNode(List<Node> nodes, List<SysResource> resources) {

		List<String> parentIdsls = new ArrayList<String>();
		if(null != sysResourceId && !"".equals(sysResourceId)){
			String[] pa = sysResourceId.split(",");
			for(String p : pa){
				parentIdsls.add(p);
			}
		}
		Set<Node>  pnodes = new HashSet<Node>();
		Node node = new Node();
		node.setId("-1");
		node.setText("菜单");
		node.setState("colse");
		node.setIconCls("menu-tree-node-icon-dir");
		for(SysResource sysResourcec:resources){
			if(sysResourcec instanceof SysResource){ 
				Node nodec = new Node();
				nodec.setId(""+sysResourcec.getId());
				nodec.setText(sysResourcec.getResourceName());
				nodec.setState("colse");
				if(null !=sysResourcec.getChildren() && sysResourcec.getChildren().size() >0){
					nodec.setIconCls("menu-tree-node-icon-dir");
				} else {
					nodec.setIconCls("menu-tree-node-icon-leaf");
				}
				
				if(parentIdsls.contains(""+sysResourcec.getId())){
					nodec.setChecked(true);
				}
				if (sysResourcec.getStatus() == SmfConst.STATUS_SHOW) {
					this.getChildren(nodec,sysResourcec,parentIdsls);
					pnodes.add(nodec);
				}
			}

		}
		node.setChildren(pnodes);
		nodes.add(node);
	
		
	}

	private void getChildren(Node node, SysResource sysResourcec, List<String> parentIdsls) {
		Set<Node>  nodes = new HashSet<Node>();
		if(null != sysResourcec.getChildren()){
			for(SysResource groupc:sysResourcec.getChildren()){
				Node childrennode = new Node();
				childrennode.setId(""+groupc.getId());
				childrennode.setText(groupc.getResourceName());
				childrennode.setState("colse");
				if(null !=groupc.getChildren()&& groupc.getChildren().size() >0){
					childrennode.setIconCls("menu-tree-node-icon-dir");
				} else {
					childrennode.setIconCls("menu-tree-node-icon-leaf");
				}
				if(parentIdsls.contains(""+groupc.getId())){
					childrennode.setChecked(true);
				}
				if (groupc.getStatus() == SmfConst.STATUS_SHOW) {
					nodes.add(childrennode);
					this.getChildren(childrennode,groupc,parentIdsls);
				}				
			}
			node.setChildren(nodes);
		} 
	}	
	/**
	 *  跳转到添加修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "input", results = { @Result(name = INPUT, location = "menuprivilege-cu.jsp") })
	@SkipValidation
	public String input() throws Exception{
		return INPUT;
	}
	
	/**
	 * 保存权限（新建或更新）
	 * 
	 * @return
	 */
	@Action(value = "save", results = {
			@Result(name = RELOAD, location = "menuprivilege.jsp", type = "redirect"),
			@Result(name = INPUT, location = "menuprivilege-cu.jsp") })
	public String save(){
		List<SysResource> resourceSet = new ArrayList<SysResource>();
		StringTokenizer menus = new StringTokenizer(this.getSysResourceId(), ",");
		while(menus.hasMoreTokens()){
			SysResource sysResource = sysResourceService.get(new Long(menus.nextToken()));
			resourceSet.add(sysResource);
		}
		privilege.setResources(resourceSet);
		privilege.setPrivilegeType(SmfConst.PRIV_RESOURCE_TYPE_MENU);
		if(privilege.getId()==null){
			// 因为SpringSecurity的权限前置为PRIV_，因此对于所有的权限编码默认加上PRIV_
			privilege.setPrivilegeCode(SmfConst.PREFIX_PRIV + privilege.getPrivilegeCode());
			sysPrivilegeService.save(privilege);
		} else{
			//修改时要设定权限的角色
			SysPrivilege sysPrivilege = sysPrivilegeService.get(privilege.getId());
			if(sysPrivilege!=null){
				Set<SysRole> roleSet = sysPrivilege.getRoles();
				privilege.setRoles(roleSet);
			}
			sysPrivilegeService.update(privilege);
		}
		return RELOAD;
	}		
	
	/**
	 * 删除操作 可以删除多条记录，多个ID之间使用逗号分隔符
	 * 
	 * @return
	 */
	@Action(value = "delete", results = { @Result(name = JSON, type = "json") })
	@SkipValidation
	public String delete() throws Exception{
		if(null!=this.getId()){
			String strId = this.getId().trim();
			String[] privilegeIds = strId.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String id : privilegeIds) {
				idList.add(Long.parseLong(id));
			}
			sysPrivilegeService.remove(idList);
		}
		return JSON;
	}
	
	/**
	 * 根据页面传递的id参数加载相应的对象
	 * 在CrudActionSupport的prepareList、prepareInput、prepareSave中调用
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (null != this.getId() && !this.getId().equals("")) {
			privilege = sysPrivilegeService.get(Long.parseLong(this.getId()));
			List<SysResource> menus = privilege.getResources();
			Iterator<SysResource> menuIter = menus.iterator();
			this.sysResourceId="";
			while(menuIter.hasNext()){
				SysResource resourceTemp = menuIter.next();
				if (resourceTemp.getStatus() == SmfConst.STATUS_SHOW) {
					this.sysResourceId = this.sysResourceId + resourceTemp.getId()+ ",";
				}
			}
			if (this.sysResourceId.endsWith(",")) {
				this.sysResourceId = this.sysResourceId.substring(0, this.sysResourceId.length() - 1);
			}
		} else {
			privilege = new SysPrivilege();
		}
	}
	
	/**
	 * 在保存前进行验证，在save方法调用前被调用，由DefaultWorkflowInterceptor拦截器提供该功能
	 * @throws Exception
	 */
	public void validateSave() {
		if(privilege!=null){
			if(privilege.getPrivilegeCode()==null||"".equals(privilege.getPrivilegeCode())){
				this.addFieldError("privilege.privilegeCode", "权限编码不能为空");
			}
			if(!"".equals(privilege.getPrivilegeCode())){
				if(privilege.getPrivilegeCode().length()>100){
					this.addFieldError("privilege.privilegeCode", "权限编码长度太长");
				}
				if(privilege.getId()==null || "".equals(privilege.getId())){
					List<SysPrivilege> list = sysPrivilegeService.listByCode(privilege.getPrivilegeCode());
					if(list.size()>0){
						this.addFieldError("privilege.privilegeCode", "权限编码已经存在,请从新输入");
					}
				}
			}
			if(privilege.getPrivilegeName()== null||"".equals(privilege.getPrivilegeName())){
				this.addFieldError("privilege.privilegeName", "权限名称不能为空");
			}
			if(!"".equals(privilege.getPrivilegeName())){
				if(privilege.getPrivilegeName().length()>100){
					this.addFieldError("privilege.privilegeName", "权限名称长度太长");
				}
				List<SysPrivilege> list = sysPrivilegeService.listByName(privilege.getPrivilegeName());
				//判断是添加还是修改
				if(privilege.getId()!=null&&!"".equals(privilege.getId())){
					SysPrivilege sysPrivilege = sysPrivilegeService.get(privilege.getId());
					if(sysPrivilege!=null&&!"".equals(sysPrivilege)){
						//判断用户名是否改变
						if(!sysPrivilege.getPrivilegeName().equals(privilege.getPrivilegeName())){
							if(list.size()>0){
								this.addFieldError("privilege.privilegeName", "权限名称已经存在,请从新输入");
							}
						}
					}
				}else{
					if(list.size()>0){
						this.addFieldError("privilege.privilegeName", "权限名称已经存在,请从新输入");
					}
				}
			}
		}
	}
	
	public SysPrivilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(SysPrivilege privilege) {
		this.privilege = privilege;
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

	public SysResource getSysResource() {
		return sysResource;
	}
	
	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}
	
	public String getSysResourceId() {
		return sysResourceId;
	}
	
	public void setSysResourceId(String sysResourceId) {
		this.sysResourceId = sysResourceId;
	}
	
	public String getPrivilegesJson() {
		return privilegesJson;
	}

	public void setPrivilegesJson(String privilegesJson) {
		this.privilegesJson = privilegesJson;
	}

	public String getResourcesJson() {
		return resourcesJson;
	}

	public void setResourcesJson(String resourcesJson) {
		this.resourcesJson = resourcesJson;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

}
