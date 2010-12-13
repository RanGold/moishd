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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetAllUsersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7171012395494987373L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().write("Not Logged In");
		} else {
			long amount = -1;
			try {
				String strAmount = request.getReader().readLine();
				amount = Long.valueOf(strAmount);
			} catch (NumberFormatException e) {
				// Ignoring
			}
			try {
				DSCommon.GetUserByGoogleId(user.getEmail());
				List<ClientMoishdUser> allUsers = (amount == -1 ? 
						DSCommon.GetAllRegisteredClientUsers(user.getEmail(), true, amount) :
							DSCommon.GetAllRegisteredClientUsers(user.getEmail(), true));
				GsonCommon.WriteJsonToResponse(allUsers, response);
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("GetAllUsersServlet: " + e.getMessage());
			}
		}
	}
}
