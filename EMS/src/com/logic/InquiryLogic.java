package com.logic;

import com.constant.CommonData;
import com.dao.InquiryDAO;
import com.entity.Inquiry;
import com.entity.InquiryDtl;

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
public class InquiryLogic{
	
	private static final Logger log = LoggerFactory.getLogger(InquiryLogic.class);
	
	@Resource
    private InquiryDAO inquiryDAO; 
    
	public String getInquiryId4New() {
		log.debug("getInquiryId4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nowDate", DateUtil.getNowDateString(DateUtil.DATE_FORMAT2));
			String inquiryId = inquiryDAO.getInquiryId4New(map);
			if(inquiryId == null){
				inquiryId = "I-" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT2) + "-001";
			}else{
				index = Integer.parseInt(inquiryId.substring(11));
				String indexString = String.format("%03d", ++index);
				inquiryId = inquiryId.substring(0, 11) + indexString;
			}
			return inquiryId;
		} catch (RuntimeException re) {
			log.error("getInquiryId4New", re);
			throw re;
		}
	}
	
	public Inquiry findByKey(String inquiryIdSelected) {
		log.debug("finding Inquiry instance with key:" + inquiryIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inquiryId", inquiryIdSelected);
			return inquiryDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Inquiry instance with key", re);
			throw re;
		}
	}
	
	public List<InquiryDtl> findDtlByKey(String inquiryIdSelected) {
		log.debug("finding InquiryDtl instance with key:" + inquiryIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inquiryId", inquiryIdSelected);
			return inquiryDAO.findDtlByKey(map);
		} catch (RuntimeException re) {
			log.error("finding InquiryDtl instance with key", re);
			throw re;
		}
	}
	
	public List<InquiryDtl> findLastDtlByKey(String inquiryIdSelected, String productionIdSelected) {
		log.debug("finding InquiryDtl instance with key:" + inquiryIdSelected + ", " + productionIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inquiryId", inquiryIdSelected);
			map.put("productionId", productionIdSelected);
			return inquiryDAO.findLastDtlByKey(map);
		} catch (RuntimeException re) {
			log.error("finding InquiryDtl instance with key", re);
			throw re;
		}
	}
	
	public List<InquiryDtl> findByProperty(Inquiry inquiry) {
		log.debug("finding Inquiry instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", inquiry.getProductionId4S() == null ? null : inquiry.getProductionId4S().toUpperCase());
			map.put("customerId", inquiry.getCustomerId4S() == null ? null : inquiry.getCustomerId4S().toUpperCase());
			map.put("customerName", inquiry.getCustomerName4S() == null ? null : inquiry.getCustomerName4S().toUpperCase());
			map.put("inquiryId", inquiry.getInquiryId4S() == null ? null : inquiry.getInquiryId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
					&& !"Way".equals(CommonData.getCmnData().getLoginInfo().getUserId())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("startIndex", inquiry.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<InquiryDtl> inquiryList = inquiryDAO.findByProperty(map);
			return inquiryList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Inquiry inquiry) {
		log.debug("finding Inquiry instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", inquiry.getProductionId4S() == null ? null : inquiry.getProductionId4S().toUpperCase());
			map.put("customerId", inquiry.getCustomerId4S() == null ? null : inquiry.getCustomerId4S().toUpperCase());
			map.put("customerName", inquiry.getCustomerName4S() == null ? null : inquiry.getCustomerName4S().toUpperCase());
			map.put("inquiryId", inquiry.getInquiryId4S() == null ? null : inquiry.getInquiryId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
					&& !"Way".equals(CommonData.getCmnData().getLoginInfo().getUserId())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			int count = inquiryDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int update(Inquiry inquiry) {
		log.debug("update with key: " + inquiry.getInquiryId());
		try {
			int count = inquiryDAO.update(inquiry);
			return count;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public int delete(Inquiry inquiry) {
		log.debug("delete with key: " + inquiry.getInquiryId());
		try {
			int count = inquiryDAO.delete(inquiry);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int deleteDtl(Inquiry inquiry) {
		log.debug("delete with key: " + inquiry.getInquiryId());
		try {
			int count = inquiryDAO.deleteDtl(inquiry);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Inquiry inquiry) {
		log.debug("insert inquiry: " + inquiry.getInquiryId());
		try {
			int count = inquiryDAO.insert(inquiry);
			return count;
		} catch (RuntimeException re) {
			log.error("insert inquiry failed", re);
			throw re;
		}
	}
	
	public int insertDtl(InquiryDtl inquiryDtl) {
		log.debug("insert inquiry: " + inquiryDtl.getInquiryId());
		try {
			int count = inquiryDAO.insertDtl(inquiryDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("insert inquiry failed", re);
			throw re;
		}
	}
	
	public InquiryDtl findDtlByTradeOrder(String tradeOrderId, String productionId, String versionNo, String unitPrice, String inquiryId) {
		log.debug("finding InquiryDtl instance with key:" + productionId + ", " + versionNo + ", " + unitPrice + ", " + inquiryId);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tradeOrderId", tradeOrderId);
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			map.put("unitPrice", unitPrice);
			map.put("inquiryId", inquiryId);
			return inquiryDAO.findDtlByTradeOrder(map);
		} catch (RuntimeException re) {
			log.error("finding InquiryDtl instance with key", re);
			throw re;
		}
	}
	
	public int updateDtlOnTradeOrderId(InquiryDtl inquiryDtl) {
		log.debug("updateDtlOnTradeOrderId with key: " + inquiryDtl.getInquiryId());
		try {
			int count = inquiryDAO.updateDtlOnTradeOrderId(inquiryDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("updateDtlOnTradeOrderId failed", re);
			throw re;
		}
	}
	
	public int clearDtlOnTradeOrderId(InquiryDtl inquiryDtl) {
		log.debug("clearDtlOnTradeOrderId with key: " + inquiryDtl.getInquiryId());
		try {
			int count = inquiryDAO.clearDtlOnTradeOrderId(inquiryDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("clearDtlOnTradeOrderId failed", re);
			throw re;
		}
	}
}