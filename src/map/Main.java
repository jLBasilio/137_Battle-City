import java.awt.BorderLayout;
import java.awt.CardLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Main extends JPanel {


  CardLayout mainLayout;

  JButton startButton, helpButton, exitButton;
  JButton createLobby, joinLobby;

  JPanel firstPanel, secondPanel, thirdPanel;


  GridBagConstraints gbc;
  BackgroundPanel bp;
  JFrame mainFrame;

  public Main() {


    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    startButton = new JButton("START");
    startButton.setPreferredSize(new Dimension(300, 50));
    
    helpButton = new JButton("HELP");
    helpButton.setPreferredSize(new Dimension(300, 50));
    
    exitButton = new JButton("EXIT");
    exitButton.setPreferredSize(new Dimension(300, 50));

    createLobby = new JButton("CREATE LOBBY");
    createLobby.setPreferredSize(new Dimension(300, 50));

    joinLobby = new JButton("JOIN LOBBY");
    joinLobby.setPreferredSize(new Dimension(300, 50));



    /* ========== FIRST PANEL =========== */    

    firstPanel = new BackgroundPanel(null);
    firstPanel.setLayout(new GridBagLayout());
    
    // Position startButton
    gbc.gridx = 0;
    gbc.gridy = 0;
    firstPanel.add(startButton, gbc);

    // Position helpButton
    gbc.gridx = 0;
    gbc.gridy = 1;
    firstPanel.add(helpButton, gbc);

    // Position exitButton
    gbc.gridx = 0;
    gbc.gridy = 2;
    firstPanel.add(exitButton, gbc);
    firstPanel.setOpaque(false);


    /* ========== SECOND PANEL =========== */    

    // Create container panel
    secondPanel = new BackgroundPanel("mainBackground.png");
    secondPanel.setLayout(new GridBagLayout());

    // Position createLobby
    gbc.gridx = 0;
    gbc.gridy = 0;
    secondPanel.add(createLobby, gbc);

    // Position joinLobby
    gbc.gridx = 0;
    gbc.gridy = 1;
    secondPanel.add(joinLobby, gbc);
    secondPanel.setOpaque(false);

    /* ========== SECOND PANEL =========== */ 




    gbc.gridheight = 3;



  }

  public void buildGUI() {

    bp = new BackgroundPanel("mainBackground.png");
    
    mainFrame = new JFrame("JavBoiz - BattleCity");
    mainFrame.pack();
    mainFrame.setSize(1200, 600);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);
    mainFrame.add(bp);

    bp.setLayout(new GridBagLayout());
    bp.add(firstPanel, new GridBagConstraints());





    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        // Go to second panel
        mainFrame.setContentPane(secondPanel);
        mainFrame.invalidate();
        mainFrame.validate();

      }
    });

    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });



  }


  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run(){
        new Main().buildGUI();
      }
    });
  }

}