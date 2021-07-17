package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.entity.Production;
import com.service.CustomerService;
import com.service.InquiryService;
import com.service.ProductionService;
import com.service.TradeOrderService;
import com.service.ValidationAPIService;
import com.service.VendorService;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;

@Controller
@RequestMapping("/production")
public class ProductionController{
	
	@Autowired
	private ProductionService productionService = null;
	
	@Autowired
	private VendorService vendorService = null;
	
	@Autowired
	private CustomerService customerService = null;
	
	@Autowired
	private TradeOrderService tradeOrderService = null;
	
	@Autowired
	private InquiryService inquiryService = null;
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	/**
	 * 产品检索POPUP画面初期化
	 * @param customerId
	 * @param customerName
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView productionSearchPopGet(@RequestParam(value="customerId")String customerId, @RequestParam(value="customerName")String customerName) {
		
		ModelAndView mav = new ModelAndView();
		Production production = new Production();
		production.setCustomerName4S(customerName);
		production.setCustomerId4S(customerId);
		
		mav.addObject("production", production);
		mav.setViewName("productionPopSearch");
		return mav;
	}
	
	/**
	 * 产品POPUP检索
	 * @param production
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPop")
	public ModelAndView productionSearchPopPost(Production production) {
		
		ModelAndView mav = new ModelAndView();
		try {
			//已审核的产品为前提条件
			production.setStatus4S("1");
			production = productionService.productionSearch(production);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("production", production);
		mav.setViewName("productionPopSearch");
		
		return mav;
	}
	
	/**
	 * 产品检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView productionSearchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_production");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("production", new Production());
		mav.setViewName("productionSearch");
		return mav;
	}
	
	/**
	 * 产品检索
	 * @param production
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView productionSearchPost(Production production, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(production.getIsBack())
				&& null != session.getAttribute("searchCondition_production")){
			
			production = (Production)session.getAttribute("searchCondition_production");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_production", production);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			production = productionService.productionSearch(production);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("production", production);
		mav.setViewName("productionSearch");
		
		return mav;
	}
	
	/**
	 * 产品照会画面初期化
	 * @param productionIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("productionIdSelected")String productionIdSelected, @RequestParam("versionNoSelected")String versionNoSelected, HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONREF);
		
		Production production = productionService.findByKey(productionIdSelected, versionNoSelected);
		
		productionService.setImgFileName(production);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("production", production);
		mav.setViewName("productionRef");
		return mav;
	}
	
	/**
	 * 产品复制画面初期化
	 * @param productionIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/copyShow")
	@Token(save = true, remove= false)
	public ModelAndView copyGet(@RequestParam("productionIdSelected")String productionIdSelected, @RequestParam("versionNoSelected")String versionNoSelected, HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONADD);
		
		ModelAndView mav = new ModelAndView();
		
		try{
			
			Production production = productionService.findByKey(productionIdSelected, versionNoSelected);
			production.setVersionNo("1");
			
			productionService.setImgFileName(production);
			
			mav.addObject("production", production);
			mav.setViewName("productionAdd");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("forward:/production/searchShow");
		}
		
		return mav;
	}
	
	/**
	 * 产品追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONADD);
		
		ModelAndView mav = new ModelAndView();
		Production production = new Production();
		production.setVersionNo("1");
		production.setStatus("1");
		mav.addObject("production", production);
		mav.setViewName("productionAdd");
		return mav;
	}
	
	/**
	 * 产品追加
	 * @param production
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("production")Production production, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("productionAdd");
		
		//输入验证
		validate(br, production);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			try {
				//产品登録
				String newProductionId = productionService.add(production);
				
				//画像更新
				productionService.saveImgFile(production);
				
				//产品登録完了、返回检索画面
				mav.addObject("errorMessage", String.format(Message.SUCCESS_CREATE_PRODUCTION, newProductionId, "1"));
				mav.setViewName("forward:/production/searchShow");
			} catch (Exception e) {
				tokenReset(mav, session);
				mav.addObject("errorMessage", e.getMessage());
			}
		} else {
			tokenReset(mav, session);
			mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
		}
		return mav;
	}
	
	/**
	 * 产品变更画面初期化
	 * @param productionIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView productionUpdateGet(@RequestParam("productionIdSelected")String productionIdSelected, @RequestParam("versionNoSelected")String versionNoSelected, HttpSession session) throws Exception{
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONUPDATE);
		
		ModelAndView mav = new ModelAndView();
		
		try{
			
			Production production = productionService.findByKey(productionIdSelected, versionNoSelected);
			
			if(!validationAPIService.validateCU(production.getCustomerId())){
				
				throw new Exception(Message.NO_CUSTOMER_AUTH);
			}
			
			productionService.setImgFileName(production);
			
			mav.addObject("production", production);
			mav.setViewName("productionUpdate");
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("forward:/production/searchShow");
		}
		
		return mav;
	}
	
	/**
	 * 产品变更
	 * @param production
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView productionUpdate(@Valid @ModelAttribute("production")Production production, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "productionManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_PRODUCTIONUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("productionUpdate");
		
		//输入验证
		validate(br, production);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			String orignalVersionNo =  production.getVersionNo();
			try {
				
				//产品更新
				productionService.update(production);
				
				//画像更新
				productionService.saveImgFile(production);
				
				//产品更新完了、返回检索画面
				mav.addObject("errorMessage", String.format(Message.SUCCESS_UPDATE_PRODUCTION, production.getProductionId(), production.getVersionNo()));
				mav.setViewName("forward:/production/searchShow");
			} catch (Exception e) {
				
				production.setVersionNo(orignalVersionNo);
				tokenReset(mav, session);
				mav.addObject("errorMessage", e.getMessage());
			}
		} else {
			
			tokenReset(mav, session);
			mav.addObject("errorMessage", Message.PAGE_VALIDATE_ERROR);
		}
		return mav;
	}
	
	/**
	 * 产品削除
	 * @param productionIdSelected
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView productionDelete(@RequestParam("productionIdSelected")String productionIdSelected, @RequestParam("versionNoSelected")String versionNoSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//产品削除
			productionService.delete(productionIdSelected, versionNoSelected);
			mav.addObject("errorMessage", String.format(Message.SUCCESS_DELETE_PRODUCTION, productionIdSelected, versionNoSelected) );
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/production/searchShow");
		return mav;
	}
	
	/**
	 * 产品返回检索画面
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView productionReturn(){
		
		Production production = new Production();
		production.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/production/search");
		mav.addObject("production", production);
		return mav;
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
	            	
	            	int i = (int)(Math.random() * 900) + 100; 
	                originalFilename = DateUtil.getNowDateString(DateUtil.DATE_FORMAT2 + i);
	                System.out.println("文件原名: " + originalFilename);
	                System.out.println("文件名称: " + picFile.getName());
	                System.out.println("文件长度: " + picFile.getSize());
	                System.out.println("文件类型: " + picFile.getContentType());
	                System.out.println("========================================");
	                try {
	                	picFile.transferTo(new File(ConstantParam.PRODUCTION_PIC_ROOT_TEMP_REALPATH, originalFilename));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    out.print("1");
	                    out.flush();
	                    return null;
	                }
	            }
	        }
	        out.print("0" + ConstantParam.PRODUCTION_PIC_ROOT_TEMP + "/" + originalFilename);
	        out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
	}  
	
	/**
	 * 输入验证
	 * @param production
	 * @param br
	 */
	private void validate(BindingResult br, Production production){
		
		if(IsEmptyUtil.isEmpty(production.getProductionId())){
			
			br.rejectValue("productionId", "", Message.EMPTY_ERROR);  
		}else if(production.getProductionId().contains("\"")
				|| production.getProductionId().contains("'")
				|| production.getProductionId().contains("'")){
			
			br.rejectValue("productionId", "", Message.PRODUCTIONID_INPUTERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(production.getDescriptionE())){
			
			br.rejectValue("descriptionE", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(production.getProductionCname4export())){
			
			br.rejectValue("productionCname4export", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(production.getProductionEname4export())){
			
			br.rejectValue("productionEname4export", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(production.getVendorId())){
			
			br.rejectValue("vendorId", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(production.getTaxReturnRate())){
			
			br.rejectValue("taxReturnRate", "", Message.EMPTY_ERROR);  
		}
		
		if(!StringHandler.isRate(production.getTaxReturnRate())){
			
			br.rejectValue("taxReturnRate", "", Message.RATE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDouble(production.getVolume(), 4)){
			
			br.rejectValue("volume", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDouble(production.getGrossWeight(), 2)){
			
			br.rejectValue("grossWeight", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDouble(production.getNetWeight(), 2)){
			
			br.rejectValue("netWeight", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isInt(production.getInside())){
			
			br.rejectValue("inside", "", Message.INT_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isInt(production.getOutside())){
			
			br.rejectValue("outside", "", Message.INT_IUPUT_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
