package com.entity;

import com.util.StringHandler;

public class InterTradeOrderDtl extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2223864779515149378L;

	private String interTradeOrderId;
	private String productionId;
	private String versionNo;
	private String productionIdVendor;
	private String descriptionC;
	private String volume;
	private String grossWeight;
	private String netWeight;
	private String inside;
	private String outside;
	private String volumeTtl;
	private String grossWeightTtl;
	private String netWeightTtl;
	private String boxAmount;
	private String quantity;
	private String quantitySent;
	private String unitPrice;
	private String amount;
	private String sendDate1;
	private String sendQuantity1;
	private String sendDate2;
	private String sendQuantity2;
	private String sendDate3;
	private String sendQuantity3;
	private String sendDate4;
	private String sendQuantity4;
	private String feeTitle1;
	private String fee1;
	private String feeTitle2;
	private String fee2;
	private String feeTitle3;
	private String fee3;
	private String feeTitle4;
	private String fee4;
	private String comment;
	private String status;
	
	public String getFeeTitle1() {
		return StringHandler.format(feeTitle1);
	}
	public void setFeeTitle1(String feeTitle1) {
		this.feeTitle1 = feeTitle1;
	}
	public String getFee1() {
		return StringHandler.format(fee1);
	}
	public void setFee1(String fee1) {
		this.fee1 = fee1;
	}
	public String getFeeTitle2() {
		return StringHandler.format(feeTitle2);
	}
	public void setFeeTitle2(String feeTitle2) {
		this.feeTitle2 = feeTitle2;
	}
	public String getFee2() {
		return StringHandler.format(fee2);
	}
	public void setFee2(String fee2) {
		this.fee2 = fee2;
	}
	public String getFeeTitle3() {
		return StringHandler.format(feeTitle3);
	}
	public void setFeeTitle3(String feeTitle3) {
		this.feeTitle3 = feeTitle3;
	}
	public String getFee3() {
		return StringHandler.format(fee3);
	}
	public void setFee3(String fee3) {
		this.fee3 = fee3;
	}
	public String getFeeTitle4() {
		return StringHandler.format(feeTitle4);
	}
	public void setFeeTitle4(String feeTitle4) {
		this.feeTitle4 = feeTitle4;
	}
	public String getFee4() {
		return StringHandler.format(fee4);
	}
	public void setFee4(String fee4) {
		this.fee4 = fee4;
	}
	public String getSendDate1() {
		return StringHandler.format(sendDate1);
	}
	public void setSendDate1(String sendDate1) {
		this.sendDate1 = sendDate1;
	}
	public String getSendQuantity1() {
		return StringHandler.format(sendQuantity1);
	}
	public void setSendQuantity1(String sendQuantity1) {
		this.sendQuantity1 = sendQuantity1;
	}
	public String getSendDate2() {
		return StringHandler.format(sendDate2);
	}
	public void setSendDate2(String sendDate2) {
		this.sendDate2 = sendDate2;
	}
	public String getSendQuantity2() {
		return StringHandler.format(sendQuantity2);
	}
	public void setSendQuantity2(String sendQuantity2) {
		this.sendQuantity2 = sendQuantity2;
	}
	public String getSendDate3() {
		return StringHandler.format(sendDate3);
	}
	public void setSendDate3(String sendDate3) {
		this.sendDate3 = sendDate3;
	}
	public String getSendQuantity3() {
		return StringHandler.format(sendQuantity3);
	}
	public void setSendQuantity3(String sendQuantity3) {
		this.sendQuantity3 = sendQuantity3;
	}
	public String getSendDate4() {
		return StringHandler.format(sendDate4);
	}
	public void setSendDate4(String sendDate4) {
		this.sendDate4 = sendDate4;
	}
	public String getSendQuantity4() {
		return StringHandler.format(sendQuantity4);
	}
	public void setSendQuantity4(String sendQuantity4) {
		this.sendQuantity4 = sendQuantity4;
	}
	public String getQuantitySent() {
		return StringHandler.format(quantitySent);
	}
	public void setQuantitySent(String quantitySent) {
		this.quantitySent = quantitySent;
	}
	public String getVersionNo() {
		return StringHandler.format(versionNo);
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getInterTradeOrderId() {
		return StringHandler.format(interTradeOrderId);
	}
	public void setInterTradeOrderId(String interTradeOrderId) {
		this.interTradeOrderId = interTradeOrderId;
	}
	public String getProductionId() {
		return StringHandler.format(productionId);
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getProductionIdVendor() {
		return StringHandler.format(productionIdVendor);
	}
	public void setProductionIdVendor(String productionIdVendor) {
		this.productionIdVendor = productionIdVendor;
	}
	public String getDescriptionC() {
		return StringHandler.format(descriptionC);
	}
	public void setDescriptionC(String descriptionC) {
		this.descriptionC = descriptionC;
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
	public String getBoxAmount() {
		return StringHandler.format(boxAmount);
	}
	public void setBoxAmount(String boxAmount) {
		this.boxAmount = boxAmount;
	}
	public String getQuantity() {
		return StringHandler.format(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitPrice() {
		return StringHandler.format(unitPrice);
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getAmount() {
		return StringHandler.format(amount);
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getComment() {
		return StringHandler.format(comment);
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return StringHandler.format(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
