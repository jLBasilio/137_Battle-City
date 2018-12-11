package com.main.app;

public class StateManager{
	private static State state;

	public StateManager(State state){
		this.state = state;
	}

	public void setState(State state){
		this.state = state;
	}

	public State getState(){
		return this.state;
	}
}