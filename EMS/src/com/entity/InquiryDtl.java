package com.entity;

import com.util.StringHandler;

public class InquiryDtl extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5325833377997148661L;
	
	private String inquiryId;
	private String productionId;
	private String versionNo;
	private String tradeOrderId;
	private String descriptionC;
	private String descriptionE;
	private String taxReturnRate;
	private String buyoutPrice;
	private String RMB;
	private String rate;
	private String preliminaryPrice;
	private String adjustPrice;
	private String finalPrice;
	private String factoryPriceFluctuation;
	private String rateFluctuation;
	private String finalDollarPriceFluctuation;
	private String costProfitRatio;
	private String comment;
	
	private String previousInquiryId;
	private String previousTradeOrderId;
	private String previousRMB;
	private String previousUSD;
	private String previousRate;
	
	private String customerId;
	private String customerName;
	
	private boolean isUsed = false;
	
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
	public boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public String getTradeOrderId() {
		return StringHandler.format(tradeOrderId);
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getPreviousTradeOrderId() {
		return StringHandler.format(previousTradeOrderId);
	}
	public void setPreviousTradeOrderId(String previousTradeOrderId) {
		this.previousTradeOrderId = previousTradeOrderId;
	}
	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getInquiryId() {
		return StringHandler.format(inquiryId);
	}
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	public String getProductionId() {
		return StringHandler.format(productionId);
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
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
	public String getTaxReturnRate() {
		return StringHandler.format(taxReturnRate);
	}
	public void setTaxReturnRate(String taxReturnRate) {
		this.taxReturnRate = taxReturnRate;
	}
	public String getBuyoutPrice() {
		return StringHandler.format(buyoutPrice);
	}
	public void setBuyoutPrice(String buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}
	public String getRMB() {
		return StringHandler.format(RMB);
	}
	public void setRMB(String rMB) {
		RMB = rMB;
	}
	public String getRate() {
		return StringHandler.format(rate);
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPreliminaryPrice() {
		return StringHandler.format(preliminaryPrice);
	}
	public void setPreliminaryPrice(String preliminaryPrice) {
		this.preliminaryPrice = preliminaryPrice;
	}
	public String getAdjustPrice() {
		return StringHandler.format(adjustPrice);
	}
	public void setAdjustPrice(String adjustPrice) {
		this.adjustPrice = adjustPrice;
	}
	public String getFinalPrice() {
		return StringHandler.format(finalPrice);
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getFactoryPriceFluctuation() {
		return StringHandler.format(factoryPriceFluctuation);
	}
	public void setFactoryPriceFluctuation(String factoryPriceFluctuation) {
		this.factoryPriceFluctuation = factoryPriceFluctuation;
	}
	public String getRateFluctuation() {
		return StringHandler.format(rateFluctuation);
	}
	public void setRateFluctuation(String rateFluctuation) {
		this.rateFluctuation = rateFluctuation;
	}
	public String getFinalDollarPriceFluctuation() {
		return StringHandler.format(finalDollarPriceFluctuation);
	}
	public void setFinalDollarPriceFluctuation(String finalDollarPriceFluctuation) {
		this.finalDollarPriceFluctuation = finalDollarPriceFluctuation;
	}
	public String getCostProfitRatio() {
		return StringHandler.format(costProfitRatio);
	}
	public void setCostProfitRatio(String costProfitRatio) {
		this.costProfitRatio = costProfitRatio;
	}
	public String getComment() {
		return StringHandler.format(comment);
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPreviousInquiryId() {
		return StringHandler.format(previousInquiryId);
	}
	public void setPreviousInquiryId(String previousInquiryId) {
		this.previousInquiryId = previousInquiryId;
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
}
