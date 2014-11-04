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

import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.system.domain.Node;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysPrivilege;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysGroupService;
import com.smf.platform.system.service.api.SysPrivilegeService;
import com.smf.platform.system.service.api.SysRoleService;
import com.smf.platform.system.service.api.SysUserService;

/**
 * 角色管理
 * 
 * @author robbie
 * 
 */
@Namespace("/platform/sysrole")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysRoleAction extends CrudActionSupport{
	
	@Autowired
	@Qualifier("sysRoleService")
	private SysRoleService sysRoleService = null;
	
	@Autowired
	@Qualifier("sysPrivilegeService")
	private SysPrivilegeService sysPrivilegeService = null;
	
	@Autowired
	@Qualifier("sysGroupService")
	private SysGroupService sysGroupService = null;
	
	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
	
	
	// ------ 页面属性 Begin ------//
	
	//Json数据
	private String roleJson;
	private String PrivilegeJson;
	private String groupJson;
	private String userJson;
	
	//选中的角色ID
	private String id;
	
	//添加修改页面选中权限的ID
	private String selectedPrivileges;
	
	//添加修改页面选中组织机构的ID
	private String selectedGroups;
	
	//添加修改页面选中用户的ID
	private String selectedUsers;
	
	//角色对象
	private SysRole role;
	
	// 查询条件
	private String roleName;

	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	
	// ------ 页面属性 end ------//
	
	/**
	 * 初期显示 获取角色
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysrole-json.jsp") })
	@SkipValidation
	public String list() throws ParseException{
		SysRole tempRole = new SysRole();
		tempRole.setRoleName(roleName);
		PageResponse<SysRole> pageresponse = sysRoleService.list(tempRole, page-1, rows);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[5];
		excludes[0] = "parent";
		excludes[1] = "privileges";
		excludes[2] = "users";
		excludes[3] = "groups";
		excludes[4] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsObj = JSONArray.fromObject(pageresponse.getResult(), jsn);			
		roleJson = "{\"total\":" + pageresponse.getTotal() + ",\"rows\":"
				+ jsObj.toString() + "}";
		return SUCCESS;
	}
	
	
	/**
	 * 获取所有角色
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listAllRole", results = { @Result(name = SUCCESS, location = "sysrole-json.jsp") })
	@SkipValidation
	public String listAllRole() throws ParseException{
		List<SysRole> listSysRole = sysRoleService.listSysRole();
		List<Node> nodes = new ArrayList<Node>();
		homeConverToNode(nodes,listSysRole);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[6];
		excludes[0] = "parents";
		excludes[1] = "attributes";
		excludes[2] = "target";
		excludes[3] = "state";
		excludes[4] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(nodes, jsn);
		roleJson = jsonObject.toString();
		return SUCCESS;
	}
	
	private void homeConverToNode(List<Node> nodes, List<SysRole> listSysRole) {
		for(SysRole sysRole:listSysRole){
			if(sysRole instanceof SysRole){
				
				Node nodec = new Node();
				nodec.setId(""+sysRole.getRoleCode());
				nodec.setText(sysRole.getRoleName());
					//nodec.setIconCls("menu-tree-node-icon-dir");
				nodec.setIconCls("menu-tree-node-icon-leaf");
	
				nodes.add(nodec);
			}
		}
	}

	/**
	 * 跳转到添加/修改页面
	 * @return
	 * @throws Exception
	 */
	@Action(value = "input",results = { @Result(name = INPUT, location = "sysrole-cu.jsp")})
	@SkipValidation
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	/**
	 * 添加修改角色
	 * @return
	 */
	@Action(value = "save", results = {
			@Result(name = RELOAD, location = "sysrole.jsp", type = "redirect"),
			@Result(name = INPUT, location = "sysrole-cu.jsp") })
	public String save(){
		Set<SysPrivilege> PrivilegeSet = new HashSet<SysPrivilege>();
		StringTokenizer privilegeSt = new StringTokenizer(this.getSelectedPrivileges(),",");
		while(privilegeSt.hasMoreTokens()){
			SysPrivilege sysPrivilege = sysPrivilegeService.get(new Long(privilegeSt.nextToken()));
			if(null!= sysPrivilege){
				PrivilegeSet.add(sysPrivilege);
			}
		}
		role.setPrivileges(PrivilegeSet);
		
		Set<SysUser> userSet = new HashSet<SysUser>();
		StringTokenizer userSt = new StringTokenizer(this.getSelectedUsers(),",");
		while(userSt.hasMoreTokens()){
			SysUser sysUser = sysUserService.get(new Long(userSt.nextToken()));
			if(null!=sysUser){
				userSet.add(sysUser);
			}
		}
		role.setUsers(userSet);
		
		Set<SysGroup> groupSet = new HashSet<SysGroup>();
		StringTokenizer groupSt = new StringTokenizer(this.getSelectedGroups(),",");
		while(groupSt.hasMoreTokens()){
			SysGroup sysGroup = sysGroupService.get(new Long(groupSt.nextToken()));
			if(null!=sysGroup){
				groupSet.add(sysGroup);
			}
		}
		role.setGroups(groupSet);

		sysRoleService.saveOrUpdate(role);
		return RELOAD;
	}
	
	/**
	 *  删除权限
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "delete", results = { @Result(name = JSON, type = "json") })
	@SkipValidation
	public String delete() throws Exception{
		if(null!=this.getId()){
			String strId = this.getId().trim();
			String[] roleIds = strId.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String id : roleIds) {
				idList.add(Long.parseLong(id));
			}
			sysRoleService.remove(idList);
		}
		return JSON;
	}
	
	public void validateSave() {
		if(role!=null){
			if(role.getRoleCode()==null||"".equals(role.getRoleCode())){
				this.addFieldError("role.roleCode", "角色编码不能为空");
			}
			if(!"".equals(role.getRoleCode()) && role.getRoleCode()!=null){
				if(role.getRoleCode().length()>100){
					this.addFieldError("role.roleCode", "角色编码长度太长");
				}
				if(role.getId()==null || "".equals(role.getId())){
					List<SysRole> sysRole = sysRoleService.listByCode(role.getRoleCode());
					if(sysRole.size()>0){
						this.addFieldError("role.roleCode", "角色编码已经存在！请重新输入");
					}
				}
			}
			if(role.getRoleName()==null || "".equals(role.getRoleName())){
				this.addFieldError("role.roleName", "角色名称不能为空");
			}
			if(!"".equals(role.getRoleName()) && role.getRoleName()!=null){
				if(role.getRoleName().length()>100){
					this.addFieldError("role.roleName", "角色名称长度太长");
				}
				List<SysRole> roleList = sysRoleService.listByName(role.getRoleName());
				//判断是添加还是修改
				if(role.getId()!=null&&!"".equals(role.getId())){
					SysRole baseRole = sysRoleService.get(role.getId());
					if(baseRole!=null&&!"".equals(baseRole)){
						//判断用户名是否改变
						if(!baseRole.getRoleName().equals(role.getRoleName())){
							if(roleList.size()>0){
								this.addFieldError("role.roleName", "角色名称已经存在,请从新输入");
							}
						}
					}
				}else{
					if(roleList.size()>0){
						this.addFieldError("role.roleName", "角色名称已经存在,请从新输入");
					}
				}
			}
		}
	}
	
	/**
	 * 根据页面传递的id参数加载相应的对象
	 * 在CrudActionSupport的prepareList、prepareInput、prepareSave中调用
	 * @throws Exception
	 */
	@SkipValidation
	protected void prepareModel() throws Exception{
		if(null != this.getId()){
			role = sysRoleService.get(Long.parseLong(this.getId()));
			if(role!=null){
				Set<SysPrivilege> privilege = role.getPrivileges();
				Iterator<SysPrivilege> privilegeIt = privilege.iterator();
				this.selectedPrivileges = "";
				while(privilegeIt.hasNext()){
					this.selectedPrivileges = this.selectedPrivileges + privilegeIt.next().getId() + ",";
				}
				if(this.selectedPrivileges.endsWith(",")){
					this.selectedPrivileges = this.selectedPrivileges.substring(0, this.selectedPrivileges.length()-1);
				}
				
				Set<SysUser> user = role.getUsers();
				Iterator<SysUser> userIt = user.iterator();
				this.selectedUsers = "";
				while(userIt.hasNext()){
					this.selectedUsers = this.selectedUsers + userIt.next().getId() + ",";
				}
				if(this.selectedUsers.endsWith(",")){
					this.selectedUsers = this.selectedUsers.substring(0, this.selectedUsers.length()-1);
				}
				
				Set<SysGroup> group = role.getGroups();
				Iterator<SysGroup> groupIt = group.iterator();
				this.selectedGroups ="";
				while(groupIt.hasNext()){
					this.selectedGroups = this.selectedGroups + groupIt.next().getId() + ",";
				}
				if(this.selectedGroups.endsWith(",")){
					this.selectedGroups = this.selectedGroups.substring(0, this.selectedGroups.length()-1);
				}
				
			}
		}else {
			role = new SysRole();
		}	
	}
	
	/**
	 *  跳转到角色详情页面
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "detail", results = { @Result(name = SUCCESS, location = "sysrole-detail.jsp") })
	@SkipValidation
	public String roleDetail() throws Exception{
		if(null != this.getId()){
			role = sysRoleService.get(Long.parseLong(this.getId()));
			if(role!=null){
				Set<SysPrivilege> privilege = role.getPrivileges();
				Iterator<SysPrivilege> privilegeIt = privilege.iterator();
				this.selectedPrivileges = "";
				while(privilegeIt.hasNext()){
					this.selectedPrivileges = this.selectedPrivileges + privilegeIt.next().getId() + ",";
				}
				if(this.selectedPrivileges.endsWith(",")){
					this.selectedPrivileges = this.selectedPrivileges.substring(0, this.selectedPrivileges.length()-1);
				}
				
				Set<SysUser> user = role.getUsers();
				Iterator<SysUser> userIt = user.iterator();
				this.selectedUsers = "";
				while(userIt.hasNext()){
					this.selectedUsers = this.selectedUsers + userIt.next().getId() + ",";
				}
				if(this.selectedUsers.endsWith(",")){
					this.selectedUsers = this.selectedUsers.substring(0, this.selectedUsers.length()-1);
				}
				
				Set<SysGroup> group = role.getGroups();
				Iterator<SysGroup> groupIt = group.iterator();
				this.selectedGroups ="";
				while(groupIt.hasNext()){
					this.selectedGroups = this.selectedGroups + groupIt.next().getId() + ",";
				}
				if(this.selectedGroups.endsWith(",")){
					this.selectedGroups = this.selectedGroups.substring(0, this.selectedGroups.length()-1);
				}
				
			}
		}
		return SUCCESS;
	}
	
	/**
	 *  获取权限记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listSysPrivilege", results = { @Result(name = SUCCESS, location = "sysprivilege-json.jsp") })
	@SkipValidation
	public String listSysPrivilege() throws Exception{
		List<SysPrivilege> resource = sysPrivilegeService.listAllPrivilege();
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[4];
		excludes[0] = "isLog";
		excludes[1] = "roles";
		excludes[2] = "resources";
		excludes[3] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(resource, jsn);
		PrivilegeJson = jsonObject.toString();
		return SUCCESS;
	}
	
	/**
	 *  获取组织机构记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listSysGroup", results = { @Result(name = SUCCESS, location = "sysgroup-json.jsp") })
	@SkipValidation
	public String listSysGroup() throws Exception{
		List<SysGroup> resources = sysGroupService.listSysGroupRelationOfRoot();
		List<Node> nodes = new ArrayList<Node>();
		converToNode(nodes,resources);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[4];
		excludes[0] = "users";
		excludes[1] = "roles";
		excludes[2] = "parents";
		excludes[3] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(nodes, jsn);
		groupJson = jsonObject.toString();
		return SUCCESS;
	}
	private void converToNode(List<Node> nodes, List<SysGroup> sysGroupList) {
		List<String> parentIdsls = new ArrayList<String>();
		if(null != selectedGroups && !"".equals(selectedGroups)){
			String[] pa = selectedGroups.split(",");
			for(String p : pa){
				parentIdsls.add(p);
			}
		}
		Set<Node>  pnodes = new HashSet<Node>();
		Node node = new Node();
		node.setId("-1");
		node.setText("组织机构");
		node.setState("colse");
		node.setIconCls("icon-group");
		for(SysGroup sysGroupc:sysGroupList){
			if(sysGroupc instanceof SysGroup){ 
				Node nodec = new Node();
				nodec.setId(""+sysGroupc.getId());
				nodec.setText(sysGroupc.getGroupName());
				nodec.setState("colse");
				nodec.setIconCls("icon-sysUsers");
				if(parentIdsls.contains(""+sysGroupc.getId())){
					nodec.setChecked(true);
				}
				this.getChildren(nodec,sysGroupc,parentIdsls);
				pnodes.add(nodec);
			}

		}
		node.setChildren(pnodes);
		nodes.add(node);
	}

	private void getChildren(Node node, SysGroup sysGroupc,List<String> parentIdsls) {
		Set<Node>  nodes = new HashSet<Node>();
		if(null != sysGroupc.getChildren()){
			for(SysGroup groupc:sysGroupc.getChildren()){
				Node childrennode = new Node();
				childrennode.setId(""+groupc.getId());
				childrennode.setText(groupc.getGroupName());
				childrennode.setState("colse");
				childrennode.setIconCls("icon-sysUsers");
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
	 *  获取用户记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listSysUser", results = { @Result(name = SUCCESS, location = "sysuser-json.jsp") })
	@SkipValidation
	public String listSysUser() throws Exception{
		List<SysUser> resource = sysUserService.list();
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[8];
		excludes[0] = "password";
		excludes[1] = "email";
		excludes[2] = "description";
		excludes[3] = "telephone";
		excludes[4] = "cellphone";
		excludes[5] = "groups";
		excludes[6] = "roles";
		excludes[7] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(resource, jsn);
		userJson = jsonObject.toString();
		return SUCCESS;
	}
	
	public String getRoleJson() {
		return roleJson;
	}
	
	public void setRoleJson(String roleJson) {
		this.roleJson = roleJson;
	}
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysRole getRole() {
		if(role==null){
			role = new SysRole();
		}
		return role;
	}
	
	public void setRole(SysRole role) {
		this.role = role;
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

	public String getPrivilegeJson() {
		return PrivilegeJson;
	}

	public void setPrivilegeJson(String privilegeJson) {
		PrivilegeJson = privilegeJson;
	}

	public String getGroupJson() {
		return groupJson;
	}

	public void setGroupJson(String groupJson) {
		this.groupJson = groupJson;
	}

	public String getUserJson() {
		return userJson;
	}

	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}
	
	public String getSelectedPrivileges() {
		return selectedPrivileges;
	}

	public void setSelectedPrivileges(String selectedPrivileges) {
		this.selectedPrivileges = selectedPrivileges;
	}
	
	public String getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(String selectedGroups) {
		this.selectedGroups = selectedGroups;
	}
	
	public String getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(String selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
