package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class Vendor extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -6814284619669503799L;
	
	private String vendorId;
	private String vendorName;
	private String vendorFullName;
	private String location;
	private String shortLocation;
	private String tel;
	private String fax;
	private String orcc;
	private String billingInfo;
	private String contact1;
	private String contact2;
	private String contact3;
	private String contact4;
	private String contact5;
	private String mobile1;
	private String mobile2;
	private String mobile3;
	private String mobile4;
	private String mobile5;
	private String title1;
	private String title2;
	private String title3;
	private String title4;
	private String title5;
	
	private String vendorId4S;
	private String vendorName4S;
	private String location4S;
	
	private String noAuth;
	
	private List<Vendor> resultVendorList = null;
	
	public Vendor() {
		
		resultVendorList = new ArrayList<Vendor>();
	}

	public String getShortLocation() {
		return shortLocation;
	}

	public void setShortLocation(String shortLocation) {
		this.shortLocation = shortLocation;
	}

	public String getNoAuth() {
		return StringHandler.format(noAuth);
	}
	
	public void setNoAuth(String noAuth) {
		this.noAuth = noAuth;
	}
	
	public String getBillingInfo() {
		return StringHandler.format(billingInfo);
	}

	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}

	public String getVendorFullName() {
		return StringHandler.format(vendorFullName);
	}

	public void setVendorFullName(String vendorFullName) {
		this.vendorFullName = vendorFullName;
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

	public String getMobile1() {
		return StringHandler.format(mobile1);
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return StringHandler.format(mobile2);
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getMobile3() {
		return StringHandler.format(mobile3);
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	public String getMobile4() {
		return StringHandler.format(mobile4);
	}

	public void setMobile4(String mobile4) {
		this.mobile4 = mobile4;
	}

	public String getMobile5() {
		return StringHandler.format(mobile5);
	}

	public void setMobile5(String mobile5) {
		this.mobile5 = mobile5;
	}

	public String getTitle1() {
		return StringHandler.format(title1);
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return StringHandler.format(title2);
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return StringHandler.format(title3);
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	public String getTitle4() {
		return StringHandler.format(title4);
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	public String getTitle5() {
		return StringHandler.format(title5);
	}

	public void setTitle5(String title5) {
		this.title5 = title5;
	}

	public String getOrcc() {
		return StringHandler.format(orcc);
	}

	public void setOrcc(String orcc) {
		this.orcc = orcc;
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

	public List<Vendor> getResultVendorList() {
		return resultVendorList;
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

	public String getLocation4S() {
		return StringHandler.format(location4S);
	}

	public void setLocation4S(String location4s) {
		location4S = location4s;
	}

	public void setResultVendorList(List<Vendor> resultVendorList) {
		this.resultVendorList = resultVendorList;
	}
	
}
