package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.Vendor;

@Repository
public interface VendorDAO{
	
	Vendor findByKey(Map<String, Object> map);
	
	List<Vendor> findByProperty(Map<String, Object> map);

	int countFindByProperty(Map<String, Object> map);
	
	int delete(Vendor vendor);
	
	int insert(Vendor vendor);
	
	int update(Vendor vendor);
}