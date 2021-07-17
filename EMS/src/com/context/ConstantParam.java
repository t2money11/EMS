package com.context;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantParam {

	public static final String SESSION_KEY_LOGIN_INFO = "loginUser";
	
	public static final String DB_Del_FLG_OFF = "0";
	public static final String DB_Del_FLG_ON = "1";
	
	public static String COMPLAINT_PIC_ROOT_TEMP_REALPATH = "C:/tool/apache-tomcat-7.0.54/webapps/upload_complaint_pic";
	public static String COMPLAINT_PIC_ROOT_TEMP = "/upload_complaint_pic";
	
	public static String PRODUCTION_PIC_ROOT_TEMP_REALPATH = "C:/tool/apache-tomcat-7.0.54/webapps/upload_production_pic";
	public static String PRODUCTION_PIC_ROOT_TEMP = "/upload_production_pic";
	
	public static final String GENDER = "GENDER";
	public static final String HIGHESTEDUCATION = "HIGHESTEDUCATION";
	public static final String VACATION = "VACATION";
	public static final String C_STATUS = "C_STATUS";
	public static final String T_STATUS = "T_STATUS";
	public static final String CATEGORY = "CATEGORY";
	public static final String P_TYPE = "P_TYPE";
	public static final String SEND_MODE = "SEND_MODE";
	
	public static final String PRODUCTIONID_4_TITLE = "0";
	
	public static final String SALES_CONTRACT_TEXT = "The Sales Contract prices are based on the current market floating exchange rate 1:%s daily published from Bank of China and export tax rebate rate of hardware %s%% according to Export Tax Rebate Norm in PRC. The final USD payment should “fix” this RMB rate hereinbefore.Once the Chinese Export Tax Rebate Rate Norm is announced invalid by Chinese government, it will align with the actual Export Tax Rebate Rate Norm as per Chinese government."; 
	
	public static final String SPECIAL_PRODUCTION_ID = "87089999";
	
	public static LinkedHashMap<String, Map<String, String>> codeMap = null;
}
