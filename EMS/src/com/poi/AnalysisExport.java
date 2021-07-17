package com.poi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.context.ConstantParam;
import com.entity.AnalysisProfit;
import com.entity.AnalysisResult;
import com.entity.AnalysisResultExportInfo;
import com.entity.Production;
import com.entity.TradeOrderPop;
import com.logic.AnalysisLogic;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

public class AnalysisExport {
	
	private AnalysisLogic analysisLogic = null;
	public void setAnalysisLogic(AnalysisLogic analysisLogic) {
		this.analysisLogic = analysisLogic;
	}

	private void copyRow(XSSFSheet sheet, int row, int souceRowNum){
 		
 		XSSFRow sourceRow = null;
		XSSFRow targetRow = null;
		XSSFCell sourceCell = null;
		XSSFCell targetCell = null;
		
		sheet.shiftRows(row, row + 1, 1, true, false);
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
	
    public String generateFile4ProductionAnalysis(List<TradeOrderPop> tradeOrderPopList, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/productionAnalysisTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // TITLE
            int row = 1;
            int col = 0;
            
            String lastProductionId = null;
            for (int i = 0; i < tradeOrderPopList.size(); i++) {
				
            	TradeOrderPop tradeOrderPop = tradeOrderPopList.get(i);
            	
            	col = 0;
            	
            	if(i == 0 || !lastProductionId.equals(tradeOrderPop.getProductionId())){
            		
            		CommonExport.writeInTemplate(sheet, tradeOrderPop.getProductionId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            		CommonExport.writeInTemplate(sheet, tradeOrderPop.getDescriptionE(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            		CommonExport.writeInTemplate(sheet, tradeOrderPop.getDescriptionC(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	}else{
            		
            		col++;
            		col++;
            		col++;
            	}
            	
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getTradeOrderId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getPo(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getContractNo(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, tradeOrderPop.getCustomerId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, tradeOrderPop.getCustomerName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, tradeOrderPop.getVendorId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, tradeOrderPop.getVendorName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getiUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getiQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.gettUnitPrice(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.gettQuantity(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            	CommonExport.writeInTemplate(sheet, tradeOrderPop.getQuantitySent(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
            	
            	lastProductionId = tradeOrderPop.getProductionId();
            	
            	row++;
			}
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public String generateFile4TradeOrderAnalysis(List<AnalysisResult> analysisResultList, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/tradeOrderAnalysisTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // TITLE
            int row = 1;
            
            String lastTradeOrderId = null;
            String lastProductionId = null;
            String lastVersionNo = null;
            for (int i = 0; i < analysisResultList.size(); i++) {
				
            	AnalysisResult analysisResult = analysisResultList.get(i);
            	
            	int col = 0;
            	
            	if(i == 0 || !lastTradeOrderId.equals(analysisResult.getTradeOrderId()) 
            			|| !lastProductionId.equals(analysisResult.getProductionId())
            			|| !lastVersionNo.equals(analysisResult.getVersionNo())){
            		
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getCustomerName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getTradeOrderCreateDate()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getContractNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getPo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getStatusName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getVendorName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getInterTradeCreateDate()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		if("1".equals(analysisResult.getiSendMode())){
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getRecieveDate()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	}else{
                		String tempStr = "";
                		if(analysisResult.getiSendDate1() != null || analysisResult.getiSendQuantity1() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.getiSendDate1()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.getiSendQuantity1()))
                					.concat("pcs\r\n");
                		}
                		if(analysisResult.getiSendDate2() != null || analysisResult.getiSendQuantity2() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.getiSendDate2()))
	            					.concat(" ")
	            					.concat(StringHandler.getStr(analysisResult.getiSendQuantity2()))
	        						.concat("pcs\r\n");
                		}
                		if(analysisResult.getiSendDate3() != null || analysisResult.getiSendQuantity3() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.getiSendDate3()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.getiSendQuantity3()))
                					.concat("pcs\r\n");
                		}
                		if(analysisResult.getiSendDate4() != null || analysisResult.getiSendQuantity4() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.getiSendDate4()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.getiSendQuantity4()))
                					.concat("pcs");
                		}
                		CommonExport.writeInTemplate(sheet, tempStr, row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	}
                	if("1".equals(analysisResult.gettSendMode())){
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getShipment()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	}else{
                		String tempStr = "";
                		if(analysisResult.gettSendDate1() != null || analysisResult.gettSendQuantity1() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.gettSendDate1()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.gettSendQuantity1()))
                					.concat("pcs\r\n");
                		}
                		if(analysisResult.gettSendDate2() != null || analysisResult.gettSendQuantity2() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.gettSendDate2()))
	            					.concat(" ")
	            					.concat(StringHandler.getStr(analysisResult.gettSendQuantity2()))
	        						.concat("pcs\r\n");
                		}
                		if(analysisResult.gettSendDate3() != null || analysisResult.gettSendQuantity3() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.gettSendDate3()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.gettSendQuantity3()))
                					.concat("pcs\r\n");
                		}
                		if(analysisResult.gettSendDate4() != null || analysisResult.gettSendQuantity4() != null){
                			
                			tempStr = tempStr.concat(StringHandler.getStr8(analysisResult.gettSendDate4()))
                					.concat(" ")
                					.concat(StringHandler.getStr(analysisResult.gettSendQuantity4()))
                					.concat("pcs");
                		}
                		CommonExport.writeInTemplate(sheet, tempStr, row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	}
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getProductionId()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getVersionNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getDescriptionE()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.gettQuantity()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getiQuantity()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	if("T-20160229-002".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-003".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-004".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-005".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-006".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-007".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-008".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-009".equals(analysisResult.getTradeOrderId())
                			|| "T-20160229-010".equals(analysisResult.getTradeOrderId())){
                		
                		CommonExport.writeInTemplate(sheet, "0", row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	}else{
                		
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getQuantityNotSent()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	}
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getReceiptNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getTransportation()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getETD()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getQuantity()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	
                	row++;
            	}else{
            		
            		String receiptNo = sheet.getRow(row - 1).getCell(15).getStringCellValue();
            		String transportation = sheet.getRow(row - 1).getCell(16).getStringCellValue();
            		String etd = sheet.getRow(row - 1).getCell(17).getStringCellValue();
            		String quantity = sheet.getRow(row - 1).getCell(18).getStringCellValue();
            		
            		receiptNo = receiptNo.concat("\r\n").concat(StringHandler.getStr(analysisResult.getReceiptNo()));
            		transportation = transportation.concat("\r\n").concat(StringHandler.getStr(analysisResult.getTransportation()));
            		etd = etd.concat("\r\n").concat(StringHandler.getStr(analysisResult.getETD()));
            		quantity = quantity.concat("\r\n").concat(StringHandler.getStr(analysisResult.getQuantity()));
            		
            		CommonExport.writeInTemplate(sheet, receiptNo, row - 1, 15, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, transportation, row - 1, 17, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, etd, row - 1, 17, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, quantity, row - 1, 18, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
            	}
            	
            	lastTradeOrderId = analysisResult.getTradeOrderId();
            	lastProductionId = analysisResult.getProductionId();
            	lastVersionNo = analysisResult.getVersionNo();
			}
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public String generateFile4ComplaintAnalysis(List<AnalysisResult> analysisResultList, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/complaintAnalysisTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // TITLE
            int row = 1;
            
            String lastComplaintId = null;
            String lastTradeOrderId = null;
            String lastProductionId = null;
            String lastVersionNo = null;
            for (int i = 0; i < analysisResultList.size(); i++) {
				
            	AnalysisResult analysisResult = analysisResultList.get(i);
            	
            	int col = 0;
            	
            	if(i == 0 || !lastComplaintId.equals(analysisResult.getComplaintId())
            			|| !lastTradeOrderId.equals(analysisResult.getTradeOrderId()) 
            			|| !lastProductionId.equals(analysisResult.getProductionId())
            			|| !lastVersionNo.equals(analysisResult.getVersionNo()) ){
            		
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getCustomerName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getComplaintId()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getDealDeadLine()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getContractNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getPo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getStatusName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getVendorName()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getProductionId()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getVersionNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getDescriptionE()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getiQuantity()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getQuantityNotSent()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA));
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getReceiptId()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getReceiptNo()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getTransportation()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getETD()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisResult.getQuantity()), row, col++, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	
                	row++;
            	}else{
            		
            		String receiptId = sheet.getRow(row - 1).getCell(12).getStringCellValue();
            		String receiptNo = sheet.getRow(row - 1).getCell(13).getStringCellValue();
            		String transportation = sheet.getRow(row - 1).getCell(14).getStringCellValue();
            		String etd = sheet.getRow(row - 1).getCell(15).getStringCellValue();
            		String quantity = sheet.getRow(row - 1).getCell(16).getStringCellValue();
            		
            		receiptId = receiptId.concat("\r\n").concat(StringHandler.getStr(analysisResult.getReceiptId()));
            		receiptNo = receiptNo.concat("\r\n").concat(StringHandler.getStr(analysisResult.getReceiptNo()));
            		transportation = transportation.concat("\r\n").concat(StringHandler.getStr(analysisResult.getTransportation()));
            		etd = etd.concat("\r\n").concat(StringHandler.getStr(analysisResult.getETD()));
            		quantity = quantity.concat("\r\n").concat(StringHandler.getStr(analysisResult.getQuantity()));
            		
            		CommonExport.writeInTemplate(sheet, receiptId, row - 1, 12, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
            		CommonExport.writeInTemplate(sheet, receiptNo, row - 1, 13, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, transportation, row - 1, 14, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, etd, row - 1, 15, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
                	CommonExport.writeInTemplate(sheet, quantity, row - 1, 16, XSSFCell.CELL_TYPE_STRING, CommonExport.getCellStyle(wb, CommonExport.CELL_STYLE_TPA_W));
            	}
            	
            	lastComplaintId = analysisResult.getComplaintId();
            	lastTradeOrderId = analysisResult.getTradeOrderId();
            	lastProductionId = analysisResult.getProductionId();
            	lastVersionNo = analysisResult.getVersionNo();
			}
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public String generateFile4ExportInfoAnalysis(String yearSelected, 
    		String monthSelected, 
    		List<AnalysisResultExportInfo> analysisResultExportInfoList, 
    		String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/exportInfoTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // 月份
            int row = 2;
            int col = 0;
            
            CommonExport.writeInTemplate(sheet, yearSelected.concat(StringHandler.getStr(monthSelected)), row++, col, XSSFCell.CELL_TYPE_STRING, null);
            
            // 明细
            for (int i = 0; i < analysisResultExportInfoList.size(); i++) {
            	
            	AnalysisResultExportInfo analysisResultExportInfo = analysisResultExportInfoList.get(i);
            	
            	
            	if(i != 0){
            		
            		copyRow(sheet, row, 3);
            	}
            	
            	List<Production> productionList = analysisLogic.exportInfoAnalysisSub(analysisResultExportInfo.getReceiptId());
            	String productionCName4Export = "";
            	String hscode = "";
            	for (Production production : productionList) {
					
            		if(!IsEmptyUtil.isEmpty(production.getProductionCname4export()) && !productionCName4Export.contains(production.getProductionCname4export().concat(","))){
            			
            			productionCName4Export = productionCName4Export.concat(production.getProductionCname4export()).concat(",");
            		}
            		
            		if(!IsEmptyUtil.isEmpty(production.getHscode()) && !hscode.contains(production.getHscode().concat(","))){
            			
            			hscode = hscode.concat(production.getHscode()).concat(",");
            		}
				}
            	productionCName4Export = productionCName4Export.substring(0, productionCName4Export.length() - 1);
            	hscode = hscode.substring(0, hscode.length() - 1);
            	
            	col = 1;
            		
        		CommonExport.writeInTemplate(sheet, analysisResultExportInfo.getCustomerName(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, analysisResultExportInfo.getReceiptNo(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, analysisResultExportInfo.getAmountTtl4Export(), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
        		CommonExport.writeInTemplate(sheet, analysisResultExportInfo.getPortOfLoading(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, productionCName4Export, row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, hscode, row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	
            	row++;
			}
            
            row++;
            
            // 总金额
        	CommonExport.writeInTemplate(sheet, String.format("SUM(D%d:D%d)", 4, row), row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public String generateFile4ProductionExport(List<Production> productionList, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/productionExportTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            // TITLE
            int row = 1;
            int col = 0;
            
            for (int i = 0; i < productionList.size(); i++) {
				
            	Production production = productionList.get(i);
            	
            	if(i != 0){
            		
            		copyRow(sheet, row, 1);
            	}
            	
            	col = 0;
            	String customerStr = StringHandler.getStr(production.getCustomerId()).concat("  ").concat(StringHandler.getStr(production.getCustomerName()));
        		CommonExport.writeInTemplate(sheet, customerStr, row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, production.getProductionId(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		String productionDespStr = StringHandler.getStr(production.getDescriptionC()).concat("\r\n").concat(StringHandler.getStr(production.getDescriptionE()));
        		CommonExport.writeInTemplate(sheet, productionDespStr, row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, production.getMaterial(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, production.getSize(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
        		CommonExport.writeInTemplate(sheet, production.getSurface(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
            	
        		String Path= ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH + "/" + production.getProductionId() + "/" + production.getVersionNo();
        		File file = new File(Path);
        		if(file.exists()){
        			
//        			XSSFDrawing patri = sheet.createDrawingPatriarch();
//        			XSSFClientAnchor anchor = null;
//        			
//        			File[] tempList = file.listFiles();
//        			for (File temp : tempList) {
//        				
//        				byte[] bytes = IOUtils.toByteArray(new FileInputStream(temp));    
//        				anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 3, 2,(short)5, 4);
//        				patri.createPicture(anchor ,wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG));
//        			}
        			
        			Drawing patri = sheet.createDrawingPatriarch();
        			XSSFClientAnchor anchor = null;
        			File[] tempList = file.listFiles();
        			for (int j = 0; j < tempList.length; j++) {
        				
        				if(tempList.length <= 3){
        					
        					File temp = tempList[j];
        					anchor = new XSSFClientAnchor(0, 0, 0, 0, 6 + j, 1 + i, 7 + j, 2 + i);
        					
        					ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        					BufferedImage bufferImg = ImageIO.read(temp);  
        					
        					String tempFileName = temp.getName();
        					String prefix = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
        					if("png".equals(prefix)){
        						
        						ImageIO.write(bufferImg, "png", byteArrayOut);  
        					}else{
        						
        						ImageIO.write(bufferImg, "jpg", byteArrayOut);  
        					}
        					patri.createPicture(anchor ,wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
        				}
        			}
        		}
        		
            	row++;
			}
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public String generateFile4ProfitAnalysis(List<AnalysisProfit> analysisProfitList, String rate, String fileName) {
        
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	XSSFWorkbook wb = null;
    	String fileFullPath = null;
    	
    	try {
    		
    		//对读取Excel表格
    		input = new FileInputStream("C:/excelTemp/profitAnalysisTemp.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            //用户ID, LISTMAP
			Map<String, List<AnalysisProfit>> userIdMap = new LinkedHashMap<String, List<AnalysisProfit>>();
			List<AnalysisProfit> tempList = null;
			String lastUserId = "";
			for (int i = 0; i < analysisProfitList.size(); i++) {

				AnalysisProfit analysisProfitTemp = analysisProfitList.get(i);

				if (i == 0 || !lastUserId.equals(analysisProfitTemp.getUserId())) {

					tempList = new ArrayList<AnalysisProfit>();
					lastUserId = analysisProfitTemp.getUserId();
				}
				tempList.add(analysisProfitTemp);
				userIdMap.put(analysisProfitTemp.getUserId(), tempList);
			}
            
            List<AnalysisProfit> closedList = null;
            List<AnalysisProfit> unclosedList = null;
            Map<String, List<AnalysisProfit>> closedIMap = null;
            Map<String, List<AnalysisProfit>> unclosedMergedIMap = null;
            Map<String, List<AnalysisProfit>> unclosedTMap = null;
            
            int row = 1;
            lastUserId = "";
            int sheetCount = 0;
            for (Map.Entry<String, List<AnalysisProfit>> entry : userIdMap.entrySet()) {  
              
            	// 每个用户创建一个SHEET
            	if(IsEmptyUtil.isEmpty(lastUserId) || !lastUserId.equals(entry.getKey())){
            		
            		//结账未结账LIST分割
            		List<AnalysisProfit> byUserListFrom = entry.getValue();
            		List<AnalysisProfit> byUserListTo = new ArrayList<AnalysisProfit>();
                	closedList = new ArrayList<AnalysisProfit>();
                	unclosedList = new ArrayList<AnalysisProfit>();
                	for (AnalysisProfit analysisProfitTemp : byUserListFrom) {
                		
                		//如果存在未发送数量的情况，看是否有数据，没的话就添加一条
                    	if("0".equals(analysisProfitTemp.getFeeNum())
                    			&& Integer.parseInt(analysisProfitTemp.getQuantityNotSent()) > 0){
                    		
            				AnalysisProfit analysisProfitTempNew = (AnalysisProfit)analysisProfitTemp.clone();
            				analysisProfitTempNew.setReceiptId("9999");
            				analysisProfitTempNew.setFeeNum(null);
            				analysisProfitTempNew.setPlanPostingDate(null);
            				analysisProfitTempNew.setBalanceDate(null);
            				analysisProfitTempNew.setPostingDate(null);
            				analysisProfitTempNew.setQuantitySent(null);
            					
        					boolean isExist = false;
        					for (AnalysisProfit temp : byUserListTo) {
								
        						if("9999".equals(temp.getReceiptId())
        								&& temp.getInterTradeOrderId().equals(analysisProfitTemp.getInterTradeOrderId())
            							&& temp.getProductionId().equals(analysisProfitTemp.getProductionId())
            							&& temp.getVersionNo().equals(analysisProfitTemp.getVersionNo())){
            						
        							isExist = true;
        							break;
            					}
							}
        					
        					if(isExist == false){
        						byUserListTo.add(analysisProfitTempNew);
        					}
                    	}
                    	
                    	byUserListTo.add(analysisProfitTemp);
                	}
                	
                	for (AnalysisProfit analysisProfitTemp : byUserListTo) {
            			
                		if(IsEmptyUtil.isEmpty(analysisProfitTemp.getPostingDate())
                				|| IsEmptyUtil.isEmpty(analysisProfitTemp.getBalanceDate())){
                			
                			unclosedList.add(analysisProfitTemp);
                		}else{
                			
                			closedList.add(analysisProfitTemp);
                		}
            		}
                	
                    //未结账外贸合同ID, LISTMAP
                	unclosedTMap = new LinkedHashMap<String, List<AnalysisProfit>>();
                    tempList = null;
                    String lastTradeOrderIdTemp = "";
                    for (int j = 0; j < unclosedList.size(); j++) {
                    	
                    	AnalysisProfit analysisProfitTemp = unclosedList.get(j);
                    	
                    	if(j == 0 || !lastTradeOrderIdTemp.equals(analysisProfitTemp.getTradeOrderId())){
                    	
                    		tempList = new ArrayList<AnalysisProfit>();
                    		lastTradeOrderIdTemp = analysisProfitTemp.getTradeOrderId();
                    	}
                    	tempList.add(analysisProfitTemp);
                    	unclosedTMap.put(analysisProfitTemp.getTradeOrderId(), tempList);
                    }
                    
                    //未结账内贸合同ID, LISTMAP
                    unclosedMergedIMap = new LinkedHashMap<String, List<AnalysisProfit>>();
                    tempList = null;
                    String lastInterTradeOrderIdTemp = "";
                    for (int j = 0; j < unclosedList.size(); j++) {
                    	
                    	AnalysisProfit analysisProfitTemp = unclosedList.get(j);
                    	
                    	if(j == 0 || !lastInterTradeOrderIdTemp.equals(analysisProfitTemp.getInterTradeOrderId())){
                    	
                    		tempList = new ArrayList<AnalysisProfit>();
                    		lastInterTradeOrderIdTemp = analysisProfitTemp.getInterTradeOrderId();
                    	}
                    	tempList.add(analysisProfitTemp);
                    	unclosedMergedIMap.put(analysisProfitTemp.getInterTradeOrderId(), tempList);
                    }
                	
                	//结账内贸合同ID, LISTMAP
                	closedIMap = new LinkedHashMap<String, List<AnalysisProfit>>();
                    tempList = null;
                    lastInterTradeOrderIdTemp = "";
                    for (int j = 0; j < closedList.size(); j++) {
                    	
                    	AnalysisProfit analysisProfitTemp = closedList.get(j);
                    	
                    	if(j == 0 || !lastInterTradeOrderIdTemp.equals(analysisProfitTemp.getInterTradeOrderId())){
                    	
                    		tempList = new ArrayList<AnalysisProfit>();
                    		lastInterTradeOrderIdTemp = analysisProfitTemp.getInterTradeOrderId();
                    	}
                    	tempList.add(analysisProfitTemp);
                    	closedIMap.put(analysisProfitTemp.getInterTradeOrderId(), tempList);
                    }
                    
            		//复制sheet
            		sheet = wb.cloneSheet(0);
            		//SHEET重命名
                    wb.setSheetName(sheetCount + 1, entry.getKey());
            		//重置行号
                	row = 3;
                	
                	//设定用户ID
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr(entry.getKey()), 1, 1, XSSFCell.CELL_TYPE_STRING, null);
            		//设定文字已结账
            		CommonExport.writeInTemplate(sheet, StringHandler.getStr("已结账"), 2, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		sheetCount++;
            		
            		lastUserId = entry.getKey();
            	}
            	
            	String lastTradeOrderId = "";
                String lastInterTradeOrderId = "";
            	int rowNum = 1;
            	for(int j = 0; j < closedList.size(); j++){
            		
            		AnalysisProfit analysisProfit = closedList.get(j);
            		
                	//重置列号
                	int col = 0;
                	if(j == 0 || !lastTradeOrderId.equals(analysisProfit.getTradeOrderId())){
                		
                		//行号
                		CommonExport.writeInTemplate(sheet, String.valueOf(rowNum++), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//客户
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getCustomerName()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//合同号码
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getContractNo()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//合同金额(美金)
//                		tempList = closedTMap.get(analysisProfit.getTradeOrderId());
//                		double amountTtl = 0;
//                		for (AnalysisProfit temp : tempList) {
//							
//                			if("0".equals(temp.getFeeNum())){
//                				
//                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
//                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
//                				amountTtl = amountTtl + (quantity * unitPrice);
//                			}else if("1".equals(temp.getFeeNum())){
//                				
//                				double fee1 = IsEmptyUtil.isEmpty(temp.gettFee1()) ? 0 : Double.parseDouble(temp.gettFee1());
//                				amountTtl = amountTtl + fee1;
//                			}else if("2".equals(temp.getFeeNum())){
//                				
//                				double fee2 = IsEmptyUtil.isEmpty(temp.gettFee2()) ? 0 : Double.parseDouble(temp.gettFee2());
//                				amountTtl = amountTtl + fee2;
//                			}else if("3".equals(temp.getFeeNum())){
//                				
//                				double fee3 = IsEmptyUtil.isEmpty(temp.gettFee3()) ? 0 : Double.parseDouble(temp.gettFee3());
//                				amountTtl = amountTtl + fee3;
//                			}else if("4".equals(temp.getFeeNum())){
//                				
//                				double fee4 = IsEmptyUtil.isEmpty(temp.gettFee4()) ? 0 : Double.parseDouble(temp.gettFee4());
//                				amountTtl = amountTtl + fee4;
//                			}
//						}
//                		amountTtl = StringHandler.numFormat4D(amountTtl, StringHandler.NUMBER_FORMAT2);
//                		CommonExport.writeInTemplate(sheet, String.valueOf(amountTtl), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                		col++;
                		//交货日期
                		String tempShipment = "";
                		if("1".equals(analysisProfit.gettSendMode())){
                			
                			tempShipment = analysisProfit.getShipment();
                		}else{
                			
                			if(analysisProfit.gettSendDate1() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate1())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate2() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate2())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate3() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate3())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate4() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate4())).concat(", ");
                			}
                		}
            			if(tempShipment.endsWith(", ")){
                			
                			tempShipment = tempShipment.substring(0, tempShipment.length() - 2);
                		}
                		CommonExport.writeInTemplate(sheet, tempShipment, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//预计收汇日期
                		CommonExport.writeInTemplate(sheet, "-", row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		
                		lastTradeOrderId = analysisProfit.getTradeOrderId();
                	}
                	
                	col = 6;
                	if(j == 0 || !lastInterTradeOrderId.equals(analysisProfit.getInterTradeOrderId())){
                		
                		//合同金额(美金)
                		double amountTtl = 0;
                		tempList = closedIMap.get(analysisProfit.getInterTradeOrderId());
                		for (AnalysisProfit temp : tempList) {
							
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
                				amountTtl = amountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.gettFee1()) ? 0 : Double.parseDouble(temp.gettFee1());
                				amountTtl = amountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.gettFee2()) ? 0 : Double.parseDouble(temp.gettFee2());
                				amountTtl = amountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.gettFee3()) ? 0 : Double.parseDouble(temp.gettFee3());
                				amountTtl = amountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.gettFee4()) ? 0 : Double.parseDouble(temp.gettFee4());
                				amountTtl = amountTtl + fee4;
                			}
						}
                		amountTtl = StringHandler.numFormat4D(amountTtl, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(amountTtl), row, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
                		
                		//工厂
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getVendorName()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//工厂金额
                		amountTtl = 0;
                		for (AnalysisProfit temp : tempList) {
							
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				amountTtl = amountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.getiFee1()) ? 0 : Double.parseDouble(temp.getiFee1());
                				amountTtl = amountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.getiFee2()) ? 0 : Double.parseDouble(temp.getiFee2());
                				amountTtl = amountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.getiFee3()) ? 0 : Double.parseDouble(temp.getiFee3());
                				amountTtl = amountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.getiFee4()) ? 0 : Double.parseDouble(temp.getiFee4());
                				amountTtl = amountTtl + fee4;
                			}
						}
                		if(!IsEmptyUtil.isEmpty(analysisProfit.getAdvancePaymentDiscountRate())){
                			
                			amountTtl = amountTtl * Double.parseDouble(analysisProfit.getAdvancePaymentDiscountRate()) / 100;
                		}
                		amountTtl = StringHandler.numFormat4D(amountTtl, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(amountTtl), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                		
                		//使用汇率
                		CommonExport.writeInTemplate(sheet, rate, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//退税率, 利润
                		String taxReturnRate = "";
                		List<String> taxReturnRateList = new ArrayList<String>();
                		double profit = 0;
                		for (AnalysisProfit temp : tempList) {
                			
                			if(!taxReturnRateList.contains(temp.getTaxReturnRate())){
                				
                				taxReturnRate = taxReturnRate.concat(StringHandler.getStr(temp.getTaxReturnRate())).concat(", ");
                				taxReturnRateList.add(temp.getTaxReturnRate());
                			}
                			
                			//本次买断价
                			double base = 0;
                			if("5.00".equals(temp.getTaxReturnRate())){
                				
                				base = 8.5432;
                			}else if("9.00".equals(temp.getTaxReturnRate())){

                				base = 8.8596;
                			}else if("10.00".equals(temp.getTaxReturnRate())){

                				base = 8.9200;
                			}else if("11.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.0200;
                			}else if("13.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.1800;
                			}else if("15.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.3804;
                			}else if("16.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.4200;
                			}else if("17.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.5684;
                			}
                			double buyoutPrice = Double.valueOf(rate) / 8.2641 * base - 0.010;
                			
                			//外贸合同金额
                    		double tAmountTtl = 0;
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
                				tAmountTtl = tAmountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.gettFee1()) ? 0 : Double.parseDouble(temp.gettFee1());
                				tAmountTtl = tAmountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.gettFee2()) ? 0 : Double.parseDouble(temp.gettFee2());
                				tAmountTtl = tAmountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.gettFee3()) ? 0 : Double.parseDouble(temp.gettFee3());
                				tAmountTtl = tAmountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.gettFee4()) ? 0 : Double.parseDouble(temp.gettFee4());
                				tAmountTtl = tAmountTtl + fee4;
                			}
                    		
                    		//内贸合同金额
                    		double iAmountTtl = 0;
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				iAmountTtl = iAmountTtl + (quantity * unitPrice) / buyoutPrice;
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.getiFee1()) ? 0 : Double.parseDouble(temp.getiFee1());
                				iAmountTtl = iAmountTtl + fee1 / buyoutPrice;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.getiFee2()) ? 0 : Double.parseDouble(temp.getiFee2());
                				iAmountTtl = iAmountTtl + fee2 / buyoutPrice;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.getiFee3()) ? 0 : Double.parseDouble(temp.getiFee3());
                				iAmountTtl = iAmountTtl + fee3 / buyoutPrice;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.getiFee4()) ? 0 : Double.parseDouble(temp.getiFee4());
                				iAmountTtl = iAmountTtl + fee4 / buyoutPrice;
                			}
                    		iAmountTtl = iAmountTtl * Double.valueOf(IsEmptyUtil.isEmpty(temp.getAdvancePaymentDiscountRate()) ? "100" : temp.getAdvancePaymentDiscountRate()) / 100;
                    		//利润
                			profit = profit + (tAmountTtl - iAmountTtl);
    					}
                		//退税率
                		if(taxReturnRate.endsWith(", ")){
                			
                			taxReturnRate = taxReturnRate.substring(0, taxReturnRate.length() - 2);
                		}
                		CommonExport.writeInTemplate(sheet, taxReturnRate, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//利润
                		profit = StringHandler.numFormat4D(profit, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(profit), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                		CommonExport.writeInTemplate(sheet, analysisProfit.getAdvancePayment(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		
                		lastInterTradeOrderId = analysisProfit.getInterTradeOrderId();
                		
                		copyRow(sheet, ++row, 2);
                	}else{
                		
                		continue;
                	}
            		
            	}
            	
            	if(closedList.size() > 0){
            		
            		//小计
            		CommonExport.writeInTemplate(sheet, "小计", row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//合同金额(美金)小计
                	CommonExport.writeInTemplate(sheet, "SUM(D3:D" + row + ")" , row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
                	//工厂金额小计
                	CommonExport.writeInTemplate(sheet, "SUM(H3:H" + row + ")" , row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
                	//利润小计
                	CommonExport.writeInTemplate(sheet, "SUM(K3:K" + row + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	}
            	
            	//下一行
            	copyRow(sheet, ++row, 2);
            	//下一行
            	copyRow(sheet, ++row, 2);
            	//设定文字未结账
        		CommonExport.writeInTemplate(sheet, StringHandler.getStr("未结账"), row, 1, XSSFCell.CELL_TYPE_STRING, null);
        		//下一行
        		copyRow(sheet, ++row, 2);
        		
        		
        		//未结账小计开始行
        		int startRow = row;
        		
            	lastTradeOrderId = "";
                lastInterTradeOrderId = "";
            	for(int j = 0; j < unclosedList.size(); j++){
            		
            		AnalysisProfit analysisProfit = unclosedList.get(j);
            		
                	//重置列号
                	int col = 0;
                	if(j == 0 || !lastTradeOrderId.equals(analysisProfit.getTradeOrderId())){
                		
                		//行号
                		CommonExport.writeInTemplate(sheet, String.valueOf(rowNum++), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//客户
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getCustomerName()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//合同号码
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getContractNo()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//合同金额(美金)
                		col++;
                		//交货日期
                		String tempShipment = "";
                		if("1".equals(analysisProfit.gettSendMode())){
                			
                			tempShipment = analysisProfit.getShipment();
                		}else{
                			
                			if(analysisProfit.gettSendDate1() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate1())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate2() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate2())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate3() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate3())).concat(", ");
                			}
                			if(analysisProfit.gettSendDate4() != null){
                				
                				tempShipment = tempShipment.concat(StringHandler.getStr8(analysisProfit.gettSendDate4())).concat(", ");
                			}
                		}
                		if(tempShipment.endsWith(", ")){
                			
                			tempShipment = tempShipment.substring(0, tempShipment.length() - 2);
                		}
                		CommonExport.writeInTemplate(sheet, tempShipment, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//预计收汇日期
                		tempList = unclosedTMap.get(analysisProfit.getTradeOrderId());
                		List<String> planPostingDateList = new ArrayList<String>();
                		String planPostingDate = "";
                		for (AnalysisProfit temp : tempList) {
                			
                			if(!IsEmptyUtil.isEmpty(temp.getPlanPostingDate())
                					&& !planPostingDateList.contains(temp.getPlanPostingDate())){
                				
                				planPostingDate = planPostingDate.concat(StringHandler.getStr(temp.getPlanPostingDate())).concat(", ");
                				planPostingDateList.add(temp.getPlanPostingDate());
                			}
                		}
                		if(planPostingDate.endsWith(", ")){
                			
                			planPostingDate = planPostingDate.substring(0, planPostingDate.length() - 2);
                		}
                		CommonExport.writeInTemplate(sheet, planPostingDate, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		
                		lastTradeOrderId = analysisProfit.getTradeOrderId();
                	}
                	
                	col = 6;
                	if(j == 0 || !lastInterTradeOrderId.equals(analysisProfit.getInterTradeOrderId())){
                		
                		//合同金额(美金)
                		double amountTtl = 0;
                		tempList = unclosedMergedIMap.get(analysisProfit.getInterTradeOrderId());
                		for (AnalysisProfit temp : tempList) {
							
                			if("0".equals(temp.getFeeNum())){
                				
            					int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
            					double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
            					amountTtl = amountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.gettFee1()) ? 0 : Double.parseDouble(temp.gettFee1());
                				amountTtl = amountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.gettFee2()) ? 0 : Double.parseDouble(temp.gettFee2());
                				amountTtl = amountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.gettFee3()) ? 0 : Double.parseDouble(temp.gettFee3());
                				amountTtl = amountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.gettFee4()) ? 0 : Double.parseDouble(temp.gettFee4());
                				amountTtl = amountTtl + fee4;
                			}else if(IsEmptyUtil.isEmpty(temp.getFeeNum())){
                				
                				int quantity = 0;
                				if(!IsEmptyUtil.isEmpty(temp.getQuantityNotSent())){
                					
                					quantity = Integer.parseInt(temp.getQuantityNotSent());
                				}else{
                					
                					quantity = Integer.parseInt(temp.getiQuantity());
                				}
                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
            					amountTtl = amountTtl + (quantity * unitPrice);
                			}
						}
                		amountTtl = StringHandler.numFormat4D(amountTtl, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(amountTtl), row, 3, XSSFCell.CELL_TYPE_NUMERIC, null);
                		
                		//工厂
                		CommonExport.writeInTemplate(sheet, StringHandler.getStr(analysisProfit.getVendorName()), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//工厂金额
                		amountTtl = 0;
                		for (AnalysisProfit temp : tempList) {
							
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				amountTtl = amountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.getiFee1()) ? 0 : Double.parseDouble(temp.getiFee1());
                				amountTtl = amountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.getiFee2()) ? 0 : Double.parseDouble(temp.getiFee2());
                				amountTtl = amountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.getiFee3()) ? 0 : Double.parseDouble(temp.getiFee3());
                				amountTtl = amountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.getiFee4()) ? 0 : Double.parseDouble(temp.getiFee4());
                				amountTtl = amountTtl + fee4;
                			}else if(IsEmptyUtil.isEmpty(temp.getFeeNum())){
                				
                				int quantity = 0;
                				if(!IsEmptyUtil.isEmpty(temp.getQuantityNotSent())){
                					
                					quantity = Integer.parseInt(temp.getQuantityNotSent());
                				}else{
                					
                					quantity = Integer.parseInt(temp.getiQuantity());
                				}
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				amountTtl = amountTtl + (quantity * unitPrice);
                			}
						}
                		if(!IsEmptyUtil.isEmpty(analysisProfit.getAdvancePaymentDiscountRate())){
                			
                			amountTtl = amountTtl * Double.parseDouble(analysisProfit.getAdvancePaymentDiscountRate()) / 100;
                		}
                		amountTtl = StringHandler.numFormat4D(amountTtl, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(amountTtl), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                		
                		//使用汇率
                		CommonExport.writeInTemplate(sheet, rate, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//退税率, 利润
                		String taxReturnRate = "";
                		List<String> taxReturnRateList = new ArrayList<String>();
                		double profit = 0;
                		for (AnalysisProfit temp : tempList) {
                			
                			if(!taxReturnRateList.contains(temp.getTaxReturnRate())){
                				
                				taxReturnRate = taxReturnRate.concat(StringHandler.getStr(temp.getTaxReturnRate())).concat(", ");
                				taxReturnRateList.add(temp.getTaxReturnRate());
                			}
                			
                			//本次买断价
                			double base = 0;
                			if("5.00".equals(temp.getTaxReturnRate())){
                				
                				base = 8.5432;
                			}else if("9.00".equals(temp.getTaxReturnRate())){

                				base = 8.8596;
                			}else if("10.00".equals(temp.getTaxReturnRate())){

                				base = 8.9200;
                			}else if("11.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.0200;
                			}else if("13.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.1800;
                			}else if("15.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.3804;
                			}else if("16.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.4200;
                			}else if("17.00".equals(temp.getTaxReturnRate())){
                				
                				base = 9.5684;
                			}
                			double buyoutPrice = Double.valueOf(rate) / 8.2641 * base - 0.010;
                			//外贸合同金额
                    		double tAmountTtl = 0;
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
                				tAmountTtl = tAmountTtl + (quantity * unitPrice);
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.gettFee1()) ? 0 : Double.parseDouble(temp.gettFee1());
                				tAmountTtl = tAmountTtl + fee1;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.gettFee2()) ? 0 : Double.parseDouble(temp.gettFee2());
                				tAmountTtl = tAmountTtl + fee2;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.gettFee3()) ? 0 : Double.parseDouble(temp.gettFee3());
                				tAmountTtl = tAmountTtl + fee3;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.gettFee4()) ? 0 : Double.parseDouble(temp.gettFee4());
                				tAmountTtl = tAmountTtl + fee4;
                			}else if(IsEmptyUtil.isEmpty(temp.getFeeNum())){
                				
                				int quantity = 0;
                				if(!IsEmptyUtil.isEmpty(temp.getQuantityNotSent())){
                					
                					quantity = Integer.parseInt(temp.getQuantityNotSent());
                				}else{
                					
                					quantity = Integer.parseInt(temp.getiQuantity());
                				}
                				double unitPrice = IsEmptyUtil.isEmpty(temp.gettUnitPrice()) ? 0 : Double.parseDouble(temp.gettUnitPrice());
                				tAmountTtl = tAmountTtl + (quantity * unitPrice);
                			}
                    		
                    		//内贸合同金额
                    		double iAmountTtl = 0;
                			if("0".equals(temp.getFeeNum())){
                				
                				int quantity = IsEmptyUtil.isEmpty(temp.getQuantitySent()) ? 0 : Integer.parseInt(temp.getQuantitySent());
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				iAmountTtl = iAmountTtl + (quantity * unitPrice) / buyoutPrice;
                			}else if("1".equals(temp.getFeeNum())){
                				
                				double fee1 = IsEmptyUtil.isEmpty(temp.getiFee1()) ? 0 : Double.parseDouble(temp.getiFee1());
                				iAmountTtl = iAmountTtl + fee1 / buyoutPrice;
                			}else if("2".equals(temp.getFeeNum())){
                				
                				double fee2 = IsEmptyUtil.isEmpty(temp.getiFee2()) ? 0 : Double.parseDouble(temp.getiFee2());
                				iAmountTtl = iAmountTtl + fee2 / buyoutPrice;
                			}else if("3".equals(temp.getFeeNum())){
                				
                				double fee3 = IsEmptyUtil.isEmpty(temp.getiFee3()) ? 0 : Double.parseDouble(temp.getiFee3());
                				iAmountTtl = iAmountTtl + fee3 / buyoutPrice;
                			}else if("4".equals(temp.getFeeNum())){
                				
                				double fee4 = IsEmptyUtil.isEmpty(temp.getiFee4()) ? 0 : Double.parseDouble(temp.getiFee4());
                				iAmountTtl = iAmountTtl + fee4 / buyoutPrice;
                			}else if(IsEmptyUtil.isEmpty(temp.getFeeNum())){
                				
                				int quantity = 0;
                				if(!IsEmptyUtil.isEmpty(temp.getQuantityNotSent())){
                					
                					quantity = Integer.parseInt(temp.getQuantityNotSent());
                				}else{
                					
                					quantity = Integer.parseInt(temp.getiQuantity());
                				}
                				double unitPrice = IsEmptyUtil.isEmpty(temp.getiUnitPrice()) ? 0 : Double.parseDouble(temp.getiUnitPrice());
                				iAmountTtl = iAmountTtl + (quantity * unitPrice) / buyoutPrice;
                			}
                    		iAmountTtl = iAmountTtl * Double.valueOf(IsEmptyUtil.isEmpty(temp.getAdvancePaymentDiscountRate()) ? "100" : temp.getAdvancePaymentDiscountRate()) / 100;
                    		//利润
                			profit = profit + (tAmountTtl - iAmountTtl);
    					}
                		//退税率
                		if(taxReturnRate.endsWith(", ")){
                			
                			taxReturnRate = taxReturnRate.substring(0, taxReturnRate.length() - 2);
                		}
                		CommonExport.writeInTemplate(sheet, taxReturnRate, row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		//利润
                		profit = StringHandler.numFormat4D(profit, StringHandler.NUMBER_FORMAT2);
                		CommonExport.writeInTemplate(sheet, String.valueOf(profit), row, col++, XSSFCell.CELL_TYPE_NUMERIC, null);
                		CommonExport.writeInTemplate(sheet, analysisProfit.getAdvancePayment(), row, col++, XSSFCell.CELL_TYPE_STRING, null);
                		
                		lastInterTradeOrderId = analysisProfit.getInterTradeOrderId();
                		
                		copyRow(sheet, ++row, 2);
                	}else{
                		
                		continue;
                	}
            	}
            	
            	if(unclosedList.size() > 0){
            		
            		//小计
            		CommonExport.writeInTemplate(sheet, "小计", row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//合同金额(美金)小计
                	CommonExport.writeInTemplate(sheet, "SUM(D" + startRow + ":D" + row + ")" , row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
                	//工厂金额小计
                	CommonExport.writeInTemplate(sheet, "SUM(H" + startRow + ":H" + row + ")" , row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
                	//利润小计
                	CommonExport.writeInTemplate(sheet, "SUM(K" + startRow + ":K" + row + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	}
            	
            	//下一行
            	copyRow(sheet, ++row, 2);
            	//下一行
            	copyRow(sheet, ++row, 2);
            	
            	if(closedList.size() > 0
            			&& unclosedList.size() > 0){
            		
            		//小计
            		CommonExport.writeInTemplate(sheet, "总计", row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//合同金额(美金)小计
                	CommonExport.writeInTemplate(sheet, "SUM(D" + (startRow - 2) + ",D" + (row - 1) + ")" , row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
                	//工厂金额小计
                	CommonExport.writeInTemplate(sheet, "SUM(H" + (startRow - 2) + ",H" + (row - 1) + ")" , row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
                	//利润小计
                	CommonExport.writeInTemplate(sheet, "SUM(K" + (startRow - 2) + ",K" + (row - 1) + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	}else if(closedList.size() > 0){
            		
            		//小计
            		CommonExport.writeInTemplate(sheet, "总计", row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//合同金额(美金)小计
                	CommonExport.writeInTemplate(sheet, "SUM(D" + (startRow - 2) + ")" , row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
                	//工厂金额小计
                	CommonExport.writeInTemplate(sheet, "SUM(H" + (startRow - 2) + ")" , row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
                	//利润小计
                	CommonExport.writeInTemplate(sheet, "SUM(K" + (startRow - 2) + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	}else{
            		
            		//小计
            		CommonExport.writeInTemplate(sheet, "总计", row, 1, XSSFCell.CELL_TYPE_STRING, null);
            		
            		//合同金额(美金)小计
                	CommonExport.writeInTemplate(sheet, "SUM(D" + (row - 1) + ")" , row, 3, XSSFCell.CELL_TYPE_FORMULA, null);
                	//工厂金额小计
                	CommonExport.writeInTemplate(sheet, "SUM(H" + (row - 1) + ")" , row, 7, XSSFCell.CELL_TYPE_FORMULA, null);
                	//利润小计
                	CommonExport.writeInTemplate(sheet, "SUM(K" + (row - 1) + ")" , row, 10, XSSFCell.CELL_TYPE_FORMULA, null);
            	}
            }
            
            // 删除多余SHEET
            wb.removeSheetAt(0);
            
            fileFullPath = "C:/excelDownloadTemp/analysis/" + fileName;
            // 对读取Excel表格标题测试
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
    
    public static void main(String[] args) {
		
//    	TradeOrderExport tradeOrderExport = new TradeOrderExport();
//    	tradeOrderExport.generateFile(null);
    	
    	System.out.println(1 + 2 / 2);
	}
}
