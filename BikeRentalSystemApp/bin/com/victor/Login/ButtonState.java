package com.victor.Login;

import java.awt.*;

/**
 * 
 * @Description change the display of the button with the change of its state
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月19日上午10:43:55
 *
 */

public class ButtonState {
	private Color backgroundColor;
	private Font textFont;
	private Color wordsColor;
	private Color borderColor = Color.WHITE;
	private int borderWidth = 1;
	
	
	public ButtonState() {
		super();
	}

	public ButtonState(int borderWidth) {
		super();
		this.borderWidth = borderWidth;
	}

	public ButtonState(Color backgroundColor, Color wordsColor, Color borderColor) {
		super();
		this.backgroundColor = backgroundColor;
		this.wordsColor = wordsColor;
		this.borderColor = borderColor;
	}

	public ButtonState(Color backgroundColor, Font textFont, Color wordsColor, Color borderColor, int borderWidth) {
		super();
		this.backgroundColor = backgroundColor;
		this.textFont = textFont;
		this.wordsColor = wordsColor;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Font getTextFont() {
		return textFont;
	}

	public void setTextFont(Font font) {
		this.textFont = font;
	}

	public Color getWordsColor() {
		return wordsColor;
	}

	public void setWordsColor(Color wordsColor) {
		this.wordsColor = wordsColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	
}
