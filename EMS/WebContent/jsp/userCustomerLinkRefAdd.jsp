<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/userCustomerLinkRef.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script src="../jsp/js/userPopCall.js"></script>

<sf:form id="form1" method="post" action="/EMS/userCustomerLinkRef/add" modelAttribute="userCustomerLinkRef">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>
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
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						客户担当信息
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
								用户
							</th>
							<td colspan="3">
								<sf:input class="span2 control-largeCommon" path="userId" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="userName" maxlength="20" readonly="true"/>
								<a id="userRef" class="btn btn-info w50" style="margin-left: 10px" onclick="callUserPop()">
									<b>
										用户检索
									</b>
								</a>
								<a id="userRefClear" class="btn btn-info w50" style="margin-left: 10px" onclick="clearUser()">
									<b>
										清除
									</b>
								</a>
								<br/>
								<sf:errors path="userId" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户
							</th>
							<td colspan="3">
								<sf:input class="span2 control-largeCommon" path="customerId" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="customerName" maxlength="20" readonly="true"/>
								<a id="customerRef" class="btn btn-info w50" style="margin-left: 10px" onclick="callCustomerPopFull()">
									<b>
										客户检索
									</b>
								</a>
								<a id="customerRefClear" class="btn btn-info w50" style="margin-left: 10px" onclick="clearCustomerFull()">
									<b>
										清除
									</b>
								</a>
								<br/>
								<sf:errors path="customerId" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" href="javascript:$('#form1').submit()">
					<b>
						添加客户担当
					</b>
				</a>
				<a style="margin-left:30px;" class="btn btn-primary w150" id="goBack">
					<b>
						返回上一页
					</b>
				</a>
			</div>
		</div>
	</div>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
