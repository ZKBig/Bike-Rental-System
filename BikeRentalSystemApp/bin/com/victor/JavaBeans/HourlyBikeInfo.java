package com.victor.JavaBeans;

public class HourlyBikeInfo extends BikeInfo{
	private String year;
	private String month;
	private String day;
	private String startHour;
	private String endHour;
	private String rentType = "Hourly";
	
	
	
	public HourlyBikeInfo() {
		super();
	}


	public HourlyBikeInfo(String year, String month, String day, String startHour, String endHour) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
	}

	
	
	public HourlyBikeInfo(String company, String iD, String years, boolean status,String region, 
			String year, String month, String day, String startHour, String endHour) {
		super(company, iD, years, status, region);
		this.year = year;
		this.month = month;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
		
}
