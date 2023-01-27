package com.victor.AdministratorSystem;

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
 * @Description construct the column renderer to display icon according to the sex
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月11日下午7:01:39
 *
 */
public class sexColumnRenderer extends CustomPanel implements TableCellRenderer {
	private static final long serialVersionUID = 6842188105775724620L;
	private ImageView imageField = new ImageView();
	private Image male;
	private Image female;
	
	public sexColumnRenderer() {
		this.setLayout(new BorderLayout());
		this.setPadding(8);
		this.setPreferredSize(new Dimension(150,150));
		this.add(imageField, BorderLayout.CENTER);
		
		try {
			male = ImageIO.read(getClass().getResource("/com/victor/Images/male.png"));
			female = ImageIO.read(getClass().getResource("/com/victor/Images/female.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//set the background color of the image is transparent
		imageField.setBgcolor(new Color(255,255,255,0));
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Boolean sex = (Boolean)value;
		if(sex !=null && sex == true) {
			imageField.setImage(male);
		}else {
			imageField.setImage(female);
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
