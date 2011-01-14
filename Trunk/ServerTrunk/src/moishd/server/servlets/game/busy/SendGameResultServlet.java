package moishd.server.servlets.game.busy;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.TrophiesEnum;
import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.BusyObject;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.StringIntPair;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class SendGameResultServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530008367358724317L;

	private int factorNearBy = 1;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("resultQueue")) {
			try {
				String gameId = request.getParameter("gameId");
				String gameType = request.getParameter("gameType");
				String winValue = request.getParameter("winValue");
				String loseValue = request.getParameter("loseValue");

				MoishdGame tg = DSCommon.GetGameById(gameId);

				if (!tg.getIsDecided()) {
					tg.setIsDecided(true);
					tg.SaveChanges();

					MoishdUser mInitUser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
					MoishdUser mRecUser = DSCommon.GetUserByGoogleId(tg.getPlayerRecId());

					MoishdUser winner;
					MoishdUser loser;

					tg = DSCommon.GetGameById(gameId);
					if ((tg.getPlayerRecEndTime() == null) ||
							((tg.getPlayerInitEndTime() != null) && 
									(tg.getPlayerRecEndTime().after(tg.getPlayerInitEndTime())))) {

						winner = mInitUser;
						loser = mRecUser;
					}
					else{
						winner = mRecUser;
						loser = mInitUser;
					}

					HashMap<String, String> winPayload = new HashMap<String, String>();
					winPayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					winPayload.put("Result", winValue.toString() + ":" + gameType);

					HashMap<String, String> losePayload = new HashMap<String, String>();
					losePayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					losePayload.put("Result", loseValue.toString() + ":" + gameType);

					if (winner.getLocation().isInitialized() && 
							loser.getLocation().isInitialized() &&
							DSCommon.CalculateDistance(winner.getLocation(), loser.getLocation()) <=1){
						LoggerCommon.Get().LogInfo(this, "winner location is " + winner.getLocation());
						LoggerCommon.Get().LogInfo(this, "loser location is " + loser.getLocation());
						LoggerCommon.Get().LogInfo(this, "distance is " + DSCommon.CalculateDistance(winner.getLocation(), loser.getLocation()));

						factorNearBy = 2;
						winPayload.put("NearByGame", "yes");
						losePayload.put("NearByGame", "yes");
					}
					else {
						winPayload.put("NearByGame", "no");
						losePayload.put("NearByGame", "no");
					}

					if (winValue.toString().equals("Won")){
						int [] pointsAddedToBothSides = UpdateGameStatistics(tg, winner, loser);
						winPayload.put("Points", String.valueOf(pointsAddedToBothSides[0]));
						losePayload.put("Points", String.valueOf(pointsAddedToBothSides[1]));
					}
					else {
						int [] pointsAddedToBothSides = UpdateGameStatistics(tg, loser, winner);
						winPayload.put("Points", String.valueOf(pointsAddedToBothSides[1]));
						losePayload.put("Points", String.valueOf(pointsAddedToBothSides[0]));	
					}

					updateRankAndTrophies(winPayload, winner);
					updateRankAndTrophies(losePayload, loser);

					LoggerCommon.Get().LogInfo(this, "Winner: " + winner.getUserGoogleIdentifier());
					LoggerCommon.Get().LogInfo(this, "Winner: " + winner.getUserGoogleIdentifier());
					C2DMCommon.PushGenericMessage(winner.getRegisterID(), 
							C2DMCommon.Actions.GameResult.toString(), winPayload);
					C2DMCommon.PushGenericMessage(loser.getRegisterID(), 
							C2DMCommon.Actions.GameResult.toString(), losePayload);

					List<BusyObject> busyUsers = new LinkedList<BusyObject>();
					busyUsers.add(new BusyObject(mInitUser.getUserGoogleIdentifier(), false, mRecUser.getBusyWith()));
					busyUsers.add(new BusyObject(mRecUser.getBusyWith(), false, mInitUser.getUserGoogleIdentifier()));

					String json = GsonCommon.GetJsonString(busyUsers);

					Queue queue = QueueFactory.getQueue("inviteQueue");
					queue.add(TaskOptions.Builder
							.url("/queues/UpdateBusySynced").method(Method.POST)
							.param("json", json));
					//					mInitUser.setNotBusy();
					//					mInitUser.SaveChanges();
					//					mRecUser.setNotBusy();
					//					mRecUser.SaveChanges();
				}

			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}

	private int [] UpdateGameStatistics(MoishdGame moishdGame, MoishdUser winner, MoishdUser loser){

		//Increment number of games played for both users
		int winnerGamesPlayed = winner.getStats().getGamesPlayed();
		winner.getStats().setGamesPlayed(winnerGamesPlayed + 1);

		int loserGamesPlayed= loser.getStats().getGamesPlayed();
		loser.getStats().setGamesPlayed(loserGamesPlayed + 1);

		//Update winner's won games statistics
		int winnerGamesWon = winner.getStats().getGamesWon();
		winner.getStats().setGamesWon(winnerGamesWon+1);

		int winnerGamesWonInARow = winner.getStats().getGamesWonInARow();
		winner.getStats().setGamesWonInARow(winnerGamesWonInARow+1);

		//Update loser's won games statistics
		loser.getStats().setGamesWonInARow(0);

		int winnerPoints = winner.getStats().getPoints();
		int loserPoints = loser.getStats().getPoints();

		int [] addedPoints = new int[2];
		//Do points logic
		if (moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameTruth.toString())){
			winner.getStats().setPoints(winnerPoints + 1);
			addedPoints[0] = 1 *factorNearBy;
			addedPoints[1] = 0*factorNearBy;
		}else if (moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareFastClick.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3*factorNearBy;
			addedPoints[1] = 1*factorNearBy;
		}else if(moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareMixing.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3*factorNearBy;
			addedPoints[1] = 1*factorNearBy;
		}else if(moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareSimonPro.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3*factorNearBy;
			addedPoints[1] = 1*factorNearBy;
		}
		else{

		}

		winner.SaveChanges();
		loser.SaveChanges();

		List<StringIntPair> winnerGamesPoints = winner.getStats().getGamesPoints();
		updateGamePoints(winnerGamesPoints, winner, addedPoints[0], moishdGame);

		List<StringIntPair> loserGamesPoints = loser.getStats().getGamesPoints();
		updateGamePoints(loserGamesPoints, loser, addedPoints[1], moishdGame);

		return addedPoints;
	}
	
	private void updateGamePoints(List<StringIntPair> gamesPointsList, MoishdUser user, int addedPoints, MoishdGame moishdGame){
		
		boolean updated = false;
		int currentGamePoints = -1;
		
		for (int i=0; i < gamesPointsList.size(); i++){
			StringIntPair current = gamesPointsList.get(i);
			if (current.getStringValue().equals(moishdGame.getGameType())){
				int previousGamePoints = current.getNumberValue();
				currentGamePoints = previousGamePoints + addedPoints;
				current.setNumberValue(currentGamePoints);
				updated = true;
				break;
			}
		}
		
		if (!updated){
			currentGamePoints = addedPoints;
			gamesPointsList.add(new StringIntPair(moishdGame.getGameType(), currentGamePoints));
		}
		
		GameStatistics gameStatistics = DSCommon.GetGameStatByName(moishdGame.getGameType());
		List<StringIntPair> topMoishersList = gameStatistics.getTopMoishers();
		if (topMoishersList == null){
			topMoishersList = new LinkedList<StringIntPair>();
			topMoishersList.add(0, new StringIntPair(user.getUserGoogleIdentifier(), currentGamePoints));
		}
		else{
			for (int i=0; i < topMoishersList.size(); i++){
				StringIntPair currentMoisher = topMoishersList.get(i);
				int currentMoisherPoints = currentMoisher.getNumberValue();
				if (currentMoisherPoints < currentGamePoints){
					topMoishersList.add(i, new StringIntPair(user.getUserGoogleIdentifier(), currentGamePoints));
				}
			}
		}
		
		if (topMoishersList.size() == 6){
			topMoishersList.remove(5);
		}
		
		user.SaveChanges();
		gameStatistics.SaveChanges();
	}


	private void updateRankAndTrophies(HashMap<String, String> payload, MoishdUser user) {

		updateRank(payload, user);

		LoggerCommon.Get().LogInfo(this, "starting trophies calc ");

		List<TrophiesEnum> trophies = user.getTrophies();

		String tropiesAchieved = "";
		int numOfTrophiesObtained = 0;

		int numOfWinsInARow = user.getStats().getGamesWonInARow();
		LoggerCommon.Get().LogInfo(this, "numOfWinsInARow " + numOfWinsInARow );

		if (numOfWinsInARow == 10 && !trophies.contains(TrophiesEnum.TenInARow)){
			LoggerCommon.Get().LogInfo(this, "TenInARow");

			trophies.add(TrophiesEnum.TenInARow);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.TenInARow.toString();
		}
		else if(numOfWinsInARow == 20 && !trophies.contains(TrophiesEnum.TwentyInARow)){
			trophies.add(TrophiesEnum.TwentyInARow);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.TwentyInARow.toString();
		}

		int numOfWins = user.getStats().getGamesWon();
		LoggerCommon.Get().LogInfo(this, "gamesWon " + numOfWins );

		if  (numOfWins == 1 && !trophies.contains(TrophiesEnum.FirstTime)){
			LoggerCommon.Get().LogInfo(this, "firstTime");

			trophies.add(TrophiesEnum.FirstTime);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.FirstTime.toString();
		}
		else if (numOfWins == 10 && !trophies.contains(TrophiesEnum.TinyMoisher)){
			trophies.add(TrophiesEnum.TinyMoisher);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.TinyMoisher.toString();
		}
		else if (numOfWins == 50 && !trophies.contains(TrophiesEnum.MiniMoisher)){
			trophies.add(TrophiesEnum.MiniMoisher);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.MiniMoisher.toString();
		}
		else if (numOfWins == 100 && !trophies.contains(TrophiesEnum.MasterMoisher)){
			trophies.add(TrophiesEnum.MasterMoisher);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.MasterMoisher.toString();
		}
		else if (numOfWins == 250 && !trophies.contains(TrophiesEnum.SuperMoisher)){
			trophies.add(TrophiesEnum.SuperMoisher);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.SuperMoisher.toString();
		}
		else if (numOfWins == 500 && !trophies.contains(TrophiesEnum.MegaMoisher)){
			trophies.add(TrophiesEnum.MegaMoisher);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.MegaMoisher.toString();
		}
		user.SaveChanges();

		payload.put("Trophies", tropiesAchieved);
		payload.put("NumberOfTrophies", String.valueOf(numOfTrophiesObtained));

	}

	private void updateRank(HashMap<String, String> payload, MoishdUser user){

		int userUpdatedPoints = user.getStats().getPoints();
		LoggerCommon.Get().LogInfo(this, "currentPoints " + userUpdatedPoints);

		int currentRank = user.getStats().getRank();
		if (100 > userUpdatedPoints && userUpdatedPoints >= 50 && currentRank == 0){
			user.getStats().setRank(1);
			payload.put("Rank", "1");
		}
		else if (300 > userUpdatedPoints && userUpdatedPoints >= 150 && currentRank == 1){
			user.getStats().setRank(2);
			payload.put("Rank", "2");
		}
		else if (500 > userUpdatedPoints && userUpdatedPoints >= 300 && currentRank == 2){
			user.getStats().setRank(3);
			payload.put("Rank", "3");
		}
		else if (1000 > userUpdatedPoints && userUpdatedPoints >= 500 && currentRank == 3){
			user.getStats().setRank(4);
			payload.put("Rank", "4");
		}
		else if (userUpdatedPoints >= 1000 && currentRank == 4){
			user.getStats().setRank(5);
			payload.put("Rank", "5");
		}
		user.SaveChanges();

	}
}