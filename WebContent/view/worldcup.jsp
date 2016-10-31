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
						<h2>华风世界杯城市预报产品</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th>任务部署IP地址</th>
									  <th>任务名称</th>
									  <th>业务名称</th>
									  <th>任务启动时间</th>
									  <th>任务完成时间</th>
									  <th>状态</th>
									  <th>备注</th>
									  <th>操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>${log[0] }</td>
									<td>${log[1] }</td>
									<td>${log[2] }</td>
									<td>${log[3] }</td>
									<td>${log[4] }</td>
									<c:if test="${log[5] ==3}"><td>成功</td></c:if>
									<c:if test="${log[5] !=3}"><td style="color:red">失败</td></c:if>
									<td>${log[6] }</td>
									<td rowspan="3" class="td"><a class="worldcup" href="#">补转</a></td>
								</tr>
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
			$(".worldcup").click(function(){
				$("#bowlG").show();
				$.post($("base").attr("href")+"product/redo_worldcup",{},function(flag){
					if(flag.success){
						$("#bowlG").hide();
						alert("补转成功!");
						location.reload(true);
					}else{
						$("#bowlG").hide();
						alert("补转失败，请刷新重试!");
					};
				},"json");
				setTimeout(function(){
					$("#bowlG").hide();
					alert("时间过长，有可能失败，请刷新重试!");
				},1000*60*15);
			});
		});
	</script>
</body>
</html>