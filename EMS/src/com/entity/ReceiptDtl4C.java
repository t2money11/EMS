package com.entity;

import com.util.StringHandler;

public class ReceiptDtl4C extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6126403265353388861L;
	
	private String receiptId;
	private String complaintId;
	private String tradeOrderId;
	private String productionId;
	private String versionNo;
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
	private String iUnitPrice;
	private String tQuantity;
	private String tQuantityNotSent;
	private String tAmount;
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
	
	public String getExportKey() {
		return StringHandler.getStr(getProductionCName4Export()) + "_" + StringHandler.getStr(getShortLocation());
	}
	public String getShortLocation() {
		return shortLocation;
	}
	public void setShortLocation(String shortLocation) {
		this.shortLocation = shortLocation;
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
