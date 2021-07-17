package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.Receipt;
import com.entity.ReceiptDtl;
import com.entity.ReceiptDtl4C;

@Repository
public interface ReceiptDAO{
	
	Receipt findByKey(Map<String, Object> map);
	
	List<ReceiptDtl> findDtlByKey(Map<String, Object> map);
	
	List<ReceiptDtl4C> findDtl4CByKey(Map<String, Object> map);
	
	List<Receipt> findByProperty(Map<String, Object> map);
	
	int countFindByProperty(Map<String, Object> map);
	
	int update(Receipt receipt);
	
	int delete(Receipt receipt);
	
	int deleteDtl(Receipt receipt);
	
	int deleteDtl4C(Receipt receipt);
	
	int insert(Receipt receipt);
	
	int insertDtl(ReceiptDtl receiptDtl);
	
	int insertDtl4C(ReceiptDtl4C receiptDtl4C);
	
	String getReceiptId4New(Map<String, Object> map);
	
	String getReceiptNo4New(Map<String, Object> map);
	
	int validateDuplicatedReceiptNo(Map<String, Object> map);
	
	int validateRelateReceipt(Map<String, Object> map);
}