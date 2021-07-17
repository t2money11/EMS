package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.context.Message;
import com.entity.Complaint;
import com.entity.Top;
import com.service.ComplaintService;
import com.util.IsEmptyUtil;

@Controller
@RequestMapping("/topMenu")
public class TopController {

	@Autowired
	private ComplaintService complaintService = null;
	
	/**
	 * トップ画面初期化
	 * @param model
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/topMenuShow")
	public ModelAndView loginGet(Model model, HttpSession session) {
		
		//CURRENT菜单设定
		session.setAttribute("currentMenu", "topMenu");
		//CURRENT画面标题设定
		session.setAttribute("pageTitle", Message.PAGETITLE_TOPMENU);
		
		ModelAndView mav = new ModelAndView();
		
		Complaint complaint = new Complaint();
		try {
			
			complaint.setIsTopFlg("1");
			complaint = complaintService.complaintSearch(complaint);
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
		}
		
		//编辑主页系统通知
		Top top = new Top();
		List<String> infoList = new ArrayList<String>();
		for (Complaint temp : complaint.getResultComplaintList()) {
			
			if(IsEmptyUtil.isEmpty(temp.getReplyDate())){
				
				infoList.add(String.format(Message.ALERT_UNREPLY, temp.getComplaintId()));
			}else{
				
				infoList.add(String.format(Message.ALERT_UNDEAL, temp.getComplaintId(), temp.getDealDeadline()));
			}
		}
		top.setInfoList(infoList);
		mav.addObject("top", top);
		mav.setViewName("topMenu");
		
		return mav;
	}
}
