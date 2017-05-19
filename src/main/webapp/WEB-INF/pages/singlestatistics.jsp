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
					<li ><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
					<li><a href="<%=request.getContextPath()%>/promoter/payedorder">提交已完成订单</a></li>
					<li ><!-- 查看已支付的订单 -->
					     <a href="<%=request.getContextPath()%>/promoter/payedorderlist">现金兑换订单列表</a>
					</li>
					<li class="cur">
						<a href="<%=request.getContextPath()%>/statistics/singleshow?datatype=pic1" >数据统计</a>				
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/promoter/forum" >用户吐槽</a>
					</li>										
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
						<li><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}"> 
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
			<form action="<%=request.getContextPath()%>/statistics/singleshow" method="get" id="Form">
					<input type="hidden" name="gameId" value="${gameId}"/>
					<input type="hidden" name="actId" value="${actId}"/>
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="gameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option <c:if test="${gameName==nameList }">selected="true"</c:if> value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
						</p>
						<p class="fl">
							<label>数据类型</label> 
							<select id="u4_input" name="datatype">
								<option <c:if test="${datatype eq null }">selected="true"</c:if> <c:if test="${datatype == pic1 }">selected="true"</c:if> value="pic1">邀请人及付费情况</option>
								<option <c:if test="${datatype == 'pic2' }">selected="true"</c:if> value="pic2">分享方式统计情况</option>
								<option <c:if test="${datatype == 'pic3' }">selected="true"</c:if> value="pic3">点击数统计情况</option>
								<option <c:if test="${datatype == 'pic4' }">selected="true"</c:if> value="pic4">留存率统计情况</option>
							</select>
						</p>
						<p class="fl">
							开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="beginTime" value="${beginTime }" class="iText" />
						</p>
						<p class="fl">
							结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="endTime" value="${endTime }" class="iText" />
						</p>
						<input type="submit" value="查询" class="btn" />
						<a href="<%=request.getContextPath()%>/statistics/export?gameId=${gameId}&actId=${actId}&beginTime=${beginTime}&endTime=${endTime}&pic=pic1" style="background-color:green" class="btn">导出Excel</a>
					</form>
			</div>
			<div class="wrap">
				<div class="grid" style="background:WhiteSmoke">
					<form action="<%=request.getContextPath()%>/statistics/show" method="get" id="Form">

					<c:if test="${datatype == 'pic1'}">
					    <div style="width: 1200px; height: 480px; margin: 0 auto ">
					        <div id="container1" style="width: 1200px; height: 400px; margin: 0 auto "><c:if test="${not empty pic1msg }">${pic1msg }</c:if></div>
					        <div style="width: 1200px; margin: 40 auto ">
                               <table border="1">
									<thead>
										<tr style="background-color:rgb(220,220,220); height:30px; width:100%;border-top:2px solid #141414;border-bottom:2px solid #141414;">
											<th>日期</th>
											<th>已邀请人数</th>
											<th>已付费人数</th>
											<th>已付费总金额</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<td colspan="4"><strong>邀请人数及付费情况统计表</strong></td>
										</tr>
									</tfoot>									
									<tbody>
									<c:forEach items="${payInfolist}" var="list">
									<tr>
									   <td>${list.date}</td>
									   <td>${list.invitedPeople}</td>
									   <td>${list.payingPeople}</td>
									   <td>${list.payingAmount}</td>
									</tr>
								    </c:forEach>
									</tbody>
							</table>                             
							</div>
					    </div>
					</c:if>
					<c:if test="${datatype eq 'pic2'}">    
					    <div style="width: 1200px; height: 480px; margin: 0 auto">
					        <div id="container2" style="width: 1100px; height: 400px; margin: 0 auto"><c:if test="${not empty pic2msg }">${pic2msg }</c:if></div>
					        <div style="width: 1200px; margin: 40 auto ">
                               <table border="1">
									<thead>
										<tr style="background-color:rgb(220,220,220); height:30px; width:100%;border-top:2px solid #141414;border-bottom:2px solid #141414;">
											<th>日期</th>
											<th>微信浏览次数</th>
											<th>朋友圈浏览次数</th>
											<th>QQ浏览次数</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<td colspan="4"><strong>分享方式统计表</strong></td>
										</tr>
									</tfoot>									
									<tbody>
									<c:forEach items="${actInfolist}" var="list">
										<tr>
										   <td>${list.date}</td>
										   <td>${list.weiXinFriends}</td>
										   <td>${list.weiXinMoments}</td>
										   <td>${list.qq}</td>
										</tr>
								    </c:forEach>
									</tbody>
								</table>                             
							</div>
					    </div>
					</c:if>
					<c:if test="${datatype eq 'pic3'}">
					    <div style="width: 1200px; height: 580px; margin: 0 auto">
					        <div id="container3" style="width: 1100px; height: 450px; margin: 0 auto"><c:if test="${not empty pic3msg}">${pic3msg }</c:if></div>
					        <div style="width: 1200px; margin: 40 auto ">
                               <table border="1" style="border-bottom-color:#FF0000;">
									<thead>
										<tr style="background-color:rgb(220,220,220); height:30px; width:100%;border-top:2px solid #141414;border-bottom:2px solid #141414;">
											<th>日期</th>
											<th>活动入口点击数</th>
											<th>分享次数</th>
											<th>下载点击数</th>
											<th>新增玩家数</th>
											<th>页面浏览次数</th>
											<th>分享成功次数</th>											
										</tr>
									</thead>
									<tfoot>
										<tr>
											<td colspan="7"><strong>点击情况统计表</strong></td>
										</tr>
									</tfoot>
									<tbody>
									<c:forEach items="${actInfolist}" var="list">
										<tr>
										   <td>${list.date}</td>
										   <td>${list.clickActivity}</td>
										   <td>${list.shareCode}</td>
										   <td>${list.clickCode}</td>
										   <td>${list.invitedPeople}</td>
										   <td>${list.qq+list.weiXinFriends+list.weiXinMoments}</td>
										   <td>${list.shareSuccess}</td>
										</tr>
								    </c:forEach>
									</tbody>
								</table>                             
							</div>					        
					    </div>
					</c:if>				    
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
					var title = {
						text : '${title}'
					};

					var xAxis = {
						categories : ${categories},

					};
					var yAxis = {
						title : {
							text : '${yAxis} '
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ] 
					};
					var series = ${series};

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
					   
					var json = {};
					json.title = title;
                    json.credits = credits;
					json.xAxis = xAxis;
					json.yAxis = yAxis;
					json.tooltip = tooltip;
					json.legend = legend;
					json.series = series;
					json.plotOptions = plotOptions;
	
					$('#container1').highcharts(json);
					$('#container2').highcharts(json);
					$('#container3').highcharts(json);
				});
	</script>
</body>
</html>
