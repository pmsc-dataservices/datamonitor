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
						<h2>源文件</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					
					<div class="box-content">
					<div class="tabhead">
						<span>IP</span>
						<select>
							<option value="">10.14.83.67</option>
						</select>
						<span>业务</span>
						<select id="sourceSel">
							<c:forEach var="name" items="${sourceServiceName }">
								<option value="${name }">${name }</option>
							</c:forEach>
						</select>
						</br>
						<!-- <span>路径</span>
						<input id="sourcepath" class="filepath" type="text" readonly style="margin-left: 0"> -->
					</div>
						<div class="tabdiv">
							<table class="table table-bordered" id="sourceTab">
							  <thead>
								  <tr>
								  	  <th width="5%">序号</th>
									  <th width="75%">文件路径</th>
									  <th>文件大小(B)</th>
									  <th>文件生成时间</th>
								  </tr>
							  </thead>   
							  <tbody>
								
							  </tbody>
						 </table> 
						</div> 
					</div>
				</div>
			</div>
			
			<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>目的地文件</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
					<div class="tabhead">
						<span>IP</span>
						<select>
							<option value="">10.0.122.171</option>
						</select>
						<span>业务</span>
						<select id="targetSel">
							<c:forEach var="name" items="${targetServiceName }">
								<option value="${name }">${name }</option>
							</c:forEach>
						</select>
						</br>
						<!-- <span>路径</span>
						<input id="targetpath" class="filepath" type="text" readonly style="margin-left: 0"> -->
					</div>
						<div class="tabdiv">
							<table class="table table-bordered" id="targetTab">
							  <thead>
								  <tr>
								  	  <th width="5%">序号</th>
									  <th width="75%">文件路径</th>
									  <th>文件大小(B)</th>
									  <th>文件生成时间</th>
								  </tr>
							  </thead>   
							  <tbody>
							  </tbody>
						 </table>
						</div>  
					</div>
				</div>
			</div>
			</div><!--/#content.span10-->
			
				</div><!--/fluid-row-->
	<jsp:include page="./footer.jsp"/>
	</div>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/file.js"></script>
</body>
</html>