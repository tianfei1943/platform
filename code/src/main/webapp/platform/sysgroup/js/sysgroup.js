$(function(){
   // var sysGroupTreediv = $('#sysGroupTreediv');
   // alert(getGridHeight());
   // sysGroupTreediv.css({height:getGridHeight()});
	$('#sysGroupTree').tree({
		url: 'platform/sysgroup/list.action?gType=1'
	});
}); 

//删除
function deleteinfoType() {
        var node = $('#sysGroupTree').tree('getSelected');
        if(node == null){
		     $.messager.alert('操作提示','请选择要删除的记录！','warning');
		     return;
		}
		var groupId = node.id;
		var b = $('#sysGroupTree').tree('isLeaf', node.target);
		var p = $('#sysGroupTree').tree('getParent', node.target);
		//var c =  $('#sysGroupTree').tree('getChildren', node.target);
		if(groupId == -1){
		     $.messager.alert('操作提示','不能对根节点信息进行修改或删除操作，请重新选择！','warning');
		     return;
		}
		if(b){//为子节点
           if (groupId.indexOf('U_')<0){//是否为用户
              groupId = groupId; //选中组织机构的Id
           	  groupParentId = p.id;
              userParentGroupId = "";
           } else {
             groupId = groupId.replace("U_","");
             userParentGroupId = p.id;
             groupParentId = "";
           }
		} else {
           groupId = groupId; //选中组织机构的Id
           groupParentId = p.id;
           userParentGroupId = "";
		
		}
		$.messager.confirm('确认提示', '确定要删除该记录吗？', function(btn) {
				if (btn) {
					$.ajax({
					    url:'platform/sysgroup/delete.action',
					    type:'post',
					    data:{id:groupId, userParentGroupId:userParentGroupId, groupParentId:groupParentId},
					    error:function(){
					    $.messager.alert('操作提示','删除数据失败!','info');
					    },
					    success:function(){
					    $.messager.alert('操作提示','删除数据成功!','info');
					    $('#sysGroupTree').tree("reload");
					    }
					
					});
			}
		});	
}


function editGroup() {
        var node = $('#sysGroupTree').tree('getSelected');
        if(node == null){
		     $.messager.alert('操作提示','请选择要删除的记录！','warning');
		     return;
		}
		var groupId = node.id;
		if(groupId == -1){
		     $.messager.alert('操作提示','不能对根节点信息进行修改或删除操作，请重新选择！','warning');
		     return;
		}
		if (groupId.indexOf('U_')>=0){//是否为用户
			 $.messager.alert('操作提示','不能对用户节点信息进行修改操作，请重新选择！','warning');
		     return;
		}
		$("#id").val(groupId);
		$("#sysGroupForm").attr('action','platform/sysgroup/input.action');
		$("#sysGroupForm").submit();
}
//function getGridHeight() {
//				var height1 = $("#_title").height();
//				var height3 = $("#_operation").height();
//				var height4 = document.compatMode=="CSS1Compat"?document.documentElement.clientHeight:document.body.clientHeight;
//				return height4 - height3  - height1 - 20;
//			}