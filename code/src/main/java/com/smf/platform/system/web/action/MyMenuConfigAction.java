package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.smf.platform.common.SmfConst;
import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.security.SmfSecurityContextHolder;
import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.MyMenuConfig;
import com.smf.platform.system.domain.Node;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.MyMenuConfigService;
import com.smf.platform.system.service.api.SysResourceService;

@Namespace("/platform/mymenuconfig")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class MyMenuConfigAction extends CrudActionSupport {

	@Autowired
	@Qualifier("myMenuConfigService")
	private MyMenuConfigService myMenuConfigService;
	
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService;
	
	private int id;
	
	private String resourceIds;
	
	// 菜单资源查询结果，以JSON格式返回，用作ExtJS tree的数据源，保留了children节点
	private String menusJsonOfTree;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 *  用户自定义菜单 树
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "listHomeDefineMenusTree", results = { @Result(name = SUCCESS, location = "sysresource-menutree-json.jsp") })
	@SkipValidation
	public String listHomeDefineMenusTree() throws Exception{
		
		List<SysResource> resources = sysResourceService.listSysresource(SmfConst.PRIV_RESOURCE_TYPE_MENU);
		
		List<Node> nodes = new ArrayList<Node>();
		homeConverToNode(nodes,resources);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[6];
		excludes[0] = "parents";
		excludes[1] = "attributes";
		excludes[2] = "target";
		excludes[3] = "state";
		excludes[4] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(nodes, jsn);
		menusJsonOfTree = jsonObject.toString();
		return SUCCESS;
	}
	
	private void homeConverToNode(List<Node> nodes, List<SysResource> resources) {
		SmfUserDetails userDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		List<SysResource> userSysResources= userDetails.getCurrentSysResources();//用户拥有的菜单资源
		if(null == userSysResources || userSysResources.size()==0){
			log.info("用户拥有的菜单为空");
		}
		
		this.getMunuConfigInfo();
		List<String> listCheckedID = new ArrayList<String>();
		String[] checkedIdArr = resourceIds.split(",");
		for(String checkedId:checkedIdArr){
			listCheckedID.add(checkedId);
		}
		
		
		for(SysResource sysResourcec:resources){
			if(sysResourcec instanceof SysResource){
				if(userSysResources.contains(sysResourcec) == false){
					continue;
				}
				
				Node nodec = new Node();
				nodec.setId(""+sysResourcec.getId());
				nodec.setText(sysResourcec.getResourceName());
				if(null !=sysResourcec.getChildren() && sysResourcec.getChildren().size() >0){
					nodec.setIconCls("menu-tree-node-icon-dir");
				} else {
					nodec.setIconCls("menu-tree-node-icon-leaf");
				}
				
				this.HomeGetChildren(nodec,sysResourcec,userSysResources,listCheckedID);
				nodes.add(nodec);
			}

		}
		
	
		
	}

	/**
	 * @param node
	 * @param sysResourcec
	 * @param userSysResources 用户能访问的菜单
	 * @param listCheckedID   用户已经设置的自定义菜单
	 */
	private void HomeGetChildren(Node node, SysResource sysResourcec,List<SysResource> userSysResources,List<String> listCheckedID) {
		Set<Node>  nodes = new HashSet<Node>();
		if(null != sysResourcec.getChildren()){
			for(SysResource groupc:sysResourcec.getChildren()){
				
				if(userSysResources.contains(groupc) == false){
					continue;
				}
				
				Node childrennode = new Node();
				childrennode.setId(""+groupc.getId());
				childrennode.setText(groupc.getResourceName());
				if(null !=groupc.getChildren()&& groupc.getChildren().size() >0){
					childrennode.setIconCls("menu-tree-node-icon-dir");
				} else {
					childrennode.setIconCls("menu-tree-node-icon-leaf");
				}
				if(listCheckedID.contains(groupc.getId().toString())){
					childrennode.setChecked(true);
				}
				
				nodes.add(childrennode);
				this.HomeGetChildren(childrennode,groupc,userSysResources,listCheckedID);
			}
			node.setChildren(nodes);
		} 
	}
	
	
	@Action(value = "input", results = { @Result(name = "success", location = "tree.jsp") })
	@SkipValidation
	@Override
	public String input() throws Exception {
		this.getMunuConfigInfo();
		
		return "success";
	}
	
	private void getMunuConfigInfo(){
		SysUser loginUser = SmfSecurityContextHolder.getCurrentUser();
		MyMenuConfig myMenuConfig = myMenuConfigService.getMyMenuConfig(loginUser.getUserCode());
		if(myMenuConfig != null){
			id = Integer.parseInt(myMenuConfig.getId().toString());
			resourceIds = myMenuConfig.getResourceIds();
		}else{
			id = 0;
			resourceIds = myMenuConfigService.getDefalutShortCastEntranceSet();
			//resourceIds = "215,216,217,218";
		}
	}
	
	@Action(value = "save", results = { @Result(name = CrudActionSupport.RELOAD, location = "input.action", type = "redirect")})
	@Override
	public String save() throws Exception {
		MyMenuConfig myMenuConfig = null;
		if(this.getId()>0){
			myMenuConfig = myMenuConfigService.get(new Long(id));
		}else{
			myMenuConfig = new MyMenuConfig();
			SysUser loginUser = SmfSecurityContextHolder.getCurrentUser();
			myMenuConfig.setUsercode(loginUser.getUserCode());
		}
		
		if(this.getResourceIds().trim().length()>0){
			String resourceIdstr = "";
			String [] resourceIdsArr = resourceIds.split(",");
			for(int i =0;i<resourceIdsArr.length;i++){
				//判断资源id --- 是否符合权限要求--是否为父节点
				String resourceId = resourceIdsArr[i];
				SysResource sysResource = sysResourceService.get(Long.parseLong(resourceId));
				if(sysResource.getResourceType().equals(SmfConst.PRIV_RESOURCE_TYPE_MENU)){
					if(null !=sysResource.getChildren()&& sysResource.getChildren().size() >0 ){
						//父节点排除
						
					}else{
						//只选择子节点添加
						if(resourceIdstr.length() > 0){
							resourceIdstr += ",";
						}
						resourceIdstr += resourceId;
					}
				}
			}
			
			if(resourceIdstr.length()>0){
				myMenuConfig.setResourceIds(resourceIdstr);
				myMenuConfigService.saveOrUpdate(myMenuConfig);
			}else{
				log.info("筛选后的resourceIdstr的值为空");
			}
			
			
			
		}else{
			log.info("首页快速入口设置传递的参数ResourceIds的值为空");
		}
		
		
		return RELOAD;
	}
	
	
	/**
	 * 获取用户菜单配置记录，以JSON的格式返回用户记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysmymenu-json.jsp") })
	@SkipValidation
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	public String getMenusJsonOfTree() {
		return menusJsonOfTree;
	}

	public void setMenusJsonOfTree(String menusJsonOfTree) {
		this.menusJsonOfTree = menusJsonOfTree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	
}
