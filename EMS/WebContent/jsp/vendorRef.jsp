<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/vendor.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="vendor">
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
						供应商信息
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
								供应商ID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="vendorId" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="vendorId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商名称
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="vendorName" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="vendorName" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商全称
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="vendorFullName" maxlength="500" style="resize:none;height:140px" readonly="true"/>
							</td>
						</tr>
						<tr>
							
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商地址
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="location" maxlength="500" style="resize:none;height:140px" readonly="true"/>
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
								组织机构代码
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="orcc" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="orcc" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								境内货源地
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="shortLocation" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								开票资料
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="billingInfo" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="billingInfo" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人1（姓名以及职位）
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="contact1" maxlength="10" readonly="true"/>
								 - <sf:input style="width: 148px" class="span2 control-largeCommon" path="title1" maxlength="13" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电话
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mobile1" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人2（姓名以及职位）
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="contact2" maxlength="10" readonly="true"/>
								 - <sf:input style="width: 148px" class="span2 control-largeCommon" path="title2" maxlength="13" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电话
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mobile2" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人3（姓名以及职位）
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="contact3" maxlength="10" readonly="true"/>
								 - <sf:input style="width: 148px" class="span2 control-largeCommon" path="title3" maxlength="13" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电话
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mobile3" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人4（姓名以及职位）
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="contact4" maxlength="10" readonly="true"/>
								 - <sf:input style="width: 148px" class="span2 control-largeCommon" path="title4" maxlength="13" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电话
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mobile4" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								联系人5（姓名以及职位）
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="contact5" maxlength="10" readonly="true"/>
								 - <sf:input style="width: 148px" class="span2 control-largeCommon" path="title5" maxlength="13" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电话
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mobile5" maxlength="30" readonly="true"/>
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
