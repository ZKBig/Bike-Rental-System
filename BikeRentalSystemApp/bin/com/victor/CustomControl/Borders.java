package com.victor.CustomControl;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * 
 * @Description A utility class of helping to set the padding and margin quickly
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月10日下午1:12:52
 *
 */

public class Borders {
	//set padding of which sizes of four sides are the same 
	public static void setPadding(JComponent c, int size) {
		setPadding(c, size, size, size, size);
	}
	//set padding of which sizes of four sides are different
    public static void setPadding(JComponent c, int top, int left, int bottom, int right) {
		Border border = BorderFactory.createEmptyBorder(top, left, bottom, right);
		setInnerBorder(c, border);
	}
    
    //set margin of which sizes of four sides are the same 
    public static void setMargin(JComponent c, int size) {
    	setMargin(c, size, size, size, size);
    }
    
    //set margin of which sizes of four sides are different
    public static void setMargin(JComponent c, int top, int left, int bottom, int right) {
    	Border border = BorderFactory.createEmptyBorder(top, left, bottom, right);
    	setOuterBorder(c, border);
    }
    
	public static void setOuterBorder(JComponent c, Border border) {
		Border b = c.getBorder();
		if(b!=null) {
			//If there is a border in the original component, then compound border.
			b = BorderFactory.createCompoundBorder(border, b);
			c.setBorder(b);
		}else {
			c.setBorder(border);
		}
		
	}
	
	public static void setInnerBorder(JComponent c, Border border) {
		Border b = c.getBorder();
		if(b!=null) {
			//If there is a border in the original component, then compound border.
			b = BorderFactory.createCompoundBorder(b, border);
			c.setBorder(b);
		}else {
			c.setBorder(border);
		}
	}

}
