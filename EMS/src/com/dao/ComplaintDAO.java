package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.Complaint;
import com.entity.ComplaintDtl;
import com.entity.TradeOrderPop;

@Repository
public interface ComplaintDAO{
	
	String getComplaintId4New(Map<String, Object> map);
	
	Complaint findByKey(Map<String, Object> map);
	
	List<ComplaintDtl> findDtlByKey(Map<String, Object> map);
	
	List<ComplaintDtl> findDtlByTradeOrderId(Map<String, Object> map);
	
	List<Complaint> findByProperty(Map<String, Object> map);
	
	int countFindByProperty(Map<String, Object> map);
	
	List<TradeOrderPop> findPopByProperty(Map<String, Object> map);
	
	int countFindPopByProperty(Map<String, Object> map);
	
	int update(Complaint complaint);
	
	int delete(Complaint complaint);
	
	int deleteDtl(Complaint complaint);
	
	int insert(Complaint complaint);
	
	int insertDtl(ComplaintDtl complaintDtl);
}