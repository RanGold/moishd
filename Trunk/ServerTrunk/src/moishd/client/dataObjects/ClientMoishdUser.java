package moishd.client.dataObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClientMoishdUser implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4540541990307042204L;

	private String userNick;

    private String pictureLink;

    private Date dateRegistered;
    
	private String userGoogleIdentifier;
    
	private String registerID;
	
	private String facebookID;
    
    private ClientLocation location;
    
    private List<ClientTrophy> trophies;
    
    private ClientUserGameStatistics stats;
    
	public ClientMoishdUser() {
		super();
		this.userNick = "";
		this.pictureLink = "";
		this.dateRegistered = null;
		this.userGoogleIdentifier = "";
		this.registerID = "";
		this.facebookID = "";
		this.location = null;
		this.trophies = null;
		this.stats = null;
	}

	public ClientMoishdUser(String userNick, String pictureLink,
			Date dateRegistered, String userGoogleIdentifier,
			String registerID, String facebookID, ClientLocation location,
			List<ClientTrophy> trophies, ClientUserGameStatistics stats) {
		super();
		this.userNick = userNick;
		this.pictureLink = pictureLink;
		this.dateRegistered = dateRegistered;
		this.userGoogleIdentifier = userGoogleIdentifier;
		this.registerID = registerID;
		this.facebookID = facebookID;
		this.location = location;
		this.trophies = trophies;
		this.stats = stats;
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

	public String getUserGoogleIdentifier() {
		return userGoogleIdentifier;
	}

	public void setUserGoogleIdentifier(String userGoogleIdentifier) {
		this.userGoogleIdentifier = userGoogleIdentifier;
	}

	public String getRegisterID() {
		return registerID;
	}

	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}
	
	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	public ClientLocation getLocation() {
		return location;
	}

	public void setLocation(ClientLocation location) {
		this.location = location;
	}

	public List<ClientTrophy> getTrophies() {
		return trophies;
	}

	public void setTrophies(List<ClientTrophy> trophies) {
		this.trophies = trophies;
	}

	public ClientUserGameStatistics getStats() {
		return stats;
	}

	public void setStats(ClientUserGameStatistics stats) {
		this.stats = stats;
	}

}
