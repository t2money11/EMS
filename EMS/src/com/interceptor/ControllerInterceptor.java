package com.interceptor;

import com.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.constant.CommonData;

public class ControllerInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//チャー设定設定
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		boolean result = false;
		
		//登陆画面无需验证
		if(request.getServletPath().startsWith("/user/login")) {  
			result = true;  
	    }else{
	    	//登录認証
	    	if(request.getSession().getAttribute("loginUser") != null) {  
		    	
		    	CommonData.getCmnData().setLoginInfo((User) request.getSession().getAttribute("loginUser"));
		    	result = true;  
		    }else{
		    	
		    	response.sendRedirect(request.getContextPath() + "/user/loginShow");  
		    	result = false;  
		    }
		    
		    //检查是否修改url提交
	    	if(request.getHeader("referer") == null && !request.getServletPath().endsWith("PopShow")){
	    		response.sendRedirect(request.getContextPath() + "/jsp/errorUrl.jsp");  
		    	result = false;  
	    	};
	    }
	    
	    return result;
	    
	}
	
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}