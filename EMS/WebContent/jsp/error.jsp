<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ERROR</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="description" content="ERROR_PAGE">
	<script src="../jsp/js/jquery.js"></script>
	<script src="../jsp/js/header.js"></script>
  </head>
  
  <body>
  	<sf:form id="form1" method="post" action="" modelAttribute="">
	    <Strong>
	    	<font color="red">
	    		该页面已经过期，请按
	    	</font>
	    	<a href="javascript:void(0)" id="topMenu">返回</a>
	    	<font color="red">
	    		回到主页
	    	</font>
	    </Strong>
  	</sf:form>
  </body>
</html>
