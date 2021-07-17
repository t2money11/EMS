package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.context.Message;
import com.entity.Analysis;
import com.entity.AnalysisProfit;
import com.entity.AnalysisResult;
import com.entity.AnalysisResultExportInfo;
import com.entity.Production;
import com.entity.TradeOrderPop;
import com.poi.AnalysisExport;
import com.service.AnalysisService;
import com.util.DateUtil;
import com.util.IsEmptyUtil;
import com.util.StringHandler;


@Controller
@RequestMapping("/analysis")
public class AnalysisController{
	
	@Autowired
	private AnalysisService analysisService = null;
	
	@Autowired
	private AnalysisExport analysisExport = null;
	
	/**
	 * 数据分析检索画面初期化
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchShow")
	public ModelAndView searchGet(HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "analysisManagement");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_ANALYSIS);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("analysis", new Analysis());
		mav.setViewName("analysis");
		return mav;
	}
	
	/**
	 * 下载产品订购信息
	 * @param analysis
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadPTA")
	public ModelAndView downloadPTA(Analysis analysis, HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<TradeOrderPop> tradeOrderPopList = null;
		
		try {
			tradeOrderPopList = analysisService.productionAnalysis(analysis.getDateFrom(), analysis.getDateTo());
		} catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}
		
		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("产品订购信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4ProductionAnalysis(tradeOrderPopList, fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}
	
	/**
	 * 下载订单进展信息
	 * @param analysis
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadTPA")
	public ModelAndView downloadTPA(Analysis analysis, HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<AnalysisResult> analysisResultList = null;
		
		try {  
	    	analysisResultList = analysisService.tradeOrderAnalysis(analysis.getDateFrom(), analysis.getDateTo());
	    } catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}

		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("订单进展信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4TradeOrderAnalysis(analysisResultList, fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}
	
	/**
	 * 下载投诉单补货进展信息
	 * @param analysis
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadC")
	public ModelAndView downloadC(Analysis analysis, HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<AnalysisResult> analysisResultList = null;
		
		try {  
	    	analysisResultList = analysisService.complaintAnalysis(analysis.getDateFrom(), analysis.getDateTo());
	    } catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}

		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("投诉单补货进展信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4ComplaintAnalysis(analysisResultList, fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}

	/**
	 * 下载报关金额信息
	 * @param analysis
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadMEI")
	public ModelAndView downloadMEI(Analysis analysis, HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<AnalysisResultExportInfo> analysisResultExportInfoList = null;
		
		try {
			//年份必须选择
			if(IsEmptyUtil.isEmpty(analysis.getYearSelected())){
				
				throw new Exception(Message.YEAR_REQUIRED);
			}
			analysisResultExportInfoList = analysisService.exportInfoAnalysis(analysis.getYearSelected(), analysis.getMonthSelected());
	    } catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}

		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("报关金额信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4ExportInfoAnalysis(analysis.getYearSelected(), 
	    			analysis.getMonthSelected(), 
	    			analysisResultExportInfoList, 
	    			fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}
	
	/**
	 * 下载产品基本信息
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadPI")
	public ModelAndView downloadPI(HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<Production> productionList = null;
		
		try {
			productionList = analysisService.exportProduction();
	    } catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}

		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("产品基本信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4ProductionExport(productionList, fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}
	
	/**
	 * 下载利润信息
	 * @param analysis
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadPA")
	public ModelAndView downloadPA(Analysis analysis, HttpServletResponse response) throws IOException {  
		
		ModelAndView mav = null;
		List<AnalysisProfit> analysisProfitList = null;
		
		try {  
			
			if(IsEmptyUtil.isEmpty(analysis.getRate())){
				
				throw new Exception(Message.RATE_REQUIRED);
			}else if(!StringHandler.isDouble(analysis.getRate(), 2)){
				
				throw new Exception(Message.RATE_FORMAT_ERROR);
			}
			analysisProfitList = analysisService.profitAnalysis();
	    } catch (Exception e) {
	    	
	    	mav = new ModelAndView();
	    	mav.addObject("analysis", new Analysis());
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("analysis");
			return mav;
		}

		OutputStream os = response.getOutputStream();  
	    try {  
	    	
	    	String fileName = URLEncoder.encode("利润信息_" + DateUtil.getNowDateString(DateUtil.DATE_FORMAT4) + ".xlsx", "UTF-8");
	    	String fileFullPath = analysisExport.generateFile4ProfitAnalysis(analysisProfitList, analysis.getRate(), fileName);
	    	response.reset();  
	    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	    	response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));  
	        os.flush();  
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
		} finally {  
			
	        if (os != null) {  
	            os.close();  
	        }  
	    }
	    return mav;
	}
	
}
