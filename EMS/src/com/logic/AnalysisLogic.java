package com.logic;

import com.constant.CommonData;
import com.dao.AnalysisDAO;
import com.entity.AnalysisProfit;
import com.entity.AnalysisResult;
import com.entity.AnalysisResultExportInfo;
import com.entity.Production;
import com.entity.TradeOrderPop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class AnalysisLogic{
	
	private static final Logger log = LoggerFactory.getLogger(AnalysisLogic.class);
	
	@Resource
    private AnalysisDAO analysisDAO; 
    
	public List<TradeOrderPop> productionAnalysis(String dateFrom, String dateTo) {
		log.debug("productionAnalysis with key:" + dateFrom + ", " + dateTo);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dateFrom", dateFrom);
			map.put("dateTo", dateTo);
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			return analysisDAO.productionAnalysis(map);
		} catch (RuntimeException re) {
			log.error("productionAnalysis with key", re);
			throw re;
		}
	}
	
	public List<AnalysisResult> tradeOrderAnalysis(String dateFrom, String dateTo) {
		log.debug("tradeOrderAnalysis with key:" + dateFrom + ", " + dateTo);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dateFrom", dateFrom);
			map.put("dateTo", dateTo);
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			return analysisDAO.tradeOrderAnalysis(map);
		} catch (RuntimeException re) {
			log.error("tradeOrderAnalysis with key", re);
			throw re;
		}
	}
	
	public List<AnalysisResult> complaintAnalysis(String dateFrom, String dateTo) {
		log.debug("complaintAnalysis with key:" + dateFrom + ", " + dateTo);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dateFrom", dateFrom);
			map.put("dateTo", dateTo);
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			return analysisDAO.complaintAnalysis(map);
		} catch (RuntimeException re) {
			log.error("complaintAnalysis with key", re);
			throw re;
		}
	}
	
	public List<AnalysisResultExportInfo> exportInfoAnalysis(String yearSelected, String monthSelected) {
		log.debug("exportInfoAnalysis with key:" + yearSelected + ", " + monthSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yearSelected", yearSelected);
			map.put("monthSelected", monthSelected);
			return analysisDAO.exportInfoAnalysis(map);
		} catch (RuntimeException re) {
			log.error("exportInfoAnalysis with key", re);
			throw re;
		}
	}
	
	public List<Production> exportInfoAnalysisSub(String receiptId) {
		log.debug("exportInfoAnalysisSub with key:" + receiptId);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("receiptId", receiptId);
			return analysisDAO.exportInfoAnalysisSub(map);
		} catch (RuntimeException re) {
			log.error("exportInfoAnalysisSub with key", re);
			throw re;
		}
	}
	
	public List<Production> exportProduction() {
		log.debug("exportProduction");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			return analysisDAO.exportProduction(map);
		} catch (RuntimeException re) {
			log.error("exportProduction");
			throw re;
		}
	}
	
	public List<AnalysisProfit> profitAnalysis() {
		log.debug("profitAnalysis");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
					&& !"Way".equals(CommonData.getCmnData().getLoginInfo().getUserId())){
				map.put("isCustomerRefFlg", "1");
				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
			}
			return analysisDAO.profitAnalysis(map);
		} catch (RuntimeException re) {
			log.error("profitAnalysis", re);
			throw re;
		}
	}
}