<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>用户管理</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/platform/sysuser/js/sysuser.js"></script>
	</head>

	<body id="_right_panel_body">
        <div id="wrapper">
			<form id="entityForm" name="entityForm" method="post">
				<input type="hidden" id="id" name="id" />
				<div id="_title" class="content_title">
                    <div id="_title_label" class="title_label"><span>用户管理</span></div>
                    <div id="_title_operation" class="title_operation">
    					<img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加" onClick="window.location.href='<%=request.getContextPath()%>/platform/sysuser/input.action'" >
    					<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改" onClick="editInfoType()" >
    					<img src="<%=request.getContextPath()%>/images/icon_inactive.png" alt="停用" onClick="deleteinfoType(1)" >
    					<img src="<%=request.getContextPath()%>/images/icon_active.png"	alt="启用" onClick="deleteinfoType(2)" >
    				</div>
                </div>
				<div id="_query" class="query_panel">
					<div style="float: left; padding: 10px 5px 5px 10px;">用户姓名：</div>
					<div style="float: left; padding: 5px;">
						<input type="text" id="userName" name="userName" value="${userName}" style="width: 120px;" />
					</div>
					<div style="float: left; padding: 5px;">
						<input id="query" name="query" type="button" class="BUTTONCLASS" onclick="reloadgrid()" value="&nbsp查&nbsp询&nbsp">
					</div>
				</div>                
				<div id="userGrid"></div>                
			</form>
        </div>
	</body>
</html>
