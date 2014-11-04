$(function(){
			$('#privilegeGrid').datagrid({
				title:'业务对象属性权限管理',
				//iconCls:'icon-save',
				height:getGridHeight(),
				nowrap: true,
				striped: true,
				url:'platform/sysprivilege/bopropprivilege/list.action',
				sortName: 'privilegeName',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{title:'权限名称',field:'privilegeName', width: 200, sortable: true},	
					{title:'权限类型',field:'privilegeType', width: 100, formatter : function(value){
						if(value == "bussinessObjects"){
							return "业务对象";
						}
					}},
					{title:'操作类',field:'opClass',width: 300, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}},
					{title:'操作方法',field:'opMethod', width: 200, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}},
					{title:'记录日志',field:'isLog', width: 200, sortable: true}
				]],
				pagination:true,
				queryParams:{"privilegeName":""},//查询和排序
				rownumbers:true//,//,
				//pageSize:10,
				//pageNumber:1
				//singleSelect:true
			});
			var p = $('#privilegeGrid').datagrid('getPager');
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
			//queryParams:{"privilegeName":"","privilegeOpClass":"","privilegeOpMethod":""},//查询和排序
	        var queryParams = $('#privilegeGrid').datagrid('options').queryParams;
	        var privilegeName = $("#privilegeName").val();
			queryParams.privilegeName=privilegeName;
			$('#privilegeGrid').datagrid('options').queryParams=queryParams;
			$('#privilegeGrid').datagrid('reload');
		
		}
//预设条件
$('#privilegeGrid').datagrid({
  onBeforeLoad:function(param){
       //param.;
  }
});	
$(window).resize(function() {
			$('#privilegeGrid').datagrid('resize', {
				width:$('#_title_table').width(),
				height:getGridHeight()
			});
});	
	
//修改
function editInfoType() {
	var rows = $('#privilegeGrid').datagrid('getSelections');
	var num = rows.length;
	if(num<1){
		$.messager.alert('错误提示','请选择一条记录！','warning');
		return ;
	}else if(num>1){
		$.messager.alert('错误提示','不能选择多条记录进行编辑！','warning');
		$('#privilegeGrid').datagrid('clearSelections');
		return ;
	}else{
		$("#id").val(rows[0].id);
		$("#privilegeForm").attr('action','platform/sysprivilege/bopropprivilege/input.action');
		$("#privilegeForm").submit();
	}
}

//删除
function deleteinfoType() {
 		var rows = $('#privilegeGrid').datagrid('getSelections');
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
			$.messager.confirm('确认提示','你确定要删除该数据吗?',function(r){ 
			    if(r){
						$.ajax({
						    url:'platform/sysprivilege/bopropprivilege/delete.action',
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
