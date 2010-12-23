package moishd.server.servlets.game;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;

public class SendGameResultServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530008367358724317L;
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// TODO : check if any authentication is possiable
		//if (UserServiceFactory.getUserService().isUserAdmin()) {
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
					
					HashMap<String, String> winPayload = new HashMap<String, String>();
					winPayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					winPayload.put("Result", winValue.toString() + ":" + gameType);
					
					HashMap<String, String> losePayload = new HashMap<String, String>();
					losePayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					losePayload.put("Result", loseValue.toString() + ":" + gameType);
					
					tg = DSCommon.GetGameById(gameId);
					
					// TODO: Check if concurrent win is consistent
					if ((tg.getPlayerRecEndTime() == null) ||
							((tg.getPlayerInitEndTime() != null) && 
									(tg.getPlayerRecEndTime().after(tg.getPlayerInitEndTime())))) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameResult.toString(), winPayload);
							C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
									C2DMCommon.Actions.GameResult.toString(), losePayload);
					} else {
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), winPayload);
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), losePayload);
					}
					
					mInitUser.setBusy(false);
					mInitUser.SaveChanges();
					mRecUser.setBusy(false);
					mRecUser.SaveChanges();
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		//}
	}
}

