package com.victor.CustomLayout;

import java.awt.*;
import java.util.*;

/**
 * 
 * @Description create a class to enable us set layout flexiblely
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月18日下午5:14:25
 *
 */
public class CustomFlexibleLayout extends CustomLayout implements  LayoutManager2{
	private ArrayList<ComponentItem> items = new ArrayList<>();
	
	public CustomFlexibleLayout() {
		
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		ComponentItem item = new ComponentItem(comp, -1);
		this.items.add(item);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		Iterator<ComponentItem> iterator = this.items.iterator();
		while(iterator.hasNext()) {
			ComponentItem item = (ComponentItem) iterator.next();
			if(comp == item.getComponent()) {
				iterator.remove();
				break;
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		
		return new Dimension(30, 30);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		
		return new Dimension(30, 30);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		if(insets == null) {
			insets = new Insets(0, 0, 0, 0);
		}
		
		int left = insets.left;
		int top = insets.top;
		int width = parent.getWidth()-insets.left-insets.right;
		int height = parent.getHeight()-insets.top-insets.bottom;
		for(int index=0; index<items.size(); index++) {
			ComponentItem item = (ComponentItem)items.get(index);
			Component component = item.getComponent();
			if(component.isVisible()) {
				Dimension preferredSize = component.getPreferredSize();
				int[] widths = this.getPosition(item.getLeft(), item.getRight(), width, preferredSize.width);
				int[] heights = this.getPosition(item.getTop(), item.getBottom(), height, preferredSize.height);
				component.setBounds(widths[0]+left, heights[0]+top, widths[1]-widths[0], heights[1]-heights[0]);
			}
		}
	}
	
	private int[] getPosition(int start, int end, int length, int preferredSize) {
		int[] size = new int[2];
		if(start<0 && end<0) {
			size[0] = (length-preferredSize)/2;
			size[1] = size[0]+preferredSize;
		}else if(start<0) {
			size[0] = length - end - preferredSize;
			size[1] = length - end;
		}else if(end<0) {
			size[0] = start;
			size[1] = start+preferredSize;
		}else {
			size[0] = start;
			size[1] = length - end;
		}
		
		return size;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		ComponentItem item = new ComponentItem();
		item.setComponent(comp);
		if(constraints instanceof ComponentItem) {
			item.setTop(((ComponentItem) constraints).getTop());
			item.setLeft(((ComponentItem) constraints).getLeft());
			item.setBottom(((ComponentItem) constraints).getBottom());
			item.setRight(((ComponentItem) constraints).getRight());
		}else {
			item.setBottom(-1);
			item.setLeft(-1);
			item.setRight(-1);
			item.setTop(-1);
		}
		this.items.add(item);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(30, 30);
	}
	
}
