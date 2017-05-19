<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<title>推广员后台管理系统</title>
<link href="<%=request.getContextPath()%>/resources/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/reveal.css">
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />
</head>
<body>
	<div class="header">
		<h1>
			<span><img src="<%=request.getContextPath()%>/resources/images/logo.png" alt="iDreamsky" title="iDreamsky"></span>
		</h1>
		<h2 class="title">
			<em>业务推广员系统</em>-资源管理后台
		</h2>
		<div class="userBar">
			<span>欢迎您：</span><a class="user-name" href="<%=request.getContextPath()%>/promoter/index">${pageContext.request.userPrincipal.name}</a>
			<a class="logout" href="<%=request.getContextPath()%>/logout">退出</a>
		</div>
	</div>
	<div class="mainWrap clearfix">
		<div class="sidebar" style="height: 1650px;">
			<div class="box">
				<div class="title">
					<h3 class="ms">推广员运营配置</h3>
				</div>
				<ul>
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/payedorder">提交已完成订单</a></li>
					<li ><!-- 查看已支付的订单 -->
					     <a href="<%=request.getContextPath()%>/promoter/payedorderlist">现金兑换订单列表</a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/promoter/forum" >用户吐槽</a>
					</li>										
					<c:if
						test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
						<li><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if
						test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
						<li><a href="<%=request.getContextPath()%>/game/game">游戏管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>数据统计</strong>
			</div>
			<div class="toolbar">
			</div>
			<div class="wrap">
				<div class="grid" style="background:WhiteSmoke">
					<form action="<%=request.getContextPath()%>/statistics/show" method="get" id="Form">
					<input type="hidden" name="gameId" value="${gameId}"/>
					<input type="hidden" name="actId" value="${actId}"/>
					<input type="hidden" name="gameName" value="${gameName}"/>
					    <div style="width: 1200px; height: 480px; margin: 0 auto ">
					        <div style="width: 900px; height: 50px; margin: 0 auto ">
						        <p class="fl">
									开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="beginTime1" value="${beginTime1}" class="iText" />
								</p>
								<p class="fl">
									结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="endTime1" value="${endTime1}" class="iText" />
								</p>
								<input type="submit" value="查询" class="btn" />
								<a href="<%=request.getContextPath()%>/statistics/export?gameId=${gameId}&actId=${actId}&beginTime=${beginTime1}&endTime=${endTime1}&pic=pic1" style="background-color:green" class="btn">导出Excel</a>
							</div>
					        <div id="container1" style="width: 1100px; height: 400px; margin: 0 auto "><c:if test="${not empty pic1msg }">${pic1msg }</c:if></div>
					    </div>
					    
					    <div style="width: 1200px; height: 480px; margin: 0 auto">
					        <div style="width: 900px; height: 50px; margin: 0 auto ">
						        <p class="fl">
									开始时间：<input type="text" id="datetimepicker_dark3" placeholder="点击选择日期时间" name="beginTime2" value="${beginTime2 }" class="iText" />
								</p>
								<p class="fl">
									结束时间：<input type="text" id="datetimepicker_dark4" placeholder="点击选择日期时间" name="endTime2" value="${endTime2 }" class="iText" />
								</p>
								<input type="submit" value="查询" class="btn" />
								<a href="<%=request.getContextPath()%>/statistics/export?gameId=${gameId}&actId=${actId}&beginTime=${beginTime2}&endTime=${endTime2}&pic=pic2" style="background-color:green" class="btn">导出Excel</a>
							</div>
					        <div id="container2" style="width: 1100px; height: 400px; margin: 0 auto"><c:if test="${not empty pic2msg }">${pic2msg }</c:if></div>
					    </div>
	
					    <div style="width: 1200px; height: 580px; margin: 0 auto">
					        <div style="width: 900px; height: 50px; margin: 0 auto ">
						        <p class="fl">
									开始时间：<input type="text" id="datetimepicker_dark5" placeholder="点击选择日期时间" name="beginTime3" value="${beginTime3}" class="iText" />
								</p>
								<p class="fl">
									结束时间：<input type="text" id="datetimepicker_dark6" placeholder="点击选择日期时间" name="endTime3" value="${endTime3}" class="iText" />
								</p>
								<input type="submit" value="查询" class="btn" />
								<a href="<%=request.getContextPath()%>/statistics/export?gameId=${gameId}&actId=${actId}&beginTime=${beginTime3}&endTime=${endTime3}&pic=pic3" style="background-color:green" class="btn">导出Excel</a>
							</div>
					        <div id="container3" style="width: 1100px; height: 450px; margin: 0 auto"><c:if test="${not empty pic3msg}">${pic3msg }</c:if></div>
					    </div>				    
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/highcharts.js" type="text/javascript"></script>
    <!-- <script src="http://code.highcharts.com/highcharts.js" type="text/javascript"></script> -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script language="JavaScript">
	$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark3').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark4').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark5').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark6').datetimepicker({theme:'dark'})
	</script>
	<script language="JavaScript">
		$(document).ready(
				function() {
					Highcharts.setOptions({ 
					    colors: ['#058DC7', '#50B432', '#ED561B','#080808','#4B0082','#FFF263','#6AF9C4', '#DDDF00', '#64E572','#24CBE5','#FF9655'] 
					}); 
					//去掉水印
					var credits = {
						      enabled: false
					};
					//图1
					var title1 = {
						text : '邀请人数及付费情况'
					};

					var xAxis1 = {
						categories : ${categories1},

					};
					var yAxis1 = {
						title : {
							text : '数值(人或元) '
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ] 
					};
					var series1 = ${series1};
					//图2
					var title2 = {
							text : '分享方式统计'
					};
					var xAxis2 = {
						categories : ${categories2}
					};
					var yAxis2 = {
						title : {
							text : '数值(人或元)'
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ]
					};
					var series2 = ${series2};
					//图3
					var title3 = {
							text : '点击数统计'
					};
					var xAxis3 = {
						categories : ${categories3}
					};
					var yAxis3 = {
						title : {
							text : '数值(次)'
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ]
					};
					var series3 = ${series3};
					var tooltip = {
						valueSuffix : ''
					};
					var legend = {
					      layout: 'vertical',
					      align: 'right',
					      verticalAlign: 'middle',
					      borderWidth: 0
					};
					
					//用于显示数据标签
				   var plotOptions = {
						      line: {
						         dataLabels: {
						            enabled: true
						         },   
						         enableMouseTracking: true
						      }
				   };
					   
					var json1 = {};
					json1.title = title1;
                    json1.credits = credits;
					json1.xAxis = xAxis1;
					json1.yAxis = yAxis1;
					json1.tooltip = tooltip;
					json1.legend = legend;
					json1.series = series1;
					json1.plotOptions = plotOptions;
					
					var json2 = {};
					json2.title = title2;
					json2.credits = credits;
					json2.xAxis = xAxis2;
					json2.yAxis = yAxis2;
					json2.tooltip = tooltip;
					json2.legend = legend;
					json2.series = series2;
					json2.plotOptions = plotOptions;
					
					var json3 = {};
					json3.title = title3;
					json3.credits = credits;
					json3.xAxis = xAxis3;
					json3.yAxis = yAxis3;
					json3.tooltip = tooltip;
					json3.legend = legend;
					json3.series = series3;
					json3.plotOptions = plotOptions;

					$('#container1').highcharts(json1);
					$('#container2').highcharts(json2);
					$('#container3').highcharts(json3);
				});
	</script>
</body>
</html>
