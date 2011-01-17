package moishd.server.servlets.game.busy;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.BusyObject;
import moishd.server.dataObjects.MoishdUser;
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
					.add(new BusyObject(mUser.getUserGoogleIdentifier(), false, mUser.getBusyWith()));
			busyUsers.add(new BusyObject(mUser.getBusyWith(), false, mUser.getUserGoogleIdentifier()));

			String json = GsonCommon.GetJsonString(busyUsers);

			Queue queue = QueueFactory.getQueue("inviteQueue");
			queue.add(TaskOptions.Builder
					.url("/queues/DeleteLastUndecidedGame").method(Method.POST)
					.param("initId", mUser.getUserGoogleIdentifier())
					.param("recId", mUser.getBusyWith()));
			
			queue.add(TaskOptions.Builder
					.url("/queues/UpdateBusySynced").method(Method.POST)
					.param("json", json));
			
			try {
				MoishdUser mRecUser = DSCommon.GetUserByGoogleId(mUser.getBusyWith());
			
				HashMap<String, String> payload = new HashMap<String, String>();
				payload.put("GameId", "");
				payload.put("InitName", mUser.getUserNick());
				payload.put("RecName", mRecUser.getUserNick());
				
				C2DMCommon.PushGenericMessage(mUser.getRegisterID(), 
						C2DMCommon.Actions.GameCanceled.toString(), payload);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
