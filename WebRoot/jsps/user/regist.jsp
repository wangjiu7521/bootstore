
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> <%--页面编码--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册页面</title>
    
	<meta http-equiv="content-type" content="text/html;charset=utf-8">  

  </head>
  
<body>
	<h1>注册</h1>
	<%--
	1.显示错误信息
	 --%>
	 <p stype="color:red;font-weight:900">${msg }</p>
	<form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
		<input type="hidden" name="method" value="regist"/>
		用户名：<input type="text" name="username" value="${form.username }">
		<p stype="color:red;font-weight:900">${errors.username }</p>
		<br/>
		密    码：<input type="password" name="password" value="${form.password }">
		<p stype="color:red;font-weight:900">${errors.password }</p>
		<br/>
		邮    箱：<input type="text" name="email" value="${form.email }">
		<p stype="color:red;font-weight:900">${errors.email }</p>
		<br/>
		<input type="submit" value="注册">
	</form>    

</body>
</html>
