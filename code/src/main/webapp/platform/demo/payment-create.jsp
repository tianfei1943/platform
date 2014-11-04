<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改报销信息</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/platform/demo/js/payment-win.js"></script>
		<script type="text/javascript">		 
			function submitEvent(){
				$("#comment").attr("value",$("#comment2").val());
	            cuForm.submit();
			}
		</script>
	</head>

	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/demo/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
            <input type="hidden" name="processId" value="${processId}"></input>
            <input type="hidden" name="comment" id="comment" value=""></input>
			<div id="div_createorupdate">
				<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
							<div style="height: 22px; width: 10px"></div>
						</td>
						<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
							<table width="100%">
								<tr><td>
									<div class="LABEL_TITLE">&nbsp;&nbsp;报销管理 - 添加报销信息</div>
								</td></tr>
							</table>
						</td>
						<td width="12" height="22" align="right" background="<%=request.getContextPath()%>/platform/images/top_right.jpg">
							<div style="height: 22px; width: 10px"></div>
						</td>
					</tr>
				</table>
				<table>
					<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
						<tr>
							<td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td>
						</tr>
					</table>
					<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">金额：</span>
								<input id="payment.amount" name="payment.amount" type="text" style="width: 150px" value="${payment.amount}" ></input>
								<span class="LABEL_ERROR"><s:fielderror><s:param>payment.amount</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 0px 10px">
								<span class="LABEL_NORMAL">描述：</span>
								<input id="payment.description" name="payment.description" type="text" style="width: 150px" value="${payment.description}" ></input>
								<span class="LABEL_ERROR"><s:fielderror><s:param>payment.description</s:param></s:fielderror></span>
							</div>
						</td></tr>
						<tr style="width: 100%"><td>
							<div style="margin:10px 0px 10px 10px">
								<input type="button" value="&nbsp;提&nbsp;交&nbsp;" onclick="openCommentWindow()" class="SUBMIT_BUTTON_CLASS"/>
								<span style="width: 5px;"></span>
								<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysuser/sysuser.jsp'" class="SUBMIT_BUTTON_CLASS"/>
							</div>
						</td></tr>
					</table>
				</table>
				<div id="win" icon="icon-save" style="padding:10px;">
					<div fit="true">
						<div region="center">
						  	<textarea id="comment2" name="comment2" cols="25" rows="3">添加报销单</textarea>
						 </div>
						 <div region="south" border="false" style="text-align:center;height:30px;line-height:30px;margin-top:10px">
							<a class="easyui-linkbutton"  icon="icon-ok" href="javascript:void(0)" onclick="submitEvent()">确认</a>
							<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeCommentWindow()">取消</a>
						 </div>
					</div>
				</div>
			</div>
		</form>
		</div>
	</body>
</html>
