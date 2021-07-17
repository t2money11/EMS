package com.constant;

import com.entity.User;

/**
 * 共通データクラス
 * 
 * @author wangchenyi
 */
public class SysCmnDataObj {

	/**
     * 登录情報
     */
    private User loginInfo;

	public User getLoginInfo() {
		return loginInfo;
	}

	/**
     * 登录情報の取得
     * 
     * @return LoginInfo 登录情報
     */
	public void setLoginInfo(User loginInfo) {
		this.loginInfo = loginInfo;
	}
    
}
