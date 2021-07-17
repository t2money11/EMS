package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
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
import com.context.Message;
import com.entity.InquiryDtl;
import com.entity.InterTradeOrder;
import com.entity.InterTradeOrderDtl;
import com.entity.Production;
import com.entity.TradeOrder;
import com.entity.TradeOrderDtl;
import com.entity.TradeOrderPop;
import com.entity.Vendor;
import com.poi.TradeOrderExport;
import com.service.ProductionService;
import com.service.TradeOrderService;
import com.service.VendorService;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

@Controller
@RequestMapping("/tradeOrder")
public class TradeOrderController{
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	@Autowired
	private ProductionService productionService = null;
	
	@Autowired
	private VendorService vendorService = null;
	
	@Autowired
	private TradeOrderExport tradeOrderExport = null;
	
	/**
	 * 订单检索POPUP画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView searchPopGet(@RequestParam(value="customerId")String customerId, @RequestParam(value="customerName")String customerName) {
		
		ModelAndView mav = new ModelAndView();
		TradeOrderPop tradeOrderPop = new TradeOrderPop();
		tradeOrderPop.setCustomerName4S(customerName);
		tradeOrderPop.setCustomerId4S(customerId);
		tradeOrderPop.getPageInfo().setPageSize(30);;
		
		mav.addObject("tradeOrderPop", tradeOrderPop);
		mav.setViewName("tradeOrderPopSearch");
		return mav;
	}
	
	/**
	 * 订单POPUP检索
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
			tradeOrderPop = tradeOrderService.tradeOrderPopSearch(tradeOrderPop);
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("tradeOrderPop", tradeOrderPop);
		mav.setViewName("tradeOrderPopSearch");
		
		return mav;
	}
	
	/**
	 * 订单检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERSEARCH);
		//检索条件清除
		session.removeAttribute("searchCondition_tradeOrder");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("tradeOrder", new TradeOrder());
		mav.setViewName("tradeOrderSearch");
		return mav;
	}
	
	/**
	 * 订单检索
	 * @param tradeOrder
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(TradeOrder tradeOrder, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(tradeOrder.getIsBack())
				&& null != session.getAttribute("searchCondition_tradeOrder")){
			
			tradeOrder = (TradeOrder)session.getAttribute("searchCondition_tradeOrder");
		}else{
			
			//检索条件保存
			session.setAttribute("searchCondition_tradeOrder", tradeOrder);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			tradeOrder = tradeOrderService.tradeOrderSearch(tradeOrder);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("tradeOrder", tradeOrder);
		mav.setViewName("tradeOrderSearch");
		
		return mav;
	}
	
	/**
	 * 订单追加产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addProduction")
	public ModelAndView addProduction(@ModelAttribute("tradeOrder")TradeOrder tradeOrder, 
			@RequestParam("pageMode")String pageMode, 
			@RequestParam("productions")String productions,
			HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		
		String errorMessage = "";
		
		ModelAndView mav = new ModelAndView();
		
		//将json格式的字符串转换为json数组对象  
        JSONArray array=(JSONArray)JSONObject.fromObject(productions).get("tempList");  
        
        for (int i = 0; i < array.size(); i++) {
        	
        	//取得json数组中的对象  
        	JSONObject o = (JSONObject) array.get(i);
        	
        	String productionId = o.get("productionId").toString();
        	String versionNo = o.get("versionNo").toString();
        	
			Production production = productionService.findByKey(productionId, versionNo);
    		List<InterTradeOrder> interTradeOrderList = tradeOrder.getInterTradeOrderList();
    		
    		boolean vendorAdded = false;
    		boolean productionAdded = false;
    		
    		for (InterTradeOrder interTradeOrder : interTradeOrderList) {
    			
    			if(interTradeOrder.getVendorId().equals(production.getVendorId())){
    				
    				//如果已经添加过该产品，返回页面报错
    				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
    					
    					if(interTradeOrderDtl.getProductionId().equals(production.getProductionId())
    							&& interTradeOrderDtl.getVersionNo().equals(production.getVersionNo())){
    						productionAdded = true;
    						break;
    					}
    				}
    				if(productionAdded){
    					
    					errorMessage = errorMessage.concat(String.format(Message.DUPLICATED_PRODUCTION_IN_ORDER, production.getProductionId(), production.getVersionNo())).concat("</br>");
    				}else{
    					//设定内贸合同明细
    					InterTradeOrderDtl interTradeOrderDtl = new InterTradeOrderDtl();
    					setInterTradeOrderDtl(interTradeOrderDtl, production);
    					//添加到内贸合同明细list
    					interTradeOrder.getInterTradeOrderDtlList().add(interTradeOrderDtl);
    				}
    				vendorAdded = true;
    				break;
    			}
    		}
    		
    		if(vendorAdded == false){
    			
    			Vendor vendor = vendorService.findByKey(production.getVendorId());
    			
    			InterTradeOrder interTradeOrder = new InterTradeOrder();
    			interTradeOrder.setVendorId(vendor.getVendorId());
    			interTradeOrder.setVendorName(vendor.getVendorName());
    			interTradeOrder.setVendorFullName(vendor.getVendorFullName());
    			interTradeOrder.setLocation(vendor.getLocation());
    			interTradeOrder.setVendorUpdateTime(vendor.getVendorUpdateTime());
    			
    			//设定内贸合同明细
    			InterTradeOrderDtl interTradeOrderDtl = new InterTradeOrderDtl();
    			setInterTradeOrderDtl(interTradeOrderDtl, production);
    			//添加到内贸合同明细list
    			interTradeOrder.getInterTradeOrderDtlList().add(interTradeOrderDtl);
    			tradeOrder.getInterTradeOrderList().add(interTradeOrder);
    		}
    		
    		if(productionAdded == false){
    			
    			//设定订单明细
    			TradeOrderDtl tradeOrderDtl = new TradeOrderDtl();
    			tradeOrderDtl.setProductionId(production.getProductionId());
    			tradeOrderDtl.setVersionNo(production.getVersionNo());
    			tradeOrderDtl.setDescriptionE(production.getDescriptionE());
    			tradeOrderDtl.setLastComplaintDate(production.getLastComplaintDate());
    			tradeOrderDtl.setLastDrawUpdateDate(production.getLastDrawUpdateDate());
    			tradeOrderDtl.setProductionUpdateTime(production.getProductionUpdateTime());
    			//添加到订单明细list
    			tradeOrder.getTradeOrderDtlList().add(tradeOrderDtl);
    		}
		}
		
		if(pageMode.equals("add")){
			mav.setViewName("tradeOrderAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERADD);
		}else{
			mav.setViewName("tradeOrderUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERUPDATE);
		}
		mav.addObject("tradeOrder", tradeOrder);
		if(!IsEmptyUtil.isEmpty(errorMessage)){
			mav.addObject("errorMessage", errorMessage);
		}
		return mav;
	}
	
	private void setInterTradeOrderDtl(InterTradeOrderDtl interTradeOrderDtl, Production production){
		
		interTradeOrderDtl.setProductionId(production.getProductionId());
		interTradeOrderDtl.setVersionNo(production.getVersionNo());
		interTradeOrderDtl.setProductionIdVendor(production.getProductionIdVendor());
		interTradeOrderDtl.setDescriptionC(production.getDescriptionC());
		interTradeOrderDtl.setVolume(production.getVolume());
		interTradeOrderDtl.setGrossWeight(production.getGrossWeight());
		interTradeOrderDtl.setNetWeight(production.getNetWeight());
		interTradeOrderDtl.setInside(production.getInside());
		interTradeOrderDtl.setOutside(production.getOutside());
		
//		String unitPrice = tradeOrderService.findPreviousRMBByProductionId(interTradeOrderDtl.getProductionId());
//		interTradeOrderDtl.setUnitPrice(unitPrice);
		
	}
	
	/**
	 * 订单删除产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/deleteProduction")
	public ModelAndView deleteProduction(@ModelAttribute("tradeOrder")TradeOrder tradeOrder, 
			@RequestParam("productionId")String productionId, 
			@RequestParam("versionNo")String versionNo, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		
		ModelAndView mav = new ModelAndView();
		String vendorId = null;
		//删除外贸合同中的该产品
		for (int i = 0; i < tradeOrder.getTradeOrderDtlList().size(); i++) {
			
			TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
			
			if(productionId.equals(tradeOrderDtl.getProductionId())
					&& versionNo.equals(tradeOrderDtl.getVersionNo())){
				
				tradeOrder.getTradeOrderDtlList().remove(i);
				break;
			}
		}
		//删除内贸合同中的该产品，如果删除后内贸合同里没有产品的话也删除该内贸合同
		for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
			
			InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
			for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
				
				InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
				if(productionId.equals(interTradeOrderDtl.getProductionId())
						&& versionNo.equals(interTradeOrderDtl.getVersionNo())){
					
					vendorId = interTradeOrder.getVendorId();
					interTradeOrder.getInterTradeOrderDtlList().remove(j);
					break;
				}
			}
			
			if(vendorId != null){
				if(interTradeOrder.getInterTradeOrderDtlList().size() == 0){
					tradeOrder.getInterTradeOrderList().remove(i);
				}
				break;
			}
		}
		
		//重新计算画面相关项目
		tradeOrderService.calculate(tradeOrder);
		
		if(pageMode.equals("add")){
			mav.setViewName("tradeOrderAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERADD);
		}else{
			mav.setViewName("tradeOrderUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERUPDATE);
		}
		mav.addObject("tradeOrder", tradeOrder);
		return mav;
	}
	
	/**
	 * 订单引入询单价格
	 * @param tradeOrder
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/calculate")
	public ModelAndView calculatePost(@Valid @ModelAttribute("tradeOrder")TradeOrder tradeOrder, BindingResult br, HttpSession session, @RequestParam("pageMode")String pageMode) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		
		ModelAndView mav = new ModelAndView();
		
		if(pageMode.equals("add")){
			mav.setViewName("tradeOrderAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERADD);
		}else{
			mav.setViewName("tradeOrderUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_ORDERUPDATE);
		}
		
		try {
			//询单号必填
			if(IsEmptyUtil.isEmpty(tradeOrder.getInquiryId())){
				
				throw new Exception(Message.NOT_INPUT_INQUIRY);
			}
			
			//引入询单价格
			for (InquiryDtl inquiryDtl : tradeOrderService.getFromInquiry(tradeOrder)) {
				
				Production production = productionService.findByKey(inquiryDtl.getProductionId(), inquiryDtl.getVersionNo());
				
				if(tradeOrder.getCustomerId() == null || !tradeOrder.getCustomerId().equals(production.getCustomerId())){
					
					throw new Exception(Message.CUSTOMER_INPUT_INQUIRY);
				}
				
	    		List<InterTradeOrder> interTradeOrderList = tradeOrder.getInterTradeOrderList();
	    		
	    		boolean vendorAdded = false;
	    		boolean productionAdded = false;
	    		
	    		for (InterTradeOrder interTradeOrder : interTradeOrderList) {
	    			
	    			if(interTradeOrder.getVendorId().equals(production.getVendorId())){
	    				
	    				//如果已经添加过该产品，返回页面报错
	    				for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
	    					
	    					if(interTradeOrderDtl.getProductionId().equals(production.getProductionId())
	    							&& interTradeOrderDtl.getVersionNo().equals(production.getVersionNo())){
	    						productionAdded = true;
	    						break;
	    					}
	    				}
	    				if(productionAdded){
	    					
	    				}else{
	    					//设定内贸合同明细
	    					InterTradeOrderDtl interTradeOrderDtl = new InterTradeOrderDtl();
	    					setInterTradeOrderDtl(interTradeOrderDtl, production);
	    					//添加到内贸合同明细list
	    					interTradeOrder.getInterTradeOrderDtlList().add(interTradeOrderDtl);
	    				}
	    				vendorAdded = true;
	    				break;
	    			}
	    		}
	    		
	    		if(vendorAdded == false){
	    			
	    			Vendor vendor = vendorService.findByKey(production.getVendorId());
	    			
	    			InterTradeOrder interTradeOrder = new InterTradeOrder();
	    			interTradeOrder.setVendorId(vendor.getVendorId());
	    			interTradeOrder.setVendorName(vendor.getVendorName());
	    			interTradeOrder.setVendorFullName(vendor.getVendorFullName());
	    			interTradeOrder.setLocation(vendor.getLocation());
	    			interTradeOrder.setVendorUpdateTime(vendor.getVendorUpdateTime());
	    			
	    			//设定内贸合同明细
	    			InterTradeOrderDtl interTradeOrderDtl = new InterTradeOrderDtl();
	    			setInterTradeOrderDtl(interTradeOrderDtl, production);
	    			//添加到内贸合同明细list
	    			interTradeOrder.getInterTradeOrderDtlList().add(interTradeOrderDtl);
	    			tradeOrder.getInterTradeOrderList().add(interTradeOrder);
	    		}
	    		
	    		if(productionAdded == false){
	    			
	    			//设定订单明细
	    			TradeOrderDtl tradeOrderDtl = new TradeOrderDtl();
	    			tradeOrderDtl.setProductionId(production.getProductionId());
	    			tradeOrderDtl.setVersionNo(production.getVersionNo());
	    			tradeOrderDtl.setDescriptionE(production.getDescriptionE());
	    			tradeOrderDtl.setLastComplaintDate(production.getLastComplaintDate());
	    			tradeOrderDtl.setLastDrawUpdateDate(production.getLastDrawUpdateDate());
	    			tradeOrderDtl.setProductionUpdateTime(production.getProductionUpdateTime());
	    			//添加到订单明细list
	    			tradeOrder.getTradeOrderDtlList().add(tradeOrderDtl);
	    		}
				
				for (TradeOrderDtl tradeOrderDtl : tradeOrder.getTradeOrderDtlList()) {
					
					if(inquiryDtl.getProductionId().equals(tradeOrderDtl.getProductionId())
							&& inquiryDtl.getVersionNo().equals(tradeOrderDtl.getVersionNo())){
						
						tradeOrderDtl.setUnitPrice(inquiryDtl.getFinalPrice());
						break;
					}
				}
				
				for (InterTradeOrder interTradeOrder : tradeOrder.getInterTradeOrderList()) {
					
					for (InterTradeOrderDtl interTradeOrderDtl : interTradeOrder.getInterTradeOrderDtlList()) {
						
						if(inquiryDtl.getProductionId().equals(interTradeOrderDtl.getProductionId())
								&& inquiryDtl.getVersionNo().equals(interTradeOrderDtl.getVersionNo())){
							
							interTradeOrderDtl.setUnitPrice(inquiryDtl.getRMB());
							break;
						}
					}
				}
			}
			
			//输入验证
			validate4C(br, tradeOrder);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//计算
				tradeOrderService.calculate(tradeOrder);
				mav.addObject("tradeOrder", tradeOrder);
			} else {
				mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
			}
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 订单追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERADD);
		
		ModelAndView mav = new ModelAndView();
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setInquiryNeeded("1");
		mav.addObject("tradeOrder", tradeOrder);
		mav.setViewName("tradeOrderAdd");
		return mav;
	}
	
	/**
	 * 订单追加
	 * @param tradeOrder
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("tradeOrder")TradeOrder tradeOrder, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeOrderAdd");
		
		try {
			//入力チェック
			validate(br, tradeOrder);
			
			//入力チェック結果がOKの場合
			if(!br.hasErrors()){
			
				//重新计算画面相关项目
				tradeOrderService.calculate(tradeOrder);
				
				//订单登録
				String newOrderId = tradeOrderService.add(tradeOrder);
				//订单登録完了、检索画面へ戻る
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_ORDER + newOrderId);
				mav.setViewName("forward:/tradeOrder/searchShow");
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
	 * 订单查看画面初期化
	 * @param tradeOrderIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("tradeOrderIdSelected")String tradeOrderIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERREF);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeOrderRef");
		try{
			
			TradeOrder tradeOrder = tradeOrderService.findByKey(tradeOrderIdSelected);
			mav.addObject("tradeOrder", tradeOrder);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("forward:/tradeOrder/searchShow");
		}
		return mav;
	}
	
	/**
	 * 订单变更画面初期化
	 * @param tradeOrderIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView updateGet(@RequestParam("tradeOrderIdSelected")String tradeOrderIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeOrderUpdate");
		try{
			
			TradeOrder tradeOrder = tradeOrderService.findByKey(tradeOrderIdSelected);
			
			//设定isConfirmed
			if(Integer.parseInt(tradeOrder.getStatus()) > 0){
				
				tradeOrder.setIsConfirmed(true);
			}
			mav.addObject("tradeOrder", tradeOrder);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("forward:/tradeOrder/searchShow");
		}
		return mav;
	}
	
	/**
	 * 订单变更
	 * @param tradeOrder
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView updatePost(@Valid @ModelAttribute("tradeOrder")TradeOrder tradeOrder, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "tradeOrderManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ORDERUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeOrderUpdate");
		
		//入力チェック
		try {
			validate(br, tradeOrder);
		
			//入力チェック結果がOKの場合
			if(!br.hasErrors()){
				
					//重新计算画面相关项目
					tradeOrderService.calculate(tradeOrder);
				
					//订单更新
					tradeOrderService.update(tradeOrder);
					
					//订单更新完了、检索画面へ戻る
					mav.addObject("errorMessage", Message.SUCCESS_UPDATE_ORDER + tradeOrder.getTradeOrderId());
					mav.setViewName("forward:/tradeOrder/searchShow");
				
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
	 * 订单删除
	 * @param tradeOrder
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("tradeOrderIdSelected")String tradeOrderIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			TradeOrder tradeOrder = tradeOrderService.findByKey(tradeOrderIdSelected);
			//已经确定的订单不能删除
			if("1".equals(tradeOrder.getStatus())
				|| "2".equals(tradeOrder.getStatus())){
				
				throw new Exception(Message.DELETE_STATUS_ERROR);
			}
			//删除订单
			tradeOrderService.delete(tradeOrderIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_ORDER + tradeOrderIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/tradeOrder/searchShow");
		return mav;
	}
	
	/**
	 * 下载
	 * @param tradeOrder
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/download")
	public void download(TradeOrder tradeOrder, HttpServletResponse response) throws IOException {  
		
		OutputStream os = response.getOutputStream();  
	    try {  
	    	String fileName = StringHandler.convertStringToUTF8(tradeOrder.getContractNo()) + "_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx";
	    	String fileFullPath = tradeOrderExport.generateFile(tradeOrder, fileName);
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
	 * 订单检索画面へ戻る
	 * @param tradeOrder
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView tradeOrderReturn(){
		
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/tradeOrder/search");
		mav.addObject("tradeOrder", tradeOrder);
		return mav;
	}
	
	/**
	 * 订单保存验证
	 * @param tradeOrder
	 * @param br
	 */
	private void validate(BindingResult br, TradeOrder tradeOrder) throws Exception{
		
		//ContractNo合理性
		if(!StringHandler.isContractNo(tradeOrder.getContractNo())){
			
			br.rejectValue("contractNo", "", Message.CONTRACTNO_ERROR);  
		}
		//客户必须选择
		if(IsEmptyUtil.isEmpty(tradeOrder.getCustomerId())){
			
			br.rejectValue("customerId", "", Message.EMPTY_ERROR);  
		}
		//订单创建日
		if(!StringHandler.isDate(tradeOrder.getTradeOrderCreateDate())){
			
			br.rejectValue("tradeOrderCreateDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//订单确定日
		if(!StringHandler.isDate(tradeOrder.getTradeOrderConfirmDate())){
				
			br.rejectValue("tradeOrderConfirmDate", "", Message.DATE_IUPUT_ERROR);  
		}
		//shipment
		if(!StringHandler.isDate(tradeOrder.getShipment())){
			
			br.rejectValue("shipment", "", Message.DATE_IUPUT_ERROR);  
		}
		//Exchange Rate
		if(!StringHandler.isRate(tradeOrder.getExRate())){
				
			br.rejectValue("exRate", "", Message.RATE_IUPUT_ERROR);  
		}
		//Tax Rebate Rate
		if(!StringHandler.isInt(tradeOrder.getEtrRate())){
				
			br.rejectValue("etrRate", "", Message.INT_IUPUT_ERROR);  
		}
		//至少要添加一件产品
		if(tradeOrder.getTradeOrderDtlList().size() == 0){
			
			throw new Exception(Message.PRODUCTION_EMPTY_ERROR);
		}
		
		//订单确认时的追加验证
		if(tradeOrder.getStatus().equals("1")
				|| tradeOrder.getStatus().equals("2")){
			
			//P.O
			if(IsEmptyUtil.isEmpty(tradeOrder.getPo())){
				
				br.rejectValue("po", "", Message.EMPTY_ERROR);  
			}
			//ContractNo
			if(IsEmptyUtil.isEmpty(tradeOrder.getContractNo())
					&& !IsEmptyUtil.isEmpty(tradeOrder.getTradeOrderId())){
				
				br.rejectValue("contractNo", "", Message.EMPTY_ERROR);  
			}
			//订单创建日
			if(IsEmptyUtil.isEmpty(tradeOrder.getTradeOrderCreateDate())){
				
				br.rejectValue("tradeOrderCreateDate", "", Message.EMPTY_ERROR);  
			}
			//订单确定日
			if(IsEmptyUtil.isEmpty(tradeOrder.getTradeOrderConfirmDate())){
				
				br.rejectValue("tradeOrderConfirmDate", "", Message.EMPTY_ERROR);  
			}
			if("1".equals(tradeOrder.getSendMode())){
				
				//Shipment
				if(IsEmptyUtil.isEmpty(tradeOrder.getShipment())){
					
					br.rejectValue("shipment", "", Message.EMPTY_ERROR);  
				}
			}
			//Exchange Rate
			if(IsEmptyUtil.isEmpty(tradeOrder.getExRate())){
				
				br.rejectValue("exRate", "", Message.EMPTY_ERROR);  
			}
			//Tax Rebate Rate
			if(IsEmptyUtil.isEmpty(tradeOrder.getEtrRate())){
				
				br.rejectValue("etrRate", "", Message.EMPTY_ERROR);  
			}
		}
		
		for (int i = 0; i < tradeOrder.getTradeOrderDtlList().size(); i++) {
			
			TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
			
			//数量
			if(!StringHandler.isInt(tradeOrderDtl.getQuantity())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].quantity", "", Message.INT_IUPUT_ERROR);  
			}
			//单价
			if(!StringHandler.isDouble(tradeOrderDtl.getUnitPrice(), 3)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].unitPrice", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//分批发货数1
			if(!StringHandler.isInt(tradeOrderDtl.getSendQuantity1())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity1", "", Message.INT_IUPUT_ERROR);  
			}
			//分批发货数2
			if(!StringHandler.isInt(tradeOrderDtl.getSendQuantity2())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity2", "", Message.INT_IUPUT_ERROR);  
			}
			//分批发货数3
			if(!StringHandler.isInt(tradeOrderDtl.getSendQuantity3())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity3", "", Message.INT_IUPUT_ERROR);  
			}
			//分批发货数4
			if(!StringHandler.isInt(tradeOrderDtl.getSendQuantity4())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity4", "", Message.INT_IUPUT_ERROR);  
			}
			//分批发货日期1
			if(!StringHandler.isDate(tradeOrderDtl.getSendDate1())){
				
				br.rejectValue("tradeOrderDtlList[" + i + "].sendDate1", "", Message.DATE_IUPUT_ERROR);  
			}
			//分批发货日期2
			if(!StringHandler.isDate(tradeOrderDtl.getSendDate2())){
				
				br.rejectValue("tradeOrderDtlList[" + i + "].sendDate2", "", Message.DATE_IUPUT_ERROR);  
			}
			//分批发货日期3
			if(!StringHandler.isDate(tradeOrderDtl.getSendDate3())){
				
				br.rejectValue("tradeOrderDtlList[" + i + "].sendDate3", "", Message.DATE_IUPUT_ERROR);  
			}
			//分批发货日期4
			if(!StringHandler.isDate(tradeOrderDtl.getSendDate4())){
				
				br.rejectValue("tradeOrderDtlList[" + i + "].sendDate4", "", Message.DATE_IUPUT_ERROR);  
			}
			//分批配套确认
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity1()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate1())){
				
				//分批发货数1
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity1())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity1", "", Message.EMPTY_ERROR);  
				}
				//分批发货日期1
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate1())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendDate1", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity2()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate2())){
				
				//分批发货数2
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity2())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity2", "", Message.EMPTY_ERROR);  
				}
				//分批发货日期2
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate2())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendDate2", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity3()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate3())){
				
				//分批发货数3
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity3())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity3", "", Message.EMPTY_ERROR);  
				}
				//分批发货日期3
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate3())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendDate3", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity4()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate4())){
				
				//分批发货数4
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity4())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendQuantity4", "", Message.EMPTY_ERROR);  
				}
				//分批发货日期4
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate4())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].sendDate4", "", Message.EMPTY_ERROR);  
				}
			}
			//费用1
			if(!StringHandler.isDouble(tradeOrderDtl.getFee1(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee1", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用2
			if(!StringHandler.isDouble(tradeOrderDtl.getFee2(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee2", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用3
			if(!StringHandler.isDouble(tradeOrderDtl.getFee3(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee3", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用4
			if(!StringHandler.isDouble(tradeOrderDtl.getFee4(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee4", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用配套确认
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle1()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getFee1())){
				
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle1())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].feeTitle1", "", Message.EMPTY_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFee1())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].fee1", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle2()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getFee2())){
				
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle2())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].feeTitle2", "", Message.EMPTY_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFee2())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].fee2", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle3()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getFee3())){
				
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle3())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].feeTitle3", "", Message.EMPTY_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFee3())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].fee3", "", Message.EMPTY_ERROR);  
				}
			}
			if(!IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle4()) || !IsEmptyUtil.isEmpty(tradeOrderDtl.getFee4())){
				
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFeeTitle4())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].feeTitle4", "", Message.EMPTY_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getFee4())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].fee4", "", Message.EMPTY_ERROR);  
				}
			}
			
			//订单确认时的追加验证
			if(tradeOrder.getStatus().equals("1")
					|| tradeOrder.getStatus().equals("2")){
				
				//数量
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getQuantity())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].quantity", "", Message.EMPTY_ERROR);  
				}
				//单价
				if(IsEmptyUtil.isEmpty(tradeOrderDtl.getUnitPrice())){
					
					br.rejectValue("tradeOrderDtlList[" + i + "].unitPrice", "", Message.EMPTY_ERROR);  
				}
				if("2".equals(tradeOrder.getSendMode())){
					
					//分批发货日期1
					if(IsEmptyUtil.isEmpty(tradeOrderDtl.getSendDate1())){
						
						br.rejectValue("tradeOrderDtlList[" + i + "].sendDate1", "", Message.EMPTY_ERROR);  
					}
					
					//分批数量总和确认
					if(StringHandler.isInt(tradeOrderDtl.getQuantity()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getQuantity())){
						
						int sum = Integer.parseInt(tradeOrderDtl.getQuantity());
						
						if(StringHandler.isInt(tradeOrderDtl.getSendQuantity1()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity1())){
							
							sum = sum - Integer.parseInt(tradeOrderDtl.getSendQuantity1());
						}
						if(StringHandler.isInt(tradeOrderDtl.getSendQuantity2()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity2())){
							
							sum = sum - Integer.parseInt(tradeOrderDtl.getSendQuantity2());
						}
						if(StringHandler.isInt(tradeOrderDtl.getSendQuantity3()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity3())){
							
							sum = sum - Integer.parseInt(tradeOrderDtl.getSendQuantity3());
						}
						if(StringHandler.isInt(tradeOrderDtl.getSendQuantity4()) && !IsEmptyUtil.isEmpty(tradeOrderDtl.getSendQuantity4())){
							
							sum = sum - Integer.parseInt(tradeOrderDtl.getSendQuantity4());
						}
						if(sum != 0){
							
							br.rejectValue("tradeOrderDtlList[" + i + "].quantity", "", Message.WRONG_QUANTITY);  
						}
					}
				}
			}
		}
		
		for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
			
			InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
			
			//内贸合同创建日
			if(!StringHandler.isDate(interTradeOrder.getInterTradeCreateDate())){
				
				br.rejectValue("interTradeOrderList[" + i + "].interTradeCreateDate", "", Message.DATE_IUPUT_ERROR);  
			}
			//内贸合同签订日
			if(!StringHandler.isDate(interTradeOrder.getInterTradeConfirmDate())){
				
				br.rejectValue("interTradeOrderList[" + i + "].interTradeConfirmDate", "", Message.DATE_IUPUT_ERROR);  
			}
			//内贸合同交货日
			if(!StringHandler.isDate(interTradeOrder.getRecieveDate())){
				
				br.rejectValue("interTradeOrderList[" + i + "].recieveDate", "", Message.DATE_IUPUT_ERROR);  
			}
			//内贸合同预付日期
			if(!StringHandler.isDate(interTradeOrder.getAdvancePaymentDate())){
				
				br.rejectValue("interTradeOrderList[" + i + "].advancePaymentDate", "", Message.DATE_IUPUT_ERROR);  
			}
			//预付金额
			if(!StringHandler.isDouble(interTradeOrder.getAdvancePayment(), 2)){
					
				br.rejectValue("interTradeOrderList[" + i + "].advancePayment", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//预付折扣（%）
			if(!StringHandler.isDouble(interTradeOrder.getAdvancePaymentDiscountRate(), 2)){
					
				br.rejectValue("interTradeOrderList[" + i + "].advancePaymentDiscountRate", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//预付金额，预付折扣（%），内贸合同预付日期
			if(!IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePayment())
				|| !IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDate())){
				
				if(IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDiscountRate())){
					
					br.rejectValue("interTradeOrderList[" + i + "].advancePaymentDiscountRate", "", Message.ADVANCEPAYMENT_COMBO_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePayment())){
					
					br.rejectValue("interTradeOrderList[" + i + "].advancePayment", "", Message.ADVANCEPAYMENT_COMBO_ERROR);  
				}
				if(IsEmptyUtil.isEmpty(interTradeOrder.getAdvancePaymentDate())){
					
					br.rejectValue("interTradeOrderList[" + i + "].advancePaymentDate", "", Message.ADVANCEPAYMENT_COMBO_ERROR);  
				}
			}
			
			//订单确认时的追加验证
			if(tradeOrder.getStatus().equals("1")
					|| tradeOrder.getStatus().equals("2")){
				
				//内贸合同创建日
				if(IsEmptyUtil.isEmpty(interTradeOrder.getInterTradeCreateDate())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeCreateDate", "", Message.EMPTY_ERROR);  
				}
				//内贸合同签订日
				if(IsEmptyUtil.isEmpty(interTradeOrder.getInterTradeConfirmDate())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeConfirmDate", "", Message.EMPTY_ERROR);  
				}
				
				if("1".equals(interTradeOrder.getSendMode())){
					
					//内贸合同交货日
					if(IsEmptyUtil.isEmpty(interTradeOrder.getRecieveDate())){
						
						br.rejectValue("interTradeOrderList[" + i + "].recieveDate", "", Message.EMPTY_ERROR);  
					}
				}
			}
			
			for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
				
				InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
				
				//数量
				if(!StringHandler.isInt(interTradeOrderDtl.getQuantity())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].quantity", "", Message.INT_IUPUT_ERROR);  
					
				}
				//单价
				if(!StringHandler.isDouble(interTradeOrderDtl.getUnitPrice(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].unitPrice", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//体积
				if(!StringHandler.isDouble(interTradeOrderDtl.getVolume(), 4)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].volume", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//毛重
				if(!StringHandler.isDouble(interTradeOrderDtl.getGrossWeight(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].grossWeight", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//净重
				if(!StringHandler.isDouble(interTradeOrderDtl.getNetWeight(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].netWeight", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//内箱
				if(!StringHandler.isInt(interTradeOrderDtl.getInside())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].inside", "", Message.INT_IUPUT_ERROR);  
				}
				//外箱
				if(!StringHandler.isInt(interTradeOrderDtl.getOutside())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].outside", "", Message.INT_IUPUT_ERROR);  
				}
				//分批发货数1
				if(!StringHandler.isInt(interTradeOrderDtl.getSendQuantity1())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity1", "", Message.INT_IUPUT_ERROR);  
				}
				//分批发货数2
				if(!StringHandler.isInt(interTradeOrderDtl.getSendQuantity2())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity2", "", Message.INT_IUPUT_ERROR);  
				}
				//分批发货数3
				if(!StringHandler.isInt(interTradeOrderDtl.getSendQuantity3())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity3", "", Message.INT_IUPUT_ERROR);  
				}
				//分批发货数4
				if(!StringHandler.isInt(interTradeOrderDtl.getSendQuantity4())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity4", "", Message.INT_IUPUT_ERROR);  
				}
				//分批发货日期1
				if(!StringHandler.isDate(interTradeOrderDtl.getSendDate1())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate1", "", Message.DATE_IUPUT_ERROR);  
				}
				//分批发货日期2
				if(!StringHandler.isDate(interTradeOrderDtl.getSendDate2())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate2", "", Message.DATE_IUPUT_ERROR);  
				}
				//分批发货日期3
				if(!StringHandler.isDate(interTradeOrderDtl.getSendDate3())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate3", "", Message.DATE_IUPUT_ERROR);  
				}
				//分批发货日期4
				if(!StringHandler.isDate(interTradeOrderDtl.getSendDate4())){
					
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate4", "", Message.DATE_IUPUT_ERROR);  
				}
				//分批配套确认
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity1()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate1())){
					
					//分批发货数1
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity1())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity1", "", Message.EMPTY_ERROR);  
					}
					//分批发货日期1
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate1())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate1", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity2()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate2())){
					
					//分批发货数2
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity2())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity2", "", Message.EMPTY_ERROR);  
					}
					//分批发货日期2
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate2())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate2", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity3()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate3())){
					
					//分批发货数3
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity3())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity3", "", Message.EMPTY_ERROR);  
					}
					//分批发货日期3
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate3())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate3", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity4()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate4())){
					
					//分批发货数4
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity4())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendQuantity4", "", Message.EMPTY_ERROR);  
					}
					//分批发货日期4
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate4())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate4", "", Message.EMPTY_ERROR);  
					}
				}
				//费用1
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee1(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee1", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用2
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee2(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee2", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用3
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee3(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee3", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用4
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee4(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee4", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用配套确认
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle1()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee1())){
					
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle1())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].feeTitle1", "", Message.EMPTY_ERROR);  
					}
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee1())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee1", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle2()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee2())){
					
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle2())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].feeTitle2", "", Message.EMPTY_ERROR);  
					}
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee2())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee2", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle3()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee3())){
					
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle3())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].feeTitle3", "", Message.EMPTY_ERROR);  
					}
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee3())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee3", "", Message.EMPTY_ERROR);  
					}
				}
				if(!IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle4()) || !IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee4())){
					
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFeeTitle4())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].feeTitle4", "", Message.EMPTY_ERROR);  
					}
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getFee4())){
						
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee4", "", Message.EMPTY_ERROR);  
					}
				}
				
				//订单确认时的追加验证
				if(tradeOrder.getStatus().equals("1")
						|| tradeOrder.getStatus().equals("2")){
					
					//数量
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())){
							
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].quantity", "", Message.EMPTY_ERROR);  
					}
					//单价
					if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getUnitPrice())){
							
						br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].unitPrice", "", Message.EMPTY_ERROR);  
					}
					
					if("2".equals(interTradeOrder.getSendMode())){
						
						//分批发货日期1
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendDate1())){
							
							br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].sendDate1", "", Message.EMPTY_ERROR);  
						}
						
						//分批数量总和确认
						if(StringHandler.isInt(interTradeOrderDtl.getQuantity()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())){
							
							int sum = Integer.parseInt(interTradeOrderDtl.getQuantity());
							
							if(StringHandler.isInt(interTradeOrderDtl.getSendQuantity1()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity1())){
								
								sum = sum - Integer.parseInt(interTradeOrderDtl.getSendQuantity1());
							}
							if(StringHandler.isInt(interTradeOrderDtl.getSendQuantity2()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity2())){
								
								sum = sum - Integer.parseInt(interTradeOrderDtl.getSendQuantity2());
							}
							if(StringHandler.isInt(interTradeOrderDtl.getSendQuantity3()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity3())){
								
								sum = sum - Integer.parseInt(interTradeOrderDtl.getSendQuantity3());
							}
							if(StringHandler.isInt(interTradeOrderDtl.getSendQuantity4()) && !IsEmptyUtil.isEmpty(interTradeOrderDtl.getSendQuantity4())){
								
								sum = sum - Integer.parseInt(interTradeOrderDtl.getSendQuantity4());
							}
							if(sum != 0){
								
								br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].quantity", "", Message.WRONG_QUANTITY);  
							}

						}
					}
					
					if(!"0".equals(interTradeOrderDtl.getQuantitySent())
							&& interTradeOrderDtl.getQuantitySent() != null){
						
						//体积
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getVolume())){
								
							br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].volume", "", Message.EMPTY_ERROR);  
						}
						//毛重
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getGrossWeight())){
								
							br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].grossWeight", "", Message.EMPTY_ERROR);  
						}
						//净重
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getNetWeight())){
								
							br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].netWeight", "", Message.EMPTY_ERROR);  
						}
						//外箱
						if(IsEmptyUtil.isEmpty(interTradeOrderDtl.getOutside())){
								
							br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].outside", "", Message.EMPTY_ERROR);  
						}
					}
				}
			}
		}
		
		//订单完成时的追加验证
		if(tradeOrder.getStatus().equals("2")){
			
			boolean isAllSent = true;
			
			for (int i = 0; i < tradeOrder.getTradeOrderDtlList().size(); i++) {
				
				TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
				
				//数量
				if((IsEmptyUtil.isEmpty(tradeOrderDtl.getQuantity())
						|| IsEmptyUtil.isEmpty(tradeOrderDtl.getQuantitySent()))
						|| (StringHandler.isInt(tradeOrderDtl.getQuantity()) 
						&& StringHandler.isInt(tradeOrderDtl.getQuantitySent())
						&& Integer.parseInt(tradeOrderDtl.getQuantity()) > Integer.parseInt(tradeOrderDtl.getQuantitySent()))){
					
					isAllSent = false;
				}
			}
			
			for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
				
				InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
				
				for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
					
					InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
					
					//数量
					if((IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantity())
							|| IsEmptyUtil.isEmpty(interTradeOrderDtl.getQuantitySent()))
							|| (StringHandler.isInt(interTradeOrderDtl.getQuantity()) 
							&& StringHandler.isInt(interTradeOrderDtl.getQuantitySent())
							&& Integer.parseInt(interTradeOrderDtl.getQuantity()) > Integer.parseInt(interTradeOrderDtl.getQuantitySent()))){
						
						isAllSent = false;
					}
				}
			}
			
			if(isAllSent == false){
				
				br.rejectValue("status", "", Message.COMPLETE_STATUS_ERROR);
			}
		}
	}
	
	/**
	 * 订单计算验证
	 * @param tradeOrder
	 * @param br
	 */
	private void validate4C(BindingResult br, TradeOrder tradeOrder) throws Exception{
		
		for (int i = 0; i < tradeOrder.getTradeOrderDtlList().size(); i++) {
			
			TradeOrderDtl tradeOrderDtl = tradeOrder.getTradeOrderDtlList().get(i);
			
			//数量
			if(!StringHandler.isInt(tradeOrderDtl.getQuantity())){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].quantity", "", Message.INT_IUPUT_ERROR);  
			}
			//单价
			if(!StringHandler.isDouble(tradeOrderDtl.getUnitPrice(), 3)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].unitPrice", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用1
			if(!StringHandler.isDouble(tradeOrderDtl.getFee1(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee1", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用2
			if(!StringHandler.isDouble(tradeOrderDtl.getFee2(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee2", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用3
			if(!StringHandler.isDouble(tradeOrderDtl.getFee3(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee3", "", Message.DOUBLE_IUPUT_ERROR);  
			}
			//费用4
			if(!StringHandler.isDouble(tradeOrderDtl.getFee4(), 2)){
					
				br.rejectValue("tradeOrderDtlList[" + i + "].fee4", "", Message.DOUBLE_IUPUT_ERROR);  
			}
		}
		
		for (int i = 0; i < tradeOrder.getInterTradeOrderList().size(); i++) {
			
			InterTradeOrder interTradeOrder = tradeOrder.getInterTradeOrderList().get(i);
				
			for (int j = 0; j < interTradeOrder.getInterTradeOrderDtlList().size(); j++) {
				
				InterTradeOrderDtl interTradeOrderDtl = interTradeOrder.getInterTradeOrderDtlList().get(j);
				
				//数量
				if(!StringHandler.isInt(interTradeOrderDtl.getQuantity())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].quantity", "", Message.INT_IUPUT_ERROR);  
					
				}
				//单价
				if(!StringHandler.isDouble(interTradeOrderDtl.getUnitPrice(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].unitPrice", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//体积
				if(!StringHandler.isDouble(interTradeOrderDtl.getVolume(), 4)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].volume", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//毛重
				if(!StringHandler.isDouble(interTradeOrderDtl.getGrossWeight(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].grossWeight", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//净重
				if(!StringHandler.isDouble(interTradeOrderDtl.getNetWeight(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].netWeight", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//内箱
				if(!StringHandler.isInt(interTradeOrderDtl.getInside())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].inside", "", Message.INT_IUPUT_ERROR);  
				}
				//外箱
				if(!StringHandler.isInt(interTradeOrderDtl.getOutside())){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].outside", "", Message.INT_IUPUT_ERROR);  
				}
				//费用1
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee1(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee1", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用2
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee2(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee2", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用3
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee3(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee3", "", Message.DOUBLE_IUPUT_ERROR);  
				}
				//费用4
				if(!StringHandler.isDouble(interTradeOrderDtl.getFee4(), 2)){
						
					br.rejectValue("interTradeOrderList[" + i + "].interTradeOrderDtlList[" + j + "].fee4", "", Message.DOUBLE_IUPUT_ERROR);  
				}
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
