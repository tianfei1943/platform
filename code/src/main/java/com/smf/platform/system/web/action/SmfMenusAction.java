package com.smf.platform.system.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.common.SmfConst;
import com.smf.platform.security.SmfSecurityContextHolder;
import com.smf.platform.security.domain.SmfUserDetails;
import com.smf.platform.system.domain.RootMenus;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.service.api.SysResourceService;

/**
 * 提供当前用户能访问的菜单等功能
 * @author robbie
 * @since 2011-08-15
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("smfjson-default")
public class SmfMenusAction extends ActionSupport {

	// 获取资源接口(可以获取到菜单的所有信息)
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService;

	// 查询结果
	private List<RootMenus> rootMenus = new ArrayList<RootMenus>();
	
	private List<SysResource> sysMenus = new ArrayList<SysResource>();

	// ------ 页面属性 Begin ------//
	private String selectedMenuId;
	private int rooMenusSize;
	private String jsonResult = null;
	// 当前登录用户姓名
	private String userName;
	// 当前登录时间
	private String logedTime;
	
	private String pagetype;
	// ------ 页面属性 End ------//
	
	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}
	
	/**
	 * 获取用户能访问的根节点菜单
	 * 
	 * @return
	 */
	@Action(value = "/platform/frames/listRootMenus", results = { 
			@Result(name = ActionSupport.SUCCESS, type = "json"),
			@Result(name = "Failure", location = "/platform/login/login.jsp", type="redirect")})
	public String listRootMenus() throws Exception {
		SmfUserDetails userDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		if(null != userDetails) {
			// 获取用户可以访问的根目录菜单
			List<SysResource> rootMenuss = userDetails.getRootMenus();
			for (int i = 0; i < rootMenuss.size(); i++) {
				SysResource sr = rootMenuss.get(i);
				RootMenus  rms = new RootMenus();
				rms.setId(sr.getId());
				rms.setResourceName(sr.getResourceName());
				rms.setResourceUrl(sr.getResourceUrl());
				rootMenus.add(rms);
			}
			// 设置当前用户姓名
			this.setUserName(userDetails.getUser().getUserName());		
			// 设置当前登录时间
			Date currentDate = new Date();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			this.setLogedTime(sdf.format(currentDate));
			return ActionSupport.SUCCESS;
		} else {
			return "Failure";
		}
	}


	/**
	 * 获取用户能访问的根节点菜单
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "/platform/frames/listRootMenus2", results = { 
			@Result(name = ActionSupport.SUCCESS,location = "menus-json.jsp"),
			@Result(name = "MAIN", location = "main.jsp"),
			@Result(name = "Failure", location = "/platform/login/login.jsp", type="redirect")})
	public String listRootMenus2() throws Exception {
		SmfUserDetails userDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		if(null != userDetails) {
			// 获取用户可以访问的根目录菜单
			//List<SysResource> sysMenu = sysResourceService.listAllSysResource(SmfConst.PRIV_RESOURCE_TYPE_MENU);
			List<SysResource> sysMenu= userDetails.getCurrentSysResources();
			for(SysResource sr : sysMenu){
				if(null == sr.getResourceUrl() || "".equals(sr.getResourceUrl())){
					SysResource  sysResource = sysResourceService.get(sr.getId());
					sysMenus.add(sysResource);
				}
				
			}	
			// 从sysMenu中移除状态为删除的资源，移除用户不具有权限的资源
			for (SysResource temp : sysMenus) {
				dataCleaning(temp);
			}	
			for (SysResource temp : sysMenus) {
				dataCleaning(sysMenu, temp);
			}	
			
			Collections.sort(sysMenus, new Comparator(){

				@Override
				public int compare(Object o1, Object o2) {
					SysResource s1 = (SysResource)o1;
					SysResource s2 = (SysResource)o2;
					return s1.getSortOrder().compareTo(s2.getSortOrder());
				}
				
			});
			
			// 设置当前登录时间
			Date currentDate = new Date();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			
			JsonConfig jsonConfig = new JsonConfig();
			String[] excludes = new String[3];
			excludes[0] = "hibernateLazyInitializer";
			//excludes[1] = "children";
			excludes[1] = "privileges";
			excludes[2] = "parent";
	
			jsonConfig.setExcludes(excludes);
			JSONArray jsonObject = JSONArray.fromObject(sysMenus, jsonConfig);
			this.jsonResult = "{\"userName\":\"" + userDetails.getUser().getUserName() + "\",\"logedTime\":\"" + sdf.format(currentDate) + "\",\"sysMenus\":" + jsonObject.toString() + "}";
			if("main".equals(this.pagetype)) {
				return "MAIN";
			} else {
				return ActionSupport.SUCCESS;
			}
		} else {
			return "Failure";
		}
	}

	/**
	 * 数据清洗，从rootMenu的所有子节点中移除状态为删除的菜单
	 * 
	 * @param sysResources
	 * @param menu
	 */
	private void dataCleaning(SysResource rootMenu) {
		List<SysResource> menuChildren = rootMenu.getChildren();
		Iterator<SysResource> menuChildrenIter = menuChildren.iterator();
		while (menuChildrenIter.hasNext()) {
			SysResource child = menuChildrenIter.next();
			if (child.getStatus() == SmfConst.STATUS_HIDDEN) {
				menuChildrenIter.remove();
			} else {
				if (child.getChildren() != null) {
					for (SysResource temp : child.getChildren()) {
						dataCleaning(temp);
					}					
				}
			}
		}
	}

	/**
	 * 数据清洗，从menu的所有子节点中移除在sysResources中不存在的菜单
	 * 
	 * @param sysResources
	 * @param menu
	 */
	private void dataCleaning(List<SysResource> sysResources, SysResource menu) {
		List<SysResource> menuChildren = menu.getChildren();
		Iterator<SysResource> menuChildrenIter = menuChildren.iterator();
		while (menuChildrenIter.hasNext()) {
			SysResource child = menuChildrenIter.next();
			if (!sysResources.contains(child)) {
				menuChildrenIter.remove();
			} else {
				if (child.getChildren() != null) {
					dataCleaning(sysResources, child);
				}
			}
		}
	}

	/**
	 * 获取某个根节点菜单下的第一个默认子菜单，以Json格式返回
	 * 
	 * @return
	 */
	@Action(value = "/platform/frames/listSubDefaultMenu")
	public void listSubDefaultMenu() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		SysResource menu = sysResourceService.get(new Long(this.getSelectedMenuId()));
	
		SmfUserDetails userDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		List<SysResource> sysResources = userDetails.getCurrentSysResources();

		List<SysResource> childrenTemp = null;
		if (null != menu) {
			// 数据清洗
			this.dataCleaning(sysResources, menu);
			childrenTemp = menu.getChildren();
		}

		// 从chaildrenTemp中查找第一个默认打开菜单（通过判断defaultOpen），如没有，则返回第一个子菜单
		String url = this.findDefaultMenuUrl(childrenTemp);
		if (url == null) {
			response.getWriter().print("1");
		} else {
			response.getWriter().print(url);
		}
	}

	public String findDefaultMenuUrl(List<SysResource> childrenResources) {
		if (childrenResources !=null && childrenResources.size() > 0) {
			// 首先在当前子节点中查找，如找不到，再在子节点的子节点中寻找
			Iterator<SysResource> childIter = childrenResources.iterator();
			while (childIter.hasNext()) {
				SysResource child = childIter.next();
				if (child.getResourceType().equals(SmfConst.PRIV_RESOURCE_TYPE_MENU)) {
					if (child.isDefaultOpen()) {
						return child.getResourceUrl();
					}
				}
			}
			
			while (childIter.hasNext()) {
				SysResource child = childIter.next();
				if (child.getResourceType().equals(SmfConst.PRIV_RESOURCE_TYPE_MENU)) {
					if (child.getChildren() != null) {
						return this.findDefaultMenuUrl(child.getChildren());
					}
				}
			}
			
			// 找第一个菜单返回
			childIter = childrenResources.iterator();
			while (childIter.hasNext()) {
				SysResource child = childIter.next();
				if (child.getResourceType().equals(SmfConst.PRIV_RESOURCE_TYPE_MENU)) {
					return child.getResourceUrl();
				}
			}
		}
		
		return null;
	}
	/**
	 * 获取某个根节点菜单下的所有子菜单，以Json格式返回
	 * 
	 * @return
	 */
	@Action(value = "/platform/frames/listSubMenus", results = { 
			@Result(name = ActionSupport.SUCCESS, location = "menus-json.jsp"),
			@Result(name = "Failure", location = "/platform/login/login.jsp", type="redirect")})
	public String listSubMenus() throws Exception {
		SysResource menu = sysResourceService.get(new Long(this.getSelectedMenuId()));
	
		SmfUserDetails userDetails = SmfSecurityContextHolder.getCurrentUserDetails();
		if (userDetails != null) {
			
			List<SysResource> sysResources = userDetails.getCurrentSysResources();
	
			List<SysResource> childrenTemp = null;
			if (null != menu) {
				// 数据清洗
				this.dataCleaning(sysResources, menu);
				childrenTemp = menu.getChildren();
			}
	
			JsonConfig jsonConfig = new JsonConfig();
			String[] excludes = new String[3];
			excludes[0] = "parent";
			excludes[1] = "privileges";
			excludes[2] = "hibernateLazyInitializer";
	
			jsonConfig.setExcludes(excludes);
			JSONArray jsonObject = JSONArray.fromObject(childrenTemp, jsonConfig);
			this.jsonResult = jsonObject.toString();
			return ActionSupport.SUCCESS;
		} else {
			return "Failure";
		}
	}
	
	public int getRooMenusSize() {
		if (this.rootMenus != null) {
			return this.rootMenus.size();
		}
		return rooMenusSize;
	}

	public void setRooMenusSize(int rooMenusSize) {
		this.rooMenusSize = rooMenusSize;
	}

	public List<RootMenus> getRootMenus() {
		return rootMenus;
	}

	public void setRootMenus(List<RootMenus> rootMenus) {
		this.rootMenus = rootMenus;
	}

	public String getSelectedMenuId() {
		return selectedMenuId;
	}

	public void setSelectedMenuId(String selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogedTime() {
		return logedTime;
	}

	public void setLogedTime(String logedTime) {
		this.logedTime = logedTime;
	}

	public List<SysResource> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(List<SysResource> sysMenus) {
		this.sysMenus = sysMenus;
	}

}
