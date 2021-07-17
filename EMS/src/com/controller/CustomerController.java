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
import com.entity.Customer;
import com.service.CustomerService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/customer")
public class CustomerController{
	
	@Autowired
	private CustomerService customerService = null;
	
	/**
	 * 客户检索POPUP画面初期化
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView searchPopGet(@RequestParam(value="noAuth", required=false)String noAuth) {
		
		ModelAndView mav = new ModelAndView();
		Customer customer = new Customer();
		if("1".equals(noAuth)){
			customer.setNoAuth(noAuth);
		}
		mav.addObject("customer", customer);
		mav.setViewName("customerPopSearch");
		return mav;
	}
	
	/**
	 * 客户POPUP检索
	 * @param customer
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPop")
	public ModelAndView searchPopPost(Customer customer) {
		
		ModelAndView mav = new ModelAndView();
		try {
			customer = customerService.customerSearch(customer);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("customer", customer);
		mav.setViewName("customerPopSearch");
		
		return mav;
	}
	
	/**
	 * 客户检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_customer");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("customer", new Customer());
		mav.setViewName("customerSearch");
		return mav;
	}
	
	/**
	 * 客户检索
	 * @param customer
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Customer customer, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERSEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(customer.getIsBack())
				&& null != session.getAttribute("searchCondition_customer")){
			
			customer = (Customer)session.getAttribute("searchCondition_customer");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_customer", customer);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			customer = customerService.customerSearch(customer);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("customer", customer);
		mav.setViewName("customerSearch");
		
		return mav;
	}
	
	/**
	 * 客户照会画面初期化
	 * @param customerIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERREF);
		
		Customer customer = customerService.findByKey(customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("customer", customer);
		mav.setViewName("customerRef");
		return mav;
	}
	
	/**
	 * 客户复制画面初期化
	 * @param customerIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/copyShow")
	@Token(save = true, remove= false)
	public ModelAndView copyGet(@RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERADD);
		
		Customer customer = customerService.findByKey(customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("customer", customer);
		mav.setViewName("customerAdd");
		return mav;
	}
	
	/**
	 * 客户追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERADD);
		
		ModelAndView mav = new ModelAndView();
		Customer customer = new Customer();
		mav.addObject("customer", customer);
		mav.setViewName("customerAdd");
		return mav;
	}
	
	/**
	 * 客户追加
	 * @param customer
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("customer")Customer customer, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customerAdd");
		
		//输入验证
		validate(br, customer);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//客户登録
			try {
				String newCustomerId = customerService.add(customer);
				//客户登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_CUSTOMER + newCustomerId);
				mav.setViewName("forward:/customer/searchShow");
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
	 * 客户变更画面初期化
	 * @param customerIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView updateGet(@RequestParam("customerIdSelected")String customerIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERUPDATE);
		
		Customer customer = customerService.findByKey(customerIdSelected);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("customer", customer);
		mav.setViewName("customerUpdate");
		return mav;
	}
	
	/**
	 * 客户变更
	 * @param customer
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView update(@Valid @ModelAttribute("customer")Customer customer, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "customerManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CUSTOMERUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customerUpdate");
		
		//输入验证
		validate(br, customer);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			try {
				
				//客户更新
				customerService.update(customer);
				
				//客户更新完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_UPDATE_CUSTOMER + customer.getCustomerId());
				mav.setViewName("forward:/customer/searchShow");
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
	 * 客户削除
	 * @param customerIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("customerIdSelected")String customerIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//客户削除
			customerService.delete(customerIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_CUSTOMER + customerIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/customer/searchShow");
		return mav;
	}
	
	/**
	 * 客户返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Customer customer = new Customer();
		customer.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/customer/search");
		mav.addObject("customer", customer);
		return mav;
	}
	
	/**
	 * 输入验证
	 * @param customer
	 * @param br
	 */
	private void validate(BindingResult br, Customer customer){
		
		if(IsEmptyUtil.isEmpty(customer.getCustomerId())){
			
			br.rejectValue("customerId", "", Message.EMPTY_ERROR);  
		}else if(!StringHandler.isVCId(customer.getCustomerId())){
			
			br.rejectValue("customerId", "", String.format(Message.ID_ERROR, "3"));  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getCustomerName())){
			
			br.rejectValue("customerName", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getCustomerFullName())){
			
			br.rejectValue("customerFullName", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getLocation())){
			
			br.rejectValue("location", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getCountry())){
			
			br.rejectValue("country", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getFreightTerms())){
			
			br.rejectValue("freightTerms", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(customer.getPortOfDestination())){
			
			br.rejectValue("portOfDestination", "", Message.EMPTY_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
