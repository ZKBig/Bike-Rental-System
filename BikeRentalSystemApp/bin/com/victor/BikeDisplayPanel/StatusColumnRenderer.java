package com.victor.BikeDisplayPanel;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import com.victor.CustomControl.*;


public class StatusColumnRenderer extends CustomPanel implements TableCellRenderer{

	private static final long serialVersionUID = -128058238944011374L;
	private ImageView imageField = new ImageView();
	private Image available;
	private Image unavailable;
	
	public StatusColumnRenderer() {
		this.setLayout(new BorderLayout());
		this.setPadding(8);
		this.setPreferredSize(new Dimension(150,150));
		this.add(imageField, BorderLayout.CENTER);
		
		try {
			available = ImageIO.read(getClass().getResource("/com/victor/Images/available.png"));
			unavailable = ImageIO.read(getClass().getResource("/com/victor/Images/unavailable.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//set the background color of the image is transparent
		imageField.setBgcolor(new Color(255,255,255,0));
		
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Boolean status = (Boolean)value;
		if(status!=null && status == true) {
			imageField.setImage(available);
		}else {
			imageField.setImage(unavailable);
		}
		this.setOpaque(true);
		
		if(isSelected) {
			this.setBackground(table.getSelectionBackground());
			this.setForeground(table.getSelectionForeground());
			imageField.setBgcolor(table.getSelectionBackground());
		}else {
			this.setBackground(table.getBackground());
        	this.setForeground(table.getForeground());
        	imageField.setBgcolor(table.getBackground());
		}
		
		return this;
	}
}
