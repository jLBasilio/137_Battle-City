package com.main.app;

import java.awt.Graphics;

public abstract class State{
	private Handler handler;

	public State(Handler handler){
		this.handler = handler;
	}

	public abstract void update();

	public abstract void render(Graphics g);
}