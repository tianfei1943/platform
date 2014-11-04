$(function(){
	$('#userGrid').datagrid({
		//title:'用户管理',
		//iconCls:'icon-save',
		//width:$('#_grid').width(),
		height:getGridHeight(),
		nowrap: true,
		striped: true,
		url:'platform/sysuser/list.action',
		sortName: 'userCode',
		sortOrder: 'desc',
		idField:'id',
		frozenColumns:[[
			{field:'ck',checkbox:true}
		]],
		columns:[[
			{title:'用户编码',field:'userCode', width: 150, sortable: true},	
			{title:'用户名称',field:'userName', width: 150, sortable: true},
			{title:'邮件',field:'email', width: 150,
				formatter : function(value) {
					if (value == null) {
						return "";
					}
					return value;
				}
			},
			{title:'用户状态',field:'status', width: 150,
				formatter : function(value) {
					if (value == 'A') {
						return "启用";
					} else {
						return "停用";
					}
				}
			},
			{title:'备注',field:'description', width: 150, 
				formatter : function(value) {
					if (value == null) {
						return "";
					}
					return value;
				}
			}	
		]],
		pagination:true,
		queryParams:{"userName":""},//查询和排序
		rownumbers:true//,//,
		//pageSize:10,
		//pageNumber:1
		//singleSelect:true
	});
	
	var p = $('#userGrid').datagrid('getPager');
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
	var queryParams = $('#userGrid').datagrid('options').queryParams;
	var userName = $("#userName").val();
	queryParams.userName=userName;
	$('#userGrid').datagrid('options').queryParams=queryParams;
	$('#userGrid').datagrid('reload');
}

//预设条件
$('#userGrid').datagrid({
  onBeforeLoad:function(param){
  //param.;
}
});		
//修改
function editInfoType() {
	var rows = $('#userGrid').datagrid('getSelections');
	var num = rows.length;
	if(num<1){
		$.messager.alert('错误提示','请选择一条记录！','warning');
		return ;
	}else if(num>1){
		$.messager.alert('错误提示','不能选择多条记录进行编辑！','warning');
		$('#userGrid').datagrid('clearSelections');
		return ;
	}else{
		$("#id").val(rows[0].id);
		$("#entityForm").attr('action','platform/sysuser/input.action');
		$("#entityForm").submit();
	}
}

//删除
function deleteinfoType(u) {
	var urlstr = "";
	var title = "";
	var msg = "";
	if(u==1){
		title = "停用";
		msg = "你确认要停用这些数据吗？";
	   urlstr = "platform/sysuser/delete.action";
	} else {
		title = "启用";
		msg = "你确认要启用这些数据吗？";
	   urlstr = "platform/sysuser/restore.action";
	}
	var rows = $('#userGrid').datagrid('getSelections');
	var num = rows.length;
	//批量处理删除
	if(num < 1){
		$.messager.alert('操作提示','请选择你要停用的记录!','info');
	}else{
		var ids = "";
		for (var i = 0; i < num; i++) {
			if (0 == i) {
				ids = rows[i].id;
			} else {
				ids = ids + "," + rows[i].id;
			}
		}
		$.messager.confirm('确认'+title, msg, function(btn) {
			if (btn) {
				$.ajax({
					url:urlstr,
					type:'post',
					data:{id:ids},
					error:function(){
						$.messager.alert('操作提示',title+'数据失败!','info');
					},
					success:function(){
						$.messager.alert('操作提示',title+'数据成功!','info');
						reloadgrid();
					}
				});	
			}
		});
	}
}

$(window).resize(function() {
			$('#userGrid').datagrid('resize', {
				width:$('#_title').width(),
				height:getGridHeight()
			});
});

function getGridHeight() {
	var height1 = $("#_title").height();
	var height2 = $("#_query").height();
	var height3 = document.compatMode=="CSS1Compat"?document.documentElement.clientHeight:document.body.clientHeight;
	// 减11是由于query div有上下各5个像素的margin，title div有一个1像素的下边框
	return height3 - height2 - height1 - 11;
}