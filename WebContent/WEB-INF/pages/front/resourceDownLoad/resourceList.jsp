<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/css/resource_zigong.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/public/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/public/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/public/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/public/js/resourceUpload.js"></script>
	<script type="text/javascript" src="${ctx}/public/js/easyuiComboboxOnSelect.js"></script>
</head>
<style>

</style>
<body style="width:960px;background:#fbfbfb !important;">
	<div class="header_zg">
		<span class="title0">广电资源列表</span>
		<a href="${session_domain_config.sso}/logout.html"  class="gr_out">退出</a>
		<a href="javaScript:void(0)" class="gr_info">${session_login_user.realName}</a>	
	</div>
	<div class="nav_title"><p class="p_cont"><span>资源超市</span></p></div>
	<div class="select_a">
		<ul class="commonSelect">
			<li>
			<label>学段：</label> 
			<select
				data-options="editable:false, valueField:&quot;id&quot;, textField:&quot;semesterName&quot;, onChange:onChangeSemester"
				style="width: 80px; display: none;"
				class="easyui-combobox combobox-f combo-f" id="semesterList"
				name="semesterList">
			</select>
			<label class="ml20">年级：</label> 
			<select
				data-options="editable:false, valueField:&quot;id&quot;, textField:&quot;classLevelName&quot;, onChange:onChangeClassLevel"
				style="width: 80px; display: none;"
				class="easyui-combobox combobox-f combo-f" id="classlevelList"
				comboname="classlevelList">
					<option value="">请选择</option>
			</select>
			
			<label class="ml20">学科：</label> 
			<select
				data-options="editable:false, valueField:&quot;id&quot;, textField:&quot;disciplineName&quot;, onChange:onChangeDiscipline"
				style="width: 80px; display: none;"
				class="easyui-combobox combobox-f combo-f" id="disciplineList"
				comboname="disciplineList">
					<option value="">请选择</option>
			</select>
			<div style="display: inline;" id="resourceCatalogContainer"></div> <label>状态：</label>
			<select style="width: 80px; display: none;" id="isDownLoad" comboname="isDownLoad"
				class="easyui-combobox combobox-f combo-f">
					<option value="">请选择</option>
					<option value="Y">已下载</option>
					<option value="N">未下载</option>
			</select>
			<label
				class="ml20">名称：</label> <input type="text" class="searchInput"
				placeholder="请输入您要搜索的资源名称" title="请输入您要搜索的资源名称" name="resourceName"
				id="resourceName"> 
			</li>
			<li><label>时间：</label> 
			<input
				style="width: 125px; display: none;" id="startTime"
				data-options="editable:false,showSeconds:false"
				class="easyui-datetimebox combo-f datetimebox-f"> 至：
				<input
				style="width: 125px; display: none;" id="endTime"
				data-options="editable:false,showSeconds:false"
				class="easyui-datetimebox combo-f datetimebox-f">
				<a onclick="javascript:queryResource();"
				class="btn bgBlue ml20" href="javascript:;">搜索</a></li>
		</ul>
	</div>
	
		<div class="sourceList2" id="contentContainer">
			<p class="title_pt">
				<span class="mc_span">名称</span><span  class="zt_span">状态</span><span
					class="cz_span">操作</span>
			</p>
			<div id="nodata" style="text-align: center;height:40px;font-size: 14px;display:none;line-height: 40px;"></div>
			<div id="dataList"></div>	
			 
		</div>
		
		<div class="page_yema" style="margin-top:20px;margin-bottom: 20px;" id="pageNavi">
		</div>
</body>
<script type="text/javascript">
	var ctx = '${ctx}';
	var loginFlag = "${not empty session_login_user}" == "true" ? true:false;
	var resourceColumnId = "";
	var mySplit;
	$(document).ready(function(){
		$.get("${ctx}/skeleton/getAllSemester.do", function(result){
			result.unshift({"id":"", "semesterName":"请选择"});
			$("#semesterList").combobox("loadData", result);
		}, "json");	
		
		//查询全部
		config = {
				node : $id("pageNavi"),
			    url : "${ctx}/getListInfo.do",
			    count : 8,
			    data : {},
			    callback : listInfosData,
			    type : 'post'};
		
		mySplit = new SplitPage(config);
		
	});
	function downloadVideo(obj){
		var resourceId = $(obj).next("input").next("input").next("input").next("input").attr("value");
		var resourceName = $(obj).next("input").next("input").next("input").next("input").next("input").attr("value");
		var highDefine = $(obj).next("input").attr("value");
		var normalDefine = $(obj).next("input").next("input").attr("value");
		$.post("${ctx}/addDownLoadLog.do",{"resourceId":resourceId,"resourceName":resourceName},function(){});
		if(highDefine != '' && highDefine != 'null'){
			window.location.href="${ctx}/Video/VideoServlet/"+highDefine+"?type=zigong";
		}else if((highDefine == '' || highDefine == 'null') && (normalDefine != '' && normalDefine != 'null')){
			window.location.href="${ctx}/Video/ResVideoServlet/"+normalDefine+"?type=zigong";
		}	
		}
		
	
	function queryResource() {
		$("#pageNavi").html("");
		var param = {
			startTime : $("#startTime").datetimebox("getValue"),
			endTime : $("#endTime").datetimebox("getValue"),
			resourceName : $("#resourceName").val()
		//.replace("%","%25")
		};
		if ($("#semesterList").length > 0) {
			param.semesterId = $("#semesterList").combobox("getValue");
		}
		if ($("#classlevelList").length > 0) {
			param.classlevelId = $("#classlevelList").combobox("getValue");
		}
		if ($("#disciplineList").length > 0) {
			param.disciplineId = $("#disciplineList").combobox("getValue");
		}
		if ($("#isDownLoad").length > 0) {
			param.isDownLoad = $("#isDownLoad").combobox("getValue");
		}
		config = {
			node : $id("pageNavi"),
			url : "${ctx}/getListInfo.do",
			count : 8,
			data : param,
			callback : listInfosData,
			type : 'post'
		};

		mySplit = new SplitPage(config);
	}

	function listInfosData(data, total) {
		$("#dataList").html("");
		$("#nodata").hide().children().remove();
		if (total == 0 || (!data)) {
			$("#nodata").html("<span >抱歉！没有找到相关资源！</span>");
			$("#nodata").show();
			$("#pageNavi").hide();
		} else {
			if (total > 8) {
				$("#pageNavi").show();
			} else {
				$("#pageNavi").hide();
			}
			var len = data.length;
			var html = "";
			if (len > 0) {
				for (var i = 0; i < len; i++) {
					var push = data[i];
					var starClass = "current-rating star"
							+ push.resources.evaluateAvg;
					var time = new Date(push.createTime);
					time = time.pattern("yyyy年MM月dd日 HH:mm");

					html += '<div class="sourceList2 clearfix">'
							+ '<span class="sourcePhoto"> '
							+ '	<a href="javaScript:void(0)" onclick ="viewVideo(this)">'
							+ '		<img height="97" src="${ctx}/ResourceImageServlet/'+push.resources.thumb+'">'
							+ '	</a>'
							+ '</span>'
							+ '<div class="sourceDetail xelIcon">'
							+ '	<div class="sourceEdit">'
							+ '		<a href="javaScript:;" onclick="downloadVideo(this)">下载</a>'
							+ '		<input type="hidden" value="'+push.resources.highDefine+'" />'
							+ '		<input type="hidden" value="'+push.resources.normalDefine+'" />'
							+ '		<input type="hidden" value="'+push.resources.studyResource+'" />'
							+ '		<input type="hidden" value="'+push.resourceId+'" />'
							+ '		<input type="hidden" value="'+push.resourceName+'" />'
							+ '		<input type="hidden" value="'+push.resources.thumb+'" />'
							+ '	</div>'
							+ '	<ul>'
							+ '		<li class="name">'
							+ '			<span>'
							+ 				push.resources.resourceName
							+ '			</span>'
							+ '		</li>'
							+ '		<li>'
							+ '			<label>学科：</label>'
							+ '			<span>'
							+ push.resources.baseDisciplineName
							+ '</span>'
							+ '		</li>'
							+ '		<li>'
							+ '			<label>评分：</label>'
							+ '			<span class="star-rating">'
							+ '				<span class="'+starClass+'"></span>'
							+ '			</span>'
							+ '			<span class="red">'
							+ push.resources.evaluateAvg
							+ '</span>分'
							+ '		</li>'
							+ '		<li>'
							+ '			<label>评选时间：</label>'
							+ '			<span>'
							+ time
							+ '</span>'
							+ '		</li>'
							+ '	</ul>'
							+ '</div>'
							+ '<div class="sourceState">'
							+ '	<span class="waitCheck">'
							+ push.isDownLoad
							+ '</span> ' + '</div>' + '</div>';
				}
			}
			$("#dataList").append(html);
		}

	};

	Date.prototype.pattern = function(fmt) {
		var o = {
			"M+" : this.getMonth() + 1, //月份         
			"d+" : this.getDate(), //日         
			"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时         
			"H+" : this.getHours(), //小时         
			"m+" : this.getMinutes(), //分         
			"s+" : this.getSeconds(), //秒         
			"q+" : Math.floor((this.getMonth() + 3) / 3), //季度         
			"S" : this.getMilliseconds() //毫秒         
		};
		var week = {
			"0" : "/u65e5",
			"1" : "/u4e00",
			"2" : "/u4e8c",
			"3" : "/u4e09",
			"4" : "/u56db",
			"5" : "/u4e94",
			"6" : "/u516d"
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		if (/(E+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1,((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
									: "/u5468")
									: "")
									+ week[this.getDay() + ""]);
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	};

	function viewVideo(obj) {
		var videoPath = '';
		var highDefine = $(obj).parent().next("div").children("div").children("input").eq(0).attr("value");
		var normalDefine = $(obj).parent().next("div").children("div").children("input").eq(1).attr("value");
		var resourceName = $(obj).parent().next("div").children("div").children("input").eq(4).attr("value");
		var thumb = $(obj).parent().next("div").children("div").children("input").eq(5).attr("value");
		if (highDefine != '' && highDefine != 'null') {
			videoPath = highDefine;
		} else if ((highDefine == '' || highDefine == 'null')
				&& (normalDefine != '' && normalDefine != 'null')) {
			videoPath = normalDefine;
		}
		Win.open({
			id : "viewVideo",
			url : "${ctx}/toReourceViewVideo.html?videoPath=" + videoPath + "&thumb=" + thumb,
			title : resourceName,
			width : 740,
			height : 530,
			mask : true
		});
	}
</script>
</html>

