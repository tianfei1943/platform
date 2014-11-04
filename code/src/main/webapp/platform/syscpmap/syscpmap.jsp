<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.*"
	import="java.text.SimpleDateFormat"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>CP名称映射管理</title>
		<script type="text/javascript" src="platform/syscpmap/js/syscpmap.js"></script>

		<script type="text/javascript">
			function checkEditItem() {
				var selParIds = Ext.getDom("id").value;
				if (selParIds == null || selParIds == "") {
					Ext.MessageBox.alert("提示", "当前没有选择记录，请选择！");
					return;
				}
				var strIDs = new Array();
				strIDs = selParIds.split(",");
				if (strIDs.length > 1) {
					Ext.MessageBox.alert("提示", "修改只能选择一条记录，请重新选择！");
				} else {
					editFormSubmit();
				}
			}
			
			function editFormSubmit() {
				var parameterTypeForm = Ext.getDom("entityForm");
				parameterTypeForm.action = "platform/syscpmap/input.action";
				parameterTypeForm.submit();
			}
			
			//删除操作检查
		    function deleteItem() {
		    	var ids = Ext.getDom("id").value;    
		      	if(null==ids || ""==ids) {
		     		Ext.MessageBox.alert("提示", "当前没有选择记录，请选择！");
			  		return;
		      	} else {
		        	Ext.MessageBox.confirm('确认删除', '你确认要删除这条数据吗？', function(btn) {
						if (btn == "yes") {
							deleteFormSubmit(); 		
						}
					});       
		      	}
		    }			
			
			function deleteFormSubmit() {
				var parameterForm = Ext.getDom("entityForm");
				var conn = new Ext.data.Connection({url:"platform/syscpmap/delete.action"});
				conn.request({params:{id:Ext.getDom("id").value}, success:function () {
					Ext.MessageBox.alert("提示", "数据删除成功");
					var queryBtn = Ext.getDom('query');
					queryBtn.click();
				}});
			}
		</script>
	</head>
	<body id="_right_panel_body" class="body_rightPanel">
		<div id="wrapper" >
		<form id="entityForm" name="entityForm">
			<input type="hidden" id="id" name="id" />
			<table id="_title_table" width="100%" height="22px" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10" height="22" align="left"	background="<%=request.getContextPath()%>/platform/images/top_left.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
					<td width="100%" height="22" align="left" background="<%=request.getContextPath()%>/platform/images/top_middle.jpg">
						<table width="100%">
							<tr><td>
								<div class="LABEL_TITLE">&nbsp;&nbsp;CP名称映射管理</div>
							</td></tr>
						</table>
					</td>
					<td width="12" height="22" align="right" background="<%=request.getContextPath()%>/platform/images/top_right.jpg">
						<div style="height: 22px; width: 10px"></div>
					</td>
				</tr>
			</table>
			<table id="_query_table" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#DEE8E9">
				<tr><td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
						<tr>
							<div style="margin: 5px; padding: 0px">
								<div style="float: left; padding: 10px 5px 5px 10px;">
									<label class="LABEL_NORMAL">CP名称：</label>
								</div>
								<div style="float: left; padding: 5px;">
									<input type="text" id="cpname" name="cpname" style="width: 120px; height: 17px;" />
								</div>
								<div style="float: left; padding: 5px;">
									<input id="query" name="query" type="button" class="BUTTONCLASS" value="&nbsp查&nbsp询&nbsp">
								</div>
							</div>
						</tr>
					</table>
				</td></tr>
			</table>
			<br />
			<table id="_operation_table" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#DEE8E9">
				<tr><td>
					<table style="width: 100%">
						<tr>
							<td width="20px" />
							<td height="35px">
								<img
									src="<%=request.getContextPath()%>/platform/images/icon_add.png"
									alt="添加" onClick="window.location.href='<%=request.getContextPath()%>/platform/syscpmap/input.action'"
									style="cursor: pointer">
									&nbsp;&nbsp;
								<img
									src="<%=request.getContextPath()%>/platform/images/icon_edit.png"
									alt="修改" onClick="checkEditItem()" style="cursor: pointer">
								&nbsp;&nbsp;
								<img
									src="<%=request.getContextPath()%>/platform/images/icon_del.png"
									alt="删除" onClick="deleteItem()" style="cursor: pointer">
							</td>
						</tr>
					</table>
				</td></tr>
			</table>			
			<div id="div_grid" style="left: 0px; right: 0px; position: absolute; width: auto; top: 121px; bottom: 0px; height: auto; ">
			</div>			
		</form>
		</div>
	</body>
</html>
