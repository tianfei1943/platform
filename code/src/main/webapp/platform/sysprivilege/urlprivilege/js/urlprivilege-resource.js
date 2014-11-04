$(function(){
			$('#urlprivilegeGrid').datagrid({
				//iconCls:'icon-save',
				width:$('#_title_table').width()-10,
				height:400,
				nowrap: true,
				striped: true,
				url:'platform/sysprivilege/urlprivilege/listresource.action',
				sortName: 'resourceName',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
				   {field:'ck',checkbox:true}
				]],
				columns:[[
	    			{title:'资源名称',field:'resourceName', width: 150, sortable: true},
					{title:'资源类型',field:'resourceType', width: 100, formatter : function(value){
						if(value == "url"){
							return "URL";
						}
					}},
					{title:'资源路径',field:'resourceUrl', width: 260, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}},
					{title:'资源描述',field:'desciption', width: 200, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}}
				]],
				//pagination:true,
				rownumbers:true,
				onLoadSuccess:function(){
					var sysResourceIds = $("#sysResourceId").val();
	                var sysResourceIda =  sysResourceIds.split(',');
                    for(var i in sysResourceIda){
                       $('#urlprivilegeGrid').datagrid('selectRecord',sysResourceIda[i]);
                    }  
				}
			});
});

function editInfoType() {
	
	var rows = $('#urlprivilegeGrid').datagrid('getSelections');
	var num = rows.length;
	var sysResourceId = "";
	if(num<1){
		$.messager.alert('错误提示','请选择记录！','warning');
		return ;
	}else{
		
		for (var i = 0; i < num; i++) {
			if (0 == i) {
				sysResourceId = rows[i].id;
			} else {
				sysResourceId = sysResourceId + "," + rows[i].id;
			}
		}	    
	}
	$("#sysResourceId").val(sysResourceId);
	$("#privilegeForm").attr('action','platform/sysprivilege/urlprivilege/save.action');
	$("#privilegeForm").submit();
}
