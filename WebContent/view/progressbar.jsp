<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="bowlG" class="panel">
	<div id="bowl_ringG">
		Loading...
		<div class="ball_holderG">
			<div class="ballG"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("body").css({
		"overflow-x":"hidden",
		"overflow-y":"hidden"
	});
	document.body.onmousewheel=function(){return false;};
	$('#bowlG').show();
</script>