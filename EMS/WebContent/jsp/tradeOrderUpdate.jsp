<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/tradeOrder.js"></script>
<script src="../jsp/js/productionPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script src="../jsp/js/vendorPopCall.js"></script>
<script>
$(document).ready(function(){
	
	if(document.getElementsByName("isConfirmed")[0].value == "false"){
		
		var inputList = $("select[vendorId='sendMode']");
		for(var i = 0; i < inputList.size(); i++){
			
			sendModeChange(inputList[i], false);
		}
	}
});
$(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
</script>

<sf:form id="form1" method="post" action="/EMS/tradeOrder/update" modelAttribute="tradeOrder">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>
<input type="hidden" name="pageMode" value="update"/>
<input type="hidden" id="productions" name="productions"/>
<sf:hidden path="isConfirmed"/>
<sf:hidden path="updateTime"/>

<div class="container" id="pagetop">
	<h1>
		${sessionScope.pageTitle}
	</h1>
	<div class="iobox iobox-h">
	</div>
	<c:if test="${!empty requestScope.errorMessage}">
		<div id="alertDiv" class="alert alert-danger alert-dismissable">
			<button id="alertDismissButton" type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>${requestScope.errorMessage}</strong>
		</div>
	</c:if>
	<div class="subnav" id="subnav">
		<ul class="nav nav-pills">
			<li><a name="sw" href="javascript:void(0)" onclick="switchTab('tradeOrder', this)" style="font-weight:bold; color:red">外贸合同</a></li>
			<c:forEach var="item" items="${tradeOrder.interTradeOrderList}" varStatus="status">
				<li><a name="sw" href="javascript:void(0)" onclick="switchTab('${tradeOrder.interTradeOrderList[status.index].vendorId}', this)">${tradeOrder.interTradeOrderList[status.index].vendorId}</a></li>
			</c:forEach>
		</ul>
	</div>
	<div id="div_tradeOrder" type="tradeOrderDiv">
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						订单信息
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
								订单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tradeOrderId" readonly="true"/>
								<br/>
								<sf:errors path="tradeOrderId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单状态
							</th>
							<td>
								<sf:select class="w200" path="status" items="${tradeOrder.statusInput}"/>
								<sf:errors path="status" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								P.O
							</th>
							<td colspan="3">
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="po" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="po" maxlength="30"/>
									<br/>
									<sf:errors path="po" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Contract No.
							</th>
							<td colspan="3">
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="contractNo" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="contractNo" maxlength="30"/>
									<br/>
									<sf:errors path="contractNo" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单创建日
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="tradeOrderCreateDate" maxlength="10" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="tradeOrderCreateDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="tradeOrderCreateDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单确定日
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="tradeOrderConfirmDate" maxlength="10" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="tradeOrderConfirmDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="tradeOrderConfirmDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户
							</th>
							<td colspan="3">
								<sf:input class="span2 control-largeCommon" path="customerId" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="customerName" readonly="true"/>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
								</c:when>
								<c:otherwise>
									<c:if test="${tradeOrder.tradeOrderDtlList.size()==0}">
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
									</c:if>
								</c:otherwise>
								</c:choose>
								<br/>
								<sf:hidden path="customerUpdateTime"/>
								<sf:errors path="customerId" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户全称
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="customerFullName" style="resize:none;height:140px" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户地址
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="location" style="resize:none;height:140px" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Tel
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tel" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Fax
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="fax" readonly="true"/>
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
								成交方式
							</th>
							<td colspan="3">
								<sf:input class="span4 control-largeCommon" path="freightTerms" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="freightTerms" class="sf-error"/>
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
								出货方式
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:select class="w200" path="sendMode" items="${tradeOrder.sendModeInput}" 
										vendorId="sendMode"
										sendMode="tradeOrder" onchange="sendModeChange(this, true)" disabled="true"/>
									<sf:hidden path="sendMode"/>
								</c:when>
								<c:otherwise>
									<sf:select class="w200" path="sendMode" items="${tradeOrder.sendModeInput}" 
										vendorId="sendMode"
										sendMode="tradeOrder" onchange="sendModeChange(this, true)"/>
									<sf:errors path="sendMode" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Shipment
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="shipment" maxlength="30" readonly="true"
										vendorId="recieveDate_tradeOrder"/>
										
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="shipment" maxlength="10" onfocus="WdatePicker()"
										vendorId="recieveDate_tradeOrder"/>
									<br/>
									<sf:errors path="shipment" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Exchange Rate
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="exRate" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="exRate" maxlength="30"/>
									<br/>
									<sf:errors path="exRate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Tax Rebate Rate
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="etrRate" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="etrRate" maxlength="30"/>
									<br/>
									<sf:errors path="etrRate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								备注
							</th>
							<td colspan="3">
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="500" style="resize:none;height:140px"/>
									<br/>
									<sf:errors path="comment" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单确定后变更备注
							</th>
							<td colspan="3">
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:textarea class="span9 control-largeCommon" path="updateComment" maxlength="500" style="resize:none;height:140px"/>
									<br/>
									<sf:errors path="updateComment" class="sf-error"/>
								</c:when>
								<c:otherwise>
									<sf:textarea class="span9 control-largeCommon" path="updateComment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								是否需要询价
							</th>
							<td colspan="3">
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:radiobutton path="inquiryNeeded" value="1" disabled="true"/>需要询价
									<sf:radiobutton style="margin-left: 10px" path="inquiryNeeded" value="0" disabled="true"/>历史数据不需要询价
									<sf:hidden path="inquiryNeeded"/>
								</c:when>
								<c:otherwise>
									<sf:radiobutton path="inquiryNeeded" value="1"/>需要询价
									<sf:radiobutton style="margin-left: 10px" path="inquiryNeeded" value="0"/>历史数据不需要询价
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<c:choose>
				<c:when test="${tradeOrder.isConfirmed}">
				<div class="form-actions ta-c">
					询单号: <sf:input class="span3 control-largeCommon" style="margin-top:10px;margin-right:5px" placeholder="请填写14位询单编号" path="inquiryId" maxlength="14" readonly="true"/>
				</div>
				</c:when>
				<c:otherwise>
				<div class="form-actions ta-c">
					<a class="btn btn-info" id="productionRef" funcId="tradeOrder">
						<b>
							添加产品
						</b>
					</a>
				</div>
				<div class="form-actions ta-c">
					<sf:input class="span3 control-largeCommon" style="margin-top:10px;margin-right:5px" placeholder="请填写14位询单编号" path="inquiryId" maxlength="14"/>
					<a id="calculate" class="btn w100" href="javascript:void(0)">
						<b>
							引入询单价格
						</b>
					</a>
				</div>
				</c:otherwise>
			</c:choose>
			
			<div class="form-actions ta-c">
				<a style="" class="btn btn-primary w100" id="updateSave">
					<b>
						保存订单
					</b>
				</a>
				<a style="margin-left:30px;" class="btn btn-primary w100" id="goBack">
					<b>
						返回上一页
					</b>
				</a>
			</div>
		</div>
	</div>
	<c:if test="${tradeOrder.tradeOrderDtlList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							产品信息
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div id="result" class="datagrid">
					<table class="table table-striped  table-bordered" id="tab1">
						<tbody id="productionList">
							<c:forEach var="item" items="${tradeOrder.tradeOrderDtlList}" varStatus="status">
								<tr>
									<th style="width:40px;">
										删除
									</th>
									<th style="">
										产品
									</th>
									<th style="width:80px">
										数量
									</th>
									<th style="width:120px">
										美金单价
									</th>
									<th style="width:180px">
										小计
									</th>
									<th style="width:150px">
										备注
									</th>
								</tr>
								<tr>
									<td class="va-m ta-c" rowspan="6">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed 
											|| String.tradeOrder.tradeOrderDtlList.get(status.index).getQuantitySent() != null 
											|| tradeOrder.tradeOrderDtlList.get(status.index).getCountComplaintDtl() != '0'}">
										</c:when>
										<c:otherwise>
											<a class="btn btn-info w30" name="deleteProduction"
												productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}"
												productionIdOrignal="${tradeOrder.tradeOrderDtlList[status.index].productionId}"
												versionNoOrignal="${tradeOrder.tradeOrderDtlList[status.index].versionNo}">
												<b>
													删
												</b>
											</a>
										</c:otherwise>
										</c:choose>
										<sf:hidden path="tradeOrderDtlList[${status.index}].tradeOrderId"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].productionId"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].versionNo"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].quantitySent"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].countComplaintDtl"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].descriptionE"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].lastComplaintDate"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].lastDrawUpdateDate"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].amount" 
											productionId="amount_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											vendorId="amount_tradeOrder"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].productionUpdateTime"/>
									</td>
									<td rowspan="6">
										<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
										<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
										${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/>
										<br/>
										<font style="color:blue">已发送：<br/>
											<c:choose>
											<c:when test="${tradeOrder.tradeOrderDtlList[status.index].quantitySent != null}">
												${tradeOrder.tradeOrderDtlList[status.index].quantitySent}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
											</c:choose>
										</font>
										<c:if test="${item.lastDrawUpdateDate != null}">
											<br/><font style="color: red;"><b>上次修改图纸日期：${item.lastDrawUpdateDate}</b></font>
										</c:if>
										<c:if test="${item.lastComplaintDate != null}">
											<br/><font style="color: red;"><b>上次客户投诉日期：${item.lastComplaintDate}</b></font>
										</c:if>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].quantity" maxlength="7" 
											style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											id="Q_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 											
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											readonly="true"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].quantity" maxlength="7" 
											style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											id="Q_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											onfocus="removeCommas(this)" onblur="quantityOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].quantity" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].unitPrice" maxlength="11" 
											style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											id="U_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											readonly="true"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].unitPrice" maxlength="11" 
											style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											id="U_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}" 
											onfocus="removeCommas(this)" onblur="priceOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].unitPrice" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td rowspan="6">
										<sf:label id="A_${tradeOrder.tradeOrderDtlList[status.index].productionId}${tradeOrder.tradeOrderDtlList[status.index].versionNo}"
											path="tradeOrderDtlList[${status.index}].amount">
											${item.amount}
										</sf:label>
									</td>
									<td rowspan="10">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:textarea class="control-largeCommon" path="tradeOrderDtlList[${status.index}].comment" maxlength="320" style="resize:none;width:150px;height:400px" readonly="true"/>
										</c:when>
										<c:otherwise>
											<sf:textarea class="control-largeCommon" path="tradeOrderDtlList[${status.index}].comment" maxlength="320" style="resize:none;width:150px;height:400px"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>
										分批发货数
									</th>
									<th>
										分批发货日期
									</th>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity1" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity1" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity1" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity1" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate1" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate1" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate1" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate1" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity2" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity2" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity2" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity2" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate2" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate2" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate2" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate2" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity3" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity3" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity3" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity3" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate3" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate3" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate3" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate3" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity4" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity4" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity4" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity4" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate4" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate4" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate4" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate4" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										费用1
									</td>
									<td colspan="3">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle1" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle1" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle1" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle1" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee1" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee1" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee1" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder"
											onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee1" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										费用2
									</td>
									<td colspan="3">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle2" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle2" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle2" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle2" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee2" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee2" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee2" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" 
											onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee2" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										费用3
									</td>
									<td colspan="3">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle3" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle3" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle3" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle3" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee3" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee3" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee3" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" 
											onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee3" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										费用4
									</td>
									<td colspan="3">
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle4" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle4" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle4" 
												maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle4" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
										<c:when test="${tradeOrder.isConfirmed}">
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee4" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee4" class="sf-error"/>
										</c:when>
										<c:otherwise>
											<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee4" maxlength="11" 
											style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder" fee="fee_tradeOrder" 
											onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].fee4" class="sf-error"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
					    </tbody>
					    <tbody>
				    		<tr>
								<th class="va-m ta-c" id="amountTtltradeOrder" colspan="4">
									合计金额
									<sf:hidden path="amountTtl" tradeOrderHidden="amountTtl_tradeOrder"/>
								</th>
								<td id="amountTtlTD_tradeOrder" class="" colspan="2">
									${tradeOrder.amountTtl}
								</td>
							</tr>
					    </tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	</div>
	<jsp:include page="tradeOrderUpdatePlus.jsp"></jsp:include>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
