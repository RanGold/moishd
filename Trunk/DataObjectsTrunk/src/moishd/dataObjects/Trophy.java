package moishd.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
    private Integer points;
    
    public Trophy(String name, Integer points) {
    	this.name = name;
    	this.points = points;
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}