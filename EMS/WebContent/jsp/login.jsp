<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录画面</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<script src="../jsp/js/jquery.js"></script>
</head>

<body data-spy="scroll">
	<div class="navbar navbar-fixed-top nicenavbar navbar-inverse">
	  <div class="navbar-inner">
	    <div class="container">
	      <a class="brand" >
	        <b>社内系统</b>
	      </a>
	    </div>
	  </div>
	</div>
	<div class="container" id="pagetop">
      <div class="hero-unit hero-skeleton pb0">
      </div>
      <div class="row-fluid">
        <div class="span6 offset3" style="margin-top: 150px">
          <div class="titledbox">
            <div class="titledbox-header ta-c">
              <h1><big>登录画面</big></h1>
            </div>
            <sf:form id="form1" method="post" action="/EMS/user/login" modelAttribute="user">
	            <div class="titledbox-body titledbox-body-extended">
	              <div class="iobox iobox-h skeleton">
	                <table class="table">
	                  <tbody>
	                  <tr>
	                    <td class="p10">
	                      <input type="text" class="span12 control-large" name="userId" placeholder="用户名" autofocus></input>
	                    </td>
	                  </tr>
	                  <tr>
	                    <td class="p10">
	                      <input type="password" class="span12 control-large" name="password" placeholder="密码"/></input>
	                    </td>
	                  </tr>
	                  </tbody>
	                </table>
	              </div>
	              <div class="form-actions" style="text-align:center;">
	                <a name="login" class="btn btn-primary btn-large btn-block" href="javascript:$('#form1').submit()"><b>登录</b></a>
	                <b><sf:errors path="errorMessage" class="sf-error"/></b>
	              </div>
	            </div>
            </sf:form>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="container">
        <p class="additional ta-c">Copyright @ 2015 All Rights Reserved 有限公司</p>
      </div>
    </div>
</body>
</html>
