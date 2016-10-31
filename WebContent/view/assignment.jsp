<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal hide fade" id="myModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="display: inline-block;">定时任务</h3><span id="errorMsg">该任务名已存在!</span>
	</div>
	<div class="modal-body">
		<div class="box-content">
			<form class="form-horizontal" id="addForm">
			  <fieldset>
				<div class="control-group">
				  <label class="control-label" for="name">服务名称 </label>
				  <div class="controls">
					<input class="span4 focused" id="name" type="text" name="name">
					<input type="hidden" id="status" name="status" value="unexectued">
				  </div>
				</div>
				<div class="control-group" style="display:none">
				  <label class="control-label">连接数据库类型 </label>
				  <div class="controls">
					 <label class="radio">
						<input type="radio" name="type" id="oracle" value="oracle" checked="">
						oracle
					  </label>
					  <label class="radio period">
						<input type="radio" name="type" id="sqlserver" value="sqlserver">
						<span>sqlserver</span>
					  </label>
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="ip">数据库IP </label>
				  <div class="controls">
					<input class="span4 focused" id="ip" type="text" name="ip">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="port">数据库端口 </label>
				  <div class="controls">
					<input class="span4 focused" id="port" type="text" name="port">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="database">数据库名 </label>
				  <div class="controls">
					<input class="span4 focused" id="database" type="text" name="database">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="username">用户名</label>
				  <div class="controls">
					<input class="span4 focused" id="username" type="text" name="username">
				  </div>
				  </div>
				  <div class="control-group">
				  <label class="control-label" for="pwd">密码 </label>
				  <div class="controls">
					<input class="span4 focused" id="pwd" type="password" name="pwd">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="sql">SQL语句 </label>
				  <div class="controls">
					<textarea class="span4 focused" id="sql" name="sql" style="resize:vertical;height:50px"
					placeholder="请输入要执行的SQL语句"></textarea>
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="typeahead">执行时间 </label>
				  <div class="controls">
				  <label class="radio">
						<input type="radio" name="time" id="immediately" value="immediately" checked="">
						立即
					  </label>
					  <label class="timelabel">
						<input type="radio" name="time" id="timing" value="timing">
						<select id="selectTime" data-rel="chosen" style="width:50px">
					      <c:forEach begin="0" end="23" var="index">
					      <option value="${index }">${index }</option>
					      </c:forEach>
					    </select> <span class="timespan">点</span>
					  </label>
				  </div>
				</div> 
				<div class="control-group">
				  <label class="control-label">执行方式 </label>
				  <div class="controls">
					  <label class="radio">
						<input type="radio" name="executivemode" id="oncemode" value="once" checked="">
						一次执行
					  </label>
					  <label class="radio period">
						<input type="radio" name="executivemode" id="periodmode" value="period">
						<span>周期执行</span>
					  </label>
				  </div>
				</div>  
				<div class="control-group" style="display:none" id="periodDiv">
				  <label class="control-label">执行周期 </label>
				  <div class="controls">
				  	<label>
					  <select id="period" name="period" data-rel="chosen" style="width:50px">
					      <c:forEach begin="1" end="24" var="index">
					      <option value="${index }">${index }</option>
					      </c:forEach>
					  </select> <span class="timespan">时</span>
					  </label>
				  </div>
				</div>   
				<div class="control-group">
				  <label class="control-label" for="path">文件保存路径 </label>
				  <div class="controls">
					<input class="span4 focused" id="path" name="path" type="text">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="fileName">文件保存名称 </label>
				  <div class="controls">
					<input class="span4 focused" id="fileName" name="fileName" type="text">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="suffix">文件名后缀 </label>
				  <div class="controls">
					<input class="span4 focused" id="suffix" name="suffix" type="text">
				  </div>
				</div>
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="saveAssignment">保存</a>
		<a href="#" class="btn" data-dismiss="modal">取消</a>
	</div>
</div>
<script>
</script>