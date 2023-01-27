package com.victor.CustomLayout;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 
 * @Description customize RowLayout
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月6日下午9:36:24
 *
 */

public class CustomRowLayout extends CustomLayout{
	
	private List<CustomControl> controls = new ArrayList<>();
	private boolean isFullColumn = false;
	private int distance = 3;

	public CustomRowLayout() {
		super();
	}
	
	public CustomRowLayout(int distance) {
		super();
		this.distance = distance;
	}
	
	public CustomRowLayout(boolean isFullColumn, int distance) {
		super();
		this.isFullColumn = isFullColumn;
		this.distance = distance;
	}

	public List<CustomControl> getControls() {
		return controls;
	}

	public void setControls(List<CustomControl> controls) {
		this.controls = controls;
	}

	public boolean isFullColumn() {
		return isFullColumn;
	}

	public void setFullColumn(boolean isFullColumn) {
		this.isFullColumn = isFullColumn;
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
		return new Dimension(40,40);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(30,30);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(45,45);
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
		int sumWidth = 0;
		int sumWeight = 0;
		int sumOfDistances = distance * (validControls.size()-1);
		int avaliableWidth= rect.width - sumOfDistances;
		for(CustomControl control : validControls){
			Dimension dimension = control.comp.getPreferredSize();
			control.width = dimension.width;
			control.weight = 0;
			control.height = isFullColumn ? rect.height : dimension.height;
			
			String setting = control.customSetting;
			if(setting.endsWith("%")) {
				int percentage = Integer.valueOf(setting.substring(0, setting.length()-1));
				control.width = avaliableWidth * percentage/100;
			}else if(setting.endsWith("w")) {
				int weight = Integer.valueOf(setting.substring(0, setting.length()-1));
				control.width = 0;
				control.weight = weight;
			}else if(setting.endsWith("pixels")) {
				int pixels = Integer.valueOf(setting.substring(0, setting.length()-6));
				control.width = pixels;
			}
			
			sumWidth += control.width;
			sumWeight += control.weight;	
		}
		
		//set the control according to its weight using the remaining width.
		if(sumWeight>0) {
			int remainWidth = avaliableWidth - sumWidth;
			for(CustomControl control : validControls) {
				if(control.weight>0) 
					control.width = (int)(((double)remainWidth/sumWeight) * control.weight);
			}
		}
		
		int x = 0;
		for(CustomControl control : validControls) {
			//show controls in the middle of the panel.
			int y = (rect.height - control.height)/2;
			//calculate the width of each control.
			if(x + control.width > rect.width) {
				control.width = rect.width - x;
			}
			if(control.width<=0) break;
			
			control.comp.setBounds(rect.x + x, rect.y+y, control.width, control.height);
			x += control.width;
			x += distance;
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
