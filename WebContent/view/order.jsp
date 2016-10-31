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
<base href=" ${pageContext.request.contextPath}/" aa='${pageContext.request.contextPath}'>
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
		  <div class="row-fluid sortable ui-sortable">
				<div class="box span6" style="margin-top:0">
					<div class="box-header well" data-original-title="">
						<h2><i class="icon-arrow-up"></i>最新预报温度城市排名(最高温前10)</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>站名</th>
									  <th>日期</th>
									  <th>时次</th>
									  <th>时效</th>
									  <th>温度</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="order" items="${top10 }">
								<tr>
									<td>${order[1] }</td>
									<td>${order[2] }</td>
									<td>${order[3] }</td>
									<td>${order[4] }</td>
									<td>${order[5] }</td>
								</tr>
								</c:forEach>
							  </tbody>
						 </table>  
					</div>
				</div>
				
				<div class="box span6" style="margin-top:0">
					<div class="box-header well" data-original-title="">
						<h2><i class="icon-arrow-down"></i>最新预报温度城市排名(最低温前10)</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>站名</th>
									  <th>日期</th>
									  <th>时次</th>
									  <th>时效</th>
									  <th>温度</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="order" items="${last10 }">
								<tr>
									<td>${order[1] }</td>
									<td>${order[2] }</td>
									<td>${order[3] }</td>
									<td>${order[4] }</td>
									<td>${order[5] }</td>
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
</body>
</html>