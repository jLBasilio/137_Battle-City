import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends Tank implements Constants{
	private int dir, prevDir = 0;
  private List<Bullet> bullets = new ArrayList<>();

	public Player(Handler handler,int x,int y,int width, int height){
		super(handler,x,y,width,height);
	}

  public void update(){
    double timerPerFire = 2000000000;
    double delta = 0;
    long now;
    long lastTime = System.nanoTime();

    if (handler.getGame().getKeyHandler().isKeyPressed()) {
      switch(handler.getGame().getKeyHandler().getDirection()){
        case 0: // move up
          dir=0;
          if(dir == prevDir){
            if((y-moveSpeed) >= 0){
              if(!collision()){
                y-=moveSpeed;
              }
              else{
                System.out.println("Collision detected @ top!");
                // y+=1;
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
              if(!collision()){
                x+=moveSpeed;
              }
              else{
                System.out.println("Collision detected @ right!");
                // x-=1;
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
              if(!collision()){
                y+=moveSpeed;
              }
              else{
                System.out.println("Collision detected @ bottom!");
                // y-=1;
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
              if(!collision()){
                x-=moveSpeed;
              }
              else{
                System.out.println("Collision detected @ left!");
                // x+=1;
              }
            }
          }
          else{
            prevDir = dir;
          }
          break;
      }
    }

    if(handler.getGame().getKeyHandler().isFiring()){
      bullets.add(new Bullet(handler,x,y,dir));
      System.out.println("Bullet fired!");
    }
  }

	public void render(Graphics g){
		//render sprite base on the direction the tank is facing/moving
		switch(dir){
			case 0: g.drawImage(Assets.tankU ,x ,y ,width ,height ,null);break;
			case 1: g.drawImage(Assets.tankR ,x ,y ,width ,height ,null);break;
			case 2: g.drawImage(Assets.tankD ,x ,y ,width ,height ,null);break;
			case 3: g.drawImage(Assets.tankL ,x ,y ,width ,height ,null);break;
		}
	}

	public boolean collision(){
		System.out.println("Detecting collision.");

		Rectangle r = this.getBounds();

    List<Tile> tiles = handler.getGameMap().getTiles();

    for(Tile tile : tiles){
      Rectangle r2 = tile.getBounds();
      if (r.intersects(r2)) {
        System.out.println(x/TILE_WIDTH + ":" + y/TILE_HEIGHT);
        System.out.println(dir);

        if(dir == 0){
          if(r.y > r2.y)
            return true;
          else
            return false;
        }
        else if(dir == 1){
          if(r.x < r2.x)
            return true;
          else
            return false;
        }
        else if(dir == 2){
          if(r.y < r2.y)
            return true;
          else
            return false;
        }
        else if(dir == 3){
          if(r.x > r2.x)
            return true;
          else
            return false;
        }
      }
    }

    return false;
	}

  public List<Bullet> getBullets(){
    return bullets;
  }

  public void removeBullet(int i){
    this.bullets.remove(i);
  }
}