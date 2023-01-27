package com.victor.AdministratorSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.victor.BikeDisplayPanel.CompanyColumnRenderer;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.BikeInfo;
import com.victor.JavaBeans.DailyBikeInfo;
import com.victor.JavaBeans.HourlyBikeInfo;
import com.victor.JavaBeans.UserInfo;
import com.victor.JavaBeans.WeeklyBikeInfo;
import com.victor.Login.Util;

/**
 * 
 * @Description a panel to operate the user informations
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020骞�12鏈�11鏃ヤ笅鍗�6:08:11
 *
 */
public class UserListPanel extends JPanel{
	private static final long serialVersionUID = -3823373636912900728L;
	public  DefaultTableModel tableModel = new DefaultTableModel();
	public  List<UserInfo> userList = new ArrayList<>();
	private JTable table = null;
	private JScrollPane scrollPane;
	private CustomPanel bar;
	private JFrame owner;
	private JButton addButton;
	private JTextField searchField;

	public UserListPanel(JFrame owner) {
		// set the table to the main frame
		this.owner = owner;
		this.setLayout(new CustomColumnLayout());
		bar = new CustomPanel(this.getWidth(), this.getHeight());
		bar.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		bar.setLayout(new CustomRowLayout());
		this.add(bar, "50pixels");

		initialToolBar();

		initialTable();
		
		loadData();
	}
	
	/**
	 * 
	 * @Description initial the tool bar
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�6:58:052020骞�12鏈�11鏃�
	 *
	 */
	private void initialTable() {
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 2L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true); 
		table.setRowHeight(30);
		this.add(scrollPane, "1w");
		
		tableModel.addColumn("Name");
		tableModel.addColumn("Sex");
		tableModel.addColumn("Email");
		tableModel.addColumn("Cellphone");
		tableModel.addColumn("Password");
		tableModel.addColumn("Purse Money");
		
		table.getColumnModel().getColumn(0).setCellRenderer(new CompanyColumnRenderer());
		table.getColumnModel().getColumn(1).setCellRenderer(new sexColumnRenderer());
		
	}

	private void initialToolBar() {
		JToolBar toolBar = new JToolBar();
		JLabel search = new JLabel(Util.loadIcon("/com/victor/Images/search.png"));
		searchField = new JTextField();
		toolBar.setFloatable(false);
		bar.add(toolBar, "1w");
		toolBar.add(addToolButton("modify.png", "modify", "modify"));
		toolBar.add(addToolButton("delete.png", "delete", "delete"));
		
		toolBar.addSeparator();
		toolBar.add(search);
		toolBar.addSeparator(new Dimension(500, 10));
		toolBar.add(search);
		toolBar.add(searchField);
		searchField.setMaximumSize(new Dimension(170,30));
		searchField.addActionListener((e)->{
			SearchItem();
		});
		
	}
	
	protected JButton addToolButton(String imageName, String action, String tooltip) {
		URL imageURL = this.getClass().getResource("/com/victor/Images/" + imageName);
		JButton button = new JButton();
		button.setActionCommand(action);
		button.setToolTipText(tooltip);
		button.setIcon(new ImageIcon(imageURL));
		button.setFocusPainted(false);
		button.addActionListener((e) -> {

			if (e.getActionCommand().equals("modify")) {
				editItem();
			}
			if (e.getActionCommand().equals("delete")) {
				deleteItem();
			}
		});
		return button;
	}
	
	private void deleteItem() {
		int[] rows = table.getSelectedRows();
		if(rows.length==0) {
			return;
		}
		
		int select = JOptionPane.showConfirmDialog(this, "Do you confirm to delete the user item?");
		if(select!=0) {
			return;
		}else {
			for(int i = rows.length-1; i>=0; i--) {
				String name = (String)tableModel.getValueAt(rows[i], 0);
				tableModel.removeRow(rows[i]);
				removeFromUserList(name);
			}
			saveData();
		}
	}

	private void saveData() {
		JSONArray array = new JSONArray();
		for(int i=0; i<userList.size(); i++) {
			UserInfo user = userList.get(i);
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
			e.printStackTrace();
		}
	}
	
	private void loadData() {
		File file = new File("User_Information.json");
		if(!file.exists()) {
			return;
		}
		
		JSONArray array = null;
		try {
			array = (JSONArray)Util.readFromFile(file, "UTF-8");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
			return;
		}
		
		tableModel.setRowCount(0);
		userList.clear();
		for (int i = 0; i < array.length(); i++) {
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

			addToUserList(user);
		}
		addTableRow(userList);
	}

	private void addToUserList(UserInfo user) {
		boolean flag = false;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getName().equals(user.getName())) {
				userList.set(i, user);
				flag = true;
			}
		}
		if (!flag) {
			userList.add(user);
		}
	}

	private void removeFromUserList(String name) {
		Iterator<UserInfo> iterator = userList.iterator();
		while(iterator.hasNext()) {
			UserInfo user = iterator.next();
			if(user.getName().equals(name)) {
				iterator.remove();
				break;
			}
		}
		
	}

	private void editItem() {
		int[] rows = table.getSelectedRows();
		if(rows.length==0) {
			return;
		}
		
		UserInfo user = getTableRow(rows[0]);
		UserEditDialog dialog = new UserEditDialog(owner, user);
		dialog.setValue(user);
		if(dialog.execute()) {
			UserInfo userInfo = dialog.getValue();
			setTableRow(userInfo , rows[0]);
			updateBikeList(userInfo);
		}
		saveData();
		
	}

	private void updateBikeList(UserInfo user) {
		for(int i=0; i<userList.size(); i++) {
			UserInfo userInfo = userList.get(i);
			if(userInfo.getName().equals(user.getName())) {
				userList.set(i, user);
			}
		}
	}

	private void setTableRow(UserInfo user, int i) {
		tableModel.setValueAt(user.getName(), i, 0);
		tableModel.setValueAt(user.isSex(), i, 1);
		tableModel.setValueAt(user.getEmail(), i, 2);
		tableModel.setValueAt(user.getCellPhone(), i, 3);
		tableModel.setValueAt(user.getPassword(), i, 4);
		tableModel.setValueAt(user.getMoney()+" RMB", i, 5);
		
	}

	private UserInfo getTableRow(int i) {
		UserInfo user = new UserInfo();
		for(int j=0; j<userList.size(); j++) {
			if(userList.get(j).getName().equals((String)tableModel.getValueAt(i, 0))) {
				user=userList.get(j);
			}
		}
		return user;
	}

	/**
	 * 
	 * @Description search user item according to the text
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�7:16:192020骞�12鏈�11鏃�
	 *
	 */
	private void SearchItem() {
		String filter = searchField.getText().trim();
		if(filter.length()==0) {
			tableModel.setRowCount(0);
			for(UserInfo user : userList) {
				addTableRow(user);
			}
			return;
		}
		
		tableModel.setRowCount(0);
		for(UserInfo user : userList) {
			if(user.getName().indexOf(filter)>=0 || user.getCellPhone().indexOf(filter)>=0){
				addTableRow(user);
			}
		}
	}
	
	private void addTableRow(List<UserInfo> userList) {
		for(int i=0; i<userList.size(); i++) {
			UserInfo user = userList.get(i);
			Vector<Object> rowData = new Vector<>();
			rowData.add(user.getName());
			rowData.add(user.isSex());
			rowData.add(user.getEmail());
			rowData.add(user.getCellPhone());	
			rowData.add(user.getPassword());
			rowData.add(user.getMoney()+" RMB");
			tableModel.addRow(rowData); 
		}
	}
	
	private void addTableRow(UserInfo user) {
		Vector<Object> rowData = new Vector<>();
		rowData.add(user.getName());
		rowData.add(user.isSex());
		rowData.add(user.getEmail());
		rowData.add(user.getCellPhone());	
		rowData.add(user.getPassword());
		rowData.add(user.getMoney()+" RMB");
		tableModel.addRow(rowData); 
	}
}
