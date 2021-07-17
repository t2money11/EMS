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
import com.entity.Code;
import com.service.CodeService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/code")
public class CodeController{
	
	@Autowired
	private CodeService codeService = null;
	
	/**
	 * Code检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "codeManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CODESEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_code");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("code", new Code());
		mav.setViewName("codeSearch");
		return mav;
	}
	
	/**
	 * Code检索
	 * @param code
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(Code code, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "codeManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CODESEARCH);
		
		//返回检索的话，从session获取检索条件
		if("1".equals(code.getIsBack())
				&& null != session.getAttribute("searchCondition_code")){
			
			code = (Code)session.getAttribute("searchCondition_code");
		}else{
			
			//保存检索条件
			session.setAttribute("searchCondition_code", code);
		}
		
		ModelAndView mav = new ModelAndView();
		try {
			code = codeService.codeSearch(code);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("code", code);
		mav.setViewName("codeSearch");
		
		return mav;
	}
	
	
	/**
	 * Code追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "codeManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CODEADD);
		
		ModelAndView mav = new ModelAndView();
		Code code = new Code();
		mav.addObject("code", code);
		mav.setViewName("codeAdd");
		return mav;
	}
	
	/**
	 * Code追加
	 * @param code
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("code")Code code, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "codeManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_CODEADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("codeAdd");
		
		//输入验证
		validate(br, code);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			
			//Code登録
			try {
				code.setCategoryId("P_TYPE");
				codeService.add(code);
				//Code登録完了、返回检索画面
				mav.addObject("errorMessage", Message.INSERT_SUCCESS);
				mav.setViewName("forward:/code/searchShow");
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
	 * Code削除
	 * @param codeIdSelected
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("codeIdSelected")String codeIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//Code削除
			codeService.delete(codeIdSelected);
			mav.addObject("errorMessage", Message.DELETE_SUCCESS);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/code/searchShow");
		return mav;
	}
	
	/**
	 * Code返回检索画面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView searchReturn(){
		
		Code code = new Code();
		code.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/code/search");
		mav.addObject("code", code);
		return mav;
	}
	
	/**
	 * 输入验证
	 * @param code
	 * @param br
	 */
	private void validate(BindingResult br, Code code){
		
		if(IsEmptyUtil.isEmpty(code.getCodeId())){
			
			br.rejectValue("codeId", "", Message.EMPTY_ERROR);  
		}else if(!StringHandler.isVCId(code.getCodeId())){
			
			br.rejectValue("codeId", "", String.format(Message.ID_ERROR, "3"));  
		}
		
		if(IsEmptyUtil.isEmpty(code.getCodeName())){
			
			br.rejectValue("codeName", "", Message.EMPTY_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
