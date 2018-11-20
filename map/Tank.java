public abstract class Tank extends Entity{
	
	protected int moveSpeed, health, atkDmg, powerUp;

	public Tank(Handler handler,int x, int y, int width, int height){
		super(handler,x,y,width,height);

		moveSpeed = 3;
		health = 1;
		atkDmg = 1;
		powerUp = 0;
	}

	public int getMoveSpeed(){
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed){
		this.moveSpeed = moveSpeed;
	}

	public int getHealth(){
		return health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public int getAtkDmg(){
		return atkDmg;
	}

	public void setAtkDmg(int atkDmg){
		this.atkDmg = atkDmg;
	}

	public int getPowerUp(){
		return powerUp;
	}

	public void setPowerUp(){
		this.powerUp = powerUp;
	}
}