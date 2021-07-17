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
import com.constant.CommonData;
import com.context.Message;
import com.entity.User;
import com.service.UserService;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/user")
public class UserController{
	
	@Autowired
	private UserService userService = null;
	
	/**
	 * 用户密码变更POPUP画面初期化
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/passwordUpdatePopShow")
	public ModelAndView passwordUpdatePopGet() {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.setViewName("passwordUpdate");
		return mav;
	}
	
	/**
	 * 用户密码变更
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/passwordUpdate")
	public ModelAndView passwordUpdatePopPost(@Valid @ModelAttribute("user")User user, BindingResult br) {
		
		ModelAndView mav = new ModelAndView();
		
		if(IsEmptyUtil.isEmpty(user.getPasswordOld())){
			
			br.rejectValue("passwordOld", "", Message.EMPTY_ERROR);
		}
		
		if(IsEmptyUtil.isEmpty(user.getPasswordNew1())){
			
			br.rejectValue("passwordNew1", "", Message.EMPTY_ERROR);
		}
		
		if(IsEmptyUtil.isEmpty(user.getPasswordNew2())){
			
			br.rejectValue("passwordNew2", "", Message.EMPTY_ERROR);
		}
		
		if(!IsEmptyUtil.isEmpty(user.getPasswordNew1())
			&& !IsEmptyUtil.isEmpty(user.getPasswordNew2())
			&& !user.getPasswordNew1().equals(user.getPasswordNew2())){
			
			br.rejectValue("passwordNew2", "", Message.PASSWORD_UNMATCH);
		}
		
		if(!IsEmptyUtil.isEmpty(user.getPasswordOld())
			&& !IsEmptyUtil.isEmpty(user.getPasswordNew1())
			&& user.getPasswordNew1().equals(user.getPasswordOld())){
			
			br.rejectValue("passwordNew1", "", Message.PASSWORD_SAME);
		}
		
		if(!br.hasErrors()){
			
			try {
				//密码变更
				userService.passwordUpdate(CommonData.getCmnData().getLoginInfo().getUserId(), user.getPasswordOld(), user.getPasswordNew1());
				mav.addObject("errorMessage", Message.SUCCESS_UPDATE_PASSWORD);
			} catch (Exception e) {
				mav.addObject("errorMessage", e.getMessage());
			}
		}
		mav.setViewName("passwordUpdate");
		return mav;
	}
	
	/**
	 * 用户检索POPUP画面初期化
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPopShow")
	public ModelAndView searchPopGet() {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.setViewName("userPopSearch");
		return mav;
	}
	
	/**
	 * 用户POPUP检索
	 * @param user
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPop")
	public ModelAndView searchPopPost(User user) {
		
		ModelAndView mav = new ModelAndView();
		try {
			user = userService.userSearch(user);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("user", user);
		mav.setViewName("userPopSearch");
		
		return mav;
	}
	
	/**
	 * Password Reset
	 * @param user
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/passwordReset")
	public ModelAndView passwordReset(@RequestParam("userIdSelected")String userIdSelected, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		try {
			//用户Password Reset
			userService.passwordReset(userIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_PASSWORDRESET_USER + userIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/user/searchShow");
		return mav;
	}
	
	/**
	 * 登录画面初期化
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/loginShow")
	public ModelAndView loginGet() {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.setViewName("login");
		return mav;
	}
	
	/**
	 * 登录处理
	 * @param user
	 * @param br
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/login")
	public String loginPost(@Valid @ModelAttribute("user")User user, BindingResult br, HttpSession session) {
		
		//認証失敗の時の画面遷移先
		String returnView = "login";
		
		if(IsEmptyUtil.isEmpty(user.getUserId())){
			//Id未入力の場合、エラーメッセージ画面に出力
			br.rejectValue("errorMessage", "", Message.LOGINID_IUPUT_ERROR);
		}else if(user.getPassword() == null || user.getPassword().equals("")){
			//パスワード未入力の場合、エラーメッセージ画面に出力
			br.rejectValue("errorMessage", "", Message.PASSWORD_IUPUT_ERROR);
		}else{
			
			User loginUser = userService.login(user);
			if(loginUser != null){
				//登录情報セッションに保存
				session.setAttribute("loginUser", loginUser);
				//登录情報共通データに保存
				CommonData.getCmnData().setLoginInfo(loginUser);
				//登录認証成功、トップ画面へ遷移
				returnView = "forward:/topMenu/topMenuShow";
			}else{
				br.rejectValue("errorMessage", "", Message.WRONG_USERID_PASSWORD);
			}
		}
		return returnView;
	}
	
	/**
	 * 登出处理
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/logout")
	public String loginOutPost(HttpSession session) {
		
		//登出画面遷移先
		String returnView = "login";
		session.invalidate();
		return returnView;
	}
	
	/**
	 * 用户检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERSEARCH);
		//清除检索条件
		session.removeAttribute("searchCondition_user");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.setViewName("userSearch");
		return mav;
	}
	
	/**
	 * 用户检索
	 * @param user
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchPost(User user, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERSEARCH);
		
		ModelAndView mav = new ModelAndView();
		
		//返回检索的话，从session获取检索条件
		if("1".equals(user.getIsBack())
				&& null != session.getAttribute("searchCondition_user")){
			
			user = (User)session.getAttribute("searchCondition_user");
		}else{
			//保存检索条件
			session.setAttribute("searchCondition_user", user);
		}
		
		try {
			//用户检索
			user = userService.userSearch(user);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.addObject("user", user);
		mav.setViewName("userSearch");
		
		return mav;
	}
	
	/**
	 * 用户照会画面初期化
	 * @param userIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/refShow")
	public ModelAndView refGet(@RequestParam("userIdSelected")String userIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERREF);
		
		User user = userService.findByUserId(userIdSelected);
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		mav.setViewName("userRef");
		return mav;
	}
	
	/**
	 * 用户追加画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addShow")
	@Token(save = true, remove= false)
	public ModelAndView addGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERADD);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.setViewName("userAdd");
		return mav;
	}
	
	/**
	 * 用户追加
	 * @param user
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add")
	@Token(save = false, remove= true)
	public ModelAndView addPost(@Valid @ModelAttribute("user")User user, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERADD);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userAdd");
		
		//输入验证
		validate(br, user);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			//用户登録
			try {
				String newUserId = userService.add(user);
				//用户登録完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_CREATE_USER + newUserId);
				mav.setViewName("forward:/user/searchShow");
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
	 * 用户变更画面初期化
	 * @param userIdSelected
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateShow")
	@Token(save = true, remove = false)
	public ModelAndView userUpdateGet(@RequestParam("userIdSelected")String userIdSelected, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERUPDATE);
		
		User user = userService.findByUserId(userIdSelected);
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		mav.setViewName("userUpdate");
		return mav;
	}
	
	/**
	 * 用户变更
	 * @param user
	 * @param br
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	@Token(save = false, remove = true)
	public ModelAndView userUpdate(@Valid @ModelAttribute("user")User user, BindingResult br, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "userManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_USERUPDATE);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userUpdate");
		
		//输入验证
		validate(br, user);
		
		//输入验证結果OKの場合
		if(!br.hasErrors()){
			try {
				//用户更新
				userService.update(user);
				//用户更新完了、返回检索画面
				mav.addObject("errorMessage", Message.SUCCESS_UPDATE_USER + user.getUserId());
				mav.setViewName("forward:/user/searchShow");
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
	 * 用户削除
	 * @param user
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView userDelete(@RequestParam("userIdSelected")String userIdSelected){
		
		ModelAndView mav = new ModelAndView();
		try {
			//用户削除
			userService.delete(userIdSelected);
			mav.addObject("errorMessage", Message.SUCCESS_DELETE_USER + userIdSelected);
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
		}
		mav.setViewName("forward:/user/searchShow");
		return mav;
	}
	
	/**
	 * 用户返回检索画面
	 * @param user
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/searchReturn")
	public ModelAndView userReturn(){
		
		User user = new User();
		user.setIsBack("1");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/user/search");
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 输入验证
	 * @param user
	 * @param br
	 */
	private void validate(BindingResult br, User user){
		
		if(IsEmptyUtil.isEmpty(user.getUserId())){
			
			br.rejectValue("userId", "", Message.EMPTY_ERROR);  
		}
		
		if(IsEmptyUtil.isEmpty(user.getCategory())){
			
			br.rejectValue("category", "", Message.EMPTY_ERROR);  
		}else{
			
			if((CommonData.getCmnData().getLoginInfo().getCategory() == null
				|| CommonData.getCmnData().getLoginInfo().getCategory().compareTo(user.getCategory()) >= 0)
				&& !CommonData.getCmnData().getLoginInfo().getUserId().equals(user.getUserId())){
				
				br.rejectValue("category", "", Message.USER_CATEGORY_AUTH_ERROR);  
			}
		}
		
		if((user.getUserNameC() == null || user.getUserNameC().isEmpty())
				&& (user.getUserNameE() == null || user.getUserNameE().isEmpty())){
			
			br.rejectValue("userNameC", "", Message.USERNAME_EMPTY_ERROR);  
			br.rejectValue("userNameE", "", Message.USERNAME_EMPTY_ERROR);  
		}
		
		if(!StringHandler.isMail(user.getMail())){
			
			br.rejectValue("mail", "", Message.MAIL_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDouble(user.getSalary(), 2)){
			
			br.rejectValue("salary", "", Message.DOUBLE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDate(user.getBirthday())){
			
			br.rejectValue("birthday", "", Message.DATE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDate(user.getOnboardDate())){
			
			br.rejectValue("onboardDate", "", Message.DATE_IUPUT_ERROR);  
		}
		
		if(!StringHandler.isDate(user.getSeperateDate())){
			
			br.rejectValue("seperateDate", "", Message.DATE_IUPUT_ERROR);  
		}
	}
	
	private void tokenReset(ModelAndView mav, HttpSession session){
		
		//token再生成
		String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
		session.setAttribute("token", token);
		mav.addObject("token", token);
	}

}
