import java.awt.Graphics;

public abstract class Entity{
	protected float x,y;
	protected int width, height;

	public Entity(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public abstract void update();


	public abstract void render(Graphics g);

	public abstract void render(Graphics g,int tx,int ty,int dir);
}