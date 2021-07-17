package com.interceptor;

import com.entity.User;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.common.Token;
import com.constant.CommonData;

public class DuplicateSubmitTokenInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		User sessionUser = CommonData.getCmnData().getLoginInfo();

		if (sessionUser != null) {

			if (handler instanceof HandlerMethod) {
	            HandlerMethod handlerMethod = (HandlerMethod) handler;
	            Method method = handlerMethod.getMethod();
	            Token annotation = method.getAnnotation(Token.class);
	            if (annotation != null) {
	                boolean needSaveSession = annotation.save();
	                if (needSaveSession) {
	                    request.getSession(false).setAttribute("token", new BigInteger(165, new Random()).toString(36).toUpperCase());
	                }
	                boolean needRemoveSession = annotation.remove();
	                if (needRemoveSession) {
	                    if (isRepeatSubmit(request)) {
	                    	
	                    	response.sendRedirect(request.getContextPath() + "/jsp/error.jsp");  
	                        return false;
	                    }
	                    request.getSession(false).removeAttribute("token");
	                    
	                }
	            }
	           
	            return true;
	        } else {
	            return super.preHandle(request, response, handler);
	        }
		}
		return true;
	}

	/**
	 * isRepeatSubmit 重複サブミットチェック
	 * @param request
	 * @return boolean
	 * @exception
	 */
	private boolean isRepeatSubmit(HttpServletRequest request) {

		String serverToken = (String) request.getSession(false).getAttribute("token");
		if (serverToken == null) {

			return true;
		}

		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {

			return true;
		}

		if (!serverToken.equals(clinetToken)) {

			return true;
		}
		return false;
	}

}