<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href=" <%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据监控系统</title>
<link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/charisma-app.css" rel="stylesheet">
<link href="css/uniform.default.css" rel="stylesheet">
<link href="css/chosen.css" rel="stylesheet">
<link href="css/opa-icons.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<script src="js/jquery-1.7.2.min.js"></script>
</head>
<body>
<jsp:include page="./progressbar.jsp"/>
		
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
			
			<jsp:include page="./leftmenu.jsp"/>
			
			<div class="span10 dataContent">
		 	<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>内网</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th>时间</th>
									  <th>时次</th>
									  <th>实到站数</th>
									  <th>应到站数</th>
									  <th>操作建议</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="list" items="${intranetList }">
									<tr>
										<td>${list[2] }</td>
										<td>${list[1] }</td>
										<td>${list[0] }</td>
										<td>2409</td>
										<c:if test="${list[0] >= 2400 }">
											<td>正常</td>
										</c:if>
										<c:if test="${list[0] < 2400 }">
											<td><a class="fsrun" href="#" name="inner_no_data_${list[1].substring(0,2) }" type="内网${list[1] }">补转</a></td>
										</c:if>
										
									</tr>
								</c:forEach>
							  </tbody>
						 </table>  
					</div>
				</div>
			</div> 
			<div class="row-fluid sortable">
				<div class="box span12">
				  <div class="box-header well" data-original-title="">
						<h2>DMZ</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th>时间</th>
									  <th>时次</th>
									  <th>实到站数</th>
									  <th>应到站数</th>
									  <th>错站数</th>
									  <th>操作建议</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="list" items="${dmzList }">
									<tr>
										<td>${list[2] }</td>
										<td>${list[1] }</td>
										<td>${list[0] }</td>
										<td>2409</td>
										<td class="errcnt" fchh="${list[1] }"></td>
										<c:if test="${list[0] >= 2400 }">
											<td>正常</td>
										</c:if>
										<c:if test="${list[0] < 2400 }">
										<td><a class="run_7d" href="#" name="dmz_and_rac_no_data_${list[1] }" type="DMZ${list[1] }">补转</a></td>
										</c:if>
									</tr>
								</c:forEach>
							  </tbody>
						 </table>
					</div>
				</div>
			</div>
			
			<div class="row-fluid sortable">
				<div class="box span12">
				  <div class="box-header well" data-original-title="">
						<h2>外网</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th>时间</th>
									  <th>时次</th>
									  <th>实到站数</th>
									  <th>应到站数</th>
									  <th>操作建议</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="list" items="${outernetList }">
									<tr>
										<td>${list[2] }</td>
										<td>${list[1] }</td>
										<td>${list[0] }</td>
										<td>2404</td>
										<c:if test="${list[0] >= 2400 }">
											<td>正常</td>
										</c:if>
										<c:if test="${list[0] < 2400 }">
										<td><a class="run_7d" href="#" name="dmz_and_rac_no_data_${list[1] }" type="外网${list[1] }">补转</a></td>
										</c:if>
									</tr>
								</c:forEach>
							  </tbody>
						 </table>
					</div>
				</div>
			</div>
			
			</div><!--/#content.span10-->
			
				</div><!--/fluid-row-->
		<jsp:include page="./footer.jsp"/>
	</div>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script>
	$(function(){
		$("a.fsrun").click(function(){
			if(!confirm("请慎重操作，是否确认补转"+$(this).attr("type")+"？")){
				return;
			}
			$("#bowlG").show();
			//var timer;
			$.post($("base").attr("href")+"jobmanage/redofs",{type:$(this).attr("name")},function(flag){
				if(flag.success){
					$("#bowlG").hide();
					alert("补转成功!");
					//clearTimeout(timer);
				}else{
					$("#bowlG").hide();
					alert("补转失败，请刷新重试!");
					//clearTimeout(timer);
				};
			},"json");
			/* alert("这个过程可能需要20分钟左右，请耐心等候。");
			timer = setTimeout(function(){
				$("#bowlG").hide();
				alert("时间过长，有可能失败，请刷新重试!");
			},1000*60*20); */
		});
		
		$("td.errcnt").each(function(){
		    var _this = $(this);
			$.post($("base").attr("href")+"jobmanage/dmzerr",{fctime:$(this).attr("fchh")},function(cnt){
				_this.html(cnt);
			},"text");
			
		});
	});
	</script>
</body>
</html>