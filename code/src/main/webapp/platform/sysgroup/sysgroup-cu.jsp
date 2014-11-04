<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改组织机构</title>	
		<script type="text/javascript" src="platform/sysgroup/js/sysgroupanduser.js"></script>
	</head>
	<body class="body_rightPanel" >
		<div id="wrapper" >
		<form  id="roleForm" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<input type="hidden" id="userIds" name="userIds" value="${userIds}" />
			<input type="hidden" id="parentIds" name="parentIds" value="${parentIds}" />
			<div id="div_createorupdate">
                <div id="_title" class="content_title">
                    <div id="_title_label" class="title_label">
                        <s:if test="id == null || id.isEmpty()">
                            <span>组织机构管理 - 添加组织机构</span>
                        </s:if>
                        <s:else>
                            <span>组织机构管理 - 修改组织机构</span>
                        </s:else>									
                    </div>
                </div>
				<table>
					<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
						<tr>
							<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
						</tr>
					</table>
					<table style="width:100%; border:0; cellpadding:0; cellspacing:0">
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">组织机构编码：</span>
								<s:if test="id == null || id.isEmpty()">
									<input type="text" id="sysGroup.groupCode" name="sysGroup.groupCode" style="width: 150px;" value="${sysGroup.groupCode}"/>
								</s:if>
								<s:else>
									<input type="text" id="sysGroup.groupCode" name="sysGroup.groupCode" style="width: 150px;" value="${sysGroup.groupCode}" readOnly/>
								</s:else>
								<span class="LABEL_ERROR"><s:fielderror><s:param>sysGroup.groupCode</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">组织机构名称：</span>
								<input id="sysGroup.groupName" name="sysGroup.groupName" type="text" style="width: 150px; height:20px;" value="${sysGroup.groupName}" />
								<span class="LABEL_ERROR"><s:fielderror><s:param>sysGroup.groupName</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 10px 10px">
								<input type="button" value="&nbsp;提&nbsp;交&nbsp;" onClick="editInfoType()" class="SUBMIT_BUTTON_CLASS">
								<span style="width: 5px;"></span>
								<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysgroup/sysgroup.jsp'" class="SUBMIT_BUTTON_CLASS">
							</div>
						</td></tr>
					</table>
				</table>
						<div id="tabContents" class="easyui-tabs" tools="#tab-tools" style="width:850px;height:320px;">
							<div title="用户" iconCls="icon-reload" closable="true" style="padding:5px;">
								<table id="userGrid" style="width: 98%; height:auto;"></table>
							</div>
							<div title="组织机构" iconCls="icon-reload" closable="true" style="padding:5px;">
<%--							  <div style="float:left; width:670px;  height:220px; overflow-y:scroll; border:1px solid;">--%>
								<ul id="sysGroupTree" class="easyui-tree" animate="true" dnd="true"></ul><%--</div>--%>
							</div>
							</div>			
			</div>
		</form>
		<br />
		</div>
	</body>
</html>
