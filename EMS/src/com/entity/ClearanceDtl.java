package com.entity;

import com.util.StringHandler;

public class ClearanceDtl extends BaseEntity implements java.io.Serializable {

	
	private static final long serialVersionUID = 2534761968403627454L;
	
	private String receiptId;
	private String hscode;
	private String productionEName4Export;
	private String productionCName4Export;
	//报关数量
	private String quantity;
	//报关单价
	private String unitPrice;
	//报关小计
	private String amount;
	//RMB小计
	private String amountRMB;
	private String boxAmount;
	private String grossWeight;
	private String netWeight;
	private String volume;
	private String shortLocation;
	
	public String getExportKey() {
		return StringHandler.getStr(getProductionEName4Export()) + "_" + StringHandler.getStr(getShortLocation());
	}
	public String getShortLocation() {
		return shortLocation;
	}
	public void setShortLocation(String shortLocation) {
		this.shortLocation = shortLocation;
	}
	public String getHscode() {
		return StringHandler.format(hscode);
	}
	public void setHscode(String hscode) {
		this.hscode = hscode;
	}
	public String getProductionCName4Export() {
		return StringHandler.format(productionCName4Export);
	}
	public void setProductionCName4Export(String productionCName4Export) {
		this.productionCName4Export = productionCName4Export;
	}
	public String getReceiptId() {
		return StringHandler.format(receiptId);
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getProductionEName4Export() {
		return StringHandler.format(productionEName4Export);
	}
	public void setProductionEName4Export(String productionEName4Export) {
		this.productionEName4Export = productionEName4Export;
	}
	public String getQuantity() {
		return StringHandler.format(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitPrice() {
		return StringHandler.format(unitPrice);
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getAmount() {
		return StringHandler.format(amount);
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountRMB() {
		return StringHandler.format(amountRMB);
	}
	public void setAmountRMB(String amountRMB) {
		this.amountRMB = amountRMB;
	}
	public String getBoxAmount() {
		return StringHandler.format(boxAmount);
	}
	public void setBoxAmount(String boxAmount) {
		this.boxAmount = boxAmount;
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
	public String getVolume() {
		return StringHandler.format(volume);
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
}
