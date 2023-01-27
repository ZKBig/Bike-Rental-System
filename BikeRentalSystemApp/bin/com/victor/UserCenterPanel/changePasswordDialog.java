package com.victor.UserCenterPanel;

import java.awt.Color;




import java.util.*;
import javax.swing.*;


import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.*;
import com.victor.JavaBeans.*;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;

/**
 * 
 * @Description the change password dialog
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月3日下午3:53:57
 *
 */
public class changePasswordDialog extends JDialog{
	private static final long serialVersionUID = 1453298114764724705L;
	private JLabel label1 = new JLabel("Old Password");
	private JLabel label2 = new JLabel("New Password");
	private JLabel label3 = new JLabel("Repeat New Password");
	private JPasswordField oldPassWordField;
	private JPasswordField newPassWordField;
	private JPasswordField repeatNewPassWordField;
	private JButton confirmButton;
	private JButton cancelButton;
	private CustomPanel root = new CustomPanel();
	private UserInfo user;
	private LoginFrame frame1;
	
	public changePasswordDialog(JFrame frame, UserInfo user, LoginFrame frame1) {
		super(frame, "Change Passsword", true);
		this.frame1 = frame1;
		this.user = user;
		this.setSize(450,210);
		this.setContentPane(root);
		this.setResizable(false);
		root.setLayout(new CustomColumnLayout(10));
		root.setPadding(7);
		this.oldPassWordField = new JPasswordField(30);
		this.newPassWordField = new JPasswordField(30);
		this.repeatNewPassWordField = new JPasswordField(30);
		
		
		initialDialog();
	}

	/**
	 * 
	 * @Description initial the password change dialog content
	 * @author Victor
	 * @date 2020年12月3日下午4:02:242020年12月3日
	 *
	 */
	private void initialDialog() {
		root.setBorder(BorderFactory.createLineBorder(new Color(0x02B8FA), 4));
		root.setPadding(10);
		
		setContent(label1, oldPassWordField);
		setContent(label2, newPassWordField);
		setContent(label3, repeatNewPassWordField);
		
		setBottomButtons();
	}

	/**
	 * 
	 * @Description initial the bottom buttons
	 * @author Victor
	 * @date 2020年12月3日下午4:11:112020年12月3日
	 *
	 */
	private void setBottomButtons() {
		confirmButton = new JButton("Confirm");
		cancelButton = new JButton("Cancel");
		
		CustomPanel rowPanel = new CustomPanel();
		rowPanel.setPreferrenHeight(50);
		root.add(rowPanel, "30pixels");
		JLabel label11 = new JLabel();
		label11.setBackground(Color.white);
		JLabel label22 = new JLabel();
		label22.setBackground(Color.white);
		JLabel label33 = new JLabel();
		label33.setBackground(Color.white);
		
		rowPanel.add(label11, "10%");
		rowPanel.add(confirmButton, "20%");
		rowPanel.add(label22, "40%");
		rowPanel.add(cancelButton, "20%");
		rowPanel.add(label33, "10%");
		
		
		cancelButton.addActionListener((e)->{
			this.dispose();
		});
		
		confirmButton.addActionListener((e)->{
			if (checkIsValid()) {
				List<UserInfo> list = frame1.itemList;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getName().equals(user.getName())) {
						list.get(i).setPassword(String.valueOf(newPassWordField.getPassword()));
					}
				}
				frame1.saveData();
				JOptionPane.showMessageDialog(this, "Successfully change!");
				this.setVisible(false);
			}
		});
	}
	
	/**
	 * 
	 * @Description check whether the input is valid
	 * @author Victor
	 * @date 2020年12月17日下午1:58:272020年12月17日
	 *
	 */
	public boolean checkIsValid() {
		if(String.valueOf(oldPassWordField.getPassword()).trim()==null) {
			JOptionPane.showMessageDialog(this, "Please enter the old password");
			return false;
		}
		if(newPassWordField.getPassword()==null) {
			JOptionPane.showMessageDialog(this, "Please enter the new password");
			return false;
		}
		if(newPassWordField.getPassword()!=null && repeatNewPassWordField.getPassword()==null) {
			JOptionPane.showMessageDialog(this, "Please repeat the new password");
			return false;
		}
		if(!(String.valueOf(newPassWordField.getPassword()).trim().equals(String.valueOf(repeatNewPassWordField.getPassword()).trim()))) {
			JOptionPane.showMessageDialog(this, "The repeat new password is not the same as the new password");
			return false;
		}
		if(!(String.valueOf(oldPassWordField.getPassword()).trim().equals(user.getPassword().trim()))) {
			System.out.print(new String(oldPassWordField.getPassword()).trim());
			System.out.print(user.getPassword());
			JOptionPane.showMessageDialog(this, "The old password is wrong");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Description set the content of the change password panel
	 * @author Victor
	 * @date 2020年12月3日下午4:06:562020年12月3日
	 *
	 */
	private void setContent(JLabel label, JPasswordField passWordField) {
		passWordField = new JPasswordField();
		passWordField.setBorder(null);
		passWordField.setFont(passWordField.getFont().deriveFont(16f));
		CustomPanel rowPanel = new CustomPanel();
		root.add(rowPanel, "30pixels");
		rowPanel.setLayout(new CustomRowLayout(10));
		rowPanel.add(label, "160pixels");
		rowPanel.add(passWordField, "300pixels");
	}
	

}
