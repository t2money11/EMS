//检索
$(document).ready(function(){
	$("#userCustomerLinkRefSearch").click(function(){
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#userCustomerLinkRefAdd").click(function(){
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/addShow");
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
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#userCustomerLinkRefRef").click(function(){
		
		var userId = null;
		var customerId = null;
		var userCustomerLinkReflist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userCustomerLinkReflist.length; i++)
		{
		    if (userCustomerLinkReflist[i].checked)
		    {
		        userId = userCustomerLinkReflist[i].getAttribute("userId");
		        customerId = userCustomerLinkReflist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/userCustomerLinkRef/refShow?userIdSelected=" + userId + "&customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//复制click
$(document).ready(function(){
	$("#userCustomerLinkRefCopy").click(function(){
		
		var userId = null;
		var customerId = null;
		var userCustomerLinkReflist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userCustomerLinkReflist.length; i++)
		{
		    if (userCustomerLinkReflist[i].checked)
		    {
		        userId = userCustomerLinkReflist[i].getAttribute("userId");
		        customerId = userCustomerLinkReflist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/userCustomerLinkRef/copyShow?userIdSelected=" + userId + "&customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#userCustomerLinkRefUpdate").click(function(){
		
		var userId = null;
		var customerId = null;
		var userCustomerLinkReflist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userCustomerLinkReflist.length; i++)
		{
		    if (userCustomerLinkReflist[i].checked)
		    {
		        userId = userCustomerLinkReflist[i].getAttribute("userId");
		        customerId = userCustomerLinkReflist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/userCustomerLinkRef/updateShow?userIdSelected=" + userId + "&customerIdSelected=" + customerId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#userCustomerLinkRefDelete").click(function(){
		
		var userId = null;
		var customerId = null;
		var userCustomerLinkReflist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userCustomerLinkReflist.length; i++)
		{
		    if (userCustomerLinkReflist[i].checked)
		    {
		        userId = userCustomerLinkReflist[i].getAttribute("userId");
		        customerId = userCustomerLinkReflist[i].getAttribute("customerId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除客户担当：" + userId + ", " + customerId)){
				
				$("#form1").attr("action", "/EMS/userCustomerLinkRef/delete?userIdSelected=" + userId + "&customerIdSelected=" + customerId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/userCustomerLinkRef/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
