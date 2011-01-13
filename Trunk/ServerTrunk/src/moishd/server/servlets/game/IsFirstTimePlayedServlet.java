package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.dataObjects.StringIntPair;
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
			if (!mUser.getGameTypesPlayed().contains(new StringIntPair(gameType, 0))) {
				mUser.getGameTypesPlayed().add(new StringIntPair(gameType, 1));
				mUser.SaveChanges();
			}
			else {
				int index = mUser.getGameTypesPlayed().indexOf(new StringIntPair(gameType, 0));
				StringIntPair pair = mUser.getGameTypesPlayed().get(index);
				pair.setNumberValue(pair.getNumberValue() + 1);
				if (pair.getNumberValue() == 3){
					response.addHeader("ThirdTimePlayed", "");
				}
				mUser.SaveChanges();
			}
		}
	}
}
