<%@ page language="java" import="java.util.*"
	import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>组织机构管理</title>
		<script type="text/javascript" src="platform/sysgroup/js/sysgroup.js"></script>
	</head>

	<body id="_right_panel_body">
	  <div id="wrapper">
		<form id="sysGroupForm" name="sysGroupForm" method="post">
			<input type="hidden" id="id" name="id" />
			<input id="userParentGroupId" type="hidden" value="${userParentGroupId}" />
			<input id="groupParentId" type="hidden" value="${groupParentId}" />		
            <div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>组织机构管理</span></div>
                <div id="_title_operation" class="title_operation">
                    <img src="<%=request.getContextPath()%>/images/icon_add.png" alt="添加组织机构" onclick="window.location.href='<%=request.getContextPath()%>/platform/sysgroup/input.action'" >
					<img src="<%=request.getContextPath()%>/images/icon_edit.png" alt="修改组织机构" onclick="editGroup()" >
					<img src="<%=request.getContextPath()%>/images/icon_del.png" alt="删除用户或组织机构"	onclick="deleteinfoType()" >
                </div>
            </div>
			<div style="float:left; left: 0px; right: 0px; top:40px; bottom: 0px; position: absolute;*+position: fixed; width: auto; height: auto;*+height: 700px; overflow-y:scroll; border:1px solid #99BBE8;">
				<ul id="sysGroupTree" class="easyui-tree" animate="true" dnd="true"></ul>
			</div>
		</form>
		</div>
	</body>
</html>

