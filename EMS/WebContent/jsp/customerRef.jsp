<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/customer.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="customer">
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
						客户信息
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
								客户ID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="customerId" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="customerId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户名称
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="customerName" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="customerName" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户全称
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="customerFullName" maxlength="500" style="resize:none;height:140px" readonly="true"/>
							</td>
						</tr>
						<tr>
							
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户地址
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="location" maxlength="500" style="resize:none;height:140px" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								国家（城市）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="country" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="country" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								成交方式
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="freightTerms" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="freightTerms" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								装船口岸
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="portOfLoading" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="portOfLoading" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								目的地
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="portOfDestination" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="portOfDestination" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								付款方式
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="paymentTerms" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="paymentTerms" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								抬头人
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="consignee" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="consignee" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								通知人
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								TEL
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tel" maxlength="30" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								FAX
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="fax" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								公司网站
							</th>
							<td colspan="3">
								<sf:input class="span4 control-largeCommon" path="webSite" maxlength="45" readonly="true"/>
								<sf:errors path="webSite" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人1
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact1" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact1" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人2
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact2" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact2" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人3
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact3" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact3" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人4
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact4" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact4" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人5
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="contact5" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="contact5" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								备注
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="comment" class="sf-error"/>
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
