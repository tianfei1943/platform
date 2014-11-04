<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改URL权限</title>
		<script type="text/javascript" src="platform/sysprivilege/urlprivilege/js/urlprivilege-resource.js"></script>
	</head>
	
	<body class="body_rightPanel">
		<div id="wrapper" >
		<form id="privilegeForm" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<input id="sysResourceId" type="hidden" name="sysResourceId" value=${sysResourceId}></input>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="id == null || id.isEmpty()">
    					<span>URL权限管理 - 添加URL权限</span>
					</s:if>
					<s:else>
						<span>URL权限管理 - 修改URL权限</span>
					</s:else>									
                </div>
            </div>

			<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
				<tr><td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td></tr>
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
							<span class="LABEL_NORMAL">记录日志：</span>
						</div>
						<div class="LABEL_NORMAL" style="float:left;">
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
						<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysprivilege/urlprivilege/urlprivilege.jsp'" class="SUBMIT_BUTTON_CLASS"/>
					</div>
				</td></tr>
			</table>
		</form>
		</div>
	</body>
</html>


