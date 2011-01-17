package moishd.server.servlets.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.ConstantLocation;

import com.google.appengine.api.users.UserServiceFactory;

public class AddConstantLocationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6961928549684092647L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
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
				
				List<ConstantLocation> locs = DSCommon.GetConstantLocationsByName(name);
				
				if (locs.size() == 0) {
					ConstantLocation loc;
					if (trophyName.equals("")) {
						loc = new ConstantLocation(Double.valueOf(longitude),
								Double.valueOf(latitude), name);
					} else {
						loc = new ConstantLocation(Double.valueOf(longitude),
								Double.valueOf(latitude), name, trophyName);
					}
					loc.SaveChanges();
				} else if (locs.size() > 1){
					LoggerCommon.Get().LogError(this, response, "Too many locations in the name " + name);
				} else {
					ConstantLocation loc = locs.get(0);
					loc.setLatitude(Double.valueOf(latitude));
					loc.setLongitude(Double.valueOf(longitude));
					
					if (!trophyName.equals("")) {
						loc.setTrophyName(trophyName);
					}
					
					loc.SaveChanges();
				}
				response.sendRedirect("/");
			}
		}

	}
}