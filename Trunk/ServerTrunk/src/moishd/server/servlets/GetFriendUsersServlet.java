package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;

import com.google.gson.reflect.TypeToken;

public class GetFriendUsersServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1688665969608951036L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		
		super.doPost(request, response);

		if (user != null) {
			try {
				List<String> filterValues = GsonCommon.GetObjFromJsonStream(
						request.getInputStream(),
						new TypeToken<List<String>>() {
						}.getType());
				
				if (filterValues.size() == 0){
					filterValues = mUser.getFriendsFacebookIds();
				}
				else {
					mUser.setFriendsFacebookIds(DSCommon.GetExistingFacebookIds(filterValues));
					mUser.SaveChanges();
				}

				List<ClientMoishdUser> users = DSCommon
						.GetFilteredRegisteredClientUsers(user.getEmail(),
								true, "facebookID", filterValues);
				
				for (ClientMoishdUser cUser : users){
					LoggerCommon.Get().LogInfo("Tammy", cUser.getUserNick().toString() );
					}
				GsonCommon.WriteJsonToResponse(users, response);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			} catch (SecurityException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			}
		}
	}
}