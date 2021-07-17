package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.InterTradeOrder;

@Repository
public interface InterTradeOrderDAO{
	
	List<InterTradeOrder> findByTradeOrderId(Map<String, Object> map);
	
	List<InterTradeOrder> findAdvancePayment(Map<String, Object> map);

	int deleteByTradeOrderId(Map<String, Object> map);
	
	int insert(InterTradeOrder interTradeOrder);
	
	int update(InterTradeOrder interTradeOrder);
}