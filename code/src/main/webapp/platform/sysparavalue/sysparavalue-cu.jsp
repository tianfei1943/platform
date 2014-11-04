<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改数据值</title>
		<script type="text/javascript" src="platform/sysparavalue/js/paraTypeCombo.js"></script>
	</head>

	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/sysparavalue/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="id == null || id.isEmpty()">
    					<span>数据值管理 - 添加数据值</span>
					</s:if>
					<s:else>
						<span>数据值管理 - 修改数据值</span>
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
							<div style="float:left; width:100px;">
								<span class="LABEL_NORMAL" style="width: 100px;">数据值名称：</span>
							</div>
							<div style="float:left; width:130px;">
								<input id="value.name" name="value.name" type="text" style="width: 150px" value="${value.name}" ></input>
							</div>
							<div style="float:left; margin:0px 0px 0px 10px;">
								<span class="LABEL_ERROR"><s:fielderror><s:param>value.name</s:param></s:fielderror></span>
							</div>
						</div>
					</td></tr>			
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<div style="float:left; width:100px;">
								<span class="LABEL_NORMAL" style="width: 100px;">数据值：</span>
							</div>
							<div style="float:left; width:130px;">
								<input id="value.value" name="value.value" type="text" style="width: 150px" value="${value.value}" ></input>
							</div>
							<div style="float:left; margin:0px 0px 0px 10px;">
								<span class="LABEL_ERROR"><s:fielderror><s:param>value.value</s:param></s:fielderror></span>
							</div>
						</div>
					</td></tr>							
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<div style="float:left; width:100px;">
								<span class="LABEL_NORMAL" style="width: 100px;">参数类型：</span>
							</div>
							<div style="float:left; width:130px; margin:0px 0px 0px 0px;">
								<input style="width:183px;" id="typeCombo" name="value.type.id"   value="${value.type.id}"></input>
							</div>
							<div style="float:left; margin:0px 0px 0px 10px;">
								<span class="LABEL_ERROR"><s:fielderror><s:param>value.type.id</s:param></s:fielderror></span>
							</div>
						</div>
					</td></tr>	
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<div style="float:left; width:100px;">
								<span class="LABEL_NORMAL" style="width: 100px;">父参数值：</span>
							</div>
							<div style="float:left; width:130px; margin:0px 0px 0px 0px;">
								<input style="width:183px;" id="parentValueCombo" name="parentValueId"   value="${value.parentSysParameterValue.id}"></input>
							</div>
							<div style="float:left; margin:0px 0px 0px 10px;">
								<span class="LABEL_ERROR"><s:fielderror><s:param>parentValueId</s:param></s:fielderror></span>
							</div>
						</div>
					</td></tr>	
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 0px 10px">
							<div style="float:left; width:100px;">
								<span class="LABEL_NORMAL" style="width: 100px;">数据值描述：</span>
							</div>
							<div style="float:left; width:130px;">
								<textarea style="width:200px; height:100px" class="LABEL_NORMAL" name="value.description">${value.description}</textarea>
							</div>
							<div style="float:left; margin:0px 0px 0px 10px;">
								<span class="LABEL_ERROR"><s:fielderror><s:param>value.description</s:param></s:fielderror></span>
							</div>
						</div>
					</td></tr>											
					<tr style="width: 100%"><td>
						<div style="margin:10px 0px 10px 10px">
							<input type="submit" value="&nbsp;提&nbsp;交&nbsp;" class="SUBMIT_BUTTON_CLASS"/>
							<span style="width: 5px;"></span>
							<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysparavalue/sysparavalue.jsp'" class="SUBMIT_BUTTON_CLASS"/>
						</div>
					</td></tr>					
				</table>
			</table>
			</div>
		</form>
		</div>
	</body>
</html>

