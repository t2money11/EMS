//切换tab
function switchTab(divStr, obj){
	
	var divList = $("div[type='tradeOrderDiv']");
	var aList = $("a[name='sw']");
	
	for(var i = 0; i < divList.size(); i++){
		
		aList[i].style.fontWeight = "normal";
		aList[i].style.color = "blue";
		divList[i].style.display = "none";
	}
	obj.style.fontWeight = "bold";
	obj.style.color = "red";
	document.getElementById("div_" + divStr).style.display = "";
}

//发货模式变化
function sendModeChange(obj, needConfirm){
	
	var vendorId = obj.getAttribute("sendMode");
	
	var index = obj.selectedIndex;
	var value = obj.options[index].value; 
	
	var inputList = $("input[kind='sendDateAndQuantity_" + vendorId + "']");
	var recieveDate = $("input[vendorId='recieveDate_" + vendorId + "']")[0];
	
	var msg = null;
	
	if(value == '1'){
		
		if(needConfirm){
			
			if(vendorId == "tradeOrder"){
				
				msg = "变更成一次性发货的话会导致该订单的产品上的出货日期和数量被清除，请确认是否需要继续操作？";
			}else{
				
				msg = "变更成一次性发货的话会导致该供应商的产品上的出货日期和数量被清除，请确认是否需要继续操作？";
			}
			if(!confirm(msg)){
				
				return;
			}
		}
		for(var i = 0; i < inputList.size(); i++){
			
			inputList[i].value = "";
			inputList[i].disabled = true;
		}
		recieveDate.disabled = false;
	}else{
	
		if(needConfirm){
			
			if(vendorId == "tradeOrder"){
				
				msg = "变更成分批发货的话会导致该订单的shipment被清除，请确认是否需要继续操作？";
			}else{
				
				msg = "变更成分批发货的话会导致该供应商的交货日被清除，请确认是否需要继续操作？";
			}
			if(!confirm(msg)){
				
				return;
			}
		}
		for(var i = 0; i < inputList.size(); i++){
			
			inputList[i].disabled = false;
		}
		recieveDate.value = "";
		recieveDate.disabled = true;
	}
}

function countAmountTtl(vendorId){
	
	var amountTtl = 0;
	var amount = $("input[vendorId='amount_" + vendorId + "']");
	var fee = $("input[fee='fee_" + vendorId + "']");
	var amountTtlTD = null;
	var amountTtlH = null;
	
	if(vendorId != "tradeOrder"){
		
		//内贸合同
		amountTtlH = $("input[interTradeOrderHidden='amountTtl_" + vendorId + "']")[0];
	}else{
		
		//外贸合同
		amountTtlH = $("input[tradeOrderHidden='amountTtl_" + vendorId + "']")[0];
	}
	for (var i = 0; i < amount.length; i++) {
		
		if(amount[i] != null && amount[i].value != ""){
			amountTtl += parseFloat(amount[i].value.replaceAll(",", ""));
		}
	}
	for (var i = 0; i < fee.length; i++) {
		
		if(fee[i] != null && fee[i].value != ""){
			amountTtl += parseFloat(fee[i].value.replaceAll(",", ""));
		}
	}
	amountTtlTD = $("#amountTtlTD_" + vendorId)[0];
	if(amountTtl == 0){
		amountTtlTD.innerText = "";
		amountTtlH.value = "";
	}else{
		
		amountTtl = parseFloat(amountTtl).toFixed(2);
		amountTtl = addCommas(new String(amountTtl));
		amountTtlH.value = amountTtl;
		amountTtlTD.innerText = amountTtl;
	}
}

//function advancePaymentDiscountRateOnBlur(obj, vendorId){
//	
//	if(obj.value == ""){
//		
//		countAmountTtl(vendorId);
//	}else{
//		
//		if(isDouble(obj) == true){
//			
//			if(obj.value > 100){
//				
//				alert("预付折扣不能超过100%");
//				obj.focus();
//			}else{
//				
//				obj.value = parseFloat(obj.value).toFixed(2);
//			}
//		}else{
//			
//			alert("请正确输入预付折扣，最多保留小数2位");
//			obj.focus();
//		}
//	}
//}

function feeOnBlur(obj){
	
	var vendorId = obj.getAttribute("vendorId");
	
	if(obj.value == ""){
		
		countAmountTtl(vendorId);
	}else{
		
		if(isDouble(obj) == true){
			
			obj.value = parseFloat(obj.value).toFixed(2);
			countAmountTtl(vendorId);
		}else{
			
			alert("请正确输入金额");
			obj.focus();
		}
	}
}

function quantityOnBlur(obj){
	
	var productionId = obj.getAttribute("productionId");
	var vendorId = obj.getAttribute("vendorId");
	
	var nodeP = null;
	var nodeA = null;
	var nodeAH = null;
	
	//内贸合同
	if(vendorId != "tradeOrder"){
		
		nodeP = document.getElementById("UI_" + productionId);
		nodeA = document.getElementById("AI_" + productionId);
		nodeAH = $("input[productionId='amountI_" + productionId + "']")[0];
		
		var nodeB = document.getElementById("BA_" + productionId);
		var nodeBH = $("input[productionId='boxAmount_" + productionId + "']")[0];
		
		var nodeV = document.getElementById("V_" + productionId);
		var nodeGW = document.getElementById("GW_" + productionId);
		var nodeNW = document.getElementById("NW_" + productionId);
		
		var nodeVT = document.getElementById("VT_" + productionId);
		var nodeGWT = document.getElementById("GWT_" + productionId);
		var nodeNWT = document.getElementById("NWT_" + productionId);
		
		var nodeO = document.getElementById("OUT_" + productionId);
		var nodeI = document.getElementById("IN_" + productionId);
		
			
		if(isLong(obj) == true || obj.value == ""){
			
			if(obj.value == ""){
				
				nodeA.innerText = "";
				nodeAH.value = "";
				countAmountTtl(vendorId);
			}else{
				
				if(nodeP.value != ""){
					
					var amount = obj.value.replaceAll(",", "") * nodeP.value.replaceAll(",", "");
					amount = parseFloat(amount).toFixed(2);
					nodeA.innerText = addCommas(amount);
					nodeAH.value = nodeA.innerText;
				}
			}
		
			calulateBasicInfo(productionId);
			
			countAmountTtl(vendorId);
		}else{
			
			alert("请正确输入数量");
			obj.focus();
		}
		
	}else{
	//外贸合同
		nodeP = document.getElementById("U_" + productionId);
		nodeA = document.getElementById("A_" + productionId);
		nodeAH = $("input[productionId='amount_" + productionId + "']")[0];
		
		if(obj.value == ""){
			
			nodeA.innerText = "";
			nodeAH.value = "";
			countAmountTtl(vendorId);
			
			var iQuantity = $("input[iProductionId='" + productionId + "']")[0];
			iQuantity.value = "";
			quantityOnBlur(iQuantity);
		}else{
			
			if(isLong(obj) == true){
				
				var quantity = obj.value;
				obj.value = addCommas(quantity);
				
				if(nodeP.value != ""){
					
					var amount = obj.value.replaceAll(",", "") * nodeP.value.replaceAll(",", "");
					amount = parseFloat(amount).toFixed(2);
					nodeA.innerText = addCommas(amount);
					nodeAH.value = nodeA.innerText;
				}
				countAmountTtl(vendorId);
				
				var iQuantity = $("input[iProductionId='" + productionId + "']")[0];
				iQuantity.value = quantity;
				quantityOnBlur(iQuantity);
			}else{
				
				alert("请正确输入数量");
				obj.focus();
			}
		}
	}
}

function priceOnBlur(obj){
	
	var productionId = obj.getAttribute("productionId");
	var vendorId = obj.getAttribute("vendorId");
	
	var nodeQ = null;
	var nodeA = null;
	var nodeAH = null;
	
	//内贸合同
	var jindu = null;
	if(vendorId != "tradeOrder"){
		
		nodeQ = document.getElementById("QI_" + productionId);
		nodeAH = $("input[productionId='amountI_" + productionId + "']")[0];
		nodeA = document.getElementById("AI_" + productionId);
		jindu = 2;
	}else{
		
		nodeQ = document.getElementById("Q_" + productionId);
		nodeAH = $("input[productionId='amount_" + productionId + "']")[0];
		nodeA = document.getElementById("A_" + productionId);
		jindu = 3;
	}
	
	if(obj.value == ""){
		
		nodeA.innerText = "";
		nodeAH.value = "";
		countAmountTtl(vendorId);
	}else{
		
		if(isDouble(obj) == true){
			
			var price = parseFloat(obj.value).toFixed(jindu);
				
			obj.value = addCommas(price);
			
			if(nodeQ.value != ""){
				
				var amount = price.replaceAll(",", "") * nodeQ.value.replaceAll(",", "");
				amount = parseFloat(amount).toFixed(2);
				nodeA.innerText = addCommas(amount);
				nodeAH.value = nodeA.innerText;
			}
			countAmountTtl(vendorId);
		}else{
			
			alert("请正确输入金额");
			obj.focus();
		}
	}
}

function outsideOnBlur(obj){
	
	if(isLong0(obj) == true || obj.value == ""){
		
		var productionId = obj.getAttribute("productionId");
		calulateBasicInfo(productionId);
	}else{
		
		alert("请输入整数");
		obj.focus();
	}
}

function insideOnBlur(obj){
	
	if(isLong0(obj) == true || obj.value == ""){
		
		var productionId = obj.getAttribute("productionId");
		calulateBasicInfo(productionId);
	}else{
		
		alert("请输入整数");
		obj.focus();
	}
}

function volumeOnBlur(obj){
	
	var productionId = obj.getAttribute("productionId");
	var nodeBH = $("input[productionId='boxAmount_" + productionId + "']")[0];
	var nodeVT = document.getElementById("VT_" + productionId);
	
	if(obj.value == ""){
		
		nodeVT.innerText = "";
		
	}else if(isDouble0(obj) == true){
		
		if(nodeBH.value == ""){
			
			nodeVT.innerText = "";
		}else{
			
			nodeVT.innerText = parseFloat(obj.value * nodeBH.value).toFixed(4);
		}
		
		if(isDouble0(obj) == true){
			
			obj.value = parseFloat(obj.value).toFixed(4);
		}
		
	}else{
		
		alert("请输入浮点数，小数最多保留4位");
		obj.focus();
	}
}

function grossWeightOnBlur(obj){
	
	var productionId = obj.getAttribute("productionId");
	var nodeBH = $("input[productionId='boxAmount_" + productionId + "']")[0];
	var nodeGWT = document.getElementById("GWT_" + productionId);
	
	if(obj.value == ""){
		
		nodeGWT.innerText = "";
		
	}else if(isDouble0(obj) == true){
		
		if(nodeBH.value == ""){
			
			nodeGWT.innerText = "";
		}else{
			
			nodeGWT.innerText = parseFloat(obj.value * nodeBH.value).toFixed(2);
		}
		
		if(isDouble0(obj) == true){
			
			obj.value = parseFloat(obj.value).toFixed(2);
		}
		
	}else{
		
		alert("请输入浮点数，小数最多保留2位");
		obj.focus();
	}
}

function netWeightOnBlur(obj){
	
	var productionId = obj.getAttribute("productionId");
	var nodeBH = $("input[productionId='boxAmount_" + productionId + "']")[0];
	var nodeNWT = document.getElementById("NWT_" + productionId);
	
	if(obj.value == ""){
		
		nodeNWT.innerText = "";
		
	}else if(isDouble0(obj) == true){
		
		if(nodeBH.value == ""){
			
			nodeNWT.innerText = "";
		}else{
			
			nodeNWT.innerText = parseFloat(obj.value * nodeBH.value).toFixed(2);
		}
		obj.value = parseFloat(obj.value).toFixed(2);
		
	}else{
		
		alert("请输入浮点数，小数最多保留2位");
		obj.focus();
	}
}



function calulateBasicInfo(productionId){
	
	var nodeB = document.getElementById("BA_" + productionId);
	var nodeBH = $("input[productionId='boxAmount_" + productionId + "']")[0];
	
	var nodeV = document.getElementById("V_" + productionId);
	var nodeGW = document.getElementById("GW_" + productionId);
	var nodeNW = document.getElementById("NW_" + productionId);
	
	var nodeVT = document.getElementById("VT_" + productionId);
	var nodeGWT = document.getElementById("GWT_" + productionId);
	var nodeNWT = document.getElementById("NWT_" + productionId);
	
	var nodeO = document.getElementById("OUT_" + productionId);
	var nodeI = document.getElementById("IN_" + productionId);
	
	var quantity = document.getElementById("QI_" + productionId).value;
	if(nodeO.value == "0"){
		
		nodeB.innerText = "0";
		nodeBH.value = "0";
		nodeI.value = "0";
		nodeVT.innerText = "0.0000";
		nodeGWT.innerText = "0.00";
		nodeNWT.innerText = "0.00";
		
	}else if(nodeO.value == "" || quantity == ""){
		
		nodeB.innerText = "";
		nodeBH.value = "";
		nodeVT.innerText = "";
		nodeGWT.innerText = "";
		nodeNWT.innerText = "";
		
		if(nodeO.value == ""){
			nodeI.value = "";
		}
		
	}else{
		
		var outside = nodeO.value;
//		var inside = nodeI.value;
//		if(trim(inside) == "" || inside == "0"){
//			inside = 1;
//		}
		var boxAmount = quantity / outside;
		if(parseInt(quantity) <= parseInt(outside)){
			
			nodeB.innerText = 1;
		}else if(quantity % outside > 0){
			
			nodeB.innerText = parseInt(boxAmount) + 1;
		}else{
			
			nodeB.innerText = parseInt(boxAmount);
		}
		nodeBH.value = nodeB.innerText;
		
		if(isDouble0(nodeV)){
			nodeVT.innerText = parseFloat(nodeV.value * nodeBH.value).toFixed(4);
		}else{
			nodeVT.innerText = "";
		}
		
		if(isDouble0(nodeGW)){
			nodeGWT.innerText = parseFloat(nodeGW.value * nodeBH.value).toFixed(2);
		}else{
			nodeGWT.innerText = "";
		}
		
		if(isDouble0(nodeNW)){
			nodeNWT.innerText = parseFloat(nodeNW.value * nodeBH.value).toFixed(2);
		}else{
			nodeNWT.innerText = "";
		}
	}
}


//删除产品
$(document).ready(function(){
	$("a[name='deleteProduction']").click(function(){
		if(confirm("请确认是否要从订单中删除产品：" + this.getAttribute("productionId"))){
			$("#form1").attr("action", "/EMS/tradeOrder/deleteProduction?productionId=" + encodeURIComponent(this.getAttribute("productionIdOrignal")) + "&versionNo=" + this.getAttribute("versionNoOrignal"));
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

//检索
$(document).ready(function(){
	$("#tradeOrderSearch").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#tradeOrderAdd").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/addShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//计算
$(document).ready(function(){
	$("#calculate").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/calculate");
		$("#form1").attr("method", "post");
		$(window).unbind('beforeunload');
		$("#form1").submit();
	});
});

//分页控件
$(document).ready(function(){
	$("#toNextPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 + $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/tradeOrder/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/tradeOrder/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/tradeOrder/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/tradeOrder/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#tradeOrderRef").click(function(){
		
		var tradeOrderId = null;
		var tradeOrderlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<tradeOrderlist.length; i++)
		{
		    if (tradeOrderlist[i].checked)
		    {
		        tradeOrderId = tradeOrderlist[i].getAttribute("tradeOrderId");
		        break;
		    }
		}
		
		if(tradeOrderId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/tradeOrder/refShow?tradeOrderIdSelected=" + tradeOrderId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#tradeOrderUpdate").click(function(){
		
		var tradeOrderId = null;
		var tradeOrderlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<tradeOrderlist.length; i++)
		{
		    if (tradeOrderlist[i].checked)
		    {
		        tradeOrderId = tradeOrderlist[i].getAttribute("tradeOrderId");
		        break;
		    }
		}
		
		if(tradeOrderId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/tradeOrder/updateShow?tradeOrderIdSelected=" + tradeOrderId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#tradeOrderDelete").click(function(){
		
		var tradeOrderId = null;
		var tradeOrderlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<tradeOrderlist.length; i++)
		{
		    if (tradeOrderlist[i].checked)
		    {
		        tradeOrderId = tradeOrderlist[i].getAttribute("tradeOrderId");
		        break;
		    }
		}
		
		if(tradeOrderId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除订单：" + tradeOrderId)){
				
				$("#form1").attr("action", "/EMS/tradeOrder/delete?tradeOrderIdSelected=" + tradeOrderId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//下载
$(document).ready(function(){
	$("#download").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/download");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/tradeOrder/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#addSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/tradeOrder/add");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

$(document).ready(function(){
	$("#updateSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/tradeOrder/update");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});
