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
<link href="<%=request.getContextPath()%>/resources/css/reset.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />
<link href="<%=request.getContextPath()%>/resources/css/style.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css"
	rel="stylesheet" type="text/css">

</head>
<body>
	<div class="header">
		<h1>
			<span><img
				src="<%=request.getContextPath()%>/resources/images/logo.png"
				alt="iDreamsky" title="iDreamsky"></span>
		</h1>
		<h2 class="title">
			<em>业务推广员系统</em>-资源管理后台
		</h2>
		<div class="userBar">
			<span>欢迎您：</span><a class="user-name"
				href="${pageContext.request.contextPath}/userInfo">${pageContext.request.userPrincipal.name}</a>
			<a class="logout" href="<%=request.getContextPath()%>/logout">退出</a>
		</div>
	</div>
	<div class="mainWrap clearfix">
		<div class="sidebar">
			<div class="box">
				<div class="title">
					<h3 class="ms">推广员运营配置</h3>
				</div>
				<ul>
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">配置管理</a></li>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>配置管理</strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form action="<%=request.getContextPath()%>/promoter/save"  modelAttribute="promoterBean,redeemCodeBean,integralSchemeBean" method="POST">
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>基本信息配置</legend>
							<p id="info"></p>
							<p>
								<label for="username">游戏名称</label> 
								<input type="text"
									name="gameName" class="iText"
									value="${promoterBean.gameName }" />
								<input type="text"
									name="id" class="iText"
									value="${promoterBean.id}" style="display:none" />
							</p>
							<p>
								<label for="username">游戏ID</label> <input type="text"
									class="iText" name="gameId"
									value="${promoterBean.gameId }" />
							</p>
							<p>
								<label for="username" id="u4">国家和地区</label> <select 
									id="u4_input" name="location">
									<option <c:if test="${promoterBean.location=='中国大陆' }">selected="true"</c:if>  value="中国大陆">中国大陆</option>
									<option <c:if test="${promoterBean.location=='台湾' }">selected="true"</c:if>value="台湾">台湾</option>
									<option <c:if test="${promoterBean.location=='香港' }">selected="true"</c:if>value="香港">香港</option>
								</select>
							</p>
							<p>
								<label for="confirm-password">活动开始时间</label> <input type="text"
									id="datetimepicker_dark1" placeholder="点击选择日期时间" class="iText"
									name="beginTime" value="${promoterBean.beginTime }" />
							</p>
							<p>
								<label for="confirm-password">活动结束时间</label> <input type="text"
									id="datetimepicker_dark2" placeholder="点击选择日期时间" class="iText"
									name="endTime" value="${promoterBean.endTime }" />
							</p>
							<p>
								<label for="confirm-password">活动预热时间</label> <input type="text"
									id="datetimepicker_dark3" class="iText" placeholder="点击选择日期时间"
									name="preheatingTime"
									value="${promoterBean.preheatingTime }" />
							</p>
							<p>
								<label>积分方案简述</label>
								<c:choose>
									<c:when test="${not empty promoterBean.description}">
										<textarea name="description"
											style="width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;"
											>${promoterBean.description }</textarea>
									</c:when>
									<c:otherwise>
										<textarea name="description"
											style="width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;"
											placeholder="方案简述..."></textarea>
									</c:otherwise>
								</c:choose>
							</p>
						</fieldset>
						<br>
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>兑换码限制配置</legend>
							<p>
								<label>单台设备可生成</label> <input type="number"  name="deviceCount"  value="${redeemCodeBean.deviceCount }"style="width: 60px" min="0"/> <label>个兑换码</label>
							</p>
							<p>
								<label>一个兑换码一共可兑换</label> <input type="number" name="recommandCount" value="${redeemCodeBean.recommandCount }" style="width: 60px" min="0" /> <label>次</label>
							</p>
						</fieldset>
						<br>
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>积分方案配置</legend>
							<p>
								<label>新用户注册获得</label> <input type="number" name="register" value="${integralSchemeBean.register }" style="width: 60px"min="0"/> <label>个积分</label>
							</p>
							<p>
								<label>游戏角色级数要求</label> <input type="number" name="level" value="${integralSchemeBean.level }" style="width: 60px" min="0" /> <label>级</label>
							    <label>游戏角色在线时间要求</label> <input type="number" name="time" value="${integralSchemeBean.time }" style="width: 60px" min="0" /> <label>小时</label>
							</p>
							<p>
								<label>推广员获得</label> <input type="number" name="promoterA" value="${integralSchemeBean.promoterA }" style="width: 60px"min="0" > <label>个积分</label> 
								<label>被推广员获得</label> <input type="number" name="promoterB" value="${integralSchemeBean.promoterB }" style="width: 60px"  min="1" > <label>个积分</label>
							</p>
						</fieldset>
						<p>
							<label><input type="submit" style="width:100px ;height:30px" value="保存" /></label>
							<label><input type="button" style="width:100px ;height:30px" value="返回上一步"
								onClick="javascript :history.back(-1);" /></label>
						</p>
					</form>
				</div>
			</div>
		</div></div>
		<script type="text/javascript">
			$('#datetimepicker_dark1').datetimepicker({
				theme : 'dark'
			})
			$('#datetimepicker_dark2').datetimepicker({
				theme : 'dark'
			})
			$('#datetimepicker_dark3').datetimepicker({
				theme : 'dark'
			})
		</script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
</body>
</html>
