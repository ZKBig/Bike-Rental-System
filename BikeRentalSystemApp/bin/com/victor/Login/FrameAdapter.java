package com.victor.Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;

/**
 * 
 * @Description create a class that can enable frame to move along with the mouse
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月18日下午3:54:06
 *
 */
public class FrameAdapter extends MouseAdapter{
	private Component component;
	private boolean pressed = false;
	private Point mouseStartPosition;
	private Point windowStartPosition;
	private Dimension windowStartSize;
	private Window window;
	
	public FrameAdapter(Component component) {
		this.component = component;
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		window = SwingUtilities.windowForComponent(component);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			pressed = true;
			mouseStartPosition = e.getLocationOnScreen();
			
			//get the size and the position of the window
			windowStartPosition = window.getLocation();
			windowStartSize = window.getSize();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			Point position = e.getLocationOnScreen();
			int dx = position.x - mouseStartPosition.x;
			int dy = position.y - mouseStartPosition.y;
			
			window.setLocation(windowStartPosition.x + dx, windowStartPosition.y+dy);
		}
	}
}
