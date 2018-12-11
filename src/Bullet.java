package com.main.app;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Bullet implements Constants{
  private int x,y,dir;
  private boolean visibility;
  private String sender, playerOwner;

  public Bullet(String playerOwner, int x,int y, int dir){
    this.playerOwner = playerOwner;
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.visibility = true;
  }

  public void setX(int x){
    this.x = x;
  }

  public int getX(){
    return x;
  }

  public void setY(int y){
    this.y = y;
  }

  public int getY(){
    return y;
  }

  public void setDirection(int dir){
    this.dir = dir;
  }

  public int getDirection(){
    return dir;
  }

  public void setVisibility(boolean visibility){
    this.visibility = visibility;
  }

  public boolean isVisible(){
    return visibility;
  }

  public void update(){
    if(visibility){
      switch(dir){
        case 0: // move up
          y-=BULLET_SPEED;
          break;
        case 1: // move right
          x+=BULLET_SPEED;
          break;
        case 2: // move down
          y+=BULLET_SPEED;
          break;
        case 3: // move left
          x-=BULLET_SPEED;
          break;
      }
      // if(collision())
      //   visibility = false;
    }
  }



  public void render(Graphics g){
    //render sprite base on the direction the tank is facing/moving
    g.drawImage(Assets.bullet ,x ,y ,BULLET_WIDTH ,BULLET_HEIGHT ,null);

  }  

  // public boolean collision(){
  //   System.out.println("Detecting bullet collision.");

  //   Rectangle r = new Rectangle(x,y,BULLET_WIDTH,BULLET_HEIGHT);

  //   List<Tile> tiles = handler.getGameMap().getTiles();

  //   if(x < 0 || x >= 900 || y < 0 || y >= 600){
  //     System.out.println("Bullet out of bounds.");
  //     return true;
  //   }

  //   for(Tile tile : tiles){
  //     Rectangle r2 = tile.getBounds();
  //     if (r.intersects(r2)) {
  //       System.out.println("Collision with tile.");
  //       return true;
  //     }
  //   }

  //   return false;
  // }
}