package dark2phoenix.mods.rescuechest.core.types;


public class ChestLocation {

	private int locationx;
	
	private int locationy;
	
	private int locationz;
	
	private int dimension;
	
  private int playerId;	

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getLocationx() {
		return locationx;
	}

	public void setLocationx(int locationx) {
		this.locationx = locationx;
	}

	public int getLocationy() {
		return locationy;
	}

	public void setLocationy(int locationy) {
		this.locationy = locationy;
	}

	public int getLocationz() {
		return locationz;
	}

	public void setLocationz(int locationz) {
		this.locationz = locationz;
	}

	public int getDimension() {
		return dimension;
	}

	public void setWorld(int newDimension) {
		this.dimension = newDimension;
	}

/**
 * Creates a Chest Location Object used to determine who this chest is bound to in which dimension
 * @param dimension world dimension world.getWorldInfo.getDimension()
 * @param locationx x coordinate where base block is located
 * @param locationy y coordinate where base block is located
 * @param locationz z coordinate where base block is located
 * @param playerId  player id player.getEntityId
 */
	public ChestLocation( int dimension, int locationx, int locationy, int locationz, int playerId) {
		super();
		this.locationx = locationx;
		this.locationy = locationy;
		this.locationz = locationz;
		this.dimension = dimension;
		this.playerId = playerId;
	}

	@Override
	public boolean equals(Object object) {
		if ( !( object instanceof ChestLocation ) ) {
			return false;
		}
		
		ChestLocation compareLocation = (ChestLocation) object;
		
		if ( (compareLocation.getDimension() == this.getDimension() ) && (compareLocation.getLocationx() == this.getLocationx() ) && ( compareLocation.getLocationy() == this.getLocationy() ) && ( compareLocation.getLocationz() == this.getLocationz() ) && (compareLocation.getPlayerId() == this.getPlayerId() ) ) {
		  return true;	
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ("Dimension: " + this.getDimension() 
				+ " X: " + this.getLocationx()
				+ " Y: " + this.getLocationy()
				+ " Z: " + this.getLocationz()
				+ " PlayerID: " + this.getPlayerId()
		);
	}
	
	
	
	
}
