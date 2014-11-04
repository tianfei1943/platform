<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<html>
	<head>
		<title>添加/修改其它资源</title>
		<%@ include file="/templates/include.jsp"%>

		<script type="text/javascript" src="platform/sysresource/otherresource/js/resourcetype-combo.js"></script>
	</head>
	<body>
		<div id="wrapper" >
		<form action="platform/sysresource/otherresource/save.action" id="otherresourceForm" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="id == null || id.isEmpty()">
                        <span>其它资源管理 - 添加其它资源</span>
                    </s:if>
                    <s:else>
                        <span>其它资源管理 - 修改其它资源</span>
                    </s:else>									
                </div>
            </div>
			<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
				<tr>
					<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
				</tr>
			</table>
			<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">资源类型：</span>
						</div>
						<div style="float:left; width:150px;">
							<input id="resourceTypeCombo" name="sysResource.resourceType" style="width: 113px;" value="${sysResource.resourceType}" />
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.resourceType</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">资源名称：</span>
						</div>
						<div style="float:left; width:150px;">
							<input type="text" name="sysResource.resourceName" value="${sysResource.resourceName}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.resourceName</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">资源路径：</span>
						</div>
						<div style="float:left; width:450px;">
							<input type="text" name="sysResource.resourceUrl" value="${sysResource.resourceUrl}" style="width: 400px"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.resourceUrl</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>	
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL">资源描述：</span>
						</div>
						<div style="float:left; width:450px;">
							<input type="text" name="sysResource.desciption" style="width: 400px; height: 20px" value="${sysResource.desciption}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.desciption</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>				
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 10px 10px">
						<input type="button" value="&nbsp;提&nbsp;交&nbsp;" onclick="otherresourceForm.submit();" class="SUBMIT_BUTTON_CLASS">
						<span style="width: 5px;"></span>
						<input type="button" value="&nbsp;返&nbsp;回&nbsp;"	class="SUBMIT_BUTTON_CLASS"	
								onclick="window.location.href= '<%=request.getContextPath()%>/platform/sysresource/otherresource/sysresource-others.jsp'" />
					</div>
				</td></tr>
			</table>
		</form>
		</div>
	</body>
</html>


