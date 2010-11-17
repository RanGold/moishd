package moishd.server;

import com.google.appengine.api.datastore.Key;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class MoishdUser {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key userId;

    @Persistent
    private String userNick;

    @Persistent
    private String pictureLink;

    @Persistent
    private Date dateRegistered;
    
    @Persistent
    private MoishdGroup group;
    
    @Persistent
    private Location location;

	public MoishdUser(String userNick, String pictureLink, Date dateRegistered,
			MoishdGroup group, Location location) {
		super();
		this.userNick = userNick;
		this.pictureLink = pictureLink;
		this.dateRegistered = dateRegistered;
		this.group = group;
		this.location = location;
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

	public MoishdGroup getGroup() {
		return group;
	}

	public void setGroup(MoishdGroup group) {
		this.group = group;
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
}