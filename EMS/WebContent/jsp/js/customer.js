//检索
$(document).ready(function(){
	$("#customerSearch").click(function(){
		$("#form1").attr("action", "/EMS/customer/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#customerAdd").click(function(){
		$("#form1").attr("action", "/EMS/customer/addShow");
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
		$("#form1").attr("action", "/EMS/customer/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/customer/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/customer/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/customer/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#customerRef").click(function(){
		
		var customerId = null;
		var customerlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<customerlist.length; i++)
		{
		    if (customerlist[i].checked)
		    {
		        customerId = customerlist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(customerId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/customer/refShow?customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//复制click
$(document).ready(function(){
	$("#customerCopy").click(function(){
		
		var customerId = null;
		var customerlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<customerlist.length; i++)
		{
		    if (customerlist[i].checked)
		    {
		        customerId = customerlist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(customerId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/customer/copyShow?customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#customerUpdate").click(function(){
		
		var customerId = null;
		var customerlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<customerlist.length; i++)
		{
		    if (customerlist[i].checked)
		    {
		        customerId = customerlist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(customerId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/customer/updateShow?customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#customerDelete").click(function(){
		
		var customerId = null;
		var customerlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<customerlist.length; i++)
		{
		    if (customerlist[i].checked)
		    {
		        customerId = customerlist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(customerId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除客户：" + customerId)){
				
				$("#form1").attr("action", "/EMS/customer/delete?customerIdSelected=" + customerId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/customer/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
