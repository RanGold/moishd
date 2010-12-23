package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

public class HasLocationServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8007187587366249474L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				MoishdUser mUser = DSCommon.GetUserByGoogleId(user.getEmail());
				if (mUser.getLocation().isInitialized()) {
					response.addHeader("HasLocation", "");
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
