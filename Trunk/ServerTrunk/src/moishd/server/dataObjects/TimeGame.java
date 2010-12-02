package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class TimeGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8811952842123204672L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private long gameId;
	
	@Persistent
	private String playerInitId;
	
	@Persistent
	private String playerRecId;
	
	@Persistent
	private Date playerInitStartTime;
	
	@Persistent
	private Date playerRecStartTime;
	
	@Persistent
	private Date playerInitEndTime;
	
	@Persistent
	private Date playerRecEndTime;
	
	@Persistent
	private Boolean isDecided;

	
	public TimeGame(String playerInitId, String playerRecId) {
		super();
		this.playerInitId = playerInitId;
		this.playerRecId = playerRecId;
	}

	public String getPlayerInitId() {
		return playerInitId;
	}

	public void setPlayerInitId(String playerInitId) {
		this.playerInitId = playerInitId;
	}

	public String getPlayerRecId() {
		return playerRecId;
	}

	public void setPlayerRecId(String playerRecId) {
		this.playerRecId = playerRecId;
	}

	public Date getPlayerInitStartTime() {
		return playerInitStartTime;
	}

	public void setPlayerInitStartTime(Date playerInitStartTime) {
		this.playerInitStartTime = playerInitStartTime;
	}

	public Date getPlayerRecStartTime() {
		return playerRecStartTime;
	}

	public void setPlayerRecStartTime(Date playerRecStartTime) {
		this.playerRecStartTime = playerRecStartTime;
	}

	public Date getPlayerInitEndTime() {
		return playerInitEndTime;
	}

	public void setPlayerInitEndTime(Date playerInitEndTime) {
		this.playerInitEndTime = playerInitEndTime;
	}

	public Date getPlayerRecEndTime() {
		return playerRecEndTime;
	}

	public void setPlayerRecEndTime(Date playerRecEndTime) {
		this.playerRecEndTime = playerRecEndTime;
	}

	public Boolean getIsDecided() {
		return isDecided;
	}

	public void setIsDecided(Boolean isDecided) {
		this.isDecided = isDecided;
	}

	public long getGameId() {
		return gameId;
	}
}
