package com.victor.mainFrame;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * @Description customize the left menu content
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月17日下午5:51:32
 *
 */

public class LeftMenuCellRenderer extends JLabel implements ListCellRenderer<MenuItem> {
	
	private static final long serialVersionUID = 2740320135413602376L;

	public LeftMenuCellRenderer() {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setPreferredSize(new Dimension(150, 40));
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
