package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.*;
import java.io.*;


public class Main extends JPanel implements KeyListener{
  
  static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
  static GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();

  public Main () {

    
  } 

  public void keyPressed(KeyEvent key) {

  }

  public void keyTyped(KeyEvent ke) {

  }

  public void keyReleased(KeyEvent ke) {

  }


  public static void main (String[] args){
    JFrame frame = new JFrame("CMSC 137 - JavBois");

    frame.setPreferredSize(new Dimension((defaultScreen.getDisplayMode().getWidth())/2, 700));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Container container = frame.getContentPane();
    Main main = new Main();
    container.add(main);

    frame.pack();
    frame.setVisible(true);
    frame.setFocusable(true);
    frame.addKeyListener(main);

  }
}
