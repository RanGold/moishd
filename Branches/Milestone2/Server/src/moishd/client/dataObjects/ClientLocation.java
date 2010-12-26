package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388703558398355826L;

	private double longitude;

	private double latitude;

	public ClientLocation() {
		super();
	}

	public ClientLocation(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
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
}
