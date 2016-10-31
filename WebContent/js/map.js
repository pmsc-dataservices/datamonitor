(function () {
	window.addEventListener('load',map,false);

	function map () {
		addStyle ();
		var aEl = document.getElementsByTagName("*");
		for (var i = 0; i < aEl.length; i++) {
			if(aEl[i].getAttribute("_type") == "map"){
				init(aEl[i]);
			}
		};
	}

	function init (oEl) {
		var oDiv = document.createElement("div");
		oDiv.innerHTML = '<img src="img/map.png" alt="map"><ul><li class="xinjiang"></li><li class="xizang"></li><li class="qinghai"></li><li class="neimenggu"></li><li class="sichuan"></li><li class="gansu"></li><li class="ningxia"></li><li class="yunnan"></li><li class="gansu"></li><li class="guizhou"></li><li class="chongqing"></li><li class="shanxi"></li><li class="shan-xi"></li><li class="beijing"></li><li class="hebei"></li><li class="tianjin"></li><li class="liaoning"></li><li class="jilin"></li><li class="heilongjiang"></li><li class="shandong"></li><li class="anhui"></li><li class="jiangsu"></li><li class="shanghai"></li><li class="hunan"></li><li class="jiangxi"></li><li class="zhejiang"></li><li class="guangxi"></li><li class="guangdong"></li><li class="fujian"></li><li class="hainan"></li><li class="aomen"></li><li class="xianggang"></li><li class="taiwan"></li><li class="henan"></li><li class="hubei"></li></ul>';
		oDiv.className = "map";
		oEl.style.position = "relative";
		oEl.appendChild(oDiv);
	}

	function addStyle () {
		var style = document.createElement("link");
		style.href = "css/map.css";
		style.type = "text/css";
		style.rel = "stylesheet";
		document.getElementsByTagName("head")[0].appendChild(style);
	}
})();