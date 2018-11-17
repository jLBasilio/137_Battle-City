import java.awt.Graphics;

public class Player extends Tank{
	private int dir;

	public Player(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){
		if (handler.getGame().getKeyHandler().isKeyPressed()) {
			switch(handler.getGame().getKeyHandler().getDirection()){
				case 0:
					dir=0;
					if((y-2) >= 0){
						y-=2;
					}
					break;
				case 1:
					dir=1;
					if((x+2) <= 870){
						x+=2;
					}
					break;
				case 2:
					dir=2;
					if((y+2) <= 570){
						y+=2;
					}
					break;
				case 3:
					dir=3;
					if((x-2) >= 0){
						x-=2;
					}
					break;
			}
		}
	}

	public void render(Graphics g){
		switch(dir){
			case 0: g.drawImage(Assets.tankU ,(int)x ,(int)y ,width ,height ,null);break;
			case 1: g.drawImage(Assets.tankR ,(int)x ,(int)y ,width ,height ,null);break;
			case 2: g.drawImage(Assets.tankD ,(int)x ,(int)y ,width ,height ,null);break;
			case 3: g.drawImage(Assets.tankL ,(int)x ,(int)y ,width ,height ,null);break;
		}
	}
}