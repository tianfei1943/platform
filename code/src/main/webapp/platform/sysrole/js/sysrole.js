$(function(){
			$('#roleGrid').datagrid({
				title:'角色管理',
				//iconCls:'icon-save',
				width:$('#_operation_table').width()-2,
				height:getGridHeight(),
				nowrap: true,
				striped: true,
				url:'platform/sysrole/list.action',
				sortName: 'roleCode',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
	   		{title:'角色编码',field:'roleCode', width: 150, sortable: true},	
		{title:'角色名称',field:'roleName', width: 150, sortable: true,formatter:function(value, rec){
			return '<a href="platform/sysrole/detail.action?id='+rec.id+'" target="rightPanel" class="SMFHREF">'+value+'</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		}}
				]],
				pagination:true,
				queryParams:{"roleName":""},//查询和排序
				rownumbers:true
				//pageSize:10,
				//pageNumber:1
				//singleSelect:true
			});
			var p = $('#roleGrid').datagrid('getPager');
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
	        var queryParams = $('#roleGrid').datagrid('options').queryParams;
	        var roleName = $("#roleName").val();
			queryParams.roleName=roleName;
			$('#roleGrid').datagrid('options').queryParams=queryParams;
			$('#roleGrid').datagrid('reload');
		
		}
//预设条件
$('#roleGrid').datagrid({
  onBeforeLoad:function(param){
       //param.;
  }
});

$(window).resize(function() {
			$('#roleGrid').datagrid('resize', {
				width:$('#_title_table').width(),
				height:getGridHeight()
			});
});		
//修改
function editInfoType() {
	var rows = $('#roleGrid').datagrid('getSelections');
	var num = rows.length;
	if(num<1){
		$.messager.alert('错误提示','请选择一条记录！','warning');
		return ;
	}else if(num>1){
		$.messager.alert('错误提示','不能选择多条记录进行编辑！','warning');
		$('#roleGrid').datagrid('clearSelections');
		return ;
	}else{
		$("#id").val(rows[0].id);
		$("#roleForm").attr('action','platform/sysrole/input.action');
		$("#roleForm").submit();
	}
}

//删除
function deleteinfoType() {
 		var rows = $('#roleGrid').datagrid('getSelections');
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
			    url:'platform/sysrole/delete.action',
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
	// 减11是由于query div有上下各5个像素的margin，title div有一个1像素的下边框
	return height3 - height2 - height1 - 11;
}