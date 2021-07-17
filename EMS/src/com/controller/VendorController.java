package com.controller;

import java.math.BigInteger;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.common.Token;
import com.context.Message;
import com.entity.Vendor;
import com.service.VendorService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/vendor")
public class VendorController{
	
	@Autowired
	private VendorService vendorService = null;
	
	/**
	 * 供应商检索POPUP画面初期化
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView searchPopGet(@RequestParam(value="noAuth", required=false)String noAuth) {
		
		ModelAndView mav = new ModelAndView();
		Vendor vendor = new Vendor();
		if("1".equals(noAuth)){
			vendor.setNoAuth(noAuth);
		}
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorPopSearch");
		return mav;
	}
	
	/**
	 * 供应商POPUP检索
	 * @param vendor
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPop")
	public ModelAndView searchPopPost(Vendor vendor) {
		
		ModelAndView mav = new ModelAndView();
		try {
			vendor = vendorService.vendorSearch(vendor);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorPopSearch");
		
		return mav;
	}
	
	/**
	 * 供应商检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_vendor");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vendor", new Vendor());
		mav.setViewName("vendorSearch");
		return mav;
	}
	
	/**
	 * 供应商检索
	 * @param vendor
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Vendor vendor, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(vendor.getIsBack())
				&& null != session.getAttribute("searchCondition_vendor")){
			
			vendor = (Vendor)session.getAttribute("searchCondition_vendor");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_vendor", vendor);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			vendor = vendorService.vendorSearch(vendor);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorSearch");
		
		return mav;
	}
	
	/**
	 * 供应商照会画面初期化
	 * @param vendorIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("vendorIdSelected")String vendorIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORREF);
		
		Vendor vendor = vendorService.findByKey(vendorIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorRef");
		return mav;
	}
	
	/**
	 * 供应商复制画面初期化
	 * @param vendorIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/copyShow")
	@Token(save = true, remove= false)
	public ModelAndView copyGet(@RequestParam("vendorIdSelected")String vendorIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORADD);
		
		Vendor vendor = vendorService.findByKey(vendorIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorAdd");
		return mav;
	}
	
	/**
	 * 供应商追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORADD);
		
		ModelAndView mav = new ModelAndView();
		Vendor vendor = new Vendor();
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorAdd");
		return mav;
	}
	
	/**
	 * 供应商追加
	 * @param vendor
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("vendor")Vendor vendor, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("vendorAdd");
		
		//输入验证
		validate(br, vendor);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//供应商登録
			try {
				String newVendorId = vendorService.add(vendor);
				//供应商登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_VENDOR + newVendorId);
				mav.setViewName("forward:/vendor/searchShow");
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
	 * 供应商变更画面初期化
	 * @param vendorIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView updateGet(@RequestParam("vendorIdSelected")String vendorIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORUPDATE);
		
		Vendor vendor = vendorService.findByKey(vendorIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vendor", vendor);
		mav.setViewName("vendorUpdate");
		return mav;
	}
	
	/**
	 * 供应商变更
	 * @param vendor
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView update(@Valid @ModelAttribute("vendor")Vendor vendor, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "vendorManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_VENDORUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("vendorUpdate");
		
		//输入验证
		validate(br, vendor);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			try {
				
				//供应商更新
				vendorService.update(vendor);
				
				//供应商更新完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_UPDATE_VENDOR + vendor.getVendorId());
				mav.setViewName("forward:/vendor/searchShow");
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
	 * 供应商削除
	 * @param vendorIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("vendorIdSelected")String vendorIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//供应商削除
			vendorService.delete(vendorIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_VENDOR + vendorIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/vendor/searchShow");
		return mav;
	}
	
	/**
	 * 供应商返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Vendor vendor = new Vendor();
		vendor.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/vendor/search");
		mav.addObject("vendor", vendor);
		return mav;
	}
	
	/**
	 * 输入验证
	 * @param vendor
	 * @param br
	 */
	private void validate(BindingResult br, Vendor vendor){
		
		if(IsEmptyUtil.isEmpty(vendor.getVendorId())){
			
			br.rejectValue("vendorId", "", Message.EMPTY_ERROR);  
		}else if(!StringHandler.isVCId(vendor.getVendorId())){
			
			br.rejectValue("vendorId", "", String.format(Message.ID_ERROR, "3"));  
		}
		
		if(IsEmptyUtil.isEmpty(vendor.getVendorName())){
			
			br.rejectValue("vendorName", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(vendor.getVendorFullName())){
			
			br.rejectValue("vendorFullName", "", Message.EMPTY_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
