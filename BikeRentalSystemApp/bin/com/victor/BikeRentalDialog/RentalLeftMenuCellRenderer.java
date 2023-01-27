package com.victor.BikeRentalDialog;

import java.awt.*;


import javax.swing.*;

import com.victor.mainFrame.MenuItem;

/**
 * 
 * @Description left menu of the individual rental dialog
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月24日下午12:33:46
 *
 */

public class RentalLeftMenuCellRenderer extends JLabel implements ListCellRenderer<MenuItem> {
	private static final long serialVersionUID = -1406561572812621936L;

	public RentalLeftMenuCellRenderer() {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setPreferredSize(new Dimension(230,40));
		this.setForeground(new Color(0x333333));
		this.setBorder(BorderFactory.createEmptyBorder(10, 4, 10, 4));
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends MenuItem> list, MenuItem value, int index,
			boolean isSelected, boolean cellHasFocus) {
		String name = value.getName();
		this.setText(name);
		
		if (isSelected) {
			this.setBackground(new Color(255, 255, 255, 60));
			this.setForeground(new Color(0x66B4FF));
		} else {
			this.setBackground(new Color(255, 255, 255, 20));
			this.setForeground(new Color(0xF1F1F1));
		}
		return this;
	}

}
