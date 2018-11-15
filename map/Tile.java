import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile{
	//each element of the array corresponds to a specific tile type
	public static Tile[] tiles = new Tile[5];

	//defines the tile types and their id's
	public static Tile roadTile = new RoadTile(0);
	public static Tile brickTile = new BrickTile(1);
	public static Tile grassTile = new GrassTile(2);
	public static Tile waterTile = new WaterTile(3);
	public static Tile steelTile = new SteelTile(4);

	public static final int TILEWIDTH = 30, TILEHEIGHT = 30;
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
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT,null);
	}

	public int getId(){
		return id;
	}

	public boolean isPassable(){
		return true;
	}
}