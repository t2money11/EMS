<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/code.js"></script>

<sf:form id="form1" method="post" action="/EMS/code/add" modelAttribute="code">
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
						Code信息
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
								CodeID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="codeId" maxlength="30"/>
								<br/>
								<sf:errors path="codeId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Code名称
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="codeName" maxlength="30"/>
								<br/>
								<sf:errors path="codeName" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" href="javascript:$('#form1').submit()">
					<b>
						添加Code
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
