$(function(){
$('#typeCombo').combobox({   
    url:'platform/sysparavalue/listParaType.action',   
    valueField:'id',   
    textField:'parameterType',
    onSelect:function(record){
	                //刷新数据，重新读取省份下的城市，并清空当前输入的值
	                parentValueCombo.combobox({
	                    disabled:false,
	                    url:'platform/sysparavalue/listParentPara.action?parameterTypeId='+record.id,
	                    valueField:'id',
	                    textField:'name'
	                }).combobox('clear');
	            }
      
  });
  var typeID = $('#typeCombo').combobox('getValue');
  
var parentValueCombo = $('#parentValueCombo').combobox({   
						    url:'platform/sysparavalue/listParentPara.action?parameterTypeId='+typeID,
						    disabled:true,   
						    valueField:'id',   
						    textField:'name'  
					  });	  
  /*$('#parentValueCombo').combobox('setValue','-1'); */
});