<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/inquiry.js"></script>
<script src="../jsp/js/productionPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="inquiry">
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
								<br/>
								<sf:errors path="customerId" class="sf-error"/>
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
									</td>
								</tr>
								<tr>
									<td>
										${item.previousRMB!=null?item.previousRMB:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].previousRMB"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="inquiryDtlList[${status.index}].RMB" maxlength="11" style="width:120px" readonly="true"/>
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
										<sf:input class="control-largeCommon" path="inquiryDtlList[${status.index}].rate" maxlength="7" style="width:80px" readonly="true"/>
										<sf:errors path="inquiryDtlList[${status.index}].rate" class="sf-error" style="margin-top:-5px"/>
									</td>
									<td>
										${item.taxReturnRate!=null?item.taxReturnRate:'&nbsp;'}
										<sf:hidden path="inquiryDtlList[${status.index}].taxReturnRate"/>
									</td>
									<td>
										<sf:input class="control-largeCommon" path="inquiryDtlList[${status.index}].adjustPrice" maxlength="11" style="width:120px" readonly="true"/>
										<sf:errors path="inquiryDtlList[${status.index}].adjustPrice" class="sf-error"/>
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
										<font style="color:red"><b>${item.finalPrice!=null?item.finalPrice:'&nbsp;'}</b></font>
										<sf:hidden path="inquiryDtlList[${status.index}].finalPrice"/>
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
