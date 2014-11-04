<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>其它资源管理</title>
		<script type="text/javascript" src="platform/sysresource/otherresource/js/sysresource-others.js"></script>
	</head>
	<body id="_right_panel_body">
	  <div id="wrapper">
		<form id="resourceForm" name="resourceForm" method="post"">
			<input type="hidden" id="id" name="id"/>
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>其它资源管理</span></div>
                <div id="_title_operation" class="title_operation">
                    <img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加" onClick="window.location.href='<%=request.getContextPath()%>/platform/sysresource/otherresource/input.action'" >
					<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改" onClick="editInfoType()" >
					<img src="<%=request.getContextPath()%>/images/icon_del.png" alt="删除" onClick="deleteinfoType()" >
                </div>
            </div>
            <div id="_query" class="query_panel">
                <div style="float: left; padding: 10px 5px 5px 10px;">资源类型：</div>
                <div style="float: left; padding: 5px;">
                    <select id="resourceTypeCombo" class="easyui-combobox" name="state" style="width:200px;" required="true">
                        <option value="-1">所有</option>
                        <option value="url">URL</option>
                        <option value="bussinessObjects">业务对象</option>
                        <option value="boProp">业务对象属性</option>
                    </select>
                </div>
                <div style="float: left; padding: 10px 5px 5px 10px;">资源名称：</div>
                <div style="float: left; padding: 5px;">
                    <input type="text" id="resourceName" name="resourceName" value="${resourceName }" style="width: 120px;" />
                </div>
                <div style="float: left; padding: 5px;">
                    <input id="queryButton" name="queryButton" type="button" onclick="reloadgrid();" class="BUTTONCLASS" value="&nbsp查&nbsp询&nbsp">
                </div>
            </div>
			<div id="resourceGrid"></div>
		</form>
		</div>
	</body>
</html>
