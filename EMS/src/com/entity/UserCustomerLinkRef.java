package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class UserCustomerLinkRef extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 7520733420443100716L;

	private String userId;
	private String userName;
	private String customerId;
	private String customerName;
	
	private String userId4S;
	private String userName4S;
	private String customerId4S;
	private String customerName4S;
	
	private List<UserCustomerLinkRef> resultUserCustomerLinkRefList = new ArrayList<UserCustomerLinkRef>();

	public String getUserId() {
		return StringHandler.format(userId);
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return StringHandler.format(userName);
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserId4S() {
		return StringHandler.format(userId4S);
	}

	public void setUserId4S(String userId4S) {
		this.userId4S = userId4S;
	}

	public String getUserName4S() {
		return StringHandler.format(userName4S);
	}

	public void setUserName4S(String userName4S) {
		this.userName4S = userName4S;
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

	public List<UserCustomerLinkRef> getResultUserCustomerLinkRefList() {
		return resultUserCustomerLinkRefList;
	}

	public void setResultUserCustomerLinkRefList(
			List<UserCustomerLinkRef> resultUserCustomerLinkRefList) {
		this.resultUserCustomerLinkRefList = resultUserCustomerLinkRefList;
	}
}
