import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class MapFrame{
	private JFrame frame;
	private Canvas canvas;

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

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setFocusable(false);

		frame.add(canvas);
		frame.pack();
	}	

	public JFrame getFrame(){
		return this.frame;
	}

	public Canvas getCanvas(){
		return this.canvas;
	}
}