package com.victor.CustomControl;

import java.awt.*;

import javax.swing.*;
/**
 * 
 * @Description A class that can display and romve the popup window
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月17日下午4:07:17
 *
 */

public class PopupPanel extends JPanel{
	
	private static final long serialVersionUID = -1284905472327206205L;
	protected PopupMouseListener mouseListener;

	public void showPopup(Component owner, int x, int y) {
		Point p = new Point(x, y);
		SwingUtilities.convertPointToScreen(p, owner);
		
		JWindow popup = new JWindow(SwingUtilities.windowForComponent(owner));
		popup.getRootPane().setContentPane(this);
		popup.setSize(this.getPreferredSize());
		popup.setLocation(p.x, p.y);
		
		this.mouseListener = new PopupMouseListener(owner, popup);
		this.mouseListener.AddListeners();
		popup.setVisible(true);
	}
	
	public void hidePopup() {
		if(mouseListener!=null) {
			mouseListener.cancelPopup();
			mouseListener = null;
		}
	}
}
