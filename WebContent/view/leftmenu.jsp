<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="span2 main-menu-span">
	<div class="well nav-collapse sidebar-nav">
		<ul class="nav nav-tabs nav-stacked main-menu">
			<li class="nav-header hidden-tablet">主菜单</li>
			
			<li><a class="ajax-link" href="#">
				<i class="icon-wrench"></i>
				<span class="hidden-tablet">7天常规预报</span>
				</a>
				<ul class="second-menu">
					<li><a class="ajax-link" href="weatherdata/list">
						<span class="hidden-tablet">数据订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="weatherdata/errEdit">
						<span class="hidden-tablet">错站订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="jobmanage/forecast">
						<span class="hidden-tablet">常规预报监控</span>
						</a>
					</li>
					<li><a class="ajax-link" href="weatherdata/order">
						<span class="hidden-tablet">温度排名</span>
						</a>
					</li>
				</ul>
			</li>
			
			<li><a class="ajax-link" href="#">
				<i class="icon-wrench"></i>
				<span class="hidden-tablet">精细化预报</span>
				</a>
				<ul class="second-menu">
					<li><a class="ajax-link" href="forecast_15day/list?tabType=3h">
						<span class="hidden-tablet">3H15天订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="forecast_15day/list?tabType=12h">
						<span class="hidden-tablet">12H15天订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="forecast_15day/list?tabType=24h">
						<span class="hidden-tablet">24H15天订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="product/forecast">
						<span class="hidden-tablet">产品监控</span>
						</a>
					</li>
				</ul>
			</li>
			<li><a class="ajax-link" href="#">
				<i class="icon-wrench"></i>
				<span class="hidden-tablet">实况</span>
				</a>
				<ul class="second-menu">
					<li><a class="ajax-link" href="realtimedata/monitor">
						<span class="hidden-tablet">数据监控</span>
						</a>
					</li>
					<li><a class="ajax-link" href="realtimedata/list">
						<span class="hidden-tablet">数据补点</span>
						</a>
					</li>
				</ul>
			</li>
			
			<li><a class="ajax-link" href="#">
				<i class="icon-wrench"></i>
				<span class="hidden-tablet">手机客户端</span>
				</a>
				<ul class="second-menu">
					<li><a class="ajax-link" href="mobile/templist">
						<span class="hidden-tablet">温度订正</span>
						</a>
					</li>
					<li><a class="ajax-link" href="mobile/rainlist">
						<span class="hidden-tablet">降水订正</span>
						</a>
					</li>
				</ul>
			</li>
			
			<li><a class="ajax-link" href="#">
				<i class="icon-wrench"></i>
				<span class="hidden-tablet">指数</span>
				</a>
				<ul class="second-menu">
					<li><a class="ajax-link" href="product/compare">
						<span class="hidden-tablet">精细化指数产品监控</span>
						</a>
					</li>
					<li><a class="ajax-link" href="index/3d">
						<span class="hidden-tablet">3天指数监控</span>
						</a>
					</li>
				</ul>
			</li>
			
			<!-- <li><a class="ajax-link" href="product/worldcup">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">华风世界杯城市预报监控</span>
				</a>
			</li> -->
			
			<li><a class="ajax-link" href="realtimedata/windorrain">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">降水和风速排行监控</span>
				</a>
			</li>
			<li><a class="ajax-link" href="jobmanage/list">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">业务管理</span>
				</a>
			</li>
			
			<li><a class="ajax-link" href="./view/dataintegration.jsp">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">数据整合</span>
				</a>
			</li>
			<li><a class="ajax-link" href="http://10.1.64.154/stat/index_101.html" target="_blank">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">信息中心实况监控</span>
				</a>
			</li>
			<li><a class="ajax-link" href="filemonitor/list">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">文件监控</span>
				</a>
			</li>
			<li><a class="ajax-link" href="filemonitor/radar">
				<i class="icon-briefcase"></i>
				<span class="hidden-tablet">雷达基数据到达监控</span>
				</a>
			</li>
			<%-- <li <c:if test="${flag=='download'}">class="active"</c:if>><a class="ajax-link" href="weatherdata/todownload">
				<i class="icon-download-alt"></i>
				<span class="hidden-tablet">定制下载</span>
				</a>
			</li> --%>
		</ul>
	</div>
</div>