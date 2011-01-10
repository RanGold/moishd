package moishd.server.servlets.game.busy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.LoggerCommon;
import moishd.server.servlets.GeneralServlet;

public class SetBusyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8141924964503496779L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		checkBusy = false;
		super.doPost(request, response);

		if (user != null) {
			if (mUser.isBusy()) {
				LoggerCommon.Get().LogError(this, response,
					"Tried to get busy (with himself) while busy with " + mUser.getBusyWith());
			} else {
				mUser.setPartner(mUser.getUserGoogleIdentifier());
				mUser.SaveChanges();
			}
		}
	}
}
