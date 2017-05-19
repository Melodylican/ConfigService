<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<title>P2P后台管理系统</title>
<link media="screen" href="resources/css/reset.css" rel="stylesheet" type="text/css">
<link media="screen" href="resources/css/layout-ms.css" rel="stylesheet" type="text/css">
<script src="resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="resources/js/core-dev.js" type="text/javascript"></script>
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
            <h2 class="title"><em>P2P</em> 后台管理系统</h2>
        </div>
        <form action="" method="post">
        <ul class="form clearfix">
            <li>
                <span class="label">用户名：</span>
                <div class="fc">
                    <input type="text" class="iText" style="width:220px;" />
                </div>
            </li>
            <li>
                <span class="label">密码：</span>
                <div class="fc">
                    <input type="password" class="iText" style="width:220px;" />
                </div>
            </li>
            <li>
                <div class="fc">
                    <input type="submit" value="登录" class="btn" />
                </div>
            </li>
        </ul>
    </div>
    </form>
    <script type="text/javascript">
    $(function(){
        $(".header").functionName();
    });
    </script>
</body>
</html>