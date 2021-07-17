//检索
$(document).ready(function(){
	$("#userSearch").click(function(){
		$("#form1").attr("action", "/EMS/user/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#userAdd").click(function(){
		$("#form1").attr("action", "/EMS/user/addShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//计算年龄
//function Appendzero (obj) {
//     if (obj < 10) return "0" + obj; else return obj;
//}
//
//$(document).ready(function(){
//	var myDate = new Date();
//	var month = Appendzero(myDate.getMonth() + 1);
//	var day = Appendzero(myDate.getDate());
//	//alert("now:" + myDate.getFullYear() + month + day);
//	$('td[name="birthday"]').each(function(){
//	   	var birthday = $(this).text();
//	   	birthday = birthday.replace(/(^\s*)|(\s*$)/, "");
//	   	if(birthday != ''){
//			var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
//			//alert("birthday:" + birthday.substring(0, 4) + birthday.substring(5, 7) + birthday.substring(8, 10));
//			//alert(birthday.substring(5, 7) + birthday.substring(8, 10));
//			//alert(month + "" + day );
//			//alert(birthday.substring(5, 7) + birthday.substring(8, 10) <= month + "" + day);
//			if (birthday.substring(5, 7) + birthday.substring(8, 10) <= month + "" + day) {
//				age++;
//			} 
//			$(this).next().text(age);
//	   	}else{
//	   		$(this).next().html($(this).next().html()+"&nbsp;");
//	   	}
//	});
//});

//分页控件
$(document).ready(function(){
	$("#toNextPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 + $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/user/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toPreviousPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#startIndex").val()*1 - $("#pageSize").val()*1);
		$("#form1").attr("action", "/EMS/user/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toLastPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val($("#lastIndex").val());
		$("#form1").attr("action", "/EMS/user/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
$(document).ready(function(){
	$("#toFirstPage").click(function(){
		$("#conditionValue").val($("#conditionValueHidden").val());
		$("#conditionSelected").val($("#conditionSelectedHidden").val());
		$("#startIndex").val("0");
		$("#form1").attr("action", "/EMS/user/search");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//查看click
$(document).ready(function(){
	$("#userRef").click(function(){
		
		var userId = null;
		var userlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userlist.length; i++)
		{
		    if (userlist[i].checked)
		    {
		        userId = userlist[i].getAttribute("userId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/user/refShow?userIdSelected=" + userId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//修改click
$(document).ready(function(){
	$("#userUpdate").click(function(){
		
		var userId = null;
		var userlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userlist.length; i++)
		{
		    if (userlist[i].checked)
		    {
		        userId = userlist[i].getAttribute("userId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			$("#form1").attr("action", "/EMS/user/updateShow?userIdSelected=" + userId);
			$("#form1").attr("method", "post");
			$("#form1").submit();
		}
	});
});

//削除click
$(document).ready(function(){
	$("#userDelete").click(function(){
		
		var userId = null;
		var userlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userlist.length; i++)
		{
		    if (userlist[i].checked)
		    {
		        userId = userlist[i].getAttribute("userId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除用户：" + userId)){
				
				$("#form1").attr("action", "/EMS/user/delete?userIdSelected=" + userId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//passwordResetclick
$(document).ready(function(){
	$("#passwordReset").click(function(){
		
		var userId = null;
		var userlist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<userlist.length; i++)
		{
		    if (userlist[i].checked)
		    {
		        userId = userlist[i].getAttribute("userId");
		        break;
		    }
		}
		
		if(userId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否对用户：" + userId + " 进行密码重置")){
				
				$("#form1").attr("action", "/EMS/user/passwordReset?userIdSelected=" + userId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/user/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
