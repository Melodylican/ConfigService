<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
				<span>当前位置：</span> <strong>积分兑换审批</strong>
			</div>
			<div class="toolbar">
			      <form method="post" action="<%=request.getContextPath()%>/promoter/orderajax">
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
							订单编号：<input type="text" placeholder="输入查询特定订单编号" name="orderId" value="${orderId }" class="iText" />
						</p>						
						<p><input type="submit" value="查询" class="btn"></p>
				 </form>
				</div>
			<div class="wrap">
				<div class="grid">
					<table width="1000">
						<thead>
							<tr
								style="background-color:rgb(220,220,220); height:30px; width:100%;">
								<th><span class="checkbox" id="checkAll" ></span></th>
								<th>申请单编号</th>
								<th>申请单创建时间</th>
								<th>支付宝账号</th>
								<th>游戏角色名</th>
								<th>游戏级数</th>
								<th>在线时长</th>
								<th>账号累积积分</th>
								<th>请求兑换积分</th>
								<th>邀请码使用次数</th>
								<th>积分详情</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td ><span name="checkme" status="${list.status}" class="checkbox"></span></td>
								<td>${list.orderId}</td>
								<td>${list.orderCreatedDate }</td>
								<td>${list.payInfo}</td>
								<td>${list.userMemo}</td>
								<td>${list.level}</td>
								<td>${list.onlineTime}</td>
								<td>${list.hasPoints}</td>
								<td>${list.requestExchangePoints }</td>
								<td>${list.invitedPeople }</td>
								<td>
									<a id="detail" data-reveal="detail" href="javascript:void(0);" title="点击查看积分详情" item-id1="${list.playerId}" item-id2="${list.gameId}" >积分详情</a>
								</td>
								<td>
								<!-- 通过按钮 -->
							    <c:choose>
								   <c:when test="${list.status == 'PASSED' }">
							             <a href="<%=request.getContextPath()%>/promoter/throughaudit?orderId=${list.orderId }&page=${page}&pageSize=${pageSize}&searchGameName=${searchGameName}" title="点击通过审批" style="background-color:green">通过</a>
							       </c:when>
							       <c:otherwise>
							       		 <c:if test="${list.status == 'INVALID' or list.status == 'REJECTED'}">
							               <a href="javascript:return false;" title="作废的订单不能再次通过审批" class="disabled" style="background-color:#ccc">通过</a>
							             </c:if>
							             <c:if test="${list.status == 'REQUESTED'}">
							               <a href="<%=request.getContextPath()%>/promoter/throughaudit?orderId=${list.orderId}&state=${list.status }&page=${page}&pageSize=${pageSize}&searchGameName=${searchGameName}" title="点击通过审批" >通过</a>							             
							             </c:if>					             
							             <!-- <a href="javascript:void(0);" id="notpass" item-id2="${page}" item-id3="${pageSize}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批">未通过</a>  -->
							       </c:otherwise>
							    </c:choose>
							    
							    <!-- 未通过按钮-->
							    <c:choose>
								   <c:when test="${list.status == 'REJECTED' }">
							             <a href="javascript:void(0);" id="notpass" item-id2="${page}" item-id3="${pageSize}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批" style="background-color:#206ecd">未通过</a>
							       </c:when>
							       <c:otherwise>
							       		 <c:if test="${list.status == 'INVALID' }">
							               <a href="javascript:return false;" title="作废的订单不能再次通过审批" class="disabled" style="background-color:#ccc">未通过</a>
							             </c:if>
							             <c:if test="${list.status == 'REQUESTED' or list.status == 'PASSED'}">
							               <a href="javascript:void(0);" id="notpass" item-id2="${page}" item-id3="${pageSize}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批">未通过</a>
							             </c:if>					             
							             <!-- <a href="javascript:void(0);" id="notpass" item-id2="${page}" item-id3="${pageSize}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal1" data-animation="fade" title="点击未通过审批">未通过</a>  -->
							       </c:otherwise>
							    </c:choose>
							    <a href="javascript:return false;" title="作废的订单不能再次通过审批" class="disabled" style="background-color:#ccc">作废</a>
							    <!-- 作废按钮
							    <c:choose>
								   <c:when test="${list.status == 'INVALID' }">
							             <a href="javascript:void(0);" id="delete" item-id2="${page}" item-id3="${pageSize}" item-id4="${list.operationMemo}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal" data-animation="fade" title="点击作废" style="background-color:red">作废</a>
							       </c:when>
							       <c:otherwise>
							             <c:if test="${list.status == 'REJECTED'}">
							               <a href="javascript:return false;" title="作废的订单不能再次通过审批" class="disabled" style="background-color:#ccc">作废</a>
							             </c:if>
							             <c:if test="${list.status == 'REQUESTED'or list.status == 'PASSED'}">							       
							             	<a href="javascript:void(0);" id="delete" item-id2="${page}" item-id3="${pageSize}" item-id="${list.orderId}" item-id1="${searchGameName}" data-reveal-id="myModal" data-animation="fade" title="点击作废">作废</a>
							             </c:if>
							       </c:otherwise>
							    </c:choose>
							    -->
							   </td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${not empty list }">
				<div class="tfoot"> 
				              <form action="<%=request.getContextPath()%>/promoter/pass" method="POST">
				                   <input name="orderIds" id="orderIds" hidden="hidden" value="0"/>
				                   <input name="page" hidden="hidden" value="${page}"/>
				                   <input name="pageSize" hidden="hidden" value="${pageSize}"/>
				                   <input name="searchGameName" hidden="hidden" value="${searchGameName}">
				                   <input type="submit" value="批量通过" id="pass" class="btn"/>
				              </form>
			   </div>
			   </c:if>
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%>/promoter/orderajax?page=${page-1}&pageSize=${pageSize}&searchGameName=${searchGameName}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1  }">
				               <c:choose>
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/orderajax?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}" class="cur">${index}</a>
                                  </c:when>
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/orderajax?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}" >${index}</a>
                                  </c:otherwise>  
                               </c:choose>
				           </c:when>
				           <c:otherwise>
				                <c:choose>
				                <c:when test="${(index-page< 0 ? page-index:index-page) == 4}">
				                   <span>...</span> 
				                </c:when>
				                <c:otherwise>
				                   <c:if test="${index==pages }"> 
				                     <a href="<%=request.getContextPath()%>/promoter/orderajax?page=${index}&pageSize=${pageSize}&searchGameName=${searchGameName}" <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                   </c:if> 
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/orderajax?page=${page+1}&pageSize=${pageSize}&searchGameName=${searchGameName}">下一页</a>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal3">
	<center><h4 ><font color="red">请输入作废原因</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
			<form action="<%=request.getContextPath()%>/promoter/invalid" method="post">
			<input type="hidden" id="invalid_orderId" name="orderId" value="0" />
			<input type="hidden" id="searchGameName" name="searchGameName" value="1" />
			<input type="hidden" id="page" name="page" value="1" />
			<input type="hidden" id="pageSize" name="pageSize" value="8" />
            <hr>
			<br>
			<textarea name="otherReason" id="otherReasonId" rows="10" cols="100" placeholder="请在此输入作废原因..."></textarea><br>
			<br>
			<center><center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交" /></center>
			</form>
	</div>
	<div id="myModal1" class="reveal-modal">
			<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			请选择未通过审核原因</h1><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/promoter/donotpassaudit" method="post" name="form1">
			<input type="hidden" id="orderId" name="orderId" value="1" />
			<input type="hidden" id="searchGameName" name="searchGameName" value="${searchGameName}" />
			<input type="hidden" id="page" name="page" value="1" />
			<input type="hidden" id="pageSize" name="pageSize" value="8" />
			<input type="radio" name="reason" value="对不起，您未能满足活动条件中游戏在线时长或者等级的要求" />&nbsp;&nbsp;对不起，您未能满足活动条件中游戏在线时长或者等级的要求<br>
			<input type="radio" name="reason" value="您要求兑换的积分已超过该活动可兑换的积分上限" />&nbsp;&nbsp;您要求兑换的积分已超过该活动可兑换的积分上限<br>
			<input type="radio" name="reason" value="部分积分涉嫌刷单行为" />&nbsp;&nbsp;部分积分涉嫌刷单行为<br><br>
			<hr>
			<br>
			其它原因:&nbsp;&nbsp;<input type=text name="otherReason" class="iText"/><br>
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交"  onclick="return check(this.form)"/>
			</form>
	</div>
	<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
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
			//批量通过
			$("#pass").click(function() {
				
		        if($(this).hasClass("disabled")){
		            return false;
		        }else{

		        	var obj = document.getElementsByName("checkme");
		        	console.log(obj.length);
		        	var orderIds="";
		        	for(var i=0;i<obj.length;i++){
		        		if ($(obj[i]).hasClass("checkbox checkbox-check")) {
		        		console.log("true" );
		        		if($(obj[i]).attr("status")!='REJECTED') {
		        	    var orderId = $(obj[i]).parent().siblings().eq(0).text();
		        	    orderIds += orderId+",";
		        	    }
		        	    //alert("orderIds "+orderIds);
		        	    }
		        	}
		        	$("#orderIds").val(orderIds);
		            console.log("pass all"+orderIds +"  ---  "+$("#orderIds").val());
		        }
			}); 
		});
        $('a[data-reveal]').live('click', function () {
        
        	//jAlert("3333");
        	//jPrompt("111111111111");
        	//setMask("111111111111");
        	console.log("开始获取userid");
            var userId = $(this).attr("item-id1");
            var gameId = $(this).attr("item-id2");
            console.log(userId);
		    popWin.showWin("1000","600","玩家积分的来源详情","<%=request.getContextPath()%>/promoter/detail?userId="+userId+"&gameId="+gameId);

		});
        //用于作废按钮
		$('a[data-reveal-id]').live('click', function () {
			var orderId = $(this).attr("item-id");
			var searchGameName =  $(this).attr("item-id1");
			var page =  $(this).attr("item-id2");
			var pageSize =  $(this).attr("item-id3");
			var operationMemo = $(this).attr("item-id4");
	        //e.preventDefault();
	        $("#searchGameName").val(searchGameName);
	        $("#page").val(page);
	        $("#pageSize").val(pageSize);
	        $("#invalid_orderId").val(orderId);
	        $("#otherReasonId").val(operationMemo);
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
			//var searchGameName =  $(this).attr("item-id1");
			var page =  $(this).attr("item-id2");
			var pageSize =  $(this).attr("item-id3");
	        //e.preventDefault();
	        $("#orderId").val(id);
	        //$("#searchGameName").val(searchGameName);
	        $("#page").val(page);
	        $("#pageSize").val(pageSize);
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
           // $.post("<%=request.getContextPath()%>/promoter/orderajax", { searchGameName: checkValue } ); 

/*
            jQuery.ajax({
                   type : 'GET',
                   data :ajaxData,
                   url : '<%=request.getContextPath()%>/promoter/orderajax',
                   dataType:'text',
                  
                   success:function(){
                	     window.location.href="<%=request.getContextPath()%>/promoter/orderajax" ;
                         console.log('请求成功');
                   },
                  
                   error : function(XMLHttpRequest, textStatus, errorThrown) {
                         console.log('请求失败');
                   }
       });
*/
	});
        //用于验证未通过审核原因是否选择或者输入，否则不允许提交
        function check(form) {
        	var reason = document.form1.reason.value;
        	var otherReason = document.form1.otherReason.value;
            if(reason=="" && otherReason =="") {
                  alert("请选择未通过审核的原因，或者输入其它原因!");
                  form.otherReason.focus();
                  return false;
             }
        }


	    
	</script>
</body>
</html>
