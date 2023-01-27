package com.victor.CustomControl;

import java.awt.*;
import javax.swing.*;


public class ImageView extends JPanel{
	private static final long serialVersionUID = 1L;
	// zoom type
	public static final int FIT_XY = 0;
	public static final int FIT_CENTER = 1;
	public static final int FIT_CENTER_INSIDE = 2;

	private Image image;
	private int scaleType = FIT_CENTER;
	private Color bgColor = Color.white; 

	public ImageView() {
		this.setOpaque(false);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	public int getSclaerType() {
		return scaleType;
	}

	public void setSclaerType(int sclaerType) {
		this.scaleType = sclaerType;
		repaint();
	}

	public Color getBgcolor() {
		return bgColor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgColor = bgcolor;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		//call the suoerclass's paintCompoment() to draw the necessary borders and background.
		super.paintComponent(g);

		//get the height and width of this control
		int width = this.getWidth();
		int height = this.getHeight();

		// clear display
		g.clearRect(0, 0, width, height);

		// set the background color
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);

		if (image != null) {
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);

			ImageScaler scaler = new ImageScaler(imageWidth, imageHeight, width, height);

			Rectangle fit = scaler.fitXY();
			if (scaleType == FIT_CENTER) {
				fit = scaler.fitCenter();
			} else if (scaleType == FIT_CENTER_INSIDE) {
				fit = scaler.fitCenterInside();
			}

			g.drawImage(image, fit.x, fit.y, fit.width, fit.height, null);
		}

	}

}
