package com.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.SearchCondition;
import com.entity.TradeOrderPop;
import com.logic.ValidationAPILogic;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

@Service
public class ValidationAPIService{
	
	public static final String PRODUCTION = "production";
	public static final String TRADEORDER = "tradeOrder";
	public static final String TRADEORDERDTL = "tradeOrderDtl";
	public static final String COMPLAINT = "complaint";
	public static final String RECEIPT = "receipt";
	public static final String RECEIPTDTL = "receiptDtl";
	public static final String INQUIRY = "inquiry";
	public static final String VENDOR = "vendor";
	public static final String CUSTOMER = "customer";
	
	private ValidationAPILogic validationAPILogic = null;
	public void setValidationAPILogic(ValidationAPILogic validationAPILogic) {
		this.validationAPILogic = validationAPILogic;
	}
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	@Transactional(rollbackFor=Exception.class)
    public Timestamp getUpdateTime(String target, String... params) throws Exception{
		
		Map<String, Object> map = setMap(target, params);
		return validationAPILogic.getUpdateTime(map);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void setUpdateTime(String target, String... params) throws Exception{
		
		Map<String, Object> map = setMap(target, params);
		validationAPILogic.setUpdateTime(map);
    }
	
	private Map<String, Object> setMap(String target, String[] params) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SearchCondition> searchConditionList = new ArrayList<SearchCondition>();
		SearchCondition searchCondition = null;
		
		switch (target) {
			case PRODUCTION:
				
				map.put("tableName", "production");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("productionId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("versionNo");
				searchCondition.setColValue(params[1]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case TRADEORDER:
	
				map.put("tableName", "tradeOrder");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("tradeOrderId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case TRADEORDERDTL:
				
				map.put("tableName", "tradeOrderDtl");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("tradeOrderId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("productionId");
				searchCondition.setColValue(params[1]);
				searchConditionList.add(searchCondition);
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("versionNo");
				searchCondition.setColValue(params[2]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case COMPLAINT:
				map.put("tableName", "complaint");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("complaintId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case RECEIPT:
				map.put("tableName", "receipt");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("receiptId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
			
			case RECEIPTDTL:
				map.put("tableName", "receiptDtl");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("tradeOrderId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("productionId");
				searchCondition.setColValue(params[1]);
				searchConditionList.add(searchCondition);
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("versionNo");
				searchCondition.setColValue(params[2]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case INQUIRY:
				map.put("tableName", "inquiry");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("inquiryId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case VENDOR:
				map.put("tableName", "vendor");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("vendorId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
				
			case CUSTOMER:
				map.put("tableName", "customer");
				
				searchCondition = new SearchCondition();
				searchCondition.setColName("customerId");
				searchCondition.setColValue(params[0]);
				searchConditionList.add(searchCondition);
				
				map.put("searchConditionList", searchConditionList);
				break;
		}
		return map;
	}
	
	@Transactional(rollbackFor=Exception.class)
    public List<String> validateUnConfirmedR(String receiptId) throws Exception{
		
		return validationAPILogic.validateUnConfirmedR(receiptId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public List<String> validateUnConfirmedC(String complaintId) throws Exception{
		
		return validationAPILogic.validateUnConfirmedC(complaintId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateIsAllSent(String tradeOrderId) throws Exception{
		
		boolean isAllSent = true;
		
		TradeOrderPop tradeOrderPop = new TradeOrderPop();
		tradeOrderPop.setTradeOrderId4S(tradeOrderId);
		tradeOrderPop = tradeOrderService.tradeOrderPopSearch(tradeOrderPop);
		
		for (int i = 0; i < tradeOrderPop.getResultTradeOrderPopList().size(); i++) {
			
			TradeOrderPop temp = tradeOrderPop.getResultTradeOrderPopList().get(i);
			
			//数量
			if((IsEmptyUtil.isEmpty(temp.gettQuantity())
					|| IsEmptyUtil.isEmpty(temp.getiQuantity())
					|| IsEmptyUtil.isEmpty(temp.getQuantitySent()))
					|| (StringHandler.isInt(temp.gettQuantity()) 
					&& StringHandler.isInt(temp.getiQuantity())
					&& StringHandler.isInt(temp.getQuantitySent())
					&& Integer.parseInt(temp.gettQuantity()) > Integer.parseInt(temp.getQuantitySent())
					&& Integer.parseInt(temp.getiQuantity()) > Integer.parseInt(temp.getQuantitySent()))){
				
				isAllSent = false;
				break;
			}
		}
		
		return isAllSent;
    }

	@Transactional(rollbackFor=Exception.class)
    public boolean validateCU(String customerId) throws Exception{
		
		return validationAPILogic.validateCU(customerId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validatePVC(String vendorId,
			String customerId,
			String productionId, String versionNo) throws Exception{
		
		return validationAPILogic.validatePVC(vendorId, customerId, productionId, versionNo);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateIDtl(String productionId, String inquiryId) throws Exception{
		
		return validationAPILogic.validateIDtl(productionId, inquiryId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateP(String productionId, String versionNo, String statusRef) throws Exception{
		
		return validationAPILogic.validateP(productionId, versionNo, statusRef);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateV(String vendorId) throws Exception{
		
		return validationAPILogic.validateV(vendorId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateC(String customerId) throws Exception{
		
		return validationAPILogic.validateC(customerId);
    }
	
	@Transactional(rollbackFor=Exception.class)
    public boolean validateCode(String codeId) throws Exception{
		
		return validationAPILogic.validateCode(codeId);
    }
	
}
