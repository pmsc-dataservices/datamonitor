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
				  <label class="control-label" for="name">数据库别名 </label>
				  <div class="controls">
					<input class="span4 focused" id="name" type="text" name="name">
					<input type="hidden" id="status" name="status" value="unexectued">
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
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="saveDatabase">保存</a>
		<a href="#" class="btn" data-dismiss="modal">取消</a>
	</div>
</div>
<script>
</script>