function openShowModalDialogC(url,param,whparam,e){
 
	 // 传递至子窗口的参数
	var paramObj = param || {};
	// 模态窗口高度和宽度
	var whparamObj = whparam || {
		width : 500,
		height : 500
	};
	// 相对于浏览器的居中位置
	var bleft = ($(window).width() - whparamObj.width) / 2;
	var btop = ($(window).height() - whparamObj.height) / 2;
	// 根据鼠标点击位置算出绝对位置
	var tleft = e.screenX - e.clientX;
	var ttop = e.screenY - e.clientY;
	// 最终模态窗口的位置
	var left = bleft + tleft;
	var top = btop + ttop;
	// 参数
	var p = "help:no;status:no;center:yes;";
	p += 'dialogWidth:' + (whparamObj.width) + 'px;';
	p += 'dialogHeight:' + (whparamObj.height) + 'px;';
	p += 'dialogLeft:' + left + 'px;';
	p += 'dialogTop:' + top + 'px;';

	return showModalDialog(url, paramObj, p);
}
//检索画面用
function callCustomerPopSimple(obj) {
	var whObj = { width: 1000, height: 850 };
    var returnData = null;
    if(obj.getAttribute("noAuth") == "1"){
    	
    	returnData = openShowModalDialogC("/EMS/customer/searchPopShow?noAuth=1", null, whObj, window.event);
    }else{
    	
    	returnData = openShowModalDialogC("/EMS/customer/searchPopShow", null, whObj, window.event);
    }
    
    if (returnData) {
    	var nodeName = obj.previousElementSibling || obj.previousSibling;
    	var nodeId = nodeName.previousElementSibling.previousElementSibling || nodeName.previousSibling.previousSibling;
    	nodeId.value = returnData.customerId;
    	nodeName.value = returnData.customerName;
    }
}
function clearCustomerSimple(obj) {
	var nodeName = obj.previousElementSibling.previousElementSibling || obj.previousSibling.previousSibling;
	var nodeId = nodeName.previousElementSibling.previousElementSibling || nodeName.previousSibling.previousSibling;
	nodeId.value = null;
	nodeName.value = null;
}

//登录变更画面用
function callCustomerPopFull() {
    var whObj = { width: 1000, height: 850 };
	var returnData = openShowModalDialogC("/EMS/customer/searchPopShow", null, whObj, window.event);
    if (returnData) {
    	if($("#customerId").length!=0){
    		
    		$("#customerId").attr("value", returnData.customerId);
    		$("#customerName").attr("value", returnData.customerName);
    		$("#customerFullName").val(returnData.customerFullName);
    		$("#country").attr("value", returnData.country);
    		$("#location").val(returnData.location);
    		$("#freightTerms").attr("value", returnData.freightTerms);
    		$("#portOfLoading").attr("value", returnData.portOfLoading);
    		$("#portOfDestination").attr("value", returnData.portOfDestination);
    		$("#paymentTerms").val(returnData.paymentTerms);
    		$("#tel").attr("value", returnData.tel);
    		$("#fax").attr("value", returnData.fax);
    		$("#customerUpdateTime").attr("value", returnData.customerUpdateTime);
    	}
    }
}
function clearCustomerFull() {
	if($("#customerId").length!=0){
		
		$("#customerId").attr("value", "");
		$("#customerName").attr("value", "");
		$("#customerFullName").val("");
		$("#country").attr("value", "");
		$("#location").val("");
		$("#freightTerms").attr("value", "");
		$("#portOfLoading").attr("value", "");
		$("#portOfDestination").attr("value", "");
		$("#paymentTerms").val("");
		$("#tel").attr("value", "");
		$("#fax").attr("value", "");
		$("#customerUpdateTime").attr("value", "");
	}
}
