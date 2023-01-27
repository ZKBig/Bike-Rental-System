package com.victor.JavaBeans;

public class UserInfo {
	private String name;
	private boolean sex;
	private String email;
	private String cellPhone;
	private String password;
	private BikeInfo[] bikes;
	private String money = "1000";
	private String imageName = "headPortrait.png";
	private String birthday;
	private String individualResume;
	
	public UserInfo() {
		super();
	}

	public UserInfo(String name, boolean sex, String email, String cellPhone, String password) {
		super();
		this.name = name;
		this.sex = sex;
		this.email = email;
		this.cellPhone = cellPhone;
		this.password = password;
	}
	
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BikeInfo[] getBikes() {
		return bikes;
	}
	public void setBikes(BikeInfo[] bikes) {
		this.bikes = bikes;
	}	
	public String getMoney() {
		return money;
	}

	public void setMoney(String string) {
		this.money = string;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIndividualResume() {
		return individualResume;
	}
	public void setIndividualResume(String individualResume) {
		this.individualResume = individualResume;
	}
	
}
