function autoInput(obj){
	
	var tempList = $("input[rate='1']");
	
	for(var i = 0; i < tempList.size(); i++){
		
		tempList[i].value = obj.value;
	}
}

//检索
$(document).ready(function(){
	$("#inquirySearch").click(function(){
		$("#form1").attr("action", "/EMS/inquiry/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//计算
$(document).ready(function(){
	$("#calculate").click(function(){
		$("#form1").attr("action", "/EMS/inquiry/calculate");
		$("#form1").attr("method", "post");
		$(window).unbind('beforeunload');
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#inquiryAdd").click(function(){
		$("#form1").attr("action", "/EMS/inquiry/addShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//分页控件
$(document).ready(function(){
	$("#toNextPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 + $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/inquiry/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/inquiry/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/inquiry/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/inquiry/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#inquiryRef").click(function(){
		
		var inquiryId = null;
		var inquirylist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<inquirylist.length; i++)
		{
		    if (inquirylist[i].checked)
		    {
		        inquiryId = inquirylist[i].getAttribute("inquiryId");
		        break;
		    }
		}
		
		if(inquiryId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/inquiry/refShow?inquiryIdSelected=" + inquiryId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//删除产品
$(document).ready(function(){
	$("a[name='deleteProduction']").click(function(){
		if(confirm("请确认是否要从询单中删除产品 （产品型号：" + this.getAttribute("productionId") + ", 版本：" + this.getAttribute("versionNo") + ")")){
			$("#form1").attr("action", "/EMS/inquiry/deleteProduction?productionId=" + encodeURIComponent(this.getAttribute("productionId")) + "&versionNo=" + this.getAttribute("versionNo"));
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#inquiryUpdate").click(function(){
		
		var inquiryId = null;
		var inquirylist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<inquirylist.length; i++)
		{
		    if (inquirylist[i].checked)
		    {
		        inquiryId = inquirylist[i].getAttribute("inquiryid");
		        break;
		    }
		}
		
		if(inquiryId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/inquiry/updateShow?inquiryIdSelected=" + inquiryId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#inquiryDelete").click(function(){
		
		var inquiryId = null;
		var inquirylist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<inquirylist.length; i++)
		{
		    if (inquirylist[i].checked)
		    {
		        inquiryId = inquirylist[i].getAttribute("inquiryId");
		        break;
		    }
		}
		
		if(inquiryId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请注意此处的删除处理将删除所选的整个询单，而不是单个询单产品！！！\r\n请确认是否要删除询单：" + inquiryId)){
				
				$("#form1").attr("action", "/EMS/inquiry/delete?inquiryIdSelected=" + inquiryId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/inquiry/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#addSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/inquiry/add");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});

$(document).ready(function(){
	$("#updateSave").click(function(){
		if(confirm("确定要保存吗？")){
			$("#form1").attr("action", "/EMS/inquiry/update");
			$("#form1").attr("method", "post");
			$(window).unbind('beforeunload');
			$("#form1").submit();
		}
	});
});
