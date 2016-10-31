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
<script src="js/map.js"></script>
<script src="js/main/realtime.js"></script>
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
						<h2><i class="icon-globe"></i>实况一体化数据监控结果</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<div _type="map">
							<span class="rtTitle"></span>	
						</div> 
						<span style="color:#369BD7">系统只监控当前小时</span>
						<table class="table table-bordered">
							<thead>
								  <tr>
									  <th width="10%">北京时间</th>
									  <th width="8%">省名</th>
									  <th width="74%">缺站信息</th>
									  <th>缺站总数</th>
								  </tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${delist }" var="detail">
							  		<tr>
							  			<c:forEach items="${detail.value }" var="item">
							  				<td>${item }</td>
							  			</c:forEach>
							  		<tr>
							  	</c:forEach>
							  
							  </tbody>
						</table>
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th width="10%">表名</th>
									  <th width="10%">北京时间</th>
									  <th width="12%">使用站点数量</th>
									  <th width="12%">温度错站数量</th>
									  <th width="12%">风速错站数量</th>
									  <th width="12%">相对湿度错站数量</th>
									  <th width="12%">能见度错站数量</th>
									  <th width="14%">小时降水量错站数量</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="rtdata" items="${list }">
									<tr>
										<td><c:if test="${rtdata[0]=='nation' }">国家站</c:if>
										<c:if test="${rtdata[0]=='area' }">区域站</c:if></td>
										<td>${rtdata[1] }</td>
										<td>${rtdata[2] }</td>
										<td>${rtdata[3] }</td>
										<td>${rtdata[4] }</td>
										<td>${rtdata[5] }</td>
										<td>${rtdata[6] }</td>
										<td>${rtdata[7] }</td>
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