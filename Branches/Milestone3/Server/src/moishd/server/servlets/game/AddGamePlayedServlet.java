package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class AddGamePlayedServlet extends GeneralServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7503858122567616291L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);
		if (user != null) {
			String input = request.getReader().readLine();				
			String gameType = input;
			
			Queue queue = QueueFactory.getQueue("gameStatsQueue");
			queue.add (TaskOptions.Builder.url("/queues/UpdateGameStats").method(Method.POST).
					param("gameType", gameType).param("rank", "-1").param("isRank", "0"));
		}
	}
}
