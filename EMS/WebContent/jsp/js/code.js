//检索
$(document).ready(function(){
	$("#codeSearch").click(function(){
		$("#form1").attr("action", "/EMS/code/search");
		$("#form1").attr("method", "post");
		$("#startIndex").val("0");
		$("#form1").submit();
	});
});

//添加click
$(document).ready(function(){
	$("#codeAdd").click(function(){
		$("#form1").attr("action", "/EMS/code/addShow");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

//削除click
$(document).ready(function(){
	$("#codeDelete").click(function(){
		
		var codeId = null;
		var codelist = document.getElementsByName("itemSelected");
		var i = 0;
		
		for (i=0; i<codelist.length; i++)
		{
		    if (codelist[i].checked)
		    {
		    	codeId = codelist[i].getAttribute("codeId");
		        break;
		    }
		}
		
		if(codeId == null){
			
			alert("请先选择要操作的记录。");
		}else{
			
			if(confirm("请确认是否要删除Code：" + codeId)){
				
				$("#form1").attr("action", "/EMS/code/delete?codeIdSelected=" + codeId);
				$("#form1").attr("method", "post");
				$("#form1").submit();
			}
		}
	});
});

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/code/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
