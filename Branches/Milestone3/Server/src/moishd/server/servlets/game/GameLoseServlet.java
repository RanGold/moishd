package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameLoseServlet extends GameResultServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4781789030101766984L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		servletName = "GameLoseServlet";
		winValue = Result.Lost;
		loseValue = Result.Won;
		super.doPost(request, response);
	}
}
