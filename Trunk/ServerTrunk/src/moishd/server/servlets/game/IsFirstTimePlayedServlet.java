package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

import moishd.server.common.LoggerCommon;
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
			// Counting palyed time and replying if it is the third time played
			if (!mUser.getGameTypesPlayed().containsKey(gameType)) {
				mUser.getGameTypesPlayed().put(gameType, 1);
				mUser.SaveChanges();
			}
			else {
				Integer amountPlayed = mUser.getGameTypesPlayed().get(gameType);
				LoggerCommon.Get().LogInfo(this, "user: " + 
						mUser.getUserGoogleIdentifier() + 
						" game type:" + gameType + 
						" number: " + String.valueOf(amountPlayed));
				mUser.getGameTypesPlayed().remove(gameType);
				mUser.getGameTypesPlayed().put(gameType, ++amountPlayed);
				if (amountPlayed == 3){
					response.addHeader("ThirdTimePlayed", "");
					Queue queue = QueueFactory.getQueue("inviteQueue");
					queue.add(TaskOptions.Builder.url("/SetBusy")
							.method(Method.GET)
							.param("id", mUser.getUserGoogleIdentifier()));
				}
				mUser.SaveChanges();
			}
		}
	}
}
