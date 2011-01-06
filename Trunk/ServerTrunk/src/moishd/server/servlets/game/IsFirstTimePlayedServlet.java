package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class IsFirstTimePlayedServlet extends GeneralServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7089082547031169899L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
	
		super.doPost(request, response);
		
		if (user != null) {
			String gameType = request.getReader().readLine();
			if (!mUser.getGameTypesPlayed().contains(gameType)) {
				mUser.getGameTypesPlayed().add(gameType);
				mUser.SaveChanges();
				response.addHeader("FirstTimePlayed", "");
			}
		}
	}

}
