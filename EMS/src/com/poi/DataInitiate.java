package com.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.Customer;
import com.entity.Production;
import com.entity.UserCustomerLinkRef;
import com.entity.Vendor;
import com.util.Filehandler;

import java.sql.Connection;

public class DataInitiate {
	
	private final String db_driver="com.mysql.jdbc.Driver"; 
	private final String url = "jdbc:mysql://127.0.0.1/bms"; 
	private final String user = "root";  
	private final String password = "1234qwer";  
    
	private Connection getconnection() throws Exception {
		
		Connection conn = null;
		
		try {

			Class.forName(db_driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
		}
		return conn;
	}
	
	private String getCellString(XSSFSheet sheet, int row, int col){
		
		String str = null;
		if(sheet.getRow(row).getCell(col) != null){
			
			str = CommonExport.getCellValue(sheet.getRow(row).getCell(col));
		}
		
		return str;
	}

    public void inputProduction() {
        
    	FileInputStream input = null;
    	XSSFWorkbook wb = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/initiateData/initiateData.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            List<Production> productionList = new ArrayList<Production>();
            
            for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
				
            	int col = 0;
            	
            	if(sheet.getRow(row) == null){
            		
            		break;
            	}
            	
            	Production production = new Production();
            	production.setProductionId(getCellString(sheet, row, col++));
            	production.setProductionIdVendor(getCellString(sheet, row, col++));
            	production.setDescriptionC(getCellString(sheet, row, col++));
            	production.setDescriptionE(getCellString(sheet, row, col++));
            	production.setMaterial(getCellString(sheet, row, col++));
            	production.setSurface(getCellString(sheet, row, col++));
            	production.setSize(getCellString(sheet, row, col++));
            	production.setHscode(getCellString(sheet, row, col++));
            	production.setProductionCname4export(getCellString(sheet, row, col++));
            	production.setProductionEname4export(getCellString(sheet, row, col++));
            	production.setTaxReturnRate(getCellString(sheet, row, col++));
            	production.setBrand(getCellString(sheet, row, col++));
            	production.setPurpose(getCellString(sheet, row, col++));
            	production.setKind(getCellString(sheet, row, col++));
            	production.setVendorId(getCellString(sheet, row, col++));
            	production.setCustomerId(getCellString(sheet, row, col++));
            	production.setPackMethod(getCellString(sheet, row, col++));
            	production.setVolume(getCellString(sheet, row, col++));
            	production.setGrossWeight(getCellString(sheet, row, col++));
            	production.setNetWeight(getCellString(sheet, row, col++));
            	production.setInside(getCellString(sheet, row, col++));
            	production.setOutside(getCellString(sheet, row, col++));
            	
            	productionList.add(production);
			}
            
            Connection connection = getconnection();
            PreparedStatement ps = connection.prepareStatement("truncate table bms.production");;
            ps.executeUpdate();
            
            String insertSql = "insert into bms.production values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            for (Production production : productionList) {
				
            	ps = connection.prepareStatement(insertSql); 
            	int i = 1;
            	ps.setString(i++, production.getProductionId());
            	ps.setString(i++, "1");
            	ps.setString(i++, production.getProductionIdVendor());
            	ps.setString(i++, production.getDescriptionC());
            	ps.setString(i++, production.getDescriptionE());
            	ps.setString(i++, production.getMaterial());
            	ps.setString(i++, production.getSurface());
            	ps.setString(i++, production.getSize());
            	ps.setString(i++, production.getHscode());
            	ps.setString(i++, production.getProductionCname4export());
            	ps.setString(i++, production.getProductionEname4export());
            	ps.setString(i++, production.getTaxReturnRate());
            	ps.setString(i++, production.getBrand());
            	ps.setString(i++, production.getPurpose());
            	ps.setString(i++, production.getKind());
            	ps.setString(i++, production.getVendorId());
            	ps.setString(i++, production.getCustomerId());
            	ps.setString(i++, production.getPackMethod());
            	ps.setString(i++, production.getVolume());
            	ps.setString(i++, production.getGrossWeight());
            	ps.setString(i++, production.getNetWeight());
            	ps.setString(i++, production.getInside());
            	ps.setString(i++, production.getOutside());
            	ps.setString(i++, production.getLastDrawUpdateDate());
            	ps.setString(i++, production.getComment());
            	ps.setString(i++, "1");
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setString(i++, "0");
            	
            	ps.executeUpdate();
            	
            	Filehandler.createFolder(production.getProductionId());
			}
            ps.close(); 
            connection.close();
            
            
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
			} catch (Exception e) {
				
				System.out.println("意外错误!");
	            e.printStackTrace();
			}
        }
    }
    
    public void inputCustomer() {
        
    	FileInputStream input = null;
    	XSSFWorkbook wb = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/initiateData/initiateData.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(1);
            
            List<Customer> customerList = new ArrayList<Customer>();
            
            for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
				
            	int col = 0;
            	
            	if(sheet.getRow(row) == null){
            		
            		break;
            	}
            	
            	Customer customer = new Customer();
            	customer.setCustomerId(getCellString(sheet, row, col++));
            	customer.setCustomerName(getCellString(sheet, row, col++));
            	customer.setCustomerFullName(getCellString(sheet, row, col++));
            	customer.setWebSite(getCellString(sheet, row, col++));
            	customer.setCountry(getCellString(sheet, row, col++));
            	customer.setLocation(getCellString(sheet, row, col++));
            	customer.setFreightTerms(getCellString(sheet, row, col++));
            	customer.setPaymentTerms(getCellString(sheet, row, col++));
            	customer.setPortOfLoading(getCellString(sheet, row, col++));
            	customer.setPortOfDestination(getCellString(sheet, row, col++));
            	customer.setConsignee(getCellString(sheet, row, col++));
            	customer.setContact(getCellString(sheet, row, col++));
            	customer.setTel(getCellString(sheet, row, col++));
            	customer.setFax(getCellString(sheet, row, col++));
            	customer.setContact1(getCellString(sheet, row, col++));
            	customer.setContact2(getCellString(sheet, row, col++));
            	customer.setContact3(getCellString(sheet, row, col++));
            	customer.setContact4(getCellString(sheet, row, col++));
            	customer.setContact5(getCellString(sheet, row, col++));
            	
            	customerList.add(customer);
			}
            
            Connection connection = getconnection();
            PreparedStatement ps = connection.prepareStatement("truncate table bms.customer");;
            ps.executeUpdate();
            
            String insertSql = "insert into bms.customer values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            for (Customer customer : customerList) {
				
            	ps = connection.prepareStatement(insertSql); 
            	int i = 1;
            	ps.setString(i++, customer.getCustomerId());
            	ps.setString(i++, customer.getCustomerName());
            	ps.setString(i++, customer.getCustomerFullName());
            	ps.setString(i++, customer.getWebSite());
            	ps.setString(i++, customer.getCountry());
            	ps.setString(i++, customer.getLocation());
            	ps.setString(i++, customer.getFreightTerms());
            	ps.setString(i++, customer.getPaymentTerms());
            	ps.setString(i++, customer.getPortOfLoading());
            	ps.setString(i++, customer.getPortOfDestination());
            	ps.setString(i++, customer.getConsignee());
            	ps.setString(i++, customer.getContact());
            	ps.setString(i++, customer.getTel());
            	ps.setString(i++, customer.getFax());
            	ps.setString(i++, customer.getContact1());
            	ps.setString(i++, customer.getContact2());
            	ps.setString(i++, customer.getContact3());
            	ps.setString(i++, customer.getContact4());
            	ps.setString(i++, customer.getContact5());
            	ps.setString(i++, null);
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setString(i++, "0");
            	
            	ps.executeUpdate();
			}
            ps.close(); 
            connection.close();
            
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
			} catch (Exception e) {
				
				System.out.println("意外错误!");
	            e.printStackTrace();
			}
        }
    }
    
    public void inputVendor() {
        
    	FileInputStream input = null;
    	XSSFWorkbook wb = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/initiateData/initiateData.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(2);
            
            List<Vendor> vendorList = new ArrayList<Vendor>();
            
            for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
				
            	int col = 0;

            	if(sheet.getRow(row) == null){
            		
            		break;
            	}
            	
            	Vendor vendor = new Vendor();
            	vendor.setVendorId(getCellString(sheet, row, col++));
            	vendor.setVendorName(getCellString(sheet, row, col++));
            	vendor.setVendorFullName(getCellString(sheet, row, col++));
            	vendor.setLocation(getCellString(sheet, row, col++));
            	vendor.setTel(getCellString(sheet, row, col++));
            	vendor.setFax(getCellString(sheet, row, col++));
            	vendor.setOrcc(getCellString(sheet, row, col++));
            	vendor.setBillingInfo(getCellString(sheet, row, col++));
            	vendor.setContact1(getCellString(sheet, row, col++));
            	vendor.setMobile1(getCellString(sheet, row, col++));
            	vendor.setTitle1(getCellString(sheet, row, col++));
            	vendor.setContact2(getCellString(sheet, row, col++));
            	vendor.setMobile2(getCellString(sheet, row, col++));
            	vendor.setTitle2(getCellString(sheet, row, col++));
            	vendor.setContact3(getCellString(sheet, row, col++));
            	vendor.setMobile3(getCellString(sheet, row, col++));
            	vendor.setTitle3(getCellString(sheet, row, col++));
            	vendor.setContact4(getCellString(sheet, row, col++));
            	vendor.setMobile4(getCellString(sheet, row, col++));
            	vendor.setTitle4(getCellString(sheet, row, col++));
            	vendor.setContact5(getCellString(sheet, row, col++));
            	vendor.setMobile5(getCellString(sheet, row, col++));
            	vendor.setTitle5(getCellString(sheet, row, col++));
            	
            	vendorList.add(vendor);
			}
            
            Connection connection = getconnection();
            PreparedStatement ps = connection.prepareStatement("truncate table bms.vendor");;
            ps.executeUpdate();
            
            String insertSql = "insert into bms.vendor values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            for (Vendor vendor : vendorList) {
				
            	ps = connection.prepareStatement(insertSql); 
            	int i = 1;
            	ps.setString(i++, vendor.getVendorId());
            	ps.setString(i++, vendor.getVendorName());
            	ps.setString(i++, vendor.getVendorFullName());
            	ps.setString(i++, vendor.getLocation());
            	ps.setString(i++, vendor.getTel());
            	ps.setString(i++, vendor.getFax());
            	ps.setString(i++, vendor.getOrcc());
            	ps.setString(i++, vendor.getBillingInfo());
            	ps.setString(i++, vendor.getContact1());
            	ps.setString(i++, vendor.getContact2());
            	ps.setString(i++, vendor.getContact3());
            	ps.setString(i++, vendor.getContact4());
            	ps.setString(i++, vendor.getContact5());
            	ps.setString(i++, vendor.getMobile1());
            	ps.setString(i++, vendor.getMobile2());
            	ps.setString(i++, vendor.getMobile3());
            	ps.setString(i++, vendor.getMobile4());
            	ps.setString(i++, vendor.getMobile5());
            	ps.setString(i++, vendor.getTitle1());
            	ps.setString(i++, vendor.getTitle2());
            	ps.setString(i++, vendor.getTitle3());
            	ps.setString(i++, vendor.getTitle4());
            	ps.setString(i++, vendor.getTitle5());
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setString(i++, "0");
            	
            	ps.executeUpdate();
			}
            ps.close(); 
            connection.close();
            
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
			} catch (Exception e) {
				
				System.out.println("意外错误!");
	            e.printStackTrace();
			}
        }
    }
    
    public void inputUserCustomerLinkRef() {
        
    	FileInputStream input = null;
    	XSSFWorkbook wb = null;
    	
    	try {
    		
    		// 对读取Excel表格
    		input = new FileInputStream("C:/initiateData/initiateData.xlsx");
            wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(3);
            
            List<UserCustomerLinkRef> userCustomerLinkRefList = new ArrayList<UserCustomerLinkRef>();
            
            for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
				
            	int col = 0;
            	
            	if(sheet.getRow(row) == null){
            		
            		break;
            	}
            	
            	UserCustomerLinkRef userCustomerLinkRef = new UserCustomerLinkRef();
            	userCustomerLinkRef.setUserId(getCellString(sheet, row, col++));
            	userCustomerLinkRef.setCustomerId(getCellString(sheet, row, col++));
            	
            	userCustomerLinkRefList.add(userCustomerLinkRef);
			}
            
            Connection connection = getconnection();
            PreparedStatement ps = connection.prepareStatement("truncate table bms.userCustomerLinkRef");;
            ps.executeUpdate();
            
            String insertSql = "insert into bms.userCustomerLinkRef values (?, ?, ?, ?, ?, ?, ?)"; 
            for (UserCustomerLinkRef userCustomerLinkRef : userCustomerLinkRefList) {
				
            	ps = connection.prepareStatement(insertSql); 
            	int i = 1;
            	ps.setString(i++, userCustomerLinkRef.getUserId());
            	ps.setString(i++, userCustomerLinkRef.getCustomerId());
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            	ps.setString(i++, "systemInitiate");
            	ps.setString(i++, "0");
            	
            	ps.executeUpdate();
			}
            ps.close(); 
            connection.close();
            
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
			} catch (Exception e) {
				
				System.out.println("意外错误!");
	            e.printStackTrace();
			}
        }
    }
    
    public static void main(String[] args) {
		
    	System.out.println("初始化开始");
    	DataInitiate dataInitiate = new DataInitiate();
    	dataInitiate.inputProduction();
//    	dataInitiate.inputCustomer();
//    	dataInitiate.inputVendor();
//    	dataInitiate.inputUserCustomerLinkRef();
    	System.out.println("初始化结束");
	}
}
