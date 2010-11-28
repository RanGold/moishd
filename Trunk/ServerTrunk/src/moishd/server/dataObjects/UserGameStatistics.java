package moishd.server.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import moishd.client.dataObjects.ClientUserGameStatistics;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserGameStatistics implements Serializable {
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

	@Persistent(mappedBy = "stats")
	private MoishdUser moishdUser;

	public UserGameStatistics() {
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.rank = -1;
		this.points = 0;
	}

	public UserGameStatistics(int gamesPlayed, int gamesWon,
			int rank, int points) {
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.rank = rank;
		this.points = points;
	}

	public ClientUserGameStatistics toClientUserGameStatistics() {
		return (new ClientUserGameStatistics(this.getGamesPlayed(),
				this.getGamesWon(), this.getRank(), this.getPoints()));
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

	public Key getStatsId() {
		return statsId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
}