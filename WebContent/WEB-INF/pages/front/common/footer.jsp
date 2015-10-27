<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="footer" id="footer">
	<p>${sessionScope.deploySysConfig.companyInfo }</p>
</div>
<script>
	//搜索框交互
	// var input_search = $('input.search');
 //    var old_width = input_search.css('width');

 //    input_search.bind('focusin', function() {
 //        input_search.animate({'width': '240px'}, 500);
	// 	input_search.removeAttr("placeholder");       
 //    }).bind('focusout', function() {
 //        input_search.animate({'width': old_width}, 500);
 //        input_search.attr("placeholder", "Search"); 
 //    });

	//头部hover弹出框
	(function(){
		var _t;
		$(".tipBox").hide();
		$(".loginBar .userPhoto").hover(function(){
			clearTimeout(_t);
			$(".tipBox").show();
		},function(){
			_t = setTimeout('$(".tipBox").hide()', 300);
		})
		$(".tipBox").bind("mouseover", function(){
			clearTimeout(_t);
		})
	})();
	
	// side导航
	$(".sideNav a").click(function(){
		$(".sideNav a").removeClass("selected");
		$(this).addClass("selected");
	})
	// side班级选择
	$(".classList").delegate("a", "click", function(){
		$(this).removeClass("selected").addClass("selected");
	})
	//content的tab切换
	$(".navTab a").click(function(){
		var i = this.getAttribute("index");
		$(".currTab").removeClass("currTab");
		if (typeof i != "undefined" && i !== null ) {
			$(".navTab a").eq(i).addClass("currTab");
		}
		$(".currBox").removeClass("currBox");
		$(".tabBox").eq(i).addClass("currBox");
	});
	//测试中心--查看试卷tab切换
	$(".klgList a").click(function(){
		var thisWrap = $(this).parents(".knowledge");
			thisWrap.find(".selected").removeClass("selected");
			$(this).addClass("selected");
		var thisCont = thisWrap.find(".klgCont");
		var i = $(this).index();
	 	thisCont.eq(i).removeClass("hidden").siblings(".klgCont").addClass("hidden");
	});
	//content内部二级菜单tab切换
	$(".contentInNav").delegate("a", "click", function(){
		var i = $(this).attr("index");
		$(".contentInNav .selected").removeClass("selected");
		$(this).addClass("selected");
		$(".sourceList").addClass("hidden").eq(i).removeClass("hidden");
	});

	// 设置左侧side高度
/* 	$(function(){
		var containerH = $('.container').height();
		$('.side').height(containerH - 20);
	}); */
	
	function canVisit(id){
		$.post("${sessionScope.session_domain_config.dynamic}/front/visitor/canVisit.do?id="+id,function (data) {
			if(data.result){
				window.location.href="${ctx}/front/visitor/visitorIndex.html?id="+id;
			}else{
				Win.alert("无访问权限");
			}
		}, 'json');
	}
	$(document).on('ajaxComplete', function (jqXHR, xhr) {
		var responseText = xhr.responseText || "";

		var start = responseText.indexOf("<script>top.location='");
		if(start>-1){
			top.location="${sessionScope.appredirect}";
		}
	});
</script>