package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import com.main.app.PlayerProtos.*;
import com.main.app.TcpPacketProtos.*;


import java.util.Scanner;


public class ChatSendHandler implements Runnable {

  ChatResource serverResource;
  Scanner sc = new Scanner(System.in);

  public ChatSendHandler(ChatResource serverResource) {
    this.serverResource = serverResource;
  }



  @Override
  public void run() {

    /* For continuous send */
    while(true) {

      try {
        serverResource.selfMessage = sc.nextLine();

        if (serverResource.selfMessage.equals("Quit")) {

          /* Send Disconnect Packet to server */
          serverResource.toSendChat = serverResource.disconnectPacket.build().toByteArray();
          serverResource.os.write(serverResource.toSendChat);  
          serverResource.stopReceiving();
          System.out.println("You have disconnected.");
          break;
          
        }

        else if (serverResource.selfMessage.equals("?noPL")){
          System.out.println("Total Connected Players: " + serverResource.getCountPlayers());
        }

        else if (serverResource.selfMessage.equals("?listPL")){
          System.out.println("Players connected: ");
        }

        else {

          serverResource.chatPacket.setMessage(serverResource.selfMessage);
          serverResource.chatPacket.setLobbyId(serverResource.lobbyId);

          /* Send Chat Packet to server */
          serverResource.toSendChat = serverResource.chatPacket.build().toByteArray();
          serverResource.os.write(serverResource.toSendChat);
          
        }        
      } catch (Exception e) {
        System.err.println("[Send ERR]:" + e.toString());
      }

    }

  }

}
