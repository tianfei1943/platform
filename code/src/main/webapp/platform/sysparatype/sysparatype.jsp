<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.*"
	import="java.text.SimpleDateFormat"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>数据类型管理</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/platform/sysparatype/js/sysparatype.js"></script>
	</head>	
	<body id="_right_panel_body">
	  <div id="wrapper">
		<form id="entityForm" name="entityForm" method="post"">
			<input type="hidden" id="id" name="id"/>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>数据类型管理</span></div>
                <div id="_title_operation" class="title_operation">
                    <img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加" onClick="window.location.href='<%=request.getContextPath()%>/platform/sysparatype/input.action'" >
					<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改" onClick="editInfoType()" >
					<img src="<%=request.getContextPath()%>/images/icon_del.png" alt="删除" onClick="deleteinfoType()">
                </div>
            </div>
            <div id="_query" class="query_panel">
                <div style="float: left; padding: 10px 5px 5px 10px;">类型名称：</div>
                <div style="float: left; padding: 5px;">
                   <input type="text" id="parameterType" name="parameterType" value="${parameterType}" style="width: 120px; height: 17px;" />
                </div>
                <div style="float: left; padding: 5px;">
                    <input id="queryButton" name="queryButton" type="button" onclick="reloadgrid();" class="BUTTONCLASS" value="&nbsp查&nbsp询&nbsp">
                </div>
            </div>
			<div id="sysParameterTypeGrid"></div>
		</form>
		</div>
	</body>		
</html>
