package com.victor.BikeDisplayPanel;

import java.awt.Color;



import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


import com.victor.Login.CustomButton;
import com.victor.CustomControl.PopupPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.JavaBeans.*;


/**
 * 
 * @Description create the sort panel
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月7日上午9:07:54
 *
 */
public class SortPopupContentPanel extends PopupPanel
{
	private static final long serialVersionUID = 6670296639338748915L;
	private CustomButton age;
	private CustomButton status;
	private CustomButton number;
	private BikeRentalPanel panel;
	
	public SortPopupContentPanel(BikeRentalPanel panel)
	{
		this.setLayout( new CustomColumnLayout());			
		this.setOpaque(true);
		this.setBackground(new Color(0xFFFFE1));
		this.setPreferredSize(new Dimension(110,80));
		this.panel = panel;
		
		initialButtons();
	}
	
	
	
	/**
	 * 
	 * @Description initial the sort buttons
	 * @author Victor
	 * @date 2020年12月7日上午9:04:432020年12月7日
	 *
	 */
	private void initialButtons() {

		age = new CustomButton("Age");
		age.setPreferredSize(new Dimension(100, 25));
		age.getNormalState().setBackgroundColor(Color.white);
		age.getNormalState().setWordsColor(Color.BLACK);
		age.getPressedState().setBackgroundColor(Color.white);
		age.getPressedState().setWordsColor(null);
		age.setButtonMargin(0);
		age.setButtonPadding(3);
		age.setLength(5);
		age.addActionListener((e)->{
			Collections.sort(panel.bikeList, new Comparator<BikeInfo>() {
				@Override
				public int compare(BikeInfo o1, BikeInfo o2) {
					if(Integer.parseInt(o1.getYears().substring(0, 1))==Integer.parseInt(o2.getYears().substring(0, 1))) {
						return Integer.compare(Integer.parseInt(o1.getID()), Integer.parseInt(o2.getID()));
					}else {
						return  Integer.compare(Integer.parseInt(o1.getYears().substring(0, 1)),Integer.parseInt(o2.getYears().substring(0, 1)));
					}
				}
			});
			
			panel.tableModel.setRowCount(0);
			updateTableData();
		});

		status= new CustomButton("Status");
		status.setPreferredSize(new Dimension(100, 25));
		status.getNormalState().setBackgroundColor(Color.white);
		status.getNormalState().setWordsColor(Color.BLACK);
		status.getPressedState().setBackgroundColor(Color.white);
		status.getPressedState().setWordsColor(null);
		status.setButtonMargin(0);
		status.setButtonPadding(0);
		status.setLength(5);
		status.addActionListener((e)->{
			Collections.sort(panel.bikeList, new Comparator<BikeInfo>() {
				@Override
				public int compare(BikeInfo o1, BikeInfo o2) {
					return -(String.valueOf(o1.getStatus())).compareTo(String.valueOf(o2.getStatus()));
				}
			});
			panel.tableModel.setRowCount(0);
			updateTableData();
		});
		
		number= new CustomButton("Number");
		number.setPreferredSize(new Dimension(100, 25));
		number.getNormalState().setBackgroundColor(Color.white);
		number.getNormalState().setWordsColor(Color.BLACK);
		number.getPressedState().setBackgroundColor(Color.white);
		number.getPressedState().setWordsColor(null);
		number.setButtonMargin(0);
		number.setButtonPadding(0);
		number.setLength(5);
		number.addActionListener((e)->{
			Collections.sort(panel.bikeList, new Comparator<BikeInfo>() {
				@Override
				public int compare(BikeInfo o1, BikeInfo o2) {
					return Integer.compare(Integer.parseInt(o1.getID()), Integer.parseInt(o2.getID()));
				}
			});
			panel.tableModel.setRowCount(0);
			updateTableData();
		});
		
		add(age, "33%");
		add(status, "33%");
		add(number, "33%");
	}

	/**
	 * 
	 * @Description update the display of table
	 * @author Victor
	 * @date 2020年12月7日上午9:25:192020年12月7日
	 *
	 */
	private void updateTableData() {
		for(int i=0; i<panel.bikeList.size(); i++) {
			panel.addTableRow(panel.bikeList.get(i));
		}
	}
}
