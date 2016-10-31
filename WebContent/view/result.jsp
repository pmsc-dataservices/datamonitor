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
<link href="css/main.css" rel="stylesheet">
</head>
<body>
		
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
				
			<!-- left menu starts -->
			<jsp:include page="./leftmenu.jsp"/>
			<!-- left menu ends -->
			
			<div id="content" class="span10">
			<div class="row-fluid sortable">
				<div class="box span12 result">
				  <div class="box-header well" data-original-title>
					  <h2><i class="icon-file"></i>任务执行情况</h2>
					  <div class="box-icon">
						  <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
				  </div>
				  <div class="box-content">
					<div id="external-events" class="well">
						<h4>任务</h4>
						<c:forEach var="ass" items="${asslist}">
						<div class="badge <c:if test="${ass.id==selectedAss.id }">selectedAss</c:if>" id="${ass.id }">${ass.name }</div>
						</c:forEach>
					</div>
					<div class="resulTab">
					<span id="filePath">文件存放路径:${selectedAss.path }</span>
						<table class="table table-striped table-bordered bootstrap-datatable datatable" id="resultTab">
						  <thead>
							  <tr>
								  <th>文件名称</th>
								  <th>大小</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:forEach var="f" items="${files }">
							<tr>
								<td>${f.name }</td>
								<td class="center">${f }</td>
								<td class="center">
									<a class="btn btn-success" href="#">
										<i class="icon-download-alt icon-white"></i>  
										下载                                            
									</a>
									<a class="btn btn-danger" href="#">
										<i class="icon-trash icon-white"></i> 
										删除
									</a>
								</td>
							</tr>
							</c:forEach>
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
	<script src="js/main/main.js"></script>
</body>
</html>