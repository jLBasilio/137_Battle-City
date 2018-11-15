import java.awt.image.BufferedImage;

public class Assets{
	private static final int tileWidth = 168, tileHeight = 166;
	private static final int tankWidth = 182, tankHeight = 182;
	public static BufferedImage brick, road, grass, water, steel;
	public static BufferedImage tankU, tankR, tankD, tankL;

	public static void init(){
		//loads spritesheet image and create new instance of SpriteSheet class
		SpriteSheet tileSprites = new SpriteSheet(ImageLoader.loadImage("tileSprites.png"));
		SpriteSheet tankSprites = new SpriteSheet(ImageLoader.loadImage("tankSprites.png"));

		//create textures for tiles by getting a subimage of the spritesheet
		brick = tileSprites.cropImg(0,0,tileWidth,tileHeight);
		road = tileSprites.cropImg(tileWidth,0,tileWidth,tileHeight);
		grass = tileSprites.cropImg(tileWidth*2,0,tileWidth,tileHeight);
		water = tileSprites.cropImg(tileWidth*3,0,tileWidth,tileHeight);
		steel = tileSprites.cropImg(tileWidth*4,0,tileWidth,tileHeight);

		tankU = tankSprites.cropImg(0,0,tankWidth,tankHeight);
		tankR = tankSprites.cropImg(tankWidth,0,tankWidth,tankHeight);
		tankD = tankSprites.cropImg(0,tankHeight,tankWidth,tankHeight);
		tankL = tankSprites.cropImg(tankWidth,tankHeight,tankWidth,tankHeight);
	}
}