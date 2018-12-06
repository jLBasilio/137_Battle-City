package com.main.app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Main implements Runnable {

  /* Components are arranged according to the panel they belong */
  // ====== START PANEL ====== //
  JPanel startPanel;
  JButton startButton, helpButton, exitButton;

  // ====== CREATE PANEL ====== //
  JPanel createPanel;
  JButton createLobbyButton, joinLobbyButton, createBackButton;


  // ====== SERVER SET PANEL ====== //
  JPanel serverPanel;
  JLabel playerNameLabel, serverIPLabel, maxPlayersLabel;
  JTextField playerNameInput, serverIPInput, maxPlayersInput;
  JButton confirmButton, serverBackButton;

  // ====== SERVER LOBBY PANEL ====== //
  JPanel serverLobbyPanel;
  JLabel currentlyInLobby, serverLobbyLobbyId, serverLobbyPlayerLabel;
  JTextField serverLobbyLobbyIdField, lobbyPlayerCount;
  JButton serverLobbyStart, serverLobbyBack;


  // ====== CLIENT JOIN LOBBY PANEL ====== //
  JPanel clientJoinLobbyPanel;
  JLabel clientJoinLobbyLobbyId, clientJoinLobbyName, inputserverLobbyLobbyId, errorLabel;
  JLabel udpLobbyLabel;
  JTextField clientJoinLobbyLobbyIdField, clientJoinLobbyNameField, udpLobbyField;
  JButton clientJoinLobbyConfirm, clientJoinLobbyBack;


  // ====== CLIENT LOBBY PANEL ====== //
  JPanel clientLobbyPanel;
  JLabel clientLobbyInLobbyLabel, clientLobbyLobbyId, clientLobbyPlayers;
  JTextField clientLobbyLobbyIdField, clientLobbyPlayersField;
  JButton clientLobbyBack, clientLobbyConfirm;
  

  /* ====== Other Constants ====== */
  JFrame mainFrame;
  BackgroundPanel bp;
  GridBagConstraints gbc;
  String backgroundImage;
  Dimension defaultDimension, cancelDimension;

  ChatResource chatResource;
  Launcher gameLauncher;
  String lobbyId, playerName, serverIP;
  Thread chatResourceThread;


  JButton testButton, testButton2, testButton3, testButton4, testButton5;
  int maxPlayers, connectedPlayers;



  public Main() {

    try {

      System.out.println(InetAddress.getLocalHost().getHostAddress());
    } catch (Exception e){}

    backgroundImage = "assets/mainBackground.png";
    lobbyId = "";
    chatResourceThread = new Thread(this);
    
    defaultDimension = new Dimension(240, 50);
    cancelDimension = new Dimension(200, 50);

    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);


    // ====== START PANEL ====== //
    startButton = new JButton("START");
    startButton.setPreferredSize(defaultDimension);
    helpButton = new JButton("HELP");
    helpButton.setPreferredSize(defaultDimension);
    exitButton = new JButton("EXIT");
    exitButton.setPreferredSize(defaultDimension);
    
    // ====== CREATE PANEL ====== //
    createBackButton = new JButton("BACK");
    createBackButton.setPreferredSize(cancelDimension);
    createLobbyButton = new JButton("CREATE LOBBY");
    createLobbyButton.setPreferredSize(defaultDimension);
    joinLobbyButton = new JButton("JOIN LOBBY");
    joinLobbyButton.setPreferredSize(defaultDimension);

    // ====== SERVER SET PANEL ====== //
    playerNameInput = new JTextField(20);
    serverIPInput = new JTextField(20);
    maxPlayersInput = new JTextField(20);
    playerNameLabel = new JLabel("Your Name");
    serverIPLabel = new JLabel("Your IP");
    maxPlayersLabel = new JLabel("Max Players");
    confirmButton = new JButton("CONFIRM");
    confirmButton.setPreferredSize(cancelDimension);
    serverBackButton = new JButton("BACK");
    serverBackButton.setPreferredSize(cancelDimension);

    // ====== SERVER LOBBY PANEL ====== //
    currentlyInLobby = new JLabel("YOU ARE CURRENTLY IN LOBBY.");
    serverLobbyLobbyIdField = new JTextField(20);
    serverLobbyLobbyIdField.setText("-----");
    serverLobbyLobbyIdField.setEditable(false);
    serverLobbyLobbyId = new JLabel("LOBBY ID");
    serverLobbyPlayerLabel = new JLabel("PLAYERS ");
    lobbyPlayerCount = new JTextField(20);
    lobbyPlayerCount.setEditable(false);
    serverLobbyStart = new JButton("WAITING FOR PLAYERS");
    serverLobbyStart.setPreferredSize(defaultDimension);
    serverLobbyStart.setEnabled(false);
    serverLobbyBack = new JButton("BACK");
    serverLobbyBack.setPreferredSize(cancelDimension);


    // ====== CLIENT JOIN LOBBY PANEL ====== //
    // inputserverLobbyLobbyId = new JLabel("Input LOBBY ID");
    // errorLabel = new JLabel("ERROR");
    clientJoinLobbyName = new JLabel("Your Name");
    clientJoinLobbyNameField = new JTextField(20);
    clientJoinLobbyLobbyId = new JLabel("LOBBY ID");
    clientJoinLobbyConfirm = new JButton("CONFIRM"); 
    clientJoinLobbyConfirm.setPreferredSize(defaultDimension);
    clientJoinLobbyBack = new JButton("BACK");
    clientJoinLobbyBack.setPreferredSize(cancelDimension);
    clientJoinLobbyLobbyIdField = new JTextField(20);
    udpLobbyLabel = new JLabel("SERVER IP");
    udpLobbyField = new JTextField(20);


    // ====== CLIENT LOBBY PANEL ====== //
    clientLobbyInLobbyLabel = new JLabel("YOU ARE CURRENTLY IN LOBBY.");
    clientLobbyLobbyId = new JLabel("LOBBY ID");
    clientLobbyPlayers = new JLabel("PLAYERS");
    clientLobbyLobbyIdField = new JTextField(20);
    clientLobbyLobbyIdField.setEditable(false);
    clientLobbyPlayersField = new JTextField(20);
    clientLobbyPlayersField.setEditable(false);
    clientLobbyBack = new JButton("BACK");
    clientLobbyBack.setPreferredSize(cancelDimension);
    clientLobbyConfirm = new JButton("WAITING FOR SERVER");
    clientLobbyConfirm.setPreferredSize(defaultDimension);
    clientLobbyConfirm.setEnabled(false);


    // ====== TEST BUTTONS ====== //
    testButton = new JButton("TEST");
    testButton.setPreferredSize(defaultDimension);
    testButton2 = new JButton("TEST");
    testButton2.setPreferredSize(defaultDimension);
    testButton3 = new JButton("TEST");
    testButton3.setPreferredSize(defaultDimension);
    testButton4 = new JButton("TEST");
    testButton4.setPreferredSize(defaultDimension);
    testButton5 = new JButton("TEST");
    testButton5.setPreferredSize(defaultDimension);


    /* ========== START PANEL =========== */    

    startPanel = new BackgroundPanel(backgroundImage);
    startPanel.setLayout(new GridBagLayout());
    startPanel.setOpaque(false);

    // Position buttons
    gbc.gridx = 0;
    gbc.gridy = 0;
    startPanel.add(startButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    startPanel.add(helpButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    startPanel.add(exitButton, gbc);

    
    /* ========== CREATE PANEL =========== */    

    // Create container panel
    createPanel = new BackgroundPanel(backgroundImage);
    createPanel.setLayout(new GridBagLayout());
    createPanel.setOpaque(false);

    // Position buttons
    gbc.gridx = 0;
    gbc.gridy = 0;
    createPanel.add(createLobbyButton, gbc);

    gbc.gridy = 1;
    createPanel.add(joinLobbyButton, gbc);

    gbc.gridy = 2;
    createPanel.add(createBackButton, gbc);


    /* ========== SERVER SET PANEL =========== */    

    serverPanel = new BackgroundPanel(backgroundImage);
    serverPanel.setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.gridy = 0;
    serverPanel.add(playerNameLabel, gbc);
    gbc.gridx = 1;
    serverPanel.add(playerNameInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    serverPanel.add(serverIPLabel, gbc);
    gbc.gridx = 1;
    serverPanel.add(serverIPInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    serverPanel.add(maxPlayersLabel, gbc);
    gbc.gridx = 1;
    serverPanel.add(maxPlayersInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    serverPanel.add(serverBackButton, gbc);
    gbc.gridx = 1;
    serverPanel.add(confirmButton, gbc);



    /* ========== CLIENT JOIN LOBBY PANEL =========== */    
    clientJoinLobbyPanel = new BackgroundPanel(backgroundImage);
    clientJoinLobbyPanel.setLayout(new GridBagLayout());

    // TODO: Conditional add, when lobby does not exist

    gbc.gridx = 0;
    gbc.gridy = 0;
    clientJoinLobbyPanel.add(clientJoinLobbyName, gbc);
    gbc.gridx = 1;
    clientJoinLobbyPanel.add(clientJoinLobbyNameField, gbc);  

    gbc.gridx = 0;
    gbc.gridy = 1;
    clientJoinLobbyPanel.add(clientJoinLobbyLobbyId, gbc);
    gbc.gridx = 1;
    clientJoinLobbyPanel.add(clientJoinLobbyLobbyIdField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    clientJoinLobbyPanel.add(udpLobbyLabel, gbc);
    gbc.gridx = 1;
    clientJoinLobbyPanel.add(udpLobbyField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    clientJoinLobbyPanel.add(clientJoinLobbyBack, gbc);
    gbc.gridx = 1;
    clientJoinLobbyPanel.add(clientJoinLobbyConfirm, gbc);



    /* ========== SERVER LOBBY PANEL =========== */    

    serverLobbyPanel = new BackgroundPanel(backgroundImage);
    serverLobbyPanel.setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.gridy = 0;
    serverLobbyPanel.add(currentlyInLobby, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    serverLobbyPanel.add(serverLobbyLobbyId, gbc);
    gbc.gridx = 1;
    serverLobbyPanel.add(serverLobbyLobbyIdField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    serverLobbyPanel.add(serverLobbyPlayerLabel, gbc);
    gbc.gridx = 1;
    serverLobbyPanel.add(lobbyPlayerCount, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    serverLobbyPanel.add(serverLobbyBack, gbc);
    gbc.gridx = 1;
    serverLobbyPanel.add(serverLobbyStart, gbc);



    /* ========== CLIENT LOBBY PANEL =========== */    

    clientLobbyPanel = new BackgroundPanel(backgroundImage);
    clientLobbyPanel.setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.gridy = 0;
    clientLobbyPanel.add(clientLobbyInLobbyLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    clientLobbyPanel.add(clientLobbyLobbyId, gbc);
    gbc.gridx = 1;
    clientLobbyPanel.add(clientLobbyLobbyIdField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    clientLobbyPanel.add(clientLobbyPlayers, gbc);
    gbc.gridx = 1;
    clientLobbyPanel.add(clientLobbyPlayersField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    clientLobbyPanel.add(clientLobbyBack, gbc);
    gbc.gridx = 1;
    clientLobbyPanel.add(clientLobbyConfirm, gbc);






    // ===================== BUTTON LISTENERS =================== //

    /* ===================== START PANEL ===================== */
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        mainFrame.setContentPane(createPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    helpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        // Display help panel

      }
    });

    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    
    /* ===================== CREATE PANEL ===================== */
    createLobbyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        // Create lobby UI
        mainFrame.setContentPane(serverPanel);
        mainFrame.invalidate();
        mainFrame.validate();


      }
    });

    joinLobbyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        // Join lobby UI
        mainFrame.setContentPane(clientJoinLobbyPanel);
        mainFrame.invalidate();
        mainFrame.validate();
      }
    });

    createBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(startPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });


    /* ===================== SERVER SET PANEL ===================== */
    confirmButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        /* Create the chat server */
        playerName = playerNameInput.getText();
        maxPlayers = Integer.parseInt(maxPlayersInput.getText());

        serverIP = serverIPInput.getText();


        // Instantiate a chat server resource
        chatResource = new ChatResource(playerName, maxPlayers);
        lobbyId = chatResource.getLobbyId();
        
        Thread crThread = new Thread(chatResource);
        crThread.start();
        chatResourceThread.start();

        mainFrame.setContentPane(serverLobbyPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    serverBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(createPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    /* ===================== CLIENT JOIN LOBBY PANEL ===================== s*/
    clientJoinLobbyBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(createPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    clientJoinLobbyConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        playerName = clientJoinLobbyNameField.getText();
        lobbyId = clientJoinLobbyLobbyIdField.getText();
        chatResource = new ChatResource(playerName, lobbyId);
        connectedPlayers = chatResource.getCountPlayers();
        maxPlayers = chatResource.getMaxPlayers();

        serverIP = udpLobbyField.getText();

        Thread crThread = new Thread(chatResource);
        crThread.start();
        chatResourceThread.start();

        mainFrame.setContentPane(clientLobbyPanel);
        mainFrame.invalidate();
        mainFrame.validate();



      }
    });

    /* ===================== SERVER LOBBY PANEL ===================== s*/
    
    serverLobbyStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        startGame();

      }
    });

    serverLobbyBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(serverPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });


    /* ===================== CLIENT LOBBY PANEL ===================== s*/

    clientLobbyBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(clientJoinLobbyPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });


  }

  @Override
  public void run() {

    while(true) {

      try{
        
        connectedPlayers = chatResource.getCountPlayers();
        System.out.println("Connected PLAYERS => " + connectedPlayers);
        
        // For server only
        serverLobbyLobbyIdField.setText(lobbyId);
        lobbyPlayerCount.setText(Integer.toString(connectedPlayers) + "/" + maxPlayersInput.getText());
        if(connectedPlayers == maxPlayers) {
          serverLobbyStart.setEnabled(true);
          serverLobbyStart.setText("START GAME");
          break;
        }

        // For client only
        clientLobbyLobbyIdField.setText(lobbyId);
        clientLobbyPlayersField.setText(Integer.toString(connectedPlayers));
        if(chatResource.checkIfGameStarted()) {
          mainFrame.dispose();
          Thread.sleep(1500);
          gameLauncher = new Launcher(this);
          break;
        }

        Thread.sleep(1500);

      } catch (Exception e) {}
      
    }

  }


  // For server only
  public void startGame() {

    // Close window and spawn the game proper
    mainFrame.dispose();
    try{

      Thread.sleep(1500);

    } catch (Exception e) {}
    gameLauncher = new Launcher(this);
    chatResource.setAndBroadcastStart();
  }


  public void buildGUI() {

    mainFrame = new JFrame("JavBoiz - BattleCity");
    mainFrame.pack();
    mainFrame.setSize(1200, 600);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);
    mainFrame.setContentPane(startPanel);

  }


  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run(){
        new Main().buildGUI();
      }
    });
  }

}

/* TODO:
[CLIENT]
  - Check if lobby exists in lobby id
[SERVER]
  - Check if server exists
*/
