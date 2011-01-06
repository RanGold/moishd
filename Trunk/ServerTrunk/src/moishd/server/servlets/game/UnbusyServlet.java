package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class UnbusyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8947016425378186670L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		super.doPost(request, response);

		if (user != null) {
			try {
				MoishdUser otherUser = DSCommon.GetUserByGoogleId(mUser.getBusyWith());
				mUser.setNotBusy();
				mUser.SaveChanges();
				otherUser.setNotBusy();
				otherUser.SaveChanges();
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
