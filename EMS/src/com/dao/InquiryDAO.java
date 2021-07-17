package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.Inquiry;
import com.entity.InquiryDtl;

@Repository
public interface InquiryDAO{
	
	String getInquiryId4New(Map<String, Object> map);
	
	Inquiry findByKey(Map<String, Object> map);
	
	List<InquiryDtl> findDtlByKey(Map<String, Object> map);
	
	List<InquiryDtl> findLastDtlByKey(Map<String, Object> map);
	
	List<InquiryDtl> findByProperty(Map<String, Object> map);
	
	int countFindByProperty(Map<String, Object> map);
	
	int update(Inquiry inquiry);
	
	int delete(Inquiry inquiry);
	
	int deleteDtl(Inquiry inquiry);
	
	int insert(Inquiry inquiry);
	
	int insertDtl(InquiryDtl inquiryDtl);
	
	InquiryDtl findDtlByTradeOrder(Map<String, Object> map);
	
	int updateDtlOnTradeOrderId(InquiryDtl inquiryDtl);
	
	int clearDtlOnTradeOrderId(InquiryDtl inquiryDtl);
}