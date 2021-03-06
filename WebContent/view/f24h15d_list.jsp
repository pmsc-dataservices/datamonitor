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
</head>
<body>
<jsp:include page="./progressbar.jsp"/>
		
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
			
			<div style="display: none;" id="leftMenu">
				<jsp:include page="./leftmenu.jsp"/>
			</div>
			
			<div class="dataContent">
			<div>
				<ul class="breadcrumb">
					<li>
						<i class="icon-eye-open"></i>
						<a href="#" id="showMenu">显示主菜单</a>
					</li>
				</ul>
			</div>
			<div class="row-fluid sortable">
				<div class="box span12 result">
				  <div class="box-header well" data-original-title>
					  <h2><i class="icon-align-justify"></i>24小时15天精细化预报订正</h2>
					  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					  <button class="btn btn-primary noty handout" style="float:right">
						<i class="icon-random icon-white"></i> 分发
					  </button>
				  </div>
				  <div class="box-content">
					<div id="mainTab">
						<div>
						<form action="forecast_15day/list?tabType=24h" method="post" id="forecast_24h_15dayForm">
						<select id="cityType" name="cityType" style="width: 80px;">
							<option value="stationid">站号</option>
							<option value="name">站名</option>
						</select>
						<input type="text" name="city" id="city" value="${city }" class="w176" style="margin-left: 20px;">
						</br>
						<span class="w100">时效</span>
						<input type="text" name="vti" id="vti" value="${vti }" class="w176">
						<input type="checkbox" name="bj" id="bj" <c:if test="${bj }">checked</c:if>><label for="bj" style="display: inline;">北京专查</label>
						<button class="btn btn-primary noty searchBtn">
						<i class="icon-search icon-white"></i> 查询
						</button>
						</form>
						</div>
						<table class="table table-striped table-bordered bootstrap-datatable datatable" id="forecast_24h_15dayTab">
						  <thead>
							  <tr>
							  	  <th>站号</th>
								  <th>站名</th>
								  <th>日期</th>
								  <th>时效</th>
								  <th>发布时次</th>
								  <th>前12天象编码</th>
								  <th>前12天象(中)</th>
								  <th>前12天象(英)</th>
								  <th>后12天象编码</th>
								  <th>后12天象(中)</th>
								  <th>后12天象(英)</th>
								  <th>前12风向编码</th>
								  <th>前12风向(中)</th>
								  <th>前12风向(英)</th>
								  <th>后12风向编码</th>
								  <th>后12风向(中)</th>
								  <th>后12风向(英)</th>
								  <th>前12风速编码</th>
								  <th>前12风速(中)</th>
								  <th>前12风速(英)</th>
								  <th>后12风速编码</th>
								  <th>后12风速(中)</th>
								  <th>后12风速(英)</th>
								  <th>前12温度</th>
								  <th>后12温度</th>
								  <th width="70px">操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:forEach var="data" items="${datalist }" varStatus="index">
							<tr id="${index.count }">
								<td name="column1"><p title='${data[0] }'>${data[0] }</p></td>
								<td name="column2"><p title='${data[1] }'>${data[1] }</p></td>
								<td name="column3"><p title='${data[2] }'>${data[2] }</p></td>
								<td name="column4"><p title='${data[3] }'>${data[3] }</p></td>
								<td name="column5"><p title='${data[4] }'>${data[4] }</p></td>
								<td name="column6"><p title='${data[5] }'>${data[5] }</p></td>
								<td name="column7"><p title='${data[6] }'>${data[6] }</p></td>
								<td name="column8"><p title='${data[7] }'>${data[7] }</p></td>
								<td name="column9"><p title='${data[8] }'>${data[8] }</p></td>
								<td name="column10"><p title='${data[9] }'>${data[9] }</p></td>
								<td name="column11"><p title='${data[10] }'>${data[10] }</p></td>
								<td name="column12"><p title='${data[11] }'>${data[11] }</p></td>
								<td name="column13"><p title='${data[12] }'>${data[12] }</p></td>
								<td name="column14"><p title='${data[13] }'>${data[13] }</p></td>
								<td name="column15"><p title='${data[14] }'>${data[14] }</p></td>
								<td name="column16"><p title='${data[15] }'>${data[15] }</p></td>
								<td name="column17"><p title='${data[16] }'>${data[16] }</p></td>
								<td name="column18"><p title='${data[17] }'>${data[17] }</p></td>
								<td name="column19"><p title='${data[18] }'>${data[18] }</p></td>
								<td name="column20"><p title='${data[19] }'>${data[19] }</p></td>
								<td name="column21"><p title='${data[20] }'>${data[20] }</p></td>
								<td name="column22"><p title='${data[21] }'>${data[21] }</p></td>
								<td name="column23"><p title='${data[22] }'>${data[22] }</p></td>
								<td name="column24"><p title='${data[23] }'>${data[23] }</p></td>
								<td name="column25"><p title='${data[24] }'>${data[24] }</p></td>
								<td class="center">
									<a class="btn btn-success dataedit" href="#">
										<i class="icon-edit icon-white"></i>  
										更正                                           
									</a>
									<a class='btn btn-primary datasave' href='#'>
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
			<div class="row-fluid sortable ui-sortable">	
				<div class="box span12" id="tianxiang">
					<div class="box-header well" data-original-title="">
						<h2>天气现象编码对应表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>天象类型</th>
									  <th>编码</th>
									  <th>天象类型</th>
									  <th>编码</th>
									  <th>天象类型</th>
									  <th>编码</th>
									  <th>天象类型</th>
									  <th>编码</th>
									  <th>天象类型</th>
									  <th>编码</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="code" items="${txcode }" begin="0" step="5" varStatus="index">
								<tr>
									<td>${code[0] }</td>
									<td>${code[1] }</td>
									<td>${txcode[index.count*5-4][0] }</td>
									<td>${txcode[index.count*5-4][1] }</td>
									<td>${txcode[index.count*5-3][0] }</td>
									<td>${txcode[index.count*5-3][1] }</td>
									<td>${txcode[index.count*5-2][0] }</td>
									<td>${txcode[index.count*5-2][1] }</td>
									<td>${txcode[index.count*5-1][0] }</td>
									<td>${txcode[index.count*5-1][1] }</td>
								</tr>
								</c:forEach>
							  </tbody>
						 </table>  
					</div>
				</div>
				</div>
			<div class="row-fluid sortable ui-sortable">
				<div class="box span6" id="fengxiang">
					<div class="box-header well" data-original-title="">
						<h2>风向编码对应表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>风向类型</th>
									  <th>编码</th>
									  <th>风向类型</th>
									  <th>编码</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="code" items="${fxcode }" begin="0" step="2" varStatus="index">
								<tr>
									<td>${code[0] }</td>
									<td>${code[1] }</td>
									<td>${fxcode[index.count*2-1][0] }</td>
									<td>${fxcode[index.count*2-1][1] }</td>
								</tr>
								</c:forEach>
							  </tbody>
						 </table>   
					</div>
				</div>
			
				<div class="box span6" id="fengsu">
					<div class="box-header well" data-original-title="">
						<h2>风速编码对应表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							  <thead>
								  <tr>
									  <th>风速类型</th>
									  <th>编码</th>
									  <th>风速类型</th>
									  <th>编码</th>
								  </tr>
							  </thead>   
							  <tbody>
								<c:forEach var="code" items="${fscode }" begin="0" step="2" varStatus="index">
								<tr>
									<td>${code[0] }</td>
									<td>${code[1] }</td>
									<td>${fscode[index.count*2-1][0] }</td>
									<td>${fscode[index.count*2-1][1] }</td>
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
	<script src="js/main/forecast.js"></script>
	<script type="text/javascript">
		function showDate(){
			WdatePicker({dateFmt:'yyyyMMdd',
						 isShowOK:false,
						 isShowClear:false,
						 readOnly:true
						 });
		}
		$(function(){
			$("#cityType").val("${cityType}");
		});
	</script>
</body>
</html>