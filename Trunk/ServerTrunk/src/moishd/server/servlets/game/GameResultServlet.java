package moishd.server.servlets.game;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GameResultServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530008367358724317L;
	
	protected enum Result {
		Won,
		Lost
	}
	
	protected String servletName;
	protected Result winValue;
	protected Result loseValue;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				Date endDate = new Date();
				String gameId = request.getReader().readLine();
				TimeGame tg = DSCommon.GetTimeGameById(gameId);
				if (tg.getPlayerInitId().equals(user.getEmail())) {
					tg.setPlayerInitEndTime(endDate);
				} else if (tg.getPlayerRecId().equals(user.getEmail())) {
					tg.setPlayerRecEndTime(endDate);
				} else {
					response.addHeader("Error", "");
					response.getWriter().println(servletName + ": player " + 
							user.getEmail() + " isn't in game " + gameId);
					return;
				}
				tg.SaveChanges();
				
				tg = DSCommon.GetTimeGameById(gameId);
				
				if (!tg.getIsDecided()) {
					tg.setIsDecided(true);
					tg.SaveChanges();
					
					MoishdUser mInitUser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
					MoishdUser mRecUser = DSCommon.GetUserByGoogleId(tg.getPlayerRecId());
					
					HashMap<String, String> winPayload = new HashMap<String, String>();
					winPayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					winPayload.put("Result", winValue.toString());
					
					HashMap<String, String> losePayload = new HashMap<String, String>();
					losePayload.put("GameId", String.valueOf(tg.getGameId().getId()));
					losePayload.put("Result", loseValue.toString());
					
					tg = DSCommon.GetTimeGameById(gameId);
					
					// TODO: Check if concurrent win is consistent
					if ((tg.getPlayerRecEndTime() == null) ||
							((tg.getPlayerInitEndTime() != null) && 
									(tg.getPlayerRecEndTime().after(tg.getPlayerInitEndTime())))) {
						if (user.getEmail().equals(mInitUser.getUserGoogleIdentifier())) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameResult.toString(), winPayload);
							C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
									C2DMCommon.Actions.GameResult.toString(), losePayload);
						}
					} else if (user.getEmail().equals(mRecUser.getUserGoogleIdentifier())) {
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), winPayload);
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameResult.toString(), losePayload);
					}
				}
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println(servletName + ": " + e.getMessage());
			} catch (ServletException e) {
				response.addHeader("Error", "");
				response.getWriter().println(servletName + ": " + e.getMessage());
			}
		}
	}
}
