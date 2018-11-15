public class State{
	private static State currentState = null;
	protected Map map;
	
	public State(Map map){
		this.map = map;
	}

	public static void setState(State state){
		currentState = state;
	}

	public static State getState(){
		return currentState;
	}
}