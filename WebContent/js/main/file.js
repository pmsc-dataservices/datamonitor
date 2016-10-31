$().ready(function(){
		$('#bowlG').hide();
		$("body").css({
			"overflow-x":"auto",
			"overflow-y":"auto"
		});
		document.body.onmousewheel=function(){return true;};
});
$(function(){
	function getfilepath(servicename,$input){
		$.post($("base").attr("href")+"filemonitor/loadpath",{serviceName:servicename},function(path){
			$input.parent().children(".fp").remove();
			var paths = eval(path);
			$input.val(paths[0]);
			if(paths.length>1){
			for(var i=1;i<paths.length;i++){
				var input = $("<input/>");
				input.addClass("filepath fp");
				input.attr("readonly","readonly");
				input.attr("type","text");
				input.val(paths[i]);
				input.insertAfter($input);
			}
		}
		},"text");
	}
	
	function fillTab(servicename,$tab){
		$.post($("base").attr("href")+"filemonitor/loadfiles",{serviceName:servicename},function(fs){
			var files = eval(fs);
			$tab.html("");
			for(var i=0;i<files.length;i++){
				var tr = $("<tr></tr>");
				var html = "<td>"+(i+1)+"</td><td>"+files[i].v1+"/"+files[i].v0+"</td>"+"<td>"+files[i].v2+"</td>"+"<td>"+files[i].v3+"</td>";
				tr.html(html);
				$tab.append(tr);
			}
			
		},"text");
	}
	
//	getfilepath($("#sourceSel").val(),$("#sourcepath"));
	fillTab($("#sourceSel").val(),$("#sourceTab tbody"));
//	getfilepath($("#targetSel").val(),$("#targetpath"));
	fillTab($("#targetSel").val(),$("#targetTab tbody"));
	
	$("#sourceSel").change(function(){
//		getfilepath($("#sourceSel").val(),$("#sourcepath"));
		fillTab($("#sourceSel").val(),$("#sourceTab tbody"));
	});
	$("#targetSel").change(function(){
//		getfilepath($("#targetSel").val(),$("#targetpath"));
		fillTab($("#targetSel").val(),$("#targetTab tbody"));
	});
});