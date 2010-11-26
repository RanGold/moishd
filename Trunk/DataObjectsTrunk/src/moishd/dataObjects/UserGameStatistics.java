package moishd.dataObjects;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
    private Integer gamesPlayed;
    
    @Persistent
    private Integer gamesWon;
    
    @Persistent
    private Integer rank;
    
    @Persistent
    private Integer points;
    
    @Persistent(mappedBy = "stats")
    private MoishdUser moishdUser;

    public UserGameStatistics() {
    	this.gamesPlayed = 0;
    	this.gamesWon = 0;
    	this.rank = -1;
    	this.points = 0;
    }
    
    public UserGameStatistics(Integer gamesPlayed, Integer gamesWon, Integer rank, Integer points) {
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.rank = rank;
        this.points = points;
    }

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Key getStatsId() {
		return statsId;
	}

	public MoishdUser getMoishdUser() {
		return moishdUser;
	}
}