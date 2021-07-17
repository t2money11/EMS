<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/inquiry.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="inquiry">
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
								询单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="inquiryId4S" maxlength="30"/>
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
				<a class="btn w100" id="inquirySearch">
					<b>
						检索
					</b>
				</a>
				<a style="margin-left:30px;" class="btn btn-primary w100" id="inquiryAdd">
					<b>
						添加
					</b>
				</a>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${inquiry.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="width:100px">
							询单编号
						</th>
						<th style="width:100px">
							客户
						</th>
						<th>
							产品型号
						</th>
						<th style="width:100px">
							订单号
						</th>
						<th style="width:50px">
							汇率
						</th>
						<th style="width:80px">
							RMB
						</th>
						<th style="width:80px">
							最终报价
						</th>
						<th style="width:90px">
							成本利润率(%) 
						</th>
					</tr>
					<c:forEach var="item" items="${inquiry.resultInquiryDtlList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" inquiryId="${item.inquiryId}"/>
							</td>
							<td>
								${item.inquiryId!=null?item.inquiryId:'&nbsp;'}
							</td>
							<td>
								${item.customerId!=null?item.customerId:'&nbsp;'}
								<br>
								${item.customerName!=null?item.customerName:'&nbsp;'}
							</td>
							<td>
								${item.productionId!=null?item.productionId:'&nbsp;'}
								<br>
								版本：${item.versionNo!=null?item.versionNo:'&nbsp;'}
							</td>
							<td>
								${item.tradeOrderId!=null?item.tradeOrderId:'&nbsp;'}
							</td>
							<td>
								${item.rate!=null?item.rate:'&nbsp;'}
							</td>
							<td>
								${item.RMB!=null?item.RMB:'&nbsp;'}
							</td>
							<td>
								${item.finalPrice!=null?item.finalPrice:'&nbsp;'}
							</td>
							<td>
								${item.costProfitRatio!=null?item.costProfitRatio:'&nbsp;'}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${inquiry.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${inquiry.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${inquiry.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${inquiry.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${inquiry.pageInfo.startIndex ne '0'}">
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
									<c:when test="${inquiry.pageInfo.previousIndex lt inquiry.pageInfo.startIndex}">
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
									<c:when test="${inquiry.pageInfo.nextIndex > inquiry.pageInfo.startIndex}">
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
									<c:when test="${inquiry.pageInfo.lastIndex eq inquiry.pageInfo.startIndex}">
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
							<a class="btn btn-primary w100" id="inquiryRef" href="javascript:void(0);">
								<b>
									查看
								</b>
							</a>
							<a style="margin-left:30px;" class="btn btn-primary w100" id="inquiryUpdate" href="javascript:void(0);">
								<b>
									修改
								</b>
							</a>
							<a style="margin-left:30px;" id="inquiryDelete" class="btn w100" href="javascript:void(0);">
								<b>
									删除
								</b>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>