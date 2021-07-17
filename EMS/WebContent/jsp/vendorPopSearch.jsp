<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base target="_self">
<title>供应商选择</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<script src="../jsp/js/jquery.js"></script>
<script src="../jsp/js/common.js"></script>

<script>
	function ReturnWin(str) {
		
		if(str == 1){
			
			var vendorId = null;
			var vendorName = null;
			var vendorlist = document.getElementsByName("itemSelected");
			var i = 0;
			
			for (i=0; i<vendorlist.length; i++)
			{
			    if (vendorlist[i].checked)
			    {
			        vendorId = vendorlist[i].getAttribute("vendorId");
			        vendorName = vendorlist[i].getAttribute("vendorName");
			        break;
			    }
			}
			
			if(vendorId == null){
				
				alert("请先选择要操作的记录。");
				return;
			}else{
				window.returnValue = {
					vendorId : vendorId, 
					vendorName : vendorName
				};
			}
		}
		window.close();
	}
</script>
</head>
<body style="overflow-x:hidden">
<script src="../jsp/js/vendorPop.js"></script>
<sf:form id="form1" method="post" action="" modelAttribute="vendor">
<sf:hidden id="startIndex" path="pageInfo.startIndex"/>
<sf:hidden id="pageSize" path="pageInfo.pageSize"/>
<sf:hidden id="lastIndex" path="pageInfo.lastIndex"/>
<sf:hidden path="noAuth"/>

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
								供应商ID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="vendorId4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商名称
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="vendorName4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								供应商地址
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="location4S" maxlength="30"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="vendorSearch">
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
		<c:when test="${vendor.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th style="width:50px">
							供应商ID
						</th>
						<th style="width:200px">
							供应商名称
						</th>
						<th>
							供应商地址
						</th>
					</tr>
					<c:forEach var="item" items="${vendor.resultVendorList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" 
									vendorId="${fn:escapeXml(item.vendorId)}" 
									vendorName="${fn:escapeXml(item.vendorName)}"
									/>
							</td>
							<td>
								${item.vendorId!=null?item.vendorId:'&nbsp;'}
							</td>
							<td>
								${item.vendorName!=null?item.vendorName:'&nbsp;'}
							</td>
							<td>
								${item.location!=null?item.location:'&nbsp;'}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${vendor.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${vendor.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${vendor.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${vendor.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${vendor.pageInfo.startIndex ne '0'}">
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
									<c:when test="${vendor.pageInfo.previousIndex lt vendor.pageInfo.startIndex}">
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
									<c:when test="${vendor.pageInfo.nextIndex > vendor.pageInfo.startIndex}">
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
									<c:when test="${vendor.pageInfo.lastIndex eq vendor.pageInfo.startIndex}">
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
</body>
</html>