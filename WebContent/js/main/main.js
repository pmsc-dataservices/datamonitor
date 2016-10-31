$().ready(function(){
		$('#bowlG').hide();
		$("body").css({
			"overflow-y":"auto"
		});
		document.body.onmousewheel=function(){return true;};
});
$(function(){
	$('#addAssignment').click(function(e){
		e.preventDefault();
		$('#myModal').modal('show');
	});
	
	$('.changepwdBtn').click(function(e){
		e.preventDefault();
		$('#pwdModal').modal('show');
	});
	
	$('#pwdModal .close,#pwdModal .cancelBtn').click(function(){
		$("#pwdErrorMsg").text("").show();
		$("#oldPwd").val(""),
		$("#newPwd").val(""),
		$("#newPwd2").val("");
	});
	
	$("#pwd_ok").click(function(e){
		e.preventDefault();
		var oldPwd = $("#oldPwd").val(),
			newPwd = $("#newPwd").val(),
			newPwd2 = $("#newPwd2").val();
		$("#pwdErrorMsg").text("").show();
		$.post($("base").attr("href")+"user/checkPwd",{oldPwd:oldPwd},function(json){
			if(json.result){
				if(newPwd.trim() == ""){
					$("#pwdErrorMsg").text("请输入新密码!").show();
				}
				else if(newPwd != newPwd2){
					$("#pwdErrorMsg").text("两次输入密码不一致!").show();
				}
				else{
					$.post($("base").attr("href")+"user/changePwd",{newPwd:newPwd},function(text){
						if(text.result){
							alert("修改成功!");
							$("#pwdModal .close").click();
						}
						else{
							$("#pwdErrorMsg").text(text.msg).show();
						}
					},"json");
				}
			}
			else{
				$("#pwdErrorMsg").text(json.msg).show();
			}
		},"json");
		
	});
	
	$(".chzn-drop .chzn-search input").hide();
	
	$("#saveAssignment").click(function(){
		var obj = $("#addForm").serialize();
		$.post($("base").attr("href")+"assignment/add",obj,function(json){
			if(json.result){
				$("#errorMsg").hide();
				location.reload(true);
			}
			else{
				$("#errorMsg").text(json.msg).show();
			}
		},"json");
	});
	
	$("input[name='executivemode']").change(function(){
		if($("#periodmode").attr("checked")=="checked"){
			$("#periodDiv").show();
		}
		else {
			$("#periodDiv").hide();
		}
	});
	
	$(".badge").click(function(){
		var id = $(this).attr("id");
		location.href = $("base").attr("href")+"assignment/showResult?assId="+id;
	});
	
	$("#forecastForm .searchBtn").click(function(){
		var date = $("#fcdate").val().trim(),
			city = $("#city").val().trim(),
			bj = $("#bj").attr("checked");
		if(date == "" || (city == "" && bj != "checked")){
			alert("由于数据量大，请同时输入查询条件'时间'和'站号/站点'!");
			return false;
		}
//		if($("#cityType").val()=="stationid" && isNaN($("#city").val())){
//			alert("站号请输入数字!");
//			$("#city").val("");
//			return;
//		}
		$("#forecastForm").submit();
	});
	
	$("#errStationForm .searchBtn").click(function(){
		$("#errStationForm").submit();
	});
	
	var dataMap = {};
	var ediTMap = {forecastTab:{begin:6,filLen:'td:lt(20)',filter:'td:gt(5):lt(14)',url:'weatherdata/save'},
		  	  	   errStationTab:{begin:5,filLen:'td:lt(19)',filter:'td:gt(4):lt(14)',url:'weatherdata/correct'},
		  	       realtimeTab:{begin:3,filLen:'td:lt(18)',filter:'td:gt(2):lt(15)',url:'realtimedata/edit'},
		  	       windTab:{begin:4,filLen:'td:lt(8)',filter:'td:gt(3):lt(4)',url:'realtimedata/editWoR'},
		  	       rainTab:{begin:4,filLen:'td:lt(5)',filter:'td:gt(3):lt(1)',url:'realtimedata/editWoR'},
		  	       forecast_3h_15dayTab:{begin:4,filLen:'td:lt(20)',filter:'td:gt(3):lt(16)',url:'forecast_15day/save?tabType=3h'},
		  	       forecast_12h_15dayTab:{begin:4,filLen:'td:lt(22)',filter:'td:gt(3):lt(18)',url:'forecast_15day/save?tabType=12h'},
		  	       forecast_24h_15dayTab:{begin:5,filLen:'td:lt(25)',filter:'td:gt(4):lt(20)',url:'forecast_15day/save?tabType=24h'}};
	$(".dataedit").live("click",function(){
		var tr = $(this).parents("tr"),
			tabId = $(this).parents("table").attr("id");
			editTds = tr.children(ediTMap[tabId].filter),
			obj = {};
		$.each(editTds,function(){
			var text = $(this).children("p").text();
			var input = "<input type='text' style='width: 80%;margin-bottom: 0;height: 15px' value='" + text + "'>";
			$(this).html(input);
			obj[$(this).attr("name")] = text;
		});
		dataMap[tr.attr("id")] = obj;
		$(this).next().show();
		$(this).hide();
	});
	
	$(".datasave").live("click",function(){
		var tabId = $(this).parents("table").attr("id");
		var tr = $(this).parents("tr"),
			editTds = tr.children(ediTMap[tabId].filLen);
		var obj = {},flag = true;
		
		$.each(editTds,function(i){
			if(i<ediTMap[tabId].begin){
				var text = $(this).html().replace(/[\r\n]/g," ").replace(/<.*?>/g,"");
				
				if(text.trim() == ""){
					flag = false;
					return;
				}
				
				obj[$(this).attr("name")] = text;
			}
			else{
				var text = $(this).children("input").val();
				if(text.trim() == ""){
					flag = false;
					return;
				}
				obj[$(this).attr("name")] = text;
			}
		});
		if(!flag){
			alert("有空白值,请检查!");
			return;
		}
		
		var oldObj = dataMap[tr.attr("id")],change = false;
		for(el in oldObj){
			if(oldObj[el] != obj[el]){
				change = true;
			}
		}
		if(!change){
			editTR(tr,oldObj,ediTMap[tabId].filter);
			return false;
		}
		
		$("#bowlG").show();
		var url = $("base").attr("href")+ediTMap[tabId].url,err=false;
		
		$.post(url,obj,function(json){
			if(json.result){
				$("#bowlG").hide();
				alert("更正成功!");
				if(err){
					location.reload(true);
				}
				else{
					if(tabId=="errStationTab"){
						location.reload(true);
					}
					editTR(tr,obj,ediTMap[tabId].filter);
				}
			}
			else{
				$("#bowlG").hide();
				alert("更正失败，请联系管理员!");
			}
		},"json");
	});
	
	function editTR(tr,obj,filter){
		var editTds = tr.children(filter);
		$.each(editTds,function(){
			var p = "<p title='" + obj[$(this).attr("name")] + "'>" + obj[$(this).attr("name")] + "</p>";
			$(this).html(p);
		});
		$(tr.children("td:last").children("a.datasave")).hide();
		$(tr.children("td:last").children("a.dataedit")).show();
	}
	
	$("th span.icon").click(function(e){
		var div = $("div#"+$(this).attr("type"));
		div.show();
		$(window).scrollTop(div.offset().top);
		e.stopPropagation();
	});
	
	//返回顶部js代码
	var to_top = function(){
		var topArrow = $( '<img alt="Top_arrow" id="top_arrow" src="http://csdnimg.cn/www/images/top_arrow.png" />' );
	    topArrow.css( {
	        display: 'none',
	        cursor: 'pointer',
	        border: '0 none',
	        bottom: '80px',
	        height: 'auto',
	        margin: 0,
	        opacity: 0.5,
	        padding: 0,
	        position: 'fixed',
	        right: '40px',
	        width: '35px'
	    } );
	    $( 'body' ).append( topArrow );

	    $(window).scroll(function(){
	        $( window ).scrollTop() > 20 ? $( topArrow ).fadeIn( 400 ) : $( topArrow ).fadeOut( 400 );
	    });

	    $('body,html').scroll(function(){
	        $( 'body, html' ).scrollTop() > 20 ? $( topArrow ).fadeIn( 400 ) : $( topArrow ).fadeOut( 400 );
	    });

	    //当点击跳转链接后，回到页面顶部位置
	    $( topArrow ).click(function(){
	        $('body,html').animate({scrollTop:0},400);
	        return false;
	    });
	};
	to_top();
	
	$("#addSite").click(function(){
		var site = $("#site").val();
		if(site.trim()!=""){
			var ul = $("ul.chzn-choices");
			var li = $("<li></li>");
			li.addClass("li-choices");
			li.append("<span>"+site+"</span>");
			li.append("<a href='javascript:void(0)' class='choice-close'></a>");
			ul.append(li).show();
		}
	});
	
	$("a.choice-close").live("click",function(){
		$(this).parent().remove();
		var li = $("ul.chzn-choices").children("li");
		if(li.length==0){
			$("ul.chzn-choices").hide();
		}
	});
	
	$("a.run").click(function(){
		if(!confirm("请慎重操作，是否确认补转？")){
			return;
		}
		$("#bowlG").show();
		$.post($("base").attr("href")+"product/executive",{},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("补转成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("补转失败，请刷新重试!");
			};
		},"json");
		alert("这个过程可能需要10-15分钟，请耐心等候。");
		setTimeout(function(){
			$("#bowlG").hide();
			alert("时间过长，有可能失败，请刷新重试!");
		},1000*60*30);
	});
	
	$("#showMenu").click(function(){
		if($("#leftMenu").css("display") != "none"){
			$("#leftMenu").hide();
			this.innerHTML="显示主菜单";
			$(".dataContent").removeClass("span10");
			$(this).prev().attr("class","icon-eye-open");
		}
		else{
			$("#leftMenu").show();
			this.innerHTML="隐藏主菜单";
			$(".dataContent").addClass("span10");
			$(this).prev().attr("class","icon-eye-close");
		}
	});
	
	$(".run_15d").click(function(){
		if(!confirm("请慎重操作，是否确认补转"+$(this).attr("type")+"？")){
			return;
		}
		$("#bowlG").show();
		var timer;
		$.post($("base").attr("href")+"product/redo_f_15d",{type:$(this).attr("type")},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("补转成功!");
				clearTimeout(timer);
			}else{
				$("#bowlG").hide();
				alert("补转失败，请刷新重试!");
				clearTimeout(timer);
			};
		},"json");
		alert("这个过程可能需要20分钟左右，请耐心等候。");
		timer = setTimeout(function(){
			$("#bowlG").hide();
			alert("时间过长，有可能失败，请刷新重试!");
		},1000*60*20);
	});
	
	$(".push_update").click(function(){
		if(!confirm("是否确认推送修改外网库？")){
			return;
		}
		$("#bowlG").show();
		$.post($("base").attr("href")+"weatherdata/handout",{},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("修改成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("修改失败，请刷新重试!");
			};
		},"json");
	});
	
	$(".push_05").click(function(){
		if(!confirm("是否确认推送05前数据？")){
			return;
		}
		$("#bowlG").show();
		$.post($("base").attr("href")+"weatherdata/handout05",{},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("推送成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("推送失败，请刷新重试!");
			};
		},"json");
	});
	
	
	$("a.autoup").click(function(){
		if(!confirm("是否确认自动订正精细化数据（"+$(this).attr("type")+"）？")){
			return;
		}
		$("#bowlG").show();
		$.post($("base").attr("href")+"weatherdata/autoup",{type:$(this).attr("type")},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("订正成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("订正失败，请刷新重试!");
			};
		},"json");
	});
	
	
	//menu
	$(".main-menu>li").hover(function(){
		$(this).children(".second-menu").slideDown();
	},function(){
		var has = false;
		
		$(this).find(".second-menu li").each(function(){
			if($(this).hasClass("active")){
				has = true;
				return;
			}
		});
		if(!has){
			$(this).children(".second-menu").slideUp();
		}
	});
});