package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientMoishdUser;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MoishdUser extends CommonJDO implements Serializable {
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

	@Persistent
	private String userGoogleIdentifier;

	@Persistent
	private String registerID;
	
	@Persistent
	private boolean isRegistered;
	
	@Persistent
	private String facebookID;
	
	@Persistent
	private String MACAddress;
	
	@Persistent
	private boolean isBusy;
	
	@Persistent(dependent = "true")
	private Location location;

	@Persistent
	private Set<Key> trophies;

	@Persistent(dependent = "true")
	private UserGameStatistics stats;

	public MoishdUser(String userNick, String pictureLink,
			String userGoogleIdentifier, String registerID, String facebookID, String MACAddress) {
		super();
		this.userNick = userNick;
		this.pictureLink = pictureLink;
		this.userGoogleIdentifier = userGoogleIdentifier;
		this.registerID = registerID;
		this.isRegistered = false;
		this.facebookID = facebookID;
		this.setMACAddress(MACAddress);
		this.isBusy = false;
		this.dateRegistered = new Date();
		this.trophies = new HashSet<Key>();
		this.stats = new UserGameStatistics();
		this.location = new Location(-1, -1);
	}

	public ClientMoishdUser toClientMoishdUser() {
		return (new ClientMoishdUser(this.getUserNick(), this.getPictureLink(),
				this.getDateRegistered(), this.getUserGoogleIdentifier(),
				this.getRegisterID(), this.getFacebookID(), this.getMACAddress(), 
				this.getLocation().toClientLocaion(),
				Trophy.copyToClientTrophyList(this.getTrophies()), this
						.getStats().toClientUserGameStatistics()));
	}

	public static List<ClientMoishdUser> copyToClientMoishdUserList(
			List<MoishdUser> muList) {
		LinkedList<ClientMoishdUser> cmuList = new LinkedList<ClientMoishdUser>();
		for (MoishdUser mu : muList) {
			cmuList.add(mu.toClientMoishdUser());
		}

		return cmuList;
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

	public Set<Key> getTrophyKeys() {
		return trophies;
	}

	//@SuppressWarnings("unchecked")
	public List<Trophy> getTrophies() {
		// TODO: fix this
		List<Trophy> tList = new LinkedList<Trophy>();
		/*if (this.getTrophyKeys().size() > 0) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			tList = (List<Trophy>) pm.newQuery(Trophy.class,
					":keys.contains(trophyId)").execute(this.getTrophyKeys());
		}*/
		return tList;
	}

	public void addTrophy(Trophy trophy) {
		getTrophyKeys().add(trophy.getTrophyId());
	}

	public void setStats(UserGameStatistics stats) {
		this.stats = stats;
	}

	public UserGameStatistics getStats() {
		return stats;
	}

	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}

	public String getRegisterID() {
		return registerID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	public String getFacebookID() {
		return facebookID;
	}
	public String getUserGoogleIdentifier() {
		return userGoogleIdentifier;
	}

	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}

	public String getMACAddress() {
		return MACAddress;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}
}