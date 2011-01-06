package moishd.server.servlets.game;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.servlets.GeneralServlet;

public class GetMostPopularGameServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 490380246631338919L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			List<GameStatistics> stats = DSCommon.GetMostPopularGame();
			String gameType;
			if (stats.size() == 0) {
				gameType = "DareSimonPro";
			} else {
				gameType = stats.get(0).getGameType();
			}
			
			LoggerCommon.Get().LogInfo(this, gameType);
			
			response.getWriter().print(gameType);
		}
	}
}
