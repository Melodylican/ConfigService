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
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
					<li ><a href="<%=request.getContextPath()%>/exchange/exchange">现金兑换配置</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/payedorder">提交已完成订单</a></li>
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
				<span>当前位置：</span> <strong>积分配置管理</strong>
			</div>
			<c:if test="${empty search}">
				<div class="toolbar">
					<form action="<%=request.getContextPath()%>/promoter/search" method="get" id="Form">
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="gameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${search_gameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
						</p>
						<p class="fl">
							国家和地区：<select id="u4_input" name="location">
									<option selected value="中国大陆">中国大陆</option>
									<option value="台湾">台湾</option>
									<option value="香港">香港</option>
								</select>
						</p>
						<p class="fl">
							开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="beginTime" class="iText" />
						</p>
						<p class="fl">
							结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="endTime" class="iText" />
						</p>
						<input type="submit" value="查询" class="btn" />
					</form>
				</div>
			</c:if>
			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th><span class="checkbox" id="checkAll"></span></th>
								<th>游戏名称</th>
								<th>游戏ID</th>
								<th>国家和地区</th>
								<th>类型</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>预热时间</th>
								<th>积分方案</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td><span class="checkbox"></span></td>
								<td>${list.gameName}</td>
								<td>${list.gameId}</td>
								<td>${list.location}</td>
								<td>${list.gameType}</td>
								<td>${list.beginTime}</td>
								<td>${list.endTime}</td>
								<td>${list.preheatingTime }</td>
								<td>
									<p>${list.description}</p>
								</td>
								<td><a href="<%=request.getContextPath()%>/promoter/update?promoterBean={id:'${list.id }',gameName:'${list.gameName }',
								gameId:'${list.gameId}',location:'${list.location}',gameType:'${list.gameType}',beginTime:'${list.beginTime }',
								endTime:'${list.endTime }',preheatingTime:'${list.preheatingTime }',description:'${list.description }'}" >修改</a> <a
									href="<%=request.getContextPath()%>/promoter/create" >新建</a>
									<a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade">删除</a>
							        <c:choose>
								   <c:when test="${list.state == 0 }">
							             <a href="<%=request.getContextPath()%>/promoter/state?id=${list.id }&state=${list.state }&page=${page}&pageSize=${pageSize}" title="点击开启" style="background-color:red">暂停/启用</a>
							       </c:when>
							       <c:otherwise>
							             <a href="<%=request.getContextPath()%>/promoter/state?id=${list.id }&state=${list.state }&page=${page}&pageSize=${pageSize}" title="点击暂停使用">暂停/启用</a>
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
                             <a href="<%=request.getContextPath()%>/promoter/search?page=${page-1}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/search?page=${index}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/search?page=${index}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}" >${index}</a>
                                  </c:otherwise>  
                               </c:choose>
				           </c:when>
				           <c:otherwise>
				                <c:choose>
				                <c:when test="${(index-page< 0 ? page-index:index-page) == 4}">
				                   <span>...</span> 
					               <a href="<%=request.getContextPath()%>/promoter/search?page=${pages}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}">${pages}</a>
				                </c:when>
				                <c:otherwise>
				                	<c:if test="${index==pages }"> 
				                     <a href="<%=request.getContextPath()%>/promoter/search??page=${index}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}""  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/search?page=${page+1}&pageSize=${pageSize}&gameName=${search_gameName}&location=${search_location}&beginTime=${search_beginTime}&endTime=${search_endTime}">下一页</a>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要删除该条配置信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/delete" method="post">
			<input type="hidden" name="id" value="1" />
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
			</form>
	</div>
	<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js" type="text/javascript" ></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
		$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
		$(function() {
			$(".sidebar").height($(document).height() - 103);
			//全选反选
			$("#checkAll").click(
					function() {
						if ($(this).hasClass("checkbox-check")) {
							$(this).parents("table").find("tr").removeClass(
									"check").end().find(".checkbox-check")
									.removeClass("checkbox-check");
						} else {
							$(this).parents("table").find("tr").addClass(
									"check").end().find(".checkbox").addClass(
									"checkbox-check");
						}
					});
			$(".grid").find("tbody").find(".checkbox").click(
					function() {
						if ($(this).hasClass("checkbox-check")) {
							$(this).removeClass("checkbox-check").parents("tr")
									.removeClass("check");
						} else {
							$(this).addClass("checkbox-check").parents("tr")
									.addClass("check");
						}
					});
			//批量删除
			$("#delete").click(function() {
				if ($(this).hasClass("disabled")) {
					return false;
				} else {
					console.log("delete all");
				}
			});
		});
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
					beginTime : {
						required : true,
					},
					endTime : {
						required : true,
					}
			},
			messages : {
					beginTime : {
						required : '请输入开始时间',
					},
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
	</script>
</body>
</html>
