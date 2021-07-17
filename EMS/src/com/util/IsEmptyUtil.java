package com.util;

import java.util.List;
import com.constant.NullCollection;

public class IsEmptyUtil {
	
	public static boolean isEmpty(String input) {  
		
		boolean isEmpty = false;
		if(input == null || input.isEmpty()){
			
			isEmpty = true;
		}
		return isEmpty;
	}
	
	public static boolean isEmpty(List<?> inputList) {  
		
		boolean isEmpty = true;
		if(inputList != null){
			
			inputList.removeAll(new NullCollection());
			if(inputList.size() > 0){
				
				isEmpty = false;
			}
		}
		return isEmpty;
	}
}
