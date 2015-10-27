<%@ page contentType="text/html; charset=UTF-8"%>
<p class="infoTip">
重要提示：为了您的帐号安全，可定期修改您的帐号密码。如果您正在使用与其他网站相同的密码，建议您尽快修改。
</p>
<form onsubmit="return false;" id="pswEdit">
	<ul class="pwd_list">
		<li>
			<label class="lbl">当前密码：</label>
			<input type="password" autocomplete="off" name="oldpassword" class="txt" nullmsg="请输入原密码！" limitmsg="原密码长度限制6-18个字节!" limit="6,18" needcheck id="oldpsd">
			<i class="errorTip" id="oldpsdtip"></i>
		</li>
		<li>
			<label class="lbl">新密码：</label>
			<input type="password" autocomplete="off" name="newpassword" class="txt" nullmsg="请输入新密码！" limitmsg="密码长度限制6-18个字节!" limit="6,18" needcheck id="newpsd">
			<i class="errorTip" id="newpsdtip"></i>
		</li>
		<li>
			<label class="lbl">确认新密码：</label>
			<input type="password" autocomplete="off" name="newpassword2" class="txt" watchmsg="两次输入的密码不一致！" watchnode="newpassword" nullmsg="请再次输入密码！" limitmsg="密码长度限制6-18个字节!" limit="6,18" needcheck id="cnewpsd">
			<i class="errorTip" id="cnewpsdtip"></i>
		</li>
		<li class="subLi">
			<label class="lbl"> </label>
			<input class="btn" type="submit" value="保存" id="submit">
		</li>
	</ul>
</form>
<script>
<c:if test="${param.changePsdTab}">
	$(".navTab a[index=2]").click();
</c:if>
new BasicCheck({
	form: $id("pswEdit"),
	ajaxReq : function(){
		//ajax提交
		$.ajax({
			type: 'POST',
			url: '${ctx}/front/personalData/modifyPassword.do',
			data: $("#pswEdit").serialize(),
			success : function(d){
				if(d && d.result){
					Win.alert("修改成功！");
					setTimeout("window.location.reload()",3000);
				}else if(d && !d.result){
					$("#oldpsdtip").html(d.message);
				}else{
					$("#oldpsdtip").html("修改失败！");
				}
				
			}
		});
	}
});


</script>