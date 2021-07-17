package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class Complaint extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 4229965232187278622L;
	
	//DB项目
	private String complaintId;
	private String complaintDate;
	private String customerId;
	private String comment;
	private String replyDate;
	private String 	alertDateFrom;
	private String 	dealDeadline;
	private String dealDate;
	private String isTopFlg;
	
	//检索条件
	private String complaintId4S;
	private String customerId4S;
	private String customerName4S;
	private String productionId4S;
	
	//画面项目
	private String customerName;
	private String picture;
	private String pictureExisted;
	
	private String quantityTtl;
	private String boxAmountTtl;
	private String volumeTtl;
	private String grossWeightTtl;
	private String netWeightTtl;
	private String iAmountTtl;
	private String tAmountTtl;
	
	private String quantityTtl4A;
	private String boxAmountTtl4A;
	private String volumeTtl4A;
	private String grossWeightTtl4A;
	private String netWeightTtl4A;
	private String iAmountTtl4A;
	private String tAmountTtl4A;
	
	private String quantityTtl4R;
	private String boxAmountTtl4R;
	private String volumeTtl4R;
	private String grossWeightTtl4R;
	private String netWeightTtl4R;
	private String iAmountTtl4R;
	private String tAmountTtl4R;
	
	private String quantityTtl4U;
	private String boxAmountTtl4U;
	private String volumeTtl4U;
	private String grossWeightTtl4U;
	private String netWeightTtl4U;
	private String iAmountTtl4U;
	private String tAmountTtl4U;
	
	private List<Complaint> resultComplaintList = new ArrayList<Complaint>();
	private List<ComplaintDtl> complaintDtlList = new ArrayList<ComplaintDtl>();
	
	public Complaint() {
		
	}
	
	public String getQuantityTtl4U() {
		return StringHandler.format(quantityTtl4U);
	}

	public void setQuantityTtl4U(String quantityTtl4U) {
		this.quantityTtl4U = quantityTtl4U;
	}

	public String getBoxAmountTtl4U() {
		return StringHandler.format(boxAmountTtl4U);
	}

	public void setBoxAmountTtl4U(String boxAmountTtl4U) {
		this.boxAmountTtl4U = boxAmountTtl4U;
	}

	public String getVolumeTtl4U() {
		return StringHandler.format(volumeTtl4U);
	}

	public void setVolumeTtl4U(String volumeTtl4U) {
		this.volumeTtl4U = volumeTtl4U;
	}

	public String getGrossWeightTtl4U() {
		return StringHandler.format(grossWeightTtl4U);
	}

	public void setGrossWeightTtl4U(String grossWeightTtl4U) {
		this.grossWeightTtl4U = grossWeightTtl4U;
	}

	public String getNetWeightTtl4U() {
		return StringHandler.format(netWeightTtl4U);
	}

	public void setNetWeightTtl4U(String netWeightTtl4U) {
		this.netWeightTtl4U = netWeightTtl4U;
	}

	public String getiAmountTtl4U() {
		return StringHandler.format(iAmountTtl4U);
	}

	public void setiAmountTtl4U(String iAmountTtl4U) {
		this.iAmountTtl4U = iAmountTtl4U;
	}

	public String gettAmountTtl4U() {
		return StringHandler.format(tAmountTtl4U);
	}

	public void settAmountTtl4U(String tAmountTtl4U) {
		this.tAmountTtl4U = tAmountTtl4U;
	}
	
	public String getQuantityTtl4A() {
		return StringHandler.format(quantityTtl4A);
	}

	public void setQuantityTtl4A(String quantityTtl4A) {
		this.quantityTtl4A = quantityTtl4A;
	}

	public String getBoxAmountTtl4A() {
		return StringHandler.format(boxAmountTtl4A);
	}

	public void setBoxAmountTtl4A(String boxAmountTtl4A) {
		this.boxAmountTtl4A = boxAmountTtl4A;
	}

	public String getVolumeTtl4A() {
		return StringHandler.format(volumeTtl4A);
	}

	public void setVolumeTtl4A(String volumeTtl4A) {
		this.volumeTtl4A = volumeTtl4A;
	}

	public String getGrossWeightTtl4A() {
		return StringHandler.format(grossWeightTtl4A);
	}

	public void setGrossWeightTtl4A(String grossWeightTtl4A) {
		this.grossWeightTtl4A = grossWeightTtl4A;
	}

	public String getNetWeightTtl4A() {
		return StringHandler.format(netWeightTtl4A);
	}

	public void setNetWeightTtl4A(String netWeightTtl4A) {
		this.netWeightTtl4A = netWeightTtl4A;
	}

	public String getiAmountTtl4A() {
		return StringHandler.format(iAmountTtl4A);
	}

	public void setiAmountTtl4A(String iAmountTtl4A) {
		this.iAmountTtl4A = iAmountTtl4A;
	}

	public String gettAmountTtl4A() {
		return StringHandler.format(tAmountTtl4A);
	}

	public void settAmountTtl4A(String tAmountTtl4A) {
		this.tAmountTtl4A = tAmountTtl4A;
	}
	
	public String getQuantityTtl4R() {
		return StringHandler.format(quantityTtl4R);
	}

	public void setQuantityTtl4R(String quantityTtl4R) {
		this.quantityTtl4R = quantityTtl4R;
	}

	public String getBoxAmountTtl4R() {
		return StringHandler.format(boxAmountTtl4R);
	}

	public void setBoxAmountTtl4R(String boxAmountTtl4R) {
		this.boxAmountTtl4R = boxAmountTtl4R;
	}

	public String getVolumeTtl4R() {
		return StringHandler.format(volumeTtl4R);
	}

	public void setVolumeTtl4R(String volumeTtl4R) {
		this.volumeTtl4R = volumeTtl4R;
	}

	public String getGrossWeightTtl4R() {
		return StringHandler.format(grossWeightTtl4R);
	}

	public void setGrossWeightTtl4R(String grossWeightTtl4R) {
		this.grossWeightTtl4R = grossWeightTtl4R;
	}

	public String getNetWeightTtl4R() {
		return StringHandler.format(netWeightTtl4R);
	}

	public void setNetWeightTtl4R(String netWeightTtl4R) {
		this.netWeightTtl4R = netWeightTtl4R;
	}

	public String getiAmountTtl4R() {
		return StringHandler.format(iAmountTtl4R);
	}

	public void setiAmountTtl4R(String iAmountTtl4R) {
		this.iAmountTtl4R = iAmountTtl4R;
	}

	public String gettAmountTtl4R() {
		return StringHandler.format(tAmountTtl4R);
	}

	public void settAmountTtl4R(String tAmountTtl4R) {
		this.tAmountTtl4R = tAmountTtl4R;
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

	public String getDealDate() {
		return StringHandler.format(dealDate);
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getIsTopFlg() {
		return StringHandler.format(isTopFlg);
	}

	public void setIsTopFlg(String isTopFlg) {
		this.isTopFlg = isTopFlg;
	}

	public String getReplyDate() {
		return StringHandler.format(replyDate);
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public String getAlertDateFrom() {
		return StringHandler.format(alertDateFrom);
	}

	public void setAlertDateFrom(String alertDateFrom) {
		this.alertDateFrom = alertDateFrom;
	}

	public String getDealDeadline() {
		return StringHandler.format(dealDeadline);
	}

	public void setDealDeadline(String dealDeadline) {
		this.dealDeadline = dealDeadline;
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

	public String getCustomerName4S() {
		return StringHandler.format(customerName4S);
	}

	public void setCustomerName4S(String customerName4S) {
		this.customerName4S = customerName4S;
	}

	public String getComplaintId() {
		return StringHandler.format(complaintId);
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getComplaintDate() {
		return StringHandler.format(complaintDate);
	}

	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
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

	public String getComplaintId4S() {
		return StringHandler.format(complaintId4S);
	}

	public void setComplaintId4S(String complaintId4S) {
		this.complaintId4S = complaintId4S;
	}

	public String getCustomerId4S() {
		return StringHandler.format(customerId4S);
	}

	public void setCustomerId4S(String customerId4S) {
		this.customerId4S = customerId4S;
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

	public List<Complaint> getResultComplaintList() {
		return resultComplaintList;
	}
	
	public void setResultComplaintList(List<Complaint> resultComplaintList) {
		this.resultComplaintList = resultComplaintList;
	}

	public List<ComplaintDtl> getComplaintDtlList() {
		return complaintDtlList;
	}

	public void setComplaintDtlList(List<ComplaintDtl> complaintDtlList) {
		this.complaintDtlList = complaintDtlList;
	}
	
}
