<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href=" ${pageContext.request.contextPath}/"
	aa='${pageContext.request.contextPath}'>
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
	<jsp:include page="./progressbar.jsp" />

	<div class="container-fluid container1">
		<!-- topbar starts -->
		<jsp:include page="./navbar.jsp" />
		<!-- topbar ends -->
		<div class="row-fluid row">

			<div style="display: none;" id="leftMenu">
				<jsp:include page="./leftmenu.jsp" />
			</div>

			<div class="dataContent">
				<div>
					<ul class="breadcrumb">
						<li><i class="icon-eye-open"></i> <a href="#" id="showMenu">显示主菜单</a>
						</li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12 result">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-align-justify"></i>实况数据补点 <span class="msg">北京最新实况数据时间是${latestTime
									}</span>
							</h2>

							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a>
							</div>
							
							<button class="btn btn-primary noty handout" style="float: right">
								<i class="icon-random icon-white"></i> 全站补点
							</button>
							<div style="float: right">
								<span style="font-weight: normal; font-size: 12px;">补点时间</span>
								<input type="text" class="Wdate fcdate" name="supplytime"
									id="supplytime" readonly="readonly" style="margin-bottom: 0;">
							</div>
							<button class="btn btn-primary noty dzbd"
								style="float: right; margin-right: 10px">单站补点</button>
							<div style="float: right;margin-right:10px">
							<input type="radio" name="type" value="old" checked="checked" id="old"><label for="old" style="display: inline-block;">旧流程</label>
							<input type="radio" name="type" value="new" id="new"><label for="new" style="display: inline-block;">新流程(CIMISS)</label>
							</div>
						</div>
						<div class="box-content">
							<div id="mainTab">
								<div>
									<form action="realtimedata/list" method="post"
										id="realtimeForm">
										<span class="w100">时间</span> <input type="text"
											class="Wdate fcdate" name="begin" id="begin"
											value="${begin }" readonly="readonly"> 至 <input
											type="text" class="Wdate fcdate" name="end" id="end"
											value="${end }" readonly="readonly"
											style="margin-left: 15px;"> </br> <select id="cityType"
											name="cityType" style="width: 80px;">
											<option value="stationid">站号</option>
											<option value="namecn">站名</option>
										</select> <input type="text" name="city" id="city" value="${city }"
											class="w176" style="margin-left: 20px;">
										<button class="btn btn-primary noty searchBtn">
											<i class="icon-search icon-white"></i> 查询
										</button>
										<i class="icon-plus"></i> <a href="#" id="addrtdata">添加实况数据</a>

									</form>

								</div>
								<table
									class="table table-striped table-bordered bootstrap-datatable datatable"
									id="realtimeTab">
									<thead>
										<tr>
											<th>站号</th>
											<th>站名</th>
											<th>北京时间</th>
											<th>气温原值</th>
											<th>服务气温</th>
											<th>风向原值</th>
											<th>风速原值</th>
											<th>服务风速级别</th>
											<th>相对湿度</th>
											<th>1小时降水</th>
											<th>6小时降水</th>
											<th>12小时降水</th>
											<th>24小时降水</th>
											<th>能见度</th>
											<th>本站气压原值</th>
											<th>服务气压</th>
											<th>正点观测总云量</th>
											<th>现在天气现象</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="data" items="${list }" varStatus="index">
											<tr id="${index.count }">
												<td name="column1"><p title='${data[1] }'>${data[1]
														}</p></td>
												<td name="column2"><p title='${data[0] }'>${data[0]
														}</p></td>
												<td name="column3"><p title='${data[2] }'>${data[2]
														}</p></td>
												<td name="column4"><p title='${data[3] }'>${data[3]
														}</p></td>
												<td name="column5"><p title='${data[4] }'>${data[4]
														}</p></td>
												<td name="column6"><p title='${data[5] }'>${data[5]
														}</p></td>
												<td name="column7"><p title='${data[6] }'>${data[6]
														}</p></td>
												<td name="column8"><p title='${data[7] }'>${data[7]
														}</p></td>
												<td name="column9"><p title='${data[8] }'>${data[8]
														}</p></td>
												<td name="column10"><p title='${data[9] }'>${data[9]
														}</p></td>
												<td name="column11"><p title='${data[10] }'>${data[10]
														}</p></td>
												<td name="column12"><p title='${data[11] }'>${data[11]
														}</p></td>
												<td name="column13"><p title='${data[12] }'>${data[12]
														}</p></td>
												<td name="column14"><p title='${data[13] }'>${data[13]
														}</p></td>
												<td name="column15"><p title='${data[14] }'>${data[14]
														}</p></td>
												<td name="column16"><p title='${data[15] }'>${data[15]
														}</p></td>
												<td name="column17"><p title='${data[16] }'>${data[16]
														}</p></td>
												<td name="column18"><p title='${data[17] }'>${data[17]
														}</p></td>
												<td class="center"><a class="btn btn-success dataedit"
													href="#"> <i class="icon-edit icon-white"></i> 更正
												</a> <a class='btn btn-primary datasave' href='#'> <i
														class='icon-white icon_save'></i> 保存
												</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/#content.span10-->

		</div>
		<!--/fluid-row-->
		<jsp:include page="./footer.jsp" />
		<jsp:include page="./rtdata.jsp" />
	</div>

	<jsp:include page="./js.jsp" />
	<script src="js/main/main.js"></script>
	<script src="js/main/realtime.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#cityType").val("${cityType}");
		});
	</script>
</body>
</html>