package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388703558398355826L;

	private int xCoordinate;

	private int yCoordinate;

	public ClientLocation() {
		super();
	}

	public ClientLocation(int xCoordinate, int yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
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
}
