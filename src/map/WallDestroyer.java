import java.awt.Graphics;

public class WallDestroyer extends PowerUp{
	

	public WallDestroyer(Handler handler,int x,int y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.wallDestroyer ,x ,y ,width ,height ,null);
	}
}