package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.User;

@Repository
public interface UserDAO{
	
	User selectUserById(Map<String, Object> map);

	List<User> selectUserByCondition(Map<String, Object> map);
	
	int selectUserCountByCondition(Map<String, Object> map);
	
	int deleteUserById(User user);
	
	int passwordReset(User user);
	
	int passwordUpdate(User user);
	
	int insertUser(User user);
	
	int updateUser(User user);
}