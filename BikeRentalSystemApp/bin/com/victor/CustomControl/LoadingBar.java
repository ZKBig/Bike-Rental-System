package com.victor.CustomControl;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.*;

import javax.swing.JPanel;
import javax.swing.Timer;

public class LoadingBar extends JPanel{

	private static final long serialVersionUID = 2773268689866753659L;
	private Color backgroundColor = new Color(0xFFFFFF);
	private Color foregroundColor = new Color(0x1E90FF);
	private int padding = 2;
	private double speed = 0.15; //control the speed of rolling
	private Timer timer; 
	private int offset = 0;
	
	public LoadingBar() {
		this.setPreferredSize(new Dimension(90,20));
	}
	
	
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}



	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}



	public Color getForegroundColor() {
		return foregroundColor;
	}



	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}



	public int getPadding() {
		return padding;
	}



	public void setPadding(int padding) {
		this.padding = padding;
	}



	public double getSpeed() {
		return speed;
	}



	public void setSpeed(double speed) {
		this.speed = speed;
	}



	public int getOffset() {
		return offset;
	}



	public void setOffset(int offset) {
		this.offset = offset;
	}


	//turn on the timer
	public void startMoving() {
		if(timer != null) {
			return;
		}
		
		timer = new Timer(100, (e)->{
			this.repaint();
		});
		
		timer.start();
	}
	
	//turn off the timer
	public void stopMoving() {
		if(timer!=null) {
			timer.stop();
			timer = null;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, width, height);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Rectangle rect = new Rectangle(0, 0, width, height);
		rect.grow(-padding, -padding);
		g2d.setPaint(backgroundColor);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
		g2d.setPaint(foregroundColor);
		g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		double unit = 20;
		double halfUnit = unit * 0.6;
		offset += unit * speed;
		if(offset > unit) {
			offset = 0;
		}
		
		int x = rect.x;
		int y = rect.y;
		for(double gap = 0-offset; gap<width; gap+=unit) {
			Path2D path = new Path2D.Double();
			path.moveTo(x+gap, y+0);
			path.lineTo(x+gap+halfUnit, y+0);
			path.lineTo(x+gap, y+rect.height);
			path.lineTo(x+gap-halfUnit, y+rect.height);
			path.closePath();
			
			g2d.setPaint(foregroundColor);
			g2d.fill(path);
		}
	}
	
}
