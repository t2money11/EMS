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
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/receiptTemp.xlsx");
    		wb = new XSSFWorkbook(input);
    		
    		//外贸合同实施跟踪表
    		if(receipt.getReceiptDtlList().size() > 0){
    			
    			setSheet10(receipt, wb);
    		}
    		
    		copyList(receipt);
            
            // 对账单
            setSheet0(receipt, wb);
            
            // CI清关
            setSheet6(receipt, wb);
            
            // PL清关
            setSheet7(receipt, wb);
            
            if("1".equals(receipt.getReceiptMode())){
            	
            	// 报关单
                setSheet1(receipt, wb);
                
                // 报关申报要素
                setSheet2(receipt, wb);
                
                // CI报关
                setSheet3(receipt, wb);
                
                // PL报关
                setSheet4(receipt, wb);
                
                // 报关合同
                setSheet5(receipt, wb);
                
                // 托书
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
            
            // 创建目标工作簿
            fileFullPath = "C:/excelDownloadTemp/receipt/" + fileName;
            output = new FileOutputStream(fileFullPath);
            // 生成目标xlsx
            wb.write(output);
            
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("文件读写错误!");
            e.printStackTrace();
        } catch (Exception e) {
        	System.out.println("意外错误!");
            e.printStackTrace();
        } finally {
        	
        	try {
        		wb.close();
        		input.close();
            	output.close();
			} catch (Exception e) {
				
				System.out.println("意外错误!");
	            e.printStackTrace();
			}
        }
    	return fileFullPath;
        
    }
    
    private void setSheet0(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(0);
        
        //起始行
        int row = 0;
        
        // Title
        String title = "%s %s %s";
        title = String.format(title, receipt.getCustomerName(),
        		DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT7, Locale.ENGLISH),
        		receipt.getReceiptNo());
    	CommonExport.writeInTemplate(sheet, title, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	//下一行
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
        		
        		//总毛重kg
        		CommonExport.writeInTemplate(sheet, null, row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
        		//总净重kg
        		CommonExport.writeInTemplate(sheet, null, row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
        		//总体积M3
        		CommonExport.writeInTemplate(sheet, null, row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
        	}else{
        		
        		boolean hasDiscount = false;
                if(!IsEmptyUtil.isEmpty(receiptDtl.getAdvancePayment())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDate())
            			&& !IsEmptyUtil.isEmpty(receiptDtl.getAdvancePaymentDiscountRate())){
                	
                	hasDiscount = true;
                }
        		
        		if(!"0".equals(receiptDtl.getFeeNum())){
        			
        			//产品描述（英文）
            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//RMB总价
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		if(hasDiscount && !IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
            			
            			String temp = String.valueOf(Double.parseDouble(receiptDtl.getiAmount()) * Double.parseDouble(receiptDtl.getAdvancePaymentDiscountRate()) / 100);
            			//RMB折扣价
            			CommonExport.writeInTemplate(sheet, temp, row, 6, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_RED));
            		}else{
            			//RMB总价
                		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_NORMAL));
            		}
            		//USD总价
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		//总毛重kg
            		CommonExport.writeInTemplate(sheet, null, row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
            		//总净重kg
            		CommonExport.writeInTemplate(sheet, null, row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
            		//总体积M3
            		CommonExport.writeInTemplate(sheet, null, row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
        		}else{
        			
        			//产品型号
            		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//产品描述（英文）
            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL));
            		//数量
            		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//供应商名称
            		CommonExport.writeInTemplate(sheet, receiptDtl.getVendorName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            		//RMB单价
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//RMB总价
            		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		if(hasDiscount){
                		
                		String temp = String.valueOf(Double.parseDouble(receiptDtl.getiAmount()) * Double.parseDouble(receiptDtl.getAdvancePaymentDiscountRate()) / 100);
                		//RMB折扣价
                		CommonExport.writeInTemplate(sheet, temp, row, col++, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_RED));
                	}else{
                		
                		//RMB总价
                		CommonExport.writeInTemplate(sheet, receiptDtl.getiAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_RECEIPT_DTL_NORMAL));
                	}
            		//USD单价
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//USD总价
            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		
            		//下一列
            		col++;
            		
            		//单箱数量 = 外箱容纳数
            		CommonExport.writeInTemplate(sheet, receiptDtl.getOutside(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//单箱毛重kg
            		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//单箱净重kg
            		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//单箱体积m3
            		CommonExport.writeInTemplate(sheet, receiptDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//箱数量
            		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            		//总毛重kg
            		CommonExport.writeInTemplate(sheet, String.format("L%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
            		//总净重kg
            		CommonExport.writeInTemplate(sheet, String.format("M%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
            		//总体积M3
            		CommonExport.writeInTemplate(sheet, String.format("N%d*O%d", row + 1, row + 1), row, col++, XSSFCell.CELL_TYPE_FORMULA, null);
        		}
        	}
        	
        	//下一行
        	row++;
		}
        
        //下一行
    	row++;
    	
        //总数量
    	CommonExport.writeInTemplate(sheet, String.format("SUM(C%d:C%d)", 3, row), row, 2, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//总RMB总价
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 3, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    		
		//折扣价格
		CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 3, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
		
    	//总USD总价
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 3, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//总箱数量
    	CommonExport.writeInTemplate(sheet, String.format("SUM(O%d:O%d)", 3, row), row, 14, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//总总毛重kg
    	CommonExport.writeInTemplate(sheet, String.format("SUM(P%d:P%d)", 3, row), row, 15, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//总总净重kg
    	CommonExport.writeInTemplate(sheet, String.format("SUM(Q%d:Q%d)", 3, row), row, 16, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//总总体积M3
    	CommonExport.writeInTemplate(sheet, String.format("SUM(R%d:R%d)", 3, row), row, 17, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	List<InterTradeOrder> interTradeOrderList = interTradeOrderLogic.findAdvancePayment(receipt.getReceiptId());
    	if(interTradeOrderList.size() != 0){
    		
    		//重新设定行号
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
	    		
	    		//合同号
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getContractNo(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//工厂
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getVendorName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//预付日期
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePaymentDate(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		//预付金额
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePayment(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//预付折扣
        		CommonExport.writeInTemplate(sheet, interTradeOrder.getAdvancePaymentDiscountRate(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		
        		//下一行
        		row++;
			}
    	}
    	
    	
    	if("1".equals(receipt.getReceiptMode())){
    	
    		if(interTradeOrderList.size() == 0){
    			
    			//重新设定行号
    	    	row = row + 11;
    		}else{
    			
    			//重新设定行号
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
	        	
	        	//产品名称（报关用）
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
	    		//数量
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//报关单价
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//报关总价
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//箱数
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//毛重
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//净重
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//体积
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		//人民币
	    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmountRMB(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
	    		
	    		//下一行
	        	row++;
			}
	    	
	        //下一行
	    	row++;
	    	
	        //总数量
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(B%d:B%d)", clearanceDtlStratRow, row), row, 1, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//总报关总价
	    	CommonExport.writeInTemplate(sheet, receipt.getAmountTtl4Export(), row, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
	    	
	    	//总箱数
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", clearanceDtlStratRow, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//总毛重
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", clearanceDtlStratRow, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//总净重
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", clearanceDtlStratRow, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//总体积
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", clearanceDtlStratRow, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
	    	
	    	//总人民币
	    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", clearanceDtlStratRow, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
		}
    }
    
    private void setSheet1(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(1);
        
        //起始行
        int row = 0;
        
        // 出境关别
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 境外收货人
    	CommonExport.writeInTemplate(sheet, receipt.getCustomerFullName(), 9, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// 贸易国（地区）
    	CommonExport.writeInTemplate(sheet, receipt.getCountry(), 13, 3, XSSFCell.CELL_TYPE_STRING, null);
    	
        // 抵运国（地区）
    	CommonExport.writeInTemplate(sheet, receipt.getCountry(), 13, 8, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 离境口岸
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 13, 14, XSSFCell.CELL_TYPE_STRING, null);
        
    	// 件数
    	CommonExport.writeInTemplate(sheet, receipt.getBoxAmountTtl(), 15, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// 毛重（公斤）
    	CommonExport.writeInTemplate(sheet, receipt.getGrossWeightTtl(), 15, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// 净重（公斤）
    	CommonExport.writeInTemplate(sheet, receipt.getNetWeightTtl(), 15, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
    	
    	// 成交方式
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 15, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        	
        	//项号
    		CommonExport.writeInTemplate(sheet, String.valueOf(i + 1), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//商品编号
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getHscode(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		if(ConstantParam.SPECIAL_PRODUCTION_ID.equals(clearanceDtl.getHscode())){
    			
    			CommonExport.writeInTemplate(sheet, String.format("预归（S%02dBB003K）", i + 1), 21, 0, XSSFCell.CELL_TYPE_STRING, null);
    		}
    		//商品名称、规格型号
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export().concat("\r\n").concat(clearanceDtl.getProductionCName4Export()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//数量单位
    		col = 5;
    		String quantity = clearanceDtl.getQuantity().concat(" pcs / ").concat(clearanceDtl.getNetWeight()).concat(" 千克");
    		CommonExport.writeInTemplate(sheet, quantity, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//单价/总价/币制
    		col = 8;
    		CommonExport.writeInTemplate(sheet, String.format("%s/%s/USD", clearanceDtl.getUnitPrice(), clearanceDtl.getAmount()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//最终目的国
    		col = 10;
    		CommonExport.writeInTemplate(sheet, "中国", row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//最终目的国
    		col = 13;
    		CommonExport.writeInTemplate(sheet, receipt.getCountry(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//境内货源地
    		col = 17;
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getShortLocation(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//下一行
        	row++;
		}
    }
    
    private void setSheet2(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(2);
        
        //起始行
        int row = 4;
        
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
    		
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
        	
        	int col = 0;
        	
        	//title
        	String title = clearanceDtl.getHscode().concat(" ").concat(clearanceDtl.getProductionEName4Export()).concat(" ").concat(clearanceDtl.getProductionCName4Export());
    		CommonExport.writeInTemplate(sheet, title, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//品名
    		String productionName = "1、品名：   ".concat(clearanceDtl.getProductionEName4Export().concat(" ").concat(clearanceDtl.getProductionCName4Export()));
    		CommonExport.writeInTemplate(sheet, productionName, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//品牌
			List<String> brandList = new ArrayList<String>();
			//用途
			List<String> purposeList = new ArrayList<String>();
			//材质
			List<String> materialList = new ArrayList<String>();
			//种类
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
    		
    		//品牌
    		String brand = "2、品牌：   ";
    		for (String temp : brandList) {
				
    			brand = brand.concat(temp).concat("、");
			}
    		brand = brand.substring(0, brand.length() - 1);
    		CommonExport.writeInTemplate(sheet, brand, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//用途
    		String purpose = "3、用途：   ";
    		for (String temp : purposeList) {
				
    			purpose = purpose.concat(temp).concat("、");
			}
    		purpose = purpose.substring(0, purpose.length() - 1);
    		CommonExport.writeInTemplate(sheet, purpose, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//材质
    		String material = "4、材质：   ";
    		for (String temp : materialList) {
				
    			material = material.concat(temp).concat("、");
			}
    		material = material.substring(0, material.length() - 1);
    		CommonExport.writeInTemplate(sheet, material, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//种类
    		String kindName = "5、种类：   ";
    		for (String temp : kindNameList) {
				
    			kindName = kindName.concat(temp).concat("、");
			}
    		kindName = kindName.substring(0, kindName.length() - 1);
    		CommonExport.writeInTemplate(sheet, kindName, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//固定文字
    		String fixedText = "6、品牌类型：境外品牌（贴牌生产）";
    		CommonExport.writeInTemplate(sheet, fixedText, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//固定文字
    		fixedText = "7、享惠情况：不确定";
    		CommonExport.writeInTemplate(sheet, fixedText, row++, col, XSSFCell.CELL_TYPE_STRING, null);
    		
    		//下一行
        	row++;
		}
    }
    
    private void setSheet3(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(3);
        
        //起始行
        int row = 0;
        
        // 客户名
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 客户地址
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	String receiptDate = DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 装船口岸
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 12, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 目的地
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 12, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 成交方式
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 18, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 唛头及编号
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 20, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// P.O.
    	CommonExport.writeInTemplate(sheet, "P.O. ".concat(po), 20, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        	//货品名称
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col, XSSFCell.CELL_TYPE_STRING, null);
    		col = 5;
    		//数量
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//单价
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//总值
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//下一行
        	row++;
		}
    	
    	//下一行
    	row++;
    	
    	if(size <= 20){
        	
        	//重新设定起始行
        	row = 42;
        }
    	
        //总总值
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 22, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet4(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(4);
        
        //起始行
        int row = 0;
        
        // 客户名
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 客户地址
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	String receiptDate = DateUtil.stringToString(receipt.getGoodTime(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 唛头及编号
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 15, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// P.O.
    	CommonExport.writeInTemplate(sheet, "P.O. ".concat(po), 15, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        	//货品名称
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, col, XSSFCell.CELL_TYPE_STRING, null);
    		col = 3;
    		//数量
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//箱数
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//毛重
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//净重
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//体积
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//下一行
        	row++;
		}
    	
    	//下一行
    	row++;
    	
    	if(size <= 21){
        	
        	//重新设定起始行
        	row = 38;
        }
    	
        //总数量
    	CommonExport.writeInTemplate(sheet, String.format("SUM(D%d:D%d)", 17, row), row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总箱数
    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", 17, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总毛重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 17, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总净重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 17, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总体积
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 17, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet5(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(5);
        
        //起始行
        int row = 0;
        
        // 客户名
    	CommonExport.writeInTemplate(sheet, receipt.getCustomerFullName(), 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 客户地址
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 10, 1, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 11, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	String receiptDate = DateUtil.stringToString(receipt.getReceiptDtlList().get(0).getTradeOrderCreateDate(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT5, Locale.ENGLISH);
    	CommonExport.writeInTemplate(sheet, receiptDate, 13, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 13, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 成交方式
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 17, 8, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        	
        	//货品名称
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getProductionEName4Export(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    		//数量
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getQuantity(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//单价
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//总值
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
    		
    		//下一行
        	row++;
        	//下一行
        	row++;
		}
    	
    	//下一行
    	row++;
    	//下一行
    	row++;
    	
    	if(size <= 8){
        	
        	//重新设定起始行
        	row = 38;
        }
    	
        //总数量
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 21, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//重新设定起始行
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
        
        //起始行
        int row = 0;
        
        // 客户名
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 客户地址
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	String receiptDate = DateUtil.stringToString(receipt.getETD(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 装船口岸
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 11, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 目的地
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 11, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 成交方式
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 18, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 唛头及编号
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 21, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        		//下一行
        		row++;    
        		
        		//下一项
        		receiptDtl = receipt.getReceiptDtlList().get(++i);
        		
        		//产品型号
        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		//数量
        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//USD单价
        		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), row, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//USD总价
        		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), row, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//下一行
        		row++;    
        		
        		//产品描述（英文）
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
            			
            			//产品描述（英文）
	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
	            		//USD总价
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
        						
        						//产品型号
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		//数量
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), startRow, 5, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		//USD单价
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.gettUnitPrice(), startRow, 6, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		//USD总价
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.gettAmount(), startRow, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 4));
        					}else{
        						
        						//产品描述（英文）
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 4));
        					}
        				}
            			
            			//下一行
                		row++;
            		}
            	}
        	}
        	
        	//下一行
        	row++;
		}
    	
    	//下一行
    	row++;
    	//下一行
    	row++;
    	
    	//总总值
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 22, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet7(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(7);
        
        //起始行
        int row = 0;
        
        // 客户名
    	CommonExport.writeInTemplate(sheet, "TO:  ".concat(StringHandler.getStr(receipt.getCustomerFullName())), 5, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 客户地址
    	CommonExport.writeInTemplate(sheet, receipt.getLocation(), 6, 0, XSSFCell.CELL_TYPE_STRING, null);
        
    	// Tel
    	CommonExport.writeInTemplate(sheet, "Tel:".concat(StringHandler.getStr(receipt.getTel())), 8, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// Fax
    	CommonExport.writeInTemplate(sheet, "Fax:".concat(StringHandler.getStr(receipt.getFax())), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 5, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	String receiptDate = DateUtil.stringToString(receipt.getETD(), DateUtil.DATE_FORMAT1, DateUtil.DATE_FORMAT8, Locale.CHINA);
    	CommonExport.writeInTemplate(sheet, receiptDate, 7, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 9, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 9, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 唛头及编号
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 17, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//重新设置行号
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
        		//下一行
        		row++;    
        		
        		//下一项
        		receiptDtl = receipt.getReceiptDtlList().get(++i);
        		
        		int col = 1;
            	//货品名称
        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), row, col, XSSFCell.CELL_TYPE_STRING, null);
        		
        		col = 3;
        		//数量
        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//箱数
        		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//毛重
        		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//净重
        		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeightTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//体积
        		CommonExport.writeInTemplate(sheet, receiptDtl.getVolumeTtl(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		//下一行
        		row++;    
        		
        		//产品描述（英文）
        		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		
        		//下一行
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
            		
            		//下一行
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
        		            	//货品名称
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getProductionId(), startRow, col, XSSFCell.CELL_TYPE_STRING, null);
        		        		
        		        		col = 3;
        		        		//数量
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getQuantity(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//箱数
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getBoxAmount(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//毛重
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getGrossWeightTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//净重
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getNetWeightTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		        		//体积
        		        		CommonExport.writeInTemplate(sheet, receiptDtl.getVolumeTtl(), startRow, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 2));
        					}else{
        						
        						//产品描述（英文）
        	            		CommonExport.writeInTemplate(sheet, receiptDtl.getDescriptionE(), startRow, 1, XSSFCell.CELL_TYPE_STRING, null);
        	            		
        	            		sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 1, 2));
        					}
        				}
                		//下一行
                		row++;  
                		//下一行
                		row++;
            		}
            	}
        	}
        	
		}
    	
    	//下一行
    	row++;
    	//下一行
    	row++;
    	
    	//总数量
    	CommonExport.writeInTemplate(sheet, String.format("SUM(D%d:D%d)", 19, row), row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总箱数
    	CommonExport.writeInTemplate(sheet, String.format("SUM(E%d:E%d)", 19, row), row, 4, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总毛重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 19, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总净重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 19, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总体积
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 19, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    }
    
    private void setSheet8(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(8);
        
        //日期
    	CommonExport.writeInTemplate(sheet, DateUtil.getNowDateString(DateUtil.DATE_FORMAT6), 1, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 2, 10, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 3, 10, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//唛头及编号
    	CommonExport.writeInTemplate(sheet, receipt.getMark(), 4, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//货物品名
    	String productionEName4Export = "";
    	for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
			
    		ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
    		
    		productionEName4Export = productionEName4Export.concat(clearanceDtl.getProductionEName4Export()).concat("/");
    	}
    	productionEName4Export = productionEName4Export.substring(0, productionEName4Export.length() - 1);
    	CommonExport.writeInTemplate(sheet, productionEName4Export, 5, 5, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//毛重
		CommonExport.writeInTemplate(sheet, receipt.getGrossWeightTtl(), 5, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 6, 6), 6, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    	
		//净重
		CommonExport.writeInTemplate(sheet, receipt.getNetWeightTtl(), 5, 8, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 6, 6), 6, 8, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//箱数
		CommonExport.writeInTemplate(sheet, receipt.getBoxAmountTtl(), 5, 9, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(J%d:J%d)", 6, 6), 6, 9, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//体积
		CommonExport.writeInTemplate(sheet, receipt.getVolumeTtl(), 5, 10, XSSFCell.CELL_TYPE_NUMERIC, null);
		CommonExport.writeInTemplate(sheet, String.format("SUM(K%d:K%d)", 6, 6), 6, 10, XSSFCell.CELL_TYPE_FORMULA, null);
		
		//货好日期
    	CommonExport.writeInTemplate(sheet, receipt.getGoodTime(), 7, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//装货港口
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfLoading(), 8, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//目的港
    	CommonExport.writeInTemplate(sheet, receipt.getPortOfDestination(), 8, 6, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//运输方式
    	CommonExport.writeInTemplate(sheet, receipt.getTransportation(), 8, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//运费
    	CommonExport.writeInTemplate(sheet, receipt.getFreightTerms(), 9, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//抬头人
    	CommonExport.writeInTemplate(sheet, receipt.getConsignee(), 14, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//通知人
    	CommonExport.writeInTemplate(sheet, receipt.getContact(), 18, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//备注
    	String comment = "联系人\r\n";
    	User loginUser = CommonData.getCmnData().getLoginInfo();
    	comment = comment.concat(StringHandler.getStr(loginUser.getUserNameE())).concat(": ")
    			.concat(StringHandler.getStr(loginUser.getContact())).concat("/")
    			.concat(StringHandler.getStr(loginUser.getUrgentContact())).concat("/")
    			.concat(StringHandler.getStr(loginUser.getMail()));
    	CommonExport.writeInTemplate(sheet, comment, 29, 1, XSSFCell.CELL_TYPE_STRING, null);
    }
    
    private void setSheet9(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	XSSFSheet sheet = wb.getSheetAt(9);
        
        // 船期
    	CommonExport.writeInTemplate(sheet, "船期：".concat(StringHandler.getStr(receipt.getETD())), 1, 0, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 订单号码
    	String po = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		po = po.concat(receiptDtl.getPo()).concat("/");
        	}
    	}
    	po = po.substring(0, po.length() - 1);
    	CommonExport.writeInTemplate(sheet, po, 2, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 合同号码
    	String contractNo = "";
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
        	ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
        	
        	if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        	}
    	}
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	CommonExport.writeInTemplate(sheet, contractNo, 3, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 发票号码
    	CommonExport.writeInTemplate(sheet, receipt.getReceiptNo(), 4, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	// 日期
    	CommonExport.writeInTemplate(sheet, DateUtil.getNowDateString(DateUtil.DATE_FORMAT1), 5, 9, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//起始行
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
        	//货品名称
        	String productionName4Export = clearanceDtl.getProductionEName4Export().concat("\r\n").concat(clearanceDtl.getProductionCName4Export());
    		CommonExport.writeInTemplate(sheet, productionName4Export, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 1));
    		//下一列
    		col++;
    		//HS编码
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getHscode(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		//单价
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//金额
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//毛重/Kg
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getGrossWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//净重/Kg
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getNetWeight(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//箱数/Ctn
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getBoxAmount(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//体积/CBM
    		CommonExport.writeInTemplate(sheet, clearanceDtl.getVolume(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
    		//工厂联系人
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
    				.concat(StringHandler.getStr(vendor.getContact1())).concat("：").concat(StringHandler.getStr(vendor.getMobile1()))
    				.concat("\r\n组织机构代码：").concat(StringHandler.getStr(vendor.getOrcc()));
    		
    		CommonExport.writeInTemplate(sheet, vendorContact, row, col++, XSSFCell.CELL_TYPE_STRING, null);
    		sheet.addMergedRegion(new CellRangeAddress(row, row, 9, 10));
    		
    		//下一行
        	row++;
		}
    	
    	//总毛重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(F%d:F%d)", 8, row), row, 5, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总净重
    	CommonExport.writeInTemplate(sheet, String.format("SUM(G%d:G%d)", 8, row), row, 6, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总箱数
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 8, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
    	//总体积
    	CommonExport.writeInTemplate(sheet, String.format("SUM(I%d:I%d)", 8, row), row, 8, XSSFCell.CELL_TYPE_FORMULA, null);
    	
    	//下一行
    	row++;
    	//下一行
    	row++;
    	//下一行
    	row++;
    	//下一行
    	row++;
    	//下一行
    	row++;
    	
    	//抬头人
    	CommonExport.writeInTemplate(sheet, receipt.getConsignee(), row, 1, XSSFCell.CELL_TYPE_STRING, null);
    	
    }
    
    private void setSheet10(Receipt receipt, XSSFWorkbook wb) throws Exception{
    	
    	//结账编号
    	String checkoutNumber = receipt.getCheckoutNumber();
    	
    	//应收
    	String remint = receipt.getRemint();
    	
    	//实收
    	String postingAmount = receipt.getPostingAmount();
    	
    	//客户名称
    	String customerName = receipt.getCustomerName();
    	
    	//发票号
    	String receiptNo = receipt.getReceiptNo();
    	
    	//汇率
    	String exRate = receipt.getReceiptDtlList().get(1).getExRate();
    	
    	//循环获取
    	//订单号码
    	String po = "";
    	//合同号码
    	String contractNo = "";
    	//货物英文描述
    	List<String> descriptionEList = new ArrayList<String>();
    	//工厂小计
    	Map<String, BigDecimal> vendorMap = new HashMap<String, BigDecimal>();
    	//订单创建时间
    	Map<String, String> tradeOrderMap = new HashMap<String, String>();
    	
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
    		
    		ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
        		
    			//订单号码
        		po = po.concat(receiptDtl.getPo()).concat("/");
        		//合同号码
        		contractNo = contractNo.concat(receiptDtl.getContractNo()).concat("/");
        		//订单创建时间
        		if(!tradeOrderMap.containsKey(receiptDtl.getPo())){
        			
        			tradeOrderMap.put(receiptDtl.getPo(), receiptDtl.getTradeOrderCreateDate());
        		}
        	}else{
        		
        		//货物英文描述
        		if(!descriptionEList.contains(receiptDtl.getProductionEName4Export())){
        			
        			descriptionEList.add(receiptDtl.getProductionEName4Export());
        		}
        		
        		//工厂小计
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
    	//订单号码
    	po = po.substring(0, po.length() - 1);
    	//合同号码
    	contractNo = contractNo.substring(0, contractNo.length() - 1);
    	//制作发货单的时间
    	String receiptDate = receipt.getReceiptDate();
    	//货好日期
    	String goodTime = receipt.getGoodTime();
    	//船期（ETD）
    	String etd = receipt.getETD();
    	//实际进账日期
    	String postingDate = receipt.getPostingDate();
    	
    	//编辑EXCEL
    	XSSFSheet sheet = wb.getSheetAt(10);
        //结账编号
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(checkoutNumber), 1, 7, XSSFCell.CELL_TYPE_STRING, null);
    	//应收
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(remint), 2, 4, XSSFCell.CELL_TYPE_STRING, null);
    	//实收
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(postingAmount), 2, 7, XSSFCell.CELL_TYPE_STRING, null);
    	//客户名称
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(customerName), 3, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//订单号码
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(po), 4, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//合同号码
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
    			//下一行
    			row++;
    		}
		}
    	//下一行
    	row++;
    	//发票号
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(receiptNo), row++, 3, XSSFCell.CELL_TYPE_STRING, null);
    	//下一行
    	row++;
    	//汇率
    	CommonExport.writeInTemplate(sheet, StringHandler.getStr(exRate), row++, 7, XSSFCell.CELL_TYPE_STRING, null);
    	
    	//下5行
    	row = row + 5;
    	int startRow = row - 1;
    	Iterator<Map.Entry<String, BigDecimal>> iter1 = vendorMap.entrySet().iterator();
		while (iter1.hasNext()) {
			
			Map.Entry<String, BigDecimal> entry = iter1.next();
			copyRow(sheet, row, row + 1);
			CommonExport.writeInTemplate(sheet, entry.getKey(), row, 3, XSSFCell.CELL_TYPE_STRING, null);
			CommonExport.writeInTemplate(sheet, String.valueOf(entry.getValue()), row, 7, XSSFCell.CELL_TYPE_NUMERIC, null);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 3, 6));
			//下一行
			row++;
		}
		sheet.addMergedRegion(new CellRangeAddress(startRow, row + 1, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(startRow, row + 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(row, row, 3, 6));
		//下一行
		row++;
		//合计
    	CommonExport.writeInTemplate(sheet, String.format("SUM(H%d:H%d)", 17, row), row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
		
    	//下4行
    	row = row + 4;
    	Iterator<Map.Entry<String, String>> iter2 = tradeOrderMap.entrySet().iterator();
		while (iter2.hasNext()) {
			
			Map.Entry<String, String> entry = iter2.next();
			copyRow(sheet, row, row + 1);
			CommonExport.writeInTemplate(sheet, entry.getValue(), row, 0, XSSFCell.CELL_TYPE_STRING, null);
			CommonExport.writeInTemplate(sheet, "下订单 ( PO: ".concat(entry.getKey()).concat(")"), row, 1, XSSFCell.CELL_TYPE_STRING, null);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 1, 7));
			//下一行
			row++;
		}
		//下一行
		row++;
		//制作发货单的时间
		CommonExport.writeInTemplate(sheet, receiptDate, row, 0, XSSFCell.CELL_TYPE_STRING, null);
		//下2行
		row = row + 2;
		//货好日期
		CommonExport.writeInTemplate(sheet, goodTime, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//船期（ETD）
		CommonExport.writeInTemplate(sheet, etd, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//实际进账日期
		CommonExport.writeInTemplate(sheet, postingDate, row++, 0, XSSFCell.CELL_TYPE_STRING, null);
		//下一行
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
