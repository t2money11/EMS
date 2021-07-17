package com.interceptor;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.constant.CommonData;
import com.constant.SysCmnDataObj;

public class ThreadInitialRequestListener implements ServletRequestListener {

	private static final Logger log = LoggerFactory.getLogger(ThreadInitialRequestListener.class);
	
	/**
	 * リクエスト開始处理
	 */
	public void requestInitialized(ServletRequestEvent event) {
		
		log.debug("リクエスト開始处理");
		
		// 共通データ読書きUtilクラス新規
		initSysDataObj();
	}

	/**
	 * リクエスト終了处理
	 * 
	 * @param ServletRequestEvent
	 *            イベント
	 */
	public void requestDestroyed(ServletRequestEvent event) {

		// 共通データ読書きUtilクラスクリア
		clearSysDataObj();
	}
	
	/**
	 * 共通データ読書きUtilクラスの初期化处理（新規）
	 */
	protected void initSysDataObj() {
		
		try {
			
			// 共通データ読書きUtilクラス新規
			CommonData.setCmnData(new SysCmnDataObj());
			log.debug("CommonData has been created!");
		} catch (final Exception e) {
			
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 共通データ読書きUtilクラスの初期化处理（クリア）
	 */
	protected void clearSysDataObj() {
		
		try {
			
			// 共通データ読書きUtilクラスクリア
			CommonData.remove();
			log.debug("CommonData has been removed!");
		} catch (final Exception e) {
			
			log.error(e.getMessage());
        }
	}
	
}
