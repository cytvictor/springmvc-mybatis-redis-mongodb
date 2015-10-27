<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${ctx}/public/bigfile/style.css"/>
	<script type="text/javascript" src="${ctx}/public/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/resourceUpload.js"></script>
	<script type="text/javascript" src="${ctx}/public/js/easyuiComboboxOnSelect.js"></script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<%@ include file="side_define.jsp"%>
		<script type="text/javascript">
			setSideNav("resourceCenter");
		</script>
		<div class="content">
			<h4 class="contentNav">
				<c:if test="${not empty schoolTag and schoolTag eq 'yes'}">
					<a class="backup" href="${ctx}/front/schoolresource/uploaded.html">&lt;&lt;返回</a>
				</c:if>
				<c:if test="${empty schoolTag or schoolTag eq 'no'}">
					<c:if test="${loginUser.userType eq 'TEACHER' or loginUser.userType eq 'STUDENT'}">
						<a class="backup" href="${ctx}/front/resource/uploaded.html">&lt;&lt;返回</a>
					</c:if>
					<c:if test="${loginUser.userType eq 'ORG' or loginUser.userType eq 'ORG_MANAGER'}">
						<a class="backup" href="${ctx}/front/orgresource/uploaded.html">&lt;&lt;返回</a>
					</c:if>
				</c:if>
				上传资源
			</h4>
			<form action="${ctx}/front/resUpload/${module }/addResource.html" method="post" id="addResourceForm">
			<input type="hidden" name="baseSemesterId" id="baseSemesterId" value="">
			<input type="hidden" name="baseClasslevelId" id="baseClasslevelId" value="">
			<input type="hidden" name="baseDisciplineId" id="baseDisciplineId" value="">
			<input type="hidden" name="baseVersionId" id="baseVersionId" value="">
			<input type="hidden" name="baseFascicleId" id="baseFascicleId" value="">
			<input type="hidden" name="baseChapterId" id="baseChapterId" value="">
			<input type="hidden" name="basePartId" id="basePartId" value="">
			
			<input type="hidden" name="maxUploadSizeHDVideo" id="maxUploadSizeHDVideo" value="${maxUploadSizeHDVideo }">
			<input type="hidden" name="maxUploadSizeDocument" id="maxUploadSizeDocument" value="${maxUploadSizeDocument }">
			<input type="hidden" name="maxUploadSizePicture" id="maxUploadSizePicture" value="${maxUploadSizePicture }">
			<input type="hidden" name="maxUploadSizeThumb" id="maxUploadSizeThumb" value="${maxUploadSizeThumb }">
			
			<input type="hidden" name="resourceCatalogFirstId" id="resourceCatalogFirstId" value="">
			<input type="hidden" name="resourceCatalogSecondId" id="resourceCatalogSecondId" value="">
			<input type="hidden" name="knowledgeIds" id="knowledgeIds">
			<input type="hidden" name="thumbSequence" id="thumbSequence">
			<input type="hidden" name="uploading" id="uploading" value=""/>
			<input type="hidden" name="resourceColumnId" id="resourceColumnId" value="${resourceColumnId }"/>
			<input type="hidden" name="baseCatalogKnowledgeFlag" id="baseCatalogKnowledgeFlag" value="${resourceColumn.baseCatalogKnowledgeFlag }"/>
			<input type="hidden" name="resourceCatalogFlag" id="resourceCatalogFlag" value="${resourceColumn.resourceCatalogFlag }"/>
			<div class="otContent">
				<div class="selectBoxTop">
					<p>类型：
						<c:forEach items="${allResourceColumn }" var="resourceColumn">
							<label><input type="radio" name="radioResourceColumnId" data-url="${ctx}/front/resUpload/${module }/upload/${resourceColumn.id }.html" <c:if test="${resourceColumnId == resourceColumn.id }">checked="checked"</c:if>>${ resourceColumn.columnName }</label>
						</c:forEach>
					</p>
					<h5><b class="red">∗</b>标题名称：<input type="text" id="resourceName" name="resourceName" maxlength="30"/><span class="gray ml10">可输入30个汉字</span></h5>
					<h5>资源简介：</h5>
					<textarea rows="3" style="width:400px;" id="description" name="description" maxlength="160"></textarea><span class="gray ml10">可输入160个汉字</span>
				</div>
				<div class="selectBox mt20">
					<c:if test="${resourceColumn.baseCatalogKnowledgeFlag == 'Y' }">
						<h5>关联章节：</h5>
						<ul>
							<li>
								<label><b class="red">∗</b>学段：</label>
								<select id='semesterList' class="easyui-combobox" name='semesterList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"semesterName", onChange:onChangeSemester'>
								</select>
								<label class="ml20"><b class="red">∗</b>年级：</label>
								<select id='classlevelList' class="easyui-combobox" name='classlevelList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"classLevelName", onChange:onChangeClassLevel'>
									<option value="">请选择</option>
								</select>
								<label class="ml20"><b class="red">∗</b>学科：</label>
								<select id='disciplineList' class="easyui-combobox" name='disciplineList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"disciplineName", onChange:onChangeDiscipline'>
									<option value="">请选择</option>
								</select>
								<label class="ml20">版本：</label>
								<select id='versionList' class="easyui-combobox" name='versionList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"name", onChange:onChangeVersion'>
									<option value="">请选择</option>
								</select>
								<label class="ml20">分册：</label>
								<select id='fascicleList' class="easyui-combobox" name='fascicleList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onChangeFascicle'>
									<option value="">请选择</option>
								</select>
								<label class="ml20">章：</label>
								<select id='chapterList' class="easyui-combobox" name='chapterList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onChangeChapter'>
									<option value="">请选择</option>
								</select>
								<label class="ml20">节：</label>
								<select id='partList' class="easyui-combobox" name='partList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName"'>
									<option value="">请选择</option>
								</select>
							</li>
						</ul>
						<h5>关联知识点：</h5>
						<ul>
							<li>
								<label><b class="red">∗</b>学段：</label>
								<select id='semesterAllList' class="easyui-combobox" name='semesterAllList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"semesterName", onChange:onChangeSemesterKnowledge'>
								</select>
								<label class="ml20"><b class="red">∗</b>学科：</label>
								<select id='disciplineAllList' class="easyui-combobox" name='disciplineAllList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"disciplineName", onChange:onChangeDisciplineKnowledge'>
								</select>
								<label class="ml20"><b class="red">∗</b>知识点：</label>
								<select id='knowledgeRoot' class="easyui-combobox" name='knowledgeRoot' style='width:80px;' data-options='editable:false, valueField:"id", textField:"knowledgeName", onChange:onKnowledgeChange'>
									<option value="">请选择</option>
								</select>
								<div id="knowledgeContainer" style="display:inline;"></div>
							</li>
							<li>
								<a href="javascript:;" class="btn bgBlue" onclick="addKnowledge()">添加知识点</a>
							</li>
							<li>
								<label>知识点：</label>
								<div id="knowledgeSelected">
								</div>
							</li>
						</ul>
					</c:if>
					<c:if test="${resourceColumn.resourceCatalogFlag == 'Y' }">
						<h5>关联资源分类：</h5>
						<ul>
							<li>
								<label>分类：</label>
								<select id='resourceCatalogRoot' class="easyui-combobox" name='resourceCatalogRoot' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onResourceCatalogChange'>
									<option value="">请选择</option>
								</select>
								<div id="resourceCatalogContainer" style="display:inline;"></div>
							</li>
						</ul>
					</c:if>
					<h5><b class="red">∗</b>附件：</h5>
					<ul>
						<li id="studyresourceFile">
							<a class="btn bgBlue uploadFileBtn" href="javascript:;">选择附件</a>
								<div class="uploadFileName"></div>
								<input type="hidden" class="successFileName" value=""/>
								<div class="process clearfix">
									<span class="progress-box">
										<span class="progress-bar" style="width:0%;"></span>
									</span>
									<span class="progress_percent" style="width: 50px;">0%</span>
									<span class="info" style="margin-left: 20px;width:600px;text-align:left;"></span>
								</div>
							</li>
						</ul>
					
						<h5><b class="red">∗</b>缩略图：</h5>
						<ul>
							<li id="thumbFile">
								<a class="btn bgBlue uploadFileBtn" href="javascript:;">选择缩略图</a>
								<div class="uploadFileName"></div>
								<input type="hidden" class="successFileName" value=""/>
								<input type="hidden" id="thumb" name="thumb" class="imageName" value=""/>
								<div class="process clearfix">
									<span class="progress-box">
										<span class="progress-bar" style="width:0%;"></span>
									</span>
									<span class="progress_percent" style="width: 50px;">0%</span>
									<span class="info" style="margin-left: 20px;width:600px;text-align:left;"></span>
								</div>
							</li>
						</ul>
				
						<h5>标签：</h5>
						<ul>
							<li>
								<input type="text" id="labels" name="labels" value=""/><span>注：多个标签间请用中文逗号“，”隔开。</span>
							</li>
						</ul>
						
						<h5>作者：</h5>
						<ul>
							<li>
								<input type="text" id="author" name="author" value=""/>
							</li>
						</ul>
					<div class="submitBtn">
						<a class="btn" onclick="onAddResource()">提交保存</a>
					</div>
				</div>
			</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var ctx = '${ctx}';
		$(document).ready(function(){
			// 是否关联章节和知识点
			var baseCatalogKnowledgeFlag = $("#baseCatalogKnowledgeFlag").val();
			if (baseCatalogKnowledgeFlag == 'Y') {
				$.get("${ctx}/skeleton/getAllSemester.do", function(result){
					result.unshift({"id":"", "semesterName":"请选择"});
					$("#semesterList").combobox("loadData", result);
					$("#semesterAllList").combobox("loadData", result);
				}, "json");	
				
				$.get("${ctx}/skeleton/getAllDiscipline.do", function(result){
					result.unshift({"id":"", "disciplineName":"请选择"});
					$("#disciplineAllList").combobox("loadData", result);
				}, "json");
			}
			
			// 是否关联资源分类
			var resourceCatalogFlag = $("#resourceCatalogFlag").val();
			if (resourceCatalogFlag == 'Y') {
				var resourceColumnId = $("#resourceColumnId").val();
				$.get("${ctx}/skeleton/getRootResourceCatalogs.do?resourceColumnId=" + resourceColumnId, function(result){
					result.unshift({"id":"", "catalogName":"请选择"});
					$("#resourceCatalogRoot").combobox("loadData", result);
				}, "json");	
			}
			
			// 普通附件上传
			ajaxUpload('studyresource');
			
			// 缩略图上传
			ajaxUpload('thumb');
		});
		
		function ajaxUpload(sequence) {
			var u = '${ctx}/uploadHandle/upload.do?sequence=' + sequence;
			if(sequence == "studyresource") {
				var maxUploadSizeDocument = $("#maxUploadSizeDocument").val();
				uploadDocument(sequence + "File", u, maxUploadSizeDocument);
			} else if (sequence == "thumb") {
				var u = '${ctx}/public/uploadImage.do';
				var maxUploadSizeThumb = $("#maxUploadSizeThumb").val();
				uploadImage(sequence + "File", u, maxUploadSizeThumb);				
			}
		}

		function onAddResource(){
			var rcId = $("#resourceColumnId").val() || '';
			if(rcId.length == 0){
				Win.alert("资源栏目不存在，无法上传资源!");
				return;
			}
			var uploading = $("#uploading").val();
			if (uploading == "1"){
				Win.alert("文件上传中，请等待文件上传结束!");
				return;
			}
			if ($("#resourceName").val().trim() == ""){
				Win.alert("资源名称不能为空!");
				return;
			}
			if ($.trim($('#resourceName').val()).length > 30) {
				Win.alert("资源名称只能输入30个字");
				return;
			}
			if ($.trim($('#description').val()).length > 160) {
				Win.alert("资源简介只能输入160个字");
				return;
			}
			// 是否关联章节和知识点
			var baseCatalogKnowledgeFlag = $("#baseCatalogKnowledgeFlag").val();
			// 是否关联资源分类
			var resourceCatalogFlag = $("#resourceCatalogFlag").val();
			if (baseCatalogKnowledgeFlag == 'Y') {
				// 学段
				var baseSemesterId = $("#semesterList").combobox("getValue");
				if (baseSemesterId == null || baseSemesterId == '') {
					Win.alert("请选择资源的学段!");
					return;
				} else {
					$("#baseSemesterId").val(baseSemesterId);
				}
				
				// 年级
				var baseClasslevelId = $("#classlevelList").combobox("getValue");
				if (baseClasslevelId == null || baseClasslevelId == '') {
					Win.alert("请选择资源的年级!");
					return;
				} else {
					$("#baseClasslevelId").val(baseClasslevelId);
				}
				
				// 学科
				var baseDisciplineId = $("#disciplineList").combobox("getValue");
				if (baseDisciplineId == null || baseDisciplineId == '') {
					Win.alert("请选择资源的学科!");
					return;
				} else {
					$("#baseDisciplineId").val(baseDisciplineId);
				}
				
				$("#baseVersionId").val(getValue($("#versionList").combobox("getValue")));
				$("#baseFascicleId").val(getValue($("#fascicleList").combobox("getValue")));
				$("#baseChapterId").val(getValue($("#chapterList").combobox("getValue")));
				$("#basePartId").val(getValue($("#partList").combobox("getValue")));
				
				//knowledgeIds
				var str = "";
				var rows = $(".smallBlock").each(function(){
					if (str.length == 0){
						str = str + $(this).attr("knowledgeId");
					}else{
						str = str + "," + $(this).attr("knowledgeId");
					}
				});
				$("#knowledgeIds").val(str);
			}
			
			if (resourceCatalogFlag == 'Y') {
				// 资源分类
				$("#resourceCatalogFirstId").val(getValue($("#resourceCatalogRoot").combobox("getValue")));
				if ($(".resourceCatalogSecond").length>0) {
					$("#resourceCatalogSecondId").val(getValue($(".resourceCatalogSecond").combobox("getValue")));	
				}
			}
			
			var studyresourceName = $(".successFileName","#studyresourceFile").val();
			if (typeof(studyresourceName) == "undefined" || studyresourceName == ''){
				Win.alert("请先上传附件!");
				return;
			}
			
			var thumbName = $(".successFileName","#thumbFile").val();
			if (typeof(thumbName) == "undefined" || thumbName == '') {
				Win.alert("请先上传缩略图!");
				return;
			}
			
			var labels = $("#labels").val();
			if (labels != '' && labels.length > 20) {
				Win.alert("标签最多只能输入20个字符");
				return;
			} else {
				var lblArr = labels.split("，");
				var sglLbl = "";
				var errorFlag = false;
				for(var i = 0; i< lblArr.length;i++){
					sglLbl = lblArr[i];
					if(sglLbl.length > 10){
						errorFlag = true;
						break;
					}
				}
				if(errorFlag){
					Win.alert("单个标签最多只能输入10个字符");
					return;
				}				
			}
			
			if ($('#author').val() != '' && $('#author').val().length > 20) {
				Win.alert("作者最多只能输入20个字符");
				return;
			}
			
			$('#addResourceForm').submit();
			onAddResource = function(){};
		}
		
		var $radioResourceColumnId = $('input[name=radioResourceColumnId]');
		var $selresourceColumnId = $("input[name=radioResourceColumnId]:checked");
		$radioResourceColumnId.on('click', function () {
			var url = $(this).attr('data-url');
			if (url) {
				$selresourceColumnId[0].checked = true;
				window.location = url;
			}
			return false;
		})
		
	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>