package com.entity;

import com.util.StringHandler;

public class ReceiptDtl extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6126403265353388861L;
	
	private String receiptId;
	private String complaintId;
	private String tradeOrderId;
	private String productionId;
	private String versionNo;
	private String feeNum;
	private String quantity;
	private String po;
	private String contractNo;
	private String tradeOrderCreateDate;
	private String descriptionE;
	private String hscode;
	private String productionEName4Export;
	private String productionCName4Export;
	private String vendorId;
	private String vendorName;
	private String shortLocation;
	private String tUnitPrice;
	private String tQuantity;
	private String tQuantityNotSent;
	private String tAmount;
	private String iUnitPrice;
	private String iQuantity;
	private String iQuantityNotSent;
	private String iAmount;
	private String cAmount;
	private String quantitySent;
	private String volume;
	private String grossWeight;
	private String netWeight;
	private String inside;
	private String outside;
	private String volumeTtl;
	private String grossWeightTtl;
	private String netWeightTtl;
	private String brand;
	private String purpose;
	private String material;
	private String kind;
	private String kindName;
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
	
	private String advancePayment;
	private String advancePaymentDate;
	private String advancePaymentDiscountRate;
	
	private String exRate;
		
	public String getExportKey() {
		return StringHandler.getStr(getProductionEName4Export()) + "_" + StringHandler.getStr(getShortLocation());
	}
	public String getShortLocation() {
		return shortLocation;
	}
	public void setShortLocation(String shortLocation) {
		this.shortLocation = shortLocation;
	}
	public String getExRate() {
		return exRate;
	}
	public void setExRate(String exRate) {
		this.exRate = exRate;
	}
	public String getComplaintId() {
		return StringHandler.format(complaintId);
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
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
	public String getcAmount() {
		return StringHandler.format(cAmount);
	}
	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}
	public String getAdvancePaymentDate() {
		return StringHandler.format(advancePaymentDate);
	}
	public void setAdvancePaymentDate(String advancePaymentDate) {
		this.advancePaymentDate = advancePaymentDate;
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
	public String getFeeNum() {
		return StringHandler.format(feeNum);
	}
	public void setFeeNum(String feeNum) {
		this.feeNum = feeNum;
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
	public String getTradeOrderCreateDate() {
		return StringHandler.format(tradeOrderCreateDate);
	}
	public void setTradeOrderCreateDate(String tradeOrderCreateDate) {
		this.tradeOrderCreateDate = tradeOrderCreateDate;
	}
	public String getKindName() {
		return StringHandler.format(kindName);
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getBrand() {
		return StringHandler.format(brand);
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPurpose() {
		return StringHandler.format(purpose);
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getMaterial() {
		return StringHandler.format(material);
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getKind() {
		return StringHandler.format(kind);
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getHscode() {
		return StringHandler.format(hscode);
	}
	public void setHscode(String hscode) {
		this.hscode = hscode;
	}
	public String getProductionCName4Export() {
		return StringHandler.format(productionCName4Export);
	}
	public void setProductionCName4Export(String productionCName4Export) {
		this.productionCName4Export = productionCName4Export;
	}
	public String getQuantitySent() {
		return StringHandler.format(quantitySent);
	}
	public void setQuantitySent(String quantitySent) {
		this.quantitySent = quantitySent;
	}
	public String getBoxAmount() {
		return StringHandler.format(boxAmount);
	}
	public void setBoxAmount(String boxAmount) {
		this.boxAmount = boxAmount;
	}
	public String getProductionEName4Export() {
		return StringHandler.format(productionEName4Export);
	}
	public void setProductionEName4Export(String productionEName4Export) {
		this.productionEName4Export = productionEName4Export;
	}
	public String getReceiptId() {
		return StringHandler.format(receiptId);
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getTradeOrderId() {
		return StringHandler.format(tradeOrderId);
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getProductionId() {
		return StringHandler.format(productionId);
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getQuantity() {
		return StringHandler.format(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDescriptionE() {
		return StringHandler.format(descriptionE);
	}
	public void setDescriptionE(String descriptionE) {
		this.descriptionE = descriptionE;
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
	public String gettUnitPrice() {
		return StringHandler.format(tUnitPrice);
	}
	public void settUnitPrice(String tUnitPrice) {
		this.tUnitPrice = tUnitPrice;
	}
	public String gettQuantity() {
		return StringHandler.format(tQuantity);
	}
	public void settQuantity(String tQuantity) {
		this.tQuantity = tQuantity;
	}
	public String getiUnitPrice() {
		return StringHandler.format(iUnitPrice);
	}
	public void setiUnitPrice(String iUnitPrice) {
		this.iUnitPrice = iUnitPrice;
	}
	public String getiQuantity() {
		return StringHandler.format(iQuantity);
	}
	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
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
	public String gettAmount() {
		return StringHandler.format(tAmount);
	}
	public void settAmount(String tAmount) {
		this.tAmount = tAmount;
	}
	public String getiAmount() {
		return StringHandler.format(iAmount);
	}
	public void setiAmount(String iAmount) {
		this.iAmount = iAmount;
	}
	public String gettQuantityNotSent() {
		return StringHandler.format(tQuantityNotSent);
	}
	public void settQuantityNotSent(String tQuantityNotSent) {
		this.tQuantityNotSent = tQuantityNotSent;
	}
	public String getiQuantityNotSent() {
		return StringHandler.format(iQuantityNotSent);
	}
	public void setiQuantityNotSent(String iQuantityNotSent) {
		this.iQuantityNotSent = iQuantityNotSent;
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
}
