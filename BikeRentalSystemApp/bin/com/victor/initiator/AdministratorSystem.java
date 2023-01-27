package com.victor.initiator;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.victor.AdministratorSystem.AdministratorLoginFrame;
public class AdministratorSystem {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});

	}

	/**
	 * 
	 * @Description create the graphical user interface
	 * @author Victor
	 * @date 2020年11月20日下午6:24:042020年11月20日
	 *
	 */
	private static void createGUI() {
		JFrame frame = new AdministratorLoginFrame("Swing Demo");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
//			frame.setSize(800, 500);
		frame.setVisible(true);
	}

}
