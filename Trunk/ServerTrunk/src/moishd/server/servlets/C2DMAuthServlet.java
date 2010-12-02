package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;

import com.google.appengine.api.users.UserServiceFactory;

public class C2DMAuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795970457297476756L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		if (UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin()) {
			try {
				DSCommon.SetC2DMAuth(C2DMCommon.getAuthToken(true));
			} catch (IOException e) {
				response.addHeader("Error", "");
				response.getWriter().write("C2DMAuthServlet" + e.getMessage());
			} catch (ServletException e) {
				response.addHeader("Error", "");
				response.getWriter().write("C2DMAuthServlet" + e.getMessage());
			}
		}
	}
}
