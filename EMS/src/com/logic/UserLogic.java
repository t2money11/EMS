package com.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.constant.CommonData;
import com.dao.UserDAO;
import com.entity.User;
import com.util.EncodeUtil;
import com.util.PaginationSupport;

@Repository
public class UserLogic {

	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	
	@Resource
    private UserDAO userDAO;  
    
	public User login(User user) {
		log.debug("login in with userID: " + user.getUserId());
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId());
			map.put("password", EncodeUtil.generatePassword(user.getPassword()));
			user = userDAO.selectUserById(map);
			return user;
		} catch (RuntimeException re) {
			log.error("login failed", re);
			throw re;
		}
	}
	
	public User findByUserId(String userId) {
		log.debug("finding User instance with property: userId: " + userId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("category", CommonData.getCmnData().getLoginInfo().getCategory());
			User user = userDAO.selectUserById(map);
			return user;
		} catch (RuntimeException re) {
			log.error("find by UserId", re);
			throw re;
		}
	}
	
	public List<User> findByProperty(User user) {
		log.debug("finding User instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId4S());
			map.put("userNameC", user.getUserNameC4S());
			map.put("userNameE", user.getUserNameE4S());
			map.put("category", CommonData.getCmnData().getLoginInfo().getCategory());
			map.put("startIndex", user.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<User> userList = userDAO.selectUserByCondition(map);
			return userList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(User user) {
		log.debug("finding User instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId4S());
			map.put("userNameC", user.getUserNameC4S());
			map.put("userNameE", user.getUserNameE4S());
			int count = userDAO.selectUserCountByCondition(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public int delete(User user) {
		log.debug("delete with userID: " + user.getUserId());
		try {
			int count = userDAO.deleteUserById(user);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int passwordReset(User user) {
		log.debug("passwordReset with userID: " + user.getUserId());
		try {
			int count = userDAO.passwordReset(user);
			return count;
		} catch (RuntimeException re) {
			log.error("passwordReset failed", re);
			throw re;
		}
	}
	
	public int passwordUpdate(User user) {
		log.debug("passwordUpdate with userID: " + user.getUserId());
		try {
			int count = userDAO.passwordUpdate(user);
			return count;
		} catch (RuntimeException re) {
			log.error("passwordReset failed", re);
			throw re;
		}
	}
	
	public int insertUser(User user) {
		log.debug("insert user, userID: " + user.getUserId());
		try {
			int count = userDAO.insertUser(user);
			return count;
		} catch (RuntimeException re) {
			log.error("insert user failed", re);
			throw re;
		}
	}
	
	public int updateUser(User user) {
		log.debug("update user, userID: " + user.getUserId());
		try {
			int count = userDAO.updateUser(user);
			return count;
		} catch (RuntimeException re) {
			log.error("update user failed", re);
			throw re;
		}
	}
}
