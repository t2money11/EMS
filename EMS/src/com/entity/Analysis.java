package com.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import com.util.StringHandler;

public class Analysis extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 2719574786603002144L;
	
	private String dateFrom;
	private String dateTo;
	
	private String yearSelected;
	private String monthSelected;
	
	private String rate;
	
	Map<String, String> yearInput = new LinkedHashMap<String, String>();
	Map<String, String> monthInput = new LinkedHashMap<String, String>();
	
	public Analysis() {
		
		Format f = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		
		yearInput.put("", "");
		
		c.add(Calendar.YEAR, -3);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		c.add(Calendar.YEAR, 1);
		yearInput.put(f.format(c.getTime()), f.format(c.getTime()));
		
		monthInput.put("", "");
		monthInput.put("01", "01");
		monthInput.put("02", "02");
		monthInput.put("03", "03");
		monthInput.put("04", "04");
		monthInput.put("05", "05");
		monthInput.put("06", "06");
		monthInput.put("07", "07");
		monthInput.put("08", "08");
		monthInput.put("09", "09");
		monthInput.put("10", "10");
		monthInput.put("11", "11");
		monthInput.put("12", "12");
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDateFrom() {
		return StringHandler.format(dateFrom);
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return StringHandler.format(dateTo);
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getYearSelected() {
		return StringHandler.format(yearSelected);
	}

	public void setYearSelected(String yearSelected) {
		this.yearSelected = yearSelected;
	}

	public String getMonthSelected() {
		return StringHandler.format(monthSelected);
	}

	public void setMonthSelected(String monthSelected) {
		this.monthSelected = monthSelected;
	}

	public Map<String, String> getYearInput() {
		return yearInput;
	}

	public void setYearInput(Map<String, String> yearInput) {
		this.yearInput = yearInput;
	}

	public Map<String, String> getMonthInput() {
		return monthInput;
	}

	public void setMonthInput(Map<String, String> monthInput) {
		this.monthInput = monthInput;
	}
}
