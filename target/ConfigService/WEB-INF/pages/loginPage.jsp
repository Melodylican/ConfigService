<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
<title>业务推广员系统</title>
<link media="screen" href="<%=request.getContextPath()%>/resources/css/reset.css" rel="stylesheet" type="text/css">
<link media="screen" href="<%=request.getContextPath()%>/resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<!-- <script src="resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>-->
<!--  <script src="resources/js/core-dev.js" type="text/javascript"></script> -->
<style type="text/css">
body{background: #222;}
.login{width:500px;height:240px;padding:20px;position:absolute;top:50%;left:50%;margin:-141px 0 0 -271px;border:1px solid #bbb;background:#fff;}
.header{background: none;border: none;margin-bottom: 10px;}
</style>
</head>
<body>
    <div class="login">
        <div class="header">
            <h1><span><img src="resources/images/logo.png" alt="iDreamsky" title="iDreamsky"></span></h1>
            <h2 class="title"><em>业务推广员系统</em></h2>
        </div>
        <c:if test="${not empty param.error}">
           <center> &nbsp;&nbsp;&nbsp;&nbsp;<font color="red">Oops</font>:用户名或密码错误！ <!--  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}--></center>
        </c:if>
        <form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
        <ul class="form clearfix">
            <li>
                <span class="label">用户名：</span>
                <div class="fc">
                    <input type="text" name="username" class="iText"
                         <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_USERNAME'] }"> 
                                  value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"
                    	 </c:if> 
                    style="width:220px;" />
                </div>
            </li>
            <li>
                <span class="label">密码：</span>
                <div class="fc">
                    <input type="password" name="password" class="iText" style="width:220px;" />
                </div>
            </li>
            <li>
                <div class="fc">
                    <input type="submit" value="登录" class="btn" />
                </div>
            </li>
        </ul>
       </form>
    </div>
</body>
</html>
