package com.entity;

import com.util.StringHandler;

public class ComplaintDtl extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 4973934026306266321L;
	
	//DB项目
	private String complaintId;
	private String tradeOrderId;
	private String po;
	private String productionId;
	private String versionNo;
	private String quantity;
	private String boxAmount;
	private String handleType;
	private String comment;
	
	//画面项目
	private String descriptionC;
	private String descriptionE;
	private String vendorId;
	private String vendorName;
	
	private String volume;
	private String grossWeight;
	private String netWeight;
	private String inside;
	private String outside;
	private String tUnitPrice;
	private String iUnitPrice;
	
	private String volumeTtl;
	private String grossWeightTtl;
	private String netWeightTtl;
	private String tAmount;
	private String iAmount;
	
	private String quantitySent;
	
	public String getQuantitySent() {
		return StringHandler.format(quantitySent);
	}
	public void setQuantitySent(String quantitySent) {
		this.quantitySent = quantitySent;
	}
	public String getTradeOrderId() {
		return StringHandler.format(tradeOrderId);
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getHandleType() {
		return StringHandler.format(handleType);
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
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
	public String gettUnitPrice() {
		return StringHandler.format(tUnitPrice);
	}
	public void settUnitPrice(String tUnitPrice) {
		this.tUnitPrice = tUnitPrice;
	}
	public String getiUnitPrice() {
		return StringHandler.format(iUnitPrice);
	}
	public void setiUnitPrice(String iUnitPrice) {
		this.iUnitPrice = iUnitPrice;
	}
	public String getVolumeTtl() {
		return StringHandler.format(volumeTtl);
	}
	public void setVolumeTtl(String volumeTtl) {
		this.volumeTtl = volumeTtl;
	}
	public String getGrossWeightTtl() {
		return StringHandler.format(grossWeightTtl);
	}
	public void setGrossWeightTtl(String grossWeightTtl) {
		this.grossWeightTtl = grossWeightTtl;
	}
	public String getNetWeightTtl() {
		return StringHandler.format(netWeightTtl);
	}
	public void setNetWeightTtl(String netWeightTtl) {
		this.netWeightTtl = netWeightTtl;
	}
	public String gettAmount() {
		return StringHandler.format(tAmount);
	}
	public void settAmount(String tAmount) {
		this.tAmount = tAmount;
	}
	public String getiAmount() {
		return StringHandler.format(iAmount);
	}
	public void setiAmount(String iAmount) {
		this.iAmount = iAmount;
	}
	public String getPo() {
		return StringHandler.format(po);
	}
	public void setPo(String po) {
		this.po = po;
	}
	public String getQuantity() {
		return StringHandler.format(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBoxAmount() {
		return StringHandler.format(boxAmount);
	}
	public void setBoxAmount(String boxAmount) {
		this.boxAmount = boxAmount;
	}
	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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
	public String getComplaintId() {
		return StringHandler.format(complaintId);
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}
	public String getProductionId() {
		return StringHandler.format(productionId);
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getComment() {
		return StringHandler.format(comment);
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	
}
