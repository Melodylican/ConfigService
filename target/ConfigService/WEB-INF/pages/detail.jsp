<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>积分详情</title>
<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />
<link rel="stylesheet"type="text/css" href="<%=request.getContextPath()%>/resources/css/reveal.css">
<style type="text/css">
.table {
	width: 100%;
	padding: 5px;
	margin: 0px;
	align:center;
}

button {
	font-size:9px;
	font-family:Verdana;
	font-weight:normal;	
	-moz-border-radius:25px;
	-webkit-border-radius:25px;
	border-radius:25px;	
	padding:5px 25px;
	text-decoration:none;
}
/*样式三：淡紫色*/
.btn_style3 {
	border:1px solid #84bbf3;
	background:-moz-linear-gradient( center top, #bddbfa 8%, #80b5ea 97% );
	background:-ms-linear-gradient( top, #bddbfa 8%, #80b5ea 97% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#bddbfa', endColorstr='#80b5ea');
	background:-webkit-gradient( linear, left top, left bottom, color-stop(8%, #bddbfa), color-stop(97%, #80b5ea) );
	background-color:#bddbfa;
	color:#ffffff;
	display:inline-block;
	text-shadow:0px 0px 0px #528ecc;
 	-webkit-box-shadow:inset 0px 0px 0px -50px #dcecfb;
 	-moz-box-shadow:inset 0px 0px 0px -50px #dcecfb;
 	box-shadow:inset 0px 0px 0px -50px #dcecfb;
}.btn_style3:hover {
	background:-moz-linear-gradient( center top, #80b5ea 8%, #bddbfa 97% );
	background:-ms-linear-gradient( top, #80b5ea 8%, #bddbfa 97% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#80b5ea', endColorstr='#bddbfa');
	background:-webkit-gradient( linear, left top, left bottom, color-stop(8%, #80b5ea), color-stop(97%, #bddbfa) );
	background-color:#80b5ea;
}.btn_style3:active {
	position:relative;
	top:1px;
}

th {
	font: bold 12px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #4f6b72;
	border-right: 1px solid #C1DAD7;
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	letter-spacing: 2px;
	text-transform: uppercase;
	text-align: center;
	padding: 6px 6px 6px 12px;
	background: #CAE8EA no-repeat;
}

td {
	border-right: 1px solid #C1DAD7;
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	font-size: 14px;
	padding: 6px 6px 6px 12px;
	color: #4f6b72;
	text-align: center;
}

td.alt {
	background: #F5FAFA;
	color: #797268;
}

th.spec,td.spec {
	border-left: 1px solid #C1DAD7;
}
/*---------for IE 5.x bug*/
html>body td {
	font-size: 13px;
}

tr.select th,tr.select td {
	background-color: #CAE8EA;
	color: #797268;
}
</style>
</head>
<body>
<div>
	<form action="<%=request.getContextPath()%>/promoter/detail" method="get" id="Form">
	<br>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" id="datetimepicker_dark1" placeholder="点击选择日期时间" name="beginTime" class="iText"  value="${beginTime}"/>
		结束时间：<input type="text" id="datetimepicker_dark2" placeholder="点击选择日期时间" name="endTime" class="iText"  value="${endTime }"/>
		<input type="hidden" name="userId" value="${userId}"/>
		<input type="hidden" name="gameId" value="${gameId}"/>
		<input type="submit" class="button btn_style3" value="查询"/></p>
	</form>

	<table class="table" cellspacing="0" summary="用户积分详细信息" >
		<tr>
			<th class="spec">推广员账号（A）</th>
			<th>邀请码或兑换码</th>
			<th>被推广员账号（B）</th>
			<th>积分变化</th>
			<th>备注</th>
			<th>时间</th>
		</tr>
		<c:forEach items="${list}" var="list">
			<tr>
				<td class="spec"><a href="javascript:void(0);"  item-id="${list.playerId}" data-reveal-id="myModal" data-animation="fade">${list.playerId}</a></td>
				<td>${list.invitedCode}</td>
				<td class="alt"><a href="javascript:void(0);" item-id="${list.fromPlayerId}" data-reveal-id="myModal" data-animation="fade">${list.fromPlayerId}</a></td>
				<td><c:if test="${list.addNum >= 0}">+${list.addNum}</c:if> <c:if
						test="${list.addNum < 0}">${list.addNum}</c:if></td>
				<td class="alt">${list.memo}</td>
				<td class="alt">${list.createTime}</td>
			</tr>
		</c:forEach>

		<tr>
			<td colspan="6" align="center">
				<ul class="pagination">
					<c:if test="${pages>1 && page>1 }">
						<li><a
							href="<%=request.getContextPath()%>/promoter/detail?page=${page-1}&pageSize=${pageSize}&userId=${userId }&gameId=${gameId}" class="btn_style3"><font color="red">上一页</font></a></li>
					</c:if>

					<c:forEach var="index" begin="1" end="${pages}" step="1">
						<c:choose>
							<c:when
								test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
								<c:choose>
									<c:when test="${index==page }">
										<li class="active"><a
											href="<%=request.getContextPath()%>/promoter/detail?page=${index}&pageSize=${pageSize}&userId=${userId }&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}"
											class="cur" class="btn_style3"><font color="red">${index}</font></a>
									</c:when>
									<c:otherwise>
										<li><a
											href="<%=request.getContextPath()%>/promoter/detail?page=${index}&pageSize=${pageSize}&userId=${userId }&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}" class="btn_style3"><font color="red">${index}</font></a></li>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${(index-page< 0 ? page-index:index-page) == 4}">
										<li><span><font color="red">...</font></span></li>
									</c:when>
									<c:otherwise>
										<c:if test="${index==pages }">
											<li><a
												href="<%=request.getContextPath()%>/promoter/detail?page=${index}&pageSize=${pageSize}&userId=${userId }&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}" class="btn_style3"><font color="red">${pages}</font></a></li>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${pages>1 && page < pages }">
						<li><a
							href="<%=request.getContextPath()%>/promoter/detail?page=${page+1}&pageSize=${pageSize}&userId=${userId }&gameId=${gameId}&beginTime=${beginTime}&endTime=${endTime}" class="btn_style3"><font color="red">下一页</font></a></li>
					</c:if>
				</ul>
			</td>
		</tr>
	</table>
</div>
<div>
	<div id="myModal" class="reveal-modal1">
			<center><h3>用户信息如下：</h3></center>
			<a class="close-reveal-modal"><font size="11">&#215;</font></a>
			<table>
				<tr>
					<td>用户Id:</td><td><input type="text" disabled id="uid" name="uid" value="1" /></td>
				</tr>
				<tr>
					<td>用户等级:</td><td><input type="text" disabled id="level" name="level" value="1" /></td>
				</tr>
				<tr>
					<td>用户充值金额(元):</td><td><input type="text" disabled id="money" name="money" value="1" /></td>
				</tr>
				<tr>
					<td>用户在线时长(秒):</td><td><input type="text" disabled id="time" name="time" value="1" /></td>
				</tr>
				<tr><td colspan="2"><a class="button btn_style3" close-reveal-id="myModal" >关闭</a></td></tr>
			</table>
	</div>
</div>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
		$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
		
		$('a[data-reveal-id]').live('click', function () {
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
						//data:{"level":"63","onlinetime":"961463","amount":"616.0"}
						result = data['data'];
						//alert(JSON.stringify(data));
 
						var level =eval('result.level');
						var time =eval('result.onlinetime');
						var money =eval('result.amount');
						
				        $("#uid").val(uid);
				        $("#level").val(level);
				        $("#time").val(time);
				        $("#money").val(money);
						
						console.log(level);
						console.log(time);
						console.log(money);
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
				},
				complete : function() {
				}
			});
			console.log(result);
	        //调用结束

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
