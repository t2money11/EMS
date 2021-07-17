package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.Production;

@Repository
public interface ProductionDAO{
	
	Production findByKey(Map<String, Object> map);
	
	List<Production> findByProperty(Map<String, Object> map);

	int countFindByProperty(Map<String, Object> map);
	
	int delete(Production production);
	
	int insert(Production production);
	
	int update(Production production);
}