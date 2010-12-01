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
	private String player1Id;
	
	@Persistent
	private String player2Id;
	
	@Persistent
	private Date player1StartTime;
	
	@Persistent
	private Date player2StartTime;
	
	@Persistent
	private Date player1EndTime;
	
	@Persistent
	private Date player2EndTime;
	
	@Persistent
	private Boolean isDecided;

	public TimeGame(String player1Id) {
		super();
		this.player1Id = player1Id;
	}

	public String getPlayer1Id() {
		return player1Id;
	}

	public void setPlayer1Id(String player1Id) {
		this.player1Id = player1Id;
	}

	public String getPlayer2Id() {
		return player2Id;
	}

	public void setPlayer2Id(String player2Id) {
		this.player2Id = player2Id;
	}

	public Date getPlayer1StartTime() {
		return player1StartTime;
	}

	public void setPlayer1StartTime(Date player1StartTime) {
		this.player1StartTime = player1StartTime;
	}

	public Date getPlayer2StartTime() {
		return player2StartTime;
	}

	public void setPlayer2StartTime(Date player2StartTime) {
		this.player2StartTime = player2StartTime;
	}

	public Date getPlayer1EndTime() {
		return player1EndTime;
	}

	public void setPlayer1EndTime(Date player1EndTime) {
		this.player1EndTime = player1EndTime;
	}

	public Date getPlayer2EndTime() {
		return player2EndTime;
	}

	public void setPlayer2EndTime(Date player2EndTime) {
		this.player2EndTime = player2EndTime;
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
