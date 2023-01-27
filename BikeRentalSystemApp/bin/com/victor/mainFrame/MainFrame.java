package com.victor.mainFrame;

import java.awt.*;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.victor.CustomControl.CustomShortTask;
import com.victor.BikeDisplayPanel.BikeRentalPanel;
import com.victor.BikeDisplayPanel.WaitDialog;
import com.victor.BikeReturnPanel.BikeReturnPanel;
import com.victor.CustomControl.CustomPanel;
import com.victor.CustomLayout.CustomRowLayout;
import com.victor.JavaBeans.*;
import com.victor.Login.LoginFrame;
import com.victor.Login.Util;

import com.victor.UserCenterPanel.UserPanel;

/**
 * 
 * @Description create the main window
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020楠烇拷11閺堬拷17閺冦儰绗呴崡锟�5:35:06
 *
 */

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 8856063542020395067L;
	private JList<MenuItem> leftMenu = new JList<>();
	private JPanel mainPanel;
	private UserInfo user;
	private LoginFrame frame;
	private WaitDialog waitDialog;
	
	public MainFrame(String title, LoginFrame frame) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.user = frame.getUser();
		this.frame = frame;
		this.setResizable(false);
		waitDialog = new WaitDialog(this);
		
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
	 * @date 2020楠烇拷11閺堬拷17閺冦儰绗呴崡锟�10:51:242020楠烇拷11閺堬拷17閺冿拷
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
		menu.add(logo, "770pixels");
		
		JLabel icon = new JLabel();
		icon.setIcon(Util.loadIcon("/com/victor/Images/user2.png"));
		menu.add(icon, "23pixels");
		
		
		JLabel userName = new JLabel(LoginFrame.getUser().getName());
		userName.setFont(new Font("Arial", Font.PLAIN, 16));
		userName.setForeground(Color.BLACK);
		menu.add(userName, "60pixels");
//		
		JLabel logout = new JLabel();
		logout.setIcon(Util.loadIcon("/com/victor/Images/logout.png"));
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					LogoutThread logout = new LogoutThread();
					logout.start();
					waitDialog.execute();
				}
			}
		});
		menu.add(logout, "23pixels");
		
		this.getContentPane().add(menu, BorderLayout.PAGE_START);
	}

	public class LogoutThread extends CustomShortTask{

		@Override
		protected void doInBackground() throws Exception {
			MainFrame.this.setVisible(false);
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
			if(waitDialog!=null) {
				waitDialog.setVisible(false);
				waitDialog = null;
			}
		}
		
	}
	
	/**
	 * 
	 * @Description initialize the left menu
	 * @author Victor
	 * @date 2020楠烇拷11閺堬拷17閺冦儰绗呴崡锟�6:57:472020楠烇拷11閺堬拷17閺冿拷
	 *
	 */
	private void initialLeftMenu() {
		DefaultListModel<MenuItem> itemListModel = new DefaultListModel<>();
		itemListModel.addElement(new MenuItem("Rent Bike"));
		itemListModel.addElement(new MenuItem("Return Bike"));
		itemListModel.addElement(new MenuItem("Personal Setting"));
		
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
	 * @date 2020楠烇拷11閺堬拷17閺冦儰绗呴崡锟�6:59:532020楠烇拷11閺堬拷17閺冿拷
	 *
	 */
	private void initialPanel() {
		
		CardLayout cardLayout = new CardLayout();
		mainPanel.setLayout(cardLayout);
		mainPanel.setOpaque(true);
		mainPanel.setBackground(new Color(0xF4F4F4));
		mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(0xCCCCCC)));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		UserPanel userPanel = new UserPanel(frame, this);
		BikeReturnPanel bikeReturnPanel = new BikeReturnPanel(this, user, frame, userPanel);
		BikeRentalPanel bikeRentalPanel=new BikeRentalPanel(this, user, frame, bikeReturnPanel);
	
		mainPanel.add(bikeRentalPanel, "panel0");
		mainPanel.add(bikeReturnPanel, "panel1");
		mainPanel.add(userPanel, "panel2");
		
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
	 * @date 2020楠烇拷11閺堬拷17閺冦儰绗呴崡锟�5:43:392020楠烇拷11閺堬拷17閺冿拷
	 *
	 */
	private void setContentPane() {
		mainPanel = new JPanel();
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
	}
}
