<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smf.platform.framework.SystemConfig" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><%=SystemConfig.getValue(SystemConfig.TITLE) %></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <!-- base标签为页面上所有链接规定默认地址或默认目标，链接包括<a>、<img>、<link>、<form>标签中的URL -->
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        
        String mainUrlHref = request.getParameter("urlPost");
        if(null == mainUrlHref){
       	   mainUrlHref = SystemConfig.getValue(SystemConfig.HOMEURL);
        }
        
    %>
    <base href="<%=basePath%>" target="_self"/>
    <link href="platform/frames2/css/frames.css" rel="stylesheet" type="text/css" />
    <link href="platform/frames2/css/superfish.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="platform/frames2/js/listSubMenus.js"></script>	
    <script type="text/javascript" src="platform/frames2/js/superfish.js"></script>
    <script type="text/javascript" src="platform/frames2/js/hoverIntent.js"></script>	
</head>

<body style="overflow:hidden">
	<input id="homeurl" type="hidden" value=<%=SystemConfig.getValue(SystemConfig.HOMEURL) %>></input>
    <div class="top">
        <div class="topTitle" style="background: url('<%=SystemConfig.getValue(SystemConfig.TITLEIMAGE) %>') no-repeat;"> 
        </div>
        <div class="topRight"> 
        </div>
        <div class="topMenu"> 
            <UL id="menuId" class="sf-menu">
            </UL>
        </div>
        <div class="topInfo">
            <div class="logedTime">
                <a id="logedTime"></a>
            </div>
            <div class="userName">
                <div><img style="margin: 5px 5px 0px 0px;" src="platform/frames2/images/icon_user.gif" /></div>
                <div style="margin: 5px 0px 0px 0px;">欢迎您，<span id="userName"></span>！ <a id="logout" href="j_spring_security_logout">退出</a></div>
            </div>
        </div>
    </div>
    
    <div class="main">
        <iframe id = "workArea" frameborder="0" src="<%=mainUrlHref %>"></iframe>
        <form method="post" target="_blank" action="<%=request.getContextPath()%>/platform/frames2/mainframe.jsp" id="form1" name="form1">
        	<input type="hidden" name="urlPost" id="urlPost" value="">
        </form>
    </div>

    <div class="bottom">
        <div class="content">
            <span>Copyright © 2012-2015 <%=SystemConfig.getValue(SystemConfig.COMPANYNAME) %> All rights reserved</span>
        </div>
    </div>
</body>
</html>