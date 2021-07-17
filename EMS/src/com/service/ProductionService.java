package com.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.ConstantParam;
import com.context.Message;
import com.logic.ProductionLogic;
import com.entity.Production;
import com.exception.ValiationException;
import com.util.DbCommonUtil;
import com.util.Filehandler;
import com.util.IsEmptyUtil;

@Service
public class ProductionService{
	
	private ProductionLogic productionLogic;
	public void setProductionLogic(ProductionLogic productionLogic) {
		this.productionLogic = productionLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public Production findByKey(String productionId, String versionNo) throws Exception{
		
		
		Production production = productionLogic.findByKey(productionId, versionNo);
		production.setFolderName1(production.getProductionId());
		production.setFolderName2(production.getVersionNo());
		production.setIsUsed(validationAPIService.validateP(productionId, production.getVersionNo(), "1"));
		
		return production;
    }
	
	public Production productionSearch(Production production) throws Exception{
		
		int totalCount = 0;
		
		totalCount = productionLogic.countFindByProperty(production);
		if(totalCount != 0){
			
			List<Production> productionList = productionLogic.findByProperty(production);
			production.setResultProductionList(productionList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		production.getPageInfo().setTotalCount(totalCount);
		
        return production;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Production production) throws Exception{
		
		//验证当前用户是否有权限操作该客户的订单数据
		if(!validationAPIService.validateCU(production.getCustomerId())){
			
			throw new ValiationException(Message.NO_CUSTOMER_AUTH);
		}
		
		//供应商存在验证
		Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.VENDOR, production.getVendorId());
		if(updateTime == null){
			
			throw new ValiationException(Message.NOT_EXISTS_VENDOR);
		}
		
		//重复主键确认
		if(productionLogic.findByKey(production.getProductionId(), production.getVersionNo()) != null){
			throw new Exception(Message.DUPLICATED_PRODUCTION);
		}
		DbCommonUtil.setCreateCommon(production);
		try {
			productionLogic.insert(production);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		Filehandler.createFolder(production.getProductionId());
		return production.getProductionId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void update(Production production) throws Exception{

		int count = 0;
		try {
			
			//验证当前用户是否有权限操作该客户的订单数据
			if(!validationAPIService.validateCU(production.getCustomerId())){
				
				throw new ValiationException(Message.NO_CUSTOMER_AUTH);
			}
			
			//供应商存在验证
			Timestamp updateTime = validationAPIService.getUpdateTime(ValidationAPIService.VENDOR, production.getVendorId());
			if(updateTime == null){
				
				throw new ValiationException(Message.NOT_EXISTS_VENDOR);
			}

			//更新冲突验证
			updateTime = validationAPIService.getUpdateTime(ValidationAPIService.PRODUCTION, production.getProductionId(), production.getVersionNo());
			if(updateTime == null ||production.getUpdateTime().compareTo(updateTime) != 0){
				
				throw new ValiationException(Message.SYNCHRONIZED_ERROR);
			}
			
			//是否被引用，是的话新规新版本，否的话更新当前版本
			if(validationAPIService.validateP(production.getProductionId(), production.getVersionNo(), "1")
					&& production.getIsUpdate() == false){
				
				production.setVersionNo(String.valueOf(Integer.parseInt(production.getVersionNo()) + 1));
				
				//重复主键确认
				if(productionLogic.findByKey(production.getProductionId(), production.getVersionNo()) != null){
					throw new ValiationException(Message.UPDATE_VERSION_PRODUCTION);
				}
				DbCommonUtil.setCreateCommon(production);
				count = productionLogic.insert(production);
			}else{
				
				DbCommonUtil.setUpdateCommon(production);
				count = productionLogic.update(production);
			};
			
			Filehandler.createFolder(production.getProductionId());
			
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.UPDATE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.UPDATE_NOT_FOUND);
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String productionIdSelected, String versionNoSelected) throws Exception{
		
		Production production = new Production();
		production.setProductionId(productionIdSelected);
		production.setVersionNo(versionNoSelected);
		int count = 0;
		try {
			production = productionLogic.findByKey(productionIdSelected, versionNoSelected);
			if(!validationAPIService.validateCU(production.getCustomerId())){
				throw new ValiationException(Message.NO_CUSTOMER_AUTH);
			}
			if(validationAPIService.validateP(production.getProductionId(), production.getVersionNo(), "0")){
				throw new ValiationException(Message.USED_PRODUCTION);
			}
			count = productionLogic.delete(production);
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
	public void setImgFileName(Production production){
		
		String Path= ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH + "/" + production.getProductionId() + "/" + production.getVersionNo();
		File file = new File(Path);
		if(file.exists()){
			
			String picture = "";
			
			File[] tempList = file.listFiles();
			for (File temp : tempList) {
				picture += temp.getName() + ";";
			}
			if(!IsEmptyUtil.isEmpty(picture)){
				production.setPictureExisted(picture.substring(0, picture.length() - 1));
			}
		}
	}
	
	public void saveImgFile(Production production) throws Exception{
		
		String Path= ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH + "/" + production.getProductionId() + "/" + production.getVersionNo();
		File file = new File(Path);
		if(!file.exists()){
			file.mkdirs();
		}else{
			File[] files = file.listFiles();
			for (File temp : files) {
				temp.delete();
			}
		}
		
		String picture = production.getPicture();
		if(!IsEmptyUtil.isEmpty(picture)){
			
			String[] picFile = picture.split(";");
			for (int i = 0; i < picFile.length; i++) {	
				
				File filefrom = new File(ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH + "/" + picFile[i]);
				Filehandler.copyFile(filefrom, new File(Path + "/" + picFile[i]));
			}
		}
		
		picture = production.getPictureExisted();
		if(!IsEmptyUtil.isEmpty(picture)){
			
			String[] picFile = picture.split(";");
			for (int i = 0; i < picFile.length; i++) {	
				
				File filefrom = new File(ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH + "/" + picFile[i]);
				Filehandler.copyFile(filefrom, new File(Path + "/" + picFile[i]));
			}
		}
	}
	
}
