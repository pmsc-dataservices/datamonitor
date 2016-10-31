$(function(){
	
	$("#rtwrForm .searchBtn").click(function(){
		var time = $("#time").val().trim(),
			city = $("#city").val().trim();
		if(time == "" || city == ""){
			alert("请输入'时间'和'站号/站点'!");
			return false;
		}
		if($("#cityType").val()=="stationid" && isNaN($("#city").val())){
			alert("站号请输入数字!");
			$("#city").val("");
			return;
		}
		$("#rtwrForm").submit();
	});
	
	$("#mainTab .dataTables_filter").hide();
	
	$("#time").click(function(){
		WdatePicker({dateFmt:'yyyyMMddHH',
			 isShowOK:false,
			 isShowClear:false,
			 readOnly:true,
			 maxDate:'%y-%M-{%d+1}-%H'
			 });
	});
	
	$("#wind").click(function(){
		$("#windTab_wrapper").show();
		$("#rainTab_wrapper").hide();
	});
	
	$("#rain").click(function(){
		$("#windTab_wrapper").hide();
		$("#rainTab_wrapper").show();
	});
	
	
	$(".delBtn").click(function(){
		var answer = confirm("确认删除该条数据吗？");
		if(answer){
			var tr = $(this).parents("tr");
			var data = {column1:tr[0].children[0].children[0].innerHTML,
						column2:tr[0].children[1].children[0].innerHTML,
						column3:tr[0].children[3].children[0].innerHTML};
			$("#bowlG").show();
			$.post($("base").attr("href")+"realtimedata/delWoR",data,function(json){
				if(json.result){
					$("#bowlG").hide();
					tr[0].parentNode.removeChild(tr[0]);
					alert("删除成功");
				}
				else{
					$("#bowlG").hide();
					alert("删除失败");
				}
			},"json");
		}
	});
	
});