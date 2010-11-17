package moishd.server;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Location {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key locationId;

    @Persistent
    private Integer xCoordinate;
    
    @Persistent
    private Integer yCoordinate;
    
	public Location(Key locationId, Integer xCoordinate, Integer yCoordinate) {
		this.locationId = locationId;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Key getLocationId() {
		return locationId;
	}
}