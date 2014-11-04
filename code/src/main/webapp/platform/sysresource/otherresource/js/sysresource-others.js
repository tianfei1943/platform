$(function(){
			$('#resourceGrid').datagrid({
				//title:'其它资源管理',
				//iconCls:'icon-save',
				//width:$('#_operation_table').width()-2,
				height:getGridHeight(),
				nowrap: true,
				striped: true,
				url:'platform/sysresource/otherresource/list.action',
				sortName: 'resourceName',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
	    {title:'资源名称',field:'resourceName', width: 200, sortable: true},	
	    {
			title : '资源类型',
			field : 'resourceType',
			width : 100,
			formatter : function(value) {
				if (value == "menu") {
					return "菜单";
				}
				if (value == "boProp") {
					return "业务对象属性";
				}
				if (value == "url") {
					return "URL";
				}
				if (value == "bussinessObjects") {
					return "业务对象";
				}
			}
		}, {
			title : '资源路径',
			field : 'resourceUrl',
			width : 300,
			formatter : function(value) {
				if (value == null) {
					return "";
				}
				return value;
			}
		}, {
			title : '资源描述',
			field : 'desciption',
			width : 200,
			formatter : function(value) {
				if (value == null) {
					return "";
				}
				return value;
			}
		} 
				]],
				pagination:true,
				queryParams:{"resourceName":"","resourceType":"-1"},//查询和排序
				rownumbers:true
				//pageSize:10,
				//pageNumber:1
				//singleSelect:true
			});
			var p = $('#resourceGrid').datagrid('getPager');
			if (p){
				$(p).pagination({
					pageSize:10,
					pageList:[10,50,100],
					beforePageText:'第',
					afterPageText:'页       共{pages}页',
					displayMsg:'当前显示{from}-{to}条记录      共{total}条记录'
				});
			}
		});
		function reloadgrid(){
	        var queryParams = $('#resourceGrid').datagrid('options').queryParams;
	        var resourceName = $("#resourceName").val();
	         var resourceType = $('#resourceTypeCombo').combobox('getValue');;
			queryParams.resourceName=resourceName;
			queryParams.resourceType=resourceType;
			$('#resourceGrid').datagrid('options').queryParams=queryParams;
			$('#resourceGrid').datagrid('reload');
		
		}
//预设条件
$('#resourceGrid').datagrid({
  onBeforeLoad:function(param){
       //param.;
  }
});		
//修改
function editInfoType() {
	var rows = $('#resourceGrid').datagrid('getSelections');
	var num = rows.length;
	if(num<1){
		$.messager.alert('错误提示','请选择一条记录！','warning');
		return ;
	}else if(num>1){
		$.messager.alert('错误提示','不能选择多条记录进行编辑！','warning');
		$('#resourceGrid').datagrid('clearSelections');
		return ;
	}else{
		$("#id").val(rows[0].id);
		$("#resourceForm").attr('action','platform/sysresource/otherresource/input.action');
		$("#resourceForm").submit();
	}
}
$(window).resize(function() {
			$('#resourceGrid').datagrid('resize', {
				width:$('#_title_table').width(),
				height:getGridHeight()
			});
});	
//删除
function deleteinfoType() {
 		var rows = $('#resourceGrid').datagrid('getSelections');
		var num = rows.length;
		//批量处理删除
		if(num < 1){
			$.messager.alert('操作提示','请选择你要删除的记录!','info');
		}else{
			var ids = "";
			for (var i = 0; i < num; i++) {
				if (0 == i) {
					ids = rows[i].id;
				} else {
					ids = ids + "," + rows[i].id;
				}
			}
		$.messager.confirm('确认提示', '确定要删除该记录吗？', function(btn) {
		if (btn) {
			$.ajax({
			    url:'platform/sysresource/otherresource/delete.action',
			    type:'post',
			    data:{id:ids},
			    error:function(){
			    $.messager.alert('操作提示','删除数据失败!','info');
			    },
			    success:function(){
			    $.messager.alert('操作提示','删除数据成功!','info');
			    reloadgrid();
			    }
			
			});
		}
		});	
		}
}

function getGridHeight() {
				var height1 = $("#_title").height();
				var height2 = $("#_query").height();
				var height3 = document.compatMode=="CSS1Compat"?document.documentElement.clientHeight:document.body.clientHeight;
				return height3 - height2 - height1 - 11;
			}