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
						<a href="#" id="addAssignment">创建定时任务</a>
					</li>
				</ul>
			</div>
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-tasks"></i> 任务列表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th width="8%">服务名称</th>
								  <th>SQL语句</th>
								  <th width="11%">所在库</th>
								  <th width="12%">保存文件名称</th>
								  <th width="12%">保存文件路径</th>
								  <th width="8%">执行方式</th>
								  <th width="8%">状态</th>
								  <th width="16%">操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach var="assignment" items="${asslist }">
							<tr>
								<td class="center"><p title="${assignment.name }">${assignment.name }</p></td>
								<td class="center"><p title="${assignment.sql }">${assignment.sql }</p></td>
								<td class="center"><p title="${assignment.ip }${'/'}${assignment.database}">${assignment.ip }${'/'}${assignment.database}</p></td>
								<td class="center"><p title="${assignment.fileName }${'_yyyymmddHH'}${assignment.suffix}${'.txt'}">${assignment.fileName }${'_yyyymmddHH'}${assignment.suffix}${'.txt'}</p></td>
								<td class="center"><p title="${assignment.path}">${assignment.path }</p></td>
								<td class="center">
								<c:if test="${assignment.executivemode=='once' }">
									<p title="一次执行">一次执行</p>
								</c:if>
								<c:if test="${assignment.executivemode=='period' }">
									<p title="周期执行">周期执行</p>
								</c:if>
								</td>
								<td class="center">
								<c:if test="${assignment.status=='unexectued' }">
									<p title="未执行">未执行</p>
								</c:if>
								<c:if test="${assignment.status=='executing' }">
									<p title="执行中">执行中</p>
								</c:if>
								<c:if test="${assignment.status=='success' }">
									<p title="已执行">已执行</p>
								</c:if>
								<c:if test="${assignment.status=='failure' }">
									<p title="执行失败">执行失败</p>
								</c:if>
								</td>
								<td class="center">
									<a class="btn btn-info" href="#">
										<i class="icon-edit icon-white"></i>  
										编辑                                            
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