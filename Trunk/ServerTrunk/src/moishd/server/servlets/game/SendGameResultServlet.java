package moishd.server.servlets.game;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.TrophiesEnum;
import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.common.C2DMCommon.Actions;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;

public class SendGameResultServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530008367358724317L;


	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		LoggerCommon.Get().LogInfo("adsd", "check1");
		String headername = ""; 
		for(@SuppressWarnings("rawtypes")
				Enumeration e = request.getHeaderNames(); e.hasMoreElements();){
			headername = (String)e.nextElement();
			LoggerCommon.Get().LogInfo("adsd", headername + " - " + request.getHeader(headername));
		}
		LoggerCommon.Get().LogInfo("adsd", "check2");

		// TODO : check if any authentication is possiable
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

					HashMap<String, String> winPayload = new HashMap<String, String>();
					winPayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					winPayload.put("Result", winValue.toString() + ":" + gameType);

					HashMap<String, String> losePayload = new HashMap<String, String>();
					losePayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					losePayload.put("Result", loseValue.toString() + ":" + gameType);

					tg = DSCommon.GetGameById(gameId);
					int [] pointsAddedToBothSides;
					// TODO: Check if concurrent win is consistent
					if ((tg.getPlayerRecEndTime() == null) ||
							((tg.getPlayerInitEndTime() != null) && 
									(tg.getPlayerRecEndTime().after(tg.getPlayerInitEndTime())))) {

						winner = mInitUser;
						loser = mRecUser;
						pointsAddedToBothSides = UpdateGameStatistics(tg, winner, loser);
						winPayload.put("Points", String.valueOf(pointsAddedToBothSides[0]));
						losePayload.put("Points", String.valueOf(pointsAddedToBothSides[1]));

						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), winPayload);

						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), losePayload);

					} else {

						winner = mRecUser;
						loser = mInitUser;
						pointsAddedToBothSides = UpdateGameStatistics(tg, winner, loser);
						winPayload.put("Points", String.valueOf(pointsAddedToBothSides[0]));
						losePayload.put("Points", String.valueOf(pointsAddedToBothSides[1]));

						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), winPayload);
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), losePayload);

					}

					mInitUser.setBusy(false);
					mInitUser.SaveChanges();
					mRecUser.setBusy(false);
					mRecUser.SaveChanges();

					updateRankAndTrophiesAndNotifyUser(winner, loser);
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
			addedPoints[0] = 1;
			addedPoints[1] = 0;
		}else if (moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareFastClick.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3;
			addedPoints[1] = 1;
		}else if(moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareMixing.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3;
			addedPoints[1] = 1;
		}else if(moishdGame.getGameType().equals(C2DMCommon.Actions.StartGameDareSimonPro.toString())){
			winner.getStats().setPoints(winnerPoints + 3);
			loser.getStats().setPoints(loserPoints + 1);
			addedPoints[0] = 3;
			addedPoints[1] = 1;
		}
		else{

		}

		winner.SaveChanges();
		loser.SaveChanges();

		return addedPoints;
	}

	private void updateRankAndTrophiesAndNotifyUser(MoishdUser winner, MoishdUser loser){

		updateRank(loser, true);
		updateRankAndTrophies(winner);
	}

	private void updateRankAndTrophies(MoishdUser winner) {

		boolean rankUpdated;

		HashMap<String, String> rankAndTrophiesPayload = updateRank(winner, false);

		if (rankAndTrophiesPayload == null){
			rankAndTrophiesPayload = new HashMap<String, String>();
			rankUpdated = false;
		}
		else{
			rankUpdated = true;
		}

		List<TrophiesEnum> trophies = winner.getTrophies();
		String tropiesAchieved = "";
		int numOfTrophiesObtained = 0;

		int numOfWinsInARow = winner.getStats().getGamesWonInARow();

		if (numOfWinsInARow == 10 && !trophies.contains(TrophiesEnum.TenInARow)){
			trophies.add(TrophiesEnum.TenInARow);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.TenInARow.toString();
		}
		else if(numOfWinsInARow == 20 && !trophies.contains(TrophiesEnum.TwentyInARow)){
			trophies.add(TrophiesEnum.TwentyInARow);
			numOfTrophiesObtained++;
			tropiesAchieved = tropiesAchieved + "#" + TrophiesEnum.TwentyInARow.toString();
		}

		int numOfWins = winner.getStats().getGamesWon();

		if  (numOfWins == 1 && !trophies.contains(TrophiesEnum.FirstTime)){
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

		rankAndTrophiesPayload.put("Trophies", tropiesAchieved);	

		if (numOfTrophiesObtained > 0){
			winner.SaveChanges();
			rankAndTrophiesPayload.put("NumberOfTrophies", String.valueOf(numOfTrophiesObtained));
		}

		String action;

		if (rankUpdated){
			if (numOfTrophiesObtained > 0){
				action = C2DMCommon.Actions.RankAndTrophiesUpdated.toString();
			}
			else{
				action = C2DMCommon.Actions.RankUpdated.toString();
			}
		}
		else{
			if (numOfTrophiesObtained > 0){
				action = C2DMCommon.Actions.TrophiesUpdated.toString();
			}
			else{
				action = null;
			}
		}

		if (action != null){
			try {
				C2DMCommon.PushGenericMessage(winner.getRegisterID(), action, rankAndTrophiesPayload);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HashMap<String, String> updateRank(MoishdUser user, boolean sendToUser){

		int userUpdatedPoints = user.getStats().getPoints();

		HashMap<String, String> rankPayload = new HashMap<String, String>();
		switch(userUpdatedPoints){
		case 50:
			user.getStats().setRank(1);
			rankPayload.put("Rank", "1");
			break;
		case 150:
			user.getStats().setRank(2);
			rankPayload.put("Rank", "2");
			break;
		case 300:
			user.getStats().setRank(3);
			rankPayload.put("Rank", "3");
			break;
		case 500:
			user.getStats().setRank(4);
			rankPayload.put("Rank", "4");
			break;
		case 1000:
			user.getStats().setRank(5);
			rankPayload.put("Rank", "5");
			break;
		default:
			return null;
		}

		user.SaveChanges();

		if (sendToUser){
			try {
				C2DMCommon.PushGenericMessage(user.getRegisterID(), C2DMCommon.Actions.RankUpdated.toString(), rankPayload);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return rankPayload;
		}
		else{
			return rankPayload;
		}
	}
}


