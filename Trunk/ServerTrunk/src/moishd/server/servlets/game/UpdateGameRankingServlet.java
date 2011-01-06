package moishd.server.servlets.game;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.GameStatistics;

public class UpdateGameRankingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6100912194997060648L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("rankQueue")) {
			String gameType = request.getParameter("gameType");
			int rank = Integer.parseInt(request.getParameter("rank"));

			List<GameStatistics> stats = DSCommon.GetGameStatsByType(gameType);

			GameStatistics newGame;
			if (stats.size() > 1) {
				LoggerCommon.Get().
				LogError(this, response, 
						"Game type " + gameType + 
						" has more than 1 occurences");
				return;
			} else if (stats.size() == 0) {
				newGame = new GameStatistics(gameType);
			} else {
				newGame = stats.get(0);
			}
			newGame.addGamePlayed();
			newGame.addRank(rank);
			newGame.SaveChanges();
		}
	}
}
