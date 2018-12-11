package com.main.app;

import java.awt.image.BufferedImage;

public class Assets{
	private static final int tileWidth = 168, tileHeight = 166;
	private static final int tankWidth = 182, tankHeight = 182;
	private static final int defPowUpWidth = 108, defPowUpHeight = 108;
	private static final int atkPowUpWidth = 107, atkPowUpHeight = 108;
	private static final int msPowUpWidth = 111, msPowUpHeight = 108;
	private static final int bulletWidth = 64, bulletHeight = 64;
	public static BufferedImage brick, road, grass, water, steel;
	public static BufferedImage tankU, tankR, tankD, tankL;
	public static BufferedImage wallDestroyer, powerArmor, timeSkip;
	public static BufferedImage bullet;

	public static void initialize(){
		
		SpriteSheet tileSprites = new SpriteSheet(ImageLoader.loadImage("assets/tileSprites.png"));
		SpriteSheet tankSprites = new SpriteSheet(ImageLoader.loadImage("assets/tankSprites.png"));
		SpriteSheet powUpDefSprite = new SpriteSheet(ImageLoader.loadImage("assets/powerArmor.png"));
		SpriteSheet powUpAtkSprite = new SpriteSheet(ImageLoader.loadImage("assets/wallDestroyer.PNG"));
		SpriteSheet powUpMsSprite = new SpriteSheet(ImageLoader.loadImage("assets/timeSkip.PNG"));
		SpriteSheet bulletSprite = new SpriteSheet(ImageLoader.loadImage("assets/bullet.png"));

		brick = tileSprites.cropImg(0,0,tileWidth,tileHeight);
		road = tileSprites.cropImg(tileWidth,0,tileWidth,tileHeight);
		grass = tileSprites.cropImg(tileWidth*2,0,tileWidth,tileHeight);
		water = tileSprites.cropImg(tileWidth*3,0,tileWidth,tileHeight);
		steel = tileSprites.cropImg(tileWidth*4,0,tileWidth,tileHeight);

		tankU = tankSprites.cropImg(0,0,tankWidth,tankHeight);
		tankR = tankSprites.cropImg(tankWidth,0,tankWidth,tankHeight);
		tankD = tankSprites.cropImg(0,tankHeight,tankWidth,tankHeight);
		tankL = tankSprites.cropImg(tankWidth,tankHeight,tankWidth,tankHeight);

		wallDestroyer = powUpDefSprite.cropImg(0,0,defPowUpWidth,defPowUpHeight);
		powerArmor = powUpAtkSprite.cropImg(0,0,atkPowUpWidth,atkPowUpHeight);
		timeSkip = powUpMsSprite.cropImg(0,0,msPowUpWidth,msPowUpHeight);

		bullet = bulletSprite.cropImg(0,0,bulletWidth,bulletHeight);
	}
}