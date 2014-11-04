$(function(){
	//弹窗初始化---评论操作
	$('#win').window({   
		width:280,   
		height:200,
		title:'demo操作备注',
		closed:true, //窗口默认关闭
		minimizable:false,//是否显示最小化按钮
		maximizable:false,//是否显示最大化按钮
		collapsible:false,//是否显示折叠按钮
		modal:true  
	});
	//tab初始化
	$('#div_tab').tabs({  
	    border:false 
	}); 
});
//开窗
function openCommentWindow(){
	$('#win').window('open');
}
//关窗
function closeCommentWindow(){
	$('#win').window('close');
}

