public abstract class PowerUp extends Entity{
	
	protected boolean shield, atkBonus, speedBonus;

	public PowerUp(Handler handler,float x, float y, int width, int height){
		super(handler,x,y,width,height);
	}

	public boolean getShield(){
		return shield;
	}

	public void setShield(boolean shield){
		this.shield = shield;
	}

	public boolean getAtkBonus(){
		return atkBonus;
	}

	public void setAtkBonus(boolean atkBonus){
		this.atkBonus = atkBonus;
	}

	public boolean getSpeedBonus(){
		return speedBonus;
	}

	public void setSpeedBonus(boolean speedBonus){
		this.speedBonus = speedBonus;
	}
}