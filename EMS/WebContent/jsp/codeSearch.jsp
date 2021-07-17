<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/code.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="code">

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
						检索条件
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
								CodeType
							</th>
							<td>
								产品种类
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="codeSearch">
					<b>
						检索
					</b>
				</a>
				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2'}">
					<a style="margin-left:30px;" class="btn btn-primary w100" id="codeAdd">
						<b>
							添加
						</b>
					</a>
				</c:if>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${code.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="width:100px">
							CodeId
						</th>
						<th style="">
							CodeName
						</th>
					</tr>
					<c:forEach var="item" items="${code.resultCodeList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" codeId="${item.codeId}"/>
							</td>
							<td>
								${item.codeId!=null?item.codeId:'&nbsp;'}
							</td>
							<td>
								${item.codeName!=null?item.codeName:'&nbsp;'}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="bulk-editing-area dividingbox dividingbox-jointed">
					<div class="row-fluid">
						<div class="span12 ta-c">
							<a style="margin-left:30px;" id="codeDelete" class="btn w100" href="javascript:void(0);">
								<b>
									删除
								</b>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>