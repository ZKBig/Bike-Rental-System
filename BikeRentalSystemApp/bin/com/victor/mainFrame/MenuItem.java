package com.victor.mainFrame;

/**
 * 
 * @Description create the item choice class in the menu
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月17日下午5:38:17
 *
 */
public class MenuItem {
	private String name;
	private String cmd;
	
	
	public MenuItem(String name) {
		super();
		this.name = name;
	}
	
	public MenuItem(String name, String cmd) {
		super();
		this.name = name;
		this.cmd = cmd;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
