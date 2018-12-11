package com.main.app;

import java.awt.Image;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BackgroundPanel extends JPanel {

  Image image;
  public BackgroundPanel(String fileName) {
    try {
      image = ImageIO.read(new File(fileName));
    } catch(Exception e) { /*handled in paintComponent()*/ }
  }
 
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image != null)
      g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
  }
}