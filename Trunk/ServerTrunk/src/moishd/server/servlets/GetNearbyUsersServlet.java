package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetNearbyUsersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1688665969608951036L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			LoggerCommon.Get().LogError(this, "Not Logged In");
			response.addHeader("Error", "");
			response.getWriter().write("Not Logged In");
		} else {
			try {
				MoishdUser mUser = DSCommon.GetUserByGoogleId(user.getEmail());
				List<ClientMoishdUser> allUsers =  
						MoishdUser.copyToClientMoishdUserList(DSCommon.GetNearbyUsers(mUser, 1));
				GsonCommon.WriteJsonToResponse(allUsers, response);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().println("GetNearbyUsersServlet: " + e.getMessage());
			}
		}
	}
}
