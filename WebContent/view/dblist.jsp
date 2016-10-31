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
<base href="<%=basePath%>"> 
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
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
				
			<!-- left menu starts -->
			<jsp:include page="./leftmenu.jsp"/>
			<!-- left menu ends -->
			
			<div id="content" class="span10">
			<!-- content starts -->
			<div>
				<ul class="breadcrumb">
					<li>
						<i class="icon-plus"></i>
						<a href="#" id="addAssignment">添加数据库</a>
					</li>
				</ul>
			</div>
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-list"></i> 数据库列表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>库表别名</th>
								  <th>IP地址</th>
								  <th>端口号</th>
								  <th>数据库名</th>
								  <th>用户名</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach var="db" items="${dblist }">
							<tr>
								<td class="center"><p title="${db.name }">${db.name }</p></td>
								<td class="center"><p title="${db.ip }">${db.ip }</p></td>
								<td class="center"><p title="${db.port }">${db.port }</p></td>
								<td class="center"><p title="${db.dbName }">${db.dbName }</p></td>
								<td class="center"><p title="${db.userName}">${db.userName }</p></td>
								<td class="center">
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
				</div><!--/span-->
			
			</div><!--/row-->
					<!-- content ends -->
			</div><!--/#content.span10-->
			
				</div><!--/fluid-row-->
		<jsp:include page="./footer.jsp"/>		
	</div>
	<jsp:include page="./assignment.jsp"/>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>