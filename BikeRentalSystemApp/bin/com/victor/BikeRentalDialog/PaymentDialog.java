package com.victor.BikeRentalDialog;
import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomColumnLayout;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.Login.Util;
import com.victor.JavaBeans.*;



/**
 * 
 * @Description the payment dialog including wechet, 
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月30日下午8:38:51
 *
 */ 
public class PaymentDialog extends JDialog{
	private static final long serialVersionUID = -8594656450595891930L;
	private CustomPanel root;
	private String rentMoney;
	private JButton confirmButton;
	private String text;
	private String purseMoney;
	private boolean flag = false;
	
	public PaymentDialog(JFrame owner, String rentMoney, UserInfo user) {
		super(owner, "Paymnet", true);
		this.setResizable(false);
		this.setSize(480, 450);
		this.setAlwaysOnTop(true);
		root = new CustomPanel();
		this.setContentPane(root);
		this.rentMoney = rentMoney;
		this.purseMoney = user.getMoney();
		root.setLayout(new CustomColumnLayout(10));
		root.setPadding(6);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width-this.getWidth())/2;
		int y = (screenSize.height-this.getHeight())/2;
		this.setLocation(x, y);
		
		initialDialog();
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 
	 * @Description initial the payment dialog
	 * @author Victor
	 * @date 2020年11月30日下午9:07:412020年11月30日
	 *
	 */
	private void initialDialog() {
		//create the top label
		JLabel topLabel = new JLabel("Please choose the payment method", JLabel.CENTER);
		Font font = new Font("Arial", Font.BOLD, 25);
		topLabel.setFont(font);
		topLabel.setForeground(Color.BLACK);
		root.add(topLabel, "15%");
		
		//display the money that is needed to payed
		JLabel moneyLabel = new JLabel("   Rent money: "+rentMoney+" RMB");
		Font font1 = new Font("Arial", Font.PLAIN, 15);
		moneyLabel.setFont(font1);
		moneyLabel.setForeground(Color.black);
		root.add(moneyLabel, "5%");
		
		setPaymentSelectionButtons();
		
		setBottomButton();
	}

	/**
	 * 
	 * @Description set the bottom button
	 * @author Victor
	 * @date 2020年11月30日下午9:22:342020年11月30日
	 *
	 */
	private void setBottomButton() {
		CustomPanel bottomButton = new CustomPanel();
		bottomButton.setMargin(10);
		int width = bottomButton.getWidth();
		root.add(bottomButton, "18%");
		bottomButton.setLayout(new BorderLayout());
		confirmButton = new JButton("Confirm Pay");
		confirmButton.setBackground(new Color(0xfbac37));
		confirmButton.setForeground(Color.white);
		confirmButton.setOpaque(true);
		confirmButton.setBorderPainted(false);
		confirmButton.setFont(new Font("Arial", Font.PLAIN, 16));
		confirmButton.setBounds((width-bottomButton.getWidth())/2, 0, 600, 400);
		bottomButton.add(confirmButton);
		
		confirmButton.addActionListener((e)->{
			if(text==null) {
				JOptionPane.showMessageDialog(this, "Please select one payment method");
			}else {
				if(text.equals("personal")) {
					int select=JOptionPane.showConfirmDialog(this, "Do you confirm to pay by personal purse?");
					if(select==0) {
						if(Integer.parseInt(purseMoney)<Integer.parseInt(rentMoney)) {
							JOptionPane.showMessageDialog(this, "Balance is insufficient!");
							flag = false;
						}else {
							JOptionPane.showMessageDialog(this, "Pay successfully!");
							flag =true;
						}
					}
				}
				this.setVisible(false);
			}
		});
		
	}

	/**
	 * 
	 * @Description set the display of the payment selection buttons
	 * @author Victor
	 * @date 2020年11月30日下午9:21:462020年11月30日
	 *
	 */
	private void setPaymentSelectionButtons() {
		CustomPanel panel = new CustomPanel();
		panel.setMargin(10);
		root.add(panel, "60%");
		panel.setLayout(new CustomRowLayout());
		JButton personal = new JButton(Util.loadIcon("/com/victor/Images/personal.png"));
		JButton Allipay = new JButton(Util.loadIcon("/com/victor/Images/zhifubao.png"));
		JButton Wechat = new JButton(Util.loadIcon("/com/victor/Images/wechat.png"));
		JButton BankCard = new JButton(Util.loadIcon("/com/victor/Images/bankCard.png"));
		personal.setPreferredSize(new Dimension(125, 125));
		Allipay.setPreferredSize(new Dimension(125, 125));
		Wechat.setPreferredSize(new Dimension(125, 125));
		BankCard.setPreferredSize(new Dimension(125, 125));
		panel.add(personal, "25%");
		panel.add(Allipay, "25%");
		panel.add(Wechat, "25%");
		panel.add(BankCard, "25%");
		
		personal.addActionListener((e)->{
			text = "personal";
		});
		Allipay.addActionListener((e)->{
			text = "Allipay";
		});
		Wechat.addActionListener((e)->{
			text = "Wechat";
		});
		BankCard.addActionListener((e)->{
			text = "BankCard";
		});
	}

}
