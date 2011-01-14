package moishd.server.servlets.game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			Set<Entry<String, Integer>> entries = stat.getTopMoishers().entrySet();
			for (Entry<String, Integer> siPair : entries) {
				try {
					MoishdUser topMoisher = DSCommon.GetUserByGoogleId(siPair.getKey());
					topMoisher.getStats().setTopMoisherPoints(siPair.getValue());
					users.add(topMoisher);
				} catch (DataAccessException e) {
					LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
				}
			}
			
			GsonCommon.WriteJsonToResponse(MoishdUser.copyToClientMoishdUserList(users), response);
		}
	}

}
