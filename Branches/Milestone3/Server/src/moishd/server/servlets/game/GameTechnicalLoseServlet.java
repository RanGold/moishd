package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GameTechnicalLoseServlet  extends GameResultServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5467101390392060133L;


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		servletName = "GameTechnicalLoseServlet";
		winValue = Result.LostTechnicly;
		loseValue = Result.Won;
		super.doPost(request, response);
	}
}
