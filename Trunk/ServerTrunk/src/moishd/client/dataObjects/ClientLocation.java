package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388703558398355826L;

	private double xCoordinate;

	private double yCoordinate;

	public ClientLocation() {
		super();
	}

	public ClientLocation(double xCoordinate, double yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
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
}
