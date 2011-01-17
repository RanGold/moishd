package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientUserGameStatistics;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserGameStatistics extends CommonJDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5610076177924180280L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key statsId;

	@Persistent
	private int gamesPlayed;

	@Persistent
	private int gamesWon;

	@Persistent
	private int rank;

	@Persistent
	private int points;
	
	@Persistent
	private int gamesWonInARow;
	
	@Persistent
	private int topMoisherPoints;
	
	@Persistent(serialized = "true", defaultFetchGroup = "true")
    private Map<String, Integer> gamesPoints;

	@Persistent(mappedBy = "stats")
	private MoishdUser moishdUser;

	public UserGameStatistics() {
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.rank = 0;
		this.points = 0;
		this.gamesWonInARow = 0;
		this.topMoisherPoints = -1;
		this.gamesPoints = new HashMap<String, Integer>();
	}

	public UserGameStatistics(int gamesPlayed, int gamesWon, int rank, 
			int points, int gamesWonInARow, int topMoisherPoints, 
			Map<String, Integer> gamesPoints ) {
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.rank = rank;
		this.points = points;
		this.gamesWonInARow = gamesWonInARow;
		this.topMoisherPoints = topMoisherPoints;
		this.gamesPoints = gamesPoints;
	}

	public ClientUserGameStatistics toClientUserGameStatistics() {
		return (new ClientUserGameStatistics(this.getGamesPlayed(), this.getGamesWon(), 
				this.getRank(), this.getPoints(), this.getGamesWonInARow(), 
				this.getTopMoisherPoints(), this.getGamesPoints()));
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getGamesWonInARow() {
		return gamesWonInARow;
	}

	public void setGamesWonInARow(int gamesWonInARow) {
		this.gamesWonInARow = gamesWonInARow;
	}
	
	public int getTopMoisherPoints() {
		return topMoisherPoints;
	}

	public void setTopMoisherPoints(int topMoisherPoints) {
		this.topMoisherPoints = topMoisherPoints;
	}

	public Key getStatsId() {
		return statsId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}

	public void setGamesPoints(Map<String, Integer> gamesPoints) {
		this.gamesPoints = gamesPoints;
	}

	public Map<String, Integer> getGamesPoints() {
		return gamesPoints;
	}
}