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
</head>
<body>
		
		<div class="container-fluid container1">
		<!-- topbar starts -->
	<jsp:include page="./navbar.jsp"/>
	<!-- topbar ends -->
		<div class="row-fluid row">
				
			<!-- left menu starts -->
			<jsp:include page="./leftmenu.jsp"/>
			<!-- left menu ends -->
			
			<div id="content" class="span10">
			<div class="row-fluid sortable">
				<div class="box span12" style="margin-top:0">
					<div class="box-header well" data-original-title="">
						<h2><i class="icon-pencil"></i> 定制数据</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal">
						  <fieldset>
						  	<!-- <div class="control-group">
							  <label class="control-label">数据库表 </label>
							  <div class="controls">
							  <select id="selectTab" class="select">
							  <option>请选择需要导出的数据库表</option>
							  <option value="base.observe_ele_awst">base.observe_ele_awst</option>
							  <option value="base.observe_ele_awst_temp">base.observe_ele_awst_temp</option>
							  <option value="history.history_ele_awst">history.history_ele_awst</option>
							  <option value="history.history_ele_awst2">history.history_ele_awst2</option>
							  </select>
							  </div>
							</div> -->
							<div class="control-group">
							  <label class="control-label">时间 </label>
							  <div class="controls">
							  <input type="text" class="Wdate fcdate" id="beginTime" onfocus="date1()">到<input type="text" class="Wdate fcdate" id="endTime" onfocus="date2()" style="margin-left: 15px">
							  </div>
							</div>
							<div class="control-group">
							  <label class="control-label" for="site">站点</label>
							  <div class="controls">
								<input type="text" id="site" style="width: 176px">
								<span class="icon32 icon-color icon-plus" id="addSite"></span>
							  </div>
							</div>
							<div class="control-group">
							  <div class="controls">
								<ul class="chzn-choices">
								</ul>
							  </div>
							</div>
							<div class="control-group">
							  <label class="control-label">导出字段 </label>
							  <div class="controls ulDiv">
							  <ul style="margin-left:0">
							  <li class="colum"><input type="checkbox" >1</li>
							  <li class="colum"><input type="checkbox" >1</li>
							  </ul>
							  </div>
							</div>
							<div class="form-actions" style="padding-left: 120px;">
							  <button type="submit" class="btn btn-primary">生成数据文件并下载</button>
							</div>
						  </fieldset>
						</form>   

					</div>
				</div>
				</div>
			</div>
			</div><!--/#content.span10-->
			<jsp:include page="./footer.jsp"/>
		</div><!--/container-fluid-->
				
	
	<jsp:include page="./js.jsp"/>
	<script src="js/main/main.js"></script>
	<script type="text/javascript">
		function date1(){
			WdatePicker({
						 dateFmt:'yyyyMMdd',
						 isShowOK:false,
						 isShowClear:false,
						 readOnly:true,
						 maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}'
						 });
		}
		function date2(){
			WdatePicker({
						 dateFmt:'yyyyMMdd',
						 isShowOK:false,
						 isShowClear:false,
						 readOnly:true,
						 minDate:'#F{$dp.$D(\'beginTime\',{d:1});}'
						 });
		}
	</script>
</body>
</html>