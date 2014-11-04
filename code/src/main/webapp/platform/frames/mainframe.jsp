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
		%>
		<base href="<%=basePath%>" target="_self"/>
		<link href="platform/frames/css/mainframe.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="platform/frames/js/listSubMenus.js"></script>		
	</head>
<body>
	<input id="homeurl" type="hidden" value=<%=SystemConfig.getValue(SystemConfig.HOMEURL) %>></input>
	<div class="header">
		<div class="topTitle" style="background: url('<%=SystemConfig.getValue(SystemConfig.TITLEIMAGE) %>') no-repeat;"> 
        </div>
        <div class="topInfo">
            <div class="logedTime">
                <a id="logedTime"></a>
            </div>
            <div class="userName">
                <img src="platform/frames2/images/icon_user.gif" />欢迎您，<span id="userName"></span>！ <a id="logout" href="j_spring_security_logout">退出</a>
            </div>
        </div>
        <div class="hCenter"> 
			<UL class=tabs>
  			</UL>
   		</div>
   </div>   
   
   <div class="tab_content">
   		<div id="left" class="left">
        </div>
        
       	<div id="split" class="split">
           	<div class="split_button"></div>
        </div>        

       <div id="mianRight" class="mianRight">
          <iframe id = "mianRightframe"  frameborder="0" src="#"></iframe>
       </div>
   </div>
   
   <div class="footer">Copyright © 2012-2015 <%=SystemConfig.getValue(SystemConfig.COMPANYNAME) %> All rights reserved</div>
</body>
</html>