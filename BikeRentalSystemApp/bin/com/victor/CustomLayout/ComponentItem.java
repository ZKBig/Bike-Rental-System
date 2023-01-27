package com.victor.CustomLayout;

import java.awt.Component;

public class ComponentItem{
	private Component component;
	private int top;
	private int left;
	private int bottom;
	private int right;
	
	public ComponentItem() {
		
	}
	
	public ComponentItem(int num) {
		this(num, num, num, num);
	}
	
	public ComponentItem(int top, int left, int bottom, int right) {
		this.top = top;
	    this.left = left;
	    this.bottom = bottom;
	    this.right = right;
	}
	
	public ComponentItem(Component component, int num) {
		this(component, num, num, num, num);
	}
	
	public ComponentItem(Component component, int top, int left, int bottom, int right) {
		this.component = component;
		this.top = top;
	    this.left = left;
	    this.bottom = bottom;
	    this.right = right;
	}

	
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}
	
}
