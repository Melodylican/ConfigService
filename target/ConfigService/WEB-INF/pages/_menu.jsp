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
			<!--<span>欢迎您：</span><a class="user-name"
				href="${pageContext.request.contextPath}/userInfo">${pageContext.request.userPrincipal.name}</a>
			 <a class="logout" href="/promoter_system/logout">退出</a>  -->
		</div>
	</div>
    

<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">

  <a href="${pageContext.request.contextPath}/promoter/index">Home</a>

  | &nbsp;<!-- 
 
   <a href="${pageContext.request.contextPath}/userInfo">User Info</a>
 
  | &nbsp;
 
  <a href="${pageContext.request.contextPath}/admin">Admin</a>  -->
 
  <c:if test="${pageContext.request.userPrincipal.name != null}">
 
     | &nbsp;
     <a href="${pageContext.request.contextPath}/logout">Logout</a>
    
  </c:if>
 
</div>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
</body>
</html>
