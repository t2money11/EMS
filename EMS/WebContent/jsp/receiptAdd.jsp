<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/receipt.js"></script>
<script src="../jsp/js/tradeOrderPopCall.js"></script>
<script src="../jsp/js/complaintPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script>
$(document).ready(function(){
	
	receiptModeChange();
	clearanceModeChange(false);
});
$(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
</script>

<sf:form id="form1" method="post" action="/EMS/receipt/add" modelAttribute="receipt">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>
<input type="hidden" name="pageMode" value="add"/>
<input type="hidden" id="productions" name="productions"/>
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
						发货单信息
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
								发货单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="receiptId" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="receiptId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="receiptDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="receiptDate" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								发票号码
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="receiptNo" maxlength="30"/>
								<br/>
								<sf:errors path="receiptNo" class="sf-error"/>
							</td>
							<td colspan="2">
								<font style="color:red;">
									需要报关且不手动输入的话，系统会根据规则自动生成发票号码<br/>
									不需要报关的话，必须手动输入发票号码
								</font>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								关联报关发票号码
							</th>
							<td colspan="3">
								<sf:input class="span4 control-largeCommon" path="relateReceiptNo" maxlength="30"/>
								<br/>
								<sf:errors path="relateReceiptNo" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商结算日期
							</th>
							<td colspan="3">
								<sf:input class="span4 control-largeCommon" path="balanceDate" maxlength="30" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="balanceDate" class="sf-error"/>
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
								<c:if test="${receipt.receiptDtlList.size()==0 && receipt.receiptDtl4CList.size()==0}">
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
								<br/>
								<sf:hidden path="customerUpdateTime"/>
								<sf:errors path="customerId" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户国家（城市）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="country" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户地址
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="location" maxlength="30" readonly="true"/>
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
								<sf:input class="span4 control-largeCommon" path="fax" maxlength="30" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								成交方式
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="freightTerms" readonly="true"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								唛头
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="mark" maxlength="30"/>
								<br/>
								<sf:errors path="mark" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								船期（ETD）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="ETD" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="ETD" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预期到达（ETA）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="ETA" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="ETA" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								应收汇金额
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="remint" maxlength="30"/>
								<br/>
								<sf:errors path="remint" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预计货好日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="goodTime" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="goodTime" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								预计进账日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="planPostingDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="planPostingDate" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								实际进账日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="postingDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="postingDate" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								电放日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="telexRelease" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="telexRelease" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								运送方式
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="transportation" maxlength="30"/>
								<br/>
								<sf:errors path="transportation" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								结账编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="checkoutNumber" maxlength="30"/>
								<br/>
								<sf:errors path="checkoutNumber" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								利润
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="profit" maxlength="30"/>
								<br/>
								<sf:errors path="profit" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								进账方向
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="postingTo" maxlength="30"/>
								<br/>
								<sf:errors path="postingTo" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								实际进账金额
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="postingAmount" maxlength="30"/>
								<br/>
								<sf:errors path="postingAmount" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								备注
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="500" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="comment" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								报关与否
							</th>
							<td colspan="3">
								<sf:radiobutton path="receiptMode" value="1" checked="true" onchange="receiptModeChange()"/>需要报关
								<sf:radiobutton style="margin-left: 10px" path="receiptMode" value="0" onchange="receiptModeChange()"/>不需要报关
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-info w150" id="traderOrderRef" href="javascript:void(0)">
					<b>
						添加订单产品
					</b>
				</a>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-info w150" id="complaintRef" href="javascript:void(0)">
					<b>
						添加投诉单产品
					</b>
				</a>
			</div>
			<div class="form-actions ta-c">
				<a id="calculate" class="btn w150" href="javascript:void(0)">
					<b>
						计算
					</b>
				</a>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" href="javascript:void(0)" id="addSave">
					<b>
						保存发货单
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
	
	<c:if test="${receipt.receiptDtlList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							订单产品信息
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div id="result" class="datagrid">
					<table class="table table-striped  table-bordered" id="tab1">
						<tr>
							<th style="width:40px;">
							</th>
							<th style="">
								产品
							</th>
							<th style="width:80px">
								供应商
							</th>
							<th style="width:80px;">
								未收发数
							</th>
							<th style="width:80px;">
								数量
							</th>
							<th style="width:65px;">
								RMB单价
							</th>
							<th style="width:85px;">
								RMB小计
							</th>
							<th style="width:65px;">
								美金单价
							</th>
							<th style="width:85px;">
								美金小计
							</th>
						</tr>
						<c:forEach var="item" items="${receipt.receiptDtlList}" varStatus="status">
							
							<c:if test="${item.productionId!='0' && item.feeNum=='0'}">
								<tbody>
									<tr>
										<td class="va-m ta-c">
											<a class="btn btn-info w30" name="deleteProduction"
												tradeOrderId="${receipt.receiptDtlList[status.index].tradeOrderId}"
												productionId="${receipt.receiptDtlList[status.index].productionId}"
												versionNo="${receipt.receiptDtlList[status.index].versionNo}"
												feeNum="${receipt.receiptDtlList[status.index].feeNum}">
												<b>
													删
												</b>
											</a>
										</td>
										<td>
											<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
											<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
											${item.descriptionE!=null?item.descriptionE:'&nbsp;'}
											<sf:hidden path="receiptDtlList[${status.index}].tradeOrderId"/>
											<sf:hidden path="receiptDtlList[${status.index}].contractNo"/>
											<sf:hidden path="receiptDtlList[${status.index}].po"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionId"/>
											<sf:hidden path="receiptDtlList[${status.index}].versionNo"/>
											<sf:hidden path="receiptDtlList[${status.index}].feeNum"/>
											<sf:hidden path="receiptDtlList[${status.index}].descriptionE"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionEName4Export"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionCName4Export"/>
											<sf:hidden path="receiptDtlList[${status.index}].shortLocation"/>
											<sf:hidden path="receiptDtlList[${status.index}].volume"/>
											<sf:hidden path="receiptDtlList[${status.index}].grossWeight"/>
											<sf:hidden path="receiptDtlList[${status.index}].netWeight"/>
											<sf:hidden path="receiptDtlList[${status.index}].volumeTtl"/>
											<sf:hidden path="receiptDtlList[${status.index}].grossWeightTtl"/>
											<sf:hidden path="receiptDtlList[${status.index}].netWeightTtl"/>
											<sf:hidden path="receiptDtlList[${status.index}].inside"/>
											<sf:hidden path="receiptDtlList[${status.index}].outside"/>
											<sf:hidden path="receiptDtlList[${status.index}].tradeOrderUpdateTime"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionUpdateTime"/>
											<sf:hidden path="receiptDtlList[${status.index}].tradeOrderCreateDate"/>
										</td>
										<td>
											${item.vendorId!=null?item.vendorId:'&nbsp;'}<br/>
											${item.vendorName!=null?item.vendorName:'&nbsp;'}
											<sf:hidden path="receiptDtlList[${status.index}].vendorId"/>
											<sf:hidden path="receiptDtlList[${status.index}].vendorName"/>
										</td>
										<td>
											供应商：${item.iQuantityNotSent!=null?item.iQuantityNotSent:'&nbsp;'}<br/>
											客户：${item.tQuantityNotSent!=null?item.tQuantityNotSent:'&nbsp;'}
											<sf:hidden path="receiptDtlList[${status.index}].iQuantityNotSent"/>
											<sf:hidden path="receiptDtlList[${status.index}].tQuantityNotSent"/>
										</td>
										<td>
											<sf:input class="control-largeCommon" path="receiptDtlList[${status.index}].quantity" maxlength="7" style="width:80px"/>
											<br/><sf:errors path="receiptDtlList[${status.index}].quantity" class="sf-error" style="margin-top:-5px"/>
										</td>
										<td>
											${item.iUnitPrice!=null?item.iUnitPrice:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].iUnitPrice"/>
										</td>
										<td>
											${item.iAmount!=null?item.iAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].iAmount"/>
										</td>
										<td>
											${item.tUnitPrice!=null?item.tUnitPrice:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].tUnitPrice"/>
										</td>
										<td>
											${item.tAmount!=null?item.tAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].tAmount"/>
										</td>
									</tr>
						    	</tbody>
					    	</c:if>
					    	<c:if test="${item.productionId!='0' && item.feeNum!='0'}">
								<tbody>
									<tr>
										<td class="va-m ta-c">
											<a class="btn btn-info w30" name="deleteProduction"
												tradeOrderId="${receipt.receiptDtlList[status.index].tradeOrderId}"
												productionId="${receipt.receiptDtlList[status.index].productionId}"
												versionNo="${receipt.receiptDtlList[status.index].versionNo}"
												feeNum="${receipt.receiptDtlList[status.index].feeNum}">
												<b>
													删
												</b>
											</a>
										</td>
										<td>
											<font style="color:blue">${item.descriptionE!=null?item.descriptionE:'&nbsp;'}</font><br/>
											<sf:hidden path="receiptDtlList[${status.index}].tradeOrderId"/>
											<sf:hidden path="receiptDtlList[${status.index}].contractNo"/>
											<sf:hidden path="receiptDtlList[${status.index}].po"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionId"/>
											<sf:hidden path="receiptDtlList[${status.index}].versionNo"/>
											<sf:hidden path="receiptDtlList[${status.index}].feeNum"/>
											<sf:hidden path="receiptDtlList[${status.index}].descriptionE"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionEName4Export"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionCName4Export"/>
										</td>
										<td>
											${item.vendorId!=null?item.vendorId:'&nbsp;'}<br/>
											${item.vendorName!=null?item.vendorName:'&nbsp;'}
											<sf:hidden path="receiptDtlList[${status.index}].vendorId"/>
											<sf:hidden path="receiptDtlList[${status.index}].vendorName"/>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
											${item.iAmount!=null?item.iAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].iAmount"/>
										</td>
										<td>
										</td>
										<td>
											${item.tAmount!=null?item.tAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtlList[${status.index}].tAmount"/>
											<sf:hidden path="receiptDtlList[${status.index}].cAmount"/>
										</td>
									</tr>
						    	</tbody>
					    	</c:if>
					    	<c:if test="${item.productionId=='0'}">
					    		<tbody id="productionList">
									<tr>
										<td class="va-m ta-c" rowspan="6">
											&nbsp;
										</td>
										<td colspan="8">
											Contract No.： <font style="color: red;"><b>${item.contractNo!=null?item.contractNo:'&nbsp;'}</b></font>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P.O : <font style="color: red;"><b>${item.po!=null?item.po:'&nbsp;'}</b></font>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单编号 : <font style="color: red;"><b>${item.tradeOrderId!=null?item.tradeOrderId:'&nbsp;'}</b></font>
											<sf:hidden path="receiptDtlList[${status.index}].tradeOrderId"/>
											<sf:hidden path="receiptDtlList[${status.index}].contractNo"/>
											<sf:hidden path="receiptDtlList[${status.index}].po"/>
											<sf:hidden path="receiptDtlList[${status.index}].productionId"/>
										</td>
									</tr>
						    	</tbody>
					    	</c:if>
						</c:forEach>
						<tbody>
							<tr>
								<th class="va-m ta-c" colspan="4">
									合计
								</th>
								<td>
									${receipt.quantityTtl4T!=''?receipt.quantityTtl4T:'&nbsp;'}
									<sf:hidden path="quantityTtl4T"/>
								</td>
								<td>
								</td>
								<td>
									${receipt.iAmountTtl4T!=''?receipt.iAmountTtl4T:'&nbsp;'}
									<sf:hidden path="iAmountTtl4T"/>
								</td>
								<td>
								</td>
								<td>
									${receipt.tAmountTtl4T!=''?receipt.tAmountTtl4T:'&nbsp;'}
									<sf:hidden path="tAmountTtl4T"/>
								</td>
							</tr>
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${receipt.receiptDtl4CList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							投诉补货产品信息
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div id="result" class="datagrid">
					<table class="table table-striped  table-bordered" id="tab1">
						<tr>
							<th style="width:40px;">
							</th>
							<th style="">
								产品
							</th>
							<th style="width:80px">
								供应商
							</th>
							<th style="width:80px;">
								未补货数量
							</th>
							<th style="width:80px;">
								数量
							</th>
							<th style="width:65px;">
								RMB单价
							</th>
							<th style="width:85px;">
								RMB小计
							</th>
							<th style="width:65px;">
								美金单价
							</th>
							<th style="width:85px;">
								美金小计
							</th>
						</tr>
						<c:forEach var="item" items="${receipt.receiptDtl4CList}" varStatus="status">
							<c:if test="${item.productionId!='0'}">
								<tbody>
									<tr>
										<td class="va-m ta-c">
											<a class="btn btn-info w30" name="deleteProduction4C"
												complaintId="${receipt.receiptDtl4CList[status.index].complaintId}"
												tradeOrderId="${receipt.receiptDtl4CList[status.index].tradeOrderId}"
												productionId="${receipt.receiptDtl4CList[status.index].productionId}"
												versionNo="${receipt.receiptDtl4CList[status.index].versionNo}">
												<b>
													删
												</b>
											</a>
										</td>
										<td>
											<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
											<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
											${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/><br/>
											<font style="color:red">投诉单编号: ${item.complaintId!=null?item.complaintId:'&nbsp;'}</font><br/>
											<sf:hidden path="receiptDtl4CList[${status.index}].complaintId"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].tradeOrderId"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].contractNo"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].po"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].productionId"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].versionNo"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].descriptionE"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].productionEName4Export"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].productionCName4Export"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].shortLocation"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].volume"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].grossWeight"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].netWeight"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].volumeTtl"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].grossWeightTtl"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].netWeightTtl"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].inside"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].outside"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].boxAmount"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].complaintUpdateTime"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].tradeOrderUpdateTime"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].productionUpdateTime"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].tradeOrderCreateDate"/>
										</td>
										<td>
											${item.vendorId!=null?item.vendorId:'&nbsp;'}<br/>
											${item.vendorName!=null?item.vendorName:'&nbsp;'}
											<sf:hidden path="receiptDtl4CList[${status.index}].vendorId"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].vendorName"/>
										</td>
										<td>
											${item.tQuantityNotSent!=null?item.tQuantityNotSent:'&nbsp;'}
											<sf:hidden path="receiptDtl4CList[${status.index}].tQuantityNotSent"/>
										</td>
										<td>
											<sf:input class="control-largeCommon" path="receiptDtl4CList[${status.index}].quantity" maxlength="7" style="width:80px"/>
											<br/><sf:errors path="receiptDtl4CList[${status.index}].quantity" class="sf-error" style="margin-top:-5px"/>
										</td>
										<td>
											${item.iUnitPrice!=null?item.iUnitPrice:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtl4CList[${status.index}].iUnitPrice"/>
										</td>
										<td>
											${item.iAmount!=null?item.iAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtl4CList[${status.index}].iAmount"/>
										</td>
										<td>
											${item.tUnitPrice!=null?item.tUnitPrice:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtl4CList[${status.index}].tUnitPrice"/>
										</td>
										<td>
											${item.tAmount!=null?item.tAmount:'&nbsp;'}<br/>
											<sf:hidden path="receiptDtl4CList[${status.index}].tAmount"/>
										</td>
									</tr>
						    	</tbody>
						    </c:if>
						    <c:if test="${item.productionId=='0'}">
					    		<tbody id="productionList">
									<tr>
										<td class="va-m ta-c" rowspan="6">
											&nbsp;
										</td>
										<td colspan="8">
											Contract No.： <font style="color: red;"><b>${item.contractNo!=null?item.contractNo:'&nbsp;'}</b></font>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P.O : <font style="color: red;"><b>${item.po!=null?item.po:'&nbsp;'}</b></font>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单编号 : <font style="color: red;"><b>${item.tradeOrderId!=null?item.tradeOrderId:'&nbsp;'}</b></font>
											<sf:hidden path="receiptDtl4CList[${status.index}].tradeOrderId"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].contractNo"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].po"/>
											<sf:hidden path="receiptDtl4CList[${status.index}].productionId"/>
										</td>
									</tr>
						    	</tbody>
					    	</c:if>
						</c:forEach>
						<tbody>
							<tr>
								<th class="va-m ta-c" colspan="4">
									合计
								</th>
								<td>
									${receipt.quantityTtl4C!=''?receipt.quantityTtl4C:'&nbsp;'}
									<sf:hidden path="quantityTtl4C"/>
								</td>
								<td>
								</td>
								<td>
									${receipt.iAmountTtl4C!=''?receipt.iAmountTtl4C:'&nbsp;'}
									<sf:hidden path="iAmountTtl4C"/>
								</td>
								<td>
								</td>
								<td>
									${receipt.tAmountTtl4C!=''?receipt.tAmountTtl4C:'&nbsp;'}
									<sf:hidden path="tAmountTtl4C"/>
								</td>
							</tr>
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${receipt.receiptDtlList.size()>0 || receipt.receiptDtl4CList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							金额合计
						</h2>
					</div>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div id="result" class="datagrid">
					<table class="table table-striped  table-bordered" id="tab1">
						<tbody>
							<tr>
								<th>
									总数量
								</th>
								<th>
									总RMB金额
								</th>
								<th>
									总美金金额
								</th>
							</tr>
				    	</tbody>
						<tbody>
							<tr>
								<td>
									${receipt.quantityTtl!=''?receipt.quantityTtl:'&nbsp;'}
									<sf:hidden path="quantityTtl"/>
								</td>
								<td>
									${receipt.iAmountTtl!=''?receipt.iAmountTtl:'&nbsp;'}
									<sf:hidden path="iAmountTtl"/>
								</td>
								<td>
									${receipt.tAmountTtl!=''?receipt.tAmountTtl:'&nbsp;'}
									<sf:hidden path="tAmountTtl"/>
								</td>
							</tr>
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${receipt.clearanceDtlList.size()>0}">
		<div id="clearanceDtlDiv" class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							报关信息
						</h2>
					</div>
					<sf:radiobutton path="clearanceMode" value="1" checked="true" onchange="clearanceModeChange(true)"/><font style="font-size: 13">从报关总价计算</font>
					<sf:radiobutton style="margin-left: 10px" path="clearanceMode" value="2" onchange="clearanceModeChange(true)"/><font style="font-size: 13">从报关小计计算</font>
				</div>
			</div>
			<div class="titledbox-body titledbox-body-extended">
				<div id="result" class="datagrid">
					<table class="table table-striped  table-bordered" id="tab1">
						<tr>
							<th style="width:120px">
								产品名（报关用英文）
							</th>
							<th style="width:80px;">
								货源地
							</th>
							<th style="width:50px">
								数量
							</th>
							<th style="width:80px;">
								报关单价
							</th>
							<th style="width:90px;">
								RMB总价
							</th>
							<th style="width:120px;">
								报关总价
							</th>
							<th style="width:45px;">
								箱数
							</th>
							<th style="width:">
								毛重
							</th>
							<th style="width:">
								净重
							</th>
							<th style="width:">
								体积
							</th>
						</tr>
						<c:forEach var="item" items="${receipt.clearanceDtlList}" varStatus="status">
							<tbody>
								<tr>
									<td>
										${item.productionEName4Export!=null?item.productionEName4Export:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].productionEName4Export"/>
										<sf:hidden path="clearanceDtlList[${status.index}].productionCName4Export"/>
										<sf:hidden path="clearanceDtlList[${status.index}].shortLocation"/>
									</td>
									<td>
										${item.shortLocation!=null?item.shortLocation:'&nbsp;'}<br/>
									</td>
									<td>
										${item.quantity!=null?item.quantity:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].quantity"/>
									</td>
									<td>
										<sf:input cduInput="1" class="control-largeCommon" path="clearanceDtlList[${status.index}].unitPrice"
											style="width:90px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="clearanceDtlList[${status.index}].unitPrice" class="sf-error"/>
									</td>
									<td>
										${item.amountRMB!=null?item.amountRMB:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].amountRMB"/>
									</td>
									<td>
										<sf:input cdaInput="1" class="control-largeCommon" path="clearanceDtlList[${status.index}].amount" maxlength="11" 
											style="width:130px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px" readonly="true"/>
										<sf:errors path="clearanceDtlList[${status.index}].amount" class="sf-error"/>
									</td>
									<td>
										${item.boxAmount!=null?item.boxAmount:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].boxAmount"/>
									</td>
									<td>
										${item.grossWeight!=null?item.grossWeight:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].grossWeight"/>
									</td>
									<td>
										${item.netWeight!=null?item.netWeight:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].netWeight"/>
									</td>
									<td>
										${item.volume!=null?item.volume:'&nbsp;'}<br/>
										<sf:hidden path="clearanceDtlList[${status.index}].volume"/>
									</td>
								</tr>
					    	</tbody>
						</c:forEach>
						<tbody>
							<tr>
								<th class="va-m ta-c" colspan="2">
									合计
								</th>
								<td>
									${receipt.quantityCTtl!=''?receipt.quantityCTtl:'&nbsp;'}
									<sf:hidden path="quantityCTtl"/>
								</td>
								<td>
								</td>
								<td>
									${receipt.iAmountTtl!=''?receipt.iAmountTtl:'&nbsp;'}
								</td>
								<td>
									<sf:input class="control-largeCommon" path="amountTtl4Export" maxlength="11" style="width:130px;margin-left:-5px;margin-bottom:-7px;margin-top:-6px"
										id="amountTtl4Export"/>
									<sf:errors path="amountTtl4Export" class="sf-error" style="margin-top:10px"/>
								</td>
								<td>
									${receipt.boxAmountTtl!=''?receipt.boxAmountTtl:'&nbsp;'}
									<sf:hidden path="boxAmountTtl"/>
								</td>
								<td>
									${receipt.grossWeightTtl!=''?receipt.grossWeightTtl:'&nbsp;'}
									<sf:hidden path="grossWeightTtl"/>
								</td>
								<td>
									${receipt.netWeightTtl!=''?receipt.netWeightTtl:'&nbsp;'}
									<sf:hidden path="netWeightTtl"/>
								</td>
								<td>
									${receipt.volumeTtl!=''?receipt.volumeTtl:'&nbsp;'}
									<sf:hidden path="volumeTtl"/>
								</td>
							</tr>
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
