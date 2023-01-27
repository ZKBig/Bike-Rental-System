package com.victor.BikeReturnPanel;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomControl.ImageView;


/**
 * 
 * @Description customize the display of the column
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月5日下午3:18:15
 *
 */
public class RentalStatusColumnRenderer extends CustomPanel implements TableCellRenderer{
	private static final long serialVersionUID = -918020155676008858L;
	private ImageView imageField = new ImageView();
	private Image toBeReturned;
	private Image returned;
	private Image overdue;

	public RentalStatusColumnRenderer() {
		this.setLayout(new BorderLayout());
		this.setPadding(8);
		this.setPreferredSize(new Dimension(150, 150));
		this.add(imageField, BorderLayout.CENTER);
		

		try {
			toBeReturned = ImageIO.read(getClass().getResource("/com/victor/Images/toBePaid.png"));
			returned = ImageIO.read(getClass().getResource("/com/victor/Images/returned.png"));
			overdue = ImageIO.read(getClass().getResource("/com/victor/Images/overdue.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//set the background color of the image is transparent
		imageField.setBgcolor(new Color(255,255,255,0));
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String status = (String)value;
		if(status.equals("toBeReturned")) {
			imageField.setImage(toBeReturned);
		}else if(status.equals("returned")) {
			imageField.setImage(returned);
		}else if(status.equals("overdue")) {
			imageField.setImage(overdue);
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
