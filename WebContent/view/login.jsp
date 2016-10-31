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
<link href="css/bootstrap-cerulean.css" rel="stylesheet">
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/charisma-app.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
</head>
<body>
		<div class="container-fluid container1">
		<div class="row-fluid row">
		
			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>数据监控系统</h2>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<c:if test="${failure }">
						<div class="alert alert-info">
							用户名或密码错误！
						</div>
					</c:if>
					<c:if test="${!failure }">
						<div class="mTop"></div>
					</c:if>
					<form class="form-horizontal" action="user/login" method="post">
						<fieldset>
							<div class="input-prepend" title="Username" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="name" id="username" type="text"/>
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock" style="background-image: url('./img/glyphicons-halflings.png');"></i></span><input class="input-large span10" name="pwd" id="password" type="password"/>
							</div>
							
							<div class="clearfix"></div>

							<p class="center span5" style="margin-top: 10px;">
							<button type="submit" class="btn btn-primary">登录</button>
							</p>
						</fieldset>
					</form>
				</div>
			</div>
				</div>
		<jsp:include page="./footer.jsp"/>
	</div>
	
</body>
</html>