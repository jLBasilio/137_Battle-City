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

public class MapFrame {
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

	public MapFrame(String mapName,int width,int height){
		this.mapName = mapName;
		this.width = width;
		this.height = height;

		createMapFrame();
	}

	private void createMapFrame(){
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
		canvas.setFocusable(false);

		chatLogs = new JTextArea(30, 25);
		chatLogs.append("Chat Panel");
		chatLogs.setFocusable(false);
		chatLogs.setEditable(false);
		chatLogsScroll = new JScrollPane(chatLogs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		chatInput = new JTextArea(8, 25);
		chatInput.append("Input chat here\n");
		chatInput.setLineWrap(true);
		chatInput.setWrapStyleWord(true);

		chatInput.setFocusable(false);
		chatInput.setEditable(false);
		chatInputScroll = new JScrollPane(chatInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// gamePanel = new JPanel();
		// gamePanel.setPreferredSize(new Dimension(900,600));
		// gamePanel.setBorder(BorderFactory.createLineBorder(Color.red));
		// gamePanel.add(canvas);

		chatPanel = new JPanel();
		chatPanel.setPreferredSize(new Dimension(300,600));
		chatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chatPanel.add(chatLogsScroll);
		chatPanel.add(chatInputScroll);


		wrapperPanel.add(canvas,BorderLayout.WEST);
		wrapperPanel.add(chatPanel,BorderLayout.EAST);

		frame.add(wrapperPanel);
		frame.pack();
	}	

	public JFrame getFrame(){
		return this.frame;
	}

	public Canvas getCanvas(){
		return this.canvas;
	}
}