<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>添加/修改菜单资源</title>
		<script type="text/javascript" src="platform/sysresource/menu/js/sysresource-menu-tree.js"></script>
		<script type="text/javascript" src="platform/sysresource/menu/js/opentypecombo.js"></script>
		<script type="text/javascript">
			//对数据的验证
			function IsNum(theField) {
				if(!IsNum2(theField.value)) {
					Ext.MessageBox.alert("提示", "排序要求是整数 ！");
				 	theField.value ="";
					theField.focus();
				}
			}
			
			// 判断数字类型
			function IsNum2(s) {
				var Number = "0123456789";
				for (i  = 0; i < s.length;i++) {
					// Check that current character isn't whitespace.
					var c = s.charAt(i);
						if (Number.indexOf(c) == -1){
						return false;
					}
				}   
				return true;
			}		
		</script>
	</head>
	<body>
		<div id="wrapper" >
		<form id="menusCuForm" method="post">
			<input type="hidden" id="id" name="id" value="${id}"></input>
			<input type="hidden" id="parentId" name="parentId" value="${parentId}"></input>
            <div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
                    <s:if test="#{id == null || id.isEmpty}">
                        <span>菜单资源管理 - 添加菜单资源</span>
                    </s:if>
                    <s:else>
                        <span>菜单资源管理 - 修改菜单资源</span>
                    </s:else>									
                </div>
            </div>
			<table height="1px" style="width: 100%;" border="0" cellpadding="0"	cellspacing="0">
				<tr><td height="1px" background="<%=request.getContextPath()%>/platform/images/2px.jpg"></td></tr>
			</table>
			<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;"><span class="LABEL_NORMAL" style="width: 100px;">资源名称：</span></div>
						<div style="float:left; width:130px;">
							<input type="text" name="sysResource.resourceName" value="${sysResource.resourceName}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.resourceName</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL" style="width: 100px;">打开类型：</span>
						</div>
						<div style="float:left; width:130px;">
							<input id="openTypeCombo" type="text" name="sysResource.openType" value="${sysResource.openType}" style="width: 115px"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.openType</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL" style="width: 100px;">排序(输入整数)：</span>
						</div>
						<div style="float:left; width:130px;">
							<input type="text" name="sysResource.sortOrder"	value="${sysResource.sortOrder}" onblur=IsNum(this);></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.sortOrder</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL" style="width: 100px;">资源路径：</span>
						</div>
						<div style="float:left; width:450px;">
							<input type="text" name="sysResource.resourceUrl" value="${sysResource.resourceUrl}" style="width: 400px"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.resourceUrl</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL" style="width: 100px;">资源描述：</span>
						</div>
						<div style="float:left; width:450px;">
							<input type="text" name="sysResource.desciption" style="width: 400px; height: 20px" value="${sysResource.desciption}"></input>
						</div>
						<div style="float:left; margin:0px 0px 0px 10px;">
							<span class="LABEL_ERROR"><s:fielderror><s:param>sysResource.desciption</s:param></s:fielderror></span>
						</div>
					</div>
				</td></tr>
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 0px 10px">
						<div style="float:left; width:100px;">
							<span class="LABEL_NORMAL" style="width: 100px;">父节点：</span>
							<s:if test="#{sysResourceId == null || sysResourceId.isEmpty}">
								<input id="sysResourceId" type="hidden" name="sysResourceId" value="-1"></input>
							</s:if>
							<s:else>
								<input id="sysResourceId" type="hidden" name="sysResourceId" value="${sysResource.parent.id}"></input>
							</s:else>
						</div>
						<div style="float:left; width:400px;  height:200px; overflow-y:scroll; border:1px solid #99BBE8;">
								<ul id="menusTree" class="easyui-tree" animate="true" dnd="true"></ul>
						</div>
					</div>
				</td></tr>			
				<tr style="width: 100%"><td>
					<div style="margin:10px 0px 10px 10px">
						<input type="button" onClick="editInfoType()" value="&nbsp;提&nbsp;交&nbsp;" class="SUBMIT_BUTTON_CLASS">
						<span style="width: 5px;"></span>
						<input type="button" value="&nbsp;返&nbsp;回&nbsp;" class="SUBMIT_BUTTON_CLASS"
							onclick="window.location.href= '<%=request.getContextPath()%>/platform/sysresource/menu/sysresource-menu.jsp'" />
					</div>
				</td></tr>	
			</table>
		</form>
	</div>
	</body>
</html>


