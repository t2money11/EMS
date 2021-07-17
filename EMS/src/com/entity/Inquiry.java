package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class Inquiry extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -442326240986481677L;
	
	private String inquiryId;
	private String customerId;
	private String customerName;
	private String comment;
	
	private String productionId4S;
	private String customerId4S;
	private String customerName4S;
	private String inquiryId4S;
	
	private List<InquiryDtl> resultInquiryDtlList = new ArrayList<InquiryDtl>();
	private List<InquiryDtl> inquiryDtlList = new ArrayList<InquiryDtl>();
	
	public Inquiry() {
		
	}
	
	public String getInquiryId() {
		return StringHandler.format(inquiryId);
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getCustomerId() {
		return StringHandler.format(customerId);
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getProductionId4S() {
		return StringHandler.format(productionId4S);
	}

	public void setProductionId4S(String productionId4S) {
		this.productionId4S = productionId4S;
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

	public String getInquiryId4S() {
		return StringHandler.format(inquiryId4S);
	}

	public void setInquiryId4S(String inquiryId4S) {
		this.inquiryId4S = inquiryId4S;
	}

	public List<InquiryDtl> getResultInquiryDtlList() {
		return resultInquiryDtlList;
	}

	public void setResultInquiryDtlList(List<InquiryDtl> resultInquiryDtlList) {
		this.resultInquiryDtlList = resultInquiryDtlList;
	}

	public List<InquiryDtl> getInquiryDtlList() {
		return inquiryDtlList;
	}

	public void setInquiryDtlList(List<InquiryDtl> inquiryDtlList) {
		this.inquiryDtlList = inquiryDtlList;
	}
	
}
