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
<script src="../jsp/js/jquery.js"></script>
<script src="../jsp/js/common.js"></script>
<script src="../jsp/js/customerPopCall.js"></script>
<script language=javascript>
	function ReturnWin(str) {
		
		if(str == 1){
			
			var productionId = null;
			var versionNo = null;
			var productionIdVendor = null;
			var descriptionC = null;
			var descriptionE = null;
			var packMethod = null;
			var volume = null;
			var grossWeight = null;
			var netWeight = null;
			var inside = null;
			var outside = null;
			var customerId = null;
			var customerName = null;
			var taxReturnRate = null;
			var productionUpdateTime = null;
			
			var productionlist = document.getElementsByName("itemSelected");
			var i = 0;
			var jsonObj = {tempList: []};
			
			for (i=0; i<productionlist.length; i++)
			{
			    if (productionlist[i].checked)
			    {
			    	var temp = {
				        productionId : productionlist[i].getAttribute("productionId"),
				        versionNo : productionlist[i].getAttribute("versionNo"),
				        productionIdVendor : productionlist[i].getAttribute("productionIdVendor"),
				        descriptionC : productionlist[i].getAttribute("descriptionC"),
				        descriptionE : productionlist[i].getAttribute("descriptionE"),
				        packMethod : productionlist[i].getAttribute("packMethod"),
				        volume : productionlist[i].getAttribute("volume"),
				        grossWeight : productionlist[i].getAttribute("grossWeight"),
				        netWeight : productionlist[i].getAttribute("netWeight"),
				        inside : productionlist[i].getAttribute("inside"),
				        outside : productionlist[i].getAttribute("outside"),
				        customerId : productionlist[i].getAttribute("customerId"),
				        customerName : productionlist[i].getAttribute("customerName"),
				        taxReturnRate : productionlist[i].getAttribute("taxReturnRate"),
				        previousRMB : productionlist[i].getAttribute("previousRMB"),
				        productionUpdateTime : productionlist[i].getAttribute("productionUpdateTime")
			    	};
			    	jsonObj.tempList.push(temp);
			    }
			}
			
			if(jsonObj.tempList == null){
				
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
<script src="../jsp/js/productionPop.js"></script>
<sf:form id="form1" method="post" action="" modelAttribute="production">
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
								产品型号
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionId4S"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品型号（供应商）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="productionIdVendor4S"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品描述（中文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="descriptionC4S"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								产品描述（英文）
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="descriptionE4S"/>
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
				<a style="margin-left:30px;" id="cancel" class="btn btn-primary w100" href="javascript:ReturnWin(2);">
					<b>
						取消
					</b>
				</a>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${production.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped table-bordered" style="">
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
						<th style="width:560px">
							产品描述
						</th>
					</tr>
					<c:forEach var="item" items="${production.resultProductionList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="checkbox" name="itemSelected" 
								productionId="${fn:escapeXml(item.productionId)}" 
								versionNo="${fn:escapeXml(item.versionNo)}" 
								productionIdVendor="${fn:escapeXml(item.productionIdVendor)}" 
								descriptionC="${fn:escapeXml(item.descriptionC)}"
								descriptionE="${fn:escapeXml(item.descriptionE)}"
								packMethod="${fn:escapeXml(item.packMethod)}"
								volume="${fn:escapeXml(item.volume)}"
								grossWeight="${fn:escapeXml(item.grossWeight)}"
								netWeight="${fn:escapeXml(item.netWeight)}"
								inside="${fn:escapeXml(item.inside)}"
								outside="${fn:escapeXml(item.outside)}"
								customerId="${fn:escapeXml(item.customerId)}"
								customerName="${fn:escapeXml(item.customerName)}"
								taxReturnRate ="${fn:escapeXml(item.taxReturnRate)}" 
								previousRMB ="${fn:escapeXml(item.previousRMB)}" 
								productionUpdateTime ="${fn:escapeXml(item.productionUpdateTime)}" 
								/>
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