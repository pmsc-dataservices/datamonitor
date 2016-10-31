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
					  <h2><i class="icon-align-justify"></i>手机客户端-温度订正
					  </h2>
					  
					  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
				  </div>
				  <div class="box-content">
					<div id="mainTab">
						<div>
						<form action="mobile/templist" method="post" id="tempForm">
						<span class="w100">站名</span>
						<input type="text" name="city" id="city" value="${city }" class="w176" style="margin-left: 20px;">
						<button class="btn btn-primary noty searchBtn">
						<i class="icon-search icon-white"></i> 查询
						</button>
						</form>
						
						</div>
							<table class="table table-striped table-bordered bootstrap-datatable datatable" id="tempTab">
							  <thead>
								  <tr>
								  	  <th>站号</th>
									  <th>站名</th>
									  <th>北京时间</th>
									  <th>产品名称</th>
									  <th>温度</th>
									  <th>操作</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="data" items="${templist }" varStatus="index">
								<tr id="${index.count }">
									<td name="column1"><p title='${data[0] }'>${data[0] }</p></td>
									<td name="column2"><p title='${data[1] }'>${data[1] }</p></td>
									<td name="column3"><p title='${data[2] }'>${data[2] }</p></td>
									<td name="column4"><p title='${data[3] }'>${data[3] }</p></td>
									<td name="column5"><p title='${data[4] }'>${data[4] }</p></td>
									<td class="center">
										<a class="btn btn-success mdataedit" href="#">
											<i class="icon-edit icon-white"></i>  
											更正                                           
										</a>
										<a class='btn btn-primary mdatasave hide' href='#'>
											<i class='icon-white icon_save'></i>
											保存
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
	<script type="text/javascript">
	$(function(){
		$("#mainTab .dataTables_filter").hide();
		$(".searchBtn").click(function(){
			$("#tempForm").submit();
		});
		$(".mdataedit").click(function(){
			var text = $(this).parent().prev().children("p").text();
			var input = "<input type='text' style='width: 80%;margin-bottom: 0;height: 15px' value='" + text + "'>";
			$(this).parent().prev().html(input);
			$(this).hide();
			$(this).next().show();
		});
		
		$(".mdatasave").click(function(){
			var text = $(this).parent().prev().children("input").val();
			var obj = {station:$(this).parents("tr").children("td:eq(0)").children("p").text(),
					   c_bjtime:$(this).parents("tr").children("td:eq(2)").children("p").text(),
					   ntemp:text};
			var _this = $(this);
			$.post($("base").attr("href")+"mobile/edittemp",obj,function(json){
				$("#bowlG").hide();
				if(json.result){
					alert("更正成功!");
					var p = "<p title='" + text + "'>" + text + "</p>";
					_this.parent().prev().html(p);
					_this.hide();
					_this.prev().show();
				}
				else{
					alert("更正失败!");
				}
			},"json");
		});
	});
	</script>
</body>
</html>