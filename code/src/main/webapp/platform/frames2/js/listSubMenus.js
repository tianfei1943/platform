var childrenstr = function(childrens){
    if(null != childrens && childrens.length > 0){
        var menuc = "<ul>"
	    for(var i in childrens){
   	        var id = childrens[i].id;
            var resourceName = childrens[i].resourceName;
            var resourceUrl = childrens[i].resourceUrl;
            var children = childrens[i].children;
	        if(null == children || children.length == 0){
	             menuc +=  "<li class=\"current\"><a href=\""+resourceUrl+"\">"+resourceName+"</a></li>";
	        }else {
	             menuc += "<li ><a href=\"#\">"+resourceName+"</a>";
	             menuc += childrenstr(children);
	             menuc += "</li>";
	        }
	    }
	     return menuc += "</ul>";
    }
}


function initMenu(){
	$.ajax({
	type:"post",
	dataType:"json",
	url:"platform/frames/listRootMenus2.action",
	//data:"",
	success:function(data){
		$('#userName').html(data.userName);
	    $('#logedTime').html(data.logedTime);
	    var rootmenus = data.sysMenus;
		
	    var homeurl = $('#homeurl').val();
		var menustr =  "<li class=\"current\"><a href=\"" + homeurl + "\">首页</a></li>";
		var $li_muens = $(menustr);
		$("ul.sf-menu").append($li_muens);

	    for(var muens =0; muens <rootmenus.length; muens++ ){
			var id = rootmenus[muens].id;
	        var resourceName = rootmenus[muens].resourceName;
	        var resourceUrl = rootmenus[muens].resourceUrl;
	        var children = rootmenus[muens].children;
	        if(null == resourceUrl || "" == resourceUrl){
				var menustr =  "";
	            if(null == children || children.length == 0){
					menustr =  "<li class=\"current\"><a href=\"#\">"+resourceName+"</a></li>";
	            } else {
	             	menustr = "<li><a href=\"#\">"+resourceName+"</a>";
	               	menustr += childrenstr(children);
	               	menustr += "</li>";
	            }
                var $li_muens = $(menustr);
	            $("ul.sf-menu").append($li_muens);
	        }   
		}
		//On Click Event
		$("ul.sf-menu li").click(function() {
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
			//var AnchorIndexId = activeTab.indexOf("#");
			//var Href = activeTab.slice(AnchorIndexId ,activeTab.length);
			if(""!=activeTab && "#"!=activeTab && activeTab.indexOf("#") == -1){
				$("#workArea").attr("src",activeTab);	
			}
			$(activeTab).fadeIn(); //Fade in the active content
			return false;
		});
	},
	error:function(){
		//alert("error");
	}
});
}


$(function(){
initMenu();
$("ul.sf-menu").superfish();
});