package com.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.dao.TradeOrderDtlDAO;
import com.entity.TradeOrderDtl;

@Repository
public class TradeOrderDtlLogic {

	private static final Logger log = LoggerFactory.getLogger(TradeOrderDtlLogic.class);
	
	@Resource
    private TradeOrderDtlDAO tradeOrderDtlDAO; 
	
	public List<TradeOrderDtl> findByTradeOrderId(String tradeOrderId) {
		log.debug("finding TradeOrderDtl instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			List<TradeOrderDtl> tradeOrderDtlList = tradeOrderDtlDAO.findByTradeOrderId(map);
			return tradeOrderDtlList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int deleteByTradeOrderId(String tradeOrderId) {
		log.debug("delete with key: " + tradeOrderId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			int count = tradeOrderDtlDAO.deleteByTradeOrderId(map);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(TradeOrderDtl tradeOrderDtl) {
		log.debug("insert tradeOrderDtl: " + tradeOrderDtl.getTradeOrderId() + "," + tradeOrderDtl.getProductionId());
		try {
			int count = tradeOrderDtlDAO.insert(tradeOrderDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("insert tradeOrderDtl failed", re);
			throw re;
		}
	}
	
	public int update(TradeOrderDtl tradeOrderDtl) {
		log.debug("update tradeOrderDtl: " + tradeOrderDtl.getTradeOrderId() + "," + tradeOrderDtl.getProductionId());
		try {
			int count = tradeOrderDtlDAO.update(tradeOrderDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("update tradeOrderDtl failed", re);
			throw re;
		}
	}
}
