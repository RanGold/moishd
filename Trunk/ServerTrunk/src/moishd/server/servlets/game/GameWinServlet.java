package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameWinServlet extends GameResultServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5653745064360245143L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		servletName = "GameWinServlet";
		winValue = Result.Won;
		loseValue = Result.Lost;
		super.doPost(request, response);
	}
}
