public class SteelTile extends Tile{
	
	public SteelTile(int id){
		super(Assets.steel, id);
	}

	@Override
	public boolean isPassable(){
		return false;
	}
}