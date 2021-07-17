<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/analysis.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="analysis">

<div class="container" id="pagetop">
	<h1>
		${sessionScope.pageTitle}
	</h1>
	<div class="iobox iobox-h ">
	</div>
	<c:if test="${!empty requestScope.errorMessage}">
		<div id="alertDiv" class="alert alert-danger alert-dismissable">
			<button id="alertDismissButton" type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>${requestScope.errorMessage}</strong>
		</div>
	</c:if>
	<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '5'}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							产品订单报表下载
						</h2>
					</div>
					<!-- /span3 -->
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div class="iobox iobox-h">
					<table class="table">
						<tbody>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									对象期间
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="dateFrom" maxlength="10" onfocus="WdatePicker()"/>
									～
									<sf:input class="span4 control-largeCommon" path="dateTo" maxlength="10" onfocus="WdatePicker()"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadPTA">
						<b>
							下载产品订购信息
						</b>
					</a>
				</div>
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadTPA">
						<b>
							下载订单进展信息
						</b>
					</a>
				</div>
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadC">
						<b>
							下载投诉单补货进展信息
						</b>
					</a>
				</div>
			</div>
		</div>
		
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							发货统计报表
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div class="iobox iobox-h">
					<table class="table">
						<tbody>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									年份选择
								</th>
								<td>
									<sf:select class="w200" path="yearSelected" items="${analysis.yearInput}"/>
								</td>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									月份选择
								</th>
								<td>
									<sf:select class="w200" path="monthSelected" items="${analysis.monthInput}"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadMEI">
						<b>
							下载报关金额信息
						</b>
					</a>
				</div>
			</div>
		</div>
		
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							基本信息下载
						</h2>
					</div>
					<!-- /span3 -->
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadPI">
						<b>
							下载产品基本信息
						</b>
					</a>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${sessionScope.loginUser.category == '1'|| sessionScope.loginUser.category == '4' || sessionScope.loginUser.category == '5'}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							分析统计报表
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div class="iobox iobox-h">
					<table class="table">
						<tbody>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									汇率
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="rate" maxlength="10"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="form-actions ta-c">
					<a class="btn w200" id="downloadPA">
						<b>
							下载利润信息
						</b>
					</a>
				</div>
			</div>
		</div>
	</c:if>
	
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>