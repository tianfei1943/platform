<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>角色详情</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tab.css" />	
		<script type="text/javascript" src="platform/sysrole/js/sysrole-cu.js"></script>
	</head>
	<body class="body_rightPanel">
		<div id="wrapper" >
		<form id="roleForm" method="post">
			<s:if test="#{selectedPrivileges == null || selectedPrivileges.isEmpty}">
				<input id="selectedPrivileges" type="hidden" name="selectedPrivileges" value="-1"></input>
			</s:if>
			<s:else>
				<input id="selectedPrivileges" type="hidden" name="selectedPrivileges" value="${selectedPrivileges}"></input>
			</s:else>
			<s:if test="#{selectedUsers == null || selectedUsers.isEmpty}">
				<input id="selectedUsers" type="hidden" name="selectedUsers" value="-1"></input>
			</s:if>
			<s:else>
				<input id="selectedUsers" type="hidden" name="selectedUsers" value="${selectedUsers}"></input>
			</s:else>
			<s:if test="#{selectedGroups == null || selectedGroups.isEmpty}">
				<input id="selectedGroups" type="hidden" name="selectedGroups" value="-1"></input>
			</s:if>
			<s:else>
				<input id="selectedGroups" type="hidden" name="selectedGroups" value="${selectedGroups}"></input>
			</s:else>

			<input type="hidden" name="roleId" value="${roleId}"></input>
			<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
					<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
						<table width="100%">
							<tr><td>
								<div class="LABEL_TITLE">&nbsp;&nbsp;角色管理 - 角色详情</div>
							</td></tr>
						</table>
					</td>
					<td width="12" height="22" align="right" background="<%=request.getContextPath()%>/platform/images/top_right.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
				</tr>
			</table>
			<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
				<tr>
					<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
				</tr>
			</table>
			<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<span class="LABEL_NORMAL">角色编码：</span>
						<s:if test="#{roleId == null || roleId.isEmpty }">
							<input type="text" name=role.roleCode value="${role.roleCode}"></input>
						</s:if>
						<s:else>
							<input type="text" name="role.roleCode" value="${role.roleCode}" readOnly></input>
						</s:else>
						<span class="LABEL_ERROR"><s:fielderror><s:param>role.roleCode</s:param></s:fielderror></span>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<span class="LABEL_NORMAL">角色名称：</span>
						<input type="text" name="role.roleName" value="${role.roleName}"></input>
						<span class="LABEL_ERROR"><s:fielderror><s:param>role.roleName</s:param></s:fielderror></span>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 10px 10px">
						<input type="button" value="&nbsp;返&nbsp;回&nbsp;" 
								onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysrole/sysrole.jsp'" class="SUBMIT_BUTTON_CLASS">
					</div>
				</td></tr>
			</table>

			<div id="tabContents" class="easyui-tabs" tools="#tab-tools" style="width:700px;height:250px;">
				<div title="用户" iconCls="icon-reload" closable="true" style="padding:5px;">
					<table id="userGrid" style="width: 98%; height:auto;"></table>
				</div>
				<div title="权限" iconCls="icon-reload" closable="true" style="padding:5px;">
					<ul id="urlprivilegeGrid" class="easyui-tree" animate="true" dnd="true"></ul>
				</div>
				<div title="组织机构" iconCls="icon-reload" closable="true" style="padding:5px;">
					<ul id="sysGroupTree" class="easyui-tree" animate="true" dnd="true"></ul>
				</div>				
			</div>	
		</form>
		</div>
	</body>
</html>


