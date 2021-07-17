package com.logic;

import com.dao.UserCustomerLinkRefDAO;
import com.entity.UserCustomerLinkRef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.IsEmptyUtil;
import com.util.PaginationSupport;

@Repository
public class UserCustomerLinkRefLogic{
	
	private static final Logger log = LoggerFactory.getLogger(UserCustomerLinkRefLogic.class);
	
	@Resource
    private UserCustomerLinkRefDAO userCustomerLinkRefDAO; 
    
	public UserCustomerLinkRef findByKey(String userIdSelected, String customerIdSelected) {
		log.debug("finding UserCustomerLinkRef instance with key:" + userIdSelected + ", " + customerIdSelected);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userIdSelected);
			map.put("customerId", customerIdSelected);
			List<UserCustomerLinkRef> userCustomerLinkRefList = userCustomerLinkRefDAO.selectUserCustomerLinkRefByCondition(map);
			if(IsEmptyUtil.isEmpty(userCustomerLinkRefList)){
				return null;
			}else{
				return userCustomerLinkRefList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("finding UserCustomerLinkRef instance with key", re);
			throw re;
		}
	}
	
	public List<UserCustomerLinkRef> findByProperty(UserCustomerLinkRef userCustomerLinkRef) {
		log.debug("finding UserCustomerLinkRef instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userCustomerLinkRef.getUserId4S());
			map.put("userName", userCustomerLinkRef.getUserName4S());
			map.put("customerId", userCustomerLinkRef.getCustomerId4S());
			map.put("customerName", userCustomerLinkRef.getCustomerName4S());
			map.put("startIndex", userCustomerLinkRef.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<UserCustomerLinkRef> userCustomerLinkRefList = userCustomerLinkRefDAO.selectUserCustomerLinkRefByCondition(map);
			return userCustomerLinkRefList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(UserCustomerLinkRef userCustomerLinkRef) {
		log.debug("finding UserCustomerLinkRef instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userCustomerLinkRef.getUserId4S());
			map.put("userName", userCustomerLinkRef.getUserName4S());
			map.put("customerId", userCustomerLinkRef.getCustomerId4S());
			map.put("customerName", userCustomerLinkRef.getCustomerName4S());
			int count = userCustomerLinkRefDAO.selectUserCustomerLinkRefCountByCondition(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int delete(UserCustomerLinkRef userCustomerLinkRef) {
		log.debug("delete with key: " + userCustomerLinkRef.getUserId() + ", " + userCustomerLinkRef.getCustomerId());
		try {
			int count = userCustomerLinkRefDAO.delete(userCustomerLinkRef);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(UserCustomerLinkRef userCustomerLinkRef) {
		log.debug("insert userCustomerLinkRef: " + userCustomerLinkRef.getUserId() + ", " + userCustomerLinkRef.getCustomerId());
		try {
			int count = userCustomerLinkRefDAO.insert(userCustomerLinkRef);
			return count;
		} catch (RuntimeException re) {
			log.error("insert userCustomerLinkRef failed", re);
			throw re;
		}
	}
	
	public int update(UserCustomerLinkRef userCustomerLinkRef) {
		log.debug("update userCustomerLinkRef: " + userCustomerLinkRef.getUserId() + ", " + userCustomerLinkRef.getCustomerId());
		try {
			int count = userCustomerLinkRefDAO.update(userCustomerLinkRef);
			return count;
		} catch (RuntimeException re) {
			log.error("update userCustomerLinkRef failed", re);
			throw re;
		}
	}
}