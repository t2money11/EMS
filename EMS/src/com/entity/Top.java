package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Top extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 5245794424593377551L;
	
	private List<String> infoList = new ArrayList<String>();
	
	public Top() {
		
	}

	public List<String> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<String> infoList) {
		this.infoList = infoList;
	}
	
}
