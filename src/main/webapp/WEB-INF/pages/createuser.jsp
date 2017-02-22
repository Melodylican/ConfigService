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
					<li ><a href="<%=request.getContextPath()%>/exchange/exchange">现金兑换配置</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/order">现金兑换审批</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/orderpay">现金订单支付</a></li>
					<li ><a href="<%=request.getContextPath()%>/promoter/payedorder">提交已完成订单</a></li>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li class="cur"><a href="<%=request.getContextPath()%>/user/user">操作人员管理</a></li>
					</c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
					     <li ><a href="<%=request.getContextPath()%>/game/game">游戏管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="colMain">
			<div class="crumb">
				<span>当前位置：</span> <strong>操作人员管理           <c:if test="${not empty insertMsg}">
				                                              <h1><font color="red">${insertMsg}</font></h1>
			                                           </c:if></strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form id="Form" action="<%=request.getContextPath()%>/user/saveusercreate"  modelAttribute="promoterBean,redeemCodeBean,integralSchemeBean" method="POST">
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>账号信息配置</legend>
							<p id="info"></p>
							<p>
								<label >用户名</label> 
								<input type="text"
									name="userName" class="iText"
									value="${userBean.userName }" />
							</p>
							<p>
								<label >账号密码</label> <input type="text"
									class="iText" name="password"
									value="${userBean.password }" />
							</p>
							<p>
								<label >使用者姓名</label> <input type="text"
									class="iText" name="userRealName"
									value="${userBean.userRealName }" />
							</p>
							<p>
								<label  id="u4">用户权限</label> <select 
									id="u4_input" name="role">
									<option <c:if test="${userBean.role=='USER' }">selected="true"</c:if>  value="USER">普通用户</option>
									<option <c:if test="${userBean.role=='ADMIN' }">selected="true"</c:if>value="ADMIN">积分管理</option>
									<option <c:if test="${userBean.role=='SUPER_ADMIN' }">selected="true"</c:if>value="SUPER_ADMIN">超级管理员</option>
								</select>
							</p>
							<p>
								<label for="confirm-password">设置注册时间</label> <input type="text"
									id="datetimepicker_dark1" placeholder="点击选择日期时间" class="iText"
									name="registerTime" value="${userBean.registerTime }" />
							</p>
							<p>     <label>可配置游戏权限</label>
							        <c:forEach items="${list}" var="list">
                                          <input type="checkbox"  value="${list.gameName }"  class="inputStyle"/>${list.gameName }
                                    </c:forEach>
                                    </p>
                                    <p><label></label>
                                       <textarea name="gameType"  placeholder="拥有操作游戏的权限..." readonly="readonly" style="background-color:#dcdcdc;width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;" id="gamedate" >
                                       </textarea>
                                    </p>
							<p>
								<label>账号用途简述</label>
								<c:choose>
									<c:when test="${not empty userBean.description}">
										<textarea name="description"
											style="width:250px;height:60px;padding:5px;border:1px solid #c9c9c9;color:#666;"
											>${userBean.description }</textarea>
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
		</div>
 </div>
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
                userName: {
                    required: true,
                    minlength: 2,
                    maxlength: 20
                },
                password: {
                    required: true,
                    minlength: 1,
                    maxlength: 16
                },
                userRealName: {
                    required: true,
                    minlength: 2,
                    maxlength: 20
                },
                registerTime: {
                    required: true,
                }
 
            },
            messages: {
                userName: {
                    required: '请输入用户名',
                    minlength: '用户名不能小于2个字符',
                    maxlength: '用户名不能超过10个字符',
                    remote: '游戏名不存在'
                },
                password: {
                    required: '请输入密码',
                    minlength: '密码不能小于1个字符',
                    maxlength: '密码不能超过16个字符'
                },
                registerTime: {
                    required: '请选择或者输入注册时间'
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
    
    $("input.inputStyle").each(function(){//给所有的input绑定事件
	 $(this).click(function(){
	     var l=[]; //创建空数组l
		 $("input.inputStyle:checked").each(function(i){l[i]=this.value});
		 //将所有的选中的值存放l
		 $("#gamedate").val(l.join(","));//将数据值联合字符串给显示对象附值
		});});
		

		</script>
</body>
</html>
