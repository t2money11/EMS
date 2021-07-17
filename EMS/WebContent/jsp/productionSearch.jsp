<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/productionSearch.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script src="../jsp/js/vendorPopCall.js"></script>

<sf:form id="form1" method="post" action="" modelAttribute="production">
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
								产品型号
							</th>
							<td>
								<sf:input class="span6 control-largeCommon" path="productionId4S" maxlength="45"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品型号（供应商）
							</th>
							<td>
								<sf:input class="span6 control-largeCommon" path="productionIdVendor4S" maxlength="45"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品描述（中文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="descriptionC4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品描述（英文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="descriptionE4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品名（报关用中文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionCname4export4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品名（报关用英文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionEname4export4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								审核状态
							</th>
							<td>
								<sf:select class="w200" path="status4S" items="${production.statusInput4S}"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="vendorId4S" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="vendorName4S" maxlength="20" readonly="true"/>
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
								<sf:errors path="vendorId4S" class="sf-error"/>
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
								<a id="" noAuth="1" class="btn btn-info w50" style="margin-left: 10px" onclick="callCustomerPopSimple(this)">
									<b>
										客户检索
									</b>
								</a>
								<a id="" class="btn btn-info w50" style="margin-left: 10px" onclick="clearCustomerSimple(this)">
									<b>
										清除
									</b>
								</a>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								数据版本
							</th>
							<td>
								<sf:radiobutton path="searchOption" value="0" checked="true"/>只显示最新版本
								<sf:radiobutton style="margin-left: 10px" path="searchOption" value="1"/>包含历史版本
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="productionSearch">
					<b>
						检索
					</b>
				</a>
				<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2' || sessionScope.loginUser.category == '3' || sessionScope.loginUser.category == '5'}">
					<a style="margin-left:30px;" class="btn btn-primary w100" id="productionAdd">
						<b>
							添加
						</b>
					</a>
				</c:if>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${production.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="">
							产品型号
						</th>
						<th style="width:30px">
							版本
						</th>
						<th style="width:450px">
							产品描述
						</th>
						<th style="width:200px">
							产品报关信息
						</th>
					</tr>
					<c:forEach var="item" items="${production.resultProductionList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" productionid="${fn:escapeXml(item.productionId)}" versionNo="${item.versionNo}"/>
							</td>
							<td>
								${item.productionId!=null?item.productionId:'&nbsp;'}
							</td>
							<td>
								${item.versionNo!=null?item.versionNo:'&nbsp;'}
							</td>
							<td>
								${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/>
								${item.descriptionC!=null?item.descriptionC:'&nbsp;'}<br/>
							</td>
							<td>
								${item.productionCname4export!=null?item.productionCname4export:'&nbsp;'}<br/>
								${item.productionEname4export!=null?item.productionEname4export:'&nbsp;'}<br/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${production.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${production.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${production.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${production.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${production.pageInfo.startIndex ne '0'}">
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
									<c:when test="${production.pageInfo.previousIndex lt production.pageInfo.startIndex}">
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
									<c:when test="${production.pageInfo.nextIndex > production.pageInfo.startIndex}">
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
									<c:when test="${production.pageInfo.lastIndex eq production.pageInfo.startIndex}">
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
							<a class="btn btn-primary w100" id="productionRef" href="javascript:void(0);">
								<b>
									查看
								</b>
							</a>
							<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2' || sessionScope.loginUser.category == '3' || sessionScope.loginUser.category == '5'}">
								<a style="margin-left:30px;" class="btn btn-primary w100" id="productionCopy" href="javascript:void(0);">
									<b>
										复制
									</b>
								</a>
							</c:if>
							<c:if test="${sessionScope.loginUser.category == '1' || sessionScope.loginUser.category == '2' || sessionScope.loginUser.category == '3' || sessionScope.loginUser.category == '5'}">
								<a style="margin-left:30px;" class="btn btn-primary w100" id="productionUpdate" href="javascript:void(0);">
									<b>
										修改
									</b>
								</a>
								<a style="margin-left:30px;" id="productionDelete" class="btn w100" href="javascript:void(0);">
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