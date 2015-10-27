<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
<style>
.contentNav{padding-left:30px;background:rgba(0, 0, 0, 0) url("${ctx}/public/img/index/resourceLog.png") no-repeat scroll 4px 13px}
.seach-t{overflow:hidden;}
.ming_c,.text_k,.fl{float:left;}
.ming_c{margin-left:20px;}
.table_main_0{border-collapse:collapse;border:1px solid #ccc;width:100%;margin-top:30px;}
.table_main_0 th{background:#eee;}
.table_main_0 th,.table_main_0 td{height:38px;padding:0;border:1px solid #ccc;text-align:center;}

</style>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<%@ include file="../common/department_side.jsp"%>
		<script type="text/javascript">
			setSideNav("resourceLog");
		</script>
		<div class="content">
			<h4 class="contentNav">
				资源日志
			</h4>
			<div class="otContent">
				<p class="seach-t">
					<label class="ming_c">资源名称：</label>
					<input type="text" class="text_k" id="resourceName" autocomplete="off">
					<label class="ming_c">操作人：</label>
					<input type="text" class="text_k" id="realName" autocomplete="off">
					<a href="javascript:;" class="btn ml20" onclick="getResourceLogList()">查询</a>
				</p>
				<table class="table_main_0">
					<thead>
						<tr>
							<th width="30%">时间</th>
							<th width="20%">操作人</th>
							<th width="30%">资源名称</th>
							<th width="20%">操作内容</th>
						</tr>
					</thead>
					<tbody id="resourceLogList">
							
					</tbody>
					<tfoot >
						<tr>
							<td colspan="4" id="pageBody"></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var splitPage;
	function getResourceLogList(){
		$("#pageBody").html("");
		var config={
				node:$id("pageBody"),
				url:"${ctx}/resourceLog/getResourceLogByCondition.do",
				data:{resourceName:$("#resourceName").val(),realName:$("#realName").val()},
				callback:splitCallback,
				count:20
		};
		splitPage = new SplitPage(config);
	}
	
	function splitCallback(data,total){
		if(total == 0 || (!data)) {
			$("#resourceLogList").html("<tr><td colspan='4'>抱歉！没有找到相关资源！</td></tr>");
			$("#pageBody").hide();
		} else {
			if (total > 20) {
				$("#pageBody").show();
			} else {
				$("#pageBody").hide();
			}
			var resLog;
			var html = '';
			for(var i=0,j=data.length;i<j;i++) {
				resLog = data[i];
				html+='<tr>';
				html+='<td>'+resLog.visitTime+'</td>';
				html+='<td>'+resLog.realName+'</td>';
				html+='<td>'+resLog.resourceName+'</td>';
				html+='<td>'+resLog.resourceDescribe+'</td>';
				html+='</tr>';
			}
			$("#resourceLogList").html(html);
		}
	}
	getResourceLogList();
	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>