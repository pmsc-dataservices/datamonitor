$(function(){
	$("#mainTab .dataTables_filter").hide();
	
	$(".searchBtn").click(function(){
		var city = $("#city").val().trim(),
			bj = $("#bj").attr("checked");
		if(city == "" && bj != "checked"){
			return false;
		}
		if($("#cityType").val()=="stationid" && isNaN($("#city").val())){
			alert("站号请输入数字!");
			$("#city").val("");
			return;
		}
		$(this).parents("form").submit();
	});
	$(".handout").click(function(){
		if(!confirm("请慎重操作，是否确认分发？")){
			return;
		}
		$("#bowlG").show();
		$.post($("base").attr("href")+"forecast_15day/handout",{},function(flag){
			if(flag.success){
				$("#bowlG").hide();
				alert("分发成功!");
				location.reload(true);
			}else{
				$("#bowlG").hide();
				alert("分发失败，请刷新重试!");
			};
		},"json");
		/* alert("这个过程可能需要10-15分钟，请耐心等候。");
		setTimeout(function(){
			$("#bowlG").hide();
			alert("时间过长，有可能失败，请刷新重试!");
		},1000*60*30); */
	});
});