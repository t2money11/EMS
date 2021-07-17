package com.entity;

public class AnalysisResultExportInfo extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -8839979405794382874L;
	
	private String receiptId;
	private String customerName;
	private String receiptNo;
	private String amountTtl4Export;
	private String portOfLoading;
	private String productionCName4Export;
	private String HSCode;
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getAmountTtl4Export() {
		return amountTtl4Export;
	}
	public void setAmountTtl4Export(String amountTtl4Export) {
		this.amountTtl4Export = amountTtl4Export;
	}
	public String getPortOfLoading() {
		return portOfLoading;
	}
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}
	public String getProductionCName4Export() {
		return productionCName4Export;
	}
	public void setProductionCName4Export(String productionCName4Export) {
		this.productionCName4Export = productionCName4Export;
	}
	public String getHSCode() {
		return HSCode;
	}
	public void setHSCode(String hSCode) {
		HSCode = hSCode;
	}
}
