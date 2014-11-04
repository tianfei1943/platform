$(function(){
$('#typeCombo').combobox({   
    url:'platform/sysparatype/listAll.action',   
    valueField:'parameterCode',   
    textField:'parameterType'  
  });
 
});
