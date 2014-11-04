<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改数据类型</title>
	</head>

	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/sysparatype/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="id == null || id.isEmpty()">
    					<span>数据类型管理 - 添加数据类型</span>
					</s:if>
					<s:else>
						<span>数据类型管理 - 修改数据类型</span>
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
							<span class="LABEL_NORMAL">类型编码：</span>
							<s:if test="id == null || id.isEmpty()">
								<input type="text" id="type.parameterCode" name="type.parameterCode"
										style="width: 280px;" value="${type.parameterCode}"/>
							</s:if>
							<s:else>
									<input type="text" id="type.parameterCode" name="type.parameterCode"
										style="width: 280px;" value="${type.parameterCode}" readOnly/>
							</s:else>
							<span class="LABEL_ERROR"><s:fielderror><s:param>type.parameterCode</s:param></s:fielderror></span>
						</div>
					</td></tr>
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<span class="LABEL_NORMAL">类型名称：</span>
							<input id="type.parameterType" name="type.parameterType" type="text" style="width: 280px" value="${type.parameterType}" ></input>
							<span class="LABEL_ERROR"><s:fielderror><s:param>type.parameterType</s:param></s:fielderror></span>
						</div>
					</td></tr>
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 10px 10px">
							<input type="submit" value="&nbsp;提&nbsp;交&nbsp;" class="SUBMIT_BUTTON_CLASS"/>
							<span style="width: 5px;"></span>
							<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysparatype/sysparatype.jsp'" class="SUBMIT_BUTTON_CLASS"/>
						</div>
					</td></tr>
				</table>
			</table>
			</div>
		</form>
		</div>
	</body>
</html>


