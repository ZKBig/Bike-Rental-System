package com.victor.Register;

import java.awt.*;




import java.util.List;
import java.util.*;

import javax.swing.*;


import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.*;
import com.victor.JavaBeans.*;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;



/**
 * 
 * @Description create the registration interface
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月20日上午11:12:12
 *
 */
public class RegisterDialog extends JDialog{
	private static final long serialVersionUID = 7297307983525516322L;
	private JTextField nameField = new JTextField(20);
	private JTextField emailField = new JTextField(20);
	private JTextField cellPhoneField = new JTextField(20);
	private JPasswordField passWordField = new JPasswordField(20);
	private JPasswordField passWordConfirmField = new JPasswordField(20);
	private JComboBox<String> sexField = new JComboBox<>();
	private Icon nameImage;
	private Icon emailImage;
	private Icon phoneImage;
	private Icon passwordImage;
	private Icon passwordConfirmImage;
	private Icon sexImage;
	private boolean returnValue = false;
	private JButton registerButton;
	private CustomPanel root = new CustomPanel();
	private CustomPanel mainPanel = new CustomPanel();
	private List<UserInfo> list = new ArrayList<>();
	
	public RegisterDialog(JFrame owner) {
		super(owner, "Register", true);
		this.setSize(430,440);
		this.setContentPane(root);
		root.setLayout(new CustomColumnLayout(10));
		root.setPadding(7);
	
		initialDialog();
	}

	/**
	 * 
	 * @Description initialize the dialog
	 * @author Victor
	 * @date 2020年11月20日上午11:51:532020年11月20日
	 *
	 */
	private void initialDialog() {
		root.add(mainPanel, "1w");
		mainPanel.setLayout(new CustomColumnLayout(10));
		mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0x02B8FA), 4));
		mainPanel.setPadding(10);
		
		nameImage = Util.loadIcon("/com/victor/Images/account.png");
		emailImage = Util.loadIcon("/com/victor/Images/mail.png");
		phoneImage = Util.loadIcon("/com/victor/Images/phone.png");
		passwordImage = Util.loadIcon("/com/victor/Images/password.png");
		passwordConfirmImage = Util.loadIcon("/com/victor/Images/confirm.png");
		sexImage = Util.loadIcon("/com/victor/Images/sex.png");
		
		setContent(nameImage, "38pixels", "Name", "1w", nameField);
		setComboBox(sexImage, "46pixels", "Sex", "1w", sexField);
		setContent(emailImage, "38pixels", "E-mail", "1w", emailField);
		setContent(phoneImage, "38pixels", "Cell phone", "1w", cellPhoneField);
		setContent(passwordImage, "38pixels", "Password", "1w", passWordField);
		setContent(passwordConfirmImage, "38pixels", "Confirm password", "1w", passWordConfirmField);
		
		setBottomButton();
	}

	/**
	 * 
	 * @Description set the bottom button
	 * @author Victor
	 * @date 2020年11月20日下午12:34:202020年11月20日
	 *
	 */
	private void setBottomButton() {
		CustomPanel bottomButton = new CustomPanel();
		int width = bottomButton.getWidth();
		root.add(bottomButton, "40pixels");
		bottomButton.setLayout(new BorderLayout());
		registerButton = new JButton("Sign up");
		registerButton.setBackground(new Color(0x02B8FA));
		registerButton.setForeground(new Color(0x02B8FA));
		registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
		registerButton.setBounds((width-bottomButton.getWidth())/2, 0, 600, 400);
		bottomButton.add(registerButton);
		
		registerButton.addActionListener((e)->{
			if(checkIsValid()) {
				returnValue = true;
				setVisible(false);
			}
		});
	}

	

	/**
	 * 
	 * @Description check whether the input is correct
	 * @author Victor
	 * @date 2020年11月20日下午1:31:592020年11月20日
	 *
	 */
	private boolean checkIsValid() {
		UserInfo user = getInfo();
		list = LoginFrame.getItemList();
		if(user.getName().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Name cannot be empty");
			return false;
		}
		if(!user.getEmail().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
			JOptionPane.showMessageDialog(this, "Please enter the correct format for email");
			return false;
		}
		if(user.getPassword().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Password cannot be empty");
			return false;
		}
		
		if(!(String.valueOf(passWordField.getPassword()).equals(String.valueOf(passWordConfirmField.getPassword())))){
			JOptionPane.showMessageDialog(this, "Two input password must be consistent");
			return false;
		}
		
		if(user.getPassword().length()<6) {
			JOptionPane.showMessageDialog(this, "password is at least 6 digits");
			return false;
		}
		
		for(int i =0; i<list.size(); i++) {
			if(list.get(i).getName().equals(user.getName())) {
				JOptionPane.showMessageDialog(this, "The user name exists");
				return false;
			}
			if(list.get(i).getPassword().equals(user.getPassword())) {
				JOptionPane.showMessageDialog(this, "The password exists");
				return false;
			}	
		}
		return true;
	}
	
	/**
	 * 
	 * @Description get the information of the user
	 * @author Victor
	 * @date 2020年11月20日下午1:35:392020年11月20日
	 *
	 */
	public UserInfo getInfo() {
		UserInfo user = new UserInfo();
		DailyBikeInfo bike = new DailyBikeInfo();
		bike.setCompany("haha");
		bike.setID("1234567");
		bike.setRegion("Peking");
		bike.setStatus(true);
		bike.setYears("5");
		bike.setRentType("Daily");
		bike.setDay("1");
		bike.setDay1("2");
		bike.setMonth("1");
		bike.setMonth1("2");
		bike.setYear("1");
		bike.setYear1("2");
		BikeInfo[] bikes = new BikeInfo[]{bike};
		user.setName(nameField.getText().trim());
		user.setEmail(emailField.getText().trim());
		user.setSex(sexField.getSelectedIndex()==1);
		user.setCellPhone(cellPhoneField.getText().trim());
		user.setPassword(String.valueOf(passWordField.getPassword()).trim());
		user.setBikes(bikes);
		user.setBirthday("null");
		user.setIndividualResume("null");
		return user;
	}

	/**
	 * 
	 * @Description set the content of the comboBox
	 * @author Victor
	 * @date 2020年11月20日下午12:29:242020年11月20日
	 *
	 */
	private void setComboBox(Icon sexImage2, String string, String string2, String string3,
			JComboBox<String> sexField2) {
		CustomPanel row = new CustomPanel();
		mainPanel.add(row, "40pixels");
		row.setLayout(new CustomRowLayout());
		JLabel icon = new JLabel();
		icon.setIcon(sexImage2);
		row.add(icon, string);
		row.add(new JLabel("Sex"), "125pixels");
		row.add(sexField2, "1w");
		sexField2.addItem("Female");
		sexField2.addItem("Male");
	}

	/**
	 * 
	 * @Description set the content of the text field
	 * @author Victor
	 * @date 2020年11月20日下午12:10:352020年11月20日
	 *
	 */
	private void setContent(Icon Image, String size1, String labelName, String size2, JTextField textField) {
		CustomPanel rowPanel = new CustomPanel();
		mainPanel.add(rowPanel, "40pixels");
		rowPanel.setLayout(new CustomRowLayout(10));
		JLabel icon = new JLabel();
		icon.setIcon(Image);
		rowPanel.add(icon, size1);
		rowPanel.add(new JLabel(labelName), "120pixels");
		rowPanel.add(textField, size2);
	}

	/**
	 * 
	 * @Description let the owner judge whether the dialog should be displayed
	 * @author Victor
	 * @date 2020年11月20日下午1:37:562020年11月20日
	 *
	 */
	public boolean execut() {
		Rectangle frameRect = this.getOwner().getBounds();
		int width = this.getWidth();
		int height = this.getHeight();
		int x = frameRect.x + (frameRect.width - width)/2;
		int y = frameRect.y + (frameRect.height - height)/2;
		this.setBounds(x,y, width, height);
		
		this.setVisible(true);
		
		return returnValue;
	}
}
