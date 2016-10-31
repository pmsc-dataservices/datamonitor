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
				  <div class="box-header well " data-original-title>
					  <h2><i class="icon-align-justify"></i>数据订正
					  <span class="msg">精细化自动订正：<a href="#" class="autoup" type="06">05前预报</a> <a href="#" class="autoup" type="08">08</a> 
					  <a href="#" class="autoup" type="12">12</a> 
					  <a href="#" class="autoup" type="20">20</a></span>
					  </h2>
					  
					  <div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
						<button class="btn btn-primary noty push_update" style="float:right">
						<i class="icon-random icon-white"></i> 推送
					  </button>
					  <button class="btn btn-primary noty push_05" style="float:right;margin-right: 10px">
						<i class="icon-random icon-white"></i> 05前推送
					  </button>
				  </div>
				  <div class="box-content">
					<div id="mainTab">
						<div>
						<form action="weatherdata/list" method="post" id="forecastForm">
						<span class="w100">时间</span>
						<input type="text" class="Wdate fcdate" name="fcdate" id="fcdate" value="${fcdate }" onfocus="showDate()" readonly="readonly">
						</br>
						<select id="cityType" name="cityType" style="width: 80px;">
							<option value="stationid">站号</option>
							<option value="name">站名</option>
						</select>
						<input type="text" name="city" id="city" value="${city }" class="w176" style="margin-left: 20px;">
						</br>
						<span class="w100">时次</span>
						<input type="text" name="fctime" id="fctime" value="${fctime }" class="w176">
						<input type="checkbox" name="bj" id="bj" <c:if test="${bj }">checked</c:if>><label for="bj" style="display: inline;">北京专查</label>
						<button class="btn btn-primary noty searchBtn">
						<i class="icon-search icon-white"></i> 查询
						</button>
						<i class="icon-plus"></i>
						<a href="#" id="addfcdata">添加预报数据</a>
						</form>
						</div>
						<table class="table table-striped table-bordered bootstrap-datatable datatable" id="forecastTab">
						  <thead>
							  <tr>
							  	  <th>站号</th>
								  <th>站名</th>
								  <th>日期</th>
								  <th>时次</th>
								  <th>时效</th>
								  <th>发布日期</th>
								  <th>前12天象<span class="icon icon-blue icon-info pointer" title="天气现象编码对应表" type="tianxiang"></span></th>
								  <th>编码</th>
								  <th>后12天象</th>
								  <th>编码</th>
								  <th width="5%">前12风向<span class="icon icon-blue icon-info pointer" title="风向编码对应表" type="fengxiang"></span></th>
								  <th>编码</th>
								  <th width="5%">后12风向</th>
								  <th>编码</th>
								  <th width="5%">前12风速<span class="icon icon-blue icon-info pointer" title=风速编码对应表" type="fengsu"></span></th>
								  <th>编码</th>
								  <th width="5%">后12风速</th>
								  <th>编码</th>
								  <th width="5%">前12温度</th>
								  <th width="5%">后12温度</th>
								  <th width="70px">操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:forEach var="data" items="${datalist }" varStatus="index">
							<tr id="${index.count }">
								<td name="stationID"><p title="${data.stationID }">${data.stationID }</p></td>
								<td name="name"><p title="${data.name }">${data.name }</p></td>
								<td name="fcdate"><p title="${data.fcdate }">${data.fcdate }</p></td>
								<td name="fctime"><p title="${data.fctime }">${data.fctime }</p></td>
								<td name="vti"><p title="${data.vti }">${data.vti }</p></td>
								<td name="releasedate"><p title="${data.releasedate }">${data.releasedate }</p></td>
								<td name="weather1"><p title="${data.weather1 }">${data.weather1 }</p></td>
								<td name="weather1_code"><p title="${data.weather1_code }">${data.weather1_code }</p></td>
								<td name="weather2"><p title="${data.weather2 }">${data.weather2 }</p></td>
								<td name="weather2_code"><p title="${data.weather2_code }">${data.weather2_code }</p></td>
								<td name="wd1"><p title="${data.wd1 }">${data.wd1 }</p></td>
								<td name="wd1_code"><p title="${data.wd1_code }">${data.wd1_code }</p></td>
								<td name="wd2"><p title="${data.wd2 }">${data.wd2 }</p></td>
								<td name="wd2_code"><p title="${data.wd2_code }">${data.wd2_code }</p></td>
								<td name="ws1"><p title="${data.ws1 }">${data.ws1 }</p></td>
								<td name="ws1_code"><p title="${data.ws1_code }">${data.ws1_code }</p></td>
								<td name="ws2"><p title="${data.ws2 }">${data.ws2 }</p></td>
								<td name="ws2_code"><p title="${data.ws2_code }">${data.ws2_code }</p></td>
								<td name="temp1"><p title="${data.temp1 }">${data.temp1 }</p></td>
								<td name="temp2"><p title="${data.temp2 }">${data.temp2 }</p></td>
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
	<jsp:include page="./forecastdata.jsp"/>
	</div>
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script src="js/main/fcadd.js"></script>
	<script type="text/javascript">
		window.onload=function(){
			$("#mainTab .dataTables_filter").hide();
			$("#cityType").val("${cityType}");
		};
		function showDate(){
			WdatePicker({dateFmt:'yyyyMMdd',
						 isShowOK:false,
						 isShowClear:false,
						 readOnly:true
						 });
		}
	</script>
</body>
</html>