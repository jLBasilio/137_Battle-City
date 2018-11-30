package com.main.app;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity{
	protected Handler handler;
	protected int x,y;
	protected int width, height;
	protected boolean visibility;

	public Entity(Handler handler,int x, int y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void setVisibility(boolean visibility){
		this.visibility = visibility;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public boolean isVisible(){
		return visibility;
	}

	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}

	public abstract void update();

	public abstract void render(Graphics g);
}