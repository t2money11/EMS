package com.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.constant.CommonData;
import com.context.ConstantParam;
import com.entity.ClearanceDtl;
import com.entity.InterTradeOrder;
import com.entity.Receipt;
import com.entity.ReceiptDtl;
import com.entity.ReceiptDtl4C;
import com.entity.User;
import com.entity.Vendor;
import com.logic.InterTradeOrderLogic;
import com.logic.VendorLogic;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

public class ReceiptExport {
	
	private VendorLogic vendorLogic = null;
	public void setVendorLogic(VendorLogic vendorLogic) {
		this.vendorLogic = vendorLogic;
	}
	
	private InterTradeOrderLogic interTradeOrderLogic = null;
	public void setInterTradeOrderLogic(InterTradeOrderLogic interTradeOrderLogic) {
		this.interTradeOrderLogic = interTradeOrderLogic;
	}
	
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
	
	private void copyList(Receipt receipt){
		
		List<ReceiptDtl> receiptDtlList = receipt.getReceiptDtlList();
		
		for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
			
			ReceiptDtl receiptDtl = new ReceiptDtl();
			
			receiptDtl.setReceiptId(receiptDtl4C.getReceiptId());
			receiptDtl.setComplaintId(receiptDtl4C.getComplaintId());
			receiptDtl.setTradeOrderId(receiptDtl4C.getTradeOrderId());
			receiptDtl.setProductionId(receiptDtl4C.getProductionId());
			receiptDtl.setVersionNo(receiptDtl4C.getVersionNo());
			receiptDtl.setFeeNum("0");
			receiptDtl.setQuantity(receiptDtl4C.getQuantity());
			receiptDtl.setPo(receiptDtl4C.getPo());
			receiptDtl.setContractNo(receiptDtl4C.getContractNo());
			receiptDtl.setTradeOrderCreateDate(receiptDtl4C.getTradeOrderCreateDate());
			receiptDtl.setDescriptionE(receiptDtl4C.getDescriptionE());
			receiptDtl.setHscode(receiptDtl4C.getHscode());
			receiptDtl.setProductionEName4Export(receiptDtl4C.getProductionEName4Export());
			receiptDtl.setProductionCName4Export(receiptDtl4C.getProductionCName4Export());
			receiptDtl.setVendorId(receiptDtl4C.getVendorId());
			receiptDtl.setVendorName(receiptDtl4C.getVendorName());
			receiptDtl.setShortLocation(receiptDtl4C.getShortLocation());
			receiptDtl.settUnitPrice(receiptDtl4C.gettUnitPrice());
			receiptDtl.settQuantity(receiptDtl4C.gettQuantity());;
			receiptDtl.settQuantityNotSent(receiptDtl4C.gettQuantityNotSent());
			receiptDtl.settAmount(receiptDtl4C.gettAmount());
			receiptDtl.setiUnitPrice(receiptDtl4C.getiUnitPrice());
			receiptDtl.setiQuantity(receiptDtl4C.gettQuantity());
			receiptDtl.setiQuantityNotSent(receiptDtl4C.gettQuantityNotSent());
			receiptDtl.setiAmount(receiptDtl4C.getiAmount());
			receiptDtl.setcAmount(receiptDtl4C.getcAmount());;
			receiptDtl.setVolume(receiptDtl4C.getVolume());
			receiptDtl.setGrossWeight(receiptDtl4C.getGrossWeight());
			receiptDtl.setNetWeight(receiptDtl4C.getNetWeight());
			receiptDtl.setInside(receiptDtl4C.getInside());
			receiptDtl.setOutside(receiptDtl4C.getOutside());
			receiptDtl.setVolumeTtl(receiptDtl4C.getVolumeTtl());
			receiptDtl.setGrossWeightTtl(receiptDtl4C.getGrossWeightTtl());
			receiptDtl.setNetWeightTtl(receiptDtl4C.getNetWeightTtl());
			receiptDtl.setBrand(receiptDtl4C.getBrand());
			receiptDtl.setPurpose(receiptDtl4C.getPurpose());
			receiptDtl.setMaterial(receiptDtl4C.getMaterial());
			receiptDtl.setKind(receiptDtl4C.getKind());
			receiptDtl.setKindName(receiptDtl4C.getKindName());
			receiptDtl.setBoxAmount(receiptDtl4C.getBoxAmount());
			
			receiptDtlList.add(receiptDtl);
		}
	}
	
    public String generateFile(Receipt receipt, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// ?????????Excel??????
    		input = new FileInputStream("C:/excelTemp/receiptTemp.xlsx");
    		wb = new XSSFWorkbook(input);
    		
    		//???????????????????????????
    		if(receipt.getReceiptDtlList().size() > 0){
    			
    			setSheet10(receipt, wb);
    		}
    		
    		copyList(receipt);
            
            // ?????????
            setSheet0(receipt, wb);
            
            // CI??????
            setSheet6(receipt, wb);
            
            // PL??????
            setSheet7(receipt, wb);
            
            if("1".equals(receipt.getReceiptMode())){
            	
            	// ?????????
                setSheet1(receipt, wb);
                
                // ??????????????????
                setSheet2(receipt, wb);
                
                // CI??????
                setSheet3(receipt, wb);
                
                // PL??????
                setSheet4(receipt, wb);
                
                // ????????????
                setSheet5(receipt, wb);
                
                // ??????
                setSheet8(receipt, wb);
                
                // FormA
                if("002".equals(receipt.getCustomerId())){
                    setSheet9(receipt, wb);
                }else{
                	wb.removeSheetAt(9);
                }
                
            }else{
            	
            	wb.removeSheetAt(9);
            	wb.removeSheetAt(8);
            	wb.removeSheetAt(5);
            	wb.removeSheetAt(4);
            	wb.removeSheetAt(3);
            	wb.removeSheetAt(2);
            	wb.removeSheetAt(1);
            }
            
            // ?????????????????????
            fileFullPath = "C:/excelDownloadTemp/receipt/" + fileName;
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
    
    private void setSheet0(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(0);
        
        //?????????
        int row = 0;
        
        // Title
        String title = "%s %s %s";
        title = String.format(title, receipt.getCustomerName(),
        		DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT7, Locale.ENGLISH),
        		receipt.getReceiptNo());
    	CommonExport.writeInTemplate(sheet, title, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	//?????????
    	row++;
    	
        for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(i >= 2){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				
				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
				int startRow = row;
				sourceRow = sheet.getRow(3);
				targetRow = sheet.createRow(startRow);
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
        	
        	int col = 0;
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		//Contract No.
        		CommonExport.writeInTemplate(sheet, receiptDtl.getContractNo(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_TITLE));
        		//P.O
        		CommonExport.writeInTemplate(sheet, receiptDtl.getPo(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_TITLE));
        		
        		//?????????kg
        		CommonExport.writeInTemplate(sheet, null, row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
        		//?????????kg
        		CommonExport.writeInTemplate(sheet, null, row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
        		//?????????M3
        		CommonExport.writeInTemplate(sheet, null, row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
        	}else{
        		
        		boolean hasDiscount = false;
                if(!IsEmptyUtil.isEmpty(receiptDtl.getAdvancePayment())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDate())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDiscountRate())){
                	
                	hasDiscount = true;
                }
        		
        		if(!"0".equals(receiptDtl.getFeeNum())){
        			
        			//????????????????????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//RMB??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		if(hasDiscount && !IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
            			
            			String temp = String.valueOf(Double.parseDouble(receiptDtl.getiAmount()) * Double.parseDouble(receiptDtl.getAdvancePaymentDiscountRate()) / 100);
            			//RMB?????????
            			CommonExport.writeInTemplate(sheet, temp, row, 6, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_RED));
            		}else{
            			//RMB??????
                		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_NORMAL));
            		}
            		//USD??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		//?????????kg
            		CommonExport.writeInTemplate(sheet, null, row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
            		//?????????kg
            		CommonExport.writeInTemplate(sheet, null, row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
            		//?????????M3
            		CommonExport.writeInTemplate(sheet, null, row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
        		}else{
        			
        			//????????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//????????????????????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//???????????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getVendorName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            		//RMB??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//RMB??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		if(hasDiscount){
                		
                		String temp = String.valueOf(Double.parseDouble(receiptDtl.getiAmount()) * Double.parseDouble(receiptDtl.getAdvancePaymentDiscountRate()) / 100);
                		//RMB?????????
                		CommonExport.writeInTemplate(sheet, temp, row, col++, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_RED));
                	}else{
                		
                		//RMB??????
                		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_NORMAL));
                	}
            		//USD??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//USD??????
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		//?????????
            		col++;
            		
            		//???????????? = ???????????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getOutside(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//????????????kg
            		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//????????????kg
            		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//????????????m3
            		CommonExport.writeInTemplate(sheet, receiptDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//?????????
            		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//?????????kg
            		CommonExport.writeInTemplate(sheet, String.format("L%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
            		//?????????kg
            		CommonExport.writeInTemplate(sheet, String.format("M%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
            		//?????????M3
            		CommonExport.writeInTemplate(sheet, String.format("N%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
        		}
        	}
        	
        	//?????????
        	row++;
		}
        
        //?????????
    	row++;
    	
        //?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(C%d:C%d)", 3, row), row, 2, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//???RMB??????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 3, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    		
		//????????????
		CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 3, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
		
    	//???USD??????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 3, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//????????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(O%d:O%d)", 3, row), row, 14, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//????????????kg
    	CommonExport.writeInTemplate(sheet, String.format("SUM(P%d:P%d)", 3, row), row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//????????????kg
    	CommonExport.writeInTemplate(sheet, String.format("SUM(Q%d:Q%d)", 3, row), row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//????????????M3
    	CommonExport.writeInTemplate(sheet, String.format("SUM(R%d:R%d)", 3, row), row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	List<InterTradeOrder> interTradeOrderList = interTradeOrderLogic.findAdvancePayment(receipt.getReceiptId());
    	if(interTradeOrderList.size() != 0){
    		
    		//??????????????????
	    	row = row + 5;
	    	
	    	for (int i = 0; i < interTradeOrderList.size(); i++) {
				
	    		InterTradeOrder interTradeOrder = interTradeOrderList.get(i);
	    		
	    		if(i >= 1){
	        		
	        		XSSFRow sourceRow = null;
					XSSFRow targetRow = null;
					XSSFCell sourceCell = null;
					XSSFCell targetCell = null;
					
					sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
					int startRow = row;
					sourceRow = sheet.getRow(startRow - 1);
					targetRow = sheet.createRow(startRow);
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
	    		
	    		int col = 0;
	    		
	    		//?????????
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getContractNo(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getVendorName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//????????????
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePaymentDate(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//????????????
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePayment(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//????????????
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePaymentDiscountRate(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		
        		//?????????
        		row++;
			}
    	}
    	
    	
    	if("1".equals(receipt.getReceiptMode())){
    	
    		if(interTradeOrderList.size() == 0){
    			
    			//??????????????????
    	    	row = row + 11;
    		}else{
    			
    			//??????????????????
    	    	row = row + 5;
    		}
	    	
	    	int clearanceDtlStratRow = row;
	    	
	    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
	    		
	    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
	        	
	        	if(i >= 1){
	        		
	        		XSSFRow sourceRow = null;
					XSSFRow targetRow = null;
					XSSFCell sourceCell = null;
					XSSFCell targetCell = null;
					
					sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
					int startRow = row;
					sourceRow = sheet.getRow(startRow - 1);
					targetRow = sheet.createRow(startRow);
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
	        	
	        	int col = 0;
	        	
	        	//???????????????????????????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
	    		//??????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//????????????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//????????????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//??????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//??????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//??????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//??????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//?????????
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmountRMB(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		
	    		//?????????
	        	row++;
			}
	    	
	        //?????????
	    	row++;
	    	
	        //?????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(B%d:B%d)", clearanceDtlStratRow, row), row, 1, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//???????????????
	    	CommonExport.writeInTemplate(sheet, receipt.getAmountTtl4Export(), row, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
	    	
	    	//?????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", clearanceDtlStratRow, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//?????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", clearanceDtlStratRow, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//?????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", clearanceDtlStratRow, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//?????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", clearanceDtlStratRow, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//????????????
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", clearanceDtlStratRow, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
		}
    }
    
    private void setSheet1(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(1);
        
        //?????????
        int row = 0;
        
        // ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getCustomerFullName(), 9, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// ?????????????????????
    	CommonExport.writeInTemplate(sheet, receipt.getCountry(), 13, 3, XSSFCell.CELL_TYPE_STRING, null);
    	
        // ?????????????????????
    	CommonExport.writeInTemplate(sheet, receipt.getCountry(), 13, 8, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 13, 14, XSSFCell.CELL_TYPE_STRING, null);
        
    	// ??????
    	CommonExport.writeInTemplate(sheet, receipt.getBoxAmountTtl(), 15, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// ??????????????????
    	CommonExport.writeInTemplate(sheet, receipt.getGrossWeightTtl(), 15, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// ??????????????????
    	CommonExport.writeInTemplate(sheet, receipt.getNetWeightTtl(), 15, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 15, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 22;
    	
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	if(i >= 5){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				
				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
				int startRow = row;
				sourceRow = sheet.getRow(startRow - 1);
				targetRow = sheet.createRow(startRow);
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
        	
        	int col = 0;
        	
        	//??????
    		CommonExport.writeInTemplate(sheet, String.valueOf(i + 1), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//????????????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getHscode(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		if(ConstantParam.SPECIAL_PRODUCTION_ID.equals(clearanceDtl.getHscode())){
    			
    			CommonExport.writeInTemplate(sheet, String.format("?????????S%02dBB003K???", i + 1), 21, 0, XSSFCell.CELL_TYPE_STRING, null);
    		}
    		//???????????????????????????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export().concat("\r\n").concat(clearanceDtl.getProductionCName4Export()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//????????????
    		col = 5;
    		String quantity = clearanceDtl.getQuantity().concat(" pcs / ").concat(clearanceDtl.getNetWeight()).concat(" ??????");
    		CommonExport.writeInTemplate(sheet, quantity, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//??????/??????/??????
    		col = 8;
    		CommonExport.writeInTemplate(sheet, String.format("%s/%s/USD", clearanceDtl.getUnitPrice(), clearanceDtl.getAmount()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//???????????????
    		col = 10;
    		CommonExport.writeInTemplate(sheet, "??????", row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//???????????????
    		col = 13;
    		CommonExport.writeInTemplate(sheet, receipt.getCountry(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//???????????????
    		col = 17;
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getShortLocation(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//?????????
        	row++;
		}
    }
    
    private void setSheet2(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(2);
        
        //?????????
        int row = 4;
        
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	int col = 0;
        	
        	//title
        	String title = clearanceDtl.getHscode().concat(" ").concat(clearanceDtl.getProductionEName4Export()).concat(" ").concat(clearanceDtl.getProductionCName4Export());
    		CommonExport.writeInTemplate(sheet, title, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//??????
    		String productionName = "1????????????   ".concat(clearanceDtl.getProductionEName4Export().concat(" ").concat(clearanceDtl.getProductionCName4Export()));
    		CommonExport.writeInTemplate(sheet, productionName, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//??????
			List<String> brandList = new ArrayList<String>();
			//??????
			List<String> purposeList = new ArrayList<String>();
			//??????
			List<String> materialList = new ArrayList<String>();
			//??????
			List<String> kindNameList = new ArrayList<String>();
    		for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
    			if(clearanceDtl.getProductionEName4Export().equals(receiptDtl.getProductionEName4Export())){
    				
    				if(!IsEmptyUtil.isEmpty(receiptDtl.getBrand()) && !brandList.contains(receiptDtl.getBrand())){
    					
    					brandList.add(receiptDtl.getBrand());
    				}
    				
					if(!IsEmptyUtil.isEmpty(receiptDtl.getPurpose()) && !purposeList.contains(receiptDtl.getPurpose())){
					    					
						purposeList.add(receiptDtl.getPurpose());
    				}
					
					if(!IsEmptyUtil.isEmpty(receiptDtl.getMaterial()) && !materialList.contains(receiptDtl.getMaterial())){
						
						materialList.add(receiptDtl.getMaterial());
					}
					
					if(!IsEmptyUtil.isEmpty(receiptDtl.getKindName()) && !kindNameList.contains(receiptDtl.getKindName())){
						
						kindNameList.add(receiptDtl.getKindName());
					}
    			}
			}
    		
    		//??????
    		String brand = "2????????????   ";
    		for (String temp : brandList) {
				
    			brand = brand.concat(temp).concat("???");
			}
    		brand = brand.substring(0, brand.length() - 1);
    		CommonExport.writeInTemplate(sheet, brand, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//??????
    		String purpose = "3????????????   ";
    		for (String temp : purposeList) {
				
    			purpose = purpose.concat(temp).concat("???");
			}
    		purpose = purpose.substring(0, purpose.length() - 1);
    		CommonExport.writeInTemplate(sheet, purpose, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//??????
    		String material = "4????????????   ";
    		for (String temp : materialList) {
				
    			material = material.concat(temp).concat("???");
			}
    		material = material.substring(0, material.length() - 1);
    		CommonExport.writeInTemplate(sheet, material, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//??????
    		String kindName = "5????????????   ";
    		for (String temp : kindNameList) {
				
    			kindName = kindName.concat(temp).concat("???");
			}
    		kindName = kindName.substring(0, kindName.length() - 1);
    		CommonExport.writeInTemplate(sheet, kindName, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//????????????
    		String fixedText = "6????????????????????????????????????????????????";
    		CommonExport.writeInTemplate(sheet, fixedText, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//????????????
    		fixedText = "7???????????????????????????";
    		CommonExport.writeInTemplate(sheet, fixedText, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//?????????
        	row++;
		}
    }
    
    private void setSheet3(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(3);
        
        //?????????
        int row = 0;
        
        // ?????????
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	String receiptDate = DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 12, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ?????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 12, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 18, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 20, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// P.O.
    	CommonExport.writeInTemplate(sheet, "P.O. ".concat(po), 20, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 21;
    	int size = receipt.getClearanceDtlList().size();
    	
    	for (int i = 0; i < size; i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	if(i >= 20){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				
				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
				int startRow = row;
				sourceRow = sheet.getRow(startRow - 1);
				targetRow = sheet.createRow(startRow);
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
        	
        	int col = 1;
        	//????????????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col, XSSFCell.CELL_TYPE_STRING, null);
    		col = 5;
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//?????????
        	row++;
		}
    	
    	//?????????
    	row++;
    	
    	if(size <= 20){
        	
        	//?????????????????????
        	row = 42;
        }
    	
        //?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 22, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet4(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(4);
        
        //?????????
        int row = 0;
        
        // ?????????
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	String receiptDate = DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 15, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// P.O.
    	CommonExport.writeInTemplate(sheet, "P.O. ".concat(po), 15, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 16;
    	int size = receipt.getClearanceDtlList().size();
    	
    	for (int i = 0; i < size; i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	if(i >= 21){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				
				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
				int startRow = row;
				sourceRow = sheet.getRow(startRow - 1);
				targetRow = sheet.createRow(startRow);
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
        	
        	int col = 1;
        	//????????????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col, XSSFCell.CELL_TYPE_STRING, null);
    		col = 3;
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//?????????
        	row++;
		}
    	
    	//?????????
    	row++;
    	
    	if(size <= 21){
        	
        	//?????????????????????
        	row = 38;
        }
    	
        //?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(D%d:D%d)", 17, row), row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", 17, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 17, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 17, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 17, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet5(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(5);
        
        //?????????
        int row = 0;
        
        // ?????????
    	CommonExport.writeInTemplate(sheet, receipt.getCustomerFullName(), 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 10, 1, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 11, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	String receiptDate = DateUtil.stringToString(receipt.getReceiptDtlList().get(0).getTradeOrderCreateDate(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT5, Locale.ENGLISH);
    	CommonExport.writeInTemplate(sheet, receiptDate, 13, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 13, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 17, 8, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 20;
    	int size = receipt.getClearanceDtlList().size();
    	
    	for (int i = 0; i < size; i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	if(i >= 8){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				for(int j = 0; j < 2; j++){
					
					sheet.shiftRows(row + j, sheet.getLastRowNum(), 1, true, false);
					int startRow = row + j;
					sourceRow = sheet.getRow(startRow - 2);
					targetRow = sheet.createRow(startRow);
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
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 4));
				}
        	}
        	
        	//????????????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//?????????
        	row++;
        	//?????????
        	row++;
		}
    	
    	//?????????
    	row++;
    	//?????????
    	row++;
    	
    	if(size <= 8){
        	
        	//?????????????????????
        	row = 38;
        }
    	
        //?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 21, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//?????????????????????
    	row = row + 4;
    	
    	//Shipment
    	String shipment = DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT5, Locale.ENGLISH);
    	CommonExport.writeInTemplate(sheet, shipment, row++, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//Port of Loading
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//Destination
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), row++, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//Freight Term
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), row++, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//Payment
    	CommonExport.writeInTemplate(sheet, receipt.getPaymentTerms(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    }
    
    private void setSheet6(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(6);
        
        //?????????
        int row = 0;
        
        // ?????????
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	String receiptDate = DateUtil.stringToString(receipt.getETD(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 11, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ?????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 11, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 18, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 21, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 21;
    	
    	XSSFRow sourceRow = null;
		XSSFRow targetRow = null;
		XSSFCell sourceCell = null;
		XSSFCell targetCell = null;
    	
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(i == 0){
        		
        		//P.O
        		CommonExport.writeInTemplate(sheet, "P.O. ".concat(receiptDtl.getPo()), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		//?????????
        		row++;    
        		
        		//?????????
        		receiptDtl = receipt.getReceiptDtlList().get(++i);
        		
        		//????????????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//USD??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//USD??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//?????????
        		row++;    
        		
        		//????????????????????????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        	}else{
        		
        		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
            		
        			for(int j = 0; j < 2; j++){
        				
        				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
        				int startRow = row;
        				sourceRow = sheet.getRow(21);
        				targetRow = sheet.createRow(startRow);
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
        				
        				if(j == 0){
        					row++;
        				}
        			}
        			
            		//P.O
            		CommonExport.writeInTemplate(sheet, "P.O. ".concat(receiptDtl.getPo()), row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            	}else{
            		
            		if(!"0".equals(receiptDtl.getFeeNum())){
            			
            			copyRow(sheet, row, 23);
            			
            			//????????????????????????
	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
	            		//USD??????
	            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
	            		
	            		sheet.addMergedRegion(new CellRangeAddress(row, row, 1, 4));
	            		
            		}else{
            			
            			for(int j = 0; j < 2; j++){
        					
        					sheet.shiftRows(row + j, sheet.getLastRowNum(), 1, true, false);
        					int startRow = row + j;
        					
        					if(j == 0){
        						
        						sourceRow = sheet.getRow(22);
        					}else{
        						
        						sourceRow = sheet.getRow(23);
        					}
        					targetRow = sheet.createRow(startRow);
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
        					
        					if(j == 0){
        						
        						//????????????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		//??????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), startRow, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		//USD??????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), startRow, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		//USD??????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), startRow, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 4));
        					}else{
        						
        						//????????????????????????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 4));
        					}
        				}
            			
            			//?????????
                		row++;
            		}
            	}
        	}
        	
        	//?????????
        	row++;
		}
    	
    	//?????????
    	row++;
    	//?????????
    	row++;
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 22, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet7(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(7);
        
        //?????????
        int row = 0;
        
        // ?????????
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	String receiptDate = DateUtil.stringToString(receipt.getETD(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 17, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????????????????
    	row = 17;
    	
    	XSSFRow sourceRow = null;
		XSSFRow targetRow = null;
		XSSFCell sourceCell = null;
		XSSFCell targetCell = null;
    	
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(i == 0){
        		
        		//P.O
        		CommonExport.writeInTemplate(sheet, "P.O. ".concat(receiptDtl.getPo()), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		//?????????
        		row++;    
        		
        		//?????????
        		receiptDtl = receipt.getReceiptDtlList().get(++i);
        		
        		int col = 1;
            	//????????????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, col, XSSFCell.CELL_TYPE_STRING, null);
        		
        		col = 3;
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//??????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getVolumeTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//?????????
        		row++;    
        		
        		//????????????????????????
        		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		
        		//?????????
        		row++;
        	}else{
        		
        		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
            		
        			for(int j = 0; j < 2; j++){
        				
        				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
        				int startRow = row;
        				sourceRow = sheet.getRow(17);
        				targetRow = sheet.createRow(startRow);
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
        				
        				if(j == 0){
        					row++;
        				}
        			}
            		
            		//P.O
            		CommonExport.writeInTemplate(sheet, "P.O. ".concat(receiptDtl.getPo()), row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//?????????
            		row++;
            		
            	}else{
                	
            		if("0".equals(receiptDtl.getFeeNum())){
            			
            			for(int j = 0; j < 2; j++){
        					
        					sheet.shiftRows(row + j, sheet.getLastRowNum(), 1, true, false);
        					int startRow = row + j;
        					
        					if(j == 0){
        						
        						sourceRow = sheet.getRow(18);
        					}else{
        						
        						sourceRow = sheet.getRow(19);
        					}
        					targetRow = sheet.createRow(startRow);
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
        					
        					if(j == 0){
        						
        						int col = 1;
        		            	//????????????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), startRow, col, XSSFCell.CELL_TYPE_STRING, null);
        		        		
        		        		col = 3;
        		        		//??????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//??????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//??????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeightTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//??????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeightTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//??????
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getVolumeTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 2));
        					}else{
        						
        						//????????????????????????
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 2));
        					}
        				}
                		//?????????
                		row++;  
                		//?????????
                		row++;
            		}
            	}
        	}
        	
		}
    	
    	//?????????
    	row++;
    	//?????????
    	row++;
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(D%d:D%d)", 19, row), row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", 19, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 19, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 19, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 19, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet8(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(8);
        
        //??????
    	CommonExport.writeInTemplate(sheet, DateUtil.getNowDateString(DateUtil.DATE_FORMAT6), 1, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 2, 10, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 3, 10, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//???????????????
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 4, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//????????????
    	String productionEName4Export = "";
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
			
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
    		
    		productionEName4Export = productionEName4Export.concat(clearanceDtl.getProductionEName4Export()).concat("/");
    	}
    	productionEName4Export = productionEName4Export.substring(0, productionEName4Export.length() - 1);
    	CommonExport.writeInTemplate(sheet, productionEName4Export, 5, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????
		CommonExport.writeInTemplate(sheet, receipt.getGrossWeightTtl(), 5, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 6, 6), 6, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    	
		//??????
		CommonExport.writeInTemplate(sheet, receipt.getNetWeightTtl(), 5, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 6, 6), 6, 8, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//??????
		CommonExport.writeInTemplate(sheet, receipt.getBoxAmountTtl(), 5, 9, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(J%d:J%d)", 6, 6), 6, 9, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//??????
		CommonExport.writeInTemplate(sheet, receipt.getVolumeTtl(), 5, 10, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(K%d:K%d)", 6, 6), 6, 10, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//????????????
    	CommonExport.writeInTemplate(sheet, receipt.getGoodTime(), 7, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//????????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 8, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//????????????
    	CommonExport.writeInTemplate(sheet, receipt.getTransportation(), 8, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 9, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, receipt.getConsignee(), 14, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, receipt.getContact(), 18, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//??????
    	String comment = "?????????\r\n";
    	User loginUser = CommonData.getCmnData().getLoginInfo();
    	comment = comment.concat(StringHandler.getStr(loginUser.getUserNameE())).concat(": ")
    			.concat(StringHandler.getStr(loginUser.getContact())).concat("/")
    			.concat(StringHandler.getStr(loginUser.getUrgentContact())).concat("/")
    			.concat(StringHandler.getStr(loginUser.getMail()));
    	CommonExport.writeInTemplate(sheet, comment, 29, 1, XSSFCell.CELL_TYPE_STRING, null);
    }
    
    private void setSheet9(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(9);
        
        // ??????
    	CommonExport.writeInTemplate(sheet, "?????????".concat(StringHandler.getStr(receipt.getETD())), 1, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 2, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 3, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ????????????
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 4, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// ??????
    	CommonExport.writeInTemplate(sheet, DateUtil.getNowDateString(DateUtil.DATE_FORMAT1), 5, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//?????????
        int row = 7;
    	
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	if(i >= 1){
        		
        		XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				
				sheet.shiftRows(row, sheet.getLastRowNum(), 1, true, false);
				int startRow = row;
				sourceRow = sheet.getRow(startRow - 1);
				targetRow = sheet.createRow(startRow);
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
        	
        	int col = 0;
        	//????????????
        	String productionName4Export = clearanceDtl.getProductionEName4Export().concat("\r\n").concat(clearanceDtl.getProductionCName4Export());
    		CommonExport.writeInTemplate(sheet, productionName4Export, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 1));
    		//?????????
    		col++;
    		//HS??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getHscode(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????/Kg
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????/Kg
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????/Ctn
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//??????/CBM
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//???????????????
    		List<ReceiptDtl> tempList= new ArrayList<ReceiptDtl>();
    		for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
    			if(clearanceDtl.getExportKey().equals(receiptDtl.getExportKey())){
    				
    				tempList.add(receiptDtl);
    			}
			}
    		
    		Map<String, Double> tempMap = new HashMap<String, Double>();
    		for (ReceiptDtl receiptDtl : tempList) {
				
    			String vendorId = receiptDtl.getVendorId();
    			
    			if(!IsEmptyUtil.isEmpty(receiptDtl.gettAmount())){
    				
    				if(tempMap.containsKey(vendorId)){
    					
    					double tempValue = tempMap.get(vendorId);
    					tempValue = tempValue + Double.parseDouble(receiptDtl.gettAmount());
    					tempMap.put(vendorId, tempValue);
    				}else{
    					
    					tempMap.put(vendorId, Double.parseDouble(receiptDtl.gettAmount()));
    				}
    			}
			}
    		
    		double maxAmount = 0;
    		String vendorId = null;
    		Iterator<Map.Entry<String,Double>> iter = tempMap.entrySet().iterator();
    		while (iter.hasNext()) {
    			
    			Map.Entry<String,Double> entry = iter.next();
    			if(entry.getValue() > maxAmount){
    				
    				maxAmount = entry.getValue();
    				vendorId = entry.getKey();
    			}
			}
    		Vendor vendor = vendorLogic.findByKey(vendorId);
    		String vendorContact = StringHandler.getStr(vendor.getVendorFullName()).concat("\r\n")
    				.concat(StringHandler.getStr(vendor.getContact1())).concat("???").concat(StringHandler.getStr(vendor.getMobile1()))
    				.concat("\r\n?????????????????????").concat(StringHandler.getStr(vendor.getOrcc()));
    		
    		CommonExport.writeInTemplate(sheet, vendorContact, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		sheet.addMergedRegion(new CellRangeAddress(row, row, 9, 10));
    		
    		//?????????
        	row++;
		}
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 8, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 8, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 8, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    	//?????????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 8, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//?????????
    	row++;
    	//?????????
    	row++;
    	//?????????
    	row++;
    	//?????????
    	row++;
    	//?????????
    	row++;
    	
    	//?????????
    	CommonExport.writeInTemplate(sheet, receipt.getConsignee(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    }
    
    private void setSheet10(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	//????????????
    	String checkoutNumber = receipt.getCheckoutNumber();
    	
    	//??????
    	String remint = receipt.getRemint();
    	
    	//??????
    	String postingAmount = receipt.getPostingAmount();
    	
    	//????????????
    	String customerName = receipt.getCustomerName();
    	
    	//?????????
    	String receiptNo = receipt.getReceiptNo();
    	
    	//??????
    	String exRate = receipt.getReceiptDtlList().get(1).getExRate();
    	
    	//????????????
    	//????????????
    	String po = "";
    	//????????????
    	String contractNo = "";
    	//??????????????????
    	List<String> descriptionEList = new ArrayList<String>();
    	//????????????
    	Map<String, BigDecimal> vendorMap = new HashMap<String, BigDecimal>();
    	//??????????????????
    	Map<String, String> tradeOrderMap = new HashMap<String, String>();
    	
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
    		
    		ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
    			//????????????
        		po = po.concat(receiptDtl.getPo()).concat("/");
        		//????????????
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        		//??????????????????
        		if(!tradeOrderMap.containsKey(receiptDtl.getPo())){
        			
        			tradeOrderMap.put(receiptDtl.getPo(), receiptDtl.getTradeOrderCreateDate());
        		}
        	}else{
        		
        		//??????????????????
        		if(!descriptionEList.contains(receiptDtl.getProductionEName4Export())){
        			
        			descriptionEList.add(receiptDtl.getProductionEName4Export());
        		}
        		
        		//????????????
    			BigDecimal sub1 = new BigDecimal(0);
        		BigDecimal sub2 = new BigDecimal(0);
        		if(vendorMap.containsKey(receiptDtl.getVendorName())){
        			
        			sub1 = vendorMap.get(receiptDtl.getVendorName());
        		}
        		
        		if("0".equals(receiptDtl.getFeeNum())){
        			
        			BigDecimal iUnit = new BigDecimal(receiptDtl.getiUnitPrice());
        			BigDecimal sentQuantity = new BigDecimal(receiptDtl.getQuantity());
        			sub2 = iUnit.multiply(sentQuantity);
        		}else{
        			
        			sub2 = new BigDecimal(IsEmptyUtil.isEmpty(receiptDtl.getiAmount()) ? "0" : receiptDtl.getiAmount());
        		}
        		
        		if(!IsEmptyUtil.isEmpty(receiptDtl.getAdvancePayment())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDate())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDiscountRate())){
                	
        			BigDecimal advancePaymentDiscountRate = new BigDecimal(receiptDtl.getAdvancePaymentDiscountRate());
        			sub2 = sub2.multiply(advancePaymentDiscountRate);
                }
    			vendorMap.put(receiptDtl.getVendorName(), sub1.add(sub2));
    		}
    		
    	}
    	//????????????
    	po = po.substring(0, po.length() - 1);
    	//????????????
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	//????????????????????????
    	String receiptDate = receipt.getReceiptDate();
    	//????????????
    	String goodTime = receipt.getGoodTime();
    	//?????????ETD???
    	String etd = receipt.getETD();
    	//??????????????????
    	String postingDate = receipt.getPostingDate();
    	
    	//??????EXCEL
    	XSSFSheet sheet = wb.getSheetAt(10);
        //????????????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(checkoutNumber), 1, 7, XSSFCell.CELL_TYPE_STRING, null);
    	//??????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(remint), 2, 4, XSSFCell.CELL_TYPE_STRING, null);
    	//??????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(postingAmount), 2, 7, XSSFCell.CELL_TYPE_STRING, null);
    	//????????????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(customerName), 3, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//????????????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(po), 4, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//????????????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(contractNo), 5, 3, XSSFCell.CELL_TYPE_STRING, null);
    	
    	int row = 7;
    	for (int i = 0; i < descriptionEList.size(); i++) {
			
    		if(i == 0){
    			
    			CommonExport.writeInTemplate(sheet, StringHandler.getStr(descriptionEList.get(i)), 6, 3, XSSFCell.CELL_TYPE_STRING, null);
    		}else{
    			
    			copyRow(sheet, row, 5);
    			CommonExport.writeInTemplate(sheet, StringHandler.getStr(descriptionEList.get(i)), row, 3, XSSFCell.CELL_TYPE_STRING, null);
    			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 1));
    			sheet.addMergedRegion(new CellRangeAddress(row, row, 3, 7));
    			//?????????
    			row++;
    		}
		}
    	//?????????
    	row++;
    	//?????????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(receiptNo), row++, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//?????????
    	row++;
    	//??????
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(exRate), row++, 7, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//???5???
    	row = row + 5;
    	int startRow = row - 1;
    	Iterator<Map.Entry<String, BigDecimal>> iter1 = vendorMap.entrySet().iterator();
		while (iter1.hasNext()) {
			
			Map.Entry<String, BigDecimal> entry = iter1.next();
			copyRow(sheet, row, row + 1);
			CommonExport.writeInTemplate(sheet, entry.getKey(), row, 3, XSSFCell.CELL_TYPE_STRING, null);
			CommonExport.writeInTemplate(sheet, String.valueOf(entry.getValue()), row, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 3, 6));
			//?????????
			row++;
		}
		sheet.addMergedRegion(new CellRangeAddress(startRow, row + 1, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(startRow, row + 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(row, row, 3, 6));
		//?????????
		row++;
		//??????
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 17, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
		
    	//???4???
    	row = row + 4;
    	Iterator<Map.Entry<String, String>> iter2 = tradeOrderMap.entrySet().iterator();
		while (iter2.hasNext()) {
			
			Map.Entry<String, String> entry = iter2.next();
			copyRow(sheet, row, row + 1);
			CommonExport.writeInTemplate(sheet, entry.getValue(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
			CommonExport.writeInTemplate(sheet, "????????? ( PO: ".concat(entry.getKey()).concat(")"), row, 1, XSSFCell.CELL_TYPE_STRING, null);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 1, 7));
			//?????????
			row++;
		}
		//?????????
		row++;
		//????????????????????????
		CommonExport.writeInTemplate(sheet, receiptDate, row, 0, XSSFCell.CELL_TYPE_STRING, null);
		//???2???
		row = row + 2;
		//????????????
		CommonExport.writeInTemplate(sheet, goodTime, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//?????????ETD???
		CommonExport.writeInTemplate(sheet, etd, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//??????????????????
		CommonExport.writeInTemplate(sheet, postingDate, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//?????????
		row++;
		CommonExport.writeInTemplate(sheet, receipt.getComment(), row++, 1, XSSFCell.CELL_TYPE_STRING, null);
    }
    
    public static void main(String[] args) {
		
//    	ReceiptExport receiptExport = new ReceiptExport();
//    	receiptExport.generateFile(null);
    	
//    	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
//    	System.out.println(sdf.format(new Date()));
    	
    	System.out.println(String.format("K%d*N%d", 1));
	}
}
