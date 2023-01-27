package com.victor.UserCenterPanel;

import java.awt.BorderLayout;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomControl.RoundIcon;
import com.victor.Login.CustomButton;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.*;


public class UserPanel extends CustomPanel{
	
	private static final long serialVersionUID = -4734765348414425113L;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private List<BikeInfo> bikeList = new ArrayList<>();
	private List<UserInfo> userList = new ArrayList<>();
	private JTable table = null;
	private JScrollPane scrollPane;
	private CustomPanel topHalfPanel = new CustomPanel();
	private CustomPanel bottomHalfPanel = new CustomPanel();
	private CustomPanel userImagePanel = new CustomPanel();
	private CustomPanel userInfoPanel = new CustomPanel();
	private String imagePath;
	private RoundIcon image;
	private RoundIcon userImage;
	private UserInfo user;
	private JFrame frame1;
	private LoginFrame frame;
	private JLabel label3;
	
	
	public UserPanel(LoginFrame frame, JFrame frame1) {
		this.user = frame.user;
		this.frame = frame;
		this.frame1 = frame1;
		this.setBackground(Color.white);
		this.setLayout(new CustomColumnLayout());
		
		initialTopHalfPanel();
		
		initialBottomHalfPanel();
		
		loadData();

	}
	
	public JLabel getLabel3() {
		return label3;
	}

	public void setLabel3(JLabel label3) {
		this.label3 = label3;
	}



	/**
	 * 
	 * @Description update user information
	 * @author Victor
	 * @date 2020年12月12日上午10:20:532020年12月12日
	 *
	 */
	private void updateUserInfo() {
		userList = frame.itemList;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getName().equals(user.getName())) {
				System.out.println(user.getImageName());
				user = userList.get(i);
			}
		}

	}


	/**
	 * 
	 * @Description load the bike data of the user
	 * @author Victor
	 * @date 2020年12月4日上午8:51:432020年12月4日
	 *
	 */
	private void loadData() {
		for(int i =1; i<user.getBikes().length; i++) {
			if(user.getBikes()[i].getStatus()) {
				addTableRow(user.getBikes()[i]);
				addToList(user.getBikes()[i]);
			}
		}
	}

	/**
	 * 
	 * @Description add the user bike data to the list
	 * @author Victor
	 * @date 2020年12月4日上午9:34:392020年12月4日
	 *
	 */
	private void addToList(BikeInfo bikeInfo) {
		bikeList.add(bikeInfo);
		
	}

	/**
	 * 
	 * @Description add the user bike data to the table row
	 * @author Victor
	 * @date 2020年12月4日上午9:34:272020年12月4日
	 *
	 */
	private void addTableRow(BikeInfo bikeInfo) {
		Vector<Object> rowData = new Vector<>();
		if(bikeInfo instanceof DailyBikeInfo) {
			DailyBikeInfo bike = (DailyBikeInfo)bikeInfo;
			rowData.add(bike.getCompany());
			rowData.add(bike.getID());
			rowData.add(bike.getYears());
			rowData.add(bike.getRentType());
			if(Integer.parseInt(bike.getMonth1())<10) {
				if(Integer.parseInt(bike.getDay1())<10) {
					rowData.add(bike.getYear1()+"/0"+bike.getMonth1()+"/0"+bike.getDay1()+"/24:00");
				}else {
					rowData.add(bike.getYear1()+"/0"+bike.getMonth1()+"/"+bike.getDay1()+"/24:00");
				}
			}else {
				if(Integer.parseInt(bike.getDay())<10) {
					rowData.add(bike.getYear1()+"/"+bike.getMonth1()+"/0"+bike.getDay1()+"/24:00");
				}else {
					rowData.add(bike.getYear1()+"/"+bike.getMonth1()+"/"+bike.getDay1()+"/24:00");
				}	
			}
			tableModel.addRow(rowData);
		}else if(bikeInfo instanceof HourlyBikeInfo) {
			HourlyBikeInfo bike = (HourlyBikeInfo)bikeInfo;
			rowData.add(bike.getCompany());
			rowData.add(bike.getID());
			rowData.add(bike.getYears());
			rowData.add(bike.getRentType());
			if(Integer.parseInt(bike.getMonth())<10) {
				if(Integer.parseInt(bike.getDay())<10) {
						rowData.add(bike.getYear()+"/0"+bike.getMonth()+"/0"+bike.getDay()+"/"+bike.getEndHour());
				}else {
						rowData.add(bike.getYear()+"/0"+bike.getMonth()+"/"+bike.getDay()+"/"+bike.getEndHour());
				}
			}else {
				if(Integer.parseInt(bike.getDay())<10) {
					rowData.add(bike.getYear()+"/"+bike.getMonth()+"/0"+bike.getDay()+"/"+bike.getEndHour());
				}else {
					rowData.add(bike.getYear()+"/"+bike.getMonth()+"/"+bike.getDay()+"/"+bike.getEndHour());
				}	
			}
			tableModel.addRow(rowData);
		}else if(bikeInfo instanceof WeeklyBikeInfo) {
			WeeklyBikeInfo bike = (WeeklyBikeInfo)bikeInfo;
			rowData.add(bike.getCompany());
			rowData.add(bike.getID());
			rowData.add(bike.getYears());
			rowData.add(bike.getRentType());
			if(Integer.parseInt(bike.getMonth1())<10) {
				if(Integer.parseInt(bike.getDay1())<10) {
					rowData.add(bike.getYear1()+"/0"+bike.getMonth1()+"/0"+bike.getDay1()+"/24:00");
				}else {
					rowData.add(bike.getYear1()+"/0"+bike.getMonth1()+"/"+bike.getDay1()+"/24:00");
				}
			}else {
				if(Integer.parseInt(bike.getDay())<10) {
					rowData.add(bike.getYear1()+"/"+bike.getMonth1()+"/0"+bike.getDay1()+"/24:00");
				}else {
					rowData.add(bike.getYear1()+"/"+bike.getMonth1()+"/"+bike.getDay1()+"/24:00");
				}	
			}
			tableModel.addRow(rowData);
		}
	
	}


	private void initialBottomHalfPanel() {
		this.add(bottomHalfPanel, "70%");
		bottomHalfPanel.setLayout(new BorderLayout());
		bottomHalfPanel.setMargin(10);
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 3L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setRowHeight(30);
		bottomHalfPanel.add(scrollPane, BorderLayout.CENTER);
		
		tableModel.addColumn("Company");
		tableModel.addColumn("Serial number");
		tableModel.addColumn("Age");
		tableModel.addColumn("Type");
		tableModel.addColumn("Due Time");
		
		JLabel label = new JLabel("Returned bike list:");
		label.setPreferredSize(new Dimension(150, 20));
		bottomHalfPanel.add( label, BorderLayout.PAGE_START);
		
	}

	
	/**
	 * 
	 * @Description initial the top half panel of the user frame
	 * @author Victor
	 * @date 2020年12月1日下午7:54:432020年12月1日
	 *
	 */
	private void initialTopHalfPanel() {
		this.add(topHalfPanel, "28%");
		topHalfPanel.setLayout(new CustomRowLayout());
		topHalfPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		userInfoPanel.setLayout(new CustomColumnLayout());
		userImagePanel.setLayout(new BorderLayout());
		topHalfPanel.add(userImagePanel, "25%");
		topHalfPanel.add(userInfoPanel, "60%");
		userInfoPanel.setPreferredSize(new Dimension(230,100));
		
		//set the head portrait of the user
		image = new RoundIcon(Util.loadImage("/com/victor/UserImages/"+user.getImageName()));
		image.setPreferredSize(new Dimension(83, 83));
		userImagePanel.add(image, BorderLayout.PAGE_START);
		
		CustomButton loadPictureButton = new CustomButton("Upload picture");
		loadPictureButton.getNormalState().setWordsColor(Color.black);
		loadPictureButton.getNormalState().setBackgroundColor(new Color(0xF1F1F1));
		loadPictureButton.getNormalState().setBorderColor(new Color(0xD0D0D0));
		loadPictureButton.getNormalState().setBorderWidth(2);
		
		loadPictureButton.getPressedState().setWordsColor(new Color(0x8470FF));
		loadPictureButton.getPressedState().setBackgroundColor(new Color(0xF1F1F1));
		loadPictureButton.getPressedState().setBorderColor(new Color(0x8470FF));
		loadPictureButton.getPressedState().setBorderWidth(2);
		
		loadPictureButton.addActionListener((e)->{
			String image1 = selectFolderDialog();
			
			saveImageName(image1);
			
			List<UserInfo> list = frame.itemList;
			for(int i=0; i<list.size(); i++) {
				if(list.get(i).getName().equals(user.getName())) {
					list.get(i).setImageName(image1);;
				}
			}
			frame.saveData();		
			
			this.remove(image);
//			String jarPath = getPath();
			userImage = new RoundIcon(Util.loadImage("/com/victor/UserImages/"+user.getImageName()));
			userImage.setPreferredSize(new Dimension(83, 83));
			
			userImagePanel.add(userImage, BorderLayout.PAGE_START);
			userImage.setOpaque(true);
			this.validate();
			this.user.setImageName(image1);

			frame.loadUserData();
			updateUserInfo();
			frame.user = user;
		});
		
		
		
		userImagePanel.add(loadPictureButton, BorderLayout.PAGE_END);
		
		//set the user information panel
		CustomPanel panel = new CustomPanel();
		userInfoPanel.add(panel, "33%");
		panel.setBackground(new Color(0xF1F1F1));
		panel.setLayout(new CustomRowLayout(10));
		panel.setOpaque(true);

		JLabel label1 = new JLabel(" "+user.getName());
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		label1.setForeground(Color.BLACK);
		panel.add(label1, "default");
		
		JLabel label2 = new JLabel();
		if(user.isSex()) {
			label2.setIcon(Util.loadIcon("/com/victor/Images/male.png"));
		}else {
			label2.setIcon(Util.loadIcon("/com/victor/Images/female.png"));
		}
		panel.add(label2, "55pixels");
		
		
		//set the money label
		label3 = new JLabel(" Personal Purse: "+user.getMoney()+"RMB");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		label3.setForeground(Color.BLACK);
		userInfoPanel.add(label3, "33%");
		
		//set the operation panel
		CustomPanel panel1 = new CustomPanel();
		userInfoPanel.add(panel1, "33%");
		panel1.setLayout(new CustomRowLayout(10));
		panel1.setBackgroundColor(new Color(0xF1F1F1));
		
		CustomButton modifyButton = new CustomButton("Modify the information");
		modifyButton.getNormalState().setWordsColor(Color.black);
		modifyButton.getNormalState().setBackgroundColor(new Color(0xF1F1F1));
		modifyButton.getNormalState().setBorderColor(null);
		modifyButton.getNormalState().setBorderWidth(0);
		
		modifyButton.getPressedState().setWordsColor(new Color(0x8470FF));
		modifyButton.getPressedState().setBackgroundColor(new Color(0xF1F1F1));
		modifyButton.getPressedState().setBorderColor(null);
		modifyButton.getPressedState().setBorderWidth(0);
		
		modifyButton.addActionListener((e)->{
			showModifyDialog();
		});
		panel1.add(modifyButton, "150pixels");
		
		CustomButton changePasswordButton = new CustomButton("Change Password");
		changePasswordButton.getNormalState().setWordsColor(Color.black);
		changePasswordButton.getNormalState().setBackgroundColor(new Color(0xF1F1F1));
		changePasswordButton.getNormalState().setBorderColor(null);
		changePasswordButton.getNormalState().setBorderWidth(0);
		
		changePasswordButton.getPressedState().setWordsColor(new Color(0x8470FF));
		changePasswordButton.getPressedState().setBackgroundColor(new Color(0xF1F1F1));
		changePasswordButton.getPressedState().setBorderColor(null);
		changePasswordButton.getPressedState().setBorderWidth(0);		
		changePasswordButton.addActionListener((e)->{
			changePasswordDialog dialog = new changePasswordDialog(frame1, user, frame);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width-dialog.getWidth())/2;
			int y = (screenSize.height-dialog.getHeight())/2;
			dialog.setLocation(x, y);
			dialog.setVisible(true);
		});
		panel1.add(changePasswordButton, "150pixels");
		
		
	}
	
	/**
	 * 
	 * @Description save image name to the file
	 * @author Victor
	 * @date 2020年12月12日上午10:44:252020年12月12日
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

	/**
	 * 
	 * @Description select the picture from the computer
	 * @author Victor
	 * @date 2020年12月4日下午5:14:012020年12月4日
	 *
	 */
	private String selectFolderDialog() {
		JFileChooser chooser = new JFileChooser();
		ExampleFileFilter filter = new ExampleFileFilter();
		File dir = null;
		filter.addExtension("jpg");
		filter.addExtension("png");
		filter.setDescription("JPG & PNG Images");
		chooser.setFileFilter(filter);
//		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose the head portrait picture");
		int ret = chooser.showOpenDialog(this);
		//when the user choose the direction
		if(ret == JFileChooser.APPROVE_OPTION) {
			dir = chooser.getSelectedFile();
			imagePath = chooser.getSelectedFile().getAbsolutePath();
			String projectPath = getPath();
//			copyImageFile(imagePath, projectPath+"/bin/com/victor/UserImages/"+dir.getName());
//			copyImageFile(imagePath, projectPath+"/src/com/victor/UserImages/"+dir.getName());
			copyImageFile(imagePath, projectPath+"/com/victor/UserImages/"+dir.getName());
		}
		return dir.getName();	
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

	/**
	 * 
	 * @Description copy image files from local computer
	 * @author Victor
	 * @date 2020年12月4日下午6:06:162020年12月4日
	 *
	 */
	private void copyImageFile(String imagePath1, String string) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			File srcFile = new File(imagePath1);
			File desFile = new File(string);
			
			FileInputStream fis = new FileInputStream(srcFile);
			FileOutputStream fos = new FileOutputStream(desFile);
			
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			
			byte[] buffer = new byte[1024];
			int length;
			while((length=bis.read(buffer))!=-1) {
				bos.write(buffer, 0, length);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description display the modify dialog
	 * @author Victor
	 * @date 2020年12月4日下午9:11:122020年12月4日
	 *
	 */
	private void showModifyDialog(){
		ModifyDialog dialog = new ModifyDialog(frame1, user);
		if(dialog.execute()) {
			List<UserInfo> list = frame.itemList;
			UserInfo user1 = dialog.getInfo();
			for(int i=0; i<list.size(); i++) {
				if(list.get(i).getName().equals(user.getName())) {
					list.get(i).setName(user1.getName());
					list.get(i).setBirthday(user1.getBirthday());
					list.get(i).setCellPhone(user1.getCellPhone());
					list.get(i).setEmail(user1.getEmail());
					list.get(i).setIndividualResume(user1.getIndividualResume());
					list.get(i).setSex(user1.isSex());
				}
			}
			frame.saveData();
		}
	}
	
	
}
