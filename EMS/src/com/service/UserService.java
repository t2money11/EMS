package com.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.constant.CommonData;
import com.context.Message;
import com.entity.User;
import com.logic.UserLogic;
import com.util.DbCommonUtil;
import com.util.EncodeUtil;


@Service
public class UserService{
	
	private UserLogic userLogic = null;
	public void setUserLogic(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Transactional(rollbackFor=Exception.class)
	public User login(User user){
		
		return userLogic.login(user);
    }
	
	public User findByUserId(String userId){
		
		return userLogic.findByUserId(userId);
    }
	
	public User userSearch(User user) throws Exception{
		
		int totalCount = 0;
		
		totalCount = userLogic.countFindByProperty(user);
		if(totalCount != 0){
			
			List<User> userList = userLogic.findByProperty(user);
			user.setResultUserList(userList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		user.getPageInfo().setTotalCount(totalCount);
		
        return user;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(User user) throws Exception{
		
		DbCommonUtil.setCreateCommon(user);
		try {
			userLogic.insertUser(user);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		
		return user.getUserId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(User user) throws Exception{
		
		DbCommonUtil.setUpdateCommon(user);
		int count = 0;
		try {
			user.setLoginUserCategory(CommonData.getCmnData().getLoginInfo().getCategory());
			count = userLogic.updateUser(user);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String userIdSelected) throws Exception{
		
		User user = new User();
		user.setUserId(userIdSelected);
		DbCommonUtil.setUpdateCommon(user);
		
		int count = 0;
		try {
			count = userLogic.delete(user);
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void passwordReset(String userIdSelected) throws Exception{
		
		User user = new User();
		user.setUserId(userIdSelected);
		DbCommonUtil.setUpdateCommon(user);
		
		int count = 0;
		try {
			count = userLogic.passwordReset(user);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void passwordUpdate(String userId, String passwordOld, String passwordNew1) throws Exception{
		
		User user = new User();
		user.setUserId(userId);
		user.setPasswordOld(EncodeUtil.generatePassword(passwordOld));
		user.setPasswordNew1(EncodeUtil.generatePassword(passwordNew1));
		DbCommonUtil.setUpdateCommon(user);
		
		int count = 0;
		try {
			count = userLogic.passwordUpdate(user);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.PASSWORDOLD_WRONG);
		}
    }
	
}
