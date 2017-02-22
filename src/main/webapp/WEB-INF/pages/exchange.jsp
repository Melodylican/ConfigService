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
					<li class="cur"><a href="<%=request.getContextPath()%>/exchange/exchange">现金兑换配置</a></li>
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
				<span>当前位置：</span> <strong>积分兑换配置管理</strong>
			</div>
				<div class="toolbar">
					<form action="<%=request.getContextPath()%>/exchange/exchange" method="get" id="Form">
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="searchGameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${searchGameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
						</p>
						<p class="fl">
							开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="exchBeginTime" value="${exchBeginTime}" class="iText" />
						</p>
						<p class="fl">
							结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="exchEndTime" value="${exchEndTime}" class="iText" />
						</p>
						<input type="submit" value="查询" class="btn" />
						<a href="<%=request.getContextPath()%>/exchange/create" class="btn">新建</a>
					</form>
				</div>

			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr
								style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th><span class="checkbox" id="checkAll"></span></th>
								<th>游戏名称</th>
								<th>兑换开始时间</th>
								<th>兑换结束时间</th>
								<th>现金兑换周期</th>
								<th>现金兑换上限</th>
								<th>现金兑换标准</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty search }">
						<c:if test="${empty list }">
						 <tr class="odd"><td colspan="10"><label>您好,您当前还没有创积分兑换规则,快点击“新建”来创建吧！</label><a href="<%=request.getContextPath()%>/exchange/create" style="background-color:green" >新建</a></td></tr>
						</c:if>
						</c:if>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td><span class="checkbox"></span></td>
								<td>${list.gameName}</td>
								<td>${list.exchBeginTime}</td>
								<td>${list.exchEndTime}</td>
								<td>
								     <c:if test="${list.period1==2 }">每天都可兑换</c:if>
								     <c:if test="${list.period1==1 }">
								                    每周 <c:if test="${list.period2==1 }">周一 兑换</c:if>
								           <c:if test="${list.period2==2 }">周二 兑换</c:if>
								           <c:if test="${list.period2==3 }">周三 兑换</c:if>
								           <c:if test="${list.period2==4 }">周四 兑换</c:if>
								           <c:if test="${list.period2==5 }">周五 兑换</c:if>
								           <c:if test="${list.period2==6 }">周六 兑换</c:if>
								           <c:if test="${list.period2==7 }">周日 兑换</c:if>
								                    
								     </c:if>
								</td>
								<td>${list.exchLimit }</td>
								<td>${list.exchStandard1} 积分兑换  ${list.exchStandard2} 元</td>
								<td>
								<a href="<%=request.getContextPath()%>/exchange/update?exchangeBean={id:'${list.id }',gameName:'${list.gameName }',exchLimit:'${list.exchLimit }',
								exchBeginTime:'${list.exchBeginTime}',exchEndTime:'${list.exchEndTime}',period1:'${list.period1}',
								period2:'${list.period2}',exchStandard1:'${list.exchStandard1}',exchStandard2:'${list.exchStandard2}'
								}" >修改</a> 
								
								<a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade">删除</a>
							   </td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%>/exchange/exchange?page=${page-1}&pageSize=${pageSize}&searchGameName=${searchGameName}&exchBeginTime=${exchBeginTime}&exchEndTime=${exchEndTime}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1} ">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/exchange/exchange?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}&exchBeginTime=${exchBeginTime}&exchEndTime=${exchEndTime}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/exchange/exchange?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}&exchBeginTime=${exchBeginTime}&exchEndTime=${exchEndTime}" >${index}</a>
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
				                     <a href="<%=request.getContextPath()%>/exchange/exchange?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}&exchBeginTime=${exchBeginTime}&exchEndTime=${exchEndTime}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/exchange/exchange?page=${page+1}&pageSize=${pageSize}">下一页</a>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要删除该条配置信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/exchange/delete" method="post">
			<input type="hidden" name="id" value="1" />
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
			</form>
	</div>
	<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
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
		
		 $('a[data-reveal]').live('click', function () {
        
        	//创建积分规则
        	//jPrompt("111111111111");
        	//setMask("111111111111");
        	console.log("开始获取userid");
            //var userId = $(this).attr("item-id1");
            console.log(userId);
		    popWin.showWin("800","600","创建积分兑换规则","<%=request.getContextPath()%>/exchange/create");

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
					exchBeginTime : {
						required : true,
					},
					exchEndTime : {
						required : true,
					}
			},
			messages : {
					exchBeginTime : {
						required : '请输入开始时间',
					},
					exchEndTime : {
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

			//    $("#check").click(function () {
			//      console.log($("#Form").valid() ? "填写正确" : "填写不正确");
			// });
		});

	</script>
</body>
</html>
