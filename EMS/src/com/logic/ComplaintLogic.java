package com.logic;

import com.constant.CommonData;
import com.dao.ComplaintDAO;
import com.entity.Complaint;
import com.entity.ComplaintDtl;
import com.entity.TradeOrderPop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.PaginationSupport;

@Repository
public class ComplaintLogic{
	
	private static final Logger log = LoggerFactory.getLogger(ComplaintLogic.class);
	
	@Resource
    private ComplaintDAO complaintDAO; 
    
	public String getComplaintId4New() {
		log.debug("getComplaintId4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nowDate", DateUtil.getNowDateString(DateUtil.DATE_FORMAT2));
			String complaintId = complaintDAO.getComplaintId4New(map);
			if(complaintId == null){
				complaintId = "C-" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT2) + "-001";
			}else{
				index = Integer.parseInt(complaintId.substring(11));
				String indexString = String.format("%03d", ++index);
				complaintId = complaintId.substring(0, 11) + indexString;
			}
			return complaintId;
		} catch (RuntimeException re) {
			log.error("getComplaintId4New", re);
			throw re;
		}
	}
	
	public Complaint findByKey(String complaintIdSelected) {
		log.debug("finding Complaint instance with key:" + complaintIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("complaintId", complaintIdSelected);
			return complaintDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Complaint instance with key", re);
			throw re;
		}
	}
	
	public List<ComplaintDtl> findDtlByKey(String complaintIdSelected) {
		log.debug("finding ComplaintDtl instance with key:" + complaintIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("complaintId", complaintIdSelected);
			List<ComplaintDtl> complaintDtlList = complaintDAO.findDtlByKey(map);
			
			return complaintDtlList;
		} catch (RuntimeException re) {
			log.error("finding ComplaintDtl instance with key", re);
			throw re;
		}
	}
	
	public List<ComplaintDtl> findDtlByTradeOrderId(String tradeOrderId, String productionId, String versionNo) {
		log.debug("finding ComplaintDtl instance with key:" + tradeOrderId + ", " + productionId + ", " + versionNo);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			List<ComplaintDtl> complaintDtlList = complaintDAO.findDtlByTradeOrderId(map);
			
			return complaintDtlList;
		} catch (RuntimeException re) {
			log.error("finding ComplaintDtl instance with key", re);
			throw re;
		}
	}
	
	public List<Complaint> findByProperty(Complaint complaint) {
		log.debug("finding Complaint instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("complaintId", complaint.getComplaintId4S() == null ? null : complaint.getComplaintId4S().toUpperCase());
			map.put("customerId", complaint.getCustomerId4S());
			map.put("productionId", complaint.getProductionId4S() == null ? null : complaint.getProductionId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("isTopFlg", complaint.getIsTopFlg());
			map.put("startIndex", complaint.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<Complaint> complaintList = complaintDAO.findByProperty(map);
			return complaintList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Complaint complaint) {
		log.debug("finding Complaint instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("complaintId", complaint.getComplaintId4S() == null ? null : complaint.getComplaintId4S().toUpperCase());
			map.put("customerId", complaint.getCustomerId4S());
			map.put("productionId", complaint.getProductionId4S() == null ? null : complaint.getProductionId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("isTopFlg", complaint.getIsTopFlg());
			int count = complaintDAO.countFindByProperty(map);
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
			map.put("complaintId", tradeOrderPop.getComplaintId4S());
			map.put("tradeOrderId", tradeOrderPop.getTradeOrderId4S());
			map.put("po", tradeOrderPop.getPo4S());
			map.put("contractNo", tradeOrderPop.getContractNo4S());
			map.put("customerId", tradeOrderPop.getCustomerId4S());
			map.put("productionId", tradeOrderPop.getProductionId4S() == null ? null : tradeOrderPop.getProductionId4S().toUpperCase());
			map.put("isStatusRefFlg", tradeOrderPop.getIsStatusRefFlg());
			map.put("startIndex", tradeOrderPop.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<TradeOrderPop> tradeOrderList = complaintDAO.findPopByProperty(map);
			
			for (TradeOrderPop temp : tradeOrderList) {
				
				int quantitySent = temp.getQuantitySent() == null ? 0 : Integer.parseInt(temp.getQuantitySent());
				temp.settQuantityNotSent(String.valueOf(Integer.parseInt(temp.gettQuantity()) - quantitySent));
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
			map.put("complaintId", tradeOrderPop.getComplaintId4S());
			map.put("tradeOrderId", tradeOrderPop.getTradeOrderId4S());
			map.put("po", tradeOrderPop.getPo4S());
			map.put("contractNo", tradeOrderPop.getContractNo4S());
			map.put("customerId", tradeOrderPop.getCustomerId4S());
			map.put("productionId", tradeOrderPop.getProductionId4S() == null ? null : tradeOrderPop.getProductionId4S().toUpperCase());
			map.put("isStatusRefFlg", tradeOrderPop.getIsStatusRefFlg());
			int count = complaintDAO.countFindPopByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int update(Complaint complaint) {
		log.debug("update with key: " + complaint.getComplaintId());
		try {
			int count = complaintDAO.update(complaint);
			return count;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public int delete(Complaint complaint) {
		log.debug("delete with key: " + complaint.getComplaintId());
		try {
			int count = complaintDAO.delete(complaint);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int deleteDtl(Complaint complaint) {
		log.debug("delete with key: " + complaint.getComplaintId());
		try {
			int count = complaintDAO.deleteDtl(complaint);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Complaint complaint) {
		log.debug("insert complaint: " + complaint.getComplaintId());
		try {
			int count = complaintDAO.insert(complaint);
			return count;
		} catch (RuntimeException re) {
			log.error("insert complaint failed", re);
			throw re;
		}
	}
	
	public int insertDtl(ComplaintDtl complaintDtl) {
		log.debug("insert complaint: " + complaintDtl.getComplaintId());
		try {
			if(IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())){
				
				complaintDtl.setTradeOrderId("unDefined");
			}
			int count = complaintDAO.insertDtl(complaintDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("insert complaint failed", re);
			throw re;
		}
	}
}