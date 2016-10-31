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
						<h2>数据整合补转</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									  <th>产品</th>
									  <th>时次</th>
									  <th>频次</th>
									  <th>操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>华风字幕实况</td>
									<td></td>
									<td>1小时一次,整点后20分钟更新</td>
									<td>
									<a _name="华风字幕实况" class="dinte" href="#" type="realtime">补转</a>
									</td>
								</tr>
								<tr>
									<td>华风景点预报</td>
									<td>08,12,20</td>
									<td>一天三次</td>
									<td>
									<a _name="华风景点预报" class="dinte"  href="#" type="forecast">补转</a>
									</td>
								</tr>
								<tr>
									<td>华风手机文件</td>
									<td>00,12</td>
									<td>一天两次</td>
									<td>
									<a _name="华风手机文件" class="dinte"  href="#" type="mobilefile">补转</a>
									</td>
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
	<script type="text/javascript">
		$(function(){
			$("a.dinte").click(function(){
				if(!confirm("是否确认补转"+$(this).attr("_name")+"程序？")){
					return;
				}
				$("#bowlG").show();
				$.post($("base").attr("href")+"dataintegration/redo",{type:$(this).attr("type")},function(flag){
					if(flag.success){
						alert("补转成功!");
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