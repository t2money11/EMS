package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.UserCustomerLinkRef;

@Repository
public interface UserCustomerLinkRefDAO{
	
	List<UserCustomerLinkRef> selectUserCustomerLinkRefByCondition(Map<String, Object> map);
	
	int selectUserCustomerLinkRefCountByCondition(Map<String, Object> map);
	
	int delete(UserCustomerLinkRef userCustomerLinkRef);
	
	int insert(UserCustomerLinkRef userCustomerLinkRef);
	
	int update(UserCustomerLinkRef userCustomerLinkRef);
}