package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.common.Token;
import com.context.ConstantParam;
import com.context.Message;
import com.entity.Complaint;
import com.entity.ComplaintDtl;
import com.entity.Production;
import com.entity.TradeOrderPop;
import com.service.ComplaintService;
import com.service.ProductionService;
import com.service.ValidationAPIService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/complaint")
public class ComplaintController{
	
	@Autowired
	private ComplaintService complaintService = null;
	
	@Autowired
	private ProductionService productionService = null;
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	/**
	 * 投诉单检索POPUP画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView searchPopGet(@RequestParam(value="customerId")String customerId, @RequestParam(value="customerName")String customerName) {
		
		ModelAndView mav = new ModelAndView();
		TradeOrderPop tradeOrderPop = new TradeOrderPop();
		tradeOrderPop.setCustomerName4S(customerName);
		tradeOrderPop.setCustomerId4S(customerId);
		
		mav.addObject("tradeOrderPop", tradeOrderPop);
		mav.setViewName("complaintPopSearch");
		return mav;
	}
	
	/**
	 * 投诉单POPUP检索
	 * @param tradeOrder
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPop")
	public ModelAndView searchPopPost(TradeOrderPop tradeOrderPop) {
		
		ModelAndView mav = new ModelAndView();
		
		try {
			//固定设置选取确定或完成的订单产品
			tradeOrderPop.setIsStatusRefFlg("1");
			tradeOrderPop = complaintService.tradeOrderPopSearch(tradeOrderPop);
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("tradeOrderPop", tradeOrderPop);
		mav.setViewName("complaintPopSearch");
		
		return mav;
	}
	
	/**
	 * 投诉追加产品
	 * @param complaint
	 * @param pageMode
	 * @param productions
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addProduction")
	public ModelAndView addProduction(@ModelAttribute("complaint")Complaint complaint, 
			@RequestParam("pageMode")String pageMode, 
			@RequestParam("productions")String productions,
			HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		
		String errorMessage = "";
		
		ModelAndView mav = new ModelAndView();
		
		//将json格式的字符串转换为json数组对象  
        JSONArray array=(JSONArray)JSONObject.fromObject(productions).get("tempList");  
        
        for (int i = 0; i < array.size(); i++) {
        	
        	//取得json数组中的对象  
        	JSONObject o = (JSONObject) array.get(i);
        	
        	String productionId = o.get("productionId").toString();
        	String versionNo = o.get("versionNo").toString();
        	
        	//获取产品信息
    		Production production = productionService.findByKey(productionId, versionNo);
    			
			//添加产品到列表
    		ComplaintDtl complaintDtl = new ComplaintDtl();
    		complaintDtl.setProductionId(production.getProductionId());
    		complaintDtl.setVersionNo(production.getVersionNo());
    		complaintDtl.setDescriptionC(production.getDescriptionC());
    		complaintDtl.setDescriptionE(production.getDescriptionE());
    		complaintDtl.setVendorId(production.getVendorId());
    		complaintDtl.setVendorName(production.getVendorName());
    		complaintDtl.setProductionUpdateTime(production.getProductionUpdateTime());
    		
    		complaint.getComplaintDtlList().add(complaintDtl);
        }
        
		if(pageMode.equals("add")){
			mav.setViewName("complaintAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		}else{
			mav.setViewName("complaintUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		}
		mav.addObject("complaint", complaint);
		if(!IsEmptyUtil.isEmpty(errorMessage)){
			mav.addObject("errorMessage", errorMessage);
		}
		return mav;
		
	}
	
	/**
	 * 投诉删除产品
	 * @param complaint
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/deleteProduction")
	public ModelAndView deleteProduction(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br,
			@RequestParam("index")String index, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		
		ModelAndView mav = new ModelAndView();
		
		complaint.getComplaintDtlList().remove(Integer.parseInt(index));
		
		sum(complaint);
		
		if(pageMode.equals("add")){
			mav.setViewName("complaintAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		}else{
			mav.setViewName("complaintUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		}
		mav.addObject("complaint", complaint);
		return mav;
	}
	
	/**
	 * 清除
	 * @param complaint
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/clear")
	public ModelAndView clear(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br,
			@RequestParam("index")String index, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//清除对象
		ComplaintDtl complaintDtl = complaint.getComplaintDtlList().get(Integer.parseInt(index));
		
		complaintDtl.setTradeOrderId(null);
		complaintDtl.setPo(null);
		complaintDtl.setVolume(null);
		complaintDtl.setGrossWeight(null);
		complaintDtl.setNetWeight(null);
		complaintDtl.setInside(null);
		complaintDtl.setOutside(null);
		complaintDtl.settUnitPrice(null);
		complaintDtl.setiUnitPrice(null);
		complaintDtl.setQuantity(null);
		complaintDtl.setBoxAmount(null);
		complaintDtl.setVolumeTtl(null);
		complaintDtl.setGrossWeightTtl(null);
		complaintDtl.setNetWeightTtl(null);
		complaintDtl.settAmount(null);
		complaintDtl.setiAmount(null);
		
		sum(complaint);
		
		if(pageMode.equals("add")){
			mav.setViewName("complaintAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		}else{
			mav.setViewName("complaintUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		}
		mav.addObject("complaint", complaint);
		return mav;
	}
	
	/**
	 * 引入订单产品信息
	 * @param complaint
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tradeOrderRef")
	public ModelAndView tradeOrderRef(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br,
			@RequestParam("index")String index, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//引入订单产品信息
		ComplaintDtl complaintDtl = complaint.getComplaintDtlList().get(Integer.parseInt(index));
		
		//PO
		if(IsEmptyUtil.isEmpty(complaintDtl.getPo())){
			
			br.rejectValue("complaintDtlList[" + index + "].po", "", Message.EMPTY_ERROR);  
		}
		
		if(!br.hasErrors()){
			
			try {	
				
				complaintDtl = complaintService.findDtlByPo(complaint.getCustomerId(), complaintDtl, true);
			} catch (Exception e) {
				
				mav.addObject("errorMessage", e.getMessage());
			}
		} else {
			mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
		}
		
		sum(complaint);
		
		if(pageMode.equals("add")){
			mav.setViewName("complaintAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		}else{
			mav.setViewName("complaintUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		}
		mav.addObject("complaint", complaint);
		return mav;
	}
	
	/**
	 * 计算
	 * @param complaint
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/calculate")
	public ModelAndView calculate(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br,
			@RequestParam("index")String index, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//引入订单产品信息
		ComplaintDtl complaintDtl = complaint.getComplaintDtlList().get(Integer.parseInt(index));
		
		//PO
		if(IsEmptyUtil.isEmpty(complaintDtl.getPo())){
			
			br.rejectValue("complaintDtlList[" + index + "].po", "", Message.EMPTY_ERROR);  
		}
		
		//数量
		if(IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
			
			br.rejectValue("complaintDtlList[" + index + "].quantity", "", Message.EMPTY_ERROR);  
		}else if(!StringHandler.isInt(complaintDtl.getQuantity())){
			
			br.rejectValue("complaintDtlList[" + index + "].quantity", "", Message.INT_IUPUT_ERROR);  
		}
		//箱数
		if(!StringHandler.isInt(complaintDtl.getBoxAmount())){
			
			br.rejectValue("complaintDtlList[" + index + "].boxAmount", "", Message.INT_IUPUT_ERROR);  
		}
		
		if(!br.hasErrors()){
			
			try {	
				
				complaintDtl = complaintService.findDtlByPo(complaint.getCustomerId(), complaintDtl, false);
				complaintService.calculate(complaintDtl);
			} catch (Exception e) {
				
				mav.addObject("errorMessage", e.getMessage());
			}
		} else {
			mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
		}
		
		sum(complaint);
		
		if(pageMode.equals("add")){
			mav.setViewName("complaintAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		}else{
			mav.setViewName("complaintUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		}
		mav.addObject("complaint", complaint);
		return mav;
	}
	
	private void sum(Complaint complaint){
		
		int quantityTtl = 0;
		int boxAmountTtl = 0;
		double volumeTtl = 0;
		double grossWeightTtl = 0;
		double netWeightTtl = 0;
		double iAmountTtl = 0;
		double tAmountTtl = 0;
		
		int quantityTtl4U = 0;
		int boxAmountTtl4U = 0;
		double volumeTtl4U = 0;
		double grossWeightTtl4U = 0;
		double netWeightTtl4U = 0;
		double iAmountTtl4U = 0;
		double tAmountTtl4U = 0;
		
		int quantityTtl4R = 0;
		int boxAmountTtl4R = 0;
		double volumeTtl4R = 0;
		double grossWeightTtl4R = 0;
		double netWeightTtl4R = 0;
		double iAmountTtl4R = 0;
		double tAmountTtl4R = 0;
		
		int quantityTtl4A = 0;
		int boxAmountTtl4A = 0;
		double volumeTtl4A = 0;
		double grossWeightTtl4A = 0;
		double netWeightTtl4A = 0;
		double iAmountTtl4A = 0;
		double tAmountTtl4A = 0;

		for (ComplaintDtl complaintDtl : complaint.getComplaintDtlList()) {
			
			if("0".equals(complaintDtl.getHandleType())){
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
					
					quantityTtl4U =  quantityTtl4U + Integer.parseInt(complaintDtl.getQuantity());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					boxAmountTtl4U =  boxAmountTtl4U + Integer.parseInt(complaintDtl.getBoxAmount());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getVolume()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					volumeTtl4U =  StringHandler.numFormat4D(volumeTtl4U + (Double.parseDouble(complaintDtl.getVolume()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT4);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getGrossWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					grossWeightTtl4U =  StringHandler.numFormat4D(grossWeightTtl4U + (Double.parseDouble(complaintDtl.getGrossWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getNetWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					netWeightTtl4U =  StringHandler.numFormat4D(netWeightTtl4U + (Double.parseDouble(complaintDtl.getNetWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getiAmount())){
					
					iAmountTtl4U =  StringHandler.numFormat4D(iAmountTtl4U + Double.parseDouble(complaintDtl.getiAmount()), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.gettAmount())){
					
					tAmountTtl4U =  StringHandler.numFormat4D(tAmountTtl4U + Double.parseDouble(complaintDtl.gettAmount()), StringHandler.NUMBER_FORMAT2);
				}
			}else if("1".equals(complaintDtl.getHandleType())){
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
					
					quantityTtl4A =  quantityTtl4A + Integer.parseInt(complaintDtl.getQuantity());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					boxAmountTtl4A =  boxAmountTtl4A + Integer.parseInt(complaintDtl.getBoxAmount());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getVolume()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					volumeTtl4A =  StringHandler.numFormat4D(volumeTtl4A + (Double.parseDouble(complaintDtl.getVolume()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT4);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getGrossWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					grossWeightTtl4A =  StringHandler.numFormat4D(grossWeightTtl4A + (Double.parseDouble(complaintDtl.getGrossWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getNetWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					netWeightTtl4A =  StringHandler.numFormat4D(netWeightTtl4A + (Double.parseDouble(complaintDtl.getNetWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getiAmount())){
					
					iAmountTtl4A =  StringHandler.numFormat4D(iAmountTtl4A + Double.parseDouble(complaintDtl.getiAmount()), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.gettAmount())){
					
					tAmountTtl4A =  StringHandler.numFormat4D(tAmountTtl4A + Double.parseDouble(complaintDtl.gettAmount()), StringHandler.NUMBER_FORMAT2);
				}
			}else{
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
					
					quantityTtl4R =  quantityTtl4R + Integer.parseInt(complaintDtl.getQuantity());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					boxAmountTtl4R =  boxAmountTtl4R + Integer.parseInt(complaintDtl.getBoxAmount());
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getVolume()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					volumeTtl4R =  StringHandler.numFormat4D(volumeTtl4R + (Double.parseDouble(complaintDtl.getVolume()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT4);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getGrossWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					grossWeightTtl4R =  StringHandler.numFormat4D(grossWeightTtl4R + (Double.parseDouble(complaintDtl.getGrossWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getNetWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					netWeightTtl4R =  StringHandler.numFormat4D(netWeightTtl4R + (Double.parseDouble(complaintDtl.getNetWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.getiAmount())){
					
					iAmountTtl4R =  StringHandler.numFormat4D(iAmountTtl4R + Double.parseDouble(complaintDtl.getiAmount()), StringHandler.NUMBER_FORMAT2);
				}
				
				if(!IsEmptyUtil.isEmpty(complaintDtl.gettAmount())){
					
					tAmountTtl4R =  StringHandler.numFormat4D(tAmountTtl4R + Double.parseDouble(complaintDtl.gettAmount()), StringHandler.NUMBER_FORMAT2);
				}
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
				
				quantityTtl =  quantityTtl + Integer.parseInt(complaintDtl.getQuantity());
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				boxAmountTtl =  boxAmountTtl + Integer.parseInt(complaintDtl.getBoxAmount());
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getVolume()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				volumeTtl =  StringHandler.numFormat4D(volumeTtl + (Double.parseDouble(complaintDtl.getVolume()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT4);
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getGrossWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				grossWeightTtl =  StringHandler.numFormat4D(grossWeightTtl + (Double.parseDouble(complaintDtl.getGrossWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getNetWeight()) && !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				netWeightTtl =  StringHandler.numFormat4D(netWeightTtl + (Double.parseDouble(complaintDtl.getNetWeight()) * Double.parseDouble(complaintDtl.getBoxAmount())), StringHandler.NUMBER_FORMAT2);
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.getiAmount())){
				
				iAmountTtl =  StringHandler.numFormat4D(iAmountTtl + Double.parseDouble(complaintDtl.getiAmount()), StringHandler.NUMBER_FORMAT2);
			}
			
			if(!IsEmptyUtil.isEmpty(complaintDtl.gettAmount())){
				
				tAmountTtl =  StringHandler.numFormat4D(tAmountTtl + Double.parseDouble(complaintDtl.gettAmount()), StringHandler.NUMBER_FORMAT2);
			}
		}
		
		complaint.setQuantityTtl4U(String.valueOf(quantityTtl4U));
		complaint.setBoxAmountTtl4U(String.valueOf(boxAmountTtl4U));
		complaint.setVolumeTtl4U(StringHandler.numFormat(volumeTtl4U, StringHandler.NUMBER_FORMAT4));
		complaint.setGrossWeightTtl4U(StringHandler.numFormat(grossWeightTtl4U, StringHandler.NUMBER_FORMAT2));
		complaint.setNetWeightTtl4U(StringHandler.numFormat(netWeightTtl4U, StringHandler.NUMBER_FORMAT2));
		complaint.setiAmountTtl4U(StringHandler.numFormat(iAmountTtl4U, StringHandler.NUMBER_FORMAT2));
		complaint.settAmountTtl4U(StringHandler.numFormat(tAmountTtl4U, StringHandler.NUMBER_FORMAT2));
		
		complaint.setQuantityTtl4A(String.valueOf(quantityTtl4A));
		complaint.setBoxAmountTtl4A(String.valueOf(boxAmountTtl4A));
		complaint.setVolumeTtl4A(StringHandler.numFormat(volumeTtl4A, StringHandler.NUMBER_FORMAT4));
		complaint.setGrossWeightTtl4A(StringHandler.numFormat(grossWeightTtl4A, StringHandler.NUMBER_FORMAT2));
		complaint.setNetWeightTtl4A(StringHandler.numFormat(netWeightTtl4A, StringHandler.NUMBER_FORMAT2));
		complaint.setiAmountTtl4A(StringHandler.numFormat(iAmountTtl4A, StringHandler.NUMBER_FORMAT2));
		complaint.settAmountTtl4A(StringHandler.numFormat(tAmountTtl4A, StringHandler.NUMBER_FORMAT2));
		
		complaint.setQuantityTtl4R(String.valueOf(quantityTtl4R));
		complaint.setBoxAmountTtl4R(String.valueOf(boxAmountTtl4R));
		complaint.setVolumeTtl4R(StringHandler.numFormat(volumeTtl4R, StringHandler.NUMBER_FORMAT4));
		complaint.setGrossWeightTtl4R(StringHandler.numFormat(grossWeightTtl4R, StringHandler.NUMBER_FORMAT2));
		complaint.setNetWeightTtl4R(StringHandler.numFormat(netWeightTtl4R, StringHandler.NUMBER_FORMAT2));
		complaint.setiAmountTtl4R(StringHandler.numFormat(iAmountTtl4R, StringHandler.NUMBER_FORMAT2));
		complaint.settAmountTtl4R(StringHandler.numFormat(tAmountTtl4R, StringHandler.NUMBER_FORMAT2));
		
		complaint.setQuantityTtl(String.valueOf(quantityTtl));
		complaint.setBoxAmountTtl(String.valueOf(boxAmountTtl));
		complaint.setVolumeTtl(StringHandler.numFormat(volumeTtl, StringHandler.NUMBER_FORMAT4));
		complaint.setGrossWeightTtl(StringHandler.numFormat(grossWeightTtl, StringHandler.NUMBER_FORMAT2));
		complaint.setNetWeightTtl(StringHandler.numFormat(netWeightTtl, StringHandler.NUMBER_FORMAT2));
		complaint.setiAmountTtl(StringHandler.numFormat(iAmountTtl, StringHandler.NUMBER_FORMAT2));
		complaint.settAmountTtl(StringHandler.numFormat(tAmountTtl, StringHandler.NUMBER_FORMAT2));
	}
	
	/**
	 * 投诉检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_complaint");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("complaint", new Complaint());
		mav.setViewName("complaintSearch");
		return mav;
	}
	
	/**
	 * 投诉检索
	 * @param complaint
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Complaint complaint, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(complaint.getIsBack())
				&& null != session.getAttribute("searchCondition_complaint")){
			
			complaint = (Complaint)session.getAttribute("searchCondition_complaint");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_complaint", complaint);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			complaint = complaintService.complaintSearch(complaint);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("complaint", complaint);
		mav.setViewName("complaintSearch");
		
		return mav;
	}
	
	/**
	 * 投诉照会画面初期化
	 * @param complaintIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("complaintIdSelected")String complaintIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTREF);
		
		ModelAndView mav = new ModelAndView();
		try {
			Complaint complaint = complaintService.findByKey(complaintIdSelected);
			if(complaint != null){
				
				List<ComplaintDtl> complaintDtl = complaintService.findDtlByKey(complaint.getComplaintId());
				complaint.getComplaintDtlList().addAll(complaintDtl);
			}
			
			setImgFileName(complaint);
			
			sum(complaint);
			
			mav.addObject("complaint", complaint);
			mav.setViewName("complaintRef");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Complaint complaint = new Complaint();
			complaint.setIsBack("1");
			mav.addObject("complaint", complaint);
			mav.setViewName("forward:/complaint/search");
		}
		return mav;
	}
	
	/**
	 * 投诉追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		
		ModelAndView mav = new ModelAndView();
		Complaint complaint = new Complaint();
		mav.addObject("complaint", complaint);
		mav.setViewName("complaintAdd");
		return mav;
	}
	
	/**
	 * 投诉追加
	 * @param complaint
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("complaintAdd");
		
		try {
			//输入验证
			validate(br, complaint);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//投诉登録
				String newComplaintId = complaintService.add(complaint);
				
				//画像更新
				complaintService.saveImgFile(complaint);
				
				//投诉登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_COMPLAINT + newComplaintId);
				mav.setViewName("forward:/complaint/searchShow");
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
	 * 投诉变更画面初期化
	 * @param complaintIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView complaintUpdateGet(@RequestParam("complaintIdSelected")String complaintIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		
		ModelAndView mav = new ModelAndView();
		try {
			Complaint complaint = complaintService.findByKey(complaintIdSelected);
			if(complaint != null){
				
				List<ComplaintDtl> complaintDtl = complaintService.findDtlByKey(complaint.getComplaintId());
				complaint.getComplaintDtlList().addAll(complaintDtl);
			}
			
			setImgFileName(complaint);
			
			sum(complaint);
			
			mav.addObject("complaint", complaint);
			mav.setViewName("complaintUpdate");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Complaint complaint = new Complaint();
			complaint.setIsBack("1");
			mav.addObject("complaint", complaint);
			mav.setViewName("forward:/complaint/search");
		}
		return mav;
	}
	
	/**
	 * 投诉变更
	 * @param complaint
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView complaintUpdate(@Valid @ModelAttribute("complaint")Complaint complaint, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "complaintManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_COMPLAINTUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("complaintUpdate");
		
		//入力チェック
		try {
			validate(br, complaint);
		
			//入力チェック結果がOKの場合
			if(!br.hasErrors()){
				
					//投诉更新
					complaintService.update(complaint);
					
					//画像更新
					complaintService.saveImgFile(complaint);
					
					//投诉更新完了、返回检索画面
					mav.addObject("errorMessage", Message.SUCCESS_UPDATE_COMPLAINT + complaint.getComplaintId());
					mav.setViewName("forward:/complaint/searchShow");
				
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
	 * 投诉削除
	 * @param complaintIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("complaintIdSelected")String complaintIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//投诉削除
			complaintService.delete(complaintIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_COMPLAINT + complaintIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/complaint/searchShow");
		return mav;
	}
	
	/**
	 * 投诉返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Complaint complaint = new Complaint();
		complaint.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/complaint/search");
		mav.addObject("complaint", complaint);
		return mav;
	}
	
	public void setImgFileName(Complaint complaint){
		
		String Path= ConstantParam.COMPLAINT_PIC_ROOT_TEMP_REALPATH + "/" + complaint.getComplaintId();
		File file = new File(Path);
		if(file.exists()){
			
			String picture = "";
			
			File[] tempList = file.listFiles();
			for (File temp : tempList) {
				picture += temp.getName() + ";";
			}
			if(!IsEmptyUtil.isEmpty(picture)){
				complaint.setPictureExisted(picture.substring(0, picture.length() - 1));
			}
		}
	}
	
	/**
	 * 画像文件上传
	 * @param production
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/picFilesUpLoad")  
	public String fileUpLoad(@RequestParam MultipartFile[] picFiles, HttpServletRequest request, HttpServletResponse response){  
       
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out;
		try {
			out = response.getWriter();
		
	        String originalFilename = null;
	        for(MultipartFile picFile : picFiles){
	            if(picFile.isEmpty()){
	                out.flush();
	                return null;
	            }else{
	                originalFilename = picFile.getOriginalFilename();
	                System.out.println("文件原名: " + originalFilename);
	                System.out.println("文件名称: " + picFile.getName());
	                System.out.println("文件长度: " + picFile.getSize());
	                System.out.println("文件类型: " + picFile.getContentType());
	                System.out.println("========================================");
	                try {
	                	picFile.transferTo(new File(ConstantParam.COMPLAINT_PIC_ROOT_TEMP_REALPATH, originalFilename));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    out.print("1");
	                    out.flush();
	                    return null;
	                }
	            }
	        }
	        out.print("0" + ConstantParam.COMPLAINT_PIC_ROOT_TEMP + "/" + originalFilename);
	        out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
	}  
	
	/**
	 * 投诉验证
	 * @param complaint
	 * @param br
	 */
	private void validate(BindingResult br, Complaint complaint) throws Exception{
		
		//日期
		if(IsEmptyUtil.isEmpty(complaint.getComplaintDate())){
			
			br.rejectValue("complaintDate", "", Message.EMPTY_ERROR);  
		}
		if(!StringHandler.isDate(complaint.getComplaintDate())){
			
			br.rejectValue("complaintDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//客户
		if(IsEmptyUtil.isEmpty(complaint.getCustomerId())){
			
			br.rejectValue("customerId", "", Message.EMPTY_ERROR);  
		}
		//反馈日期
		if(!StringHandler.isDate(complaint.getReplyDate())){
			
			br.rejectValue("replyDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//处理完了日期
		if(!StringHandler.isDate(complaint.getDealDate())){
			
			br.rejectValue("dealDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//提醒开始日期
		if(!StringHandler.isDate(complaint.getAlertDateFrom())){
			
			br.rejectValue("alertDateFrom", "", Message.DATE_IUPUT_ERROR);  
		}
		//投诉处理期限
		if(!StringHandler.isDate(complaint.getDealDeadline())){
			
			br.rejectValue("dealDeadline", "", Message.DATE_IUPUT_ERROR);  
		}
		//日期关联check
		if(!IsEmptyUtil.isEmpty(complaint.getAlertDateFrom())
				|| !IsEmptyUtil.isEmpty(complaint.getDealDeadline())){
			
			if(IsEmptyUtil.isEmpty(complaint.getAlertDateFrom())){
				
				br.rejectValue("alertDateFrom", "", Message.EMPTY_ERROR);  
			}else if(IsEmptyUtil.isEmpty(complaint.getDealDeadline())){
				
				br.rejectValue("dealDeadline", "", Message.EMPTY_ERROR);  
			}else if(complaint.getDealDeadline().compareTo(complaint.getAlertDateFrom()) < 0){
				
				br.rejectValue("alertDateFrom", "", Message.WRONG_ALERTDATEFROM);  
			}
		}
		
		for (int i = 0; i < complaint.getComplaintDtlList().size(); i++) {
			
			ComplaintDtl complaintDtl = complaint.getComplaintDtlList().get(i);
			
			if(!"0".equals(complaintDtl.getHandleType())){
				
				if(IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
					
					br.rejectValue("complaintDtlList[" + i + "].quantity", "", Message.NOT_INPUT_ERROR);  
				}else if(!StringHandler.isInt(complaintDtl.getQuantity())){
					
					br.rejectValue("complaintDtlList[" + i + "].quantity", "", Message.INT_IUPUT_ERROR);  
				}
				
				if(IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
					
					br.rejectValue("complaintDtlList[" + i + "].boxAmount", "", Message.NOT_INPUT_ERROR);  
				}else if(!StringHandler.isInt(complaintDtl.getBoxAmount())){
					
					br.rejectValue("complaintDtlList[" + i + "].boxAmount", "", Message.INT_IUPUT_ERROR);  
				}
			}
			
			if(IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())
					&& !IsEmptyUtil.isEmpty(complaintDtl.getQuantity())){
				
				br.rejectValue("complaintDtlList[" + i + "].quantity", "", Message.INPUT_ERROR_4_NOREF);  
			}
			
			if(IsEmptyUtil.isEmpty(complaintDtl.getTradeOrderId())
					&& !IsEmptyUtil.isEmpty(complaintDtl.getBoxAmount())){
				
				br.rejectValue("complaintDtlList[" + i + "].boxAmount", "", Message.INPUT_ERROR_4_NOREF);  
			}
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
