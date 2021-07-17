package com.logic;

import com.context.ConstantParam;
import com.dao.CodeDAO;
import com.entity.Code;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CodeLogic{
	
	private static final Logger log = LoggerFactory.getLogger(CodeLogic.class);
	
	@Resource
    private CodeDAO codeDAO; 
    
	public List<Code> findCodeByCategoryId(Code code) {
		log.debug("finding Code instance with CategoryId");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryId", "P_TYPE");
			List<Code> codeList = codeDAO.findCodeByCategoryId(map);
			return codeList;
		} catch (RuntimeException re) {
			log.error("find by CategoryId failed", re);
			throw re;
		}
	}
	
	public Code findByKey(Code code) {
		log.debug("finding Code instance with key");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryId", "P_TYPE");
			map.put("codeId", code.getCodeId());
			return codeDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("find by key failed", re);
			throw re;
		}
	}
	
	public int delete(Code code) {
		log.debug("delete with key: " + code.getCodeId());
		try {
			int count = codeDAO.delete(code);
			codeMapRefresh();
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Code code) {
		log.debug("insert code: " + code.getCodeId());
		try {
			int count = codeDAO.insert(code);
			codeMapRefresh();
			return count;
		} catch (RuntimeException re) {
			log.error("insert code failed", re);
			throw re;
		}
	}
	
	private void codeMapRefresh(){
		
		LinkedHashMap<String, Map<String, String>> codeListMap = null;
		if(ConstantParam.codeMap != null){
			
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