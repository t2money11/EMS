<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base target="_self">
<title>密码变更</title>
<link rel="stylesheet" type="text/css" href="../jsp/css/sparkplug.css"></link>
<script src="../jsp/js/jquery.js"></script>
<script src="../jsp/js/common.js"></script>
<script language=javascript>
	function ReturnWin(str) {
		
		window.close();
	}
	function update() {
		
		$("#form1").attr("action", "/EMS/user/passwordUpdate");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	}
</script>
</head>
<body style="overflow-x:hidden">
<sf:form id="form1" method="post" action="" modelAttribute="user">

<div class="container" id="pagetop" style="width:90%">
	<div class="titledbox">
		<div class="titledbox-header">
			<div class="row-fluid">
				<div class="span3">
					<h2>
						密码变更
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
								旧密码
							</th>
							<td>
								<sf:password class="span4 control-largeCommon" path="passwordOld" maxlength="10"/>
								</br><sf:errors path="passwordOld" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								新密码
							</th>
							<td>
								<sf:password class="span4 control-largeCommon" path="passwordNew1" maxlength="10"/>
								</br><sf:errors path="passwordNew1" class="sf-error"/>
							</td>
						</tr>
						<tr>
							<th class="iobox-label-area additional va-m" style="width: 140px;">
								新密码（确认）
							</th>
							<td>
								<sf:password class="span4 control-largeCommon" path="passwordNew2" maxlength="10"/>
								</br><sf:errors path="passwordNew2" class="sf-error"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /I/Oボックス -->
			<div class="form-actions ta-c">
				<a class="btn w100" id="update" href="javascript:update();">
					<b>
						更新
					</b>
				</a>
				<a style="margin-left:30px;" id="cancel" class="btn btn-primary w100" href="javascript:ReturnWin();">
					<b>
						取消
					</b>
				</a>
			</div>
		</div>
	</div>
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