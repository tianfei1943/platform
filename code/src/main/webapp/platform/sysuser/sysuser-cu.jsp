<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改用户信息</title>
	</head>
<!--	<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>-->
		<script type="text/javascript">		 
			//function enterEvent(event){
			//   if(event.keyCode == 13){
			//      submitEvent();
			//   }
			//}
			
			function submitEvent(){
			       //cuForm.j_password.value = hex_md5(cuForm.j_password.value);
                   //alert(saveOrUpdateUserForm.j_password.value);
				   cuForm.submit();
			}
		</script>
	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/sysuser/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
            <div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="id == null || id.isEmpty()">
    					<span>用户管理 - 添加用户</span>
					</s:if>
					<s:else>
						<span>用户管理 - 修改用户</span>
					</s:else>									
                </div>
            </div>

			<div id="div_createorupdate">
				<table>
					<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
						<tr>
							<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
						</tr>
					</table>
					<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">编码：</span>
								<s:if test="id == null || id.isEmpty()">
									<input type="text" id="user.userCode" name="user.userCode"
											style="width: 150px;" value="${user.userCode}"/>
								</s:if>
								<s:else>
										<input type="text" id="user.userCode" name="user.userCode"
											style="width: 150px;" value="${user.userCode}" readOnly/>
								</s:else>
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.userCode</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">姓名：</span>
								<input id="user.userName" name="user.userName" type="text" style="width: 150px" value="${user.userName}" ></input>
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.userName</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">密码：</span>
								<input id="j_password" name="user.password" type="password" style="width: 150px" value="${user.password}" ></input>
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.password</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">邮件：</span>
								<input id="user.email" name="user.email" type="text" style="width: 150px" value="${user.email}" />
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.email</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">电话：</span>
								<input id="user.telephone" name="user.telephone" type="text" style="width: 150px" value="${user.telephone}" />
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.telephone</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">手机：</span>
								<input id="user.cellphone" name="user.cellphone" type="text" style="width: 150px" value="${user.cellphone}" />
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.cellphone</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">备注：</span>
								<textarea id="user.description" name="user.description" style="width: 200px; height: 100px; font-size:9pt">${user.description}</textarea>
								<span class="LABEL_ERROR"><s:fielderror><s:param>user.description</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 10px 10px">
								<input type="button" value="&nbsp;提&nbsp;交&nbsp;" onclick="submitEvent()" class="SUBMIT_BUTTON_CLASS"/>
								<span style="width: 5px;"></span>
								<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysuser/sysuser.jsp'" class="SUBMIT_BUTTON_CLASS"/>
							</div>
						</td></tr>
					</table>
				</table>
			</div>
		</form>
		</div>
	</body>
</html>
