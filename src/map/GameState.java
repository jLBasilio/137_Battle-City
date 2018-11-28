import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState extends State{
	private GameMap gameMap;
	private Player player;
	private List<Coordinates> spawnAreas;
	
	public GameState(Handler handler){
		super(handler);

		gameMap =  new GameMap("city1.map");
		handler.setGameMap(gameMap);

		Coordinates spawnLoc = setSpawnLocation();

		player = new Player(handler,spawnLoc.getX(),spawnLoc.getY(),30,30);
	}

	private Coordinates setSpawnLocation(){
		boolean state = false;

		spawnAreas = gameMap.getSpawnAreas();

		Coordinates spawnLoc = new Coordinates(0,0,true);

		while(!state){
			Random r = new Random();
			int x = r.nextInt(30)*30;
			int y = r.nextInt(20)*30;

			System.out.println("random coodinate => " + x + ":" + y);

			for(Coordinates spawnArea: spawnAreas){
				if(spawnArea.compare(x,y)){
					spawnLoc = spawnArea;
					state = true;

					System.out.println("final spawn location => " + x + ":" + y);
				}
			}
		}

		return spawnLoc;
	}

	public void update(){
		gameMap.update();
		player.update();

		List<Bullet> bullets = player.getBullets();

		for(int i = 0; i < bullets.size(); i++){
			Bullet bullet = bullets.get(i);

			if(bullet.isVisible())
				bullet.update();
		}
	}

	public void render(Graphics g){
		gameMap.render(g);
		player.render(g);

		List<Bullet> bullets = player.getBullets();

		for(Bullet bullet: bullets){
			if(bullet.isVisible())
				bullet.render(g);
		}
	}
}