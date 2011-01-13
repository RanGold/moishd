package moishd.server.servlets.game.busy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.GsonCommon;
import moishd.server.dataObjects.BusyObject;
import moishd.server.servlets.GeneralServlet;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class CancelGameServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8947016425378186670L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		super.doPost(request, response);

		if (user != null) {
			List<BusyObject> busyUsers = new LinkedList<BusyObject>();
			busyUsers
					.add(new BusyObject(mUser.getUserGoogleIdentifier(), false));
			busyUsers.add(new BusyObject(mUser.getBusyWith(), false));

			String json = GsonCommon.GetJsonString(busyUsers);

			Queue queue = QueueFactory.getQueue("inviteQueue");
			queue.add(TaskOptions.Builder
					.url("/queues/UpdateBusySynced").method(Method.POST)
					.param("json", json));
			
			DSCommon.DeleteLastUnDecidedGame(mUser.getUserGoogleIdentifier(), mUser.getBusyWith());
		}
	}
}
