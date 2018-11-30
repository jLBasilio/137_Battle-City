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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Main implements Runnable {

  BackgroundPanel bp;
  GridBagConstraints gbc;
  JButton startButton, helpButton, exitButton;
  JButton createLobbyButton, joinLobbyButton, createBackButton;
  JButton confirmButton, serverBackButton;
  JButton lobbyStartButton, lobbyBackButton;

  JButton testButton, testButton2, testButton3, testButton4, testButton5;
  JFrame mainFrame;
  JLabel playerNameLabel, maxPlayersLabel, lobbyIdLabel;
  JPanel startPanel, createPanel, serverPanel, lobbyIdPanel;
  JTextField playerNameInput, maxPlayersInput, lobbyIdInput;
  String backgroundImage;

  Gameserver gameServer;
  String lobbyId, playerName;
  Thread gameServerThread;

  int maxPlayers;

  public Main() {

    backgroundImage = "assets/mainBackground.png";
    lobbyId = "";

    gameServerThread = new Thread(this);


    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    // ====== START PANEL ====== //
    startButton = new JButton("START");
    startButton.setPreferredSize(new Dimension(240, 50));
    helpButton = new JButton("HELP");
    helpButton.setPreferredSize(new Dimension(240, 50));
    exitButton = new JButton("EXIT");
    exitButton.setPreferredSize(new Dimension(240, 50));
    
    // ====== CREATE PANEL ====== //
    createBackButton = new JButton("BACK");
    createBackButton.setPreferredSize(new Dimension(200, 50));
    createLobbyButton = new JButton("CREATE LOBBY");
    createLobbyButton.setPreferredSize(new Dimension(240, 50));
    joinLobbyButton = new JButton("JOIN LOBBY");
    joinLobbyButton.setPreferredSize(new Dimension(240, 50));

    // ====== SERVER PANEL ====== //
    playerNameInput = new JTextField(20);
    maxPlayersInput = new JTextField(20);
    playerNameLabel = new JLabel("Your Name");
    maxPlayersLabel = new JLabel("Max Players");
    confirmButton = new JButton("CONFIRM");
    confirmButton.setPreferredSize(new Dimension(200, 50));
    serverBackButton = new JButton("BACK");
    serverBackButton.setPreferredSize(new Dimension(200, 50));

    // ====== TEST BUTTONS ====== //
    testButton = new JButton("TEST");
    testButton.setPreferredSize(new Dimension(240, 50));
    testButton2 = new JButton("TEST");
    testButton2.setPreferredSize(new Dimension(240, 50));
    testButton3 = new JButton("TEST");
    testButton3.setPreferredSize(new Dimension(240, 50));
    testButton4 = new JButton("TEST");
    testButton4.setPreferredSize(new Dimension(240, 50));
    testButton5 = new JButton("TEST");
    testButton5.setPreferredSize(new Dimension(240, 50));

    lobbyIdInput = new JTextField(20);
    lobbyIdLabel = new JLabel("LOBBY ID");
    lobbyStartButton = new JButton("START GAME");
    lobbyStartButton.setPreferredSize(new Dimension(240, 50));
    lobbyBackButton = new JButton("BACK");
    lobbyBackButton.setPreferredSize(new Dimension(200, 50));



    /* ========== START(1) PANEL =========== */    

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

    
    /* ========== CREATE(2) PANEL =========== */    

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


    /* ========== SERVER PANEL =========== */    

    serverPanel = new BackgroundPanel(backgroundImage);
    serverPanel.setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.gridy = 0;
    serverPanel.add(playerNameLabel, gbc);
    gbc.gridx = 1;
    serverPanel.add(playerNameInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    serverPanel.add(maxPlayersLabel, gbc);
    gbc.gridx = 1;
    serverPanel.add(maxPlayersInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    serverPanel.add(serverBackButton, gbc);
    gbc.gridx = 1;
    serverPanel.add(confirmButton, gbc);



    /* ========== SERVER PANEL =========== */    

    lobbyIdPanel = new BackgroundPanel(backgroundImage);
    lobbyIdPanel.setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.gridy = 0;
    lobbyIdPanel.add(lobbyIdLabel, gbc);
    gbc.gridx = 1;
    lobbyIdPanel.add(lobbyIdInput, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    lobbyIdPanel.add(lobbyBackButton, gbc);
    gbc.gridx = 1;
    lobbyIdPanel.add(lobbyStartButton, gbc);




    // ===================== BUTTON LISTENERS =================== //

    // Start button evenr
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        mainFrame.setContentPane(createPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    // help button event
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

      }
    });



    createBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(startPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    confirmButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        

        /* Create the chat server */
        playerName = playerNameInput.getText();
        maxPlayers = Integer.parseInt(maxPlayersInput.getText());
        gameServerThread.start();

        System.out.println("Wow");
        mainFrame.setContentPane(lobbyIdPanel);
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

    
    lobbyStartButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        // Game start!

      }
    });

    lobbyBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        mainFrame.setContentPane(serverPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });


  }

  @Override
  public void run() {

    // TODO: NOT WORKING
    gameServer = new Gameserver(playerName, maxPlayers);
    lobbyId = gameServer.getLobbyId();
    System.out.println("Lobby Id retrieved.");
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