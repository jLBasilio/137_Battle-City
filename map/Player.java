import java.awt.Graphics;

public class Player extends Tank{
	// public static final int TILEWIDTH = 30, TILEHEIGHT = 60;
	
	public Player(float x,float y,int width, int height){
		super(x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.tankU ,(int)x ,(int)y ,width ,height ,null);
	}

	public void render(Graphics g,int tx, int ty, int dir){
		switch(dir){
			case 0: g.drawImage(Assets.tankU ,tx ,ty ,width ,height ,null);break;
			case 1: g.drawImage(Assets.tankR ,tx ,ty ,width ,height ,null);break;
			case 2: g.drawImage(Assets.tankD ,tx ,ty ,width ,height ,null);break;
			case 3: g.drawImage(Assets.tankL ,tx ,ty ,width ,height ,null);break;
		}
	}
}