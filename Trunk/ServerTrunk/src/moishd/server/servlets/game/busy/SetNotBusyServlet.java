package moishd.server.servlets.game.busy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class SetNotBusyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8482003842059400287L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		checkBusy = false;
		super.doPost(request, response);

		if (user != null) {
			Queue queue = QueueFactory.getQueue("inviteQueue");
			queue.add(TaskOptions.Builder.url("/SetNotBusy")
					.method(Method.GET)
					.param("id", mUser.getUserGoogleIdentifier()));
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("inviteQueue")) {
			try {
				String id = request.getParameter("id");
				MoishdUser mUser = DSCommon.GetUserByGoogleId(id);

				if (!mUser.isPartnerWith(mUser.getUserGoogleIdentifier())) {
					LoggerCommon.Get().LogInfo(this,
							"Tried to get un busy (with himself) while " + (!mUser.isBusy() ? "not busy" : 
								"busy with " + mUser.getBusyWith()));
				} else {
					mUser.setNotBusy();
					mUser.SaveChanges();
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			}
		}
	}
}
