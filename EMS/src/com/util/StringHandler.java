package com.util;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler {
	
	public static final String NUMBER_FORMAT1 = "####0";
	public static final String NUMBER_FORMAT2 = "####0.00";
	public static final String NUMBER_FORMAT3 = "####0.000";
	public static final String NUMBER_FORMAT4 = "####0.0000";
	
	public static final String NUMBER_COMMA_FORMAT1 = "##,##0";
	public static final String NUMBER_COMMA_FORMAT2 = "##,##0.00";
	public static final String NUMBER_COMMA_FORMAT3 = "##,##0.000";
	public static final String NUMBER_COMMA_FORMAT4 = "##,##0.0000";
	
	
	public static String format(String input) {  
        
		return input != null && !input.trim().equals("") ? input.trim() : null;
    }  
	
	public static boolean isVCId(String str) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^[0-9]{3}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean isReceiptNo(String str) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^[0-9]{2}YCFT18[0-9]{3}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean isContractNo(String str) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^RP[0-9]{2}-[0-9]{3}-[0-9]{2} .*$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean isMail(String str) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean isInt(String str) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		boolean isInt = true;
		try {
			
			Integer.parseInt(str);
		} catch (Exception e) {
			
			isInt = false;
		}
		return isInt;
	}
	
	public static boolean isDouble(String str, int scale) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^(([1-9]+)|([0-9]+\\.[0-9]{1," + String.valueOf(scale) + "}))$");
		Matcher m = p.matcher(str);
		return m.matches() || isInt(str);
	}
	
	public static boolean isDoubleZF(String str, int scale) {
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
		
		Pattern p = Pattern.compile("^[+-]?(([1-9]+)|([0-9]+\\.[0-9]{1," + String.valueOf(scale) + "}))$");
		Matcher m = p.matcher(str);
		return m.matches() || isInt(str);
	}
	
	public static boolean isRate(String str) {
		
		boolean result = isDouble(str, 2);
		
		if(result == true && str != null && Double.parseDouble(str) > 100){
			
			result = false;
		}
		return result;
	}
	
	public static boolean isDate(String str) {  
		
		if(IsEmptyUtil.isEmpty(str)){
			return true;
		}
        
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    	boolean isDate = true;
    	
    	try {
    		
    		df.parse(str);
		} catch (Exception e) {
			
			isDate = false;
		}
    	return isDate;
    }
	
	public static String numFormat(Number num, String numFormat) {  
		
		DecimalFormat format = new DecimalFormat();
		format.applyPattern(numFormat);
		return format.format(num);
	}
	
	public static double numFormat4D(Number num, String numFormat) {  
		
		DecimalFormat format = new DecimalFormat();
		format.applyPattern(numFormat);
		return Double.parseDouble(format.format(num));
	}
	
	public static String getStr(String str) {  
		
		return str == null || "unDefined".equals(str) ? "" : str;
	}
	
	public static String getStr8(String str) {  
		
		return str == null ? "        " : str;
	}
	
	public static boolean isZero(String num) {  
		
		if(new BigDecimal(num).compareTo(new BigDecimal(0)) == 0){
			
			return true;
		}else{
			
			return false;
		}
	}
	
	public static String convertStringToUTF8(String str) throws Exception{  
		
		return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", " ");
	}
}
