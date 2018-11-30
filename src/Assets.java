package com.main.app;

import java.awt.image.BufferedImage;

public class Assets{
	private static final int tileWidth = 168, tileHeight = 166;
	private static final int tankWidth = 182, tankHeight = 182;
	private static final int powUpWidth = 108, powUpHeight = 108;
	public static BufferedImage brick, road, grass, water, steel;
	public static BufferedImage tankU, tankR, tankD, tankL;
	public static BufferedImage wallDestroyer, powerArmor, timeSkip;

	public static void initialize(){
		
		SpriteSheet tileSprites = new SpriteSheet(ImageLoader.loadImage("assets/tileSprites.png"));
		SpriteSheet tankSprites = new SpriteSheet(ImageLoader.loadImage("assets/tankSprites.png"));
		SpriteSheet powUpSprites = new SpriteSheet(ImageLoader.loadImage("assets/powerArmor.png"));

		brick = tileSprites.cropImg(0,0,tileWidth,tileHeight);
		road = tileSprites.cropImg(tileWidth,0,tileWidth,tileHeight);
		grass = tileSprites.cropImg(tileWidth*2,0,tileWidth,tileHeight);
		water = tileSprites.cropImg(tileWidth*3,0,tileWidth,tileHeight);
		steel = tileSprites.cropImg(tileWidth*4,0,tileWidth,tileHeight);

		tankU = tankSprites.cropImg(0,0,tankWidth,tankHeight);
		tankR = tankSprites.cropImg(tankWidth,0,tankWidth,tankHeight);
		tankD = tankSprites.cropImg(0,tankHeight,tankWidth,tankHeight);
		tankL = tankSprites.cropImg(tankWidth,tankHeight,tankWidth,tankHeight);

		wallDestroyer = powUpSprites.cropImg(0,0,powUpWidth,powUpHeight);
		powerArmor = powUpSprites.cropImg(0,0,powUpWidth,powUpHeight);
		timeSkip = powUpSprites.cropImg(0,0,powUpWidth,powUpHeight);
	}
}