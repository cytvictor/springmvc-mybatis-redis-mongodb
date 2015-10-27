<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<%@ include file="common/meta.jsp"%>
	<script src="${ctx }/public/js/basiccheck.js" type="text/javascript"></script>
	<style>
		html,body{height: 100%;}
		.containerLogin{background-image: url()\9;*background-image: rrl();}
		.lte8Bg{width: 100%;height: 100%;position:absolute;z-index:-100;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctx }/public/img/admin/loginBg.jpg', sizingMethod='scale');}
		.placeholderWaper label {line-height: 36px;padding-left: 40px; width: 270px;}
	</style>
</head>
<body>
	<div class="containerLogin">
		<div class="MaxCard">
			<img src="${ctx}/public/img/MaxCard.png" alt="" width="128" height="128" />
		</div>
		<div class="lte8Bg"></div>
		<div class="login">
			<div class="loginInner">
				<form id="loginForm">
					<div class="placeholderWaper">
						<label for="user">用户名</label>
						<input type="text" value="" name="username" id="user" placeholder="用户名" maxlength="20" needcheck nullmsg="请输入用户名！" limit="6,20" limitmsg="登录名或密码错误" reg="${userIdReg}" errormsg="登录名或密码错误" autocomplete="off" />
					</div>
					<div  class="placeholderWaper">
						<label for="password">密码</label>
						<input type="password" value="" name="password" id="password" placeholder="密码" needcheck nullmsg="请输入密码！" limit="6,18" limitmsg="登录名或密码错误" autocomplete="off" />
					</div>
					<!--input type="checkbox" name="rememberPassword" id="rememberPassword" class="rememberPassword"/><label for="rememberPassword">下次自动登录</label> 
					<a href="javascript:;" class="forgetPassword">忘记密码</a-->
					<input type="submit" class="loginBtn" value="登录" />
				</form>
			</div>
		</div>
	</div>
	<script>
	var isTouchSupport = (function () {
		var support = {}, events = ['touchstart', 'touchmove', 'touchend'],
		el = document.createElement('div'), i;
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
		} catch(err) {
			return false;
		}
	})();
	if (isTouchSupport) {
		$(".containerLogin .login").css('marginTop', "8%");	
	}
	
	if (UA.isIE9 || UA.isIElt9) {
		$(document).on('focus', 'input[placeholder]', function () {
			var $elm = $(this);
			$elm.siblings('label').hide();
		}).on('blur', 'input[placeholder]', function () {
			var $elm = $(this);
			if ($elm.val() == "") {
				$elm.siblings('label').show();
			}
		});
		$('.placeholderWaper label').show()
	}
	
	
	
	$("#user").focus();
	//表单验证
	new BasicCheck({
		form: $id("loginForm"),
		ajaxReq : function(){
			//ajax提交
			$.post('${ctx}/front/login/validateLogin.do', $("#loginForm").serializeArray(), function(data){
				if(data.result){
					window.location.href = "${ctx}/front/login/index.html";
				}else{
					Win.alert(data.message);
				}
			}, 'json');
		},
		warm: function warm(o, msg) {
			Win.alert(msg);
		}
	});
	</script>
</body>
</html>