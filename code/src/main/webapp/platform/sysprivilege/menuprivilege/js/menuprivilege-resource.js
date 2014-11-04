$(function(){
$('#menusTree').tree({
	    checkbox: true,
	    cascadeCheck:false,
		url: 'platform/sysprivilege/menuprivilege/listmenuresource.action?sysResourceId='+$("#sysResourceId").val()
});
});
function editInfoType() {
	var sysResourceId = "";
	var nodes = $('#menusTree').tree('getChecked');
	for(var i=0; i<nodes.length; i++){
		if (sysResourceId != '') sysResourceId += ',';
		if(nodes[i].id != -1){
			sysResourceId += nodes[i].id;
		}
	}   
	$("#sysResourceId").val(sysResourceId);
	$("#privilegeForm").attr('action','platform/sysprivilege/menuprivilege/save.action');
	$("#privilegeForm").submit();
}
