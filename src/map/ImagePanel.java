import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


public class ImagePanel extends JPanel {

  private Image img;
  JButton startButton;
  JButton helpButton;
  JButton exitButton;

  public ImagePanel(String img) {
    this(new ImageIcon(img).getImage());

  }

  public ImagePanel(Image img) {
    this.img = img;
    Dimension size = new Dimension(1200, 600);
    setPreferredSize(size);
    setSize(size);
    setLayout(null);
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, 1200, 600, null);
  }

}