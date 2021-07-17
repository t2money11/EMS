package com.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.constant.CommonData;

public class DbCommonUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DbCommonUtil.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setUpdateCommon(Object obj){
		
		try {
			
			Class objClass = obj.getClass().getSuperclass();
			
	        Method setUpdateTime = objClass.getDeclaredMethod("setUpdateTime", Timestamp.class);
	        Method setUpdateUser = objClass.getDeclaredMethod("setUpdateUser", String.class);
	        setUpdateTime.invoke(obj, new Timestamp(System.currentTimeMillis()));
	        setUpdateUser.invoke(obj, CommonData.getCmnData().getLoginInfo().getUserId());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setCreateCommon(Object obj){
		
		try {
			
			Class objClass = obj.getClass().getSuperclass();
			
	        Method setCreateTime = objClass.getDeclaredMethod("setCreateTime", Timestamp.class);
	        Method setCreateUser = objClass.getDeclaredMethod("setCreateUser", String.class);
	        Method setUpdateTime = objClass.getDeclaredMethod("setUpdateTime", Timestamp.class);
	        Method setUpdateUser = objClass.getDeclaredMethod("setUpdateUser", String.class);
	        
			setCreateTime.invoke(obj, new Timestamp(System.currentTimeMillis()));
			setCreateUser.invoke(obj, CommonData.getCmnData().getLoginInfo().getUserId());
			setUpdateTime.invoke(obj, new Timestamp(System.currentTimeMillis()));
			setUpdateUser.invoke(obj, CommonData.getCmnData().getLoginInfo().getUserId());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}
