package com.main.app;

public class Handler{
	private BattleCity game;
	private GameMap gameMap;
	private GameState gameState;

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

	public GameState getGameState(){
		return gameState;
	}

	public void setGameState(GameState gameState){
		this.gameState = gameState;
	}
}