package com.smf.platform.system.web.action;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.common.EncoderByMd5;
import com.smf.platform.security.SmfSecurityContextHolder;
import com.smf.platform.system.domain.SysGroup;
import com.smf.platform.system.domain.SysRole;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.SysUserService;
/**
 * 修改用户基本信息
 * 
 * @author GuanYong
 * 
 */
@Namespace("/platform/sysuser")
@ParentPackage("smfjson-default")
@SuppressWarnings("serial")
public class EditUserAction extends ActionSupport{

	@Autowired
	@Qualifier("sysUserService")
	private SysUserService sysUserService;
	
	private SysUser user;
	
	/**
	 * 跳转到修改用户基本信息页面
	 * */
	@Action(value = "editUserInit", results = { @Result(name = "success",location = "editSysUser.jsp")})
	@SkipValidation
	public String redirectToEdit() throws Exception{
		user = SmfSecurityContextHolder.getCurrentUser();
		return "success";
	}
	/**
	 * 更新用户基本信息
	 * @return
	 * */
	@Action(value = "updateUser", results = {
			@Result(name = "list",type = "redirect",location = "editSysUserSuccess.jsp"),
			@Result(name = "input",location = "editSysUser.jsp")})
	public String UpdateUser() throws Exception{
		SysUser loginUser = SmfSecurityContextHolder.getCurrentUser();
		Set<SysRole> roleSet = loginUser.getRoles();
		user.setRoles(roleSet);
		Set<SysGroup> groupSet = loginUser.getGroups();
		user.setGroups(groupSet);
		user.setStatus(loginUser.getStatus());
		user.setPassword(EncoderByMd5.encodeByMd5(user.getPassword()));
		sysUserService.updateUser(user);
		
		// 将更新后的用户信息重新设置到Context中
		loginUser.setCellphone(user.getCellphone());
		loginUser.setDescription(user.getDescription());
		loginUser.setEmail(user.getEmail());
		loginUser.setPassword(user.getPassword());
		loginUser.setTelephone(user.getTelephone());
		loginUser.setUserCode(user.getUserCode());
		loginUser.setUserName(user.getUserName());
		return "list";
	}
	
	public void validateUpdateUser() throws Exception{
		if(user!=null){
			if(user.getUserName()==null || "".equals(user.getUserName().trim())){
				this.addFieldError("user.userName", "姓名不能为空");
			}
			if(!"".equals(user.getUserName())){
				if(user.getUserName().trim().length()>100){
					this.addFieldError("user.userName", "姓名长度过长");
				}
			}
			if(user.getPassword()==null || "".equals(user.getPassword().trim())){
				this.addFieldError("user.password", "密码不能为空");
			}
			if(!"".equals(user.getPassword())){
				if(user.getPassword().trim().length()>100){
					this.addFieldError("user.password", "密码长度过长");
				}
			}
			if(!"".equals(user.getCellphone())){
				if(user.getCellphone().trim().length()>20){
					this.addFieldError("user.cellphone", "手机号码长度过长");
				}
			}
			if(!"".equals(user.getTelephone())){
				if(user.getTelephone().trim().length()>20){
					this.addFieldError("user.telephone", "电话号码长度过长");
				}
			}
			if(!"".equals(user.getDescription())){
				if(user.getDescription().trim().length()>100){
					this.addFieldError("user.description", "备注长度过长");
				}
			}
			String email = user.getEmail();
			if(email!=null && !"".equals(email.trim())){
				String regEx = "(\\w)*@([a-zA-Z]*(\\.))*[a-zA-Z]*";
				boolean mat = Pattern.matches(regEx, email);
				if (mat == false) {
					this.addFieldError("user.email", "邮件不符合格式，请重新输入！");
				}else{
					if(email.trim().length()>100){
						this.addFieldError("user.email", "邮件长度过长");
					}
				}
			}
		}
	}
	
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
}
