<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<jsp:include page="header.jsp"></jsp:include>

<sf:form id="form1" method="post" action="" modelAttribute="top">

	<div class="container" id="pagetop">
		<h1>
			${sessionScope.pageTitle}
		</h1>
		<div class="iobox iobox-h "></div>

		<div class="iobox iobox-h" style="margin-top:20px">
			<table class="table">
				<tbody>
					<tr>
						<th class="iobox-label-area additional va-m">当前用户ID</th>
						<td>${sessionScope.loginUser.userId} &nbsp</td>
						<th class="iobox-label-area additional va-m">当前用户姓名</th>
						<td>${sessionScope.loginUser.userNameC} &nbsp</td>
					</tr>
					<tr>
						<th class="iobox-label-area additional va-m">用户类型</th>
						<td colspan="3">${sessionScope.loginUser.categoryName} &nbsp</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<c:if test="${top.infoList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span6">
						<h2>
							系统通知 <span class="badge badge-important">${top.infoList.size()}</span>
						</h2>
					</div>
					<!-- /span8 -->
					<div class="span6 ta-r secondary">
						<a href="javascript:void(0);"></a>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div class="iobox iobox-h skeleton">
					<table class="table">
					<c:forEach var="item" items="${top.infoList}" varStatus="status">
						<tr>
							<td class="">${item}</td>
						</tr>
					</c:forEach>
					</table>
				</div>
			</div>
		</div>
		</c:if>
		
	</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
