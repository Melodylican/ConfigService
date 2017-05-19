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
					<li class="cur"><a href="<%=request.getContextPath()%>/promoter/index">活动配置</a></li>
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
				<span>当前位置：</span> <strong>积分配置管理 > 导入兑换码</strong>
			</div>
		<div class="header">
		     <h2 class="title"><em>注：</em>请导入如下格式的excel文件！ </h2>

        </div>
		<div class="toolbar">
		            <div style="float: left;margin:auto;clear: left;">
		           	 <center><h1><span><img src="<%=request.getContextPath()%>/resources/images/excel.png" alt="iDreamsky" title="iDreamsky"></span></h1></center>
                    </div>
                    <div style="float: left;margin:auto;clear: left;"><center>
					 <form method="POST"  enctype="multipart/form-data" id="form1" action="<%=request.getContextPath()%>/redeem/import">  
					        <table >  
					         <tr >  
					            <td >上传兑换码Excel文件: </td>  
					            <td > <input id="upfile" type="file" name="upfile" class="iText"></td>
					            <td>
					                <input type="hidden" name="gameId" value="${gameId}" />
									<input type="hidden" name="actId" value="${actId}" />
									<input type="hidden" name="gameName" value="${gameName}" />
								</td>
					            <td ><input type="button" value="提交Excel" id="btn" name="btn" class="btn"></td>
					         </tr>  
					        
					            <!-- <td><input type="submit" value="提交" onclick="return checkData()"></td>  -->  
					        </table>    </center>
					  </form>
					  <label><input type="button" style="width:100px ;height:30px" value="返回上一步"
								onClick="javascript :history.back(-1);" /></label>
				  </div>
		</div>

		</div>
	</div>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.8.2.min.js"
	type="text/javascript"></script> 
<script src="<%=request.getContextPath()%>/resources/js/core-dev.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.validate-1.13.1.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.form.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$(".sidebar").height($(document).height() - 103);
		});
		
		
		//ajax 方式上传文件操作  
        $(document).ready(function(){  
           $("#btn").click(function(){  
               if(checkData()){  
                   $("#form1").ajaxSubmit({    
                       url:'<%=request.getContextPath()%>/redeem/import',  
                       //dataType: 'text',  
                       success: resutlMsg,  
                       error: errorMsg  
                   });   
                   function resutlMsg(msg){  
                       alert(msg);     
                       $("#upfile").val("");  
                   }  
                   function errorMsg(){   
                       alert("导入excel出错！");      
                   }  
               }  
           });  
        });  
          
        //JS校验form表单信息  
        function checkData(){  
           var fileDir = $("#upfile").val();  
           var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
           if("" == fileDir){  
               alert("选择需要导入的Excel文件！");  
               return false;  
           }  
           if(".xls" != suffix && ".xlsx" != suffix ){  
               alert("选择Excel格式的文件导入！");  
               return false;  
           }  
           return true;  
        }  
  
	</script>
</body>
</html>
