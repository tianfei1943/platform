$(function(){
 			$('#typeCombo').combobox('setValue','-1');
			$('#sysparavalueGrid').propertygrid({
				title:'数据值管理',
				//iconCls:'icon-save',
				width:$('#_operation_table').width()-2,
				height:getGridHeight(),
				nowrap: true,
				striped: true,
				showGroup:true ,
				groupField:'groupValue',
				url:'platform/sysparavalue/list.action',
				sortName: 'name',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'parameterCode',title:'参数类型编号',width:120},
					{title:'数据值名',field:'name', width: 150, sortable: true},
					{title:'数据值',field:'value', width: 100, sortable: true},
					{title:'数据值描述',field:'description', width: 200, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}},
					{title:'数据类型',field:'parameterType', width: 100}
				]],
				pagination:true,
				queryParams:{"parameterCode":""},//查询和排序
				rownumbers:true//,//,
				//pageSize:10,
				//pageNumber:1
				//singleSelect:true
			});
			var p = $('#sysparavalueGrid').datagrid('getPager');
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
	        var queryParams = $('#sysparavalueGrid').datagrid('options').queryParams;
	        var parameterCode = $('#typeCombo').combobox('getValue');
			queryParams.parameterCode=parameterCode;
			$('#sysparavalueGrid').datagrid('options').queryParams=queryParams;
			$('#sysparavalueGrid').datagrid('reload');
		
		}
//预设条件
$('#sysparavalueGrid').datagrid({
  onBeforeLoad:function(param){
       //param.;
  }
});	

$(window).resize(function() {
			$('#sysparavalueGrid').datagrid('resize', {
				width:$('#_title_table').width(),
				height:getGridHeight()
			});
});		
//修改
function editInfoType() {
	$('#parentValueCombo').combobox('enable');
	var rows = $('#sysparavalueGrid').datagrid('getSelections');
	var num = rows.length;
	if(num<1){
		$.messager.alert('错误提示','请选择一条记录！','warning');
		return ;
	}else if(num>1){
		$.messager.alert('错误提示','不能选择多条记录进行编辑！','warning');
		$('#sysparavalueGrid').datagrid('clearSelections');
		return ;
	}else{
		$("#id").val(rows[0].id);
		$("#entityForm").attr('action','platform/sysparavalue/input.action');
		$("#entityForm").submit();
	}
}

//删除
function deleteinfoType() {
 		var rows = $('#sysparavalueGrid').datagrid('getSelections');
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
			    url:'platform/sysparavalue/delete.action',
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