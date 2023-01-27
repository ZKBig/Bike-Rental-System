package com.victor.JavaBeans;

/**
 * 
 * @Description construct the administrator account
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月11日下午2:30:51
 *
 */
public class Administrator {
	private String name;
	private String password;
	
	public Administrator() {
		
	}

	public Administrator(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
