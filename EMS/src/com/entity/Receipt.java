package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class Receipt extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 639099886901489125L;
	
	private String receiptId;
	private String receiptDate;
	private String receiptNo;
	private String mark;
	private String planPostingDate;
	private String postingDate;
	private String ETD;
	private String ETA;
	private String remint;
	private String goodTime;
	private String telexRelease;
	private String transportation;
	private String balanceDate;
	private String profit;
	private String collectionDate;
	private String collectionAmount;
	private String checkoutNumber;
	private String iAmountTtl;
	private String tAmountTtl;
	private String quantityTtl;
	private String iAmountTtl4T;
	private String tAmountTtl4T;
	private String quantityTtl4T;
	private String iAmountTtl4C;
	private String tAmountTtl4C;
	private String quantityTtl4C;
	private String quantityCTtl;
	private String amountTtl4Export;
	private String boxAmountTtl;
	private String grossWeightTtl;
	private String netWeightTtl;
	private String volumeTtl;
	private String postingTo;
	private String postingAmount;
	private String comment;
	private String receiptMode;
	private String clearanceMode;
	private String relateReceiptNo;
	private String isRelated;
	
	private String customerId;
	private String customerName;
	private String customerFullName;
	private String country;
	private String location;
	private String freightTerms;
	private String paymentTerms;
	private String portOfLoading;
	private String portOfDestination;
	private String consignee;
	private String contact;
	private String tel;
	private String fax;
	
	private String receiptId4S;
	private String tradeOrderId4S;
	private String po4S;
	private String contractNo4S;
	private String productionId4S;
	private String customerId4S;
	private String customerName4S;
	
	private List<Receipt> resultReceiptList = new ArrayList<Receipt>();
	private List<ReceiptDtl> receiptDtlList = new ArrayList<ReceiptDtl>();
	private List<ReceiptDtl> receiptDtlRemoveList = new ArrayList<ReceiptDtl>();
	private List<ReceiptDtl4C> receiptDtl4CList = new ArrayList<ReceiptDtl4C>();
	private List<ClearanceDtl> clearanceDtlList = new ArrayList<ClearanceDtl>();
	
	public String getProductionId4S() {
		return StringHandler.format(productionId4S);
	}
	public void setProductionId4S(String productionId4S) {
		this.productionId4S = productionId4S;
	}
	public String getIsRelated() {
		return StringHandler.format(isRelated);
	}
	public void setIsRelated(String isRelated) {
		this.isRelated = isRelated;
	}
	public String getRelateReceiptNo() {
		return StringHandler.format(relateReceiptNo);
	}
	public void setRelateReceiptNo(String relateReceiptNo) {
		this.relateReceiptNo = relateReceiptNo;
	}
	public String getPostingTo() {
		return StringHandler.format(postingTo);
	}
	public void setPostingTo(String postingTo) {
		this.postingTo = postingTo;
	}
	public String getPostingAmount() {
		return StringHandler.format(postingAmount);
	}
	public void setPostingAmount(String postingAmount) {
		this.postingAmount = postingAmount;
	}
	public String getiAmountTtl4T() {
		return StringHandler.format(iAmountTtl4T);
	}
	public void setiAmountTtl4T(String iAmountTtl4T) {
		this.iAmountTtl4T = iAmountTtl4T;
	}
	public String gettAmountTtl4T() {
		return StringHandler.format(tAmountTtl4T);
	}
	public void settAmountTtl4T(String tAmountTtl4T) {
		this.tAmountTtl4T = tAmountTtl4T;
	}
	public String getQuantityTtl4T() {
		return StringHandler.format(quantityTtl4T);
	}
	public void setQuantityTtl4T(String quantityTtl4T) {
		this.quantityTtl4T = quantityTtl4T;
	}
	public String getiAmountTtl4C() {
		return StringHandler.format(iAmountTtl4C);
	}
	public void setiAmountTtl4C(String iAmountTtl4C) {
		this.iAmountTtl4C = iAmountTtl4C;
	}
	public String gettAmountTtl4C() {
		return StringHandler.format(tAmountTtl4C);
	}
	public void settAmountTtl4C(String tAmountTtl4C) {
		this.tAmountTtl4C = tAmountTtl4C;
	}
	public String getQuantityTtl4C() {
		return StringHandler.format(quantityTtl4C);
	}
	public void setQuantityTtl4C(String quantityTtl4C) {
		this.quantityTtl4C = quantityTtl4C;
	}
	public List<ReceiptDtl4C> getReceiptDtl4CList() {
		return receiptDtl4CList;
	}
	public void setReceiptDtl4CList(List<ReceiptDtl4C> receiptDtl4CList) {
		this.receiptDtl4CList = receiptDtl4CList;
	}
	public String getClearanceMode() {
		return StringHandler.format(clearanceMode);
	}
	public void setClearanceMode(String clearanceMode) {
		this.clearanceMode = clearanceMode;
	}
	public String getConsignee() {
		return StringHandler.format(consignee);
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getContact() {
		return StringHandler.format(contact);
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getReceiptMode() {
		return StringHandler.format(receiptMode);
	}
	public void setReceiptMode(String receiptMode) {
		this.receiptMode = receiptMode;
	}
	public String getReceiptNo() {
		return StringHandler.format(receiptNo);
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getPlanPostingDate() {
		return StringHandler.format(planPostingDate);
	}
	public void setPlanPostingDate(String planPostingDate) {
		this.planPostingDate = planPostingDate;
	}
	public String getPostingDate() {
		return StringHandler.format(postingDate);
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getETD() {
		return StringHandler.format(ETD);
	}
	public void setETD(String eTD) {
		ETD = eTD;
	}
	public String getETA() {
		return StringHandler.format(ETA);
	}
	public void setETA(String eTA) {
		ETA = eTA;
	}
	public String getRemint() {
		return StringHandler.format(remint);
	}
	public void setRemint(String remint) {
		this.remint = remint;
	}
	public String getGoodTime() {
		return StringHandler.format(goodTime);
	}
	public void setGoodTime(String goodTime) {
		this.goodTime = goodTime;
	}
	public String getTelexRelease() {
		return StringHandler.format(telexRelease);
	}
	public void setTelexRelease(String telexRelease) {
		this.telexRelease = telexRelease;
	}
	public String getTransportation() {
		return StringHandler.format(transportation);
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public String getBalanceDate() {
		return StringHandler.format(balanceDate);
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getCustomerFullName() {
		return StringHandler.format(customerFullName);
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	public String getFreightTerms() {
		return StringHandler.format(freightTerms);
	}
	public void setFreightTerms(String freightTerms) {
		this.freightTerms = freightTerms;
	}
	public String getPaymentTerms() {
		return StringHandler.format(paymentTerms);
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getPortOfLoading() {
		return StringHandler.format(portOfLoading);
	}
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}
	public String getPortOfDestination() {
		return StringHandler.format(portOfDestination);
	}
	public void setPortOfDestination(String portOfDestination) {
		this.portOfDestination = portOfDestination;
	}
	public String getTel() {
		return StringHandler.format(tel);
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return StringHandler.format(fax);
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLocation() {
		return StringHandler.format(location);
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return StringHandler.format(country);
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getiAmountTtl() {
		return StringHandler.format(iAmountTtl);
	}
	public void setiAmountTtl(String iAmountTtl) {
		this.iAmountTtl = iAmountTtl;
	}
	public String gettAmountTtl() {
		return StringHandler.format(tAmountTtl);
	}
	public void settAmountTtl(String tAmountTtl) {
		this.tAmountTtl = tAmountTtl;
	}
	public String getAmountTtl4Export() {
		return StringHandler.format(amountTtl4Export);
	}
	public void setAmountTtl4Export(String amountTtl4Export) {
		this.amountTtl4Export = amountTtl4Export;
	}
	public String getReceiptId() {
		return StringHandler.format(receiptId);
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getReceiptDate() {
		return StringHandler.format(receiptDate);
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getMark() {
		return StringHandler.format(mark);
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCustomerId() {
		return StringHandler.format(customerId);
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProfit() {
		return StringHandler.format(profit);
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getCollectionDate() {
		return StringHandler.format(collectionDate);
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getCollectionAmount() {
		return StringHandler.format(collectionAmount);
	}
	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	public String getCheckoutNumber() {
		return StringHandler.format(checkoutNumber);
	}
	public void setCheckoutNumber(String checkoutNumber) {
		this.checkoutNumber = checkoutNumber;
	}
	public String getComment() {
		return StringHandler.format(comment);
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCustomerName() {
		return StringHandler.format(customerName);
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReceiptId4S() {
		return StringHandler.format(receiptId4S);
	}
	public void setReceiptId4S(String receiptId4S) {
		this.receiptId4S = receiptId4S;
	}
	public String getTradeOrderId4S() {
		return StringHandler.format(tradeOrderId4S);
	}
	public void setTradeOrderId4S(String tradeOrderId4S) {
		this.tradeOrderId4S = tradeOrderId4S;
	}
	public String getPo4S() {
		return StringHandler.format(po4S);
	}
	public void setPo4S(String po4s) {
		po4S = po4s;
	}
	public String getContractNo4S() {
		return StringHandler.format(contractNo4S);
	}
	public void setContractNo4S(String contractNo4S) {
		this.contractNo4S = contractNo4S;
	}
	public String getCustomerId4S() {
		return StringHandler.format(customerId4S);
	}
	public void setCustomerId4S(String customerId4S) {
		this.customerId4S = customerId4S;
	}
	public String getCustomerName4S() {
		return StringHandler.format(customerName4S);
	}
	public void setCustomerName4S(String customerName4S) {
		this.customerName4S = customerName4S;
	}
	public String getQuantityCTtl() {
		return StringHandler.format(quantityCTtl);
	}
	public void setQuantityCTtl(String quantityCTtl) {
		this.quantityCTtl = quantityCTtl;
	}
	public String getQuantityTtl() {
		return StringHandler.format(quantityTtl);
	}
	public void setQuantityTtl(String quantityTtl) {
		this.quantityTtl = quantityTtl;
	}
	public String getBoxAmountTtl() {
		return StringHandler.format(boxAmountTtl);
	}
	public void setBoxAmountTtl(String boxAmountTtl) {
		this.boxAmountTtl = boxAmountTtl;
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
	public String getVolumeTtl() {
		return StringHandler.format(volumeTtl);
	}
	public void setVolumeTtl(String volumeTtl) {
		this.volumeTtl = volumeTtl;
	}
	public List<Receipt> getResultReceiptList() {
		return resultReceiptList;
	}
	public void setResultReceiptList(List<Receipt> resultReceiptList) {
		this.resultReceiptList = resultReceiptList;
	}
	public List<ReceiptDtl> getReceiptDtlList() {
		return receiptDtlList;
	}
	public void setReceiptDtlList(List<ReceiptDtl> receiptDtlList) {
		this.receiptDtlList = receiptDtlList;
	}
	public List<ReceiptDtl> getReceiptDtlRemoveList() {
		return receiptDtlRemoveList;
	}
	public void setReceiptDtlRemoveList(List<ReceiptDtl> receiptDtlRemoveList) {
		this.receiptDtlRemoveList = receiptDtlRemoveList;
	}
	public List<ClearanceDtl> getClearanceDtlList() {
		return clearanceDtlList;
	}
	public void setClearanceDtlList(List<ClearanceDtl> clearanceDtlList) {
		this.clearanceDtlList = clearanceDtlList;
	}
	
}
