package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MoishdGame extends CommonJDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8811952842123204672L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key gameId;
	
	@Persistent
	private long gameLongId;
	
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
	private boolean isDecided;

	@Persistent
	private Date initiated;
	
	@Persistent
	private String gameType;
	
	public MoishdGame(String playerInitId, String playerRecId) {
		super();
		this.playerInitId = playerInitId;
		this.playerRecId = playerRecId;
		this.initiated = new Date();
		this.isDecided = false;
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

	public boolean getIsDecided() {
		return isDecided;
	}

	public void setIsDecided(boolean isDecided) {
		this.isDecided = isDecided;
	}

	public Key getGameId() {
		return gameId;
	}

	public Date getInitiated() {
		return initiated;
	}

	public void setGameLongId(long gameLongId) {
		this.gameLongId = gameLongId;
	}

	public long getGameLongId() {
		return gameLongId;
	}
	
	@Override
	public void SaveChanges() {
		super.SaveChanges();
		this.setGameLongId(this.getGameId().getId());
		super.SaveChanges();
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameType() {
		return gameType;
	}
}
