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
<link href="<%=request.getContextPath()%>/resources/css/reveal.css" rel="stylesheet"type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" /> -->
<link href="<%=request.getContextPath()%>/resources/css/comment.css" rel="stylesheet" type="text/css">
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
				<span>当前位置：</span> <strong>活动配置 > 用户用户吐槽</strong>
			</div>
			
			<div class="wrap">
				<div class="grid">

				<!-- 评论列表  开始-->
				<div class="container bootstrap snippet">

				    <div class="row">
						<div class="col-md-12">
						    <div class="blog-comment">
								<center><h3 class="text-success">用户吐槽信息</h3></center>
				                <hr/>
				               <c:if test="${empty list }">
		   						<center><label>Oops,竟然没有吐槽，这不科学啊！</label></center>
							   </c:if>
							   
								<ul class="comments">
								   <c:forEach items="${list}" var="list">
									<li class="clearfix">
									  <img src="<%=request.getContextPath()%>/resources/images/user1.png" class="avatar" alt="${list.nickname }">
									  <div class="post-comments">
									      <p class="meta">${list.getCreateTime(1)}  <a href="#">${list.nickname}</a> 吐槽说 : 
									      	<i class="pull-right">
									          <a href="#"><small>删除</small></a>
									          <a href="#"><small>置顶</small></a>
									          <a href="javascript:void(0);" id="add" item-to-uid="${list.userId}" item-cid="${list.cid}" item-to-nickname="${list.nickname }" data-reveal-id="myModal" data-animation="fade"><small>回复</small></a>
									        </i>
									      </p>
									      <p>
									          ${list.content }
									      </p>
									  </div>

								      	<c:if test="${not empty list.rfb }">
								      		<c:forEach items="${list.rfb}" var="listreply">
									      		<ul class="comments">
												      <li class="clearfix">
												          <img src="<%=request.getContextPath()%>/resources/images/ledou.png" class="avatar" alt="${ listreply.from_nickname}">
												          <div class="post-comments">
												              <p class="meta">${listreply.getCreatetime(1)}  <a href="#">${ listreply.from_nickname}</a> 回复  <a href="#">${list.nickname}</a> 说 : 
												              	<i class="pull-right">
												              	    <a href="javascript:void(0);" id="add" item-to-uid="${list.userId}" item-cid="${list.cid}" item-to-nickname="${list.nickname }" data-reveal-id="myModal" data-animation="fade"><small>删除</small></a>
												              	    <a href="#"><small>修改</small></a>
												              	    <a href="#"><small>回复</small></a></i></p>
												              <p>
												                  ${listreply.content }
												              </p>
												          </div>
												      </li>
												  </ul>
								            </c:forEach>
										</c:if>
									</li>
								</c:forEach>								
								</ul>
							</div>
						</div>
					<!-- 评论列表结束 -->
					</div>
				 </div>

			  </div>
				<!-- 分页模块 -->
				
				<div class="pagination">
				    <c:if test="${pages>1 && page>1 }">
                             <a href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${page-1}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">上一页</a>
                    </c:if>
				     
				    <c:forEach  var="index" begin="1" end="${pages}" step="1">
				       <c:choose>
				           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
				               <c:choose>
                                  <c:when test="${index==page }">
                                        <a href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${index}&actId=${actId }&gameId=${gameId}&gameName=${gameName}" class="cur">${index}</a>
                                  </c:when>
                                  <c:otherwise>
                                        <a href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${index}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">${index}</a>
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
				                     <a href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${index}&actId=${actId }&gameId=${gameId}&gameName=${gameName}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a>
				                    </c:if>   
				                </c:otherwise>
				                </c:choose>
				           </c:otherwise>
				       </c:choose>
                    </c:forEach>
                    <c:if test="${pages>1 && page < pages }">
                             <a href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${page+1}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">下一页</a>
                    </c:if>
				</div>

			</div>
		</div>
	</div>
	 <!-- 弹出层 -->
<div id="myModal" class="reveal-modal">
	<center><h4 ><font color="red">请输入吐槽回复信息</font></h4></center><br>
	<a class="close-reveal-modal">&#215;</a>
	<center>
	<form action="<%=request.getContextPath()%>/promoter/forumreply" method="post"  data-ajax="false">
		<input type="hidden" name="gameId" value="${gameId }" />
		<input type="hidden" name="actId" value="${actId}" />
		<input type="hidden" name="gameName" value="${gameName }" />
		<input type="hidden" name="from_uid" id="from_uid" value="0" />
		<input type="hidden" name="to_uid" id="to_uid" value="0" />
		<input type="hidden" name="from_nickname" id="from_nickname" value="管理员" />
		<input type="hidden" name="to_nickname" id="to_nickname" value="0" />
		<input type="hidden" name="cid" id="cid" value="0" />
		<input type="hidden" name="page" value="${page}" />
		<input type="hidden" name="pageSize" value="${pageSize }" />
		<textarea type="text" name="content" ></textarea><br><br>
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交" /></center>
    </form>
    </center>
</div>
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/popwin.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script type="text/javascript">
	//弹出回复层
	$('a[data-reveal-id]').live('click', function () {
		var to_uid = $(this).attr("item-to-uid");
		var cid = $(this).attr("item-cid");
		var to_nickname = $(this).attr("item-to-nickname");
        //e.preventDefault();
        $("#to_uid").val(to_uid);
        $("#cid").val(cid);
        $("#to_nickname").val(to_nickname);
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
