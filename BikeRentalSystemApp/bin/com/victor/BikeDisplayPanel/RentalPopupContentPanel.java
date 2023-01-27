package com.victor.BikeDisplayPanel;

import java.awt.Color;



import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import com.victor.BikeRentalDialog.IndividualRentalDialog;
import com.victor.BikeReturnPanel.BikeReturnPanel;
import com.victor.CustomControl.CustomShortTask;
import com.victor.CustomControl.PopupPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.Login.CustomButton;
import com.victor.Login.LoginFrame;
import com.victor.JavaBeans.*;



/**
 * 
 * @Description customize the popup panel
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月23日下午5:51:06
 *
 */
public class RentalPopupContentPanel extends PopupPanel
{
	private static final long serialVersionUID = 5670296639338748915L;
	private CustomButton individual;
	private CustomButton family;
	private int[] rows;
	private JFrame owner;
	private BikeInfo[] bikeItems;
	private UserInfo user;
	private LoginFrame frame;
	private BikeRentalPanel bikePanel;
	private BikeReturnPanel bikeReturn;
	private WaitDialog waitDialog;
	
	public RentalPopupContentPanel(int[] rows, JFrame owner, BikeInfo[] bikes, UserInfo user, LoginFrame frame, BikeRentalPanel bikePanel, BikeReturnPanel bikeReturn)
	{
		this.owner = owner;
		this.bikeItems=bikes;
		this.user = user;
		this.frame = frame;
		this.bikeReturn = bikeReturn;
		this.setLayout( new CustomColumnLayout());			
		this.setOpaque(true);
		this.setBackground(new Color(0xFFFFE1));
		this.setPreferredSize(new Dimension(110,50));
		this.bikePanel = bikePanel;
		this.rows = rows;
		waitDialog = new WaitDialog(owner);

		individual = new CustomButton("Individual");
		individual.setPreferredSize(new Dimension(100, 25));
		individual.getNormalState().setBackgroundColor(Color.white);
		individual.getNormalState().setWordsColor(Color.BLACK);
		individual.getPressedState().setBackgroundColor(Color.white);
		individual.getPressedState().setWordsColor(null);
		individual.setButtonMargin(0);
		individual.setButtonPadding(3);
		individual.setLength(5);
		individual.addActionListener((e)->{
			showIndividualRentalDialog();
		});

		family= new CustomButton("Family");
		family.setPreferredSize(new Dimension(100, 25));
		family.getNormalState().setBackgroundColor(Color.white);
		family.getNormalState().setWordsColor(Color.BLACK);
		family.getPressedState().setBackgroundColor(Color.white);
		family.getPressedState().setWordsColor(null);
		family.setButtonMargin(0);
		family.setButtonPadding(0);
		family.setLength(5);
		family.addActionListener((e)->{
			if(rows.length<3) {
				JOptionPane.showMessageDialog(this, "Please select at least three bikes");
			}else {
				showFamilyRentalDialog();
			}
		});
		
		add(individual, "50%");
		
		add(family, "50%");
	}

	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	private void showFamilyRentalDialog() {
		Boolean flag = false;
		for(int i=0; i<bikeItems.length; i++) {
			if(bikeItems[i].getStatus()==false) {
				flag = true;
			}
		}
		if(flag) {
			JOptionPane.showMessageDialog(this, "The unavailable bike cannot be selected");
			return;
		}else {
			FamilyRentalThread thread1 = new FamilyRentalThread();
			thread1.start();
			waitDialog.execute();
		}
	}

	private void showIndividualRentalDialog() {
		Boolean flag = false;
		for(int i=0; i<bikeItems.length; i++) {
			if(bikeItems[i].getStatus()==false) {
				flag = true;
			}
		}
		if(flag) {
			JOptionPane.showMessageDialog(this, "The unavailable bike cannot be selected");
			return;
		}else {
			IndividualRentalThread thread = new IndividualRentalThread();
			thread.start();
			
			waitDialog.execute();
		}
		
	}
	
	private class IndividualRentalThread extends CustomShortTask{
		private BikeInfo bike;
		
		public IndividualRentalThread() {
			super();
		}
		
		public BikeInfo getBike() {
			return bike;
		}

		public void setBike(BikeInfo bike) {
			this.bike = bike;
		}

		@Override
		protected void doInBackground() throws Exception {
//			IndividualRentalDialog dialog = new IndividualRentalDialog(owner, bikeItems, frame, user, bikePanel, bikeReturn);
		}

		@Override
		protected void done() {
			IndividualRentalDialog dialog = new IndividualRentalDialog(owner, bikeItems, frame, user, bikePanel, bikeReturn);
			if(waitDialog!=null) {
				waitDialog.setVisible(false);
				waitDialog = null;
			}
			dialog.setVisible(true);
		}
	}
	
	private class FamilyRentalThread extends CustomShortTask{
		private BikeInfo bike;
		
		public FamilyRentalThread() {
			super();
		}
		
		@Override
		protected void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void done() {
			FamilyRentalDialog dialog = new FamilyRentalDialog(owner, bikeItems, frame, user, bikePanel, bikeReturn);
			if(waitDialog!=null) {
				waitDialog.setVisible(false);
				waitDialog = null;
			}
			dialog.setVisible(true);
		}
		
	}
}
