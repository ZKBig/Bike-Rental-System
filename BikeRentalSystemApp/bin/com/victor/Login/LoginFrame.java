package com.victor.Login;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;	

import com.google.gson.Gson;


import com.victor.BikeDisplayPanel.WaitDialog;

import com.victor.CustomControl.Borders;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomControl.CustomShortTask;
import com.victor.CustomControl.ImageView;
import com.victor.CustomControl.RoundIcon;
import com.victor.Register.RegisterDialog;
import com.victor.mainFrame.MainFrame;
import com.victor.CustomLayout.ComponentItem;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomFlexibleLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.*;


/**
 * 
 * @Description create the login frame
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月18日下午4:11:19
 *
 */

public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private CustomButton registerButton = new CustomButton("Register");
	public static List<UserInfo> itemList = new ArrayList<UserInfo>();
	private JLabel closeButton = new JLabel();
	private CustomButton button;
	private JPasswordField passWordField;
	private JTextField textField;
	public static UserInfo user;
	private LoginFrame frame;
	private WaitDialog waitDialog;
	
	public LoginFrame(String title){
		super(title);
		this.setBackground(Color.white);
		this.setUndecorated(true);
		this.setResizable(true);
		this.setSize(430, 330);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame = this;
		this.waitDialog = new WaitDialog(this);
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
		root.addLayers(initialRegisterButton(), 2, new ComponentItem(-1, 0, 0, -1));
		root.addLayers(initialRoundImage(), 2, new ComponentItem(100, -1, -1, -1));
		root.addLayers(initialLogin(), 2, new ComponentItem(180, -1, -1, -1));
	}
	
	public static void setItemList(List<UserInfo> itemList) {
		LoginFrame.itemList = itemList;
	}


	/**
	 * 
	 * @Description initial the title of the window
	 * @author Victor
	 * @date 2020年11月18日下午8:28:092020年11月18日
	 *
	 */
	public Component initialTitle() {
		JLabel label = new JLabel("Bicycle Rental System");
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setForeground(Color.white);
		Borders.setPadding(label, 5, 10, 0, 0);
		
		return label;
	}
	
	/**
	 * 
	 * @Description initial close button
	 * @author Victor
	 * @date 2020年11月18日下午8:31:322020年11月18日
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
	 * @Description initial register button
	 * @author Victor
	 * @date 2020年11月18日下午8:46:572020年11月18日
	 *
	 */
	private Component initialRegisterButton() {
		registerButton.getNormalState().setWordsColor(new Color(0x606060));
		registerButton.getNormalState().setBackgroundColor(Color.white);
		registerButton.getNormalState().setBorderColor(null);
		registerButton.getNormalState().setBorderWidth(0);
		
		registerButton.getPressedState().setWordsColor(new Color(0x8470FF));
		registerButton.getPressedState().setBackgroundColor(Color.white);
		registerButton.getPressedState().setBorderColor(null);
		registerButton.getPressedState().setBorderWidth(0);
		
		registerButton.addActionListener((e)->{
			showRegisterDialog();
		});
		return registerButton;
	}
	
	/**
	 * 
	 * @Description display the registration dialog
	 * @author Victor
	 * @date 2020年11月20日上午11:26:392020年11月20日
	 *
	 */
	private void showRegisterDialog() {
		RegisterDialog dialog = new RegisterDialog(this);
		if(dialog.execut()) {
			UserInfo user = dialog.getInfo();
			itemList.add(user);
			saveData();
		}	
	}

	/**
	 * 
	 * @Description save the data to Json file
	 * @author Victor
	 * @date 2020年11月20日下午5:39:062020年11月20日
	 *
	 */
	public void saveData() {
		JSONArray array = new JSONArray();
		Gson gson = new Gson();
		
		for(int i=0; i<itemList.size(); i++) {
			UserInfo user = itemList.get(i);
			JSONObject object = new JSONObject();
			object.put("name", user.getName());
			object.put("sex", user.isSex());
			object.put("email", user.getEmail());
			object.put("cellPhone", user.getCellPhone());
			object.put("password", user.getPassword());
			object.put("bikes", user.getBikes());
			object.put("imageName", user.getImageName());
			object.put("birthday", user.getBirthday());
			object.put("individualResume",user.getIndividualResume());
			object.put("money", user.getMoney());
			array.put(object);
		}
		
		File file = new File("User_Information.json");
		try {
			Util.WriteToFile(array, file, "UTF-8");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description set the background image
	 * @author Victor
	 * @date 2020年11月18日下午8:54:412020年11月18日
	 *
	 */
	public Component initialBackgroundImage(){
		ImageView image = new ImageView();
		image.setImage(Util.loadImage("/com/victor/Images/Image1.jpg"));
		image.setPreferredSize(new Dimension(0, 130));
		image.setSclaerType(ImageView.FIT_XY);
		
		return image;
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020年11月18日下午9:05:042020年11月18日
	 *
	 */
	private Component initialRoundImage() {
		String name = loadImageName();
//		String path = getPath();
		RoundIcon icon = new RoundIcon(Util.loadImage("/com/victor/UserImages/"+name));
		icon.setPreferredSize(new Dimension(60,60));
		return icon;
	}
	
	/**
	 * 
	 * @Description get the file path both in the Eclipse situation and jar package situation
	 * @author Victor
	 * @date 2020年12月19日下午2:07:012020年12月19日
	 *
	 */
	private static String getPath() {
		String filePath = System.getProperty("java.class.path");
		String pathSplit = System.getProperty("path.separator");
		
		if(filePath.contains(pathSplit)) {
			filePath = filePath.substring(0, filePath.indexOf(pathSplit));
		}else if(filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		}
		
		return filePath;
	}
	
	private String loadImageName(){
		String read;
		String text = "";
		FileReader fr;
		BufferedReader buffer;
		try {
			File file = new File("imageName.txt");
			try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fr = new FileReader(file);
			buffer = new BufferedReader(fr);
			try {
				while((read = buffer.readLine())!=null) {
					text = text + read;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 
	 * @Description initial login panel
	 * @author Victor
	 * @throws IOException 
	 * @date 2020年11月18日下午9:05:272020年11月18日
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
	 * @date 2020年11月18日下午9:14:262020年11月18日
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
	 * @date 2020年11月18日下午9:14:232020年11月18日
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
	 * @date 2020年11月18日下午9:14:192020年11月18日
	 *
	 */
	private void initialThirdLine() {
		button = new CustomButton("LOG IN");
		button.getNormalState().setBackgroundColor(new Color(0x02B8FA));
		button.getNormalState().setWordsColor(new Color(0xFFFFFF));
		registerButton.getNormalState().setBorderColor(Color.white);
		registerButton.getNormalState().setBorderWidth(0);
		button.setButtonMargin(0);
		button.setButtonPadding(0);
		
		button.getPressedState().setBackgroundColor(new Color(0x00aeef));
		button.getPressedState().setWordsColor(null);
		registerButton.getPressedState().setBorderColor(Color.white);
		registerButton.getPressedState().setBorderWidth(0);
		button.setLength(8);
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020年11月19日下午5:05:582020年11月19日
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
			
			waitDialog.execute();
		}
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020年11月20日下午5:23:272020年11月20日
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
					user = itemList.get(i);
					saveImageName(user.getImageName());
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
	 * @date 2020年11月20日下午5:46:442020年11月20日
	 *
	 */
	public void loadUserData() {
		File file = new File("User_Information.json");
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
		
		itemList.clear();
		for(int i=0; i<array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			UserInfo user = new UserInfo();
			JSONArray array1;
			user.setName(object.getString("name"));
			user.setCellPhone(object.getString("cellPhone"));
			user.setEmail(object.getString("email"));
			user.setPassword(object.getString("password"));
			user.setSex(object.getBoolean("sex"));
			user.setImageName(object.getString("imageName"));
			user.setBirthday(object.getString("birthday"));
			user.setIndividualResume(object.getString("individualResume"));
			user.setMoney(object.getString("money"));
			if (object.getJSONArray("bikes")!=null) {
				array1 = object.getJSONArray("bikes");
				BikeInfo[] bikeItems = new BikeInfo[array1.length()];
				for (int j = 0; j < array1.length(); j++) {
					JSONObject object1 = array1.getJSONObject(j);
					if (object1.getString("rentType").equals("Hourly")) {
						HourlyBikeInfo bike = new HourlyBikeInfo();
						bike.setCompany(object1.getString("company"));
						bike.setID(object1.getString("ID"));
						bike.setYears(object1.getString("years"));
						bike.setStatus(object1.getBoolean("status"));
						bike.setRegion(object1.getString("region"));
						bike.setYear(object1.getString("year"));
						bike.setMonth(object1.getString("month"));
						bike.setDay(object1.getString("day"));
						bike.setStartHour(object1.getString("startHour"));
						bike.setEndHour(object1.getString("endHour"));
						bike.setRentType(object1.getString("rentType"));
						bikeItems[j] = bike;
					} else if (object1.getString("rentType").equals("Daily")) {
						DailyBikeInfo bike = new DailyBikeInfo();
						bike.setCompany(object1.getString("company"));
						bike.setID(object1.getString("ID"));
						bike.setYears(object1.getString("years"));
						bike.setStatus(object1.getBoolean("status"));
						bike.setRegion(object1.getString("region"));
						bike.setYear(object1.getString("year"));
						bike.setYear1(object1.getString("year1"));
						bike.setMonth(object1.getString("month"));
						bike.setMonth1(object1.getString("month1"));
						bike.setDay(object1.getString("day"));
						bike.setDay1(object1.getString("day1"));
						bike.setRentType(object1.getString("rentType"));
						bikeItems[j] = bike;
					} else if (object1.getString("rentType").equals("Weekly")) {
						WeeklyBikeInfo bike = new WeeklyBikeInfo();
						bike.setCompany(object1.getString("company"));
						bike.setID(object1.getString("ID"));
						bike.setYears(object1.getString("years"));
						bike.setStatus(object1.getBoolean("status"));
						bike.setRegion(object1.getString("region"));
						bike.setYear(object1.getString("year"));
						bike.setYear1(object1.getString("year1"));
						bike.setMonth(object1.getString("month"));
						bike.setMonth1(object1.getString("month1"));
						bike.setDay(object1.getString("day"));
						bike.setDay1(object1.getString("day1"));
						bike.setRentType(object1.getString("rentType"));
						bikeItems[j] = bike;
					}
				}
				user.setBikes(bikeItems);
			} else {
				user.setBikes(null);
			}
			itemList.add(user);
		}
	}

	/**
	 * 
	 * @Description
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020年11月19日下午5:13:13
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
			LoginFrame.this.setVisible(false);
			
			MainFrame mainFrame = new MainFrame("Bicycle Rental System", frame);
			mainFrame.setSize(930, 650);
			//set the main frame displayed in the middle of the screen
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width-mainFrame.getWidth())/2;
			int y = (screenSize.height-mainFrame.getHeight())/2;
			mainFrame.setLocation(x, y);
			mainFrame.setVisible(true);
		}

		@Override
		protected void done() {
			if(waitDialog!=null) {
				waitDialog.setVisible(false);
				waitDialog = null;
			}
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020年11月18日下午8:11:02
	 *
	 */
	private class ImageButton extends ImageView{

		private ActionListener aL;
		@Override
		protected void processMouseEvent(MouseEvent e) {
			int ID = e.getID();
			if(ID == 501 && this.aL!=null) {
				ActionEvent event = new ActionEvent(this, ID, "clicked");
				this.aL.actionPerformed(event);
			}
			super.processMouseEvent(e);
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020年11月18日下午8:21:10
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
	
	/**
	 * 
	 * @Description return the itemList 
	 * @author Victor
	 * @date 2020年11月20日下午6:29:592020年11月20日
	 *
	 */
	public static List<UserInfo> getItemList(){
		return itemList;
	}
	
	/**
	 * 
	 * @Description return the log in user 
	 * @author Victor
	 * @date 2020年11月22日下午2:49:592020年11月22日
	 *
	 */
	public static UserInfo getUser() {
		return user;
	}
	
	/**
	 * 
	 * @Description save the name of the use image
	 * @author Victor
	 * @date 2020年12月12日下午2:12:572020年12月12日
	 *
	 */
	private void saveImageName(String image1) 
	{
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("imageName.txt");
			pw.write(image1);
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
		
	}
	
}
