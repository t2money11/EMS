package com.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.context.ConstantParam;
import com.util.StringHandler;

public class TradeOrder extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3103396873578223250L;
	
	private String tradeOrderId;
	private String po;
	private String contractNo;
	private String tradeOrderCreateDate;
	private String tradeOrderConfirmDate;
	private String customerId;
	private String customerName;
	private String customerFullName;
	private String location;
	private String tel;
	private String fax;
	private String sendMode;
	private String shipment;
	private String portOfLoading;
	private String freightTerms;
	private String paymentTerms;
	private String portOfDestination;
	private String exRate;
	private String etrRate;
	private String comment;
	private String updateComment;
	private String amountTtl;
	private String status;
	private String statusName;
	private String advancePaymentCondition;
	
	private String inquiryId;
	
	private boolean isConfirmed = false;
	private String inquiryNeeded;
	
	private String tradeOrderId4S;
	private String po4S;
	private String contractNo4S;
	private String productionId4S;
	private String vendorId4S;
	private String vendorName4S;
	private String customerId4S;
	private String customerName4S;
	private String status4S;
	
	Map<String, String> statusInput = null;  
	Map<String, String> statusInput4S = null;  
	Map<String, String> sendModeInput = null;  
	
	private List<TradeOrder> resultTradeOrderList = new ArrayList<TradeOrder>();
	private List<TradeOrderDtl> tradeOrderDtlList = new ArrayList<TradeOrderDtl>();
	private List<InterTradeOrder> interTradeOrderList = new ArrayList<InterTradeOrder>();
	
	public TradeOrder(){
		
		statusInput = ConstantParam.codeMap.get(ConstantParam.T_STATUS);
		statusInput.remove("");
		
		statusInput4S = new HashMap<String, String>();
		statusInput4S.put(null, null);
		Iterator<Map.Entry<String,String>> iter = statusInput.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry<String,String> entry = iter.next();
			statusInput4S.put(entry.getKey(), entry.getValue());
		}
		
		sendModeInput = ConstantParam.codeMap.get(ConstantParam.SEND_MODE);
		sendModeInput.remove("");
	}
	
	public String getProductionId4S() {
		return StringHandler.format(productionId4S);
	}

	public void setProductionId4S(String productionId4S) {
		this.productionId4S = productionId4S;
	}
	
	public String getInquiryNeeded() {
		return inquiryNeeded;
	}

	public void setInquiryNeeded(String inquiryNeeded) {
		this.inquiryNeeded = inquiryNeeded;
	}

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public Map<String, String> getSendModeInput() {
		return sendModeInput;
	}

	public void setSendModeInput(Map<String, String> sendModeInput) {
		this.sendModeInput = sendModeInput;
	}
	
	public String getSendMode() {
		return StringHandler.format(sendMode);
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public String getAdvancePaymentCondition() {
		return StringHandler.format(advancePaymentCondition);
	}

	public void setAdvancePaymentCondition(String advancePaymentCondition) {
		this.advancePaymentCondition = advancePaymentCondition;
	}

	public String getVendorId4S() {
		return StringHandler.format(vendorId4S);
	}

	public void setVendorId4S(String vendorId4S) {
		this.vendorId4S = vendorId4S;
	}

	public String getVendorName4S() {
		return StringHandler.format(vendorName4S);
	}

	public void setVendorName4S(String vendorName4S) {
		this.vendorName4S = vendorName4S;
	}
	
	public String getStatus4S() {
		return StringHandler.format(status4S);
	}

	public void setStatus4S(String status4s) {
		status4S = status4s;
	}

	public boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getCustomerFullName() {
		return StringHandler.format(customerFullName);
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	
	public String getTradeOrderId() {
		return StringHandler.format(tradeOrderId);
	}

	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}

	public String getPo() {
		return StringHandler.format(po);
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getContractNo() {
		return StringHandler.format(contractNo);
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTradeOrderCreateDate() {
		return StringHandler.format(tradeOrderCreateDate);
	}

	public void setTradeOrderCreateDate(String tradeOrderCreateDate) {
		this.tradeOrderCreateDate = tradeOrderCreateDate;
	}

	public String getTradeOrderConfirmDate() {
		return StringHandler.format(tradeOrderConfirmDate);
	}

	public void setTradeOrderConfirmDate(String tradeOrderConfirmDate) {
		this.tradeOrderConfirmDate = tradeOrderConfirmDate;
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

	public String getLocation() {
		return StringHandler.format(location);
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getShipment() {
		return StringHandler.format(shipment);
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	
	public String getPortOfLoading() {
		return StringHandler.format(portOfLoading);
	}

	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
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

	public String getPortOfDestination() {
		return StringHandler.format(portOfDestination);
	}

	public void setPortOfDestination(String portOfDestination) {
		this.portOfDestination = portOfDestination;
	}

	public String getExRate() {
		return StringHandler.format(exRate);
	}

	public void setExRate(String exRate) {
		this.exRate = exRate;
	}

	public String getEtrRate() {
		return StringHandler.format(etrRate);
	}

	public void setEtrRate(String etrRate) {
		this.etrRate = etrRate;
	}

	public String getComment() {
		return StringHandler.format(comment);
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUpdateComment() {
		return StringHandler.format(updateComment);
	}

	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	public String getAmountTtl() {
		return StringHandler.format(amountTtl);
	}

	public void setAmountTtl(String amountTtl) {
		this.amountTtl = amountTtl;
	}

	public String getStatus() {
		return StringHandler.format(status);
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public Map<String, String> getStatusInput() {
		return statusInput;
	}

	public void setStatusInput(Map<String, String> statusInput) {
		this.statusInput = statusInput;
	}
	
	public Map<String, String> getStatusInput4S() {
		return statusInput4S;
	}

	public void setStatusInput4S(Map<String, String> statusInput4S) {
		this.statusInput4S = statusInput4S;
	}

	public List<TradeOrder> getResultTradeOrderList() {
		return resultTradeOrderList;
	}
	
	public void setResultTradeOrderList(List<TradeOrder> resultTradeOrderList) {
		this.resultTradeOrderList = resultTradeOrderList;
	}
	
	public List<TradeOrderDtl> getTradeOrderDtlList() {
		return tradeOrderDtlList;
	}
	
	public void setTradeOrderDtlList(List<TradeOrderDtl> tradeOrderDtlList) {
		this.tradeOrderDtlList = tradeOrderDtlList;
	}

	public List<InterTradeOrder> getInterTradeOrderList() {
		return interTradeOrderList;
	}

	public void setInterTradeOrderList(List<InterTradeOrder> interTradeOrderList) {
		this.interTradeOrderList = interTradeOrderList;
	}
}
