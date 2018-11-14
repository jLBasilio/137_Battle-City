package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import com.main.app.PlayerProtos.*;
import com.main.app.TcpPacketProtos.*;

import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Gameserver implements Runnable {

  public static final int PORT = 80;
  public static final String IP_ADDRESS = "202.92.144.45";
  Socket serverSocket;
  BufferedReader br;
  BufferedOutputStream out;
  BufferedReader dIn;
  DataOutputStream dOut;
  Thread gameThread = new Thread(this);
  byte[] toSend = new byte[1024];
  String serverResponse;

  TcpPacket.Builder tcppacket = TcpPacket.newBuilder();
  TcpPacket.CreateLobbyPacket.Builder lobbyPacket = TcpPacket.CreateLobbyPacket.newBuilder();
  TcpPacket.ChatPacket.Builder chatpacket = TcpPacket.ChatPacket.newBuilder();


  public Gameserver(int noOfPlayers) {

    System.out.println("\n");

    // Set type of request to server
    tcppacket.setType(TcpPacket.PacketType.CREATE_LOBBY);
    lobbyPacket.setType(TcpPacket.PacketType.CREATE_LOBBY);
    lobbyPacket.setMaxPlayers(5); 
    System.out.println("Type: " + lobbyPacket.getType());
    System.out.println("Max players: " + lobbyPacket.getMaxPlayers());


    System.out.println("All lobby fields are set: " + lobbyPacket.isInitialized() + "\n");
    toSend = lobbyPacket.build().toByteArray();


    // Send byte of request to server
    try {
      
      serverSocket = new Socket(IP_ADDRESS, PORT);
      System.out.println("Connected to server: " + serverSocket.getInetAddress() + ":" + serverSocket.getPort() + "\n");

      dOut = new DataOutputStream(serverSocket.getOutputStream());
      dOut.writeInt(1024);
      dOut.write(toSend);

      dIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
      serverResponse = dIn.readLine();
      System.out.println(serverResponse);


    } catch (IOException e) {
      System.err.println("Error: " + e.toString());
    } catch(Exception e) { 
      System.err.println("Error: " + e.toString());
    }
    
  }

  @Override
  public void run() {

    while(true) {

      try {



      } catch (Exception e) {
        System.err.println("Error: " + e.toString());
      }
    }

  } 


  public static void main (String[] args) throws Exception {

    if (args.length != 1) {
      System.out.println("Usage: java com.main.app.Gameserver <noOfPlayers>");
      System.exit(1);
    }

    new Gameserver(Integer.parseInt(args[0]));

  }



}