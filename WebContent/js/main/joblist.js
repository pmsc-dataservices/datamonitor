$(function(){
	$(".detail").click(function(){
		var _this = this;
		$.post($("base").attr("href")+"jobmanage/detail",{"jobName":$(this).attr("jobName")},function(json){
			var tr = document.createElement("tr"),
			td = document.createElement("td");
			td.colSpan = 6;
			tr.className = "info";
			$(".infoDiv").css("display","block");
			
			var infoDiv = $(".infoDiv");
			for(var pro in json){
				infoDiv.find("span." + pro).text(json[pro]=="null"?"":json[pro]);
			}
			
			td.appendChild(infoDiv[0]);
			tr.appendChild(td);
			$(".info").remove();
			$(tr).insertAfter($(_this).parent().parent());
		},"json");
		
	});
	
	$(".up a").click(function(){
		$(".infoDiv").css("display","none");
		$("table#jobTab").after($(".infoDiv"));
		$(".info").remove();
	});
});