//检索
$(document).ready(function(){
	$("#receiptSearch").click(function(){
		$("#form1").attr("action", "/EMS/receipt/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#receiptAdd").click(function(){
		$("#form1").attr("action", "/EMS/receipt/addShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//报关输入模式切换
function clearanceModeChange(needConfirm){
	
	//需要报关的话，显示报关信息，否则隐藏
	if(document.getElementById('clearanceDtlDiv')){
		
		if(needConfirm){
			
			if(!confirm("变更报关输入模式会清除报关单价小计和总价，请确认是否需要继续操作？")){
				
				return;
			}
			
			var tempList = $("input[cduInput='1']");
			for(var i = 0; i < tempList.size(); i++){
				
				tempList[i].value = "";
			}
			
			tempList = $("input[cdaInput='1']");
			for(var i = 0; i < tempList.size(); i++){
				
				tempList[i].value = "";
			}
			
			document.getElementById('amountTtl4Export').value = "";
		}
		
		if(document.getElementsByName('clearanceMode')[0].checked){
			
			var tempList = $("input[cdaInput='1']");
			for(var i = 0; i < tempList.size(); i++){
				
				tempList[i].readOnly = true;
			}
			
			document.getElementById('amountTtl4Export').readOnly = false;
		}else{
			
			var tempList = $("input[cdaInput='1']");
			for(var i = 0; i < tempList.size(); i++){
				
				tempList[i].readOnly = false;
			}
			
			document.getElementById('amountTtl4Export').readOnly = true;
		}
	}
}

//报关模式切换
function receiptModeChange(){
	
	//需要报关的话，显示报关信息，否则隐藏
	if(document.getElementById('clearanceDtlDiv')){
		
		if(document.getElementsByName('receiptMode')[0].checked){
			
			document.getElementById('clearanceDtlDiv').style.display = "block";
		}else{
			
			document.getElementById('clearanceDtlDiv').style.display = "none";
		}
	}
}

//计算
$(document).ready(function(){
	$("#calculate").click(function(){
		$("#form1").attr("action", "/EMS/receipt/calculate");
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
		$("#form1").attr("action", "/EMS/receipt/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/receipt/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/receipt/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/receipt/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#receiptRef").click(function(){
		
		var receiptId = null;
		var receiptlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<receiptlist.length; i++)
		{
		    if (receiptlist[i].checked)
		    {
		        receiptId = receiptlist[i].getAttribute("receiptId");
		        break;
		    }
		}
		
		if(receiptId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/receipt/refShow?receiptIdSelected=" + receiptId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//删除产品
$(document).ready(function(){
	$("a[name='deleteProduction']").click(function(){
		if(confirm("请确认是否要从发货单中删除订单：" + this.getAttribute("tradeOrderId") + ", 产品：" + this.getAttribute("productionId") + ", 版本：" + this.getAttribute("versionNo"))){
			$("#form1").attr("action", "/EMS/receipt/deleteProduction?tradeOrderId=" + this.getAttribute("tradeOrderId") 
					+ "&productionId=" + encodeURIComponent(this.getAttribute("productionId"))
					+ "&versionNo=" + this.getAttribute("versionNo")
					+ "&feeNum=" + this.getAttribute("feeNum"));
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

//删除产品
$(document).ready(function(){
	$("a[name='deleteProduction4C']").click(function(){
		if(confirm("请确认是否要从发货单中删除投诉单：" + this.getAttribute("complaintId") + ", 订单：" + this.getAttribute("tradeOrderId") + ", 产品：" + this.getAttribute("productionId") + ", 版本：" + this.getAttribute("versionNo"))){
			$("#form1").attr("action", "/EMS/receipt/deleteProduction4C?complaintId=" + this.getAttribute("complaintId") 
					+ "&tradeOrderId=" + this.getAttribute("tradeOrderId")
					+ "&productionId=" + encodeURIComponent(this.getAttribute("productionId")) 
					+ "&versionNo=" + this.getAttribute("versionNo"));
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#receiptUpdate").click(function(){
		
		var receiptId = null;
		var receiptlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<receiptlist.length; i++)
		{
		    if (receiptlist[i].checked)
		    {
		        receiptId = receiptlist[i].getAttribute("receiptid");
		        break;
		    }
		}
		
		if(receiptId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/receipt/updateShow?receiptIdSelected=" + receiptId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#receiptDelete").click(function(){
		
		var receiptId = null;
		var receiptlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<receiptlist.length; i++)
		{
		    if (receiptlist[i].checked)
		    {
		        receiptId = receiptlist[i].getAttribute("receiptId");
		        break;
		    }
		}
		
		if(receiptId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除发货单：" + receiptId)){
				
				$("#form1").attr("action", "/EMS/receipt/delete?receiptIdSelected=" + receiptId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//下载
$(document).ready(function(){
	$("#download").click(function(){
		$("#form1").attr("action", "/EMS/receipt/download");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/receipt/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#addSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/receipt/add");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

$(document).ready(function(){
	$("#updateSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/receipt/update");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});
