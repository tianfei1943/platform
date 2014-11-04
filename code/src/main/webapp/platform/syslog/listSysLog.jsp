<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.text.SimpleDateFormat" import="com.smf.platform.util.PlatFormDateUtil" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>

<html>
	<head>
		<title>日志浏览</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="platform/syslog/js/listSysLog.js"></script>
	</head>

	<body id="_right_panel_body" class="body_rightPanel">
	  <div id="wrapper">
		<form id="userFrom" name="userFrom" method="post">
            <div id="_title" class="content_title">
                <div id="_title_label" class="title_label"><span>日志浏览</span></div>
                <div id="_title_operation" class="title_operation">
                </div>
            </div>
            <div id="_query" class="query_panel">
                <div style="float: left; padding: 10px 5px 5px 10px;">远程地址：</div>
                <div style="float: left; padding: 5px;">
                    <input type="text" id="remoteAddress" name="remoteAddress" value="${remoteAddress }" style="width: 120px;"/>
                </div>
                <div style="float: left; padding: 10px 5px 5px 10px;">开始日期：</div>
                <div style="float: left; padding: 5px;">
                    <input id="startTime" name="startTime" type="text" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(PlatFormDateUtil.getCurrentDate())%>"
                            onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"	style="width: 140px;" />
                </div>
                <div style="float: left; padding: 10px 5px 5px 10px;">结束日期：</div>
                <div style="float: left; padding: 5px;">
                    <input id="endTime" name="endTime" type="text" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(PlatFormDateUtil.getCurrentDate())%>"
                            onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"	style="width: 140px;" />
                </div>
                <div style="float: left; padding: 5px;">
                    <input id="queryBtn" type="button" class="BUTTONCLASS" onclick="reloadgrid()" value="&nbsp;查&nbsp;询&nbsp;" />
                </div>
            </div>
			<div id="userLogGrid"></div>
		</form>
		</div>
	</body>
</html>
