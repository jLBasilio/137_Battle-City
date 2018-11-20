import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameMap implements Constants{
	private int[][] mapTiles;
	private List<Tile> solidTiles = new ArrayList<Tile>();
	private List<Tile> roadTiles = new ArrayList<Tile>();

	public GameMap(String mapFilePath){
		loadMap(mapFilePath,30,20);
	}

	public void update(){

	}

	public void render(Graphics g){
		for(Tile solidTile : solidTiles)
			solidTile.render(g);

		for(Tile roadTile : roadTiles)
			roadTile.render(g);
	}

	private void loadMap(String path,int col, int row){
		String mapFile = MapUtils.loadMapFile(path);
		String[] tokens = mapFile.split("\\s+");

		mapTiles = new int[col][row];

		for(int y=0; y<row; y++){
			for(int x=0; x<col; x++){
				mapTiles[x][y] = MapUtils.parseInt(tokens[(x+y*col)]);
				if(mapTiles[x][y] > 0)
					solidTiles.add(new Tile(x*TILE_WIDTH,y*TILE_HEIGHT,mapTiles[x][y]));
				else
					roadTiles.add(new Tile(x*TILE_WIDTH,y*TILE_HEIGHT,mapTiles[x][y]));
			}
		}
	}

	public List getTiles(){
		return solidTiles;
	}
}