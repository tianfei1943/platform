package com.smf.platform.system.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
import com.smf.platform.common.EncoderByMd5;
import com.smf.platform.common.SmfConst;
import com.smf.platform.common.action.CrudActionSupport;
import com.smf.platform.common.page.PageResponse;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysUserService;

/**
 * 用户管理 
 * @author robbie
 * @since 2011-08-11
 * 
 */
// 定义URL映射对应/platform/sysuser.action
@Namespace("/platform/sysuser")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class SysUserAction extends CrudActionSupport {
	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
	// ------ 页面属性 Begin ------//
	// UserID
	private String id;
	// UserName
	private String userName;

	// 当前用户
	private SysUser user;
	
	// EXTJS查询User数据时，由于需要对JSON的返回值进行处理，因此将返回值作为Action的属性
	private String jsonOfList;

	protected int page = 0;
	/**The max record will show in every page*/
	protected int rows = 10;
	
	// ------ 页面属性 End ------//

	/**
	 * 获取用户记录，以JSON的格式返回用户记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(name = SUCCESS, location = "sysuser-json.jsp") })
	@SkipValidation
	@Override
	public String list() throws Exception {
		PageResponse<SysUser> pageresponse = sysUserService.listUser(this.getUserName(), page - 1, rows);
		JsonConfig jsn = new JsonConfig();
		String[] excludes = new String[5];
		excludes[0] = "groups";
		excludes[1] = "roles";
		excludes[2] = "telephone";
		excludes[3] = "cellphone";
		excludes[4] = "hibernateLazyInitializer";
		jsn.setExcludes(excludes);
		JSONArray jsObj = JSONArray.fromObject(pageresponse.getResult(), jsn);
		jsonOfList = "{\"total\":" + pageresponse.getTotal() + ",\"rows\":" + jsObj.toString() + "}";
		return SUCCESS;
	}

	/**
	 * 跳转到添加/修改页面
	 * 
	 **/
	@Action(value = "input", results = { @Result(name = ActionSupport.INPUT, location = "sysuser-cu.jsp")})
	@SkipValidation
	@Override
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存用户（新建或更新）
	 * 
	 * @return
	 */
	@Action(value = "save", results = { 
			@Result(name = CrudActionSupport.RELOAD, location = "sysuser.jsp", type = "redirect"), 
			@Result(name = ActionSupport.INPUT, location = "sysuser-cu.jsp")})
	@Override
	public String save() throws Exception {
		if (this.getId() != null && !this.getId().equals("")) {
			Set<SysRole> roleSet = user.getRoles();
			user.setRoles(roleSet);
			Set<SysGroup> groupSet = user.getGroups();
			user.setGroups(groupSet);
			user.setPassword(EncoderByMd5.encodeByMd5(user.getPassword()));
			sysUserService.update(user);
//			pfLogService.info(this.getClass().getName(), "用户操作", "修改用户信息，用户名:" + user.getUserName());
		} else {
			user.setStatus(SmfConst.STATUS_ACTIVE);
			user.setPassword(EncoderByMd5.encodeByMd5(user.getPassword()));
			sysUserService.save(user);
//			pfLogService.info(this.getClass().getName(), "用户操作", "添加用户信息，用户名:" + user.getUserName());
		}
		return RELOAD;
	}

	/**
	 * 删除操作 可以删除多条记录，多个ID之间使用逗号分隔符
	 * 该删除操作已修改为停用操作 -- add by robbie 2012-02-06
	 * @return
	 */
	@Action(value = "delete", results = { @Result(name = CrudActionSupport.JSON, type = "json") })
	@SkipValidation
	@Override
	public String delete() throws Exception {
		if (null != id) {
			String strId = this.getId().trim();
			String[] userIds = strId.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String id : userIds) {
				idList.add(Long.parseLong(id));
			}
			sysUserService.remove(idList);
		}
		return CrudActionSupport.JSON;
	}
	
	/**
	 * 启用操作 可以启用多条记录，多个ID之间使用逗号分隔符
	 * 
	 * @return
	 */
	@Action(value = "restore", results = { @Result(name = CrudActionSupport.JSON, type = "json") })
	@SkipValidation
	public String restore() throws Exception {
		if (null != id) {
			String strId = this.getId().trim();
			String[] userIds = strId.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String id : userIds) {
				idList.add(Long.parseLong(id));
			}
			sysUserService.restore(idList);
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
			user = sysUserService.get(Long.parseLong(this.getId().trim()));
		} else {
			user = new SysUser();
		}
	}

	/**
	 * 在保存前进行验证，在save方法调用前被调用，由DefaultWorkflowInterceptor拦截器提供该功能
	 * @throws Exception
	 */
	public void validateSave() throws Exception {
		if (user != null) {
			if (user.getUserCode() == null
					|| "".equals(user.getUserCode().trim())) {
				this.addFieldError("user.userCode", "编码不能为空");
			}
			if (!"".equals(user.getUserCode())) {
				if (user.getUserCode().trim().length() > 100) {
					this.addFieldError("user.userCode", "编码长度过长");
				}
			}
			if (user.getUserName() == null
					|| "".equals(user.getUserName().trim())) {
				this.addFieldError("user.userName", "姓名不能为空");
			}
			if (!"".equals(user.getUserName())) {
				if (user.getUserName().trim().length() > 100) {
					this.addFieldError("user.userName", "姓名长度过长");
				}
			}
			if (user.getPassword() == null
					|| "".equals(user.getPassword().trim())) {
				this.addFieldError("user.password", "密码不能为空");
			}
			if (!"".equals(user.getPassword())) {
				if (user.getPassword().trim().length() > 100) {
					this.addFieldError("user.password", "密码长度过长");
				}
			}
			if (!"".equals(user.getCellphone())) {
				if (user.getCellphone().trim().length() > 20) {
					this.addFieldError("user.cellphone", "手机号码长度过长");
				}
			}
			if (!"".equals(user.getTelephone())) {
				if (user.getTelephone().trim().length() > 20) {
					this.addFieldError("user.telephone", "电话号码长度过长");
				}
			}
			if (!"".equals(user.getDescription())) {
				if (user.getDescription().trim().length() > 100) {
					this.addFieldError("user.description", "备注长度过长");
				}
			}
			if (user.getUserCode() != null
					&& !"".equals(user.getUserCode().trim())) {
				if (user.getId() == null || "".equals(user.getId())) {
					List<SysUser> list = sysUserService.queryByUserCode(user
							.getUserCode());
					if (list.size() > 0) {
						this.addFieldError("user.userCode", "此用户编码已存在，请重新输入！");
					}
				}
			}
			String email = user.getEmail();
			if (email != null && !"".equals(email.trim())) {
				String regEx = "(\\w)*@([a-zA-Z0-9]*(\\.))*[a-zA-Z]*";
				boolean mat = Pattern.matches(regEx, email);
				if (mat == false) {
					this.addFieldError("user.email", "邮件不符合格式，请重新输入！");
				} else {
					if (email.trim().length() > 100) {
						this.addFieldError("user.email", "邮件长度过长");
					}
				}
			}
		}
	}

	public String getJsonOfList() {
		return jsonOfList;
	}

	public void setJsonOfList(String jsonOfList) {
		this.jsonOfList = jsonOfList;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
