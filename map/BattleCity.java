import java.awt.image.BufferStrategy;
import java.awt.Graphics;

public class BattleCity implements Runnable,Constants{
	private String mapName;
	private int width,height;

	private MapFrame mapFrame;

	private boolean running = false;

	private Thread mapThread = new Thread(this);

	private BufferStrategy bs;
	private Graphics g;

	private Handler handler;
	private State gameState;
	private StateManager stateManager;

	private KeyHandler keyHandler;

	public BattleCity(String mapName,int width,int height){
		this.mapName = mapName;
		this.width = width;
		this.height = height;

		mapThread.start();

		keyHandler = new KeyHandler();
	}

	private void initialize(){
		mapFrame = new MapFrame(this.mapName,this.width,this.height);
		mapFrame.getFrame().addKeyListener(keyHandler);
		running = true;
		Assets.initialize();

		handler = new Handler(this);
		gameState = new GameState(handler);
		stateManager = new StateManager(gameState);
	}

	private void update(){
		stateManager.getState().update();
	}

	private void render(){
		bs = mapFrame.getCanvas().getBufferStrategy();
		if(bs == null){
			mapFrame.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		//draw here
		stateManager.getState().render(g);
		//draw here

		bs.show();
		g.dispose();
	}

	public void run(){
		initialize();

		while(running){
			update();
			render();
		}
	}

	public KeyHandler getKeyHandler(){
		return keyHandler;
	}
}