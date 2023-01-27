package com.victor.BikeDisplayPanel;

import java.awt.Component;

import java.awt.Image;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CompanyColumnRenderer extends JCheckBox implements TableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.setSelected(isSelected);
		if(value!=null) {
			this.setText(value.toString());
		}
		this.setOpaque(true);
		if(isSelected) {
			this.setBackground(table.getSelectionBackground());
			this.setForeground(table.getSelectionForeground());
		}else {
			this.setBackground(table.getBackground());
			this.setForeground(table.getForeground());
		}
		
		return this;
	}

}
