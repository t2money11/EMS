package com.logic;

import com.constant.CommonData;
import com.dao.CustomerDAO;
import com.entity.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.PaginationSupport;

@Repository
public class CustomerLogic{
	
	private static final Logger log = LoggerFactory.getLogger(CustomerLogic.class);
	
	@Resource
    private CustomerDAO customerDAO; 
    
	public Customer findByKey(String customerIdSelected) {
		log.debug("finding Customer instance with key:" + customerIdSelected);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customerIdSelected);
			return customerDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Customer instance with key", re);
			throw re;
		}
	}
	
	public List<Customer> findByProperty(Customer customer) {
		log.debug("finding Customer instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customer.getCustomerId4S() == null ? null : customer.getCustomerId4S().toUpperCase());
			map.put("customerName", customer.getCustomerName4S() == null ? null : customer.getCustomerName4S().toUpperCase());
			map.put("location", customer.getLocation4S() == null ? null : customer.getLocation4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
					&& !"1".equals(customer.getNoAuth())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("startIndex", customer.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<Customer> customerList = customerDAO.findByProperty(map);
			return customerList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Customer customer) {
		log.debug("finding Customer instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customer.getCustomerId4S() == null ? null : customer.getCustomerId4S().toUpperCase());
			map.put("customerName", customer.getCustomerName4S() == null ? null : customer.getCustomerName4S().toUpperCase());
			map.put("location", customer.getLocation4S() == null ? null : customer.getLocation4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
					&& !"1".equals(customer.getNoAuth())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			int count = customerDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int delete(Customer customer) {
		log.debug("delete with key: " + customer.getCustomerId());
		try {
			int count = customerDAO.delete(customer);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Customer customer) {
		log.debug("insert customer: " + customer.getCustomerId());
		try {
			int count = customerDAO.insert(customer);
			return count;
		} catch (RuntimeException re) {
			log.error("insert customer failed", re);
			throw re;
		}
	}
	
	public int update(Customer customer) {
		log.debug("update customer: " + customer.getCustomerId());
		try {
			int count = customerDAO.update(customer);
			return count;
		} catch (RuntimeException re) {
			log.error("update customer failed", re);
			throw re;
		}
	}
}