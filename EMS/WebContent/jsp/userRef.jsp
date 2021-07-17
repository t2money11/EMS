<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/user.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="user">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>

<div class="container" id="pagetop">
	<h1>
		${sessionScope.pageTitle}
	</h1>
	<div class="iobox iobox-h ">
	</div>
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						用户信息
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
								用户ID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userId" maxlength="30" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用户中文名
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userNameC" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="userNameC" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用户英文名
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userNameE" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="userNameE" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								合同期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="contractPeriod" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用户类型
							</th>
							<td>
								<sf:select class="w300" path="category" items="${user.categoryInput}" disabled="true"/>
								<br/>
								<sf:errors path="category" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								邮箱
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mail" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="mail" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								性别
							</th>
							<td>
								<sf:select class="w300" path="gender" items="${user.genderInput}" disabled="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								生日
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="birthday" maxlength="10" onfocus="" readonly="true"/>
								<br/>
								<sf:errors path="birthday" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								最高学历
							</th>
							<td>
								<sf:select class="w300" path="highestEducation" items="${user.highestEducationInput}" disabled="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								职位
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="position" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="position" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								入职日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="onboardDate" maxlength="10" onfocus="" readonly="true"/>
								<br/>
								<sf:errors path="onboardDate" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								离职日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="seperateDate" maxlength="10" onfocus="" readonly="true"/>
								<br/>
								<sf:errors path="seperateDate" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								带薪休假
							</th>
							<td>
								<sf:select class="w300" path="vacation" items="${user.vacationInput}" disabled="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								人事关系
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="humanRelations" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								工资（单位:元/月）
							</th>
							<c:choose>
								<c:when test="${sessionScope.loginUser.category eq '1'}">
									<td>
										<sf:input class="span4 control-largeCommon" path="salary" maxlength="30" readonly="true"/>
										<br/>
										<sf:errors path="salary" class="sf-error"/>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										保密信息
									</td>
								</c:otherwise>
							</c:choose>
							<th class="iobox-label-area additional va-m">
								福利
							</th>
							<c:choose>
								<c:when test="${sessionScope.loginUser.category eq '1'}">
									<td>
										<sf:input class="span4 control-largeCommon" path="welfare" maxlength="30" readonly="true"/>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										保密信息
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								座机
							</th>
							<td>
								<sf:textarea class="span4 control-largeCommon" path="contact" maxlength="100"
								style="resize:none;" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								手机
							</th>
							<td>
								<sf:textarea class="span4 control-largeCommon" path="urgentContact" maxlength="100"
								style="resize:none;" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								家庭地址
							</th>
							<td>
								<sf:textarea class="span4 control-largeCommon" path="address" maxlength="100"
								style="resize:none;" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								备注
							</th>
							<td>
								<sf:textarea class="span4 control-largeCommon" path="comment" maxlength="100"
								style="resize:none;" readonly="true"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" id="goBack">
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
