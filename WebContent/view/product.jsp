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
		<%-- 	<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>3小时精细化指数产品</h2>
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
									  <th>操作建议</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>DMZ区数据库更新信息</td>
									<td>${dmz3h[0] }</td>
									<td>${dmz3h[1] }</td>
									<td>${dmz3h[2] }</td>
									<c:if test="${dmz3h[2]>=700000}">
									<td>正常</td>
									<td>无</td>
									</c:if>
									<c:if test="${dmz3h[2]<700000}">
									<td style="color:red">异常</td>
									<td style="word-break: break-all">请用product账号登陆10.14.82.51服务器确认/surface.data/WMCF/OUTPUT/OCF/FINAL/目录下数据源是否正常生成,未生成则联系梁乐宁,生成则重新执行入库程序并通知郭杰</td>
									</c:if>
								</tr>
							  </tbody>
						 </table>  
					</div>
				</div>
			</div> --%>
			<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
				  <div class="box-header well" data-original-title="">
						<h2>常规7天指数预报产品<span class="msg">(每天数据更新时间上午07:40，中午11:40，下午17:50)</span></h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							  <thead>
								  <tr>
									 <th width="15%">产品</th>
									  <th width="8%">更新时间</th>
									  <th width="8%">时次</th>
									  <th width="10%">数据量(条)</th>
									  <th width="8%">状态</th>
									  <th width="15%">操作</th>
									  <th>操作建议</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>DMZ区数据库更新信息</td>
									<td>${dmz7d[0] }</td>
									<td>${dmz7d[1] }</td>
									<td>${dmz7d[2] }</td>
									<c:if test="${dmz7d[2]>=500000}">
									<td>正常</td>
									<td>
										<a class="run_7d" href="#" type="08">补转08</a>
										<a class="run_7d" href="#" type="12">补转12</a>
										<a class="run_7d" href="#" type="20">补转20</a>
									</td>
									<td>无</td>
									</c:if>
									<c:if test="${dmz7d[2]<500000}">
									<td style="color:red">异常</td>
									<td>
										<a class="run_7d" href="#" type="08">补转08</a>
										<a class="run_7d" href="#" type="12">补转12</a>
										<a class="run_7d" href="#" type="20">补转20</a>
									</td>
									<td style="word-break: break-all">请用product账号登陆10.14.82.51服务器/usr/product/data/目录下查看预报文件是否到达,如预报文件无异常则重新执行入库程序并联系郭杰</td>
									</c:if>
								</tr>
							  </tbody>
						 </table>  
					</div>
				</div>
			</div>
			
			<%-- <div class="row-fluid sortable">
				<div class="box span12">
				  <div class="box-header well" data-original-title="">
						<h2>精细化预报产品</h2>
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
									  <th width="10%">操作建议</th>
									  <th>备注</th>
								  </tr>
							  </thead>   
							  <tbody>
								<tr>
									<td>3小时精细化预报产品</td>
									<td>${f3h[0] }</td>
									<td>${f3h[1] }</td>
									<td>${f3h[2] }</td>
									<c:if test="${f3h[0]==year && f3h[1]==time}">
									<td rowspan="3" class="td">正常</td>
									<td rowspan="3" class="td">无</td>
									</c:if>
									<c:if test="${f3h[0]!=year || f3h[1]!=time}">
									<td style="color:red"  rowspan="3" class="td">异常</td>
									<td rowspan="3" class="td"><a class="run" href="#">补转</a></td>
									</c:if>
									<td rowspan="3" style="word-break: break-all">异常时请用fine_user/fine_password账号登陆10.14.83.64服务器/surface.data/WMCF/OUTPUT/OCF/目录下LST_3h和LST_12h文件夹查看相应（00,12）时次数据文件是否正常,如若正常只需补转即可,反之请联系梁乐宁,待梁乐宁重新生成数据文件后再执行补转操作。</td>
								</tr>
								<tr>
									<td>12小时精细化预报产品</td>
									<td>${f12h[0] }</td>
									<td>${f12h[1] }</td>
									<td>${f12h[2] }</td>
									<c:if test="${f12h[2]>=200000}">
									<td>正常</td>
									<td>无</td>
									</c:if>
									<c:if test="${f12h[2]<200000}">
									<td style="color:red">异常</td>
									<td style="word-break: break-all">用nsmc_usr账号登录,查看/surface.data/WMCF/OUTPUT/OCF/FINE/目录下数据是否正常生成,如正常则执行相应入库程序并联系郭杰,如数据异常则联系梁乐宁并告知郭杰</td>
									</c:if>
								</tr>
								<tr>
									<td>24小时精细化预报产品</td>
									<td>${f24h[0] }</td>
									<td>${f24h[1] }</td>
									<td>${f24h[2] }</td>
									<c:if test="${f24h[2]>=20000}">
									<td>正常</td>
									<td>无</td>
									</c:if>
									<c:if test="${f24h[2]<20000}">
									<td style="color:red">异常</td>
									<td style="word-break: break-all"><a class="run" href="#">补转</a></td>
									</c:if>
								</tr>
							  </tbody>
						 </table>  
					</div>
				</div>
			</div> --%>
			
			</div><!--/#content.span10-->
			
				</div><!--/fluid-row-->
		<jsp:include page="./footer.jsp"/>
	</div>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script>
	$(function(){
		$("a.run_7d").click(function(){
			if(!confirm("请慎重操作，是否确认补转"+$(this).attr("type")+"？")){
				return;
			}
			$("#bowlG").show();
			var timer;
			$.post($("base").attr("href")+"product/redo_7d",{type:$(this).attr("type")},function(flag){
				if(flag.success){
					$("#bowlG").hide();
					alert("补转成功!");
					clearTimeout(timer);
				}else{
					$("#bowlG").hide();
					alert("补转失败，请刷新重试!");
					clearTimeout(timer);
				};
			},"json");
			/* alert("这个过程可能需要20分钟左右，请耐心等候。");
			timer = setTimeout(function(){
				$("#bowlG").hide();
				alert("时间过长，有可能失败，请刷新重试!");
			},1000*60*20); */
		});
	});
	</script>
</body>
</html>