package com.main.app;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map;

import com.main.app.Mainprotos.*;

public class GameServer implements Runnable, Constants{
  private String playerData;
  private DatagramSocket serverSocket = null;
  protected GameState game;
  private Thread gsThread = new Thread(this);
  private int numOfPlayers, playerCount=0, gameStage=WAITING_FOR_PLAYERS;


  byte[] toReceive, toSend, toParse;
  DatagramPacket packet, toSendPacket;

  UDPPacket.Connect.Builder newPlayerToSend = UDPPacket.Connect.newBuilder();
  UDPPacket.Custom.Builder customPacket = UDPPacket.Custom.newBuilder();
  UDPPacket.Playerinfo.Builder playerInfoToSend = UDPPacket.Playerinfo.newBuilder();
  UDPPacket.Movement.Builder movementPacket = UDPPacket.Movement.newBuilder();

  public GameServer(int numOfPlayers){

    newPlayerToSend.setType(UDPPacket.PacketType.CONNECT);
    customPacket.setType(UDPPacket.PacketType.CUSTOM);
    playerInfoToSend.setType(UDPPacket.PacketType.PLAYER_INFO);

    toReceive = new byte[1024];


    this.numOfPlayers = numOfPlayers;
    try{
      serverSocket = new DatagramSocket(PORT, InetAddress.getByName("0.0.0.0"));
      serverSocket.setSoTimeout(1000000);
    } catch (IOException e) {
        System.err.println("Could not listen on port: " + PORT);
        System.exit(1);
    }catch(Exception e) {
      e.printStackTrace();
    }


    game = new GameState();
    gsThread.start();
  }


  public void run() {

    System.out.println("<=== Server Running ===> ");
    while(true) {

      packet = new DatagramPacket(toReceive, toReceive.length);
      try{

      serverSocket.receive(packet);
      toParse = new byte[packet.getLength()];
      System.arraycopy(packet.getData(), packet.getOffset(), toParse, 0, packet.getLength());

        // CONNECT - Give players the coordinates        
        if (UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.CONNECT) {

          System.out.println("Someone has connected.");
          String newPlayerName = UDPPacket.Connect.parseFrom(toParse).getName();

          Coordinates coord = game.getSpawnLocation();
          Player newPlayer = new Player(newPlayerName);
          newPlayer.setX(coord.getX());
          newPlayer.setY(coord.getY());
          newPlayer.setAddress(packet.getAddress());
          newPlayer.setPort(packet.getPort());
          newPlayer.setDir(0);
          game.addPlayer(newPlayerName, newPlayer);

          System.out.println(newPlayerName + " successfully connected to server.\n");
          playerCount++;

          // Broadcast a new player to all
          newPlayerToSend.setName(newPlayerName);
          newPlayerToSend.setX(newPlayer.getX());
          newPlayerToSend.setY(newPlayer.getY());
          newPlayerToSend.setDir(0);


          // Broadcasting - loop through each existing player to notify a new player
          customPacket.setMessage("New Player Connected: " + newPlayerName);
          toSend = customPacket.build().toByteArray();
          for (Map.Entry<String, Player> entry : game.getPlayers().entrySet()) {
            String key = entry.getKey();
            Player currentPlayer = entry.getValue();
            System.out.println("Broadcasting message to " + key);
            toSendPacket = new DatagramPacket(toSend, toSend.length, currentPlayer.getAddress(), currentPlayer.getPort());
            serverSocket.send(toSendPacket);
          }

          // Send all player list to all when playercountis reached
          if (playerCount == numOfPlayers) {
            broadcastInformation(game.getGameData());     // All data of all players
          }
        }

        else if (UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.MOVE) {
          String movement = UDPPacket.Movement.parseFrom(toParse).getAction();
          broadcastInformation(movement);     // All data of single player
        }

        else if (UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.FIRE_BULLET) {
          String bulletMovement = UDPPacket.Movement.parseFrom(toParse).getAction();
          System.out.println("BULLET RECEIVED => " + bulletMovement);
          
          // Broadcast initial bullet
          broadcastBullet(calculateBulletSpawn(bulletMovement));    


        }



      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }


  private void parseMovement(String info) {

    if(info.startsWith("PLAYER")){
      String[] playersInfo = info.split(" ");
      for(int i=0; i<playersInfo.length; i++){
        String pname = playersInfo[1];
        int px = Integer.parseInt(playersInfo[2]);
        int py = Integer.parseInt(playersInfo[3]);
        int pdir = Integer.parseInt(playersInfo[4]);
        System.out.println("Player data: "+pname+"|"+px+"|"+py+"|"+pdir);
        Player player = new Player(pname);
        player.setX(px);
        player.setY(py);
        player.setDir(pdir);
        game.update(pname,player); //adds player to map or updates player details saved in map.
        broadcastInformation(player.getPlayerData());
      }
    }
  }

  public void broadcastInformation(String message) {
    try {
      playerInfoToSend.setInfo(message);
      toSend = playerInfoToSend.build().toByteArray();
      for (Map.Entry<String, Player> entry : game.getPlayers().entrySet()) {
        String key = entry.getKey();
        Player currentPlayer = entry.getValue();
        System.out.println("Broadcasting list of players to " + key);
        toSendPacket = new DatagramPacket(toSend, toSend.length, currentPlayer.getAddress(), currentPlayer.getPort());
        serverSocket.send(toSendPacket);
      }

    } catch (Exception e) { System.err.println("Error broadcast info: " + e.toString()); }

  }

  public void broadcastBullet(String message) {
    try {
      movementPacket.setType(UDPPacket.PacketType.FIRE_BULLET);
      movementPacket.setAction(message);
      toSend = movementPacket.build().toByteArray();
      for (Map.Entry<String, Player> entry : game.getPlayers().entrySet()) {
        String key = entry.getKey();
        Player currentPlayer = entry.getValue();
        System.out.println("Broadcasting Bullet to " + key);
        toSendPacket = new DatagramPacket(toSend, toSend.length, currentPlayer.getAddress(), currentPlayer.getPort());
        serverSocket.send(toSendPacket);
      }

    } catch (Exception e) { System.err.println("Error broadcasting bullet: " + e.toString()); }
  }


  public String calculateBulletSpawn(String info) {

    String toReturn = "";
    int bx, by;
    if(info.startsWith("BULLET")){
      String[] playersInfo = info.split(":");
      for(int i=0; i<playersInfo.length; i++){

        String[] playerData = playersInfo[i].split(" ");
        String pname = playerData[1];
        int px = Integer.parseInt(playerData[2]);
        int py = Integer.parseInt(playerData[3]);
        int pdir = Integer.parseInt(playerData[4]);

        // Initial location of bullet
        switch(pdir){
          case 0: 
            toReturn = "BULLET " + pname + " " + (px-5+TILE_WIDTH/2) + " " + py + " " + pdir; 
            bx = px-5+TILE_WIDTH/2;
            by = py;
            game.getBullets().add(new Bullet(pname, bx, by, pdir));
            break;

          case 1: 
            toReturn = "BULLET " + pname + " " + (px+20) + " " + (py-5+TILE_HEIGHT/2) + " " + pdir;
            bx = px+20;
            by = py-5+TILE_HEIGHT/2;
            game.getBullets().add(new Bullet(pname, bx, by, pdir));
            break;

          case 2: 
            toReturn = "BULLET " + pname + " " + (px-5+TILE_WIDTH/2) + " " + (py+20) + " " + pdir;
            bx = px-5+TILE_WIDTH/2;
            by = py+20;
            game.getBullets().add(new Bullet(pname, bx, by, pdir));
            break;

          case 3: 
            toReturn = "BULLET " + pname + " " + px + " " + (py-5+TILE_HEIGHT/2) + " " + pdir;
            bx = px;
            by = py-5+TILE_HEIGHT/2;
            game.getBullets().add(new Bullet(pname, bx, by, pdir));
            break;

        }
      }
    }

    return toReturn;
  }


  public static void main(String[] args) {
    if(args.length !=1){
      System.out.println("Usage: java GameServer <number of players>");
      System.exit(1);
    }

    new GameServer(Integer.parseInt(args[0]));
  }


}