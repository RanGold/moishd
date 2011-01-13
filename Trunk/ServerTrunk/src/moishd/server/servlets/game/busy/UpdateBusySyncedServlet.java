package moishd.server.servlets.game.busy;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.BusyObject;
import moishd.server.dataObjects.MoishdUser;

import com.google.gson.reflect.TypeToken;

public class UpdateBusySyncedServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9109001677014006249L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("inviteQueue")) {
			try { 
				List<BusyObject> busyUsers = GsonCommon.GetObjFromJsonString(request.getParameter("json"), 
						new TypeToken<List<BusyObject>>(){}.getType());
				
				for (BusyObject user : busyUsers) {
					List<MoishdUser> mUsers = DSCommon.GetUsersByGoogleId(user.getUserId());
					if (mUsers.size() == 0) {
						LoggerCommon.Get().LogInfo(this, "User " + user.getUserId() + " not found");
					} else {
						MoishdUser mUser = mUsers.get(0);
					
						if (!user.isBusy()) {
							mUser.setNotBusy();
							mUser.SaveChanges();
						} else {
							mUser.setPartner(user.getBusyWith());
							mUser.SaveChanges();
						}
					}
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			}
		}
	}
}
