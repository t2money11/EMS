package com.logic;

import com.dao.VendorDAO;
import com.entity.Vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.PaginationSupport;

@Repository
public class VendorLogic{
	
	private static final Logger log = LoggerFactory.getLogger(VendorLogic.class);
	
	@Resource
    private VendorDAO vendorDAO; 
    
	public Vendor findByKey(String vendorIdSelected) {
		log.debug("finding Vendor instance with key:" + vendorIdSelected);
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", vendorIdSelected);
			return vendorDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Vendor instance with key", re);
			throw re;
		}
	}
	
	public List<Vendor> findByProperty(Vendor vendor) {
		log.debug("finding Vendor instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", vendor.getVendorId4S());
			map.put("vendorName", vendor.getVendorName4S());
			map.put("location", vendor.getLocation4S());
//			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
//					&& !"1".equals(vendor.getNoAuth())){
//				map.put("isCustomerRefFlg", "1");
//				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
//			}
			map.put("startIndex", vendor.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<Vendor> vendorList = vendorDAO.findByProperty(map);
			return vendorList;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Vendor vendor) {
		log.debug("finding Vendor instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorId", vendor.getVendorId4S());
			map.put("vendorName", vendor.getVendorName4S());
			map.put("location", vendor.getLocation4S());
//			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())
//					&& !"1".equals(vendor.getNoAuth())){
//				map.put("isCustomerRefFlg", "1");
//				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
//			}
			int count = vendorDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property failed", re);
			throw re;
		}
	}
	
	public int delete(Vendor vendor) {
		log.debug("delete with key: " + vendor.getVendorId());
		try {
			int count = vendorDAO.delete(vendor);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Vendor vendor) {
		log.debug("insert vendor: " + vendor.getVendorId());
		try {
			int count = vendorDAO.insert(vendor);
			return count;
		} catch (RuntimeException re) {
			log.error("insert vendor failed", re);
			throw re;
		}
	}
	
	public int update(Vendor vendor) {
		log.debug("update vendor: " + vendor.getVendorId());
		try {
			int count = vendorDAO.update(vendor);
			return count;
		} catch (RuntimeException re) {
			log.error("update vendor failed", re);
			throw re;
		}
	}
}