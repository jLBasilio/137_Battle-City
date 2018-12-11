package com.main.app;

public class Coordinates{
  private int x,y;
  private boolean state;

  public Coordinates(int x, int y, boolean state){
    this.x = x;
    this.y = y;
    this.state = state;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public boolean compare(int rx, int ry){
    if(this.x == rx && this.y == ry)
      return true;

    return false;
  }

  public void setOpen(){
    this.state = true;
  }

  public void setClose(){
    this.state = false;
  }

  public boolean getState(){
    return state;
  }
}