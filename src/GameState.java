package com.main.app;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameState {
  private List<Coordinates> spawnAreas;
  private HashMap<String, Player> players = new HashMap<String, Player>();
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

  public HashMap<String, Player> getPlayers(){
    return this.players;
  }

  private void printAllPlayers() {

    System.out.println("=========== CURRENT PLAYERS IN SERVER ============");

    for (HashMap.Entry<String, Player> entry : players.entrySet()) {
        String key = entry.getKey();
        Player value = entry.getValue();
        
        System.out.println(key);

    }
  }

}