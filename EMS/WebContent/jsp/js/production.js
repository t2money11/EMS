//画面初期化
$(window).load(function() {
	//画像がある場合
	if($("#pictureExisted").val() != "" && $("#pictureExisted").val() != undefined){
		
		var pathExisted = "/upload_production_pic/" + $('#folderName1').val() + "/" + $('#folderName2').val();
		var fileListExisted = $("#pictureExisted").val().split(";");
		
		for(var i = 0; i < fileListExisted.length; i++){
			
			if($("#isRef").length > 0){
				var html = '<div style="float:left; margin-left:10px"><a href="javascript:void(0)"><img id="uploadImage" width="250" src="' + pathExisted + '/' + fileListExisted[i] + '" onclick="zoom(this)"></a></div>';
			}else{
				var html = '<div style="float:left; margin-left:10px"><a href="javascript:void(0)" id="' + fileListExisted[i] + '" title="删除" onclick="deletePic(this)" isExisted="1">删除</a><br/>'
				+ '<a href="javascript:void(0)"><img id="uploadImage" width="250" src="' + pathExisted + '/' + fileListExisted[i] + '" onclick="zoom(this)"></a></div>';
			}
			
			$("#imgDiv")[0].innerHTML = $("#imgDiv")[0].innerHTML + html;
		}
	}
	if($("#picture").val() != "" && $("#picture").val() != undefined){
		
		var path = "/upload_production_pic";
		var fileList = $("#picture").val().split(";");
		
		for(var i = 0; i < fileList.length; i++){
			var html = '<div style="float:left; margin-left:10px"><a href="javascript:void(0)" id="' + fileList[i] + '" title="删除" onclick="deletePic(this)" isExisted="0">删除</a><br/>'
			+ '<a href="javascript:void(0)"><img id="uploadImage" width="250" src="' + path + '/' + fileList[i] + '" onclick="zoom(this)"></a></div>';
        	
        	$("#imgDiv")[0].innerHTML = $("#imgDiv")[0].innerHTML + html;
    	}
	}
});

function zoom(obj){
	
	$("#zoom")[0].innerHTML = '<img width="' + 800 + 'px" src="' + obj.src + '"></a>';
	$("#modal-container-1").modal();
}

//画像選択
function picSelect(){
	$("#picFiles").click();
}
function ajaxFileUpload(){
    //执行上传文件操作的函数
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
        url:'/EMS/production/picFilesUpLoad',
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:'picFiles',           //文件选择框的id属性
        dataType:'text',                       //服务器返回的格式,可以是json或xml等
        success:function(data, status){        //服务器响应成功时的处理函数
            data = data.replace("<PRE>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
            data = data.replace("</PRE>", '');
            data = data.replace("<pre>", '');
            data = data.replace("</pre>", ''); //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
            if(data.substring(0, 1) == 0){     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
            	var fileName = data.substring(data.lastIndexOf("/") + 1);
            	var html = '<div style="float:left; margin-left:10px"><a href="javascript:void(0)" id="' + fileName + '" title="删除" onclick="deletePic(this)" isExisted="0">删除</a><br/>'
				+ '<a href="javascript:void(0)"><img id="uploadImage" width="250" src="' + data.substring(1) + '" onclick="zoom(this)"></a></div>';
            	
            	$("#imgDiv")[0].innerHTML = $("#imgDiv")[0].innerHTML + html;
            	
        		if($("#picture").val() == ""){
					$("#picture").attr("value", fileName);
				}else{
					$("#picture").attr("value", $("#picture").val() + ";" + fileName);
				}
            	
        		alert("图片上传成功")
            }else{
            	alert("图片上传失败，请重试！！")
            }
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
        	alert("图片上传失败，请重试！！")
        }
    });
}

function deletePic(obj){
	
	if(confirm("请确认是否要删除这张图片")){
		
		var p= obj.parentNode;
		var navigatorName = "Microsoft Internet Explorer"; 
		
		if(obj.getAttribute("isExisted") == 0){
			
			var fileList = $("#picture").val().split(";");
			if(p){
				
				$("#picture").attr("value", "");
				for(var i = 0; i < fileList.length; i++){
					
					if(fileList[i] != obj.id){
						if($("#picture").val() == ""){
							$("#picture").attr("value", fileList[i]);
						}else{
							$("#picture").attr("value", $("#picture").val() + ";" + fileList[i]);
						}
					}
		    	}
			    p.removeNode(true);
		    }
		}else{
			
			var fileList = $("#pictureExisted").val().split(";");
			if(p){
				
				$("#pictureExisted").attr("value", "");
				for(var i = 0; i < fileList.length; i++){
					
					if(fileList[i] != obj.id){
						if($("#pictureExisted").val() == ""){
							$("#pictureExisted").attr("value", fileList[i]);
						}else{
							$("#pictureExisted").attr("value", $("#pictureExisted").val() + ";" + fileList[i]);
						}
					}
		    	}
			    p.removeNode(true);
		    }
		}
	}
}

//返回上一页
$(document).ready(function(){
	$("#goBack").click(function(){
		$("#form1").attr("action", "/EMS/production/searchReturn");
		$("#form1").attr("method", "post");
		$("#form1").submit();
	});
});
