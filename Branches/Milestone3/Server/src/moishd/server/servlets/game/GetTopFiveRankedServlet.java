package moishd.server.servlets.game;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.GsonCommon;
import moishd.server.servlets.GeneralServlet;

public class GetTopFiveRankedServlet extends GeneralServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4504932226580707385L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			List<String> topFiveRanked = DSCommon.GetTopFiveGames("gameRank");
			GsonCommon.WriteJsonToResponse(topFiveRanked, response);
		}
	}

}
