package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
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

		super.doPost(request, response);

		if (user != null) {
			try {
				ClientMoishdUser newUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());
				
				MoishdUser muser;
				
				if (!DSCommon.DoesUserByGoogleIdExist(user.getEmail())) {	
					muser = new MoishdUser(newUser.getUserNick(), newUser.getPictureLink(), 
							user.getEmail(), "NULL", newUser.getFacebookID(), newUser.getMACAddress());
				} else {
					muser = DSCommon.GetUserByGoogleId(user.getEmail()) ;
					if (!muser.getFacebookID().equals(newUser.getFacebookID())) {
						response.addHeader("Error", "");
						response.getWriter().println("UserLoginServlet: facebook id given " + 
								"differes from the database version");
						return;
					}
				}
				
				muser.setMACAddress(newUser.getMACAddress());
				muser.getLocation().setLatitude(newUser.getLocation().getLatitude());
				muser.getLocation().setLongitude(newUser.getLocation().getLongitude());
						
				muser.SaveChanges();
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}