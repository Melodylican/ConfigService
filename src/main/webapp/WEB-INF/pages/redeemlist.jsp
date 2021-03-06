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
				<span>当前位置：</span> <strong>积分配置管理 > 兑换码管理</strong>
			</div>
			<c:if test="${empty search}">
				<div class="toolbar">
					<form action="<%=request.getContextPath()%>/redeem/list" method="get" id="Form">
						<p class="fl">
							<label>游戏名称</label> 
							<select id="u4_input" name="gameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${gameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}
									</option>
								</c:forEach>
							</select>
						</p>
						<p class="fl">
							<label>积分类别</label> 
							<select id="u4_input" name="searchScore">
							    <c:if test="${empty scoreList }">
							    	<option>暂无兑换码</option>
							    </c:if>
								<c:forEach items="${scoreList}" var="scoreL">
									<option
										<c:if test="${scoreL==searchScore }">selected="true"</c:if>
										value="${scoreL }">${scoreL}
									</option>
								</c:forEach>
							</select>
						</p>
						<p class="fl">
							<label>是否已使用</label> 
							<select id="u4_input" name="status">
										<option
											<c:if test="${status==0 }">selected="true"</c:if>
											value="0">已使用
										</option>
										<option
											<c:if test="${status==1 }">selected="true"</c:if>
											value="1">未使用
										</option>
							</select>
						</p>
						<c:if test="${status==0 }">
							<p class="fl">
								<label>使用者playerId</label> 
								<input type="text" name="playerId" value="${playerId}" style="width:150px" class="iText"/>
						    </p>
						</c:if>
						<p class="fl">
								<input type="hidden" name="gameId" value="${gameId}" />
								<input type="hidden" name="actId" value="${actId}" />
								<input type="hidden" name="gameName" value="${gameName }" />
								<input type="hidden" name="page" value="${page}" />
								<input type="hidden" name="pageSize" value="${pageSize}" />
						</p>
						<p class="fl">
							开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="beginTime" value="${beginTime}" style="width:150px" class="iText" />
						</p>
						<p class="fl">
							结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="endTime" value="${endTime }" style="width:150px" class="iText" />
						</p>
						<input type="submit" value="查询" class="btn" />
						<a href="<%=request.getContextPath()%>/redeem/create?gameName=${gameName}&gameId=${gameId}&actId=${actId}" class="btn">新建</a>
						<c:if test="${status==1 }">
							<a href="<%=request.getContextPath()%>/redeem/importpage?page=${page}&pageSize=${pageSize}&actId=${actId}&gameId=${gameId}&gameName=${gameName}&beginTime=${beginTime}&endTime=${endTime}" class="btn">导入</a>
							<a href="<%=request.getContextPath()%>/redeem/deleteall?actId=${actId}&gameId=${gameId}&gameName=${gameName}&status=${status}&searchScore=${searchScore}" class="btn">一键删除</a>
						</c:if>
						<c:if test="${status==0 }">
							<a href="<%=request.getContextPath()%>/redeem/export?&status=${status}&searchScore=${searchScore}&actId=${actId}&gameId=${gameId}&gameName=${gameName}&beginTime=${beginTime}&endTime=${endTime}" class="btn">导出</a>
						</c:if>
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
								<th>兑换码</th>
								<th>兑换积分</th>
								<th>创建时间</th>
								<th>
								<c:choose>
								   <c:when test="${status==1 }"  >
								   		修改时间
							       </c:when>
							       <c:otherwise>
							      		 使用时间
							       </c:otherwise>
							    </c:choose>								
								
								</th>
								<c:if test="${status==0 }">
								   <th>使用者playerId</th>
								</c:if>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty list }">
						 <tr class="odd"><td colspan="10"><label>您好,当前该游戏还没有您查询类型的兑换码,您可以点击“导入”或者“新建”创建兑换,或者换个查询条件试试吧！</label>
						 <a href="<%=request.getContextPath()%>/redeem/importpage?page=${page}&pageSize=${pageSize}&actId=${actId}&gameId=${gameId}&gameName=${gameName}&beginTime=${beginTime}&endTime=${endTime}">导入</a> &nbsp;
						 <a href="<%=request.getContextPath()%>/redeem/create?gameName=${gameName}&gameId=${gameId}&actId=${actId}" style="background-color:pink" >新建</a></td></tr>
						</c:if>
							<c:forEach items="${list}" var="list">
							<tr class="odd">
								
								<td>${gameName}</td>
								<td>${list.gameId}</td>
								<td>${list.code}</td>
								<td>${list.score}</td>
								<td>${list.getCreateTime(1)}</td>
								<td>${list.getUpdateTime(1)}</td>
								<c:if test="${status==0 }">
								   <td>
								   		<a id="detail" data-reveal-id1="detail" href="javascript:void(0);" title="点击查看用户积分详情" item-id1="${list.playerId}" item-id2="${list.gameId}" style="background-color:white;color:#36648B" >${list.playerId}</a>
								   </td>
								</c:if>
								<td>
								<!-- 已使用的兑换码不允许修改 -->
								<c:choose>
								   <c:when test="${list.status == 0 }"  >
							             <a href="javascript:return false;" title="已使用的兑换码不能修改" class="disabled" style="background-color:#ccc">修改</a> 
							       </c:when>
							       <c:otherwise>
							            <a href="<%=request.getContextPath()%>/redeem/update?gameRedeemBean={id:'${list.id }',actId:'${list.actId }',gameId:'${list.gameId }',code:'${list.code }',score:'${list.score }',createTime:'${list.createTime }'}&gameName=${gameName }" >修改</a> 
							       </c:otherwise>
							    </c:choose>
								<a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade">删除</a>
								<c:choose>
								   <c:when test="${list.status == 0 }"> <!-- state为1时处于开启状态  0位暂停状态-->
							             <a href="#" style="background-color:red">已使用</a>
							       </c:when>
							       <c:otherwise>
							             <a href="#" style="background-color:green">未使用</a>
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
                             <a href="<%=request.getContextPath()%>/redeem/list?page=${page-1}&pageSize=${pageSize}&actId=${actId}&gameName=${gameName}&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}&status=${status}&searchScore=${searchScore}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
				               <c:choose>  
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/redeem/list?page=${index}&pageSize=${pageSize}&actId=${actId}&gameName=${gameName}&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}&status=${status}&searchScore=${searchScore}" class="cur">${index}</a>
                                  </c:when>  
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/redeem/list?page=${index}&pageSize=${pageSize}&actId=${actId}&gameName=${gameName}&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}&status=${status}&searchScore=${searchScore}" >${index}</a>
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
				                     <a href="<%=request.getContextPath()%>/redeem/list?page=${index}&pageSize=${pageSize}&actId=${actId}&gameName=${gameName}&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}&status=${status}&searchScore=${searchScore}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>   
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/redeem/list?page=${page+1}&pageSize=${pageSize}&actId=${actId}&gameName=${gameName}&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}&status=${status}&searchScore=${searchScore}">下一页</a>
                    </c:if>
                    <c:if test="${rowCount ne 0 }">
                    	<span>共有：${rowCount }条</span>
                    </c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="reveal-modal">
			<center><h1>您确定要删除该条兑换码信息吗？</h1></center><br><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/redeem/delete" method="post">
			<input type="hidden" id="id" name="id" value="1" />
			<input type="hidden" name="gameId" value="${gameId}" />
			<input type="hidden" name="actId" value="${actId}" />
			<input type="hidden" name="gameName" value="${gameName }" />
			<input type="hidden" name="page" value="${page}" />
			<input type="hidden" name="pageSize" value="${pageSize}" />
			<input type="hidden" name="beginTime" value="${beginTime }"/>
			<input type="hidden" name="endTime" value="${endTime }"/>
			<input type="hidden" name="status" value="${status}">
			<input type="hidden" name="searchScore" value="${searchScore }">
			<input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn1" close-reveal-id="myModal" >取消</a>
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
	        $("#id").val(id);
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

	</script>
</body>
</html>
