

public class ActionHandler {

  Player player;
  Gameserver gameserver;

  public ActionHandler(Player player, Gameserver gameserver) { 

    this.player = player;
    this.gameserver = gameserver;

  }

  public move(String name, int dir) {

    switch(dir) {

      case 0:             
        player.setY();
        break;
      case 1:
        player.setY();

    }

  }


  public fireBullet() {


  }


  public sendToserver() {



  }


}