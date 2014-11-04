<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改业务对象权限</title>
		<script type="text/javascript" src="platform/sysprivilege/boprivilege/js/boprivilege-resource.js"></script>
	</head>
	<body class="body_rightPanel">
		<div id="wrapper" >
		<form id="privilegeForm" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<input id="sysResourceId" type="hidden" name="sysResourceId" value=${sysResourceId}></input>
			
			<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
					<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
						<table width="100%">
							<tr><td>
								<s:if test="id == null || id.isEmpty()">
									<div class="LABEL_TITLE">&nbsp;&nbsp;业务对象权限管理 - 添加业务对象权限</div>
								</s:if>
								<s:else>
									<div class="LABEL_TITLE">&nbsp;&nbsp;业务对象权限管理 - 修改业务对象权限</div>
								</s:else>									
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
					<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg">
					</td>
				</tr>
			</table>
			<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">权限编码：</span>
						</div>
						<div style="float:left; width:150px;">
							<s:if test="id == null || id.isEmpty()">
								<input type="text" name="privilege.privilegeCode" style="width: 150px" value="${privilege.privilegeCode}"></input>
							</s:if>
							<s:else>
								<input type="text" name="privilege.privilegeCode" style="width: 150px" value="${privilege.privilegeCode}" readOnly></input>
							</s:else>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>privilege.privilegeCode</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>			
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">权限名称：</span>
						</div>
						<div style="float:left; width:150px;">
							<input type="text" name="privilege.privilegeName" style="width: 150px" value="${privilege.privilegeName}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>privilege.privilegeName</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">操作类别：</span>
						</div>
						<div style="float:left; width:150px;">
							<input type="text" name="privilege.opClass" style="width: 150px" value="${privilege.opClass}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>privilege.opClass</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">操作方法：</span>
						</div>
						<div style="float:left; width:150px;">
							<input type="text" name="privilege.opMethod" style="width: 150px" value="${privilege.opMethod}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>privilege.opMethod</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">约束条件：</span>
						</div>
						<div style="float:left; width:150px;">
							<input type="text" name="privilege.valueConstraint" style="width: 150px" value="${privilege.valueConstraint}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>privilege.valueConstraint</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">记录日志：</span>
						</div>
						<div class="LABEL_NORMAL" style="float:left; width:100px;">
							<input style="background: #f3f9FF; width: 15px; border: 0; height: 15px;" type="radio" name="privilege.isLog" value=Boolean.TRUE/>是
							<input style="background: #f3f9FF; width: 15px; border: 0; height: 15px;" type="radio" name="privilege.isLog" value=Boolean.FALSE checked/>否
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">选择资源：</span>
						</div>
						<table id="urlprivilegeGrid" style="width: 100%; height:auto;"></table>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 10px 10px">
						<input type="button" value="&nbsp;提&nbsp;交&nbsp;" class="SUBMIT_BUTTON_CLASS" onclick="editInfoType();"/>
						<span style="width: 5px;"></span>
						<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysprivilege/boprivilege/boprivilege.jsp'" class="SUBMIT_BUTTON_CLASS"/>
					</div>
				</td></tr>
			</table>
		</form>
		</div>
	</body>
</html>


