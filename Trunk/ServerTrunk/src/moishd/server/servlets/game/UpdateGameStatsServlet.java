package moishd.server.servlets.game;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.GameStatistics;

public class UpdateGameStatsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -741149291956359903L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("gameStatsQueue")) {
			String gameType = request.getParameter("gameType");
			int rank = Integer.parseInt(request.getParameter("rank"));
			int isRank = Integer.parseInt(request.getParameter("isRank"));
			int createGame = Integer.parseInt(request
					.getParameter("createGame"));

			List<GameStatistics> stats = DSCommon.GetGameStatsByType(gameType);

			GameStatistics newGame;
			if ((createGame == 1) && (stats.size() == 0)) {
				newGame = new GameStatistics(gameType);
			} else {
				if (stats.size() > 1) {
					LoggerCommon.Get().LogError(
							this,
							response,
							"Game type " + gameType
									+ " has more than 1 occurences");
					return;
				} else if (stats.size() == 0) {
					newGame = new GameStatistics(gameType);
				} else {
					newGame = stats.get(0);
				}

				if (isRank == 1) {
					newGame.addRank(rank);
				} else {
					newGame.addGamePlayed();
				}
			}

			newGame.SaveChanges();
		}
	}
}
