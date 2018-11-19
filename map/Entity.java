import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity{
	protected Handler handler;
	protected float x,y;
	protected int width, height;
	protected Rectangle bounds;

	public Entity(Handler handler,float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		bounds = new Rectangle((int)x,(int)y,width,height);
	}

	public void setX(float x){
		this.x = x;
	}

	public void setY(float y){
		this.y = y;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public Rectangle getBounds(int dx, int dy){
		return new Rectangle((int)x + dx,(int)y + dy,width,height);
	}

	public abstract void update();

	public abstract void render(Graphics g);
}