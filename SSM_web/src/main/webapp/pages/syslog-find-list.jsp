<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 页面meta -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>访问日志</title>

<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/iCheck/square/blue.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/morris/morris.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.theme.default.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/colorpicker/bootstrap-colorpicker.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/AdminLTE.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/skins/_all-skins.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.skinNice.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-slider/slider.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
</head>

<body class="hold-transition skin-blue sidebar-mini">

	<div class="wrapper">

		<!-- 页面头部 -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- 页面头部 /-->

		<!-- 导航侧栏 -->
		<jsp:include page="aside.jsp"></jsp:include>
		<!-- 导航侧栏 /-->

		<!-- 内容区域 -->
		<div class="content-wrapper">

			<!-- 内容头部 -->
			<section class="content-header">
			<h1>
				日志管理 <small>搜索日志</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/index.jsp"><i
						class="fa fa-dashboard"></i> 首页</a></li>
				<li><a href="${pageContext.request.contextPath}/sysLog/findAll.do?page=1&pageSize=4">日志管理</a></li>

				<li class="active">搜索日志</li>
			</ol>
			</section>
			<!-- 内容头部 /-->

			<!-- 正文区域 -->
			<section class="content"> <!-- .box-body -->
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">列表</h3>
				</div>

				<div class="box-body">

					<!-- 数据表格 -->
					<div class="table-box">

						<!--工具栏-->
						<div class="pull-left">
							<div class="form-group form-inline">
								<div class="btn-group">
									<button type="button" class="btn btn-default" title="刷新"
										onclick="window.location.reload();">
										<i class="fa fa-refresh"></i> 刷新
									</button>
									<button type="button" class="btn btn-default" id="deleteSelected" title="删除选中">
										<i class="fa fa-trash-o"></i> 删除选中
									</button>
								</div>
							</div>
						</div>
						<div class="box-tools pull-right">
							<form action="${pageContext.request.contextPath}/sysLog/findSysLog.do" method="post">
								<div class="has-feedback">
									<%--搜索--%>
									<input type="text" class="form-control input-sm" placeholder="输入访问用户名称搜索"
										   name="username" id="username" value="${findUsername}">
									<span class="glyphicon glyphicon-search form-control-feedback"></span>
								</div>
							</form>
						</div>
						<!--工具栏/-->

						<%--用form包裹起来，这样提交的时候就可以获得选中的checkbox--%>
						<form id="form1" action="${pageContext.servletContext.contextPath}/sysLog/delete.do" method="post">
							<!--数据列表-->
							<table id="dataList"
								class="table table-bordered table-striped table-hover dataTable">
								<thead>
									<tr>
										<th class="" style="padding-right: 0px"><input id="selall"
											type="checkbox" class="icheckbox_square-blue"></th>
										<th class="sorting_asc">ID</th>
										<th class="sorting">访问时间</th>
										<th class="sorting">访问用户</th>
										<th class="sorting">访问IP</th>
										<th class="sorting">资源URL</th>
										<th class="sorting">执行时间</th>
										<th class="sorting">访问方法</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pageInfo.list}" var="syslog">
										<tr>
												<%-- checkbox 提交form，所以要有name ,value为要删除的id--%>
											<td><input type="checkbox" name="selectIds" value="${syslog.id}"></td>
											<td>${syslog.id}</td>
											<td>${syslog.visitTimeStr }</td>
											<td>${syslog.username }</td>
											<td>${syslog.ip }</td>
											<td>${syslog.url}</td>
											<td>${syslog.executionTime}毫秒</td>
											<td>${syslog.method}</td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
							<!--数据列表/-->
						</form>

					</div>
					<!-- 数据表格 /-->

				</div>
				<!-- /.box-body -->

				<!-- .box-footer-->
				<div class="box-footer">
					<div class="pull-left">
						<div class="form-group form-inline">
							总共${pageInfo.pages} 页，共${pageInfo.total} 条数据。 每页
							<select class="form-control" id="changPageSize" onchange="changPageSize()">
								<option>请选择</option>
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select> 条
						</div>
					</div>

					<div class="box-tools pull-right">
						<ul class="pagination">
							<li>
								<a aria-label="Previous" href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=1&pageSize=${pageInfo.pageSize}">首页</a>
							</li>
							<li><a href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=${pageInfo.pageNum-1}&pageSize=${pageInfo.pageSize}">上一页</a></li>


							<!-- 分页 -->
							<!--总页数没有10页-->
							<c:choose>
								<c:when test="${pageInfo.pages <= 10}">
									<c:set var="begin" value="1"/>
									<c:set var="end" value="${pageInfo.pages}"/>
								</c:when>
								<%--页数超过了10页--%>
								<c:otherwise>
									<c:set var="begin" value="${pageInfo.pageNum - 4}"/>
									<c:set var="end" value="${pageInfo.pageNum + 5}"/>
									<%--如果begin减1后为0,设置起始页为1,最大页为10--%>
									<c:if test="${begin -1 <= 0}">
										<c:set var="begin" value="1"/>
										<c:set var="end" value="10"/>
									</c:if>
									<%--如果end超过最大页,设置起始页=最大页-5--%>
									<c:if test="${end > pageInfo.pages}">
										<c:set var="begin" value="${pageInfo.pages - 9}"/>
										<c:set var="end" value="${pageInfo.pages}"/>
									</c:if>
								</c:otherwise>
							</c:choose>
							<!-- 分页 /-->

							<%--遍历--%>
							<c:forEach begin="${begin}" end="${end}" var="i">
								<c:if test="${i==pageInfo.pageNum}">
									<li><a style="background-color: #2aabd2" href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=${i}&pageSize=${pageInfo.pageSize}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pageInfo.pageNum}">
									<li><a href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=${i}&pageSize=${pageInfo.pageSize}">${i}</a></li>
								</c:if>
							</c:forEach>

							<li><a href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=${pageInfo.pageNum+1}&pageSize=${pageInfo.pageSize}">下一页</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=${pageInfo.pages}&pageSize=${pageInfo.pageSize}" aria-label="Next">尾页</a>
							</li>
						</ul>
					</div>

				</div>
				<!-- /.box-footer-->

			</div>

			</section>
			<!-- 正文区域 /-->

		</div>
		<!-- 内容区域 /-->

		<!-- 底部导航 -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 1.0.8
			</div>
			<strong>Designed by <a href="${pageContext.request.contextPath}/AboutMe.jsp">刘嘉俊</a>.</strong> All rights reserved.
		</footer>
		<!-- 底部导航 /-->

	</div>

	<script
		src="${pageContext.request.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jQueryUI/jquery-ui.min.js"></script>
	<script>
		$.widget.bridge('uibutton', $.ui.button);
	</script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/raphael/raphael-min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/morris/morris.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/sparkline/jquery.sparkline.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/knob/jquery.knob.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/moment.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/fastclick/fastclick.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/adminLTE/js/app.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/locale/bootstrap-markdown.zh.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/to-markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/ckeditor/ckeditor.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.extensions.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/chartjs/Chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.resize.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.pie.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.categories.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-slider/bootstrap-slider.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>

	<script>
		$(document).ready(function() {
			// 选择框
			$(".select2").select2();

			// WYSIHTML5编辑器
			$(".textarea").wysihtml5({
				locale : 'zh-CN'
			});
		});

		// 设置激活菜单
		function setSidebarActive(tagUri) {
			var liObj = $("#" + tagUri);
			if (liObj.length > 0) {
				liObj.parent().parent().addClass("active");
				liObj.addClass("active");
			}
		}

        //改变每页显示条数
        function changPageSize(){
            var pageSize=$("#changPageSize").val();
            location.href="${pageContext.request.contextPath}/sysLog/findSysLog.do?username=${findUsername}&page=1&pageSize="+pageSize;
        }


        //删除所选的
        $("#deleteSelected").click(function () {
            if (confirm("您确定要删除选定条目吗?")) {

                var flag=false;
                //判断是否有选中条目
                var cbs = document.getElementsByName("selectIds");
                for (var i=0;i<cbs.length;i++){
                    if ( cbs[i].checked ){
                        flag=true;
                    }
                }
                //有条目被选中
                if (flag==true){
                    //提交表格
                    $("#form1").submit();
                }

            }
        });


		$(document).ready(function() {

			// 激活导航位置
			setSidebarActive("order-manage");

			// 列表按钮 
			$("#dataList td input[type='checkbox']").iCheck({
				checkboxClass : 'icheckbox_square-blue',
				increaseArea : '20%'
			});
			// 全选操作 
			$("#selall").click(function() {
				var clicks = $(this).is(':checked');
				if (!clicks) {
					$("#dataList td input[type='checkbox']").iCheck("uncheck");
				} else {
					$("#dataList td input[type='checkbox']").iCheck("check");
				}
				$(this).data("clicks", !clicks);
			});
		});
	</script>
</body>

</html>