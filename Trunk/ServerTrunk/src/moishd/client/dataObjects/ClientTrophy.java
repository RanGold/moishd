package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientTrophy implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3057237340017072466L;

	private String name;
    
    private int points;
    
	public ClientTrophy() {
		super();
	}

	public ClientTrophy(String name, int points) {
		super();
		this.name = name;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
