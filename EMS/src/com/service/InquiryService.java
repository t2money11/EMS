package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.Inquiry;
import com.entity.InquiryDtl;
import com.exception.ValiationException;
import com.logic.InquiryLogic;
import com.util.DbCommonUtil;
import com.util.IsEmptyUtil;

@Service
public class InquiryService{
	
	private InquiryLogic inquiryLogic = null;
	public void setInquiryLogic(InquiryLogic inquiryLogic) {
		this.inquiryLogic = inquiryLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public Inquiry findByKey(String inquiryIdSelected){
		
		Inquiry inquiry = inquiryLogic.findByKey(inquiryIdSelected);
		return inquiry;
    }
	
	public List<InquiryDtl> findDtlByKey(String inquiryIdSelected) throws Exception{
		
		List<InquiryDtl> inquiryDtlList = inquiryLogic.findDtlByKey(inquiryIdSelected);
		
		for (InquiryDtl inquiryDtl : inquiryDtlList) {
			
			//上次询单数据获取
			InquiryDtl lastInquiryDtl = findLastDtlByProductionId(inquiryDtl.getInquiryId(), inquiryDtl.getProductionId());
			if(lastInquiryDtl != null){
				
				inquiryDtl.setPreviousInquiryId(lastInquiryDtl.getInquiryId());
				inquiryDtl.setPreviousTradeOrderId(lastInquiryDtl.getTradeOrderId());
				inquiryDtl.setPreviousRMB(lastInquiryDtl.getRMB());
				inquiryDtl.setPreviousUSD(lastInquiryDtl.getFinalPrice());
				inquiryDtl.setPreviousRate(lastInquiryDtl.getRate());
			}
			
			inquiryDtl.setIsUsed(validationAPIService.validateIDtl(inquiryDtl.getProductionId(), inquiryDtl.getInquiryId())
					|| !IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId()));
		}
		
		return inquiryDtlList;
    }
	
	public Inquiry inquirySearch(Inquiry inquiry) throws Exception{
		
		int totalCount = 0;
		
		totalCount = inquiryLogic.countFindByProperty(inquiry);
		if(totalCount != 0){
			
			List<InquiryDtl> inquiryDtlList = inquiryLogic.findByProperty(inquiry);
			inquiry.setResultInquiryDtlList(inquiryDtlList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		inquiry.getPageInfo().setTotalCount(totalCount);
		
        return inquiry;
    }
	
	public InquiryDtl findLastDtlByProductionId(String inquiryId, String productionId){
		
		List<InquiryDtl> inquiryDtlList = inquiryLogic.findLastDtlByKey(inquiryId, productionId);
		
		if(inquiryDtlList.size() > 0){
			
			return inquiryDtlList.get(0);
		}else{
			
			return null;
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Inquiry inquiry) throws Exception{
		
		try {
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, inquiry.getCustomerId());
			if(customerUpdateTime == null ||inquiry.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, inquiryDtl.getProductionId(), inquiryDtl.getVersionNo());
				if(productionUpdateTime == null ||inquiryDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//验证订单，产品是否匹配
				if(!IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId()) 
						&& validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, inquiryDtl.getTradeOrderId(), inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.TP_UNMATCH, inquiryDtl.getTradeOrderId(), inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()));
				}
			}
			
			//采番
			inquiry.setInquiryId(inquiryLogic.getInquiryId4New());
			DbCommonUtil.setCreateCommon(inquiry);
			inquiryLogic.insert(inquiry);
			
			for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
				
				inquiryDtl.setInquiryId(inquiry.getInquiryId());
				DbCommonUtil.setCreateCommon(inquiryDtl);
				inquiryLogic.insertDtl(inquiryDtl);
			}
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return inquiry.getInquiryId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Inquiry inquiry) throws Exception{
		
		try {
			
			//更新冲突验证
			Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.INQUIRY, inquiry.getInquiryId());
			if(updateTime == null ||inquiry.getUpdateTime().compareTo(updateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, inquiry.getCustomerId());
			if(customerUpdateTime == null ||inquiry.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, inquiryDtl.getProductionId(), inquiryDtl.getVersionNo());
				if(productionUpdateTime == null ||inquiryDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//验证订单，产品是否匹配
				if(!IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId()) 
						&& validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, inquiryDtl.getTradeOrderId(), inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.TP_UNMATCH, inquiryDtl.getTradeOrderId(), inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()));
				}
			}
			
			//更新询单
			DbCommonUtil.setUpdateCommon(inquiry);
			inquiryLogic.update(inquiry);
			
			//删除当前询单明细，重新登录
			inquiryLogic.deleteDtl(inquiry);
			for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
				
				if(IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId())){
					
					inquiryDtl.setInquiryId(inquiry.getInquiryId());
					DbCommonUtil.setCreateCommon(inquiryDtl);
					inquiryLogic.insertDtl(inquiryDtl);
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String inquiryIdSelected) throws Exception{
		
		Inquiry inquiry = new Inquiry();
		inquiry.setInquiryId(inquiryIdSelected);
		
		int count = 0;
		int countDtl = 0;
		try {
			
			List<InquiryDtl> inquiryDtlList = inquiryLogic.findDtlByKey(inquiryIdSelected);
			for (InquiryDtl inquiryDtl : inquiryDtlList) {
				
				//验证该询单是否已经被其他询单引用
				if(validationAPIService.validateIDtl(inquiryDtl.getProductionId(), inquiryDtl.getInquiryId())
						|| !IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId())){
					
					throw new ValiationException(String.format(Message.USED_INQUIRY, inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()));
				}
			}
			count = inquiryLogic.delete(inquiry);
			countDtl = inquiryLogic.deleteDtl(inquiry);
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0 || countDtl == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
}
