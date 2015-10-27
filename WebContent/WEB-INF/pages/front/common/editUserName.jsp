<%@ page contentType="text/html; charset=UTF-8"%>
<li>
	<label class="lbl">用户名:</label>
	<input type="text" onblur="editUserName();" id="userName" value="${user.username }" maxlength="20" disabled>
	<a href="javascript:toEditUserName();" id="a_editName" class="blue ml20">修改</a>
</li>
<script>
$(function(){
	var userName = "${user.username }";
	$("#userName").val(userName);
	$("#userName").prop("disabled","disabled");
});
function toEditUserName(){
	$("#userName").removeAttr("disabled");
	$("#userName").focus();
	$("#a_editName").hide();
}

function editUserName(){
	var userName = $.trim($("#userName").val());
	if(!/${userIdReg}/.test(userName)){
		Win.alert({html:"用户名只能是6-20位的英文和数字组合！",mask:true,afterClose:toEditUserName});
		return;
	}
	
	$.post("${ctx}/front/personalData/editUserName.do",{userName:userName},function(data){
		if(data && data.result){
			$("#userName").prop("disabled","disabled");
			$("#a_editName").show();
		}else if(data && !data.result){
			Win.alert(data.message);
		}else{
			Win.alert("修改用户名出错！");
		}
	});	
}
</script>