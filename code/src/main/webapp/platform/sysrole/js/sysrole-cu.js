$(function(){
$('#sysGroupTree').tree({
		checkbox: true,
	    cascadeCheck:false,
		url: 'platform/sysrole/listSysGroup.action?selectedGroups='+$("#selectedGroups").val()
});

			$('#userGrid').datagrid({
				//iconCls:'icon-save',
				width:$('#tabContents').width()-30,
				height:$('#tabContents').height()-50,
				nowrap: true,
				striped: true,
				url:'platform/sysrole/listSysUser.action',
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
					var userIds = $("#selectedUsers").val();
	                var userIda =  userIds.split(',');
                    for(var i in userIda){
                       $('#userGrid').datagrid('selectRecord',userIda[i]);
                    }  
				}
			});
			
			$('#urlprivilegeGrid').datagrid({
				//iconCls:'icon-save',
				width:$('#tabContents').width()-30,
				height:$('#tabContents').height()-50,
				nowrap: true,
				striped: true,
				url:'platform/sysrole/listSysPrivilege.action',
				sortName: 'privilegeCode',
				sortOrder: 'desc',
				idField:'id',
				frozenColumns:[[
				   {field:'ck',checkbox:true}
				]],
				columns:[[
	    			{title:'权限编码',field:'privilegeCode', width: 150, sortable: true},
					{title:'权限名称',field:'privilegeName', width: 150, sortable: true},
					{title:'权限类型',field:'privilegeType', width: 100, sortable: true},
					{title:'操作类',field:'opClass', width: 100, sortable: true},
					{title:'操作方法',field:'opMethod', width: 100, sortable: true},
					{title:'约束条件',field:'valueConstraint', width: 100, sortable: true}
				]],
				//pagination:true,
				rownumbers:true,
				onLoadSuccess:function(){
					var userIds = $("#selectedPrivileges").val();
	                var userIda =  userIds.split(',');
                    for(var i in userIda){
                       $('#urlprivilegeGrid').datagrid('selectRecord',userIda[i]);
                    }  
				}
			});			
			
});
		
//修改
function editInfoType() {
	
	var rows = $('#userGrid').datagrid('getSelections');
	var num = rows.length;
	var selectedUsers = "";
	var selectedGroups = "";
	if(num<1){
		$.messager.alert('错误提示','请选择用户记录！','warning');
		return ;
	}else{
		
		for (var i = 0; i < num; i++) {
			if (0 == i) {
				selectedUsers = rows[i].id;
			} else {
				selectedUsers = selectedUsers + "," + rows[i].id;
			}
		}	    
	}
	
	var rows2 = $('#urlprivilegeGrid').datagrid('getSelections');
	var num2 = rows2.length;
	var selectedPrivileges = "";
	if(num2<1){
		$.messager.alert('错误提示','请选择用户记录！','warning');
		return ;
	}else{
		
		for (var i = 0; i < num2; i++) {
			if (0 == i) {
				selectedPrivileges = rows2[i].id;
			} else {
				selectedPrivileges = selectedPrivileges + "," + rows2[i].id;
			}
		}	    
	}
	
	var nodes = $('#sysGroupTree').tree('getChecked');
	for(var i=0; i<nodes.length; i++){
		if (selectedGroups != '') selectedGroups += ',';
		if(nodes[i].id != -1){
			selectedGroups += nodes[i].id;
		}
	}   
	$("#selectedGroups").val(selectedGroups);
	$("#selectedUsers").val(selectedUsers);
	$("#selectedPrivileges").val(selectedPrivileges);
	$("#roleForm").attr('action','platform/sysrole/save.action');
	$("#roleForm").submit();
}
		