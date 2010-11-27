package moishd.dataObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class MoishdUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8353060520322729907L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key userId;

    @Persistent
    private String userNick;

    @Persistent
    private String pictureLink;

    @Persistent
    private Date dateRegistered;
    
    @Persistent(dependent = "true")
    private Location location;
    
    @Persistent
    private Set<Key> trophies;
    
	@Persistent(dependent = "true")
    private UserGameStatistics stats;
	
	@Persistent
	private String userIdentifier;

	public MoishdUser() {
		this.userIdentifier = "Guest";
		this.location = new Location(0,0);
		this.dateRegistered = new Date();
		this.trophies = new HashSet<Key>();
		this.stats = new UserGameStatistics();
	}
	
	public MoishdUser(String userNick, String pictureLink, 
			Location location, String userIdentifier) {
		this.userNick = userNick;
		this.pictureLink = pictureLink;
		this.location = location;
		this.userIdentifier = userIdentifier;
		this.dateRegistered = new Date();
		this.trophies = new HashSet<Key>();
		this.stats = new UserGameStatistics();
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Key getUserId() {
		return userId;
	}

	public void setTrophies(Set<Key> trophies) {
		this.trophies = trophies;
	}

	public Set<Key> getTrophies() {
		return trophies;
	}

	public void setStats(UserGameStatistics stats) {
		this.stats = stats;
	}

	public UserGameStatistics getStats() {
		return stats;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}
}