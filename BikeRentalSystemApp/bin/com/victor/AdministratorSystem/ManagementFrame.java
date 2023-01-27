package com.victor.AdministratorSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.victor.CustomControl.*;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;
import com.victor.mainFrame.LeftMenuCellRenderer;
import com.victor.mainFrame.MenuItem;

public class ManagementFrame extends JFrame{
	private static final long serialVersionUID = 8856063542020395067L;
	private JList<MenuItem> leftMenu = new JList<>();
	private JPanel mainPanel;
	
	public ManagementFrame(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		setContentPane();
		
		initialTopMenu();
		
		initialLeftMenu();
		
		initialPanel();
		
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
	/**
	 * 
	 * @Description initial tool bar in the main frame
	 * @author Victor
	 * @date 2020骞�11鏈�17鏃ヤ笅鍗�10:51:242020骞�11鏈�17鏃�
	 *
	 */
	private void initialTopMenu() {
		CustomPanel menu = new CustomPanel(this.getContentPane().getWidth(), this.getContentPane().getHeight()/10);
		menu.setPreferrenHeight(37);
		menu.setLayout(new CustomRowLayout(10));
		menu.setOpaque(true);
		menu.setBackgroundColor(Color.white);
		
		JLabel logo = new JLabel();
		logo.setIcon(Util.loadIcon("/com/victor/Images/logo.png"));
		menu.add(logo, "950pixels");
		
		JLabel logout = new JLabel();
		logout.setIcon(Util.loadIcon("/com/victor/Images/logout.png"));
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					LogoutThread logout = new LogoutThread();
					logout.start();
				}
			}
		});
		menu.add(logout, "23pixels");
		
		this.getContentPane().add(menu, BorderLayout.PAGE_START);
	}

	public class LogoutThread extends CustomShortTask{

		@Override
		protected void doInBackground() throws Exception {
			ManagementFrame.this.setVisible(false);
			LoginFrame frame = new LoginFrame("Bicycle Rental System");
			frame.setVisible(true);
			frame.setSize(430, 330);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width-frame.getWidth())/2;
			int y = (screenSize.height-frame.getHeight())/2;
			frame.setLocation(x, y);
		}
		@Override
		protected void done() {
		}
		
	}
	
	/**
	 * 
	 * @Description initialize the left menu
	 * @author Victor
	 * @date 2020骞�11鏈�17鏃ヤ笅鍗�6:57:472020骞�11鏈�17鏃�
	 *
	 */
	private void initialLeftMenu() {
		DefaultListModel<MenuItem> itemListModel = new DefaultListModel<>();
		itemListModel.addElement(new MenuItem("Bike Management"));
		itemListModel.addElement(new MenuItem("User Management"));
		
		leftMenu.setModel(itemListModel);
		leftMenu.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		leftMenu.setCellRenderer(new LeftMenuCellRenderer());
		leftMenu.setOpaque(true);
		leftMenu.setBackground(new Color(0x2F4056));
		
		JScrollPane listScrollPane = new JScrollPane(leftMenu);
		this.getContentPane().add(listScrollPane, BorderLayout.LINE_START);
	}
	
	/**
	 * 
	 * @Description initialize the main panel of the main frame
	 * @author Victor
	 * @date 2020骞�11鏈�17鏃ヤ笅鍗�6:59:532020骞�11鏈�17鏃�
	 *
	 */
	private void initialPanel() {
		
		CardLayout cardLayout = new CardLayout();
		mainPanel.setLayout(cardLayout);
		mainPanel.setOpaque(true);
		mainPanel.setBackground(new Color(0xF4F4F4));
		mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(0xCCCCCC)));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		BikeListPanel bikeListPanel = new BikeListPanel(this);
		UserListPanel userListPanel=new UserListPanel(this);
	
		mainPanel.add(bikeListPanel, "panel0");
		mainPanel.add(userListPanel, "panel1");
		
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
	 * @Description set the content pane of the main frame
	 * @author Victor
	 * @date 2020骞�11鏈�17鏃ヤ笅鍗�5:43:392020骞�11鏈�17鏃�
	 *
	 */
	private void setContentPane() {
		mainPanel = new JPanel();
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
	}

}
