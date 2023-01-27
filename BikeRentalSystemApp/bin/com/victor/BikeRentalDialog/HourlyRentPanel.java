package com.victor.BikeRentalDialog;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.victor.Login.Util;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;


/**
 * 
 * @Description 
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月5日上午10:50:53
 *
 */
public class HourlyRentPanel extends JPanel{

	private static final long serialVersionUID = -1717019270198119283L;
	private JPanel mainPanel;
	private CustomPanel topPanel;
	private JPanel bottomPanel;
	private JLabel[] days = new JLabel[42];
	private JButton[] weeks = new JButton[7];
	private String[] weekName = {
			"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
	};
	
	private JComboBox year, month, day, startHour, endHour;
	private JLabel yearLabel, monthLabel, dayLabel, startHourLabel;
	private CalendarBean calendar;
	private int yearNumber = Calendar.getInstance().get(Calendar.YEAR);
	private int monthNumber = Calendar.getInstance().get(Calendar.MONTH)+1;
	private String[] tempA = new String[31];
	
	public HourlyRentPanel() {
		mainPanel = new JPanel();
		topPanel = new CustomPanel();
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(topPanel, BorderLayout.PAGE_START);
		
		mainPanel.setLayout(new GridLayout(7,7));
		calendar = new CalendarBean();
		
		for(int i=0; i<7; i++) {
			weeks[i] = new JButton(weekName[i]);
			weeks[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
			mainPanel.add(weeks[i]);
		}
		
		for(int j=0; j<42; j++) {
			days[j] = new JLabel("", JLabel.CENTER);
			days[j].setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
			mainPanel.add(days[j]);
		}
		
		year = new JComboBox();
		month = new JComboBox();
		day = new JComboBox();
		startHour = new JComboBox();
		endHour = new JComboBox();
		
		yearLabel = new JLabel("year");
		monthLabel = new JLabel("month");
		dayLabel = new JLabel("day");
		startHourLabel = new JLabel("to");
		
		for(int i=2020; i<2050; i++) {
			year.addItem(i);
		}
		
		year.addActionListener((e)->{
			calendar.setYear((Integer)year.getSelectedItem());
			String[] days5 = calendar.getCalendar();
			for(int i=0; i<42; i++) {
				days[i].setText(days5[i]);
			}
		});
		
		for(int j=1; j<13; j++) {
			month.addItem(j);
		}
		
		for(int j =0; j<24; j++) {
			startHour.addItem(String.valueOf(j)+":00");
			endHour.addItem(String.valueOf(j)+":00");
		}
		
		month.addActionListener((e)->{
			calendar.setMonth((Integer)month.getSelectedItem());
			String[] days6 = calendar.getCalendar();
			for(int k=0; k<tempA.length; k++) {
				tempA[k]=null;
			}
			
			for(int i=0, j=0; i<42; i++) {
				days[i].setText(days6[i]);
				if(days6[i]!=null) {
					tempA[j++]=days6[i];
				}
			}
			day.setModel(new DefaultComboBoxModel(tempA));
		});
	
		JPanel top1 = new JPanel();
		topPanel.setLayout(new CustomColumnLayout());
		topPanel.setPreferrenHeight(56);
		topPanel.add(top1, "50%");
		
		top1.add(year);
		top1.add(yearLabel);
		top1.add(month);
		top1.add(monthLabel);
		top1.add(day);
		top1.add(dayLabel);
		top1.add(startHour);
		top1.add(startHourLabel);
		top1.add(endHour);

		setYearAndMonth(yearNumber, monthNumber);
		month.setSelectedItem(Calendar.getInstance().get(Calendar.MONTH)+1);
		day.setSelectedItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
	
	
	public String getMonth() {
		return String.valueOf(month.getSelectedItem());
	}
	
	public String getDay() {
		return String.valueOf(day.getSelectedItem());
	}
	
	public String getStartTime() {
		return String.valueOf(startHour.getSelectedItem());
	}
	
	public String getEndTime() {
		return String.valueOf(endHour.getSelectedItem());
	}
	
	public String getYear() {
		return String.valueOf(year.getSelectedItem());
	}
	
	/**
	 * 
	 * @Description
	 * @author Victor
	 * @date 2020年11月24日下午7:14:402020年11月24日
	 *
	 */
	private void setYearAndMonth(int year2, int month2) {
		calendar.setYear(year2);
		calendar.setMonth(month2);
		String days2[] = calendar.getCalendar();
		for(int i =0; i<42; i++) {
			days[i].setText(days2[i]);
			days[i].addMouseListener(new MouseAdapter() {
				
			});
		}
	}
	/**
	 * 
	 * @Description
	 * @author Victor Wang Email:1779408741@qq.com
	 * @version
	 * @date 2020年11月24日下午7:14:44
	 *
	 */
	private class CalendarBean{
		String[] days;
		int year = 2020; 
		int month = 12;
		
		
		public CalendarBean() {
			super();
		}

		public CalendarBean(String[] days, int year, int month) {
			super();
			this.days = days;
			this.year = year;
			this.month = month;
		}
		
		public String[] getDays() {
			return days;
		}
		public void setDays(String[] days) {
			this.days = days;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		
		private String[] getCalendar() {
			String[] days2 = new String[42];
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month-1,1);
			int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
			int day =0;
			
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
				day = 31;
			if (month == 4 || month == 6 || month == 9 || month == 11)
				day = 30;
			if (month == 2) {
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
					day = 29;
				else
					day = 28;
			}
			
			for(int i=week, j = 1; i<week+day; i++) {
				days2[i] = String.valueOf(j++);
			}
			return days2;
		}
		
		
	}
	

}
