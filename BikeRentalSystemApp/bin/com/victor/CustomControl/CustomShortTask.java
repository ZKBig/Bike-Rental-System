package com.victor.CustomControl;

import java.io.IOException;

import javax.swing.SwingUtilities;

/**
 * 
 * @Description 处理短任务的抽象方法
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年11月4日下午7:03:23
 *
 */
public abstract class CustomShortTask extends Thread{
	
	public Object[] args;
	
	public CustomShortTask() {
		super();
	}
	
	public void execute(Object...args) {
		this.args=args;
		this.start();
	}

	@Override
	public void run() {
		try {
			doInBackground();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(()->{
			done();
		});
	}
	
	protected abstract void doInBackground() throws Exception;
	
	protected abstract void done();
	

}
