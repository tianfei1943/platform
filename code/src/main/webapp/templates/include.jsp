<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" errorPage="/platform/common/500.jsp"%>
<!-- base标签为页面上所有链接规定默认地址或默认目标，链接包括<a>、<img>、<link>、<form>标签中的URL -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<base href="<%=basePath%>" target="_self"/>

<%@include file="meta.jsp" %>
<%@include file="css.jsp" %>
<%@include file="js.jsp" %>
<script type='text/javascript'>
  	var contextPath = "${pageContext.request.contextPath}";
  	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
  	$.blockUI.defaults.css = {
  			padding:	10,
			margin:		10,
			width:		'20%',
			top:		'40%',
			left:		'35%',
			textAlign:	'center',
			color:		'#000',
			border:		'2px solid #6593cf',
			backgroundColor:'#fff',
			cursor:		'wait'
	};
  	$.blockUI.defaults.message="正在处理，请等待......";
</script>