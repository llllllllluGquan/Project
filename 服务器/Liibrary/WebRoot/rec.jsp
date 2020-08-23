<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<%
  	request.setCharacterEncoding("utf-8");
   %>
<body>
	您的姓名是： ${param.username }
	<br> 您的密码是： ${param.password }
	<br> 您的姓别是： ${param.sex }
	<br> 您的爱好是： ${param.aihao }
	<br> 您的星座是： ${param.xingzuo }
	<br> 您的个人简介是： ${param.intro }
	<br>
</body>
</html>
