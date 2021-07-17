package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.Vendor;
import com.exception.ValiationException;
import com.logic.VendorLogic;
import com.util.DbCommonUtil;

@Service
public class VendorService{
	
	private VendorLogic vendorLogic = null;
	public void setVendorLogic(VendorLogic vendorLogic) {
		this.vendorLogic = vendorLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public Vendor findByKey(String vendorIdSelected){
		
		Vendor vendor = vendorLogic.findByKey(vendorIdSelected);
		return vendor;
    }
	
	public Vendor vendorSearch(Vendor vendor) throws Exception{
		
		int totalCount = 0;
		
		totalCount = vendorLogic.countFindByProperty(vendor);
		if(totalCount != 0){
			
			List<Vendor> vendorList = vendorLogic.findByProperty(vendor);
			vendor.setResultVendorList(vendorList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		vendor.getPageInfo().setTotalCount(totalCount);
		
        return vendor;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Vendor vendor) throws Exception{
		
		//重复主键确认
		if(vendorLogic.findByKey(vendor.getVendorId()) != null){
			throw new Exception(Message.DUPLICATED_VENDOR);
		}
		DbCommonUtil.setCreateCommon(vendor);
		try {
			vendorLogic.insert(vendor);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return vendor.getVendorId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Vendor vendor) throws Exception{
		
		//更新冲突验证
		Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.VENDOR, vendor.getVendorId());
		if(updateTime == null ||vendor.getUpdateTime().compareTo(updateTime) != 0){
			
			throw new ValiationException(Message.SYNCHRONIZED_ERROR);
		}
		
		DbCommonUtil.setUpdateCommon(vendor);
		
		int count = 0;
		try {
			count = vendorLogic.update(vendor);
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String vendorIdSelected) throws Exception{
		
		Vendor vendor = new Vendor();
		vendor.setVendorId(vendorIdSelected);
		
		int count = 0;
		try {
			//删除供应商之前，验证供应商是否已经被引用
			if(validationAPIService.validateV(vendorIdSelected)){
				throw new ValiationException(Message.USED_VENDOR);
			}
			count = vendorLogic.delete(vendor);
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
