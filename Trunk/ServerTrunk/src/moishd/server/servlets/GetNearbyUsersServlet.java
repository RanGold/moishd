package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

public class GetNearbyUsersServlet extends GeneralServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1688665969608951036L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				MoishdUser mUser = DSCommon.GetUserByGoogleId(user.getEmail());
				List<ClientMoishdUser> allUsers =  
						MoishdUser.copyToClientMoishdUserList(DSCommon.GetNearbyUsers(mUser, 1));
				GsonCommon.WriteJsonToResponse(allUsers, response);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
