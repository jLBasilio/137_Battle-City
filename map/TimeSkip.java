import java.awt.Graphics;

public class TimeSkip extends PowerUp{
	

	public TimeSkip(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.timeSkip ,(int)x ,(int)y ,width ,height ,null);
	}
}