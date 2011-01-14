package moishd.server.servlets.game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.StringIntPair;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class GetTopGamePlayersServlet extends GeneralServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5452032628927024429L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			String gameName = request.getReader().readLine();
			GameStatistics stat = DSCommon.GetGameStatByName(gameName);
			LoggerCommon.Get().LogInfo(this, "TOP MOISHER GAME STAT " + stat.getGameType());

			List<MoishdUser> users = new LinkedList<MoishdUser>();
			for (StringIntPair siPair : stat.getTopMoishers()) {
				try {
					MoishdUser topMoisher = DSCommon.GetUserByGoogleId(siPair.getStringValue());
					topMoisher.getStats().setTopMoisherPoints(siPair.getNumberValue());
					users.add(DSCommon.GetUserByGoogleId(siPair.getStringValue()));
				} catch (DataAccessException e) {
					LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
				}
			}
			
			GsonCommon.WriteJsonToResponse(MoishdUser.copyToClientMoishdUserList(users), response);
		}
	}

}
