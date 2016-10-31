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
<script src="js/main/rtwor.js"></script>

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
					  <h2><i class="icon-align-justify"></i>降水和风速排行监控
					  </h2>
					  
					  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
				  </div>
				  <div class="box-content">
					<div id="mainTab">
						<div>
						<form action="realtimedata/windorrain" method="post" id="rtwrForm">
						<span class="w100">查询类型</span>
						<label for="wind">风速</label><input type="radio" name="type" id="wind" value="OBSERVE.ELE_AWST_WIND" checked>
						<label for="rain">降水</label><input type="radio" name="type" id="rain" value="OBSERVE.ELE_AWST_RAIN" <c:if test="${type=='OBSERVE.ELE_AWST_RAIN'}">checked</c:if>>
						</br>
						<span class="w100">时间</span>
						<input type="text" class="Wdate fcdate" name="time" id="time" value="${time }" readonly="readonly">
						</br>
						<select id="cityType" name="cityType" style="width: 80px;">
							<option value="stationid">站号</option>
							<option value="name">站名</option>
						</select>
						<input type="text" name="city" id="city" value="${city }" class="w176" style="margin-left: 20px;">
						<button class="btn btn-primary noty searchBtn">
						<i class="icon-search icon-white"></i> 查询
						</button>
						
						</form>
						
						</div>
							<table class="table table-striped table-bordered bootstrap-datatable datatable" id="windTab">
							  <thead>
								  <tr>
								  	  <th style="display:none"></th>
								  	  <th>站号</th>
									  <th>站名</th>
									  <th>北京时间</th>
									  <th>风向</th>
									  <th>风向级别</th>
									  <th>风速</th>
									  <th>风速级别</th>
									  <th>操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="data" items="${windlist }" varStatus="index">
								<tr id="${index.count }">
									<td name="column1" style="display:none"><p>wind</p></td>
									<td name="column2"><p title='${data[1] }'>${data[1] }</p></td>
									<td name="column3"><p title='${data[0] }'>${data[0] }</p></td>
									<td name="column4"><p title='${data[2] }'>${data[2] }</p></td>
									<td name="column5"><p title='${data[3] }'>${data[3] }</p></td>
									<td name="column6"><p title='${data[4] }'>${data[4] }</p></td>
									<td name="column7"><p title='${data[5] }'>${data[5] }</p></td>
									<td name="column8"><p title='${data[6] }'>${data[6] }</p></td>
									<td class="center">
										<a class="btn btn-success dataedit" href="#">
											<i class="icon-edit icon-white"></i>  
											更正                                           
										</a>
										<a class='btn btn-primary datasave' href='#'>
											<i class='icon-white icon_save'></i>
											保存
										</a>
										<a class="btn btn-danger delBtn" href="#">
											<i class="icon-trash icon-white"></i> 
											删除
										</a>
									</td>
								</tr>
								</c:forEach>
							  </tbody>
						  </table>
							<table class="table table-striped table-bordered bootstrap-datatable datatable" id="rainTab">
							  <thead>
								  <tr>
								  	  <th style="display:none"></th>
								  	  <th>站号</th>
									  <th>站名</th>
									  <th>北京时间</th>
									  <th>降水量</th>
									  <th>操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="data" items="${rainlist }" varStatus="index">
								<tr id="${index.count }">
									<td name="column1" style="display:none"><p>rain</p></td>
									<td name="column2"><p title='${data[1] }'>${data[1] }</p></td>
									<td name="column3"><p title='${data[0] }'>${data[0] }</p></td>
									<td name="column4"><p title='${data[2] }'>${data[2] }</p></td>
									<td name="column5"><p title='${data[3] }'>${data[3] }</p></td>
									<td class="center">
										<a class="btn btn-success dataedit" href="#">
											<i class="icon-edit icon-white"></i>  
											更正                                          
										</a>
										<a class='btn btn-primary datasave' href='#'>
											<i class='icon-white icon_save'></i>
											保存
										</a>
										<a class="btn btn-danger delBtn" href="#">
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
	<script src="js/main/realtime.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#cityType").val("${cityType}");
		if("${type}" == "OBSERVE.ELE_AWST_RAIN"){
			$("#windTab_wrapper").hide();
		}else{
			$("#rainTab_wrapper").hide();
		}
	});	
	</script>
</body>
</html>