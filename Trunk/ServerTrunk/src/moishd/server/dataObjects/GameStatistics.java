package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class GameStatistics extends CommonJDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -906128763963841878L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key gameStatId;

    @Persistent
    private String gameType;
    
    @Persistent
    private int timesPlayed;
    
    @Persistent
    private int rankTotal;
    
    @Persistent
    private int rankingNumber;
    
    @Persistent
    private double gameRank;
    
    @Persistent
    private Map<String,Integer> gamesPoints;

	public GameStatistics(String gameType) {
		super();
		this.gameType = gameType;
		this.timesPlayed = 0;
		this.rankingNumber = 0;
		this.rankTotal = 0;
		this.setGameRank(0);
	}

	public Key getGameStatId() {
		return gameStatId;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameType() {
		return gameType;
	}

	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}

	public void setRankTotal(int rankTotal) {
		this.rankTotal = rankTotal;
	}

	public int getRankTotal() {
		return rankTotal;
	}

	public void setRankingNumber(int rankingNumber) {
		this.rankingNumber = rankingNumber;
	}

	public int getRankingNumber() {
		return rankingNumber;
	}
	
	public void setGameRank(double gameRank) {
		this.gameRank = gameRank;
	}

	public double getGameRank() {
		return gameRank;
	}
	
	public void addGamePlayed() {
		this.setTimesPlayed(this.getTimesPlayed() + 1);
	}
	
	public void addRank(int rank) {
		this.setRankTotal(this.getRankTotal() + rank);
		this.setRankingNumber(this.getRankingNumber() + 1);
		this.setGameRank(this.getRankTotal() / this.getRankingNumber());
	}

	public void setGamesPoints(Map<String,Integer> gamesPoints) {
		this.gamesPoints = gamesPoints;
	}

	public Map<String,Integer> getGamesPoints() {
		return gamesPoints;
	}
}