package com.victor.CustomControl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @Description the mouse listener of the popup window
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月17日下午1:08:11
 *
 */
public class PopupMouseListener implements AWTEventListener, WindowListener, ComponentListener{

	private Window window;
	private JWindow popup;
	private Component owner;
	
	public PopupMouseListener(Component owner, JWindow popup) {
		this.owner = owner;
		this.popup = popup;
	}
	
	/**
	 * 
	 * @Description add the listener of the operation of the window
	 * @author Victor
	 * @date 2020年11月17日下午1:25:502020年11月17日
	 *
	 */
	public void AddListeners() {
		//listen the mouse event occurs in the main window
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		toolKit.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK|AWTEvent.MOUSE_MOTION_EVENT_MASK|
				AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		//get the ancestor window of the component
		window = SwingUtilities.getWindowAncestor(owner);
		
		//set the listener of the operation of maximize, minimize and close the  main window
		window.addWindowListener(this);
		
		//set the listener of the operation of move and change the size of the window
		window.addComponentListener(this);
	}
	
	/**
	 * 
	 * @Description remove the listeners of the window
	 * @author Victor
	 * @date 2020年11月17日下午1:28:432020年11月17日
	 *
	 */
	public void removeListeners() {
		window.removeWindowListener(this);
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
	}
	
	/**
	 * 
	 * @Description cancel the display of the popup window
	 * @author Victor
	 * @date 2020年11月17日下午3:30:182020年11月17日
	 *
	 */
	public void cancelPopup() {
		this.removeListeners();
		popup.setVisible(false);
	}
	
	
	
	@Override
	public void componentResized(ComponentEvent e) {
		cancelPopup();
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		cancelPopup();
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		cancelPopup();
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		cancelPopup();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		cancelPopup();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		cancelPopup();
		
	}

	/**
	 * judge whether the component clicked by the mouse is in the popup window
	 */
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent){
			int ID = event.getID();
			if(ID == MouseEvent.MOUSE_PRESSED) {
				MouseEvent e = (MouseEvent) event;
				//get the component that mouse clicked
				Component comp = e.getComponent();
				Window targetWindow = SwingUtilities.getWindowAncestor(comp);
				if(targetWindow!=popup) {
					cancelPopup();
				}
			}
		}
		
	}

}
