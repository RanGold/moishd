package moishd.server.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
//32.063856,34.77999
@PersistenceCapable
public class ConstantLocation extends CommonJDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -229245300527618813L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key constLocId;

    @Persistent
    private double longitude;
    
    @Persistent
    private double latitude;
    
    @Persistent
    private double name;

    @Persistent
    private double trophyName;
	
	public ConstantLocation(double longitude, double latitude, double name,
			double trophyName) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
		this.trophyName = trophyName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getName() {
		return name;
	}

	public void setName(double name) {
		this.name = name;
	}

	public double getTrophyName() {
		return trophyName;
	}

	public void setTrophyName(double trophyName) {
		this.trophyName = trophyName;
	}

	public Key getConstLocId() {
		return constLocId;
	}

	public boolean isInitialized() {
		// Longitude measurements range from 0� to (+/�)180�
		// Latitude measurements range from 0� to (+/�)90�
		return ((this.getLatitude() >= -90.0) && (this.getLatitude() <= 90.0) && 
				(this.getLongitude() >= -180.0) && (this.getLongitude() <= 180.0));
	}
}