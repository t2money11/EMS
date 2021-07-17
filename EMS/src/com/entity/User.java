package com.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.context.ConstantParam;
import com.util.StringHandler;

public class User extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -814282956411810252L;
	
	private String userId;
	private String userNameC;
	private String userNameE;
	private String password;
	private String mail;
	private String contact;
	private String urgentContact;
	private String birthday;
	private String gender;
	private String genderName;
	private String highestEducation;
	private String highestEducationName;
	private String position;
	private String onboardDate;
	private String seperateDate;
	private String contractPeriod;
	private String humanRelations;
	private String salary;
	private String welfare;
	private String vacation;
	private String address;
	private String comment;
	
	private String category;
	private String categoryName;
	//只为用户更新用
	private String loginUserCategory;
	
	Map<String, String> genderInput = null;  
	Map<String, String> highestEducationInput = null;  
	Map<String, String> vacationInput = null;
	Map<String, String> categoryInput = null;
	
	private String userId4S;
	private String userNameC4S;
	private String userNameE4S;
	private List<User> resultUserList = null;
	
	private String passwordOld;
	private String passwordNew1;
	private String passwordNew2;
	
	public User(){
		
		resultUserList = new ArrayList<User>();
		genderInput = ConstantParam.codeMap.get(ConstantParam.GENDER);
		highestEducationInput = ConstantParam.codeMap.get(ConstantParam.HIGHESTEDUCATION);
		vacationInput = ConstantParam.codeMap.get(ConstantParam.VACATION);
		categoryInput = ConstantParam.codeMap.get(ConstantParam.CATEGORY);
	}
	
	
	public String getMail() {
		return StringHandler.format(mail);
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getLoginUserCategory() {
		return StringHandler.format(loginUserCategory);
	}
	public void setLoginUserCategory(String loginUserCategory) {
		this.loginUserCategory = loginUserCategory;
	}
	public String getCategoryName() {
		return StringHandler.format(categoryName);
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategory() {
		return StringHandler.format(category);
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUserId() {
		return StringHandler.format(userId);
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNameC() {
		return StringHandler.format(userNameC);
	}
	public void setUserNameC(String userNameC) {
		this.userNameC = userNameC;
	}
	public String getUserNameE() {
		return StringHandler.format(userNameE);
	}
	public void setUserNameE(String userNameE) {
		this.userNameE = userNameE;
	}
	public String getPassword() {
		return StringHandler.format(password);
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return StringHandler.format(contact);
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUrgentContact() {
		return StringHandler.format(urgentContact);
	}
	public void setUrgentContact(String urgentContact) {
		this.urgentContact = urgentContact;
	}
	public String getBirthday() {
		return StringHandler.format(birthday);
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return StringHandler.format(gender);
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHighestEducation() {
		return StringHandler.format(highestEducation);
	}
	public void setHighestEducation(String highestEducation) {
		this.highestEducation = highestEducation;
	}
	public String getPosition() {
		return StringHandler.format(position);
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOnboardDate() {
		return StringHandler.format(onboardDate);
	}
	public void setOnboardDate(String onboardDate) {
		this.onboardDate = onboardDate;
	}
	public String getSeperateDate() {
		return StringHandler.format(seperateDate);
	}
	public void setSeperateDate(String seperateDate) {
		this.seperateDate = seperateDate;
	}
	public String getContractPeriod() {
		return StringHandler.format(contractPeriod);
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getHumanRelations() {
		return StringHandler.format(humanRelations);
	}
	public void setHumanRelations(String humanRelations) {
		this.humanRelations = humanRelations;
	}
	public String getSalary() {
		return StringHandler.format(salary);
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getWelfare() {
		return StringHandler.format(welfare);
	}
	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}
	public String getVacation() {
		return StringHandler.format(vacation);
	}
	public void setVacation(String vacation) {
		this.vacation = vacation;
	}
	public String getAddress() {
		return StringHandler.format(address);
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComment() {
		return StringHandler.format(comment);
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Map<String, String> getGenderInput() {
		return genderInput;
	}
	public void setGenderInput(Map<String, String> genderInput) {
		this.genderInput = genderInput;
	}
	public Map<String, String> getHighestEducationInput() {
		return highestEducationInput;
	}
	public void setHighestEducationInput(Map<String, String> highestEducationInput) {
		this.highestEducationInput = highestEducationInput;
	}
	public Map<String, String> getVacationInput() {
		return vacationInput;
	}
	public void setVacationInput(Map<String, String> vacationInput) {
		this.vacationInput = vacationInput;
	}
	public String getUserId4S() {
		return StringHandler.format(userId4S);
	}
	public void setUserId4S(String userId4S) {
		this.userId4S = userId4S;
	}
	public String getUserNameC4S() {
		return StringHandler.format(userNameC4S);
	}
	public void setUserNameC4S(String userNameC4S) {
		this.userNameC4S = userNameC4S;
	}
	public String getUserNameE4S() {
		return StringHandler.format(userNameE4S);
	}
	public void setUserNameE4S(String userNameE4S) {
		this.userNameE4S = userNameE4S;
	}
	public List<User> getResultUserList() {
		return resultUserList;
	}
	public void setResultUserList(List<User> resultUserList) {
		this.resultUserList = resultUserList;
	}
	public Map<String, String> getCategoryInput() {
		return categoryInput;
	}
	public void setCategoryInput(Map<String, String> categoryInput) {
		this.categoryInput = categoryInput;
	}
	public String getGenderName() {
		return StringHandler.format(genderName);
	}
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
	public String getHighestEducationName() {
		return StringHandler.format(highestEducationName);
	}
	public void setHighestEducationName(String highestEducationName) {
		this.highestEducationName = highestEducationName;
	}
	public String getPasswordOld() {
		return StringHandler.format(passwordOld);
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getPasswordNew1() {
		return StringHandler.format(passwordNew1);
	}
	public void setPasswordNew1(String passwordNew1) {
		this.passwordNew1 = passwordNew1;
	}
	public String getPasswordNew2() {
		return StringHandler.format(passwordNew2);
	}
	public void setPasswordNew2(String passwordNew2) {
		this.passwordNew2 = passwordNew2;
	}
	
}