package com.poi;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.util.IsEmptyUtil;

public class CommonExport {
	
	public static final int CELL_STYLE_RECEIPT_DTL_TITLE = 1;
	public static final int CELL_STYLE_RECEIPT_DTL = 2;
	public static final int CELL_STYLE_BOLD = 3;
	public static final int CELL_STYLE_CI_B = 4;
	public static final int CELL_STYLE_CI = 5;
	public static final int CELL_STYLE_TPA = 6;
	public static final int CELL_STYLE_TPA_W = 7;
	public static final int CELL_STYLE_LEFT = 8;
	public static final int CELL_STYLE_CENTER = 9;
	public static final int CELL_STYLE_RECEIPT_DTL_RED = 10;
	public static final int CELL_STYLE_RECEIPT_DTL_NORMAL = 11;

	public static void writeInTemplate(XSSFSheet sheet, String newContent, int rowIndex, int colIndex, int cellType, XSSFCellStyle cellStyle) {     
        XSSFRow row  = sheet.getRow(rowIndex);   
        if(null == row ){  
            row = sheet.createRow(rowIndex);  
        }  
        XSSFCell cell = row.getCell(colIndex);  
        if(null == cell){  
            cell = row.createCell(colIndex);  
        }  
        //设置单元格格式
        if(cellStyle != null){
        	
        	cell.setCellStyle(cellStyle);  
        }
        
        //向单元格中放入值  
        cell.setCellValue(newContent);      
        if (cellType == XSSFCell.CELL_TYPE_NUMERIC && !IsEmptyUtil.isEmpty(newContent)) {  
            cell.setCellValue(Double.parseDouble(newContent));      
        } else if (cellType == XSSFCell.CELL_TYPE_STRING) {  
        	cell.setCellValue(newContent);      
        } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {  
        	cell.setCellFormula(newContent);
        }
    }  
	
	public static XSSFCellStyle getCellStyle(XSSFWorkbook wb, int type){
		
		XSSFCellStyle cellStyle = wb.createCellStyle(); 
		XSSFFont font = wb.createFont(); 
		
		if(type == CELL_STYLE_RECEIPT_DTL_TITLE){
			
			//设置字体
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Arial");
			cellStyle.setFont(font); 
			
			//设置边框
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
		}else if(type == CELL_STYLE_RECEIPT_DTL){
			
			//设置字体
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//粗体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Arial");
			cellStyle.setFont(font); 
			
			//设置边框
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
		}else if(type == CELL_STYLE_RECEIPT_DTL_RED){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Arial");
			font.setColor(IndexedColors.RED.index);
			cellStyle.setFont(font); 
			XSSFDataFormat format= wb.createDataFormat();  
			cellStyle.setDataFormat(format.getFormat("￥#,##0.00")); 
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			
			//设置边框
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
		}else if(type == CELL_STYLE_RECEIPT_DTL_NORMAL){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Arial");
			font.setColor(IndexedColors.BLACK.index);
			cellStyle.setFont(font); 
			XSSFDataFormat format= wb.createDataFormat();  
			cellStyle.setDataFormat(format.getFormat("￥#,##0.00")); 
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			
			//设置边框
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			
		}else if(type == CELL_STYLE_BOLD){
			
			//设置字体
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Arial");
			cellStyle.setFont(font); 
		}else if(type == CELL_STYLE_CI_B){
			
			//设置字体
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//粗体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Verdana");
			cellStyle.setFont(font); 
			
		}else if(type == CELL_STYLE_CI){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Verdana");
			cellStyle.setFont(font); 
		}else if(type == CELL_STYLE_TPA){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("宋体");
			cellStyle.setFont(font); 
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		}else if(type == CELL_STYLE_TPA_W){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("宋体");
			cellStyle.setWrapText(true);
			cellStyle.setFont(font); 
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		}else if(type == CELL_STYLE_LEFT){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Verdana");
			cellStyle.setFont(font); 
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		}else if(type == CELL_STYLE_CENTER){
			
			//设置字体
			font.setFontHeightInPoints((short) 10);//设置字体大小
			font.setFontName("Verdana");
			cellStyle.setFont(font); 
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		}
		
		return cellStyle;
	}
	
	/** 
     * 复制行
     * @param wb 
     * @param fromRow 
     * @param toRow
     * @param copyValueFlag 
     *            true则连同cell的内容一起复制 
     */  
	public static void copyRow(XSSFWorkbook wb,XSSFRow fromRow,XSSFRow toRow,boolean copyValueFlag){  
        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext();) {  
            XSSFCell tmpCell = (XSSFCell) cellIt.next();  
            XSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());  
            copyCell(wb,tmpCell, newCell, copyValueFlag);  
        }  
    }
	
	/** 
     * 复制单元格 
     * @param wb 
     * @param srcCell 
     * @param distCell 
     * @param copyValueFlag 
     *            true则连同cell的内容一起复制 
     */  
    public static void copyCell(XSSFWorkbook wb,XSSFCell srcCell, XSSFCell distCell,  
            boolean copyValueFlag) {  
        XSSFCellStyle newstyle=wb.createCellStyle();  
        copyCellStyle(srcCell.getCellStyle(), newstyle);  
        //样式  
        distCell.setCellStyle(newstyle);  
        //评论  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        // 不同数据类型处理  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (copyValueFlag) {  
            if (srcCellType == XSSFCell.CELL_TYPE_NUMERIC) {  
                if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                    distCell.setCellValue(srcCell.getDateCellValue());  
                } else {  
                    distCell.setCellValue(srcCell.getNumericCellValue());  
                }  
            } else if (srcCellType == XSSFCell.CELL_TYPE_STRING) {  
                distCell.setCellValue(srcCell.getRichStringCellValue());  
            } else if (srcCellType == XSSFCell.CELL_TYPE_BLANK) {  
                // nothing21  
            } else if (srcCellType == XSSFCell.CELL_TYPE_BOOLEAN) {  
                distCell.setCellValue(srcCell.getBooleanCellValue());  
            } else if (srcCellType == XSSFCell.CELL_TYPE_ERROR) {  
                distCell.setCellErrorValue(srcCell.getErrorCellValue());  
            } else if (srcCellType == XSSFCell.CELL_TYPE_FORMULA) {  
                distCell.setCellFormula(srcCell.getCellFormula());  
            } else { // nothing29  
            }  
        }  
    }  
	
	/** 
     * 复制一个单元格样式到目的单元格样式 
     * @param fromStyle 
     * @param toStyle 
     */  
    public static void copyCellStyle(XSSFCellStyle fromStyle,  
            XSSFCellStyle toStyle) {  
        toStyle.setAlignment(fromStyle.getAlignment());  
        //边框和边框颜色  
        toStyle.setBorderBottom(fromStyle.getBorderBottom());  
        toStyle.setBorderLeft(fromStyle.getBorderLeft());  
        toStyle.setBorderRight(fromStyle.getBorderRight());  
        toStyle.setBorderTop(fromStyle.getBorderTop());  
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());  
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());  
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());  
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());  
          
        //背景和前景  
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());  
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());  
          
        toStyle.setDataFormat(fromStyle.getDataFormat());  
        toStyle.setFillPattern(fromStyle.getFillPattern());  
        toStyle.setFont(fromStyle.getFont());  
        toStyle.setHidden(fromStyle.getHidden());  
        toStyle.setIndention(fromStyle.getIndention());//首行缩进  
        toStyle.setLocked(fromStyle.getLocked());  
        toStyle.setRotation(fromStyle.getRotation());//旋转  
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());  
        toStyle.setWrapText(fromStyle.getWrapText());  
          
    }  
    
	public static void insertRow(XSSFWorkbook wb, XSSFSheet sheet, int starRow, int step) {

		try{
			

			for (int i = 0; i < step; i++) {
				sheet.shiftRows(starRow, sheet.getLastRowNum(), step, true, false);
				starRow = starRow - 1;
				
				XSSFRow sourceRow = null;
				XSSFRow targetRow = null;
				XSSFCell sourceCell = null;
				XSSFCell targetCell = null;
				short m;
		
				for(int j = 0; j < step; j++){
					
					starRow = starRow - step;
					sourceRow = sheet.getRow(starRow);
					targetRow = sheet.createRow(starRow + step);
					targetRow.setHeight(sourceRow.getHeight());
		
					for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
		
						sourceCell = sourceRow.getCell(m);
						targetCell = targetRow.createCell(m);
		
						targetCell.setCellStyle(sourceCell.getCellStyle());
						targetCell.setCellType(sourceCell.getCellType());
					}
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
	
	public static String getCellValue(Cell cell) {  
        String ret;  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_BLANK:  
            ret = "";  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            ret = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_ERROR:  
            ret = null;  
            break;  
        case Cell.CELL_TYPE_FORMULA:  
            Workbook wb = cell.getSheet().getWorkbook();  
            CreationHelper crateHelper = wb.getCreationHelper();  
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
            ret = getCellValue(evaluator.evaluateInCell(cell));  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
            break;  
        case Cell.CELL_TYPE_STRING:  
            ret = cell.getRichStringCellValue().getString();  
            break;  
        default:  
            ret = null;  
        }  
          
        return ret; //有必要自行trim  
    }  
}
