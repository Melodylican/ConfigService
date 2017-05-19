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
				<span>当前位置：</span> <strong>积分兑换配置 <c:if
						test="${not empty updateMsg}">
						<h1>
							<font color="red">${updateMsg}</font>
						</h1>
					</c:if></strong>
			</div>
			<div class="wrap">
				<div class="grid">
					<form id="Form" action="<%=request.getContextPath()%>/exchange/saveupdate"
						modelAttribute="exchBean" method="POST">
						<p id="info"></p>
						<p>
							<label>游戏名称</label> <select id="u4_input" name="gameName">
								<c:forEach items="${gameNameList}" var="nameList">
									<option
										<c:if test="${exchBean.gameName==nameList }">selected="true"</c:if>
										value="${nameList }">${nameList}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="id" value="${exchBean.id }"/>
						</p>

						<p>
							<label for="confirm-password">兑换开始时间</label> <input type="text"
								id="datetimepicker_dark1" placeholder="点击选择日期时间" class="iText"
								name="exchBeginTime" value="${exchBean.exchBeginTime }" />
						</p>
						<p>
							<label for="confirm-password">兑换结束时间</label> <input type="text"
								id="datetimepicker_dark2" placeholder="点击选择日期时间" class="iText"
								name="exchEndTime" value="${exchBean.exchEndTime }" />
						</p>

						<br>
						<fieldset
							style="border-width: 2px; border-color: #008000; width:50%; height:220">
							<legend>现金兑换方案</legend>
							<p>
								<label>现金兑换周期</label> 
								<select id="period1" name="period1">
									<option>----请选择周期----</option>
									<option value="1" <c:if test="${exchBean.period1==1 }">selected="true"</c:if>>每周</option>
									<option value="2" <c:if test="${exchBean.period1==2 }">selected="true"</c:if>>每天</option>
								</select>
								<select class="period2">
									<option>----请选择星期----</option>
								</select> 
								<select class="period2" name="period2">
									<option value="1" <c:if test="${exchBean.period2==1 }">selected="true"</c:if>>星期一</option>
									<option value="2" <c:if test="${exchBean.period2==2 }">selected="true"</c:if>>星期二</option>
									<option value="3" <c:if test="${exchBean.period2==3 }">selected="true"</c:if>>星期三</option>
									<option value="4" <c:if test="${exchBean.period2==4 }">selected="true"</c:if>>星期四</option>
									<option value="5" <c:if test="${exchBean.period2==5 }">selected="true"</c:if>>星期五</option>
									<option value="6" <c:if test="${exchBean.period2==6 }">selected="true"</c:if>>星期六</option>
									<option value="7" <c:if test="${exchBean.period2==7 }">selected="true"</c:if>>星期日</option>
								</select>
							</p>
							<p>
								<label>现金兑换上限</label> <input type="number" name="exchLimit"
									value="${exchBean.exchLimit }" style="width: 60px" min="0">
								<label>积分</label>
							</p>

							<p>
								<label>现金兑换标准 </label> <input type="number"
									name="exchStandard1" value="${exchBean.exchStandard1 }"
									style="width: 60px" min="0" /> 积分,兑换  <input type="number"
									name="exchStandard2" value="${exchBean.exchStandard2 }"
									style="width: 60px" min="0" /> 元
							</p>
						</fieldset>
						<p>
							<label><input type="submit"
								style="width:100px ;height:30px" value="保存" /></label> <label><input
								type="button" style="width:100px ;height:30px" value="返回上一步"
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
		$('#datetimepicker_dark2').datetimepicker({
			theme : 'dark'
		})
		$('#datetimepicker_dark3').datetimepicker({
			theme : 'dark'
		})

		$('#scoreLimit')
				.bind(
						'input propertychange',
						function() {
							var scoreLimit = parseInt($("#scoreLimit").val());
							var promoterA = parseInt($("#promoterA").val());
							var promoterASecond = parseInt($("#promoterASecond")
									.val());
							console.log(scoreLimit / promoterA);
							console.log("值发生了变化");
							$("#times").val(
									Math.floor(scoreLimit
											/ (promoterA + promoterASecond)));

						});

		$('#promoterA')
				.bind(
						'input propertychange',
						function() {
							var scoreLimit = parseInt($("#scoreLimit").val());
							var promoterA = parseInt($("#promoterA").val());
							var promoterASecond = parseInt($("#promoterASecond")
									.val());
							console.log(scoreLimit / promoterA);
							console.log("值发生了变化");
							$("#times").val(
									Math.floor(scoreLimit
											/ (promoterA + promoterASecond)));
						});

		$('#promoterASecond')
				.bind(
						'input propertychange',
						function() {
							var scoreLimit = parseInt($("#scoreLimit").val());
							var promoterA = parseInt($("#promoterA").val());
							var promoterASecond = parseInt($("#promoterASecond")
									.val());
							console.log(scoreLimit / promoterA);
							console.log("值发生了变化" + promoterA + promoterASecond);
							$("#times").val(
									Math.floor(scoreLimit
											/ (promoterA + promoterASecond)));
						});

		var validator1;
		$(document).ready(function() {
			validator1 = $("#Form").validate({
				debug : true,

				rules : {
					exchBeginTime : {
						required : true,
					},
					exchEndTime : {
						required : true,
					},
					exchLimit : {
						required : true,
						minlength : 1,
						maxlength : 10
					},
					exchStandard1 : {
						required : true,
						minlength : 1,
						maxlength : 4
					},
					exchStandard2 : {
						required : true,
						minlength : 1,
						maxlength : 4
					},
					period1 : {
					     required : true,
					     number:true
				    }
			},
			messages : {
				    period1: {
					    required : '请选择周期',
					    number:'请选择周期'
				    },
					exchBeginTime : {
						required : '请输入兑换开始时间',
					},
					exchEndTime : {
						required : '请输入兑换结束时间'
					},
					exchLimit : {
						required : '兑换积分上限',
						minlength : '兑换上限至少为1位',
						maxlength : '兑换上限至多为4位'

					},
					exchStandard1 : {
						required : '请输入积分',
						minlength : '不能小于1个字符',
						maxlength : '不能超过4个字符'
					},
					exchStandard2 : {
						required : '请输入元',
						minlength : '不能小于1个字符',
						maxlength : '不能超过4个字符'
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

			//    $("#check").click(function () {
			//      console.log($("#Form").valid() ? "填写正确" : "填写不正确");
			// });
		});

		$(document).ready(function() {
			$("#period1").change(function() {
				$("#period1 option").each(function(i, o) {
					if ($(this).attr("selected")) {
						$(".period2").hide();
						$(".period2").eq(i).show();
					}
				});
			});
			$("#period1").change();
		});
	</script>
</body>
</html>
