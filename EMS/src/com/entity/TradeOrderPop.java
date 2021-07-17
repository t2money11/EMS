package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.util.StringHandler;

public class TradeOrderPop extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3103396873578223250L;
	
	private String complaintId;
	private String tradeOrderId;
	private String po;
	private String contractNo;
	private String tradeOrderCreateDate;
	private String productionId;
	private String versionNo;
	private String descriptionE;
	private String descriptionC;
	private String productionEName4Export;
	private String productionCName4Export;
	private String vendorId;
	private String vendorName;
	private String shortLocation;
	private String customerId;
	private String customerName;
	private String hscode;
	private String tQuantity;
	private String tUnitPrice;
	private String tQuantityNotSent;
	private String iQuantity;
	private String iUnitPrice;
	private String iQuantityNotSent;
	private String quantitySent;
	private String volume;
	private String grossWeight;
	private String netWeight;
	private String inside;
	private String outside;
	private String boxAmount;
	
	private String tFeeTitle1;
	private String tFee1;
	private String tFeeTitle2;
	private String tFee2;
	private String tFeeTitle3;
	private String tFee3;
	private String tFeeTitle4;
	private String tFee4;
	private String iFeeTitle1;
	private String iFee1;
	private String iFeeTitle2;
	private String iFee2;
	private String iFeeTitle3;
	private String iFee3;
	private String iFeeTitle4;
	private String iFee4;
	
	private String complaintId4S;
	private String tradeOrderId4S;
	private String po4S;
	private String contractNo4S;
	private String customerId4S;
	private String customerName4S;
	private String productionId4S;
	private String isStatusRefFlg;
	
	private List<TradeOrderPop> resultTradeOrderPopList = new ArrayList<TradeOrderPop>();
	
	public TradeOrderPop(){
		
	}
	
	public String getShortLocation() {
		return shortLocation;
	}

	public void setShortLocation(String shortLocation) {
		this.shortLocation = shortLocation;
	}

	public String getProductionId4S() {
		return productionId4S;
	}

	public void setProductionId4S(String productionId4S) {
		this.productionId4S = productionId4S;
	}

	public String getBoxAmount() {
		return StringHandler.format(boxAmount);
	}

	public void setBoxAmount(String boxAmount) {
		this.boxAmount = boxAmount;
	}

	public String getComplaintId4S() {
		return StringHandler.format(complaintId4S);
	}

	public void setComplaintId4S(String complaintId4S) {
		this.complaintId4S = complaintId4S;
	}

	public String getComplaintId() {
		return StringHandler.format(complaintId);
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String gettFeeTitle1() {
		return StringHandler.format(tFeeTitle1);
	}

	public void settFeeTitle1(String tFeeTitle1) {
		this.tFeeTitle1 = tFeeTitle1;
	}

	public String gettFee1() {
		return StringHandler.format(tFee1);
	}

	public void settFee1(String tFee1) {
		this.tFee1 = tFee1;
	}

	public String gettFeeTitle2() {
		return StringHandler.format(tFeeTitle2);
	}

	public void settFeeTitle2(String tFeeTitle2) {
		this.tFeeTitle2 = tFeeTitle2;
	}

	public String gettFee2() {
		return StringHandler.format(tFee2);
	}

	public void settFee2(String tFee2) {
		this.tFee2 = tFee2;
	}

	public String gettFeeTitle3() {
		return StringHandler.format(tFeeTitle3);
	}

	public void settFeeTitle3(String tFeeTitle3) {
		this.tFeeTitle3 = tFeeTitle3;
	}

	public String gettFee3() {
		return StringHandler.format(tFee3);
	}

	public void settFee3(String tFee3) {
		this.tFee3 = tFee3;
	}

	public String gettFeeTitle4() {
		return StringHandler.format(tFeeTitle4);
	}

	public void settFeeTitle4(String tFeeTitle4) {
		this.tFeeTitle4 = tFeeTitle4;
	}

	public String gettFee4() {
		return StringHandler.format(tFee4);
	}

	public void settFee4(String tFee4) {
		this.tFee4 = tFee4;
	}

	public String getiFeeTitle1() {
		return StringHandler.format(iFeeTitle1);
	}

	public void setiFeeTitle1(String iFeeTitle1) {
		this.iFeeTitle1 = iFeeTitle1;
	}

	public String getiFee1() {
		return StringHandler.format(iFee1);
	}

	public void setiFee1(String iFee1) {
		this.iFee1 = iFee1;
	}

	public String getiFeeTitle2() {
		return StringHandler.format(iFeeTitle2);
	}

	public void setiFeeTitle2(String iFeeTitle2) {
		this.iFeeTitle2 = iFeeTitle2;
	}

	public String getiFee2() {
		return StringHandler.format(iFee2);
	}

	public void setiFee2(String iFee2) {
		this.iFee2 = iFee2;
	}

	public String getiFeeTitle3() {
		return StringHandler.format(iFeeTitle3);
	}

	public void setiFeeTitle3(String iFeeTitle3) {
		this.iFeeTitle3 = iFeeTitle3;
	}

	public String getiFee3() {
		return StringHandler.format(iFee3);
	}

	public void setiFee3(String iFee3) {
		this.iFee3 = iFee3;
	}

	public String getiFeeTitle4() {
		return StringHandler.format(iFeeTitle4);
	}

	public void setiFeeTitle4(String iFeeTitle4) {
		this.iFeeTitle4 = iFeeTitle4;
	}

	public String getiFee4() {
		return StringHandler.format(iFee4);
	}

	public void setiFee4(String iFee4) {
		this.iFee4 = iFee4;
	}

	public String getIsStatusRefFlg() {
		return StringHandler.format(isStatusRefFlg);
	}

	public void setIsStatusRefFlg(String isStatusRefFlg) {
		this.isStatusRefFlg = isStatusRefFlg;
	}

	public String getTradeOrderCreateDate() {
		return StringHandler.format(tradeOrderCreateDate);
	}
	
	public void setTradeOrderCreateDate(String tradeOrderCreateDate) {
		this.tradeOrderCreateDate = tradeOrderCreateDate;
	}
	
	public String getHscode() {
		return StringHandler.format(hscode);
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}
	
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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

	public String getProductionCName4Export() {
		return StringHandler.format(productionCName4Export);
	}

	public void setProductionCName4Export(String productionCName4Export) {
		this.productionCName4Export = productionCName4Export;
	}

	public String getProductionEName4Export() {
		return StringHandler.format(productionEName4Export);
	}

	public void setProductionEName4Export(String productionEName4Export) {
		this.productionEName4Export = productionEName4Export;
	}

	public String getVolume() {
		return StringHandler.format(volume);
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getGrossWeight() {
		return StringHandler.format(grossWeight);
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getNetWeight() {
		return StringHandler.format(netWeight);
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getInside() {
		return StringHandler.format(inside);
	}

	public void setInside(String inside) {
		this.inside = inside;
	}

	public String getOutside() {
		return StringHandler.format(outside);
	}

	public void setOutside(String outside) {
		this.outside = outside;
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

	public String getProductionId() {
		return StringHandler.format(productionId);
	}

	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}

	public String getDescriptionE() {
		return StringHandler.format(descriptionE);
	}

	public void setDescriptionE(String descriptionE) {
		this.descriptionE = descriptionE;
	}

	public String gettQuantity() {
		return StringHandler.format(tQuantity);
	}

	public void settQuantity(String tQuantity) {
		this.tQuantity = tQuantity;
	}

	public String gettQuantityNotSent() {
		return StringHandler.format(tQuantityNotSent);
	}

	public void settQuantityNotSent(String tQuantityNotSent) {
		this.tQuantityNotSent = tQuantityNotSent;
	}

	public String getiQuantity() {
		return StringHandler.format(iQuantity);
	}

	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
	}

	public String getiQuantityNotSent() {
		return StringHandler.format(iQuantityNotSent);
	}

	public void setiQuantityNotSent(String iQuantityNotSent) {
		this.iQuantityNotSent = iQuantityNotSent;
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
	
	public String getQuantitySent() {
		return StringHandler.format(quantitySent);
	}

	public void setQuantitySent(String quantitySent) {
		this.quantitySent = quantitySent;
	}

	public String getDescriptionC() {
		return StringHandler.format(descriptionC);
	}

	public void setDescriptionC(String descriptionC) {
		this.descriptionC = descriptionC;
	}

	public String gettUnitPrice() {
		return StringHandler.format(tUnitPrice);
	}

	public void settUnitPrice(String tUnitPrice) {
		this.tUnitPrice = tUnitPrice;
	}

	public String getiUnitPrice() {
		return StringHandler.format(iUnitPrice);
	}

	public void setiUnitPrice(String iUnitPrice) {
		this.iUnitPrice = iUnitPrice;
	}

	public List<TradeOrderPop> getResultTradeOrderPopList() {
		return resultTradeOrderPopList;
	}

	public void setResultTradeOrderPopList(
			List<TradeOrderPop> resultTradeOrderPopList) {
		this.resultTradeOrderPopList = resultTradeOrderPopList;
	}
	
}
