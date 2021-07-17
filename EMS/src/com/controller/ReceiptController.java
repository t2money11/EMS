package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.common.Token;
import com.context.ConstantParam;
import com.context.Message;
import com.entity.ClearanceDtl;
import com.entity.Receipt;
import com.entity.ReceiptDtl;
import com.entity.ReceiptDtl4C;
import com.poi.ReceiptExport;
import com.service.ReceiptService;
import com.service.ProductionService;
import com.service.TradeOrderService;
import com.service.ValidationAPIService;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/receipt")
public class ReceiptController{
	
	@Autowired
	private ReceiptService receiptService = null;
	
	@Autowired
	private ProductionService productionService = null;
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	@Autowired
	private ReceiptExport receiptExport = null;
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public static final Comparator<ReceiptDtl> comparatorReceiptDtl = new Comparator<ReceiptDtl>(){ 
 		
		@Override
 		public int compare(ReceiptDtl r1, ReceiptDtl r2) { 
			
	 	    if(!r1.getTradeOrderId().equals(r2.getTradeOrderId())){ 
 	    	//先排订单编号	
	 	    	return r1.getTradeOrderId().compareTo(r2.getTradeOrderId()); 
	 	    }else if(!r1.getProductionId().equals(r2.getProductionId())){ 
	     	//订单编号相同则按产品型号排序 
 	    		return r1.getProductionId().compareTo(r2.getProductionId()); 
	     	}else if(!r1.getVersionNo().equals(r2.getVersionNo())){
     		//产品型号相同则按版本排序 
	     		return r1.getVersionNo().compareTo(r2.getVersionNo()); 
	     	}else{
     		//最后排订费用编号	
	     		return r1.getFeeNum().compareTo(r2.getFeeNum());
	     	}
		}
 	};
 	
 	public static final Comparator<ClearanceDtl> comparatorClearanceDtl = new Comparator<ClearanceDtl>(){ 
 		
		@Override
 		public int compare(ClearanceDtl c1, ClearanceDtl c2) { 
	 		//排产品名（报关用英文）
 	    	return c1.getProductionEName4Export().compareTo(c2.getProductionEName4Export()); 
		}
 	};
 	
 	public static final Comparator<ReceiptDtl4C> comparatorReceiptDtl4C = new Comparator<ReceiptDtl4C>(){ 
 		
		@Override
 		public int compare(ReceiptDtl4C r1, ReceiptDtl4C r2) { 
			
			 if(!r1.getTradeOrderId().equals(r2.getTradeOrderId())){ 
 	    	//先排订单编号	
	 	    	return r1.getTradeOrderId().compareTo(r2.getTradeOrderId()); 
	 	    }else if(!r1.getProductionId().equals(r2.getProductionId())){ 
	     	//订单编号相同则按产品型号排序 
 	    		return r1.getProductionId().compareTo(r2.getProductionId()); 
	     	}else if(!r1.getVersionNo().equals(r2.getVersionNo())){
     		//产品型号相同则按版本排序 
	     		return r1.getVersionNo().compareTo(r2.getVersionNo()); 
	     	}else{
     		//最后排投诉单编号	
	     		return r1.getComplaintId().compareTo(r2.getComplaintId());
	     	}
		}
 	};
 	
	/**
	 * 发货单追加订单产品
	 * @param receipt
	 * @param pageMode
	 * @param productions
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addProduction")
	public ModelAndView addProduction(@ModelAttribute("receipt")Receipt receipt, 
			@RequestParam("pageMode")String pageMode, 
			@RequestParam("productions")String productions,
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		
		String errorMessage = "";
		
		ModelAndView mav = new ModelAndView();
		
        //将json格式的字符串转换为json数组对象  
        JSONArray array=(JSONArray)JSONObject.fromObject(productions).get("tempList");  
        
        //清除TITLE
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtlList().get(i).getProductionId())){
    			
    			receipt.getReceiptDtlList().remove(i--);
    		}
    	}
    	
        for (int i = 0; i < array.size(); i++) {
        	
        	//是否已经添加过该产品
        	boolean productionAdded = false;
        	
        	//取得json数组中的对象  
        	JSONObject o = (JSONObject) array.get(i);
        	
        	for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
        		
        		if(o.get("tradeOrderId").toString().equals(receiptDtl.getTradeOrderId())
        				&& o.get("productionId").toString().equals(receiptDtl.getProductionId())
        				&& o.get("versionNo").toString().equals(receiptDtl.getVersionNo())
        				&& "0".equals(receiptDtl.getFeeNum())){
        			
        			errorMessage = errorMessage.concat(String.format(Message.DUPLICATED_PRODUCTION_IN_RECEIPT, receiptDtl.getTradeOrderId(), receiptDtl.getProductionId(), receiptDtl.getVersionNo())).concat("</br>");
        			productionAdded = true;
        			break;
        		}
        	}
        	
        	if(productionAdded == false){
        		
            	ReceiptDtl receiptDtl = new ReceiptDtl();
    			
            	receiptDtl.setTradeOrderId(o.get("tradeOrderId").toString());
            	receiptDtl.setPo(o.get("po").toString());
            	receiptDtl.setContractNo(o.get("contractNo").toString());
            	receiptDtl.setTradeOrderCreateDate(o.get("tradeOrderCreateDate").toString());
            	receiptDtl.setProductionId(o.get("productionId").toString());
            	receiptDtl.setVersionNo(o.get("versionNo").toString());
            	receiptDtl.setFeeNum("0");
            	receiptDtl.setDescriptionE(o.get("descriptionE").toString());
            	receiptDtl.setHscode(o.get("hscode").toString());
            	receiptDtl.setProductionEName4Export(o.get("productionEName4Export").toString());
            	receiptDtl.setProductionCName4Export(o.get("productionCName4Export").toString());
            	receiptDtl.setVendorId(o.get("vendorId").toString());
            	receiptDtl.setVendorName(o.get("vendorName").toString());
            	receiptDtl.setShortLocation(o.get("shortLocation").toString());
            	receiptDtl.setiQuantity(o.get("iQuantity").toString());
            	receiptDtl.settQuantity(o.get("tQuantity").toString());
            	receiptDtl.setiUnitPrice(o.get("iUnitPrice").toString());
            	receiptDtl.settUnitPrice(o.get("tUnitPrice").toString());
            	receiptDtl.setiQuantityNotSent(o.get("iQuantityNotSent").toString());
            	receiptDtl.settQuantityNotSent(o.get("tQuantityNotSent").toString());
            	receiptDtl.setVolume(o.get("volume").toString());
            	receiptDtl.setGrossWeight(o.get("grossWeight").toString());
            	receiptDtl.setNetWeight(o.get("netWeight").toString());
            	receiptDtl.setInside(o.get("inside").toString());
            	receiptDtl.setOutside(o.get("outside").toString());
            	receiptDtl.settFeeTitle1(o.get("tFeeTitle1").toString());
            	receiptDtl.settFee1(o.get("tFee1").toString());
            	receiptDtl.settFeeTitle2(o.get("tFeeTitle2").toString());
            	receiptDtl.settFee2(o.get("tFee2").toString());
            	receiptDtl.settFeeTitle3(o.get("tFeeTitle3").toString());
            	receiptDtl.settFee3(o.get("tFee3").toString());
            	receiptDtl.settFeeTitle4(o.get("tFeeTitle4").toString());
            	receiptDtl.settFee4(o.get("tFee4").toString());
            	receiptDtl.setiFeeTitle1(o.get("iFeeTitle1").toString());
            	receiptDtl.setiFee1(o.get("iFee1").toString());
            	receiptDtl.setiFeeTitle2(o.get("iFeeTitle2").toString());
            	receiptDtl.setiFee2(o.get("iFee2").toString());
            	receiptDtl.setiFeeTitle3(o.get("iFeeTitle3").toString());
            	receiptDtl.setiFee3(o.get("iFee3").toString());
            	receiptDtl.setiFeeTitle4(o.get("iFeeTitle4").toString());
            	receiptDtl.setiFee4(o.get("iFee4").toString());
            	try {
            		receiptDtl.setTradeOrderUpdateTime(DateUtil.stringToTimestamp(o.get("tradeOrderUpdateTime").toString()));
            		receiptDtl.setProductionUpdateTime(DateUtil.stringToTimestamp(o.get("productionUpdateTime").toString()));
				} catch (Exception e) {
					// TODO: handle exception
				}
            	receipt.getReceiptDtlList().add(receiptDtl);
    		}
        }
        
        //生成各种费用
        generateFee(receipt);
        	
        //排序
        Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
        
        //添加TITLE
        addTitle(receipt);
        
        //排序
        Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
        
        //生成报关信息
        generateClearanceDtl(receipt);
        
        //排序
        Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
        
		if(pageMode.equals("add")){
			mav.setViewName("receiptAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		}else{
			mav.setViewName("receiptUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		}
		
		mav.addObject("receipt", receipt);
		if(!IsEmptyUtil.isEmpty(errorMessage)){
			mav.addObject("errorMessage", errorMessage);
		}
		return mav;
		
	}
	
	/**
	 * 发货单追加投诉单产品
	 * @param receipt
	 * @param pageMode
	 * @param productions
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addProduction4C")
	public ModelAndView addProduction4C(@ModelAttribute("receipt")Receipt receipt, 
			@RequestParam("pageMode")String pageMode, 
			@RequestParam("productions")String productions,
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		
		String errorMessage = "";
		
		ModelAndView mav = new ModelAndView();
		
        //将json格式的字符串转换为json数组对象  
        JSONArray array=(JSONArray)JSONObject.fromObject(productions).get("tempList");  
        
        //清除TITLE
    	for (int i = 0; i < receipt.getReceiptDtl4CList().size(); i++) {
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtl4CList().get(i).getProductionId())){
    			
    			receipt.getReceiptDtl4CList().remove(i--);
    		}
    	}
    	
        for (int i = 0; i < array.size(); i++) {
        	
        	//是否已经添加过该产品
        	boolean productionAdded = false;
        	
        	//取得json数组中的对象  
        	JSONObject o = (JSONObject) array.get(i);
        	
        	for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
        		
        		if(o.get("complaintId").toString().equals(receiptDtl4C.getComplaintId())
        				&& o.get("tradeOrderId").toString().equals(receiptDtl4C.getTradeOrderId())
        				&& o.get("productionId").toString().equals(receiptDtl4C.getProductionId())
        				&& o.get("versionNo").toString().equals(receiptDtl4C.getVersionNo())){
        			
        			errorMessage = errorMessage.concat(String.format(Message.DUPLICATED_PRODUCTION_IN_RECEIPT_4C, receiptDtl4C.getComplaintId(), receiptDtl4C.getTradeOrderId(), receiptDtl4C.getProductionId(), receiptDtl4C.getVersionNo())).concat("</br>");
        			productionAdded = true;
        			break;
        		}
        	}
        	
        	if(productionAdded == false){
        		
        		ReceiptDtl4C receiptDtl4C = new ReceiptDtl4C();
    			
        		receiptDtl4C.setComplaintId(o.get("complaintId").toString());
        		receiptDtl4C.setTradeOrderId(o.get("tradeOrderId").toString());
        		receiptDtl4C.setPo(o.get("po").toString());
            	receiptDtl4C.setContractNo(o.get("contractNo").toString());
            	receiptDtl4C.setTradeOrderCreateDate(o.get("tradeOrderCreateDate").toString());
            	receiptDtl4C.setProductionId(o.get("productionId").toString());
            	receiptDtl4C.setVersionNo(o.get("versionNo").toString());
            	receiptDtl4C.setDescriptionE(o.get("descriptionE").toString());
            	receiptDtl4C.setHscode(o.get("hscode").toString());
            	receiptDtl4C.setProductionEName4Export(o.get("productionEName4Export").toString());
            	receiptDtl4C.setProductionCName4Export(o.get("productionCName4Export").toString());
            	receiptDtl4C.setVendorId(o.get("vendorId").toString());
            	receiptDtl4C.setVendorName(o.get("vendorName").toString());
            	receiptDtl4C.setShortLocation(o.get("shortLocation").toString());
            	receiptDtl4C.settQuantity(o.get("tQuantity").toString());
            	receiptDtl4C.settUnitPrice(o.get("tUnitPrice").toString());
            	receiptDtl4C.setiUnitPrice(o.get("iUnitPrice").toString());
            	receiptDtl4C.settQuantityNotSent(o.get("tQuantityNotSent").toString());
            	receiptDtl4C.setVolume(o.get("volume").toString());
            	receiptDtl4C.setGrossWeight(o.get("grossWeight").toString());
            	receiptDtl4C.setNetWeight(o.get("netWeight").toString());
            	receiptDtl4C.setInside(o.get("inside").toString());
            	receiptDtl4C.setOutside(o.get("outside").toString());
            	receiptDtl4C.setBoxAmount(o.get("boxAmount").toString());
            	
            	try {
            		receiptDtl4C.setTradeOrderUpdateTime(DateUtil.stringToTimestamp(o.get("tradeOrderUpdateTime").toString()));
            		receiptDtl4C.setProductionUpdateTime(DateUtil.stringToTimestamp(o.get("productionUpdateTime").toString()));
            		receiptDtl4C.setComplaintUpdateTime(DateUtil.stringToTimestamp(o.get("complaintUpdateTime").toString()));
				} catch (Exception e) {
					// TODO: handle exception
				}
            	receipt.getReceiptDtl4CList().add(receiptDtl4C);
    		}
        }
        
        //排序
        Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
        
        //添加TITLE
        addTitle4C(receipt);
        
        //排序
        Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
        
        //生成报关信息
        generateClearanceDtl(receipt);
        
        //排序
        Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
        
		if(pageMode.equals("add")){
			mav.setViewName("receiptAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		}else{
			mav.setViewName("receiptUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		}
		
		mav.addObject("receipt", receipt);
		if(!IsEmptyUtil.isEmpty(errorMessage)){
			mav.addObject("errorMessage", errorMessage);
		}
		return mav;
		
	}
	
	private void generateFee(Receipt receipt){
		
		List<ReceiptDtl> receiptDtlList = receipt.getReceiptDtlList();
		
		List<ReceiptDtl> receiptDtlListAdd = new ArrayList<ReceiptDtl>();
		
		for (ReceiptDtl receiptDtl : receiptDtlList) {
		
			
			if(!IsEmptyUtil.isEmpty(receiptDtl.getiFee1()) || !IsEmptyUtil.isEmpty(receiptDtl.gettFee1())){
				
				ReceiptDtl receiptTemp = new ReceiptDtl();
				
				receiptTemp.setTradeOrderId(receiptDtl.getTradeOrderId());
				receiptTemp.setPo(receiptDtl.getPo());
				receiptTemp.setContractNo(receiptDtl.getContractNo());
				receiptTemp.setProductionId(receiptDtl.getProductionId());
				receiptTemp.setVersionNo(receiptDtl.getVersionNo());
		    	receiptTemp.setHscode(receiptDtl.getHscode());
		    	receiptTemp.setProductionEName4Export(receiptDtl.getProductionEName4Export());
		    	receiptTemp.setProductionCName4Export(receiptDtl.getProductionCName4Export());
		    	receiptTemp.setVendorId(receiptDtl.getVendorId());
		    	receiptTemp.setVendorName(receiptDtl.getVendorName());
				receiptTemp.setFeeNum("1");
				if(IsEmptyUtil.isEmpty(receiptDtl.gettFee1())){
					
					receiptTemp.setDescriptionE(receiptDtl.getiFeeTitle1());
				}else{
					
					receiptTemp.setDescriptionE(receiptDtl.gettFeeTitle1());
				}
		    	receiptTemp.settAmount(receiptDtl.gettFee1());
		    	receiptTemp.setiAmount(receiptDtl.getiFee1());
		    	
		    	boolean isAdded = false;
		    	for (ReceiptDtl temp : receiptDtlList) {
					
		    		if(temp.getTradeOrderId().equals(receiptTemp.getTradeOrderId())
		    				&& temp.getProductionId().equals(receiptTemp.getProductionId())
		    				&& temp.getVersionNo().equals(receiptTemp.getVersionNo())
		    				&& temp.getFeeNum().equals(receiptTemp.getFeeNum())){
		    			
		    			isAdded = true;
		    			break;
		    		}
				}
		    	if(isAdded == false){
		    		
		    		receiptDtlListAdd.add(receiptTemp);
		    	}
			}
			
			if(!IsEmptyUtil.isEmpty(receiptDtl.getiFee2()) || !IsEmptyUtil.isEmpty(receiptDtl.gettFee2())){
				
				ReceiptDtl receiptTemp = new ReceiptDtl();
				
				receiptTemp.setTradeOrderId(receiptDtl.getTradeOrderId());
				receiptTemp.setPo(receiptDtl.getPo());
				receiptTemp.setContractNo(receiptDtl.getContractNo());
				receiptTemp.setProductionId(receiptDtl.getProductionId());
				receiptTemp.setVersionNo(receiptDtl.getVersionNo());
		    	receiptTemp.setHscode(receiptDtl.getHscode());
		    	receiptTemp.setProductionEName4Export(receiptDtl.getProductionEName4Export());
		    	receiptTemp.setProductionCName4Export(receiptDtl.getProductionCName4Export());
		    	receiptTemp.setVendorId(receiptDtl.getVendorId());
		    	receiptTemp.setVendorName(receiptDtl.getVendorName());
				receiptTemp.setFeeNum("2");
				if(IsEmptyUtil.isEmpty(receiptDtl.gettFee2())){
					
					receiptTemp.setDescriptionE(receiptDtl.getiFeeTitle2());
				}else{
					
					receiptTemp.setDescriptionE(receiptDtl.gettFeeTitle2());
				}
		    	receiptTemp.settAmount(receiptDtl.gettFee2());
		    	receiptTemp.setiAmount(receiptDtl.getiFee2());
		    	
		    	boolean isAdded = false;
		    	for (ReceiptDtl temp : receiptDtlList) {
					
		    		if(temp.getTradeOrderId().equals(receiptTemp.getTradeOrderId())
		    				&& temp.getProductionId().equals(receiptTemp.getProductionId())
		    				&& temp.getVersionNo().equals(receiptTemp.getVersionNo())
		    				&& temp.getFeeNum().equals(receiptTemp.getFeeNum())){
		    			
		    			isAdded = true;
		    			break;
		    		}
				}
		    	if(isAdded == false){
		    		
		    		receiptDtlListAdd.add(receiptTemp);
		    	}
		    	
			}
			if(!IsEmptyUtil.isEmpty(receiptDtl.getiFee3()) || !IsEmptyUtil.isEmpty(receiptDtl.gettFee3())){
				
				ReceiptDtl receiptTemp = new ReceiptDtl();
				
				receiptTemp.setTradeOrderId(receiptDtl.getTradeOrderId());
				receiptTemp.setPo(receiptDtl.getPo());
				receiptTemp.setContractNo(receiptDtl.getContractNo());
				receiptTemp.setProductionId(receiptDtl.getProductionId());
				receiptTemp.setVersionNo(receiptDtl.getVersionNo());
		    	receiptTemp.setHscode(receiptDtl.getHscode());
		    	receiptTemp.setProductionEName4Export(receiptDtl.getProductionEName4Export());
		    	receiptTemp.setProductionCName4Export(receiptDtl.getProductionCName4Export());
		    	receiptTemp.setVendorId(receiptDtl.getVendorId());
		    	receiptTemp.setVendorName(receiptDtl.getVendorName());
				receiptTemp.setFeeNum("3");
				if(IsEmptyUtil.isEmpty(receiptDtl.gettFee3())){
					
					receiptTemp.setDescriptionE(receiptDtl.getiFeeTitle3());
				}else{
					
					receiptTemp.setDescriptionE(receiptDtl.gettFeeTitle3());
				}
		    	receiptTemp.settAmount(receiptDtl.gettFee3());
		    	receiptTemp.setiAmount(receiptDtl.getiFee3());
		    	
		    	boolean isAdded = false;
		    	for (ReceiptDtl temp : receiptDtlList) {
					
		    		if(temp.getTradeOrderId().equals(receiptTemp.getTradeOrderId())
		    				&& temp.getProductionId().equals(receiptTemp.getProductionId())
		    				&& temp.getVersionNo().equals(receiptTemp.getVersionNo())
		    				&& temp.getFeeNum().equals(receiptTemp.getFeeNum())){
		    			
		    			isAdded = true;
		    			break;
		    		}
				}
		    	if(isAdded == false){
		    		
		    		receiptDtlListAdd.add(receiptTemp);
		    	}
		    	
			}
			if(!IsEmptyUtil.isEmpty(receiptDtl.getiFee4()) || !IsEmptyUtil.isEmpty(receiptDtl.gettFee4())){
				
				ReceiptDtl receiptTemp = new ReceiptDtl();
				
				receiptTemp.setTradeOrderId(receiptDtl.getTradeOrderId());
				receiptTemp.setPo(receiptDtl.getPo());
				receiptTemp.setContractNo(receiptDtl.getContractNo());
				receiptTemp.setProductionId(receiptDtl.getProductionId());
				receiptTemp.setVersionNo(receiptDtl.getVersionNo());
		    	receiptTemp.setHscode(receiptDtl.getHscode());
		    	receiptTemp.setProductionEName4Export(receiptDtl.getProductionEName4Export());
		    	receiptTemp.setProductionCName4Export(receiptDtl.getProductionCName4Export());
		    	receiptTemp.setVendorId(receiptDtl.getVendorId());
		    	receiptTemp.setVendorName(receiptDtl.getVendorName());
				receiptTemp.setFeeNum("4");
				if(IsEmptyUtil.isEmpty(receiptDtl.gettFee4())){
					
					receiptTemp.setDescriptionE(receiptDtl.getiFeeTitle4());
				}else{
					
					receiptTemp.setDescriptionE(receiptDtl.gettFeeTitle4());
				}
		    	receiptTemp.settAmount(receiptDtl.gettFee4());
		    	receiptTemp.setiAmount(receiptDtl.getiFee4());
		    	
		    	boolean isAdded = false;
		    	for (ReceiptDtl temp : receiptDtlList) {
					
		    		if(temp.getTradeOrderId().equals(receiptTemp.getTradeOrderId())
		    				&& temp.getProductionId().equals(receiptTemp.getProductionId())
		    				&& temp.getVersionNo().equals(receiptTemp.getVersionNo())
		    				&& temp.getFeeNum().equals(receiptTemp.getFeeNum())){
		    			
		    			isAdded = true;
		    			break;
		    		}
				}
		    	if(isAdded == false){
		    		
		    		receiptDtlListAdd.add(receiptTemp);
		    	}
			}
		}
		
		receipt.getReceiptDtlList().addAll(receiptDtlListAdd);
	}
	
	/**
	 * 添加TITLE
	 * @param receiptDtl
	 */
	private void addTitle(Receipt receipt){
		
		//清除TITLE
    	for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtlList().get(i).getProductionId())){
    			
    			receipt.getReceiptDtlList().remove(i);
    		}
    	}
		
		//前一个订单编号
        String lastTradeOrderId = null;
        int size = receipt.getReceiptDtlList().size();
		for (int i = 0; i < size; i++) {
			
			ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
			
			if(i == 0 || !receiptDtl.getTradeOrderId().equals(lastTradeOrderId)){
	    		ReceiptDtl receiptDtlTtile = new ReceiptDtl();
	    		receiptDtlTtile.setTradeOrderId(receiptDtl.getTradeOrderId());
	    		receiptDtlTtile.setPo(receiptDtl.getPo());
	    		receiptDtlTtile.setContractNo(receiptDtl.getContractNo());
	    		receiptDtlTtile.setTradeOrderCreateDate(receiptDtl.getTradeOrderCreateDate());
	    		receiptDtlTtile.setProductionId(ConstantParam.PRODUCTIONID_4_TITLE);
	    		receipt.getReceiptDtlList().add(receiptDtlTtile);
	    	}
			lastTradeOrderId = receiptDtl.getTradeOrderId();
		}
		
	}
	
	/**
	 * 添加TITLE
	 * @param receiptDtl
	 */
	private void addTitle4C(Receipt receipt){
		
		//清除TITLE
    	for (int i = 0; i < receipt.getReceiptDtl4CList().size(); i++) {
    		
    		if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtl4CList().get(i).getProductionId())){
    			
    			receipt.getReceiptDtl4CList().remove(i);
    		}
    	}
		
		//前一个订单编号
        String lastTradeOrderId = null;
        int size = receipt.getReceiptDtl4CList().size();
		for (int i = 0; i < size; i++) {
			
			ReceiptDtl4C receiptDtl4C = receipt.getReceiptDtl4CList().get(i);
			
			if(i == 0 || !receiptDtl4C.getTradeOrderId().equals(lastTradeOrderId)){
	    		ReceiptDtl4C receiptDtl4CTtile = new ReceiptDtl4C();
	    		receiptDtl4CTtile.setTradeOrderId(receiptDtl4C.getTradeOrderId());
	    		receiptDtl4CTtile.setPo(receiptDtl4C.getPo());
	    		receiptDtl4CTtile.setContractNo(receiptDtl4C.getContractNo());
	    		receiptDtl4CTtile.setTradeOrderCreateDate(receiptDtl4C.getTradeOrderCreateDate());
	    		receiptDtl4CTtile.setProductionId(ConstantParam.PRODUCTIONID_4_TITLE);
	    		receipt.getReceiptDtl4CList().add(receiptDtl4CTtile);
	    	}
			lastTradeOrderId = receiptDtl4C.getTradeOrderId();
		}
		
	}
	
	/**
	 * 生成报关信息
	 * @param receiptDtl
	 */
	private void generateClearanceDtl(Receipt receipt){
		
		for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
			
			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
				
				continue;
			}
			
			boolean hasSameExportKey = false;
			for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
				
				if(clearanceDtl.getExportKey().equals(receiptDtl.getExportKey())){
					
					hasSameExportKey = true;
					break;
				}
			}
			if(hasSameExportKey == false){
				
				ClearanceDtl clearanceDtl = new ClearanceDtl();
				clearanceDtl.setHscode(receiptDtl.getHscode());
				clearanceDtl.setShortLocation(receiptDtl.getShortLocation());
				clearanceDtl.setProductionEName4Export(receiptDtl.getProductionEName4Export());
				clearanceDtl.setProductionCName4Export(receiptDtl.getProductionCName4Export());
				receipt.getClearanceDtlList().add(clearanceDtl);
			}
		}
		
		for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
			
			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
				
				continue;
			}
			
			boolean hasSameExportKey = false;
			for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
				
				if(clearanceDtl.getExportKey().equals(receiptDtl4C.getExportKey())){
					
					hasSameExportKey = true;
					break;
				}
			}
			if(hasSameExportKey == false){
				
				ClearanceDtl clearanceDtl = new ClearanceDtl();
				clearanceDtl.setHscode(receiptDtl4C.getHscode());
				clearanceDtl.setShortLocation(receiptDtl4C.getShortLocation());
				clearanceDtl.setProductionEName4Export(receiptDtl4C.getProductionEName4Export());
				clearanceDtl.setProductionCName4Export(receiptDtl4C.getProductionCName4Export());
				receipt.getClearanceDtlList().add(clearanceDtl);
			}
		}
	}
	
	/**
	 * 发货单计算
	 * @param receipt
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/calculate")
	public ModelAndView calculatePost(@Valid @ModelAttribute("receipt")Receipt receipt, BindingResult br, HttpSession session, @RequestParam("pageMode")String pageMode) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		
		ModelAndView mav = new ModelAndView();
		
		if(pageMode.equals("add")){
			mav.setViewName("receiptAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		}else{
			mav.setViewName("receiptUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		}
		
		try {
			//输入验证
			validate(br, receipt);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//排序
		        Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
				
				//计算
				calculate(receipt);
				
		        //排序
		        Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
		        
				mav.addObject("receipt", receipt);
			} else {
				mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
			}
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	private Receipt calculate(Receipt receipt){
		
		BigDecimal quantityTtl = new BigDecimal(0);
		BigDecimal RMBTtl = new BigDecimal(0);
		BigDecimal USDTtl = new BigDecimal(0);
		
		BigDecimal quantityTtl4T = new BigDecimal(0);
		BigDecimal RMBTtl4T = new BigDecimal(0);
		BigDecimal USDTtl4T = new BigDecimal(0);

		BigDecimal quantityTtl4C = new BigDecimal(0);
		BigDecimal RMBTtl4C = new BigDecimal(0);
		BigDecimal USDTtl4C = new BigDecimal(0);
		
		BigDecimal quantityCTtl = new BigDecimal(0);
		
		//计算上部区域
		for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {

			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
				
				continue;
			}
			
			if(!"0".equals(receiptDtl.getFeeNum())){
				
				//各种费用计算
				if(!IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
					
					//RMB小计
					BigDecimal RMB = new BigDecimal(receiptDtl.getiAmount());
					RMBTtl = RMBTtl.add(RMB);
					RMBTtl4T = RMBTtl4T.add(RMB);
				}
				
				if(!IsEmptyUtil.isEmpty(receiptDtl.gettAmount())){
					
					//美金小计
					BigDecimal USD = new BigDecimal(receiptDtl.gettAmount());
					USDTtl = USDTtl.add(USD);
					USDTtl4T = USDTtl4T.add(USD);
				}
			}else{
				
				//发货单数量
				BigDecimal quantity =  new BigDecimal(receiptDtl.getQuantity());
				//RMB小计
				BigDecimal RMB = new BigDecimal(receiptDtl.getiUnitPrice()).multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
				//美金小计
				BigDecimal USD = new BigDecimal(receiptDtl.gettUnitPrice()).multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				receiptDtl.setiAmount(StringHandler.numFormat(RMB, StringHandler.NUMBER_FORMAT2));
				receiptDtl.settAmount(StringHandler.numFormat(USD, StringHandler.NUMBER_FORMAT2));
				
				quantityTtl = quantityTtl.add(quantity);
				quantityTtl4T = quantityTtl4T.add(quantity);
				
				if(!"0".equals(receiptDtl.getOutside())){
					quantityCTtl = quantityCTtl.add(quantity);
				}
				RMBTtl = RMBTtl.add(RMB);
				RMBTtl4T = RMBTtl4T.add(RMB);
				USDTtl = USDTtl.add(USD);
				USDTtl4T = USDTtl4T.add(USD);
				
				//箱数
				BigDecimal outside = new BigDecimal(receiptDtl.getOutside());
				BigDecimal boxAmount = "0".equals(receiptDtl.getOutside()) ? new BigDecimal("0") : quantity.divide(outside, 0, BigDecimal.ROUND_CEILING);
				receiptDtl.setBoxAmount(StringHandler.numFormat(boxAmount, StringHandler.NUMBER_FORMAT1));
				//毛重
				receiptDtl.setGrossWeightTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl.getGrossWeight())), StringHandler.NUMBER_FORMAT2));
				//净重
				receiptDtl.setNetWeightTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl.getNetWeight())), StringHandler.NUMBER_FORMAT2));
				//体积
				receiptDtl.setVolumeTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl.getVolume())), StringHandler.NUMBER_FORMAT4));
			}
			
			//取得cAmount
			if("1".equals(receipt.getClearanceMode())){
				
				receiptDtl.setcAmount(null);
			}else{
				
				for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
					
					if(clearanceDtl.getExportKey().equals(receiptDtl.getExportKey())){
						
						receiptDtl.setcAmount(clearanceDtl.getAmount());
						break;
					}
				}
			}
		}
		
		for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {

			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
				
				continue;
			}
			
			//发货单数量
			BigDecimal quantity =  new BigDecimal(receiptDtl4C.getQuantity());
			//RMB小计
			BigDecimal RMB = new BigDecimal(receiptDtl4C.getiUnitPrice()).multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
			//美金小计
			BigDecimal USD = new BigDecimal(receiptDtl4C.gettUnitPrice()).multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			receiptDtl4C.setiAmount(StringHandler.numFormat(RMB, StringHandler.NUMBER_FORMAT2));
			receiptDtl4C.settAmount(StringHandler.numFormat(USD, StringHandler.NUMBER_FORMAT2));
			
			quantityTtl = quantityTtl.add(quantity);
			quantityTtl4C = quantityTtl4C.add(quantity);
			if(!"0".equals(receiptDtl4C.getOutside())){
				quantityCTtl = quantityCTtl.add(quantity);
			}
			RMBTtl = RMBTtl.add(RMB);
			RMBTtl4C = RMBTtl4C.add(RMB);
			USDTtl = USDTtl.add(USD);
			USDTtl4C = USDTtl4C.add(USD);
			
			//箱数
			BigDecimal boxAmount = new BigDecimal(receiptDtl4C.getBoxAmount());
			//毛重
			receiptDtl4C.setGrossWeightTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl4C.getGrossWeight())), StringHandler.NUMBER_FORMAT2));
			//净重
			receiptDtl4C.setNetWeightTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl4C.getNetWeight())), StringHandler.NUMBER_FORMAT2));
			//体积
			receiptDtl4C.setVolumeTtl(StringHandler.numFormat(boxAmount.multiply(new BigDecimal(receiptDtl4C.getVolume())), StringHandler.NUMBER_FORMAT4));
			
			//取得cAmount
			if("1".equals(receipt.getClearanceMode())){
				
				receiptDtl4C.setcAmount(null);
			}else{
				
				for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
					
					if(clearanceDtl.getExportKey().equals(receiptDtl4C.getExportKey())){
						
						receiptDtl4C.setcAmount(clearanceDtl.getAmount());
						break;
					}
				}
			}
		}
		
		receipt.setQuantityTtl(StringHandler.numFormat(quantityTtl, StringHandler.NUMBER_FORMAT1));
		receipt.setiAmountTtl(StringHandler.numFormat(RMBTtl, StringHandler.NUMBER_FORMAT2));
		receipt.settAmountTtl(StringHandler.numFormat(USDTtl, StringHandler.NUMBER_FORMAT2));
		
		receipt.setQuantityTtl4T(StringHandler.numFormat(quantityTtl4T, StringHandler.NUMBER_FORMAT1));
		receipt.setiAmountTtl4T(StringHandler.numFormat(RMBTtl4T, StringHandler.NUMBER_FORMAT2));
		receipt.settAmountTtl4T(StringHandler.numFormat(USDTtl4T, StringHandler.NUMBER_FORMAT2));
		
		receipt.setQuantityTtl4C(StringHandler.numFormat(quantityTtl4C, StringHandler.NUMBER_FORMAT1));
		receipt.setiAmountTtl4C(StringHandler.numFormat(RMBTtl4C, StringHandler.NUMBER_FORMAT2));
		receipt.settAmountTtl4C(StringHandler.numFormat(USDTtl4C, StringHandler.NUMBER_FORMAT2));
		
		receipt.setQuantityCTtl(StringHandler.numFormat(quantityCTtl, StringHandler.NUMBER_FORMAT1));
		
		//计算下部区域
		if("1".equals(receipt.getReceiptMode())){
			
			BigDecimal boxAmountTtl = new BigDecimal(0);
			BigDecimal grossWeightTtl = new BigDecimal(0);
			BigDecimal netWeightTtl = new BigDecimal(0);
			BigDecimal volumeTtl = new BigDecimal(0);
			BigDecimal amountTtl4Export = new BigDecimal(0);
			
			//清空下部区域
			receipt.setClearanceDtlList(new ArrayList<ClearanceDtl>());
			
			//订单产品部分
			for (ReceiptDtl receiptDtl : receipt.getReceiptDtlList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())){
					
					continue;
				}
				
				boolean hasSameExportKey = false;
				for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
					
					if(clearanceDtl.getExportKey().equals(receiptDtl.getExportKey())){
						
						//各种费用只计算金额
						if(!"0".equals(receiptDtl.getFeeNum())){
							
							if(!IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
								
								//RMB小计
								BigDecimal RMB = new BigDecimal(receiptDtl.getiAmount());
								clearanceDtl.setAmountRMB(StringHandler.numFormat(RMB.add(clearanceDtl.getAmountRMB() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmountRMB())), StringHandler.NUMBER_FORMAT2));
								//报关小计
								if("1".equals(receipt.getClearanceMode())){
									
									if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
											&& RMBTtl.compareTo(new BigDecimal(0)) > 0
											&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
										BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
										clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
									}
								}else{
									
									clearanceDtl.setAmount(receiptDtl.getcAmount());
								}
								//报关单价
								if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
									
									BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
									clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
								}
							}
						}else{
							
							//发货单数量
							BigDecimal quantity =  new BigDecimal("0".equals(receiptDtl.getOutside()) ? "0" : receiptDtl.getQuantity());
							clearanceDtl.setQuantity(StringHandler.numFormat(quantity.add(clearanceDtl.getQuantity() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getQuantity())), StringHandler.NUMBER_FORMAT1));
							//RMB小计
							if(!IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
								
								BigDecimal RMB = new BigDecimal(receiptDtl.getiAmount());
								clearanceDtl.setAmountRMB(StringHandler.numFormat(RMB.add(clearanceDtl.getAmountRMB() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmountRMB())), StringHandler.NUMBER_FORMAT2));
							}
							//报关小计
							if("1".equals(receipt.getClearanceMode())){
								
								if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
										&& RMBTtl.compareTo(new BigDecimal(0)) > 0
										&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
									BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
									clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
								}
							}else{
								
								clearanceDtl.setAmount(receiptDtl.getcAmount());
							}
							//报关单价
							if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
								
								BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
								clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
							}
							//箱数
							BigDecimal boxAmount = new BigDecimal(receiptDtl.getBoxAmount());
							clearanceDtl.setBoxAmount(StringHandler.numFormat(boxAmount.add(clearanceDtl.getBoxAmount() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT1));
							//毛重
							BigDecimal grossWeight = new BigDecimal(receiptDtl.getGrossWeightTtl());
							clearanceDtl.setGrossWeight(StringHandler.numFormat(grossWeight.add(clearanceDtl.getGrossWeight() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getGrossWeight())), StringHandler.NUMBER_FORMAT2));
							//净重
							BigDecimal netWeight = new BigDecimal(receiptDtl.getNetWeightTtl());
							clearanceDtl.setNetWeight(StringHandler.numFormat(netWeight.add(clearanceDtl.getNetWeight() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getNetWeight())), StringHandler.NUMBER_FORMAT2));
							//体积
							BigDecimal volume = new BigDecimal(receiptDtl.getVolumeTtl());
							clearanceDtl.setVolume(StringHandler.numFormat(volume.add(clearanceDtl.getVolume() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getVolume())), StringHandler.NUMBER_FORMAT4));
							//境内货源地
//							if(!IsEmptyUtil.isEmpty(receiptDtl.getShortLocation()) && !clearanceDtl.getShortLocation().contains(receiptDtl.getShortLocation())){
//								
//								clearanceDtl.setShortLocation(clearanceDtl.getShortLocation() + " " + receiptDtl.getShortLocation());
//							}
							
							boxAmountTtl = boxAmountTtl.add(boxAmount);
							grossWeightTtl = grossWeightTtl.add(grossWeight);
							netWeightTtl = netWeightTtl.add(netWeight);
							volumeTtl = volumeTtl.add(volume);
						}
						
						hasSameExportKey = true;
						break;
					}
				}
				if(hasSameExportKey == false){
					
					ClearanceDtl clearanceDtl = new ClearanceDtl();
					clearanceDtl.setHscode(receiptDtl.getHscode());
					clearanceDtl.setShortLocation(receiptDtl.getShortLocation());
					clearanceDtl.setProductionEName4Export(receiptDtl.getProductionEName4Export());
					clearanceDtl.setProductionCName4Export(receiptDtl.getProductionCName4Export());
					
					//各种费用只计算金额
					if(!"0".equals(receiptDtl.getFeeNum())){
						
						if(!IsEmptyUtil.isEmpty(receiptDtl.getiAmount())){
							
							//RMB小计
							clearanceDtl.setAmountRMB(receiptDtl.getiAmount());
							//报关小计
							if("1".equals(receipt.getClearanceMode())){
								
								if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
										&& RMBTtl.compareTo(new BigDecimal(0)) > 0
										&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
									BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
									clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
								}
							}else{
								
								clearanceDtl.setAmount(receiptDtl.getcAmount());
								//报关总计合计
								amountTtl4Export = amountTtl4Export.add(clearanceDtl.getAmount() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmount()));
							}
							//报关单价
							if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
								
								BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
								clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
							}
						}
					}else{
						
						//发货单数量
						//clearanceDtl.setQuantity("0".equals(receiptDtl.getOutside()) ? "0" : receiptDtl.getQuantity());
						clearanceDtl.setQuantity(receiptDtl.getQuantity());
						//RMB小计
						clearanceDtl.setAmountRMB(receiptDtl.getiAmount());
						//报关小计
						if("1".equals(receipt.getClearanceMode())){
							
							if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
									&& RMBTtl.compareTo(new BigDecimal(0)) > 0
									&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
								BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
								clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
							}
						}else{
							
							clearanceDtl.setAmount(receiptDtl.getcAmount());
							//报关总计合计
							amountTtl4Export = amountTtl4Export.add(clearanceDtl.getAmount() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmount()));
						}
						//报关单价
						if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
							
							BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
							clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
						}
						//箱数
						BigDecimal boxAmount = new BigDecimal(receiptDtl.getBoxAmount());
						clearanceDtl.setBoxAmount(StringHandler.numFormat(boxAmount, StringHandler.NUMBER_FORMAT1));
						//毛重
						BigDecimal grossWeight = new BigDecimal(receiptDtl.getGrossWeightTtl());
						clearanceDtl.setGrossWeight(StringHandler.numFormat(grossWeight, StringHandler.NUMBER_FORMAT2));
						//净重
						BigDecimal netWeight = new BigDecimal(receiptDtl.getNetWeightTtl());
						clearanceDtl.setNetWeight(StringHandler.numFormat(netWeight, StringHandler.NUMBER_FORMAT2));
						//体积
						BigDecimal volume = new BigDecimal(receiptDtl.getVolumeTtl());
						clearanceDtl.setVolume(StringHandler.numFormat(volume, StringHandler.NUMBER_FORMAT4));
						//境内货源地
//						if(!IsEmptyUtil.isEmpty(receiptDtl.getShortLocation()) && !clearanceDtl.getShortLocation().contains(receiptDtl.getShortLocation())){
//							
//							clearanceDtl.setShortLocation(clearanceDtl.getShortLocation() + " " + receiptDtl.getShortLocation());
//						}
						
						boxAmountTtl = boxAmountTtl.add(boxAmount);
						grossWeightTtl = grossWeightTtl.add(grossWeight);
						netWeightTtl = netWeightTtl.add(netWeight);
						volumeTtl = volumeTtl.add(volume);
					}
					
					receipt.getClearanceDtlList().add(clearanceDtl);
				}
			}
			
			//投诉单产品部分
			for (ReceiptDtl4C receiptDtl4C : receipt.getReceiptDtl4CList()) {
				
				if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
					
					continue;
				}
				
				boolean hasSameExportKey = false;
				for(ClearanceDtl clearanceDtl : receipt.getClearanceDtlList()){
					
					if(clearanceDtl.getExportKey().equals(receiptDtl4C.getExportKey())){
						
						//发货单数量
						BigDecimal quantity =  new BigDecimal("0".equals(receiptDtl4C.getOutside()) ? "0" : receiptDtl4C.getQuantity());
						clearanceDtl.setQuantity(StringHandler.numFormat(quantity.add(clearanceDtl.getQuantity() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getQuantity())), StringHandler.NUMBER_FORMAT1));
						//RMB小计
						if(!IsEmptyUtil.isEmpty(receiptDtl4C.getiAmount())){
							
							BigDecimal RMB = new BigDecimal(receiptDtl4C.getiAmount());
							clearanceDtl.setAmountRMB(StringHandler.numFormat(RMB.add(clearanceDtl.getAmountRMB() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmountRMB())), StringHandler.NUMBER_FORMAT2));
						}
						//报关小计
						if("1".equals(receipt.getClearanceMode())){
							
							if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
									&& RMBTtl.compareTo(new BigDecimal(0)) > 0
									&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
								BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
								clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
							}
						}else{
							
							clearanceDtl.setAmount(receiptDtl4C.getcAmount());
						}
						//报关单价
						if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
							
							BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
							clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
						}
						//箱数
						BigDecimal boxAmount = new BigDecimal(receiptDtl4C.getBoxAmount());
						clearanceDtl.setBoxAmount(StringHandler.numFormat(boxAmount.add(clearanceDtl.getBoxAmount() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT1));
						//毛重
						BigDecimal grossWeight = new BigDecimal(receiptDtl4C.getGrossWeightTtl());
						clearanceDtl.setGrossWeight(StringHandler.numFormat(grossWeight.add(clearanceDtl.getGrossWeight() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getGrossWeight())), StringHandler.NUMBER_FORMAT2));
						//净重
						BigDecimal netWeight = new BigDecimal(receiptDtl4C.getNetWeightTtl());
						clearanceDtl.setNetWeight(StringHandler.numFormat(netWeight.add(clearanceDtl.getNetWeight() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getNetWeight())), StringHandler.NUMBER_FORMAT2));
						//体积
						BigDecimal volume = new BigDecimal(receiptDtl4C.getVolumeTtl());
						clearanceDtl.setVolume(StringHandler.numFormat(volume.add(clearanceDtl.getVolume() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getVolume())), StringHandler.NUMBER_FORMAT4));
						//境内货源地
//						if(!IsEmptyUtil.isEmpty(receiptDtl4C.getShortLocation()) && !clearanceDtl.getShortLocation().contains(receiptDtl4C.getShortLocation())){
//							
//							clearanceDtl.setShortLocation(clearanceDtl.getShortLocation() + " " + receiptDtl4C.getShortLocation());
//						}
						
						boxAmountTtl = boxAmountTtl.add(boxAmount);
						grossWeightTtl = grossWeightTtl.add(grossWeight);
						netWeightTtl = netWeightTtl.add(netWeight);
						volumeTtl = volumeTtl.add(volume);
						
						hasSameExportKey = true;
						break;
					}
				}
				
				if(hasSameExportKey == false){
					
					ClearanceDtl clearanceDtl = new ClearanceDtl();
					clearanceDtl.setHscode(receiptDtl4C.getHscode());
					clearanceDtl.setShortLocation(receiptDtl4C.getShortLocation());
					clearanceDtl.setProductionEName4Export(receiptDtl4C.getProductionEName4Export());
					clearanceDtl.setProductionCName4Export(receiptDtl4C.getProductionCName4Export());
					
					//发货单数量
					//clearanceDtl.setQuantity("0".equals(receiptDtl4C.getOutside()) ? "0" : receiptDtl4C.getQuantity());
					clearanceDtl.setQuantity(receiptDtl4C.getQuantity());
					//RMB小计
					clearanceDtl.setAmountRMB(receiptDtl4C.getiAmount());
					//报关小计
					if("1".equals(receipt.getClearanceMode())){
						
						if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmountRMB())
								&& RMBTtl.compareTo(new BigDecimal(0)) > 0
								&& !IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
							BigDecimal amount4Export = new BigDecimal(clearanceDtl.getAmountRMB()).divide(RMBTtl, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(receipt.getAmountTtl4Export()));
							clearanceDtl.setAmount(StringHandler.numFormat(amount4Export, StringHandler.NUMBER_FORMAT2));
						}
					}else{
						
						clearanceDtl.setAmount(receiptDtl4C.getcAmount());
						//报关总计合计
						amountTtl4Export = amountTtl4Export.add(clearanceDtl.getAmount() == null ? new BigDecimal(0) : new BigDecimal(clearanceDtl.getAmount()));
					}
					//报关单价
					if(!IsEmptyUtil.isEmpty(clearanceDtl.getAmount()) && !IsEmptyUtil.isEmpty(clearanceDtl.getQuantity())){
						
						BigDecimal unitPrice4Export = new BigDecimal(clearanceDtl.getAmount()).divide(new BigDecimal(clearanceDtl.getQuantity()), 4, BigDecimal.ROUND_HALF_UP);
						clearanceDtl.setUnitPrice(StringHandler.numFormat(unitPrice4Export, StringHandler.NUMBER_FORMAT2));
					}
					//箱数
					BigDecimal boxAmount = new BigDecimal(receiptDtl4C.getBoxAmount());
					clearanceDtl.setBoxAmount(StringHandler.numFormat(boxAmount, StringHandler.NUMBER_FORMAT1));
					//毛重
					BigDecimal grossWeight = new BigDecimal(receiptDtl4C.getGrossWeightTtl());
					clearanceDtl.setGrossWeight(StringHandler.numFormat(grossWeight, StringHandler.NUMBER_FORMAT2));
					//净重
					BigDecimal netWeight = new BigDecimal(receiptDtl4C.getNetWeightTtl());
					clearanceDtl.setNetWeight(StringHandler.numFormat(netWeight, StringHandler.NUMBER_FORMAT2));
					//体积
					BigDecimal volume = new BigDecimal(receiptDtl4C.getVolumeTtl());
					clearanceDtl.setVolume(StringHandler.numFormat(volume, StringHandler.NUMBER_FORMAT4));
					//境内货源地
//					if(!IsEmptyUtil.isEmpty(receiptDtl4C.getShortLocation()) && !clearanceDtl.getShortLocation().contains(receiptDtl4C.getShortLocation())){
//						
//						clearanceDtl.setShortLocation(clearanceDtl.getShortLocation() + " " + receiptDtl4C.getShortLocation());
//					}
					
					boxAmountTtl = boxAmountTtl.add(boxAmount);
					grossWeightTtl = grossWeightTtl.add(grossWeight);
					netWeightTtl = netWeightTtl.add(netWeight);
					volumeTtl = volumeTtl.add(volume);
					
					receipt.getClearanceDtlList().add(clearanceDtl);
				}
			}
			receipt.setBoxAmountTtl(StringHandler.numFormat(boxAmountTtl, StringHandler.NUMBER_FORMAT1));
			receipt.setGrossWeightTtl(StringHandler.numFormat(grossWeightTtl, StringHandler.NUMBER_FORMAT2));
			receipt.setNetWeightTtl(StringHandler.numFormat(netWeightTtl, StringHandler.NUMBER_FORMAT2));
			receipt.setVolumeTtl(StringHandler.numFormat(volumeTtl, StringHandler.NUMBER_FORMAT4));
			
			if("2".equals(receipt.getClearanceMode()) && amountTtl4Export.compareTo(new BigDecimal(0)) != 0){
				
				receipt.setAmountTtl4Export(StringHandler.numFormat(amountTtl4Export, StringHandler.NUMBER_FORMAT2));
			}
		}else{
			
			generateClearanceDtl(receipt);
			receipt.setAmountTtl4Export(null);
		}
		
		return receipt;
	}
	
	/**
	 * 发货单删除订单产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/deleteProduction")
	public ModelAndView deleteProduction(@Valid @ModelAttribute("receipt")Receipt receipt, BindingResult br,
			@RequestParam("tradeOrderId")String tradeOrderId, 
			@RequestParam("productionId")String productionId, 
			@RequestParam("versionNo")String versionNo, 
			@RequestParam("feeNum")String feeNum, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//删除发货单中该产品的各种费用
		if("0".equals(feeNum)){
			
			for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
				
				if(receipt.getReceiptDtlList().get(i).getTradeOrderId().equals(tradeOrderId)
						&& receipt.getReceiptDtlList().get(i).getProductionId().equals(productionId)
						&& receipt.getReceiptDtlList().get(i).getVersionNo().equals(versionNo)
						&& !"0".equals(receipt.getReceiptDtlList().get(i).getFeeNum())){
					
					receipt.getReceiptDtlList().remove(i--);
				}
			}
		}
		
		//删除发货单中该产品
		for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
			if(receipt.getReceiptDtlList().get(i).getTradeOrderId().equals(tradeOrderId)
					&& receipt.getReceiptDtlList().get(i).getProductionId().equals(productionId)
					&& receipt.getReceiptDtlList().get(i).getVersionNo().equals(versionNo)
					&& receipt.getReceiptDtlList().get(i).getFeeNum().equals(feeNum)){
				
				//产品的话需要添加到被删除明细LIST
				if("0".equals(receipt.getReceiptDtlList().get(i).getFeeNum())){
					
					receipt.getReceiptDtlRemoveList().add(receipt.getReceiptDtlList().get(i));
				}
				
				//（最后一个或者下一个是TITLE）并且上一个也是TITLE的情况，删除上一个和当前，否则删除当前
				if((i + 1 == receipt.getReceiptDtlList().size()
					|| ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtlList().get(i + 1).getProductionId()))
					&& ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtlList().get(i - 1).getProductionId())){
					
					receipt.getReceiptDtlList().remove(i);
					receipt.getReceiptDtlList().remove(i - 1);
				}else{
					
					receipt.getReceiptDtlList().remove(i);
				}
				
				break;
			}
		}
		
		try {
			//输入验证
			validate(br, receipt);
			
		} catch (Exception e) {
			//什么都不做
		}
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//排序
			Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
			
			//计算
			calculate(receipt);
			mav.addObject("receipt", receipt);
		}
        
        //排序
        Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
		
		if(pageMode.equals("add")){
			mav.setViewName("receiptAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		}else{
			mav.setViewName("receiptUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		}
		mav.addObject("receipt", receipt);
		return mav;
	}
	
	/**
	 * 发货单删除投诉单产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/deleteProduction4C")
	public ModelAndView deleteProduction4C(@Valid @ModelAttribute("receipt")Receipt receipt, BindingResult br,
			@RequestParam("complaintId")String complaintId, 
			@RequestParam("tradeOrderId")String tradeOrderId, 
			@RequestParam("productionId")String productionId, 
			@RequestParam("versionNo")String versionNo, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//删除发货单中该产品
		for (int i = 0; i < receipt.getReceiptDtl4CList().size(); i++) {
			
			if(receipt.getReceiptDtl4CList().get(i).getTradeOrderId().equals(tradeOrderId)
					&& receipt.getReceiptDtl4CList().get(i).getProductionId().equals(productionId)
					&& receipt.getReceiptDtl4CList().get(i).getVersionNo().equals(versionNo)
					&& receipt.getReceiptDtl4CList().get(i).getComplaintId().equals(complaintId)){
					
				//（最后一个或者下一个是TITLE）并且上一个也是TITLE的情况，删除上一个和当前，否则删除当前
				if((i + 1 == receipt.getReceiptDtl4CList().size()
					|| ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtl4CList().get(i + 1).getProductionId()))
					&& ConstantParam.PRODUCTIONID_4_TITLE.equals(receipt.getReceiptDtl4CList().get(i - 1).getProductionId())){
					
					receipt.getReceiptDtl4CList().remove(i);
					receipt.getReceiptDtl4CList().remove(i - 1);
				}else{
					
					receipt.getReceiptDtl4CList().remove(i);
				}
				
				break;
			}
		}
		
		try {
			//输入验证
			validate(br, receipt);
			
		} catch (Exception e) {
			//什么都不做
		}
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//排序
			Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
			
			//计算
			calculate(receipt);
			mav.addObject("receipt", receipt);
		}
        
        //排序
        Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
		
		if(pageMode.equals("add")){
			mav.setViewName("receiptAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		}else{
			mav.setViewName("receiptUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		}
		mav.addObject("receipt", receipt);
		return mav;
	}
	
	/**
	 * 发货单检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_receipt");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("receipt", new Receipt());
		mav.setViewName("receiptSearch");
		return mav;
	}
	
	/**
	 * 发货单检索
	 * @param receipt
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Receipt receipt, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(receipt.getIsBack())
				&& null != session.getAttribute("searchCondition_receipt")){
			
			receipt = (Receipt)session.getAttribute("searchCondition_receipt");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_receipt", receipt);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			receipt = receiptService.receiptSearch(receipt);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("receipt", receipt);
		mav.setViewName("receiptSearch");
		
		return mav;
	}
	
	/**
	 * 发货单照会画面初期化
	 * @param receiptIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("receiptIdSelected")String receiptIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTREF);
		
		ModelAndView mav = new ModelAndView();
		try {
			Receipt receipt = receiptService.findByKey(receiptIdSelected);
			if(receipt != null){
				
				receipt.setReceiptDtlList(receiptService.findDtlByKey(receipt.getReceiptId()));
				receipt.setReceiptDtl4CList(receiptService.findDtl4CByKey(receipt.getReceiptId()));
			}
			
			//排序
			Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
			Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
			
			//添加TITLE
			addTitle(receipt);
			addTitle4C(receipt);
			
			//排序
	        Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
	        Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
            
            //发货单计算
            calculate(receipt);
            
            //排序
            Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
			
			mav.addObject("receipt", receipt);
			mav.setViewName("receiptRef");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Receipt receipt = new Receipt();
			receipt.setIsBack("1");
			mav.addObject("receipt", receipt);
			mav.setViewName("forward:/receipt/search");
		}
		return mav;
	}
	
	/**
	 * 发货单追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		
		ModelAndView mav = new ModelAndView();
		Receipt receipt = new Receipt();
		mav.addObject("receipt", receipt);
		mav.setViewName("receiptAdd");
		return mav;
	}
	
	/**
	 * 发货单追加
	 * @param receipt
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("receipt")Receipt receipt, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("receiptAdd");
		
		try {
			//输入验证
			validate4Save(br, receipt);
			validate(br, receipt);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//计算
				calculate(receipt);
				//发货单登録
				String newReceiptId = receiptService.add(receipt);
				//发货单登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_RECEIPT + newReceiptId);
				mav.setViewName("forward:/receipt/searchShow");
			} else {
				tokenReset(mav, session);
				mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
			}
		} catch (Exception e) {
			tokenReset(mav, session);
			mav.addObject("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 发货单变更画面初期化
	 * @param receiptIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView receiptUpdateGet(@RequestParam("receiptIdSelected")String receiptIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		
		ModelAndView mav = new ModelAndView();
		try {
			Receipt receipt = receiptService.findByKey(receiptIdSelected);
			if(receipt != null){
				
				receipt.setReceiptDtlList(receiptService.findDtlByKey(receipt.getReceiptId()));
				receipt.setReceiptDtl4CList(receiptService.findDtl4CByKey(receipt.getReceiptId()));
			}
			//排序
			Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
			Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
			
			//添加TITLE
			addTitle(receipt);
			addTitle4C(receipt);
			
			//排序
	        Collections.sort(receipt.getReceiptDtlList(), ReceiptController.comparatorReceiptDtl);
	        Collections.sort(receipt.getReceiptDtl4CList(), ReceiptController.comparatorReceiptDtl4C);
			
            //发货单计算
            calculate(receipt);
            
            //排序
            Collections.sort(receipt.getClearanceDtlList(), ReceiptController.comparatorClearanceDtl);
			
			mav.addObject("receipt", receipt);
			mav.setViewName("receiptUpdate");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Receipt receipt = new Receipt();
			receipt.setIsBack("1");
			mav.addObject("receipt", receipt);
			mav.setViewName("forward:/receipt/search");
		}
		return mav;
	}
	
	/**
	 * 发货单变更
	 * @param receipt
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView receiptUpdate(@Valid @ModelAttribute("receipt")Receipt receipt, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "receiptManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_RECEIPTUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("receiptUpdate");
		
		//入力チェック
		try {
			validate4Save(br, receipt);
			validate(br, receipt);
		
			//入力チェック結果がOKの場合
			if(!br.hasErrors()){
				
					//计算
					calculate(receipt);
					//发货单更新
					receiptService.update(receipt);
					
					//发货单更新完了、返回检索画面
					mav.addObject("errorMessage", Message.SUCCESS_UPDATE_RECEIPT + receipt.getReceiptId());
					mav.setViewName("forward:/receipt/searchShow");
				
			} else {
				tokenReset(mav, session);
				mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tokenReset(mav, session);
			mav.addObject("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	
	/**
	 * 发货单削除
	 * @param receiptIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("receiptIdSelected")String receiptIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//发货单削除
			receiptService.delete(receiptIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_RECEIPT + receiptIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/receipt/searchShow");
		return mav;
	}
	
	/**
	 * 下载
	 * @param receipt
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/download")
	public void download(Receipt receipt, HttpServletResponse response) throws IOException {  
		
		OutputStream os = response.getOutputStream();  
	    try {  
	    	String fileName = StringHandler.convertStringToUTF8(receipt.getReceiptNo()) + "_" + StringHandler.convertStringToUTF8(receipt.getCustomerName()) + "_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx";
	    	String fileFullPath = receiptExport.generateFile(receipt, fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
			
	    	e.printStackTrace();
		} finally {  
	        if (os != null) {  
	            os.close();  
	        }  
	    }  
	}
	
	/**
	 * 发货单返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Receipt receipt = new Receipt();
		receipt.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/receipt/search");
		mav.addObject("receipt", receipt);
		return mav;
	}
	
	/**
	 * 发货单保存验证
	 * @param receipt
	 * @param br
	 */
	private void validate4Save(BindingResult br, Receipt receipt) throws Exception{
		
		//日期
		if(IsEmptyUtil.isEmpty(receipt.getReceiptDate())){
			
			br.rejectValue("receiptDate", "", Message.EMPTY_ERROR);  
		}
		if(!StringHandler.isDate(receipt.getReceiptDate())){
			
			br.rejectValue("receiptDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//发票号
		if(IsEmptyUtil.isEmpty(receipt.getReceiptNo())
				&& (!IsEmptyUtil.isEmpty(receipt.getReceiptId()) 
					|| "0".equals(receipt.getReceiptMode()))){
			
			br.rejectValue("receiptNo", "", Message.EMPTY_ERROR);  
		}
		if("0".equals(receipt.getReceiptMode())){
			
			if(!IsEmptyUtil.isEmpty(receipt.getReceiptNo()) && StringHandler.isReceiptNo(receipt.getReceiptNo())){
				
				br.rejectValue("receiptNo", "", Message.RECEIPTNO_ERROR_0);  
			}
		}else{
			
			if(!StringHandler.isReceiptNo(receipt.getReceiptNo())){
				
				br.rejectValue("receiptNo", "", Message.RECEIPTNO_ERROR_1);  
			}
		}
		
		//客户
		if(IsEmptyUtil.isEmpty(receipt.getCustomerId())){
			
			br.rejectValue("customerId", "", Message.EMPTY_ERROR);  
		}
		//ETD
		if(!StringHandler.isDate(receipt.getETD())){
			
			br.rejectValue("ETD", "", Message.DATE_IUPUT_ERROR);  
		}
		//ETA
		if(!StringHandler.isDate(receipt.getETA())){
			
			br.rejectValue("ETA", "", Message.DATE_IUPUT_ERROR);  
		}
		//应收汇金额
		if(!StringHandler.isDouble(receipt.getRemint(), 2)){
			
			br.rejectValue("remint", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		//预计货好日期
		if(!StringHandler.isDate(receipt.getGoodTime())){
			
			br.rejectValue("goodTime", "", Message.DATE_IUPUT_ERROR);  
		}
		//预计进账日期
		if(!StringHandler.isDate(receipt.getPlanPostingDate())){
			
			br.rejectValue("planPostingDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//实际进账日期
		if(!StringHandler.isDate(receipt.getPostingDate())){
			
			br.rejectValue("postingDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//电放日期
		if(!StringHandler.isDate(receipt.getTelexRelease())){
			
			br.rejectValue("telexRelease", "", Message.DATE_IUPUT_ERROR);  
		}
		//利润
		if(!StringHandler.isDouble(receipt.getProfit(), 2)){
			
			br.rejectValue("profit", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		//实际进账金额 
		if(!StringHandler.isDouble(receipt.getPostingAmount(), 2)){
			
			br.rejectValue("postingAmount", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		//报关总价
		if("1".equals(receipt.getReceiptMode())){
			
			if("1".equals(receipt.getClearanceMode())){
				
				if(IsEmptyUtil.isEmpty(receipt.getAmountTtl4Export())){
					
					br.rejectValue("amountTtl4Export", "", Message.EMPTY_ERROR);  
				}else if(!StringHandler.isDouble(receipt.getAmountTtl4Export(), 2)){
					
					br.rejectValue("amountTtl4Export", "", Message.DOUBLE_IUPUT_ERROR);  
				}
			}else{
				
				for (int i = 0; i < receipt.getClearanceDtlList().size(); i++) {
					
					ClearanceDtl clearanceDtl = receipt.getClearanceDtlList().get(i);
					
					//报关单价
					if(IsEmptyUtil.isEmpty(clearanceDtl.getAmount())){
						
						br.rejectValue("clearanceDtlList[" + i + "].amount", "", Message.EMPTY_ERROR);  
					}else if(!StringHandler.isDouble(clearanceDtl.getAmount(), 2)){
						
						br.rejectValue("clearanceDtlList[" + i + "].amount", "", Message.DOUBLE_IUPUT_ERROR);  
					}
				}
			}
		}
	}
	
	/**
	 * 发货单验证
	 * @param receipt
	 * @param br
	 */
	private void validate(BindingResult br, Receipt receipt) throws Exception{
		
		//至少要添加一件产品
		if(IsEmptyUtil.isEmpty(receipt.getReceiptDtlList()) && IsEmptyUtil.isEmpty(receipt.getReceiptDtl4CList())){
			
			throw new Exception(Message.PRODUCTION_EMPTY_ERROR);
		}
		
		for (int i = 0; i < receipt.getReceiptDtlList().size(); i++) {
			
			ReceiptDtl receiptDtl = receipt.getReceiptDtlList().get(i);
			
			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl.getProductionId())
					|| !"0".equals(receiptDtl.getFeeNum())){
				
				continue;
			}
			//发货单数量
			if(IsEmptyUtil.isEmpty(receiptDtl.getQuantity())){
				
				br.rejectValue("receiptDtlList[" + i + "].quantity", "", Message.EMPTY_ERROR);  
			}else if(!StringHandler.isInt(receiptDtl.getQuantity())){
				
				br.rejectValue("receiptDtlList[" + i + "].quantity", "", Message.INT_IUPUT_ERROR);  
			}else if(StringHandler.isZero(receiptDtl.getQuantity())){
				
				br.rejectValue("receiptDtlList[" + i + "].quantity", "", Message.NO_ZERO_IUPUT_ERROR);  
			}
		}
		
		for (int i = 0; i < receipt.getReceiptDtl4CList().size(); i++) {
			
			ReceiptDtl4C receiptDtl4C = receipt.getReceiptDtl4CList().get(i);
			
			if(ConstantParam.PRODUCTIONID_4_TITLE.equals(receiptDtl4C.getProductionId())){
				
				continue;
			}
			
			//发货单数量
			if(IsEmptyUtil.isEmpty(receiptDtl4C.getQuantity())){
				
				br.rejectValue("receiptDtl4CList[" + i + "].quantity", "", Message.EMPTY_ERROR);  
			}else if(!StringHandler.isInt(receiptDtl4C.getQuantity())){
				
				br.rejectValue("receiptDtl4CList[" + i + "].quantity", "", Message.INT_IUPUT_ERROR);  
			}else if(StringHandler.isZero(receiptDtl4C.getQuantity())){
				
				br.rejectValue("receiptDtl4CList[" + i + "].quantity", "", Message.NO_ZERO_IUPUT_ERROR);  
			}
		}
		
		if(!StringHandler.isDouble(receipt.getAmountTtl4Export(), 2)){
			
			br.rejectValue("amountTtl4Export", "", Message.DOUBLE_IUPUT_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}
	
	public static void main(String[] args) {
		String a = "123";
		String b = "234";
		System.out.println(String.format("今天是：%s, %s, %s", a, b, a));
	}

}
