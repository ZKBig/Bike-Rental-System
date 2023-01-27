package com.victor.Login;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

/**
 * 
 * @Description a class help us to customize button
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月19日上午10:47:38
 *
 */
public class CustomButton extends JPanel{
	private static final long serialVersionUID = -5984546317707801806L;
	private int buttonMargin =2;
	private int buttonPadding = 4;
	private int length= 10;
	private boolean isPressed = false;
	private String buttonText;
	private ActionListener clickListener;
	private ButtonState normalState;
	private ButtonState pressedState;
	
	public CustomButton(String buttonText) {
		this.setBackground(Color.white);
		this.buttonText = buttonText;
		this.setOpaque(false);
		this.enableEvents(16L);
		normalState = new ButtonState(new Color(16645629), new Font("Arial", 0, 14), new Color(3158064),
				new Color(12632256), 1);
		pressedState = new ButtonState(new Color(16777215), new Font("Arial", 0, 14), new Color(7372944),
				new Color(7372944), 1);
	}

	public int getButtonMargin() {
		return buttonMargin;
	}

	public void setButtonMargin(int buttonMargin) {
		this.buttonMargin = buttonMargin;
	}

	public int getButtonPadding() {
		return buttonPadding;
	}

	public void setButtonPadding(int buttonPadding) {
		this.buttonPadding = buttonPadding;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	
	public void addActionListener(ActionListener listener) {
		this.clickListener = listener;
	}
	
	public ButtonState getNormalState() {
		return normalState;
	}

	public void setNormalState(ButtonState normalState) {
		this.normalState = normalState;
	}

	public ButtonState getPressedState() {
		return pressedState;
	}

	public void setPressedState(ButtonState pressedState) {
		this.pressedState = pressedState;
	}

	@Override
	public Dimension getPreferredSize() {
		FontMetrics fontMetrics = this.getFontMetrics(normalState.getTextFont());
		int fontHeight = fontMetrics.getHeight();
		int textWidth = fontMetrics.stringWidth(buttonText);
		int width = textWidth+buttonPadding*2 + buttonMargin*2 +3;
		int height = fontHeight+buttonPadding*2 + buttonMargin*2 +3;
		
		return new Dimension(width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		Graphics2D g2d = (Graphics2D)g;
		Rectangle rectangle = new Rectangle(width, height);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		rectangle.grow(-buttonMargin, -buttonMargin);
		Color backgroundColor = normalState.getBackgroundColor();
		Color wordsColor = normalState.getWordsColor();
		Color borderColor = normalState.getBorderColor();
		Font textfont = normalState.getTextFont();
		//If the button is presses, then change the display state of the button
		if(isPressed) {
			backgroundColor = pressedState.getBackgroundColor();
			borderColor = pressedState.getBorderColor();
			textfont = pressedState.getTextFont();
			wordsColor = pressedState.getWordsColor();
		}
		
		rectangle.grow(-1, -1);
		Shape shape = new RoundRectangle2D.Double((double)rectangle.x, (double)rectangle.y, (double)rectangle.width, (double)rectangle.height, 
				(double)this.length, (double)this.length);
		g2d.setPaint(backgroundColor);
		g2d.fill(shape);
		g2d.setStroke(new BasicStroke(1.2F));
		g2d.setPaint(borderColor);
		g2d.draw(shape);
		
		
		//draw text of the button
		if(this.buttonText!=null && textfont!=null) {
			Rectangle rect = new Rectangle(rectangle);
			rect.grow(-this.buttonPadding, -this.buttonPadding);
			FontMetrics fontMetrics = g2d.getFontMetrics(textfont); 
			
			int fontHeight = fontMetrics.getHeight();
			int textWidth = fontMetrics.stringWidth(this.buttonText);
			int leading = fontMetrics.getLeading();
			int descent = fontMetrics.getDescent();
			
			int x = rect.x + (rect.width-textWidth)/2;
			int y = rect.y + rect.height/2 + (fontHeight-leading)/2-descent;
			g2d.setFont(textfont);
			g2d.setPaint(wordsColor);
			g2d.drawString(buttonText, x, y);
		}
	}
	
	@Override
	protected void processMouseEvent(MouseEvent e) {
        int ID = e.getID();
        if (ID == 502) {
            Rectangle rectangle = new Rectangle(this.getWidth(), this.getHeight());
            if (!rectangle.contains(e.getPoint())) {
                return;
            }

            if (this.clickListener != null) {
                ActionEvent listener = new ActionEvent(this, ID, "clicked");
                this.clickListener.actionPerformed(listener);
            }
        }

        if (ID == 504) {
            this.isPressed = true;
            this.repaint();
        } else if (ID == 505) {
            this.isPressed = false;
            this.repaint();
        }
    }
}
