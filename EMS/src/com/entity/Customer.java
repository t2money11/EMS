package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class Customer extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -6814284619669503799L;
	
	private String customerId;
	private String customerName;
	private String customerFullName;
	private String webSite;
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
	private String contact1;
	private String contact2;
	private String contact3;
	private String contact4;
	private String contact5;
	private String comment;
	
	private String customerId4S;
	private String customerName4S;
	private String location4S;
	
	private String noAuth;
	
	private List<Customer> resultCustomerList = null;
	
	public Customer() {
		
		resultCustomerList = new ArrayList<Customer>();
	}
	
	
	public String getComment() {
		return StringHandler.format(comment);
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWebSite() {
		return StringHandler.format(webSite);
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getContact1() {
		return StringHandler.format(contact1);
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getContact2() {
		return StringHandler.format(contact2);
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getContact3() {
		return StringHandler.format(contact3);
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3;
	}

	public String getContact4() {
		return StringHandler.format(contact4);
	}

	public void setContact4(String contact4) {
		this.contact4 = contact4;
	}

	public String getContact5() {
		return StringHandler.format(contact5);
	}

	public void setContact5(String contact5) {
		this.contact5 = contact5;
	}

	public String getNoAuth() {
		return StringHandler.format(noAuth);
	}

	public void setNoAuth(String noAuth) {
		this.noAuth = noAuth;
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

	public String getCustomerFullName() {
		return StringHandler.format(customerFullName);
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
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

	public String getCountry() {
		return StringHandler.format(country);
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getLocation4S() {
		return StringHandler.format(location4S);
	}

	public void setLocation4S(String location4s) {
		location4S = location4s;
	}
	
	public List<Customer> getResultCustomerList() {
		return resultCustomerList;
	}
	
	public void setResultCustomerList(List<Customer> resultCustomerList) {
		this.resultCustomerList = resultCustomerList;
	}
	
}
