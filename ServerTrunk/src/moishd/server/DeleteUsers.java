package moishd.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DeleteUsers extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -141938567919490948L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.sendRedirect("/LoginServlet");
		}
		else {
			if (!user.getEmail().equals("BArkamir@gmail.com") &&
					!user.getEmail().equals("dagan.tammy@gmail.com") &&
					!user.getEmail().equals("hila.barzilai@gmail.com") &&
					!user.getEmail().equals("mran.goldberg@gmail.com")) {
				response.getWriter().println(user.getEmail() + " is not authorized to delete users");
				response.getWriter().println("<p>" +
	                    "<a href=\"/" +
	                    "\">Return Home</a>.</p>");	
			}
			else {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				try {
					pm.newQuery(MoishdUser.class).deletePersistentAll();
				} finally {
					pm.close();
				}
				response.sendRedirect("/");
			}
		}
	}
}