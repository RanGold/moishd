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

public class SendInviteToGameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("inviteQueue")) {
			try {
				String initID = request.getParameter("initID");
				String recID = request.getParameter("recID");

				MoishdUser initUser = DSCommon.GetUserByGoogleId(initID);
				MoishdUser recUser = DSCommon.GetUserByGoogleId(recID);

				if (!recUser.isRegistered()){
					C2DMCommon.PushGenericMessage(initUser.getRegisterID(),
							C2DMCommon.Actions.PlayerOffline.toString(),
							new HashMap<String, String>());
				} else if (recUser.isBusy() && !recUser.getBusyWith().equals(initID)) {
					C2DMCommon.PushGenericMessage(initUser.getRegisterID(),
							C2DMCommon.Actions.PlayerBusy.toString(),
							new HashMap<String, String>());
				} else if (!recUser.isBusy()) {
					
					if (initUser.isBusy()) {
						MoishdUser oldRecUser = DSCommon.GetUserByGoogleId(initUser.getBusyWith());
						oldRecUser.setNotBusy();
						oldRecUser.SaveChanges();
					}
					
					initUser.setPartner(recID);
					initUser.SaveChanges();
					recUser.setPartner(initID);
					recUser.SaveChanges();

					MoishdGame tg = new MoishdGame(initID, recID);
					tg.SaveChanges();
					response.getWriter().write(
							String.valueOf(tg.getGameLongId()));

					LoggerCommon.Get().LogInfo(this, initID + " invited " + recID);
					
					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId",
							String.valueOf(tg.getGameId().getId()));
					payload.put("InviterName", DSCommon.GetUserByGoogleId(initID).getUserNick());
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
