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
    private double longitude;
    
    @Persistent
    private double latitude;
    
    @Persistent(mappedBy = "location")
    private MoishdUser moishdUser;

	public Location(double longitude, double latitude) {
		super();
		this.setLongitude(longitude);
		this.setLatitude(latitude);
	}

	public ClientLocation toClientLocaion() {
		return (new ClientLocation(this.getLongitude(),this.getLatitude()));
	}

	public Key getLocationId() {
		return locationId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
	
	public void setMoishdUser(MoishdUser moishdUser) {
		this.moishdUser = moishdUser;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public boolean isInitialized() {
		// Longitude measurements range from 0° to (+/–)180°
		// Latitude measurements range from 0° to (+/–)90°
		return ((this.getLatitude() >= -90.0) && (this.getLatitude() <= 90.0) && 
				(this.getLongitude() >= -180.0) && (this.getLongitude() <= 180.0));
	}
}