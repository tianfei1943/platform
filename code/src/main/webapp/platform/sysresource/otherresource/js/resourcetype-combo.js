$(function(){
	$('#resourceTypeCombo').combobox({
		url:'platform/sysresource/otherresource/js/combobox_data.json',
		valueField:'id',
		textField:'text'
	});
});