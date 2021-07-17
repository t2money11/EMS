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
import com.entity.UserCustomerLinkRef;
import com.service.UserCustomerLinkRefService;
import com.util.IsEmptyUtil;


@Controller
@RequestMapping("/userCustomerLinkRef")
public class UserCustomerLinkRefController{
	
	@Autowired
	private UserCustomerLinkRefService userCustomerLinkRefService = null;
	
	/**
	 * 客户担当检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_userCustomerLinkRef");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userCustomerLinkRef", new UserCustomerLinkRef());
		mav.setViewName("userCustomerLinkRefSearch");
		return mav;
	}
	
	/**
	 * 客户担当检索
	 * @param userCustomerLinkRef
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(UserCustomerLinkRef userCustomerLinkRef, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(userCustomerLinkRef.getIsBack())
				&& null != session.getAttribute("searchCondition_userCustomerLinkRef")){
			
			userCustomerLinkRef = (UserCustomerLinkRef)session.getAttribute("searchCondition_userCustomerLinkRef");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_userCustomerLinkRef", userCustomerLinkRef);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			userCustomerLinkRef = userCustomerLinkRefService.userCustomerLinkRefSearch(userCustomerLinkRef);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		mav.setViewName("userCustomerLinkRefSearch");
		
		return mav;
	}
	
	/**
	 * 客户担当照会画面初期化
	 * @param userCustomerLinkRefIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("userIdSelected")String userIdSelected, @RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERREF);
		
		UserCustomerLinkRef userCustomerLinkRef = userCustomerLinkRefService.findByKey(userIdSelected, customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		mav.setViewName("userCustomerLinkRefRef");
		return mav;
	}
	
	/**
	 * 客户担当复制画面初期化
	 * @param userCustomerLinkRefIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/copyShow")
	@Token(save = true, remove= false)
	public ModelAndView copyGet(@RequestParam("userIdSelected")String userIdSelected, @RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERADD);
		
		UserCustomerLinkRef userCustomerLinkRef = userCustomerLinkRefService.findByKey(userIdSelected, customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		mav.setViewName("userCustomerLinkRefAdd");
		return mav;
	}
	
	/**
	 * 客户担当追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERADD);
		
		ModelAndView mav = new ModelAndView();
		UserCustomerLinkRef userCustomerLinkRef = new UserCustomerLinkRef();
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		mav.setViewName("userCustomerLinkRefAdd");
		return mav;
	}
	
	/**
	 * 客户担当追加
	 * @param userCustomerLinkRef
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("userCustomerLinkRef")UserCustomerLinkRef userCustomerLinkRef, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userCustomerLinkRefAdd");
		
		//输入验证
		validate(br, userCustomerLinkRef);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//客户担当登録
			try {
				userCustomerLinkRef = userCustomerLinkRefService.add(userCustomerLinkRef);
				//客户担当登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_USER_CUSTOMER + userCustomerLinkRef.getCustomerId() + "⇒" + userCustomerLinkRef.getUserId());
				mav.setViewName("forward:/userCustomerLinkRef/searchShow");
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
	 * 客户担当变更画面初期化
	 * @param userCustomerLinkRefIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView updateGet(@RequestParam("userIdSelected")String userIdSelected, @RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERUPDATE);
		
		UserCustomerLinkRef userCustomerLinkRef = userCustomerLinkRefService.findByKey(userIdSelected, customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		mav.setViewName("userCustomerLinkRefUpdate");
		return mav;
	}
	
	/**
	 * 客户担当变更
	 * @param userCustomerLinkRef
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView update(@Valid @ModelAttribute("userCustomerLinkRef")UserCustomerLinkRef userCustomerLinkRef, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userCustomerLinkRefManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USER_CUSTOMERUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userCustomerLinkRefUpdate");
		
		//输入验证
		validate(br, userCustomerLinkRef);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			try {
				
				//客户担当更新
				userCustomerLinkRefService.update(userCustomerLinkRef);
				
				//客户担当更新完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_UPDATE_USER_CUSTOMER + userCustomerLinkRef.getCustomerId() + "⇒" + userCustomerLinkRef.getUserId());
				mav.setViewName("forward:/userCustomerLinkRef/searchShow");
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
	 * 客户担当削除
	 * @param userCustomerLinkRefIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("userIdSelected")String userIdSelected, @RequestParam("customerIdSelected")String customerIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//客户担当削除
			userCustomerLinkRefService.delete(userIdSelected, customerIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_USER_CUSTOMER + userIdSelected + "⇒" + customerIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/userCustomerLinkRef/searchShow");
		return mav;
	}
	
	/**
	 * 客户担当返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		UserCustomerLinkRef userCustomerLinkRef = new UserCustomerLinkRef();
		userCustomerLinkRef.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/userCustomerLinkRef/search");
		mav.addObject("userCustomerLinkRef", userCustomerLinkRef);
		return mav;
	}
	
	/**
	 * 客户担当变更
	 * @param userCustomerLinkRef
	 * @param br
	 */
	private void validate(BindingResult br, UserCustomerLinkRef userCustomerLinkRef){
		
		if(IsEmptyUtil.isEmpty(userCustomerLinkRef.getUserId())){
			
			br.rejectValue("userId", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(userCustomerLinkRef.getCustomerId())){
			
			br.rejectValue("customerId", "", Message.EMPTY_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
