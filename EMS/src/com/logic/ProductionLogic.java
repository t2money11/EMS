package com.logic;

import com.entity.Production;
import com.dao.ProductionDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.util.PaginationSupport;

@Repository
public class ProductionLogic{
	
	private static final Logger log = LoggerFactory.getLogger(ProductionLogic.class);
    
	@Resource
    private ProductionDAO productionDAO;
	
	public Production findByKey(String productionIdSelected, String versionNoSelected) {
		log.debug("finding Production instance with property: productionId, versionNo: " + productionIdSelected + versionNoSelected);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", productionIdSelected);
			map.put("versionNo", versionNoSelected);
			return productionDAO.findByKey(map);
		} catch (RuntimeException re) {
			log.error("finding Production instance with key", re);
			throw re;
		}
	}
	
	public List<Production> findByProperty(Production production) {
		log.debug("finding Production instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", production.getProductionId4S() == null ? null : production.getProductionId4S().toUpperCase());
			map.put("productionIdVendor", production.getProductionIdVendor4S() == null ? null : production.getProductionIdVendor4S().toUpperCase());
			map.put("descriptionC", production.getDescriptionC4S() == null ? null : production.getDescriptionC4S().toUpperCase());
			map.put("descriptionE", production.getDescriptionE4S() == null ? null : production.getDescriptionE4S().toUpperCase());
			map.put("productionCname4export", production.getProductionCname4export4S() == null ? null : production.getProductionCname4export4S().toUpperCase());
			map.put("productionEname4export", production.getProductionEname4export4S() == null ? null : production.getProductionEname4export4S().toUpperCase());
			map.put("vendorId", production.getVendorId4S() == null ? null : production.getVendorId4S().toUpperCase());
			map.put("customerId", production.getCustomerId4S() == null ? null : production.getCustomerId4S().toUpperCase());
			map.put("status", production.getStatus4S());
			map.put("searchOption", production.getSearchOption());
//			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
//				map.put("isCustomerRefFlg", "1");
//				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
//			}
			map.put("startIndex", production.getPageInfo().getStartIndex());
			map.put("pageSize", PaginationSupport.PAGESIZE);
			List<Production> productionList = productionDAO.findByProperty(map);
			return productionList;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public int countFindByProperty(Production production) {
		log.debug("finding Production instance with property");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productionId", production.getProductionId4S() == null ? null : production.getProductionId4S().toUpperCase());
			map.put("productionIdVendor", production.getProductionIdVendor4S() == null ? null : production.getProductionIdVendor4S().toUpperCase());
			map.put("descriptionC", production.getDescriptionC4S() == null ? null : production.getDescriptionC4S().toUpperCase());
			map.put("descriptionE", production.getDescriptionE4S() == null ? null : production.getDescriptionE4S().toUpperCase());
			map.put("productionCname4export", production.getProductionCname4export4S() == null ? null : production.getProductionCname4export4S().toUpperCase());
			map.put("productionEname4export", production.getProductionEname4export4S() == null ? null : production.getProductionEname4export4S().toUpperCase());
			map.put("vendorId", production.getVendorId4S() == null ? null : production.getVendorId4S().toUpperCase());
			map.put("customerId", production.getCustomerId4S() == null ? null : production.getCustomerId4S().toUpperCase());
			map.put("status", production.getStatus4S());
			map.put("searchOption", production.getSearchOption());
//			if("5".equals(CommonData.getCmnData().getLoginInfo().getCategory())){
//				map.put("isCustomerRefFlg", "1");
//				map.put("userId", CommonData.getCmnData().getLoginInfo().getUserId());
//			}
			int count = productionDAO.countFindByProperty(map);
			return count;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public int delete(Production production) {
		log.debug("delete with key: " + production.getProductionId());
		try {
			int count = productionDAO.delete(production);
			return count;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int insert(Production production) {
		log.debug("insert production: " + production.getProductionId());
		try {
			int count = productionDAO.insert(production);
			return count;
		} catch (RuntimeException re) {
			log.error("insert production failed", re);
			throw re;
		}
	}
	
	public int update(Production production) {
		log.debug("update production: " + production.getProductionId());
		try {
			int count = productionDAO.update(production);
			return count;
		} catch (RuntimeException re) {
			log.error("update production failed", re);
			throw re;
		}
	}
}