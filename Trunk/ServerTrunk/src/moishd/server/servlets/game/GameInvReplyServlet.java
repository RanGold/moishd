package moishd.server.servlets.game;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class GameInvReplyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		super.doPost(request, response);

		if (user != null) {
			try {
				String paramters = request.getReader().readLine();
				
				if (paramters.split("#").length != 3) {
					response.addHeader("Error", "");
					response.getWriter().println("TimeGameInvReplyServlet: invalid parameters " + paramters);
				} else {
					String gameId = paramters.split("#")[0];
					String invReply = paramters.split("#")[1];
					String popular = paramters.split("#")[2];
					
					MoishdGame tg = DSCommon.GetGameById(gameId);
					MoishdUser mInitUser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
					MoishdUser mRecUser = DSCommon.GetUserByGoogleId(tg.getPlayerRecId());
					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId", String.valueOf(tg.getGameLongId()));
					
					// TODO : delete
					LoggerCommon.Get().LogInfo("bla", invReply);
					LoggerCommon.Get().LogInfo("bla", popular);
					
					if (invReply.equals("Decline")) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.GameDeclined.toString(), payload);
						mInitUser.setBusy(false);
						mInitUser.SaveChanges();
						mRecUser.setBusy(false);
						mRecUser.SaveChanges();
					} else if (invReply.equals("AcceptTruth")) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(),
								C2DMCommon.Actions.StartGameTruth.getPopular(popular), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameTruth.getPopular(popular), payload);
						tg.setGameType(C2DMCommon.Actions.StartGameTruth.toString());
						tg.SaveChanges();
					} else if (invReply.equals("AcceptDareSimonPro" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareSimonPro.getPopular(popular), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareSimonPro.getPopular(popular), payload);
						tg.setGameType(C2DMCommon.Actions.StartGameDareSimonPro.toString());
						tg.SaveChanges();
					} else if (invReply.equals("AcceptDareMixing" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareMixing.getPopular(popular), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareMixing.getPopular(popular), payload);
						tg.setGameType(C2DMCommon.Actions.StartGameDareMixing.toString());
						tg.SaveChanges();
					} else if (invReply.equals("AcceptDareFastClick" )) {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareFastClick.getPopular(popular), payload);
						C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
								C2DMCommon.Actions.StartGameDareFastClick.getPopular(popular), payload);
						tg.setGameType(C2DMCommon.Actions.StartGameDareFastClick.toString());
						tg.SaveChanges();
					} else {
						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), C2DMCommon.Actions.GameCanceled.toString(), payload);
						mInitUser.setBusy(false);
						mInitUser.SaveChanges();
						mRecUser.setBusy(false);
						mRecUser.SaveChanges();
					}
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
