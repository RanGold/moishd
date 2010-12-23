package moishd.server.servlets.game;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.servlets.GeneralServlet;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class GameResultServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530008367358724317L;
	
	public enum Result {
	/*	LostFirst,
		WonSecond,*/
		Won,
		Lost
	}
	
	protected String servletName;
	protected Result winValue;
	protected Result loseValue;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				Date endDate = new Date();
				String input = request.getReader().readLine();
				String gameId = input.split(":")[0];
				String gameType = input.split(":")[1];
				MoishdGame tg = DSCommon.GetGameById(gameId);
				if (tg.getPlayerInitId().equals(user.getEmail())) {
					tg.setPlayerInitEndTime(endDate);
				} else if (tg.getPlayerRecId().equals(user.getEmail())) {
					tg.setPlayerRecEndTime(endDate);
				} else {
					LoggerCommon.Get().LogError(this, response, servletName + ": player " + 
							user.getEmail() + " isn't in game " + gameId);
					return;
				}
				tg.SaveChanges();
				
				Queue queue = QueueFactory.getQueue("resultQueue");
				queue.add (TaskOptions.Builder.url("/queues/SendGameResult").method(Method.POST).
						param("gameId", gameId).param("gameType", gameType).
						param("winValue", winValue.toString()).param("loseValue", loseValue.toString()));
				
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}

