package moishd.server.servlets.game;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.servlets.GeneralServlet;

public class AddGamePlayedServlet extends GeneralServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7503858122567616291L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);
		//TODO - add real logics
		if (user != null) {
			String input = request.getReader().readLine();				
			String gameType = input;
			
			List<GameStatistics> stats = DSCommon.GetGameStatsByType(gameType);
			
			if (stats.size() == 0) {
				LoggerCommon.Get().
				LogError(this, response, 
						"Game type " + gameType + 
						" doesn't exist");
				return;
			} else if (stats.size() > 1) {
				LoggerCommon.Get().
				LogError(this, response, 
						"Game type " + gameType + 
						" has more than 1 occurences");
				return;
			} else {
				stats.get(0).addGamePlayed();
				stats.get(0).SaveChanges();
			}
		}
	}
}
