package moishd.server.servlets.test;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.dataObjects.ConstantLocation;

import com.google.appengine.api.users.UserServiceFactory;

public class AddConstantLocationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6961928549684092647L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!UserServiceFactory.getUserService().isUserLoggedIn()) {
			response.sendRedirect("/test/Login");
		} else {
			if (!UserServiceFactory.getUserService().isUserAdmin()) {
				response.getWriter().println("<p>" +
	                    "You need to be admin to delete users.</p>");
				response.getWriter().println("<p>" +
	                    "<a href=\"/" +
	                    "\">Return Home</a>.</p>");
			} else {
				String name = request.getParameter("name");
				String longitude = request.getParameter("longitude");
				String latitude = request.getParameter("latitude");
				String trophyName = request.getParameter("trophyName");
				
				ConstantLocation loc = new ConstantLocation(Double.valueOf(longitude), Double.valueOf(latitude), name, trophyName);
				loc.SaveChanges();
			}
		}

	}
}