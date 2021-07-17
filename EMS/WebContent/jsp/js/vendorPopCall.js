function openShowModalDialogV(url,param,whparam,e){
 
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
function callVendorPop(obj) {
	
	var urlString = null;
	if(obj.getAttribute("noAuth") == "1"){
    	
		urlString = "/EMS/vendor/searchPopShow?noAuth=1";
    }else{
    	
    	urlString = "/EMS/vendor/searchPopShow";
    }
	
    var whObj = { width: 1000, height: 850 };
    var returnData = openShowModalDialogV(urlString, null, whObj, window.event);
    if (returnData) {
    	var nodeName = obj.previousElementSibling || obj.previousSibling;
    	var nodeId = nodeName.previousElementSibling.previousElementSibling || nodeName.previousSibling.previousSibling;
    	nodeId.value = returnData.vendorId;
    	nodeName.value = returnData.vendorName;
    }
}
function clearVendor(obj){
	var nodeName = obj.previousElementSibling.previousElementSibling || obj.previousSibling.previousSibling;
	var nodeId = nodeName.previousElementSibling.previousElementSibling || nodeName.previousSibling.previousSibling;
	nodeId.value = null;
	nodeName.value = null;
}
