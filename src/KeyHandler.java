package com.main.app;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	protected int dir = 0;
	protected boolean pressed, firing;
  private long currentTime;
  private long shootingTime = 0;
  public static final long BULLET_SPAWN_DELAY = 400000000;

	int x, y, moveSpeed;
  String playerName;
	BattleCity bcGame;

	public KeyHandler(BattleCity bcGame) {
		this.bcGame = bcGame;
	}

	public void keyPressed(KeyEvent ke){
    currentTime = System.nanoTime();
    playerName = bcGame.name;
		x = bcGame.players.get(playerName).getX();
		y = bcGame.players.get(playerName).getY();
		moveSpeed = bcGame.players.get(playerName).getSpeed();
		switch(ke.getKeyCode()){

			case KeyEvent.VK_UP:
        System.out.println("Moved up");
				dir=0;
        if((y-moveSpeed) >= 0) {
          if(!bcGame.collision(x, y, dir)){
            y -= moveSpeed;
          	bcGame.sendUpdates("PLAYER " + playerName + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_RIGHT:
        System.out.println("Moved right");
				dir=1;
        if((x+moveSpeed) <= 870) {
          if(!bcGame.collision(x, y, dir)){
            x += moveSpeed;
          	bcGame.sendUpdates("PLAYER " + playerName + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_DOWN:
        System.out.println("Moved down");
				dir=2;
        if((y+moveSpeed) <= 570){
          if(!bcGame.collision(x, y, dir)){
            y += moveSpeed;
          	bcGame.sendUpdates("PLAYER " + playerName + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_LEFT:
        System.out.println("Moved left");
				dir=3;
        if((x-moveSpeed) >= 0){
          if(!bcGame.collision(x, y, dir)){
            x -= moveSpeed;
          	bcGame.sendUpdates("PLAYER " + playerName + " " + x + " " + y + " " + dir);
          }
          else{ System.out.println("Collision detected @ right!"); }
        }
				break;

			case KeyEvent.VK_SPACE:
        if(currentTime - shootingTime > BULLET_SPAWN_DELAY) {
          System.out.println("BULLET PRESS VALID");
          shootingTime = System.nanoTime();
          firing = true;
          bcGame.sendBullet("BULLET " + playerName + " " + x + " " + y + " " + dir);
          System.out.println(playerName + " fired");  
        } else {
          System.out.println("COOLING DOWN");
        }
  			break;

      case KeyEvent.VK_ENTER:
        bcGame.getMapFrame().getTextArea().requestFocus();
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