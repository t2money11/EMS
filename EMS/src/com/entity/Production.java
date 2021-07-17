package com.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.context.ConstantParam;
import com.util.StringHandler;

public class Production extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -8421133379184655278L;
	
	private String productionId;
	private String versionNo;
	private String productionIdVendor;
	private String descriptionC;
	private String descriptionE;
	private String material;
	private String surface;
	private String size;
	private String hscode;
	private String productionCname4export;
	private String productionEname4export;
	private String taxReturnRate;
	private String brand;
	private String purpose;
	private String kind;
	private String kindName;
	private String vendorId;
	private String vendorName;
	private String customerId;
	private String customerName;
	private String packMethod;
	private String volume;
	private String grossWeight;
	private String netWeight;
	private String inside;
	private String outside;
	private String lastDrawUpdateDate;
	private String lastComplaintDate;
	private String comment;
	private String status;
	
	private String productionId4S;
	private String productionIdVendor4S;
	private String descriptionC4S;
	private String descriptionE4S;
	private String productionCname4export4S;
	private String productionEname4export4S;
	private String vendorId4S;
	private String vendorName4S;
	private String customerId4S;
	private String customerName4S;
	private String status4S;
	private String searchOption;
	
	private String picture;
	private String pictureExisted;
	
	private String previousInquiryId;
	private String previousInquiryDate;
	private String previousTradeOrderId;
	private String previousTradeOrderConfirmDate;
	private String previousRMB;
	private String previousUSD;
	private String previousRate;
	
	private String folderName1;
	private String folderName2;
	
	private boolean isUsed = false;
	private boolean isUpdate = false;
	
	Map<String, String> kindInput = null;
	Map<String, String> statusInput = null;
	Map<String, String> statusInput4S = null;
	
	private List<Production> resultProductionList = null;
	
	public Production(){
		
		if(ConstantParam.codeMap != null){
			
			kindInput = ConstantParam.codeMap.get(ConstantParam.P_TYPE);
			statusInput = ConstantParam.codeMap.get(ConstantParam.C_STATUS);
			statusInput.remove("");
			
			statusInput4S = new HashMap<String, String>();
			statusInput4S.put(null, null);
			Iterator<Map.Entry<String,String>> iter = statusInput.entrySet().iterator();
			while (iter.hasNext()) {
				
				Map.Entry<String,String> entry = iter.next();
				statusInput4S.put(entry.getKey(), entry.getValue());
			}
		}
		resultProductionList = new ArrayList<Production>();
	}
	
	public boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Map<String, String> getStatusInput4S() {
		return statusInput4S;
	}

	public void setStatusInput4S(Map<String, String> statusInput4S) {
		this.statusInput4S = statusInput4S;
	}

	public Map<String, String> getStatusInput() {
		return statusInput;
	}

	public void setStatusInput(Map<String, String> statusInput) {
		this.statusInput = statusInput;
	}
	
	public String getFolderName1() {
		return StringHandler.format(folderName1);
	}

	public void setFolderName1(String folderName1) {
		this.folderName1 = folderName1;
	}

	public String getFolderName2() {
		return StringHandler.format(folderName2);
	}

	public void setFolderName2(String folderName2) {
		this.folderName2 = folderName2;
	}

	public String getSearchOption() {
		return StringHandler.format(searchOption);
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getStatus() {
		return StringHandler.format(status);
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus4S() {
		return StringHandler.format(status4S);
	}

	public void setStatus4S(String status4s) {
		status4S = status4s;
	}

	public String getLastDrawUpdateDate() {
		return StringHandler.format(lastDrawUpdateDate);
	}

	public void setLastDrawUpdateDate(String lastDrawUpdateDate) {
		this.lastDrawUpdateDate = lastDrawUpdateDate;
	}

	public String getLastComplaintDate() {
		return StringHandler.format(lastComplaintDate);
	}

	public void setLastComplaintDate(String lastComplaintDate) {
		this.lastComplaintDate = lastComplaintDate;
	}

	public boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getPreviousInquiryId() {
		return StringHandler.format(previousInquiryId);
	}

	public void setPreviousInquiryId(String previousInquiryId) {
		this.previousInquiryId = previousInquiryId;
	}

	public String getPreviousInquiryDate() {
		return StringHandler.format(previousInquiryDate);
	}

	public void setPreviousInquiryDate(String previousInquiryDate) {
		this.previousInquiryDate = previousInquiryDate;
	}

	public String getPreviousTradeOrderId() {
		return StringHandler.format(previousTradeOrderId);
	}

	public void setPreviousTradeOrderId(String previousTradeOrderId) {
		this.previousTradeOrderId = previousTradeOrderId;
	}

	public String getPreviousTradeOrderConfirmDate() {
		return StringHandler.format(previousTradeOrderConfirmDate);
	}

	public void setPreviousTradeOrderConfirmDate(
			String previousTradeOrderConfirmDate) {
		this.previousTradeOrderConfirmDate = previousTradeOrderConfirmDate;
	}

	public String getPreviousRMB() {
		return StringHandler.format(previousRMB);
	}

	public void setPreviousRMB(String previousRMB) {
		this.previousRMB = previousRMB;
	}

	public String getPreviousUSD() {
		return StringHandler.format(previousUSD);
	}

	public void setPreviousUSD(String previousUSD) {
		this.previousUSD = previousUSD;
	}

	public String getPreviousRate() {
		return StringHandler.format(previousRate);
	}

	public void setPreviousRate(String previousRate) {
		this.previousRate = previousRate;
	}

	public String getProductionId() {
		return StringHandler.format(productionId);
	}

	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}

	public String getProductionIdVendor() {
		return StringHandler.format(productionIdVendor);
	}

	public void setProductionIdVendor(String productionIdVendor) {
		this.productionIdVendor = productionIdVendor;
	}

	public String getDescriptionC() {
		return StringHandler.format(descriptionC);
	}

	public void setDescriptionC(String descriptionC) {
		this.descriptionC = descriptionC;
	}

	public String getDescriptionE() {
		return StringHandler.format(descriptionE);
	}

	public void setDescriptionE(String descriptionE) {
		this.descriptionE = descriptionE;
	}

	public String getMaterial() {
		return StringHandler.format(material);
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSurface() {
		return StringHandler.format(surface);
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public String getSize() {
		return StringHandler.format(size);
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getHscode() {
		return StringHandler.format(hscode);
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getProductionCname4export() {
		return StringHandler.format(productionCname4export);
	}

	public void setProductionCname4export(String productionCname4export) {
		this.productionCname4export = productionCname4export;
	}

	public String getProductionEname4export() {
		return StringHandler.format(productionEname4export);
	}

	public void setProductionEname4export(String productionEname4export) {
		this.productionEname4export = productionEname4export;
	}

	public String getTaxReturnRate() {
		return StringHandler.format(taxReturnRate);
	}

	public void setTaxReturnRate(String taxReturnRate) {
		this.taxReturnRate = taxReturnRate;
	}

	public String getVendorId() {
		return StringHandler.format(vendorId);
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return StringHandler.format(vendorName);
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCustomerId() {
		return StringHandler.format(customerId);
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return StringHandler.format(customerName);
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPackMethod() {
		return StringHandler.format(packMethod);
	}

	public void setPackMethod(String packMethod) {
		this.packMethod = packMethod;
	}

	public String getVolume() {
		return StringHandler.format(volume);
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getGrossWeight() {
		return StringHandler.format(grossWeight);
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getNetWeight() {
		return StringHandler.format(netWeight);
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getInside() {
		return StringHandler.format(inside);
	}

	public void setInside(String inside) {
		this.inside = inside;
	}

	public String getOutside() {
		return StringHandler.format(outside);
	}

	public void setOutside(String outside) {
		this.outside = outside;
	}

	public String getComment() {
		return StringHandler.format(comment);
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProductionId4S() {
		return StringHandler.format(productionId4S);
	}

	public void setProductionId4S(String productionId4S) {
		this.productionId4S = productionId4S;
	}

	public String getProductionIdVendor4S() {
		return StringHandler.format(productionIdVendor4S);
	}

	public void setProductionIdVendor4S(String productionIdVendor4S) {
		this.productionIdVendor4S = productionIdVendor4S;
	}

	public String getDescriptionC4S() {
		return StringHandler.format(descriptionC4S);
	}

	public void setDescriptionC4S(String descriptionC4S) {
		this.descriptionC4S = descriptionC4S;
	}

	public String getDescriptionE4S() {
		return StringHandler.format(descriptionE4S);
	}

	public void setDescriptionE4S(String descriptionE4S) {
		this.descriptionE4S = descriptionE4S;
	}

	public String getProductionCname4export4S() {
		return StringHandler.format(productionCname4export4S);
	}

	public void setProductionCname4export4S(String productionCname4export4S) {
		this.productionCname4export4S = productionCname4export4S;
	}

	public String getProductionEname4export4S() {
		return StringHandler.format(productionEname4export4S);
	}

	public void setProductionEname4export4S(String productionEname4export4S) {
		this.productionEname4export4S = productionEname4export4S;
	}

	public String getBrand() {
		return StringHandler.format(brand);
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPurpose() {
		return StringHandler.format(purpose);
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getKind() {
		return StringHandler.format(kind);
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKindName() {
		return StringHandler.format(kindName);
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getVendorId4S() {
		return StringHandler.format(vendorId4S);
	}

	public void setVendorId4S(String vendorId4S) {
		this.vendorId4S = vendorId4S;
	}

	public String getCustomerId4S() {
		return StringHandler.format(customerId4S);
	}

	public void setCustomerId4S(String customerId4S) {
		this.customerId4S = customerId4S;
	}
	
	public String getVendorName4S() {
		return StringHandler.format(vendorName4S);
	}

	public void setVendorName4S(String vendorName4S) {
		this.vendorName4S = vendorName4S;
	}

	public String getCustomerName4S() {
		return StringHandler.format(customerName4S);
	}

	public void setCustomerName4S(String customerName4S) {
		this.customerName4S = customerName4S;
	}

	public String getPicture() {
		return StringHandler.format(picture);
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPictureExisted() {
		return StringHandler.format(pictureExisted);
	}

	public void setPictureExisted(String pictureExisted) {
		this.pictureExisted = pictureExisted;
	}

	public Map<String, String> getKindInput() {
		return kindInput;
	}

	public void setKindInput(Map<String, String> kindInput) {
		this.kindInput = kindInput;
	}

	public List<Production> getResultProductionList() {
		return resultProductionList;
	}

	public void setResultProductionList(List<Production> resultProductionList) {
		this.resultProductionList = resultProductionList;
	}
}
