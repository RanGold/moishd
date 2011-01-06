package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.client.dataObjects.TrophiesEnum;

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
	
	@Persistent
	private int isAlive;
	
	@Persistent
	private List<String> friendsFacebookIds;
	
	@Persistent
	private List<String> gameTypesPlayed;
	
	@Persistent(dependent = "true")
	private Location location;

	@Persistent
	private List<TrophiesEnum> trophies;

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
		this.isAlive = 0;
		this.friendsFacebookIds = new LinkedList<String>();
		this.gameTypesPlayed = new LinkedList<String>();
		this.dateRegistered = new Date();
		this.trophies = new LinkedList<TrophiesEnum>();
		this.stats = new UserGameStatistics();
		this.location = new Location(200, 200);
	}

	public ClientMoishdUser toClientMoishdUser() {
		return (new ClientMoishdUser(this.getUserNick(), this.getPictureLink(),
				this.getDateRegistered(), this.getUserGoogleIdentifier(),
				"", this.getFacebookID(), this.getLocation().toClientLocaion(),
				this.getTrophies(), 
				this.getStats().toClientUserGameStatistics()));
	}

	public static List<ClientMoishdUser> copyToClientMoishdUserList(
			List<MoishdUser> muList) {
		LinkedList<ClientMoishdUser> cmuList = new LinkedList<ClientMoishdUser>();
		for (MoishdUser mu : muList) {
			cmuList.add(mu.toClientMoishdUser());
		}

		return cmuList;
	}

	public void InitUser() {
		this.setRegisterID("NULL");
		this.setRegistered(false);
		this.setBusy(false);
		this.setIsAlive(2);
		this.getLocation().setLatitude(200);
		this.getLocation().setLongitude(200);
		this.getFriendsFacebookIds().clear();
		this.SaveChanges();
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

	public void setFriendsFacebookIds(List<String> friendsFacebookIds) {
		this.friendsFacebookIds = friendsFacebookIds;
	}

	public List<String> getFriendsFacebookIds() {
		return friendsFacebookIds;
	}

	public void setIsAlive(int isAlive) {
		this.isAlive = isAlive;
	}

	public int getIsAlive() {
		return isAlive;
	}

	public void setTrophies(List<TrophiesEnum> trophies) {
		this.trophies = trophies;
	}

	public List<TrophiesEnum> getTrophies() {
		return trophies;
	}

	public void setGameTypesPlayed(List<String> gameTypesPlayed) {
		this.gameTypesPlayed = gameTypesPlayed;
	}

	public List<String> getGameTypesPlayed() {
		return gameTypesPlayed;
	}
}