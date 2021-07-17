package com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Calendar;
import java.util.Date;  
import java.util.Locale;
  
public class DateUtil {  
  
	public static final String DATE_FORMAT1 = "yyyy-MM-dd";
	public static final String DATE_FORMAT2 = "yyyyMMdd";
	public static final String DATE_FORMAT3 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT4 = "yyyyMMdd_HHmmss";
	public static final String DATE_FORMAT5 = "MMM dd, yyyy";
	public static final String DATE_FORMAT6 = "yyyy年MM月dd日";
	public static final String DATE_FORMAT7 = "yyyy.MM.dd";
	public static final String DATE_FORMAT8 = "yyyy/MM/dd";
	
    public static String dateToString(Date date, String dateFormat){  
        
    	SimpleDateFormat df = new SimpleDateFormat(dateFormat);  
    	String resultDate = null;
    	
    	try {
    		resultDate = df.format(date);
		} catch (Exception e) {
			return null;
		}
    	return resultDate;
    }  
    
    public static Date stringToDate(String dateString, String dateFormat){  
        
    	SimpleDateFormat df = new SimpleDateFormat(dateFormat); 
    	Date resultDate = null;
    	
    	try {
    		resultDate = df.parse(dateString);
		} catch (Exception e) {
			return null;
		}
    	return resultDate;
    }  
    
    public static String timestampToString(Timestamp timestamp){  
        
    	SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT3);  
    	String resultDate = null;
    	
    	try {
    		resultDate = df.format(timestamp);
		} catch (Exception e) {
			return "";
		}
    	return resultDate;
    }  
    
    public static Timestamp stringToTimestamp(String timestampString){  
        
    	SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT3);  
    	Timestamp resultDate = null;
    	
    	try {
    		resultDate = new Timestamp(df.parse(timestampString).getTime());
		} catch (Exception e) {
			return null;
		}
    	return resultDate;
    }  
    
    public static String getNowDateString(String dateFormat) {  
        
    	SimpleDateFormat df = new SimpleDateFormat(dateFormat);
    	return df.format(new Date());
    }
    
    public static String getNowDateM1DString(String dateString, String dateFormat){  
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = df.format(c.getTime());
		return dayBefore;
    }
    
    public static String stringToString(String dateString, String dateFormatFrom, String dateFormatTo, Locale locale) throws ParseException {
    	
    	SimpleDateFormat df = new SimpleDateFormat(dateFormatTo, Locale.ENGLISH);
    	String resultDate = null;
    	
    	try {
    		resultDate = df.format(stringToDate(dateString, dateFormatFrom));
		} catch (Exception e) {
			return "";
		}
    	return resultDate;
    }
    
    public static void main(String[] args) throws ParseException {
		
	}
  
}  