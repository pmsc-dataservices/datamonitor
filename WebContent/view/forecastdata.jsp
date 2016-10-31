<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal hide fade dataModal" id="forecastdataModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="display: inline-block;">添加7天预报数据</h3><span id="errorMsg"></span>
	</div>
	<div class="modal-body">
		<div class="box-content">
			<form class="form-horizontal addForm" id="forecastaddForm">
			  <fieldset>
				<div class="control-group">
				  <label class="control-label" for="stationID">站号 </label>
				  <input class="span1 focused" id="stationID" type="text" name="stationID">
				</div>
				<div class="control-group">
				  <label class="control-label" for="name">站名 </label>
					<input class="span1 focused" id="name" type="text" name="name">
				  <label class="control-label">日期 </label>
					<input class="Wdate fcdate span1 focused" id="fcdate" type="text" name="fcdate" onfocus="showDate()" readonly="readonly"> 
				</div>
				<div class="control-group">
				  <label class="control-label" for="fctime">时次 </label>
					<input class="span1 focused" id="fctime" type="text" name="fctime">
				  <label class="control-label" for="vti">时效 </label>
					<input class="span1 focused" id="vti" type="text" name="vti">
				  </div>
				  <div class="control-group">
				  <label class="control-label" for="weather1">前12天象 </label>
					<input class="span1 focused" id="weather1" type="text" name="weather1">
				  <label class="control-label" for="weather1_code">编码 </label>
					<input class="span1 focused" id="weather1_code" type="text" name="weather1_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="weather2">后12天象 </label>
					<input class="span1 focused" id="weather2" type="text" name="weather2">
				  <label class="control-label" for="weather2_code">编码 </label>
					<input class="span1 focused" id="weather2_code" type="text" name="weather2_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="wd1">前12风向 </label>
					<input class="span1 focused" id="wd1" type="text" name="wd1">
				  <label class="control-label" for="wd1_code">编码 </label>
					<input class="span1 focused" id="wd1_code" type="text" name="wd1_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="wd2">后12风向 </label>
					<input class="span1 focused" id="wd2" type="text" name="wd2">
				  <label class="control-label" for="wd2_code">编码 </label>
					<input class="span1 focused" id="wd2_code" type="text" name="wd2_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="ws1">前12风速 </label>
					<input class="span1 focused" id="ws1" type="text" name="ws1">
				  <label class="control-label" for="ws1_code">编码 </label>
					<input class="span1 focused" id="ws1_code" type="text" name="ws1_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="ws2">后12风速 </label>
					<input class="span1 focused" id="ws2" type="text" name="ws2">
				  <label class="control-label" for="ws2_code">编码 </label>
					<input class="span1 focused" id="ws2_code" type="text" name="ws2_code">
				</div>
				<div class="control-group">
				  <label class="control-label" for="temp1">前12温度 </label>
					<input class="span1 focused" id="temp1" type="text" name="temp1">
				  <label class="control-label" for="temp2">后12温度 </label>
					<input class="span1 focused" id="temp2" type="text" name="temp2">
				</div>
			  </fieldset>
			</form>   
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="saveFCdata">保存</a>
		<a href="#" class="cancelbtn" data-dismiss="modal">取消</a>
	</div>
</div>
<script>
</script>