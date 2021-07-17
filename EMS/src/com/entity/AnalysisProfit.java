package com.entity;

public class AnalysisProfit extends BaseEntity implements java.io.Serializable, Cloneable {
	
	
	private static final long serialVersionUID = -4556798046529421558L;

	private String userId;
	private String customerName;
	private String tradeOrderId;
	private String contractNo;
	private String vendorName;
	private String productionId;
	private String versionNo;
	private String taxReturnRate;
	private String tSendMode;
	private String shipment;
	private String tQuantity;
	private String tUnitPrice;
	private String tFee1;
	private String tFee2;
	private String tFee3;
	private String tFee4;
	private String tSendDate1;
	private String tSendDate2;
	private String tSendDate3;
	private String tSendDate4;
	private String iQuantity;
	private String iUnitPrice;
	private String iFee1;
	private String iFee2;
	private String iFee3;
	private String iFee4;
	private String interTradeOrderId;
	private String advancePayment;
	private String advancePaymentDiscountRate;
	private String receiptId;
	private String feeNum;
	private String planPostingDate;
	private String balanceDate;
	private String postingDate;
	private String quantitySent;
	private String quantityNotSent;
	
	public Object clone() {
		
		AnalysisProfit o = null;
		try {
			
			o = (AnalysisProfit) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return o;
	}
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getQuantityNotSent() {
		return quantityNotSent;
	}
	public void setQuantityNotSent(String quantityNotSent) {
		this.quantityNotSent = quantityNotSent;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String gettUnitPrice() {
		return tUnitPrice;
	}
	public void settUnitPrice(String tUnitPrice) {
		this.tUnitPrice = tUnitPrice;
	}
	public String getiUnitPrice() {
		return iUnitPrice;
	}
	public void setiUnitPrice(String iUnitPrice) {
		this.iUnitPrice = iUnitPrice;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTradeOrderId() {
		return tradeOrderId;
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getProductionId() {
		return productionId;
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getTaxReturnRate() {
		return taxReturnRate;
	}
	public void setTaxReturnRate(String taxReturnRate) {
		this.taxReturnRate = taxReturnRate;
	}
	public String gettSendMode() {
		return tSendMode;
	}
	public void settSendMode(String tSendMode) {
		this.tSendMode = tSendMode;
	}
	public String getShipment() {
		return shipment;
	}
	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	public String gettQuantity() {
		return tQuantity;
	}
	public void settQuantity(String tQuantity) {
		this.tQuantity = tQuantity;
	}
	public String gettFee1() {
		return tFee1;
	}
	public void settFee1(String tFee1) {
		this.tFee1 = tFee1;
	}
	public String gettFee2() {
		return tFee2;
	}
	public void settFee2(String tFee2) {
		this.tFee2 = tFee2;
	}
	public String gettFee3() {
		return tFee3;
	}
	public void settFee3(String tFee3) {
		this.tFee3 = tFee3;
	}
	public String gettFee4() {
		return tFee4;
	}
	public void settFee4(String tFee4) {
		this.tFee4 = tFee4;
	}
	public String gettSendDate1() {
		return tSendDate1;
	}
	public void settSendDate1(String tSendDate1) {
		this.tSendDate1 = tSendDate1;
	}
	public String gettSendDate2() {
		return tSendDate2;
	}
	public void settSendDate2(String tSendDate2) {
		this.tSendDate2 = tSendDate2;
	}
	public String gettSendDate3() {
		return tSendDate3;
	}
	public void settSendDate3(String tSendDate3) {
		this.tSendDate3 = tSendDate3;
	}
	public String gettSendDate4() {
		return tSendDate4;
	}
	public void settSendDate4(String tSendDate4) {
		this.tSendDate4 = tSendDate4;
	}
	public String getiQuantity() {
		return iQuantity;
	}
	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
	}
	public String getiFee1() {
		return iFee1;
	}
	public void setiFee1(String iFee1) {
		this.iFee1 = iFee1;
	}
	public String getiFee2() {
		return iFee2;
	}
	public void setiFee2(String iFee2) {
		this.iFee2 = iFee2;
	}
	public String getiFee3() {
		return iFee3;
	}
	public void setiFee3(String iFee3) {
		this.iFee3 = iFee3;
	}
	public String getiFee4() {
		return iFee4;
	}
	public void setiFee4(String iFee4) {
		this.iFee4 = iFee4;
	}
	public String getInterTradeOrderId() {
		return interTradeOrderId;
	}
	public void setInterTradeOrderId(String interTradeOrderId) {
		this.interTradeOrderId = interTradeOrderId;
	}
	public String getAdvancePayment() {
		return advancePayment;
	}
	public void setAdvancePayment(String advancePayment) {
		this.advancePayment = advancePayment;
	}
	public String getAdvancePaymentDiscountRate() {
		return advancePaymentDiscountRate;
	}
	public void setAdvancePaymentDiscountRate(String advancePaymentDiscountRate) {
		this.advancePaymentDiscountRate = advancePaymentDiscountRate;
	}
	public String getFeeNum() {
		return feeNum;
	}
	public void setFeeNum(String feeNum) {
		this.feeNum = feeNum;
	}
	public String getPlanPostingDate() {
		return planPostingDate;
	}
	public void setPlanPostingDate(String planPostingDate) {
		this.planPostingDate = planPostingDate;
	}
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getQuantitySent() {
		return quantitySent;
	}
	public void setQuantitySent(String quantitySent) {
		this.quantitySent = quantitySent;
	}
}
