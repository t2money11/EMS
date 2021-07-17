package com.logic;

import com.constant.CommonData;
import com.dao.ReceiptDAO;
import com.entity.Receipt;
import com.entity.ReceiptDtl;
import com.entity.ReceiptDtl4C;

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
public class ReceiptLogic{
	
	private static final Logger log = LoggerFactory.getLogger(ReceiptLogic.class);
	
	@Resource
    private ReceiptDAO receiptDAO; 
    
	public int validateRelateReceipt(String receiptNo, String customerId, String relateReceiptNo) {
		log.debug("validateRelateReceipt");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptNo", receiptNo);
			map.put("customerId", customerId);
			map.put("relateReceiptNo", relateReceiptNo);
			int count = receiptDAO.validateRelateReceipt(map);
			return count;
		} catch (RuntimeException re) {
			log.error("validateRelateReceipt", re);
			throw re;
		}
	}
	
	public int validateDuplicatedReceiptNo(String receiptId, String receiptNo) {
		log.debug("validateDuplicatedReceiptNo");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptId);
			map.put("receiptNo", receiptNo);
			int count = receiptDAO.validateDuplicatedReceiptNo(map);
			return count;
		} catch (RuntimeException re) {
			log.error("validateDuplicatedReceiptNo", re);
			throw re;
		}
	}
	
	public String getReceiptId4New() {
		log.debug("getReceiptId4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nowDate", DateUtil.getNowDateString(DateUtil.DATE_FORMAT2));
			String receiptId = receiptDAO.getReceiptId4New(map);
			if(receiptId == null){
				receiptId = "R-".concat(DateUtil.getNowDateString(DateUtil.DATE_FORMAT2)).concat("-001");
			}else{
				index = Integer.parseInt(receiptId.substring(11));
				String indexString = String.format("%03d", ++index);
				receiptId = receiptId.substring(0, 11).concat(indexString);
			}
			return receiptId;
		} catch (RuntimeException re) {
			log.error("getReceiptId4New", re);
			throw re;
		}
	}
	
	public String getReceiptNo4New() {
		log.debug("getReceiptNo4New");
		try {
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			String nowYear = DateUtil.getNowDateString(DateUtil.DATE_FORMAT8).substring(2, 4);
			map.put("nowYear", nowYear);
			String receiptNo = receiptDAO.getReceiptNo4New(map);
			if(receiptNo == null){
				receiptNo = nowYear.concat("YCFT18001");
			}else{
				index = Integer.parseInt(receiptNo.substring(8));
				String indexString = String.format("%03d", ++index);
				receiptNo = receiptNo.substring(0, 8) + indexString;
			}
			return receiptNo;
		} catch (RuntimeException re) {
			log.error("getReceiptNo4New", re);
			throw re;
		}
	}
	
	public Receipt findByKey(String receiptIdSelected) {
		log.debug("finding Receipt instance with key:");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptIdSelected);
			return receiptDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Receipt instance with key", re);
			throw re;
		}
	}
	
	public List<ReceiptDtl> findDtlByKey(String receiptIdSelected) {
		log.debug("finding ReceiptDtl instance with key:" + receiptIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptIdSelected);
			List<ReceiptDtl> receiptDtlList = receiptDAO.findDtlByKey(map);
			
			for (ReceiptDtl temp : receiptDtlList) {
				
				int quantitySent = temp.getQuantitySent() == null ? 0 : Integer.parseInt(temp.getQuantitySent());
				temp.settQuantityNotSent(String.valueOf(Integer.parseInt(temp.gettQuantity()) - quantitySent));
				temp.setiQuantityNotSent(String.valueOf(Integer.parseInt(temp.getiQuantity()) - quantitySent));
			}
			return receiptDtlList;
		} catch (RuntimeException re) {
			log.error("finding ReceiptDtl instance with key", re);
			throw re;
		}
	}
	
	public List<ReceiptDtl4C> findDtl4CByKey(String receiptIdSelected) {
		log.debug("finding ReceiptDtl4C instance with key:" + receiptIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptIdSelected);
			List<ReceiptDtl4C> receiptDtl4CList = receiptDAO.findDtl4CByKey(map);
			
			for (ReceiptDtl4C temp : receiptDtl4CList) {
				
				int quantitySent = temp.getQuantitySent() == null ? 0 : Integer.parseInt(temp.getQuantitySent());
				temp.settQuantityNotSent(String.valueOf(Integer.parseInt(temp.gettQuantity()) - quantitySent));
			}
			return receiptDtl4CList;
		} catch (RuntimeException re) {
			log.error("finding ReceiptDtl4C instance with key", re);
			throw re;
		}
	}
	
	public List<Receipt> findByProperty(Receipt receipt) {
		log.debug("finding Receipt instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", receipt.getCustomerId4S());
			map.put("receiptId", receipt.getReceiptId4S());
			map.put("tradeOrderId", receipt.getTradeOrderId4S());
			map.put("po", receipt.getPo4S());
			map.put("contractNo", receipt.getContractNo4S());
			map.put("productionId", receipt.getProductionId4S() == null ? null : receipt.getProductionId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("startIndex", receipt.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<Receipt> receiptList = receiptDAO.findByProperty(map);
			return receiptList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Receipt receipt) {
		log.debug("finding Receipt instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", receipt.getCustomerId4S());
			map.put("receiptId", receipt.getReceiptId4S());
			map.put("tradeOrderId", receipt.getTradeOrderId4S());
			map.put("po", receipt.getPo4S());
			map.put("contractNo", receipt.getContractNo4S());
			map.put("productionId", receipt.getProductionId4S() == null ? null : receipt.getProductionId4S().toUpperCase());
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			int count = receiptDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int update(Receipt receipt) {
		log.debug("update with key: " + receipt.getReceiptId());
		try {
			int count = receiptDAO.update(receipt);
			return count;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public int delete(Receipt receipt) {
		log.debug("delete with key: " + receipt.getReceiptId());
		try {
			int count = receiptDAO.delete(receipt);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int deleteDtl(Receipt receipt) {
		log.debug("delete with key: " + receipt.getReceiptId());
		try {
			int count = receiptDAO.deleteDtl(receipt);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int deleteDtl4C(Receipt receipt) {
		log.debug("delete with key: " + receipt.getReceiptId());
		try {
			int count = receiptDAO.deleteDtl4C(receipt);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Receipt receipt) {
		log.debug("insert receipt: " + receipt.getReceiptId());
		try {
			int count = receiptDAO.insert(receipt);
			return count;
		} catch (RuntimeException re) {
			log.error("insert receipt failed", re);
			throw re;
		}
	}
	
	public int insertDtl(ReceiptDtl receiptDtl) {
		log.debug("insert receipt: " + receiptDtl.getReceiptId());
		try {
			int count = receiptDAO.insertDtl(receiptDtl);
			return count;
		} catch (RuntimeException re) {
			log.error("insert receipt failed", re);
			throw re;
		}
	}
	
	public int insertDtl4C(ReceiptDtl4C receiptDtl4C) {
		log.debug("insert receipt: " + receiptDtl4C.getReceiptId());
		try {
			int count = receiptDAO.insertDtl4C(receiptDtl4C);
			return count;
		} catch (RuntimeException re) {
			log.error("insert receipt failed", re);
			throw re;
		}
	}
}