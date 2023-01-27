package com.victor.CustomLayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @Description Customize columnLayout
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月7日上午8:51:55
 *
 */

public class CustomColumnLayout extends CustomLayout{
	
	private List<CustomControl> controls = new ArrayList<>();
	private boolean isFullRow = false;
	private int distance = 2;

	public CustomColumnLayout() {
		super();
	}
	
	public CustomColumnLayout(int distance) {
		super();
		this.distance = distance;
	}
	
	public CustomColumnLayout(boolean isFullRow, int distance) {
		super();
		this.isFullRow = isFullRow;
		this.distance = distance;
	}

	public List<CustomControl> getControls() {
		return controls;
	}

	public void setControls(List<CustomControl> controls) {
		this.controls = controls;
	}

	public boolean isFullRow() {
		return isFullRow;
	}

	public void setFullColumn(boolean isFullRow) {
		this.isFullRow = isFullRow;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		CustomControl control = new CustomControl();
		control.comp = comp;
		control.customSetting = "default";
		controls.add(control);
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		CustomControl control = new CustomControl();
		control.comp = comp;
		control.customSetting = (String) constraints;
		controls.add(control);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		Iterator<CustomControl> iterator = controls.iterator();
		while(iterator.hasNext()) {
			CustomControl control = iterator.next();
			if(control.comp == comp) {
				iterator.remove();
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(30,30);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(30,30);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(30,30);
	}

	@Override
	public void layoutContainer(Container parent) {
		Rectangle rect = new Rectangle(parent.getWidth(), parent.getHeight());
		Insets insets = parent.getInsets();
		rect.x += insets.left;
		rect.y += insets.top;
		rect.width -= (insets.left + insets.right);
		rect.height -= (insets.top + insets.bottom);
		
		//filter the controls which are invisible.
		List<CustomControl> validControls = new ArrayList<>();
		for(CustomControl control : controls) {
			if(control.comp.isVisible()) {
				validControls.add(control);
			}
		}
		
		//calculate the results of percentage, auto and pixels.
		int sumHeight = 0;
		int sumWeight = 0;
		int sumOfDistances = distance * (validControls.size()-1);
		int avaliableHeight= rect.height- sumOfDistances;
		for(CustomControl control : validControls){
			Dimension dimension = control.comp.getPreferredSize();
			control.height = dimension.height;
			control.weight = 0;
			control.width = isFullRow ? dimension.width : rect.width;
			
			String setting = control.customSetting;
			if(setting.endsWith("%")) {
				int percentage = Integer.valueOf(setting.substring(0, setting.length()-1));
				control.height = avaliableHeight * percentage/100;
			}else if(setting.endsWith("w")) {
				int weight = Integer.valueOf(setting.substring(0, setting.length()-1));
				control.height = 0;
				control.weight = weight;
			}else if(setting.endsWith("pixels")) {
				int pixels = Integer.valueOf(setting.substring(0, setting.length()-6));
				control.height = pixels;
			}
			
			sumHeight += control.height;
			sumWeight += control.weight;	
		}
		
		//set the control according to its weight using the remaining width.
		if(sumWeight>0) {
			int remainHeight = avaliableHeight - sumHeight;
			for(CustomControl control : validControls) {
				if(control.weight>0) 
					control.height = (int)(((double)remainHeight/sumWeight) * control.weight);
			}
		}
		
		int y = 0;
		for(CustomControl control : validControls) {
			//show controls in the beginning of the each row.
			int x = 0;
			//calculate the height of each control.
			if(y + control.height > rect.height) {
				control.height = rect.height - y;
			}
			if(control.height <=0) break;
			
			control.comp.setBounds(rect.x + x, rect.y+y, control.width, control.height);
			y += control.height;
			y += distance;
		}
	}
	
	private static class CustomControl{
		Component comp;
		String customSetting = "default";
		int width = 0;
		int height = 0;
		int weight = 0;
		
		private CustomControl() {
			
		}
	}


}
