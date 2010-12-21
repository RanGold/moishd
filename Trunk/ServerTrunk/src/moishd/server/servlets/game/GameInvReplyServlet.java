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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GameInvReplyServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			LoggerCommon.Get().LogError(this, "Not Logged In");
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				String paramters = request.getReader().readLine();
				
				if (paramters.split("#").length != 2) {
					response.addHeader("Error", "");
					response.getWriter().println("TimeGameInvReplyServlet: invalid parameters " + paramters);
				} else {
					String gameId = paramters.split("#")[0];
					String invReply = paramters.split("#")[1];

					MoishdGame tg = DSCommon.GetGameById(gameId);
					MoishdUser mInitUser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
					MoishdUser mRecUser = DSCommon.GetUserByGoogleId(tg.getPlayerRecId());
					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId", String.valueOf(tg.getGameLongId()));
					
					if (invReply.equals("Decline")) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameDeclined.toString(), payload);
						mInitUser.setBusy(false);
						mInitUser.SaveChanges();
						mRecUser.setBusy(false);
						mRecUser.SaveChanges();
					} else if (invReply.equals("AcceptTruth")) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameTruth.toString(), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameTruth.toString(), payload);
					} else if (invReply.equals("AcceptDareSimonPro" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareSimonPro.toString(), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareSimonPro.toString(), payload);
					} else if (invReply.equals("AcceptDareMixing" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareMixing.toString(), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareMixing.toString(), payload);
					} else if (invReply.equals("AcceptDareFastClick" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareFastClick.toString(), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareFastClick.toString(), payload);
					} else {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameCanceled.toString(), payload);
						mInitUser.setBusy(false);
						mInitUser.SaveChanges();
						mRecUser.setBusy(false);
						mRecUser.SaveChanges();
					}
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().println("TimeGameInvReplyServlet: " + e.getMessage());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().println("TimeGameInvReplyServlet: " + e.getMessage());
			}
		}
	}
}
