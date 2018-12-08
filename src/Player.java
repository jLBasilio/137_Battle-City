package com.main.app;


import java.net.InetAddress;

public class Player implements Constants{
  private String name;
  private int x, y, dir, port, moveSpeed;
  private InetAddress address;

  public Player(String name){
    this.name = name;
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

  public void setAddress(InetAddress address){
    this.address = address;
  }

  public void setPort(int port){
    this.port = port;
  }

  public void setSpeed(int speed) {
    this.moveSpeed = speed;
  }

  public int getSpeed() {
    return this.moveSpeed;
  }

  public void moveRight() {
    if((this.x + this.moveSpeed) <= 870) {
      this.x += this.moveSpeed;
    }
  }

  public void moveLeft() {
    if((this.x - this.moveSpeed) >= 0) {
      this.x -= this.moveSpeed;
    }
  }

  public void moveUp() {
    if((this.y - this.moveSpeed) >= 0) {
      this.y -= this.moveSpeed;
    }
  }

  public void moveDown() {
    if((this.y + this.moveSpeed) <= 570) {
      this.y += this.moveSpeed;
    }
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
}