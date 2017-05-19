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
	<link rel="stylesheet"type="text/css" href="<%=request.getContextPath()%>/resources/css/reveal.css">
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />

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
					<li ><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
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
					     <li class="cur"><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li ><a href="<%=request.getContextPath()%>/game/game">游戏管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>用户管理</strong>
			</div>
			<c:if test="${empty search}">
				<div class="toolbar">
					<form action="<%=request.getContextPath()%>//user/search" method="get" id="Form">
						<p class="fl">
							用户名：<input type="text" name="userName" class="iText" />
						</p>
						<input type="submit" value="查询" class="btn" />
						<a href="<%=request.getContextPath()%>/user/create" class="btn">新建</a>
					</form>
				</div>
			</c:if>
			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th>用户名</th>
								<th>密码</th>
								<th>真实名字</th>
								<th>拥有权限</th>
								<th>注册时间</th>
								<th>简述</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty search }">
						<c:if test="${empty list }">
						 <tr class="odd"><td colspan="10"><label>您好,您当前还没有创建推广活动,快点击“新建”创建用户吧！</label><a href="<%=request.getContextPath()%>/promoter/create" style="background-color:green" >新建</a></td></tr>
						</c:if>
						</c:if>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td>${list.userName}</td>
								<td>${list.password}</td>
								<td>${list.userRealName}</td>
								<td>
								    <c:if test="${list.role == 'USER' }">普通用户</c:if>
								    <c:if test="${list.role == 'ADMIN' }">创建推广活动及积分审核权限</c:if>
								    <c:if test="${list.role == 'SUPER_ADMIN' }">超级管理员</c:if>
								</td>
								<td>${list.registerTime}</td>
								<td>
									<p>${list.description}</p>
								</td>
								<td>
								<a href="<%=request.getContextPath()%>/user/update?userBean={userName:'${list.userName }',
								password:'${list.password}',userRealName:'${list.userRealName}',role:'${list.role}',registerTime:'${list.registerTime }'
								,description:'${list.description }',gameType:'${list.gameType }'}" >修改</a> 
								
								<a href="javascript:void(0);" id="delete" item-id="${list.userName}" data-reveal-id="myModal" data-animation="fade">删除</a>
								<c:choose>
								   <c:when test="${list.state == 0 }">
							             <a href="<%=request.getContextPath()%>/user/state?userName=${list.userName }&state=${list.state }&page=${page}&pageSize=${pageSize}" title="点击启用" style="background-color:red">禁用/启用</a>
							       </c:when>
							       <c:otherwise>
							             <a href="<%=request.getContextPath()%>/user/state?userName=${list.userName }&state=${list.state }&page=${page}&pageSize=${pageSize}" title="点击暂停使用" style="background-color:green">禁用/启用</a>
							       </c:otherwise>
							    </c:choose>
							   </td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%>/user/user?page=${page-1}&pageSize=${pageSize}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/usercenter?page=${index}&pageSize=${pageSize}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/user/user?page=${index}&pageSize=${pageSize}" >${index}</a>
                                  </c:otherwise>  
                               </c:choose>
				           </c:when>
				           <c:otherwise>
				                <c:choose>
				                <c:when test="${(index-page< 0 ? page-index:index-page)== 4}">
				                   <span>...</span> 
				                </c:when>
				                <c:otherwise>
				                   	<c:if test="${index==pages }"> 
				                     <a href="<%=request.getContextPath()%>/user/user?page=${index}&pageSize=${pageSize}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/user/user?page=${page+1}&pageSize=${pageSize}">下一页</a>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要删除该条配置信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/user/delete" method="post">
			<input type="hidden" name="userName" value="1" />
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
			</form>
	</div>
	<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
		$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
		
		$('a[data-reveal-id]').live('click', function () {
			var id = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#myModal").find("input:hidden").val(id);
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
					userName : {
						required : true,
					}
			},
			messages : {
					userName : {
						required : '请输入查询用户名',
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
	</script>
</body>
</html>
