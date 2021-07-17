package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.UserCustomerLinkRef;
import com.exception.ValiationException;
import com.logic.UserCustomerLinkRefLogic;
import com.util.DbCommonUtil;

@Service
public class UserCustomerLinkRefService{
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	private UserCustomerLinkRefLogic userCustomerLinkRefLogic = null;
	public void setUserCustomerLinkRefLogic(UserCustomerLinkRefLogic userCustomerLinkRefLogic) {
		this.userCustomerLinkRefLogic = userCustomerLinkRefLogic;
	}
	
	public UserCustomerLinkRef findByKey(String userIdSelected, String customerIdSelected){
		
		UserCustomerLinkRef userCustomerLinkRef = userCustomerLinkRefLogic.findByKey(userIdSelected, customerIdSelected);
		return userCustomerLinkRef;
    }
	
	public UserCustomerLinkRef userCustomerLinkRefSearch(UserCustomerLinkRef userCustomerLinkRef) throws Exception{
		
		int totalCount = 0;
		
		totalCount = userCustomerLinkRefLogic.countFindByProperty(userCustomerLinkRef);
		if(totalCount != 0){
			
			List<UserCustomerLinkRef> userCustomerLinkRefList = userCustomerLinkRefLogic.findByProperty(userCustomerLinkRef);
			userCustomerLinkRef.setResultUserCustomerLinkRefList(userCustomerLinkRefList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		userCustomerLinkRef.getPageInfo().setTotalCount(totalCount);
		
        return userCustomerLinkRef;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public UserCustomerLinkRef add(UserCustomerLinkRef userCustomerLinkRef) throws Exception{
		
		//客户存在验证
		Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, userCustomerLinkRef.getCustomerId());
		if(updateTime == null){
			
			throw new ValiationException(Message.NOT_EXISTS_CUSTOMER);
		}
		
		//重复主键确认
		if(userCustomerLinkRefLogic.findByKey(userCustomerLinkRef.getUserId(), userCustomerLinkRef.getCustomerId()) != null){
			throw new Exception(Message.DUPLICATED_ERROR);
		}
		DbCommonUtil.setCreateCommon(userCustomerLinkRef);
		try {
			userCustomerLinkRefLogic.insert(userCustomerLinkRef);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return userCustomerLinkRef;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(UserCustomerLinkRef userCustomerLinkRef) throws Exception{
		
		//客户存在验证
		Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, userCustomerLinkRef.getCustomerId());
		if(updateTime == null){
			
			throw new ValiationException(Message.NOT_EXISTS_CUSTOMER);
		}
		
		DbCommonUtil.setUpdateCommon(userCustomerLinkRef);
		
		int count = 0;
		try {
			count = userCustomerLinkRefLogic.update(userCustomerLinkRef);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String userIdSelected, String customerIdSelected) throws Exception{
		
		UserCustomerLinkRef userCustomerLinkRef = new UserCustomerLinkRef();
		userCustomerLinkRef.setUserId(userIdSelected);
		userCustomerLinkRef.setCustomerId(customerIdSelected);
		
		int count = 0;
		try {
			count = userCustomerLinkRefLogic.delete(userCustomerLinkRef);
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
}
