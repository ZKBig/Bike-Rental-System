package com.victor.AdministratorSystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import com.victor.CustomControl.CustomShortTask;
import com.victor.CustomControl.Borders;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomControl.ImageView;
import com.victor.CustomLayout.ComponentItem;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomFlexibleLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.Administrator;
import com.victor.Login.CustomButton;
import com.victor.Login.FrameAdapter;
import com.victor.Login.Util;

/**
 * 
 * @Description the login frame of the administrator
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020骞�12鏈�11鏃ヤ笅鍗�2:12:10
 *
 */
public class AdministratorLoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static List<Administrator > itemList = new ArrayList<Administrator >();
	private JLabel closeButton = new JLabel();
	private CustomButton button;
	private JPasswordField passWordField;
	private JTextField textField;
	
	public AdministratorLoginFrame(String title){
		super(title);
		this.setBackground(Color.white);
		this.setUndecorated(true);
		this.setResizable(true);
		this.setSize(430, 330);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadUserData();
		
		
		LayeredPane root = new LayeredPane();
		this.setContentPane(root);
		this.getContentPane().setBackground(Color.white);
		root.setLayout(new CustomFlexibleLayout());
		new FrameAdapter(root);
		
		root.setBorder(BorderFactory.createLineBorder(Color.gray));
		root.setOpaque(true);
		root.setBackground(Color.white);
		root.addLayers(initialBackgroundImage(), 1, new ComponentItem(0, 0, -1, 0));
		root.addLayers(initialTitle(), 2, new ComponentItem(0, 0, -1, -2));
		root.addLayers(initialCloseButton(), 2, new ComponentItem(0, -1, -1, 0));
		root.addLayers(initialLogin(), 2, new ComponentItem(150, -1, -1, -1));
	}
	
	public static void setItemList(List<Administrator > itemList) {
		AdministratorLoginFrame.itemList = itemList;
	}

	/**
	 * 
	 * @Description initial the title of the window
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�8:28:092020骞�11鏈�18鏃�
	 *
	 */
	public Component initialTitle() {
		JLabel label = new JLabel("Administrator System");
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setForeground(Color.black);
		Borders.setPadding(label, 5, 10, 0, 0);
		
		return label;
	}
	
	/**
	 * 
	 * @Description initial close button
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�8:31:322020骞�11鏈�18鏃�
	 *
	 */
	public Component initialCloseButton() {
		closeButton.setIcon(Util.loadIcon("/com/victor/Images/close.png"));
		closeButton.setOpaque(false);
		closeButton.setBounds(30, 0, 28, 28);
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					dispose();
				}
			}
		});
		return closeButton;
	}

	/**
	 * 
	 * @Description set the background image
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�8:54:412020骞�11鏈�18鏃�
	 *
	 */
	public Component initialBackgroundImage(){
		ImageView image = new ImageView();
		image.setImage(Util.loadImage("/com/victor/Images/logo2.png"));
		image.setPreferredSize(new Dimension(100, 210));
		image.setSclaerType(ImageView.FIT_XY);
		
		return image;
	}
	
	/**
	 * 
	 * @Description initial login panel
	 * @author Victor
	 * @throws IOException 
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�9:05:272020骞�11鏈�18鏃�
	 *
	 */
	private Component initialLogin(){
		CustomPanel panel = new CustomPanel();
		panel.setLayout(new CustomColumnLayout(12));
		panel.setPreferredSize(new Dimension(240, 150));
		
		initialFirstLine(panel);
		initialSecondLine(panel);
		initialThirdLine();
		button.addActionListener((e)->{
			Longin();
		});
		panel.add(button, "34pixels");
		
		return panel;
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�9:14:262020骞�11鏈�18鏃�
	 *
	 */
	private void initialFirstLine(JPanel panel){
		CustomPanel row1 = new CustomPanel();
		panel.add(row1, "34pixels");
		row1.setLayout(new CustomRowLayout(4));
		ImageView iconView = new ImageView();
		iconView.setImage(Util.loadImage("/com/victor/Images/user.png"));
		row1.add(iconView, "30pixels");
		
		textField = new JTextField();
		textField.setBorder(null);
		row1.add(textField,"1w");
		textField.setFont(textField.getFont().deriveFont(16f));
		row1.setBackground(Color.WHITE);
		row1.setOpaque(true);
		row1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�9:14:232020骞�11鏈�18鏃�
	 *
	 */
	private void initialSecondLine(JPanel panel){
		CustomPanel row = new CustomPanel();
		panel.add(row, "34px");
		row.setLayout(new CustomRowLayout(4));
		ImageView iconView = new ImageView();
		iconView.setImage(Util.loadImage("/com/victor/Images/lock.png"));
		row.add(iconView,"30pixels");
		
		
		passWordField = new JPasswordField();
		passWordField.setBorder(null);
		row.add(passWordField,"1w");
		passWordField.setFont(passWordField.getFont().deriveFont(16f));
		row.setBackground(Color.WHITE);
		row.setOpaque(true);
		row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�9:14:192020骞�11鏈�18鏃�
	 *
	 */
	private void initialThirdLine() {
		button = new CustomButton("LOG IN");
		button.getNormalState().setBackgroundColor(new Color(0x02B8FA));
		button.getNormalState().setWordsColor(new Color(0xFFFFFF));
		button.setButtonMargin(0);
		button.setButtonPadding(0);
		
		button.getPressedState().setBackgroundColor(new Color(0x00aeef));
		button.getPressedState().setWordsColor(null);
		button.setLength(8);
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�19鏃ヤ笅鍗�5:05:582020骞�11鏈�19鏃�
	 *
	 */
	private void Longin() {
		String userName = textField.getText().trim();
		String passWord = String.valueOf(passWordField.getPassword());
		
		if(checkIsValid(userName, passWord)) {
			LoginThread login = new LoginThread();
			login.setUserName(userName);
			login.setPassword(passWord);
			login.start();
		}
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�20鏃ヤ笅鍗�5:23:272020骞�11鏈�20鏃�
	 *
	 */
	private boolean checkIsValid(String userName, String passWord) {
		if (userName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter the name", "Warning",JOptionPane.WARNING_MESSAGE);
			return false;
		}else if (passWord.isEmpty() || (!userName.isEmpty() && passWord.isEmpty())) {
			JOptionPane.showMessageDialog(this, "Please enter the password","Warning",JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(!userName.isEmpty() && !passWord.isEmpty()) {
			for(int i=0; i<itemList.size(); i++) {
				if(itemList.get(i).getName().equals(userName) && 
						itemList.get(i).getPassword().equals(passWord)) {
					return true;
				}else if(itemList.get(i).getName().equals(userName) && 
						!itemList.get(i).getPassword().equals(passWord)) {
					JOptionPane.showMessageDialog(this, "The password is wrong", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			JOptionPane.showMessageDialog(this, "The Account does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return rootPaneCheckingEnabled;
	}

	/**
	 * 
	 * @Description load the user information from the json file
	 * @author Victor
	 * @date 2020骞�11鏈�20鏃ヤ笅鍗�5:46:442020骞�11鏈�20鏃�
	 *
	 */
	private void loadUserData() {
		File file = new File("AdministratorAccount.json");
		JSONArray array = null;
		if(!file.exists()) {
			return;
		}
		try {
			array = (JSONArray)Util.readFromFile(file, "UTF-8");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
			return;
		}
		for(int i=0; i<array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			Administrator administrator = new  Administrator();
			administrator.setName(object.getString("name"));
			administrator.setPassword(object.getString("password"));
			itemList.add(administrator);
		}
	}

	/**
	 * 
	 * @Description
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020骞�11鏈�19鏃ヤ笅鍗�5:13:13
	 *
	 */
	public class LoginThread extends CustomShortTask{
		private String userName;
		private String password;
		
		public LoginThread() {
			super();
		}

		public String getUserName() {
			return userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}
		
		@Override
		protected void doInBackground() throws Exception {
			AdministratorLoginFrame.this.setVisible(false);
			
			ManagementFrame managementFrame = new ManagementFrame("Administrator System");
			managementFrame.setSize(990, 700);
			//set the main frame displayed in the middle of the screen
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width-managementFrame.getWidth())/2;
			int y = (screenSize.height-managementFrame.getHeight())/2;
			managementFrame.setLocation(x, y);
			managementFrame.setVisible(true);
		}

		@Override
		protected void done() {
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020骞�11鏈�18鏃ヤ笅鍗�8:21:10
	 *
	 */
	private class LayeredPane extends JLayeredPane{
		private static final long serialVersionUID = -4005107864550466590L;

		public LayeredPane() {
			this.setLayout(new CustomFlexibleLayout());
		}
		
		public void addLayers(Component component, int layer, ComponentItem componentItem) {
			super.setLayer(component, layer);
			super.add(component, componentItem);
		}
	}
}
