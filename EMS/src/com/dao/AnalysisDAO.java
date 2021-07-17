package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.AnalysisProfit;
import com.entity.AnalysisResult;
import com.entity.AnalysisResultExportInfo;
import com.entity.Production;
import com.entity.TradeOrderPop;

@Repository
public interface AnalysisDAO{
	
	List<TradeOrderPop> productionAnalysis(Map<String, Object> map);
	
	List<AnalysisResult> tradeOrderAnalysis(Map<String, Object> map);
	
	List<AnalysisResult> complaintAnalysis(Map<String, Object> map);
	
	List<AnalysisResultExportInfo> exportInfoAnalysis(Map<String, Object> map);
	
	List<Production> exportInfoAnalysisSub(Map<String, Object> map);
	
	List<Production> exportProduction(Map<String, Object> map);
	
	List<AnalysisProfit> profitAnalysis(Map<String, Object> map);
	
}