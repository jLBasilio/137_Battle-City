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


public class Gameclient implements Runnable {

  public static final int PORT = 80;
  public static final String IP_ADDRESS = "202.92.144.45";

  Socket serverSocket;
  OutputStream os;
  InputStream in;

  byte[] toSendArray, toReceiveArray, toSendChat, toReceiveChat;
  int serverResponseLength, receiveLength, readResult, receiveResult;
  String serverResponse, lobbyId, selfName, selfId, selfMessage;
  Scanner sc = new Scanner(System.in);

  PlayerProtos.Player.Builder playerSelf = PlayerProtos.Player.newBuilder();

  TcpPacket.ConnectPacket.Builder connectPacket = TcpPacket.ConnectPacket.newBuilder();
  TcpPacket.ConnectPacket receivedConnectPacket;

  TcpPacket.ChatPacket.Builder chatPacket = TcpPacket.ChatPacket.newBuilder();
  TcpPacket.ChatPacket receivedChatPacket;

  Thread gameThread = new Thread(this);


  public Gameclient (String playerName, String lobbyIdIn) {

    System.out.println();

    /* Initialize all necessary packets */
    playerSelf.setName(playerName);
    selfName = playerName;
    lobbyId = lobbyIdIn;

    connectPacket.setType(TcpPacket.PacketType.CONNECT);
    connectPacket.setPlayer(playerSelf);
    connectPacket.setLobbyId(lobbyId);

    chatPacket.setType(TcpPacket.PacketType.CHAT);
    chatPacket.setPlayer(playerSelf);

    try {
      
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

      /* For continuous send */
      gameThread.start();

      /* For continuous receive */
      while(true) {
        while((receiveLength = in.available()) == 0) { /* Do nothing lul */ }

        toReceiveChat = new byte[receiveLength];
        receiveResult = in.read(toReceiveChat);

        /* Skip if CONNECT PACKET */
        if(TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.CONNECT) {
          System.out.println("\n[!] " + TcpPacket.ConnectPacket.parseFrom(toReceiveChat).getPlayer().getName() + " connected!\n");
          continue;
        }

        /* Notify if DISCONNECT PACKET */
        if (TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.DISCONNECT) {
          System.out.println("\n[!] " + TcpPacket.DisconnectPacket.parseFrom(toReceiveChat).getPlayer().getName() + " disconnected.");
          System.out.println("[!] STATUS: " + TcpPacket.DisconnectPacket.parseFrom(toReceiveChat).getUpdate() + "\n");
          continue;
        }

        /* Parse message if CHAT PACKET */
        else if (TcpPacket.parseFrom(toReceiveChat).getType() == TcpPacket.PacketType.CHAT) {
          receivedChatPacket = TcpPacket.ChatPacket.parseFrom(toReceiveChat);
          System.out.println(receivedChatPacket.getPlayer().getName() + ": " + receivedChatPacket.getMessage());
        }

      }

    } catch(Exception e) { 
      System.err.println("[Client] main: " + e.toString());
    }

  }


  public void run() {
    
    try {

      /* Start of chat. Do not stop until input quit */
      System.out.println("\n\nChat Start!\n");
      while(!((selfMessage = sc.nextLine()).equals("Quit"))) {

        chatPacket.setMessage(selfMessage);
        chatPacket.setLobbyId(lobbyId);

        /* Send Chat Packet to server */
        toSendChat = chatPacket.build().toByteArray();
        os.write(toSendChat);

      }

    } catch (Exception e) {
      System.err.println("Error in run(): " + e.toString());
    }
  } 


  public static void main (String[] args) throws Exception {

    if (args.length != 2) {
      System.out.println("Usage: java com.main.app.Gameclient <playerName> <lobbyId>");
      System.exit(1);
    }

    new Gameclient(args[0], args[1]);

  }

}



