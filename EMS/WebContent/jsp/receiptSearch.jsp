<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/receipt.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="receipt">
<sf:hidden id="startIndex" path="pageInfo.startIndex"/>
<sf:hidden id="pageSize" path="pageInfo.pageSize"/>
<sf:hidden id="lastIndex" path="pageInfo.lastIndex"/>


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
						检索条件
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
								<sf:input class="span4 control-largeCommon" path="receiptId4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								订单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="tradeOrderId4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								P.O
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="po4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								Contract No.
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="contractNo4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品型号
							</th>
							<td>
								<sf:input class="span6 control-largeCommon" path="productionId4S" maxlength="45"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								客户
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="customerId4S" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="customerName4S" maxlength="20" readonly="true"/>
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
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="receiptSearch">
					<b>
						检索
					</b>
				</a>
				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '5'}">
					<a style="margin-left:30px;" class="btn btn-primary w100" id="receiptAdd">
						<b>
							添加
						</b>
					</a>
				</c:if>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${receipt.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="width:100px">
							发货单编号
						</th>
						<th style="width:100px">
							发票号码
						</th>
						<th style="width:80px">
							客户
						</th>
						<th style="width:180px">
							日期
						</th>
						<th style="width:80px">
							应收汇金额
						</th>
						<th style="width:100px">
							关联报关发票号码
						</th>
						<th>
							备注
						</th>
					</tr>
					<c:forEach var="item" items="${receipt.resultReceiptList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" receiptId="${item.receiptId}"/>
							</td>
							<td>
								${item.receiptId!=null?item.receiptId:'&nbsp;'}
							</td>
							<td>
								${item.receiptNo!=null?item.receiptNo:'&nbsp;'}
							</td>
							<td>
								${item.customerId!=null?item.customerId:'&nbsp;'}
								<br>
								${item.customerName!=null?item.customerName:'&nbsp;'}
							</td>
							<td>
								船期（ETD）：${item.ETD!=null?item.ETD:'&nbsp;'}<br/>
								预期到达（ETA）：${item.ETA!=null?item.ETA:'&nbsp;'}<br/>
								预计货好日期：${item.goodTime!=null?item.goodTime:'&nbsp;'}<br/>
								预计进账日期：${item.planPostingDate!=null?item.planPostingDate:'&nbsp;'}<br/>
								实际进账日期：${item.postingDate!=null?item.postingDate:'&nbsp;'}<br/>
								电放日期：${item.telexRelease!=null?item.telexRelease:'&nbsp;'}<br/>
								供应商结算日期：${item.balanceDate!=null?item.balanceDate:'&nbsp;'}<br/>
							</td>
							<td>
								${item.remint!=null?item.remint:'&nbsp;'}
							</td>
							<td>
								${item.relateReceiptNo!=null?item.relateReceiptNo:'&nbsp;'}
							</td>
							<td>
								${item.comment!=null?item.comment:'&nbsp;'}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${receipt.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${receipt.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${receipt.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${receipt.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${receipt.pageInfo.startIndex ne '0'}">
										<li class="">
											<a id="toFirstPage" href="javascript:void(0)">
												首页
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="disabled">
											<a>
												首页
											</a>
										</li>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${receipt.pageInfo.previousIndex lt receipt.pageInfo.startIndex}">
										<li class="">
											<a id="toPreviousPage" href="javascript:void(0)">
												上一页
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="disabled">
											<a>
												上一页
											</a>
										</li>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${receipt.pageInfo.nextIndex > receipt.pageInfo.startIndex}">
										<li class="">
											<a id="toNextPage" href="javascript:void(0)">
												下一页
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="disabled">
											<a>
												下一页
											</a>
										</li>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${receipt.pageInfo.lastIndex eq receipt.pageInfo.startIndex}">
										<li class="disabled">
											<a>
												最后一页
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="">
											<a id="toLastPage" href="javascript:void(0)">
												最后一页
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</div>
				<div class="bulk-editing-area dividingbox dividingbox-jointed">
					<div class="row-fluid">
						<div class="span12 ta-c">
							<a class="btn btn-primary w100" id="receiptRef" href="javascript:void(0);">
								<b>
									查看
								</b>
							</a>
							<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '5'}">
								<a style="margin-left:30px;" class="btn btn-primary w100" id="receiptUpdate" href="javascript:void(0);">
									<b>
										修改
									</b>
								</a>
								<a style="margin-left:30px;" id="receiptDelete" class="btn w100" href="javascript:void(0);">
									<b>
										删除
									</b>
								</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>