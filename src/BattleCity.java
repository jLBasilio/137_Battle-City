package com.main.app;

import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.main.app.Mainprotos.*;

public class BattleCity implements Runnable, Constants{
	private String mapName, name, server;
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

  UDPPacket.Connect.Builder connectPacket = UDPPacket.Connect.newBuilder();

  InetAddress inetaddress;
  DatagramPacket toServerPacket, toReceivePacket;
  Main main;

  byte[] toReceive, toParse;

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

    connectPacket.setType(UDPPacket.PacketType.CONNECT);

    try{
      inetaddress = InetAddress.getByName(this.server);
      socket = new DatagramSocket();
      socket.setSoTimeout(1000000);
    } catch(IOException ioe){}

    initialize();
    connectToServer(this.name);
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
          break;
        case 1: // move right
        	dir=1;
        	if((x+moveSpeed) <= 870) {
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
    System.out.println("ENTERED RENDER");

    //render map before rendering all players
    gameMap.render(g);
    // for(Iterator i = players.keySet().iterator(); i.hasNext(); ){
  //     String pname = (String) i.next();
  //     Player player = (Player) players.get(pname);
  //     int px = player.getX();
  //     int py = player.getY();
  //     int pdir = player.getDir();
  //     switch(pdir){
    //    case 0: g.drawImage(Assets.tankU ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //    case 1: g.drawImage(Assets.tankR ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //    case 2: g.drawImage(Assets.tankD ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //    case 3: g.drawImage(Assets.tankL ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //  }
    //  // render bullet
    //  // g.drawImage(Assets.bullet ,x ,y ,BULLET_WIDTH ,BULLET_HEIGHT ,null);
  //   }
    // for (HashMap.Entry<String, Player> entry : players.entrySet()) {
    //     String pname = entry.getKey();
    //     Player player = entry.getValue();
        
    //     int px = player.getX();
    //     int py = player.getY();
    //     int pdir = player.getDir();
    //     switch(pdir){
    //       case 0: g.drawImage(Assets.tankU ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //       case 1: g.drawImage(Assets.tankR ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //       case 2: g.drawImage(Assets.tankD ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //       case 3: g.drawImage(Assets.tankL ,px ,py ,TANK_WIDTH ,TANK_HEIGHT ,null);break;
    //     }
    // }


		bs.show();
		g.dispose();
	}

	public void run() {
		while(running) {
			try {
				Thread.sleep(1);
			} catch(Exception ioe){

      }

			//get data from server
			toReceive = new byte[1024];
			toReceivePacket = new DatagramPacket(toReceive, toReceive.length);

			try{
				
        // Holy trinity for receiving packets
        socket.receive(toReceivePacket);
        toParse = new byte[toReceivePacket.getLength()];
        System.arraycopy(toReceivePacket.getData(), toReceivePacket.getOffset(), toParse, 0, toReceivePacket.getLength());

        // Fetch all players
        if (UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.PLAYER_INFO) {
          String playerInfos = UDPPacket.Playerinfo.parseFrom(toParse).getInfo();
          System.out.println(playerInfos);
          parseAllPlayers(playerInfos);

        }

        // For custom messages from server
        else if (UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.CUSTOM) {
          System.out.println(UDPPacket.Custom.parseFrom(toParse).getMessage());

        }

        render();

			} catch (Exception e) { System.err.println("Error in receive: " + e.toString()); }
		}
	}

  private void connectToServer(String name) {
    try{
      connectPacket.setName(name);
      System.out.println("To send name: " + connectPacket.getName());
      System.out.println("To send type: " + connectPacket.getType());
      byte[] buf = connectPacket.build().toByteArray();
      toServerPacket = new DatagramPacket(buf, buf.length, inetaddress, PORT);      
      socket.send(toServerPacket);
      System.out.println(this.name + " has sent connect packet to server.\n");
    }catch(Exception e){}

  }



  private void parseAllPlayers(String info) {

    if(info.startsWith("PLAYER")){
      String[] playersInfo = info.split(":");
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