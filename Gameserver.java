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


public class Gameserver implements Runnable {

  public static final int PORT = 80;
  public static final String IP_ADDRESS = "202.92.144.45";

  Socket serverSocket;
  OutputStream os;
  InputStream in;

  byte[] toSendArray;
  byte[] toReceiveArray;
  int serverResponseLength, readResult;
  String serverResponse, lobbyId, selfName, selfId, selfMessage;
  Scanner sc = new Scanner(System.in);

  PlayerProtos.Player.Builder playerSelf = PlayerProtos.Player.newBuilder();
  TcpPacket.Builder tcppacket = TcpPacket.newBuilder();

  TcpPacket.CreateLobbyPacket.Builder lobbyPacket = TcpPacket.CreateLobbyPacket.newBuilder();
  TcpPacket.CreateLobbyPacket receivedLobbyPacket;

  TcpPacket.ConnectPacket.Builder connectPacket = TcpPacket.ConnectPacket.newBuilder();
  TcpPacket.ConnectPacket receivedConnectPacket;

  TcpPacket.ChatPacket.Builder chatPacket = TcpPacket.ChatPacket.newBuilder();
  TcpPacket.ChatPacket receivedChatPacket;

  Thread gameThread = new Thread(this);

  public Gameserver(int noOfPlayers, String playerName) {

    /* FLOW
      1. Create lobby packet
      2. Set necessary parameters, build(), toByteArray()
      3. Write #2 to outputstream
      4. Receive, continuously until available() !=0
      5. Parse the returned packet
    */

    System.out.println();

    /* Initialize all necessary packets */
    playerSelf.setName(playerName);
    selfName = playerName;

    tcppacket.setType(TcpPacket.PacketType.CREATE_LOBBY);

    lobbyPacket.setType(TcpPacket.PacketType.CREATE_LOBBY);
    lobbyPacket.setMaxPlayers(noOfPlayers); 

    connectPacket.setType(TcpPacket.PacketType.CONNECT);
    connectPacket.setPlayer(playerSelf);

    chatPacket.setType(TcpPacket.PacketType.CHAT);
    chatPacket.setPlayer(playerSelf);


    /* Send first request (CreateLobby) to server */
    toSendArray = lobbyPacket.build().toByteArray();
    try {
      
      serverSocket = new Socket(IP_ADDRESS, PORT);
      os = serverSocket.getOutputStream();
      in = serverSocket.getInputStream();
      System.out.println("Connected to server: " + serverSocket.getInetAddress() + ":" + serverSocket.getPort() + "\n");

      /* Send first request and wait response */
      os.write(toSendArray);
      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }

      /* Receive create lobby response */
      toReceiveArray = new byte[serverResponseLength];
      readResult = in.read(toReceiveArray);
      receivedLobbyPacket = TcpPacket.CreateLobbyPacket.parseFrom(toReceiveArray);
      System.out.println("[1]Server response ");
      System.out.println("   Type: \t" + receivedLobbyPacket.getType());
      System.out.println("   lobby_id:\t" + receivedLobbyPacket.getLobbyId());
      System.out.println("   max_players:\t" + receivedLobbyPacket.getMaxPlayers());

      lobbyId = receivedLobbyPacket.getLobbyId();
      gameThread.start();


    } catch (IOException e) {
      System.err.println("Error: " + e.toString());
    } catch(Exception e) { 
      System.err.println("Error: " + e.toString());
    }
    
  }

  @Override
  public void run() {


    try {


      connectPacket.setLobbyId(lobbyId);

      toSendArray = connectPacket.build().toByteArray();
      os.write(toSendArray);

      while((serverResponseLength = in.available()) == 0) {
        // Do nothing lul
      }
      toReceiveArray = new byte[serverResponseLength];
      readResult = in.read(toReceiveArray);
      receivedConnectPacket = TcpPacket.ConnectPacket.parseFrom(toReceiveArray);
      System.out.println("\n[2]Server response: ");
      System.out.println("   Player: \t" + receivedConnectPacket.getPlayer().getName());
      System.out.println("   Player id: \t" + receivedConnectPacket.getPlayer().getId());
      System.out.println("   Update: \t" + receivedConnectPacket.getUpdate());

      selfId = receivedConnectPacket.getPlayer().getId();
      

      /* Start of chat. Do not stop until input quit */
      System.out.println("\n\nChat Start!\n");
      System.out.print(selfName + ": ");
      while(!((selfMessage = sc.next()).equals("Quit"))) {

        System.out.print(selfName + ": ");

        chatPacket.setMessage(selfMessage);
        chatPacket.setLobbyId(lobbyId);

        /* Send Chat Packet to server */
        toSendArray = chatPacket.build().toByteArray();
        while((serverResponseLength = in.available()) == 0) {
          // Do nothing lul
        }
        toReceiveArray = new byte[serverResponseLength];
        readResult = in.read(toReceiveArray);
        receivedChatPacket = TcpPacket.ChatPacket.parseFrom(toReceiveArray);
        System.out.println("\n[3]Server response: ");
        System.out.println("   Message: \t" + receivedChatPacket.getMessage());
        System.out.println("   Player Name: \t" + receivedChatPacket.getPlayer().getName());
        System.out.println("   Player id: \t" + receivedChatPacket.getPlayer().getId());


      }


    } catch (Exception e) {
      System.err.println("Error: " + e.toString());
    }

  } 


  public static void main (String[] args) throws Exception {

    if (args.length != 2) {
      System.out.println("Usage: java com.main.app.Gameserver <noOfPlayers> <playerName> ");
      System.exit(1);
    }

    new Gameserver(Integer.parseInt(args[0]), args[1]);

  }



}