package com.main.app;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
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

  public GameServer(int numOfPlayers){

    newPlayerToSend.setType(UDPPacket.PacketType.CONNECT);
    customPacket.setType(UDPPacket.PacketType.CUSTOM);
    playerInfoToSend.setType(UDPPacket.PacketType.PLAYER_INFO);

    toReceive = new byte[1024];


    this.numOfPlayers = numOfPlayers;
    try{
      serverSocket = new DatagramSocket(PORT);
      serverSocket.setSoTimeout(1000000);
    } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(-1);
    }catch(Exception e) {
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
    packet = new DatagramPacket(buf, buf.length, player.getAddress(), player.getPort());
    try{
      serverSocket.send(packet);
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }

  public void run() {
    while(true) {

      packet = new DatagramPacket(toReceive, toReceive.length);
      try{

      serverSocket.receive(packet);
      toParse = new byte[packet.getLength()];
      System.arraycopy(packet.getData(), packet.getOffset(), toParse, 0, packet.getLength());

        // CONNECT - Give players the coordinates        
        if(UDPPacket.parseFrom(toParse).getType() == UDPPacket.PacketType.CONNECT) {

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
          for (HashMap.Entry<String, Player> entry : game.getPlayers().entrySet()) {
            String key = entry.getKey();
            Player currentPlayer = entry.getValue();
            System.out.println("Broadcasting message to " + key);
            toSendPacket = new DatagramPacket(toSend, toSend.length, currentPlayer.getAddress(), currentPlayer.getPort());
            serverSocket.send(toSendPacket);
          }

          // Send all player list to all
          if (playerCount == numOfPlayers) {

            playerInfoToSend.setInfo(game.getGameData());
            toSend = playerInfoToSend.build().toByteArray();
            for (HashMap.Entry<String, Player> entry : game.getPlayers().entrySet()) {
              String key = entry.getKey();
              Player currentPlayer = entry.getValue();
              System.out.println("Broadcasting list of players to " + key);
              toSendPacket = new DatagramPacket(toSend, toSend.length, currentPlayer.getAddress(), currentPlayer.getPort());
              serverSocket.send(toSendPacket);
            } 

          }

        }


        else 
          continue;


      } catch(Exception e) {
        e.printStackTrace();
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