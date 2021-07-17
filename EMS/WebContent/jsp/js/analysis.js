//下载
$(document).ready(function(){
	$("#downloadPTA").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadPTA");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#downloadTPA").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadTPA");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#downloadC").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadC");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#downloadMEI").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadMEI");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#downloadPA").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadPA");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

$(document).ready(function(){
	$("#downloadPI").click(function(){
		$("#form1").attr("action", "/EMS/analysis/downloadPI");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});

