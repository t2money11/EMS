<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base target="_self">
<title>产品选择</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<script src="../jsp/js/jquery-1.11.1.js"></script>
<script src="../jsp/js/common.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script language=javascript>
	function ReturnWin(str) {
		
		if(str == 1){
			
			var tradeOrderPopList = document.getElementsByName("itemSelected");
			var i = 0;
			
			var jsonObj = {tempList: []};
			
			for (i=0; i<tradeOrderPopList.length; i++)
			{
			    if (tradeOrderPopList[i].checked)
			    {
			        var temp = {
			        
		        		complaintId:tradeOrderPopList[i].getAttribute("complaintId"),
				        tradeOrderId:tradeOrderPopList[i].getAttribute("tradeOrderId"),
						po:tradeOrderPopList[i].getAttribute("po"), 
						contractNo:tradeOrderPopList[i].getAttribute("contractNo"),
						tradeOrderCreateDate:tradeOrderPopList[i].getAttribute("tradeOrderCreateDate"),
						tradeOrderUpdateTime:tradeOrderPopList[i].getAttribute("tradeOrderUpdateTime"),
						productionId:tradeOrderPopList[i].getAttribute("productionId"), 
						versionNo:tradeOrderPopList[i].getAttribute("versionNo"), 
						descriptionE:tradeOrderPopList[i].getAttribute("descriptionE"),
						hscode:tradeOrderPopList[i].getAttribute("hscode"),
						productionEName4Export:tradeOrderPopList[i].getAttribute("productionEName4Export"),
						productionCName4Export:tradeOrderPopList[i].getAttribute("productionCName4Export"),
						productionUpdateTime:tradeOrderPopList[i].getAttribute("productionUpdateTime"),
						vendorId:tradeOrderPopList[i].getAttribute("vendorId"),
						vendorName:tradeOrderPopList[i].getAttribute("vendorName"),
						tQuantity:tradeOrderPopList[i].getAttribute("tQuantity"),
						tUnitPrice:tradeOrderPopList[i].getAttribute("tUnitPrice"),
						iUnitPrice:tradeOrderPopList[i].getAttribute("iUnitPrice"),
						tQuantityNotSent:tradeOrderPopList[i].getAttribute("tQuantityNotSent"),
						volume:tradeOrderPopList[i].getAttribute("volume"),
						grossWeight:tradeOrderPopList[i].getAttribute("grossWeight"),
						netWeight:tradeOrderPopList[i].getAttribute("netWeight"),
						inside:tradeOrderPopList[i].getAttribute("inside"),
						outside:tradeOrderPopList[i].getAttribute("outside"),
						boxAmount:tradeOrderPopList[i].getAttribute("boxAmount"),
						complaintUpdateTime:tradeOrderPopList[i].getAttribute("complaintUpdateTime")
			        };
		        	jsonObj.tempList.push(temp);
			    }
			}
			if(jsonObj.tempList == 0){
				
				alert("请先选择要操作的记录。");
				return;
			}else{
				window.returnValue = JSON.stringify(jsonObj);
			}
		}
		window.close();
	}
</script>
</head>
<body style="overflow-x:hidden">
<script src="../jsp/js/complaintPop.js"></script>
<sf:form id="form1" method="post" action="" modelAttribute="tradeOrderPop">
<sf:hidden id="startIndex" path="pageInfo.startIndex"/>
<sf:hidden id="pageSize" path="pageInfo.pageSize"/>
<sf:hidden id="lastIndex" path="pageInfo.lastIndex"/>

<div class="container" id="pagetop">
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
								投诉单编号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="complaintId4S" maxlength="30"/>
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
								客户
							</th>
							<td>
								<sf:input class="span2 control-largeCommon" path="customerId4S" maxlength="20" readonly="true"/> 
								<font>--</font>
								<sf:input class="span5 control-largeCommon" path="customerName4S" maxlength="20" readonly="true"/>
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
								产品型号
							</th>
							<td>
								<sf:input class="span6 control-largeCommon" path="productionId4S" maxlength="45"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="complaintSearch">
					<b>
						检索
					</b>
				</a>
				<a style="margin-left:30px;" id="cancel" class="btn btn-primary w100" href="javascript:ReturnWin(2);">
					<b>
						取消
					</b>
				</a>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${tradeOrderPop.resultTradeOrderPopList.size() != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="">
							投诉单
						</th>
						<th style="">
							订单
						</th>
						<th style="">
							产品
						</th>
						<th style="width:30px">
							版本
						</th>
						<th style="width:150px;">
							数量（外贸）
						</th>
					</tr>
					<c:forEach var="item" items="${tradeOrderPop.resultTradeOrderPopList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<c:if test="${item.volume != null
												&&item.grossWeight != null
												&&item.netWeight != null
												&&item.outside != null
												}">
									<input type="checkbox" name="itemSelected" 
									complaintId="${fn:escapeXml(item.complaintId)}" 
									tradeOrderId="${fn:escapeXml(item.tradeOrderId)}" 
									po="${fn:escapeXml(item.po)}" 
									contractNo="${fn:escapeXml(item.contractNo)}"
									tradeOrderCreateDate="${fn:escapeXml(item.tradeOrderCreateDate)}"
									tradeOrderUpdateTime="${fn:escapeXml(item.tradeOrderUpdateTime)}"
									productionId="${fn:escapeXml(item.productionId)}" 
									versionNo="${fn:escapeXml(item.versionNo)}" 
									descriptionE="${fn:escapeXml(item.descriptionE)}"
									hscode="${fn:escapeXml(item.hscode)}"
									productionEName4Export="${fn:escapeXml(item.productionEName4Export)}"
									productionCName4Export="${fn:escapeXml(item.productionCName4Export)}"
									productionUpdateTime="${fn:escapeXml(item.productionUpdateTime)}"
									vendorId="${fn:escapeXml(item.vendorId)}"
									vendorName="${fn:escapeXml(item.vendorName)}"
									tQuantity="${fn:escapeXml(item.tQuantity)}"
									tUnitPrice="${fn:escapeXml(item.tUnitPrice)}"
									iUnitPrice="${fn:escapeXml(item.iUnitPrice)}"
									tQuantityNotSent="${fn:escapeXml(item.tQuantityNotSent)}"
									volume="${fn:escapeXml(item.volume)}"
									grossWeight="${fn:escapeXml(item.grossWeight)}"
									netWeight="${fn:escapeXml(item.netWeight)}"
									inside="${fn:escapeXml(item.inside)}"
									outside="${fn:escapeXml(item.outside)}"
									boxAmount="${fn:escapeXml(item.boxAmount)}"
									complaintUpdateTime="${fn:escapeXml(item.complaintUpdateTime)}"
									/>
								</c:if>
							</td>
							<td>
								${item.complaintId!=null?item.complaintId:'&nbsp;'}
							</td>
							<td>
								${item.tradeOrderId!=null?item.tradeOrderId:'&nbsp;'}<br/>
								${item.po!=null?item.po:'&nbsp;'}<br/>
								${item.contractNo!=null?item.contractNo:'&nbsp;'}<br/>
								<c:if test="${item.volume == null
												||item.grossWeight == null
												||item.netWeight == null
												||item.outside == null
												}">
									<font style="color: red">该订单产品的体积，毛重，净重以及外箱容纳个数没有输入完整，不能选择作为出货对象，请先把上述信息在订单中输入完整后重新操作</font>
								</c:if>
							</td>
							<td>
								${item.productionId!=null?item.productionId:'&nbsp;'}<br/>
								${item.descriptionE!=null?item.descriptionE:'&nbsp;'}<br/>
							</td>
							<td>
								${item.versionNo!=null?item.versionNo:'&nbsp;'}
							</td>
							<td>
								${item.tQuantity!=null?item.tQuantity:'&nbsp;'}<br/>
								（未发货数：${item.tQuantityNotSent!=null?item.tQuantityNotSent:'&nbsp;'}）
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${tradeOrderPop.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${tradeOrderPop.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${tradeOrderPop.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${tradeOrderPop.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${tradeOrderPop.pageInfo.startIndex ne '0'}">
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
									<c:when test="${tradeOrderPop.pageInfo.previousIndex lt tradeOrderPop.pageInfo.startIndex}">
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
									<c:when test="${tradeOrderPop.pageInfo.nextIndex > tradeOrderPop.pageInfo.startIndex}">
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
									<c:when test="${tradeOrderPop.pageInfo.lastIndex eq tradeOrderPop.pageInfo.startIndex}">
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
							<a class="btn btn-primary w150" id="select" href="javascript:ReturnWin(1);">
								<b>
									选择
								</b>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
	<c:if test="${!empty requestScope.errorMessage}">
		<div id="alertDiv" class="alert alert-danger alert-dismissable">
			<button id="alertDismissButton" type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>${requestScope.errorMessage}</strong>
		</div>
	</c:if>
</div>
</sf:form>

<jsp:include page="footer.jsp"></jsp:include>