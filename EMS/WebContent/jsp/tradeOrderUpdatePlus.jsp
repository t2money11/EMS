<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
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
								<sf:hidden path="interTradeOrderList[${status.index}].vendorUpdateTime"/>
								<br/>
								<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								合同创建日
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeCreateDate" maxlength="10" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeCreateDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].interTradeCreateDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								出货方式
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:select class="w200" path="interTradeOrderList[${status.index}].sendMode" items="${tradeOrder.interTradeOrderList[status.index].sendModeInput}"
										vendorId="sendMode"
										sendMode="${tradeOrder.interTradeOrderList[status.index].vendorId}" disabled="true"/>
									<sf:hidden path="interTradeOrderList[${status.index}].sendMode"/>
								</c:when>
								<c:otherwise>
									<sf:select class="w200" path="interTradeOrderList[${status.index}].sendMode" items="${tradeOrder.interTradeOrderList[status.index].sendModeInput}"
										vendorId="sendMode"
										sendMode="${tradeOrder.interTradeOrderList[status.index].vendorId}" onchange="sendModeChange(this, true)"/>
									<sf:errors path="interTradeOrderList[${status.index}].sendMode" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								交货日
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].recieveDate" maxlength="10" readonly="true"
										vendorId="recieveDate_${tradeOrder.interTradeOrderList[status.index].vendorId}"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].recieveDate" maxlength="10" onfocus="WdatePicker()"
										vendorId="recieveDate_${tradeOrder.interTradeOrderList[status.index].vendorId}"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].recieveDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								合同签订日
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeConfirmDate" maxlength="10" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].interTradeConfirmDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].interTradeConfirmDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预付金额
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePayment" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePayment" maxlength="30"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePayment" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预付日期
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDate" maxlength="10" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDate" maxlength="10" onfocus="WdatePicker()"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePaymentDate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预付折扣（%）
							</th>
							<td>
								<c:choose>
								<c:when test="${tradeOrder.isConfirmed}">
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDiscountRate" maxlength="30" readonly="true"/>
								</c:when>
								<c:otherwise>
									<sf:input class="span4 control-largeCommon" path="interTradeOrderList[${status.index}].advancePaymentDiscountRate" maxlength="30"
									id="APD_${tradeOrder.interTradeOrderList[status.index].vendorId}" onblur="advancePaymentDiscountRateOnBlur(this, '${tradeOrder.interTradeOrderList[status.index].vendorId}')"/>
									<br/>
									<sf:errors path="interTradeOrderList[${status.index}].advancePaymentDiscountRate" class="sf-error"/>
								</c:otherwise>
								</c:choose>
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
							<c:choose>
							<c:when test="${tradeOrder.isConfirmed}">
								<sf:textarea class="span9 control-largeCommon" path="interTradeOrderList[${status.index}].comment" maxlength="500" style="resize:none;height:140px" readonly="true"/>
							</c:when>
							<c:otherwise>
								<sf:textarea class="span9 control-largeCommon" path="interTradeOrderList[${status.index}].comment" maxlength="500" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="interTradeOrderList[${status.index}].comment" class="sf-error"/>
							</c:otherwise>
							</c:choose>
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
											<font style="color:blue;">已发送：<br/>
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
											<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].boxAmount"
												productionId="boxAmount_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"/>
											<sf:hidden path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].amount" 
												productionId="amountI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="amount_${tradeOrder.interTradeOrderList[status.index].vendorId}"/>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" 
												id="V_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" class="sf-error"/>												
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" 
												id="V_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="volumeOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].volume" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" 
												id="IN_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" class="sf-error"/>												
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" 
												id="IN_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="insideOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].inside" class="sf-error"/>	
											</c:otherwise>
											</c:choose>
											
										</td>
										<td id="VT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}">
											${item.volumeTtl!=null?item.volumeTtl:'&nbsp;'}
										</td>
										<td rowspan="6">
											<sf:label id="BA_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].boxAmount">
												${item.boxAmount}
											</sf:label>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].quantity" 
												id="QI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].quantity" 
												id="QI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												iProductionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="quantityOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].quantity" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].unitPrice" 
												id="UI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].unitPrice" 
												id="UI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="priceOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].unitPrice" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td rowspan="6">
											<sf:label id="AI_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].amount">
												${item.amount}
											</sf:label>
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
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeight" 
												id="GW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeight" class="sf-error"/>												
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeight" 
												id="GW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="grossWeightOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].grossWeight" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td rowspan="4">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].outside" 
												id="OUT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].outside" class="sf-error"/>												
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].outside" 
												id="OUT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="6" style="width:70px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="outsideOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].outside" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td id="GWT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}">
											${item.grossWeightTtl!=null?item.grossWeightTtl:'&nbsp;'}
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" class="sf-error"/>		
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity1" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" class="sf-error"/>		
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate1" class="sf-error"/>
											</c:otherwise>
											</c:choose>
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
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" class="sf-error"/>			
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity2" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" class="sf-error"/>					
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate2" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td rowspan="2" style="border-left: 1px solid #dddddd;">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeight" 
												id="NW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeight" class="sf-error"/>												
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeight" 
												id="NW_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}"
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" 
												productionId="${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}" 
												maxlength="9" style="width:90px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" onfocus="removeCommas(this)" onblur="netWeightOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].netWeight" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td rowspan="2" id="NWT_${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].productionId}${tradeOrder.interTradeOrderList[status.index].interTradeOrderDtlList[statusDtl.index].versionNo}">
											${item.netWeightTtl!=null?item.netWeightTtl:'&nbsp;'}
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" class="sf-error"/>					
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity3" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" class="sf-error"/>					
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate3" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td style="border-left: 1px solid #dddddd;">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" class="sf-error"/>							
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" 
												kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												maxlength="7" style="width:80px;margin-left:-7px;margin-bottom:-7px;margin-top:-6px" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendQuantity4" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" class="sf-error"/>									
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" 
													kind="sendDateAndQuantity_${tradeOrder.interTradeOrderList[status.index].vendorId}"
													maxlength="10" style="width:120px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" onfocus="WdatePicker()" disabled="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].sendDate4" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>
											费用1
										</td>
										<td colspan="6">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle1" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee1" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>
											费用2
										</td>
										<td colspan="6">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle2" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee2" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>
											费用3
										</td>
										<td colspan="6">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle3" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee3" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>
											费用4
										</td>
										<td colspan="6">
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" 
													maxlength="60" style="width:500px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].feeTitle4" class="sf-error"/>
											</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
											<c:when test="${tradeOrder.isConfirmed}">
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}" readonly="true"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" class="sf-error"/>
											</c:when>
											<c:otherwise>
												<sf:input class="control-largeCommon" path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" maxlength="11" 
												style="width:150px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" 
												vendorId="${tradeOrder.interTradeOrderList[status.index].vendorId}" fee="fee_${tradeOrder.interTradeOrderList[status.index].vendorId}"
												onfocus="removeCommas(this)" onblur="feeOnBlur(this)"/>
												<sf:errors path="interTradeOrderList[${status.index}].interTradeOrderDtlList[${statusDtl.index}].fee4" class="sf-error"/>
											</c:otherwise>
											</c:choose>
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

