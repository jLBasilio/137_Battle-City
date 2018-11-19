import java.awt.Graphics;

public class WallDestroyer extends PowerUp{
	

	public WallDestroyer(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.wallDestroyer ,(int)x ,(int)y ,width ,height ,null);
	}
}