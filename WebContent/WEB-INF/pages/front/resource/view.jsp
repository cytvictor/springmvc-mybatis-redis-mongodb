<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/public/jquery-prettyPhoto-3.1.5/css/prettyPhoto.css" type="text/css" media="screen" charset="utf-8" />
	<script type="text/javascript" src="${ctx}/public/js/photoScroll.js"></script>
	<script type="text/javascript" src="${ctx}/public/jquery-prettyPhoto-3.1.5/js/jquery.prettyPhoto.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/public/js/raty/jquery.raty.min.js"></script>
	<style>
		.commentTable{border: 0px solid #ccc !important; text-align:left !important;}
		.content {
		    color: #666;
		    font-size: 14px;
		}
		#commentContainer {
		    display: inline;
		    float: left;
		    margin-right: 20px;
		    width: 700px;
		}
		.clsBox {
		    margin: 30px 0 0 10px;
		}
		.container2-nav {
		    margin-bottom: 10px;
		    overflow: hidden;
		}
		h4.container2-nav b {
		    background: url("../../../public/img/index/homeTop/bg2.png") no-repeat scroll 100% 0 #44bce7;
		    color: #fff;
		    float: left;
		    height: 34px;
		    line-height: 34px;
		    padding: 0 15px 0 10px;
		    text-align: center;
		}
		.container2-nav b {
		    background: url("../image/person.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
		    color: #aaa;
		    display: inline-block;
		    font-size: 20px;
		    padding-left: 30px;
		}
		.content {
		    color: #666;
		    font-size: 14px;
		}
		.leaveMsg {
		    font-size: 14px;
		    margin-top: 40px;
		}
		#commentContainer table {
		    width: 700px;
		}
		#commentContainer table th, #commentContainer table td {
		    border: 1px solid #ccc;
		    padding: 10px;
		    text-align: center;
		}
		#Paging {
			clear: both;
		    margin: 10px 0 30px;
		    width: 700px;
		}
	</style>
	<script>
	$(document).ready(function(){
		<c:if test="${resource.resourceColumn.columnType eq 'MIXTURE' and resource.funType eq 'FUN_TYPE_PICTURE'}">
			events.addEvent(window,"load",function(){
				scrollHori.init();
				autoPlay.init();
			});
			$("a[rel^='prettyPhoto']").prettyPhoto();
		</c:if>
		<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' and (resource.resourceColumn.columnType eq 'VIDEO' or (resource.resourceColumn.columnType eq 'MIXTURE' and resource.funType eq 'FUN_TYPE_VIDEO'))}">
			$(".videoBtn").click(function(){
				$(".videoMask").fadeOut();
				$(this).addClass("bgBlue").siblings(".videoBtn").removeClass("bgBlue");
			});
			window.myPlayer = new FlashPlayer($id("videoBox"), "${ctx}/public/flash/player/myflvPlayBack.swf?skin=${ctx}/public/flash/player/MinimaFlatCustomColorAll.swf&thumb=${ctx}/ResourceImageServlet/${resource.thumb}");
			//视频播放按钮效果
			$(".videoMask").click(function(){
				$(this).fadeOut();
				<c:if test="${not empty resource.highDefine}">
					myPlayer.playFile('${ctx}/Video/${resource.highDefine}');
					$(".videoBtn0")[0].click();
				</c:if>
				<c:if test="${empty resource.highDefine and not empty resource.normalDefine}">
					myPlayer.playFile('${ctx}/Video/${resource.normalDefine}');
					$(".videoBtn1")[0].click();
				</c:if>
			});
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
				$(".videoMask")[0].click();	
			}
		</c:if>
	})
	</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<h4 class="movieBreadNav">
			<input type="hidden" id="resourceId" name="resourceId" value="${resource.id}"/>
			${resource.resourceName}
		</h4>
		<div class="movieContent clearfix">
			<div class="movie">
				<c:if test="${resource.resourceColumn.columnType eq 'VIDEO' or  (resource.resourceColumn.columnType eq 'MIXTURE' and resource.funType eq 'FUN_TYPE_VIDEO')}">
					<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' }">
						<div id="videoBox" style="width:720px;height:450px;"></div>
						<div class="videoMask"></div>
					</c:if>
					<c:if test="${resource.transFlag ne 'TRANS_SUCCESS' }">
						<div id="videoBox" style="width:720px;height:450px;text-align:center;line-height:450px;">
							<div class="resourceMask" style="font-size: 24px;color:#D1AC65">等待转换...</div>
						</div>
					</c:if>
				</c:if>
				
				<c:if test="${resource.resourceColumn.columnType eq 'DOCUMENT'}">
					<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' }">
						<div class="photo" style="width:720px;height:450px;">
							<object width="100%" height="100%" id="docPad" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,76" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
								<param value="true" name="allowFullScreen">
								<param value="transparent" name="wmode">
								<param value="always" name="allowScriptAccess"> 
								<param value="${ctx}/public/flash/CR_writepad/CR_writepad.swf" name="movie">
								<param value="phphost=${ctx}&amp;meetId=7" name="FlashVars">
								<embed width="100%" height="100%" allowfullscreen="true" wmode="transparent" allowscriptaccess="always" flashvars="phphost=${ctx}&amp;meetId=7" type="application/x-shockwave-flash" 
									pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" name="docPad" id="docPad" src="${ctx}/public/flash/CR_writepad/CR_writepad.swf">
							</object>
						</div>
					</c:if>
					<c:if test="${resource.transFlag ne 'TRANS_SUCCESS' }">
						<div id="videoBox" style="width:720px;height:450px;text-align:center;line-height:450px;">
							<div class="resourceMask" style="font-size: 24px;color:#D1AC65">等待转换...</div>
						</div>
					</c:if>
				</c:if>
				
				<c:if test="${resource.resourceColumn.columnType eq 'MIXTURE' and resource.funType eq 'FUN_TYPE_PICTURE'}">
					<div class="photoScroll-2" id="photo-scroll">
						<a id="rollLeft" class="rollMenu" href="javascript:;">
							<img width="28" height="50" alt="" src="${ctx}/public/img/common/scroll-left2.png" />
						</a>
						<a id="rollRight" class="rollMenu" href="javascript:;">
							<img width="28" height="50" alt="" src="${ctx}/public/img/common/scroll-right2.png" />
						</a>
						<div id="rollBox">
							<ul id="rollList">	
								<c:forEach items="${images}" var="image">
								<li class="selected">
									<a href="${ctx}/ResourceImageServlet/${image.imageName}" rel="prettyPhoto"><img style="height:450px" src="${ctx}/ResourceImageServlet/${image.imageName}"></a>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				
				<c:if test="${resource.resourceColumn.columnType eq 'MIXTURE' and resource.funType eq 'FUN_TYPE_TEXT'}">
					${resource.funContent}
				</c:if>
				
				
			</div>
			<div class="movieMsg">
				<h5>${resource.resourceName}</h5>
				<ul>
					<li>${resource.description}</li>
					<li>
						评分：
						<span class="star-rating">
							<span class="current-rating star<fmt:formatNumber value="${resource.evaluateAvg}" pattern="#"/>">
							</span>
						</span>
						<span class="ml20"><b class="red currentEvaluate">${resource.evaluateAvg}</b>分</span>
					</li>
					
				<%-- 	<c:if test="${resource.baseUser.userType eq 'STUDENT'}"><c:set var="suffix" value="同学"/></c:if>
					<c:if test="${resource.baseUser.userType ne 'STUDENT'}"><c:set var="suffix" value="老师"/></c:if> --%>
					<c:if test="${resource.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
						<li>
							<span class="setWidth">学段：${resource.baseSemesterName}</span>
							<span class="setWidth">发布人：<span title="${resource.baseUserName}<%-- ${suffix} --%>">${resource.baseUserName}<%-- ${suffix} --%></span></span>
						</li>
						<li>
							<span class="setWidth">年级：${resource.baseClassLevelName}</span>
							<span class="setWidth">发布时间：<fmt:formatDate value="${resource.createTime}" pattern="yyyy-MM-dd"/></span>
						</li>
						<li>
							<span class="setWidth">学科：${resource.baseDisciplineName}</span>
							<span class="setWidth">${duration}</span>
						</li>
					</c:if>
					
					<c:if test="${resource.resourceColumn.baseCatalogKnowledgeFlag ne 'Y'}">
						<li>
							<span class="setWidth">发布人：<span title="${resource.baseUserName}<%-- ${suffix} --%>">${resource.baseUserName}<%-- ${suffix} --%></span></span>
						</li>
						<li>
							<span class="setWidth">发布时间：<fmt:formatDate value="${resource.createTime}" pattern="yyyy-MM-dd"/></span>
						</li>
						<li>
							<span class="setWidth">${duration}</span>
						</li>
					</c:if>
					
					<li>
						<span class="setWidth">观看次数：<b class="red">${resource.viewCount}</b>次</span>
						<span class="setWidth">收藏次数：<b class="red">${resource.favoriteCount}</b>次</span>
					</li>
				</ul>
				<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' and resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
					<div class="submitBtn">
						<c:if test="${not empty resource.highDefine}">
						<a href="javascript:myPlayer.playFile('${ctx}/Video/${resource.highDefine}');" class="btn videoBtn videoBtn0">高清</a>
						</c:if>
						<c:if test="${not empty resource.normalDefine}">
						<a href="javascript:myPlayer.playFile('${ctx}/Video/${resource.normalDefine}');" class="btn mr20 videoBtn videoBtn1">普清</a>
						</c:if>
						
						&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${user.userType eq 'PARENT' or user.userType eq 'STUDENT' or user.userType eq 'TEACHER'}">
						<a href="javascript:favoriteResource();" class="btn">收藏</a>
						</c:if>
						<c:if test="${not empty resource.highDefine}">
						<a href="javascript:window.location='${ctx}/public/downloadVideo.html?filename=${resource.highDefine}&resourceId=${resource.id }';" class="btn">下载</a>
						</c:if>
						<c:if test="${empty resource.highDefine and not empty resource.normalDefine}">
						<a href="javascript:window.location='${ctx}/public/downloadVideo.html?filename=${resource.normalDefine}&resourceId=${resource.id }';" class="btn">下载</a>
						</c:if>
						<c:if test="${not empty resource.studyResource}">
						<a href="javascript:window.location='${ctx}/public/downloadFile.html?filename=${resource.studyResource}&resourceId=${resource.id }';" class="btn">下载</a>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="clsBox yhpl">
			<h4 class="container2-nav">
				<b>用户评论</b>
				<span></span>
			</h4>
			<div id="commentNew">
				<ul>
					<li>
						<span>评分: </span>
						<span id="star"></span>
						<span>最高分为10分</span>
					</li>
					<li>
					&nbsp;
					</li>
					<li>
						<span style="vertical-align:top;">评价:</span>
						<span>
							<textarea cols="60" rows="3" id="commentContent" maxlength="140"></textarea>
						</span>
						<span style="vertical-align:bottom;" class="clearfix">
							<a class="btn" onclick="addComment();" id="msgSendBtn" href="javascript:;">提交</a>
						</span>
					</li>
				</ul>
			</div>

			<div class="leaveMsg" style="clear:both;" id="commentContainer">
			</div>
			<div id="Paging">
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			showStar();
			var resourceId = $("#resourceId").val();
			getComment(resourceId);
		});
		function favoriteResource(){
			$.post("../favorite.html?id=${resource.id}" , {}, function(result){
				if (result.code == 2){
					Win.alert("请重新登录！");
				} else {
					Win.alert(result.message);
				}
			}, "json");
		}
		
		function onWritePadCompleted(){
			document.docPad.doPad({'act':'ShowDoc','id':"${resource.id}","ft":"docx"});
		}
		
		//添加评论
		function addComment() {
			var comment = $("#commentContent").val();
			$("#commentContent").val("");
			
			if(comment == "") {
				Win.alert("评论内容不能为空");
				return;
			}
			
			var url = KD_RRT.root + "/front/resource/comment/addComment.do";
			var requestType = "post";
			var isAsync = "true";
			var callBackFunction = addCommentCallBack;
			var errorMessage = "添加评论异常";
			var resourceId = $("#resourceId").val();
			var data = {"resourceId" : resourceId, "comment":comment};
			
			ajaxCall(url, requestType, isAsync, data, callBackFunction, errorMessage);
		}

		//call back: 获得评论
		function getCommentCallBack(result) {
			result =  eval(result);
			var html = "<table >";
			
			html += "<tr><td class='commentTable' colspan='2'><hr /></td></tr>";
			
			$.each(result, function(index1, json){
				html += "<tr>";
					html += "<td width='62px' class='commentTable' rowspan='2'> <img width='60px' src='"+KD_RRT.root +"/HeadImageServlet/"+json.headPic+"' onerror='this.src=\""+KD_RRT.root +"/HeadImageServlet/headPicDefault.jpg\"'/>";
					html += "</td>";
					html += "<td class='commentTable'> <b class='blue'>"+json.baseUserName+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+json.createDate;
						
					html += "</td>";
				html += "</tr>";
				
				html += "<tr>";
				html += "<td class='commentTable'>"+json.comment;
				
				html += "</td>";
				html += "</tr>";
				
				html += "<tr><td class='commentTable' colspan='2'><hr /></td></tr>";
					
			});
			html += "</table>";
			
			$("#commentContainer").html(html);
			
		}

		// 初始化分页
		function getComment(resourceId) {
				$("#Paging").empty();
				var data = {"resourceId" : resourceId};
			// 课程详情分页
		        mySplit = new SplitPage({
		        node : $id("Paging"),
		        url : KD_RRT.root + "/front/resource/comment/getComment.do",
		        data : data,
		        count : 10,
		        callback : getCommentCallBack,
		        type : 'post' //支持post,get,及jsonp
		    });
			
		}

		//call back: 添加评论
		function addCommentCallBack(data){
			var resourceId = $("#resourceId").val();
			getComment(resourceId);
		}

			//显示评价的星星
			function showStar() {
					$("#star").raty({
					path:KD_RRT.root+"/public/js/raty/img",
					hints:['很差','差','一般','好','很好'],
					half:true, 
					click: clickStar
					
				});
			}
			
			//点击星星评价
			function clickStar(score, evt) {
				
				var evaluation = 2 * score;
				
				var url = KD_RRT.root + "/front/resource/comment/evaluationResource.do";
				var requestType = "post";
				var isAsync = "true";
				var callBackFunction = clickStarCallback;
				var errorMessage = "评价资源异常";
				var resourceId = $("#resourceId").val();
				var data = {"resourceId":resourceId,"evaluation" : evaluation};
				
				ajaxCall(url, requestType, isAsync, data, callBackFunction, errorMessage, score);
				
			}
			
			//call back: 点击星星评价
			function clickStarCallback(result, score) {
				//$(".currentEvaluate").text(result);
				//将星星设置为只读, 一次只让用户评价一次
				$("#star").raty({
					path:KD_RRT.root+"/public/js/raty/img",
					hints:['很差','差','一般','好','很好'],
					half:true, 
					readOnly:true,
					score: score
				});
				Win.alert("谢谢评价,您给的评价分是:"+score*2);
			}
			
			//公共的ajax调用方法
			function ajaxCall(url, requestType, isAsync, data, callBackFunction, errorMessage, otherParm1, otherParm2) {
				$.ajax({
					url: url,
					type:requestType,
					async:isAsync,
					data:data,
					success:function(result){
						//result, otherParm1 and otherParm2是可选择参数，可以不给
						callBackFunction(result, otherParm1, otherParm2);
					},
					error:function(){
						Win.alert(errorMessage);
					}
				});
			}


	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>