package moishd.server.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

import com.google.gson.reflect.TypeToken;

public class UserLoginServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306793921674383722L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		
		doesExist = false;
		
		super.doPost(request, response);

		if (user != null) {
			try {
				ClientMoishdUser newUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());
				
				MoishdUser muser;

				List<MoishdUser> users = DSCommon.GetUsersByGoogleId(user.getEmail());
				if (users.size() > 1) {
					throw new DataAccessException("user " + user.getEmail() + " more than 1 result");
				} else if (users.size() == 0) {	
					LoggerCommon.Get().LogInfo(this, "New user: " + user.getEmail());
					muser = new MoishdUser(newUser.getUserNick(), newUser.getPictureLink(), 
							user.getEmail(), newUser.getRegisterID(), newUser.getFacebookID(), "MAC");
				} else {
					LoggerCommon.Get().LogInfo(this, "User exist: " + user.getEmail());
					muser = users.get(0);
					if (!muser.getFacebookID().equals(newUser.getFacebookID())) {
						LoggerCommon.Get().LogError(this, response, "AccountNotMatch", "Facebook id given " + 
								"differes from the database version");
						return;
					}
				}
				
				if (muser.isRegistered() && (muser.getIsAlive() == 1)) {
					muser.InitUser();
					LoggerCommon.Get().LogInfo(this, "Logging off current user connection");
				}
				
				if (muser.isRegistered() && (muser.getIsAlive() == 0)) {
					LoggerCommon.Get().LogError(this, response, "AlreadyLoggedIn", 
							"Tried to login twice with the same user");
					muser.setIsAlive(1);
				} else {
					muser.getLocation().setLatitude(newUser.getLocation().getLatitude());
					muser.getLocation().setLongitude(newUser.getLocation().getLongitude());
					muser.setNotBusy();
					muser.setRegistered(true);
					muser.setIsAlive(0);
					muser.setRegisterID(newUser.getRegisterID());
				}
				
				muser.SaveChanges();
				if (muser.getIsAlive() == 1) {
					LoggerCommon.Get().LogInfo(this, "Checking current user connection");
					C2DMCommon.PushGenericMessage(muser.getRegisterID(), 
							C2DMCommon.Actions.CheckAlive.toString(), new HashMap<String, String>());
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}