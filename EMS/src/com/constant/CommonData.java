package com.constant;

/**
 * 共通データ読み書きユーティリティクラス
 * 
 * @author wangchenyi
 */
public class CommonData {

    /**
     * 共通データ格納用のスレッドローカル
     */
    private static ThreadLocal<SysCmnDataObj> memory = new ThreadLocal<SysCmnDataObj>();
    
    /**
     * 共通データの取得
     * 
     * @return SysCmnDataObj 共通データ
     */
    public static SysCmnDataObj getCmnData() {
        SysCmnDataObj ret = memory.get();
        return ret;
    }
    
    /**
     * 共通データの設定
     * 
     * @param obj 共通データ
     */
    public static void setCmnData(SysCmnDataObj obj) {
        if (null != obj) {
            memory.set(obj);
        }
    }
    
    /**
     * ThreadLocalの初期化
     * 
     * @param obj 共通データ
     */
    public static void remove() {
    	memory.remove();
    }
}