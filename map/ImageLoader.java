import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageLoader{
	public static BufferedImage loadImage(String srcPath){
		try{
			return ImageIO.read(ImageLoader.class.getResource(srcPath));
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}