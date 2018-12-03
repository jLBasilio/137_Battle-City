package com.main.app;

import java.awt.image.BufferStrategy;
import java.awt.Graphics;

public class BattleCity implements Runnable, Constants {
	private String mapName;
	private int width, height;

	private MapFrame mapFrame;

	private boolean running = false;

	private Thread mapThread = new Thread(this);

	private BufferStrategy bs;
	private Graphics g;

	private Handler handler;
	private State gameState;
	private StateManager stateManager;

	private KeyHandler keyHandler;

	public Main main;

	public BattleCity(String mapName, int width, int height, Main main) {
		this.mapName = mapName;
		this.width = width;
		this.height = height;
		this.main = main;

		mapThread.start();

		keyHandler = new KeyHandler();
	}

	private void initialize() {
		mapFrame = new MapFrame(this.mapName, this.width, this.height, main);
		mapFrame.getFrame().addKeyListener(keyHandler);
		running = true;
		Assets.initialize();

		handler = new Handler(this);
		gameState = new GameState(handler);
		stateManager = new StateManager(gameState);
	}

	private void update() {
		stateManager.getState().update();
	}

	private void render() {
		bs = mapFrame.getCanvas().getBufferStrategy();
		if(bs == null) {
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

		int fps = 60;
		double timePerUpdate = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;

			if(delta >= 1){
				update();
				render();
				delta--;
			}
		}
	}

	public KeyHandler getKeyHandler(){
		return keyHandler;
	}
}