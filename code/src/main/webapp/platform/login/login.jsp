<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" errorPage="/platform/common/500.jsp"%>

<%@ page import="com.smf.platform.framework.SystemConfig" %>

<html>
	<head>
		<%@include file="/templates/include.jsp"%>
		<title><%=SystemConfig.getValue(SystemConfig.TITLE) %></title>
<!--		<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>-->
		<script type="text/javascript">		 
			if (window.top!=window.self) 
			{
				if (window.ActiveXObject) {
					window.top.location= "login.jsp";
				} else {
					window.top.location= "platform/login/login.jsp";
				}
			}
		
			function enterEvent(event){
			   if(event.keyCode == 13){
			      submitEvent();
			   }
			}
			
			function submitEvent(){
			       //loginForm.j_password.value = hex_md5(loginForm.j_password.value);
                   //alert(loginForm.j_password.value);
				   loginForm.submit();
			}
		</script>
		<style type="text/css">
			<!--
			body {
				margin:0px;
				background: url(platform/images/login_bg.jpg) center repeat-y #13426e;
			}
			.centent{left:50%;margin-left:-445px;
				margin-top:-90px;position:absolute;
				top:40%;text-align:center;}
			.middle{
				height:246px;
				width:891px; 
				color:#fff;
				background:url(<%=SystemConfig.getValue(SystemConfig.LOGINIMAGEBG) %>) no-repeat;
				padding-top:50px;
			}
			.title{ padding:10px 0px;}
			.footer{ color:#FFFFFF; padding:10px 0px}
			-->
		</style>
	</head>
	<body>
		<input type="hidden" value="ajaxSessionFlag">
		<form id="loginForm" action="j_spring_security_check" method="post" onKeyPress="enterEvent(event)" style="height:100%">
        <div class="centent">
            <div class="title"> <img src="<%=SystemConfig.getValue(SystemConfig.LOGINIMAGE) %>"></div>
		  <div class="middle">
<div style="margin:20px 20px 5px 30px; text-align:left; padding-left:550px; ">
					<label title="用户：" class="LABEL_NORMAL">用户：</label>&nbsp;&nbsp;
					<input id="j_username" name="j_username" type="text" value="${sysUser.userCode}" style="width: 200px; height:22px;"/>
				</div>
				<div style="margin:10px 20px 5px 30px; text-align:left; padding-left:550px;">
					<label title="密码：" class="LABEL_NORMAL">密码：</label>&nbsp;&nbsp;
					<input id="j_password" name="j_password" type="password" value="${sysUser.password}" style="width: 200px; height:22px;"/>
				</div>
				<div style="margin:10px 20px 5px 30px; text-align:left; padding-left:550px;">
					<%
						if(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null){
					%>		
							<label title="用户：" class="LABEL_ERROR">登录失败，请重试.</label>
					<%
								} else {
					%>
							<label title="用户：" class="LABEL_ERROR"></label>
					<%
								}
					%>
					
				</div>
				<div style="margin:10px 25px 5px 30px;  padding-left:550px;">
					<input type="button" src="platform/images/button_ok.gif" class="BUTTONCLASS" onClick="submitEvent()" style="width:80px; height:25px" value="登录"></input>
					<label id="nameMessage" style="color: red;"></label>
				</div>
			</div>
            <div class="footer">Copyright © 2012-2015 <%=SystemConfig.getValue(SystemConfig.COMPANYNAME) %> All rights reserved</div>
        </div>
	</form>
	</body>
</html>