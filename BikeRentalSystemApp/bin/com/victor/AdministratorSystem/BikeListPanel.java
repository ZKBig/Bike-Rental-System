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

import com.victor.BikeDisplayPanel.*;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.BikeInfo;
import com.victor.Login.Util;

public class BikeListPanel extends JPanel{
	private static final long serialVersionUID = 1497919749058813246L;
	public  DefaultTableModel tableModel = new DefaultTableModel();
	public  List<BikeInfo> bikeList = new ArrayList<>();
	private JTable table = null;
	private JScrollPane scrollPane;
	private CustomPanel bar;
	private JFrame owner;
	private String company;
	private JButton addButton;
	private JTextField searchField;
	
	public BikeListPanel(JFrame owner) {
		//set the table to the main frame
		this.owner = owner;
		this.setLayout(new CustomColumnLayout());
		bar = new CustomPanel(this.getWidth(), this.getHeight());
		bar.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		bar.setLayout(new CustomRowLayout());
		this.add(bar, "50pixels");
		
		initialToolBar();
		
		initialTable();
	}
	
	
	public List<BikeInfo> getBikeList() {
		return bikeList;
	}


	public void setBikeList(List<BikeInfo> bikeList) {
		this.bikeList = bikeList;
	}


	/**
	 * 
	 * @Description initialize the table display
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�3:34:582020骞�12鏈�11鏃�
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
		
		tableModel.addColumn("Company");
		tableModel.addColumn("Serial number");
		tableModel.addColumn("Age");
		tableModel.addColumn("Status");
		tableModel.addColumn("Region");
		
		table.getColumnModel().getColumn(0).setCellRenderer(new CompanyColumnRenderer());
		table.getColumnModel().getColumn(3).setCellRenderer(new StatusColumnRenderer());
	}




	/**
	 * 
	 * @Description load the bike information from the json file 
	 * @author Victor
	 * @date 2020骞�11鏈�22鏃ヤ笅鍗�5:29:262020骞�11鏈�22鏃�
	 *
	 */
	public void loadData(String filePath) {
		File file = new File(filePath);
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
		bikeList.clear();
		for(int i=0; i<array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			BikeInfo bike = new BikeInfo();
			bike.setCompany(object.getString("company"));
			bike.setID(object.getString("ID"));
			bike.setStatus(object.getBoolean("status"));
			bike.setYears(object.getString("years"));
			bike.setRegion(object.getString("region"));
			
			addTableRow(bike);
			addToDataList(bike);
		}
		
	}

	/**
	 * 
	 * @Description save data to the json file
	 * @author Victor
	 * @date 2020骞�11鏈�22鏃ヤ笅鍗�9:47:482020骞�11鏈�22鏃�
	 *
	 */
	public void saveData(String filePath) {
		JSONArray array = new JSONArray();
		for(int i=0; i<bikeList.size(); i++) {
			BikeInfo bike = bikeList.get(i);
			JSONObject object = new JSONObject();
			object.put("company", bike.getCompany());
			object.put("ID",bike.getID());
			object.put("status", bike.getStatus());
			object.put("years", bike.getYears());
			object.put("region", bike.getRegion());
			array.put(object);
		}
		
		File file = new File(filePath);
		try {
			Util.WriteToFile(array, file, "UTF-8");
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description add the item to the bike list
	 * @author Victor
	 * @date 2020骞�11鏈�22鏃ヤ笅鍗�9:47:442020骞�11鏈�22鏃�
	 *
	 */
	private void addToDataList(BikeInfo bike) {
		bikeList.add(bike);
	}

	/**
	 * 
	 * @Description add the item to the table
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�5:29:462020骞�12鏈�11鏃�
	 *
	 */
	public void addTableRow(BikeInfo item) {
		Vector<Object> rowData = new Vector<>();
		rowData.add(item.getCompany());
		rowData.add(item.getID());
		rowData.add(item.getYears());
		rowData.add(item.getStatus());	
		rowData.add(item.getRegion());
		tableModel.addRow(rowData); 
	}
	
	/**
	 * 
	 * @Description  initial ToolBar 
	 * @author Victor
	 * @date 2020骞�11鏈�22鏃ヤ笅鍗�3:38:312020骞�11鏈�22鏃�
	 *
	 */
	private void initialToolBar() {
		JToolBar toolBar = new JToolBar();
		JLabel search = new JLabel(Util.loadIcon("/com/victor/Images/search.png"));
		searchField = new JTextField();
		toolBar.setFloatable(false);
		bar.add(toolBar, "1w");
		addButton = addToolButton("add.png", "add", "add");
		
		toolBar.add(addToolButton("meituan.png", "Meituan", "Meituan"));
		toolBar.add(addToolButton("mobike.png", "Mobike", "Mobike"));
		toolBar.add(addToolButton("hello.png", "Hello", "Hello"));
		toolBar.add(addToolButton("Green Orange.png", "Green Orange", "Green Orange"));
		toolBar.addSeparator();
		toolBar.add(addButton);
		toolBar.add(addToolButton("modify.png", "modify", "modify"));
		toolBar.add(addToolButton("delete.png", "delete", "delete"));
		toolBar.add(addToolButton("sort.png", "sort", "sort"));
		
		toolBar.addSeparator();
		toolBar.add(search);
		toolBar.addSeparator(new Dimension(280, 10));
		toolBar.add(search);
		toolBar.add(searchField);
		searchField.setMaximumSize(new Dimension(170,30));
		searchField.addActionListener((e)->{
			SearchItem();
		});
	}
	
	private void SearchItem() {
		String filter = searchField.getText().trim();
		if(filter.length()==0) {
			tableModel.setRowCount(0);
			for(BikeInfo bike : bikeList) {
				addTableRow(bike);
			}
			this.addButton.setEnabled(true);
			return;
		}
		
		tableModel.setRowCount(0);
		for(BikeInfo bike : bikeList) {
			if(bike.getID().indexOf(filter)>=0 || bike.getRegion().indexOf(filter)>=0){
				addTableRow(bike);
			}
		}
		this.addButton.setEnabled(false);
	}

	/**
	 * 
	 * @Description add the tool button to the tool bar
	 * @author Victor
	 * @date 2020骞�11鏈�22鏃ヤ笅鍗�3:41:352020骞�11鏈�22鏃�
	 *
	 */
	protected JButton addToolButton(String imageName, String action, String tooltip) {
		URL imageURL = this.getClass().getResource("/com/victor/Images/"+ imageName);
		JButton button = new JButton();
		button.setActionCommand(action);
		button.setToolTipText(tooltip);
		button.setIcon(new ImageIcon(imageURL));
		button.setFocusPainted(false);
		
		button.addActionListener((e) -> {
			String action1 = e.getActionCommand();

			switch (action1) {
			case "Meituan":
				loadData("Meituan.json");
				company = "Meituan";
				break;
			case "Mobike":
				loadData("Mobike.json");
				company = "Mobike";
				break;
			case "Hello":
				loadData("Hello.json");
				company = "Hello";
				break;
			case "Green Orange":
				loadData("Green Orange.json");
				company = "Green Orange";
				break;
			}
			
			if(e.getActionCommand().equals("add")) {
				addItem();
			}
			
			if(e.getActionCommand().equals("modify")) {
				editItem();
			}
			
			if(e.getActionCommand().equals("delete")) {
				deleteItem();
			}
			
			if(e.getActionCommand().equals("sort")) {
				if(tableModel.getRowCount()==0) {
					JOptionPane.showMessageDialog(this, "Please select one company");
				}else {
					initialSortPopup(button);
				}
			}
			
		});
		return button;
	}

	/**
	 * 
	 * @Description delete the bike item
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:46:242020骞�12鏈�11鏃�
	 *
	 */
	private void deleteItem() {
		int[] rows = table.getSelectedRows();
		if(rows.length==0) {
			return;
		}
		
		int select = JOptionPane.showConfirmDialog(this, "Do you confirm to delete the bike item?");
		if(select!=0) {
			return;
		}else {
			for(int i = rows.length-1; i>=0; i--) {
				String number = (String)tableModel.getValueAt(rows[i], 1);
				tableModel.removeRow(rows[i]);
				removeFromBikeList(number);
			}
			saveData(company+".json");
		}
		
	}

	/**
	 * 
	 * @Description remove the bike items from the list
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:50:202020骞�12鏈�11鏃�
	 *
	 */
	private void removeFromBikeList(String number) {
		Iterator<BikeInfo> iterator = bikeList.iterator();
		while(iterator.hasNext()) {
			BikeInfo bike = iterator.next();
			if(bike.getID().equals(number)) {
				iterator.remove();
				break;
			}
		}
	}

	/**
	 * 
	 * @Description edit the information of the bike
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:37:432020骞�12鏈�11鏃�
	 *
	 */
	private void editItem() {
		int[] rows = table.getSelectedRows();
		if(rows.length==0) {
			return;
		}
		
		BikeInfo bike = getTableRow(rows[0]);
		EditDialog dialog = new EditDialog(owner, company);
		dialog.setValue(bike);
		if(dialog.execute()) {
			BikeInfo bikeInfo = dialog.getValue();
			setTableRow(bikeInfo, rows[0]);
			updateBikeList(bikeInfo);
		}
		saveData(company+".json");
	}

	/**
	 * 
	 * @Description update the information of bike in the list
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:44:042020骞�12鏈�11鏃�
	 *
	 */
	private void updateBikeList(BikeInfo bikeInfo) {
		for(int i=0; i<bikeList.size(); i++) {
			BikeInfo item = bikeList.get(i);
			if(item.getID().equals(bikeInfo.getID())) {
				bikeList.set(i, bikeInfo);
			}
		}
	}

	/**
	 * 
	 * @Description set the updated data of the bike in the table
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:45:452020骞�12鏈�11鏃�
	 *
	 */
	private void setTableRow(BikeInfo bikeInfo, int i) {
		tableModel.setValueAt(bikeInfo.getCompany(), i, 0);
		tableModel.setValueAt(bikeInfo.getID(), i, 1);
		tableModel.setValueAt(bikeInfo.getYears(), i, 2);
		tableModel.setValueAt(bikeInfo.getStatus(), i, 3);
		tableModel.setValueAt(bikeInfo.getRegion(), i, 4);
	}


	/**
	 * 
	 * @Description add item to the table
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:05:242020骞�12鏈�11鏃�
	 *
	 */
	private void addItem() {
		AddDialog dialog = new AddDialog(owner, company, this);
		if(dialog.execute()) {
			BikeInfo bike = dialog.getValue();
			addTableRow(bike);
			addToBikeList(bike);
		}
		saveData(company+".json");
	}

	/**
	 * 
	 * @Description add the bike item to the list
	 * @author Victor
	 * @date 2020骞�12鏈�11鏃ヤ笅鍗�4:25:012020骞�12鏈�11鏃�
	 *
	 */
	private void addToBikeList(BikeInfo bike) {
		bikeList.add(bike);
	}


	/**
	 * 
	 * @Description initial the sort popup
	 * @author Victor
	 * @date 2020骞�11鏈�23鏃ヤ笅鍗�7:23:222020骞�11鏈�23鏃�
	 *
	 */
	private void initialSortPopup(JButton button) {
		SortPopupPanel sortPopup = new SortPopupPanel(this);
		sortPopup.showPopup(button, 0, button.getHeight());
		
	}

	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�26鏃ヤ笂鍗�11:57:492020骞�11鏈�26鏃�
	 *
	 */
	private BikeInfo getTableRow(int i) {
		BikeInfo bike = new BikeInfo();
		bike.setCompany((String)tableModel.getValueAt(i, 0));
		bike.setID((String)tableModel.getValueAt(i, 1));
		bike.setYears((String)tableModel.getValueAt(i, 2));
		bike.setStatus((Boolean)tableModel.getValueAt(i, 3));
		bike.setRegion((String)tableModel.getValueAt(i, 4));
		return bike;
	}
	

}
