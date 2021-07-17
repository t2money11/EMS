package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.TradeOrder;
import com.entity.TradeOrderPop;

@Repository
public interface TradeOrderDAO{
	
	TradeOrder findByKey(Map<String, Object> map);
	
	List<TradeOrder> findByProperty(Map<String, Object> map);
	
	int countFindByProperty(Map<String, Object> map);
	
	List<TradeOrderPop> findPopByProperty(Map<String, Object> map);
	
	int countFindPopByProperty(Map<String, Object> map);
	
	TradeOrder findLastTradeOrderByProductionId(Map<String, Object> map);
	
	int delete(TradeOrder tradeOrder);
	
	int insert(TradeOrder tradeOrder);
	
	int update(TradeOrder tradeOrder);
	
	int updateStatus(TradeOrder tradeOrder);
	
	String getTradeOrderId4New(Map<String, Object> map);
	
	String getContractNo4New(Map<String, Object> map);
	
	String findPreviousRMBByProductionId(Map<String, Object> map);
	
	int validateDuplicatedPOorContractNo(Map<String, Object> map);
	
	int count4Complaint(Map<String, Object> map);
}