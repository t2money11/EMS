package com.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.context.ConstantParam;
import com.util.StringHandler;

public class InterTradeOrder extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -8286358390662677363L;

	private String interTradeOrderId;
	private String interTradeCreateDate;
	private String interTradeConfirmDate;
	private String recieveDate;
	private String sendMode;
	private String tradeOrderId;
	private String vendorId;
	private String vendorName;
	private String vendorFullName;
	private String location;
	private String hasAdvancePayment;
	private String advancePayment;
	private String advancePaymentDate;
	private String advancePaymentDiscountRate;
	private String comment;
	private String amountTtl;
	private String status;
	private String contractNo;
	
	private List<InterTradeOrder> resultTradeOrderList = new ArrayList<InterTradeOrder>();
	private List<InterTradeOrderDtl> interTradeOrderDtlList = new ArrayList<InterTradeOrderDtl>();
	
	Map<String, String> sendModeInput = null;  
	
	public InterTradeOrder(){
		
		sendModeInput = ConstantParam.codeMap.get(ConstantParam.SEND_MODE);
		sendModeInput.remove("");
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

	public String getVendorFullName() {
		return StringHandler.format(vendorFullName);
	}

	public void setVendorFullName(String vendorFullName) {
		this.vendorFullName = vendorFullName;
	}

	public String getContractNo() {
		return StringHandler.format(contractNo);
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getInterTradeOrderId() {
		return StringHandler.format(interTradeOrderId);
	}

	public void setInterTradeOrderId(String interTradeOrderId) {
		this.interTradeOrderId = interTradeOrderId;
	}

	public String getInterTradeCreateDate() {
		return StringHandler.format(interTradeCreateDate);
	}

	public void setInterTradeCreateDate(String interTradeCreateDate) {
		this.interTradeCreateDate = interTradeCreateDate;
	}

	public String getInterTradeConfirmDate() {
		return StringHandler.format(interTradeConfirmDate);
	}

	public void setInterTradeConfirmDate(String interTradeConfirmDate) {
		this.interTradeConfirmDate = interTradeConfirmDate;
	}
	
	public String getTradeOrderId() {
		return StringHandler.format(tradeOrderId);
	}

	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
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

	public String getLocation() {
		return StringHandler.format(location);
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAdvancePayment() {
		return StringHandler.format(advancePayment);
	}

	public void setAdvancePayment(String advancePayment) {
		this.advancePayment = advancePayment;
	}

	public String getAdvancePaymentDiscountRate() {
		return StringHandler.format(advancePaymentDiscountRate);
	}

	public void setAdvancePaymentDiscountRate(String advancePaymentDiscountRate) {
		this.advancePaymentDiscountRate = advancePaymentDiscountRate;
	}

	public String getComment() {
		return StringHandler.format(comment);
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getAdvancePaymentDate() {
		return StringHandler.format(advancePaymentDate);
	}

	public void setAdvancePaymentDate(String advancePaymentDate) {
		this.advancePaymentDate = advancePaymentDate;
	}

	public String getRecieveDate() {
		return StringHandler.format(recieveDate);
	}

	public void setRecieveDate(String recieveDate) {
		this.recieveDate = recieveDate;
	}

	public String getHasAdvancePayment() {
		return StringHandler.format(hasAdvancePayment);
	}

	public void setHasAdvancePayment(String hasAdvancePayment) {
		this.hasAdvancePayment = hasAdvancePayment;
	}

	public List<InterTradeOrderDtl> getInterTradeOrderDtlList() {
		return interTradeOrderDtlList;
	}

	public void setInterTradeOrderDtlList(List<InterTradeOrderDtl> interTradeOrderDtlList) {
		this.interTradeOrderDtlList = interTradeOrderDtlList;
	}

	public List<InterTradeOrder> getResultTradeOrderList() {
		return resultTradeOrderList;
	}

	public void setResultTradeOrderList(List<InterTradeOrder> resultTradeOrderList) {
		this.resultTradeOrderList = resultTradeOrderList;
	}
	
}
