package com.victor.Login;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.json.*;

/**
 * 
 * @Description util classes of the project
 * @author Victor Wang Email:1779408741@qq.com
 * @version
 * @date 2020年12月11日下午12:13:56
 *
 */
public class Util
{
	public static Image loadImage(String path)
	{
		try {
			URL url = Util.class.getResource(path);
			System.out.println(url);
			return ImageIO.read(url);
		}catch(Exception e)
		{
			System.out.println("无法加载图片 : " + path);
			e.printStackTrace();
			return null;
		}
	}

	public static Icon loadIcon(String path) {
		Icon icon = new ImageIcon(Util.class.getResource(path));
		return icon;
	}
	
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	/**
	 * 
	 * @Description write data to the file
	 * @author Victor
	 * @date 2020年11月13日上午11:58:052020年11月13日
	 *
	 */
	public static void WriteToFile(Object json, File file, String encoding) throws Exception {
		String jsonString = null;
		//set the indentation number is 2.
		if(json instanceof JSONObject) {
			jsonString  = ((JSONObject)json).toString(2);
		}else if(json instanceof JSONArray) {
			jsonString  = ((JSONArray)json).toString(2);
		}else {
			throw new Exception("The object should be JSONObject or JSONArray");
		}
		
		//write the data to the file
		OutputStream outputStream = new FileOutputStream(file);
		try {
			encoding = encoding.toUpperCase();
			if (encoding.equals("UTF-8")) {
				byte[] bytes = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
				outputStream.write(bytes);
			}
			byte[] data = jsonString.getBytes(encoding);
			outputStream.write(data);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @Description read data from the file 
	 * @author Victor
	 * @date 2020年11月13日上午11:57:592020年11月13日
	 *
	 */
	public static Object readFromFile(File file, String encoding) throws Exception {
		InputStream inputStream = new FileInputStream(file);
		try {
			int fileSize = (int) file.length();
			byte[] data = new byte[fileSize];
			int b = inputStream.read(data);
			int offset = 0;
			encoding = encoding.toUpperCase();
			if (b > 3 && encoding.equals("UTF-8")) {
				if (data[0] == (byte) 0xEF && data[1] == (byte) 0xBB && data[2] == (byte) 0xBF) {
					offset = 3;
				}
			}
			String jsonString = new String(data, offset, b - offset, encoding);
			char firstChar = ' ';
			for (int i = 0; i < jsonString.length(); i++) {
				firstChar = jsonString.charAt(i);
				if (firstChar != ' ' && firstChar != '\t' && firstChar != '\n' && firstChar != '\r')
					break;
			}
			if (firstChar == '{') {
				return new JSONObject(jsonString);
			} else if (firstChar == '[') {
				return new JSONArray(jsonString);
			} else {
				throw new Exception("JSON must begin with { or [.");
			}
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

