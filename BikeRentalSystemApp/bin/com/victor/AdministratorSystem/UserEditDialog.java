package com.victor.AdministratorSystem;

import java.awt.Rectangle;

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
import com.victor.JavaBeans.UserInfo;

/**
 * 
 * @Description construct dialog to edit the information of the user
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月11日下午7:38:10
 *
 */
public class UserEditDialog extends JDialog{

	private static final long serialVersionUID = -1257298515957655088L;
	private JTextField name = new JTextField(20);
	private JTextField email = new JTextField(20);
	private JTextField cellphone = new JTextField(20);
	private JTextField money = new JTextField(20);
	private JTextField password = new JTextField(20);
	private JComboBox<String> sex = new JComboBox<>();
	private boolean retValue = false;
	private JButton button = new JButton("Add");
	private CustomPanel root = new CustomPanel();
	private CustomPanel  mainPanel = new CustomPanel ();
	private String userName;
	private UserInfo User;
	
	public UserEditDialog (JFrame owner, UserInfo User) {
		super(owner, "Add bike item", true);
		this.setSize(400, 300);
		this.setContentPane(root);
		this.userName = User.getName();
		this.User = User;
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
		
		setContent("Name", "1w",name);
		setContent("Cellphone", "1w", cellphone);
		
		CustomPanel row = new CustomPanel();
		mainPanel.add(row, "24pixels");
		row.setLayout(new CustomRowLayout());
		row.add(new JLabel("Sex"), "125pixels");
		row.add(sex, "1w");
		sex.addItem("Male");
		sex.addItem("Female");
		
		setContent("Email", "1w", email);
		setContent("Password", "1w", password);
		setContent("Purse Money", "1w", money);
		
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
		UserInfo user = getValue();
		if(user.getName().isEmpty()) {
			JOptionPane.showMessageDialog(this, "User name cannot be empty!");
			return false;
		}
		if(user.getPassword().isEmpty()) {
			JOptionPane.showMessageDialog(this, "password connot be empty!");
			return false;
		}
		if(!user.getName().equals(userName)) {
			JOptionPane.showMessageDialog(this, "User name must be "+ user.getName());
			return false;
		}
		if(!isNumeric(user.getMoney())) {
			JOptionPane.showMessageDialog(this, "Please enter the correct format of number");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Description judge whether the input string only has digits
	 * @author Victor
	 * @date 2020年12月12日上午9:58:002020年12月12日
	 *
	 */
	private static boolean isNumeric(String str) {
		for(int i = str.length(); --i>=0; ) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public void setValue(UserInfo user)
	{
		name.setText(user.getName());
		sex.setSelectedIndex(user.isSex() ? 0: 1);  
		cellphone.setText(user.getCellPhone());
		email.setText(user.getEmail());
		password.setText(user.getPassword());
		money.setText(user.getMoney());
	}
	
	public UserInfo getValue() {
		User.setName(name.getText().trim());
		User.setCellPhone(cellphone.getText().trim());
		User.setSex(sex.getSelectedIndex()==0);
		User.setEmail(email.getText().trim());
		User.setPassword(password.getText().trim());
		User.setMoney(money.getText().trim());
		return User;
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
