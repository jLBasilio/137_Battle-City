package com.main.app;


import java.net.InetAddress;
// import java.awt.Graphics;
// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.List;

public class Player implements Constants{
  private String name;
  private int x,y,dir,port;
  private InetAddress address;

  // private List<Bullet> bullets = new ArrayList<>();

  // private long bulletSpawnDelay = 250000000;
  // private long currentTime;
  // private long shootingTime = 0;

  public Player(String name, InetAddress address, int port){
    this.name = name;
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.address = address;
    this.port = port;
  }

  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  public void setDir(int dir){
    this.dir = dir;
  }

  public String getName(){
    return name;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public int getDir(){
    return dir;
  }

  public InetAddress getAddress(){
    return address;
  }

  public int getPort(){
    return port;
  }

  public String getPlayerData(){
    String data = "PLAYER ";
    data += name + " ";
    data += x + " ";
    data += y + " ";
    data += dir + " ";
    return data;
  }

  // public void update(){
  //   currentTime = System.nanoTime();

  //   if(handler.getGame().getKeyHandler().isFiring() && currentTime - shootingTime > bulletSpawnDelay){
  //     shootingTime = System.nanoTime();
  //     switch(dir){
  //       case 0: bullets.add(new Bullet(handler ,x-5+TILE_WIDTH/2 ,y ,dir));break;
  //       case 1: bullets.add(new Bullet(handler ,x+20 ,y-5+TILE_HEIGHT/2 ,dir));break;
  //       case 2: bullets.add(new Bullet(handler ,x-5+TILE_WIDTH/2 ,y+20 ,dir));break;
  //       case 3: bullets.add(new Bullet(handler ,x ,y-5+TILE_HEIGHT/2 ,dir));break;
  //     }
  //     System.out.println("Bullet fired!");
  //   }
  // }

  // public void render(Graphics g){
  //  //render sprite base on the direction the tank is facing/moving
  //  switch(dir){
  //    case 0: g.drawImage(Assets.tankU ,x ,y ,width ,height ,null);break;
  //    case 1: g.drawImage(Assets.tankR ,x ,y ,width ,height ,null);break;
  //    case 2: g.drawImage(Assets.tankD ,x ,y ,width ,height ,null);break;
  //    case 3: g.drawImage(Assets.tankL ,x ,y ,width ,height ,null);break;
  //  }
  // }

  // public List<Bullet> getBullets(){
  //   return bullets;
  // }
}