$(function(){
	$('#bowlG').hide();
	
	function getRadar(provname,$select,staid){
		$.post($("base").attr("href")+"filemonitor/loadradar",{provname:provname},function(radars){
			$select.children().remove();
			radars = eval(radars);
			for(var i=0;i<radars.length;i++){
				var option = $("<option/>");
				option.attr("value",radars[i].v1);
				option.html(radars[i].v0);
				$select.append(option);
			}
			
			if(staid){
				$("#radarstas").val(staid);
			}
			
			draw($("#radarstas").val());
		},"text");
	}
	
	function draw(stationid){
		$.post($("base").attr("href")+"filemonitor/getRadarInfo",{stationid:stationid},function(info){
			info = eval(info);
			var data = [],sum = 0;	
			for(var i=0;i<info.length;i++){
				var ptime = new Date(info[i].v1.substring(0,4),info[i].v1.substring(4,6),info[i].v1.substring(6,8),info[i].v1.substring(8,10),info[i].v1.substring(10,12),info[i].v1.substring(12));
				var itime = new Date(info[i].v2.substring(0,4),info[i].v2.substring(4,6),info[i].v2.substring(6,8),info[i].v2.substring(8,10),info[i].v2.substring(10,12),info[i].v2.substring(12));
				
				var delay = Math.round((itime.getTime()-ptime.getTime())/1000/60);
				data.push([info[i].v0,delay]);
				sum += delay;
			}
			
			if(data.length > 0){
				$("#timespan").text(info[0].v1.substring(0,10));
				$("#avgtime").text(parseFloat(sum/data.length).toFixed(1));
			}
			
			chart(data);
		},"text");
	}
	
	function chart(data){
		if($("#radar").length)
		{
//			var ticks = [[0,"00"], [6, "06"]];
//			for(var i=12;i<=54;i=i+6){
//				ticks.push([i,i+""]);
//			}

			var plot = $.plot($("#radar"),
				   [ { data: data, label: "延时(y)/时次(x)"} ], {
					   series: {
						   lines: { show: true  },
						   points: { show: true }
					   },
					   grid: { hoverable: true, clickable: true, backgroundColor: { colors: ["#fff", "#eee"] } },
//					   xaxis: { ticks: ticks },
					   yaxis:{tickDecimals:0},
					   colors: ["#539F2E", "#3C67A5"]
					 });

			function showTooltip(x, y, contents) {
				$('<div id="tooltip">' + contents + '</div>').css( {
					position: 'absolute',
					display: 'none',
					top: y - 15,
					left: x + 5,
					border: '1px solid #fdd',
					padding: '2px',
					'background-color': '#dfeffc',
					opacity: 0.80
				}).appendTo("body").fadeIn(200);
			}

			var previousPoint = null;
			$("#radar").bind("plothover", function (event, pos, item) {

					if (item) {
						if (previousPoint != item.dataIndex) {
							previousPoint = item.dataIndex;

							$("#tooltip").remove();
							var x = item.datapoint[0],y = item.datapoint[1];

							showTooltip(item.pageX, item.pageY,x+"min数据延迟"+y + "分钟");
						}
					}
					else {
						$("#tooltip").remove();
						previousPoint = null;
					}
			});
		}
	}
	
	getRadar($("#stations").val(),$("#radarstas"));
		
	$("#stations").change(function(){
		$("#timespan").text("");
		$("#avgtime").text("");
		getRadar($("#stations").val(),$("#radarstas"));
	});
	
	$("#radarstas").change(function(){
		$("#timespan").text("");
		$("#avgtime").text("");
		draw($(this).val());
	});
	
	function countdelay(){
		$.post($("base").attr("href")+"filemonitor/delaycount",{},function(delay){
			delay = eval(delay);
			var data = {d1:[],d2:[],d3:[],d4:[],d5:[],d6:[],d7:[]};
			for(var i=0;i<delay.length;i++){
				var ele = "<a prov='"+delay[i].v2+"' stationid='"+delay[i].v3+"'>"+delay[i].v2+"-"+delay[i].v1+"</a>";
				if(delay[i].v0>=4&&delay[i].v0<6){
					data.d1.push(ele);
				}
				else if(delay[i].v0 >= 6 && delay[i].v0<8){
					data.d2.push(ele);
				}
				else if(delay[i].v0 >= 8 && delay[i].v0<10){
					data.d3.push(ele);
				}
				else if(delay[i].v0 >= 10 && delay[i].v0<15){
					data.d4.push(ele);
				}
				else if(delay[i].v0 >= 15 && delay[i].v0<20){
					data.d5.push(ele);
				}
				else if(delay[i].v0 >= 20 && delay[i].v0<60){
					data.d6.push(ele);
				}
				else if(delay[i].v0 >= 60){
					data.d7.push(ele);
				}
			}
			var ndata = [
				        	{ label: "延迟4≤t<6min",  data: data.d1.length},
				        	{ label: "延迟6≤t<8min",  data: data.d2.length},
				        	{ label: "延迟8≤t<10min",  data: data.d3.length},
				        	{ label: "延迟10≤t<15min",  data: data.d4.length},
				        	{ label: "延迟15≤t<20min",  data: data.d5.length},
				        	{ label: "延迟20≤t<60min",  data: data.d6.length},
				        	{ label: "延迟t≥1h",  data: data.d7.length}
				        ];
			drawpie(data,ndata);
			
		},"text");
	}
	
	function avgdelay(){
		$.post($("base").attr("href")+"filemonitor/avgdelay",{},function(delay){
			delay = eval(delay);
			var data = {d1:[],d2:[],d3:[],d4:[],d5:[],d6:[]};
			for(var i=0;i<delay.length;i++){
				var ele = "<a prov='"+delay[i].v2+"' stationid='"+delay[i].v3+"'>"+delay[i].v2+"-"+delay[i].v1+"</a>";
				if(delay[i].v0<3){
					data.d1.push(ele);
				}
				else if(delay[i].v0 >= 3 && delay[i].v0<6){
					data.d2.push(ele);
				}
				else if(delay[i].v0 >= 6 && delay[i].v0<8){
					data.d3.push(ele);
				}
				else if(delay[i].v0 >= 8 && delay[i].v0<10){
					data.d4.push(ele);
				}
				else if(delay[i].v0 >= 10 && delay[i].v0<15){
					data.d5.push(ele);
				}
				else if(delay[i].v0 >= 15){
					data.d6.push(ele);
				}
			}
			var ndata = [
				        	{ label: "延迟t<3min",  data: data.d1.length},
				        	{ label: "延迟3≤t<6min",  data: data.d2.length},
				        	{ label: "延迟6≤t<8min",  data: data.d3.length},
				        	{ label: "延迟8≤t<10min",  data: data.d4.length},
				        	{ label: "延迟10≤t<15min",  data: data.d5.length},
				        	{ label: "延迟t≥15min",  data: data.d6.length}
				        ];
			drawpie(data,ndata);
			
		},"text");
	}
	
	function drawpie(data,ndata){
		var color = ["rgb(255,156,4)", 
				      "rgb(0,32,255)",
				      "rgb(4,160,255)",
				      "rgb(142,0,255)", 
			          "rgb(239,4,255)",
			          "rgb(204,124,3)",
			          "rgb(255,4,4)"];
		$.plot($("#piechart"), ndata,
				{
					series: {
							pie: {
									show: true
							}
					},
					grid: {
							hoverable: true
					},
					legend: {
						show: false
					},
					colors: color
				});
				
				function pieHover(event, pos, obj)
				{
					if (!obj)
							return;
//					var percent = parseFloat(obj.series.percent).toFixed(2),
					var cnt = obj.datapoint[1][0][1],
						c = obj.series.color,p="";
					if(color.indexOf(c) != -1){
						var radars = data["d"+(color.indexOf(c)+1)];
						p = "<p>" + radars.join(",") + "</p>";
					}
					$("#piehover").html('<span style="font-weight: bold; color: '+c+'">'+obj.series.label+' ('+cnt+'个)</span>'+p);
					
					$("#piehover a").bind("click",function(){
						$("#timespan").text("");
						$("#avgtime").text("");
						$("#stations").val($(this).attr("prov"));
						getRadar($(this).attr("prov"),$("#radarstas"),$(this).attr("stationid"));
					});
				}
				$("#piechart").bind("plothover", pieHover);
	}
	
	function statistics(type){
		if(type == "delaycount"){
			countdelay();
		}
		else if(type == "avgdelay"){
			avgdelay();
		}
	}
	
	statistics($("#statistics").val());
	$("#statistics").change(function(){
		statistics($(this).val());
	});
	
});