package com.victor.CustomControl;

import java.awt.Rectangle;

public class ImageScaler {
	//输入参数
		private int imageWidth, imageHeight; //图片的宽度、高度
		private Rectangle rect; //要绘制的目标区域
		
		public ImageScaler(int imageWidth, int imageHeight, Rectangle rect) {
			this.imageHeight = imageHeight;
			this.imageWidth = imageWidth;
			this.rect = rect;
		}
		
		public ImageScaler(int imageWidth, int imageHeight, int dstWidth, int dstHeight) {
			this(imageWidth, imageHeight, new Rectangle(dstWidth, dstHeight));
		}
		
		//拉伸显示，占满空间，但是比例存在失调的情况
		public Rectangle fitXY() {
			return this.rect;
		}
		
		//居中显示，保持长宽比，且适合目标矩形
		public Rectangle fitCenter() {
			int width = rect.width;
			int height = rect.height;
			
			int fitW = width;
			int fitH = width * imageHeight / imageWidth;
			if( fitH > height )
			{
				fitH = height;
				fitW = height * imageWidth / imageHeight;
			}
			int x = (width - fitW ) /2;
			int y = (height - fitH ) /2;
			
			return new Rectangle(rect.x + x, rect.y + y, fitW, fitH);
		}
		
		//如果图片小于目标矩形，则直接居中显示
		//如果图片大于目标矩形，则按照fitCenter()缩放后显示
		public Rectangle fitCenterInside() {
			int width = rect.width;
			int height = rect.height;
			int fitWidth, fitHeight;
			
			if(imageWidth<=width && imageHeight<= height) {
				fitWidth = imageWidth;
				fitHeight = imageHeight;
				
				int x = (width - fitWidth)/2;
				int y =(height - fitHeight)/2;
				 
				return new Rectangle(rect.x+x, rect.y+y, fitWidth, fitHeight);
			}else {
				return fitCenter();
			}
		}


}
