package moishd.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class Location implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5699415381628138991L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key locationId;

    @Persistent
    private Integer xCoordinate;
    
    @Persistent
    private Integer yCoordinate;
    
    @Persistent(mappedBy = "location")
    private MoishdUser moishdUser;
    
	public Location(Integer xCoordinate, Integer yCoordinate) {
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

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
}