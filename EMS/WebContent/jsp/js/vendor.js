//检索
$(document).ready(function(){
	$("#vendorSearch").click(function(){
		$("#form1").attr("action", "/EMS/vendor/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#vendorAdd").click(function(){
		$("#form1").attr("action", "/EMS/vendor/addShow");
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
		$("#form1").attr("action", "/EMS/vendor/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/vendor/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/vendor/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/vendor/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#vendorRef").click(function(){
		
		var vendorId = null;
		var vendorlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<vendorlist.length; i++)
		{
		    if (vendorlist[i].checked)
		    {
		        vendorId = vendorlist[i].getAttribute("vendorId");
		        break;
		    }
		}
		
		if(vendorId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/vendor/refShow?vendorIdSelected=" + vendorId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//复制click
$(document).ready(function(){
	$("#vendorCopy").click(function(){
		
		var vendorId = null;
		var vendorlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<vendorlist.length; i++)
		{
		    if (vendorlist[i].checked)
		    {
		        vendorId = vendorlist[i].getAttribute("vendorId");
		        break;
		    }
		}
		
		if(vendorId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/vendor/copyShow?vendorIdSelected=" + vendorId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#vendorUpdate").click(function(){
		
		var vendorId = null;
		var vendorlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<vendorlist.length; i++)
		{
		    if (vendorlist[i].checked)
		    {
		        vendorId = vendorlist[i].getAttribute("vendorId");
		        break;
		    }
		}
		
		if(vendorId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/vendor/updateShow?vendorIdSelected=" + vendorId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#vendorDelete").click(function(){
		
		var vendorId = null;
		var vendorlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<vendorlist.length; i++)
		{
		    if (vendorlist[i].checked)
		    {
		        vendorId = vendorlist[i].getAttribute("vendorId");
		        break;
		    }
		}
		
		if(vendorId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除供应商：" + vendorId)){
				
				$("#form1").attr("action", "/EMS/vendor/delete?vendorIdSelected=" + vendorId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/vendor/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
