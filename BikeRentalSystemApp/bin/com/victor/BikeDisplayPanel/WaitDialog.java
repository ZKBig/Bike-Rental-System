package com.victor.BikeDisplayPanel;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.victor.CustomControl.LoadingBar;
import com.victor.JavaBeans.*;

public class WaitDialog extends JDialog{
	private static final long serialVersionUID = -5276054043380283294L;
	private LoadingBar bar = new LoadingBar();
	
	public WaitDialog(JFrame owner) {
		super(owner, "", true);
		this.setUndecorated(true);
		
		initialPanel();
	}

	/**
	 * 
	 * @Description initial the panel
	 * @author Victor
	 * @date 2020年12月10日下午7:16:072020年12月10日
	 *
	 */
	private void initialPanel() {
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		
		root.setOpaque(true);
		root.setBackground(new Color(0xF4F4F4));
		root.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		root.add(bar, BorderLayout.CENTER);
		
		this.setSize(200, 25);
	}
	
	/**
	 * 
	 * @Description let the owner execute the dialog
	 * @author Victor
	 * @date 2020年12月10日下午7:21:242020年12月10日
	 *
	 */
	public void execute() {
		Rectangle frameRect = this.getOwner().getBounds();
		int width = this.getWidth();
		int height = this.getHeight();
		int x = frameRect.x + (frameRect.width - width)/2;
		int y = frameRect.y + (frameRect.height - height)/2;
		this.setBounds(x, y, width, height);
		
		bar.startMoving();
		this.setVisible(true);
	}
}
