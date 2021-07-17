<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/tradeOrder.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="tradeOrder">
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
								<sf:select class="w200" path="status" items="${tradeOrder.statusInput}" disabled="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								P.O
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="po" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="po" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Contract No.
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="contractNo" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="contractNo" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单创建日
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tradeOrderCreateDate" maxlength="10" readonly="true"/>
								<br/>
								<sf:errors path="tradeOrderCreateDate" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单确定日
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tradeOrderConfirmDate" maxlength="10" readonly="true"/>
								<br/>
								<sf:errors path="tradeOrderConfirmDate" class="sf-error"/>
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
								<br/>
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
								<sf:select class="w200" path="sendMode" items="${tradeOrder.sendModeInput}" 
									vendorId="sendMode"
									sendMode="tradeOrder" onchange="sendModeChange(this, true)" disabled="true"/>
								<sf:hidden path="sendMode"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Shipment
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="shipment" maxlength="10" readonly="true"/>
								<br/>
								<sf:errors path="shipment" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Exchange Rate
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="exRate" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="exRate" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Tax Rebate Rate
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="etrRate" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="etrRate" class="sf-error"/>
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
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单确定后变更备注
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="updateComment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								<br/>
								<sf:errors path="updateComment" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								是否需要询价
							</th>
							<td colspan="3">
								<sf:radiobutton path="inquiryNeeded" value="1" disabled="true"/>需要询价
								<sf:radiobutton style="margin-left: 10px" path="inquiryNeeded" value="0" disabled="true"/>历史数据不需要询价
								<sf:hidden path="inquiryNeeded"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				询单号: <sf:input class="span3 control-largeCommon" style="margin-top:10px;margin-right:5px" placeholder="请填写14位询单编号" path="inquiryId" maxlength="14" readonly="true"/>
			</div>
			<div class="form-actions ta-c">
				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '5'}">
					<a class="btn w100" style="margin-right:30px;" id="download">
						<b>
							下载订单
						</b>
					</a>
				</c:if>
				<a class="btn btn-primary w100" id="goBack">
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
										<sf:hidden path="tradeOrderDtlList[${status.index}].tradeOrderId"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].productionId"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].versionNo"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].quantitySent"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].descriptionE"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].lastComplaintDate"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].lastDrawUpdateDate"/>
										<sf:hidden path="tradeOrderDtlList[${status.index}].amount"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].quantity" maxlength="7" 
											style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}" 
											readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].quantity" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].unitPrice" maxlength="11" 
											style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
											vendorId="tradeOrder"
											productionId="${tradeOrder.tradeOrderDtlList[status.index].productionId}"
											readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].unitPrice" class="sf-error"/>
									</td>
									<td rowspan="6">
										${item.amount!=null?item.amount:'&nbsp;'}
									</td>
									<td rowspan="10">
										<sf:textarea class="control-largeCommon" path="tradeOrderDtlList[${status.index}].comment" maxlength="320" style="resize:none;width:150px;height:400px" readonly="true"/>
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
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity1" 
											kind="sendDateAndQuantity_tradeOrder"
											maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity1" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate1" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate1" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity2" 
											kind="sendDateAndQuantity_tradeOrder"
											maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity2" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate2" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate2" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity3" 
											kind="sendDateAndQuantity_tradeOrder"
											maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity3" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate3" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate3" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td style="border-left: 1px solid #dddddd;">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendQuantity4" 
											kind="sendDateAndQuantity_tradeOrder"
											maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendQuantity4" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].sendDate4" 
												kind="sendDateAndQuantity_tradeOrder"
												maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											<sf:errors path="tradeOrderDtlList[${status.index}].sendDate4" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td>
										费用1
									</td>
									<td colspan="3">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle1" 
											maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle1" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee1" maxlength="11" 
										style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
										vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].fee1" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td>
										费用2
									</td>
									<td colspan="3">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle2" 
											maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle2" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee2" maxlength="11" 
										style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
										vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].fee2" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td>
										费用3
									</td>
									<td colspan="3">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle3" 
											maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle3" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee3" maxlength="11" 
										style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
										vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].fee3" class="sf-error"/>
									</td>
								</tr>
								<tr>
									<td>
										费用4
									</td>
									<td colspan="3">
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].feeTitle4" 
											maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].feeTitle4" class="sf-error"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="tradeOrderDtlList[${status.index}].fee4" maxlength="11" 
										style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
										vendorId="tradeOrder" fee="fee_tradeOrder" readonly="true"/>
										<sf:errors path="tradeOrderDtlList[${status.index}].fee4" class="sf-error"/>
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
	
	<c:forEach var="item" items="${tradeOrder.interTradeOrderList}" varStatus="status">
	<div id="div_${tradeOrder.interTradeOrderList[status.index].vendorId}" type="tradeOrderDiv" style="display: none">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3 va-m">
						<h2>
							内贸合同信息 ${tradeOrder.interTradeOrderList[status.index].vendorId}
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
									内贸合同编号
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderId" maxlength="30" readonly="true"/>
									<sf:hidden path="interTradeOrderList[${status.index}].tradeOrderId"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderId" class="sf-error"/>
								</td>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									合同创建日
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeCreateDate" maxlength="10" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].interTradeCreateDate" class="sf-error"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									出货方式
								</th>
								<td>
									<sf:select class="w200" path="interTradeOrderList[${status.index}].sendMode" items="${tradeOrder.interTradeOrderList[status.index].sendModeInput}"
										vendorId="sendMode"
										sendMode="${tradeOrder.interTradeOrderList[status.index].vendorId}" onchange="sendModeChange(this, true)" disabled="true"/>
									<sf:hidden path="interTradeOrderList[${status.index}].sendMode"/>
								</td>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									交货日
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].recieveDate" maxlength="10" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].recieveDate" class="sf-error"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									合同签订日
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeConfirmDate" maxlength="10" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].interTradeConfirmDate" class="sf-error"/>
								</td>								
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									预付金额
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePayment" maxlength="30" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePayment" class="sf-error"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									预付日期
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDate" maxlength="10" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePaymentDate" class="sf-error"/>
								</td>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									预付折扣（%）
								</th>
								<td>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDiscountRate" maxlength="30"
										id="APD_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePaymentDiscountRate" class="sf-error"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									供应商
								</th>
								<td colspan="3">
									<sf:input class="span2 control-largeCommon" path="interTradeOrderList[${status.index}].vendorId" maxlength="20" readonly="true"/> 
									<font>--</font>
									<sf:input class="span5 control-largeCommon" path="interTradeOrderList[${status.index}].vendorName" maxlength="20" readonly="true"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									供应商全称
								</th>
								<td colspan="3">
									<sf:textarea class="span9 control-largeCommon" path="interTradeOrderList[${status.index}].vendorFullName" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									供应商地址
								</th>
								<td colspan="3">
									<sf:textarea class="span9 control-largeCommon" path="interTradeOrderList[${status.index}].location" maxlength="500" style="resize:none;height:140px" readonly="true"/>
								</td>
							</tr>
							<tr>
								<th class="iobox-label-area additional va-m" style="width: 140px;">
									备注
								</th>
								<td colspan="3">
									<sf:textarea class="span9 control-largeCommon" path="interTradeOrderList[${status.index}].comment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].comment" class="sf-error"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="titledbox-body titledbox-body-extended">
					<div id="result" class="datagrid">
						<table class="table table-striped  table-bordered" id="tab1">
							<c:forEach var="item" items="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList}" varStatus="statusDtl">
							<tr>
								<th style="">
									供应商产品
								</th>
								<th style="width:80px">
									体积(cbm)
								</th>
								<th style="width:60px">
									内箱
								</th>
								<th style="width:75px">
									总体积(cbm)
								</th>
								<th style="width:40px">
									箱数
								</th>
								<th style="width:80px">
									数量
								</th>
								<th style="width:120px">
									人民币单价
								</th>
								<th style="width:150px">
									小计
								</th>
							</tr>
							<tbody id="productionList_${tradeOrder.interTradeOrderList[status.index].vendorId}">
								
									<tbody>
										<tr>
											<td rowspan="6">
												<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
												<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
												${item.descriptionC!=null?item.descriptionC:'&nbsp;'}
												<br/>
												<br/>
												<font style="color:blue">已发送：<br/>
													<c:choose>
													<c:when test="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].quantitySent != null}">
														${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].quantitySent}
													</c:when>
													<c:otherwise>
														无
													</c:otherwise>
													</c:choose>
												</font>
												
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].interTradeOrderId"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].productionId"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].versionNo"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].quantitySent"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].productionIdVendor"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].descriptionC"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volumeTtl"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeightTtl"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeightTtl"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].boxAmount"/>
												<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].amount"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" 
													id="V_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
													vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
													productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
													maxlength="9" style="width:90px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" class="sf-error"/>	
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" 
													id="IN_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
													vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
													productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
													maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" class="sf-error"/>	
											</td>
											<td>
												${item.volumeTtl!=null?item.volumeTtl:'&nbsp;'}
											</td>
											<td rowspan="6">
												${item.boxAmount!=null?item.boxAmount:'&nbsp;'}
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].quantity" 
													maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].unitPrice" 
													maxlength="9" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</td>
											<td rowspan="6">
												${item.amount!=null?item.amount:'&nbsp;'}
											</td>
										</tr>
										<tr>
											<th style="border-left: 1px solid #dddddd;">
												毛重(kg)
											</th>
											<th>
												外箱
											</th>
											<th>
												总毛重(kg)
											</th>
											<th>
												分批发货数
											</th>
											<th>
												分批发货日期
											</th>
										</tr>
										<tr>
											<td style="border-left: 1px solid #dddddd;">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeight" 
													id="GW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
													vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
													productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
													maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</td>
											<td rowspan="4">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].outside" 
													id="OUT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
													vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
													productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
													maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</td>
											<td>
												${item.grossWeightTtl!=null?item.grossWeightTtl:'&nbsp;'}
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" 
														kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
														maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<th style="border-left: 1px solid #dddddd;">
												净重(kg)
											</th>
											<th>
												总净重(kg)
											</th>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" 
														kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
														maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td rowspan="2" style="border-left: 1px solid #dddddd;">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeight" 
													id="NW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
													vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
													productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
													maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</td>
											<td rowspan="2">
												${item.netWeightTtl!=null?item.netWeightTtl:'&nbsp;'}
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" 
														kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
														maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td style="border-left: 1px solid #dddddd;">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" 
														kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
														maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
													<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td>
												费用1
											</td>
											<td colspan="6">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td>
												费用2
											</td>
											<td colspan="6">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td>
												费用3
											</td>
											<td colspan="6">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" class="sf-error"/>
											</td>
										</tr>
										<tr>
											<td>
												费用4
											</td>
											<td colspan="6">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" class="sf-error"/>
											</td>
											<td>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" class="sf-error"/>
											</td>
										</tr>
									</tbody>
								</c:forEach>
						    </tbody>
						    <tbody>
					    		<tr>
									<th class="va-m ta-c" colspan="7">
										合计金额
										<sf:hidden path="interTradeOrderList[${status.index}].amountTtl"
											interTradeOrderHidden="amountTtl_${tradeOrder.interTradeOrderList[status.index].vendorId}"/>
									</th>
									<td id="amountTtlTD_${tradeOrder.interTradeOrderList[status.index].vendorId}" class="">
										${tradeOrder.interTradeOrderList[status.index].amountTtl}
									</td>
								</tr>
						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:forEach>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
