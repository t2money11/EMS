package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Code extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5313126980291954889L;
	
	private String categoryId;
	private String codeId;
	private String codeName;
	
	private List<Code> resultCodeList = null;
	
	public Code() {
		
		resultCodeList = new ArrayList<Code>();
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public List<Code> getResultCodeList() {
		return resultCodeList;
	}
	public void setResultCodeList(List<Code> resultCodeList) {
		this.resultCodeList = resultCodeList;
	}
	
}
