import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Tank implements Constants{
	private int dir;

	public Player(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);

		bounds.x = (int)x;
		bounds.y = (int)y;
		bounds.width = width;
		bounds.height = height;
	}

	public void update(){
		if (handler.getGame().getKeyHandler().isKeyPressed()) {
			switch(handler.getGame().getKeyHandler().getDirection()){
				case 0: // move up
					dir=0;
					if((y-moveSpeed) >= 0){
						if(!collision(x,y) && !collision(x+TILE_WIDTH,y)){ //checks upper right and upper left corner for collision
							y-=moveSpeed;
						}
						else{
							y+=1; //move back if collision detected;
							System.out.println("Collision detected @ top!");
						}
					}
					break;
				case 1: // move right
					dir=1;
					if((x+moveSpeed) <= 870){
						if(!collision(x+TILE_WIDTH,y) && !collision(x+TILE_WIDTH,y+TILE_HEIGHT)){ //checks upper left and lower left corner for collision
							x+=moveSpeed;
						}
						else{
							x-=1; //move back if collision detected;
							System.out.println("Collision detected @ right!");
						}
					}
					break;
				case 2: // move down
					dir=2;
					if((y+moveSpeed) <= 570){
						if(!collision(x,y+TILE_HEIGHT) && !collision(x+TILE_WIDTH,y+TILE_HEIGHT)){ //checks lower right and lower left corner for collision
							y+=moveSpeed;
						}
						else{
							y-=1; //move back if collision detected;
							System.out.println("Collision detected @ bottom!");
						}
					}
					break;
				case 3: // move left
					dir=3;
					if((x-moveSpeed) >= 0){
						if(!collision(x,y) && !collision(x,y+TILE_HEIGHT)){ //checks upper right and lower right corner for collision
							x-=moveSpeed;
						}
						else{
							x+=1; //move back if collision detected;
							System.out.println("Collision detected @ left!");
						}
					}
					break;
			}
		}
	}

	public void render(Graphics g){
		//render sprite base on the direction the tank is facing/moving
		switch(dir){
			case 0: g.drawImage(Assets.tankU ,(int)x ,(int)y ,width ,height ,null);break;
			case 1: g.drawImage(Assets.tankR ,(int)x ,(int)y ,width ,height ,null);break;
			case 2: g.drawImage(Assets.tankD ,(int)x ,(int)y ,width ,height ,null);break;
			case 3: g.drawImage(Assets.tankL ,(int)x ,(int)y ,width ,height ,null);break;
		}
	}

	public boolean collision(float tx, float ty){
		System.out.println("Detecting collision.");

		// Rectangle r = this.getBounds();
		// Rectangle r2 = new Rectangle((int)tx,(int)ty,TILE_WIDTH,TILE_HEIGHT);


		// System.out.println("Detecting collision. .");

		// if (r.intersects(r2)) {

		// System.out.println(tx/TILE_WIDTH + ":" + ty/TILE_HEIGHT);
		// System.out.println((int)tx/TILE_WIDTH + ":" + (int)ty/TILE_HEIGHT);

		//checks if tile is passable
		return handler.getGameMap().getTile((int)tx/TILE_WIDTH,(int)ty/TILE_HEIGHT).isSolid();
		// }

		// return false;
	}
}