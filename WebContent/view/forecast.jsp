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
			
			<%-- <div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>7天精细化预报产品</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th width="15%">产品</th>
									  <th width="10%">更新时间</th>
									  <th width="10%">时次</th>
									  <th width="15%">数据量(条)</th>
									  <th width="10%">状态</th>
									  <th width="10%">操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>3小时精细化预报产品</td>
									<td>${f3h[0] }</td>
									<td>${f3h[1] }</td>
									<td>${f3h[2] }</td>
									<c:if test="${normal}">
									<td rowspan="3" class="td">正常</td>
									</c:if>
									<c:if test="${!normal}">
									<td style="color:red"  rowspan="3" class="td">异常</td>
									</c:if>
									<td rowspan="3" class="td"><a class="run" href="#">补转</a></td>
								</tr>
								<tr>
									<td>12小时精细化预报产品</td>
									<td>${f12h[0] }</td>
									<td>${f12h[1] }</td>
									<td>${f12h[2] }</td>
								</tr>
								<tr>
									<td>24小时精细化预报产品</td>
									<td>${f24h[0] }</td>
									<td>${f24h[1] }</td>
									<td>${f24h[2] }</td>
								</tr>
							  </tbody>
						 </table>  
					</div>
				</div>
			</div> --%>
			
			<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>15天精细化预报产品<span class="msg">(每天08点数据更新时间07:45，中午数据更新时间11:45，20点数据更新时间18:45)</span></h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th width="15%">产品</th>
									  <th width="10%">更新时间</th>
									  <th width="5%">时次</th>
									  <th width="12%">数据量(条)</th>
									  <th width="12%">外网数据量</th>
									  <th width="5%">状态</th>
									  <th width="15%">操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>${f_15d.get(0)[0] }</td>
									<td>${f_15d.get(0)[1] }</td>
									<td>${f_15d.get(0)[2] }</td>
									<td>${f_15d.get(0)[3] }</td>
									<td>${f_15d.get(0)[4] }</td>
									<c:if test="${f_15d.get(0)[3]>1000000 && f_15d.get(1)[3]>270000 && normal_15d }">
									<td rowspan="3" class="td">正常</td>
									</c:if>
									<c:if test="${f_15d.get(0)[3]<1000000 || f_15d.get(1)[3]<270000 || !normal_15d }">
									<td style="color:red"  rowspan="3" class="td">异常</td>
									</c:if>
									<td rowspan="3" class="td">
										<a class="run_15d" href="#" type="06">补转06</a>
										<a class="run_15d" href="#" type="08">补转08</a>
										<a class="run_15d" href="#" type="12">补转12</a>
										<a class="run_15d" href="#" type="20">补转20</a>
									</td>
								</tr>
								<tr>
									<td>${f_15d.get(1)[0] }</td>
									<td>${f_15d.get(1)[1] }</td>
									<td>${f_15d.get(1)[2] }</td>
									<td>${f_15d.get(1)[3] }</td>
									<td>${f_15d.get(1)[4] }</td>
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
</body>
</html>