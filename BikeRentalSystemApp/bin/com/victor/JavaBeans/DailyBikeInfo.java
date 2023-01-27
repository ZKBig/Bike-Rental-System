package com.victor.JavaBeans;

public class DailyBikeInfo extends BikeInfo{
	private String rentType = "Daily";
	private String year;
	private String year1;
	private String month;
	private String month1;
	private String day;
	private String day1;
	
	public DailyBikeInfo() {
		
	}
	
	public DailyBikeInfo(String year, String year1, String month, String month1, String day, String day1) {
		super();
		this.year = year;
		this.year1 = year1;
		this.month = month;
		this.month1 = month1;
		this.day = day;
		this.day1 = day1;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear1() {
		return year1;
	}

	public void setYear1(String year1) {
		this.year1 = year1;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	
}
