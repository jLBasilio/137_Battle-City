import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Tank implements Constants{
	private int dir, fm = 0, prevDir = 0;

	public Player(Handler handler,float x,float y,int width, int height){
		super(handler,x,y,width,height);

		bounds.x = (int)x;
		bounds.y = (int)y;
		bounds.width = width;
		bounds.height = height;
	}

	public void update(){
    float dx,dy;
		if (handler.getGame().getKeyHandler().isKeyPressed()) {
			switch(handler.getGame().getKeyHandler().getDirection()){
				case 0: // move up
					dir=0;
					if(dir == prevDir){
						if((y-moveSpeed) >= 0){
              dx=x;
              dy=y;
							// if(!collision(x,y) && !collision(x+TILE_WIDTH,y) && !collision(x+TILE_WIDTH/2,y)){ //checks upper mid, upper right and upper left corner for collision
							if(!collision(dx,dy) && !collision(dx+TILE_WIDTH,dy)){ //checks upper right and upper left corner for collision
              // if(!collision(x,y-TILE_HEIGHT+1)){ //checks upper right and upper left corner for collision
								y-=moveSpeed;
								// fm = 0;
							}
							else{
								// y+=1; //move back if collision detected;
								// fm+=1;
								// if(fm > 1){
								// 	y-=moveSpeed;
								// 	fm-=2;
								// }
								// else{
								// 	y+=1;
								// }
								System.out.println("Collision detected @ top!");
							}
						}
					}
					else{
						prevDir = dir;
					}
					break;
				case 1: // move right
					dir=1;
					if(dir == prevDir){
						if((x+moveSpeed) <= 870){
              dx=x;
              dy=y;
							// if(!collision(x+TILE_WIDTH,y) && !collision(x+TILE_WIDTH,y+TILE_HEIGHT) && !collision(x+TILE_WIDTH,y+TILE_HEIGHT/2)){ //checks right, upper right and lower right corner for collision
							if(!collision(dx+TILE_WIDTH,dy) && !collision(dx+TILE_WIDTH,dy+TILE_HEIGHT)){ //checks upper left and lower left corner for collision
              // if(!collision(x+TILE_WIDTH-1,y)){ //checks upper left and lower left corner for collision
								x+=moveSpeed;
								// fm = 0;
							}
							else{
								// x-=1; //move back if collision detected;
								// fm+=1;
								// if(fm > 1){
								// 	x+=moveSpeed;
								// 	fm-=2;
								// }
								// else{
								// 	x-=1;
								// }
								System.out.println("Collision detected @ right!");
							}
						}
					}
					else{
						prevDir = dir;
					}
					break;
				case 2: // move down
					dir=2;
					if(dir == prevDir){
						if((y+moveSpeed) <= 570){
              dx=x;
              dy=y;
							// if(!collision(x,y+TILE_HEIGHT) && !collision(x+TILE_WIDTH,y+TILE_HEIGHT) && !collision(x+TILE_WIDTH/2,y+TILE_HEIGHT)){ //checks lower mid, lower right and lower left corner for collision
							if(!collision(dx,dy+TILE_HEIGHT) && !collision(dx+TILE_WIDTH,dy+TILE_HEIGHT)){ //checks lower right and lower left corner for collision
              // if(!collision(x,y+TILE_HEIGHT-1)){ //checks lower right and lower left corner for collision
								y+=moveSpeed;
								// fm = 0;
							}
							else{
								// y-=1; //move back if collision detected;
								// fm+=1;
								// if(fm > 1){
								// 	y+=moveSpeed;
								// 	fm-=2;
								// }
								// else{
								// 	y-=1;
								// }
								System.out.println("Collision detected @ bottom!");
							}
						}
					}
					else{
						prevDir = dir; 
					}
					break;
				case 3: // move left
					dir=3;
					if(dir == prevDir){
						if((x-moveSpeed) >= 0){
              dx=x;
              dy=y;
							// if(!collision(x,y) && !collision(x,y+TILE_HEIGHT) && !collision(x,y+TILE_HEIGHT/2)){ //checks left mid, upper left and lower left corner for collision
              if(!collision(dx,dy) && !collision(dx,dy+TILE_HEIGHT)){ //checks upper left and lower left corner for collision
							// if(!collision(x-TILE_WIDTH+1,y)){ //checks upper right and lower right corner for collision
								x-=moveSpeed;
								// fm = 0;
							}
							else{
								// x+=1; //move back if collision detected;
								// fm+=1;
								// if(fm > 1){
								// 	x-=moveSpeed;
								// 	fm-=2;
								// }
								// else{
								// 	x+=1;
								// }
								System.out.println("Collision detected @ left!");
							}
						}
					}
					else{
						prevDir = dir;
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


		// // System.out.println("Detecting collision. .");

		// if (r.intersects(r2)) {

		// System.out.println(tx/TILE_WIDTH + ":" + ty/TILE_HEIGHT);
		System.out.println((int)tx/TILE_WIDTH + ":" + (int)ty/TILE_HEIGHT);

		//checks if tile is passable
		return handler.getGameMap().getTile((int)tx/TILE_WIDTH,(int)ty/TILE_HEIGHT).isSolid();
		// }

		// return false;
	}
}