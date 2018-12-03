package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import com.main.app.PlayerProtos.*;
import com.main.app.TcpPacketProtos.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;


public class ChatResource implements Runnable {

  public static final int PORT = 80;
  public static final String IP_ADDRESS = "202.92.144.45";

  Socket serverSocket;
  OutputStream os;
  InputStream in;

  Scanner sc = new Scanner(System.in);


  byte[] toSendArray, toReceiveArray;
  byte[] toSendChat, toReceiveChat;
  byte[] toSendPlayersList, toReceivePlayersList;
  int serverResponseLength, receiveLength, readResult, receiveResult;
  int receivedPlayerCount, noOfPlayersJoined, maxPlayers;
  String serverResponse, lobbyId, selfName, selfId, selfMessage, alertMessage, receivedMessage;
  Boolean continueReceiving, gameHasStarted;

  PlayerProtos.Player.Builder playerSelf = PlayerProtos.Player.newBuilder();

  TcpPacket.CreateLobbyPacket.Builder lobbyPacket = TcpPacket.CreateLobbyPacket.newBuilder();
  TcpPacket.CreateLobbyPacket receivedLobbyPacket;

  TcpPacket.ConnectPacket.Builder connectPacket = TcpPacket.ConnectPacket.newBuilder();
  TcpPacket.ConnectPacket receivedConnectPacket;

  TcpPacket.DisconnectPacket.Builder disconnectPacket = TcpPacket.DisconnectPacket.newBuilder();

  TcpPacket.ChatPacket.Builder chatPacket = TcpPacket.ChatPacket.newBuilder();
  TcpPacket.ChatPacket receivedChatPacket;

  TcpPacket.PlayerListPacket.Builder playerListPacket = TcpPacket.PlayerListPacket.newBuilder();
  TcpPacket.PlayerListPacket receivedPlayerListPacket;

  ChatHandler sendHandler;
  MapFrame mapFrame;
  Thread gameThread = new Thread(this);
  Thread sendingThread;


  // Instantiating a chat server
  public ChatResource(String playerName, int noOfPlayers) {

    System.out.println();

    /* Initialize all necessary packets */
    playerSelf.setName(playerName);
    selfName = playerName;

    lobbyPacket.setType(TcpPacket.PacketType.CREATE_LOBBY);
    lobbyPacket.setMaxPlayers(noOfPlayers); 

    connectPacket.setType(TcpPacket.PacketType.CONNECT);
    connectPacket.setPlayer(playerSelf);

    disconnectPacket.setType(TcpPacket.PacketType.DISCONNECT);
    disconnectPacket.setPlayer(playerSelf);

    chatPacket.setType(TcpPacket.PacketType.CHAT);
    chatPacket.setPlayer(playerSelf);

    playerListPacket.setType(TcpPacket.PacketType.PLAYER_LIST); 


    /* Send first request (CreateLobby) to server */
    toSendArray = lobbyPacket.build().toByteArray();
    try {
      
      serverSocket = new Socket(IP_ADDRESS, PORT);
      System.out.println("Checkpoint");
      os = serverSocket.getOutputStream();
      in = serverSocket.getInputStream();
      System.out.println("Connected to server: " + serverSocket.getInetAddress() + ":" + serverSocket.getPort());

      /* Send createlobbypacket and wait response */
      os.write(toSendArray);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }

      /* Receive create lobby response */
      toReceiveArray = new byte[serverResponseLength];
      readResult = in.read(toReceiveArray);
      receivedLobbyPacket = TcpPacket.CreateLobbyPacket.parseFrom(toReceiveArray);
      System.out.println("Server_response (Create): ");
      System.out.println(" Type: \t\t" + receivedLobbyPacket.getType());
      System.out.println(" lobby_id:\t" + receivedLobbyPacket.getLobbyId());
      System.out.println(" max_players:\t" + receivedLobbyPacket.getMaxPlayers());
      lobbyId = receivedLobbyPacket.getLobbyId();
      connectPacket.setLobbyId(lobbyId);

      /* Send connectpacket and wait response */
      toSendArray = connectPacket.build().toByteArray();
      os.write(toSendArray);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }
      toReceiveArray = new byte[serverResponseLength];
      readResult = in.read(toReceiveArray);
      receivedConnectPacket = TcpPacket.ConnectPacket.parseFrom(toReceiveArray);
      System.out.println("\nServer_response (Connect): ");
      System.out.println(" Player: \t" + receivedConnectPacket.getPlayer().getName());
      System.out.println(" Player id: \t" + receivedConnectPacket.getPlayer().getId());
      System.out.println(" Update: \t" + receivedConnectPacket.getUpdate());
      selfId = receivedConnectPacket.getPlayer().getId();

      maxPlayers = noOfPlayers;
      noOfPlayersJoined = 1;

      // Thread send and receive
      continueReceiving = true;
      gameHasStarted = false;
      sendHandler = new ChatHandler(this);
      // sendingThread = new Thread(sendHandler);

    } catch(Exception e) { 
      System.err.println("[Server ERR] main: " + e.toString());
    }
    
  }


  // Instantiating a chat client
  public ChatResource(String playerName, String lobbyIdIn) {
    System.out.println();

    /* Initialize all necessary packets */
    playerSelf.setName(playerName);
    selfName = playerName;
    lobbyId = lobbyIdIn;

    connectPacket.setType(TcpPacket.PacketType.CONNECT);
    connectPacket.setPlayer(playerSelf);
    connectPacket.setLobbyId(lobbyId);

    disconnectPacket.setType(TcpPacket.PacketType.DISCONNECT);
    disconnectPacket.setPlayer(playerSelf);

    chatPacket.setType(TcpPacket.PacketType.CHAT);
    chatPacket.setPlayer(playerSelf);

    playerListPacket.setType(TcpPacket.PacketType.PLAYER_LIST); 

    try {
      
      /* ======== CONNECT TO SERVER ======== */ 
      serverSocket = new Socket(IP_ADDRESS, PORT);
      os = serverSocket.getOutputStream();
      in = serverSocket.getInputStream();
      System.out.println("Connected to server: " + serverSocket.getInetAddress() + ":" + serverSocket.getPort() + "\n");

      toSendArray = connectPacket.build().toByteArray();
      os.write(toSendArray);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }
      toReceiveArray = new byte[serverResponseLength];
      readResult = in.read(toReceiveArray);
      receivedConnectPacket = TcpPacket.ConnectPacket.parseFrom(toReceiveArray);
      System.out.println("\nServer_response (Connect): ");
      System.out.println(" Player: \t" + receivedConnectPacket.getPlayer().getName());
      System.out.println(" Player id: \t" + receivedConnectPacket.getPlayer().getId());
      System.out.println(" Update: \t" + receivedConnectPacket.getUpdate());
      selfId = receivedConnectPacket.getPlayer().getId();


      /* ======== GET NUMBER OF PLAYERS FROM SERVER ======== */ 
      toSendPlayersList = playerListPacket.build().toByteArray();
      os.write(toSendPlayersList);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }
      toReceivePlayersList = new byte[serverResponseLength];
      readResult = in.read(toReceivePlayersList);
      receivedPlayerListPacket = TcpPacket.PlayerListPacket.parseFrom(toReceivePlayersList);
      noOfPlayersJoined = receivedPlayerListPacket.getPlayerListCount();
      System.out.println("\nTotal Connected Players: " + noOfPlayersJoined);


      /* ======== GET NUMBER OF MAX PLAYERS FROM SERVER ======== */ 
      toSendPlayersList = playerListPacket.build().toByteArray();
      os.write(toSendPlayersList);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }
      toReceivePlayersList = new byte[serverResponseLength];
      readResult = in.read(toReceivePlayersList);
      receivedPlayerListPacket = TcpPacket.PlayerListPacket.parseFrom(toReceivePlayersList);
      noOfPlayersJoined = receivedPlayerListPacket.getPlayerListCount();
      System.out.println("\nTotal Connected Players: " + noOfPlayersJoined);

      continueReceiving = true;
      gameHasStarted = false;
      sendHandler = new ChatHandler(this);
      // sendingThread = new Thread(sendHandler);

    } catch(Exception e) { 
      System.err.println("[Client ERR]: " + e.toString());
    }

  }


  public void startSendAndReceive() {

    try {

      gameThread.start();
      // sendingThread.start();

      // sendingThread.join();
      gameThread.join();
      
    } catch (Exception e) {
      System.err.println("Method [startSendAndReceive] ERR: " + e.toString());
    }

  }


  public void run() {

    try {

      /* Start of chat. Do not stop until input quit */
      System.out.println("\n\nChat Start!\n");
      while(continueReceiving) {

        if ((receiveLength = in.available()) == 0) { continue; }

        toReceiveChat = new byte[receiveLength];
        receiveResult = in.read(toReceiveChat);

        /* Add player count if CONNECT PACKET */
        if (TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.CONNECT) {
          alertMessage = TcpPacket.ConnectPacket.parseFrom(toReceiveChat).getPlayer().getName() + " connected!"; 
          System.out.println("\n[!] " + alertMessage);
          increasePlayers();
          continue;
        }

        /* Decrease player count if DISCONNECT PACKET */
        if (TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.DISCONNECT) {
          alertMessage = TcpPacket.DisconnectPacket.parseFrom(toReceiveChat).getPlayer().getName() + " disconnected.";
          System.out.println("\n[!] " + alertMessage);
          System.out.println("[!] STATUS: " + TcpPacket.DisconnectPacket.parseFrom(toReceiveChat).getUpdate() + "\n");
          decreasePlayers();
          continue;
        }

        /* Parse message if CHAT PACKET */
        else if (TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.CHAT) {
          receivedChatPacket = TcpPacket.ChatPacket.parseFrom(toReceiveChat);
          receivedMessage = receivedChatPacket.getMessage();
          handleMessageReceived();

        }

      }

    } catch (Exception e) {
      System.err.println("Error in run(): " + e.toString());
    }
  } 




  public void handleMessageReceived() {

    switch(receivedMessage) {

      // the server has started the game
      case "\\s game_start":
        this.setGameStart();
        break;

      // last case, print the message.
      default:
        // System.out.println(receivedChatPacket.getPlayer().getName() + ": " + receivedChatPacket.getMessage());
        mapFrame.addToChatLogs(receivedChatPacket.getPlayer().getName() + ": " + receivedChatPacket.getMessage());
        break;


    }


  }


  public void handleMessageSend(String message) {


    sendHandler.sendToAll(message);

  }




  public synchronized void stopReceiving() {
    this.continueReceiving = false;
  }

  public synchronized String getLobbyId() {
    return this.lobbyId;
  }

  public synchronized int getMaxPlayers() {
    return this.maxPlayers;
  }

  public synchronized void decreasePlayers() {
    this.noOfPlayersJoined--;
  }

  public synchronized void increasePlayers() {
    this.noOfPlayersJoined++;
  }

  public synchronized int getCountPlayers() {
    return this.noOfPlayersJoined;
  }

  public synchronized Boolean checkIfGameStarted() {
    return this.gameHasStarted;
  }

  // For server only
  public synchronized void setAndBroadcastStart() {
    this.gameHasStarted = true;
    sendHandler.sendToAll("\\s game_start");
  }

  // For client only
  public synchronized void setGameStart() {
    this.gameHasStarted = true;
  }

  public void instantiateMapFrame(MapFrame mapFrame) {
    this.mapFrame = mapFrame;
  }



  public static void main (String[] args) throws Exception {

    if (args.length != 3) {
      System.out.println("Type: [1]Server [2]Client");
      System.out.println("Usage: java com.main.app.ChatResource 1 <playerName> <noOfPlayers>");
      System.out.println("OR");
      System.out.println("Usage: java com.main.app.ChatResource 2 <playerName> <lobbyId>");
      System.out.println("");
      System.exit(1);
    }

    // Instantiate server
    if (Integer.parseInt(args[0]) == 1) {
      ChatResource main = new ChatResource(args[1], Integer.parseInt(args[2]));
      main.startSendAndReceive();
    } 

    // Instantiate Client
    else if (Integer.parseInt(args[0]) == 2) {
      ChatResource main = new ChatResource(args[1], args[2]);
      main.startSendAndReceive();
    }


  }



}