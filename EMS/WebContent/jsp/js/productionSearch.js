//检索
$(document).ready(function(){
	$("#productionSearch").click(function(){
		$("#form1").attr("action", "/EMS/production/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#productionAdd").click(function(){
		$("#form1").attr("action", "/EMS/production/addShow");
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
		$("#form1").attr("action", "/EMS/production/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/production/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/production/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/production/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#productionRef").click(function(){
		
		var productionId = null;
		var versionNo = null;
		var productionlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<productionlist.length; i++)
		{
		    if (productionlist[i].checked)
		    {
		        productionId = productionlist[i].getAttribute("productionid");
		        versionNo = productionlist[i].getAttribute("versionNo");
		        break;
		    }
		}
		
		if(productionId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/production/refShow?productionIdSelected=" + productionId + "&versionNoSelected=" + versionNo);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//复制click
$(document).ready(function(){
	$("#productionCopy").click(function(){
		
		var productionId = null;
		var versionNo = null;
		var productionlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<productionlist.length; i++)
		{
		    if (productionlist[i].checked)
		    {
		        productionId = productionlist[i].getAttribute("productionid");
		        versionNo = productionlist[i].getAttribute("versionNo");
		        break;
		    }
		}
		
		if(productionId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/production/copyShow?productionIdSelected=" + productionId + "&versionNoSelected=" + versionNo);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});


//修改click
$(document).ready(function(){
	$("#productionUpdate").click(function(){
		
		var productionId = null;
		var versionNo = null;
		var productionlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<productionlist.length; i++)
		{
		    if (productionlist[i].checked)
		    {
		        productionId = productionlist[i].getAttribute("productionid");
		        versionNo = productionlist[i].getAttribute("versionNo");
		        break;
		    }
		}
		
		if(productionId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/production/updateShow?productionIdSelected=" + productionId + "&versionNoSelected=" + versionNo);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#productionDelete").click(function(){
		
		var productionId = null;
		var versionNo = null;
		var productionlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<productionlist.length; i++)
		{
		    if (productionlist[i].checked)
		    {
		        productionId = productionlist[i].getAttribute("productionid");
		        versionNo = productionlist[i].getAttribute("versionNo");
		        break;
		    }
		}
		
		if(productionId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除产品：" + productionId)){
				
				$("#form1").attr("action", "/EMS/production/delete?productionIdSelected=" + productionId + "&versionNoSelected=" + versionNo);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});
