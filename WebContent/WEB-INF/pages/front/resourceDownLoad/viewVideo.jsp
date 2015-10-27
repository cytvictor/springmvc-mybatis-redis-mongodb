<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../common/meta.jsp" %>
</head>
<body>
<div id="videoBox" style="width:720px;height:450px;"></div>
<div class="videoMask"></div>
<script type="text/javascript">
	
	$(".videoBtn").click(function() {
		$(".videoMask").fadeOut();
		$(this).addClass("bgBlue").siblings(".videoBtn").removeClass("bgBlue");
	});
	window.myPlayer = new FlashPlayer(
			$id("videoBox"),
			"${ctx}/public/flash/player/myflvPlayBack.swf?skin=${ctx}/public/flash/player/MinimaFlatCustomColorAll.swf&thumb=${ctx}/ResourceImageServlet/${thumb}");
	//视频播放按钮效果
	$(".videoMask").click(function() {
		$(this).fadeOut();
		myPlayer.playFile('${ctx}/Video/${videoPath}');
	});
	var isTouchSupport = (function() {
		var support = {}, events = [ 'touchstart', 'touchmove', 'touchend' ], el = document
				.createElement('div'), i;
		try {
			for (i = 0; i < events.length; i++) {
				var eventName = events[i];
				eventName = 'on' + eventName;
				var isSupported = (eventName in el);
				if (!isSupported) {
					el.setAttribute(eventName, 'return;');
					isSupported = typeof el[eventName] == 'function';
				}
				support[events[i]] = isSupported;
			}
			return support.touchstart || support.touchend || support.touchmove;
		} catch (err) {
			return false;
		}
	})();
	if (isTouchSupport) {
		$(".videoMask")[0].click();
	}
</script>
</body>
</html>