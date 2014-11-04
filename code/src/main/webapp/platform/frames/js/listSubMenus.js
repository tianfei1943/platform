    function updateMenus(id, name){
    	    $.ajax({
	     		type:"post",
	     		dataType:"json",
	     		url:"platform/frames/listSubMenus.action",
	     		data:"selectedMenuId="+id,
	     		success:function(data){
	     		   $(".left").empty();
	     		   for(var i in data){
		     		   var children = data[i].children;
		     		   var desciption = data[i].desciption;
		     		   var errorMessage = data[i].errorMessage;
		     		   var id = data[i].id;
		     		   var openType = data[i].openType;
		     		   var resourceName = data[i].resourceName;
		     		   var resourceUrl = data[i].resourceUrl;
		     		   var $div_index;
		     		    $div_index = $("<div class='menu'><a style='font-size:10px' id="+resourceUrl+">"+resourceName+"</a></div>");

		     		   $(".left").append($div_index);
		     		   createchildren(children);
					    
	     		   }
	     		    if(undefined != $(".menu").eq(0).find("a").attr("id")&&""!=$(".menu").eq(0).find("a").attr("id")){
	     		        $("#mianRightframe").attr("src",$(".menu").eq(0).find("a").attr("id"));
	     		    } else {
	     		       $("#mianRightframe").attr("src",$(".menuChild div").eq(0).find("a").attr("id"));
	     		    }
	     		 
                    
					$(".menuChild div").click(function (){
						$("#mianRightframe").attr("src",$(this).find("a").attr("id")); //Hide all tab content
					});
			       	$(".menuChild").eq(0).show('normal');
					$(".menu").click(
						function(){
							if($(this).next().is('div')){
							    var $menuChild = $(this).next();
								if("menu" != $menuChild.attr('class')){
									if($menuChild.is('div') && $menuChild.is(':visible')){
										$menuChild.hide('normal');
										return false;//防止类似标签<a>的链接
									}
									if($menuChild.is('div') && (!$menuChild.is(':visible'))){
									$('.menuChild:visible').hide('normal');
									$menuChild.show('normal');
									return false;
									}
								} else {
							   		 $("#mianRightframe").attr("src",$(this).find("a").attr("id")); //Hide all tab content
								}
							}else{
								$("#mianRightframe").attr("src",$(this).find("a").attr("id")); //Hide all tab content
							}
						}
					);
	     		}
	     });
    
    }
    
    function createchildren(children){
        if(null != children && children.length > 0){
           var menuc = "<div class='menuChild'>"
           for(var i in children){
	   		   var childrenc = children[i].children;
	   		   var desciption = children[i].desciption;
	   		   var errorMessage = children[i].errorMessage;
	   		   var id = children[i].id;
	   		   var openType = children[i].openType;
	   		   var resourceName = children[i].resourceName;
	   		   var resourceUrl = children[i].resourceUrl;
	   	       menuc += "<div><a id="+resourceUrl+">"+resourceName+"</a></div>";
  		   }
  		   menuc +="</div>";
  		   var $cmenucs = $(menuc);
  		   $(".left").append($cmenucs);
        }
    }

	function initMenu(){
	    $.ajax({
	     type:"post",
	     dataType:"json",
	     url:"platform/frames/listRootMenus.action",
	     //data:"",
	     success:function(data){
	          $('#userName').html(data.userName);
	          $('#logedTime').html(data.logedTime);
	          var rootmenus = data.rootMenus;
	          for(var muens =(rootmenus.length - 1); muens >=0; muens-- ){
	                var id = rootmenus[muens].id;
	                var resourceName = rootmenus[muens].resourceName;
	                var $li_muens = $("<LI  title="+resourceName+"><A href='#tab"+(muens+1)+"_"+id+"'>"+resourceName+"</A></LI>");
	                $("ul.tabs").append($li_muens);
	                
	          }
	          var $li_index = $("<LI title='首页'><A href='#tab0_001'>首页</A></LI>");
	           $("ul.tabs").append($li_index);
	           	//On Click Event
				$("ul.tabs li").click(function() {
				    $(".left").empty();
					$("ul.tabs li").removeClass("active"); //Remove any "active" class
					$("ul.tabs li").children("a").css({color: '#ccc'});
					$(this).children("a").css({color: '#FFFFFF'});
					$(this).addClass("active"); //Add "active" class to selected tab
					var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
					if($.browser.msie){
					var tmp = activeTab.split("/");
						activeTab = tmp[tmp.length - 1];
					}
					var mianRight = $("#mianRight");
					var homeurl = $('#homeurl').val();
					var split = $("#split");
					if(activeTab == "#tab0_001"){
					    $(".left").hide();
					    split.css({left:'10px'});
					    mianRight.css({left:'10px'});
					    $("#mianRightframe").attr("src",homeurl);
					} else {
					    mianRight.css({left:'160px'});
						split.css({left:'154px'});
					    var tabid = activeTab.split("_");
					    var id = tabid[1];
					    var name = $(this).find("a").text();
					    updateMenus(id, name);
					    $(".left").show();//Hide all tab content
					    $("#mianRightframe").attr("src",$(this).find("a").attr("id"));
					    
					}
					$(activeTab).fadeIn(); //Fade in the active content
					return false;
				});
	     }
	    });

		$(".split_button").click(
			function(){
				var $left = $(".left");
				var split = $("#split");
				var mianRight = $("#mianRight");
				if($left.is('div') && $left.is(':visible')){
					split.css({left:'0px'}); 
				    mianRight.css({left:'10px'});
					$left.hide('normal');
					$(".split_button").css("background", "url(/platform/images/split_open.gif) no-repeat center");
					return false;
				}
				if($left.is('div') && (!$left.is(':visible'))){
					split.css({left:'154px'});
                    mianRight.css({left:'160px'});				 
					$('.left:visible').hide('normal');
					//ps:header footer 的边框各为5 所以减去 110 (header footer 为 75 25) 不需要边框可以在这个地方减去100 css 做相应改动
					//$left.height($(window).height()-110);//防止浏览器右侧按钮闪动  
					$left.show('normal');
					$(".split_button").css("background", "url(../../../images/split_close.gif) no-repeat center");
					return false;
				}
			});
	}
	function initContentSize(){
		//$(".tab_content").height($(window).height()-110);//用$(document).height()-100 在IE下尺寸不对
		//$(".content").height($(document).height()-100);
	}
	//function initmianRight(){
	    //$(".main>.mianRight").load("platform/frames/listRootMenus.action?pagetype=main");
	//}

	$(function(){
	$("ul.tabs li:last").children("a").css({borderLeft:0});
	$("ul.tabs li:first").children("a").css({borderRight:0});
	$("ul.tabs li:last").children("a").css({color: '#FFFFFF'});
	$("ul.tabs li:last").addClass("active").show(); //Activate first tab
	$("ul.tabs li:first").children("a").addClass("active").show();
	var homeurl = $('#homeurl').val();
	var tabcontent = $("ul.tabs li:last").find("a").attr("href");
		$(".left").empty();
		$(".left").hide();
		$("#mianRightframe").attr("src",homeurl); //Hide all tab content
		//initContentSize();	
		initMenu();	
	});