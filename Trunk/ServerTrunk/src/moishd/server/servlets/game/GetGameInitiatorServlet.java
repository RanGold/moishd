package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.servlets.GeneralServlet;

public class GetGameInitiatorServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3337103676796125760L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException{
		
		super.doPost(request, response);

		if (user != null) {
			try {
				String gameId = request.getReader().readLine();
				MoishdGame tg = DSCommon.GetGameByIdRecId(gameId, user.getEmail());
				GsonCommon.WriteJsonToResponse(DSCommon.GetUserByGoogleId(tg.getPlayerInitId()), response);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
