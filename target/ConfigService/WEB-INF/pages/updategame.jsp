<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<li>
						<a href="<%=request.getContextPath()%>/promoter/forum" >用户吐槽</a>
					</li>										
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li ><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li class="cur"><a href="<%=request.getContextPath()%>/game/game">游戏管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>游戏管理           <c:if test="${not empty updateMsg}">
				                                              <h1><font color="red">${updateMsg}</font></h1>
			                                           </c:if></strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form id="Form" action="<%=request.getContextPath()%>/game/saveupdate"  modelAttribute="gameBean" method="POST">
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>游戏信息配置</legend>
							<p id="info"></p>
							<p>
								<label >游戏名</label> 
								<input type="text"
									name="gameName" class="iText"
									value="${gameBean.gameName }" />
								<input type="hidden" name="id" value="${gameBean.id}">
							</p>
							<p>
								<label >游戏Id</label> <input type="text"
									class="iText" name="gameId"
									value="${gameBean.gameId }" />
							</p>
							<p>
								<label >游戏所属部门</label> <input type="text"
									class="iText" name="department"
									value="${gameBean.department }" />
							</p>
							<p>
								<label>简述</label>
								<c:choose>
									<c:when test="${not empty gameBean.description}">
										<textarea name="description"
											style="width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;"
											>${gameBean.description }</textarea>
									</c:when>
									<c:otherwise>
										<textarea name="description"
											style="width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;"
											placeholder="方案简述..."></textarea>
									</c:otherwise>
								</c:choose>
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
	
	var validator1;
    $(document).ready(function () {
        validator1 = $("#Form").validate({
            debug: true,
            
            rules: {
                gameName: {
                    required: true,
                    minlength: 2,
                    maxlength: 20
                },
                gameId: {
                    required: true,
                    minlength: 1,
                    maxlength: 16
                },
                department: {
                    required: true,
                    minlength: 2,
                    maxlength: 20
                }
            },
            messages: {
                gameName: {
                    required: '请输入游戏名',
                    minlength: '用户名不能小于2个字符',
                    maxlength: '用户名不能超过10个字符',
                    remote: '游戏名不存在'
                },
                gameId: {
                    required: '请输入游戏ID',
                    minlength: '密码不能小于1个字符',
                    maxlength: '密码不能超过16个字符'
                },
                department: {
                    required: '请输入游戏所属部门'
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
