<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />
<link href="<%=request.getContextPath()%>/resources/css/style.css"
	rel="stylesheet" type="text/css">
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
		<div class="sidebar" style="height: 800px;">
			<div class="box">
				<div class="title">
					<h3 class="ms">推广员运营配置</h3>
				</div>
				<ul>
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">积分配置管理</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/order">积分兑换审批</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/orderpay">通过审核订单支付</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/payedorder">已完成支付订单提交</a></li>
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
				<span>当前位置：</span> <strong>操作人员管理           <c:if test="${not empty updateMsg}">
				                                              <h1><font color="red">${updateMsg}</font></h1>
			                                           </c:if></strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form id="Form" action="<%=request.getContextPath()%>/redeem/savecreate"  modelAttribute="userBean" method="POST">
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>兑换码信息</legend>
							<p id="info"></p>
							<p>
								<label >游戏名</label> 
								<input type="text" class="iText" name="gameName" disabled value="${gameName }" />
								<input type="hidden" class="iText" name="gameName" value="${gameName }" />
								<input type="hidden" class="iText" name="actId" value="${actId }" />
							</p>
							<p>
								<label >游戏Id</label> 
								<input type="text" class="iText" disabled value="${gameId }" />
								<input type="hidden" class="iText" name="gameId" value="${gameId }" />
							</p>
							<p>
								<label >兑换码</label> <input type="text"
									class="iText" name="code"
									value="${gameRedeemBean.code }" />
							</p>
							<p>
								<label  id="u4">兑换积分</label> <input type="text"
									class="iText" name="score"
									value="${gameRedeemBean.score }" />
							</p>
						</fieldset>
						<p>
							<label><input type="submit" style="width:100px ;height:30px" value="保存" /></label>
							<label><input type="button" style="width:100px ;height:30px" value="返回上一步"
								onClick="javascript :history.back(-1);" /></label>
						</p>
					</form>
				</div>
			</div>
		</div></div>
		<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
		<script type="text/javascript">
			$('#datetimepicker_dark1').datetimepicker({
				theme : 'dark'
			})
	
	var validator1;
    $(document).ready(function () {
        validator1 = $("#Form").validate({
            debug: true,
            
            rules: {
                score: {
                    required: true
                },
                code: {
                    required: true
                }

            },
            messages: {
                code: {
                    required: '请输入兑换码'

                },
                score: {
                    required: '请输入对应积分'
                }

            },

            highlight: function(element, errorClass, validClass) {
                $(element).addClass(errorClass).removeClass(validClass);
                $(element).fadeOut().fadeIn();
            },
            unhighlight: function(element, errorClass, validClass) {
                $(element).removeClass(errorClass).addClass(validClass);
            },
            submitHandler: function (form) {
                console.log($(form).serialize());
                form.submit();
            }
        });

    //    $("#check").click(function () {
      //      console.log($("#Form").valid() ? "填写正确" : "填写不正确");
       // });
    });

    
    $("input.gameType").each(function(){//给所有的input绑定事件
	 $(this).click(function(){
	     var l=[]; //创建空数组l
		 $("input.gameType:checked").each(function(i){l[i]=this.value});
		 //将所有的选中的值存放l
		 $("#gamedate").val(l.join(","));//将数据值联合字符串给显示对象附值
		});});

		</script>
</body>
</html>
