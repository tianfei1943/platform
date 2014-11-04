<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<html>
	<head>
		<title>我的自定义菜单配置</title>
		<script type="text/javascript">
			$(function(){
				$('#tt').tree({  
				    url:'platform/mymenuconfig/listHomeDefineMenusTree.action',
				    checkbox:true,
				    onlyLeafCheck:true,
				    animate:true
				});
			});
			
			function submitEvent(){
				
				var nodes = $('#tt').tree('getChecked');
				if(nodes.length == 0){
					alert("您还没有选择快速入口链接");
					return false;
				}
				
				var resourceIds = "";
				for(var i=0;i<nodes.length;i++){
					if(resourceIds != ""){
						resourceIds += ",";
					}
					resourceIds += nodes[i].id;
				}
				
				$.messager.confirm('提示', '您确定要重新设置吗?', function(btn) {
					if (btn) {
						$("#resourceIds").attr("value",resourceIds);
						$("#homeMenuForm").attr('action','platform/mymenuconfig/save.action');
						$("#homeMenuForm").submit();
					} else {
		               
		   		    }
				});
				
				
			}
			
		</script>
	</head>

	<body  id="_right_panel_body">
		<div id="_title" class="content_title">
            <div id="_title_label" class="title_label">
				<span>我的自定义快捷菜单</span>								
            </div>
        </div>
        <form name="homeMenuForm" id="homeMenuForm" method="post">
			<div style="margin:20px;width:400px;height:500px;overflow: scroll;border:1px solid #99BBE8">
				<ul id="tt"></ul> 
				<input type="hidden" id="resourceIds" name="resourceIds" value="${resourceIds}">
				<input type="hidden" id="id" name="id" value="${id}">
			</div>
			<div style="margin:20px;width:300px">
				<input id="submit3"  type="button" class="BUTTONCLASS" onclick="submitEvent()" value="&nbsp保&nbsp存&nbsp">
				<span style="width: 5px;"></span>
			</div>
		</form>
		
		
		
		
	</body>
</html>
