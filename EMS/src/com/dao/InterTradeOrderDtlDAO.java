package com.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.entity.InterTradeOrderDtl;

@Repository
public interface InterTradeOrderDtlDAO{
	
	List<InterTradeOrderDtl> findByInterTradeOrderId(Map<String, Object> map);

	int deleteByTradeOrderId(Map<String, Object> map);
	
	int insert(InterTradeOrderDtl interTradeOrderDtl);
	
	int update(InterTradeOrderDtl interTradeOrderDtl);
}