import java.awt.Graphics;

public class GameState extends State{
	private GameMap gameMap;
	private Player player;
	
	public GameState(Handler handler){
		super(handler);
		gameMap =  new GameMap("city1.map");
		handler.setGameMap(gameMap);
		player = new Player(handler,0,0,30,30);
	}

	public void update(){
		gameMap.update();
		player.update();
	}

	public void render(Graphics g){
		gameMap.render(g);
		player.render(g);
	}
}