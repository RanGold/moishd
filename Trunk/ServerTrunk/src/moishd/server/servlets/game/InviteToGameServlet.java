package moishd.server.servlets.game;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.reflect.TypeToken;

public class InviteToGameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				MoishdUser initUser = DSCommon.GetUserByGoogleId(user.getEmail());
				
				ClientMoishdUser clientUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());
				MoishdUser invUser = DSCommon.GetUserByGoogleId(clientUser.getUserGoogleIdentifier());
				
				if (invUser.isBusy()) {
					C2DMCommon.PushGenericMessage(invUser.getRegisterID(), 
							C2DMCommon.Actions.PlayerBusy.toString(), new HashMap<String, String>());
				} else {
					initUser.setBusy(true);
					initUser.SaveChanges();
					invUser.setBusy(true);
					invUser.SaveChanges();
					
					MoishdGame tg = new MoishdGame(user.getEmail(), clientUser.getUserGoogleIdentifier());
					tg.SaveChanges();
					response.getWriter().write(String.valueOf(tg.getGameLongId()));

					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId", String.valueOf(tg.getGameId().getId()));
					C2DMCommon.PushGenericMessage(invUser.getRegisterID(), 
							C2DMCommon.Actions.GameInvitation.toString(), payload);
				}
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("InviteToTimeGameServlet: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				response.addHeader("Error", "");
				response.getWriter().println("InviteToTimeGameServlet: " + e.getMessage());
			} catch (ServletException e) {
				response.addHeader("Error", "");
				response.getWriter().println("InviteToTimeGameServlet: " + e.getMessage());
			}
		}
	}
}
