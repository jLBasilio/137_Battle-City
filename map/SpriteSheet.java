import java.awt.image.BufferedImage;

public class SpriteSheet{
	private BufferedImage sprites;

	public SpriteSheet(BufferedImage sprites){
		this.sprites = sprites;
	}

	//crops the spritesheet image
	public BufferedImage cropImg(int x, int y, int width, int height){
		return sprites.getSubimage(x,y,width,height);
	}
}