package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.Code;

@Repository
public interface CodeDAO{
	
	List<Code> selectCodeByCategoryId();
	
	List<Code> findCodeByCategoryId(Map<String, Object> map);
	
	Code findByKey(Map<String, Object> map);
	
	int delete(Code code);
	
	int insert(Code code);
	
}