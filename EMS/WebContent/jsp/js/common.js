String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
};
function StringBuffer(){
	this._strings = new Array;
}
StringBuffer.prototype.append = function (str){
	this._strings.push(str);
};
StringBuffer.prototype.toString = function (){
	return this._strings.join("");
};

function isLong(obj){
	
	var reg = /^[1-9]\d*$/;
	return reg.test(obj.value.replaceAll(",", ""));
}

function isLong0(obj){
	
	var reg = /^[0-9]\d*$/;
	return reg.test(obj.value.replaceAll(",", ""));
}

function isDouble(obj){
	var reg1 = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
	var reg2 = /^[1-9]\d*$/;
	return reg1.test(obj.value.replaceAll(",", "")) || reg2.test(obj.value.replaceAll(",", ""));
}

function isDouble0(obj){
	var reg1 = /^[0-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
	var reg2 = /^[0-9]\d*$/;
	return reg1.test(obj.value.replaceAll(",", "")) || reg2.test(obj.value.replaceAll(",", ""));
}

function addCommas(objValue){
//	var nStr = objValue.replaceAll(",", "");
//	nStr += '';
//	x = nStr.split('.');
//	x1 = x[0];
//	x2 = x.length > 1 ? '.' + x[1] : '';
//	var rgx = /(\d+)(\d{3})/;
//	while (rgx.test(x1)) {
//		x1 = x1.replace(rgx, '$1' + ',' + '$2');
//	}
//	return x1 + x2;
	return objValue;
}

function removeCommas(obj){
	obj.value = obj.value.replaceAll(",", "");
}

//警告消除
$(document).ready(function(){
	$("#alertDismissButton").click(function(){
		$("#alertDiv")[0].removeNode(true);
	});
});

function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
