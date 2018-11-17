import java.awt.Graphics;

public class GameMap implements Constants{
	private int[][] mapTiles;

	public GameMap(String mapFilePath){
		loadMap(mapFilePath,30,20);
	}

	public void update(){

	}

	public void render(Graphics g){
		for(int y=0; y<20; y++){
			for(int x=0; x<30; x++){
				getTile(x,y).render(g, x*TILE_WIDTH, y*TILE_HEIGHT);
			}
		}
	}

	private void loadMap(String path,int col, int row){
		String mapFile = MapUtils.loadMapFile(path);
		String[] tokens = mapFile.split("\\s+");

		mapTiles = new int[col][row];

		for(int y=0; y<row; y++){
			for(int x=0; x<col; x++){
				mapTiles[x][y] = MapUtils.parseInt(tokens[(x+y*col)]);
			}
		}
	}

	public Tile getTile(int x, int y){
		Tile tile = Tile.tiles[mapTiles[x][y]];
		return tile;
	}
}