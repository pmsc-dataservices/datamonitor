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
				<div class="box"  style="margin-top:0">
					<div class="box-header well">
						<h2><i class="icon-list-alt"></i>雷达基数据到达监控</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					
					<div class="box-content">
						<div class="tabhead">
						<span>省名</span>
						<select id="stations">
							<c:forEach var="name" items="${provs }">
								<option value="${name }">${name }</option>
							</c:forEach>
						</select>
						<span>雷达站</span>
						<select id="radarstas">
							
						</select>
						<span>最新数据时间：<span id="timespan"></span>
							,平均延迟 <span id="avgtime"></span> 分钟
						</span>
					</div>
					
						<div id="radar"  class="center" style="height:300px" ></div>
					</div>
				</div>
				<div class="box">
					<div class="box-header well">
						<h2><i class="icon-list-alt"></i>雷达基数据统计</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<div class="tabhead">
						<select id="statistics">
								<option value="delaycount">最新数据延迟统计</option>
								<option value="avgdelay">雷达站最近一小时平均延迟统计</option>
						</select>
						</div>
						<div id="piechart" style="height:300px;width:50%"></div>
						<div id="piehover"></div>
					</div>
				</div>
			</div>
			</div><!--/#content.span10-->
			
				</div><!--/fluid-row-->
	<jsp:include page="./footer.jsp"/>
	</div>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script src="js/main/radar.js"></script>
</body>
</html>