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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BattleCity implements Runnable, Constants{
	private String mapName,name,server;
	private int width, height, dir, x, y , moveSpeed = 5;
	private MapFrame mapFrame;
	private boolean running = false,connected = false;
	private Thread bcThread = new Thread(this);
	private BufferStrategy bs;
	private Graphics g;
	private GameMap gameMap;
	private KeyHandler keyHandler;
	private DatagramSocket socket;
	private String serverData;
  private HashMap<String, Player> players;

  Main main;

  private long bulletSpawnDelay = 250000000;
  private long currentTime;
	private long shootingTime = 0;

	public BattleCity(String mapName, int width, int height, Main main) {
		this.mapName = mapName;
		this.width = width;
		this.height = height;
    this.main = main;
    this.name = main.playerName;
		this.server = main.serverIP;

		try{
				socket = new DatagramSocket();
				socket.setSoTimeout(1000);
		}catch(IOException ioe){}

		initialize();
		bcThread.start();
	}

	private void initialize() {
		mapFrame = new MapFrame(this.mapName, this.width, this.height, main);
    keyHandler = new KeyHandler();
    mapFrame.getCanvas().addKeyListener(keyHandler);
    running = true;
    Assets.initialize();
    gameMap =  new GameMap("map/city1.map");
    players = new HashMap<String, Player>();
	}

	private void update() {
		//update client game map
		// gameMap.update();

		//update x or y position of this player
		if (keyHandler.isKeyPressed()) {
      switch(keyHandler.getDirection()){
        case 0: // move up
        	dir=0;
        	if((y-moveSpeed) >= 0){
            if(!collision()){
              y-=moveSpeed;
              send("PLAYER " + name + " " + x + " " + y + " " + dir);
            }
            else{
              System.out.println("Collision detected @ top!");
            }
          }
          break;
        case 1: // move right
        	dir=1;
        	if((x+moveSpeed) <= 870){
            if(!collision()){
              x+=moveSpeed;
              send("PLAYER " + name + " " + x + " " + y + " " + dir);
            }
            else{
              System.out.println("Collision detected @ right!");
            }
          }
          break;
        case 2: // move down
        	dir=2;
        	if((y+moveSpeed) <= 570){
            if(!collision()){
              y+=moveSpeed;
              send("PLAYER " + name + " " + x + " " + y + " " + dir);
            }
            else{
              System.out.println("Collision detected @ bottom!");
            }
					}
          break;
        case 3: // move left
        	dir=3;
        	if((x-moveSpeed) >= 0){
            if(!collision()){
              x-=moveSpeed;
              send("PLAYER " + name + " " + x + " " + y + " " + dir);
            }
            else{
              System.out.println("Collision detected @ left!");
            }
					}
          break;
      }
		}
		currentTime = System.nanoTime();
		if(keyHandler.isFiring() && currentTime - shootingTime > bulletSpawnDelay){
			shootingTime = System.nanoTime();
			send("BULLET " + name + " " + x + " " + y + " " + dir);
		}
	}

	private void render() {
		bs = mapFrame.getCanvas().getBufferStrategy();
		if(bs == null) {
			mapFrame.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		//render map before rendering all players
		gameMap.render(g);
		for(Iterator i = players.keySet().iterator(); i.hasNext(); ){
      String pname = (String) i.next();
      Player player = (Player) players.get(pname);
      int px = player.getX();
      int py = player.getY();
      int pdir = player.getDir();
      switch(pdir){
				case 0: g.drawImage(Assets.tankU ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 1: g.drawImage(Assets.tankR ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 2: g.drawImage(Assets.tankD ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
				case 3: g.drawImage(Assets.tankL ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
			}
			// render bullet
			// g.drawImage(Assets.bullet ,x ,y ,BULLET_WIDTH ,BULLET_HEIGHT ,null);
    }

		bs.show();
		g.dispose();
	}

	public void run() {
		while(running) {
			try {
				Thread.sleep(1);
			} catch(Exception ioe){}

			//get data from server
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
				socket.receive(packet);
				System.out.println("receiving from server...");
			}catch(Exception ioe){}

			serverData = new String(buf);
			serverData = serverData.trim();

			if(!connected && serverData.startsWith("CONNECTED")){ //checks if server confirms connection request
				connected=true;
				System.out.println("Connected!");
			}
			else if(!connected){	//client ask for connection
				System.out.println("Connecting...");
				send("CONNECT " + name);
			}
			else if(connected){ //retrieves broadcasted players data from server
				System.out.println("Connected and Receiving...");
				if(serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for(int i=0; i<playersInfo.length; i++){
						String[] playerData = playersInfo[i].split(" ");
						String pname = playerData[1];
						int px = Integer.parseInt(playerData[2]);
						int py = Integer.parseInt(playerData[3]);
						int pdir = Integer.parseInt(playerData[4]);
						System.out.println("Player data: "+pname+"|"+px+"|"+py+"|"+pdir);
						Player player = new Player(pname);
						player.setX(px);
						player.setY(py);
						player.setDir(pdir);
						players.put(pname,player); //adds player to map or updates player details saved in map.
					}
				}
				// render current state before sending update to server
				render();
				update();
			}
		}
	}

	private void send(String msg){
		try{
			byte[] buf = msg.getBytes();
			InetAddress address = InetAddress.getByName(server);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
			socket.send(packet);
			System.out.println("Sending packet to server...");
		}catch(Exception e){}
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