function openShowModalDialogP(url,param,whparam,e){
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
//发货单用
$(function () {
    $("#complaintRef").click(function (e) {
    	var customerId = $("input[name='customerId']")[0].value;
    	var customerName = $("input[name='customerName']")[0].value;
    	if(customerId == ""){
    		alert("添加投诉单产品之前，请先选择客户");
    		return;
    	}
    	var url = "/EMS/complaint/searchPopShow?customerId=" + customerId + "&customerName=" + customerName;
    	url=encodeURI(url);
        var whObj = { width: 1000, height: 850 };
        var returnData = openShowModalDialogP(url, null, whObj, e);
        if (returnData) {
        	$("#form1").attr("action", "/EMS/receipt/addProduction4C");
    		$("#form1").attr("method", "post");
    		document.getElementById("productions").value = returnData;
    		$(window).unbind('beforeunload');
    		$("#form1").submit();
        }
    });
});
