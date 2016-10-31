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
<script src="js/main/joblist.js"></script>
</head>
<body>
<jsp:include page="./progressbar.jsp"/>
		
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
			
				<jsp:include page="./leftmenu.jsp"/>
			
			<div class="dataContent span10">
			<div class="row-fluid sortable">
				<div class="box span12 result">
				  <div class="box-header well" data-original-title>
					  <h2><i class="icon-align-justify"></i>业务管理</h2>
					  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
				  </div>
				  <div class="box-content">
					<div id="mainTab">
						<table class="table table-striped table-bordered bootstrap-datatable datatable" id="jobTab">
						  <thead>
							  <tr>
							  	<th>业务名称</th>
							  	<th>业务负责人</th>
							  	<th>调度程序名称</th>
							  	<th>调度程序执行时间</th>
							  	<th>服务方式</th>
							  	<th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:forEach var="data" items="${list }">
								<tr>
									<td>${data[0] }</td>
									<td>${data[1] }</td>
									<td>${data[2] }</td>
									<td>${data[3] }</td>
									<td>${data[4] }</td>
									<td><a href="#" class="detail" jobName="${data[0] }">详细</a></td>
								</tr>
							</c:forEach>
						  </tbody>
					  </table>
						<div class="infoDiv">
							<div class="up"><a href="#"><span class="icon icon-blue icon-arrow-n"></span>收起</a></div>
							<div class="det">
								<ul>
									<li><label>负责人电话:</label><span class="phone">1</span></li>
									<li><label>业务用原数据库:</label><span class="sDB">2</span></li>
									<li><label>业务用源数据表:</label><span class="sTable">3</span></li>
									<li><label>业务用源数据信息:</label><span class="sDBinfo">4</span></li>
									<li><label>调度程序ip地址:</label><span class="jobIp">5</span></li>
									<li><label>目标服务器ip地址:</label><span class="tIp">6</span></li>
									<li><label>目标文件名格式:</label><span class="fNameFormat">7</span></li>
									<li><label>目标目录:</label><span class="fPath">8</span></li>
									<li><label>目标数据库:</label><span class="tDB">9</span></li>
									<li><label>目标数据表:</label><span class="tTable">10</span></li>
									<li><label>目标数据信息:</label><span class="tDBinfo">11</span></li>
									<li><label>数据使用者:</label><span class="user">12</span></li>
									<li><label>备注信息:</label><span class="note">13</span></li>
								</ul>
							</div>
						</div>
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
	<script type="text/javascript">
		window.onload=function(){
			$("#mainTab .dataTables_filter").hide();
			$("#cityType").val("${cityType}");
		};
	</script>
</body>
</html>