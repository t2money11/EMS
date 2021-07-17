package com.entity;

public class SearchCondition implements java.io.Serializable {

	private static final long serialVersionUID = 5723517748133048338L;
	
	private String colName;
	private String colValue;
	
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColValue() {
		return colValue;
	}
	public void setColValue(String colValue) {
		this.colValue = colValue;
	}
}
