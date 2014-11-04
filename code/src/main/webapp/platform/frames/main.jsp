<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<html>
	<head>
		<title>开发平台说明</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- base标签为页面上所有链接规定默认地址或默认目标，链接包括<a>、<img>、<link>、<form>标签中的URL -->
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		%>
		<base href="<%=basePath%>" target="_self"/>		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/smf.css" />		
				
<STYLE>
TABLE {
	FONT: 100% Arial, Helvetica, sans-serif
}

TD {
	FONT: 100% Arial, Helvetica, sans-serif
}
body,td,a,p,.h {
	font-family: arial, sans-serif;
	font-size: 13px;
}
TABLE {
	MARGIN: 20px 0px 0px 20px;
	WIDTH: 80%;
	BORDER-COLLAPSE: collapse
}

TH {
	BORDER-RIGHT: #fff 1px solid;
	PADDING-RIGHT: 0.5em;
	BORDER-TOP: #fff 1px solid;
	PADDING-LEFT: 0.5em;
	PADDING-BOTTOM: 0.5em;
	BORDER-LEFT: #fff 1px solid;
	PADDING-TOP: 0.5em;
	BORDER-BOTTOM: #fff 1px solid;
	TEXT-ALIGN: left
}

TD {
	BORDER-RIGHT: #fff 1px solid;
	PADDING-RIGHT: 0.5em;
	BORDER-TOP: #fff 1px solid;
	PADDING-LEFT: 0.5em;
	PADDING-BOTTOM: 0.5em;
	BORDER-LEFT: #fff 1px solid;
	PADDING-TOP: 0.5em;
	BORDER-BOTTOM: #fff 1px solid;
	TEXT-ALIGN: left
}

TH {
	BACKGROUND: #328aa4 repeat-x;
	COLOR: #fff
}

TD {
	BACKGROUND: #e5f1f4
}

TR.even TD {
	BACKGROUND: #e5f1f4
}

TR.odd TD {
	BACKGROUND: #f8fbfc
}

TH.over {
	BACKGROUND: #4a98af
}

TR.even TH.over {
	BACKGROUND: #4a98af
}

TR.odd TH.over {
	BACKGROUND: #4a98af
}

TH.down {
	BACKGROUND: #bce774
}

TR.even TH.down {
	BACKGROUND: #bce774
}

TR.odd TH.down {
	BACKGROUND: #bce774
}

TH.selected {
	
}

TR.even TH.selected {
	
}

TR.odd TH.selected {
	
}

TD.over {
	BACKGROUND: #ecfbd4
}

TR.even TD.over {
	BACKGROUND: #ecfbd4
}

TR.odd TD.over {
	BACKGROUND: #ecfbd4
}

TD.down {
	BACKGROUND: #bce774;
	COLOR: #fff
}

TR.even TD.down {
	BACKGROUND: #bce774;
	COLOR: #fff
}

TR.odd TD.down {
	BACKGROUND: #bce774;
	COLOR: #fff
}

TD.selected {
	BACKGROUND: #bce774;
	COLOR: #555
}

TR.even TD.selected {
	BACKGROUND: #bce774;
	COLOR: #555
}

TR.odd TD.selected {
	BACKGROUND: #bce774;
	COLOR: #555
}

TD.empty {
	BACKGROUND: #fff
}

TR.odd TD.empty {
	BACKGROUND: #fff
}

TR.even TD.empty {
	BACKGROUND: #fff
}

BODY {
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	BACKGROUND: #fff;
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	FONT: 90%/ 180% Arial, Helvetica, sans-serif;
	COLOR: #555;
	PADDING-TOP: 0px;
	TEXT-ALIGN: left;
}



H1 {
	FONT-WEIGHT: normal;
	FONT-SIZE: 220%;
	MARGIN: 0px 20px;
	COLOR: #8bb544;
	LINE-HEIGHT: 1em;
	PADDING-TOP: 1em;
	FONT-FAMILY: Tahoma, Trebuchet MS, Arial, Helvetica, sans-serif
}

H2 {
	FONT-WEIGHT: normal;
	FONT-SIZE: 170%;
	COLOR: #8bb544;
	FONT-FAMILY: Tahoma, Trebuchet MS, Arial, Helvetica, sans-serif
}

P.info {
	FONT-SIZE: 90%;
	MARGIN: 0px 20px;
	COLOR: #999
}

P.floater {
	FONT-SIZE: 120%;
	RIGHT: 20px;
	FLOAT: left;
	MARGIN: 0px;
	LINE-HEIGHT: 2em;
	FONT-FAMILY: Tahoma, Trebuchet MS, Arial, Helvetica, sans-serif; 宋体;
	POSITION: absolute;
	TOP: 2em;
	HEIGHT: 2em
}

P.floater A {
	PADDING-RIGHT: 20px;
	PADDING-LEFT: 20px;
	BACKGROUND: #8bb544;
	FLOAT: left;
	PADDING-BOTTOM: 0px;
	COLOR: #fff;
	LINE-HEIGHT: 2em;
	PADDING-TOP: 0px;
	HEIGHT: 2em
}

P.floater A:hover {
	BACKGROUND: #4a98af;
	COLOR: #fff
}

CODE {
	FONT-SIZE: 110%
}

TABLE {
	FONT-SIZE: 88%
}

#container {
	BACKGROUND: #fff;
	PADDING-BOTTOM: 10px;
	MARGIN: 1.2em auto 0px;
	WIDTH: 750px;
	POSITION: relative;
	TEXT-ALIGN: left
}

#content {
	MARGIN: 0px 10px
}
</STYLE>
		<META content="MSHTML 6.00.2900.3395" name=GENERATOR>
	</head>

	<body style="margin: 0px; padding: 0px;">
		<TABLE cellSpacing="0" cellPadding="0" width="500">
			<TR><TD><span style="font-size:15px; font-weight:bold;">欢迎使用通信管理平台。您现在使用的系统包括以下功能：</span></TD></TR>
		<TR><TD><a href="download/songinfo.xls">导入模板下载</a></TD></TR>
		</table>
	</body>
</html>


