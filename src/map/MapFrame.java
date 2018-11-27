import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class MapFrame {
	private JFrame frame;
	private Canvas canvas;
	private JPanel wrapperPanel;

	private JPanel gamePanel;
	private JPanel chatPanel;
	private JTextArea chatLogs;
	private JTextArea chatInput;

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

		gamePanel = new JPanel();

		chatPanel = new JPanel(new BorderLayout());
		chatLogs = new JTextArea();
		chatLogs.append("Hello");

		chatPanel.add(chatLogs, BorderLayout.CENTER);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(900, 600));
		canvas.setFocusable(false);

		gamePanel.add(canvas);
		gamePanel.add(chatPanel, BorderLayout.CENTER);

		wrapperPanel.add(gamePanel, BorderLayout.CENTER);
		// wrapperPanel.add(chatPanel, BorderLayout.EAST);

		// frame.add(canvas);
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