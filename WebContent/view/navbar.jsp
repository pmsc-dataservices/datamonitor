<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#"> <img alt="Charisma Logo" src="img/logo.png" /> <span>数据监控系统</span></a>
				
				<!-- user dropdown starts -->
				<div class="btn-group pull-right" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> ${USER.name }</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#" class="changepwdBtn">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="user/logout">退出</a></li>
					</ul>
				</div>
				<!-- user dropdown ends -->
				
			</div>
		</div>
	</div>
	
<div class="modal hide fade" id="pwdModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="display: inline-block;">修改密码</h3><span id="pwdErrorMsg"></span>
	</div>
	<div class="modal-body">
		<div class="box-content">
			<form class="form-horizontal" id="pwdForm">
			  <fieldset>
				<div class="control-group">
				  <label class="control-label" for="oldPwd">原密码 </label>
				  <div class="controls">
					<input class="span4 focused" id="oldPwd" type="password" name="oldPwd">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="newPwd">新密码 </label>
				  <div class="controls">
					<input class="span4 focused" id="newPwd" type="password" name="newPwd">
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="newPwd2">确认密码 </label>
				  <div class="controls">
					<input class="span4 focused" id="newPwd2" type="password" name="newPwd2">
				  </div>
				</div>
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="pwd_ok">确定</a>
		<a href="#" class="btn cancelBtn" data-dismiss="modal">取消</a>
	</div>
</div>