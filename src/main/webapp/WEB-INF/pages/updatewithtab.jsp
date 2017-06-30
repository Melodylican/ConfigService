<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/newtab.css" rel="stylesheet" type="text/css">

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
			<span>欢迎您：</span>
			<a class="user-name" href="<%=request.getContextPath()%>/promoter/index">${pageContext.request.userPrincipal.name}</a>
			<a class="logout" href="<%=request.getContextPath()%>/logout">退出</a>
		</div>
	</div>
	<div class="mainWrap clearfix">
		<div class="sidebar" style="height: 1050px;">
			<div class="box">
				<div class="title">
					<h3 class="ms">推广员运营配置</h3>
				</div>
				<ul>
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/payedorder">提交已完成订单</a></li>
					<li ><!-- 查看已支付的订单 -->
					     <a href="<%=request.getContextPath()%>/promoter/payedorderlist">现金兑换订单列表</a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/statistics/singleshow?datatype=pic1" >数据统计</a>				
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/promoter/forum" >用户吐槽</a>
					</li>										
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li ><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li ><a href="<%=request.getContextPath()%>/game/game">游戏管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>配置管理           <c:if test="${not empty updateMsg}">
				                                              <h1><font color="red">${updateMsg}</font></h1>
			                                           </c:if>
			                                           </strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form id="Form" action="<%=request.getContextPath()%>/promoter/saveupdate"  modelAttribute="promoterBean,redeemCodeBean,integralSchemeBean,exchBean" method="POST" >
						<!-- 表单开始标记 -->
						 <div id="tab-container" class='tab-container'>
							 <ul class='etabs'>
							   <li class='tab'><a href="#tabs1">活动说明</a></li>
							   <li class='tab'><a href="#tabs2">积分奖励配置</a></li>
							   <li class='tab'><a href="#tabs3">现金兑换配置</a></li>

							 </ul>
						 <div class='panel-container'>
							  <div id="tabs1">
								<div class="Content-Left">	  
									<fieldset style="border-width: 2px; border-color: #008000; width:95%; height:220">
										<legend>基本信息配置</legend>
										<p id="info"></p>
										<p>
											<label >游戏名称</label> 
											<select 
												id="u4_input" name="gameName">
												<c:forEach items="${gameNameList}" var="nameList">
												<option <c:if test="${promoterBean.gameName==nameList }">selected="true"</c:if>  value="${nameList }">${nameList}</option>
												</c:forEach>
											</select>
											<input type="hidden" name="id" value="${promoterBean.id }"/>
										</p>
										<p>
											<label >游戏ID</label> <input type="text"
												class="iText" name="gameId"
												value="${promoterBean.gameId }" />
										</p>
										<p>
											<label  id="u4">国家和地区</label> <select 
												id="u4_input" name="location">
												<option <c:if test="${promoterBean.location=='中国大陆' }">selected="true"</c:if>  value="中国大陆">中国大陆</option>
												<option <c:if test="${promoterBean.location=='台湾' }">selected="true"</c:if>value="台湾">台湾</option>
												<option <c:if test="${promoterBean.location=='香港' }">selected="true"</c:if>value="香港">香港</option>
											</select>
										</p>
										<p>
											<label for="preheatingTime">活动预热时间</label> <input type="text"
												id="datetimepicker_dark3" class="iText" placeholder="点击选择日期时间"
												name="preheatingTime"
												value="${promoterBean.preheatingTime }" /><label id="preTimeLabel"></label>
										</p>
										<p>
											<label for="preheatingUrl">活动预热url</label> <input type="text" class="iText" name="preheatingUrl"
												value="${promoterBean.preheatingUrl }" />
										</p>														
										<p>
											<label for="beginTime">活动开始时间</label> <input type="text"
												id="datetimepicker_dark1" placeholder="点击选择日期时间" class="iText"
												name="beginTime" value="${promoterBean.beginTime }" />
										</p>
										<p>
											<label for="endTime">活动结束时间</label> <input type="text"
												id="datetimepicker_dark2" placeholder="点击选择日期时间" class="iText"
												name="endTime" value="${promoterBean.endTime }" /><label id="endTimeLabel"></label>
										</p>
										<p>
											<label>兑换码h5Url</label> <input type="text" class="iText" name="exchH5Url"
												value="${promoterBean.exchH5Url}" />
										</p>										
										<p>
											<label>活动方案简述</label>
											<c:choose>
												<c:when test="${not empty redeemCodeBean.redeemDesc}">
													<textarea name="redeemDesc"
														style="width:550px;height:160px;padding:5px;border:1px solid #c9c9c9;color:#666;"
														>${redeemCodeBean.redeemDesc}</textarea>
												</c:when>
												<c:otherwise>
													<textarea name="redeemDesc"
														style="width:550px;height:160px;padding:5px;border:1px solid #c9c9c9;color:#666;"
														placeholder="分享简述..."></textarea>
												</c:otherwise>
											</c:choose>
										</p>									
										<p>
											<label>活动结束说明</label>
											<c:choose>
												<c:when test="${not empty promoterBean.endDesc}">
													<textarea name="endDesc"
														style="width:550px;height:160px;padding:5px;border:1px solid #c9c9c9;color:#666;"
														>${promoterBean.endDesc }</textarea>
												</c:when>
												<c:otherwise>
													<textarea name="endDesc"
														style="width:550px;height:160px;padding:5px;border:1px solid #c9c9c9;color:#666;"
														placeholder="活动结束说明..."></textarea>
												</c:otherwise>
											</c:choose>
										</p>
										<p>
									</fieldset>
									<br>
								</div>
								<div class="Content-Main">
									<fieldset style="border-width: 2px; border-color: #008000; width:95%; height:450">
										<legend>邀请码限制配置</legend>
										<p>
											<label>分享连接地址</label> <input type="text"  name="h5Url"  value="${redeemCodeBean.h5Url }" class="iText"/> 
										</p>
										<p>
											<label>分享小图标地址</label> <input type="text"  name="imgUrl"  value="${redeemCodeBean.imgUrl }" class="iText"/> 
										</p>
										<p>
											<label>分享标题</label> <input type="text"  name="title"  value="${redeemCodeBean.title }" class="iText"/>
										</p>
									    <p>
											<label>单台设备可生成</label> <input type="number"  name="deviceCount"  value="${redeemCodeBean.deviceCount }"style="width: 60px" min="0"/> <label>个邀请码</label>
										</p>
										<p>
											<label>单台设备参与活动次数限制</label> <input type="number"  name="deviceLimit"  value="${redeemCodeBean.deviceLimit }"style="width: 60px" min="0"/> <label>次</label>
										</p>														
										<p>
											<label>一个邀请码一共可有效邀请</label><input readonly = "readonly" id="times"
												type="number" name="recommandCount"
												value="${redeemCodeBean.recommandCount }" style="width: 60px;background-color:grey"
												min="0" /> <label>次</label>
										</p>
									</fieldset>								
								</div>

								<div class="Content-Buttom"> 
								  <p align="center">
									<label><input type="submit" style="width:100px ;height:30px" value="保存" onclick="return checkTime();"/></label>
				                 </p>
								</div>

								<div style="clear:both"></div>					  
							  </div>
							  <div id="tabs2">
								   <div class="Content-Left">
											<fieldset
												style="border-width: 2px; border-color: #008000; width:95%; height:220">
											<legend>推广员A</legend>
											<p>
												<label>首次获得</label> <input id="promoterA" type="number" name="promoterA" value="${integralSchemeBean.promoterA }" style="width: 60px"min="0" > <label>个积分</label>
												 <label><font color="red">注：</font>首次积分为玩家B获得注册积分后，玩家A获得的奖励积分</label>
											</p>
											<p>
												<label>第一阶段获得</label> <input id="promoterAFirst" type="number" name="promoterAFirst" value="${integralSchemeBean.promoterAFirst }" style="width: 60px"min="0" > <label>个积分</label>
								                <label><font color="red">注：</font>第一阶段获得为玩家B达到第一阶段等级时，A获得的奖励积分</label>
											</p>
											<p>
												<label>第二阶段获得</label> <input id="promoterASecond" type="number" name="promoterASecond" value="${integralSchemeBean.promoterASecond }" style="width: 60px"min="0" > <label>个积分</label>
								                <label><font color="red">注：</font>第二阶段获得为玩家B达到第二阶段等级时，A获得的奖励积分</label>
											</p>
											<p>
												<label>第三阶段获得</label> <input id="promoterAThird" type="number" name="promoterAThird" value="${integralSchemeBean.promoterAThird }" style="width: 60px"min="0" > <label>个积分</label>
								                <label><font color="red">注：</font>第三阶段获得为玩家B达到第三阶段等级时，A获得的奖励积分</label>
											</p>														
											<p>
											    <label>推广积分上限</label> <input
													id="scoreLimit" type="number" name="scoreLimit" value="${(integralSchemeBean.promoterA+integralSchemeBean.promoterAFirst+integralSchemeBean.promoterASecond+integralSchemeBean.promoterAThird)*redeemCodeBean.recommandCount }"
													style="width: 60px" min="0" /> <label>个积分</label>
											</p>
											<p>
												<label>推广员等级要求</label> <input id="promoterALevel" type="number" name="promoterALevel" value="${integralSchemeBean.promoterALevel }" style="width: 60px"min="0" > <label>级</label>
								                <label><font color="red">注：</font>成为推广员的等级要求,没有要求输入0</label>
											</p>
											<p>
												<label>推广员时间要求</label> <input id="promoterATime" type="number" name="promoterATime" value="${integralSchemeBean.promoterATime }" style="width: 60px"min="0" > <label>秒</label>
								                <label><font color="red">注：</font>成为推广员的在线时长要求,没有要求输入0</label>
											</p>
											<p>
												<label>充值奖励</label> <input type="number" name="rechargeA" value="${integralSchemeBean.rechargeA}" style="width: 60px"min="0" > <label>个积分</label>
								                <label><font color="red">注：</font>B充值后A获得的积分，没有奖励输入0</label>
											</p>							
										</fieldset>
										<br>
										<fieldset style="border-width: 2px; border-color: #008000; width:95%; height:220">
											<legend>推广员B</legend>
											<p>
												<label>新用户注册获得</label> <input type="number" name="register" value="${integralSchemeBean.register }" style="width: 60px"min="0"/> <label>个积分</label>
											</p>
											<p>
												<label>第一阶段成长获得</label> <input type="number" name="promoterB" value="${integralSchemeBean.promoterB }" style="width: 60px"  min="0" > <label>个积分 &nbsp;&nbsp;&nbsp; <font color="red">注：</font>第一阶段奖励积分为玩家B达到第一阶段等级后，玩家B获得的奖励积分</label>
											</p>
											<p>
												<label>第二阶段成长获得</label> <input type="number" name="promoterBSecond" value="${integralSchemeBean.promoterBSecond }" style="width: 60px"  min="0" > <label>个积分 &nbsp;&nbsp;&nbsp; <font color="red">注：</font>第二阶段奖励积分为玩家B达到第二阶段等级后，玩家B获得的奖励积分</label>
											</p>
											<p>
												<label>第三阶段成长获得</label> <input type="number" name="promoterBThird" value="${integralSchemeBean.promoterBThird }" style="width: 60px"  min="0" > <label>个积分 &nbsp;&nbsp;&nbsp; <font color="red">注：</font>第三阶段奖励积分为玩家B达到第三阶段等级后，玩家B获得的奖励积分</label>
											</p>														
											<label>是否发放新手礼包</label>
											       <input type="radio" <c:if test="${integralSchemeBean.redeemCode==1 }">checked="checked"</c:if> name="redeemCode" value="1" class="inputStyle"/>是&nbsp;&nbsp;&nbsp;&nbsp;
											       <input type="radio" <c:if test="${integralSchemeBean.redeemCode==0 }">checked="checked"</c:if> name="redeemCode" value="0" class="inputStyle"/>否
											</p>
											<p>
												<label>充值奖励</label> <input type="number" name="rechargeB" value="${integralSchemeBean.rechargeB}" style="width: 60px"min="0" > <label>个积分</label>
								                <label><font color="red">注：</font>B充值后B获得的积分，没有奖励输入0</label>
											</p>							
										</fieldset>							
									</fieldset>							   
								   </div>
								   <div class="Content-Main">
										<fieldset style="border-width: 2px; border-color: #008000; width:95%; height:220">
											<legend>奖励条件</legend>
											<p>
												<label>推广员B级数要求(第一阶段)</label> <input type="number" name="level" value="${integralSchemeBean.level }" style="width: 60px" min="0" /> <label>级</label>
											    <label>游戏角色在线时间要求</label> <input type="number" name="time" value="${integralSchemeBean.time }" style="width: 60px" min="0" /> <label>秒</label>
											</p>
											<p>
												<label>推广员B级数要求(第二阶段)</label> <input type="number" name="levelSecond" value="${integralSchemeBean.levelSecond }" style="width: 60px" min="0" /> <label>级</label>
												<label>充值奖励金额下限</label> <input type="number" name="recharge" value="${integralSchemeBean.recharge }" style="width: 60px" min="0" /> <label>元</label>
											</p>
											<p>
												<label>推广员B级数要求(第三阶段)</label> <input type="number" name="levelThird" value="${integralSchemeBean.levelThird }" style="width: 60px" min="0" /> <label>级</label>
											</p>
											<p>														
										  		<label>是否开启现金兑换</label>
									       		<input type="radio" checked="checked" name="exchCash" value="1" class="inputStyle"/>开启&nbsp;&nbsp;&nbsp;&nbsp;
									       		<input type="radio" name="exchCash" value="0" class="inputStyle" />关闭
							              	</p>
										  	<p>
												<label>可分享方式</label>
												<c:choose>
												   <c:when test="${not empty redeemCodeBean.shareMethod and fn:contains(redeemCodeBean.shareMethod,'1')}">
												              微信&朋友圈<input type="checkbox" name="shareMethod" checked="checked"  value="1" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:when>
												   <c:otherwise>
												             微信&朋友圈<input type="checkbox" name="shareMethod" value="1" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:otherwise>
												</c:choose>
												<c:choose>
												   <c:when test="${not empty redeemCodeBean.shareMethod and fn:contains(redeemCodeBean.shareMethod,'4')}">
												     QQ<input type="checkbox" name="shareMethod" checked="checked"  value="4" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:when>
												   <c:otherwise>
												     QQ<input type="checkbox" name="shareMethod"   value="4" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:otherwise>
												</c:choose>
												
												<c:choose>
												   <c:when test="${not empty redeemCodeBean.shareMethod and fn:contains(redeemCodeBean.shareMethod,'8')}">
												              微博<input type="checkbox" name="shareMethod" checked="checked"  value="8" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:when>
												   <c:otherwise>
												             微博<input type="checkbox" name="shareMethod"   value="8" class="inputStyle"/>&nbsp;&nbsp;&nbsp;&nbsp;
												   </c:otherwise>
												</c:choose>
										  </p>
										  <p>
												<label>点击微信分享奖励</label> <input type="number" name="weixinClickShare" value="${integralSchemeBean.weixinClickShare }" style="width: 60px" min="0" /> <label>分</label>
											    <label>微信分享成功奖励</label> <input type="number" name="weixinClickShareSuccess" value="${integralSchemeBean.weixinClickShareSuccess }" style="width: 60px" min="0" /> <label>分</label>
										  </p>
										  <p>
												<label>点击QQ分享奖励</label> <input type="number" name="qqClickShare" value="${integralSchemeBean.qqClickShare }" style="width: 60px" min="0" /> <label>分</label>
											    <label>QQ分享成功奖励</label> <input type="number" name="qqClickShareSuccess" value="${integralSchemeBean.qqClickShareSuccess }" style="width: 60px" min="0" /> <label>分</label>
										  </p>
										  <p>
												<label>点击微博分享奖励</label> <input type="number" name="weiboClickShare" value="${integralSchemeBean.weiboClickShare }" style="width: 60px" min="0" /> <label>分</label>
											    <label>微博分享成功奖励</label> <input type="number" name="weiboClickShareSuccess" value="${integralSchemeBean.weiboClickShareSuccess }" style="width: 60px" min="0" /> <label>分</label>
										  </p>											  										  										  										  							              
									</fieldset>								   
								   </div>
								<div class="Content-Buttom"> 
								  <p align="center">
									<label><input type="submit" style="width:100px ;height:30px" value="保存" onclick="return checkTime();"/></label>
				                 </p>
								</div>
								   <div style="clear:both"></div>
							  </div>
							  <div id="tabs3">
									<p>
										<label for="datetimepicker_dark4">兑换开始时间</label> <input type="text"
											id="datetimepicker_dark4" placeholder="点击选择日期时间" class="iText"
											name="exchBeginTime" value="${exchBean.exchBeginTime }" />
									</p>
									<p>
										<label for="datetimepicker_dark5">兑换结束时间</label> <input type="text"
											id="datetimepicker_dark5" placeholder="点击选择日期时间" class="iText"
											name="exchEndTime" value="${exchBean.exchEndTime }" />
									</p>
			
									<br>
									<fieldset
										style="border-width: 2px; border-color: #008000; width:50%; height:220">
										<legend>现金兑换方案</legend>
										<p>
											<label>现金兑换周期</label> 
											<select id="period1" name="period1">
												<option>----请选择周期----</option>
												<option value="1" <c:if test="${exchBean.period1==1 }">selected="true"</c:if>>每周</option>
												<option value="2" <c:if test="${exchBean.period1==2 }">selected="true"</c:if>>每天</option>
											</select>
											<select class="period2">
												<option>----请选择星期----</option>
											</select> 
											<select class="period2" name="period2">
												<option value="1" <c:if test="${exchBean.period2==1 }">selected="true"</c:if>>星期一</option>
												<option value="2" <c:if test="${exchBean.period2==2 }">selected="true"</c:if>>星期二</option>
												<option value="3" <c:if test="${exchBean.period2==3 }">selected="true"</c:if>>星期三</option>
												<option value="4" <c:if test="${exchBean.period2==4 }">selected="true"</c:if>>星期四</option>
												<option value="5" <c:if test="${exchBean.period2==5 }">selected="true"</c:if>>星期五</option>
												<option value="6" <c:if test="${exchBean.period2==6 }">selected="true"</c:if>>星期六</option>
												<option value="7" <c:if test="${exchBean.period2==7 }">selected="true"</c:if>>星期日</option>
											</select>
										</p>
										<p>
											<label>现金兑换上限</label> <input type="number" name="exchLimit"
												value="${exchBean.exchLimit }" style="width: 60px" min="0">
											<label>积分</label>
										</p>
			
										<p>
											<label>现金兑换标准 </label> <input type="number"
												name="exchStandard1" value="${exchBean.exchStandard1 }"
												style="width: 60px" min="0" /> 积分,兑换  <input type="number"
												name="exchStandard2" value="${exchBean.exchStandard2 }"
												style="width: 60px" min="0" /> 元
										</p>
									</fieldset>
									<p>
										<label><input type="submit"
											style="width:100px ;height:30px" value="保存" /></label> 
									</p>		  

				               <div style="clear:both"></div>
							  </div>
						</div>
						</div>						
						<!-- 表单结束标记 -->
					</form>
				</div>
			</div>
		</div>
	</div>
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.hashchange.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.easytabs.min.js" type="text/javascript"></script>
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
			$('#datetimepicker_dark4').datetimepicker({
				theme : 'dark'
			})
			$('#datetimepicker_dark5').datetimepicker({
				theme : 'dark'
			})
		
			
		$('#scoreLimit').bind('input propertychange', function() {
			var scoreLimit = parseInt($("#scoreLimit").val());
			var promoterA = parseInt($("#promoterA").val());
			var promoterASecond = parseInt($("#promoterASecond").val());
			var promoterAFirst = parseInt($("#promoterAFirst").val());
			var promoterAThird = parseInt($("#promoterAThird").val());
			/*
            console.log("promoterA="+promoterA);
            console.log("promoterAFirst="+promoterAFirst);
            console.log("promoterASecond="+promoterASecond);
            console.log("promoterAThird="+promoterAThird);
            console.log("scoreLimit="+scoreLimit);
            console.log("值发生了变化");*/
			$("#times").val(Math.floor(scoreLimit/(promoterA+promoterASecond+promoterAFirst+promoterAThird)));

		});
		
		$('#promoterA').bind('input propertychange', function() {
				var scoreLimit = parseInt($("#scoreLimit").val());
				var promoterA = parseInt($("#promoterA").val());
				var promoterASecond = parseInt($("#promoterASecond").val());
				var promoterAFirst = parseInt($("#promoterAFirst").val());
				var promoterAThird = parseInt($("#promoterAThird").val());
				/*
	            console.log("promoterA="+promoterA);
	            console.log("promoterAFirst="+promoterAFirst);
	            console.log("promoterASecond="+promoterASecond);
	            console.log("promoterAThird="+promoterAThird);
	            console.log("scoreLimit="+scoreLimit);
	            console.log("值发生了变化");*/
				$("#times").val(Math.floor(scoreLimit/(promoterA+promoterASecond+promoterAFirst+promoterAThird)));
			});
		$('#promoterAFirst').bind('input propertychange', function() {
			var scoreLimit = parseInt($("#scoreLimit").val());
			var promoterA = parseInt($("#promoterA").val());
			var promoterASecond = parseInt($("#promoterASecond").val());
			var promoterAFirst = parseInt($("#promoterAFirst").val());
			var promoterAThird = parseInt($("#promoterAThird").val());
			/*
            console.log("promoterA="+promoterA);
            console.log("promoterAFirst="+promoterAFirst);
            console.log("promoterASecond="+promoterASecond);
            console.log("promoterAThird="+promoterAThird);
            console.log("scoreLimit="+scoreLimit);
            console.log("值发生了变化");*/
			$("#times").val(Math.floor(scoreLimit/(promoterA+promoterASecond+promoterAFirst+promoterAThird)));
		});		
		$('#promoterASecond').bind('input propertychange', function() {
			var scoreLimit = parseInt($("#scoreLimit").val());
			var promoterA = parseInt($("#promoterA").val());
			var promoterASecond = parseInt($("#promoterASecond").val());
			var promoterAFirst = parseInt($("#promoterAFirst").val());
			var promoterAThird = parseInt($("#promoterAThird").val());
			/*
            console.log("promoterA="+promoterA);
            console.log("promoterAFirst="+promoterAFirst);
            console.log("promoterASecond="+promoterASecond);
            console.log("promoterAThird="+promoterAThird);
            console.log("scoreLimit="+scoreLimit);
            console.log("值发生了变化");*/
			$("#times").val(Math.floor(scoreLimit/(promoterA+promoterASecond+promoterAFirst+promoterAThird)));
		});
		$('#promoterAThird').bind('input propertychange', function() {
			var scoreLimit = parseInt($("#scoreLimit").val());
			var promoterA = parseInt($("#promoterA").val());
			var promoterASecond = parseInt($("#promoterASecond").val());
			var promoterAFirst = parseInt($("#promoterAFirst").val());
			var promoterAThird = parseInt($("#promoterAThird").val());
			/*
            console.log("promoterA="+promoterA);
            console.log("promoterAFirst="+promoterAFirst);
            console.log("promoterASecond="+promoterASecond);
            console.log("promoterAThird="+promoterAThird);
            console.log("scoreLimit="+scoreLimit);
            console.log("值发生了变化");*/
			$("#times").val(Math.floor(scoreLimit/(promoterA+promoterASecond+promoterAFirst+promoterAThird)));
		});
		//这个是自定义添加的验证方法
		
		function checkTime() {
			var startTime = $("#datetimepicker_dark1").val();
			console.log("开始时间 "+startTime);
			var start = new Date(startTime.replace("-", "/").replace("-", "/"));
			var endTime = $("#datetimepicker_dark2").val();
			console.log("结束时间 "+endTime);
			var end = new Date(endTime.replace("-", "/").replace("-", "/"));
			var preTime = $("#datetimepicker_dark3").val();
			console.log("预热时间 "+preTime);
			var pre = new Date(preTime.replace("-", "/").replace("-", "/"));
			var isSuccess = 1;  
			if(start > end) {
				//alert("开始时间应该不大于结束时间！");
				
		        $("#endTimeLabel").text("开始时间应该不大于结束时间！") ; 
		        $("#endTimeLabel").css({"color":"red"}); 
				isSuccess = 0;
				console.log("开始时间大于结束时间 "+start+"  "+end +" isSuccess "+isSuccess);
			}
			if(pre > start || pre  > end) {
				//alert("预热时间应该不大于开始时间！");
				
		        $("#preTimeLabel").text("预热时间应该不大于开始时间！")  ;
		        $("#preTimeLabel").css({"color":"red"});
		        isSuccess =0;
		        console.log("预热时间大于开始时间 "+start+"  "+pre +"  isSuccess "+isSuccess);
			}
            console.log("开始时间 = "+start+" 结束时间= "+end+"  预热时间 = "+pre);
            if(isSuccess == 0) {
            	console.log("输入错误！ " + isSuccess);
            	return false;
            }
		};
		
/*		
	//判断时间输入	
		function checkEndTime(){
			var startTime=$("#startTime").val();
			var start=new Date(startTime.replace("-", "/").replace("-", "/"));
			var endTime=$("#endTime").val();
			var end=new Date(endTime.replace("-", "/").replace("-", "/"));
			if(end<start){
			 	return false;
			}
			return true;
		}
*/
		
		
		
	var validator1;
    $(document).ready(function () {
        validator1 = $("#Form").validate({
            debug: true,
            rules: {
                gameId: {
                    required: true,
                    minlength: 1,
                    maxlength: 16
                },
                beginTime: {
                    required: true,
                },
                endTime: {
                    required: true,
                },
                preheatingTime: {
                    required: true,
                },
                deviceCount: {
                    required: true,
                    minlength: 1,
                    maxlength: 2
                },
                register: {
                    required: true,
                    minlength: 1,
                    maxlength: 3
                },
                scoreLimit: {
                    required: true,
                    minlength: 1,
                    maxlength: 10
                },               
                level: {
                    required: true,
                    minlength: 1
                }, 
                levelSecond: {
                    required: true,
                    minlength: 1
                },
                levelThird: {
                    required: true,
                    minlength: 1
                }, 
                time: {
                    required: true,
                    minlength: 1
                }, 
                promoterA: {
                    required: true,
                    minlength: 1
                },
                promoterASecond: {
                    required: true,
                    minlength: 1
                },
                promoterAFirst: {
                    required: true,
                    minlength: 1
                },
                promoterAThird: {
                    required: true,
                    minlength: 1
                },
                promoterALevel: {
                    required: true,
                    minlength: 1
                },
                promoterATime: {
                    required: true,
                    minlength: 1
                },
                promoterB: {
                    required: true,
                    minlength: 1
                },
                promoterBSecond: {
                    required: true,
                    minlength: 1
                },
                promoterBThird: {
                    required: true,
                    minlength: 1
                },
                h5Url: {
                    required: true,
                    minlength: 1,
                    maxlength: 1024
                },
                imgUrl: {
                    required: true,
                    minlength: 1,
                    maxlength: 1024
                },
                title: {
                    required: true,
                    minlength: 1,
                    maxlength: 50
                },
                preheatingUrl: {
                    required: true,
                    minlength: 1,
                    maxlength: 1024
                },
                endDesc: {
                	maxlength: 500
                },
                recharge: {
                	required: true
                },
                rechargeA: {
                	required: true
                },
                rechargeB: {
                	required: true
                },
				exchBeginTime : {
					required : true
				},
				exchEndTime : {
					required : true
				},
				exchLimit : {
					required : true,
					minlength : 1,
					maxlength : 10
				},
				exchStandard1 : {
					required : true,
					minlength : 1,
					maxlength : 4
				},
				exchStandard2 : {
					required : true,
					minlength : 1,
					maxlength : 4
				},
				period1 : {
					required : true,
					number:true
				}                
            },
            messages: {
                gameId: {
                    required: '请输入游戏ID',
                    minlength: '游戏ID不能小于1个字符',
                    maxlength: '游戏ID不能超过16个字符'
                },
                beginTime: {
                    required: '请选择或者输入活动开始时间'
                },
                endTime: {
                    required: '请选择或者输入活动结束时间'

                },
                preheatingTime: {
                    required: '请选择或者输入活动预热时间'
                },
                deviceCount: {
                    required: '请输入单台设备可生成的兑换码个数',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过3个字符'
                },
                register: {
                    required: '请输入新用户注册可获得多少积分，没有积分请输入0',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过4个字符'
                },
                scoreLimit: {
                    required: '请输入活动积分上限',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过4个字符'
                },
                level: {
                    required: '请输入角色级数要求，没有要求请输入0',
                    minlength: '不能小于1个字符'
                },
                levelSecond: {
                    required: '请输入角色级数要求，没有要求请输入0',
                    minlength: '不能小于1个字符'
                },
                levelThird: {
                    required: '请输入角色级数要求，没有要求请输入0',
                    minlength: '不能小于1个字符'
                },
                time: {
                    required: '请输入游戏在线时长要求，没有要求请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterA: {
                    required: '请输入推广人员首次可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterAFirst: {
                    required: '请输入推广人员第一阶段可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterASecond: {
                    required: '请输入推广人员第二阶段可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterAThird: {
                    required: '请输入推广人员第三阶段可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterALevel: {
                    required: '请输入成为推广人员的等级要求，没有积分请输入0',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过4个字符'
                },
                promoterATime: {
                    required: '请输入成为推广员的时间要求，没有积分请输入0',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过4个字符'
                },
                promoterB: {
                    required: '请输入被推广人员可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterBSecond: {
                    required: '请输入被推广人员第二阶段可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                promoterBThird: {
                    required: '请输入被推广人员第三阶段可获得的积分，没有积分请输入0',
                    minlength: '不能小于1个字符'
                },
                h5Url: {
                    required: '请输入分享链接地址',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过1024个字符'
                },
                imgUrl: {
                    required: '请输入分享小图标的地址',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过1024个字符'
                },
                title: {
                    required: '请输入分享标题',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过50个字符'
                },
                preheatingUrl: {
                    required: '请输入活动预热对应的h5url',
                    minlength: '不能小于1个字符',
                    maxlength: '不能超过1024个字符'
                },
                endDesc: {
                	maxlength:'不能超过500个字符'
                },
                recharge: {
                	required: '请输入充值奖励金额下限'
                },
                rechargeA: {
                	required: '请输入B充值后A获得的奖励积分'
                },
                rechargeB: {
                	required:  '请输入B充值后B获得的奖励积分'
                },
				period1 : {
					required : '请选择周期',
				    number:'请选择周期'
				},
				exchBeginTime : {
					required : '请输入兑换开始时间'
				},
				exchEndTime : {
					required : '请输入兑换结束时间'
				},
				exchLimit : {
					required : '兑换积分上限',
					minlength : '兑换上限至少为1位',
					maxlength : '兑换上限至多为4位'

				},
				exchStandard1 : {
					required : '请输入积分',
					minlength : '不能小于1个字符',
					maxlength : '不能超过4个字符'
				},
				exchStandard2 : {
					required : '请输入元',
					minlength : '不能小于1个字符',
					maxlength : '不能超过4个字符'
				}                
            },

            highlight: function(element, errorClass, validClass) {
                $(element).addClass(errorClass).removeClass(validClass);
                $(element).fadeOut().fadeIn();
            },
            unhighlight: function(element, errorClass, validClass) {
                $(element).removeClass(errorClass).addClass(validClass);
            },
            submitHandler: function (form) {
                console.log($(form).serialize());
                form.submit();
            }
        });

    });

   $(document).ready( function() {
  	      $('#tab-container').easytabs();
   });
   //兑换周期
	$(document).ready(function() {
		$("#period1").change(function() {
			$("#period1 option").each(function(i, o) {
				if ($(this).attr("selected")) {
					$(".period2").hide();
					$(".period2").eq(i).show();
				}
			});
		});
		$("#period1").change();
	});
</script>
</body>
</html>
