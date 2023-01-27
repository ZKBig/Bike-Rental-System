package com.victor.UserCenterPanel;

import java.awt.BorderLayout;



import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.*;
import com.victor.JavaBeans.UserInfo;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;



/**
 * 
 * @Description set the user information dialog
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月4日下午8:58:54
 *
 */
public class ModifyDialog extends JDialog{

	private static final long serialVersionUID = 5534502350054428839L;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField cellPhoneField;
	private JTextArea individualResume = new JTextArea();
	private JComboBox<String> year = new JComboBox<>();
	private JComboBox<String> month = new JComboBox<>();
	private JComboBox<String> day = new JComboBox<>();
 	private JComboBox<String> sexField = new JComboBox<>();
	private Icon nameImage;
	private Icon emailImage;
	private Icon phoneImage;
	private Icon sexImage;
	private Icon birthdayImage;
	private Icon resumeImage;
	private JButton registerButton;
	private boolean returnValue = false;
	private CustomPanel root = new CustomPanel();
	private CustomPanel mainPanel = new CustomPanel();
	private List<UserInfo> list = new ArrayList<>();
	private UserInfo user;
	private String name;
	
	public ModifyDialog(JFrame owner, UserInfo user) {
		super(owner, "Register", true);
		this.user = user;
		this.setSize(530,460);
		this.setContentPane(root);
		root.setLayout(new CustomColumnLayout(10));
		root.setPadding(7);
		name = user.getName();
		
		nameField = new JTextField(user.getName());
		emailField = new JTextField(user.getEmail());
		cellPhoneField = new JTextField(user.getCellPhone());
		if(!user.getIndividualResume().equals("null")) {
			individualResume = new JTextArea(user.getIndividualResume());
		}
		initialDialog();
		
		if(user.isSex()) {
			sexField.setSelectedIndex(1);;
		}else {
			sexField.setSelectedIndex(0);;
		}
		
		if(!user.getBirthday().equals("null")) {
			String[] birthday = user.getBirthday().split("/");
//			System.out.println(Arrays.toString(birthday));
			int count1=0, count2=0, count3=0, j =1970;
			for(; count1<50; count1++) {
				if(String.valueOf(j+count1).equals(birthday[0])) {
					break;
				}
			}
			year.setSelectedIndex(count1);;
			for(; count2<12; count2++) {
				if(String.valueOf(count2+1).equals(birthday[1])) {
					break;
				}
			}
			month.setSelectedIndex(count2);
			for(; count3<31; count3++) {
				if(String.valueOf(count3+1).equals(birthday[2])) {
					break;
				}
			}
			day.setSelectedIndex(count3);;
		}
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
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		mainPanel.setPadding(10);
		
		nameImage = Util.loadIcon("/com/victor/Images/account.png");
		emailImage = Util.loadIcon("/com/victor/Images/mail.png");
		phoneImage = Util.loadIcon("/com/victor/Images/phone.png");
		sexImage = Util.loadIcon("/com/victor/Images/sex.png");
		birthdayImage = Util.loadIcon("/com/victor/Images/birthday.png");
		resumeImage = Util.loadIcon("/com/victor/Images/resume.png");
		
		setContent(nameImage, "30pixels", "Name", "1w", nameField);
		setComboBox(sexImage, "38pixels", "Sex", "30pixels", sexField);
		setContent(emailImage, "30pixels", "E-mail", "1w", emailField);
		setContent(phoneImage, "30pixels", "Cell phone", "1w", cellPhoneField);
		
		setBirthday();
		setIndividualResume();
		setBottomButton();
	}

	/**
	 * 
	 * @Description set the birthday row
	 * @author Victor
	 * @date 2020年12月4日下午8:25:182020年12月4日
	 *
	 */
	private void setBirthday() {
		CustomPanel rowPanel = new CustomPanel();
		mainPanel.add(rowPanel, "40pixels");
		rowPanel.setLayout(new CustomRowLayout(10));
		JLabel icon = new JLabel();
		icon.setIcon(birthdayImage);
		rowPanel.add(icon, "30pixels");
		rowPanel.add(new JLabel("Birthday"), "70pixels");
		rowPanel.add(year, "100pixels");
		rowPanel.add(month, "65pixels");
		rowPanel.add(day, "80pixels");
		
		for(int i=0; i<51; i++) {
			year.addItem(String.valueOf(1970+i));
		}
		
		for(int i=0 ; i<12; i++) {
			month.addItem(String.valueOf(i+1));
		}
		
		int dayNum = 0;
		int month1 = Integer.parseInt((String)month.getSelectedItem());
		int year2 = Integer.parseInt((String)year.getSelectedItem());
		if (month1 == 1 || month1 == 3 || month1 == 5 || month1== 7 || month1 == 8 || month1 == 10 || month1 == 12)
			dayNum = 31;
		if (month1 == 4 || month1 == 6 || month1 == 9 || month1 == 11)
			dayNum = 30;
		if (month1 == 2) {
			if (((year2 % 4 == 0) && (year2 % 100 != 0)) || (year2 % 400 == 0))
				dayNum = 29;
			else
				dayNum = 28;
		}
		
		for(int i=0 ;i<dayNum; i++) {
			day.addItem(String.valueOf(i+1));
		}
	}
	
	/**
	 * 
	 * @Description set the individual resume row
	 * @author Victor
	 * @date 2020年12月4日下午8:25:212020年12月4日
	 *
	 */
	private void setIndividualResume() {
		CustomPanel rowPanel = new CustomPanel();
		mainPanel.add(rowPanel, "40pixels");
		rowPanel.setLayout(new CustomRowLayout(10));
		JLabel icon = new JLabel();
		icon.setIcon(resumeImage);
		rowPanel.add(icon, "30pixels");
		rowPanel.add(new JLabel("resume"), "70pixels");
		rowPanel.add(individualResume, "1w");
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
		root.add(bottomButton, "40pixels");
		bottomButton.setLayout(new BorderLayout());
		registerButton = new JButton("Confirm");
		registerButton.setBackground(new Color(0x02B8FA));
		registerButton.setForeground(new Color(0x02B8FA));
		registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
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
		
		for(int i =0; i<list.size(); i++) {
			if(list.get(i).getName().equals(user.getName()) && !user.getName().equals(name)) {
				JOptionPane.showMessageDialog(this, "The user name exists");
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
		user.setName(nameField.getText().trim());
		user.setEmail(emailField.getText().trim());
		user.setSex(sexField.getSelectedIndex()==1);
		user.setCellPhone(cellPhoneField.getText().trim());
		user.setIndividualResume(individualResume.getText());
		if(year.getSelectedItem()!=null) {
			user.setBirthday((String)year.getSelectedItem()+"/"+(String)month.getSelectedItem()+"/"+(String)day.getSelectedItem());
		}else {
			user.setBirthday("null");
		}
		if(individualResume.getText()!=null) {
			user.setIndividualResume(individualResume.getText());
		}else {
			user.setIndividualResume("null");
		}
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
		row.add(new JLabel("Sex"), "75pixels");
		row.add(sexField2, "130pixels");
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
		rowPanel.add(new JLabel(labelName), "70pixels");
		rowPanel.add(textField, size2);
	}

	/**
	 * 
	 * @Description let the owner judge whether the dialog should be displayed
	 * @author Victor
	 * @date 2020年11月20日下午1:37:562020年11月20日
	 *
	 */
	public boolean execute() {
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
