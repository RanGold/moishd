package moishd.client.dataObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClientMoishdUser implements Serializable, Comparable<ClientMoishdUser> {
	
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
	
	private String MACAddress;
    
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
		this.MACAddress = "";
		this.location = null;
		this.trophies = null;
		this.stats = null;
	}

	public ClientMoishdUser(String userNick, String pictureLink,
			Date dateRegistered, String userGoogleIdentifier,
			String registerID, String facebookID, String MACAddress, ClientLocation location,
			List<ClientTrophy> trophies, ClientUserGameStatistics stats) {
		super();
		this.userNick = userNick;
		this.pictureLink = pictureLink;
		this.dateRegistered = dateRegistered;
		this.userGoogleIdentifier = userGoogleIdentifier;
		this.registerID = registerID;
		this.facebookID = facebookID;
		this.MACAddress = MACAddress;
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

	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}

	public String getMACAddress() {
		return MACAddress;
	}

	@Override
	public int compareTo(ClientMoishdUser user){
		/*String lastNameUser1 = this.userNick.split(" ")[1];
		String lastNameUser2 = user.userNick.split(" ")[1];*/
		int place1 = this.userNick.indexOf(" ") + 1;
		int place2 = user.userNick.indexOf(" ") + 1;
		String lastNameUser1 = this.userNick.substring(place1);
		String lastNameUser2 = user.userNick.substring(place2);

		
		return lastNameUser1.compareTo(lastNameUser2);
	}
	


	}
