package com.main.app;

import java.awt.Graphics;

public class TimeSkip extends PowerUp{
	

	public TimeSkip(Handler handler,int x,int y,int width, int height){
		super(handler,x,y,width,height);
	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(Assets.timeSkip ,x ,y ,width ,height ,null);
	}
}