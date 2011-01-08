package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.LoggerCommon;

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
			if (!mUser.isPartnerWith(mUser.getUserGoogleIdentifier())) {
				LoggerCommon.Get().LogError(this, response,
					"Tried to get un busy (with himself) while busy with " + mUser.getBusyWith());
			} else {
				mUser.setNotBusy();
				mUser.SaveChanges();
			}
		}
	}
}
