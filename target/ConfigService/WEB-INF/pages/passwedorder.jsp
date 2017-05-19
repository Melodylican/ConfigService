<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
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
				<span>当前位置：</span> <strong>通过审核订单支付</strong>
			</div>
			<div class="toolbar">
			      <form method="post"  action="<%=request.getContextPath()%>/promoter/orderpaysearch">
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="searchGameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${searchGameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
						</p>
						<p><input type="submit" value="查询" class="btn"></p>
				 </form>
			</div>
			<div class="wrap">
				<div class="grid">
					<table width="1000" id="table2excel">
						<thead>
							<tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
			     				<th>游戏名称</th>
								<th>乐逗账号</th>
								<th>申请单编号</th>
								<th>支付宝账号</th>
								<th>申请兑换积分</th>
								<th>审核状态</th>
								<th>积分兑换方案</th>
								<th>兑换金额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td>${list.gameName}</td>
								<td>${list.userMemo}</td>
								<td>${list.orderId}</td>
								<td>${list.payInfo}</td>
								<td>${list.requestExchangePoints }</td>
								<td>通过</td>
								<td><fmt:formatNumber type="number" value="${(list.requestExchangePoints/list.amount)}" maxFractionDigits="0"/> 积分兑换 1 元</td>
								<td>${list.amount}</td>
							</tr>
							</c:forEach>
							<c:if test="${not empty list }">
							<tr>
							    <td colspan="8" align="left">
							           <p align="left"><a href="<%=request.getContextPath()%>/promoter/export?searchGameName=${searchGameName}" style="background-color:green" id="delete">导出Excel</a></p>
							    </td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%>/promoter/orderpaysearch?page=${page-1}&pageSize=${pageSize}&searchGameName=${searchGameName}">上一页</a>
                    </c:if>
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${(index-page< 0 ? page-index:index-page)< 5 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/orderpaysearch?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/orderpaysearch?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}" >${index}</a>
                                  </c:otherwise>  
                               </c:choose>
				           </c:when>
				           <c:otherwise>
				                <c:choose>
				                <c:when test="${(index-page< 0 ? page-index:index-page) == 5}">
				                   <span>...</span> 
				                </c:when>
				                <c:otherwise>
				                   	<c:if test="${index==pages }"> 
				                     <a href="<%=request.getContextPath()%>/promoter/orderpaysearch?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/orderpaysearch?page=${page+1}&pageSize=${pageSize}&searchGameName=${searchGameName}">下一页</a>
                    </c:if>
				</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
	<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$(".sidebar").height($(document).height() - 103);
		});
		$("#excel").click(function(){
			console.log("点击");
			//window.open(url)
			//$("#table2excel").tableExport({type: 'excel', escape: 'false'});
			/*
		    $("#table2excel").table2excel({
		    // exclude CSS class
		    exclude: ".noExl",
		    name: "通过审核订单"
		    });
			*/
		});
  
	</script>
</body>
</html>
