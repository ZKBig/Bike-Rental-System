package com.victor.JavaBeans;

public class BikeInfo {
	private String company;
	private String ID;
	private String years;
	private boolean status;
	private String region;
	
	
	public BikeInfo() {
		super();
	}

	public BikeInfo(String company, String iD, String years, boolean status, String region) {
		super();
		this.company = company;
		ID = iD;
		this.years = years;
		this.status = status;
		this.region = region;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return company+"    "+ID+"    "+years+"     "+status+"     "+region;
	}
	
}
