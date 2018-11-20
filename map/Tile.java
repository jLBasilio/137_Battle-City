import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile implements Constants{

  protected final int x,y;
  protected int id;

	public Tile(int x, int y, int id){
    this.x=x;
    this.y=y;
    this.id=id;
	}

	public void update(){

	}

	public void render(Graphics g){
		switch(id){
			case 0:
				g.drawImage(Assets.road, x, y, TILE_WIDTH, TILE_HEIGHT, null);
				break;
			case 1:
        g.drawImage(Assets.brick, x, y, TILE_WIDTH, TILE_HEIGHT, null);
				break;
			case 2:
        g.drawImage(Assets.grass, x, y, TILE_WIDTH, TILE_HEIGHT, null);
				break;
			case 3:
        g.drawImage(Assets.water, x, y, TILE_WIDTH, TILE_HEIGHT, null);
				break;
			case 4:
        g.drawImage(Assets.steel, x, y, TILE_WIDTH, TILE_HEIGHT, null);
				break;
		}
	}
  public void setID(int id){
    this.id = id
  }

  public Rectangle getBounds(){
    return new Rectangle(x,y,TILE_WIDTH,TILE_HEIGHT);
  }
}