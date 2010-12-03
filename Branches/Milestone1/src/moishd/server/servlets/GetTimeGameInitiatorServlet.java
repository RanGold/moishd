package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetTimeGameInitiatorServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3337103676796125760L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				DSCommon.GetUserByGoogleId(user.getEmail());

				String gameId = request.getReader().readLine();
				TimeGame tg = DSCommon.GetTimeGameByIdRecId(gameId, user.getEmail());
				MoishdUser muser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
				GsonCommon.WriteJsonToResponse(muser, response);
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("GetTimeGameInitiatorServlet: " + e.getMessage());
			}
		}
	}
}
