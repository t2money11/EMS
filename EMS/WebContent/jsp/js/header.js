//登出
$(document).ready(function(){
	$("#logout").click(function(){
		$("#form1").attr("action", "/EMS/user/logout");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　トップ画面
$(document).ready(function(){
	$("#topMenu").click(function(){
		$("#form1").attr("action", "/EMS/topMenu/topMenuShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　用户管理
$(document).ready(function(){
	$("#userSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/user/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　产品管理
$(document).ready(function(){
	$("#productionSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/production/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　供应商管理
$(document).ready(function(){
	$("#vendorSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/vendor/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　客户管理
$(document).ready(function(){
	$("#customerSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/customer/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　客户担当管理
$(document).ready(function(){
	$("#userCustomerLinkRefSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　订单管理
$(document).ready(function(){
	$("#tradeOrderSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　询单管理
$(document).ready(function(){
	$("#inquirySearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/inquiry/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　发货单管理
$(document).ready(function(){
	$("#receiptSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/receipt/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　投诉管理
$(document).ready(function(){
	$("#complaintSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/complaint/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　数据分析
$(document).ready(function(){
	$("#analysisSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/analysis/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//菜单点击　Code管理
$(document).ready(function(){
	$("#codeSearchMenu").click(function(){
		$("#form1").attr("action", "/EMS/code/searchShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

