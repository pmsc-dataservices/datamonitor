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
						<h2>3天指数监控
						<span class="msg">补转
						<a href="#" type="08" class="redo">08</a>
						<a href="#" type="12" class="redo">12</a>
						<a href="#" type="20" class="redo">20</a>
						</span>
						</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
						
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
							  <thead>
								  <tr>
									  <th>指数代码</th>
									  <th>指数名称</th>
									  <th>时次</th>
									  <th>时效-到站数</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="list" items="${index3dlist }">
									<tr>
										<td style="line-height:54px">${list[0] }</td>
										<td style="line-height:54px">${list[1] }</td>
										<td style="line-height:54px">${list[2] }</td>
										<td>${list[3] }</td>
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
	<script>
	$(function(){
		$("div#DataTables_Table_0_filter").remove();
		
		$("a.redo").click(function(){
			if(!confirm("是否确认补转指数数据（"+$(this).attr("type")+"）时次？")){
				return;
			}
			$("#bowlG").show();
			$.post($("base").attr("href")+"index/redo",{type:$(this).attr("type")},function(flag){
				if(flag.success){
					alert("补转成功!");
					location.reload(true);
				}else{
					alert("补转失败，请刷新重试!");
				};
				$("#bowlG").hide();
			},"json");
		});
	});
	</script>
</body>
</html>