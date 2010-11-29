package moishd.serever.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CookieServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getCookies() == null) {
			response.getWriter().println("no cookies");
		} else {
			for (int i = 0; i < request.getCookies().length; i++) {
				response.getWriter().println(
						"Cookie " + i + ": "
								+ request.getCookies()[i].getName());
			}
		}
		response.getWriter().println(
				"<p>" + "<a href=\"/" + "\">Return Home</a>.</p>");

	}
}