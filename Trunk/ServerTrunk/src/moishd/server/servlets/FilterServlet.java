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
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.reflect.TypeToken;

public class FilterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4061754324599668956L;

	protected String servletName;
	protected String fieldName;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().write("Not Logged In");
		} else {
			try {
				DSCommon.GetUserByGoogleId(user.getEmail());
				
				List<String> filterValues =  
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<List<String>>(){}.getType());
				
				List<ClientMoishdUser> users = DSCommon.GetFilteredRegisteredClientUsers(user.getEmail(), true, 
						fieldName, filterValues);
				GsonCommon.WriteJsonToResponse(users, response);
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println(servletName + ": " + e.getMessage());
			} catch (ClassNotFoundException e) {
				response.addHeader("Error", "");
				response.getWriter().println(servletName + ": " + e.getMessage());
			} catch (SecurityException e) {
				response.addHeader("Error", "");
				response.getWriter().println(servletName + ": " + e.getMessage());
			}
		}
	}
}
