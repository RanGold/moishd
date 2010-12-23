package moishd.server.servlets.C2DM;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class UnRegisterServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028249054566845894L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				MoishdUser muser = DSCommon.GetUserByGoogleId(user.getEmail());
				muser.setRegisterID("NULL");
				muser.setRegistered(false);
				muser.setBusy(false);
				muser.getLocation().setLatitude(200);
				muser.getLocation().setLongitude(200);
				muser.SaveChanges();
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
