<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/ajaxfileupload.js"></script>
<script src="../jsp/js/production.js"></script>
<script src="../jsp/js/vendorPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>

<sf:form id="form1" method="post" action="/EMS/production/update" modelAttribute="production">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>
<sf:hidden path="picture"/>
<sf:hidden path="pictureExisted"/>
<sf:hidden path="folderName1"/>
<sf:hidden path="folderName2"/>
<sf:hidden path="isUsed"/>
<sf:hidden path="updateTime"/>

<div class="container" id="pagetop">
	<h1>
		${sessionScope.pageTitle}
	</h1>
	<c:if test="${production.isUsed}">
		<font style="color:red">该数据已经被引用（如：确定状态的订单，发货单，询单，投诉），如有变更将会自动产生新版本。<br/>
		<b>如确认需要修改并覆盖当前版本并且反映到所有引用该产品的询单，订单，发货单，投诉单，请打钩</b></font>
		<input type="checkbox" name="isUpdate" style="margin-top:-2px"/>
	</c:if>
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
						产品基本信息
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
								产品型号
							</th>
							<td colspan="3">
								<sf:input class="span6 control-largeCommon" path="productionId" maxlength="45" readonly="true"/>
								-----
								<sf:input class="span1 control-largeCommon" path="versionNo" maxlength="4" readonly="true"/>
								<br/>
								<sf:errors path="productionId" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品型号（供应商）
							</th>
							<td colspan="3">
								<sf:input class="span6 control-largeCommon" path="productionIdVendor" maxlength="45"/>
								<br/>
								<sf:errors path="productionIdVendor" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
				<table class="table">
					<tbody>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;border-top: 1px solid #dddddd;">
								产品描述（中文）
							</th>
							<td style="border-top: 1px solid #dddddd;">
								<sf:textarea class="span4 control-largeCommon" path="descriptionC" maxlength="2500" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="descriptionC" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;border-top: 1px solid #dddddd;">
								产品描述（英文）
							</th>
							<td style="border-top: 1px solid #dddddd;">
								<sf:textarea class="span4 control-largeCommon" path="descriptionE" maxlength="2500" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="descriptionE" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品名（报关用中文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionCname4export" maxlength="100"/>
								<br/>
								<sf:errors path="productionCname4export" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品名（报关用英文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionEname4export" maxlength="100"/>
								<br/>
								<sf:errors path="productionEname4export" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								HSCode
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="hscode" maxlength="30"/>
								<br/>
								<sf:errors path="hscode" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								退税率(%)
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="taxReturnRate" maxlength="30"/>
								<br/>
								<sf:errors path="taxReturnRate" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								材质
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="material" maxlength="30"/>
								<br/>
								<sf:errors path="material" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								表面处理
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="surface" maxlength="30"/>
								<br/>
								<sf:errors path="surface" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								尺寸
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="size" maxlength="30"/>
								<br/>
								<sf:errors path="size" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								审核状态
							</th>
							<td>
								<c:choose>
								<c:when test="${(sessionScope.loginUser.category == '1' 
													|| sessionScope.loginUser.category == '3') 
													&& !production.isUsed}">
									<sf:select class="w200" path="status" items="${production.statusInput}"/>
								</c:when>
								<c:otherwise>
									<sf:select class="w200" path="status" items="${production.statusInput}" disabled="true"/>
									<sf:hidden path="status"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								图纸存放地址
							</th>
							<td>
								\\wangchenyi\designDrawing\<c:out value="${production.productionId}" ></c:out>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								图纸修改日期
							</th>
							<td>
								<c:choose>
								<c:when test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '3' || sessionScope.loginUser.category == '5'}">
									<sf:input class="span4 control-largeCommon" path="lastDrawUpdateDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="lastDrawUpdateDate" class="sf-error"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="lastDrawUpdateDate" maxlength="10" readonly="true"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								品牌
							</th>
							<td colspan="3">
								<sf:input class="span4 control-largeCommon" path="brand"/>
								<br/>
								<sf:errors path="brand" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用途
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="purpose"/>
								<br/>
								<sf:errors path="purpose" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								种类
							</th>
							<td>
								<sf:select class="w200" path="kind" items="${production.kindInput}"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商
							</th>
							<td colspan="3">
								<sf:input class="span2 control-largeCommon" path="vendorId" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="vendorName" maxlength="20" readonly="true"/>
								<a id="vendorRef" noAuth="1" class="btn btn-info w50" style="margin-left: 10px" onclick="callVendorPop(this)">
									<b>
										供应商检索
									</b>
								</a>
								<a id="vendorRefClear" class="btn btn-info w50" style="margin-left: 10px" onclick="clearVendor(this)">
									<b>
										清除
									</b>
								</a>
								<br/>
								<sf:errors path="vendorId" class="sf-error"/>
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
								<a id="customerRef" class="btn btn-info w50" style="margin-left: 10px" onclick="callCustomerPopSimple(this)">
									<b>
										客户检索
									</b>
								</a>
								<a id="customerRefClear" class="btn btn-info w50" style="margin-left: 10px" onclick="clearCustomerSimple(this)">
									<b>
										清除
									</b>
								</a>
								<br/>
								<sf:errors path="customerId" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								备注
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="9999" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="comment" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						图片信息
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
								产品图片
							</th>
							<td colspan="3">
								<input id="picFiles" type="file" style="display: none" size="30" name="picFiles" onchange="ajaxFileUpload()"/>
								<a class="btn btn-info w50" style="margin-left: 10px" href="javascript:picSelect()">
									<b>
										图片上传
									</b>
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div id="imgDiv"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						包装信息
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
								包装方式
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="packMethod" maxlength="30"/>
								<br/>
								<sf:errors path="packMethod" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								体积
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="volume" maxlength="30"/>
								<br/>
								<sf:errors path="volume" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								毛重
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="grossWeight" maxlength="30"/>
								<br/>
								<sf:errors path="grossWeight" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								净重
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="netWeight" maxlength="30"/>
								<br/>
								<sf:errors path="netWeight" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								内箱容纳产品数
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="inside" maxlength="30"/>
								<br/>
								<sf:errors path="inside" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								外箱容纳产品数
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="outside" maxlength="30"/>
								<br/>
								<sf:errors path="outside" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" href="javascript:$('#form1').submit()">
					<b>
						修改产品
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
<div class="modal fade" id="modal-container-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">

				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					图片查看
				</h4>
			</div>
			<div id="zoom" class="modal-body">
			</div>
		</div>
	</div>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
