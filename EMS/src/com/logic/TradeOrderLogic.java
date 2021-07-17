package com.logic;

import com.constant.CommonData;
import com.dao.TradeOrderDAO;
import com.entity.TradeOrder;
import com.entity.TradeOrderPop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.DateUtil;
import com.util.PaginationSupport;

@Repository
public class TradeOrderLogic{
	
	private static final Logger log = LoggerFactory.getLogger(TradeOrderLogic.class);
	
	@Resource
    private TradeOrderDAO tradeOrderDAO; 
    
	public int validateDuplicatedPOorContractNo(String tradeOrderId, String contractNo, String po, String customerId) {
		log.debug("validateDuplicatedPOorContractNo");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			map.put("po", po);
			map.put("contractNo", contractNo);
			map.put("customerId", customerId);
			int count = tradeOrderDAO.validateDuplicatedPOorContractNo(map);
			return count;
		} catch (RuntimeException re) {
			log.error("validateDuplicatedPOorContractNo", re);
			throw re;
		}
	}
	
	public String getTradeOrderId4New() {
		log.debug("getTradeOrderId4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nowDate", DateUtil.getNowDateString(DateUtil.DATE_FORMAT2));
			String tradeOrderId = tradeOrderDAO.getTradeOrderId4New(map);
			if(tradeOrderId == null){
				tradeOrderId = "T-".concat(DateUtil.getNowDateString(DateUtil.DATE_FORMAT2)).concat("-001");
			}else{
				index = Integer.parseInt(tradeOrderId.substring(11));
				String indexString = String.format("%03d", ++index);
				tradeOrderId = tradeOrderId.substring(0, 11).concat(indexString);
			}
			return tradeOrderId;
		} catch (RuntimeException re) {
			log.error("getTradeOrderId4New", re);
			throw re;
		}
	}
	
	public String getContractNo4New(String customerId, String customerName) {
		log.debug("getContractNo4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			String nowYear = DateUtil.getNowDateString(DateUtil.DATE_FORMAT8).substring(2, 4);
			map.put("nowYear", nowYear);
			map.put("customerId", customerId);
			String contractNo = tradeOrderDAO.getContractNo4New(map);
			if(contractNo == null){
				contractNo = "RP".concat(nowYear).concat("-").concat(customerId).concat("-01 ").concat(customerName);
			}else{
				index = Integer.parseInt(contractNo.substring(9, 11));
				String indexString = String.format("%02d", ++index);
				contractNo = "RP".concat(nowYear).concat("-").concat(customerId).concat("-").concat(indexString).concat(" ").concat(customerName);
			}
			return contractNo;
		} catch (RuntimeException re) {
			log.error("getContractNo4New", re);
			throw re;
		}
	}
	
	public TradeOrder findByKey(String tradeOrderIdSelected, String poSelected, String customerIdSelected) {
		log.debug("finding TradeOrder instance with key:" + tradeOrderIdSelected);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderIdSelected);
			map.put("po", poSelected);
			map.put("customerId", customerIdSelected);
			return tradeOrderDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding TradeOrder instance with key", re);
			throw re;
		}
	}
	
	public TradeOrder findLastTradeOrderByProductionId(String productionId) {
		log.debug("finding Last TradeOrder instance with ProductionId:" + productionId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionId);
			return tradeOrderDAO.findLastTradeOrderByProductionId(map);
		} catch (RuntimeException re) {
			log.error("finding Last TradeOrder instance with ProductionId:" + productionId, re);
			throw re;
		}
	}
		
	public String findPreviousRMBByProductionId(String productionId) {
		log.debug("finding Last Inter Price instance with ProductionId:" + productionId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionId);
			return tradeOrderDAO.findPreviousRMBByProductionId(map);
		} catch (RuntimeException re) {
			log.error("finding Last Inter Price instance with ProductionId:" + productionId, re);
			throw re;
		}
	}
	
	public List<TradeOrder> findByProperty(TradeOrder tradeOrder) {
		log.debug("finding TradeOrder instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrder.getTradeOrderId4S());
			map.put("po", tradeOrder.getPo4S());
			map.put("contractNo", tradeOrder.getContractNo4S());
			map.put("customerId", tradeOrder.getCustomerId4S());
			map.put("productionId", tradeOrder.getProductionId4S() == null ? null : tradeOrder.getProductionId4S().toUpperCase());
			map.put("status", tradeOrder.getStatus4S());
			map.put("vendorId", tradeOrder.getVendorId4S());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("startIndex", tradeOrder.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<TradeOrder> tradeOrderList = tradeOrderDAO.findByProperty(map);
			return tradeOrderList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(TradeOrder tradeOrder) {
		log.debug("finding TradeOrder instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrder.getTradeOrderId4S());
			map.put("po", tradeOrder.getPo4S());
			map.put("contractNo", tradeOrder.getContractNo4S());
			map.put("customerId", tradeOrder.getCustomerId4S());
			map.put("productionId", tradeOrder.getProductionId4S() == null ? null : tradeOrder.getProductionId4S().toUpperCase());
			map.put("status", tradeOrder.getStatus4S());
			map.put("vendorId", tradeOrder.getVendorId4S());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			int count = tradeOrderDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public List<TradeOrderPop> findPopByProperty(TradeOrderPop tradeOrderPop) {
		log.debug("finding TradeOrderPop instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderPop.getTradeOrderId4S());
			map.put("po", tradeOrderPop.getPo4S());
			map.put("contractNo", tradeOrderPop.getContractNo4S());
			map.put("customerId", tradeOrderPop.getCustomerId4S());
			map.put("productionId", tradeOrderPop.getProductionId4S());
			map.put("isStatusRefFlg", tradeOrderPop.getIsStatusRefFlg());
			map.put("startIndex", tradeOrderPop.getPageInfo().getStartIndex());
			map.put("pageSize", tradeOrderPop.getPageInfo().getPageSize());
			List<TradeOrderPop> tradeOrderList = tradeOrderDAO.findPopByProperty(map);
			
			for (TradeOrderPop temp : tradeOrderList) {
				
				int quantitySent = temp.getQuantitySent() == null ? 0 : Integer.parseInt(temp.getQuantitySent());
				temp.settQuantityNotSent(String.valueOf(Integer.parseInt(temp.gettQuantity()) - quantitySent));
				temp.setiQuantityNotSent(String.valueOf(Integer.parseInt(temp.getiQuantity()) - quantitySent));
			}
			return tradeOrderList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindPopByProperty(TradeOrderPop tradeOrderPop) {
		log.debug("finding TradeOrder instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderPop.getTradeOrderId4S());
			map.put("po", tradeOrderPop.getPo4S());
			map.put("contractNo", tradeOrderPop.getContractNo4S());
			map.put("customerId", tradeOrderPop.getCustomerId4S());
			map.put("productionId", tradeOrderPop.getProductionId4S());
			map.put("isStatusRefFlg", tradeOrderPop.getIsStatusRefFlg());
			int count = tradeOrderDAO.countFindPopByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int delete(TradeOrder tradeOrder) {
		log.debug("delete with key: " + tradeOrder.getTradeOrderId());
		try {
			int count = tradeOrderDAO.delete(tradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(TradeOrder tradeOrder) {
		log.debug("insert tradeOrder: " + tradeOrder.getTradeOrderId());
		try {
			int count = tradeOrderDAO.insert(tradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("insert tradeOrder failed", re);
			throw re;
		}
	}
	
	public int update(TradeOrder tradeOrder) {
		log.debug("update tradeOrder: " + tradeOrder.getTradeOrderId());
		try {
			int count = tradeOrderDAO.update(tradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("update tradeOrder failed", re);
			throw re;
		}
	}
	
	public int updateStatus(TradeOrder tradeOrder) {
		log.debug("updateStatus tradeOrder: " + tradeOrder.getTradeOrderId());
		try {
			int count = tradeOrderDAO.updateStatus(tradeOrder);
			return count;
		} catch (RuntimeException re) {
			log.error("updateStatus tradeOrder failed", re);
			throw re;
		}
	}
	
	public int count4Complaint(String tradeOrderId, String productionId, String versionNo) {
		log.debug("finding TradeOrder instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			int count = tradeOrderDAO.count4Complaint(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
}