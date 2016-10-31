$(function(){
	
	$('#addfcdata').click(function(e){
		e.preventDefault();
		$('#forecastdataModal').modal('show');
	});
	
	$('#forecastdataModal .close,#forecastdataModal .cancelbtn').click(function(){
		$("#forecastaddForm")[0].reset();
		$("#saveFCdata").removeAttr("disabled");
		$("#errorMsg").hide();
	});
	
	$("#saveFCdata").click(function(){
		if($(this).attr("disabled")=="disabled"){
			return;
		}
		$("#errorMsg").hide();
		var obj = $("#forecastaddForm").serialize();
		if(obj.indexOf("=&")!=-1 || obj.lastIndexOf("=")==(obj.length-1)){
			alert("有空值,请检查!");
			return;
		}
		$(this).attr("disabled",true);
		$.post($("base").attr("href")+"weatherdata/save",obj,function(json){
			if(json.result){
				alert("预报数据添加成功!");
				$("#forecastaddForm")[0].reset();
			}
			else{
				$("#errorMsg").text(json.msg).show();
			}
			$("#saveFCdata").removeAttr("disabled");
		},"json");
	});
});