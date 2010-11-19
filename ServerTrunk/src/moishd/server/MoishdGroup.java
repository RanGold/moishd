package moishd.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MoishdGroup {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key groupId;

    @Persistent
    private String name;
    
	@Persistent
    private GameStatistics stats; 

	@Persistent
    private Date dateCreated; 
	
	public MoishdGroup() {
		stats = new GameStatistics();
	}
	
	public MoishdGroup(String name, Date dateCreated) {
		this.name = name;
		this.dateCreated = dateCreated;
		stats = new GameStatistics();
	}
	
	public Key getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setStats(GameStatistics stats) {
		this.stats = stats;
	}
	
	public GameStatistics getStats() {
		return stats;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}