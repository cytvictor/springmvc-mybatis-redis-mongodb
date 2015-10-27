<%@ page contentType="text/html; charset=UTF-8"%>
<div id="avatar" class="setbox set_avatar">
	<div class="ori_avatar">
		<p>当前头像：</p>
		<img width="120" height="120" src="${sessionScope.session_domain_config.basedata}/HeadImageServlet/${user.headPic }" id="myAvatar">
	</div>
	<div class="edit_avatar">
		<p>编辑头像：</p>
		<div id="avatarEmbed">
		<embed width="450" height="358" wmode="transparent" src="${ctx }/public/flash/upavatar.swf?server=${ctx }/uploadHandle/uploadAvatar.do">
		</div>
	</div>
</div>
<script>
function setAvatarURL(url){
	//upavatar.swf 成功后调用；
	$id("myAvatar").src = "${sessionScope.session_domain_config.dynamic}/HeadImageServlet/" + url;
	$id("sideHeadPic").src = "${sessionScope.session_domain_config.dynamic}/HeadImageServlet/" + url;
	$id("headHeadPic").src = "${sessionScope.session_domain_config.dynamic}/HeadImageServlet/" + url;
	$id("personDataHead").src = "${sessionScope.session_domain_config.dynamic}/HeadImageServlet/" + url;
}
function reloadAvatar(){
	$id("avatarEmbed").innerHTML = $id("avatarEmbed").innerHTML;
}
</script>