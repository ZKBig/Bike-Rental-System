package com.victor.BikeRentalDialog;

import java.awt.*;




import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Calendar;

import javax.swing.*;

import com.victor.BikeDisplayPanel.BikeRentalPanel;
import com.victor.JavaBeans.*;
import com.victor.BikeReturnPanel.BikeReturnPanel;
import com.victor.CustomControl.CustomPanel;
import com.victor.Login.CustomButton;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;
import com.victor.mainFrame.MenuItem;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;

/**
 * 
 * @Description the dialog of the individual rental
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020骞�11鏈�24鏃ヤ笂鍗�8:41:04
 *
 */
public class IndividualRentalDialog extends JDialog{
	private static final long serialVersionUID = 1137705962103220620L;
	private JList<MenuItem> leftMenu = new JList<>();
	private JPanel mainPanel;
	private CustomPanel rentalPanel = new CustomPanel();
	private JPanel root;
	private int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
	private int thisYear = Calendar.getInstance().get(Calendar.YEAR);
	private int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private HourlyRentalPanel hourly = new HourlyRentalPanel();;
	private DailyRentalPanel daily = new DailyRentalPanel();
	private WeeklyRentalPanel weekly = new  WeeklyRentalPanel();
	private int daysNumberOfMonth;
	private BikeInfo[] bikes;
	private BikeInfo[] bikeItems;
	private int totalRent;
	private int[] daysNumber = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private int[] daysNumber1 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private JFrame owner;
	private LoginFrame frame;
	private UserInfo user;
	private BikeRentalPanel bikePanel;
	private BikeReturnPanel bikeReturn;
	
	public IndividualRentalDialog(JFrame owner, BikeInfo[] bikes, LoginFrame frame, UserInfo user, BikeRentalPanel bikePanel, BikeReturnPanel bikeReturn) {
		super(owner,"Individual Rental", true);
		this.setResizable(false);
		this.frame = frame;
		this.user = user;
		this.bikes = bikes;
		this.owner=owner;
		this.bikePanel = bikePanel;
		this.bikeReturn = bikeReturn;
		bikeItems = new BikeInfo[bikes.length];
		
		setContentPane();
		
		initialLeftMenu();
		
		initialPanel();
		
		initialBottomPanel();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width-this.getWidth())/2;
		int y = (screenSize.height-this.getHeight())/2;
		this.setLocation(x, y);
		
		leftMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int index = leftMenu.locationToIndex(e.getPoint());
				if(index>=0) {
					selectPanel(index);
				}
			}
		});
		
	}
	
	private void updateBikeInfo(BikeInfo[] bikes) {
		List<BikeInfo> list = bikePanel.getBikeList();
		for(int i=0; i<list.size(); i++) {
			for(int j=0; j<bikes.length; j++) {
				if(list.get(i).getCompany().equals(bikes[j].getCompany()) && 
						list.get(i).getID().equals(bikes[j].getID())) {
					list.get(i).setStatus(false);
				}
			}
		}
		bikePanel.saveData(bikes[0].getCompany()+".json");
		bikePanel.tableModel.setRowCount(0);
		bikePanel.loadData(bikes[0].getCompany()+".json");
	}
	
	private void updateLoginFrameUserInformation(BikeInfo[] bikes) {
		List<UserInfo> list = LoginFrame.itemList;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(user.getName())) {
				if (list.get(i).getBikes() == null) {
					list.get(i).setBikes(bikes);
					list.get(i).setMoney(String.valueOf(Integer.parseInt(list.get(i).getMoney())-totalRent));
				} else {
					list.get(i).setBikes(Util.concat(user.getBikes(), bikes));
					list.get(i).setMoney(String.valueOf(Integer.parseInt(list.get(i).getMoney())-totalRent));
				}
			}
		}
		frame.saveData();
	}
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�12:55:512020骞�11鏈�24鏃�
	 *
	 */
	private void initialBottomPanel() {
		
		CustomButton button = new CustomButton("Confirm");
		CustomPanel buttonPanel = new CustomPanel();
		buttonPanel.setMargin(3);
		JLabel label = new JLabel();
		buttonPanel.setLayout(new CustomRowLayout());
		root.add(buttonPanel, "10%");
		button.getNormalState().setBackgroundColor(new Color(0x02B8FA));
		button.getNormalState().setWordsColor(new Color(0xFFFFFF));
		button.setButtonMargin(0);
		button.setButtonPadding(0);
		
		
		button.getPressedState().setBackgroundColor(new Color(0x00aeef));
		button.getPressedState().setWordsColor(null);
		button.setLength(10);
		button.setBounds(button.getBounds().x, button.getBounds().y, button.getBounds().width, button.getBounds().height+10);
		buttonPanel.add(label, "370pixels");
		buttonPanel.add(button,"100pixels");
		
		button.addActionListener((e)->{
			if(isSelectionValid()) {
				getRentalMoney();
				int select;
				PaymentDialog payment = new PaymentDialog(owner, String.valueOf(totalRent), user);
				if(bikes.length==1) {
					select = JOptionPane.showConfirmDialog(this, "Do you comfirm to rent "+String.valueOf(bikes.length)
					+" bike with the rent being "+String.valueOf(totalRent)+"RMB");
				}else {
					select = JOptionPane.showConfirmDialog(this, "Do you comfirm to rent "+String.valueOf(bikes.length)
					+" bikes with the rent being "+String.valueOf(totalRent)+"RMB");
				}
				
				if(select==0) {
					payment.setVisible(true);
					if (payment.isFlag() == true) {
						if (leftMenu.getSelectedIndex() == 0) {
							for (int i = 0; i < bikeItems.length; i++) {
								bikeItems[i] = new HourlyBikeInfo(bikes[i].getCompany(), bikes[i].getID(),
										bikes[i].getYears(), !bikes[i].getStatus(), bikes[i].getRegion(),
										hourly.getHourlyRentPanel().getYear(), hourly.getHourlyRentPanel().getMonth(),
										hourly.getHourlyRentPanel().getDay(),
										hourly.getHourlyRentPanel().getStartTime(),
										hourly.getHourlyRentPanel().getEndTime());
								bikes[i].setStatus(false);
							}
							updateBikeInfo(bikes);
							updateLoginFrameUserInformation(bikeItems);
							updateBikeReturnInfo();
							payment.setVisible(false);
						} else if (leftMenu.getSelectedIndex() == 1) {
							for (int i = 0; i < bikeItems.length; i++) {
								DailyBikeInfo bike = new DailyBikeInfo();
								bike.setCompany(bikes[i].getCompany());
								bike.setID(bikes[i].getID());
								bike.setRegion(bikes[i].getRegion());
								bike.setStatus(!bikes[i].getStatus());
								bike.setYears(bikes[i].getYears());
								bike.setYear(daily.getDailyRentPanel().getYear());
								bike.setYear1(daily.getDailyRentPanel().getYear1());
								bike.setMonth(daily.getDailyRentPanel().getMonth());
								bike.setMonth1(daily.getDailyRentPanel().getMonth1());
								bike.setDay(daily.getDailyRentPanel().getDay());
								bike.setDay1(daily.getDailyRentPanel().getDay1());
								bikeItems[i] = bike;
								bikes[i].setStatus(false);
							}
							updateBikeInfo(bikes);
							updateLoginFrameUserInformation(bikeItems);
							updateBikeReturnInfo();
							payment.setVisible(false);
						} else if (leftMenu.getSelectedIndex() == 2) {
							for (int i = 0; i < bikeItems.length; i++) {
								WeeklyBikeInfo bike = new WeeklyBikeInfo();
								bike.setCompany(bikes[i].getCompany());
								bike.setID(bikes[i].getID());
								bike.setRegion(bikes[i].getRegion());
								bike.setStatus(!bikes[i].getStatus());
								bike.setYears(bikes[i].getYears());
								bike.setYear(weekly.getWeeklyRentPanel().getYear());
								bike.setYear1(weekly.getWeeklyRentPanel().getYear1());
								bike.setMonth(weekly.getWeeklyRentPanel().getMonth());
								bike.setMonth1(weekly.getWeeklyRentPanel().getMonth1());
								bike.setDay(weekly.getWeeklyRentPanel().getDay());
								bike.setDay1(weekly.getWeeklyRentPanel().getDay1());
								bikeItems[i] = bike;
								bikes[i].setStatus(false);
							}
							updateBikeInfo(bikes);
							updateLoginFrameUserInformation(bikeItems);
							updateBikeReturnInfo();
							payment.setVisible(false);
						}

					}
				}
			}
		});

	}

	/**
	 *  
	 * @Description update BikeReturn Panel Information
	 * @author Victor
	 * @date 2020骞�12鏈�7鏃ヤ笅鍗�1:30:572020骞�12鏈�7鏃�
	 *
	 */
	private void updateBikeReturnInfo() {
		bikeReturn.tableModel.setRowCount(0);
		bikeReturn.bikeList.clear();
		bikeReturn.setUser(LoginFrame.getUser());
		bikeReturn.loadData();
	}

	/**
	 * 
	 * @Description get the total rental money of the total bikes
	 * @author Victor
	 * @date 2020骞�11鏈�30鏃ヤ笅鍗�8:06:052020骞�11鏈�30鏃�
	 *
	 */
	public void getRentalMoney() {
		if(leftMenu.getSelectedIndex()==0) {
			 totalRent = (Integer.parseInt(hourly.getHourlyRentPanel().getEndTime().substring(0, hourly.getHourlyRentPanel().getEndTime().indexOf(':')))- 
					 Integer.parseInt(hourly.getHourlyRentPanel().getStartTime().substring(0, hourly.getHourlyRentPanel().getStartTime().indexOf(':'))))*50*bikes.length;
		}else if(leftMenu.getSelectedIndex()==1) {
			if(Integer.parseInt(daily.getDailyRentPanel().getYear())==
			Integer.parseInt(daily.getDailyRentPanel().getYear1())) {
				if(Integer.parseInt(daily.getDailyRentPanel().getMonth())==
						Integer.parseInt(daily.getDailyRentPanel().getMonth1())) {
					totalRent = (Integer.parseInt(daily.getDailyRentPanel().getDay1())-
							Integer.parseInt(daily.getDailyRentPanel().getDay()))*200*bikes.length;
				}else {
					int year = Integer.parseInt(daily.getDailyRentPanel().getYear());
					int month = Integer.parseInt(daily.getDailyRentPanel().getMonth());
					int month1 = Integer.parseInt(daily.getDailyRentPanel().getMonth1());
					int day =  Integer.parseInt(daily.getDailyRentPanel().getDay());
					int day1 = Integer.parseInt(daily.getDailyRentPanel().getDay1());
					int tempNumber;
					if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
						tempNumber = daysNumber[month-1]-day;
						for(int i=month; i<month1-1; i++) {
							tempNumber = daysNumber[i]+tempNumber;
						}
						tempNumber = tempNumber + day1;
						totalRent = tempNumber*200*bikes.length;
					}else {
						tempNumber = daysNumber1[month-1]-day;
						for(int i=month; i<month1-1; i++) {
							tempNumber = daysNumber1[i]+tempNumber;
						}
						tempNumber = tempNumber + day1;
						totalRent = tempNumber*200*bikes.length;
					}
				}
			}else {
				int year = Integer.parseInt(daily.getDailyRentPanel().getYear());
				int year1 = Integer.parseInt(daily.getDailyRentPanel().getYear1());
				int month = Integer.parseInt(daily.getDailyRentPanel().getMonth());
				int month1 = Integer.parseInt(daily.getDailyRentPanel().getMonth1());
				int day =  Integer.parseInt(daily.getDailyRentPanel().getDay());
				int day1 =  Integer.parseInt(daily.getDailyRentPanel().getDay1());
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
			
				for (int j = year+1; j < year1; j++) {
					if (((j % 4 == 0) && (j % 100 != 0)) || (j% 400 == 0)) {
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
					totalRent = tempNumber * 200*bikes.length;
				} else {
					for (int i = 0; i < month1-1; i++) {
						tempNumber = daysNumber1[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = tempNumber * 200*bikes.length;
				}
			}	
		}else if(leftMenu.getSelectedIndex()==2) {
			if(Integer.parseInt(weekly.getWeeklyRentPanel().getYear())==
			Integer.parseInt(weekly.getWeeklyRentPanel().getYear1())) {
				if(Integer.parseInt(weekly.getWeeklyRentPanel().getMonth())==
						Integer.parseInt(weekly.getWeeklyRentPanel().getMonth1())) {
					totalRent = ((Integer.parseInt(weekly.getWeeklyRentPanel().getDay1())-
							Integer.parseInt(weekly.getWeeklyRentPanel().getDay()))/7)*600;
				}else {
					int year = Integer.parseInt(weekly.getWeeklyRentPanel().getYear());
					int month = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth());
					int month1 = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth1());
					int day =  Integer.parseInt(weekly.getWeeklyRentPanel().getDay());
					int day1 =  Integer.parseInt(weekly.getWeeklyRentPanel().getDay1());
					int tempNumber;
					if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
						tempNumber = daysNumber[month-1]-day;
						for(int i=month; i<month1-1; i++) {
							tempNumber = daysNumber[i]+tempNumber;
						}
						tempNumber = tempNumber + day1;
						totalRent = (tempNumber/7)*600*bikes.length;
					}else {
						tempNumber = daysNumber1[month-1]-day;
						for(int i=month; i<month1-1; i++) {
							tempNumber = daysNumber1[i]+tempNumber;
						}
						tempNumber = tempNumber + day1;
						totalRent = (tempNumber/7)*600*bikes.length;
					}
				}
			}else {
				int year = Integer.parseInt(weekly.getWeeklyRentPanel().getYear());
				int year1 = Integer.parseInt(weekly.getWeeklyRentPanel().getYear1());
				int month = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth());
				int month1 = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth1());
				int day =  Integer.parseInt(weekly.getWeeklyRentPanel().getDay());
				int day1 = Integer.parseInt(weekly.getWeeklyRentPanel().getDay1());
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
				
				for (int j = year+1; j < year1; j++) {
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
					totalRent = (tempNumber/7)* 600*bikes.length;
				} else {
					for (int i = 0; i < month1-1; i++) {
						tempNumber = daysNumber1[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					totalRent = (tempNumber/7) * 600*bikes.length;
				}
			}	
			
		}
		
	}
	/**
	 * 
	 * @Description return the rental bike items
	 * @author Victor
	 * @date 2020骞�11鏈�30鏃ヤ笅鍗�6:50:422020骞�11鏈�30鏃�
	 *
	 */
	public BikeInfo[] getBikeItems() {
		return bikeItems;
	}

	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020骞�11鏈�26鏃ヤ笅鍗�2:56:532020骞�11鏈�26鏃�
	 *
	 */
	private boolean isSelectionValid() {
		if (leftMenu.getSelectedIndex() == 0) {
			if ((Integer.parseInt(hourly.getHourlyRentPanel().getYear()) == thisYear)
					&& (Integer.parseInt(hourly.getHourlyRentPanel().getMonth()) < thisMonth)) {
				JOptionPane.showMessageDialog(this, "Please select the correct month");
				return false;
			} else if (Integer.parseInt(hourly.getHourlyRentPanel().getStartTime().substring(0,
					hourly.getHourlyRentPanel().getStartTime().indexOf(':'))) >= Integer
							.parseInt(hourly.getHourlyRentPanel().getEndTime().substring(0,
									hourly.getHourlyRentPanel().getEndTime().indexOf(':')))) {
				JOptionPane.showMessageDialog(this, "The end time should be greater than the start time");
				return false;
			}
		} else if (leftMenu.getSelectedIndex() == 1) {
			if (Integer.parseInt(daily.getDailyRentPanel().getYear()) > Integer
					.parseInt(daily.getDailyRentPanel().getYear1())) {
				JOptionPane.showMessageDialog(this, "Please select the correct year");
				return false;
			} else if (Integer.parseInt(daily.getDailyRentPanel().getYear()) == Integer
					.parseInt(daily.getDailyRentPanel().getYear1())) {
				if (Integer.parseInt(daily.getDailyRentPanel().getMonth()) > Integer
						.parseInt(daily.getDailyRentPanel().getMonth1())) {
					JOptionPane.showMessageDialog(this, "Please select the correct month");
					return false;
				} else if ((Integer.parseInt(daily.getDailyRentPanel().getMonth()) == Integer
						.parseInt(daily.getDailyRentPanel().getMonth1()))
						&& (Integer.parseInt(daily.getDailyRentPanel().getDay()) > Integer
								.parseInt(daily.getDailyRentPanel().getDay1()))) {
					JOptionPane.showMessageDialog(this, "Please select the correct day");
					return false;
				}else if((Integer.parseInt(daily.getDailyRentPanel().getMonth()) == Integer
						.parseInt(daily.getDailyRentPanel().getMonth1()))
						&& (Integer.parseInt(daily.getDailyRentPanel().getDay()) < today)){
					JOptionPane.showMessageDialog(this, "Please select the correct start day");
					return false;
				}
			}
		} else if (leftMenu.getSelectedIndex() == 2) {
			if (Integer.parseInt(weekly.getWeeklyRentPanel().getYear()) == Integer
					.parseInt(weekly.getWeeklyRentPanel().getYear1())) {
				if (Integer.parseInt(weekly.getWeeklyRentPanel().getMonth()) == Integer
						.parseInt(weekly.getWeeklyRentPanel().getMonth1())) {
					if ((Integer.parseInt(weekly.getWeeklyRentPanel().getDay1())
							- Integer.parseInt(weekly.getWeeklyRentPanel().getDay())) % 7 != 0) {
						JOptionPane.showMessageDialog(this, "Please select the correct number of days");
						return false;
					}
				} else if (Integer.parseInt(weekly.getWeeklyRentPanel().getMonth()) > Integer
						.parseInt(weekly.getWeeklyRentPanel().getMonth1())) {
					JOptionPane.showMessageDialog(this, "Please select the correct month interval");
					return false;
				} else {
					switch (Integer.parseInt(weekly.getWeeklyRentPanel().getMonth())) {
					case 1:
						daysNumberOfMonth = 31;
						break;
					case 2:
						if (((thisYear % 4 == 0) && (thisYear % 100 != 0)) || (thisYear % 400 == 0))
							daysNumberOfMonth = 29;
						else
							daysNumberOfMonth = 28;
						break;
					case 3:
						daysNumberOfMonth = 31;
						break;
					case 4:
						daysNumberOfMonth = 30;
						break;
					case 5:
						daysNumberOfMonth = 31;
						break;
					case 6:
						daysNumberOfMonth = 30;
						break;
					case 7:
						daysNumberOfMonth = 31;
						break;
					case 8:
						daysNumberOfMonth = 31;
						break;
					case 9:
						daysNumberOfMonth = 30;
						break;
					case 10:
						daysNumberOfMonth = 31;
						break;
					case 11:
						daysNumberOfMonth = 30;
						break;
					case 12:
						daysNumberOfMonth = 31;
						break;
					}
					if ((daysNumberOfMonth - Integer.parseInt(weekly.getWeeklyRentPanel().getDay())
							+ Integer.parseInt(weekly.getWeeklyRentPanel().getDay1())) % 7 != 0) {
						JOptionPane.showMessageDialog(this, "Please select the correct number of days");
						return false;
					}
				}
			}else {
				int year = Integer.parseInt(weekly.getWeeklyRentPanel().getYear());
				int year1 = Integer.parseInt(weekly.getWeeklyRentPanel().getYear1());
				int month = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth());
				int month1 = Integer.parseInt(weekly.getWeeklyRentPanel().getMonth1());
				int day =  Integer.parseInt(weekly.getWeeklyRentPanel().getDay());
				int day1 = Integer.parseInt(weekly.getWeeklyRentPanel().getDay1());
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
				
				for (int j = year+1; j < year1; j++) {
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
					if(tempNumber%7!=0) {
						JOptionPane.showMessageDialog(this, "Please select the correct number of days");
						return false;
					}
				} else {
					for (int i = 0; i < month1-1; i++) {
						tempNumber = daysNumber1[i] + tempNumber;
					}
					tempNumber = tempNumber + day1;
					if(tempNumber%7!=0) {
						JOptionPane.showMessageDialog(this, "Please select the correct number of days");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @Description initial the main panel of dialog
	 * @author Victor
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�12:37:392020骞�11鏈�24鏃�
	 *
	 */
	private void initialPanel() {
		CardLayout cardLayout = new CardLayout();
		mainPanel.setLayout(cardLayout);
		mainPanel.setOpaque(true);
		mainPanel.setBackground(new Color(0xF4F4F4));
		mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(0xCCCCCC)));
		rentalPanel.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.add(hourly, "panel0");
		mainPanel.add(daily, "panel1");
		mainPanel.add(weekly, "panel2");
		
		selectPanel(0);
	}

	private void selectPanel(int i) {
		String name = "panel"+i;
		CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
		cardLayout.show(mainPanel, name);
		
		if(leftMenu.getSelectedIndex()!=i) {
			leftMenu.setSelectedIndex(i);
		}
	}

	/**
	 * 
	 * @Description initial the left menu of the dialog
	 * @author Victor
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�12:37:362020骞�11鏈�24鏃�
	 *
	 */
	private void initialLeftMenu() {
		DefaultListModel<MenuItem> itemListModel = new DefaultListModel<>();
		itemListModel.addElement(new MenuItem("Hourly Rent(50RMB per hour)"));
		itemListModel.addElement(new MenuItem("Daily Rent(200RMB per day)"));
		itemListModel.addElement(new MenuItem("Weekly Rent(600RMB per week)"));
		
		leftMenu.setModel(itemListModel);
		leftMenu.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		leftMenu.setCellRenderer(new RentalLeftMenuCellRenderer());
		leftMenu.setOpaque(true);
		leftMenu.setBackground(new Color(0x2F4056));
		
		rentalPanel.add(leftMenu, BorderLayout.LINE_START);
	}

	/**
	 * 
	 * @Description set the content pane of the main panel
	 * @author Victor
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�12:24:342020骞�11鏈�24鏃�
	 *
	 */
	private void setContentPane() {
		mainPanel = new JPanel();
		root = new JPanel();
		this.setSize(850, 550);
		this.setContentPane(root);
		root.setLayout(new CustomColumnLayout());
		rentalPanel.setLayout(new BorderLayout());
		rentalPanel.setPadding(3);
		rentalPanel.setMargin(4);
		root.add(rentalPanel, "90%");
	}
	
	/**
	 * 
	 * @Description create the hourly rental panel
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�4:02:54
	 *
	 */
	private class HourlyRentalPanel extends JPanel{
		private static final long serialVersionUID = -1352989902114413917L;
		private HourlyRentPanel hour; 
		
		public HourlyRentalPanel() {
			this.setLayout(new BorderLayout());
			hour = new HourlyRentPanel();
			this.add(hour, BorderLayout.CENTER);
		}
		
		private HourlyRentPanel getHourlyRentPanel() {
			return hour;
		}
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�4:03:13
	 *
	 */
	private class DailyRentalPanel extends JPanel{
		private static final long serialVersionUID = 3250731876403536855L;
		private DailyRentPanel daily;
		public DailyRentalPanel() {
			this.setLayout(new BorderLayout());
			daily = new DailyRentPanel();
			this.add(daily, BorderLayout.CENTER);
		}
		
		private DailyRentPanel getDailyRentPanel() {
			return daily;
		}
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020骞�11鏈�24鏃ヤ笅鍗�4:03:32
	 *
	 */
	private class WeeklyRentalPanel extends JPanel{

		private static final long serialVersionUID = 1749672963509139802L;
		private WeeklyRentPanel weekly;
		public WeeklyRentalPanel() {
			this.setLayout(new BorderLayout());
			weekly = new WeeklyRentPanel();
			this.add(weekly, BorderLayout.CENTER);
		}
		
		private WeeklyRentPanel getWeeklyRentPanel() {
			return weekly;
		}
		
	}
	
}
