import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile implements Constants{
	public static Tile[] tiles = new Tile[5];

	public static Tile roadTile = new RoadTile(0);
	public static Tile brickTile = new BrickTile(1);
	public static Tile grassTile = new GrassTile(2);
	public static Tile waterTile = new WaterTile(3);
	public static Tile steelTile = new SteelTile(4);

	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		tiles[this.id] = this;
	}

	public void update(){

	}

	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
	}

	public boolean isPassable(){
		return true;
	}
}