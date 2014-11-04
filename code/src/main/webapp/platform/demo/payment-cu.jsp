<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改报销信息</title>
	</head>
	<script type="text/javascript">		 
		function submitEvent(){
            cuForm.submit();
		}
	</script>
	<body id="_right_panel_body">
		<div id="wrapper" >
		<form id="cuForm" action="platform/payment/save.action" method="post">
			<input type="hidden" name="id" value="${id}"></input>
			<div id="div_createorupdate">
				<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
							<div style="height: 22px; width: 10px"></div>
						</td>
						<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
							<table width="100%">
								<tr><td>
									<s:if test="id == null || id.isEmpty()">
										<div class="LABEL_TITLE">&nbsp;&nbsp;报销管理 - 添加报销信息</div>
									</s:if>
									<s:else>
										<div class="LABEL_TITLE">&nbsp;&nbsp;报销管理 - 修改报销信息</div>
									</s:else>									
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
								<input type="button" value="&nbsp;提&nbsp;交&nbsp;" onclick="submitEvent()" class="SUBMIT_BUTTON_CLASS"/>
								<span style="width: 5px;"></span>
								<input type="button" value="&nbsp;返&nbsp;回&nbsp;" onClick="window.location.href = '<%=request.getContextPath()%>/platform/sysuser/sysuser.jsp'" class="SUBMIT_BUTTON_CLASS"/>
							</div>
						</td></tr>
					</table>
				</table>
			</div>
		</form>
		</div>
	</body>
</html>
