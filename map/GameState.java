import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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

		List<Bullet> bullets = player.getBullets();

		for(int i = 0; i < bullets.size(); i++){
			Bullet bullet = bullets.get(i);

			if(bullet.isVisible())
				bullet.update();
			else
				player.removeBullet(i);
				// bullets.remove(i);
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