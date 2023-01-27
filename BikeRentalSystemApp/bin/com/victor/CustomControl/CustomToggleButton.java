package com.victor.CustomControl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * 
 * @Description 自定义开关控件ToggleButton （显示，数据，行为）
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月3日上午11:49:43
 *
 */
public class CustomToggleButton extends CustomPanel{

	private static final long serialVersionUID = 1L;
	
	//参数设定
	private boolean selected = true;
	private StateListener stateListener;
	private Color backgroundColor = new Color(0xFFFFFF); //主背景
	private Color lineColor = new Color(0xDEDEDE); //描边
	private Color fillDarkColor = new Color(0xE1E1E1); //灰色填充
	private Color fillLightColor = new Color(0x33B4FF); //高亮填充
	private int distance = 2; //轮廓线与内部圆的距离
	
	public CustomToggleButton() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MouseClicked();
			}
		});
	}
	
	public interface StateListener{
		public void stateChanged(Object source);
	}
	
	public void setStateListener(StateListener listener) {
		this.stateListener=listener;
	}
	
		
	public boolean getSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected=selected;
		repaint();
	}
	
	public void toggle() {
		this.selected = !this.selected;
		repaint();
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getFillDarkColor() {
		return fillDarkColor;
	}

	public void setFillDarkColor(Color fillDarkColor) {
		this.fillDarkColor = fillDarkColor;
	}

	public Color getFillLightColor() {
		return fillLightColor;
	}

	public void setFillLightColor(Color fillLightColor) {
		this.fillLightColor = fillLightColor;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	private void MouseClicked() {
		toggle();
		if(stateListener!=null) {
			stateListener.stateChanged(this);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//算出一个2:1的最大的矩形
		int w = width;
		int h = width/2;
		if(h>height) {
			h=height;
			w=height*2;
		}
		Rectangle rect = new Rectangle((width-w)/2, (height-h)/2, w, h);
		
//		Rectangle rect = new Rectangle(width/2-50,0,80,40);
		Rectangle rect1 = new Rectangle(rect.x, rect.y, rect.width/2, rect.height);
		Rectangle rect2 = new Rectangle(rect.x+rect.width/2, rect.y, rect.width/2, rect.height);
		
		//绘制按钮控件的边框
		drawButtonFrame(g2d, rect1, rect2);
		
		//控件画两个圆按钮
		if(selected) {
			drawCircleInside(g2d, rect2, distance, lineColor, fillLightColor);
		}else {
			drawCircleInside(g2d, rect1, distance, lineColor, fillDarkColor);
		}
	}

	private void drawButtonFrame(Graphics2D g2d, Rectangle rect1, Rectangle rect2) {
		Shape arc1 = new Arc2D.Double(rect1, 90, 180, Arc2D.OPEN);
		Shape arc2 = new Arc2D.Double(rect2, 270, 180, Arc2D.OPEN);
		
		Path2D path = new Path2D.Double();
		path.append(arc1.getPathIterator(null), false);
		path.append(arc2.getPathIterator(null), true);
		path.closePath();
	
		g2d.setPaint(backgroundColor);
		g2d.fill(path);
		g2d.setPaint(lineColor);
		g2d.draw(path);
		
	}

	private void drawCircleInside(Graphics2D g2d, Rectangle rect1, int distance2, Color lineColor2, Color fillColor2) {
		Rectangle rect = new Rectangle(rect1);
		rect.x+=distance2;
		rect.y+=distance2;
		rect.width -= (distance2 * 2);
		rect.height -= (distance2 * 2);
		
		Shape shape = new Ellipse2D.Double(rect.x, rect.y, rect.width, rect.height);
		
		g2d.setPaint(lineColor);
		g2d.draw(shape);
		
		g2d.setPaint(fillColor2);
		g2d.fill(shape);
	}
	

}
