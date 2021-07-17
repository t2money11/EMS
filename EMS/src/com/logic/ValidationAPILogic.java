package com.logic;

import com.constant.CommonData;
import com.dao.ValidationAPIDAO;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ValidationAPILogic{
	
	private static final Logger log = LoggerFactory.getLogger(ValidationAPILogic.class);
	
	@Resource
    private ValidationAPIDAO validationAPIDAO; 
	
	/**
	 * 获取更新时间
	 * @param map
	 * @return
	 */
	public Timestamp getUpdateTime(Map<String, Object> map) {
		log.debug("getUpdateTime");
		try {
			
			return validationAPIDAO.getUpdateTime(map);
		} catch (RuntimeException re) {
			log.error("getUpdateTime", re);
			throw re;
		}
	}
	
	/**
	 * 设定更新时间
	 * @param map
	 * @return
	 */
	public int setUpdateTime(Map<String, Object> map) {
		log.debug("setUpdateTime");
		try {
			
			return validationAPIDAO.setUpdateTime(map);
		} catch (RuntimeException re) {
			log.error("setUpdateTime", re);
			throw re;
		}
	}
	
	/**
	 * 获取出货单中尚未确定的订单号LIST
	 * @param map
	 * @return
	 */
	public List<String> validateUnConfirmedR(String receiptId) {
		log.debug("validateUnConfirmedR");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptId);
			return validationAPIDAO.validateUnConfirmedR(map);
		} catch (RuntimeException re) {
			log.error("validateUnConfirmedR", re);
			throw re;
		}
	}
	
	/**
	 * 获取投诉单中尚未确定的订单号LIST
	 * @param map
	 * @return
	 */
	public List<String> validateUnConfirmedC(String complaintId) {
		log.debug("validateUnConfirmedC");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("complaintId", complaintId);
			return validationAPIDAO.validateUnConfirmedC(map);
		} catch (RuntimeException re) {
			log.error("validateUnConfirmedC", re);
			throw re;
		}
	}
	
	/**
	 * 验证客户权限
	 * @param customerId
	 * @return
	 */
	public boolean validateCU(String customerId) {
		log.debug("validateCU");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			map.put("customerId", customerId);
			
			int count = validationAPIDAO.validateCU(map);
			return count != 1 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateCU", re);
			throw re;
		}
	}
	
	/**
	 * 验证产品是否匹配客户和供应商 带版本
	 * @param vendorId
	 * @param customerId
	 * @param productionId
	 * @param versionNo
	 * @return
	 */
	public boolean validatePVC(String vendorId,
			String customerId,
			String productionId,
			String versionNo) {
		log.debug("validatePVC");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", vendorId);
			map.put("customerId", customerId);
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			
			int count = validationAPIDAO.validatePVC(map);
			return count != 1 ? false : true;
		} catch (RuntimeException re) {
			log.error("validatePVC", re);
			throw re;
		}
	}
	
	/**
	 * 验证产品是否已经被其他询单引用 不带版本
	 * @param productionId
	 * @param versionNo
	 * @param inquiryId
	 * @return
	 */
	public boolean validateIDtl(String productionId, String inquiryId) {
		log.debug("validateIDtl");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionId);
			map.put("inquiryId", inquiryId);
			
			int count = validationAPIDAO.validateIDtl(map);
			return count > 0 ? true : false;
		} catch (RuntimeException re) {
			log.error("validateIDtl", re);
			throw re;
		}
	}
	
	/**
	 * 投诉以及询单中输入订单号保存时，验证产品，以及订单是否存在 带版本
	 * @param productionId
	 * @param versionNo
	 * @param tradeOrderId
	 * @return
	 */
	public boolean validatePT(String productionId, String versionNo, String tradeOrderId) {
		log.debug("validateP");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			map.put("tradeOrderId", tradeOrderId);
			
			int count = validationAPIDAO.validatePT(map);
			return count == 0 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateP", re);
			throw re;
		}
	}
	
	/**
	 * 产品变更初始化，变更和删除时，验证产品是否已经被引用 带版本
	 * @param productionId
	 * @param versionNo
	 * @param statusRef
	 * @return
	 */
	public boolean validateP(String productionId, String versionNo, String statusRef) {
		log.debug("validateP");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionId);
			map.put("versionNo", versionNo);
			map.put("statusRef", statusRef);
			
			int count = validationAPIDAO.validateP(map);
			return count == 0 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateP", re);
			throw re;
		}
	}
	
	/**
	 * 删除供应商之前，验证供应商是否已经被引用
	 * @param vendorId
	 * @return
	 */
	public boolean validateV(String vendorId) {
		log.debug("validateV");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", vendorId);
			
			int count = validationAPIDAO.validateV(map);
			return count == 0 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateV", re);
			throw re;
		}
	}
	
	/**
	 * 删除客户之前，验证客户是否已经被引用
	 * @param customerId
	 * @return
	 */
	public boolean validateC(String customerId) {
		log.debug("validateC");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customerId);
			
			int count = validationAPIDAO.validateC(map);
			return count == 0 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateC", re);
			throw re;
		}
	}
	
	/**
	 * 删除产品种类之前，验证是否已经被产品引用
	 * @param customerId
	 * @return
	 */
	public boolean validateCode(String codeId) {
		log.debug("validateCode");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("kind", codeId);
			
			int count = validationAPIDAO.validateCode(map);
			return count == 0 ? false : true;
		} catch (RuntimeException re) {
			log.error("validateCode", re);
			throw re;
		}
	}
	
}