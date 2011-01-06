package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class RankGameServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1562495283256401282L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			String input = request.getReader().readLine();				
			String gameType = input.split(":")[0];
			String rank = input.split(":")[1];
			
			Queue queue = QueueFactory.getQueue("gameStatsQueue");
			queue.add (TaskOptions.Builder.url("/queues/UpdateGameStats").method(Method.POST).
					param("gameType", gameType).param("rank", rank).param("isRank", "1"));
		}
	}

}
