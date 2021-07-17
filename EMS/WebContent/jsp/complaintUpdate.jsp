<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>

<jsp:include page="header.jsp"></jsp:include>

<script src="../jsp/js/ajaxfileupload.js"></script>
<script src="../jsp/js/complaint.js"></script>
<script src="../jsp/js/productionPopCall.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script>
$(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
</script>

<sf:form id="form1" method="post" action="/EMS/complaint/update" modelAttribute="complaint">
<input type="hidden" name="token" value="${token}"/>
<input type="hidden" name="isBack" value="1"/>
<input type="hidden" name="pageMode" value="update"/>
<input type="hidden" id="productions" name="productions"/>
<sf:hidden path="picture"/>
<sf:hidden path="pictureExisted"/>
<sf:hidden path="updateTime"/>

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
						投诉信息
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
								投诉编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="complaintId" maxlength="30" readonly="true"/>
								<br/>
								<sf:errors path="complaintId" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="complaintDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="complaintDate" class="sf-error"/>
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
								<c:if test="${complaint.complaintDtlList.size()==0}">
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
								反馈日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="replyDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="replyDate" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								处理完了日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="dealDate" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="dealDate" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								提醒开始日期
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="alertDateFrom" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="alertDateFrom" class="sf-error"/>
							</td>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								投诉处理期限
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="dealDeadline" maxlength="10" onfocus="WdatePicker()"/>
								<br/>
								<sf:errors path="dealDeadline" class="sf-error"/>
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
			<div class="form-actions ta-c">
				<a class="btn btn-info w150" id="productionRef" funcId="complaint" href="javascript:void(0)">
					<b>
						添加产品
					</b>
				</a>
			</div>
			<div class="form-actions ta-c">
				<a class="btn btn-primary w150" href="javascript:void(0)" id="updateSave">
					<b>
						保存投诉
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
	
	<c:if test="${complaint.complaintDtlList.size()>0}">
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
						<tr>
							<th style="width:40px;">
							</th>
							<th style="width:30px;">
								序号
							</th>
							<th style="width:150px;">
								产品
							</th>
							<th style="width:235px;">
								信息导入
							</th>
							<th style="width:145px;">
								订单产品信息
							</th>
							<th style="">
								备注
							</th>
						</tr>
						<c:forEach var="item" items="${complaint.complaintDtlList}" varStatus="status">
							<tbody>
								<tr>
									<td class="va-m ta-c">
										<c:choose>
										<c:when test="${complaint.complaintDtlList.get(status.index).getQuantitySent() != null}">
										</c:when>
										<c:otherwise>
											<a class="btn btn-info w30" name="deleteProduction"
												productionId="${fn:escapeXml(complaint.complaintDtlList[status.index].productionId)}"
												versionNo="${complaint.complaintDtlList[status.index].versionNo}"
												index="${status.index}">
												<b>
													删
												</b>
											</a>
										</c:otherwise>
										</c:choose>
									</td>
									<td style="vertical-align: middle; text-align: center">
										${status.index + 1}
									</td>
									<td>
										<font style="color:red">${item.productionId!=null?item.productionId:'&nbsp;'}</font><br/>
										<font style="color:red">版本: ${item.versionNo!=null?item.versionNo:'&nbsp;'}</font><br/>
										${item.descriptionC!=null?item.descriptionC:'&nbsp;'}<br/>
										${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/>
										<br/>
										<font color="blue">处理方式: </font><br/>
										<c:choose>
										<c:when test="${complaint.complaintDtlList.get(status.index).getQuantitySent() != null}">
											<sf:radiobutton path="complaintDtlList[${status.index}].handleType" value="0" checked="true" disabled="true"/>未定
											<sf:radiobutton style="margin-left: 10px" path="complaintDtlList[${status.index}].handleType" value="1" disabled="true"/>补货
											<sf:radiobutton style="margin-left: 10px" path="complaintDtlList[${status.index}].handleType" value="2" disabled="true"/>退款
											<sf:hidden path="complaintDtlList[${status.index}].handleType"/>
										</c:when>
										<c:otherwise>
											<sf:radiobutton path="complaintDtlList[${status.index}].handleType" value="0" checked="true"/>未定
											<sf:radiobutton style="margin-left: 10px" path="complaintDtlList[${status.index}].handleType" value="1"/>补货
											<sf:radiobutton style="margin-left: 10px" path="complaintDtlList[${status.index}].handleType" value="2"/>退款
										</c:otherwise>
										</c:choose>
										<br/><br/>
										<font style="color:blue">已发送：<br/>
											<c:choose>
											<c:when test="${complaint.complaintDtlList[status.index].quantitySent != null}">
												${complaint.complaintDtlList[status.index].quantitySent}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
											</c:choose>
										</font>
										<sf:hidden path="complaintDtlList[${status.index}].productionId"/>
										<sf:hidden path="complaintDtlList[${status.index}].versionNo"/>
										<sf:hidden path="complaintDtlList[${status.index}].descriptionC"/>
										<sf:hidden path="complaintDtlList[${status.index}].descriptionE"/>
										<sf:hidden path="complaintDtlList[${status.index}].productionUpdateTime"/>
										<sf:hidden path="complaintDtlList[${status.index}].quantitySent"/>
									</td>
									<td>
										TO:<sf:input class="control-largeCommon" path="complaintDtlList[${status.index}].tradeOrderId" maxlength="14" style="width:145px;margin-left:16px;" readonly="true"/>
										<c:choose>
										<c:when test="${complaint.complaintDtlList.get(status.index).getQuantitySent() != null}">
										</c:when>
										<c:otherwise>
											<a class="btn btn-info w30" name="clear" style="margin-top:-10px"
												productionId="${fn:escapeXml(complaint.complaintDtlList[status.index].productionId)}"
												versionNo="${complaint.complaintDtlList[status.index].versionNo}"
												index="${status.index}">
												<b>
													清
												</b>
											</a><br/>
										</c:otherwise>
										</c:choose>
										<c:choose>
										
										<c:when test="${complaint.complaintDtlList.get(status.index).getQuantitySent() != null}">
											<br/>PO:<sf:input class="control-largeCommon" path="complaintDtlList[${status.index}].po" maxlength="30" style="width:145px;margin-left:16px;" readonly="true"/>
											<br/>
											<sf:errors path="complaintDtlList[${status.index}].po" class="sf-error" style="margin-top:-5px; margin-left:37px;"/><br/>
										</c:when>
										<c:otherwise>
											PO:<sf:input class="control-largeCommon" path="complaintDtlList[${status.index}].po" maxlength="30" style="width:145px;margin-left:16px;"/>
											<a class="btn btn-info w30" name="tradeOrderRef" style="margin-top:-10px"
												productionId="${fn:escapeXml(complaint.complaintDtlList[status.index].productionId)}"
												versionNo="${complaint.complaintDtlList[status.index].versionNo}"
												index="${status.index}">
												<b>
													引
												</b>
											</a><br/>
											<sf:errors path="complaintDtlList[${status.index}].po" class="sf-error" style="margin-top:-5px; margin-left:37px;"/><br/>
										</c:otherwise>
										</c:choose>
										
										数量:<sf:input class="control-largeCommon" path="complaintDtlList[${status.index}].quantity" maxlength="14" style="width:145px;margin-left:10px;"/>
										<a class="btn btn-info w30" name="calculate" style="margin-top:-10px"
											productionId="${fn:escapeXml(complaint.complaintDtlList[status.index].productionId)}"
											versionNo="${complaint.complaintDtlList[status.index].versionNo}"
											index="${status.index}">
											<b>
												计
											</b>
										</a><br/>
										<sf:errors path="complaintDtlList[${status.index}].quantity" class="sf-error" style="margin-top:-5px; margin-left:37px;"/><br/>
										箱数:<sf:input class="control-largeCommon" path="complaintDtlList[${status.index}].boxAmount" maxlength="14" style="width:145px;margin-left:10px;"/>
										<br/><sf:errors path="complaintDtlList[${status.index}].boxAmount" class="sf-error" style="margin-top:-5px; margin-left:37px;"/>
										<font color="red">箱数有值的情况下按计算，箱数将不会被按照数量重新计算，包装信息的小计也将按照当前箱数来计算</font>
									</td>
									<td>
										供应商: ${item.vendorId!=null?item.vendorId:'&nbsp;'} ${item.vendorName!=null?item.vendorName:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].vendorId"/>
										<sf:hidden path="complaintDtlList[${status.index}].vendorName"/>
										体积: ${item.volume!=null?item.volume:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].volume"/>
										毛重: ${item.grossWeight!=null?item.grossWeight:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].grossWeight"/>
										净重: ${item.netWeight!=null?item.netWeight:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].netWeight"/>
										内箱: ${item.inside!=null?item.inside:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].inside"/>
										外箱: ${item.outside!=null?item.outside:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].outside"/>
										RMB单价: ${item.iUnitPrice!=null?item.iUnitPrice:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].iUnitPrice"/>
										USD单价: ${item.tUnitPrice!=null?item.tUnitPrice:'&nbsp;'}<br/>
										<sf:hidden path="complaintDtlList[${status.index}].tUnitPrice"/>
										<font color="blue">体积小计: ${item.volumeTtl!=null?item.volumeTtl:'&nbsp;'}</font><br/>
										<sf:hidden path="complaintDtlList[${status.index}].volumeTtl"/>
										<font color="blue">毛重小计: ${item.grossWeightTtl!=null?item.grossWeightTtl:'&nbsp;'}</font><br/>
										<sf:hidden path="complaintDtlList[${status.index}].grossWeightTtl"/>
										<font color="blue">净重小计: ${item.netWeightTtl!=null?item.netWeightTtl:'&nbsp;'}</font><br/>
										<sf:hidden path="complaintDtlList[${status.index}].netWeightTtl"/>
										<font color="blue">RMB小计: ${item.iAmount!=null?item.iAmount:'&nbsp;'}</font><br/>
										<sf:hidden path="complaintDtlList[${status.index}].iAmount"/>
										<font color="blue">USD小计: ${item.tAmount!=null?item.tAmount:'&nbsp;'}</font><br/>
										<sf:hidden path="complaintDtlList[${status.index}].tAmount"/>
									</td>
									<td>
										<sf:textarea class="span4 control-largeCommon" path="complaintDtlList[${status.index}].comment" maxlength="9999" style="resize:none;height:250px; width:238px"/>
										<br/>
										<sf:errors path="complaintDtlList[${status.index}].comment" class="sf-error"/>
									</td>
								</tr>
					    	</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${complaint.complaintDtlList.size()>0}">
		<div class="titledbox">
			<div class="titledbox-header">
				<div class="row-fluid">
					<div class="span3">
						<h2>
							合计
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
									处理方式
								</th>
								<th>
									总数量
								</th>
								<th>
									总箱数
								</th>
								<th>
									总体积
								</th>
								<th>
									总毛重
								</th>
								<th>
									总净重
								</th>
								<th>
									总RMB
								</th>
								<th>
									总美金
								</th>
							</tr>
				    	</tbody>
						<tbody>
							<tr>
								<td>
									未定
								</td>
								<td>
									${complaint.quantityTtl4U!=''?complaint.quantityTtl4U:'&nbsp;'}
									<sf:hidden path="quantityTtl4U"/>
								</td>
								<td>
									${complaint.boxAmountTtl4U!=''?complaint.boxAmountTtl4U:'&nbsp;'}
									<sf:hidden path="boxAmountTtl4U"/>
								</td>
								<td>
									${complaint.volumeTtl4U!=''?complaint.volumeTtl4U:'&nbsp;'}
									<sf:hidden path="volumeTtl4U"/>
								</td>
								<td>
									${complaint.grossWeightTtl4U!=''?complaint.grossWeightTtl4U:'&nbsp;'}
									<sf:hidden path="grossWeightTtl4U"/>
								</td>
								<td>
									${complaint.netWeightTtl4U!=''?complaint.netWeightTtl4U:'&nbsp;'}
									<sf:hidden path="netWeightTtl4U"/>
								</td>
								<td>
									${complaint.iAmountTtl4U!=''?complaint.iAmountTtl4U:'&nbsp;'}
									<sf:hidden path="iAmountTtl4U"/>
								</td>
								<td>
									${complaint.tAmountTtl4U!=''?complaint.tAmountTtl4U:'&nbsp;'}
									<sf:hidden path="tAmountTtl4U"/>
								</td>
							</tr>
							<tr>
								<td>
									退款
								</td>
								<td>
									${complaint.quantityTtl4R!=''?complaint.quantityTtl4R:'&nbsp;'}
									<sf:hidden path="quantityTtl4R"/>
								</td>
								<td>
									${complaint.boxAmountTtl4R!=''?complaint.boxAmountTtl4R:'&nbsp;'}
									<sf:hidden path="boxAmountTtl4R"/>
								</td>
								<td>
									${complaint.volumeTtl4R!=''?complaint.volumeTtl4R:'&nbsp;'}
									<sf:hidden path="volumeTtl4R"/>
								</td>
								<td>
									${complaint.grossWeightTtl4R!=''?complaint.grossWeightTtl4R:'&nbsp;'}
									<sf:hidden path="grossWeightTtl4R"/>
								</td>
								<td>
									${complaint.netWeightTtl4R!=''?complaint.netWeightTtl4R:'&nbsp;'}
									<sf:hidden path="netWeightTtl4R"/>
								</td>
								<td>
									${complaint.iAmountTtl4R!=''?complaint.iAmountTtl4R:'&nbsp;'}
									<sf:hidden path="iAmountTtl4R"/>
								</td>
								<td>
									${complaint.tAmountTtl4R!=''?complaint.tAmountTtl4R:'&nbsp;'}
									<sf:hidden path="tAmountTtl4R"/>
								</td>
							</tr>
							<tr>
								<td>
									补货
								</td>
								<td>
									${complaint.quantityTtl4A!=''?complaint.quantityTtl4A:'&nbsp;'}
									<sf:hidden path="quantityTtl4A"/>
								</td>
								<td>
									${complaint.boxAmountTtl4A!=''?complaint.boxAmountTtl4A:'&nbsp;'}
									<sf:hidden path="boxAmountTtl4A"/>
								</td>
								<td>
									${complaint.volumeTtl4A!=''?complaint.volumeTtl4A:'&nbsp;'}
									<sf:hidden path="volumeTtl4A"/>
								</td>
								<td>
									${complaint.grossWeightTtl4A!=''?complaint.grossWeightTtl4A:'&nbsp;'}
									<sf:hidden path="grossWeightTtl4A"/>
								</td>
								<td>
									${complaint.netWeightTtl4A!=''?complaint.netWeightTtl4A:'&nbsp;'}
									<sf:hidden path="netWeightTtl4A"/>
								</td>
								<td>
									${complaint.iAmountTtl4A!=''?complaint.iAmountTtl4A:'&nbsp;'}
									<sf:hidden path="iAmountTtl4A"/>
								</td>
								<td>
									${complaint.tAmountTtl4A!=''?complaint.tAmountTtl4A:'&nbsp;'}
									<sf:hidden path="tAmountTtl4A"/>
								</td>
							</tr>
							<tr>
								<td>
									合计
								</td>
								<td>
									${complaint.quantityTtl!=''?complaint.quantityTtl:'&nbsp;'}
									<sf:hidden path="quantityTtl"/>
								</td>
								<td>
									${complaint.boxAmountTtl!=''?complaint.boxAmountTtl:'&nbsp;'}
									<sf:hidden path="boxAmountTtl"/>
								</td>
								<td>
									${complaint.volumeTtl!=''?complaint.volumeTtl:'&nbsp;'}
									<sf:hidden path="volumeTtl"/>
								</td>
								<td>
									${complaint.grossWeightTtl!=''?complaint.grossWeightTtl:'&nbsp;'}
									<sf:hidden path="grossWeightTtl"/>
								</td>
								<td>
									${complaint.netWeightTtl!=''?complaint.netWeightTtl:'&nbsp;'}
									<sf:hidden path="netWeightTtl"/>
								</td>
								<td>
									${complaint.iAmountTtl!=''?complaint.iAmountTtl:'&nbsp;'}
									<sf:hidden path="iAmountTtl"/>
								</td>
								<td>
									${complaint.tAmountTtl!=''?complaint.tAmountTtl:'&nbsp;'}
									<sf:hidden path="tAmountTtl"/>
								</td>
							</tr>
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</div>
<div class="modal fade" id="modal-container-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px;">
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
