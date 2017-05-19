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
<link href="<%=request.getContextPath()%>/resources/css/reveal.css" rel="stylesheet"type="text/css" >
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css" />
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
			<span>欢迎您：</span><a class="user-name"
				href="<%=request.getContextPath()%>/promoter/index">${pageContext.request.userPrincipal.name}</a>
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
				<span>当前位置：</span> <strong>活动配置 > 黑名单</strong>
			</div>
			<c:if test="${empty search}">
				<div class="toolbar">
					<form action="<%=request.getContextPath()%>/promoter/blacklist" method="get" id="Form">
						<p class="fl">
								<input type="hidden" name="gameId" value="${gameId}" />
								<input type="hidden" name="actId" value="${actId}" />
								<input type="hidden" name="gameName" value="${gameName }" />
						</p>
						<p>
						<label>玩家PlayerId</label>
						<input type="text" name="playerId" class="iText" value="${playerId }" />
						<input type="submit" value="查询" class="btn" />
						<a href="javascript:void(0);" id="add" item-id="${actId}" data-reveal-id2="myModal2" data-animation="fade" class="btn">新增</a>
						<a href="<%=request.getContextPath()%>/promoter/gotoblacklistimport?gameId=${gameId}&actId=${actId}&gameName=${gameName}" style="background-color:green" class="btn">导入</a>
						<a href="<%=request.getContextPath()%>/promoter/exportblacklist?gameId=${gameId}&actId=${actId}" style="background-color:green" class="btn">导出Excel</a>
						</p>
					</form>
				</div>
			</c:if>
			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th>游戏名称</th>
								<th>游戏ID</th>
								<th>添加时间</th>
								<th>黑名单ID</th>
								<th>过期时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty list }">
						 <tr class="odd">
						 	<td colspan="10"><label>您好,当前该游戏还没有黑名单  <c:if test="${not empty playerId}"><font color="red">${playerId}</font></c:if>,您可以点击  “新增” 添加黑名单,或者换个查询条件试试吧！</label>
						 		<a href="javascript:void(0);" id="add" item-id="${actId}" data-reveal-id2="myModal2" data-animation="fade" class="btn" style="background-color:pink" >新增</a>
						 	</td>
						 </tr>
						</c:if>
						<c:if test="${not empty list }">
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td>${gameName}</td>
								<td>${list.gameId}</td>
								<td>${list.getInsertTime(1)}</td>
								<td>
									<a id="detail" data-reveal-id1="detail" href="javascript:void(0);" title="点击查看用户积分详情" item-id1="${list.playerId}" item-id2="${list.gameId}" style="background-color:white;color:#36648B" >${list.playerId}</a>
								</td>
								<td>${list.getExpireTime(1)}</td>
								<td>
									<a href="javascript:void(0);" id="delete" item-id="${list.playerId}" data-reveal-id="myModal" data-animation="fade">移除</a>
							    </td>
							</tr>
							</c:forEach>
						</c:if>
						</tbody>
					</table>
				</div>
				<div class="pagination">
                    <c:if test="${rowCount ne 0 }">
                    	<span>共有：${rowCount }条</span>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要移除该条黑名单信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/delblacklist" method="post">
			<input type="hidden" id="playerId" name="playerId" value="1" />
			<input type="hidden" name="gameId" value="${gameId}" />
			<input type="hidden" name="actId" value="${actId}" />
			<input type="hidden" name="gameName" value="${gameName }" />
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
			</form>
	</div>
	
	<div id="myModal2" class="reveal-modal">
			<center><h1 ><font color="red">请输入黑名单用户的playerId</font></h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/addblacklist" method="post">
			<input type="hidden" id="id" name="id" value="1" />
			<input type="hidden" name="gameId" value="${gameId}" />
			<input type="hidden" name="actId" value="${actId}" />
			<input type="hidden" name="gameName" value="${gameName }" />
			&nbsp;&nbsp;&nbsp;&nbsp;用户账号:<input type="text" name="playerId"/><br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;过期时间:<input type="text" name="expireTime" value="0"/>&nbsp;&nbsp;秒<br>
			<br>
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id2="myModal2" >取消</a>
			</form>
	</div>
	
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
		$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
	
		//删除
		$('a[data-reveal-id]').live('click', function () {
			var playerId = $(this).attr("item-id");
			console.log(playerId);
	        //e.preventDefault();
	        $("#playerId").val(playerId);
	        var modalLocation = $(this).attr('data-reveal-id');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id]').live('click', function () {
         jQuery("#myModal").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   	
	   	
		var validator1;
		$(document).ready(function() {
			validator1 = $("#Form").validate({
				debug : true,

				rules : {
					endTime : {
						required : true,
					}
			},
			messages : {
					endTime : {
						required : '请输入结束时间'
					}
				},

				highlight : function(element, errorClass, validClass) {
					$(element).addClass(errorClass).removeClass(validClass);
					$(element).fadeOut().fadeIn();
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).removeClass(errorClass).addClass(validClass);
				},
				submitHandler : function(form) {
					console.log($(form).serialize());
					form.submit();
				}
			});

		});
		
		//处理拉取用户信息的弹出框
        $('a[data-reveal-id1]').live('click', function () {
        	console.log("开始获取userid");
            var userId = $(this).attr("item-id1");
            var gameId = $(this).attr("item-id2");
            console.log("用户id: "+userId);
            console.log("游戏id: "+gameId);
		    popWin.showWin("1000","600","玩家积分的来源详情","<%=request.getContextPath()%>/promoter/detail?userId="+userId+"&gameId="+gameId);

		});
		//新建黑名单
		$('a[data-reveal-id2]').live('click', function () {
			var id = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#id").val(id);
	        var modalLocation = $(this).attr('data-reveal-id2');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id2]').live('click', function () {
         jQuery("#myModal2").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id2').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });

	</script>
</body>
</html>
