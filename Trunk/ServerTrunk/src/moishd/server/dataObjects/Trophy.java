package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientTrophy;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Trophy implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5193254881695253865L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key trophyId;

    @Persistent
    private String name;
    
	@Persistent
    private int points;
    
    public Trophy(String name, int points) {
    	this.name = name;
    	this.points = points;
    }
    
    public ClientTrophy toClientTrophy() {
    	return (new ClientTrophy(this.getName(), this.getPoints()));
    }
    
    static public List<ClientTrophy> copyToClientTrophyList(List<Trophy> tList) {
		LinkedList<ClientTrophy> ctList = new LinkedList<ClientTrophy>();
		for (Trophy t : tList) {
			ctList.add(t.toClientTrophy());
		}
		
		return ctList;
	}
    
	public Key getTrophyId() {
		return trophyId;
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