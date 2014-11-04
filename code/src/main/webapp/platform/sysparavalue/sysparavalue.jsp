<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.*"
	import="java.text.SimpleDateFormat"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>数据类型管理</title>
		<script type="text/javascript" src="platform/sysparavalue/js/allParaTypeCombo.js"></script>		
		<script type="text/javascript" src="platform/sysparavalue/js/sysparavalue.js"></script>
	</head>	
	<body id="_right_panel_body">
	  <div id="wrapper">
		<form id="entityForm" name="entityForm" method="post"">
			<input type="hidden" id="id" name="id"/>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>数据值管理</span></div>
                <div id="_title_operation" class="title_operation">
                    <img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加" onClick="window.location.href='<%=request.getContextPath()%>/platform/sysparavalue/input.action'" >
					<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改" onClick="editInfoType()" >
					<img src="<%=request.getContextPath()%>/images/icon_del.png" alt="删除" onClick="deleteinfoType()" >
                </div>
            </div>
            <div id="_query" class="query_panel">
                <div style="float: left; padding: 10px 5px 5px 10px;">参数类型：</div>
                <div style="float: left; padding: 5px;">
                   <input style="width: 200px" id="typeCombo" name="dept"	value="${parameterCode}"></input>
                </div>
                <div style="float: left; padding: 5px;">
                    <input id="queryButton" name="queryButton" type="button" onclick="reloadgrid();" class="BUTTONCLASS" value="&nbsp查&nbsp询&nbsp">
                </div>
            </div>
			<div id="sysparavalueGrid"></div>
		</form>
		</div>
	</body>		
</html>