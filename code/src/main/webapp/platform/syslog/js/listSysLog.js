$(function(){
			$('#userLogGrid').datagrid({
				title:'日志浏览',
				//iconCls:'icon-save',
				width:$('#_operation_table').width()-2,
				height:getGridHeight(),
				nowrap: true,
				striped: true,
				url:'platform/syslog/listSysLog.action',
				sortName: 'userCode',
				sortOrder: 'desc',
				idField:'id',
				columns:[[
					{title:'用户名称',field:'username', width: 150, sortable: true},	
					{title:'日志名称',field:'logName', width: 150, sortable: true},
					{title:'日志类型',field:'logType', width: 150, sortable: true},
					{title:'IP地址',field:'remoteAddress', width: 150, sortable: true},
					{title:'内容',field:'message', width: 150, sortable: true},
					{title:'可抛出信息',field:'throwableInformation', width: 150, formatter : function(value) {
						if (value == null) {
							return "";
						}
						return value;
					}},
					{title:'时间',field:'logTime',width: 150, sortable: true, formatter :  function(value) {
						  if (value == null) {
							  return "";
						   }
					    	return (new Date(value.time)).pattern("yyyy-MM-dd HH:mm:ss");
				    	}},
					{title:'日志等级',field:'logLevel', width: 150, sortable: true}
				]],
				pagination:true,
				queryParams:{"remoteAddress":"","startTime":"","endTime":""},//查询和排序
				rownumbers:true,
				//pageSize:10,
				//pageNumber:1
				singleSelect:true
			});
			var p = $('#userLogGrid').datagrid('getPager');
			if (p){
				$(p).pagination({
					pageSize:10,
					pageList:[10,50,100],
					beforePageText:'第',
					afterPageText:'页       共{pages}页',
					displayMsg:'当前显示{from}-{to}条记录      共{total}条记录'
				});
			}
		});
		function reloadgrid(){
	        var queryParams = $('#userLogGrid').datagrid('options').queryParams;
	        var startTime = $("#startTime").val();
	        var endTime = $("#endTime").val();
	        var remoteAddress = $("#remoteAddress").val();
			queryParams.startTime=startTime;
			queryParams.endTime=endTime;
			queryParams.remoteAddress=remoteAddress;
			$('#userLogGrid').datagrid('options').queryParams=queryParams;
			$('#userLogGrid').datagrid('reload');
		
		}
//预设条件
$('#userLogGrid').datagrid({
  onBeforeLoad:function(param){
       //param.;
  }
});		
$(window).resize(function() {
			$('#userLogGrid').datagrid('resize', {
				width:$('#_title_table').width(),
				height:getGridHeight()
			});
});	
function getGridHeight() {
				var height1 = $("#_title").height();
				var height2 = $("#_query").height();
				var height4 = document.compatMode=="CSS1Compat"?document.documentElement.clientHeight:document.body.clientHeight;
				return height4  - height2 - height1 - 11;
			}
