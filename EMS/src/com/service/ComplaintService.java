package com.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.ConstantParam;
import com.context.Message;
import com.entity.Complaint;
import com.entity.ComplaintDtl;
import com.entity.TradeOrder;
import com.entity.TradeOrderPop;
import com.exception.ValiationException;
import com.logic.ComplaintLogic;
import com.logic.TradeOrderLogic;
import com.util.DbCommonUtil;
import com.util.Filehandler;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

@Service
public class ComplaintService{
	
	private ComplaintLogic complaintLogic = null;
	public void setComplaintLogic(ComplaintLogic complaintLogic) {
		this.complaintLogic = complaintLogic;
	}
	
	private TradeOrderLogic tradeOrderLogic = null;
	public void setTradeOrderLogic(TradeOrderLogic tradeOrderLogic) {
		this.tradeOrderLogic = tradeOrderLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	
	public Complaint findByKey(String complaintIdSelected) throws Exception{
		
		Complaint complaint = complaintLogic.findByKey(complaintIdSelected);
		return complaint;
    }
	
	public void calculate(ComplaintDtl complaintDtl){
		
		//计算金额
		//计算美元小计
		double tAmount = Double.parseDouble(complaintDtl.gettUnitPrice()) * Double.parseDouble(complaintDtl.getQuantity());
		//设定美元小计
		complaintDtl.settAmount(StringHandler.numFormat(tAmount, StringHandler.NUMBER_FORMAT2));
		//计算RMB小计
		double iAmount = Double.parseDouble(complaintDtl.getiUnitPrice()) * Double.parseDouble(complaintDtl.getQuantity());
		//设定RMB小计
		complaintDtl.setiAmount(StringHandler.numFormat(iAmount, StringHandler.NUMBER_FORMAT2));
		
		//计算箱数，净重，毛重，体积
		if(IsEmptyUtil.isEmpty(complaintDtl.getOutside())){
			
			complaintDtl.setBoxAmount(null);
			complaintDtl.setVolumeTtl(null);
			complaintDtl.setNetWeightTtl(null);
			complaintDtl.setGrossWeightTtl(null);
			complaintDtl.setInside(null);
			
		}else if(Integer.parseInt(complaintDtl.getOutside()) == 0){
			
			complaintDtl.setBoxAmount("0");
			complaintDtl.setVolumeTtl("0.0000");
			complaintDtl.setNetWeightTtl("0.00");
			complaintDtl.setGrossWeightTtl("0.00");
			complaintDtl.setInside("0");
			
		}else if(IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
			
			complaintDtl.setBoxAmount(null);
			complaintDtl.setVolumeTtl(null);
			complaintDtl.setNetWeightTtl(null);
			complaintDtl.setGrossWeightTtl(null);
		}else{
			
			int outside = Integer.parseInt(complaintDtl.getOutside());
			int quantity = Integer.parseInt(complaintDtl.getQuantity());
			int boxAmount = 0;
			if(IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				boxAmount = quantity / outside;
				if(quantity <= outside){
					
					boxAmount = 1;
				}else if(quantity % outside > 0){
					
					boxAmount = boxAmount + 1;
				}
			}else{
				
				boxAmount = Integer.parseInt(complaintDtl.getBoxAmount());
			}
			complaintDtl.setBoxAmount(String.valueOf(boxAmount));
			
			if(IsEmptyUtil.isEmpty(complaintDtl.getVolume())){
				
				complaintDtl.setVolumeTtl(null);
			}else{
				
				double volume = Double.parseDouble(complaintDtl.getVolume());
				complaintDtl.setVolumeTtl(StringHandler.numFormat(volume * boxAmount, StringHandler.NUMBER_FORMAT4));
			}
			
			if(IsEmptyUtil.isEmpty(complaintDtl.getNetWeight())){
				
				complaintDtl.setNetWeightTtl(null);
			}else{
				
				double netWeight = Double.parseDouble(complaintDtl.getNetWeight());
				complaintDtl.setNetWeightTtl(StringHandler.numFormat(netWeight * boxAmount, StringHandler.NUMBER_FORMAT2));
			}

			if(IsEmptyUtil.isEmpty(complaintDtl.getGrossWeight())){
				
				complaintDtl.setGrossWeightTtl(null);
			}else{
				
				double grossWeight = Double.parseDouble(complaintDtl.getGrossWeight());
				complaintDtl.setGrossWeightTtl(StringHandler.numFormat(grossWeight * boxAmount, StringHandler.NUMBER_FORMAT2));
			}
		}
	}
	
	public List<ComplaintDtl> findDtlByKey(String complaintIdSelected) throws Exception{
		
		List<String> errorTradeOrderIdList = validationAPIService.validateUnConfirmedC(complaintIdSelected);
		String temp = "";
		for (String errorTradeOrderId : errorTradeOrderIdList) {
			
			temp = temp.concat(errorTradeOrderId).concat(", ");
		}
		
		if(!IsEmptyUtil.isEmpty(errorTradeOrderIdList)){
			
			temp = temp.substring(0, temp.length() - 2);
			throw new Exception(String.format(Message.HAS_NOCONFIRMED_TRADEORDER_4_C, temp));
		}
		
		List<ComplaintDtl> complaintDtlList = complaintLogic.findDtlByKey(complaintIdSelected);
		for (ComplaintDtl complaintDtl : complaintDtlList) {
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())
					&& !IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
				
				calculate(complaintDtl);
			}
		}
		return complaintDtlList;
    }
	
	public ComplaintDtl findDtlByPo(String customerId, ComplaintDtl complaintDtl, boolean isRef) throws Exception{
		
		TradeOrder tradeOrder = tradeOrderLogic.findByKey(null, complaintDtl.getPo(), customerId);
		if(tradeOrder == null){
			
			complaintDtl.setTradeOrderId(null);
			complaintDtl.setQuantity(null);
			complaintDtl.setBoxAmount(null);
			complaintDtl.setVolumeTtl(null);
			complaintDtl.setGrossWeightTtl(null);
			complaintDtl.setNetWeightTtl(null);
			complaintDtl.settAmount(null);
			complaintDtl.setiAmount(null);
			throw new Exception(String.format(Message.NOT_FOUND_PO, complaintDtl.getPo(), complaintDtl.getProductionId(), complaintDtl.getVersionNo()));
		}
		
		List<ComplaintDtl> complaintDtlList = complaintLogic.findDtlByTradeOrderId(tradeOrder.getTradeOrderId(), complaintDtl.getProductionId(), complaintDtl.getVersionNo());
		if(IsEmptyUtil.isEmpty(complaintDtlList)){
			
			complaintDtl.setTradeOrderId(null);
			complaintDtl.setQuantity(null);
			complaintDtl.setBoxAmount(null);
			complaintDtl.setVolumeTtl(null);
			complaintDtl.setGrossWeightTtl(null);
			complaintDtl.setNetWeightTtl(null);
			complaintDtl.settAmount(null);
			complaintDtl.setiAmount(null);
			throw new Exception(String.format(Message.NOT_FOUND_PO, complaintDtl.getPo(), complaintDtl.getProductionId(), complaintDtl.getVersionNo()));
		}else{
			
			ComplaintDtl temp = complaintDtlList.get(0);
			complaintDtl.setTradeOrderId(tradeOrder.getTradeOrderId());
			complaintDtl.setVolume(temp.getVolume());
			complaintDtl.setGrossWeight(temp.getGrossWeight());
			complaintDtl.setNetWeight(temp.getNetWeight());
			complaintDtl.setInside(temp.getInside());
			complaintDtl.setOutside(temp.getOutside());
			complaintDtl.settUnitPrice(temp.gettUnitPrice());
			complaintDtl.setiUnitPrice(temp.getiUnitPrice());
			
			if(isRef){
				
				complaintDtl.setQuantity(null);
				complaintDtl.setBoxAmount(null);
				complaintDtl.setVolumeTtl(null);
				complaintDtl.setGrossWeightTtl(null);
				complaintDtl.setNetWeightTtl(null);
				complaintDtl.settAmount(null);
				complaintDtl.setiAmount(null);
			}
		}
		
		return complaintDtl;
    }
	
	public Complaint complaintSearch(Complaint complaint) throws Exception{
		
		int totalCount = 0;
		
		totalCount = complaintLogic.countFindByProperty(complaint);
		if(totalCount != 0){
			
			List<Complaint> complaintList = complaintLogic.findByProperty(complaint);
			complaint.setResultComplaintList(complaintList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		complaint.getPageInfo().setTotalCount(totalCount);
		
        return complaint;
    }
	
	public TradeOrderPop tradeOrderPopSearch(TradeOrderPop tradeOrderPop) throws Exception{
		
		int totalCount = 0;
		
		totalCount = complaintLogic.countFindPopByProperty(tradeOrderPop);
		if(totalCount != 0){
			
			List<TradeOrderPop> tradeOrderPopList = complaintLogic.findPopByProperty(tradeOrderPop);
			tradeOrderPop.setResultTradeOrderPopList(tradeOrderPopList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		tradeOrderPop.getPageInfo().setTotalCount(totalCount);
		
        return tradeOrderPop;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Complaint complaint) throws Exception{
		
		try {
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, complaint.getCustomerId());
			if(customerUpdateTime == null ||complaint.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (ComplaintDtl complaintDtl : complaint.getComplaintDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, complaintDtl.getProductionId(), complaintDtl.getVersionNo());
				if(productionUpdateTime == null ||complaintDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//验证订单，产品是否匹配
				if(!IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())
						&& !"unDefined".equals(complaintDtl.getTradeOrderId())){
					
					int count = tradeOrderService.count4Complaint(complaintDtl.getTradeOrderId()
							, complaintDtl.getProductionId()
							, complaintDtl.getVersionNo());
					
					if(count == 0){
						
						throw new ValiationException(String.format(Message.NOT_FOUND_TRADRORDERID, complaintDtl.getTradeOrderId(), complaintDtl.getProductionId(), complaintDtl.getVersionNo()));
					}
				}
				
				int count = 0;
				//查看是否有重复产品数据
				for (ComplaintDtl temp : complaint.getComplaintDtlList()) {
					
					if(StringHandler.getStr(complaintDtl.getTradeOrderId()).equals(StringHandler.getStr(temp.getTradeOrderId()))
							&& StringHandler.getStr(complaintDtl.getProductionId()).equals(StringHandler.getStr(temp.getProductionId()))
							&& StringHandler.getStr(complaintDtl.getVersionNo()).equals(StringHandler.getStr(temp.getVersionNo()))
							&& StringHandler.getStr(complaintDtl.getHandleType()).equals(StringHandler.getStr(temp.getHandleType()))){
						
						count++;
						if(count == 2){
							
							throw new ValiationException(String.format(Message.ADD_MULTI
									, StringHandler.getStr(temp.getProductionId())
									, StringHandler.getStr(temp.getVersionNo())
									, StringHandler.getStr(temp.getTradeOrderId())
									, StringHandler.getStr(temp.getHandleType()).equals("0") ? "退款" : "补货"));
						}
					}
				}
			}
			
			//采番
			complaint.setComplaintId(complaintLogic.getComplaintId4New());
			DbCommonUtil.setCreateCommon(complaint);
			complaintLogic.insert(complaint);
			
			for (ComplaintDtl complaintDtl : complaint.getComplaintDtlList()) {
				
				complaintDtl.setComplaintId(complaint.getComplaintId());
				DbCommonUtil.setCreateCommon(complaintDtl);
				complaintLogic.insertDtl(complaintDtl);
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return complaint.getComplaintId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Complaint complaint) throws Exception{
		
		try {
			
			//更新冲突验证
			Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.COMPLAINT, complaint.getComplaintId());
			if(updateTime == null ||complaint.getUpdateTime().compareTo(updateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			//更新冲突验证(客户)
			Timestamp customerUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.CUSTOMER, complaint.getCustomerId());
			if(customerUpdateTime == null ||complaint.getCustomerUpdateTime().compareTo(customerUpdateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			for (ComplaintDtl complaintDtl : complaint.getComplaintDtlList()) {
				
				//更新冲突验证(产品)
				Timestamp productionUpdateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, complaintDtl.getProductionId(), complaintDtl.getVersionNo());
				if(productionUpdateTime == null ||complaintDtl.getProductionUpdateTime().compareTo(productionUpdateTime) != 0){
					
					throw new ValiationException(Message.SYNCHRONIZED_ERROR);
				}
				
				//验证订单，产品是否匹配
				if(!IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())
						&& !"unDefined".equals(complaintDtl.getTradeOrderId())){
					
					int count = tradeOrderService.count4Complaint(complaintDtl.getTradeOrderId()
							, complaintDtl.getProductionId()
							, complaintDtl.getVersionNo());
					
					if(count == 0){
						
						throw new ValiationException(String.format(Message.NOT_FOUND_TRADRORDERID, complaintDtl.getTradeOrderId(), complaintDtl.getProductionId(), complaintDtl.getVersionNo()));
					}
				}
				
				int count = 0;
				//查看是否有重复产品数据
				for (ComplaintDtl temp : complaint.getComplaintDtlList()) {
					
					if(StringHandler.getStr(complaintDtl.getTradeOrderId()).equals(StringHandler.getStr(temp.getTradeOrderId()))
							&& StringHandler.getStr(complaintDtl.getProductionId()).equals(StringHandler.getStr(temp.getProductionId()))
							&& StringHandler.getStr(complaintDtl.getVersionNo()).equals(StringHandler.getStr(temp.getVersionNo()))
							&& StringHandler.getStr(complaintDtl.getHandleType()).equals(StringHandler.getStr(temp.getHandleType()))){
						
						count++;
						if(count == 2){
							
							throw new ValiationException(String.format(Message.ADD_MULTI
									, StringHandler.getStr(temp.getProductionId())
									, StringHandler.getStr(temp.getVersionNo())
									, StringHandler.getStr(temp.getTradeOrderId())
									, StringHandler.getStr(temp.getHandleType()).equals("0") ? "退款" : "补货"));
						}
					}
				}
			}
			
			//更新
			DbCommonUtil.setUpdateCommon(complaint);
			complaintLogic.update(complaint);
			
			//删除当前询单明细，重新登录
			complaintLogic.deleteDtl(complaint);
			for (ComplaintDtl complaintDtl : complaint.getComplaintDtlList()) {
				
				complaintDtl.setComplaintId(complaint.getComplaintId());
				DbCommonUtil.setCreateCommon(complaintDtl);
				complaintLogic.insertDtl(complaintDtl);
			}
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String complaintIdSelected) throws Exception{
		
		Complaint complaint = new Complaint();
		complaint.setComplaintId(complaintIdSelected);
		
		int count = 0;
		try {
			
			List<ComplaintDtl> complaintDtlList = complaintLogic.findDtlByKey(complaintIdSelected);
			for (ComplaintDtl complaintDtl : complaintDtlList) {
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getQuantitySent())){
					
					throw new ValiationException(Message.COMPLAINT_DELETE_ERROR);
				}
			}
			
			count = complaintLogic.delete(complaint);
			complaintLogic.deleteDtl(complaint);
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
	public void saveImgFile(Complaint complaint) throws Exception{
		
		String Path= ConstantParam.COMPLAINT_PIC_ROOT_TEMP_REALPATH + "/" + complaint.getComplaintId();
		File file = new File(Path);
		if(!file.exists()){
			file.mkdirs();
		}else{
			File[] files = file.listFiles();
			for (File temp : files) {
				temp.delete();
			}
		}
		
		String picture = complaint.getPicture();
		if(!IsEmptyUtil.isEmpty(picture)){
			
			String[] picFile = picture.split(";");
			for (int i = 0; i < picFile.length; i++) {	
				
				File filefrom = new File(ConstantParam.COMPLAINT_PIC_ROOT_TEMP_REALPATH + "/" + picFile[i]);
				Filehandler.copyFile(filefrom, new File(Path + "/" + picFile[i]));
			}
		}
		
		picture = complaint.getPictureExisted();
		if(!IsEmptyUtil.isEmpty(picture)){
			
			String[] picFile = picture.split(";");
			for (int i = 0; i < picFile.length; i++) {	
				
				File filefrom = new File(ConstantParam.COMPLAINT_PIC_ROOT_TEMP_REALPATH + "/" + picFile[i]);
				Filehandler.copyFile(filefrom, new File(Path + "/" + picFile[i]));
			}
		}
	}
	
}
