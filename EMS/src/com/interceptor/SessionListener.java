package com.interceptor;

import com.entity.User;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.context.ConstantParam;

public class SessionListener implements HttpSessionListener  {

	private static final Logger log = LoggerFactory.getLogger(SessionListener.class);
	
    /**
     * HttpSession生成時のイベント。
     *
     * @param event HttpSessionEvent
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
	public void sessionCreated(HttpSessionEvent arg0) {
		
		try {
			log.debug("HttpSession[" + arg0.getSession().getId() + "] has been created!");
		} catch (final Exception e) {
			
			log.error(e.getMessage());
        }
	}

	/**
     * HttpSession破棄時のイベント。
     *
     * @param event HttpSessionEvent
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
	public void sessionDestroyed(HttpSessionEvent arg0) {

		try {
        	// セッションタイムアウトの時、菜单と権限データ清除する
            User loginInfo = (User) arg0.getSession().getAttribute(ConstantParam.SESSION_KEY_LOGIN_INFO);
            
            if(loginInfo != null) {
                
                // セッションタイムアウトログ出力する。
                log.debug("HttpSession[" + loginInfo.getUserId() + "] is time out!");
            }
        } catch (final Exception e) {
        	
        	log.error(e.getMessage());
        }     
		
	}
}
