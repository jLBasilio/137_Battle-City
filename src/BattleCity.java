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
	private int width, height, dir, x, y ,moveSpeed = 5,prevX,prevY;
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
	private String serverData;


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
				socket.setSoTimeout(1000);
		}catch(IOException ioe){}

		keyHandler = new KeyHandler();

		initialize();

		bcThread.start();
	}

	private void initialize() {
		mapFrame = new MapFrame(this.mapName, this.width, this.height, main);
		mapFrame.getCanvas().addKeyListener(keyHandler);
		running = true;
		Assets.initialize();

		gameMap =  new GameMap("map/city1.map");
	}

	private void update() {
		gameMap.update();

		if (keyHandler.isKeyPressed()) {
			// int tx=x,ty=y;
      switch(keyHandler.getDirection()){
        case 0: // move up
        	dir=0;
        	y-=moveSpeed;
          break;
        case 1: // move right
        	dir=1;
        	x+=moveSpeed;
          break;
        case 2: // move down
        	dir=2;
        	y+=moveSpeed;
          break;
        case 3: // move left
        	dir=3;
        	x-=moveSpeed;
          break;
      }
      send("PLAYER " + name + " " + x + " " + y + " " + dir);
		}
	}

	private void render() {
		bs = mapFrame.getCanvas().getBufferStrategy();
		if(bs == null) {
			mapFrame.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		//draw here
		gameMap.render(g);
		//draw here

		bs.show();
		g.dispose();
	}

	public void run(){
		while(running){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}

			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
				socket.receive(packet);
				System.out.println("receiving from server...");
			}catch(Exception ioe){
				// ioe.printStackTrace();
			}

			serverData = new String(buf);
			serverData = serverData.trim();

			if (!serverData.equals("")){
				System.out.println("Server Data:" +serverData);
			}

			if(!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected!");
			}
			else if(!connected){
				System.out.println("Connecting...");
				send("CONNECT " + name);
			}
			else if(connected){
				System.out.println("Connected and Receiving...");
				render();
				update();
				if(serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for(int i=0; i<playersInfo.length; i++){
						String[] playerData = playersInfo[i].split(" ");
						String pname = playerData[1];
						int px = Integer.parseInt(playerData[2]);
						int py = Integer.parseInt(playerData[3]);
						int pdir = Integer.parseInt(playerData[4]);
						System.out.println("Player data: "+pname+"|"+px+"|"+py+"|"+pdir);
					}
				}
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
			System.out.println("Sending packet to server...");
		}catch(Exception e){}
	}
}