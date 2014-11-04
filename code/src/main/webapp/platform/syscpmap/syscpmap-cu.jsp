<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改CP名称映射</title>
	</head>

	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/syscpmap/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<div id="div_createorupdate">
			<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
					<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
						<table width="100%">
							<tr><td>
								<s:if test="id == null || id.isEmpty()">
									<div class="LABEL_TITLE">&nbsp;&nbsp;CP名称映射管理 - 添加CP名称映射</div>
								</s:if>
								<s:else>
									<div class="LABEL_TITLE">&nbsp;&nbsp;CP名称映射管理 - 修改CP名称映射</div>
								</s:else>									
							</td></tr>
						</table>
					</td>
					<td width="12" height="22" align="right" background="<%=request.getContextPath()%>/platform/images/top_right.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
				</tr>
			</table>
			<table>
				<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
					<tr>
						<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
					</tr>
				</table>
				<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<span class="LABEL_NORMAL">&nbsp;&nbsp;&nbsp;&nbsp;CP名称：</span>
							<s:if test="id == null || id.isEmpty()">
								<input type="text" id="type.cpname" name="type.cpname"
										style="width: 150px;" value="${type.cpname}"/>
							</s:if>
							<s:else>
									<input type="text" id="type.cpname" name="type.cpname"
										style="width: 150px;" value="${type.cpname}" readOnly/>
							</s:else>
							<span class="LABEL_ERROR"><s:fielderror><s:param>type.cpname</s:param></s:fielderror></span>
						</div>
					</td></tr>
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<span class="LABEL_NORMAL">电信CP代码：</span>
							<input id="type.telecomCpcode" name="type.telecomCpcode" type="text" style="width: 150px" value="${type.telecomCpcode}" ></input>
						</div>
					</td></tr>
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<span class="LABEL_NORMAL">联通CP代码：</span>
							<input id="type.unicomCpcode" name="type.unicomCpcode" type="text" style="width: 150px" value="${type.unicomCpcode}" ></input>
						</div>
					</td></tr>
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<span class="LABEL_NORMAL">移动CP代码：</span>
							<input id="type.cmcccpcode" name="type.cmcccpcode" type="text" style="width: 150px" value="${type.cmcccpcode}" ></input>
						</div>
					</td></tr>
					
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 10px 10px">
							<input type="submit" value="&nbsp;提&nbsp;交&nbsp;" class="SUBMIT_BUTTON_CLASS"/>
							<span style="width: 5px;"></span>
							<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/syscpmap/syscpmap.jsp'" class="SUBMIT_BUTTON_CLASS"/>
						</div>
					</td></tr>
				</table>
			</table>
			</div>
		</form>
		</div>
	</body>
</html>


