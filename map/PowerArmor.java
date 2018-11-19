import java.awt.Graphics;

public class PowerArmor extends PowerUp{
	

	public PowerArmor(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.powerArmor ,(int)x ,(int)y ,width ,height ,null);
	}
}