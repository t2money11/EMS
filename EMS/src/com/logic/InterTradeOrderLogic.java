package com.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.dao.InterTradeOrderDAO;
import com.entity.InterTradeOrder;

@Repository
public class InterTradeOrderLogic {

	private static final Logger log = LoggerFactory.getLogger(InterTradeOrderLogic.class);
	
	@Resource
    private InterTradeOrderDAO interTradeOrderDAO; 
	
	public List<InterTradeOrder> findByTradeOrderId(String tradeOrderId) {
		log.debug("finding InterTradeOrder instance with tradeOrderId");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			List<InterTradeOrder> interTradeOrderList = interTradeOrderDAO.findByTradeOrderId(map);
			return interTradeOrderList;
		} catch (RuntimeException re) {
			log.error("find by tradeOrderId failed", re);
			throw re;
		}
	}
	
	public List<InterTradeOrder> findAdvancePayment(String receiptId) {
		log.debug("finding AdvancePayment instance with receiptId");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptId);
			List<InterTradeOrder> interTradeOrderList = interTradeOrderDAO.findAdvancePayment(map);
			return interTradeOrderList;
		} catch (RuntimeException re) {
			log.error("find by receiptId failed", re);
			throw re;
		}
	}
	
	public int deleteByTradeOrderId(String tradeOrderId) {
		log.debug("delete with key: " + tradeOrderId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			int count = interTradeOrderDAO.deleteByTradeOrderId(map);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(InterTradeOrder interTradeOrder) {
		log.debug("insert interTradeOrder: " + interTradeOrder.getTradeOrderId());
		try {
			int count = interTradeOrderDAO.insert(interTradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("insert interTradeOrder failed", re);
			throw re;
		}
	}
	
	public int update(InterTradeOrder interTradeOrder) {
		log.debug("update interTradeOrder: " + interTradeOrder.getTradeOrderId());
		try {
			int count = interTradeOrderDAO.update(interTradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("update interTradeOrder failed", re);
			throw re;
		}
	}
}
