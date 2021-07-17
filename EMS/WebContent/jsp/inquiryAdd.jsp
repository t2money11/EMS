<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/inquiry.js"></script>
<script src="../jsp/js/productionPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script>
$(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
</script>

<sf:form id="form1" method="post" action="/EMS/inquiry/add" modelAttribute="inquiry">
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
						询单信息
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
								询单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="inquiryId" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="inquiryId" class="sf-error"/>
							</td>
							<th style="width: 140px;">
							</th>
							<td>
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
								<c:if test="${inquiry.inquiryDtlList.size()==0}">
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
								备注
							</th>
							<td colspan="3">
								<sf:textarea class="span9 control-largeCommon" path="comment" maxlength="500" style="resize:none;height:140px"/>
								<br/>
								<sf:errors path="comment" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-info w150" id="productionRef" funcId="inquiry" href="javascript:void(0)">
					<b>
						添加产品
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
				<a class="btn btn-primary w150" id="addSave">
					<b>
						保存询单
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
	
	<c:if test="${inquiry.inquiryDtlList.size()>0}">
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
							<c:forEach var="item" items="${inquiry.inquiryDtlList}" varStatus="status">
								<tr>
									<th style="width:30px;">
									</th>
									<th style="">
										产品
									</th>
									<th style="width:100px">
										上次RMB
									</th>
									<th style="width:120px">
										本次RMB
									</th>
									<th style="width:120px">
										本次买断价
									</th>
									<th style="width:120px">
										初步标价
									</th>
									<th style="width:100px">
										工厂涨价浮动(%)
									</th>
								</tr>
								<tr>
									<td class="va-m ta-c" rowspan="6">
										<a class="btn btn-info w30" style="margin-left:-7px" name="deleteProduction"
											productionId="${fn:escapeXml(inquiry.inquiryDtlList[status.index].productionId)}"
											versionNo="${inquiry.inquiryDtlList[status.index].versionNo}">
											<b>
												删
											</b>
										</a>
									</td>
									<td rowspan="6">
										<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
										<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
										${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/>
										<br/>
										<font style="color:blue">订单号:&nbsp&nbsp</font>${item.tradeOrderId!=null?item.tradeOrderId:'&nbsp;'}<br/>
										<font style="color:blue">上次询单号:&nbsp&nbsp</font>${item.previousInquiryId!=null?item.previousInquiryId:'无'}<br/>
										<font style="color:blue">上次订单号:&nbsp&nbsp</font>${item.previousTradeOrderId!=null?item.previousTradeOrderId:'无'}<br/>
										<sf:hidden path="inquiryDtlList[${status.index}].productionId"/>
										<sf:hidden path="inquiryDtlList[${status.index}].versionNo"/>
										<sf:hidden path="inquiryDtlList[${status.index}].descriptionC"/>
										<sf:hidden path="inquiryDtlList[${status.index}].descriptionE"/>
										<sf:hidden path="inquiryDtlList[${status.index}].tradeOrderId"/>
										<sf:hidden path="inquiryDtlList[${status.index}].previousInquiryId"/>
										<sf:hidden path="inquiryDtlList[${status.index}].previousTradeOrderId"/>
										<sf:hidden path="inquiryDtlList[${status.index}].isUsed"/>
										<sf:hidden path="inquiryDtlList[${status.index}].productionUpdateTime"/>
									</td>
								</tr>
								<tr>
									<td>
										${item.previousRMB!=null?item.previousRMB:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].previousRMB"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="inquiryDtlList[${status.index}].RMB" maxlength="11" style="width:120px"/>
										<sf:errors path="inquiryDtlList[${status.index}].RMB" class="sf-error" style="margin-top:-5px"/>
									</td>
									<td>
										${item.buyoutPrice!=null?item.buyoutPrice:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].buyoutPrice"/>
									</td>
									<td>
										${item.preliminaryPrice!=null?item.preliminaryPrice:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].preliminaryPrice"/>
									</td>
									<td>
										${item.factoryPriceFluctuation!=null?item.factoryPriceFluctuation:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].factoryPriceFluctuation"/>
									</td>
								</tr>
								<tr>
									<th>
										上次汇率
									</th>
									<th>
										汇率
									</th>
									<th>
										退税率(%) 
									</th>
									<th>
										调整金额
									</th>
									<th>
										汇率变化浮动(%)
									</th>
								</tr>
								<tr>
									<td>
										${item.previousRate!=null?item.previousRate:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].previousRate"/>
									</td>
									<td>
										<sf:input rate="1" class="control-largeCommon" path="inquiryDtlList[${status.index}].rate" maxlength="7" style="width:80px"
											onblur="autoInput(this)"/>
										<sf:errors path="inquiryDtlList[${status.index}].rate" class="sf-error" style="margin-top:-5px"/>
									</td>
									<td>
										${item.taxReturnRate!=null?item.taxReturnRate:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].taxReturnRate"/>
									</td>
									<td>
										${item.adjustPrice!=null?item.adjustPrice:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].adjustPrice"/>
									</td>
									<td>
										${item.rateFluctuation!=null?item.rateFluctuation:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].rateFluctuation"/>
									</td>
								</tr>
								<tr>
									<th>
										上次USD
									</th>
									<th>
									</th>
									<th>
										成本利润率(%)
									</th>
									<th>
										<font style="color:red"><b>最终报价</b></font>
									</th>
									<th>
										最终报价浮动(%)
									</th>
								</tr>
								<tr style="height: 55px">
									<td>
										${item.previousUSD!=null?item.previousUSD:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].previousUSD"/>
									</td>
									<td>
									</td>
									<td>
										${item.costProfitRatio!=null?item.costProfitRatio:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].costProfitRatio"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="inquiryDtlList[${status.index}].finalPrice" maxlength="11" style="width:120px"/>
										<sf:errors path="inquiryDtlList[${status.index}].finalPrice" class="sf-error"/>
									</td>
									<td>
										${item.finalDollarPriceFluctuation!=null?item.finalDollarPriceFluctuation:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].finalDollarPriceFluctuation"/>
									</td>
								</tr>
							</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
</div>

</sf:form>

<jsp:include page="footer.jsp"></jsp:include>
