package com.entity;

import java.sql.Timestamp;

import com.util.DateUtil;
import com.util.PaginationSupport;

public class BaseEntity{
	
	private String errorMessage;
	
	private PaginationSupport pageInfo = null;
	
	private String isBack = null;
	
	private Timestamp createTime;
	private String createUser;
	private Timestamp updateTime;
	private String updateUser;
	private String delFlg;
	
	private Timestamp productionUpdateTime;
	private Timestamp vendorUpdateTime;
	private Timestamp customerUpdateTime;
	private Timestamp tradeOrderUpdateTime;
	private Timestamp complaintUpdateTime;
	
	public BaseEntity(){
		
		pageInfo = new PaginationSupport(0);
		
		Timestamp defaultTimestamp = null;
		try {
			
			defaultTimestamp = DateUtil.stringToTimestamp("1900-01-01 00:00:00");
		} catch (Exception e) {
			// TODO: handle exception
		}
		productionUpdateTime = defaultTimestamp;
		vendorUpdateTime = defaultTimestamp;
		customerUpdateTime = defaultTimestamp;
		tradeOrderUpdateTime = defaultTimestamp;
		complaintUpdateTime = defaultTimestamp;
	}
	
	/**
	 * GetSetFromHere
	 * 
	 */
	public PaginationSupport getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PaginationSupport pageInfo) {
		this.pageInfo = pageInfo;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public Timestamp getProductionUpdateTime() {
		return productionUpdateTime;
	}
	public void setProductionUpdateTime(Timestamp productionUpdateTime) {
		this.productionUpdateTime = productionUpdateTime;
	}
	public Timestamp getVendorUpdateTime() {
		return vendorUpdateTime;
	}
	public void setVendorUpdateTime(Timestamp vendorUpdateTime) {
		this.vendorUpdateTime = vendorUpdateTime;
	}
	public Timestamp getCustomerUpdateTime() {
		return customerUpdateTime;
	}
	public void setCustomerUpdateTime(Timestamp customerUpdateTime) {
		this.customerUpdateTime = customerUpdateTime;
	}
	public Timestamp getTradeOrderUpdateTime() {
		return tradeOrderUpdateTime;
	}
	public void setTradeOrderUpdateTime(Timestamp tradeOrderUpdateTime) {
		this.tradeOrderUpdateTime = tradeOrderUpdateTime;
	}
	public Timestamp getComplaintUpdateTime() {
		return complaintUpdateTime;
	}
	public void setComplaintUpdateTime(Timestamp complaintUpdateTime) {
		this.complaintUpdateTime = complaintUpdateTime;
	}
	
}
