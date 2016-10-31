<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal hide fade dataModal" id="rtdataModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="display: inline-block;">添加实况数据</h3><span id="errorMsg"></span>
	</div>
	<div class="modal-body">
		<div class="box-content">
			<form class="form-horizontal addForm" id="rtaddForm">
			  <fieldset>
				<div class="control-group">
				  <label class="control-label" for="column1">站号 </label>
				  <input class="span1 focused" id="column1" type="text" name="column1">
				</div>
				<div class="control-group">
				  <label class="control-label" for="column2">北京时间 </label>
					<input class="Wdate fcdate span1 focused" id="column2" type="text" name="column2">
				  <label class="control-label" for="column3">服务气温 </label>
					<input class="span1 focused" id="column3" type="text" name="column3">
				</div>
				<div class="control-group">
				  <label class="control-label" for="column4">服务风向 </label>
					<input class="span1 focused" id="column4" type="text" name="column4">
				  <label class="control-label" for="column5">服务风级别 </label>
					<input class="span1 focused" id="column5" type="text" name="column5">
				  </div>
				  <div class="control-group">
				  <label class="control-label" for="column6">服务风速 </label>
					<input class="span1 focused" id="column6" type="text" name="column6">
				  <label class="control-label" for="column7">相对湿度 </label>
					<input class="span1 focused" id="column7" type="text" name="column7">
				</div>
				<div class="control-group">
				  <label class="control-label" for="column8">1小时降水 </label>
					<input class="span1 focused" id="column8" type="text" name="column8">
				  <label class="control-label" for="column9">6小时降水 </label>
					<input class="span1 focused" id="column9" type="text" name="column9">
				</div>
				<div class="control-group">
				  <label class="control-label" for="column10">12小时降水 </label>
					<input class="span1 focused" id="column10" type="text" name="column10">
				  <label class="control-label" for="column11">24小时降水 </label>
					<input class="span1 focused" id="column11" type="text" name="column11">
				</div>
				<div class="control-group">
				  <label class="control-label" for="column12">能见度 </label>
					<input class="span1 focused" id="column12" type="text" name="column12">
				  <label class="control-label" for="column13">服务气压 </label>
					<input class="span1 focused" id="column13" type="text" name="column13">
				</div>
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="saveRTdata">保存</a>
		<a href="#" class="cancelbtn" data-dismiss="modal">取消</a>
	</div>
</div>
<div class="modal hide fade dataModal" id="dzbdModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="display: inline-block;">单站补点</h3><span style="color: #F00;text-shadow: none;margin-left: 20px;">(多个站点以美式','分隔)</span>
	</div>
	<div class="modal-body">
		<div class="box-content">
			<form class="form-horizontal addForm" id="dzbdForm">
			  <fieldset>
				<div class="control-group">
					<label class="control-label" for="time1">时间1 </label>
					<input class="Wdate fcdate span1 focused dzbdtime" id="time1" type="text" name="time1">
				  	<label class="control-label" for="stations1">站号 </label>
					<input class="span1 focused stasinput" id="stations1" type="text" name="stations1">
				</div>
				<div class="control-group">
				  	<label class="control-label" for="time2">时间2 </label>
					<input class="Wdate fcdate span1 focused dzbdtime" id="time2" type="text" name="time2">
				  	<label class="control-label" for="stations1">站号 </label>
					<input class="span1 focused stasinput" id="stations2" type="text" name="stations2">
				</div>
				<div class="control-group">
				  	<label class="control-label" for="time3">时间3 </label>
					<input class="Wdate fcdate span1 focused dzbdtime" id="time3" type="text" name="time3">
				  	<label class="control-label" for="stations1">站号 </label>
					<input class="span1 focused stasinput" id="stations3" type="text" name="stations3">
				  </div>
				  <div class="control-group">
				  	<label class="control-label" for="time4">时间4 </label>
					<input class="Wdate fcdate span1 focused dzbdtime" id="time4" type="text" name="time4">
				  	<label class="control-label" for="stations4">站号 </label>
					<input class="span1 focused stasinput" id="stations4" type="text" name="stations4">
				</div>
				<div class="control-group">
				  	<label class="control-label" for="time5">时间5 </label>
					<input class="Wdate fcdate span1 focused dzbdtime" id="time5" type="text" name="time5">
				  	<label class="control-label" for="stations5">站号 </label>
					<input class="span1 focused stasinput" id="stations5" type="text" name="stations5">
				</div>
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="dzbdBtn">补转</a>
		<a href="#" class="cancelbtn" data-dismiss="modal">取消</a>
	</div>
</div>