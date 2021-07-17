package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.context.Message;
import com.entity.AnalysisProfit;
import com.entity.AnalysisResult;
import com.entity.AnalysisResultExportInfo;
import com.entity.Production;
import com.entity.TradeOrderPop;
import com.logic.AnalysisLogic;
import com.util.IsEmptyUtil;

@Service
public class AnalysisService{
	
	private AnalysisLogic analysisLogic = null;
	public void setAnalysisLogic(AnalysisLogic analysisLogic) {
		this.analysisLogic = analysisLogic;
	}

	public List<TradeOrderPop> productionAnalysis(String dateFrom, String dateTo) throws Exception{
		
		List<TradeOrderPop> tradeOrderPopList = analysisLogic.productionAnalysis(dateFrom, dateTo);
		if(IsEmptyUtil.isEmpty(tradeOrderPopList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return tradeOrderPopList;
    }
	
	public List<AnalysisResult> tradeOrderAnalysis(String dateFrom, String dateTo) throws Exception{
		
		List<AnalysisResult> analysisResultList = analysisLogic.tradeOrderAnalysis(dateFrom, dateTo);
		if(IsEmptyUtil.isEmpty(analysisResultList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return analysisResultList;
    }
	
	public List<AnalysisResult> complaintAnalysis(String dateFrom, String dateTo) throws Exception{
		
		List<AnalysisResult> analysisResultList = analysisLogic.complaintAnalysis(dateFrom, dateTo);
		if(IsEmptyUtil.isEmpty(analysisResultList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return analysisResultList;
    }
	
	public List<AnalysisResultExportInfo> exportInfoAnalysis(String yearSelected, String monthSelected) throws Exception{
		
		List<AnalysisResultExportInfo> analysisResultExportInfoList = analysisLogic.exportInfoAnalysis(yearSelected, monthSelected);
		if(IsEmptyUtil.isEmpty(analysisResultExportInfoList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return analysisResultExportInfoList;
    }
	
	public List<Production> exportInfoAnalysisSub(String receiptId) throws Exception{
		
		List<Production> productionList = analysisLogic.exportInfoAnalysisSub(receiptId);
		if(IsEmptyUtil.isEmpty(productionList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return productionList;
    }
	
	public List<Production> exportProduction() throws Exception{
		
		List<Production> productionList = analysisLogic.exportProduction();
		if(IsEmptyUtil.isEmpty(productionList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return productionList;
    }
	
	public List<AnalysisProfit> profitAnalysis() throws Exception{
		
		List<AnalysisProfit> analysisProfitList = analysisLogic.profitAnalysis();
		if(IsEmptyUtil.isEmpty(analysisProfitList)){
			
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		return analysisProfitList;
    }
	
}
