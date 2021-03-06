<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>菜单资源管理</title>
		<script type="text/javascript" src="platform/sysresource/menu/js/sysresource-menu.js"></script>
	</head>

	<body id="_right_panel_body">
	  <div id="wrapper">
		<form id="menusForm" name="menusForm" method="post">
			<input type="hidden" id="id" name="id" />
            <div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>菜单资源管理</span></div>
                <div id="_title_operation" class="title_operation">
                    <img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加" onclick="window.location.href='<%=request.getContextPath()%>/platform/sysresource/menu/input.action'" >
    				<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改" onClick="editInfoType()" >
    				<img src="<%=request.getContextPath()%>/images/icon_del.png" alt="删除" onClick="deleteinfoType()" >
                </div>
            </div>
            <div id="_query" class="query_panel">
                <div style="float: left; padding: 10px 5px 5px 10px;">资源名称：</div>
                <div style="float: left; padding: 5px;">
                    <input type="text" id="resourceName" name="resourceName" value="${resourceName }" style="width: 120px;" />
                </div>
                <div style="float: left; padding: 5px;">
                    <input id="resource" name="resource" type="button" class="BUTTONCLASS" onclick="reloadgrid();" value="&nbsp查&nbsp询&nbsp">
                </div>
            </div>
            <div id="resourceGrid"></div>
		</form>
		</div>
	</body>
</html>
