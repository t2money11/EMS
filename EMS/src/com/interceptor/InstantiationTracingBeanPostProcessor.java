package com.interceptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.context.ConstantParam;
import com.dao.CodeDAO;
import com.entity.Code;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    
	@Resource
    private CodeDAO codeDAO;  
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(event.getApplicationContext().getParent() == null){
			
			LinkedHashMap<String, Map<String, String>> codeListMap = null;
			
			if(ConstantParam.codeMap == null){
				
				List<Code> codeList = codeDAO.selectCodeByCategoryId();
				codeListMap = new LinkedHashMap<String, Map<String, String>>();
				String oldCategoryId = null;
				Map<String, String> codeMapList = null;
				for (Code code : codeList) {
					
					if(!code.getCategoryId().equals(oldCategoryId)){
						if(codeMapList != null){
							codeListMap.put(oldCategoryId, codeMapList);  
						}
						codeMapList = new LinkedHashMap<String, String>();
						oldCategoryId = code.getCategoryId();
						codeMapList.put("", "");
					}
					codeMapList.put(code.getCodeId(), code.getCodeName());
				}
				codeListMap.put(oldCategoryId, codeMapList);
				ConstantParam.codeMap = codeListMap;
			}
		}
	}
}
