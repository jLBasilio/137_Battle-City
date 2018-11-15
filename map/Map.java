import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Map implements Runnable{
	private MapFrame map;
	private String title;
	private int width, height;
	private boolean running = false;
	private Thread mThread;

	private BufferStrategy bs;
	private Graphics g;

	private int[][] mapTiles;

	private Player p1;

	private int dir=2,tx=0,ty=0,xspeed=5,yspeed=5,prevX,prevY;

	//constructor
	public Map(String title,int width,int height){
		this.title = title;
		this.width = width;
		this.height = height;
	}

	//initializes the map window, loads the spritesheets and map file
	private void init(){
		map = new MapFrame(this.title,this.width,this.height);
		map.getFrame().addKeyListener(new KeyHandler());
		Assets.init();
		loadMap("city1.map",30,20); //(file path, col, row)
		p1 = new Player(0,0,30,30);
	}


	private void update(){

	}

	private void render(){
		bs = map.getCanvas().getBufferStrategy();
		if(bs == null){
			map.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		//draw here
		for(int y=0;y<20;y++){
			for(int x=0;x<30;x++){
				//by multiplying the coordinates to the tile width and height
				//we can define the size of the image/tile rendered
				getTile(x,y).render(g,x*Tile.TILEWIDTH,y*Tile.TILEHEIGHT);
			}
		}

		p1.render(g,tx,ty,dir);
		// System.out.println(tx+":"+ty);
		//draw here

		bs.show();
		g.dispose();
	}

	public void run(){
		init();

		while(running){
			update();
			render();
		}
		// stop();
	}

	//create/start thread
	public synchronized void start(){
		if(running)
			return;

		running = true;
		mThread = new Thread(this);
		mThread.start();
	}

	// public synchronized void stop(){
	// 	if(!running)
	// 		return;
	// 	running = false;
	// 	try{
	// 		mThread.join();
	// 	}catch(InterruptedException e){
	// 		e.printStackTrace();
	// 	}
	// }

	//gets tiles from Tile class using tile id's stored in mapTiles
	public Tile getTile(int x, int y){

		Tile t = Tile.tiles[mapTiles[x][y]];
		if(t == null){
			return Tile.roadTile;
		}			
		return t;
	}

	//loads map file
	private void loadMap(String path,int col,int row){
		String mapFile = MapUtils.loadMapFile(path);
		String[] tokens = mapFile.split("\\s+"); //split tokens(tile id's) using spaces

		mapTiles = new int[col][row];
		for(int y=0;y<row;y++){
			for(int x=0;x<col;x++){
				//parse tokens(tile id's) into integers and store into a 2d array
				mapTiles[x][y] = MapUtils.parseInt(tokens[(x+y*col)]);
			}
		}
	}



	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=tx;prevY=ty;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_UP:{				
				if((ty-yspeed) >= 0){
					ty-=yspeed;
				}

				dir=0;
				break;
				}
			case KeyEvent.VK_RIGHT:{				
				if((tx+xspeed) <= 870){
					tx+=xspeed;
				}

				dir=1;
				break;
			}
			case KeyEvent.VK_DOWN:{				
				if((ty+yspeed) <= 570){
					ty+=yspeed;
				}

				dir=2;
				break;
			}
			case KeyEvent.VK_LEFT:{				
				if((tx-xspeed) >=0){
					tx-=xspeed;
				}

				dir=3;
				break;
			}
			}
		}
	}
}