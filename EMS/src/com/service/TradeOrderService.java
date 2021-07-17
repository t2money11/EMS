package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.InquiryDtl;
import com.entity.InterTradeOrder;
import com.entity.InterTradeOrderDtl;
import com.entity.Production;
import com.entity.TradeOrder;
import com.entity.TradeOrderDtl;
import com.entity.TradeOrderPop;
import com.entity.Vendor;
import com.exception.ValiationException;
import com.logic.InquiryLogic;
import com.logic.InterTradeOrderDtlLogic;
import com.logic.InterTradeOrderLogic;
import com.logic.TradeOrderDtlLogic;
import com.logic.TradeOrderLogic;
import com.util.DbCommonUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

@Service
public class TradeOrderService{
	
	private TradeOrderLogic tradeOrderLogic = null;
	public void setTradeOrderLogic(TradeOrderLogic tradeOrderLogic) {
		this.tradeOrderLogic = tradeOrderLogic;
	}
	
	private TradeOrderDtlLogic tradeOrderDtlLogic = null;
	public void setTradeOrderDtlLogic(TradeOrderDtlLogic tradeOrderDtlLogic) {
		this.tradeOrderDtlLogic = tradeOrderDtlLogic;
	}
	
	private InterTradeOrderLogic interTradeOrderLogic = null;
	public void setInterTradeOrderLogic(InterTradeOrderLogic interTradeOrderLogic) {
		this.interTradeOrderLogic = interTradeOrderLogic;
	}
	
	private InterTradeOrderDtlLogic interTradeOrderDtlLogic = null;
	public void setInterTradeOrderDtlLogic(InterTradeOrderDtlLogic interTradeOrderDtlLogic) {
		this.interTradeOrderDtlLogic = interTradeOrderDtlLogic;
	}
	
	private InquiryLogic inquiryLogic = null;
	public void setInquiryLogic(InquiryLogic inquiryLogic) {
		this.inquiryLogic = inquiryLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public List<InquiryDtl> getFromInquiry(TradeOrder tradeOrder) throws Exception{ 
		
		List<InquiryDtl> inquiryDtlList = inquiryLogic.findDtlByKey(tradeOrder.getInquiryId());
		
		if(IsEmptyUtil.isEmpty(inquiryDtlList)){
			
			throw new Exception(Message.NOT_FOUND_INQUIRY);
		}
		
		return inquiryDtlList;
		
//		for (InquiryDtl inquiryDtl : inquiryDtlList) {
//			for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
//				
//				if(inquiryDtl.getProductionId().equals(tradeOrderDtl.getProductionId())
//						&& inquiryDtl.getVersionNo().equals(tradeOrderDtl.getVersionNo())){
//					
//					tradeOrderDtl.setUnitPrice(inquiryDtl.getFinalPrice());
//					break;
//				}
//			}
//			
//			for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
//				
//				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
//					
//					if(inquiryDtl.getProductionId().equals(interTradeOrderDtl.getProductionId())
//							&& inquiryDtl.getVersionNo().equals(interTradeOrderDtl.getVersionNo())){
//						
//						interTradeOrderDtl.setUnitPrice(inquiryDtl.getRMB());
//						break;
//					}
//				}
//			}
//		}
	}
	
	public void calculate(TradeOrder tradeOrder){
		
		double amountTtl = 0;
		
		for (int i = 0; i < tradeOrder.getTradeOrderDtlList().size(); i++) {
			
			TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
			
			//计算金额
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getUnitPrice()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getQuantity())){
				
				//计算美元小计
				double amount = Double.parseDouble(tradeOrderDtl.getUnitPrice()) * Double.parseDouble(tradeOrderDtl.getQuantity());
				//SUM美元总计
				amountTtl = amountTtl + amount;
				//设定美元小计
				tradeOrderDtl.setAmount(StringHandler.numFormat(amount, StringHandler.NUMBER_FORMAT2));
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee1())){
				
				//SUM美元总计
				amountTtl = amountTtl + Double.parseDouble(tradeOrderDtl.getFee1());
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee2())){
				
				//SUM美元总计
				amountTtl = amountTtl + Double.parseDouble(tradeOrderDtl.getFee2());
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee3())){
				
				//SUM美元总计
				amountTtl = amountTtl + Double.parseDouble(tradeOrderDtl.getFee3());
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee4())){
				
				//SUM美元总计
				amountTtl = amountTtl + Double.parseDouble(tradeOrderDtl.getFee4());
			}
		}
		
		//设定美元总计
		if(amountTtl == 0){
			
			tradeOrder.setAmountTtl(null);
		}else{
			
			tradeOrder.setAmountTtl(StringHandler.numFormat(amountTtl, StringHandler.NUMBER_FORMAT2));
		}
		
		for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
			
			double amountRMBTtl = 0;
			
			InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
			
			for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
				
				InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
				
				//设定出货数量
				for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
					
					if (tradeOrderDtl.getProductionId().equals(interTradeOrderDtl.getProductionId())
							&& tradeOrderDtl.getVersionNo().equals(interTradeOrderDtl.getVersionNo())) {
						
						interTradeOrderDtl.setQuantitySent(tradeOrderDtl.getQuantitySent());
						break;
					}
				}
				
				//计算金额
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getUnitPrice())
						&& !IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())){
					
					//计算RMB小计
					double amountRMB = Double.parseDouble(interTradeOrderDtl.getUnitPrice()) * Double.parseDouble(interTradeOrderDtl.getQuantity());

					//RMB总计
					amountRMBTtl = amountRMBTtl + amountRMB;
					//设定RMB小计
					interTradeOrderDtl.setAmount(StringHandler.numFormat(amountRMB, StringHandler.NUMBER_FORMAT2));
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee1())){
					
					//RMB总计
					amountRMBTtl = amountRMBTtl + Double.parseDouble(interTradeOrderDtl.getFee1());
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee2())){
					
					//RMB总计
					amountRMBTtl = amountRMBTtl + Double.parseDouble(interTradeOrderDtl.getFee2());
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee3())){
					
					//RMB总计
					amountRMBTtl = amountRMBTtl + Double.parseDouble(interTradeOrderDtl.getFee3());
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee4())){
					
					//RMB总计
					amountRMBTtl = amountRMBTtl + Double.parseDouble(interTradeOrderDtl.getFee4());
				}
				
				//计算箱数，净重，毛重，体积
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())){
					
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getOutside())){
						
						interTradeOrderDtl.setBoxAmount(null);
						interTradeOrderDtl.setVolumeTtl(null);
						interTradeOrderDtl.setNetWeightTtl(null);
						interTradeOrderDtl.setGrossWeightTtl(null);
						interTradeOrderDtl.setInside(null);
						
					}else if(Integer.parseInt(interTradeOrderDtl.getOutside()) == 0){
						
						interTradeOrderDtl.setBoxAmount("0");
						interTradeOrderDtl.setVolumeTtl("0.0000");
						interTradeOrderDtl.setNetWeightTtl("0.00");
						interTradeOrderDtl.setGrossWeightTtl("0.00");
						interTradeOrderDtl.setInside("0");
						
					}else if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())){
						
						interTradeOrderDtl.setBoxAmount(null);
						interTradeOrderDtl.setVolumeTtl(null);
						interTradeOrderDtl.setNetWeightTtl(null);
						interTradeOrderDtl.setGrossWeightTtl(null);
					}else{
						
						int outside = Integer.parseInt(interTradeOrderDtl.getOutside());
//						int inside = Integer.parseInt((IsEmptyUtil.isEmpty(interTradeOrderDtl.getInside()) || Integer.parseInt(interTradeOrderDtl.getInside()) == 0) ? "1" : interTradeOrderDtl.getInside());
							
						int quantity = Integer.parseInt(interTradeOrderDtl.getQuantity());
						int boxAmount = quantity / outside;
						if(quantity <= outside){
							
							boxAmount = 1;
						}else if(quantity % outside > 0){
							
							boxAmount = boxAmount + 1;
						}
						interTradeOrderDtl.setBoxAmount(String.valueOf(boxAmount));
						
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getVolume())){
							
							interTradeOrderDtl.setVolumeTtl(null);
						}else{
							
							double volume = Double.parseDouble(interTradeOrderDtl.getVolume());
							interTradeOrderDtl.setVolumeTtl(StringHandler.numFormat(volume * boxAmount, StringHandler.NUMBER_FORMAT4));
						}
						
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getNetWeight())){
							
							interTradeOrderDtl.setNetWeightTtl(null);
						}else{
							
							double netWeight = Double.parseDouble(interTradeOrderDtl.getNetWeight());
							interTradeOrderDtl.setNetWeightTtl(StringHandler.numFormat(netWeight * boxAmount, StringHandler.NUMBER_FORMAT2));
						}

						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getGrossWeight())){
							
							interTradeOrderDtl.setGrossWeightTtl(null);
						}else{
							
							double grossWeight = Double.parseDouble(interTradeOrderDtl.getGrossWeight());
							interTradeOrderDtl.setGrossWeightTtl(StringHandler.numFormat(grossWeight * boxAmount, StringHandler.NUMBER_FORMAT2));
						}
						
					}
				}
				
			}
			if(amountRMBTtl == 0){
				
				interTradeOrder.setAmountTtl(null);
			}else{
				
				interTradeOrder.setAmountTtl(StringHandler.numFormat(amountRMBTtl, StringHandler.NUMBER_FORMAT2));
			}
		}
	}
	
	private TradeOrder getTradeOrderByTradeOrderId(String tradeOrderIdSelected){
		
		TradeOrder tradeOrder = tradeOrderLogic.findByKey(tradeOrderIdSelected, null, null);
		
		List<TradeOrderDtl> tradeOrderDtlList = tradeOrderDtlLogic.findByTradeOrderId(tradeOrderIdSelected);
		if(!IsEmptyUtil.isEmpty(tradeOrderDtlList)){
			
			tradeOrder.setTradeOrderDtlList(tradeOrderDtlList);
		}
		
		List<InterTradeOrder> interTradeOrderList = interTradeOrderLogic.findByTradeOrderId(tradeOrderIdSelected);
		if(!IsEmptyUtil.isEmpty(interTradeOrderList)){
			
			tradeOrder.setInterTradeOrderList(interTradeOrderList);
		}
		
		for (InterTradeOrder interTradeOrder : interTradeOrderList) {
			
			List<InterTradeOrderDtl> interTradeOrderDtlList = interTradeOrderDtlLogic.findByInterTradeOrderId(interTradeOrder.getInterTradeOrderId());
			if(!IsEmptyUtil.isEmpty(interTradeOrderDtlList)){
				
				interTradeOrder.setInterTradeOrderDtlList(interTradeOrderDtlList);
			}
		}
		
		calculate(tradeOrder);
		
		return tradeOrder;
	}
	
	public TradeOrder findByKey(String tradeOrderIdSelected) throws Exception{
		
		return getTradeOrderByTradeOrderId(tradeOrderIdSelected);
    }
	
	public TradeOrder findLastTradeOrderByProductionId(String productionId){
		
		TradeOrder tradeOrder = tradeOrderLogic.findLastTradeOrderByProductionId(productionId);
		return tradeOrder;
	}
	
	public TradeOrder tradeOrderSearch(TradeOrder tradeOrder) throws Exception{
		
		int totalCount = 0;
		
		totalCount = tradeOrderLogic.countFindByProperty(tradeOrder);
		if(totalCount != 0){
			
			List<TradeOrder> tradeOrderList = tradeOrderLogic.findByProperty(tradeOrder);
			tradeOrder.setResultTradeOrderList(tradeOrderList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		tradeOrder.getPageInfo().setTotalCount(totalCount);
		
        return tradeOrder;
    }
	
	public TradeOrderPop tradeOrderPopSearch(TradeOrderPop tradeOrderPop) throws Exception{
		
		int totalCount = 0;
		
		totalCount = tradeOrderLogic.countFindPopByProperty(tradeOrderPop);
		if(totalCount != 0){
			
			List<TradeOrderPop> tradeOrderPopList = tradeOrderLogic.findPopByProperty(tradeOrderPop);
			tradeOrderPop.setResultTradeOrderPopList(tradeOrderPopList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		tradeOrderPop.getPageInfo().setTotalCount(totalCount);
		
        return tradeOrderPop;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(TradeOrder tradeOrder) throws Exception{
		
		try {
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, tradeOrder.getCustomerId());
			if(customerUpdateTime == null ||tradeOrder.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, tradeOrderDtl.getProductionId(), tradeOrderDtl.getVersionNo());
				if(productionUpdateTime == null ||tradeOrderDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}
			
			for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
				
				//更新冲突验证(供应商)
				Timestamp vendorUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.VENDOR, interTradeOrder.getVendorId());
				if(vendorUpdateTime == null ||interTradeOrder.getVendorUpdateTime().compareTo(vendorUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
					
					//验证产品，客户，供应商信息是否匹配
					if(validationAPIService.validatePVC(interTradeOrder.getVendorId(), 
							tradeOrder.getCustomerId(), 
							interTradeOrderDtl.getProductionId(),
							interTradeOrderDtl.getVersionNo()
							) == false){
						throw new ValiationException(String.format(Message.PVC_UNMATCH, interTradeOrderDtl.getProductionId(), interTradeOrderDtl.getVersionNo()));
					}
				}
			}
			
			String tradeOrderId = tradeOrderLogic.getTradeOrderId4New();
			String contractNo = tradeOrderLogic.getContractNo4New(tradeOrder.getCustomerId(), tradeOrder.getCustomerName());
			
			if(("1".equals(tradeOrder.getStatus())
					|| "2".equals(tradeOrder.getStatus()))
					&& "1".equals(tradeOrder.getInquiryNeeded())){
			//订单确认，完成时，验证询单是否存在
				
				String errorMsg = "";
				for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
					
					InquiryDtl inquiryDtl = inquiryLogic.findDtlByTradeOrder(null
							, tradeOrderDtl.getProductionId()
							, tradeOrderDtl.getVersionNo()
							, tradeOrderDtl.getUnitPrice()
							, tradeOrder.getInquiryId());
					if(inquiryDtl == null){
						
						errorMsg = errorMsg.concat(String.format(Message.NOT_EXISTS_INQUIRY
								, tradeOrderDtl.getProductionId()
								, tradeOrderDtl.getVersionNo()
								, tradeOrderDtl.getUnitPrice())).concat("\r\n");
					}else{
						
						inquiryDtl.setTradeOrderId(tradeOrderId);
						inquiryDtl.setProductionId(tradeOrderDtl.getProductionId());
						inquiryDtl.setVersionNo(tradeOrderDtl.getVersionNo());
						DbCommonUtil.setUpdateCommon(inquiryDtl);
						inquiryLogic.updateDtlOnTradeOrderId(inquiryDtl);
					}
				}
				if(errorMsg.length() != 0){
					
					throw new ValiationException(errorMsg);
				}
			}else{
			//订单未确认时，更新对应询单中的订单号为空
				InquiryDtl inquiryDtl = new InquiryDtl();
				inquiryDtl.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setUpdateCommon(inquiryDtl);
				inquiryLogic.clearDtlOnTradeOrderId(inquiryDtl);
			}
			
			//验证当前用户是否有权限操作该客户的订单数据
			if(!validationAPIService.validateCU(tradeOrder.getCustomerId())){
				throw new ValiationException(Message.NO_CUSTOMER_AUTH);
			}
			
			//重复PO确认
			if(!IsEmptyUtil.isEmpty(tradeOrder.getPo()) && tradeOrderLogic.validateDuplicatedPOorContractNo(null, null, tradeOrder.getPo(), tradeOrder.getCustomerId()) != 0){
				throw new ValiationException(Message.DUPLICATED_PO);
			}
			
			//重复ContractNo确认
			if(!IsEmptyUtil.isEmpty(tradeOrder.getContractNo()) && tradeOrderLogic.validateDuplicatedPOorContractNo(null, tradeOrder.getContractNo(), null, null) != 0){
				throw new ValiationException(Message.DUPLICATED_CONTRACTNO);
			}
			
			//新建订单
			//订单号采番
			tradeOrder.setTradeOrderId(tradeOrderId);
			if(IsEmptyUtil.isEmpty(tradeOrder.getContractNo())){
				tradeOrder.setContractNo(contractNo);
			}
			DbCommonUtil.setCreateCommon(tradeOrder);
			tradeOrderLogic.insert(tradeOrder);
			
			//新建订单明细
			for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
				tradeOrderDtl.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setCreateCommon(tradeOrderDtl);
				tradeOrderDtlLogic.insert(tradeOrderDtl);
			}
			
			//新建内贸合同 状态为未确定
			for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
				
				interTradeOrder.setInterTradeOrderId(tradeOrder.getTradeOrderId() + "-" + interTradeOrder.getVendorId());
				interTradeOrder.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setCreateCommon(interTradeOrder);
				interTradeOrderLogic.insert(interTradeOrder);
				
				//新建内贸合同明细
				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
					
					interTradeOrderDtl.setInterTradeOrderId(interTradeOrder.getInterTradeOrderId());
					DbCommonUtil.setCreateCommon(interTradeOrderDtl);
					interTradeOrderDtlLogic.insert(interTradeOrderDtl);
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return tradeOrder.getTradeOrderId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(TradeOrder tradeOrder) throws Exception{
		
		try {
			
			//更新冲突验证
			Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.TRADEORDER, tradeOrder.getTradeOrderId());
			if(updateTime == null ||tradeOrder.getUpdateTime().compareTo(updateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, tradeOrder.getCustomerId());
			if(customerUpdateTime == null ||tradeOrder.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, tradeOrderDtl.getProductionId(), tradeOrderDtl.getVersionNo());
				if(productionUpdateTime == null ||tradeOrderDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
			}
			
			for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
				
				//更新冲突验证(供应商)
				Timestamp vendorUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.VENDOR, interTradeOrder.getVendorId());
				if(vendorUpdateTime == null ||interTradeOrder.getVendorUpdateTime().compareTo(vendorUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
					
					//验证产品，客户，供应商信息是否匹配
					if(validationAPIService.validatePVC(interTradeOrder.getVendorId(), 
							tradeOrder.getCustomerId(), 
							interTradeOrderDtl.getProductionId(),
							interTradeOrderDtl.getVersionNo()
							) == false){
						throw new ValiationException(String.format(Message.PVC_UNMATCH, interTradeOrderDtl.getProductionId(), interTradeOrderDtl.getVersionNo()));
					}
				}
			}
			
			if(("1".equals(tradeOrder.getStatus())
					|| "2".equals(tradeOrder.getStatus()))
					&& "1".equals(tradeOrder.getInquiryNeeded())){
			//订单确认，完成时，验证询单是否存在
				
				String errorMsg = "";
				for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
					
					InquiryDtl inquiryDtl = inquiryLogic.findDtlByTradeOrder(tradeOrderDtl.getTradeOrderId()
							, tradeOrderDtl.getProductionId()
							, tradeOrderDtl.getVersionNo()
							, tradeOrderDtl.getUnitPrice()
							, tradeOrder.getInquiryId());
					if(inquiryDtl == null){
						
						errorMsg = errorMsg.concat(String.format(Message.NOT_EXISTS_INQUIRY
								, tradeOrderDtl.getProductionId()
								, tradeOrderDtl.getVersionNo()
								, tradeOrderDtl.getUnitPrice())).concat("<br/>");
					}else{
						
						inquiryDtl.setTradeOrderId(tradeOrderDtl.getTradeOrderId());
						inquiryDtl.setProductionId(tradeOrderDtl.getProductionId());
						inquiryDtl.setVersionNo(tradeOrderDtl.getVersionNo());
						DbCommonUtil.setUpdateCommon(inquiryDtl);
						inquiryLogic.updateDtlOnTradeOrderId(inquiryDtl);
					}
				}
				if(errorMsg.length() != 0){
					
					throw new ValiationException(errorMsg);
				}
			}else{
			//订单未确认时，更新对应询单中的订单号为空
				InquiryDtl inquiryDtl = new InquiryDtl();
				inquiryDtl.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setUpdateCommon(inquiryDtl);
				inquiryLogic.clearDtlOnTradeOrderId(inquiryDtl);
			}
			
			//验证当前用户是否有权限操作该客户的订单数据
			if(!validationAPIService.validateCU(tradeOrder.getCustomerId())){
				throw new ValiationException(Message.NO_CUSTOMER_AUTH);
			}
			
			//重复PO确认
			if(!IsEmptyUtil.isEmpty(tradeOrder.getPo()) && tradeOrderLogic.validateDuplicatedPOorContractNo(tradeOrder.getTradeOrderId(), null, tradeOrder.getPo(), tradeOrder.getCustomerId()) != 0){
				throw new ValiationException(Message.DUPLICATED_PO);
			}
			
			//重复ContractNo确认
			if(!IsEmptyUtil.isEmpty(tradeOrder.getContractNo()) && tradeOrderLogic.validateDuplicatedPOorContractNo(tradeOrder.getTradeOrderId(), tradeOrder.getContractNo(), null, null) != 0){
				throw new ValiationException(Message.DUPLICATED_CONTRACTNO);
			}
			
			//更新订单
			DbCommonUtil.setUpdateCommon(tradeOrder);
			tradeOrderLogic.update(tradeOrder);
			
			//删除当前订单明细，重新登录
			tradeOrderDtlLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
				
				tradeOrderDtl.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setCreateCommon(tradeOrderDtl);
				tradeOrderDtlLogic.insert(tradeOrderDtl);
			}
			
			//删除当前内贸合同明细，重新登录
			interTradeOrderDtlLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			//删除当前内贸合同，重新登录
			interTradeOrderLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
				
				interTradeOrder.setInterTradeOrderId(tradeOrder.getTradeOrderId() + "-" + interTradeOrder.getVendorId());
				interTradeOrder.setTradeOrderId(tradeOrder.getTradeOrderId());
				DbCommonUtil.setCreateCommon(interTradeOrder);
				interTradeOrderLogic.insert(interTradeOrder);
				
				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
					
					interTradeOrderDtl.setInterTradeOrderId(interTradeOrder.getInterTradeOrderId());
					DbCommonUtil.setCreateCommon(interTradeOrderDtl);
					interTradeOrderDtlLogic.insert(interTradeOrderDtl);
				}
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void updateStatus(String tradeOrderId, String status) throws Exception{
		
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeOrderId(tradeOrderId);
		tradeOrder.setStatus(status);
		
		try {
			DbCommonUtil.setUpdateCommon(tradeOrder);
			tradeOrderLogic.updateStatus(tradeOrder);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String tradeOrderIdSelected) throws Exception{
		
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeOrderId(tradeOrderIdSelected);
		int count = 0;
		try {
			//有出货记录的订单不能删除
			List<TradeOrderDtl> tradeOrderDtlList = tradeOrderDtlLogic.findByTradeOrderId(tradeOrderIdSelected);
			for (TradeOrderDtl tradeOrderDtl : tradeOrderDtlList) {
				
				if(tradeOrderDtl.getQuantitySent() != null){
					
					throw new ValiationException(Message.TRADEORDER_DELETE_ERROR_1);
				}
				
				if(!"0".equals(tradeOrderDtl.getCountComplaintDtl())){
					
					throw new ValiationException(Message.TRADEORDER_DELETE_ERROR_2);
				}
			}
			
			count = tradeOrderLogic.delete(tradeOrder);
			tradeOrderDtlLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			interTradeOrderDtlLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			interTradeOrderLogic.deleteByTradeOrderId(tradeOrder.getTradeOrderId());
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
    public String findPreviousRMBByProductionId(String productionId){
		
		return tradeOrderLogic.findPreviousRMBByProductionId(productionId);
    }
    
    public int count4Complaint(String tradeOrderId, String productionId, String versionNo) throws Exception{
		
		return tradeOrderLogic.count4Complaint(tradeOrderId, productionId, versionNo);
    }
}
