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
	<title>用户吐槽回复 </title>
	<meta charset="UTF-8">
	<!-- 
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
     -->


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap-forum.min.css">    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/styleforum.css">    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.mobile.flatui.css" /> 
    <link rel="stylesheet"type="text/css" href="<%=request.getContextPath()%>/resources/css/reveal.css">
</head>
<body>
<div data-role="page">
	<div data-role="header" class="header linear-g">
        <a href="#panel-left" data-iconpos="notext" class="glyphicon glyphicon-th-large col-xs-2 text-right"> </a>
        <a class="text-center col-xs-8">用户吐槽信息</a>
        <a href="#panel-right" data-iconpos="notext" class="glyphicon glyphicon-user col-xs-2 text-left"> </a>
    </div>
    <div data-role="panel" data-position="left" data-display="push" class="list-group shortcut_menu dn linear-g" id="panel-left">
        <a data-ajax="false" href="javascript:void(0)" id="click" class="list-group-item"><span class="glyphicon glyphicon-home"> </span> &nbsp;返回主菜单</a>
        <a href="#" class="list-group-item"><span class="glyphicon glyphicon-edit"> </span> &nbsp;备用菜单2</a>
        <a href="#" class="list-group-item"><span class="glyphicon glyphicon-list"> </span> &nbsp;备用菜单3</a>
        <a href="#" class="list-group-item"><span class="glyphicon glyphicon-list-alt"> </span> &nbsp;备用菜单4</a>
    </div>
    <div data-role="panel" data-position="right" data-display="push" class="user_box text-center dn linear-g" id="panel-right">
        <div class="u_info">
            <img class="avatar" src="<%=request.getContextPath()%>/resources/images/avatar.png" alt="头像">
            <span class="username">${pageContext.request.userPrincipal.name}</span>
        </div>
        <ul class="user_menu">
          <li class="menu"><a href="#"><span class="glyphicon glyphicon-cog"> </span> &nbsp;基本设置(暂未开放)</a></li>
          <li class="menu"><a href="#"><span class="glyphicon glyphicon-lock"> </span> &nbsp;修改密码(暂未开放)</a></li>
          <li class="menu"><a href="#"><span class="glyphicon glyphicon-picture"> </span> &nbsp;上传头像(暂未开放)</a></li>
          <li class="menu"><a data-ajax="false" href="javascript:void(0)" id="logout"><span class="glyphicon glyphicon-off"> </span> &nbsp;安全退出</a></li>
        </ul>
    </div>
    <div data-role="content" class="container" role="main">
    <ul class="content-reply-box mg10">
		<c:if test="${empty list }">
		   <center><label>Oops,竟然没有吐槽，这不科学啊！</label></td></center>
		</c:if>
    <c:forEach items="${list}" var="list">
      <li class="odd">
          <a class="user" href="#" data-toggle="tooltip" data-placement="right" title="${list.nickname }"><img class="img-responsive avatar_" src="<%=request.getContextPath()%>/resources/images/user1.png" alt="${list.nickname }"><span class="user-name" >${list.nickname }</span></a>
          <div class="reply-content-box">
          	<span class="reply-time">${list.getCreateTime(1)}</span>
              <div class="reply-content pl">
              	<span class="arrow">&nbsp;</span>
              	${list.content }
              </div>
              <a href="javascript:void(0);" id="add" item-to-uid="${list.userId}" item-cid="${list.cid}" item-to-nickname="${list.nickname }" data-reveal-id="myModal" data-animation="fade" " class="rr"><span class="glyphicon glyphicon-comment "></span> &nbsp;回复</a>
          </div>
      </li>
      	<c:if test="${not empty list.rfb }">
      		<c:forEach items="${list.rfb}" var="listreply">
			      <li class="even">
	                <a class="user" href="#" data-toggle="tooltip" data-placement="right" title="${ listreply.from_nickname}"><img class="img-responsive avatar_" src="<%=request.getContextPath()%>/resources/images/ledou.png" alt="${ listreply.from_nickname}" ><span class="user-name" >${ listreply.from_nickname}</span></a>
	                <div class="reply-content-box">
	                	<span class="reply-time">${listreply.getCreatetime(1)}</span>
	                    <div class="reply-content pr" >
	                    	<span class="arrow">&nbsp;</span>
	                    	${listreply.content }
	                    </div>
	                </div>
	            </li>
            </c:forEach>
		</c:if>
	</c:forEach>
        </ul>
       <center>
        <ul class="pagination">
					<c:if test="${pages>1 && page>1 }">
						<li><a data-ajax="false"
							href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${page-1}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">上一页</a></li>
					</c:if>

					<c:forEach var="index" begin="1" end="${pages}" step="1">
						<c:choose>
							<c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
								<li <c:if test="${index == page }"> class="active" </c:if>>
								    <a data-ajax="false" href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${index}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">${index}</a></li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${(index-page< 0 ? page-index:index-page) == 4}">
										<li class="disabled"><span>...</span></li>
									</c:when>
									<c:otherwise>
										<c:if test="${index==pages }">
											<li <c:if test="${index==page }"> class="active" </c:if> ><a data-ajax="false"
												href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${index}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">${pages}</a></li>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${pages>1 && page < pages }">
						<li><a data-ajax="false"
							href="<%=request.getContextPath()%>/promoter/forum?pageSize=${pageSize}&page=${page+1}&actId=${actId }&gameId=${gameId}&gameName=${gameName}">下一页</a></li>
					</c:if>
				</ul>
				</center>
			<!--
            <ul class="operating row text-center linear-g">
	        	<li class="col-xs-4"><a href="#"><span class="glyphicon glyphicon-tags"></span> &nbsp;标签</a></li>
	        	<li class="col-xs-4"><a href="#"><span class="glyphicon glyphicon-comment"></span> &nbsp;回复</a></li>
	        	<li class="col-xs-4"><a href="#"><span class="glyphicon glyphicon-heart"></span> &nbsp;喜欢</a></li>
        	
            </ul>
            -->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.mobile-1.4.0-rc.1.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script type="text/javascript">

	$(function(){
		 
		// 不同页面切换转场效果
		// $.mobile.changePage ('/test.html', 'slide/pop/fade/slideup/slidedown/flip/none', false, false);
		
		$('.list-group-item,.menu a').click(function(){
			$.mobile.changePage($(this).attr('href'), {
				transition : 'flip', //转场效果
				reverse : true       //默认为false,设置为true时将导致一个反方向的转场 换页动画
			});	
		});
	});
	
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
   	
   	$("#click").click(function (){
   	    //单机后要执行的操作
   	    window.location.href="<%=request.getContextPath()%>/promoter/index";
   	});
   	
   	$("#logout").click(function (){
   	    //单机后要执行的操作
   	    window.location.href="<%=request.getContextPath()%>/logout";
   	});
   	
   	$(function () { $("[data-toggle='tooltip']").tooltip(); });

</script>
</body>
</html>