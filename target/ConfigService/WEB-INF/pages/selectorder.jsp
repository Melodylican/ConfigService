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
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
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
				<span>当前位置：</span> <strong>积分兑换订单管理</strong>
			</div>
			<div class="toolbar">
						<p class="fl">
							<label>游戏名称</label> <select id="selectGameName" name="searchGameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${exchBean.gameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
						</p>
				</div>
			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr
								style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th><span class="checkbox" id="checkAll"></span></th>
								<th>申请单编号</th>
								<th>游戏名称</th>
								<th>游戏角色ID</th>
								<th>游戏级数</th>
								<th>在线时长</th>
								<th>账号总积分</th>
								<th>请求兑换积分</th>
								<th>是否超过积分上限</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td><span class="checkbox"></span></td>
								<td>${list.orderId}</td>
								<td>${list.gameName}</td>
								<td>${list.playerId}</td>
								<td>${list.level}</td>
								<td>${list.onlineTime}</td>
								<td>${list.hasPoints}</td>
								<td>${list.requestExchangePoints }</td>
								<td>
									<label>否</label>&nbsp;&nbsp;&nbsp; <a id="detail" data-reveal="detail" href="javascript:void(0);" title="点击查看积分详情" item-id1="${list.playerId}" >积分详情</a>
								</td>
								<td>
								<c:choose>
								   <c:when test="${list.status == 'PASSED' }">
							             <a href="<%=request.getContextPath()%>/promoter/throughaudit?orderId=${list.orderId }&page=${page}&pageSize=${pageSize}" title="点击通过审批" style="background-color:green">通过</a>
							       </c:when>
							       <c:otherwise>
							             <a href="<%=request.getContextPath()%>/promoter/throughaudit?orderId=${list.orderId}&state=${list.status }&page=${page}&pageSize=${pageSize}" title="点击通过审批">通过</a>
							       </c:otherwise>
							    </c:choose>
							    <c:choose>
								   <c:when test="${list.status == 'REJECTED' }">
							             <a href="javascript:void(0);" id="notpass" item-id="${list.id}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批" style="background-color:#206ecd">未通过</a>
							       </c:when>
							       <c:otherwise>
							             <a href="javascript:void(0);" id="notpass" item-id="${list.id}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批">未通过</a>
							       </c:otherwise>
							    </c:choose>
							    <c:choose>
								   <c:when test="${list.status == 'NULL' }">
							             <a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade" title="点击作废" style="background-color:red">作废</a>
							       </c:when>
							       <c:otherwise>
							             <a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade" title="点击作废">作废</a>
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
                             <a href="<%=request.getContextPath()%>/promoter/order?page=${page-1}&pageSize=${pageSize}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/order?page=${index}&pageSize=${pageSize}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/order?page=${index}&pageSize=${pageSize}" >${index}</a>
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
				                     <a href="<%=request.getContextPath()%>/promoter/order?page=${index}&pageSize=${pageSize}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/order?page=${page+1}&pageSize=${pageSize}">下一页</a>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要作废该条信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/invalid" method="post">
			<input type="hidden" name="orderId" value="1" />
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
			</form>
	</div>
	<div id="myModal1" class="reveal-modal">
			<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			请选择未通过审核原因</h1><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/donotpassaudit" method="post">
			<input type="hidden" name="orderId" value="1" />
			<input type="radio" name="reason" checked="checked" value="对不起，您未能满足活动条件中游戏在线时长或者等级的要求" />&nbsp;&nbsp;对不起，您未能满足活动条件中游戏在线时长或者等级的要求<br>
			<input type="radio" name="reason" value="您要求兑换的积分已超过该活动可兑换的积分上限" />&nbsp;&nbsp;您要求兑换的积分已超过该活动可兑换的积分上限<br>
			<input type="radio" name="reason" value="部分积分涉嫌刷单行为" />&nbsp;&nbsp;部分积分涉嫌刷单行为<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交"/>
			</form>
	</div>
	
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/popwin.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script type="text/javascript">
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
        
        	//jAlert("3333");
        	//jPrompt("111111111111");
        	//setMask("111111111111");
        	console.log("开始获取userid");
            var userId = $(this).attr("item-id1");
            console.log(userId);
		    popWin.showWin("800","600","玩家积分的来源详情","<%=request.getContextPath()%>/promoter/detail?userId="+userId);

		});
        //用于作废按钮
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
		//用于未通过按钮
		$('a[data-reveal-id]').live('click', function () {
			var id = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#myModal1").find("input:hidden").val(id);
	        var modalLocation = $(this).attr('data-reveal-id');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id]').live('click', function () {
         jQuery("#myModal1").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	    
        $("#selectGameName").change(function() {
            var checkValue=$("#selectGameName").val();
            var ajaxData={
                searchGameName:checkValue
            };
            $.post("<%=request.getContextPath()%>/promoter/orderajax", { searchGameName: checkValue } ); 

            /*
            jQuery.ajax({
                   type : 'GET',
                   data :ajaxData,
                   url : '<%=request.getContextPath()%>/promoter/orderajax',
                   dataType:'text',
                  
                   success:function(){
                	     window.location.reload();
                         console.log('请求成功');
                   },
                  
                   error : function(XMLHttpRequest, textStatus, errorThrown) {
                         console.log('请求失败');
                   }
       });
            */
});
	    
	</script>
</body>
</html>
