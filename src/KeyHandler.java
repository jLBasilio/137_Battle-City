package com.main.app;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	protected int dir;
	protected boolean pressed, firing;

	int x, y, moveSpeed;

	BattleCity bcGame;

	public KeyHandler(BattleCity bcGame) {
		this.bcGame = bcGame;
	}

	public void keyPressed(KeyEvent ke){
		x = bcGame.players.get(bcGame.name).getX();
		y = bcGame.players.get(bcGame.name).getY();
		moveSpeed = bcGame.players.get(bcGame.name).getSpeed();

		switch(ke.getKeyCode()){

			case KeyEvent.VK_UP:
        System.out.println("Moved up");
				dir=0;
        if((y-moveSpeed) >= 0) {
          if(!bcGame.collision(x, y)){
            y -= moveSpeed;
          	bcGame.sendUpdates("PLAYER " + bcGame.name + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_RIGHT:
        System.out.println("Moved right");
				dir=1;
        if((x+moveSpeed) <= 870) {
          if(!bcGame.collision(x, y)){
            x += moveSpeed;
          	bcGame.sendUpdates("PLAYER " + bcGame.name + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_DOWN:
        System.out.println("Moved down");
				dir=2;
        if((y+moveSpeed) <= 570){
          if(!bcGame.collision(x, y)){
            y += moveSpeed;
          	bcGame.sendUpdates("PLAYER " + bcGame.name + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_LEFT:
        System.out.println("Moved left");
				dir=3;
        if((x-moveSpeed) >= 0){
          if(!bcGame.collision(x, y)){
            x -= moveSpeed;
          	bcGame.sendUpdates("PLAYER " + bcGame.name + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }

				break;
			case KeyEvent.VK_SPACE:
        System.out.println("Move: fired");
				firing = true;
				break;
		}
	}

	public void keyTyped(KeyEvent ke) {
		
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