package com.main.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameServer implements Runnable, Constants{
  private String playerData;
  private DatagramSocket serverSocket = null;
  protected GameState game;
  private int numOfPlayers, playerCount=0, gameStage=WAITING_FOR_PLAYERS;
  private Thread gsThread = new Thread(this);

  public GameServer(int numOfPlayers){
    this.numOfPlayers = numOfPlayers;
    try{
      serverSocket = new DatagramSocket(PORT);
      serverSocket.setSoTimeout(1000000);
    } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(-1);
    }catch(Exception e){
      e.printStackTrace();
    }

    game = new GameState();

    gsThread.start();
  }

  public void broadcast(String msg){
    System.out.println("Server Broadcasting . . .");
    for(Iterator i = game.getPlayers().keySet().iterator(); i.hasNext();){
      String name = (String) i.next();
      Player player = (Player) game.getPlayers().get(name);
      send(player,msg);
    }
  }

  public void send(Player player, String msg){
    DatagramPacket packet;  
    byte buf[] = msg.getBytes();    
    packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
    try{
      serverSocket.send(packet);
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }

  public void run(){
    System.out.println("Server Running . . .");
    while(true){
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try{
          serverSocket.receive(packet);
          System.out.println("receiving from players");
      }catch(Exception e){
        e.printStackTrace();
      }

      playerData = new String(buf);
      playerData = playerData.trim();

      switch(gameStage){
        case WAITING_FOR_PLAYERS:
          if(playerData.startsWith("CONNECT")){
            String tokens[] = playerData.split(" ");
            Player player = new Player(tokens[1]);
            player.setAddress(packet.getAddress());
            player.setPort(packet.getPort());
            //setting spawn location
            Coordinates spawnLoc = game.getSpawnLocation();
            player.setX(spawnLoc.getX());
            player.setY(spawnLoc.getY());
            player.setDir(0);

            game.update(tokens[1],player);
            broadcast("CONNECTED" + tokens[1]);
            playerCount++;
            if(playerCount == numOfPlayers)
              gameStage = GAME_START;
          }
          break;
        case GAME_START:
          broadcast("START");
          gameStage = IN_PROGRESS;
          break;
        case IN_PROGRESS:
          if(playerData.startsWith("PLAYER")){
            String[] playerInfo = playerData.split(" ");
            String pname = playerInfo[1];
            int px = Integer.parseInt(playerInfo[2]);
            int py = Integer.parseInt(playerInfo[3]);
            int pdir = Integer.parseInt(playerInfo[4]);
            Player player=(Player)game.getPlayers().get(pname);
            player.setX(px);
            player.setY(py);
            player.setDir(pdir);
            game.update(pname,player);
            broadcast(game.getGameData());
          }
          break;
        case GAME_END:
          break;
      }
    }
  }

  public static void main(String[] args) {
    if(args.length !=1){
      System.out.println("Usage: java GameServer <number of players>");
      System.exit(1);
    }

    new GameServer(Integer.parseInt(args[0]));
  }
}