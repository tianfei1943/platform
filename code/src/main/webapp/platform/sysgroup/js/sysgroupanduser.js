$(function(){
$('#sysGroupTree').tree({
		checkbox: true,
	    cascadeCheck:false,
		url: 'platform/sysgroup/list.action?parentIds='+$("#parentIds").val()
});

			$('#userGrid').datagrid({
				//iconCls:'icon-save',
				width:$('#tabContents').width()-30,
				height:$('#tabContents').height()-50,
				nowrap: true,
				striped: true,
				url:'platform/sysgroup/listuser.action',
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
				//pagination:true,
				rownumbers:true,
				onLoadSuccess:function(){
					var userIds = $("#userIds").val();
	                var userIda =  userIds.split(',');
                    for(var i in userIda){
                       $('#userGrid').datagrid('selectRecord',userIda[i]);
                    }  
				}
			});
		});
		
//修改
function editInfoType() {
	
	var rows = $('#userGrid').datagrid('getSelections');
	var num = rows.length;
	var userIds = "";
	var parentIds = "";
	if(num<1){
		$.messager.alert('错误提示','请选择用户记录！','warning');
		return ;
	}else{
		
		for (var i = 0; i < num; i++) {
			if (0 == i) {
				userIds = rows[i].id;
			} else {
				userIds = userIds + "," + rows[i].id;
			}
		}	    
	}
	var nodes = $('#sysGroupTree').tree('getChecked');
	for(var i=0; i<nodes.length; i++){
		if (parentIds != '') parentIds += ',';
		if(nodes[i].id != -1){
			parentIds += nodes[i].id;
		}
	}   
	$("#parentIds").val(parentIds);
	$("#userIds").val(userIds);
	$("#roleForm").attr('action','platform/sysgroup/save.action');
	$("#roleForm").submit();
}
		