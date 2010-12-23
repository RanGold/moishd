package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class InviteToGameServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		
		super.doPost(request, response);

		if (user != null) {
			String recID = request.getReader().readLine();
			
			Queue queue = QueueFactory.getQueue("inviteQueue");
			queue.add (TaskOptions.Builder.url("/queues/SendInviteToGame").method(Method.POST).
					param("initID", user.getEmail()).param("recID", recID));
		}
	}
}
