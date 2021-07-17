<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<head>
<title>${sessionScope.pageTitle}</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<link rel="stylesheet" type="text/css" href="../jsp/css/sp-theme-blue.css"></link>
<script src="../jsp/js/jquery.js"></script>
<script src="../jsp/js/My97DatePicker/WdatePicker.js"></script>
<script src="../jsp/js/header.js"></script>
<script src="../jsp/js/common.js"></script>
<script src="../jsp/js/jquery-1.11.1.js"></script>
<script src="../jsp/js/bootstrap.js"></script>
<script src="../jsp/js/passwordUpdatePopCall.js"></script>


</head>
<body class="with-navbar" style="overflow:scroll">

	<div class="navbar navbar-fixed-top nicenavbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand"> 
					<b>社内系统</b>
				</a>
				<ul class="nav">
					<c:choose>  
						<c:when test="${sessionScope.currentMenu == 'topMenu'}">
							<li><a href="javascript:void(0)" id="topMenu" style="color:#ffffff;">主页</a></li>
						</c:when>  
						<c:otherwise>
							<li><a href="javascript:void(0)" id="topMenu">主页</a></li> 
						</c:otherwise>  
					</c:choose>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'userManagement'}">
								<li><a href="javascript:void(0)" id="userSearchMenu" style="color:#ffffff;">用户管理</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="userSearchMenu">用户管理</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
					<li class="dropdown">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'productionManagement'
											|| sessionScope.currentMenu == 'vendorManagement'
											|| sessionScope.currentMenu == 'customerManagement'
											|| sessionScope.currentMenu == 'userCustomerLinkRefManagement'}">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color:#ffffff;">基础数据管理菜单<b class="caret"></b></a>
							</c:when>  
							<c:otherwise>
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">基础数据管理菜单<b class="caret"></b></a>
							</c:otherwise>  
						</c:choose>
             			<ul class="dropdown-menu">
               				<li><a id="productionSearchMenu" href="javascript:void(0)">产品管理</a></li>
               				<li><a id="vendorSearchMenu" href="javascript:void(0)">供应商管理</a></li>
               				<li><a id="customerSearchMenu" href="javascript:void(0)">客户管理</a></li>
               				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2' || sessionScope.loginUser.category == '5'}">
               					<li><a id="userCustomerLinkRefSearchMenu" href="javascript:void(0)">客户担当管理</a></li>
               				</c:if>
               				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2' || sessionScope.loginUser.category == '5'}">
	               				<li><a id="codeSearchMenu" href="javascript:void(0)">Code管理</a></li>
               				</c:if>
             			</ul>
		            </li>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '4' || sessionScope.loginUser.category == '5'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'inquiryManagement'}">
								<li><a href="javascript:void(0)" id="inquirySearchMenu" style="color:#ffffff;">询单管理</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="inquirySearchMenu">询单管理</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '4' || sessionScope.loginUser.category == '5'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'tradeOrderManagement'}">
								<li><a href="javascript:void(0)" id="tradeOrderSearchMenu" style="color:#ffffff;">订单管理</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="tradeOrderSearchMenu">订单管理</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '4' || sessionScope.loginUser.category == '5'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'receiptManagement'}">
								<li><a href="javascript:void(0)" id="receiptSearchMenu" style="color:#ffffff;">发货单管理</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="receiptSearchMenu">发货单管理</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '3' && sessionScope.loginUser.userId.toLowerCase() != 'drink' || sessionScope.loginUser.category == '5'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'complaintManagement'}">
								<li><a href="javascript:void(0)" id="complaintSearchMenu" style="color:#ffffff;">投诉管理</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="complaintSearchMenu">投诉管理</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
					<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '4' || sessionScope.loginUser.category == '5'}">
						<c:choose>  
							<c:when test="${sessionScope.currentMenu == 'analysisManagement'}">
								<li><a href="javascript:void(0)" id="analysisSearchMenu" style="color:#ffffff;">数据分析</a></li>
							</c:when>  
							<c:otherwise>
								<li><a href="javascript:void(0)" id="analysisSearchMenu">数据分析</a></li> 
							</c:otherwise>  
						</c:choose>
					</c:if>
				</ul>
				<ul class="nav pull-right">
					<li class="dropdown">
	              		<a href="#" class="dropdown-toggle" data-toggle="dropdown">用户菜单<b class="caret"></b></a>
             			<ul class="dropdown-menu">
               				<li><a id="passwordUpdate" href="javascript:void(0)">密码变更</a></li>
            				<li class="divider"></li>
               				<li><a id="logout" href="javascript:void(0)">退出系统</a></li>
             			</ul>
		            </li>
				</ul>
			</div>
		</div>
	</div>