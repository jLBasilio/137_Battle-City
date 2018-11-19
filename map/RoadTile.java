public class RoadTile extends Tile{
	
	public RoadTile(int id){
		super(Assets.road, id);
	}

	@Override
	public boolean isSolid(){
		return false;
	}
}