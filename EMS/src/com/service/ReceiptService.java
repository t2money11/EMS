package com.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.ConstantParam;
import com.context.Message;
import com.entity.Receipt;
import com.entity.ReceiptDtl;
import com.entity.ReceiptDtl4C;
import com.exception.ValiationException;
import com.logic.ReceiptLogic;
import com.util.DbCommonUtil;
import com.util.IsEmptyUtil;

@Service
public class ReceiptService{
	
	private ReceiptLogic receiptLogic = null;
	public void setReceiptLogic(ReceiptLogic receiptLogic) {
		this.receiptLogic = receiptLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	public Receipt findByKey(String receiptIdSelected) throws Exception{
		
		List<String> errorTradeOrderIdList = validationAPIService.validateUnConfirmedR(receiptIdSelected);
		String temp = "";
		for (String errorTradeOrderId : errorTradeOrderIdList) {
			
			temp = temp.concat(errorTradeOrderId).concat(", ");
		}
		
		if(!IsEmptyUtil.isEmpty(errorTradeOrderIdList)){
			
			temp = temp.substring(0, temp.length() - 2);
			throw new Exception(String.format(Message.HAS_NOCONFIRMED_TRADEORDER_4_R, temp));
		}
		
		Receipt receipt = receiptLogic.findByKey(receiptIdSelected);
		
		//关联报关发票号码
		if("1".equals(receipt.getReceiptMode())
				&& receiptLogic.validateRelateReceipt(null, null, receipt.getReceiptNo()) != 0){
			
			receipt.setIsRelated("1");
		}
		return receipt;
    }
	
	public List<ReceiptDtl> findDtlByKey(String receiptIdSelected){
		
		return receiptLogic.findDtlByKey(receiptIdSelected);
    }
	
	public List<ReceiptDtl4C> findDtl4CByKey(String receiptIdSelected){
		
		return receiptLogic.findDtl4CByKey(receiptIdSelected);
    }
	
	public Receipt receiptSearch(Receipt receipt) throws Exception{
		
		int totalCount = 0;
		
		totalCount = receiptLogic.countFindByProperty(receipt);
		if(totalCount != 0){
			
			List<Receipt> receiptList = receiptLogic.findByProperty(receipt);
			receipt.setResultReceiptList(receiptList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		receipt.getPageInfo().setTotalCount(totalCount);
		
        return receipt;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Receipt receipt) throws Exception{
		
		try {
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, receipt.getCustomerId());
			if(customerUpdateTime == null ||receipt.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())
						|| !"0".equals(receiptDtl.getFeeNum())){
					
					continue;
				}
				
				//更新冲突验证(订单)
				Timestamp tradeOrderUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDER, receiptDtl.getTradeOrderId());
				if(tradeOrderUpdateTime == null ||receiptDtl.getTradeOrderUpdateTime().compareTo(tradeOrderUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, receiptDtl.getProductionId(), receiptDtl.getVersionNo());
				if(productionUpdateTime == null ||receiptDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}
			
			for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
					
					continue;
				}
				
				//更新冲突验证(订单)
				Timestamp tradeOrderUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDER, receiptDtl4C.getTradeOrderId());
				if(tradeOrderUpdateTime == null ||receiptDtl4C.getTradeOrderUpdateTime().compareTo(tradeOrderUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo());
				if(productionUpdateTime == null ||receiptDtl4C.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(订单)
				Timestamp complaintUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.COMPLAINT, receiptDtl4C.getComplaintId());
				if(complaintUpdateTime == null ||receiptDtl4C.getComplaintUpdateTime().compareTo(complaintUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}

			//重复发票号确认
			if(!IsEmptyUtil.isEmpty(receipt.getReceiptNo()) && receiptLogic.validateDuplicatedReceiptNo(null, receipt.getReceiptNo()) != 0){
				throw new ValiationException(Message.DUPLICATED_RECEIPTNO);
			}
			
			//关联报关发票号码
			if("1".equals(receipt.getReceiptMode()) && !IsEmptyUtil.isEmpty(receipt.getRelateReceiptNo())){
				
				throw new ValiationException(Message.NO_NEED_RELATE_RECEIPTNO);
			}else if(!IsEmptyUtil.isEmpty(receipt.getRelateReceiptNo()) 
					&& receiptLogic.validateRelateReceipt(receipt.getRelateReceiptNo(), receipt.getCustomerId(), null) == 0){
				
				throw new ValiationException(Message.NOT_EXISTS_RELATE_RECEIPTNO);
			}
			
			//采番
			receipt.setReceiptId(receiptLogic.getReceiptId4New());
			if(IsEmptyUtil.isEmpty(receipt.getReceiptNo())){
				
				receipt.setReceiptNo(receiptLogic.getReceiptNo4New());
			}
			DbCommonUtil.setCreateCommon(receipt);
			receiptLogic.insert(receipt);
			
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
					
					continue;
				}
				
				//验证订单产品存在性
				if(validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, receiptDtl.getTradeOrderId(), receiptDtl.getProductionId(), receiptDtl.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.NOT_EXISTS_PRODUCTION_IN_RECEIPT, receiptDtl.getTradeOrderId(), receiptDtl.getProductionId(), receiptDtl.getVersionNo()));
				}
				
				receiptDtl.setReceiptId(receipt.getReceiptId());
				DbCommonUtil.setCreateCommon(receiptDtl);
				receiptLogic.insertDtl(receiptDtl);
			}
			
			for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
					
					continue;
				}
				
				//验证订单产品存在性
				if(validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, receiptDtl4C.getTradeOrderId(), receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.NOT_EXISTS_PRODUCTION_IN_RECEIPT, receiptDtl4C.getTradeOrderId(), receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo()));
				}

				receiptDtl4C.setReceiptId(receipt.getReceiptId());
				DbCommonUtil.setCreateCommon(receiptDtl4C);
				receiptLogic.insertDtl4C(receiptDtl4C);
			}

			//根据发货情况更新订单状态
			List<String> tradeOrderIdList = new ArrayList<String>();
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(!tradeOrderIdList.contains(receiptDtl.getTradeOrderId())){
					
					tradeOrderIdList.add(receiptDtl.getTradeOrderId());
				}
			}
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlRemoveList()) {
				
				if(!tradeOrderIdList.contains(receiptDtl.getTradeOrderId())){
					
					tradeOrderIdList.add(receiptDtl.getTradeOrderId());
				}
			}
			
			for (String tradeOrderId : tradeOrderIdList) {
				
				//全部发送完毕，订单状态完了
				if(validationAPIService.validateIsAllSent(tradeOrderId)){
					
					tradeOrderService.updateStatus(tradeOrderId, "2");
				}else{
				//反之则订单状态确定
					
					tradeOrderService.updateStatus(tradeOrderId, "1");
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return receipt.getReceiptId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Receipt receipt) throws Exception{
		
		try {
			
			//更新冲突验证
			Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.RECEIPT, receipt.getReceiptId());
			if(updateTime == null ||receipt.getUpdateTime().compareTo(updateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, receipt.getCustomerId());
			if(customerUpdateTime == null ||receipt.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())
						|| !"0".equals(receiptDtl.getFeeNum())){
					
					continue;
				}
				
				//更新冲突验证(订单)
				Timestamp tradeOrderUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDER, receiptDtl.getTradeOrderId());
				if(tradeOrderUpdateTime == null ||receiptDtl.getTradeOrderUpdateTime().compareTo(tradeOrderUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, receiptDtl.getProductionId(), receiptDtl.getVersionNo());
				if(productionUpdateTime == null ||receiptDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}
			
			for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
					
					continue;
				}
				
				//更新冲突验证(订单)
				Timestamp tradeOrderUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDER, receiptDtl4C.getTradeOrderId());
				if(tradeOrderUpdateTime == null ||receiptDtl4C.getTradeOrderUpdateTime().compareTo(tradeOrderUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo());
				if(productionUpdateTime == null ||receiptDtl4C.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//更新冲突验证(订单)
				Timestamp complaintUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.COMPLAINT, receiptDtl4C.getComplaintId());
				if(complaintUpdateTime == null ||receiptDtl4C.getComplaintUpdateTime().compareTo(complaintUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}
			
			//重复发票号确认
			if(!IsEmptyUtil.isEmpty(receipt.getReceiptNo()) && receiptLogic.validateDuplicatedReceiptNo(receipt.getReceiptId(), receipt.getReceiptNo()) != 0){
				throw new ValiationException(Message.DUPLICATED_RECEIPTNO);
			}
			
			//关联报关发票号码
			if("1".equals(receipt.getReceiptMode()) && !IsEmptyUtil.isEmpty(receipt.getRelateReceiptNo())){
				
				throw new ValiationException(Message.NO_NEED_RELATE_RECEIPTNO);
			}else if(!IsEmptyUtil.isEmpty(receipt.getRelateReceiptNo()) 
					&& receiptLogic.validateRelateReceipt(receipt.getRelateReceiptNo(), receipt.getCustomerId(), null) == 0){
				
				throw new ValiationException(Message.NOT_EXISTS_RELATE_RECEIPTNO);
			}
			
			//更新发货单
			DbCommonUtil.setUpdateCommon(receipt);
			receiptLogic.update(receipt);
			
			//删除当前发货单明细，重新登录
			receiptLogic.deleteDtl(receipt);
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
					
					continue;
				}
				
				//验证订单产品存在性
				if(validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, receiptDtl.getTradeOrderId(), receiptDtl.getProductionId(), receiptDtl.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.NOT_EXISTS_PRODUCTION_IN_RECEIPT, receiptDtl.getTradeOrderId(), receiptDtl.getProductionId(), receiptDtl.getVersionNo()));
				}

				receiptDtl.setReceiptId(receipt.getReceiptId());
				DbCommonUtil.setCreateCommon(receiptDtl);
				receiptLogic.insertDtl(receiptDtl);
			}
			
			//删除当前发货单明细，重新登录
			receiptLogic.deleteDtl4C(receipt);
			for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
					
					continue;
				}
				
				//验证订单产品存在性
				if(validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDERDTL, receiptDtl4C.getTradeOrderId(), receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo()) == null){
					
					throw new ValiationException(String.format(Message.NOT_EXISTS_PRODUCTION_IN_RECEIPT, receiptDtl4C.getTradeOrderId(), receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo()));
				}

				receiptDtl4C.setReceiptId(receipt.getReceiptId());
				DbCommonUtil.setCreateCommon(receiptDtl4C);
				receiptLogic.insertDtl4C(receiptDtl4C);
			}
			
			//根据发货情况更新订单状态
			List<String> tradeOrderIdList = new ArrayList<String>();
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(!tradeOrderIdList.contains(receiptDtl.getTradeOrderId())){
					
					tradeOrderIdList.add(receiptDtl.getTradeOrderId());
				}
			}
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlRemoveList()) {
				
				if(!tradeOrderIdList.contains(receiptDtl.getTradeOrderId())){
					
					tradeOrderIdList.add(receiptDtl.getTradeOrderId());
				}
			}
			
			for (String tradeOrderId : tradeOrderIdList) {
				
				//全部发送完毕，订单状态完了
				if(validationAPIService.validateIsAllSent(tradeOrderId)){
					
					tradeOrderService.updateStatus(tradeOrderId, "2");
				}else{
				//反之则订单状态确定
					
					tradeOrderService.updateStatus(tradeOrderId, "1");
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String receiptIdSelected) throws Exception{
		
		Receipt receipt = new Receipt();
		receipt.setReceiptId(receiptIdSelected);
		
		//获取明细LIST
		receipt = receiptLogic.findByKey(receiptIdSelected);
		List<ReceiptDtl> receiptDtlList = receiptLogic.findDtlByKey(receiptIdSelected);
		
		int count = 0;
		try {
			//关联报关发票号码 (存在的话不能删除)
			if(receiptLogic.validateRelateReceipt(null, null, receipt.getReceiptNo()) != 0){
				
				throw new ValiationException(Message.RELATE_RECEIPTNO);
			}
			
			count = receiptLogic.delete(receipt);
			receiptLogic.deleteDtl(receipt);
			receiptLogic.deleteDtl4C(receipt);
			
			//根据发货情况更新订单状态
			List<String> tradeOrderIdList = new ArrayList<String>();
			for (ReceiptDtl receiptDtl : receiptDtlList) {
				
				if(!tradeOrderIdList.contains(receiptDtl.getTradeOrderId())){
					
					tradeOrderIdList.add(receiptDtl.getTradeOrderId());
				}
			}
			
			for (String tradeOrderId : tradeOrderIdList) {
				
				//全部发送完毕，订单状态完了
				if(validationAPIService.validateIsAllSent(tradeOrderId)){
					
					tradeOrderService.updateStatus(tradeOrderId, "2");
				}else{
				//反之则订单状态确定
					
					tradeOrderService.updateStatus(tradeOrderId, "1");
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
}
