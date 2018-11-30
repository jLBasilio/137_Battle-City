package com.main.app;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class ImageLoader{
	public static BufferedImage loadImage(String srcPath){
		try{
			return ImageIO.read(new File(srcPath));
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}