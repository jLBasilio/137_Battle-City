package com.main.app;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map;
import java.awt.Rectangle;

public class GameState implements Constants{
  private List<Coordinates> spawnAreas;
  private Map<String, Player> players = new HashMap<String, Player>();
  private Map<String, Player> playerList = new HashMap<String, Player>();

  private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  private GameMap gameMap;
  
  public GameState(){
    gameMap = new GameMap("map/city1.map");

    spawnAreas = gameMap.getSpawnAreas();
  }

  public Coordinates getSpawnLocation(){
   boolean state = false;

   Coordinates spawnLoc = new Coordinates(0,0,true);

   while(!state){
     Random r = new Random();
     int x = r.nextInt(30)*30;
     int y = r.nextInt(20)*30;

     System.out.println("random coodinate => " + x + ":" + y);

     for(Coordinates spawnArea: spawnAreas){
       if(spawnArea.compare(x,y)){
         spawnLoc = spawnArea;
         state = true;
         spawnArea.setClose();

         System.out.println("final spawn location => " + x + ":" + y);
       }
     }
   }

   return spawnLoc;
  }

  public void update(String name, Player player){
    players.put(name, player);
  }

  public String getGameData(){
    String gameData = "";

    for(Iterator i = players.keySet().iterator(); i.hasNext(); ){
      String name = (String) i.next();
      Player player = (Player) players.get(name);
      gameData += player.getPlayerData() + ":";
    }

    return gameData;
  }

  public void addPlayer(String name, Player player) {
    players.put(name, player);
    printAllPlayers();

  }

  public Map<String, Player> getPlayers(){
    return this.players;
  }

  public Map<String, Player> getPlayerList(){
    return this.playerList;
  }


  public ArrayList<Bullet> getBullets() {
    return this.bulletList;
  }

  public GameMap getGameMap() {
    return this.gameMap;
  }

  private void printAllPlayers() {

    System.out.println("=========== CURRENT PLAYERS IN SERVER ============");

    for (Map.Entry<String, Player> entry : players.entrySet()) {
        String key = entry.getKey();
        Player value = entry.getValue();
        
        System.out.println(key);

    }
  }



  public boolean collision(int x, int y, int pdir){
    // System.out.println("Detecting collision.");

    Rectangle r = new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
    List<Tile> tiles = gameMap.getTiles();


    for(Tile tile : tiles) {
      Rectangle r2 = tile.getBounds();
      if (r.intersects(r2)) {
        // System.out.println(x/TILE_WIDTH + ":" + y/TILE_HEIGHT);
        // System.out.println(pdir);

        if(pdir == 0){
          if(r.y > r2.y)
            return true;
        }
        else if(pdir == 1){
          if(r.x < r2.x)
            return true;
        }
        else if(pdir == 2){
          if(r.y < r2.y)
            return true;
        }
        else if(pdir == 3){
          if(r.x > r2.x)
            return true;
        }
      }
    }

    for (Map.Entry<String, Player> entry : players.entrySet()) {
      String pname = entry.getKey();
      Player player = entry.getValue();
      
      int px = player.getX();
      int py = player.getY();;
      Rectangle playerRec = new Rectangle(px, py, TANK_WIDTH, TANK_HEIGHT);

      if (r.intersects(playerRec)) {
        // System.out.println(x/TILE_WIDTH + ":" + y/TILE_HEIGHT);
        System.out.println(pdir);
        if(pdir == 0){
          if(r.y > playerRec.y)
            return true;
        }
        else if(pdir == 1){
          if(r.x < playerRec.x)
            return true;
        }
        else if(pdir == 2){
          if(r.y < playerRec.y)
            return true;
        }
        else if(pdir == 3){
          if(r.x > playerRec.x)
            return true;
        }
      }
    }

    return false;
  }
}