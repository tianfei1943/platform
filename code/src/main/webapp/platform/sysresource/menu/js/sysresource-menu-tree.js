$(function(){
$('#menusTree').tree({
	url: 'platform/sysresource/menu/listMenusTree.action?parentId='+$("#parentId").val(),
	onLoadSuccess: function(){
	 	var node = $('#menusTree').tree('find',$('#parentId').val());
	 	if (node != null) {
	    	$('#menusTree').tree('select', node.target);
	    }
	}
});
});
function editInfoType() {
	var nodes = $('#menusTree').tree('getSelected');
	$("#parentId").val(nodes.id);
	$("#menusCuForm").attr('action','platform/sysresource/menu/save.action');
	$("#menusCuForm").submit();
}