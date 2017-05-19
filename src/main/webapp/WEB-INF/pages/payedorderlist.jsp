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
<link href="<%=request.getContextPath()%>/resources/css/reset.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/reveal.css" rel="stylesheet"type="text/css" >
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css"  rel="stylesheet" type="text/css" />

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
					<li class="cur"><!-- 查看已支付的订单 -->
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
				<span>当前位置：</span> <strong>现金兑换订单列表  
				<c:if test="${not empty msg}"><h1><font color="red">${msg}</font></h1></c:if></strong>
			</div>
			<div class="toolbar">
			      <form method="post"  action="<%=request.getContextPath()%>/promoter/payedorderlistsearch">
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="searchGameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${searchGameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
							<select id="u4_input" name="type">
									<option <c:if test="${type == 1 }">selected="true"</c:if> value="1">已打账订单列表</option>
									<option <c:if test="${type == 2 }">selected="true"</c:if> value="2">未通过审核订单列表</option>
									<option <c:if test="${type == 3 }">selected="true"</c:if> value="3">作废订单列表</option>
							</select>
							<!-- <label>&nbsp;&nbsp;&nbsp;&nbsp;订单编号： </label><input type="text" name="orderId" value="${searchedOrderId }" class="iText"/>  -->							
						</p>
						<p><input type="submit" value="查询" class="btn"></p>
				 </form>
			</div>
			<div class="wrap">
				<div class="grid">
						<table width="1000" id="table2excel">
							<thead>
								<tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
									<th>申请单编号</th>
									<th>订单创建时间</th>
									<th>PlayerID</th>	
									<th>支付宝账号</th>
									<th>申请兑换积分</th>
									<th>积分兑换方案</th>
									<th>兑换金额</th>
									<th>账号当前积分</th>
									<th>操作</th>																		
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="list">
								<tr class="odd">
									<td>${list.orderId}</td>
									<td>${list.orderCreatedDate }</td>
									<td>${list.playerId}</td>									
									<td>${list.payInfo}</td>
									<td>${list.requestExchangePoints }</td>
									<td><fmt:formatNumber type="number" value="${(list.requestExchangePoints/list.amount)}" maxFractionDigits="0"/> 积分兑换 1 元</td>
									<td>${list.amount}</td>
									<td><label>${list.hasPoints}</label>&nbsp;&nbsp;&nbsp; <a id="detail" data-reveal="detail" href="javascript:void(0);" title="点击查看积分详情" item-id1="${list.playerId}" item-id2="${list.gameId}">积分详情</a></td>
									<td><a href="javascript:void(0);" item-cellphone="${list.payInfo}" item-gameid="${list.gameId }" data-reveal-id="myModal" data-animation="fade" class="btn">短息通知</a></td>
								</tr>
								</c:forEach>
								<c:if test="${not empty list }">
								<tr>
								    <td colspan="8" align="left">
								           <p align="left"><a href="<%=request.getContextPath()%>/promoter/exportpayedorder?searchGameName=${searchGameName}&type=${type}" style="background-color:green">导出Excel</a></p>
								    </td>
								    
								</tr>
								</c:if>
							</tbody>
						</table>
			     </div>

				</div>
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%><%=request.getContextPath()%>/promoter/payedorderlistsearch?searchGameName=${searchGameName}&page=${page-1}&pageSize=${pageSize}&type=${type}">上一页</a>
                    </c:if>
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${(index-page< 0 ? page-index:index-page)< 5 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/payedorderlistsearch?searchGameName=${searchGameName}&page=${index}&pageSize=${pageSize}&type=${type}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/payedorderlistsearch?searchGameName=${searchGameName}&page=${index}&pageSize=${pageSize}&type=${type}" >${index}</a>
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
				                     <a href="<%=request.getContextPath()%>/promoter/payedorderlistsearch?searchGameName=${searchGameName}&page=${index}&pageSize=${pageSize}&type=${type}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/payedorderlistsearch?searchGameName=${searchGameName}&page=${page+1}&pageSize=${pageSize}&type=${type}">下一页</a>
                    </c:if>
				</div>
		</div>
	</div>
<!-- 回复吐槽信息弹出层 -->
<div id="myModal" class="reveal-modal3">
	<center><h4 ><font color="red">请输入短信回复信息</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
	<form action="<%=request.getContextPath()%>/promoter/shortmessage" method="post"  data-ajax="false">
		<input type="hidden" name="gameId" id="gameID" value="0" />
		<input type="hidden" name="searchGameName" value="${searchGameName}" />
		<input type="hidden" name="pageSize" value="${pageSize}" />
		<input type="hidden" name="page" value="${page}" />
		<input type="hidden" name="type" value="${type}">
		手机号：<input type="text" name="cellphone" id="cellphone"  value="0"  style="width:670px;height:30px">
		<br>
		<br>
		<textarea type="text" name="content" rows="10" cols="100" style="width:718px;" placeholder="请在此输入回复信息（注意回复信用应简洁）。。。"></textarea><br><br>
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交" /></center>
    </form>
    </center>
</div>
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
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
		
        $('a[data-reveal]').live('click', function () {
        	console.log("开始获取userid");
            var userId = $(this).attr("item-id1");
            var gameId = $(this).attr("item-id2");
            console.log(userId);
		    popWin.showWin("1000","600","玩家积分的来源详情","<%=request.getContextPath()%>/promoter/detail?userId="+userId+"&gameId="+gameId);

		});
        
    	//弹出吐槽回复层
    	$('a[data-reveal-id]').live('click', function () {
    		var cellphone = $(this).attr("item-cellphone");
    		var gameId = $(this).attr("item-gameid");
            //e.preventDefault();
            $("#cellphone").val(cellphone);
            $("#gamdID").val(gameId);
            var modalLocation = $(this).attr('data-reveal-id');
            $('#' + modalLocation).reveal($(this).data());

        });
       
       	$('a[close-reveal-id]').live('click', function () {
         jQuery("#myModal").trigger('reveal:close');
            var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
            $('#' + modalLocation).reveal($(this).data());
        });
  
	</script>
</body>
</html>
