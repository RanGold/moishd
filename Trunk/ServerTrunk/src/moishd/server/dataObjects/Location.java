package moishd.server.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientLocation;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Location extends CommonJDO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5699415381628138991L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key locationId;

    @Persistent
    private int xCoordinate;
    
    @Persistent
    private int yCoordinate;
    
    @Persistent(mappedBy = "location")
    private MoishdUser moishdUser;
    
	public Location(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public ClientLocation toClientLocaion() {
		return (new ClientLocation(this.getxCoordinate(),this.getyCoordinate()));
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Key getLocationId() {
		return locationId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
}