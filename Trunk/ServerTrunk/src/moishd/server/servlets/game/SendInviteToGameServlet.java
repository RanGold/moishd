package moishd.server.servlets.game;

import java.io.IOException;
import java.util.Enumeration;
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

public class SendInviteToGameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

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
		if (request.getHeader("X-AppEngine-QueueName").equals("inviteQueue")) {
			try {
				String initID = request.getParameter("initID");
				String recID = request.getParameter("recID");

				MoishdUser initUser = DSCommon.GetUserByGoogleId(initID);
				MoishdUser recUser = DSCommon.GetUserByGoogleId(recID);

				if (recUser.isBusy() || initUser.isBusy()) {
					C2DMCommon.PushGenericMessage(initUser.getRegisterID(),
							C2DMCommon.Actions.PlayerBusy.toString(),
							new HashMap<String, String>());
				} else if (!recUser.isRegistered()){
					C2DMCommon.PushGenericMessage(initUser.getRegisterID(),
							C2DMCommon.Actions.PlayerOffline.toString(),
							new HashMap<String, String>());
				}else {
					initUser.setBusy(true);
					initUser.SaveChanges();
					recUser.setBusy(true);
					recUser.SaveChanges();

					MoishdGame tg = new MoishdGame(initID, recID);
					tg.SaveChanges();
					response.getWriter().write(
							String.valueOf(tg.getGameLongId()));

					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId",
							String.valueOf(tg.getGameId().getId()));
					C2DMCommon.PushGenericMessage(recUser.getRegisterID(),
							C2DMCommon.Actions.GameInvitation.toString(),
							payload);
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			}
		}
	}
}
