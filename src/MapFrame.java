package com.main.app;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapFrame implements KeyListener {
	private JFrame frame;
	private Canvas canvas;
	private JPanel wrapperPanel;

	private JPanel gamePanel;
	private JPanel chatPanel;

	private JTextArea chatLogs;
	private JTextArea chatInput;

	private JScrollPane chatLogsScroll;
	private JScrollPane chatInputScroll;

	private String mapName;
	private int width, height;

	public ChatResource chatResource;
	// main.chatResource.sendHandler


	public MapFrame(String mapName, int width, int height, Main main) {
		this.mapName = mapName;
		this.width = width;
		this.height = height;
		this.chatResource = main.chatResource;

		instantiateSelfToChatResource();
		createMapFrame();
	}

	private void createMapFrame() {
		frame = new JFrame(mapName);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		wrapperPanel =  new JPanel(new BorderLayout());
		wrapperPanel.setPreferredSize(new Dimension(width,height));
		wrapperPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(900, 600));

		chatLogs = new JTextArea(30, 25);
		chatLogs.append(" === BEGIN CHAT === \n");
		chatLogs.setFocusable(false);
		chatLogs.setEditable(false);
		chatLogsScroll = new JScrollPane(chatLogs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		chatInput = new JTextArea(8, 25);
		chatInput.setLineWrap(true);
		chatInput.setWrapStyleWord(true);
		chatInputScroll = new JScrollPane(chatInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		chatPanel = new JPanel();
		chatPanel.setPreferredSize(new Dimension(300,600));
		chatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chatPanel.add(chatLogsScroll);
		chatPanel.add(chatInputScroll);

		wrapperPanel.add(canvas,BorderLayout.WEST);
		wrapperPanel.add(chatPanel,BorderLayout.EAST);

		frame.add(wrapperPanel);
		frame.pack();

		chatInput.addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
        	e.consume();
        	sendMessage(chatInput.getText());
        	chatInput.setText("");
        	canvas.requestFocus();
        }
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }
		});

	}	

	public JFrame getFrame(){
		return this.frame;
	}

	public Canvas getCanvas(){
		return this.canvas;
	}

	public void addToChatLogs(String message) {

		chatLogs.append(message + "\n");
		chatLogs.setCaretPosition(chatLogs.getDocument().getLength());

	}

	public void sendMessage(String message) {

		chatResource.handleMessageSend(message);

	}


	public void instantiateSelfToChatResource() {

		chatResource.instantiateMapFrame(this);

	}



  public void keyPressed(KeyEvent ke){
    switch(ke.getKeyCode()){
      case KeyEvent.VK_UP:
        System.out.println("Moved up");
        break;
      case KeyEvent.VK_RIGHT:
        System.out.println("Moved right");
        break;
      case KeyEvent.VK_DOWN:
        System.out.println("Moved down");
        break;
      case KeyEvent.VK_LEFT:
        System.out.println("Moved left");
        break;
      case KeyEvent.VK_SPACE:
        System.out.println("Moved fired");
        break;
    }
  }


  public void keyTyped(KeyEvent ke) {
    
  }

  public void keyReleased(KeyEvent ke){
    // pressed = false;
    // firing = false;
  }






}