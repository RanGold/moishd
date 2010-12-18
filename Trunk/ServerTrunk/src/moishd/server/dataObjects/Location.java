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
    private double xCoordinate;
    
    @Persistent
    private double yCoordinate;
    
    @Persistent(mappedBy = "location")
    private MoishdUser moishdUser;
    
	public Location(double xCoordinate, double yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public ClientLocation toClientLocaion() {
		return (new ClientLocation(this.getxCoordinate(),this.getyCoordinate()));
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Key getLocationId() {
		return locationId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
}