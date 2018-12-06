package com.main.app;

import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BattleCity implements Runnable, Constants{
	private String mapName,name,server;
	private int width, height, dir, x, y ,moveSpeed = 5;
	private MapFrame mapFrame;
	private boolean running = false,connected = false;
	private Thread bcThread = new Thread(this);
	private BufferStrategy bs;
	private Graphics g;
	private Handler handler;
	private State gameState;
	private StateManager stateManager;
	private GameMap gameMap;
	private KeyHandler keyHandler;
	private DatagramSocket socket;

  Main main;

	public BattleCity(String mapName, int width, int height, Main main) {
		this.mapName = mapName;
		this.width = width;
		this.height = height;
    this.main = main;
    this.name = main.playerName;
		this.server = main.serverIP;

		try{
				socket = new DatagramSocket();
		}catch(IOException ioe){}

		bcThread.start();

		keyHandler = new KeyHandler();
	}

	private void initialize() {
		mapFrame = new MapFrame(this.mapName, this.width, this.height, main);
		mapFrame.getCanvas().addKeyListener(keyHandler);
		running = true;
		Assets.initialize();

		gameMap =  new GameMap("map/city1.map");
		// handler = new Handler(this);
		// handler.setGameMap(gameMap);
		// gameState = new GameState(handler);
		// stateManager = new StateManager(gameState);
	}

	private void update() {
		// stateManager.getState().update();
		gameMap.update();

		if(keyHandler.isKeyPressed()){
			switch(keyHandler.getDirection()){
				case 0:
					dir = 0;

					if((y-moveSpeed) >= 0){
						if(!collision()){
							y-=moveSpeed;
							send("PLAYER" + name + " " + x + " " + y + " " + dir);
						}
						else{
							System.out.println("Collision detected @ top!");
						}
					}
					break;
				case 1:
					dir = 1;

					if((x+moveSpeed) <= 870){
						if(!collision()){
							x+=moveSpeed;
							send("PLAYER" + name + " " + x + " " + y + " " + dir);
						}
						else{
							System.out.println("Collision detected @ right!");
						}
					}
					break;
				case 2:

					if((y+moveSpeed) <= 570){
						if(!collision()){
							y+=moveSpeed;
							send("PLAYER" + name + " " + x + " " + y + " " + dir);
						}
						else{
							System.out.println("Collision detected @ bottom!");
						}
					}
					dir = 2;
					break;
				case 3:

					if((x-moveSpeed) >= 0){
						if(!collision()){
							x-=moveSpeed;
							send("PLAYER" + name + " " + x + " " + y + " " + dir);
						}
						else{
							System.out.println("Collision detected @ left!");
						}
					}
					dir = 3;
					break;
			}
		}
	}

	private void render(String object,int px, int py,int pdir) {
		bs = mapFrame.getCanvas().getBufferStrategy();
		if(bs == null) {
			mapFrame.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		//draw here
		// stateManager.getState().render(g);
		
		if(object.equals("map")){
			gameMap.render(g);
		}
		else if(object.equals("player")){
			switch(pdir){
				case 0: g.drawImage(Assets.tankU ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 1: g.drawImage(Assets.tankR ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 2: g.drawImage(Assets.tankD ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 3: g.drawImage(Assets.tankL ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
			}
		}
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

		String serverData;

		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;

			if(delta >= 1){
				serverData = receive().trim();

				if(!connected && serverData.startsWith("CONNECTED")){
					connected=true;
					System.out.println("Connected.");
				}
				else if(!connected){
					System.out.println("Connecting..");
					send("CONNECT " + name);
				}
				else if(connected){
					render("map",0,0,0);
					if(serverData.startsWith("PLAYER")){
						String[] playersInfo = serverData.split(":");
						for(int i=0; i<playersInfo.length; i++){
							String[] playerData = playersInfo[i].split(" ");
							String pname = playerData[1];
							int px = Integer.parseInt(playerData[2]);
							int py = Integer.parseInt(playerData[3]);
							int pdir = Integer.parseInt(playerData[4]);
							render("player",px,py,pdir);
						}
					}
					update();
				}
				delta--;
			}
		}
	}

	public KeyHandler getKeyHandler(){
		return keyHandler;
	}

	private void send(String msg){
		try{
			byte[] buf = msg.getBytes();
			InetAddress address = InetAddress.getByName(server);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
			socket.send(packet);
		}catch(Exception e){}
	}

	private String receive(){
		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try{
			socket.receive(packet);
		}catch(Exception ioe){}

		String serverData = new String(buf);

		return serverData;
	}

	public boolean collision(){
		System.out.println("Detecting collision.");

		Rectangle r = new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);

    List<Tile> tiles = gameMap.getTiles();

    for(Tile tile : tiles){
      Rectangle r2 = tile.getBounds();
      if (r.intersects(r2)) {
        System.out.println(x/TILE_WIDTH + ":" + y/TILE_HEIGHT);
        System.out.println(dir);

        if(dir == 0){
          if(r.y > r2.y)
            return true;
          else
            return false;
        }
        else if(dir == 1){
          if(r.x < r2.x)
            return true;
          else
            return false;
        }
        else if(dir == 2){
          if(r.y < r2.y)
            return true;
          else
            return false;
        }
        else if(dir == 3){
          if(r.x > r2.x)
            return true;
          else
            return false;
        }
      }
    }

    return false;
	}
}