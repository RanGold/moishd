package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.GsonCommon;
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
			List<ClientMoishdUser> allUsers = MoishdUser
					.copyToClientMoishdUserList(DSCommon.GetNearbyUsers(mUser,1));
			GsonCommon.WriteJsonToResponse(allUsers, response);
		}
	}
}
