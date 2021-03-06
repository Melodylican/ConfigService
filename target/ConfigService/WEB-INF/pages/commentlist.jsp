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
					<li class="cur">
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
				<span>当前位置：</span> <strong>活动配置 > 用户吐槽</strong>
			</div>
			<div class="toolbar">
			      <form method="post" action="<%=request.getContextPath()%>/promoter/forum">
						<p class="fl">
							<label>游戏名称</label> <select id="u4_input" name="gameName">
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

				<!-- 评论列表  开始-->
				<div class="container bootstrap snippet">

				    <div class="row">
						<div class="col-md-12">
								<center><h2 class="text-success">用户吐槽信息</h2></center>
				                <hr/>
						    <div class="blog-comment"  style="overflow-y:auto; overflow-x:auto; height:600px;">
				               <c:if test="${empty list }">
		   						<center><label>Oops,竟然没有吐槽，这不科学啊！</label></center>
							   </c:if>
							   
								<ul class="comments">
								   <c:forEach items="${list}" var="list">
									<li class="clearfix">
									  <img src="<%=request.getContextPath()%>/resources/images/user1.png" class="avatar" alt="${list.nickname }">
									  <div class="post-comments">
									      <p class="meta">${list.getCreateTime(1)}  <a href="javascript:void(0);"  item-id="${list.userId }" data-reveal-id2="myModal2" data-animation="fade">${list.nickname} [ ${list.userId } ]</a> 吐槽说 : 
									      	<i class="pull-right">
									          <!-- <a href="<%=request.getContextPath()%>/promoter/deleteforum?cid=${list.cid }&pageSize=${pageSize}&page=${page}&actId=${actId }&gameId=${gameId}&gameName=${gameName}" class="btn"><small>删除</small></a>  -->
									          <a href="javascript:void(0);" item-id="${list.cid}" data-reveal-id3="myModal3" data-animation="fade" class="btn"><small>删除</small></a>
									          <c:choose> 
												  <c:when test="${list.isTop == 0}">   
												    	<a href="<%=request.getContextPath()%>/promoter/move2top?cid=${list.cid }&pageSize=${pageSize}&page=${page}&actId=${actId }&gameId=${gameId}&gameName=${gameName}&type=1" class="btn"><small>置顶</small></a> 
												  </c:when> 
												  <c:otherwise>   
												        <a href="<%=request.getContextPath()%>/promoter/move2top?cid=${list.cid }&pageSize=${pageSize}&page=${page}&actId=${actId }&gameId=${gameId}&gameName=${gameName}&type=2" class="btn" style="background-color:#00A600"><small>取消置顶</small></a>  
												  </c:otherwise> 
												</c:choose> 
									          <a href="javascript:void(0);" item-message="<c:out value="${list.content }" escapeXml="true"></c:out>" item-to-uid="${list.userId}" item-cid="${list.cid}" item-to-nickname="${list.nickname }" data-reveal-id="myModal" data-animation="fade" class="btn"><small>回复</small></a>
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
												          <div class="post-comments2">
												              <p class="meta">${listreply.getCreatetime(1)}  
												                <a href="javascript:void(0);"  item-id="${listreply.from_uid}" data-reveal-id2="myModal2" data-animation="fade">${ listreply.from_nickname}</a> 回复 
												                <a href="javascript:void(0);"  item-id="${list.userId}" data-reveal-id2="myModal2" data-animation="fade">${listreply.to_nickname} [ ${listreply.to_uid } ]</a> 说 : 
												              	<i class="pull-right">
												              	    <!-- <a href="<%=request.getContextPath()%>/promoter/deleteforumreply?rid=${listreply.rid}&pageSize=${pageSize}&page=${page}&actId=${actId }&gameId=${gameId}&gameName=${gameName}"class="btn"><small>删除</small></a>  -->
												              	    <a href="javascript:void(0);" item-id="${listreply.rid}" data-reveal-id3="myModal4" data-animation="fade" class="btn"><small>删除</small></a>
												              	    <a href="javascript:void(0);" item-rid="${listreply.rid}" item-content="${listreply.content}" data-reveal-id1="myModal1" data-animation="fade" class="btn"><small>修改</small></a>
												              	    <a href="javascript:void(0);" item-message="<c:out value="${listreply.content }" escapeXml="true"></c:out>" item-to-uid="${listreply.from_uid}" item-cid="${list.cid}" item-to-nickname="${listreply.to_nickname }" data-reveal-id="myModal" data-animation="fade" class="btn"><small>回复</small></a>
												              	</i>
												              </p> 
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
<!-- 回复吐槽信息弹出层 -->
<div id="myModal" class="reveal-modal3">
	<center><h4 ><font color="red">请输入吐槽回复信息</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
	<textarea style="background:#FFC1C1" type="text" name="content" rows="5" cols="100" id="message" readonly="readonly"></textarea><br><br>
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
		<textarea style="background:#BCD2EE" type="text" name="content" rows="10" cols="100"></textarea><br><br>
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交" /></center>
    </form>
    </center>
</div>
<!-- 修改吐槽回复弹出层 -->
<div id="myModal1" class="reveal-modal3">
	<center><h4 ><font color="red">请输入修改后的吐槽回复信息</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
	<form action="<%=request.getContextPath()%>/promoter/updateforumreply" method="post"  data-ajax="false">
		<input type="hidden" name="gameId" value="${gameId }" />
		<input type="hidden" name="actId" value="${actId}" />
		<input type="hidden" name="gameName" value="${gameName }" />
		<input type="hidden" name="rid" id="rid" value="0" />
		<input type="hidden" name="page" value="${page}" />
		<input type="hidden" name="pageSize" value="${pageSize }" />
		<textarea type="text" name="content" rows="10" cols="100" id="content"></textarea><br><br>
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="提交" /></center>
    </form>
    </center>
</div>
<!-- 用户信息弹出层 -->
<div id="myModal2" class="reveal-modal4">
			<center><h3>用户信息如下：</h3></center>
			<a class="close-reveal-modal"><font size="11">&#215;</font></a>
			<table>
				<tr>
					<td>用户Id:</td><td><input type="text" disabled id="uid" name="uid" value="0" /></td>
				</tr>
				<tr>
					<td>用户昵称:</td><td><input type="text" disabled id="nickname" name="nickname" value="" /></td>
				</tr>				
				<tr>
					<td>用户等级:</td><td><input type="text" disabled id="level" name="level" value="0" /></td>
				</tr>
				<tr>
					<td>用户充值金额(元):</td><td><input type="text" disabled id="money" name="money" value="0" /></td>
				</tr>
				<tr>
					<td>用户在线时长(秒):</td><td><input type="text" disabled id="time" name="time" value="0" /></td>
				</tr>
				<tr>
					<td>用户手机号:</td><td><input type="text" disabled id="mobile" name="mobile" value="0" /></td>
				</tr>
				<tr>
					<td>注册时间:</td><td><input type="text" disabled id="registertime" name="registertime" value="" /></td>
				</tr>				
				<tr><td colspan="2"><a style="height:30px; border: 1px solid #999;width: 100px;" close-reveal-id2="myModal2" >关闭</a></td></tr>
			</table>
</div>

<!-- 确认删除吐槽弹出层 -->
<div id="myModal3" class="reveal-modal5">
	<center><h4 ><font color="red">确定要删除该条吐槽信息吗？</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
	<form action="<%=request.getContextPath()%>/promoter/deleteforum" method="post"  data-ajax="false">
		<input type="hidden" name="gameId" value="${gameId }" />
		<input type="hidden" name="actId" value="${actId}" />
		<input type="hidden" name="gameName" value="${gameName }" />
		<input type="hidden" name="cid" id="deletecid" value="0" />
		<input type="hidden" name="page" value="${page}" />
		<input type="hidden" name="pageSize" value="${pageSize }" />
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定" /></center>
    </form>
    </center>
</div>

<!-- 确认删除吐槽回复弹出层 -->
<div id="myModal4" class="reveal-modal5">
	<center><h4 ><font color="red">确定要删除该条回复信息吗？</font></h4></center><br>
	<a class="close-reveal-modal"><font size="11">&#215;</font></a>
	<center>
	<form action="<%=request.getContextPath()%>/promoter/deleteforumreply" method="post"  data-ajax="false">
		<input type="hidden" name="gameId" value="${gameId }" />
		<input type="hidden" name="actId" value="${actId}" />
		<input type="hidden" name="gameName" value="${gameName }" />
		<input type="hidden" name="rid" id="deleterid" value="0" />
		<input type="hidden" name="page" value="${page}" />
		<input type="hidden" name="pageSize" value="${pageSize }" />
		<center><input type="submit" style="height:30px; border: 1px solid #999;width: 100px;" value="确定" /></center>
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
	//弹出吐槽回复层
	$('a[data-reveal-id]').live('click', function () {
		var to_uid = $(this).attr("item-to-uid");
		var cid = $(this).attr("item-cid");
		var to_nickname = $(this).attr("item-to-nickname");
		var message = $(this).attr("item-message");
		console.log(message);
        //e.preventDefault();
        $("#to_uid").val(to_uid);
        $("#cid").val(cid);
        $("#to_nickname").val(to_nickname);
        $("#message").val(message);
        var modalLocation = $(this).attr('data-reveal-id');
        $('#' + modalLocation).reveal($(this).data());

    });
   
   	$('a[close-reveal-id]').live('click', function () {
     jQuery("#myModal").trigger('reveal:close');
        var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
        $('#' + modalLocation).reveal($(this).data());
    });
   	
	//弹出修改吐槽回复层
	$('a[data-reveal-id1]').live('click', function () {
		var rid = $(this).attr("item-rid");
		var content = $(this).attr("item-content");
		console.log(content);
        //e.preventDefault();
        $("#rid").val(rid);
        $("#content").val(content);
        var modalLocation = $(this).attr('data-reveal-id1');
        $('#' + modalLocation).reveal($(this).data());

    });
   
   	$('a[close-reveal-id1]').live('click', function () {
     jQuery("#myModal1").trigger('reveal:close');
        var modalLocation = $(this).attr('close-reveal-id1').trigger('reveal:close');
        $('#' + modalLocation).reveal($(this).data());
    });
   	
   	//弹出用信息层
		$('a[data-reveal-id2]').live('click', function () {
			var uid = $(this).attr("item-id");//1723768989;
			var gid = ${gameId};
     
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/promoter/getuserinfo?gid="+gid+"&uid="+uid,
				contentType : "application/json; charset=utf-8",
				dataType : "JSON",

				success : function(data, status) {
					if (data.statuscode == "401") {
						alert(data.msg);
					} else {
						console.log("返回码： "+data['code']);
						if(data['code'] != 3 && data['code'] != -1) {
							result = data['data'];
							console.log(result);
							//alert(JSON.stringify(data));
	 
							var level =eval('result.level');
							var time =eval('result.onlinetime');
							var money =eval('result.amount');
							var nickname= eval('result.nick_name');
							var registertime = eval('result.create_time');
							var mobile = eval('result.mobile');
							if(level == -1)
								level = "未查到等级信息";
							if(time == -1)
								time = "未查到在线时长信息";
							if(mobile == -1 || mobile == "" )
								mobile = "未查到手机号";
							if(registertime == -1)
								registertime = "未查到注册时间";
							if(nickname == -1)
								nickname = "未查到昵称信息";						
							if(money == -1)
								money = "未查到付费金额";
							
					        $("#uid").val(uid);
					        $("#level").val(level);
					        $("#time").val(time);
					        $("#money").val(money);
					        $("#mobile").val(mobile);
					        $("#nickname").val(nickname);
					        $("#registertime").val(registertime);				        
							
							console.log(level);
							console.log(time);
							console.log(money);
							console.log(nickname);
							console.log(registertime);
							console.log(mobile);
					   } else {
					        $("#uid").val(uid);
					        $("#level").val("未查到等级信息");
					        $("#time").val("未查到在线时长信息");
					        $("#money").val("未查到付费金额");
					        $("#mobile").val("未查到手机号");
					        $("#nickname").val("未查到昵称");
					        $("#registertime").val("未查到注册时间");
					        console.log("返回码不为 3 -1 ： "+data['code']);
						   
					   }
					}

			  },
			  error: function (XMLHttpRequest, textStatus, errorThrown) {
			  	alert(XMLHttpRequest.status);
			  	alert(XMLHttpRequest.readyState);
			  	alert(textStatus);
			  },
			  complete : function() {
				  console.log("complete");
			  }
			});
			console.log("开启弹出框。。。");
	        //调用结束
		    var modalLocation = $(this).attr('data-reveal-id2');
	        $('#' + modalLocation).reveal($(this).data());


	    });
	   
	   	$('a[close-reveal-id2]').live('click', function () {
         jQuery("#myModal2").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id2').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    }); 
        //确定删除吐槽信息弹出层	   	
		$('a[data-reveal-id3]').live('click', function () {
			var cid = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#deletecid").val(cid);
	        console.log(cid);
	        var modalLocation = $(this).attr('data-reveal-id3');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id3]').live('click', function () {
         jQuery("#myModal3").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id3').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });	  
	   	
        //确定删除吐槽回复信息弹出层	   	
		$('a[data-reveal-id3]').live('click', function () {
			var rid = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#deleterid").val(rid);
	        console.log(cid);
	        var modalLocation = $(this).attr('data-reveal-id3');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id3]').live('click', function () {
         jQuery("#myModal3").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id3').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });		   	
   	
	</script>
</body>
</html>
