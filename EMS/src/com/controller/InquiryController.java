package com.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.servlet.ModelAndView;

import com.common.Token;
import com.context.Message;
import com.entity.Inquiry;
import com.entity.InquiryDtl;
import com.entity.Production;
import com.service.InquiryService;
import com.service.ProductionService;
import com.service.TradeOrderService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/inquiry")
public class InquiryController{
	
	@Autowired
	private InquiryService inquiryService = null;
	
	@Autowired
	private ProductionService productionService = null;
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	/**
	 * 询单追加产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addProduction")
	public ModelAndView addProduction(@ModelAttribute("inquiry")Inquiry inquiry, 
			@RequestParam("pageMode")String pageMode, 
			@RequestParam("productions")String productions,
			HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		
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
    		
    		boolean productionAdded = false;
    				
    		//如果已经添加过该产品，返回页面报错
    		for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
    			
    			if(inquiryDtl.getProductionId().equals(production.getProductionId())
    					&& inquiryDtl.getVersionNo().equals(production.getVersionNo())){
    				
    				errorMessage = errorMessage.concat(String.format(Message.DUPLICATED_PRODUCTION_IN_RECEIPT, inquiryDtl.getProductionId(), inquiryDtl.getVersionNo())).concat("</br>");
        			productionAdded = true;
        			break;
    			}
    		}
    		
    		if(productionAdded == false){
    			
    			//设定询单明细
    			InquiryDtl inquiryDtl = new InquiryDtl();
    			inquiryDtl.setProductionId(production.getProductionId());
    			inquiryDtl.setVersionNo(production.getVersionNo());
    			inquiryDtl.setDescriptionE(production.getDescriptionE());
    			inquiryDtl.setDescriptionC(production.getDescriptionC());
    			inquiryDtl.setTaxReturnRate(production.getTaxReturnRate());
    			inquiryDtl.setProductionUpdateTime(production.getProductionUpdateTime());
    			
    			//上次询单数据获取
    			InquiryDtl lastInquiryDtl = inquiryService.findLastDtlByProductionId(inquiry.getInquiryId(), inquiryDtl.getProductionId());
    			if(lastInquiryDtl != null){
    				
    				inquiryDtl.setPreviousInquiryId(lastInquiryDtl.getInquiryId());
    				inquiryDtl.setPreviousTradeOrderId(lastInquiryDtl.getTradeOrderId());
    				inquiryDtl.setPreviousRMB(lastInquiryDtl.getRMB());
    				inquiryDtl.setPreviousUSD(lastInquiryDtl.getFinalPrice());
    				inquiryDtl.setPreviousRate(lastInquiryDtl.getRate());
    			}
    			
    			//添加到询单明细list
    			inquiry.getInquiryDtlList().add(inquiryDtl);
    		}
        }
		
		if(pageMode.equals("add")){
			mav.setViewName("inquiryAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYADD);
		}else{
			mav.setViewName("inquiryUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYUPDATE);
		}
		mav.addObject("inquiry", inquiry);
		if(!IsEmptyUtil.isEmpty(errorMessage)){
			mav.addObject("errorMessage", errorMessage);
		}
		return mav;
	}
	
	/**
	 * 询单删除产品
	 * @param tradeOrder
	 * @param productionId
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/deleteProduction")
	public ModelAndView deleteProduction(@ModelAttribute("inquiry")Inquiry inquiry, 
			@RequestParam("productionId")String productionId, 
			@RequestParam("pageMode")String pageMode, 
			HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		
		ModelAndView mav = new ModelAndView();
		
		//删除询单中该产品
		for (int i = 0; i < inquiry.getInquiryDtlList().size(); i++) {
			
			if(inquiry.getInquiryDtlList().get(i).getProductionId().equals(productionId)){
				
				inquiry.getInquiryDtlList().remove(i);
				break;
			}
		}
		
		if(pageMode.equals("add")){
			mav.setViewName("inquiryAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYADD);
		}else{
			mav.setViewName("inquiryUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYUPDATE);
		}
		mav.addObject("inquiry", inquiry);
		return mav;
	}
	
	/**
	 * 询单检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_inquiry");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("inquiry", new Inquiry());
		mav.setViewName("inquirySearch");
		return mav;
	}
	
	/**
	 * 询单检索
	 * @param inquiry
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Inquiry inquiry, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(inquiry.getIsBack())
				&& null != session.getAttribute("searchCondition_inquiry")){
			
			inquiry = (Inquiry)session.getAttribute("searchCondition_inquiry");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_inquiry", inquiry);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			inquiry = inquiryService.inquirySearch(inquiry);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("inquiry", inquiry);
		mav.setViewName("inquirySearch");
		
		return mav;
	}
	
	/**
	 * 询单照会画面初期化
	 * @param inquiryIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("inquiryIdSelected")String inquiryIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYREF);
		
		ModelAndView mav = new ModelAndView();
		try {
			Inquiry inquiry = inquiryService.findByKey(inquiryIdSelected);
			if(inquiry != null){
				
				List<InquiryDtl> inquiryDtlList = inquiryService.findDtlByKey(inquiry.getInquiryId());
				inquiry.setInquiryDtlList(inquiryDtlList);
			}
			
			mav.addObject("inquiry", inquiry);
			mav.setViewName("inquiryRef");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Inquiry inquiry = new Inquiry();
			inquiry.setIsBack("1");
			mav.addObject("inquiry", inquiry);
			mav.setViewName("forward:/inquiry/search");
		}
		return mav;
	}
	
	/**
	 * 询单追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYADD);
		
		ModelAndView mav = new ModelAndView();
		Inquiry inquiry = new Inquiry();
		mav.addObject("inquiry", inquiry);
		mav.setViewName("inquiryAdd");
		return mav;
	}
	
	/**
	 * 询单追加
	 * @param inquiry
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("inquiry")Inquiry inquiry, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("inquiryAdd");
		
		try {
			//输入验证
			validate(br, inquiry);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//计算
				calculate(inquiry);
				//询单登録
				String newInquiryId = inquiryService.add(inquiry);
				//询单登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_INQUIRY + newInquiryId);
				mav.setViewName("forward:/inquiry/searchShow");
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
	 * 询单变更画面初期化
	 * @param inquiryIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView inquiryUpdateGet(@RequestParam("inquiryIdSelected")String inquiryIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYUPDATE);
		
		ModelAndView mav = new ModelAndView();
		try {
			Inquiry inquiry = inquiryService.findByKey(inquiryIdSelected);
			if(inquiry != null){
				
				List<InquiryDtl> inquiryDtlList = inquiryService.findDtlByKey(inquiry.getInquiryId());
				inquiry.setInquiryDtlList(inquiryDtlList);
			}
			
			mav.addObject("inquiry", inquiry);
			mav.setViewName("inquiryUpdate");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			//出错返回检索画面
			Inquiry inquiry = new Inquiry();
			inquiry.setIsBack("1");
			mav.addObject("inquiry", inquiry);
			mav.setViewName("forward:/inquiry/search");
		}
		return mav;
	}
	
	/**
	 * 询单变更
	 * @param inquiry
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView inquiryUpdate(@Valid @ModelAttribute("inquiry")Inquiry inquiry, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("inquiryUpdate");
		
		//入力チェック
		try {
			validate(br, inquiry);
		
			//入力チェック結果がOKの場合
			if(!br.hasErrors()){
				
					//计算
					calculate(inquiry);
					//询单更新
					inquiryService.update(inquiry);
					
					//询单更新完了、返回检索画面
					mav.addObject("errorMessage", Message.SUCCESS_UPDATE_INQUIRY + inquiry.getInquiryId());
					mav.setViewName("forward:/inquiry/searchShow");
				
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
	 * 询单计算
	 * @param inquiry
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/calculate")
	public ModelAndView calculatePost(@Valid @ModelAttribute("inquiry")Inquiry inquiry, BindingResult br, HttpSession session, @RequestParam("pageMode")String pageMode) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "inquiryManagement");
		
		ModelAndView mav = new ModelAndView();
		
		if(pageMode.equals("add")){
			mav.setViewName("inquiryAdd");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYADD);
		}else{
			mav.setViewName("inquiryUpdate");
			//CURRENT画面标题设定
			session.setAttribute("pageTitle", Message.PAGETITLE_INQUIRYUPDATE);
		}
		
		try {
			//输入验证
			validate(br, inquiry);
			
			//输入验证結果OKの場合
			if(!br.hasErrors()){
				
				//计算
				calculate(inquiry);
				mav.addObject("inquiry", inquiry);
			} else {
				mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
			}
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 询单削除
	 * @param inquiryIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("inquiryIdSelected")String inquiryIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//询单削除
			inquiryService.delete(inquiryIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_INQUIRY + inquiryIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/inquiry/searchShow");
		return mav;
	}
	
	/**
	 * 询单返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Inquiry inquiry = new Inquiry();
		inquiry.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/inquiry/search");
		mav.addObject("inquiry", inquiry);
		return mav;
	}
	
	/**
	 * 询单验证
	 * @param inquiry
	 * @param br
	 */
	private void validate(BindingResult br, Inquiry inquiry) throws Exception{
		
		//至少要添加一件产品
		if(IsEmptyUtil.isEmpty(inquiry.getInquiryDtlList())){
			
			throw new Exception(Message.PRODUCTION_EMPTY_ERROR);
		}
		
		for (int i = 0; i < inquiry.getInquiryDtlList().size(); i++) {
			
			InquiryDtl inquiryDtl = inquiry.getInquiryDtlList().get(i);
			
//			//本次买断价
//			if(IsEmptyUtil.isEmpty(inquiryDtl.getBuyoutPrice())){
//				
//				br.rejectValue("inquiryDtlList[" + i + "].buyoutPrice", "", Message.EMPTY_ERROR);  
//			}else if(!StringHandler.isDouble(inquiryDtl.getBuyoutPrice(), 2)){
//				
//				br.rejectValue("inquiryDtlList[" + i + "].buyoutPrice", "", Message.DOUBLE_IUPUT_ERROR);  
//			}else if(StringHandler.isZero(inquiryDtl.getBuyoutPrice())){
//				
//				br.rejectValue("inquiryDtlList[" + i + "].buyoutPrice", "", Message.NO_ZERO_IUPUT_ERROR);  
//			}
			
			//本次RMB
			if(IsEmptyUtil.isEmpty(inquiryDtl.getPreviousRMB())){
				
				if(IsEmptyUtil.isEmpty(inquiryDtl.getRMB())){
					
					br.rejectValue("inquiryDtlList[" + i + "].RMB", "", Message.EMPTY_ERROR);  
				}else if(!StringHandler.isDouble(inquiryDtl.getRMB(), 2)){
					
					br.rejectValue("inquiryDtlList[" + i + "].RMB", "", Message.DOUBLE_IUPUT_ERROR);  
				}else if(StringHandler.isZero(inquiryDtl.getRMB())){
					
					br.rejectValue("inquiryDtlList[" + i + "].RMB", "", Message.NO_ZERO_IUPUT_ERROR);  
				}
			}
			
			//本次汇率
			if(IsEmptyUtil.isEmpty(inquiryDtl.getRate())){
				
				br.rejectValue("inquiryDtlList[" + i + "].rate", "", Message.EMPTY_ERROR);  
			}else if(!StringHandler.isDouble(inquiryDtl.getRate(), 2)){
				
				br.rejectValue("inquiryDtlList[" + i + "].rate", "", Message.DOUBLE_IUPUT_ERROR);  
			}else if(StringHandler.isZero(inquiryDtl.getRate())){
				
				br.rejectValue("inquiryDtlList[" + i + "].rate", "", Message.NO_ZERO_IUPUT_ERROR);  
			}
			
			//调整金额
			if(!StringHandler.isDoubleZF(inquiryDtl.getAdjustPrice(), 3)){
				
				br.rejectValue("inquiryDtlList[" + i + "].adjustPrice", "", Message.DOUBLE_IUPUT_ERROR);  
			}
		}
	}
	
	private Inquiry calculate(Inquiry inquiry) throws Exception{
		
		for (InquiryDtl inquiryDtl : inquiry.getInquiryDtlList()) {
			
			if(!IsEmptyUtil.isEmpty(inquiryDtl.getTradeOrderId())){
				
				continue;
			}
			
			boolean autoCalcFlg = IsEmptyUtil.isEmpty(inquiryDtl.getRMB());
			
			if(autoCalcFlg){
				
				inquiryDtl.setRMB(inquiryDtl.getPreviousRMB());
			}
			
			//RMB
			BigDecimal previousRMB = new BigDecimal(inquiryDtl.getPreviousRMB() != null ? inquiryDtl.getPreviousRMB() : inquiryDtl.getRMB());
			BigDecimal RMB = new BigDecimal(inquiryDtl.getRMB());
			
			//Rate
			BigDecimal previousRate = new BigDecimal(inquiryDtl.getPreviousRate() != null ? inquiryDtl.getPreviousRate() : inquiryDtl.getRate());
			BigDecimal rate = new BigDecimal(inquiryDtl.getRate());
			
			//上次最终报价
			BigDecimal previousUSD = inquiryDtl.getPreviousUSD() != null ? new BigDecimal(inquiryDtl.getPreviousUSD()) : RMB.divide(rate, 10, BigDecimal.ROUND_HALF_UP);
			
			//本次买断价
			BigDecimal buyoutPrice = null;
			if("13.00".equals(inquiryDtl.getTaxReturnRate())){
				
				buyoutPrice = rate.subtract(new BigDecimal(0.065)).multiply(new BigDecimal(1.14)).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else if("10.00".equals(inquiryDtl.getTaxReturnRate())){
				
				buyoutPrice = rate.subtract(new BigDecimal(0.065)).multiply(new BigDecimal(1.13)).divide(new BigDecimal(1.04), 10, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				throw new Exception(String.format(Message.WRONG_TAX_RETURN, inquiryDtl.getProductionId(), inquiryDtl.getVersionNo()));
			}
			
			BigDecimal adjustPrice = inquiryDtl.getAdjustPrice() != null ? new BigDecimal(inquiryDtl.getAdjustPrice()) : new BigDecimal(0);
			
			BigDecimal preliminaryPrice = null;
			if(rate.compareTo(previousRate) >= 0 && RMB.compareTo(previousRMB) <= 0){
				
				preliminaryPrice = previousUSD;
			}else{
				
				preliminaryPrice = previousUSD.multiply(previousRate).divide(rate, 10, BigDecimal.ROUND_HALF_UP).add((RMB.subtract(previousRMB).divide(buyoutPrice, 10, BigDecimal.ROUND_HALF_UP))) ;
			}
			
			BigDecimal finalPrice = null;
			if(autoCalcFlg || !IsEmptyUtil.isEmpty(inquiryDtl.getFinalPrice())){
				
				finalPrice = !IsEmptyUtil.isEmpty(inquiryDtl.getFinalPrice()) ? new BigDecimal(inquiryDtl.getFinalPrice()) : previousUSD;
				adjustPrice = finalPrice.subtract(preliminaryPrice);
			}else{
				
				finalPrice = preliminaryPrice.add(adjustPrice);
			}
			
			BigDecimal factoryPriceFluctuation = RMB.divide(previousRMB, 10, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
			BigDecimal rateFluctuation = rate.divide(previousRate, 10, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
			BigDecimal finalDollarPriceFluctuation = finalPrice.divide(previousUSD, 10, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
			
			BigDecimal costProfitRatio = finalPrice.multiply(buyoutPrice).divide(RMB, 10, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
			
			//本次买断价
			inquiryDtl.setBuyoutPrice(StringHandler.numFormat(buyoutPrice, StringHandler.NUMBER_FORMAT2));
			
			//本次RMB
			inquiryDtl.setRMB(StringHandler.numFormat(RMB, StringHandler.NUMBER_FORMAT2));
			
			//本次汇率
			inquiryDtl.setRate(StringHandler.numFormat(rate, StringHandler.NUMBER_FORMAT2));
			
			//初步标价
			inquiryDtl.setPreliminaryPrice(StringHandler.numFormat(preliminaryPrice, StringHandler.NUMBER_FORMAT3));
			
			//调整金额
			inquiryDtl.setAdjustPrice(StringHandler.numFormat(adjustPrice, StringHandler.NUMBER_FORMAT3));
			
			//最终报价
			inquiryDtl.setFinalPrice(StringHandler.numFormat(finalPrice, StringHandler.NUMBER_FORMAT3));
			
			//工厂涨价浮动
			inquiryDtl.setFactoryPriceFluctuation(StringHandler.numFormat(factoryPriceFluctuation, StringHandler.NUMBER_FORMAT2));
			
			//汇率变化浮动
			inquiryDtl.setRateFluctuation(StringHandler.numFormat(rateFluctuation, StringHandler.NUMBER_FORMAT2));
			
			//最终报价浮动
			inquiryDtl.setFinalDollarPriceFluctuation(StringHandler.numFormat(finalDollarPriceFluctuation, StringHandler.NUMBER_FORMAT2));
			
			//成本利润率
			inquiryDtl.setCostProfitRatio(StringHandler.numFormat(costProfitRatio, StringHandler.NUMBER_FORMAT2));
			
		}
		
		return inquiry;
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
