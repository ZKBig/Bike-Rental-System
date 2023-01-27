package com.victor.BikeRentalDialog;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;


/**
 * 
 * @Description 
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月5日上午10:51:08
 *
 */
public class DailyRentPanel extends JPanel{

	private static final long serialVersionUID = -1717019270198119283L;
	private JPanel mainPanel;
	private CustomPanel topPanel;
	private JPanel bottomPanel;
	private JLabel[] days = new JLabel[42];
	private JButton[] weeks = new JButton[7];
	private String[] weekName = {
			"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
	};
	private JButton next;
	private JButton last;
	private JComboBox year, month, day;
	private JComboBox year1, month1, day1;
	private JLabel yearLabel, monthLabel, dayLabel;
	private JLabel yearLabel1, monthLabel1, dayLabel1;
	private CalendarBean calendar;
	private int yearNumber = Calendar.getInstance().get(Calendar.YEAR);
	private int monthNumber = Calendar.getInstance().get(Calendar.MONTH)+1;
	private String[] tempA = new String[31];
	private String[] tempB = new String[31];
	
	public DailyRentPanel() {
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
		
		year1 = new JComboBox();
		month1 = new JComboBox();
		day1 = new JComboBox();
		
		yearLabel = new JLabel("year");
		monthLabel = new JLabel("month");
		dayLabel = new JLabel("day");
		
		yearLabel1 = new JLabel("year");
		monthLabel1 = new JLabel("month");
		dayLabel1 = new JLabel("day");
		
		
		for(int i=2020; i<2050; i++) {
			year.addItem(i);
			year1.addItem(i);
		}
		
		year.addActionListener((e)->{
			calendar.setYear((Integer)year.getSelectedItem());
			String[] days5 = calendar.getCalendar();
			for(int i=0; i<42; i++) {
				days[i].setText(days5[i]);
			}
		});
		
		year1.addActionListener((e)->{
			calendar.setYear((Integer)year1.getSelectedItem());
			String[] days5 = calendar.getCalendar();
			for(int i=0; i<42; i++) {
				days[i].setText(days5[i]);
			}
		});
		
		for(int j=1; j<13; j++) {
			month.addItem(j);
			month1.addItem(j);
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
		
		
		month1.addActionListener((e)->{
			calendar.setMonth((Integer)month1.getSelectedItem());
			String[] days6 = calendar.getCalendar();
			for(int k=0; k<tempB.length; k++) {
				tempB[k]=null;
			}
			for(int i=0, j=0; i<42; i++) {
				days[i].setText(days6[i]);
				if(days6[i]!=null) {
					tempB[j++]=days6[i];
				}
			}
			day1.setModel(new DefaultComboBoxModel(tempB));
		});
		
		JPanel top1 = new JPanel();
		JPanel top2 = new JPanel();
		topPanel.setLayout(new CustomColumnLayout());
		topPanel.setPreferrenHeight(80);
		topPanel.add(top1, "50%");
		topPanel.add(top2, "50%");
		
		top1.add(year);
		top1.add(yearLabel);
		top1.add(month);
		top1.add(monthLabel);
		top1.add(day);
		top1.add(dayLabel);

		top2.add(year1);
		top2.add(yearLabel1);
		top2.add(month1);
		top2.add(monthLabel1);
		top2.add(day1);
		top2.add(dayLabel1);
		
		month.setSelectedItem(Calendar.getInstance().get(Calendar.MONTH)+1);
		day.setSelectedItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		month1.setSelectedItem(Calendar.getInstance().get(Calendar.MONTH)+1);
		day1.setSelectedItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		setYearAndMonth(yearNumber, monthNumber);
	}
	
	public String getMonth() {
		return String.valueOf(month.getSelectedItem());
	}
	
	public String getDay() {
		return String.valueOf(day.getSelectedItem());
	}
	
	public String getYear() {
		return String.valueOf(year.getSelectedItem());
	}
	
	public String getMonth1() {
		return String.valueOf(month1.getSelectedItem());
	}
	
	public String getDay1() {
		return String.valueOf(day1.getSelectedItem());
	}
	
	public String getYear1() {
		return String.valueOf(year1.getSelectedItem());
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

