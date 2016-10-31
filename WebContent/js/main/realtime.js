$(function(){
//	setTimeout(function(){
		$.post($("base").attr("href")+"realtimedata/map",{},function(json){
			for(var name in json){
				if(json[name]){
					$(".map li." + name).addClass("suc");
				}
			}
			
			$(".rtTitle").text(json["date"]);
			var aLi = $(".map li");
			for(var i=0;i<aLi.length;i++){
				if(!$(aLi[i]).hasClass("suc")){
					(function(index){
						aLi[index].inc = -0.05;
						aLi[index].timer = setInterval(function(){
							var opa = parseFloat($(aLi[index]).css("opacity"))+aLi[index].inc;
							if(opa >= 1){
								opa = 1;
								aLi[index].inc = -0.05;
							}
							if(opa <= 0){
								opa = 0.05;
								aLi[index].inc = 0.05;
							}
							$(aLi[index]).css("opacity",opa);
						},50);
					})(i);
				}
			}
		},"json");
//	},100);
	
	$("#realtimeForm .searchBtn").click(function(){
		var begin = $("#begin").val().trim(),
			end = $("#end").val().trim(),
			city = $("#city").val().trim();
		if(begin == "" || end == "" || city == ""){
			alert("由于数据量大，请同时输入查询条件'时间'和'站号/站点'!");
			return false;
		}
		if($("#cityType").val()=="stationid" && isNaN($("#city").val())){
			alert("站号请输入数字!");
			$("#city").val("");
			return;
		}
		$("#realtimeForm").submit();
	});
	
	$("#mainTab .dataTables_filter").hide();
	
	$("#supplytime").click(function(){
		WdatePicker({dateFmt:'yyyyMMddHH',
			 isShowOK:false,
			 isShowClear:false,
			 readOnly:true,
			 maxDate:'%y-%M-%d-%H'
			 });
	});
	
	$("#begin").click(function(){
		WdatePicker({dateFmt:'yyyyMMddHH',
			 isShowOK:false,
			 isShowClear:false,
			 readOnly:true,
			 maxDate:'%y-%M-%d-{%H+12}'
			 });
	});
	$("#end").click(function(){
		if($("#begin").val() == ""){
			return;
		}
		WdatePicker({dateFmt:'yyyyMMddHH',
			 isShowOK:false,
			 isShowClear:false,
			 readOnly:true,
			 minDate:'#F{$dp.$D(\'begin\');}',
			 maxDate:'#F{$dp.$D(\'begin\',{d:2})}'
			 });
	});
	
	$('#addrtdata').click(function(e){
		e.preventDefault();
		$('#rtdataModal').modal('show');
	});
	
	$('#rtdataModal .close,#rtdataModal .cancelbtn').click(function(){
		$("#rtaddForm")[0].reset();
		$("#errorMsg").hide();
		$("#saveRTdata").removeAttr("disabled");
	});
	
	$('.dzbd').click(function(e){
		e.preventDefault();
		$('#dzbdModal').modal('show');
	});
	
	$('#dzbdModal .close,#dzbdModal .cancelbtn').click(function(){
		$("#dzbdForm")[0].reset();
		$("#errorMsg").hide();
		$("#dzbdBtn").removeAttr("disabled");
	});
	
	/*$("#column1").blur(function(){
		if($(this).val()==""){
			return;
		}
		$("#errorMsg").hide();
		$.post($("base").attr("href")+"realtimedata/validate",{stationid:$(this).val()},function(json){
			if(!json.result){
				$("#errorMsg").text(json.msg).show();
				$("#column1").val("");
			}
		},"json");	
	});*/
	
	$("#saveRTdata").click(function(){
		if($(this).attr("disabled")=="disabled"){
			return;
		}
		$("#errorMsg").hide();
		var obj = $("#rtaddForm").serialize();
		if(obj.indexOf("=&")!=-1 || obj.lastIndexOf("=")==(obj.length-1)){
			alert("有空值,请检查!");
			return;
		}
		$(this).attr("disabled",true);
		$.post($("base").attr("href")+"realtimedata/add",obj,function(json){
			if(json.result){
				alert("实况数据添加成功!");
				$("#rtaddForm")[0].reset();
			}
			else{
				$("#errorMsg").text(json.msg).show();
			}
			$("#saveRTdata").removeAttr("disabled");
		},"json");
	});
	
	$("#dzbdBtn").click(function(){
		if($(this).attr("disabled")=="disabled"){
			return;
		}
		var obj = $("#dzbdForm").serialize();
		
		$(this).attr("disabled",true);
		$("#bowlG").show();
		$.post($("base").attr("href")+"realtimedata/dzbd",obj,function(json){
			if(json.success){
				alert("补转成功!");
				$("#dzbdForm")[0].reset();
			}
			else{
				alert("补转失败!");
			}
			$("#dzbdBtn").removeAttr("disabled");
			$("#bowlG").hide();
		},"json");
	});
	
	$("#column2,.dzbdtime").click(function(){
		WdatePicker({dateFmt:'yyyyMMddHHmmss',
			 isShowOK:false,
			 isShowClear:false,
			 readOnly:true,
			 });
	});
	
	$(".handout").click(function(){
		var supplytime = $("#supplytime").val();
		if(supplytime == ""){
			alert("请选择补点时间!");
			return;
		}
		
		if(!confirm("请慎重操作，大概耗时15分钟，是否确认全站补点"+supplytime+"时次数据？")){
			return;
		}
		
		
		
		$("#bowlG").show();
		$.post($("base").attr("href")+"realtimedata/handout",{supplytime:supplytime},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("补点成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("补点失败，请刷新重试!");
			};
		},"json");
	});
});