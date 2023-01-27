package com.victor.CustomControl;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.*;

/**
 * 
 * @Description create the round icon
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月18日下午8:56:16
 *
 */
public class RoundIcon extends JPanel{
	private Image image;
	
	public RoundIcon(Image image) {
		this.setOpaque(false);
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		int width = this.getWidth();
		int height = this.getHeight();
		Rectangle rectangle = new Rectangle(width, height);
		Graphics2D g2d =(Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rectangle.grow(-2,-2);
		int size = width;
		if(size>height) {
			size = height;
		}
		
		int x = width/2;
		int y = height/2;
		int radius = size/2;
		
		Shape outline = new Ellipse2D.Double(x - radius, y-radius, size, size);
		if(image!=null) {
			g2d.setClip(outline);
			g2d.drawImage(image, 0, 0, width, height, null);
			g2d.setClip(null);
		}
		
		g2d.setPaint(new Color(0xFFD700));
		g2d.draw(outline);
	}
	
	
	
}
