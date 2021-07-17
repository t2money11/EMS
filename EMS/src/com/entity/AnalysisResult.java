package com.entity;

public class AnalysisResult extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -338466109710802761L;
	
	/**
	 * tradeOrderAnalysis
	 */
	private String customerName;
	private String tradeOrderCreateDate;
	private String tradeOrderId;
	private String contractNo;
	private String po;
	private String statusName;
	private String vendorName;
	private String interTradeCreateDate;
	private String productionId;
	private String versionNo;
	private String descriptionE;
	private String tSendMode;
	private String shipment;
	private String tQuantity;
	private String tSendDate1;
	private String tSendQuantity1;
	private String tSendDate2;
	private String tSendQuantity2;
	private String tSendDate3;
	private String tSendQuantity3;
	private String tSendDate4;
	private String tSendQuantity4;
	private String iSendMode;
	private String recieveDate;
	private String iQuantity;
	private String iSendDate1;
	private String iSendQuantity1;
	private String iSendDate2;
	private String iSendQuantity2;
	private String iSendDate3;
	private String iSendQuantity3;
	private String iSendDate4;
	private String iSendQuantity4;
	private String quantityNotSent;
	private String receiptNo;
	private String transportation;
	private String ETD;
	private String quantity;
	
	private String complaintId;
	private String dealDeadLine;
	private String receiptId;
	
	
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}
	public String getDealDeadLine() {
		return dealDeadLine;
	}
	public void setDealDeadLine(String dealDeadLine) {
		this.dealDeadLine = dealDeadLine;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String gettSendMode() {
		return tSendMode;
	}
	public void settSendMode(String tSendMode) {
		this.tSendMode = tSendMode;
	}
	public String gettSendDate1() {
		return tSendDate1;
	}
	public void settSendDate1(String tSendDate1) {
		this.tSendDate1 = tSendDate1;
	}
	public String gettSendQuantity1() {
		return tSendQuantity1;
	}
	public void settSendQuantity1(String tSendQuantity1) {
		this.tSendQuantity1 = tSendQuantity1;
	}
	public String gettSendDate2() {
		return tSendDate2;
	}
	public void settSendDate2(String tSendDate2) {
		this.tSendDate2 = tSendDate2;
	}
	public String gettSendQuantity2() {
		return tSendQuantity2;
	}
	public void settSendQuantity2(String tSendQuantity2) {
		this.tSendQuantity2 = tSendQuantity2;
	}
	public String gettSendDate3() {
		return tSendDate3;
	}
	public void settSendDate3(String tSendDate3) {
		this.tSendDate3 = tSendDate3;
	}
	public String gettSendQuantity3() {
		return tSendQuantity3;
	}
	public void settSendQuantity3(String tSendQuantity3) {
		this.tSendQuantity3 = tSendQuantity3;
	}
	public String gettSendDate4() {
		return tSendDate4;
	}
	public void settSendDate4(String tSendDate4) {
		this.tSendDate4 = tSendDate4;
	}
	public String gettSendQuantity4() {
		return tSendQuantity4;
	}
	public void settSendQuantity4(String tSendQuantity4) {
		this.tSendQuantity4 = tSendQuantity4;
	}
	public String getiSendMode() {
		return iSendMode;
	}
	public void setiSendMode(String iSendMode) {
		this.iSendMode = iSendMode;
	}
	public String getiSendDate1() {
		return iSendDate1;
	}
	public void setiSendDate1(String iSendDate1) {
		this.iSendDate1 = iSendDate1;
	}
	public String getiSendQuantity1() {
		return iSendQuantity1;
	}
	public void setiSendQuantity1(String iSendQuantity1) {
		this.iSendQuantity1 = iSendQuantity1;
	}
	public String getiSendDate2() {
		return iSendDate2;
	}
	public void setiSendDate2(String iSendDate2) {
		this.iSendDate2 = iSendDate2;
	}
	public String getiSendQuantity2() {
		return iSendQuantity2;
	}
	public void setiSendQuantity2(String iSendQuantity2) {
		this.iSendQuantity2 = iSendQuantity2;
	}
	public String getiSendDate3() {
		return iSendDate3;
	}
	public void setiSendDate3(String iSendDate3) {
		this.iSendDate3 = iSendDate3;
	}
	public String getiSendQuantity3() {
		return iSendQuantity3;
	}
	public void setiSendQuantity3(String iSendQuantity3) {
		this.iSendQuantity3 = iSendQuantity3;
	}
	public String getiSendDate4() {
		return iSendDate4;
	}
	public void setiSendDate4(String iSendDate4) {
		this.iSendDate4 = iSendDate4;
	}
	public String getiSendQuantity4() {
		return iSendQuantity4;
	}
	public void setiSendQuantity4(String iSendQuantity4) {
		this.iSendQuantity4 = iSendQuantity4;
	}
	public String getTradeOrderId() {
		return tradeOrderId;
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTradeOrderCreateDate() {
		return tradeOrderCreateDate;
	}
	public void setTradeOrderCreateDate(String tradeOrderCreateDate) {
		this.tradeOrderCreateDate = tradeOrderCreateDate;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPo() {
		return po;
	}
	public void setPo(String po) {
		this.po = po;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getInterTradeCreateDate() {
		return interTradeCreateDate;
	}
	public void setInterTradeCreateDate(String interTradeCreateDate) {
		this.interTradeCreateDate = interTradeCreateDate;
	}
	public String getRecieveDate() {
		return recieveDate;
	}
	public void setRecieveDate(String recieveDate) {
		this.recieveDate = recieveDate;
	}
	public String getShipment() {
		return shipment;
	}
	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	public String getProductionId() {
		return productionId;
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getDescriptionE() {
		return descriptionE;
	}
	public void setDescriptionE(String descriptionE) {
		this.descriptionE = descriptionE;
	}
	public String gettQuantity() {
		return tQuantity;
	}
	public void settQuantity(String tQuantity) {
		this.tQuantity = tQuantity;
	}
	public String getiQuantity() {
		return iQuantity;
	}
	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
	}
	public String getQuantityNotSent() {
		return quantityNotSent;
	}
	public void setQuantityNotSent(String quantityNotSent) {
		this.quantityNotSent = quantityNotSent;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getTransportation() {
		return transportation;
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public String getETD() {
		return ETD;
	}
	public void setETD(String eTD) {
		ETD = eTD;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
