package com.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.context.ConstantParam;
import com.entity.InterTradeOrder;
import com.entity.InterTradeOrderDtl;
import com.entity.TradeOrder;
import com.entity.TradeOrderDtl;
import com.util.CurrencyUtil;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

public class TradeOrderExport {
	
	public static final Comparator<Map.Entry<String, String>> comparatorMapKey = new Comparator<Map.Entry<String, String>>(){ 
 		
		@Override
 		public int compare(Entry<String, String> c1, Entry<String, String> c2) { 

			return c1.getKey().compareTo(c2.getKey()); 
		}
 	};
 	
 	private void copyRow(XSSFSheet sheet, int row, int souceRowNum){
 		
 		XSSFRow sourceRow = null;
		XSSFRow targetRow = null;
		XSSFCell sourceCell = null;
		XSSFCell targetCell = null;
			
		sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
		int starRow = row;
		sourceRow = sheet.getRow(souceRowNum);
		targetRow = sheet.createRow(starRow);
		targetRow.setHeight(sourceRow.getHeight());
		short m;
		
		for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
			
			sourceCell = sourceRow.getCell(m);
			targetCell = targetRow.createCell(m);
			
			if(sourceCell != null && targetCell != null){
				
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());
			}
		}
 	}

    public String generateFile(TradeOrder tradeOrder, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
            // ?????????Excel??????????????????
    		input = new FileInputStream("C:/excelTemp/tradeOrderTemp.xlsx");
           
            // ????????????SHEET1
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // Messrs
            StringBuffer messrsStringBuffer = new StringBuffer();
            messrsStringBuffer.append("Messrs: ");
            messrsStringBuffer.append(tradeOrder.getCustomerFullName());
            messrsStringBuffer.append(" ");
            messrsStringBuffer.append(tradeOrder.getLocation());
            String messrs = messrsStringBuffer.toString();
            CommonExport.writeInTemplate(sheet, messrs, 7, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            // Tel
            StringBuffer telStringBuffer = new StringBuffer();
            telStringBuffer.append("Tel:");
            telStringBuffer.append(StringHandler.getStr(tradeOrder.getTel()));
            String tel = telStringBuffer.toString();
            CommonExport.writeInTemplate(sheet, tel, 8, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            // Fax
            StringBuffer faxStringBuffer = new StringBuffer();
            faxStringBuffer.append("Fax:");
            faxStringBuffer.append(StringHandler.getStr(tradeOrder.getFax()));
            String fax = faxStringBuffer.toString();
            CommonExport.writeInTemplate(sheet, fax, 8, 3, XSSFCell.CELL_TYPE_STRING, null);
            
            // Date
            StringBuffer dateStringBuffer = new StringBuffer();
            dateStringBuffer.append("Date: ");
            dateStringBuffer.append(DateUtil.stringToString(tradeOrder.getTradeOrderCreateDate(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT5, Locale.ENGLISH));
            String date = dateStringBuffer.toString();
            CommonExport.writeInTemplate(sheet, date, 9, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            // Contract No.
            StringBuffer contractNoBuffer = new StringBuffer();
            contractNoBuffer.append("Contract No. ");
            contractNoBuffer.append(tradeOrder.getContractNo());
            String contractNo = contractNoBuffer.toString();
            CommonExport.writeInTemplate(sheet, contractNo, 9, 3, XSSFCell.CELL_TYPE_STRING, null);
            
            // P.O
            CommonExport.writeInTemplate(sheet, tradeOrder.getPo(), 9, 8, XSSFCell.CELL_TYPE_STRING, null);
            
            // FOB
            CommonExport.writeInTemplate(sheet, tradeOrder.getFreightTerms(), 12, 8, XSSFCell.CELL_TYPE_STRING, null);
            
            int row = 14;
            int size = tradeOrder.getTradeOrderDtlList().size();
            for (int i = 0; i < size; i++) {
            	
            	TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
            	
            	if(i > 0){
            		
            		copyRow(sheet, row, 14);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
            		
            		copyRow(sheet, row + 1, 15);
            		sheet.addMergedRegion(new CellRangeAddress(row + 1, row + 1, 0, 3));
            	}
            	//????????????
            	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getProductionId(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
            	//??????
            	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getQuantity(), row, 4, XSSFCell.CELL_TYPE_NUMERIC, null);
            	//??????
            	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getUnitPrice() , row, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
            	//??????
            	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getAmount() , row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
            	
            	//?????????
            	row++;
            	
            	//????????????
            	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getDescriptionE(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
            	
            	//?????????
            	row++;
            	
            	if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee1()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle1())){
            		
            		copyRow(sheet, row, 15);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
            		
            		//????????????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFeeTitle1(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFee1() , row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
                	
                	//?????????
                	row++;
            	}
            	if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee2()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle2())){
            		
            		copyRow(sheet, row, 15);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
            		
            		//????????????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFeeTitle2(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFee2() , row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
                	
                	//?????????
                	row++;
            	}
            	if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee3()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle3())){
            		
            		copyRow(sheet, row, 15);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
            		
            		//????????????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFeeTitle3(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFee3() , row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
                	
                	//?????????
                	row++;
            	}
            	if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFee4()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle4())){
            		
            		copyRow(sheet, row, 15);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
            		
            		//????????????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFeeTitle4(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, tradeOrderDtl.getFee4() , row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
                	
                	//?????????
                	row++;
            	}
            }
            
        	//?????????
        	CommonExport.writeInTemplate(sheet, "SUM(I15:I" + row + ")" , row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
        	
        	//?????????
        	row++;
        	row++;
        	
        	//??????????????????
        	if(!IsEmptyUtil.isEmpty(tradeOrder.getAmountTtl())){
            	
            	String amountTtlWord = CurrencyUtil.transferMoneyToWord(tradeOrder.getAmountTtl());
            	amountTtlWord = "Say Total US dollars: ".concat(amountTtlWord).concat(".");
            	CommonExport.writeInTemplate(sheet, amountTtlWord, row, 0, XSSFCell.CELL_TYPE_STRING, null);
            }
        	
        	//?????????????????????
        	row = row + 3;
            
            //Shipment
            String shipment = "";
            if("1".equals(tradeOrder.getSendMode())){
            	
            	shipment = shipment.concat("Before ").concat(DateUtil.stringToString(tradeOrder.getShipment(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT5, Locale.ENGLISH));
            }else{
            	
            	Map<String, String> tempMap = new HashMap<String, String>();
            	for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
					
            		if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate1())){
            			
            			String tempStr = "";
            			if(tempMap.containsKey(tradeOrderDtl.getSendDate1())){
            				
            				tempStr = tempMap.get(tradeOrderDtl.getSendDate1());
            			}
            			tempStr = tempStr.concat(tradeOrderDtl.getSendQuantity1())
        						.concat("pcs ")
        						.concat(tradeOrderDtl.getProductionId())
        						.concat(", ");
            			
            			tempMap.put(tradeOrderDtl.getSendDate1(), tempStr);
            		}
            		
            		if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate2())){
            			
            			String tempStr = "";
            			if(tempMap.containsKey(tradeOrderDtl.getSendDate2())){
            				
            				tempStr = tempMap.get(tradeOrderDtl.getSendDate2());
            			}
            			tempStr = tempStr.concat(tradeOrderDtl.getSendQuantity2())
        						.concat("pcs ")
        						.concat(tradeOrderDtl.getProductionId())
        						.concat(", ");
            			
            			tempMap.put(tradeOrderDtl.getSendDate2(), tempStr);
            		}
            		
            		if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate3())){
            			
            			String tempStr = "";
            			if(tempMap.containsKey(tradeOrderDtl.getSendDate3())){
            				
            				tempStr = tempMap.get(tradeOrderDtl.getSendDate3());
            			}
            			tempStr = tempStr.concat(tradeOrderDtl.getSendQuantity3())
        						.concat("pcs ")
        						.concat(tradeOrderDtl.getProductionId())
        						.concat(", ");
            			
            			tempMap.put(tradeOrderDtl.getSendDate3(), tempStr);
            		}
            		
            		if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate4())){
            			
            			String tempStr = "";
            			if(tempMap.containsKey(tradeOrderDtl.getSendDate4())){
            				
            				tempStr = tempMap.get(tradeOrderDtl.getSendDate4());
            			}
            			tempStr = tempStr.concat(tradeOrderDtl.getSendQuantity4())
        						.concat("pcs ")
        						.concat(tradeOrderDtl.getProductionId())
        						.concat(", ");
            			
            			tempMap.put(tradeOrderDtl.getSendDate4(), tempStr);
            		}
				}
        		for (Map.Entry<String, String> entry : tempMap.entrySet()) {
					
        			tempMap.put(entry.getKey(), entry.getValue().substring(0, entry.getValue().length() - 2)
        														.concat(" before ")
        														.concat(entry.getKey())
        														.concat("\r\n"));
				}
        		
        		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(tempMap.entrySet());
            	Collections.sort(list, comparatorMapKey);
        		for (Entry<String, String> entry : list) {
					
        			shipment = shipment.concat(entry.getValue());
				}
            }
            String shipmentString = "Shipping date (FOB from China): ";
            shipmentString = shipmentString.concat(StringHandler.getStr(shipment));
            CommonExport.writeInTemplate(sheet, shipmentString, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            //Port of Loading
            String portOfLoading = "Port of Loading: ";
            portOfLoading = portOfLoading.concat(StringHandler.getStr(tradeOrder.getPortOfLoading()));
            CommonExport.writeInTemplate(sheet, portOfLoading, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            //Freight Terms
            String freightTerms = "Freight Terms: ";
            freightTerms = freightTerms.concat(StringHandler.getStr(tradeOrder.getFreightTerms()));
            CommonExport.writeInTemplate(sheet, freightTerms, row, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            //Port of Destination
            String portOfDestination = "Port of Destination: ";
            portOfDestination = portOfDestination.concat(StringHandler.getStr(tradeOrder.getPortOfDestination()));
            CommonExport.writeInTemplate(sheet, portOfDestination, row++, 5, XSSFCell.CELL_TYPE_STRING, null);
            
            //Payment Terms
            String paymentTerms = "Payment Terms: ";
            paymentTerms = paymentTerms.concat(StringHandler.getStr(tradeOrder.getPaymentTerms()));
            CommonExport.writeInTemplate(sheet, paymentTerms, row, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            //Packing
            String packing = "Packing: Cartons";
            CommonExport.writeInTemplate(sheet, packing, row++, 5, XSSFCell.CELL_TYPE_STRING, null);
            
            //Sales Contract Text
            String salesContractText = String.format(ConstantParam.SALES_CONTRACT_TEXT, StringHandler.getStr(tradeOrder.getExRate()), StringHandler.getStr(tradeOrder.getEtrRate()));
            CommonExport.writeInTemplate(sheet, salesContractText, row, 0, XSSFCell.CELL_TYPE_STRING, null);
            
            for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
				
            	InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
            	
            	// ????????????SHEET
            	sheet = wb.cloneSheet(1);
            	
                // ???????????????
                String vendorName = "?????????";
                vendorName = vendorName.concat(interTradeOrder.getVendorFullName());
                CommonExport.writeInTemplate(sheet, vendorName, 2, 0, XSSFCell.CELL_TYPE_STRING, null);
                
                //????????????
                CommonExport.writeInTemplate(sheet, interTradeOrder.getInterTradeCreateDate(), 2, 6, XSSFCell.CELL_TYPE_STRING, null);
                
                //????????????
                CommonExport.writeInTemplate(sheet, tradeOrder.getContractNo(), 2, 15, XSSFCell.CELL_TYPE_STRING, null);
                
                //P.O
                String po = "P.O ";
                po = po.concat(StringHandler.getStr(tradeOrder.getPo()));
                CommonExport.writeInTemplate(sheet, po, 3, 5, XSSFCell.CELL_TYPE_STRING, null);
                
                boolean hasDiscount = false;
                if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDiscountRate())){
                	
                	hasDiscount = true;
                }
                
                if(hasDiscount){
                	
                	CommonExport.writeInTemplate(sheet, "??????????????????", 5, 15, XSSFCell.CELL_TYPE_STRING, null);
                }
                
                row = 6;
                size = interTradeOrder.getInterTradeOrderDtlList().size();
                for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
                	
                	InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
                	int col = 0;
                	
                	if(j > 0){
                		
                		copyRow(sheet, row, 6);
                	}
                	
                	//????????????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getProductionId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                	//?????????????????????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getProductionIdVendor(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                	//????????????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getDescriptionC(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getInside(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getOutside(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getVolumeTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????(kg)
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getGrossWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????(kg)
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getNetWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????(???)
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//??????(??????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	//?????????
                	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	
                	if(hasDiscount && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getAmount())){
                		
                		String temp = String.valueOf(Double.parseDouble(interTradeOrderDtl.getAmount()) * Double.parseDouble(interTradeOrder.getAdvancePaymentDiscountRate()) / 100);
                		//?????????
                		CommonExport.writeInTemplate(sheet, temp, row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                	}
                	
                	//?????????
                	row++;
                	
                }
                for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
                	
                	InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
                	
                	if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee1()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle1())){
                		
                		copyRow(sheet, row, 6);
                		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 13));
                		
                		//????????????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFeeTitle1(), row, 0, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_LEFT));
                    	//??????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFee1() , row, 14, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	
                    	if(hasDiscount){
                    		
                    		String temp = String.valueOf(Double.parseDouble(interTradeOrderDtl.getFee1()) * Double.parseDouble(interTradeOrder.getAdvancePaymentDiscountRate()) / 100);
                    		//?????????
                    		CommonExport.writeInTemplate(sheet, temp, row, 15, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	}
                    	
                    	//?????????
                    	row++;
                	}
                	if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee2()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle2())){
                		
                		copyRow(sheet, row, 6);
                		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 13));
                		
                		//????????????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFeeTitle2(), row, 0, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_LEFT));
                    	//??????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFee2() , row, 14, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	
                    	if(hasDiscount){
                    		
                    		String temp = String.valueOf(Double.parseDouble(interTradeOrderDtl.getFee1()) * Double.parseDouble(interTradeOrder.getAdvancePaymentDiscountRate()) / 100);
                    		//?????????
                    		CommonExport.writeInTemplate(sheet, temp, row, 15, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	}
                    	
                    	//?????????
                    	row++;
                	}
                	if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee3()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle3())){
                		
                		copyRow(sheet, row, 6);
                		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 13));
                		
                		//????????????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFeeTitle3(), row, 0, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_LEFT));
                    	//??????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFee3() , row, 14, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	
                    	if(hasDiscount){
                    		
                    		String temp = String.valueOf(Double.parseDouble(interTradeOrderDtl.getFee1()) * Double.parseDouble(interTradeOrder.getAdvancePaymentDiscountRate()) / 100);
                    		//?????????
                    		CommonExport.writeInTemplate(sheet, temp, row, 15, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	}
                    	
                    	//?????????
                    	row++;
                	}
                	if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee4()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle4())){
                		
                		copyRow(sheet, row, 6);
                		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 13));
                		
                		//????????????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFeeTitle4(), row, 0, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_LEFT));
                    	//??????
                    	CommonExport.writeInTemplate(sheet, interTradeOrderDtl.getFee4() , row, 14, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	
                    	if(hasDiscount){
                    		
                    		String temp = String.valueOf(Double.parseDouble(interTradeOrderDtl.getFee1()) * Double.parseDouble(interTradeOrder.getAdvancePaymentDiscountRate()) / 100);
                    		//?????????
                    		CommonExport.writeInTemplate(sheet, temp, row, 15, XSSFCell.CELL_TYPE_NUMERIC, null);
                    	}
                    	
                    	//?????????
                    	row++;
                	}
                }
                
                //?????????
            	CommonExport.writeInTemplate(sheet, "SUM(I7:I" + row + ")" , row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
            	//?????????
            	CommonExport.writeInTemplate(sheet, "SUM(J7:J" + row + ")" , row, 9, XSSFCell.CELL_TYPE_FORMULA, null);
            	//?????????
            	CommonExport.writeInTemplate(sheet, "SUM(K7:K" + row + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	//?????????
            	CommonExport.writeInTemplate(sheet, "SUM(L7:L" + row + ")" , row, 11, XSSFCell.CELL_TYPE_FORMULA, null);
            	//?????????
            	CommonExport.writeInTemplate(sheet, "SUM(M7:M" + row + ")" , row, 12, XSSFCell.CELL_TYPE_FORMULA, null);
                //?????????
            	CommonExport.writeInTemplate(sheet, "SUM(O7:O" + row + ")" , row, 14, XSSFCell.CELL_TYPE_FORMULA, null);
            	
            	if(hasDiscount){
            		
            		//?????????
                	CommonExport.writeInTemplate(sheet, "SUM(P7:P" + row + ")" , row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
            	}
            	
            	//?????????
            	row++;
            	
            	//?????????????????????
            	if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePayment())
            			|| !IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDate())
            			|| !IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDiscountRate())){
            		
            		copyRow(sheet, row, 6);
            		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 14));
					
            		String advancePaymentStr = "";
            		if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDiscountRate())){
            			
            			advancePaymentStr = advancePaymentStr.concat("???????????????")
            					.concat(interTradeOrder.getAdvancePaymentDiscountRate())
            					.concat("%   ");
            		}
            		if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDate())){
            			
            			advancePaymentStr = advancePaymentStr.concat("???????????????")
            					.concat(interTradeOrder.getAdvancePaymentDate())
            					.concat("   ");
            		}
            		if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePayment())){
            			
            			advancePaymentStr = advancePaymentStr.concat("?????????????????")
            					.concat(StringHandler.numFormat(Double.parseDouble(interTradeOrder.getAdvancePayment()), StringHandler.NUMBER_COMMA_FORMAT2))
            					.concat("   ");
            		}
                	CommonExport.writeInTemplate(sheet, advancePaymentStr, row, 0, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_LEFT));
                	
					//????????????
					CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePayment(), row++, 14, XSSFCell.CELL_TYPE_NUMERIC, null);
            	}
            	//??????
            	if(tradeOrder.getCustomerId().equals("001")){
            		
            		CommonExport.writeInTemplate(sheet, "???2018???8???30?????????????????????????????????????????????????????????Manufactured: MM/DD/YY??????MM????????????DD????????????YY????????????????????????2018???8???22???????????????????????????Manufactured: 08/22/18??????\r\n\r\n???2018???11???20???????????????TRIMAX??????????????????????????????????????????Prop65???????????????????????????????????????????????????????????????????????????????????????\r\n\r\n" + (interTradeOrder.getComment() == null ? "" : interTradeOrder.getComment()), row++, 1, XSSFCell.CELL_TYPE_STRING, null);            		
            	}else{
            		
            		CommonExport.writeInTemplate(sheet, interTradeOrder.getComment(), row++, 1, XSSFCell.CELL_TYPE_STRING, null);      
            	}
            	
            	//?????????
            	row++;
            	
            	//????????????
            	String recieveDate = "";
                if("1".equals(interTradeOrder.getSendMode())){
                	
                	recieveDate = "1.?????????????????????%s?????????????????????????????????????????????????????????????????????????????????????????????";
                }else{
                	
                	Map<String, String> tempMap = new HashMap<String, String>();
                	for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
    					
                		if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate1())){
                			
                			String tempStr = "";
                			if(tempMap.containsKey(interTradeOrderDtl.getSendDate1())){
                				
                				tempStr = tempMap.get(interTradeOrderDtl.getSendDate1());
                			}
                			tempStr = tempStr.concat(interTradeOrderDtl.getSendQuantity1())
            						.concat("pcs ")
            						.concat(interTradeOrderDtl.getProductionId())
            						.concat(", ");
                			
                			tempMap.put(interTradeOrderDtl.getSendDate1(), tempStr);
                		}
                		
                		if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate2())){
                			
                			String tempStr = "";
                			if(tempMap.containsKey(interTradeOrderDtl.getSendDate2())){
                				
                				tempStr = tempMap.get(interTradeOrderDtl.getSendDate2());
                			}
                			tempStr = tempStr.concat(interTradeOrderDtl.getSendQuantity2())
            						.concat("pcs ")
            						.concat(interTradeOrderDtl.getProductionId())
            						.concat(", ");
                			
                			tempMap.put(interTradeOrderDtl.getSendDate2(), tempStr);
                		}
                		
                		if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate3())){
                			
                			String tempStr = "";
                			if(tempMap.containsKey(interTradeOrderDtl.getSendDate3())){
                				
                				tempStr = tempMap.get(interTradeOrderDtl.getSendDate3());
                			}
                			tempStr = tempStr.concat(interTradeOrderDtl.getSendQuantity3())
            						.concat("pcs ")
            						.concat(interTradeOrderDtl.getProductionId())
            						.concat(", ");
                			
                			tempMap.put(interTradeOrderDtl.getSendDate3(), tempStr);
                		}
                		
                		if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate4())){
                			
                			String tempStr = "";
                			if(tempMap.containsKey(interTradeOrderDtl.getSendDate4())){
                				
                				tempStr = tempMap.get(interTradeOrderDtl.getSendDate4());
                			}
                			tempStr = tempStr.concat(interTradeOrderDtl.getSendQuantity4())
            						.concat("pcs ")
            						.concat(interTradeOrderDtl.getProductionId())
            						.concat(", ");
                			
                			tempMap.put(interTradeOrderDtl.getSendDate4(), tempStr);
                		}
    				}
            		for (Map.Entry<String, String> entry : tempMap.entrySet()) {
    					
            			tempMap.put(entry.getKey(), entry.getValue().substring(0, entry.getValue().length() - 2)
            														.concat(" before ")
            														.concat(entry.getKey())
            														.concat("\r\n"));
    				}
            		
            		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(tempMap.entrySet());
                	Collections.sort(list, comparatorMapKey);
                	
                	recieveDate = "1.?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\r\n";
            		for (Entry<String, String> entry : list) {
            			
            			recieveDate = recieveDate.concat(entry.getValue());
					}
                }
            	recieveDate = String.format(recieveDate, DateUtil.stringToString(interTradeOrder.getRecieveDate(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT6, Locale.CHINA));
            	CommonExport.writeInTemplate(sheet, recieveDate, row, 0, XSSFCell.CELL_TYPE_STRING, null);
                
                //SHEET?????????
                wb.setSheetName(i + 2, interTradeOrder.getVendorId());
			}
           
            // ????????????SHEET
            wb.removeSheetAt(1);
            
            // ?????????????????????
            fileFullPath = "C:/excelDownloadTemp/tradeOrder/" + fileName;
            output = new FileOutputStream(fileFullPath);
            // ????????????xlsx
            wb.write(output);
            
        } catch (FileNotFoundException e) {
            System.out.println("??????????????????????????????!");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("??????????????????!");
            e.printStackTrace();
        } catch (Exception e) {
        	System.out.println("????????????!");
            e.printStackTrace();
        } finally {
        	
        	try {
        		wb.close();
        		input.close();
            	output.close();
			} catch (Exception e) {
				
				System.out.println("????????????!");
	            e.printStackTrace();
			}
        }
    	return fileFullPath;
        
    }
    
    public static void main(String[] args) {
		
//    	TradeOrderExport tradeOrderExport = new TradeOrderExport();
//    	tradeOrderExport.generateFile(null);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    	System.out.println(sdf.format(new Date()));
	}
}
