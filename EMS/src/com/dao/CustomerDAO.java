package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.Customer;

@Repository
public interface CustomerDAO{
	
	Customer findByKey(Map<String, Object> map);
	
	List<Customer> findByProperty(Map<String, Object> map);

	int countFindByProperty(Map<String, Object> map);
	
	int delete(Customer customer);
	
	int insert(Customer customer);
	
	int update(Customer customer);
}