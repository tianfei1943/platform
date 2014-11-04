
Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel({
		 singleSelect: false,
		 handleMouseDown:Ext.emptyFn
	});
	
	sm.on("selectionchange", function(selectionModel){
		var selectedObjs = Ext.getDom("id");
		var records = sm.getSelections();
		var length = records.length;
        var datas='';
        for(var i=0; i<length; i++){
        	var record = records[i];        
        	if (i == (length - 1)) {
        		datas = datas + record.data.id;
        	} else {
        		datas = datas + record.data.id + ",";
        	}
        } 

        selectedObjs.value = datas;
	});
	
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
	    {header:'CP名称',dataIndex:'cpname', sortable:true, width:250},
	    {header:'电信CP代码',dataIndex:'telecomCpcode', sortable:true, width:200},
	    {header:'联通CP代码',dataIndex:'unicomCpcode', sortable:true, width:200},
	    {header:'移动CP代码',dataIndex:'cmcccpcode', sortable:true, width:200}
	]);
	
	
	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({url:'platform/syscpmap/list.action'}),    	
	    reader: new Ext.data.JsonReader({
		    totalProperty: 'pageResponse.total',
		    root: 'pageResponse.result'
		}, [
		    {name: 'id',mapping:'id',type:'int'},
		    {name: 'cpname',mapping:'cpname',type:'string'},
		    {name: 'telecomCpcode',mapping:'telecomCpcode',type:'string'},
		    {name: 'unicomCpcode',mapping:'unicomCpcode',type:'string'},
		    {name: 'cmcccpcode',mapping:'cmcccpcode',type:'string'}
		])
	});
	
	var grid = new Ext.grid.GridPanel({
	    el: 'div_grid', 
	    ds: ds,
	    sm: sm,
	    cm: cm,
	    loadMask: true,
	    width:Ext.get("div_grid").getWidth(),
	    height:Ext.get("div_grid").getHeight(),
	    autoHeight: false,
	    bbar: new Ext.PagingToolbar({
	        pageSize: 50,
	        store: ds,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条，每页显示50条记录',
	        emptyMsg: "没有记录"
    	})
	});
	//el:指定html元素用于显示grid
	grid.render();//渲染表格
	ds.load({params:{start:0, limit:50}});
	
		// 绑定查询按钮的click事件
	var queryBtn = Ext.get('query'); 
	queryBtn.on('click', function(){
		var sysParameterTypeTF = Ext.getDom('cpname');
		var sysParameterType = sysParameterTypeTF.value;
		ds.load({params:{"cpname":sysParameterType, start:0, limit:50}});
	});
	
});