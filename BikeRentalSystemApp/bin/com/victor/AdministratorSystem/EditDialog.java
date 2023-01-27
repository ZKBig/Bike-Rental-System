package com.victor.AdministratorSystem;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.BikeInfo;

/**
 * 
 * @Description create the dialog to modify the bike item
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月11日下午5:54:23
 *
 */
public class EditDialog extends JDialog{

	private static final long serialVersionUID = -1940796365944768310L;
	private  List<BikeInfo> bikeList = new ArrayList<>();
	private JTextField company = new JTextField(20);
	private JTextField ID = new JTextField(20);
	private JTextField years = new JTextField(20);
	private JTextField region = new JTextField(20);
	private JComboBox<String> status = new JComboBox<>();
	private boolean retValue = false;
	private JButton button = new JButton("Add");
	private CustomPanel root = new CustomPanel();
	private CustomPanel  mainPanel = new CustomPanel ();
	private String bikeCompany;
	
	public EditDialog(JFrame owner, String bikeCompany) {
		super(owner, "Add bike item", true);
		this.setSize(400, 300);
		this.setContentPane(root);
		this.bikeCompany = bikeCompany;
		root.setLayout(new CustomColumnLayout(10));
		root.setPadding(10);
		
		initDialog();
		
	}
	
	/**
	 * 
	 * @Description initial the display of the dialog
	 * @author Victor
	 * @date 2020年11月11日下午8:36:592020年11月11日
	 *
	 */
	private void initDialog() {
		root.add(mainPanel, "1w");
		mainPanel.setLayout(new CustomColumnLayout(10));
		mainPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		mainPanel.setPadding(10);
		
		setContent("Company", "1w",company);
		setContent("Series Number", "1w", ID);
		
		CustomPanel row = new CustomPanel();
		mainPanel.add(row, "24pixels");
		row.setLayout(new CustomRowLayout());
		row.add(new JLabel("Status"), "135pixels");
		row.add(status, "1w");
		status.addItem("Available");
		status.addItem("Unavailable");
		
		setContent("Age", "1w", years);
		setContent("Region", "1w", region);
		
		CustomPanel bottomButton = new CustomPanel();
		root.add(bottomButton, "30pixels");
		bottomButton.setLayout(new CustomRowLayout(10));
		bottomButton.add(new JLabel(), "1w");
		bottomButton.add(button, "default");
		
		button.addActionListener((e)->{
			if(checkValid()) {
				retValue = true;
				setVisible(false);
			}
		});
	}
	
	private boolean checkValid() {
		BikeInfo bike = getValue();
		if(bike.getCompany().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bike company name cannot be empty!");
			return false;
		}
		if(bike.getID().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bike series number connot be empty!");
			return false;
		}
		if(!bike.getCompany().equals(bikeCompany)) {
			JOptionPane.showMessageDialog(this, "Bike company must be "+bikeCompany);
			return false;
		}
		return true;
	}

	public void setValue(BikeInfo bike)
	{
		company.setText(bike.getCompany());
		ID.setText(bike.getID());
		status.setSelectedIndex(bike.getStatus() ? 0: 1);  
		years.setText(bike.getYears());
		region.setText(bike.getRegion());
	}
	
	public BikeInfo getValue() {
		BikeInfo bike = new BikeInfo();
		bike.setCompany(company.getText().trim());
		bike.setID(ID.getText().trim());
		bike.setStatus(status.getSelectedIndex()==0);
		bike.setRegion(region.getText().trim());
		bike.setYears(years.getText().trim());
		
		return bike;
	}

	private void setContent(String labelName, String size, JTextField textField) {
		CustomPanel rowPanel = new CustomPanel();
		mainPanel.add(rowPanel, "24pixels");
		rowPanel.setLayout(new CustomRowLayout(10));
		rowPanel.add(new JLabel(labelName), "110pixels");
		rowPanel.add(textField, size);
	}
	
	public boolean execute() {
		Rectangle frameRect = this.getOwner().getBounds();
		int width = this.getWidth();
		int height = this.getHeight();
		int x = frameRect.x + (frameRect.width - width)/2;
		int y = frameRect.y + (frameRect.height - height)/2;
		this.setBounds(x,y, width, height);
		
		this.setVisible(true);
		
		return retValue;
	}
}
