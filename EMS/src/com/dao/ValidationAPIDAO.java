package com.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface ValidationAPIDAO {

	Timestamp getUpdateTime(Map<String, Object> map);
	
	int setUpdateTime(Map<String, Object> map);
	
	List<String> validateUnConfirmedR(Map<String, Object> map);
	
	List<String> validateUnConfirmedC(Map<String, Object> map);
	
	int validateCU(Map<String, Object> map);
	
	int validatePVC(Map<String, Object> map);
	
	int validateIDtl(Map<String, Object> map);
	
	int validatePT(Map<String, Object> map);
	
	int validateP(Map<String, Object> map);
	
	int validateV(Map<String, Object> map);
	
	int validateC(Map<String, Object> map);
	
	int validateCode(Map<String, Object> map);
	
}
