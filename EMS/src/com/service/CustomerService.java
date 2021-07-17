package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.Customer;
import com.exception.ValiationException;
import com.logic.CustomerLogic;
import com.util.DbCommonUtil;

@Service
public class CustomerService{
	
	private CustomerLogic customerLogic = null;
	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public Customer findByKey(String customerIdSelected){
		
		Customer customer = customerLogic.findByKey(customerIdSelected);
		return customer;
    }
	
	public Customer customerSearch(Customer customer) throws Exception{
		
		int totalCount = 0;
		
		totalCount = customerLogic.countFindByProperty(customer);
		if(totalCount != 0){
			
			List<Customer> customerList = customerLogic.findByProperty(customer);
			customer.setResultCustomerList(customerList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		customer.getPageInfo().setTotalCount(totalCount);
		
        return customer;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Customer customer) throws Exception{
		
		//重复主键确认
		if(customerLogic.findByKey(customer.getCustomerId()) != null){
			throw new Exception(Message.DUPLICATED_CUSTOMER);
		}
		DbCommonUtil.setCreateCommon(customer);
		try {
			customerLogic.insert(customer);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return customer.getCustomerId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Customer customer) throws Exception{
		
		//更新冲突验证
		Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, customer.getCustomerId());
		if(updateTime == null ||customer.getUpdateTime().compareTo(updateTime) != 0){
			
			throw new ValiationException(Message.SYNCHRONIZED_ERROR);
		}
		
		DbCommonUtil.setUpdateCommon(customer);
		int count = 0;
		try {
			count = customerLogic.update(customer);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String customerIdSelected) throws Exception{
		
		Customer customer = new Customer();
		customer.setCustomerId(customerIdSelected);
		int count = 0;
		try {
			//删除客户之前，验证客户是否已经被引用
			if(validationAPIService.validateC(customerIdSelected)){
				throw new ValiationException(Message.USED_CUSTOMER);
			}
			count = customerLogic.delete(customer);
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
}
