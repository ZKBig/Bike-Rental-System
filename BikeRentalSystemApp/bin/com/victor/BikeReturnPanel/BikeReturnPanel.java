package com.victor.BikeReturnPanel;

import java.awt.*;



import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.victor.BikeDisplayPanel.CompanyColumnRenderer;
import com.victor.JavaBeans.*;
import com.victor.BikeRentalDialog.PaymentDialog;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomControl.CustomToggleButton;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.Login.LoginFrame;
import com.victor.UserCenterPanel.UserPanel;
import com.victor.Login.Util;


/**
 * @Description customize the system setting panel
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020骞�11鏈�17鏃ヤ笅鍗�7:18:41
 *
 */
public class BikeReturnPanel extends CustomPanel {

	private static final long serialVersionUID = -2573535459348616638L;
	public DefaultTableModel tableModel =  new DefaultTableModel();
	public List<BikeInfo> bikeList = new ArrayList<>();
	private List<BikeInfo> bikes = new ArrayList<>();
	private JTable table = null;
	private JScrollPane scrollPane;
	private CustomPanel bar;
	private JFrame owner;
	private UserInfo user;
	private LoginFrame frame;
	private int selection;
	private int overdueRent;
	private int totalRent;
	private int[] daysNumber = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private int[] daysNumber1 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private int thisYear = Calendar.getInstance().get(Calendar.YEAR);
	private int thisMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
	private int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private int thisHour;
	private UserPanel userPanel;
	
	public BikeReturnPanel(JFrame owner, UserInfo user, LoginFrame frame, UserPanel userPanel) {
		this.owner = owner;
		this.user = user;
		this.frame = frame;
		this.userPanel = userPanel;
		
		this.setLayout(new CustomColumnLayout());
		this.setMargin(5);
		bar = new CustomPanel(this.getWidth(), this.getHeight());
		bar.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		bar.setLayout(new CustomRowLayout());
		this.add(bar, "60pixels");
		
		initialToolBar();
		
		initialDiaplaySelectionPanel();
		
		initialTable();
		
		loadData();
	}
	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	/**
	 * 
	 * @Description load the bike data of the user
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�4:10:312020骞�12鏈�5鏃�
	 *
	 */
	public void loadData() {
		for(int i =1; i<user.getBikes().length && user.getBikes()[i]!=null; i++) {
			addTableRow(user.getBikes()[i]);
			addToList(user.getBikes()[i]);
		}
	}

	/**
	 * 
	 * @Description add the user bike data to the list
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�4:11:012020骞�12鏈�5鏃�
	 *
	 */
	private void addToList(BikeInfo bikeInfo) {
		bikeList.add(bikeInfo);
	}

	/**
	 * 
	 * @Description initial the bikes table 
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�2:31:442020骞�12鏈�5鏃�
	 *
	 */
	private void initialTable() {
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 5L;

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
		tableModel.addColumn("Type");
		tableModel.addColumn("Due time");
		tableModel.addColumn("Status");
		
		
		table.getColumnModel().getColumn(0).setCellRenderer(new CompanyColumnRenderer());
		table.getColumnModel().getColumn(5).setCellRenderer(new RentalStatusColumnRenderer());
	}
	
	/**
	 * 
	 * @Description add the item to the user bike list
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�3:27:022020骞�12鏈�5鏃�
	 *
	 */
	private void addTableRow(BikeInfo bikeInfo) {
		String status = "";
		LocalTime localTime = LocalTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = localTime.format(dateTimeFormatter);
		thisHour = Integer.parseInt(time.substring(0, time.indexOf(":")))*60+
				Integer.parseInt(time.substring(time.indexOf(":")+1, time.length()));
		Vector<Object> rowData = new Vector<>();
		rowData.add(bikeInfo.getCompany());
		rowData.add(bikeInfo.getID());
		rowData.add(bikeInfo.getYears());
		if(bikeInfo instanceof DailyBikeInfo) {
			DailyBikeInfo bike = (DailyBikeInfo)bikeInfo;
			rowData.add(bike.getRentType());
			if(bike.getStatus()==false) {
				if(Integer.parseInt(bike.getYear1())<thisYear) {
					status = "overdue";
				}else {
					if((Integer.parseInt(bike.getYear1())==thisYear)
							&&Integer.parseInt(bike.getMonth1())<thisMonth) {
						status = "overdue";
					}else {
						if((Integer.parseInt(bike.getYear1())== thisYear)&&
								(Integer.parseInt(bike.getMonth1())== thisMonth)&&
								Integer.parseInt(bike.getDay1())<thisDay){
							status = "overdue";
						}else {
							status = "toBeReturned";
						}
					}
				}
			}else {
				status = "returned";
			}
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
			rowData.add(status);
			tableModel.addRow(rowData);
		}else if(bikeInfo instanceof HourlyBikeInfo) {
			HourlyBikeInfo bike = (HourlyBikeInfo)bikeInfo;
			rowData.add(bike.getRentType());
			if(bike.getStatus()==false) {
				if(Integer.parseInt(bike.getYear())<thisYear) {
					status = "overdue";
				}else {
					if((Integer.parseInt(bike.getYear())==thisYear) && 
							Integer.parseInt(bike.getMonth())<thisMonth) {
						status = "overdue";
					}else {
						if((Integer.parseInt(bike.getYear())==thisYear) && 
								(Integer.parseInt(bike.getMonth())==thisMonth) &&
								Integer.parseInt(bike.getDay())<thisDay){
							status = "overdue";
						}else if((Integer.parseInt(bike.getYear())==thisYear) && 
								(Integer.parseInt(bike.getMonth())==thisMonth) &&
								Integer.parseInt(bike.getDay())==thisDay){
							if(Integer.parseInt(bike.getEndHour().substring(0,
									bike.getEndHour().indexOf(':')))*60<thisHour) {
								status = "overdue";
							}else {
								status = "toBeReturned";
							}
						}else {
								status = "toBeReturned";
						}
					}
				}
			}else {
				status = "returned";
			}
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
			rowData.add(status);
			tableModel.addRow(rowData);
		}else if(bikeInfo instanceof WeeklyBikeInfo) {
			WeeklyBikeInfo bike = (WeeklyBikeInfo)bikeInfo;
			rowData.add(bike.getRentType());
			if(bike.getStatus()==false) {
				if(Integer.parseInt(bike.getYear1())<thisYear) {
					status = "overdue";
				}else {
					if((Integer.parseInt(bike.getYear1())==thisYear)
							&&Integer.parseInt(bike.getMonth1())<thisMonth) {
						status = "overdue";
					}else {
						if((Integer.parseInt(bike.getYear1())==thisYear)&&
								(Integer.parseInt(bike.getMonth1())==thisMonth)&&
								Integer.parseInt(bike.getDay1())<thisDay){
							status = "overdue";
						}else {
							status = "toBeReturned";
						}
					}
				}
			}else {
				status = "returned";
			}
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
			rowData.add(status);
			tableModel.addRow(rowData);
		}
	}

	/**
	 * 
	 * @Description 
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�2:21:122020骞�12鏈�5鏃�
	 *
	 */
	private void initialDiaplaySelectionPanel() {
		CustomPanel displayPanel = new CustomPanel();
		displayPanel.setMargin(6);
		displayPanel.setLayout(new CustomRowLayout());
		
		CustomPanel row1 = new CustomPanel();
		row1.setPreferredSize(new Dimension(300,38));
		row1.setLayout(new CustomRowLayout());
		JLabel label1 = new JLabel("Bikes to be returned");
		label1.setFont(new Font("Arial", Font.PLAIN, 17));
		label1.setForeground(Color.BLACK);
		row1.add(label1, "1w");
		CustomToggleButton button1 = new CustomToggleButton();
		button1.setPreferredSize(new Dimension(90, 25));
		row1.add(button1, "60pixels");
		displayPanel.add(row1, "46%");
		
		CustomPanel row2 = new CustomPanel();
		row2.setPreferredSize(new Dimension(300,38));
		row2.setLayout(new CustomRowLayout());
		JLabel label2 = new JLabel("    Overdue bikes");
		label2.setFont(new Font("Arial", Font.PLAIN, 17));
		label2.setForeground(Color.BLACK);
		row2.add(label2, "1w");
		CustomToggleButton button2 = new CustomToggleButton();
		button2.setPreferredSize(new Dimension(90, 25));
		row2.add(button2, "60pixels");
		displayPanel.add(row2, "50%");
		
		this.add(displayPanel, "38pixels");
	}

	/**
	 * 
	 * @Description set the content of the tool bar
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�12:01:082020骞�12鏈�5鏃�
	 *
	 */
	private void initialToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		bar.add(toolBar, "1w");
		
		toolBar.add(addToolButton("Return Bike", "Return Bike", "return.png"));
		toolBar.addSeparator(new Dimension(10, 10));
		toolBar.add(addToolButton("Cancel Rental", "Cancel Rental", "cancel.png"));
	}

	/**
	 * 
	 * @Description create the tool bar button
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�12:04:362020骞�12鏈�5鏃�
	 *
	 */
	private JButton addToolButton(String tooltip, String action, String imageName) {
		JButton button = new JButton();
		URL imageURL = this.getClass().getResource("/com/victor/Images/" + imageName);
		button.setActionCommand(action);
		button.setToolTipText(tooltip);
		button.setIcon(new ImageIcon(imageURL));
		button.setFocusable(false);

		LocalTime localTime = LocalTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = localTime.format(dateTimeFormatter);
		thisHour = Integer.parseInt(time.substring(0, time.indexOf(":"))) * 60
				+ Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
		button.addActionListener((e) -> {
			if (e.getActionCommand().equals("Return Bike")) {
				int[] rows = table.getSelectedRows();
				if (rows.length == 1) {
					if (((String) tableModel.getValueAt(rows[0], 3)).equals("Hourly")) {
						HourlyBikeInfo bike = null;
						for (int i = 0; i < bikeList.size(); i++) {
							if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
								bike = (HourlyBikeInfo) bikeList.get(i);
							}
						}
						if (((String) tableModel.getValueAt(rows[0], 5)).equals("toBeReturned")) {
							if (isValid(bike)) {
								int select = JOptionPane.showConfirmDialog(this, "Do you confirm to return the bike?");
								if (select == JOptionPane.YES_OPTION) {
									for (int i = 0; i < frame.itemList.size(); i++) {
										if (frame.itemList.get(i).getName().equals(user.getName())) {
											for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
												if (frame.itemList.get(i).getBikes()[j].getID().equals(bike.getID())) {
													frame.itemList.get(i).getBikes()[j].setStatus(true);
												}
											}
										}
									}
									frame.saveData();
									updateBikeInfo(bike);
									tableModel.setRowCount(0);
									bikeList.clear();
									loadData();
								}
							} else {
								JOptionPane.showMessageDialog(this, "It is not rental time yet!");
							}
						} else if (((String) tableModel.getValueAt(rows[0], 5)).equals("overdue")) {
							if (Integer.parseInt(bike.getYear()) == thisYear
									&& Integer.parseInt(bike.getMonth()) == thisMonth
									&& Integer.parseInt(bike.getDay()) == thisDay) {
								if (thisHour - Integer.parseInt(
										bike.getEndHour().substring(0, bike.getEndHour().indexOf(":"))) * 60 < 240) {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 100RMB for overdue");
									overdueRent = 100;
								} else {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 300RMB for overdue");
									overdueRent = 300;
								}
							}else {
								selection = JOptionPane.showConfirmDialog(this, "Please pay 300RMB for overdue");
								overdueRent = 300;
							}
								if (selection == 0) {
									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(overdueRent), user);
									payment.setVisible(true);
									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())-overdueRent));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										loadData();
									}
								}
							}

					} else if (((String) tableModel.getValueAt(rows[0], 3)).equals("Daily")) {
						DailyBikeInfo bike = null;
						for (int i = 0; i < bikeList.size(); i++) {
							if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
								bike = (DailyBikeInfo) bikeList.get(i);
							}
						}
						if (((String) tableModel.getValueAt(rows[0], 5)).equals("toBeReturned")) {
							if (isValid(bike)) {
								int select = JOptionPane.showConfirmDialog(this, "Do you confirm to return the bike?");
								if (select == JOptionPane.YES_OPTION) {
									for (int i = 0; i < frame.itemList.size(); i++) {
										if (frame.itemList.get(i).getName().equals(user.getName())) {
											for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
												if (frame.itemList.get(i).getBikes()[j].getID().equals(bike.getID())) {
													frame.itemList.get(i).getBikes()[j].setStatus(true);
												}
											}
										}
									}
									frame.saveData();
									updateBikeInfo(bike);
									tableModel.setRowCount(0);
									bikeList.clear();
									loadData();
								}
							} else {
								JOptionPane.showMessageDialog(this, "It is not rental time yet!");
							}
						} else if (((String) tableModel.getValueAt(rows[0], 5)).equals("overdue")) {
							if (Integer.parseInt(bike.getYear1()) == thisYear
									&& Integer.parseInt(bike.getMonth1()) == thisMonth
									&& Integer.parseInt(bike.getDay1()) == thisDay) {
								if (thisHour < 720) {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 150RMB for overdue");
									overdueRent = 100;
								} else {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 300RMB for overdue");
									overdueRent = 300;
								}
								if (selection == 0) {
									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(overdueRent), user);
									payment.setVisible(true);
									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())-overdueRent));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										bikeList.clear();
										loadData();
									}
								}
							}
						}
					} else if (((String) tableModel.getValueAt(rows[0], 3)).equals("Weekly")) {
						WeeklyBikeInfo bike = null;
						for (int i = 0; i < bikeList.size(); i++) {
							if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
								bike = (WeeklyBikeInfo) bikeList.get(i);
							}
						}
						if (((String) tableModel.getValueAt(rows[0], 5)).equals("toBeReturned")) {
							if (isValid(bike)) {
								int select = JOptionPane.showConfirmDialog(this, "Do you confirm to return the bike?");
								if (select == JOptionPane.YES_OPTION) {
									for (int i = 0; i < frame.itemList.size(); i++) {
										if (frame.itemList.get(i).getName().equals(user.getName())) {
											for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
												if (frame.itemList.get(i).getBikes()[j].getID().equals(bike.getID())) {
													frame.itemList.get(i).getBikes()[j].setStatus(true);
												}
											}
										}
									}
									frame.saveData();
									updateBikeInfo(bike);
									tableModel.setRowCount(0);
									bikeList.clear();
									loadData();
								}
							} else {
								JOptionPane.showMessageDialog(this, "It is not rental time yet!");
							}
						} else if (((String) tableModel.getValueAt(rows[0], 5)).equals("overdue")) {
							if (Integer.parseInt(bike.getYear1()) == thisYear
									&& Integer.parseInt(bike.getMonth1()) == thisMonth
									&& Integer.parseInt(bike.getDay1()) == thisDay) {
								if (thisHour < 720) {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 150RMB for overdue");
									overdueRent = 100;
								} else {
									selection = JOptionPane.showConfirmDialog(this, "Please pay 300RMB for overdue");
									overdueRent = 300;
								}
								if (selection == 0) {
									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(overdueRent), user);
									payment.setVisible(true);
									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())-overdueRent));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										bikeList.clear();
										loadData();
									}
								}
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(this, "Please select one bike");
				}
			}

			if (e.getActionCommand().equals("Cancel Rental")) {
				int[] rows = table.getSelectedRows();
				if (rows.length == 1) {
					if (((String) tableModel.getValueAt(rows[0], 5)).equals("toBeReturned")) {
						if (((String) tableModel.getValueAt(rows[0], 3)).equals("Hourly")) {
							HourlyBikeInfo bike = null;
							for (int i = 0; i < bikeList.size(); i++) {
								if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
									bike = (HourlyBikeInfo) bikeList.get(i);
								}
							}
							if (!isValid(bike)) {
								int rentMoney = getRentMoney(bike);
								int select = JOptionPane.showConfirmDialog(this, "You will be returned "+rentMoney+"RMB (deduct 30% of the total rent money), Do you confirm to cancel?");
								if (select == JOptionPane.YES_OPTION) {
//									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(50), user);
//									payment.setVisible(true);
									
//									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())+rentMoney));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										bikeList.clear();
										loadData();
//									}
								}
							}
						} else if (((String) tableModel.getValueAt(rows[0], 3)).equals("Daily")) {
							DailyBikeInfo bike = null;
							for (int i = 0; i < bikeList.size(); i++) {
								if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
									bike = (DailyBikeInfo) bikeList.get(i);
								}
							}
							if (!isValid(bike)) {
								int rentMoney = getRentMoney(bike);
								int select = JOptionPane.showConfirmDialog(this, "You will be returned "+rentMoney+"RMB (deduct 30% of the total rent money), Do you confirm to cancel?");
								if (select == JOptionPane.YES_OPTION) {
//									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(50), user);
//									payment.setVisible(true);
//									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())+rentMoney));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										bikeList.clear();
										loadData();
//									}
								}
							}
						} else if (((String) tableModel.getValueAt(rows[0], 3)).equals("Weekly")) {
							WeeklyBikeInfo bike = null;
							for (int i = 0; i < bikeList.size(); i++) {
								if (bikeList.get(i).getID().equals((String) tableModel.getValueAt(rows[0], 1))) {
									bike = (WeeklyBikeInfo) bikeList.get(i);
								}
							}
							if (!isValid(bike)) {
								int rentMoney = getRentMoney(bike);
								int select = JOptionPane.showConfirmDialog(this, "You will be returned "+rentMoney+"RMB (deduct 30% of the total rent money), Do you confirm to cancel?");
								if (select == JOptionPane.YES_OPTION) {
//									PaymentDialog payment = new PaymentDialog(owner, String.valueOf(50), user);
//									payment.setVisible(true);
//									if (payment.isFlag() == true) {
										for (int i = 0; i < frame.itemList.size(); i++) {
											if (frame.itemList.get(i).getName().equals(user.getName())) {
												for (int j = 0; j < frame.itemList.get(i).getBikes().length; j++) {
													if (frame.itemList.get(i).getBikes()[j].getID()
															.equals(bike.getID())) {
														frame.itemList.get(i).getBikes()[j].setStatus(true);
														frame.itemList.get(i).setMoney(String.valueOf(Integer.parseInt(frame.itemList.get(i).getMoney())+rentMoney));
													}
												}
											}
										}
										frame.saveData();
										updateBikeInfo(bike);
										tableModel.setRowCount(0);
										bikeList.clear();
										loadData();
//									}
								}
							}
						}

					} else if (((String) tableModel.getValueAt(rows[0], 5)).equals("overdue")) {
						JOptionPane.showMessageDialog(this, "The bike is overdue!");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please select one bike");
				}
			}
		});
		return button;
	}

	/**
	 * 
	 * @Description get the rental money of the hourly rent bike
	 * @author Victor
	 * @date 2020骞�12鏈�15鏃ヤ笅鍗�2:58:502020骞�12鏈�15鏃�
	 *
	 */
	private int getRentMoney(HourlyBikeInfo bike) {
		totalRent = (int) ((Integer.parseInt(bike.getEndHour().substring(0, bike.getEndHour().indexOf(":"))) - Integer.parseInt(bike.getStartHour().substring(0,bike.getStartHour().indexOf(":")))) * 50 * 0.7);
		return totalRent;
	}
	
	/**
	 * 
	 * @Description get the rental money of the daily rent bike
	 * @author Victor
	 * @date 2020骞�12鏈�15鏃ヤ笅鍗�3:46:222020骞�12鏈�15鏃�
	 *
	 */
	private int getRentMoney(DailyBikeInfo bike) {
		if (Integer.parseInt(bike.getYear()) == Integer.parseInt(bike.getYear1())) {
			if (Integer.parseInt(bike.getMonth()) == Integer.parseInt(bike.getMonth1())) {
				totalRent = (int) ((Integer.parseInt(bike.getDay1()) - Integer.parseInt(bike.getDay())) * 200 * 0.7);
			} else {
				int year = Integer.parseInt(bike.getYear());
				int month = Integer.parseInt(bike.getMonth());
				int month1 = Integer.parseInt(bike.getMonth1());
				int day = Integer.parseInt(bike.getDay());
				int day1 = Integer.parseInt(bike.getDay1());
				int tempNumber;
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
					tempNumber = daysNumber[month - 1] - day;
					for (int i = month; i < month1-1; i++) {
						tempNumber = daysNumber[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = (int) (tempNumber * 200 * 0.7);
				} else {
					tempNumber = daysNumber1[month - 1] - day;
					for (int i = month; i < month1-1; i++) {
						tempNumber = daysNumber1[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = (int) (tempNumber * 200 * 0.7);
				}
			}
		} else {
			int year = Integer.parseInt(bike.getYear());
			int year1 = Integer.parseInt(bike.getYear1());
			int month = Integer.parseInt(bike.getMonth());
			int month1 = Integer.parseInt(bike.getMonth1());
			int day = Integer.parseInt(bike.getDay());
			int day1 = Integer.parseInt(bike.getDay1());
			int tempNumber;
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				tempNumber = daysNumber[month - 1] - day;
				for (int i = month; i < 12; i++) {
					tempNumber = daysNumber[i] + tempNumber;
				}
			} else {
				tempNumber = daysNumber1[month - 1] - day;
				for (int i = month; i < 12; i++) {
					tempNumber = daysNumber1[i] + tempNumber;
				}
			}

			for (int j = year + 1; j < year1; j++) {
				if (((j % 4 == 0) && (j % 100 != 0)) || (j % 400 == 0)) {
					tempNumber = tempNumber + 366;
				} else {
					tempNumber = tempNumber + 365;
				}
			}

			if (((year1 % 4 == 0) && (year1 % 100 != 0)) || (year1 % 400 == 0)) {
				for (int i = 0; i < month1-1; i++) {
					tempNumber = daysNumber[i] + tempNumber;
				}
				tempNumber = tempNumber + day1;
				totalRent = (int) (tempNumber * 200 * 0.7);
			} else {
				for (int i = 0; i < month1-1; i++) {
					tempNumber = daysNumber1[i] + tempNumber;
				}
				tempNumber = tempNumber + day1;
				totalRent = (int) (tempNumber * 200 * 0.7);
			}
		}
		return totalRent;
	}
	
	private int getRentMoney(WeeklyBikeInfo bike) {
		if (Integer.parseInt(bike.getYear()) == Integer.parseInt(bike.getYear1())) {
			if (Integer.parseInt(bike.getMonth()) == Integer.parseInt(bike.getMonth1())) {
				totalRent = ((Integer.parseInt(bike.getDay1()) - Integer.parseInt(bike.getDay())) / 7) * 600;
			} else {
				int year = Integer.parseInt(bike.getYear());
				int month = Integer.parseInt(bike.getMonth());
				int month1 = Integer.parseInt(bike.getMonth1());
				int day = Integer.parseInt(bike.getDay());
				int day1 = Integer.parseInt(bike.getDay1());
				int tempNumber;
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
					tempNumber = daysNumber[month - 1] - day;
					for (int i = month; i < month1 - 1; i++) {
						tempNumber = daysNumber[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = (int) ((tempNumber / 7) * 600 * 0.7);
				} else {
					tempNumber = daysNumber1[month - 1] - day;
					for (int i = month; i < month1 - 1; i++) {
						tempNumber = daysNumber1[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = (int) ((tempNumber / 7) * 600 * 0.7);
				}
			}
		} else {
			int year = Integer.parseInt(bike.getYear());
			int year1 = Integer.parseInt(bike.getYear1());
			int month = Integer.parseInt(bike.getMonth());
			int month1 = Integer.parseInt(bike.getMonth1());
			int day = Integer.parseInt(bike.getDay());
			int day1 = Integer.parseInt(bike.getDay1());
			int tempNumber;
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				tempNumber = daysNumber[month - 1] - day;
				for (int i = month; i < 12; i++) {
					tempNumber = daysNumber[i] + tempNumber;
				}
			} else {
				tempNumber = daysNumber1[month - 1] - day;
				for (int i = month; i < 12; i++) {
					tempNumber = daysNumber1[i] + tempNumber;
				}
			}

			for (int j = year + 1; j < year1; j++) {
				if (((j % 4 == 0) && (j % 100 != 0)) || (j % 400 == 0)) {
					tempNumber = tempNumber + 366;
				} else {
					tempNumber = tempNumber + 365;
				}
			}

			if (((year1 % 4 == 0) && (year1 % 100 != 0)) || (year1 % 400 == 0)) {
				for (int i = 0; i < month1 - 1; i++) {
					tempNumber = daysNumber[i] + tempNumber;
				}
				tempNumber = tempNumber + day1;
				totalRent = (int) ((tempNumber / 7) * 600 * 0.7);
			} else {
				for (int i = 0; i < month1 - 1; i++) {
					tempNumber = daysNumber1[i] + tempNumber;
				}
				tempNumber = tempNumber + day1;
				totalRent = (int) ((tempNumber / 7) * 600 * 0.7);
			}
		}

		return totalRent;
	}

	/**
	 * 
	 * @Description update the bike informations
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�6:56:372020骞�12鏈�5鏃�
	 *
	 */
	private void updateBikeInfo(BikeInfo bike) {
		File file = null;
		switch(bike.getCompany()) {
		case "Meituan":
			file = new File("Meituan.json");
			break;
		case "Mobike":
			file = new File("Mobike.json");
			break;
		case "Hello":
			file = new File("Hello.json");
			break;
		case "Green Orange":
			file = new File("Green Orange.json");
			break;
		}
		
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
	
		for(int i=0; i<array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			BikeInfo bike1 = new BikeInfo();
			bike1.setCompany(object.getString("company"));
			bike1.setID(object.getString("ID"));
			bike1.setStatus(object.getBoolean("status"));
			bike1.setYears(object.getString("years"));
			bike1.setRegion(object.getString("region"));
			
			bikes.add(bike1);
		}
		
		saveData(file, bike);
	}

	/**
	 * 
	 * @Description save the bike data to the file
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�7:04:142020骞�12鏈�5鏃�
	 *
	 */
	private void saveData(File file, BikeInfo bike) {
		for(int i=1; i<bikes.size(); i++) {
			if(bikes.get(i).getID().equals(bike.getID())) {
				bikes.get(i).setStatus(true);
			}
		}
		
		JSONArray array = new JSONArray();
		for(int i=0; i<bikes.size(); i++) {
			BikeInfo bike1 = bikes.get(i);
			JSONObject object = new JSONObject();
			object.put("company", bike1.getCompany());
			object.put("ID",bike1.getID());
			object.put("status", bike1.getStatus());
			object.put("years", bike1.getYears());
			object.put("region", bike1.getRegion());
			array.put(object);
		}
		
		try {
			Util.WriteToFile(array, file, "UTF-8");
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @Description judge whether the bike satisfy the requirement of return
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�5:43:062020骞�12鏈�5鏃�
	 *
	 */
	private boolean isValid(HourlyBikeInfo bike) {
		if (Integer.parseInt(bike.getYear()) > thisYear) {
			return false;
		} else {
			if (Integer.parseInt(bike.getMonth()) > thisMonth) {
				return false;
			} else {
				if (Integer.parseInt(bike.getDay()) > thisDay) {
					return false;
				} else if (Integer.parseInt(bike.getEndHour().substring(0, bike.getEndHour().indexOf(':')))
						* 60 > thisHour) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	/**
	 * 
	 * @Description judge whether the bike satisfy the requirement of return
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�7:46:142020骞�12鏈�5鏃�
	 *
	 */
	private boolean isValid(DailyBikeInfo bike) {
		if(Integer.parseInt(bike.getYear())>thisYear) {
			return false;
		}else {
			if(Integer.parseInt(bike.getMonth())>thisMonth) {
				return false;
			}else {
				if(Integer.parseInt(bike.getDay())>thisDay){
					return false;
				}else if(Integer.parseInt(bike.getDay1())>thisDay) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description judge whether the bike satisfy the requirement of return
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�7:54:442020骞�12鏈�5鏃�
	 *
	 */
	private boolean isValid(WeeklyBikeInfo bike) {
		if(Integer.parseInt(bike.getYear())>thisYear) {
			return false;
		}else {
			if(Integer.parseInt(bike.getMonth())>thisMonth) {
				return false;
			}else {
				if(Integer.parseInt(bike.getDay())>thisDay){
					return false;
				}else if(Integer.parseInt(bike.getDay1())>thisDay) {
						return true;
				}else {
					return false;
				}
			}
		}
	}

	/**
	 * 
	 * @Description get the user bike information from the table 
	 * @author Victor
	 * @date 2020骞�12鏈�5鏃ヤ笅鍗�5:16:302020骞�12鏈�5鏃�
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
