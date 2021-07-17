package com.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.dao.InterTradeOrderDtlDAO;
import com.entity.InterTradeOrderDtl;

@Repository
public class InterTradeOrderDtlLogic {

	private static final Logger log = LoggerFactory.getLogger(InterTradeOrderDtlLogic.class);
	
	@Resource
    private InterTradeOrderDtlDAO interTradeOrderDtlDAO; 
	
	public List<InterTradeOrderDtl> findByInterTradeOrderId(String interTradeOrderId) {
		log.debug("finding InterTradeOrderDtl instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("interTradeOrderId", interTradeOrderId);
			List<InterTradeOrderDtl> interTradeOrderDtlList = interTradeOrderDtlDAO.findByInterTradeOrderId(map);
			return interTradeOrderDtlList;
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
			int count = interTradeOrderDtlDAO.deleteByTradeOrderId(map);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(InterTradeOrderDtl interTradeOrderDtl) {
		log.debug("insert interTradeOrderDtl: " + interTradeOrderDtl.getInterTradeOrderId() + "," + interTradeOrderDtl.getProductionId());
		try {
			int count = interTradeOrderDtlDAO.insert(interTradeOrderDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("insert interTradeOrderDtl failed", re);
			throw re;
		}
	}
	
	public int update(InterTradeOrderDtl interTradeOrderDtl) {
		log.debug("update interTradeOrderDtl: " + interTradeOrderDtl.getInterTradeOrderId() + "," + interTradeOrderDtl.getProductionId());
		try {
			int count = interTradeOrderDtlDAO.update(interTradeOrderDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("update interTradeOrderDtl failed", re);
			throw re;
		}
	}
}
