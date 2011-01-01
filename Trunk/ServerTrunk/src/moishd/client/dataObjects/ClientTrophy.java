package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientTrophy implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3057237340017072466L;

	private String name;
        
	public ClientTrophy() {
		super();
	}

	public ClientTrophy(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
