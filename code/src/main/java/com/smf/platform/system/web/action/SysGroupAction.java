package com.smf.platform.system.web.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
import com.smf.platform.system.domain.Node;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysGroupService;
import com.smf.platform.system.service.api.SysUserService;

/**
 * 
 * 组织机构管理
 * @author robbie
 * @since 2011-08-15
 *
 */
@Namespace("/platform/sysgroup")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysGroupAction extends CrudActionSupport {
	
	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
	
	@Autowired
	@Qualifier("sysGroupService")
	private SysGroupService sysGroupService;
	
	private SysGroup sysGroup;
	
	// ------ 页面属性 Begin ------//
	
	// groupId
	private String id = null;
	
	// 下面两个参数是添加或修改组织机构时使用
	// 组包含的用户ID，多个用户ID之间用逗号分隔
	private String userIds;
	// 组的父节点ID，多个ID之间用逗号分隔
	private String parentIds;
	
	// 用户组的JSON返回值
	private String jsonSysGroup;
	
	// 用户的JSON返回值
	private String jsonUser;
	
	// 下面两个参数是删除SysGroup或者SysGroup包含的SysUser时使用
	//用户节点父节点的Id
	private String userParentGroupId;
	// 组织机构节点父节点的Id
	private String groupParentId;
	private int gType;
	public int getGType() {
		return gType;
	}

	public void setGType(int type) {
		gType = type;
	}
	// ------ 页面属性 End ------//
	
	/**
	 * 获取表格中的组织机构名称和用户数据，并转换成JSON格式返回
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list",results = {@Result(name = SUCCESS, location = "sysgroup-json.jsp")})
	@SkipValidation
	public String list() throws Exception {
		List<SysGroup> sysGroupList = sysGroupService.listSysGroupRelationOfRoot();
		List<Node> nodes = new ArrayList<Node>();
		converToNode(nodes,sysGroupList);
		String[] excludes = new String[1];
		excludes[0] = "parents";
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonArray = JSONArray.fromObject(nodes, jsonConfig);
		jsonSysGroup = jsonArray.toString();
		return SUCCESS;
	}
	
	private void converToNode(List<Node> nodes, List<SysGroup> sysGroupList) {
		List<String> parentIdsls = new ArrayList<String>();
		if(null != parentIds && !"".equals(parentIds)){
			String[] pa = parentIds.split(",");
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
		if(gType == 1&& null != sysGroupc.getUsers()) {
			for(SysUser sysUser:sysGroupc.getUsers()){
				Node childrennode = new Node();
				childrennode.setId("U_"+sysUser.getId());
				childrennode.setText(sysUser.getUserName());
				childrennode.setState("colse");
				childrennode.setIconCls("icon-sysUser");
				nodes.add(childrennode);
			}
			node.setChildren(nodes);
		}
	}

	/**
	 *  获取所有的用户记录
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "listuser", results = { @Result(name = SUCCESS, location = "sysgroup-user-json.jsp") })
	@SkipValidation
	public String listUser() throws ParseException{
		List<SysUser> resource = sysUserService.list();
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[7];
		excludes[0] = "password";
		excludes[1] = "email";
		excludes[2] = "telephone";
		excludes[3] = "cellphone";
		excludes[4] = "groups";
		excludes[5] = "roles";
		excludes[6] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(resource, jsn);
		jsonUser = jsonObject.toString();
		return SUCCESS;
	}
	
	/**
	 * 跳转到添加/修改页面
	 * @return
	 * @throws Exception
	 */
	@Action(value = "input",results = { @Result(name = ActionSupport.INPUT, location = "sysgroup-cu.jsp")})
	@SkipValidation
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	

	/**
	 * 保存组织机构（新建或更新）
	 * @return
	 * @throws Exception
	 */
	@Action(value = "save",results = {
			@Result(name = CrudActionSupport.RELOAD, location = "sysgroup.jsp", type = "redirect"), 
			@Result(name = ActionSupport.INPUT, location = "sysgroup-cu.jsp")})
	@Override
	public String save() throws Exception {
		// 父节点都没有选择时，则没有父节点，作为根节点处理
		if(this.getParentIds().equals("")){
			sysGroup.setParents(null);
		}else {
			StringTokenizer id = new StringTokenizer(this.getParentIds(),",");
			Set<SysGroup> set = new TreeSet<SysGroup>();
			while(id.hasMoreTokens()){
				SysGroup parents = sysGroupService.get(Long.parseLong(id.nextToken()));
				set.add(parents);
			}
			//SysGroup parents = pfSysGroupService.get(Long.parseLong(this.getSelectSysGroupRelationId()));
			sysGroup.setParents(set);
		}
		//添加用户
		if(!"".equals(this.getUserIds()) && this.getUserIds()!=null){
			Set<SysUser> userSet = new TreeSet<SysUser>();
			String[] a = this.getUserIds().split(",");
			for(String id : a){
				SysUser sysUser = sysUserService.get(Long.parseLong(id));
				userSet.add(sysUser);
			}
			sysGroup.setUsers(userSet);
		}
		//判断是添加还是修改
		if(sysGroup.getId()!=null){
			SysGroup group = sysGroupService.get(sysGroup.getId());
			sysGroup.setRoles(group.getRoles());
			sysGroupService.update(sysGroup);
		}else{
			sysGroupService.save(sysGroup);
		}
		return RELOAD;
	}
	
	/**
	 *  删除
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "delete", results = { @Result(name = CrudActionSupport.JSON, type = "json") })
	@SkipValidation
	@Override
	public String delete() throws Exception {
		if(!"".equals(this.getUserParentGroupId())){
			SysGroup parent = sysGroupService.get(Long.parseLong(this.getUserParentGroupId()));
			SysUser user = sysUserService.get(Long.parseLong(this.getId()));
			Set<SysUser> userSet = parent.getUsers();
			userSet.remove(user);
			sysGroupService.update(parent);
		}
		else if(null!=this.getId()){
			SysGroup group = sysGroupService.get(Long.parseLong(this.getId()));
			if(!"-1".equals(this.getGroupParentId())&& group.getParents().size()>1){
				SysGroup groupParent = sysGroupService.get(Long.parseLong(this.getGroupParentId()));
				Set<SysGroup> groupSet = groupParent.getChildren();
				groupSet.remove(group);
				sysGroupService.update(groupParent);
			}else{
				sysGroupService.remove(group);
			}
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
			sysGroup = sysGroupService.get(Long.parseLong(this.getId().trim()));
			if(sysGroup!=null){
				Set<SysUser> user = sysGroup.getUsers();
				
				// 将组包含的用户形成字符串赋予userId
				Iterator<SysUser> userIt = user.iterator();
				this.userIds="";
				while(userIt.hasNext()){
					this.userIds = this.userIds + userIt.next().getId()+",";
				}
				if(this.userIds.endsWith(",")){
					this.userIds = this.userIds.substring(0, this.userIds.length()-1);
				}
				
				// 将组的父节点形成字符串赋予parentIds
				Set<SysGroup> parentGroup = sysGroup.getParents();
				Iterator<SysGroup> it = parentGroup.iterator();
				this.parentIds="";
				while(it.hasNext()){
					this.parentIds = this.parentIds + it.next().getId()+",";
				}
				if(this.parentIds.endsWith(",")){
					this.setId(this.parentIds.substring(0, this.parentIds.length()-1));
				}
			}
		} else {
			sysGroup = new SysGroup();
		}		
	}
	
	public void validateSave(){
		if(sysGroup != null){
			if(null == sysGroup.getGroupCode() ||"".equals(sysGroup.getGroupCode().trim())){
				this.addFieldError("sysGroup.groupCode", "组织机构编号不能为空！");
			}
			if(null == sysGroup.getGroupName() || "".equals(sysGroup.getGroupName().trim())){
				this.addFieldError("sysGroup.groupName", "组织机构名称不能为空!");
			}
			if(!"".equals(sysGroup.getGroupCode().trim())){
				if(sysGroup.getGroupCode().trim().length()>100){
					this.addFieldError("sysGroup.groupCode", "组织机构编号过长！");
				}
			}
			if(!"".equals(sysGroup.getGroupName().trim())){
				if(sysGroup.getGroupName().trim().length()>100){
					this.addFieldError("sysGroup.groupName", "组织机构名称过长！");
				}
			}
			if(null != sysGroup.getGroupCode()&&!"".equals(sysGroup.getGroupCode().trim())){
				if(null == sysGroup.getId() || "".equals(sysGroup.getId())){
					List <SysGroup> list = sysGroupService.queryByProperty("groupCode", sysGroup.getGroupCode());
					if(list.size()>0){
						this.addFieldError("sysGroup.groupCode", "此组织机构编码已经存在！");
					}
				}
			}
		}
	}
	
	public SysGroup getSysGroup() {
		return sysGroup;
	}

	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJsonSysGroup() {
		return jsonSysGroup;
	}

	public void setJsonSysGroup(String jsonSysGroup) {
		this.jsonSysGroup = jsonSysGroup;
	}

	public String getJsonUser() {
		return jsonUser;
	}

	public void setJsonUser(String jsonUser) {
		this.jsonUser = jsonUser;
	}

	/**
	 * @return the userId
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserParentGroupId() {
		return userParentGroupId;
	}

	public void setUserParentGroupId(String userParentGroupId) {
		this.userParentGroupId = userParentGroupId;
	}

	public String getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}	

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
}
