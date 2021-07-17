<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base target="_self">
<title>用户选择</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<script src="../jsp/js/jquery.js"></script>
<script src="../jsp/js/common.js"></script>
<script language=javascript>
	function ReturnWin(str) {
		if(str == 1){
			
			var userId = null;
			var userNameC = null;
			var userlist = document.getElementsByName("itemSelected");
			var i = 0;
			
			for (i=0; i<userlist.length; i++)
			{
			    if (userlist[i].checked)
			    {
			    	userId = userlist[i].getAttribute("userId");
			    	userNameC = userlist[i].getAttribute("userNameC");
			        break;
			    }
			}
			
			if(userId == null){
				
				alert("请先选择要操作的记录。");
				return;
			}else{
				window.returnValue = {
					userId : userId, 
					userNameC : userNameC
				};
			}
		}
		window.close();
	}
</script>
</head>
<body style="overflow-x:hidden">
<script src="../jsp/js/userPop.js"></script>
<sf:form id="form1" method="post" action="" modelAttribute="user">
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
								用户ID
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userId4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用户中文名
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userNameC4S" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								用户英文名
							</th>
							<td>
								<sf:input class="span4 control-largeCommon" path="userNameE4S" maxlength="30"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="userSearch">
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
		<c:when test="${user.pageInfo.totalCount != 0}">
			<div id="result" class="datagrid">
				<table class="table table-striped  table-bordered" style="">
					<tr>
						<th style="width:25px;">
							选择
						</th>
						<th>
							用户ID
						</th>
						<th >
							用户中文名
						</th>
						<th >
							用户英文名
						</th>
					</tr>
					<c:forEach var="item" items="${user.resultUserList}" varStatus="status">
						<tr>
							<td class="va-m ta-c">
								<input type="radio" name="itemSelected" userId="${item.userId}" userNameC="${item.userNameC}"/>
							</td>
							<td>
								${item.userId!=null?item.userId:'&nbsp;'}
							</td>
							<td>
								${item.userNameC!=null?item.userNameC:'&nbsp;'}
							</td>
							<td>
								${item.userNameE!=null?item.userNameE:'&nbsp;'}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="row-fluid dividingbox ">
					<div class="span4">
						<p class="tableresult-stat additional">
							共
							<strong>
								${user.pageInfo.totalCount}
							</strong>
							记录&nbsp&nbsp&nbsp每页显示
							<strong>
								${user.pageInfo.pageSize}
							</strong>
							条记录&nbsp&nbsp&nbsp当前第
							<strong>
								${user.pageInfo.currentPage }
							</strong>
							/
							<strong>
								${user.pageInfo.pageCount}
							</strong>
							页
						</p>
					</div>
					<!-- /span4 -->
					<div class="span8">
						<div class="pagination pagination-right">
							<ul>
								<c:choose>
									<c:when test="${user.pageInfo.startIndex ne '0'}">
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
									<c:when test="${user.pageInfo.previousIndex lt user.pageInfo.startIndex}">
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
									<c:when test="${user.pageInfo.nextIndex > user.pageInfo.startIndex}">
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
									<c:when test="${user.pageInfo.lastIndex eq user.pageInfo.startIndex}">
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