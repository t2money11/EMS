package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.TradeOrderDtl;

@Repository
public interface TradeOrderDtlDAO{
	
	List<TradeOrderDtl> findByTradeOrderId(Map<String, Object> map);

	int deleteByTradeOrderId(Map<String, Object> map);
	
	int insert(TradeOrderDtl tradeOrderDtl);
	
	int update(TradeOrderDtl tradeOrderDtl);
}