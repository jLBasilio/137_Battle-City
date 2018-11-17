public class Handler{
	private BattleCity game;
	private GameMap gameMap;

	public Handler(BattleCity game){
		this.game = game;
	}

	public BattleCity getGame(){
		return game;
	}

	public void setGame(BattleCity game){
		this.game = game;
	}

	public GameMap getGameMap(){
		return gameMap;
	}

	public void setGameMap(GameMap gameMap){
		this.gameMap = gameMap;
	}
}