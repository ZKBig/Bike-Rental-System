package com.victor.CustomControl;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * 
 * @Description custom panel to help set layout quickly.
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月10日下午1:12:07
 *
 */

public class CustomPanel extends JPanel{
	private static final long serialVersionUID = -1632248847418084170L;
	private Color backgroundColor;
	
	public CustomPanel() {
		//set the background transparent.
		this.setOpaque(false);
	}
	
	public CustomPanel(int width, int height) {
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public CustomPanel setPreferrenWidth(int width) {
		Dimension sizes = this.getPreferredSize();
		if(sizes==null) {
			sizes = new Dimension(0,0);
		}
		sizes.width = width;
		this.setPreferredSize(sizes);
		return this;
	}
	
	public CustomPanel setPreferrenHeight(int height) {
		Dimension sizes = this.getPreferredSize();
		if(sizes==null) {
			sizes = new Dimension(0,0);
		}
		sizes.height = height;
		this.setPreferredSize(sizes);
		return this;
	}
	
	
	public CustomPanel setPadding(int size) {
		return setPadding(size, size, size, size);
	}

	public CustomPanel setPadding(int size, int size2, int size3, int size4) {
		Borders.setPadding(this, size, size2, size3, size4);
		return this;
	}
	
	public CustomPanel setMargin(int size) {
		return setMargin(size, size, size, size);
	}

	public CustomPanel setMargin(int size, int size2, int size3, int size4) {
		Borders.setMargin(this, size, size2, size3, size4);
		return this;
	}
	
	public void setOuterBorder(JComponent c, Border border) {
		Borders.setOuterBorder(this, border);
	}
	
	public void setInnerBorder(JComponent c, Border border) {
		Borders.setInnerBorder(this, border);
	}
	
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
		this.repaint();
	}
}
