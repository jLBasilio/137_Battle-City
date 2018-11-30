package com.main.app;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler extends KeyAdapter{
	protected int dir;
	protected boolean pressed,firing;

	public KeyHandler(){

	}

	public void keyPressed(KeyEvent ke){
		switch(ke.getKeyCode()){
			case KeyEvent.VK_UP:
				pressed = true;
				dir=0;
				break;
			case KeyEvent.VK_RIGHT:
				pressed = true;
				dir=1;
				break;
			case KeyEvent.VK_DOWN:
				pressed = true;
				dir=2;
				break;
			case KeyEvent.VK_LEFT:
				pressed = true;
				dir=3;
				break;
			case KeyEvent.VK_SPACE:
				firing = true;
				break;
		}
	}

	public void keyReleased(KeyEvent ke){
		pressed = false;
		firing = false;
	}

	public int getDirection(){
		return dir;
	}

	public boolean isKeyPressed(){
		return pressed;
	}

	public boolean isFiring(){
		return firing;
	}
}