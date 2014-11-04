package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.service.api.SysResourceService;

/**
 * 资源管理(菜单) 
 * @author robbie
 * since 2011-08-11 
 */
@Namespace("/platform/sysresource/menu")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysResourceAction extends CrudActionSupport{
	
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService = null;
	
	// ------ 页面属性 Begin ------//
	// 菜单资源
	private SysResource sysResource;
	// 资源名称
	private String resourceName;
	// 资源类型
	private String resourceType;
	
	// 菜单资源查询结果，以JSON格式返回，用作sysresource-menu.jsp表格查询的结果
	private String menusJson;
	// 菜单资源查询结果，以JSON格式返回，用作ExtJS tree的数据源，保留了children节点
	private String menusJsonOfTree;
	
	// 添加或修改时选中的父节点Id
	private String parentId;
	
	// SysResourceId
	private String id;
	
	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	// ------ 页面属性 End ------//
	
	/**
	 *  获取资源记录，以JSON格式返回，用作sysresource-menu.jsp表格查询的结果
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysresource-menu-json.jsp") })
	@SkipValidation
	public String list() throws Exception{
		PageResponse<SysResource> pageresponse = sysResourceService.listBy(this.getResourceName(),SmfConst.PRIV_RESOURCE_TYPE_MENU, page-1, rows);
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
		menusJson = "{\"total\":" + pageresponse.getTotal() + ",\"rows\":" + jsObj.toString() + "}";
		return SUCCESS;
	}
	
	/**
	 *  获取资源记录，以JSON格式返回，用作ExtJS tree的数据源，保留了children节点
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "listMenusTree", results = { @Result(name = SUCCESS, location = "sysresource-menutree-json.jsp") })
	@SkipValidation
	public String listMenusTree() throws Exception{
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
		menusJsonOfTree = jsonObject.toString();
		return SUCCESS;
	}
	
	private void converToNode(List<Node> nodes, List<SysResource> resources) {

		List<String> parentIdsls = new ArrayList<String>();
		if(null != parentId && !"".equals(parentId)){
			String[] pa = parentId.split(",");
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
		if(parentIdsls.size() == 0){
			node.setChecked(true);
		}
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
				this.getChildren(nodec,sysResourcec,parentIdsls);
				pnodes.add(nodec);
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
				nodes.add(childrennode);
				this.getChildren(childrennode,groupc,parentIdsls);
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
	@Action(value = "input", results = { @Result(name = INPUT, location = "sysresource-menu-cu.jsp") })
	@SkipValidation
	public String input() throws Exception{
		return INPUT;
	}
	
	/**
	 * 保存资源（新建或更新）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "save", results = {
			@Result(name = RELOAD, location = "sysresource-menu.jsp", type = "redirect"),
			@Result(name = INPUT, location = "sysresource-menu-cu.jsp") })
	public String save() throws Exception {
		sysResource.setResourceType(SmfConst.PRIV_RESOURCE_TYPE_MENU);
		// 当选中的父节点为-1时，表示父节点为空
		if (this.getParentId().equals("-1")) {
			sysResource.setParent(null);
		} else {
			SysResource parent = sysResourceService.get(new Long(this.getParentId()));
			sysResource.setParent(parent);
		}
		if (sysResource.getId() != null) {
			SysResource temp = sysResourceService.get(sysResource.getId());
			sysResource.setChildren(temp.getChildren());
			sysResource.setPrivileges(temp.getPrivileges());
			sysResourceService.update(sysResource);
		} else {
			sysResource.setDefaultOpen(false);
			sysResourceService.save(sysResource);
		}
		return RELOAD;
	}
	
	/**
	 *  删除资源，删除操作 可以删除多条记录，多个ID之间使用逗号分隔符
	 * 
	 * @return
	 * @throws eException
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
			
			// 将组的父节点形成字符串赋予parentIds
			SysResource parent = sysResource.getParent();
			this.parentId = parent.getId().toString();
		} else {
			sysResource = new SysResource();
		}
	}
	
	/**
	 * 在保存前进行验证，在save方法调用前被调用，由DefaultWorkflowInterceptor拦截器提供该功能
	 * @throws Exception
	 */
	public void validateSave() {
		if(null!=sysResource){
			if(sysResource.getResourceName()==null ||"".equals(sysResource.getResourceName())){
				this.addFieldError("sysResource.resourceName", "资源名称并不能为空");
			}
			if(sysResource.getResourceName()!=null ||!"".equals(sysResource.getResourceName())){
				if(sysResource.getResourceName().length()>100){
					this.addFieldError("sysResource.resourceName", "资源名称长度太长");
				}
				List<SysResource> list = sysResourceService.list(sysResource.getResourceName());
				//判断是添加还是修改
				if(sysResource.getId()!=null&&!"".equals(sysResource.getId())){
					SysResource resource = sysResourceService.get(sysResource.getId());
					if(resource!=null&&!"".equals(resource)){
						//判断资源名称是否重复
						if(!resource.getResourceName().equals(sysResource.getResourceName())){
							if(list.size()>0){
								this.addFieldError("sysResource.resourceName", "资源名称已经存在,请重新输入");
							}
						}
					}
				}else{
					if(list.size()>0){
						this.addFieldError("sysResource.resourceName", "资源名称已经存在,请重新输入");
					}
				}
			}
			if(sysResource.getOpenType()==null|| "".equals(sysResource.getOpenType())){
				this.addFieldError("sysResource.openType", "打开类型不能为空");
			}
			if(sysResource.getSortOrder()==null||"".equals(sysResource.getSortOrder())){
				this.addFieldError("sysResource.sortOrder", "排序不能为空");
			}
			if(sysResource.getDesciption()!=null||!"".equals(sysResource.getDesciption())){
				if(sysResource.getDesciption().length()>100){
					this.addFieldError("sysResource.desciption", "资源描述长度过长");
				}
			}
		}
	}
	
	public SysResource getSysResource() {
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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getMenusJson() {
		return menusJson;
	}

	public void setMenusJson(String menusJson) {
		this.menusJson = menusJson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenusJsonOfTree() {
		return menusJsonOfTree;
	}

	public void setMenusJsonOfTree(String menusJsonOfTree) {
		this.menusJsonOfTree = menusJsonOfTree;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
